package tech.mufeng.admin.boilerplate.common.base.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseQueryParam {
    // 时间筛选条件，开始时间
    private LocalDateTime startTime;

    // 时间筛选条件，结束时间
    private LocalDateTime endTime;

    // 倒叙排列
    private Boolean orderByCreatedAtDesc;
}
