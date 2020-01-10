package com.mufeng.admin.boilerplate.common.acl.service.impl;

import com.mufeng.admin.boilerplate.common.acl.model.dto.RoleWithUserOverviewDTO;
import com.mufeng.admin.boilerplate.common.acl.model.entity.Permission;
import com.mufeng.admin.boilerplate.common.acl.model.entity.Role;
import com.mufeng.admin.boilerplate.common.acl.model.entity.RolePermission;
import com.mufeng.admin.boilerplate.common.acl.model.entity.UserRole;
import com.mufeng.admin.boilerplate.common.acl.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author HuangTianyu
 * @Date 2019-12-29 17:28
 * @Version 1.0
 */
@Service
public class ACLServiceImpl implements ACLService {
    @Resource
    private UserRoleService userRoleService;
    @Resource
    private RolePermissionService rolePermissionService;
    @Resource
    private RoleService roleService;
    @Resource
    private PermissionService permissionService;

    @Override
    public List<RoleWithUserOverviewDTO> listRoleDetail() {
        List<RoleWithUserOverviewDTO> roleWithUserOverviews = new ArrayList<>();
        List<Role> roles = roleService.list();
        roles.forEach((role) -> {
            RoleWithUserOverviewDTO roleWithUserOverview = getRoleDetailWithUserAndPermissionOverview(role);
            roleWithUserOverviews.add(roleWithUserOverview);
        });
        return roleWithUserOverviews;
    }

    @Override
    public RoleWithUserOverviewDTO getRoleDetail(String roleCode) {
        Role role = roleService.getById(roleCode);
        return getRoleDetailWithUserAndPermissionOverview(role);
    }

    private RoleWithUserOverviewDTO getRoleDetailWithUserAndPermissionOverview(Role role) {
        RoleWithUserOverviewDTO roleWithUserOverviewDTO = new RoleWithUserOverviewDTO();
        BeanUtils.copyProperties(role, roleWithUserOverviewDTO);
        roleWithUserOverviewDTO.setUserCount(userRoleService.countByRoleCode(role.getCode()));
        roleWithUserOverviewDTO.setPermissionSet(getPermissionListByRoleCode(role.getCode()));
        return roleWithUserOverviewDTO;
    }

    @Override
    public List<Permission> getPermissionListByRoleCode(String roleCode) {
        List<RolePermission> rolePermissions = rolePermissionService.listByRoleCode(roleCode);
        List<Permission> permissions = new ArrayList<>();
        rolePermissions.forEach(item -> permissions.add(permissionService.getById(item.getPermissionCode())));
        return permissions;
    }

    @Override
    public List<Permission> getPermissionListByUserId(Long uid) {
        List<UserRole> userRoles = userRoleService.getUserRolesByUid(uid);
        if (userRoles.size() <= 0) return Collections.emptyList();
        List<Permission> permissions = new ArrayList<>();
        userRoles.forEach(userRole -> {
            List<Permission> permissionList = getPermissionListByRoleCode(userRole.getRoleCode());
            permissions.addAll(permissionList);
        });
        // 权限去重
        return permissions.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<Role> getRoleListByPermissionCode(String permissionCode) {
        List<RolePermission> rolePermissions = rolePermissionService.listByPermissionCode(permissionCode);
        List<Role> roles = new ArrayList<>();
        rolePermissions.forEach(rolePermission -> roles.add(roleService.getById(rolePermission.getRoleCode())));
        return null;
    }
}
