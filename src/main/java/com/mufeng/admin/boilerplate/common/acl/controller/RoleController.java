package com.mufeng.admin.boilerplate.common.acl.controller;

import com.mufeng.admin.boilerplate.common.acl.annotation.RequirePermission;
import com.mufeng.admin.boilerplate.common.acl.model.dto.RoleParam;
import com.mufeng.admin.boilerplate.common.acl.model.entity.Role;
import com.mufeng.admin.boilerplate.common.acl.service.ACLService;
import com.mufeng.admin.boilerplate.common.acl.service.RolePermissionService;
import com.mufeng.admin.boilerplate.common.acl.service.RoleService;
import com.mufeng.admin.boilerplate.common.acl.service.UserRoleService;
import com.mufeng.admin.boilerplate.common.model.dto.Result;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

import static com.mufeng.admin.boilerplate.config.PermissionModuleEnum.ACL_CONFIG;

/**
 * @Author HuangTianyu
 * @Date 2019-11-29 16:16
 * @Version 1.0
 */
@RestController
@RequestMapping("/common/acl/role")
public class RoleController {
    @Resource
    private RoleService roleService;
    @Resource
    private ACLService aclService;
    @Resource
    private RolePermissionService rolePermissionService;
    @Resource
    private UserRoleService userRoleService;

    // 角色详情列表
    @GetMapping("/details")
    @RequirePermission(ACL_CONFIG)
    public Result roles() {
        return Result.success(aclService.listRoleDetail());
    }

    // 获取角色详情
    @GetMapping("/{roleCode}/detail")
    @RequirePermission(ACL_CONFIG)
    public Result detail(@PathVariable String roleCode) {
        return Result.success(aclService.getRoleDetail(roleCode));
    }

    // 创建角色
    @PostMapping("/detail")
    @RequirePermission(ACL_CONFIG)
    public Result create(@Valid @RequestBody RoleParam roleParam) {
        List<String> permissionCodes = roleParam.getPermissionCodes();
        roleService.createRole(
                roleParam.getRoleName(),
                roleParam.getRoleCode(),
                roleParam.getRemark(),
                permissionCodes
        );
        return Result.success();
    }

    // 更新角色信息
    @PutMapping("/{roleCode}/detail")
    @RequirePermission(ACL_CONFIG)
    @Transactional(rollbackFor = Exception.class)
    public Result update(@PathVariable String roleCode, @Valid @RequestBody RoleParam roleParam) {
        List<String> permissionCodes = roleParam.getPermissionCodes();
        Role role = new Role();
        role.setCode(roleParam.getRoleCode());
        role.setRoleName(roleParam.getRoleName());
        role.setRemark(roleParam.getRemark());
        role.setUpdatedTime(LocalDateTime.now());
        // 更新角色基本信息
        roleService.updateById(role);
        // 删除全部绑定权限
        rolePermissionService.removeBind(roleCode);
        // 重新添加权限
        rolePermissionService.bind(roleCode, permissionCodes);
        return Result.success();
    }

    // 删除角色相关资源
    @DeleteMapping("/{roleCode}/detail")
    @Transactional(rollbackFor = Exception.class)
    @RequirePermission(ACL_CONFIG)
    public Result delete(@PathVariable String roleCode) {
        // 校验角色是否还有绑定用户，有存在用户绑定的角色不能删除
        userRoleService.verifyRoleHasUser(roleCode);
        // 删除角色
        roleService.removeById(roleCode);
        // 删除角色绑定资源
        rolePermissionService.removeBind(roleCode);
        return Result.success();
    }
}
