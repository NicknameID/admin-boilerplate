package com.mufeng.admin.boilerplate.config;

import com.mufeng.admin.boilerplate.common.interceptor.RequestInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @Author HuangTianyu
 * @Date 2019-11-28 16:31
 * @Version 1.0
 */
@Configuration
public class MvcInterceptorConfig implements WebMvcConfigurer {
    @Resource
    private RequestInterceptor requestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestInterceptor)
                .addPathPatterns("/**");
    }
}
