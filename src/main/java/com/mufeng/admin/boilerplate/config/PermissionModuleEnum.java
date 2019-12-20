package com.mufeng.admin.boilerplate.config;

import lombok.Getter;

/**
 * 系统权限配置，根据实际系统的权限模块设计来填写，启动的时候会自动同步到数据库
 * @Author HuangTianyu
 * @Date 2019-12-10 16:32
 * @Version 1.0
 */
public enum PermissionModuleEnum {
    SYSTEM_CONFIG("system_config", "系统配置", null),
        RUMTIME_CONFIGURATION("rumtime_configuration", "系统运行参数配置", PermissionModuleEnum.SYSTEM_CONFIG);

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
