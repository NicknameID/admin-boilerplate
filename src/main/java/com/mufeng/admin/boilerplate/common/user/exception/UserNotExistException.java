package com.mufeng.admin.boilerplate.common.user.exception;

import com.mufeng.admin.boilerplate.common.exception.CustomException;

/**
 * @Author HuangTianyu
 * @Date 2019-12-01 16:31
 * @Version 1.0
 */
public class UserNotExistException extends CustomException {
    public UserNotExistException() {
        super(4040, "该用户不存在");
    }
}
