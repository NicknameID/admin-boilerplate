package tech.mufeng.admin.boilerplate.common.context.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RequestContext {
    // 用户uid
    private Long uid;
    // 用户名
    private String username;
    // 请求ID
    private String requestId;
    // 权限
    private List<String> permissions;
}
