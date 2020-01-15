package com.mufeng.admin.boilerplate.common.interceptor;

import com.mufeng.admin.boilerplate.common.constant.ConfigConst;
import com.mufeng.admin.boilerplate.common.system_config.service.ConfigService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * HuangTianyu
 * 2020-01-10 15:42
 */
@Component
public class InterceptorUtil {
    @Resource
    private ConfigService configService;

    public String getToke(HttpServletRequest request) {
        final String headerString = getHeaderString();
        final String tokenPrefix = getTokenPrefix();
        String authHeader = request.getHeader(headerString);
        if ( authHeader == null || !authHeader.startsWith(tokenPrefix) ) return "";
        return authHeader.substring(tokenPrefix.length()).trim();
    }

    private String getHeaderString() {
        return configService.get(ConfigConst.TokenHeaderName);
    }

    private String getTokenPrefix() {
        return configService.get(ConfigConst.TokenPrefix);
    }
}
