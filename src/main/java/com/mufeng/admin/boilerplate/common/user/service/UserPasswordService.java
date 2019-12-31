package com.mufeng.admin.boilerplate.common.user.service;

/**
 * @Author HuangTianyu
 * @Date 2019-12-29 18:52
 * @Version 1.0
 */
public interface UserPasswordService {
    // 校验密码
    void verifyPassword(Long uid, String password);

    // 检查密码
    boolean checkPassword(Long uid, String password);

    // 加密密码
    String encodePassword(String password);
}
