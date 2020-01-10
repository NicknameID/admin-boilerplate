package com.mufeng.admin.boilerplate.common.user.service;

/**
 * @Author HuangTianyu
 * @Date 2019-12-29 18:52
 * @Version 1.0
 */
public interface UserPasswordService {
    // 加密密码
    String encodePassword(String password);
}
