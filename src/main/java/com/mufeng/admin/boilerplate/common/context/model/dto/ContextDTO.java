package com.mufeng.admin.boilerplate.common.context.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author HuangTianyu
 * @Date 2019-11-28 14:11
 * @Version 1.0
 */
@Data
public class ContextDTO implements Serializable {
    // 请求ID，方便日志记录
    private String requestId;

    // 访问令牌
    private String token;

    // 请求的系统用户ID
    private Long uid;

    // 请求的IP
    private String ip;
}
