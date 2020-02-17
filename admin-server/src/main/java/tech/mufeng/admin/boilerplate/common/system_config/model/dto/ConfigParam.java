package tech.mufeng.admin.boilerplate.common.system_config.model.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Author HuangTianyu
 * @Date 2019-12-01 16:21
 * @Version 1.0
 */
@Data
public class ConfigParam {
    @NotEmpty
    private String key;

    @NotEmpty
    private String value;
}
