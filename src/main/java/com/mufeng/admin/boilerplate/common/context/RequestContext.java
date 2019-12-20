package com.mufeng.admin.boilerplate.common.context;

import org.springframework.stereotype.Component;

/**
 * @Author HuangTianyu
 * @Date 2019-11-28 14:07
 * @Version 1.0
 */
@Component
public class RequestContext extends BaseContextComponent {
    /**
     * 获取请求上下文的IP
     * @return
     */
    public String getIp() {
        return getContext().getIp();
    }

    public String getToken() {
        return getContext().getToken();
    }

    public String getRequestId() {
        return getContext().getRequestId();
    }
}
