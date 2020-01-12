package com.mufeng.admin.boilerplate.common.acl.annotation.aspect;

import com.mufeng.admin.boilerplate.common.acl.annotation.RequirePermission;
import com.mufeng.admin.boilerplate.config.PermissionModuleEnum;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * HuangTianyu
 * 2020-01-12 17:01
 */
@Aspect
@Component
public class RequirePermissionAspect {

    @Before("@annotation(requirePermission)")
    public void checkPermission(RequirePermission requirePermission) {
        PermissionModuleEnum[] permissionCodes = requirePermission.value();
        List<String> contextPermissionCodes = getPermissionCodesFromContext();
        for (PermissionModuleEnum permissionModuleEnum : permissionCodes) {
            String code = permissionModuleEnum.getCode();
            if (!contextPermissionCodes.contains(code)) {
                throw new AccessDenyException();
            }
        }
    }

    private List<String> getPermissionCodesFromContext() {
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder
                .getContext().getAuthentication().getAuthorities();
        List<String> codes = new ArrayList<>();
        for (GrantedAuthority grantedAuthority : authorities) {
            String authority = grantedAuthority.getAuthority();
            codes.add(authority);
        }
        return codes;
    }
}
