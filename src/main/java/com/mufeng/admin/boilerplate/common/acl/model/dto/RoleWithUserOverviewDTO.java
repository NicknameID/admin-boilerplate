package com.mufeng.admin.boilerplate.common.acl.model.dto;

import com.mufeng.admin.boilerplate.common.acl.model.entity.Permission;
import com.mufeng.admin.boilerplate.common.acl.model.entity.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @Author HuangTianyu
 * @Date 2019-11-29 17:57
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleWithUserOverviewDTO extends Role implements Serializable {

    private Integer userCount;

    private List<Permission> permissions;
}
