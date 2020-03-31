package tech.mufeng.admin.boilerplate.common.acl.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.mufeng.admin.boilerplate.common.acl.mapper.UserMapper;
import tech.mufeng.admin.boilerplate.common.acl.model.entity.User;
import tech.mufeng.admin.boilerplate.common.acl.service.LoginRetryTimeService;
import tech.mufeng.admin.boilerplate.common.acl.service.AclUserService;
import tech.mufeng.admin.boilerplate.common.constant.UserSessionField;
import tech.mufeng.admin.boilerplate.common.exception.CustomExceptionEnum;
import tech.mufeng.admin.boilerplate.common.util.IpUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Service
public class AclUserServiceImpl extends ServiceImpl<UserMapper, User> implements AclUserService {
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private HttpSession httpSession;
    @Resource
    private LoginRetryTimeService loginRetryTimeService;
    @Resource
    private HttpServletRequest httpServletRequest;

    @Override
    public User getUserByUsername(String username) {
        return this.getOne(
                Wrappers.<User>lambdaQuery().eq(User::getUsername, username)
        );
    }

    @Override
    public User getUserByUid(long uid) {
        return this.getById(uid);
    }

    @Override
    public User login(String username, String password) {
        User user =  getUserByUsername(username);
        if (ObjectUtils.isEmpty(user)) {
            CustomExceptionEnum.NOT_FOUND_EXCEPTION.throwException("该用户不存在");
        }
        long uid = user.getUid();
        String encodedPassword = user.getPassword();

        if (!loginRetryTimeService.enableLogin(uid)) {
            CustomExceptionEnum.LOGIN_EXCEPTION.throwException("尝试次数过多，账号被锁定24小时，请尝试联系管理员");
        }

        boolean verifySuccess = passwordEncoder.matches(password, encodedPassword);
        if (!verifySuccess) {
            loginRetryTimeService.increment(uid);
            CustomExceptionEnum.LOGIN_EXCEPTION.throwException("密码错误");
        }

        // 更新user信息
        User userQuery = new User();
        userQuery.setUid(uid);
        userQuery.setLastIp(IpUtil.getIp(httpServletRequest));
        userQuery.setLastLogin(LocalDateTime.now());
        userQuery.setUpdatedTime(LocalDateTime.now());
        this.updateById(userQuery);

        loginRetryTimeService.clear(uid);
        httpSession.setAttribute(UserSessionField.UID, uid);
        httpSession.setAttribute(UserSessionField.ACTIVE, true);
        httpSession.setAttribute(UserSessionField.LOGIN_TIME_AT, LocalDateTime.now());

        return user;
    }

    @Override
    public void logout() {
        httpSession.invalidate();
    }
}
