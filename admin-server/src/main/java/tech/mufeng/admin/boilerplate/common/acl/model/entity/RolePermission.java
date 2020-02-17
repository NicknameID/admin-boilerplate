package tech.mufeng.admin.boilerplate.common.acl.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import tech.mufeng.admin.boilerplate.common.model.entity.BaseTimeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author HuangTianyu
 * @Date 2019-11-28 17:12
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RolePermission extends BaseTimeEntity {
    /**
     * 角色id
     */
    @TableId
    private String roleCode;

    /**
     * 权限id
     */
    private String permissionCode;
}

