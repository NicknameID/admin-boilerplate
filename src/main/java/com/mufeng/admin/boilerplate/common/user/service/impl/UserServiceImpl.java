package com.mufeng.admin.boilerplate.common.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mufeng.admin.boilerplate.common.components.JwtTokenOperator;
import com.mufeng.admin.boilerplate.common.context.RequestContext;
import com.mufeng.admin.boilerplate.common.user.exception.PasswordErrorException;
import com.mufeng.admin.boilerplate.common.user.exception.UserExistedException;
import com.mufeng.admin.boilerplate.common.user.mapper.UserMapper;
import com.mufeng.admin.boilerplate.common.user.model.dto.UserUpdateParam;
import com.mufeng.admin.boilerplate.common.user.model.entity.User;
import com.mufeng.admin.boilerplate.common.user.service.CustomUserDetailsService;
import com.mufeng.admin.boilerplate.common.user.service.UserDenyService;
import com.mufeng.admin.boilerplate.common.user.service.UserPasswordService;
import com.mufeng.admin.boilerplate.common.user.service.UserService;
import com.mufeng.admin.boilerplate.common.util.IdUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @Author HuangTianyu
 * @Date 2019-11-29 16:11
 * @Version 1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private JwtTokenOperator jwtTokenOperator;
    @Resource
    private UserDenyService userDenyService;
    @Resource
    private UserPasswordService userPasswordService;
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private CustomUserDetailsService customUserDetailsService;
    @Resource
    private RequestContext context;

    @Override
    public String login(String username, String password) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken( username, password );
        Authentication authentication;
        Optional<User> optionalUser = getByUsername(username);;
        optionalUser.ifPresent(user -> {
            userDenyService.verifyTryTimes(user.getId());
        });
        try {
            authentication = authenticationManager.authenticate(upToken);
        }catch (AuthenticationException e) {
            optionalUser.ifPresent(user -> userDenyService.plus(user.getId()));
            throw new PasswordErrorException();
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = customUserDetailsService.loadUserByUsername( username );
        final String token = jwtTokenOperator.generateToken(userDetails);
        optionalUser.ifPresent(user -> userDenyService.clear(user.getId()));
        return token;
    }

    @Override
    public User my() {
        Long uid = context.getUid();
        return this.getById(uid);
    }

    public Optional<User> getUserByToken(String token) {
        if (StringUtils.isEmpty(token)) return Optional.empty();
        String username = jwtTokenOperator.getUsernameFromToken(token);
        return getByUsername(username);
    }


    public Optional<User> getByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getUsername, username);
        User user = getOne(queryWrapper);
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

    public void addUser(String username, String password) {
        Optional<User> optionalUser = getByUsername(username);
        if (optionalUser.isPresent()) {
            throw new UserExistedException();
        }
        User user = new User();
        user.setId(IdUtils.generalLongId());
        user.setUsername(username);
        user.setPassword(userPasswordService.encodePassword(password));
        user.setActive(true);
        user.setCreatedTime(LocalDateTime.now());
        user.setUpdatedTime(LocalDateTime.now());
        save(user);
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
