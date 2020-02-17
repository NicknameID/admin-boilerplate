package tech.mufeng.admin.boilerplate.common.acl.model.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginParam {
    @NotNull(message = "用户名必填")
    private String username;

    @NotNull(message = "密码必填")
    private String password;
}
