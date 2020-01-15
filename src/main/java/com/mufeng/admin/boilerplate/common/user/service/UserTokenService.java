package com.mufeng.admin.boilerplate.common.user.service;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.mufeng.admin.boilerplate.common.components.RedisOperator;
import com.mufeng.admin.boilerplate.common.constant.ConfigConst;
import com.mufeng.admin.boilerplate.common.system_config.service.ConfigService;
import com.mufeng.admin.boilerplate.common.user.model.entity.User;
import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @Author HuangTianyu
 * @Date 2020-01-08 17:54
 * @Version 1.0
 */
@Service
public class UserTokenService {
    private static final String PAYLOAD_KEY_USERNAME = "username";
    private static final String PAYLOAD_KEY_CREATED = "created";
    private static final String PAYLOAD_KEY_EXPIRES = "exipresAt";
    private static final String TOKEN_CACHE_PREFIX = "user.token";

    @Resource
    private ConfigService configService;

    @Resource
    private RedisOperator redisOperator;

    public String getUsernameFromToken(@NonNull String token) {
        Map<String, Object> payload = getPayloadFromToken(token);
        Object username = payload.get(PAYLOAD_KEY_USERNAME);
        return username == null ? "" : username.toString();
    }

    private Map<String, Object> getPayloadFromToken(@NonNull String token) {
        // 缓存token
        Object content = getCacheToken(token);
        if (content == null) return Maps.newHashMap();
        return JSON.parseObject(content.toString());
    }

    private Object getCacheToken(String token) {
        String key = redisOperator.buildKeyWithPrefix(TOKEN_CACHE_PREFIX, token);
        return redisOperator.get(key);
    }

    public String generateToken(@NonNull User user) {
        final LocalDateTime expiresAt = generateExpirationLocalDateTime();
        Map<String, Object> payload = Maps.newHashMap();
        payload.put(PAYLOAD_KEY_USERNAME, user.getUsername());
        payload.put(PAYLOAD_KEY_CREATED, LocalDateTime.now());
        payload.put(PAYLOAD_KEY_EXPIRES, expiresAt);
        return generateToken(payload);
    }

    private String generateToken(Map<String, Object> userInfoMap) {
        final String token = IdUtil.randomUUID().toUpperCase();
        cacheToken(token, JSON.toJSONString(userInfoMap));
        return token;
    }

    private LocalDateTime generateExpirationLocalDateTime() {
        int exipre = configService.get(ConfigConst.LoginExpire, Integer.class);
        return LocalDateTime.now().plusSeconds(exipre);
    }

    private void cacheToken(String token, String content) {
        // 保存到redis
        String key = redisOperator.buildKeyWithPrefix(TOKEN_CACHE_PREFIX, token);
        int tokenExpire = configService.get(ConfigConst.LoginExpire, Integer.class);
        // 缓存token
        redisOperator.set(key, content, tokenExpire);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername());
    }

    public Boolean canTokenBeRefreshed(String token) {
        return !isTokenExpired(token);
    }

    private Boolean isTokenExpired(String token) {
        return getPayloadFromToken(token).isEmpty();
    }

    public String refreshToken(String token) {
        final LocalDateTime expiresAt = generateExpirationLocalDateTime();
        Map<String, Object> payload = getPayloadFromToken(token);
        payload.put(PAYLOAD_KEY_CREATED, LocalDateTime.now());
        payload.put(PAYLOAD_KEY_EXPIRES, expiresAt);
        return generateToken(payload);
    }
}
