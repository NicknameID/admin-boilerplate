package tech.mufeng.admin.boilerplate.common.system_config.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import tech.mufeng.admin.boilerplate.common.model.entity.BaseTimeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author HuangTianyu
 * @Date 2019-12-01 14:50
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Config extends BaseTimeEntity {
    @TableId
    private String configName;

    /**
     * 配置值
     */
    private String configValue;

    /**
     * 描述
     */
    private String configDesc;
}
