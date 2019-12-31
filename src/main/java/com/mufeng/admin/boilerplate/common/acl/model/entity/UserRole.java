package com.mufeng.admin.boilerplate.common.acl.model.entity;

import com.mufeng.admin.boilerplate.common.model.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author HuangTianyu
 * @Date 2019-12-29 16:53
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserRole extends BaseEntity {
    private Long uid;

    private String roleCode;
}
