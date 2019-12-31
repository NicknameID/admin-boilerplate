package com.mufeng.admin.boilerplate.common.model.dto;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mufeng.admin.boilerplate.common.application.ApplicationContextComponent;
import com.mufeng.admin.boilerplate.common.context.RequestContext;
import lombok.Builder;
import lombok.Data;

import javax.annotation.Resource;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author HuangTianyu
 * @Date 2019-11-29 16:18
 * @Version 1.0
 */
@Data
@Builder
public class Result implements Serializable {
    private Integer code;

    private String message;

    private String requestId;

    private LocalDateTime time;

    // 数据体
    private Object data;

    public String toJson() {
        return JSON.toJSONString(this);
    }

    public static Result success(String requestId) {
        return Result.builder()
                .code(0)
                .message("success")
                .requestId(requestId)
                .time(LocalDateTime.now())
                .build();
    }

    public static Result success() {
        RequestContext context = ApplicationContextComponent.getBean(RequestContext.class);
        if (context == null) {
            return success(IdUtil.simpleUUID());
        }else {
            return success(context.getRequestId());
        }
    }

    public static Result success(String requestId, Object data) {

        return Result.builder()
                .code(0)
                .message("success")
                .requestId(requestId)
                .time(LocalDateTime.now())
                .data(data)
                .build();
    }

    public static Result success(Object data) {
        RequestContext context = ApplicationContextComponent.getBean(RequestContext.class);
        if (context == null) {
            return success(IdUtil.simpleUUID());
        }else {
            return success(context.getRequestId(), data);
        }
    }

    public static Result fail(String requestId, Integer code, String message) {
        return Result.builder()
                .code(code)
                .message(message)
                .requestId(requestId)
                .time(LocalDateTime.now())
                .build();
    }

    public static Result fail(Integer code, String message) {
        RequestContext context = ApplicationContextComponent.getBean(RequestContext.class);
        if (context == null) {
            return fail(IdUtil.simpleUUID(), code, message);
        }else {
            return fail(context.getRequestId(), code, message);
        }
    }
}
