package com.mufeng.admin.boilerplate.common.user.service;

import com.mufeng.admin.boilerplate.common.components.RedisOperator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Author HuangTianyu
 * @Date 2019-12-01 16:44
 * @Version 1.0
 */
@Service
public class UserDenyService {
    @Resource
    private RedisOperator redisOperator;
    @Resource
    private UserService userService;

    public Long addLoginTimes(Long uid) {
        return redisOperator.increment(generateKey(uid), 1L, TimeUnit.DAYS);
    }

    public void removeLoginTimes(Long uid) {
        redisOperator.delete(generateKey(uid));
    }

    public void lockUser(Long uid) {
        userService.disableByUid(uid);
    }

    private String generateKey(Long uid) {
        return redisOperator.buildKeyWithPrefix("UserPasswordService:verifyPassword:times", String.valueOf(uid));
    }
}
