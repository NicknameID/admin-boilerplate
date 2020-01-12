package com.mufeng.admin.boilerplate.common.exception;

import lombok.Getter;

/**
 * @Author HuangTianyu
 * @Date 2019-11-30 17:18
 * @Version 1.0
 */
public class CustomException extends RuntimeException {
    @Getter
    private Integer code = -1;
    @Getter
    private String message = "原始自定义错误";

    public CustomException() {
        super("原始自定义错误");
    }

    public CustomException(String msg) {
        super(msg);
        this.message = msg;
    }

    public CustomException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.message = msg;
    }

    public CustomException extendsMsg(String msg) {
        this.message = String.format("[%s]: %s", this.message, msg);
        return this;
    }
}
