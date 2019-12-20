package com.mufeng.admin.boilerplate.common.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mufeng.admin.boilerplate.common.acl.mapper.RolePermissionMapper;
import com.mufeng.admin.boilerplate.common.acl.model.entity.Permission;
import com.mufeng.admin.boilerplate.common.acl.model.entity.Role;
import com.mufeng.admin.boilerplate.common.acl.model.entity.RolePermission;
import com.mufeng.admin.boilerplate.common.acl.service.PermissionService;
import com.mufeng.admin.boilerplate.common.acl.service.RolePermissionService;
import com.mufeng.admin.boilerplate.common.acl.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author HuangTianyu
 * @Date 2019-11-30 18:12
 * @Version 1.0
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {
    @Resource
    private PermissionService permissionService;
    @Resource
    private RoleService roleService;

    /**
     * 移除权限
     * @param roleCode 角色代号
     */
    @Override
    public void removePermission(String roleCode) {
        LambdaQueryWrapper<RolePermission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RolePermission::getRoleCode, roleCode);
        this.remove(queryWrapper);
    }

    /**
     * 绑定权限
     * @param roleCode 角色代号
     * @param permissionCodes 权限列表
     */
    @Override
    public void bindPermissions(String roleCode, List<String> permissionCodes) {
        List<RolePermission> rolePermissions = new ArrayList<>();
        permissionCodes.forEach(permissionCode -> {
            Permission permission = permissionService.getById(permissionCode);
            if (!Objects.isNull(permission)) {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRoleCode(roleCode);
                rolePermission.setPermissionCode(permissionCode);
                rolePermissions.add(rolePermission);
            }
        });
        this.saveBatch(rolePermissions);
    }

    @Override
    public List<Permission> getPermissionListByRoleCode(String roleCode) {
        List<RolePermission> rolePermissions = listByRoleCode(roleCode);
        List<Permission> permissions = new ArrayList<>();
        rolePermissions.forEach(item -> permissions.add(permissionService.getById(item.getPermissionCode())));
        return permissions;
    }

    @Override
    public List<Role> getRoleListByPermissionCode(String permissionCode) {
        List<RolePermission> rolePermissions = listByPermissionCode(permissionCode);
        List<Role> roles = new ArrayList<>();
        rolePermissions.forEach(rolePermission -> roles.add(roleService.getById(rolePermission.getRoleCode())));
        return null;
    }

    private List<RolePermission> listByRoleCode(String roleCode) {
        LambdaQueryWrapper<RolePermission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RolePermission::getRoleCode, roleCode);
        return this.list(queryWrapper);
    }

    private List<RolePermission> listByPermissionCode(String permissionCode) {
        LambdaQueryWrapper<RolePermission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RolePermission::getPermissionCode, permissionCode);
        return this.list(queryWrapper);
    }
}
