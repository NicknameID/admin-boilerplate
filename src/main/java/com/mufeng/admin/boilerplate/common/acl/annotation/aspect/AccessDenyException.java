package com.mufeng.admin.boilerplate.common.acl.annotation.aspect;

import com.mufeng.admin.boilerplate.common.exception.CustomException;

/**
 * HuangTianyu
 * 2020-01-12 17:15
 */
public class AccessDenyException extends CustomException {
    public AccessDenyException() {
        super(403, "没有访问权限");
    }
}
