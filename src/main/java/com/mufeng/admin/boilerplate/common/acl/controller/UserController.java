package com.mufeng.admin.boilerplate.common.acl.controller;

import com.mufeng.admin.boilerplate.common.acl.service.UserRoleService;
import com.mufeng.admin.boilerplate.common.model.dto.Result;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * @Author HuangTianyu
 * @Date 2019-12-29 18:48
 * @Version 1.0
 */
@RestController
@Validated
@RequestMapping("/common/acl/user")
public class UserController {
    @Resource
    private UserRoleService userRoleService;

    @PostMapping("/{uid}/role/bind")
    @Transactional(rollbackFor = Exception.class)
    public Result bindUserRole(@PathVariable Long uid, @Min(1) @RequestBody List<String> roles) {
        // 移除全部绑定
        userRoleService.removeBind(uid);
        // 添加绑定
        userRoleService.bind(uid, roles);
        return Result.success();
    }
}
