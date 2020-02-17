package tech.mufeng.admin.boilerplate.common.acl.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import tech.mufeng.admin.boilerplate.common.model.entity.BaseTimeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author HuangTianyu
 * @Date 2019-12-29 16:53
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserRole extends BaseTimeEntity {
    @TableId
    private Long uid;

    private String roleCode;
}
