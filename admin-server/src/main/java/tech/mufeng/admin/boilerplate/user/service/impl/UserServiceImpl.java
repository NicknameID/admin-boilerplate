package tech.mufeng.admin.boilerplate.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Service;
import tech.mufeng.admin.boilerplate.common.acl.model.entity.User;
import tech.mufeng.admin.boilerplate.common.acl.service.AclUserService;
import tech.mufeng.admin.boilerplate.common.model.dto.PageList;
import tech.mufeng.admin.boilerplate.user.model.param.UserListQueryParam;
import tech.mufeng.admin.boilerplate.user.repository.UserRepository;
import tech.mufeng.admin.boilerplate.user.service.UserService;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private AclUserService aclUserService;
    @Resource
    private UserRepository userRepository;

    @Override
    public User getUserByUsername(String username) {
        return aclUserService.getUserByUsername(username);
    }

    @Override
    public User getUserByUid(long uid) {
        return aclUserService.getUserByUid(uid);
    }

    @Override
    public PageList<User> getUserPageList(UserListQueryParam userListQueryParam) {
        IPage<User> userPage = userRepository.getUserPage(userListQueryParam);
        return PageList.transformFromPage(userPage);
    }
}
