package com.mufeng.admin.boilerplate.common.acl.controller;

import com.mufeng.admin.boilerplate.common.acl.annotation.RequirePermission;
import com.mufeng.admin.boilerplate.common.acl.service.UserRoleService;
import com.mufeng.admin.boilerplate.common.model.dto.Result;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static com.mufeng.admin.boilerplate.config.PermissionModuleEnum.ACL_CONFIG;

/**
 * @Author HuangTianyu
 * @Date 2019-12-29 18:48
 * @Version 1.0
 */
@RestController
@Validated
@RequestMapping("/common/acl/user")
public class ACLUserController {
    @Resource
    private UserRoleService userRoleService;

    @PostMapping("/{uid}/role/bind")
    @Transactional(rollbackFor = Exception.class)
    @RequirePermission(ACL_CONFIG)
    public Result bindUserRole(@PathVariable Long uid, @RequestBody List<String> roles) {
        if (roles.size() <= 0) return Result.success();
        // 移除全部绑定
        userRoleService.removeBind(uid);
        // 添加绑定
        userRoleService.bind(uid, roles);
        return Result.success();
    }
}
