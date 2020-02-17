package tech.mufeng.admin.boilerplate.common.context.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RequestContext {
    private Long uid; // 用户uid
    private String requestId; // 请求ID
    private List<String> permissions; // 权限
}
