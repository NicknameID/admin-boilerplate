package tech.mufeng.admin.boilerplate.common.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import tech.mufeng.admin.boilerplate.common.acl.mapper.RolePermissionMapper;
import tech.mufeng.admin.boilerplate.common.acl.model.entity.Permission;
import tech.mufeng.admin.boilerplate.common.acl.model.entity.RolePermission;
import tech.mufeng.admin.boilerplate.common.acl.service.PermissionService;
import tech.mufeng.admin.boilerplate.common.acl.service.RolePermissionService;
import tech.mufeng.admin.boilerplate.common.acl.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author HuangTianyu
 * @Date 2019-11-30 18:12
 * @Version 1.0
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {
    @Resource
    private PermissionService permissionService;
    @Resource
    private RoleService roleService;

    /**
     * 移除权限
     * @param roleCode 角色代号
     */
    @Override
    public void removeBind(String roleCode) {
        LambdaQueryWrapper<RolePermission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RolePermission::getRoleCode, roleCode);
        this.remove(queryWrapper);
    }

    /**
     * 绑定权限
     * @param roleCode 角色代号
     * @param permissionCodes 权限列表
     */
    @Override
    public void bind(String roleCode, List<String> permissionCodes) {
        List<RolePermission> rolePermissions = new ArrayList<>();
        permissionCodes.forEach(permissionCode -> {
            if (!hasBind(roleCode, permissionCode)) {
                Permission permission = permissionService.getById(permissionCode);
                if (!Objects.isNull(permission)) {
                    RolePermission rolePermission = new RolePermission();
                    rolePermission.setRoleCode(roleCode);
                    rolePermission.setPermissionCode(permissionCode);
                    rolePermissions.add(rolePermission);
                }
            }
        });
        if (rolePermissions.size() > 0) {
            this.saveBatch(rolePermissions);
        }
    }

    private boolean hasBind(String roleCode, String permissionCode) {
        LambdaQueryWrapper<RolePermission> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(RolePermission::getRoleCode, roleCode);
        queryWrapper.eq(RolePermission::getPermissionCode, permissionCode);
        return !Objects.isNull(this.getOne(queryWrapper));
    }

    @Override
    public List<RolePermission> listByRoleCode(String roleCode) {
        LambdaQueryWrapper<RolePermission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RolePermission::getRoleCode, roleCode);
        return this.list(queryWrapper);
    }

    @Override
    public List<RolePermission> listByPermissionCode(String permissionCode) {
        LambdaQueryWrapper<RolePermission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RolePermission::getPermissionCode, permissionCode);
        return this.list(queryWrapper);
    }
}
