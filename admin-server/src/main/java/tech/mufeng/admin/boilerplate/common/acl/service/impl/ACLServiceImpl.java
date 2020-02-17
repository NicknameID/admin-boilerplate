package tech.mufeng.admin.boilerplate.common.acl.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tech.mufeng.admin.boilerplate.common.acl.model.dto.RoleWithUserOverview;
import tech.mufeng.admin.boilerplate.common.acl.model.entity.Permission;
import tech.mufeng.admin.boilerplate.common.acl.model.entity.Role;
import tech.mufeng.admin.boilerplate.common.acl.model.entity.RolePermission;
import tech.mufeng.admin.boilerplate.common.acl.service.*;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    public List<RoleWithUserOverview> listRoleDetail() {
        List<RoleWithUserOverview> roleWithUserOverviews = new ArrayList<>();
        List<Role> roles = roleService.list();
        roles.forEach((role) -> {
            RoleWithUserOverview roleWithUserOverview = getRoleDetailWithUserAndPermissionOverview(role);
            roleWithUserOverviews.add(roleWithUserOverview);
        });
        return roleWithUserOverviews;
    }

    @Override
    public RoleWithUserOverview getRoleDetail(String roleCode) {
        Role role = roleService.getById(roleCode);
        return getRoleDetailWithUserAndPermissionOverview(role);
    }

    private RoleWithUserOverview getRoleDetailWithUserAndPermissionOverview(Role role) {
        RoleWithUserOverview roleWithUserOverview = new RoleWithUserOverview();
        BeanUtils.copyProperties(role, roleWithUserOverview);
        roleWithUserOverview.setUserCount(userRoleService.countByRoleCode(role.getCode()));
        roleWithUserOverview.setPermissions(getPermissionListByRoleCode(role.getCode()));
        return roleWithUserOverview;
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
        List<Permission> permissions = permissionService.listByUid(uid);
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
