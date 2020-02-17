package tech.mufeng.admin.boilerplate.common.acl.service;

import tech.mufeng.admin.boilerplate.common.acl.model.dto.PermissionTree;
import tech.mufeng.admin.boilerplate.common.acl.model.entity.Permission;
import tech.mufeng.admin.boilerplate.common.base.service.BaseService;

import java.util.List;

/**
 * @Author HuangTianyu
 * @Date 2019-12-19 13:08
 * @Version 1.0
 */
public interface PermissionService extends BaseService<Permission> {
    /**
     * 返回系统定义的权限树
     * @return List<PermissionTree>
     */
    List<PermissionTree> tree();

    /**
     * 同步权限到数据库表
     */
    void syncToDB();

    List<Permission> listByUid(Long uid);
}
