package com.mufeng.admin.boilerplate.common.user.service.impl;

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
    private PasswordEncoder passwordEncoder;

    @Override
    public String encodePassword(String rawPassword) {
        if (Objects.isNull(rawPassword)) return null;
        return passwordEncoder.encode(rawPassword);
    }
}
