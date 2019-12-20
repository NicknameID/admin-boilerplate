package com.mufeng.admin.boilerplate.common.user.model.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Author HuangTianyu
 * @Date 2019-12-01 16:26
 * @Version 1.0
 */
@Data
public class UserLoginParam {
    @NotEmpty
    private String username;

    @NotEmpty
    private String password;
}
