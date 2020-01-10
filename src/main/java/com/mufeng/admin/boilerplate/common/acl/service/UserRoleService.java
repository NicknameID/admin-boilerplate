package com.mufeng.admin.boilerplate.common.acl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mufeng.admin.boilerplate.common.acl.model.entity.UserRole;

import java.util.List;

/**
 * @Author HuangTianyu
 * @Date 2019-12-29 17:40
 * @Version 1.0
 */
public interface UserRoleService extends IService<UserRole> {
    int countByRoleCode(String roleCode);

//    void bind(Long uid, String roleCode);

    void bind(Long uid, List<String> roleCodes);

    void removeBind(Long uid);

//    void removeBind(Long uid, String roleCode);

//    void removeBind(Long uid, List<String> roleCodes);

//    void verfiyUserHasRole(Long uid);

    void verifyRoleHasUser(String roleCode);

    List<UserRole> getUserRolesByUid(Long uid);
}
