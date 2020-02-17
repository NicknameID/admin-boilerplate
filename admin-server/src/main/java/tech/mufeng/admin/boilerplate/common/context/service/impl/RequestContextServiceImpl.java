package tech.mufeng.admin.boilerplate.common.context.service.impl;

import tech.mufeng.admin.boilerplate.common.acl.model.entity.Permission;
import tech.mufeng.admin.boilerplate.common.acl.service.PermissionService;
import tech.mufeng.admin.boilerplate.common.context.model.RequestContext;
import tech.mufeng.admin.boilerplate.common.context.service.RequestContextService;
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

    @Override
    public RequestContext init() {
        RequestContext requestContext = RequestContext.builder()
                .requestId(SimpleCommonUtil.getUppercaseUUID())
                .permissions(getPermission())
                .build();
        REQUEST_CONTEXT.set(requestContext);
        return requestContext;
    }

    private List<String> getPermission() {
        long uid = (int) httpSession.getAttribute("uid");
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
