package com.mufeng.admin.boilerplate.common.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mufeng.admin.boilerplate.common.user.model.dto.UserUpdateParam;
import com.mufeng.admin.boilerplate.common.user.model.entity.User;

import java.util.Optional;

/**
 * HuangTianyu
 * 2020-01-09 16:58
 */
public interface UserService extends IService<User> {
    String login(String username, String password);

    Optional<User> getUserByToken(String token);

    Optional<User> getByUsername(String username);

    boolean loginUpdate(Long uid, String token, String lastIP);

    void addUser(String username, String password);

    void updateUser(Long uid, UserUpdateParam userUpdateParam);

    void disableByUid(Long uid);
}
