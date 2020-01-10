package com.mufeng.admin.boilerplate.common.user.exception;

import com.mufeng.admin.boilerplate.common.exception.NotFoundException;

/**
 * @Author HuangTianyu
 * @Date 2019-12-01 16:31
 * @Version 1.0
 */
public class UserNotExistException extends NotFoundException {
    public UserNotExistException() {
        super(4040, "该用户不存在");
    }
}
