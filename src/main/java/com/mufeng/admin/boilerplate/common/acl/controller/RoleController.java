package com.mufeng.admin.boilerplate.common.acl.controller;

import com.mufeng.admin.boilerplate.common.acl.model.dto.RoleParam;
import com.mufeng.admin.boilerplate.common.acl.model.entity.Role;
import com.mufeng.admin.boilerplate.common.context.RequestContext;
import com.mufeng.admin.boilerplate.common.model.dto.Result;
import com.mufeng.admin.boilerplate.common.acl.service.impl.RoleServiceImpl;
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
    private RoleServiceImpl roleServiceImpl;

    /**
     * 角色列表
     * @return
     */
    @GetMapping("/roles")
    public Result roles() {
        return Result.success(requestContext.getRequestId(), roleServiceImpl.listWithUserAndPermissionOverview());
    }

    /**
     * 获取角色详情
     * @param code
     * @return
     */
    @GetMapping("/role/{code}")
    public Result detail(@PathVariable String code) {
        return Result.success(requestContext.getRequestId(), roleServiceImpl.getOneWithUserAndPermissionOverview(code));
    }

    /**
     * 创建角色
     * @param roleParam
     * @return
     */
    @PostMapping("/role")
    public Result create(@Valid @RequestBody RoleParam roleParam) {
        Role role = new Role();
        role.setRoleName(roleParam.getRoleName());
        role.setRemark(roleParam.getRemark());
        role.setCode(roleParam.getCode());
        List<String> permissionCodes = roleParam.getPermissionCodes();
        roleServiceImpl.createRole(role, permissionCodes); // 创建角色
        return Result.success(requestContext.getRequestId());
    }

    /**
     * 更新角色信息
     * @param code
     * @param roleParam
     * @return
     */
    @PutMapping("/role/{code}")
    public Result update(@PathVariable String code, @Valid @RequestBody RoleParam roleParam) {
        Role role = new Role();
        role.setCode(code);
        role.setRoleName(roleParam.getRoleName());
        role.setRemark(roleParam.getRemark());
        List<String> permissionCodes = roleParam.getPermissionCodes();
        roleServiceImpl.updateRole(role, permissionCodes);
        return Result.success(requestContext.getRequestId());
    }

    /**
     * 删除角色相关资源
     * @param code
     * @return
     */
    @DeleteMapping("/role/{code}")
    public Result delete(@PathVariable String code) {
        roleServiceImpl.deleteByRoleCode(code);
        return Result.success(requestContext.getRequestId());
    }
}
