package com.mufeng.admin.boilerplate.common.acl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mufeng.admin.boilerplate.common.acl.model.dto.PermissionTreeDTO;
import com.mufeng.admin.boilerplate.common.acl.model.entity.Permission;

import java.util.List;

/**
 * @Author HuangTianyu
 * @Date 2019-12-19 13:08
 * @Version 1.0
 */
public interface PermissionService extends IService<Permission> {
    /**
     * 返回系统定义的权限树
     * @return List<PermissionTreeDTO>
     */
    List<PermissionTreeDTO> tree();

    /**
     * 同步权限到数据库表
     */
    void syncToDB();
}
