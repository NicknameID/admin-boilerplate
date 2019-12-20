package com.mufeng.admin.boilerplate.config;

import com.mufeng.admin.boilerplate.common.interceptor.AuthInterceptor;
import com.mufeng.admin.boilerplate.common.interceptor.HttpRequestInterceptor;
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
    private HttpRequestInterceptor httpRequestInterceptor;
    @Resource
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/common/user/login");
        registry.addInterceptor(httpRequestInterceptor)
                .addPathPatterns("/**");
    }
}
