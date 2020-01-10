package com.mufeng.admin.boilerplate.common.acl.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mufeng.admin.boilerplate.common.acl.mapper.PermissionMapper;
import com.mufeng.admin.boilerplate.common.acl.model.dto.PermissionTreeDTO;
import com.mufeng.admin.boilerplate.common.acl.model.entity.Permission;
import com.mufeng.admin.boilerplate.common.acl.service.PermissionService;
import com.mufeng.admin.boilerplate.config.PermissionModuleEnum;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author HuangTianyu
 * @Date 2019-11-30 18:11
 * @Version 1.0
 */
@Service
@Slf4j
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Override
    public List<PermissionTreeDTO> tree() {
        List<Permission> permissions = this.list();
        List<Permission> topLevelPermissions = permissions.stream()
                .filter(PermissionServiceImpl::isTopLevelPermission)
                .collect(Collectors.toList());
        return mapTree(permissions, topLevelPermissions);
    }

    private static boolean isTopLevelPermission(@NonNull Permission permission) {
        String parentCode = permission.getParent();
        return StringUtils.isEmpty(parentCode);
    }

    private List<PermissionTreeDTO> mapTree(List<Permission> source, List<Permission> topLevelPermissions) {
        List<PermissionTreeDTO> permissionTrees = new ArrayList<>();
        topLevelPermissions.forEach(permission -> {
            PermissionTreeDTO permissionTreeDTO = new PermissionTreeDTO();
            BeanUtils.copyProperties(permission, permissionTreeDTO);
            List<Permission> childrenPermissions = filterByParent(source, permission.getCode());
            if (childrenPermissions.size() > 0) {
                permissionTreeDTO.setChildren(mapTree(source, childrenPermissions));
            }else {
                permissionTreeDTO.setChildren(Collections.emptyList());
            }
            permissionTrees.add(permissionTreeDTO);
        });
        return permissionTrees;
    }

    private static List<Permission> filterByParent(List<Permission> source, String parentCode) {
        return source.stream()
                .filter(permission -> hasParent(permission, parentCode))
                .collect(Collectors.toList());
    }

    private static boolean hasParent(@NonNull Permission permission, String parentCode) {
        String parent = permission.getParent();
        if (parent == null) return false;
        return parent.equals(parentCode);
    }

    /**
     * 同步权限模块到数据库
     */
    @Override
    @Transactional
    public void syncToDB() {
        for (PermissionModuleEnum permissionModuleEnum : PermissionModuleEnum.values()) {
            Permission permission = new Permission();
            permission.setCode(permissionModuleEnum.getCode());
            permission.setPermissionName(permissionModuleEnum.getPermissionName());
            PermissionModuleEnum parent = permissionModuleEnum.getParent();
            if (Objects.isNull(parent)) {
                permission.setParent(null);
            }else {
                permission.setParent(parent.getCode());
            }
            this.saveOrUpdate(permission);
        }
    }
}
