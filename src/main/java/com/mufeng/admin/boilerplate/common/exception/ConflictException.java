package com.mufeng.admin.boilerplate.common.exception;

/**
 * HuangTianyu
 * 2020-01-10 16:41
 */
public class ConflictException extends CustomException{
    public ConflictException(int code, String msg) {
        super(code, msg);
    }

    public ConflictException() {
        super(409, "对象冲突");
    }
}
