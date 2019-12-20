package com.mufeng.admin.boilerplate.common.acl.model.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author HuangTianyu
 * @Date 2019-12-10 17:26
 * @Version 1.0
 */
@Data
public class PermissionTreeDTO {
    private String code;
    private String permissionName;
    private List<PermissionTreeDTO> children;
}
