package tech.mufeng.admin.boilerplate.common.context.service;

import tech.mufeng.admin.boilerplate.common.context.model.RequestContext;

public interface RequestContextService {
    RequestContext init();
    RequestContext getContext();
    void clear();
}
