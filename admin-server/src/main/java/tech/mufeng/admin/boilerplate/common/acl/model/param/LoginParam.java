package tech.mufeng.admin.boilerplate.common.acl.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel(description = "传输用户名和密码")
@Data
public class LoginParam {
    @ApiModelProperty(value = "用户名", required = true, dataType = "string", example = "admin")
    @NotNull(message = "用户名必填")
    private String username;

    @ApiModelProperty(value = "登录密码", required = true, dataType = "string", example = "admin")
    @NotNull(message = "密码必填")
    private String password;
}
