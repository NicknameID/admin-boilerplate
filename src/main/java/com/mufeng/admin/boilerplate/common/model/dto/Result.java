package com.mufeng.admin.boilerplate.common.model.dto;

import com.alibaba.fastjson.JSON;
import lombok.Builder;
import lombok.Data;

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

    public static Result success(String requestId, Object data) {

        return Result.builder()
                .code(0)
                .message("success")
                .requestId(requestId)
                .time(LocalDateTime.now())
                .data(data)
                .build();
    }

    public static Result fail(String requestId, Integer code, String message) {
        return Result.builder()
                .code(code)
                .message(message)
                .requestId(requestId)
                .time(LocalDateTime.now())
                .build();
    }
}
