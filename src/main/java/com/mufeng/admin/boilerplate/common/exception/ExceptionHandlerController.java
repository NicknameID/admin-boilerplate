package com.mufeng.admin.boilerplate.common.exception;

import com.mufeng.admin.boilerplate.common.context.RequestContext;
import com.mufeng.admin.boilerplate.common.model.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

/**
 * Rest接口统一异常捕获处理
 * @Author HuangTianyu
 * @Date 2019-08-09 10:28
 * @Version 1.0
 */
@RestControllerAdvice
@Slf4j
public class ExceptionHandlerController {
    @Resource
    private RequestContext context;

    /**
     * 系统异常捕获
     * @return Result
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public Result handleException(Exception exception) {
        String requestId = context.getRequestId();
        log.error("系统异常[{}]: {}", requestId, exception.getMessage());
        exception.printStackTrace();
        return Result.builder()
                .requestId(requestId)
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(exception.getMessage())
                .time(LocalDateTime.now())
                .build();
    }

    /**
     * 请求参数输入错误处理
     * @return Result
     */
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            MissingServletRequestParameterException.class,
            ConstraintViolationException.class,
            BeanInitializationException.class
    })
    public Result handlerNotValidException(Exception exception) {
        String requestId = context.getRequestId();
        log.warn("入参数错误[]: {}", requestId, exception.getMessage());
        exception.printStackTrace();
        return Result.builder()
                .requestId(requestId)
                .code(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .message(exception.getMessage())
                .time(LocalDateTime.now())
                .build();
    }

    /**
     * 自定义错误捕获
     * @return Result
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({CustomException.class})
    public Result handleCustomException(CustomException exception) {
        String requestId = context.getRequestId();
        log.warn("业务错误[{}]: {}", requestId, exception.getMessage());
        exception.printStackTrace();
        return Result.builder()
                .requestId(requestId)
                .code(exception.getCode())
                .message(exception.getMessage())
                .time(LocalDateTime.now())
                .build();
    }
}
