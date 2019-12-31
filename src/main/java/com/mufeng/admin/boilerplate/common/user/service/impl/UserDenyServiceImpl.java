package com.mufeng.admin.boilerplate.common.user.service.impl;

import com.mufeng.admin.boilerplate.common.components.RedisOperator;
import com.mufeng.admin.boilerplate.common.user.service.UserDenyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Author HuangTianyu
 * @Date 2019-12-01 16:44
 * @Version 1.0
 */
@Service
public class UserDenyServiceImpl implements UserDenyService {
    @Resource
    private RedisOperator redisOperator;
    @Resource
    private UserService userService;

    public Long plus(Long uid) {
        return redisOperator.increment(generateKey(uid), 1L, TimeUnit.DAYS);
    }

    public void clear(Long uid) {
        redisOperator.delete(generateKey(uid));
    }

    public void lock(Long uid) {
        userService.disableByUid(uid);
    }

    public String generateKey(Long uid) {
        return redisOperator.buildKeyWithPrefix("UserPasswordService:verifyPassword:times", String.valueOf(uid));
    }
}
