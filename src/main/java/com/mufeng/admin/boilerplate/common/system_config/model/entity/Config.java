package com.mufeng.admin.boilerplate.common.system_config.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.mufeng.admin.boilerplate.common.model.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author HuangTianyu
 * @Date 2019-12-01 14:50
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Config extends BaseEntity {
    @TableId
    private String configName;

    /**
     * 配置值
     */
    private String configValue;

    /**
     * 描述
     */
    private String cofnigDesc;
}
