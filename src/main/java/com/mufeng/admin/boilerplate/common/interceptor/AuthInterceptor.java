package com.mufeng.admin.boilerplate.common.interceptor;

import com.mufeng.admin.boilerplate.common.user.service.impl.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author HuangTianyu
 * @Date 2019-12-09 16:59
 * @Version 1.0
 */
@Component
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {
    private static final String TOKEN_NAME = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer";

    @Resource
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String bearerToken = request.getHeader(TOKEN_NAME);
        log.debug("请求凭证Authorization: {}", bearerToken);
        if (bearerToken == null || !bearerToken.startsWith(TOKEN_PREFIX)) {
            userService.verifyToken("", true);
        }
        if (bearerToken != null) {
            String token = bearerToken.replace(TOKEN_PREFIX, "").trim();
            userService.verifyToken(token, false);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
