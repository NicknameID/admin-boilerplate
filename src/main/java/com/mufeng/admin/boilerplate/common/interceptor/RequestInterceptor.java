package com.mufeng.admin.boilerplate.common.interceptor;

import cn.hutool.core.util.IdUtil;
import com.mufeng.admin.boilerplate.common.context.RequestContext;
import com.mufeng.admin.boilerplate.common.user.model.entity.User;
import com.mufeng.admin.boilerplate.common.user.service.UserService;
import com.mufeng.admin.boilerplate.common.util.http.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * @Author HuangTianyu
 * @Date 2019-12-09 16:59
 * @Version 1.0
 */
@Component
@Slf4j
public class RequestInterceptor implements HandlerInterceptor {
    @Resource
    private RequestContext context;
    @Resource
    private InterceptorUtil interceptorUtil;
    @Resource
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String token = interceptorUtil.getToke(request);
        log.debug("请求凭证Authorization: {}", token);//
        Optional<User> user = userService.getUserByToken(token);
        // 初始化Context
        context.init()
                .setToken(token)
                .setUid(user.isPresent() ? user.get().getId() : 0L)
                .setRequestId(IdUtil.randomUUID().toUpperCase())
                .setIp(IpUtil.getIp(request));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.debug("回收-LocalThread资源");
        context.remove(); // 回收资源
    }
}
