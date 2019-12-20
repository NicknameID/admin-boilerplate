package com.mufeng.admin.boilerplate.common.user.service;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mufeng.admin.boilerplate.common.components.RedisOperator;
import com.mufeng.admin.boilerplate.common.system_config.service.ConfigService;
import com.mufeng.admin.boilerplate.common.user.exception.InvalidTokenException;
import com.mufeng.admin.boilerplate.common.user.exception.UserExistedException;
import com.mufeng.admin.boilerplate.common.user.mapper.UserMapper;
import com.mufeng.admin.boilerplate.common.user.model.dto.UserUpdateParam;
import com.mufeng.admin.boilerplate.common.user.model.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @Author HuangTianyu
 * @Date 2019-11-29 16:11
 * @Version 1.0
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    @Resource
    private RedisOperator redisOperator;
    @Resource
    private UserDenyService userDenyService;
    @Resource
    private ConfigService configService;
    @Resource
    private UserPasswordService userPasswordService;

    public List<User> queryByRoleCode(String roleCode) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getRoleCode, roleCode);
        return this.list(queryWrapper);
    }

    public Integer countByRoleCode(String roleCode) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getRoleCode, roleCode);
        return this.count(queryWrapper);
    }

    /**
     * 获取令牌
     * @return
     */
    public String getAccessToken(Long uid) {
        String token = IdUtil.randomUUID().toUpperCase();
        // 保存到redis
        String key = redisOperator.buildKeyWithPrefix("User.token", token);
        Integer tokenExpire = configService.get("login.expire", Integer.class);
        // 缓存token
        redisOperator.set(key, String.valueOf(uid), tokenExpire);
        // 移除登录次数标记
        userDenyService.removeLoginTimes(uid);
        return token;
    }


    public Optional<User> getByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getUsername, username);
        User user = getOne(queryWrapper);
        return Optional.ofNullable(user);
    }

    public Optional<User> getUserByUid(Long uid) {
        User user = getById(uid);
        return Optional.ofNullable(user);
    }

    public boolean loginUpdate(Long uid, String token, String lastIP) {
        User user = new User();
        user.setId(uid);
        user.setLastToken(token);
        user.setLastIp(lastIP);
        user.setLastLogin(LocalDateTime.now());
        user.setUpdatedTime(LocalDateTime.now());
        return updateById(user);
    }

    public void disableByUid(Long uid) {
        User user = new User();
        user.setId(uid);
        user.setActive(false);
        updateById(user);
    }

    public Optional<User> getUserByToken(String token) {
        Optional<Long> uid = getUidByToken(token);
        if (uid.isEmpty()) {
            return Optional.empty();
        }
        return getUserByUid(uid.get());
    }

    public void addUser(String username, String password, boolean staff, String roleCode) {
        Optional<User> optionalUser = getByUsername(username);
        if (optionalUser.isPresent()) {
            throw new UserExistedException();
        }
        User user = new User();
        user.setId(com.mufeng.admin.boilerplate.common.util.IdUtil.generalLongId());
        user.setUsername(username);
        user.setPassword(userPasswordService.encodePassword(password));
        user.setActive(true);
        user.setStaff(staff);
        user.setSuperuser(false);
        user.setRoleCode(roleCode);
        user.setCreatedTime(LocalDateTime.now());
        user.setUpdatedTime(LocalDateTime.now());
        save(user);
    }

    public void verifyToken(String token, Boolean throwException) {
        if (throwException) throw new InvalidTokenException();
        Optional<Long> uid = getUidByToken(token);
        if (uid.isEmpty()) {
            throw new InvalidTokenException();
        }
    }

    public Optional<Long> getUidByToken(String token) {
        String key = redisOperator.buildKeyWithPrefix("User.token", token);
        Object value = redisOperator.get(key);
        if (value == null) {
            return Optional.empty();
        }

        return Optional.of(Long.valueOf(value.toString()));
    }

    public void updateUser(Long uid, UserUpdateParam userUpdateParam) {
        User user = new User();
        BeanUtils.copyProperties(userUpdateParam, user);
        user.setId(uid);
        if (user.getPassword() != null) {
            user.setPassword(userPasswordService.encodePassword(user.getPassword()));
        }
        updateById(user);
    }
}
