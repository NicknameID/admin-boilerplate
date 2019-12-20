package com.mufeng.admin.boilerplate.common.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mufeng.admin.boilerplate.common.acl.mapper.PermissionMapper;
import com.mufeng.admin.boilerplate.common.acl.model.dto.PermissionTreeDTO;
import com.mufeng.admin.boilerplate.common.acl.model.entity.Permission;
import com.mufeng.admin.boilerplate.common.acl.service.PermissionService;
import com.mufeng.admin.boilerplate.config.PermissionModuleEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Author HuangTianyu
 * @Date 2019-11-30 18:11
 * @Version 1.0
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Override
    public List<PermissionTreeDTO> tree() {
        List<Permission> permissions = new LinkedList<>(this.list());
        return mapTree(permissions);
    }

    /**
     * 同步权限模块到数据库
     */
    @Override
    @Transactional
    public void syncToDB() {
        for (PermissionModuleEnum permissionModuleEnum : PermissionModuleEnum.values()) {
            Permission permission = new Permission();
            permission.setCode(permissionModuleEnum.getCode().toLowerCase());
            permission.setPermissionName(permissionModuleEnum.getPermissionName());
            PermissionModuleEnum parent = permissionModuleEnum.getParent();
            if (Objects.isNull(parent)) {
                permission.setParent(null);
            }else {
                permission.setParent(parent.getCode().toLowerCase());
            }
            this.saveOrUpdate(permission);
        }
    }

    private List<Permission> listByParent(String parentCode) {
        LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Permission::getParent, parentCode);
        return this.list(queryWrapper);
    }

    private List<PermissionTreeDTO> mapTree(List<Permission> source) {
        List<PermissionTreeDTO> permissionTrees = new ArrayList<>();
        source.forEach(permission -> {
            PermissionTreeDTO permissionTreeDTO = new PermissionTreeDTO();
            BeanUtils.copyProperties(permission, permissionTreeDTO);
            List<Permission> childrenPermissions = listByParent(permission.getCode());
            if (childrenPermissions.size() > 0) {
                permissionTreeDTO.setChildren(mapTree(childrenPermissions));
            }else {
                permissionTreeDTO.setChildren(Collections.emptyList());
            }
            permissionTrees.add(permissionTreeDTO);
        });
        return permissionTrees;
    }
}
