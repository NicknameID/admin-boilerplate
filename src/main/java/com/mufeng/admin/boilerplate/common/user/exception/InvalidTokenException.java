package com.mufeng.admin.boilerplate.common.user.exception;

import com.mufeng.admin.boilerplate.common.exception.CustomException;

/**
 * @Author HuangTianyu
 * @Date 2019-12-07 19:43
 * @Version 1.0
 */
public class InvalidTokenException extends CustomException {
    public InvalidTokenException() {
        super(4011, "无效的token");
    }
}
