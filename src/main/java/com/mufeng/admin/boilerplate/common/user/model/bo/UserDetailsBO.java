package com.mufeng.admin.boilerplate.common.user.model.bo;

import com.mufeng.admin.boilerplate.common.acl.model.entity.Permission;
import com.mufeng.admin.boilerplate.common.user.model.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * HuangTianyu
 * 2020-01-09 16:01
 */
public class UserDetailsBO implements UserDetails {
    private User user;
    private List<Permission> permissions;

    public static UserDetailsBO getInstance(User user, List<Permission> permissions) {
        return new UserDetailsBO(user, permissions);
    }

    private UserDetailsBO(User user, List<Permission> permissions) {
        this.user = user;
        this.permissions = permissions;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Permission permission : permissions) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(permission.getCode());
            authorities.add(simpleGrantedAuthority);
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
