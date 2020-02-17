package tech.mufeng.admin.boilerplate.common.acl.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tech.mufeng.admin.boilerplate.common.acl.mapper.UserMapper;
import tech.mufeng.admin.boilerplate.common.acl.model.entity.User;
import tech.mufeng.admin.boilerplate.common.acl.service.UserService;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public User getUserByUsername(String username) {
        return this.getOne(
                Wrappers.<User>lambdaQuery().eq(User::getUsername, username)
        );
    }
}
