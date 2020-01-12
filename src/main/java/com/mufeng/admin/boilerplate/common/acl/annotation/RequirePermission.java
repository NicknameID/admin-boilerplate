package com.mufeng.admin.boilerplate.common.acl.annotation;

import com.mufeng.admin.boilerplate.config.PermissionModuleEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * HuangTianyu
 * 2020-01-12 16:58
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequirePermission {
    PermissionModuleEnum[] value() default {}; // 资源数组
}
