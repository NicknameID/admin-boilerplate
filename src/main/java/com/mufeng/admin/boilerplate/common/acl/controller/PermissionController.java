package com.mufeng.admin.boilerplate.common.acl.controller;

import com.mufeng.admin.boilerplate.common.acl.model.dto.PermissionTreeDTO;
import com.mufeng.admin.boilerplate.common.acl.service.impl.PermissionServiceImpl;
import com.mufeng.admin.boilerplate.common.context.RequestContext;
import com.mufeng.admin.boilerplate.common.model.dto.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author HuangTianyu
 * @Date 2019-11-30 20:26
 * @Version 1.0
 */
@RestController
@RequestMapping("/common/acl/permission")
public class PermissionController {
    @Resource
    private RequestContext context;
    @Resource
    private PermissionServiceImpl permissionServiceImpl;

    /**
     * 权限树
     * @return
     */
    @GetMapping("/tree")
    public Result tree() {
        List<PermissionTreeDTO> permissionTrees = permissionServiceImpl.tree();
        return Result.success(context.getRequestId(), permissionTrees);
    }
}
