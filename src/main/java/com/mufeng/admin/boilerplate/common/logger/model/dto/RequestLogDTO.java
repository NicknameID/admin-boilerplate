package com.mufeng.admin.boilerplate.common.logger.model.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @Author HuangTianyu
 * @Date 2019-11-28 15:21
 * @Version 1.0
 */
@Data
@Builder
public class RequestLogDTO implements Serializable {
    private Map<String, String> headers;
    private String method;
    private String path;
    private Map<String, String> query;
    private Map<String, String[]> body;
    private LocalDateTime time;
    private Map<String, Object> responseBody;
}
