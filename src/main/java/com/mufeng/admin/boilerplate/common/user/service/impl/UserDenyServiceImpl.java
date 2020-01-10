package com.mufeng.admin.boilerplate.common.user.service.impl;

import com.mufeng.admin.boilerplate.common.components.RedisOperator;
import com.mufeng.admin.boilerplate.common.constant.ConfigConst;
import com.mufeng.admin.boilerplate.common.exception.RetryToManyTimesException;
import com.mufeng.admin.boilerplate.common.system_config.service.ConfigService;
import com.mufeng.admin.boilerplate.common.user.service.UserDenyService;
import com.mufeng.admin.boilerplate.common.user.service.UserService;
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
    @Resource
    private ConfigService configService;

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
        return redisOperator.buildKeyWithPrefix("tryToLoginTimes", String.valueOf(uid));
    }

    public void verifyTryTimes(Long uid) {
        Long tryTimes = plus(uid);
        Integer maxTimes = configService.get(ConfigConst.LoginMaxRetryTimes, Integer.TYPE);
        if (tryTimes != null && tryTimes >= maxTimes) {
            lock(uid);
            throw new RetryToManyTimesException();
        }
    }
}
