package com.mufeng.admin.boilerplate.common.logger;

import com.alibaba.fastjson.JSON;
import com.mufeng.admin.boilerplate.common.util.http.HeaderUtil;
import com.mufeng.admin.boilerplate.common.util.http.QueryStringUtil;
import com.mufeng.admin.boilerplate.common.logger.model.dto.RequestLogDTO;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @Author HuangTianyu
 * @Date 2019-11-28 14:59
 * @Version 1.0
 */
@Component
public class ApplicationIncommingRequestLogger {
    public void log(HttpServletRequest request) {
        String method = request.getMethod().toUpperCase();
        String requestPath = request.getRequestURI();
        Map<String, String> requestHeaders = HeaderUtil.mapHeaders(request);
        Map<String, String> queryStringMap = QueryStringUtil.parse(request.getQueryString());
        Map<String, String[]> requestBody = request.getParameterMap();
        RequestLogDTO requestLog = RequestLogDTO.builder()
                .headers(requestHeaders)
                .method(method)
                .path(requestPath)
                .query(queryStringMap)
                .body(requestBody)
                .time(LocalDateTime.now())
                .build();
        System.out.println(JSON.toJSONString(requestLog));
    }
}
