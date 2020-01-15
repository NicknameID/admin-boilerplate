package com.mufeng.admin.boilerplate.common.interceptor;

import com.mufeng.admin.boilerplate.common.user.service.CustomUserDetailsService;
import com.mufeng.admin.boilerplate.common.user.service.UserTokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * HuangTianyu
 * 2020-01-08 17:57
 */
@Component
public class TokenFilter extends OncePerRequestFilter {
    @Resource
    private CustomUserDetailsService customUserDetailsService;
    @Resource
    private UserTokenService userTokenService;
    @Resource
    private InterceptorUtil interceptorUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authToken = interceptorUtil.getToke(request);
        final String username = userTokenService.getUsernameFromToken(authToken);
        if (username != null && !username.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            if ( userTokenService.validateToken(authToken, userDetails) ) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        next(request, response, filterChain); // 继续执行接下来的过滤器
    }

    private void next(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        filterChain.doFilter(request, response);
    }
}
