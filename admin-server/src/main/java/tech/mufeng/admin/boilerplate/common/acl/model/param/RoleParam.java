package tech.mufeng.admin.boilerplate.common.acl.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author HuangTianyu
 * @Date 2019-11-30 17:15
 * @Version 1.0
 */
@ApiModel(description = "角色参数")
@Data
public class RoleParam {
    /**
     * 角色名字(中文)
     */
    @ApiModelProperty(value = "角色名", required = true, dataType = "string")
    @NotEmpty
    private String roleName;

    /**
     * 角色代号
     */
    @ApiModelProperty(value = "角色code(英文名)", required = true, dataType = "string")
    @NotEmpty
    private String roleCode;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", dataType = "string")
    private String remark;

    /**
     * 权限的ID列表
     */
    @ApiModelProperty(value = "资源code列表", required = true, dataType = "string")
    @NotNull
    private List<String> permissionCodes;
}
