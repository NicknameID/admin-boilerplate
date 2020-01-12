package com.mufeng.admin.boilerplate.common.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mufeng.admin.boilerplate.common.acl.mapper.UserRoleMapper;
import com.mufeng.admin.boilerplate.common.acl.model.entity.Role;
import com.mufeng.admin.boilerplate.common.acl.model.entity.UserRole;
import com.mufeng.admin.boilerplate.common.acl.service.RoleService;
import com.mufeng.admin.boilerplate.common.acl.service.UserRoleService;
import com.mufeng.admin.boilerplate.common.exception.ConflictException;
import com.mufeng.admin.boilerplate.common.exception.CustomException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author HuangTianyu
 * @Date 2019-12-29 18:33
 * @Version 1.0
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
    @Resource
    private RoleService roleService;

    @Override
    public int countByRoleCode(String roleCode) {
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getRoleCode, roleCode);
        return this.count(queryWrapper);
    }

    @Override
    public void bind(Long uid, List<String> roleCodes) {
        List<Role> roles = roleService.list();
        Set<String> allRoleCodes = new HashSet<>();
        roles.forEach(role -> allRoleCodes.add(role.getCode()));
        roleCodes.forEach(roleCode -> {
            if (allRoleCodes.contains(roleCode)) {
                UserRole userRole = new UserRole();
                LocalDateTime now = LocalDateTime.now();
                userRole.setUid(uid);
                userRole.setRoleCode(roleCode);
                userRole.setCreatedTime(now);
                userRole.setUpdatedTime(now);
                this.save(userRole);
            }
        });
    }

    @Override
    public void removeBind(Long uid) {
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUid, uid);
        this.remove(queryWrapper);
    }

    @Override
    public void verifyRoleHasUser(String roleCode) {
        if (roleHasUser(roleCode)) {
            throw new ConflictException().extendsMsg("该角色还有用户绑定，无法删除");
        }
    }

    @Override
    public List<UserRole> getUserRolesByUid(Long uid) {
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUid, uid);
        return this.list(queryWrapper);
    }

    private boolean roleHasUser(String roleCode) {
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getRoleCode, roleCode);
        return this.count(queryWrapper) > 0;
    }
}
