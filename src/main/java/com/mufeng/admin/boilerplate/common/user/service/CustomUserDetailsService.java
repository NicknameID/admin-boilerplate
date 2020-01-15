package com.mufeng.admin.boilerplate.common.user.service;

import com.google.common.collect.Lists;
import com.mufeng.admin.boilerplate.common.acl.model.entity.Permission;
import com.mufeng.admin.boilerplate.common.acl.service.ACLService;
import com.mufeng.admin.boilerplate.common.user.model.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userService.getByUsername(s);
        if (user.isEmpty()) {
            String errorMsg = String.format("Username not found: %s", s);
            throw new UsernameNotFoundException(errorMsg);
        }
        List<Permission> permissionList = aclService.getPermissionListByUserId(user.get().getId());
        List<GrantedAuthority> authorities = Lists.newArrayList();
        for (Permission permission : permissionList) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(permission.getCode());
            authorities.add(simpleGrantedAuthority);
        }
        final String username = user.get().getUsername();
        final String password = user.get().getPassword();
        return new org.springframework.security.core.userdetails.User(username, password, authorities);
    }
}
