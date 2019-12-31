package com.mufeng.admin.boilerplate.common.acl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mufeng.admin.boilerplate.common.acl.model.entity.Role;

import java.util.List;

/**
 * @Author HuangTianyu
 * @Date 2019-12-19 14:18
 * @Version 1.0
 */
public interface RoleService extends IService<Role> {

    /**
     * 创建角色并绑定权限
     * @param roleName 角色名
     * @param roleCode 角色标识
     * @param remark 备注
     * @param permissionCodes 权限标识列表
     */
    void createRole(String roleName, String roleCode, String remark, List<String> permissionCodes);
}
