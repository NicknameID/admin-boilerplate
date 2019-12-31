package com.mufeng.admin.boilerplate.common.acl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mufeng.admin.boilerplate.common.acl.mapper.RoleMapper;
import com.mufeng.admin.boilerplate.common.acl.model.entity.Role;
import com.mufeng.admin.boilerplate.common.acl.service.RolePermissionService;
import com.mufeng.admin.boilerplate.common.acl.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author HuangTianyu
 * @Date 2019-11-29 17:54
 * @Version 1.0
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Resource
    private RolePermissionService rolePermissionService;

    @Override
    public void createRole(String roleName, String roleCode, String remark, List<String> permissionCodes) {
        Role role = new Role();
        role.setRoleName(roleName);
        role.setRemark(remark);
        role.setCode(roleCode);
        this.save(role);
        rolePermissionService.bind(role.getCode(), permissionCodes);
    }
}
