package tech.mufeng.admin.boilerplate.common.acl.model.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author HuangTianyu
 * @Date 2019-11-30 17:15
 * @Version 1.0
 */
@Data
public class RoleParam {
    /**
     * 角色名字(中文)
     */
    @NotEmpty
    private String roleName;

    /**
     * 角色代号
     */
    @NotEmpty
    private String roleCode;

    /**
     * 备注
     */
    private String remark;

    /**
     * 权限的ID列表
     */
    @NotNull
    private List<String> permissionCodes;
}
