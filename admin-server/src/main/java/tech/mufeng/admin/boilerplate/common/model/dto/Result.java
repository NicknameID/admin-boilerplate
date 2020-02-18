package tech.mufeng.admin.boilerplate.common.model.dto;

import cn.hutool.json.JSONUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiResponse;
import lombok.experimental.Accessors;
import tech.mufeng.admin.boilerplate.common.context.service.RequestContextService;
import tech.mufeng.admin.boilerplate.common.util.ApplicationContextUtil;
import tech.mufeng.admin.boilerplate.common.util.SimpleCommonUtil;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author HuangTianyu
 * @Date 2019-11-29 16:18
 * @Version 1.0
 */
@ApiModel(description = "响应体")
@Data
@Builder
@Accessors(chain = true)
public class Result<T> implements Serializable {
    private static final long serialVersionUID = -8041936765895234705L;
    private static final String DEFAULT_CODE = "SUCCESS";
    private static final String DEFAULT_MESSAGE = "成功";

    @ApiModelProperty(value = "业务码", required = true, dataType = "string", example="SUCCESS")
    private String code;

    @ApiModelProperty(value = "业务描述信息", required = true, dataType = "string", example="成功")
    private String message;

    @ApiModelProperty(value = "请求ID", required = true, dataType = "string")
    private String requestId;

    @ApiModelProperty(value = "请求时间点", required = true, dataType = "string", example="2020-01-01 00:00:00")
    private LocalDateTime time;

    // 数据体
    @ApiModelProperty(value = "数据体", required = true, dataType = "object")
    private T data;

    public String toJson() {
        return JSONUtil.toJsonStr(this);
    }

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> success(T data) {
        return success(DEFAULT_MESSAGE, data);
    }

    public static <T> Result<T> success(String message, T data) {
        return success(DEFAULT_CODE, message, data);
    }

    public static <T> Result<T> success(String code, String message, T data) {
        return Result.<T>builder()
                .code(code)
                .requestId(getRequestId())
                .message(message)
                .time(LocalDateTime.now())
                .data(data)
                .build();
    }

    public static String getRequestId() {
        RequestContextService requestContextService = ApplicationContextUtil.getBean(RequestContextService.class);
        return requestContextService != null ?
                requestContextService.getContext().getRequestId() :
                SimpleCommonUtil.getUppercaseUUID();
    }
}
