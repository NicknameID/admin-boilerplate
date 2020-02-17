package tech.mufeng.admin.boilerplate.common.acl.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import tech.mufeng.admin.boilerplate.common.model.entity.BaseTimeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author HuangTianyu
 * @Date 2019-11-28 17:10
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Permission extends BaseTimeEntity {
    /**
     * 权限CODE
     */
    @TableId
    private String code;

    /**
     * 权限名
     */
    private String permissionName;

    /**
     * 上级id
     */
    private String parent;
}
