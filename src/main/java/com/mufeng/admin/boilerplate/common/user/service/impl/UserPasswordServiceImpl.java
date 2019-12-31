package com.mufeng.admin.boilerplate.common.user.service.impl;

import com.mufeng.admin.boilerplate.common.system_config.service.ConfigService;
import com.mufeng.admin.boilerplate.common.exception.RetryToManyTimesException;
import com.mufeng.admin.boilerplate.common.user.exception.PasswordErrorException;
import com.mufeng.admin.boilerplate.common.user.model.entity.User;
import com.mufeng.admin.boilerplate.common.user.service.UserPasswordService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @Author HuangTianyu
 * @Date 2019-12-01 14:10
 * @Version 1.0
 */
@Service
public class UserPasswordServiceImpl implements UserPasswordService {
    @Resource
    private UserService userService;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private UserDenyServiceImpl userDenyServiceImpl;
    @Resource
    private ConfigService configService;

    @Override
    public void verifyPassword(Long uid, String password) {
        Long tryTimes = userDenyServiceImpl.plus(uid);
        Integer maxTimes = configService.get("login.maxRetryTimes", Integer.TYPE);
        if (tryTimes != null && tryTimes >= maxTimes) {
            userDenyServiceImpl.lock(uid);
            throw new RetryToManyTimesException();
        }
        if (!checkPassword(uid, password)) {
            throw new PasswordErrorException();
        }
    }

    @Override
    public boolean checkPassword(Long uid, String password) {
        User user = userService.getById(uid);
        String trulyPassword = user.getPassword();
        return passwordEncoder.matches(password, trulyPassword);
    }

    @Override
    public String encodePassword(String rawPassword) {
        if (Objects.isNull(rawPassword)) return null;
        return passwordEncoder.encode(rawPassword);
    }
}
