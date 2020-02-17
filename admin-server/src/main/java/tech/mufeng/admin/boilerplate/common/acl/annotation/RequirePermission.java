package tech.mufeng.admin.boilerplate.common.acl.annotation;

import tech.mufeng.admin.boilerplate.config.PermissionModuleEnum;
import org.springframework.core.annotation.AliasFor;

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
    @AliasFor("and")
    PermissionModuleEnum[] value() default {}; // 资源数组

    @AliasFor("value")
    PermissionModuleEnum[] and() default {};

    PermissionModuleEnum[] or() default {};
}
