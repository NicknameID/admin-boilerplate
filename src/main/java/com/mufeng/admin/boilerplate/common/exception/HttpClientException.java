package com.mufeng.admin.boilerplate.common.exception;

/**
 * HuangTianyu
 * 2020-01-11 00:30
 */
public class HttpClientException extends CustomException {
    private static final int code = 502;
    private static final String defaultMsg = "http请求客户异常";

    public HttpClientException() {
        super(code, defaultMsg);
    }
}
