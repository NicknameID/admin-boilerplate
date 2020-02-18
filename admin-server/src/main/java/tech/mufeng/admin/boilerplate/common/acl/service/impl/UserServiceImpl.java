package tech.mufeng.admin.boilerplate.common.acl.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.mufeng.admin.boilerplate.common.acl.mapper.UserMapper;
import tech.mufeng.admin.boilerplate.common.acl.model.entity.User;
import tech.mufeng.admin.boilerplate.common.acl.service.UserService;
import tech.mufeng.admin.boilerplate.common.constant.UserSessionField;
import tech.mufeng.admin.boilerplate.common.exception.CustomExceptionEnum;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private HttpSession httpSession;

    @Override
    public User getUserByUsername(String username) {
        return this.getOne(
                Wrappers.<User>lambdaQuery().eq(User::getUsername, username)
        );
    }

    @Override
    public void login(String username, String password) {
        User user =  getUserByUsername(username);
        if (ObjectUtils.isEmpty(user)) CustomExceptionEnum.NOT_FOUND_EXCEPTION.throwException("该用户不存在");
        String encodedPassword = user.getPassword();
        boolean verifySuccess = passwordEncoder.matches(password, encodedPassword);
        if (!verifySuccess) {
            CustomExceptionEnum.LOGIN_EXCEPTION.throwException("密码错误");
        }
        long uid = user.getUid();
        httpSession.setAttribute(UserSessionField.UID, uid);
        httpSession.setAttribute(UserSessionField.ACTIVE, true);
        httpSession.setAttribute(UserSessionField.LOGIN_TIME_AT, LocalDateTime.now());
    }

    @Override
    public void logout() {
        httpSession.invalidate();
    }
}
