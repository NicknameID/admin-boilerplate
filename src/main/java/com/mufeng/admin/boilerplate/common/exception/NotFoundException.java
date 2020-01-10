package com.mufeng.admin.boilerplate.common.exception;

/**
 * @Author HuangTianyu
 * @Date 2019-12-01 16:30
 * @Version 1.0
 */
public class NotFoundException extends CustomException {
    public NotFoundException() {
        super(404, "找不到指定对象");
    }

    public NotFoundException(int code, String msg) {
        super(code, msg);
    }
}
