package tech.mufeng.admin.boilerplate.common.exception;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

public enum CustomExceptionEnum {
    SYSTEM_EXCEPTION("系统未知错误, 请联系系统开发人员"),
    NOT_LOGIN_EXCEPTION("未登录"),
    LOGIN_EXCEPTION("登录异常"),
    ACCESS_DENY_EXCEPTION("权限错误"),
    CONFLICT_EXCEPTION("对象冲突"),
    NOT_FOUND_EXCEPTION("对象未找到");

    @Getter
    private String message;

    CustomExceptionEnum(String msg) {
        this.message = msg;
    }

    public void throwException() {
        throw generateBusinessException(null);
    }

    public void throwException(String extendsMsg) {
        throw generateBusinessException(extendsMsg);
    }

    public CustomException generateBusinessException(String extendsMsg) {
        if (StringUtils.isEmpty(extendsMsg)) {
            return new CustomException(this.name(), this.message);
        }else {
            return new CustomException(this.name(), String.format("[%s]:%s", this.message, extendsMsg));
        }
    }
}
