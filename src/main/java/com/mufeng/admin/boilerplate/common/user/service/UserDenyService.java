package com.mufeng.admin.boilerplate.common.user.service;

/**
 * @Author HuangTianyu
 * @Date 2019-12-29 18:59
 * @Version 1.0
 */
public interface UserDenyService {
    Long plus(Long uid);

    void clear(Long uid);

    void lock(Long uid);

    String generateKey(Long uid);
}
