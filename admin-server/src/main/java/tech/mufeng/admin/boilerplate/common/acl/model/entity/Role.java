package tech.mufeng.admin.boilerplate.common.acl.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import tech.mufeng.admin.boilerplate.common.model.entity.BaseTimeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author HuangTianyu
 * @Date 2019-11-28 17:11
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseTimeEntity {
    /**
     * id
     */
    @TableId
    private String code;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 备注
     */
    private String remark;
}
