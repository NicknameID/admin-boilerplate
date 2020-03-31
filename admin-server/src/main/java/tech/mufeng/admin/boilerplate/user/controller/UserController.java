package tech.mufeng.admin.boilerplate.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.mufeng.admin.boilerplate.common.acl.annotation.RequirePermission;
import tech.mufeng.admin.boilerplate.common.acl.model.entity.User;
import tech.mufeng.admin.boilerplate.common.model.dto.PageList;
import tech.mufeng.admin.boilerplate.common.model.dto.Result;
import tech.mufeng.admin.boilerplate.user.model.param.UserListQueryParam;
import tech.mufeng.admin.boilerplate.user.service.UserService;

import javax.annotation.Resource;
import javax.validation.Valid;

import static tech.mufeng.admin.boilerplate.config.PermissionModuleEnum.USER_ACCOUNT;

@Api(tags = "用户相关接口")
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Resource
    private UserService userService;

    @ApiOperation("用户分页列表")
    @GetMapping("/list-page")
    @RequirePermission(and = USER_ACCOUNT)
    public Result<PageList<User>> userPage(@Valid UserListQueryParam userListQueryParam) {
        return Result.success(userService.getUserPageList(userListQueryParam));
    }
}
