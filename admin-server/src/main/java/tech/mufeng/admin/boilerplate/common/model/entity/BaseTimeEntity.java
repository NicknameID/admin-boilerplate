package tech.mufeng.admin.boilerplate.common.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author HuangTianyu
 * @Date 2019-11-28 17:04
 * @Version 1.0
 */
@Data
public class BaseTimeEntity implements Serializable {
    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;

}
