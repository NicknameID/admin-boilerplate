package com.mufeng.admin.boilerplate.common.acl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mufeng.admin.boilerplate.common.acl.model.entity.RolePermission;

import java.util.List;

/**
 * @Author HuangTianyu
 * @Date 2019-12-19 14:17
 * @Version 1.0
 */
public interface RolePermissionService extends IService<RolePermission> {
    /**
     * 角色移除权限
     * @param roleCode 角色的字母代号
     */
    void removeBind(String roleCode);

    /**
     * 给角色绑定权限
     * @param roleCode 角色代码
     * @param permissionCodes 权限代号列表
     */
    void bind(String roleCode, List<String> permissionCodes);

    List<RolePermission> listByRoleCode(String roleCode);

    List<RolePermission> listByPermissionCode(String permissionCode);
}
