package com.mufeng.admin.boilerplate.common.acl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mufeng.admin.boilerplate.common.acl.mapper.RoleMapper;
import com.mufeng.admin.boilerplate.common.acl.model.dto.RoleWithUserOverviewDTO;
import com.mufeng.admin.boilerplate.common.acl.model.entity.Role;
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
    private RolePermissionServiceImpl rolePermissionServiceImpl;

    /**
     * 角色列表、绑定该角色的用户数、角色拥有的权限
     * @return
     */
    public List<RoleWithUserOverviewDTO> listWithUserAndPermissionOverview() {
        List<RoleWithUserOverviewDTO> roleWithUserOverviews = new ArrayList<>();
        List<Role> roles = super.list();
        roles.forEach((role) -> {
            RoleWithUserOverviewDTO roleWithUserOverview = getRoleDetailWithUserAndPermissionOverview(role);
            roleWithUserOverviews.add(roleWithUserOverview);
        });
        return roleWithUserOverviews;
    }

    /**
     * 获取角色详情
     * @param roleCode
     * @return
     */
    public RoleWithUserOverviewDTO getOneWithUserAndPermissionOverview(String roleCode) {
        Role role = this.getById(roleCode);
        return getRoleDetailWithUserAndPermissionOverview(role);
    }

    /**
     * 创建一个角色
     * @param role
     * @param permissionCodes
     */
    @Transactional
    public void createRole(Role role, List<String> permissionCodes) {
        // 保存角色
        this.save(role);
        rolePermissionServiceImpl.bindPermissions(role.getCode(), permissionCodes);
    }

    /**
     * 更新角色
     * @param role
     * @param permissionCodes
     */
    @Transactional
    public void updateRole(Role role, List<String> permissionCodes) {
        // 更新角色
        this.updateById(role);
        // 删除全部绑定权限
        rolePermissionServiceImpl.removePermission(role.getCode());
        // 重新添加权限
        rolePermissionServiceImpl.bindPermissions(role.getCode(), permissionCodes);
    }

    /**
     * 删除权限
     * @param roleCode
     */
    @Transactional
    public void deleteByRoleCode(String roleCode) {
        // 删除role表
        this.removeById(roleCode);
        // 删除角色绑定的权限
        rolePermissionServiceImpl.removePermission(roleCode);
    }

    public RoleWithUserOverviewDTO getRoleDetailWithUserAndPermissionOverview(Role role) {
        RoleWithUserOverviewDTO roleWithUserOverviewDTO = new RoleWithUserOverviewDTO();
        BeanUtils.copyProperties(role, roleWithUserOverviewDTO);
        roleWithUserOverviewDTO.setUserCount(userService.countByRoleCode(role.getCode()));
        roleWithUserOverviewDTO.setPermissionSet(rolePermissionServiceImpl.getPermissionListByRoleCode(role.getCode()));
        return roleWithUserOverviewDTO;
    }
}
