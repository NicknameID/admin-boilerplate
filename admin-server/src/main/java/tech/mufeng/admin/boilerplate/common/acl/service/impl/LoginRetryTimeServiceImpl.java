package tech.mufeng.admin.boilerplate.common.acl.service.impl;

import org.springframework.stereotype.Service;
import tech.mufeng.admin.boilerplate.common.acl.service.LoginRetryTimeService;
import tech.mufeng.admin.boilerplate.common.components.RedisOperator;
import tech.mufeng.admin.boilerplate.common.constant.ConfigConst;
import tech.mufeng.admin.boilerplate.common.system_config.service.ConfigService;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class LoginRetryTimeServiceImpl implements LoginRetryTimeService {
    private final static String LOGIN_RETRY_TIMES_PREFIX = "LoginRetryTimeService";
    private final static long LOGIN_RETRY_TIMES_EXPIRE = 24;
    @Resource
    private RedisOperator redisOperator;
    @Resource
    private ConfigService configService;

    private String getKey(long uid) {
        return redisOperator.buildKeyWithPrefix(LOGIN_RETRY_TIMES_PREFIX, String.valueOf(uid));
    }

    @Override
    public boolean enableLogin(long uid) {
        int currentRetryTimes = getCurrentRetryTimes(uid);
        int loginMaxRetryTimes = getLoginMaxRetryTimes();
        return currentRetryTimes <= Math.max(loginMaxRetryTimes - 1, 0);
    }

    private int getCurrentRetryTimes(long uid) {
        String key = getKey(uid);
        Object retryTimesObj = redisOperator.get(key);
        if (retryTimesObj == null) {
            return 0;
        }
        return (int) retryTimesObj;
    }

    private int getLoginMaxRetryTimes() {
        return configService.get(ConfigConst.LoginMaxRetryTimes, Integer.class);
    }

    @Override
    public void increment(long uid) {
        String key = getKey(uid);
        redisOperator.increment(key, LOGIN_RETRY_TIMES_EXPIRE, TimeUnit.HOURS);
    }

    @Override
    public void clear(long uid) {
        String key = getKey(uid);
        redisOperator.delete(key);
    }
}
