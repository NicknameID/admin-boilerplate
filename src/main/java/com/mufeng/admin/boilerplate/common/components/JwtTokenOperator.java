package com.mufeng.admin.boilerplate.common.components;

import com.mufeng.admin.boilerplate.common.constant.ConfigConst;
import com.mufeng.admin.boilerplate.common.system_config.service.ConfigService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author HuangTianyu
 * @Date 2020-01-08 17:54
 * @Version 1.0
 */
@Component
public class JwtTokenOperator {
    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    private static final String TOKEN_CACHE_PREFIX = "user.token";

    @Resource
    private ConfigService configService;

    @Resource
    private RedisOperator redisOperator;

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(configService.get(ConfigConst.JwtSecret))
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private Date generateExpirationDate() {
        int exipre = configService.get(ConfigConst.LoginExpire, Integer.class);
        return new Date(System.currentTimeMillis() + exipre * 1000);
    }

    private Boolean isTokenExpired(String token) {
        String username = getUsernameFromToken(token);
        if(doesNotExistCacheToken(username)) return true;
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    private String generateToken(Map<String, Object> claims) {
        final Date expiredAt = generateExpirationDate();
        final String secret =  configService.get(ConfigConst.JwtSecret);
        final String token = Jwts.builder()
                .setClaims(claims)
                .setExpiration(expiredAt)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
        cacheToken(token, claims);
        return token;
    }

    private void cacheToken(String token, Map<String, Object> claims) {
        // 保存到redis
        String key = redisOperator.buildKeyWithPrefix(TOKEN_CACHE_PREFIX, claims.get(CLAIM_KEY_USERNAME).toString());
        Integer tokenExpire = configService.get(ConfigConst.LoginExpire, Integer.class);
        // 缓存token
        redisOperator.set(key, token, tokenExpire);
    }

    public Boolean canTokenBeRefreshed(String token) {
        return !isTokenExpired(token);
    }

    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            claims.put(CLAIM_KEY_CREATED, new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean doesNotExistCacheToken(String username) {
        // 保存到redis
        String key = redisOperator.buildKeyWithPrefix(TOKEN_CACHE_PREFIX, username);
        return !redisOperator.hasKey(key);
    }
}
