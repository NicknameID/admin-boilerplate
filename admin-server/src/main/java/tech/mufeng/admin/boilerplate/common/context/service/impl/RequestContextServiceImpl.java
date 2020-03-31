package tech.mufeng.admin.boilerplate.common.context.service.impl;

import tech.mufeng.admin.boilerplate.common.acl.model.entity.Permission;
import tech.mufeng.admin.boilerplate.common.acl.model.entity.User;
import tech.mufeng.admin.boilerplate.common.acl.service.PermissionService;
import tech.mufeng.admin.boilerplate.common.acl.service.AclUserService;
import tech.mufeng.admin.boilerplate.common.constant.UserSessionField;
import tech.mufeng.admin.boilerplate.common.context.model.RequestContext;
import tech.mufeng.admin.boilerplate.common.context.service.RequestContextService;
import tech.mufeng.admin.boilerplate.common.exception.CustomExceptionEnum;
import tech.mufeng.admin.boilerplate.common.util.SimpleCommonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestContextServiceImpl implements RequestContextService {
    private final ThreadLocal<RequestContext> REQUEST_CONTEXT = new ThreadLocal<>();
    @Resource
    private HttpSession httpSession;
    @Resource
    private PermissionService permissionService;
    @Resource
    private AclUserService aclUserService;

    @Override
    public RequestContext init() {
        long uid = (long) httpSession.getAttribute(UserSessionField.UID);
        User user = aclUserService.getUserByUid(uid);
        if (user == null) {
            CustomExceptionEnum.NOT_LOGIN_EXCEPTION.throwException();
        }
        RequestContext requestContext = RequestContext.builder()
                .uid(user.getUid())
                .username(user.getUsername())
                .requestId(SimpleCommonUtil.getUppercaseUUID())
                .permissions(getPermission())
                .build();
        REQUEST_CONTEXT.set(requestContext);
        return requestContext;
    }

    private List<String> getPermission() {
        long uid = (long) httpSession.getAttribute(UserSessionField.UID);
        List<Permission> permissions = permissionService.listByUid(uid);
        return permissions.stream()
                .map(Permission::getCode)
                .collect(Collectors.toList());
    }

    @Override
    public RequestContext getContext() {
        return REQUEST_CONTEXT.get();
    }

    @Override
    public void clear() {
        REQUEST_CONTEXT.remove();
    }
}
