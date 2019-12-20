package com.mufeng.admin.boilerplate.common.acl.controller;

import com.mufeng.admin.boilerplate.common.acl.model.dto.RoleParam;
import com.mufeng.admin.boilerplate.common.acl.model.entity.Role;
import com.mufeng.admin.boilerplate.common.acl.service.RoleService;
import com.mufeng.admin.boilerplate.common.context.RequestContext;
import com.mufeng.admin.boilerplate.common.model.dto.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @Author HuangTianyu
 * @Date 2019-11-29 16:16
 * @Version 1.0
 */
@RestController
@RequestMapping("/common/acl")
public class RoleController {
    @Resource
    private RequestContext requestContext;
    @Resource
    private RoleService roleService;

    // 角色列表
    @GetMapping("/roles")
    public Result roles() {
        return Result.success(requestContext.getRequestId(), roleService.listRoleDetail());
    }

    // 获取角色详情
    @GetMapping("/role/{code}")
    public Result detail(@PathVariable String code) {
        return Result.success(requestContext.getRequestId(), roleService.getRoleDetail(code));
    }

    // 创建角色
    @PostMapping("/role")
    public Result create(@Valid @RequestBody RoleParam roleParam) {
        List<String> permissionCodes = roleParam.getPermissionCodes();
        roleService.createRole(
                roleParam.getRoleName(),
                roleParam.getCode(),
                roleParam.getRemark(),
                permissionCodes
        );
        return Result.success(requestContext.getRequestId());
    }

    // 更新角色信息
    @PutMapping("/role/{code}")
    public Result update(@PathVariable String code, @Valid @RequestBody RoleParam roleParam) {
        List<String> permissionCodes = roleParam.getPermissionCodes();
        roleService.updateRole(code, permissionCodes);
        return Result.success(requestContext.getRequestId());
    }

    // 删除角色相关资源
    @DeleteMapping("/role/{code}")
    public Result delete(@PathVariable String code) {
        roleService.delete(code);
        return Result.success(requestContext.getRequestId());
    }
}
