package com.mufeng.admin.boilerplate.common.acl.controller;

import com.mufeng.admin.boilerplate.common.acl.model.dto.PermissionTreeDTO;
import com.mufeng.admin.boilerplate.common.acl.service.PermissionService;
import com.mufeng.admin.boilerplate.common.model.dto.Result;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author HuangTianyu
 * @Date 2019-12-29 18:46
 * @Version 1.0
 */
@RestController
@RequestMapping("/common/acl/permission")
public class PermissionController {
    @Resource
    private PermissionService permissionService;

    // 权限树
    @GetMapping("/tree")
    @PreAuthorize("hasAnyAuthority('acl_config')")
    public Result tree() {
        List<PermissionTreeDTO> permissionTrees = permissionService.tree();
        return Result.success(permissionTrees);
    }
}
