package com.mufeng.admin.boilerplate.common.components;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @Author HuangTianyu
 * @Date 2019-08-05 14:52
 * @Version 1.0
 */
@Component
public class RedisOperator {
    @Value("${spring.application.name}")
    private String applicationName;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    public boolean hasKey(String key) {
        if (key == null) return false;
        Boolean hasKey = redisTemplate.hasKey(key);
        return Objects.isNull(hasKey) ? false : hasKey;
    }

    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            }else {
                redisTemplate.opsForValue().set(key, value);
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 叠加器
     * @param key
     * @param time
     * @param timeUnit
     * @return
     */
    public Long increment(String key, Long time, TimeUnit timeUnit) {
        BoundValueOperations<String, Object> ops = redisTemplate.boundValueOps(key);
        Long times = ops.increment();
        ops.expire(time, timeUnit);
        if (times == null) return null;
        return times;
    }

    /**
     * redis键名构建器
     * @param modelName
     * @param dataId
     * @return
     */
    public String buildKeyWithPrefix(String modelName, String dataId) {
        String appName = applicationName.isEmpty() ? "admin-boilerpalte" : applicationName;
        return buildKey(appName, modelName, dataId);
    }

    public static String buildKey(String prefix, String modelName, String dataId) {
        return String.join(":", prefix, modelName, dataId);
    }
}
