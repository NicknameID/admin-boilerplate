package com.mufeng.admin.boilerplate.common.context;

import com.mufeng.admin.boilerplate.common.context.model.dto.ContextDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Author HuangTianyu
 * @Date 2019-11-28 14:07
 * @Version 1.0
 */
@Component
public class RequestContext {
    // 本地线程副本
    private ThreadLocal<ContextDTO> requestContext = new ThreadLocal<>();

    public RequestContext init() {
        ContextDTO context = new ContextDTO();
        setContext(context);
        return this;
    }

    public void remove() {
        requestContext.remove();
    }

    public void setContext(ContextDTO contextDTO) {
        requestContext.set(contextDTO);
    }

    public ContextDTO getContext() {
        return requestContext.get();
    }

    public RequestContext setUid(Long uid) {
        getContext().setUid(Objects.requireNonNull(uid));
        return this;
    }

    public Long getUid() {
        return getContext().getUid();
    }

    public RequestContext setToken(String token) {
        getContext().setToken(token);
        return this;
    }

    public String getToken() {
        return getContext().getToken();
    }

    public RequestContext setIp(String ip) {
        getContext().setIp(ip);
        return this;
    }

    public String getIp() {
        return getContext().getIp();
    }

    public RequestContext setRequestId(String requestId) {
        getContext().setRequestId(requestId);
        return this;
    }

    public String getRequestId() {
        return getContext().getRequestId();
    }

}
