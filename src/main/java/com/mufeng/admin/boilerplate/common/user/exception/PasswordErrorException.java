package com.mufeng.admin.boilerplate.common.user.exception;

import com.mufeng.admin.boilerplate.common.exception.CustomException;

/**
 * @Author HuangTianyu
 * @Date 2019-12-01 14:20
 * @Version 1.0
 */
public class PasswordErrorException extends CustomException {
    public PasswordErrorException() {
        super(4010, "密码校验错误");
    }
}
