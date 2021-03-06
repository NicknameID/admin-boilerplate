package tech.mufeng.admin.boilerplate.config;

import lombok.Getter;

/**
 * 系统权限配置，根据实际系统的权限模块设计来填写，启动的时候会自动同步到数据库
 * @Author HuangTianyu
 * @Date 2019-12-10 16:32
 * @Version 1.0
 */
public enum PermissionModuleEnum {
    SYSTEM_CONFIG("system_config", "系统管理", null),
        RUNTIME_CONFIGURATION("runtime_config", "系统运行参数配置", SYSTEM_CONFIG),
    ACL_CONFIG("acl_config", "权限管理", null),
        ROLE_PERMISSION("role_permission", "角色管理", ACL_CONFIG),
        USER_ACCOUNT("user_account", "账户管理", ACL_CONFIG);

    @Getter
    private String code;

    @Getter
    private String permissionName;

    @Getter
    private PermissionModuleEnum parent;

    PermissionModuleEnum(String code, String permissionName, PermissionModuleEnum parent) {
        this.code = code;
        this.permissionName = permissionName;
        this.parent = parent;
    }
}
