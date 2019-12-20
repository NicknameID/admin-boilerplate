package com.mufeng.admin.boilerplate.common.exception;

/**
 * @Author HuangTianyu
 * @Date 2019-12-01 14:48
 * @Version 1.0
 */
public class RetryToManyTimesException extends CustomException {
    public RetryToManyTimesException() {
        super(429, "因为访问频繁，你已经被限制访问，稍后重试");
    }
}
