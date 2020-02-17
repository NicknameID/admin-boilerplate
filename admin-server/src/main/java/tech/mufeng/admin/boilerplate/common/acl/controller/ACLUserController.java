package tech.mufeng.admin.boilerplate.common.acl.controller;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import tech.mufeng.admin.boilerplate.common.acl.annotation.RequirePermission;
import tech.mufeng.admin.boilerplate.common.acl.model.entity.User;
import tech.mufeng.admin.boilerplate.common.acl.model.param.LoginParam;
import tech.mufeng.admin.boilerplate.common.acl.service.UserRoleService;
import tech.mufeng.admin.boilerplate.common.acl.service.UserService;
import tech.mufeng.admin.boilerplate.common.constant.UserSessionField;
import tech.mufeng.admin.boilerplate.common.exception.CustomExceptionEnum;
import tech.mufeng.admin.boilerplate.common.model.dto.Result;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

import static tech.mufeng.admin.boilerplate.config.PermissionModuleEnum.ACL_CONFIG;

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
    @Resource
    private UserService userService;
    @Resource
    private PasswordEncoder passwordEncoder;

    // 登录
    @PostMapping("/login")
    public Result<String> login(HttpSession httpSession, @Valid @RequestBody LoginParam loginParam) {
        String username = loginParam.getUsername();
        User user =  userService.getUserByUsername(username);
        if (ObjectUtils.isEmpty(user)) CustomExceptionEnum.NOT_FOUND_EXCEPTION.throwException("该用户不存在");
        String password = loginParam.getPassword();
        String encodedPassword = user.getPassword();
        boolean verifySuccess = passwordEncoder.matches(password, encodedPassword);
        if (!verifySuccess) {
            CustomExceptionEnum.LOGIN_EXCEPTION.throwException("密码错误");
        }
        long uid = user.getUid();
        httpSession.setAttribute(UserSessionField.UID, uid);
        httpSession.setAttribute(UserSessionField.ACTIVE, true);
        httpSession.setAttribute(UserSessionField.LOGIN_TIME_AT, LocalDateTime.now());
        return Result.success();
    }

    // 注销
    @GetMapping("/logout")
    public Result<String> logout(HttpSession httpSession) {
        httpSession.invalidate();
        return Result.success();
    }

    @PostMapping("/{uid}/role/bind")
    @RequirePermission(ACL_CONFIG)
    @Transactional(rollbackFor = Exception.class)
    public Result<String> bindUserRole(@PathVariable Long uid, @RequestBody List<String> roles) {
        if (roles.size() <= 0) return Result.success();
        // 移除全部绑定
        userRoleService.removeBind(uid);
        // 添加绑定
        userRoleService.bind(uid, roles);
        return Result.success();
    }
}
