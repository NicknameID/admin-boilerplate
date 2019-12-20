package com.mufeng.admin.boilerplate.common.user.model.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Author HuangTianyu
 * @Date 2019-12-08 11:45
 * @Version 1.0
 */
@Data
public class AddUserParam {
    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @NotNull
    private boolean staff;

    private String roleCode;
}
