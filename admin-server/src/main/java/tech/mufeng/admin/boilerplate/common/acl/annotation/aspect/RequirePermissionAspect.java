package tech.mufeng.admin.boilerplate.common.acl.annotation.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import tech.mufeng.admin.boilerplate.common.acl.annotation.RequirePermission;
import tech.mufeng.admin.boilerplate.common.context.service.RequestContextService;
import tech.mufeng.admin.boilerplate.common.exception.CustomExceptionEnum;
import tech.mufeng.admin.boilerplate.config.PermissionModuleEnum;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * HuangTianyu
 * 2020-01-12 17:01
 */
@Aspect
@Component
public class RequirePermissionAspect {
    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private RequestContextService requestContextService;

    @Before("@annotation(requirePermission)")
    public void checkPermission(RequirePermission requirePermission) throws JsonProcessingException {
        PermissionModuleEnum[] permissionCodes = requirePermission.value();
        PermissionModuleEnum[] permissionCodesAnd = requirePermission.and();
        PermissionModuleEnum[] permissionCodesOr = requirePermission.or();
        List<String> contextPermissionCodes = getPermissionCodesFromContext();

        if (permissionCodes.length > 0) {
            verifyAnd(contextPermissionCodes, permissionCodes);
        }

        if (permissionCodesAnd.length > 0) {
            verifyAnd(contextPermissionCodes, permissionCodesAnd);
        }

        if (permissionCodesOr.length > 0) {
            verifyOr(contextPermissionCodes, permissionCodesOr);
        }
    }

    private List<String> getPermissionCodesFromContext() {
        return requestContextService.getContext().getPermissions();
    }

    private void verifyAnd(List<String> contextPermissionCodes, PermissionModuleEnum[]  permissionCodes) {
        for (PermissionModuleEnum permissionModuleEnum : permissionCodes) {
            String code = permissionModuleEnum.getCode();
            if (!contextPermissionCodes.contains(code)) {
                CustomExceptionEnum.ACCESS_DENY_EXCEPTION.throwException();
            }
        }
    }

    private void verifyOr(List<String> contextPermissionCodes, PermissionModuleEnum[]  permissionModuleEnum)
            throws JsonProcessingException {
        List<String> permissionModuleCode = new ArrayList<>(permissionModuleEnum.length);
        for (PermissionModuleEnum it : permissionModuleEnum) {
            permissionModuleCode.add(it.getCode());
        }
        contextPermissionCodes.retainAll(permissionModuleCode);
        if (contextPermissionCodes.isEmpty()) {
            String errMsg = String.format("不存在任意一个权限: %s", objectMapper.writeValueAsString(permissionModuleCode));
            CustomExceptionEnum.ACCESS_DENY_EXCEPTION.throwException(errMsg);
        }
    }
}
