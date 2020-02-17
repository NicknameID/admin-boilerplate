package tech.mufeng.admin.boilerplate.config;

import tech.mufeng.admin.boilerplate.common.interceptor.AuthInterceptor;
import tech.mufeng.admin.boilerplate.common.interceptor.RequestInterceptor;
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
    @Resource
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/common/acl/user/login", "/common/acl/user/logout");
        registry.addInterceptor(requestInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/common/acl/user/login", "/common/acl/user/logout");

    }
}
