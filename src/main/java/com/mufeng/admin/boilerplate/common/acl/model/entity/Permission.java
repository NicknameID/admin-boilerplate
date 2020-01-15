package com.mufeng.admin.boilerplate.common.acl.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.mufeng.admin.boilerplate.common.model.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author HuangTianyu
 * @Date 2019-11-28 17:10
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Permission extends BaseEntity {
    /**
     * 权限CODE
     */
    @TableId
    private String code;

    /**
     * 权限名
     */
    private String permissionName;

    /**
     * 上级id
     */
    private String parent;
}
