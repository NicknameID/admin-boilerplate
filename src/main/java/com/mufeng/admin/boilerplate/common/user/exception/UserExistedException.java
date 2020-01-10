package com.mufeng.admin.boilerplate.common.user.exception;

import com.mufeng.admin.boilerplate.common.exception.ConflictException;

/**
 * @Author HuangTianyu
 * @Date 2019-12-08 12:01
 * @Version 1.0
 */
public class UserExistedException extends ConflictException {
    public UserExistedException() {
        super(4090, "用户已存在，存在冲突");
    }
}
