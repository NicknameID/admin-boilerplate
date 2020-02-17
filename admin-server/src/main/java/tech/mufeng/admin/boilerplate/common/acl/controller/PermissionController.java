package tech.mufeng.admin.boilerplate.common.acl.controller;

import tech.mufeng.admin.boilerplate.common.acl.annotation.RequirePermission;
import tech.mufeng.admin.boilerplate.common.acl.model.dto.PermissionTree;
import tech.mufeng.admin.boilerplate.common.acl.service.PermissionService;
import tech.mufeng.admin.boilerplate.common.model.dto.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static tech.mufeng.admin.boilerplate.config.PermissionModuleEnum.ACL_CONFIG;

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
    @RequirePermission(ACL_CONFIG)
    public Result<List<PermissionTree>> tree() {
        List<PermissionTree> permissionTrees = permissionService.tree();
        return Result.success(permissionTrees);
    }

    // 同步权限到数据库
    @GetMapping("/sync-to-db")
    @RequirePermission(ACL_CONFIG)
    public Result<String> syncToDB() {
        permissionService.syncToDB();
        return Result.success();
    }
}
