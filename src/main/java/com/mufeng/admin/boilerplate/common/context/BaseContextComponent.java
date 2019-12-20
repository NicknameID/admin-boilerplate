package com.mufeng.admin.boilerplate.common.context;

import cn.hutool.core.util.IdUtil;
import com.mufeng.admin.boilerplate.common.context.model.dto.ContextDTO;
import com.mufeng.admin.boilerplate.common.util.http.IpUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author HuangTianyu
 * @Date 2019-11-28 14:53
 * @Version 1.0
 */
public class BaseContextComponent {
    // 本地线程副本
    private ThreadLocal<ContextDTO> requestContext = new ThreadLocal<>();

    public void setContext(ContextDTO contextDTO) {
        requestContext.set(contextDTO);
    }

    public ContextDTO getContext() {
        return requestContext.get();
    }

    public void remove() {
        requestContext.remove();
    }

    public void init(HttpServletRequest request) {
        ContextDTO context = new ContextDTO();
        // 这里可以挂载当前请求的信息到contextDTO上
        context.setIp(IpUtil.getIp(request));
        context.setToken("");
        context.setRequestId(IdUtil.simpleUUID());
        setContext(context);
    }
}
