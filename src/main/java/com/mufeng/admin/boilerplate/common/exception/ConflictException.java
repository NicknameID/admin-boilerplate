package com.mufeng.admin.boilerplate.common.exception;

/**
 * HuangTianyu
 * 2020-01-10 16:41
 */
public class ConflictException extends CustomException{
    private static final int code = 409;
    private static final String defaultMsg = "对象冲突";


    public ConflictException(int code, String msg) {
        super(code, msg);
    }

    public ConflictException() {
        super(code, defaultMsg);
    }
}
