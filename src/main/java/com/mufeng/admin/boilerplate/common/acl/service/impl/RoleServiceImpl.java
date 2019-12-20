package com.mufeng.admin.boilerplate.common.acl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mufeng.admin.boilerplate.common.acl.mapper.RoleMapper;
import com.mufeng.admin.boilerplate.common.acl.model.dto.RoleWithUserOverviewDTO;
import com.mufeng.admin.boilerplate.common.acl.model.entity.Role;
import com.mufeng.admin.boilerplate.common.acl.service.RolePermissionService;
import com.mufeng.admin.boilerplate.common.acl.service.RoleService;
import com.mufeng.admin.boilerplate.common.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author HuangTianyu
 * @Date 2019-11-29 17:54
 * @Version 1.0
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Resource
    private UserService userService;
    @Resource
    private RolePermissionService rolePermissionService;

    @Override
    public List<RoleWithUserOverviewDTO> listRoleDetail() {
        List<RoleWithUserOverviewDTO> roleWithUserOverviews = new ArrayList<>();
        List<Role> roles = super.list();
        roles.forEach((role) -> {
            RoleWithUserOverviewDTO roleWithUserOverview = getRoleDetailWithUserAndPermissionOverview(role);
            roleWithUserOverviews.add(roleWithUserOverview);
        });
        return roleWithUserOverviews;
    }

    @Override
    public RoleWithUserOverviewDTO getRoleDetail(String roleCode) {
        Role role = this.getById(roleCode);
        return getRoleDetailWithUserAndPermissionOverview(role);
    }

    @Override
    public void createRole(String roleName, String roleCode, String remark, List<String> permissionCodes) {
        Role role = new Role();
        role.setRoleName(roleName);
        role.setRemark(remark);
        role.setCode(roleCode);
        this.save(role);
        rolePermissionService.bindPermissions(role.getCode(), permissionCodes);
    }

    @Override
    public void updateRole(String roleCode, List<String> permissionCodes) {
        // 删除全部绑定权限
        rolePermissionService.removePermission(roleCode);
        // 重新添加权限
        rolePermissionService.bindPermissions(roleCode, permissionCodes);
    }

    @Override
    @Transactional
    public void delete(String roleCode) {
        // 删除role表
        this.removeById(roleCode);
        // 删除角色绑定的权限
        rolePermissionService.removePermission(roleCode);
    }

    private RoleWithUserOverviewDTO getRoleDetailWithUserAndPermissionOverview(Role role) {
        RoleWithUserOverviewDTO roleWithUserOverviewDTO = new RoleWithUserOverviewDTO();
        BeanUtils.copyProperties(role, roleWithUserOverviewDTO);
        roleWithUserOverviewDTO.setUserCount(userService.countByRoleCode(role.getCode()));
        roleWithUserOverviewDTO.setPermissionSet(rolePermissionService.getPermissionListByRoleCode(role.getCode()));
        return roleWithUserOverviewDTO;
    }
}
