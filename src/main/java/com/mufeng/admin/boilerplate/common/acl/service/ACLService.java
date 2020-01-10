package com.mufeng.admin.boilerplate.common.acl.service;

import com.mufeng.admin.boilerplate.common.acl.model.dto.RoleWithUserOverviewDTO;
import com.mufeng.admin.boilerplate.common.acl.model.entity.Permission;
import com.mufeng.admin.boilerplate.common.acl.model.entity.Role;

import java.util.List;

/**
 * @Author HuangTianyu
 * @Date 2019-12-29 17:21
 * @Version 1.0
 */
public interface ACLService {
    // 角色列表、绑定该角色的用户数、角色拥有的权限
    List<RoleWithUserOverviewDTO> listRoleDetail();

    // 角色详情、绑定该角色的用户数、角色拥有的权限
    RoleWithUserOverviewDTO getRoleDetail(String roleCode);

    /**
     * 获取角色的权限列表
     * @param roleCode 角色代号
     * @return List<Permission>
     */
    List<Permission> getPermissionListByRoleCode(String roleCode);

    List<Permission> getPermissionListByUserId(Long uid);

    /**
     * 获取权限的角色列表
     * @param permissionCode 权限代号
     * @return List<Permission>
     */
    List<Role> getRoleListByPermissionCode(String permissionCode);
}
