package tech.mufeng.admin.boilerplate.user.service;

import tech.mufeng.admin.boilerplate.common.acl.model.entity.User;
import tech.mufeng.admin.boilerplate.common.model.dto.PageList;
import tech.mufeng.admin.boilerplate.user.model.param.UserListQueryParam;

import java.util.List;

public interface UserService {
    User getUserByUsername(String username);

    User getUserByUid(long uid);

    PageList<User> getUserPageList(UserListQueryParam userListQueryParam);
}
