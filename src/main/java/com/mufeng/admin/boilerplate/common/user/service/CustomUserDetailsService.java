package com.mufeng.admin.boilerplate.common.user.service;

import com.mufeng.admin.boilerplate.common.acl.model.entity.Permission;
import com.mufeng.admin.boilerplate.common.acl.service.ACLService;
import com.mufeng.admin.boilerplate.common.user.model.bo.UserDetailsBO;
import com.mufeng.admin.boilerplate.common.user.model.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * HuangTianyu
 * 2020-01-09 15:57
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Resource
    private UserService userService;
    @Resource
    private ACLService aclService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userService.getByUsername(username);
        if (user.isEmpty()) {
            String errorMsg = String.format("Username not found: %s", username);
            throw new UsernameNotFoundException(errorMsg);
        }
        List<Permission> permissionList = aclService.getPermissionListByUserId(user.get().getId());
        return UserDetailsBO.getInstance(user.get(), permissionList);
    }
}
