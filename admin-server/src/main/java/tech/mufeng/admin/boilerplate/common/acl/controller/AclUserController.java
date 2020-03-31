package tech.mufeng.admin.boilerplate.common.acl.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.mufeng.admin.boilerplate.common.acl.annotation.RequirePermission;
import tech.mufeng.admin.boilerplate.common.acl.model.entity.User;
import tech.mufeng.admin.boilerplate.common.acl.model.param.LoginParam;
import tech.mufeng.admin.boilerplate.common.acl.service.UserRoleService;
import tech.mufeng.admin.boilerplate.common.acl.service.AclUserService;
import tech.mufeng.admin.boilerplate.common.context.service.RequestContextService;
import tech.mufeng.admin.boilerplate.common.model.dto.Result;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static tech.mufeng.admin.boilerplate.config.PermissionModuleEnum.ACL_CONFIG;

@Api(tags = "ACL-用户相关接口")
@RestController
@RequestMapping("/api/common/acl/user")
public class AclUserController {
    @Resource
    private UserRoleService userRoleService;
    @Resource
    private AclUserService aclUserService;
    @Resource
    private HttpSession httpSession;
    @Resource
    private RequestContextService requestContextService;

    // 登录
    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@Valid @RequestBody LoginParam loginParam) {
        String username = loginParam.getUsername();
        String password = loginParam.getPassword();
        User user = aclUserService.login(username, password);
        String tokeId = httpSession.getId();
        Map<String, Object> data = new HashMap<>();
        data.put("token", tokeId);
        data.put("username", username);
        data.put("uid", String.valueOf(user.getUid()));
        return Result.success(data);
    }

    @ApiOperation(value = "获取用户信息")
    @GetMapping("/user-info")
    public Result<Map<String, Object>> getUserInfo() {
        Map<String, Object> data = new HashMap<>();
        data.put("token", httpSession.getId());
        data.put("uid", String.valueOf(requestContextService.getContext().getUid()));
        data.put("username", requestContextService.getContext().getUsername());
        return Result.success(data);
    }

    // 注销
    @ApiOperation("用户注销")
    @GetMapping("/logout")
    public Result<Object> logout() {
        aclUserService.logout();
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

    @ApiOperation("当前用户的权限列表")
    @GetMapping("/my-permission-codes")
    public Result<List<String>> permissionCodes() {
        return Result.success(requestContextService.getContext().getPermissions());
    }
}
