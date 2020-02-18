package tech.mufeng.admin.boilerplate.common.acl.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.mufeng.admin.boilerplate.common.acl.annotation.RequirePermission;
import tech.mufeng.admin.boilerplate.common.acl.model.param.LoginParam;
import tech.mufeng.admin.boilerplate.common.acl.service.UserRoleService;
import tech.mufeng.admin.boilerplate.common.acl.service.UserService;
import tech.mufeng.admin.boilerplate.common.model.dto.Result;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static tech.mufeng.admin.boilerplate.config.PermissionModuleEnum.ACL_CONFIG;

@Api(tags = "用户相关接口")
@RestController
@RequestMapping("/api/common/acl/user")
public class UserController {
    @Resource
    private UserRoleService userRoleService;
    @Resource
    private UserService userService;

    // 登录
    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public Result<Object> login(@Valid @RequestBody LoginParam loginParam) {
        String username = loginParam.getUsername();
        String password = loginParam.getPassword();
        userService.login(username, password);
        return Result.success();
    }

    // 注销
    @ApiOperation("用户注销")
    @GetMapping("/logout")
    public Result<Object> logout() {
        userService.logout();
        return Result.success();
    }

    @ApiOperation("绑定用户角色")
    @PostMapping("/{uid}/role/bind")
    @RequirePermission(ACL_CONFIG)
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> bindUserRole(
            @ApiParam("用户UID") @PathVariable Long uid,
            @ApiParam("角色code列表") @RequestBody List<String> roles
    ) {
        // 移除全部绑定
        userRoleService.removeBind(uid);
        // 添加绑定
        userRoleService.bind(uid, roles);
        return Result.success();
    }
}
