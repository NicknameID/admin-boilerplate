package com.mufeng.admin.boilerplate.common.acl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mufeng.admin.boilerplate.common.acl.model.dto.RoleWithUserOverviewDTO;
import com.mufeng.admin.boilerplate.common.acl.model.entity.Role;

import java.util.List;

/**
 * @Author HuangTianyu
 * @Date 2019-12-19 14:18
 * @Version 1.0
 */
public interface RoleService extends IService<Role> {
    /**
     * 角色列表、绑定该角色的用户数、角色拥有的权限
     * @return List<RoleWithUserOverviewDTO>
     */
    List<RoleWithUserOverviewDTO> listRoleDetail();

    /**
     * 获取角色详情
     * @param roleCode
     * @return
     */
    RoleWithUserOverviewDTO getRoleDetail(String roleCode);


    /**
     * 创建角色并绑定权限
     * @param roleName 角色名
     * @param roleCode 角色标识
     * @param remark 备注
     * @param permissionCodes 权限标识列表
     */
    void createRole(String roleName, String roleCode, String remark, List<String> permissionCodes);

    /**
     * 更新角色
     * @param roleCode 角色标识
     * @param permissionCodes 权限标识列表
     */
    void updateRole(String roleCode, List<String> permissionCodes);


    /**
     * 删除角色
     * @param roleCode 角色标识
     */
    void delete(String roleCode);
}
