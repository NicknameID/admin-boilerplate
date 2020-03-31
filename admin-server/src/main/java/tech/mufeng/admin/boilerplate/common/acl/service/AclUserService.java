package tech.mufeng.admin.boilerplate.common.acl.service;

import tech.mufeng.admin.boilerplate.common.acl.model.entity.User;
import tech.mufeng.admin.boilerplate.common.base.service.BaseService;

public interface AclUserService extends BaseService<User> {
    User getUserByUsername(String username);

    User getUserByUid(long uid);

    User login(String username, String password);

    void logout();
}
