package com.mufeng.admin.boilerplate.common.acl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mufeng.admin.boilerplate.common.acl.model.entity.Permission;
import com.mufeng.admin.boilerplate.common.acl.model.entity.Role;
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
    void removePermission(String roleCode);

    /**
     * 给角色绑定权限
     * @param roleCode 角色代码
     * @param permissionCodes 权限代号列表
     */
    void bindPermissions(String roleCode, List<String> permissionCodes);


    /**
     * 获取角色的权限列表
     * @param roleCode 角色代号
     * @return List<Permission>
     */
    List<Permission> getPermissionListByRoleCode(String roleCode);

    /**
     * 获取权限的角色列表
     * @param permissionCode 权限代号
     * @return List<Permission>
     */
    List<Role> getRoleListByPermissionCode(String permissionCode);
}
