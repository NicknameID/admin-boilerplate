package tech.mufeng.admin.boilerplate.common.exception;

import lombok.Getter;

/**
 * @Author HuangTianyu
 * @Date 2019-11-30 17:18
 * @Version 1.0
 */
public class CustomException extends RuntimeException {
    @Getter
    private String code;
    @Getter
    private String message;

    public CustomException(String code, String msg) {
        super(msg);
        this.code = code;
        this.message = msg;
    }
}
