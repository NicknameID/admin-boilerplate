package tech.mufeng.admin.boilerplate.common.exception;

import tech.mufeng.admin.boilerplate.common.model.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
    /**
     * 自定义错误捕获
     * @return Result
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({CustomException.class})
    public Result handleCustomException(CustomException exception) {
        log.warn("业务错误---{}", exception.getMessage());
        exception.printStackTrace();
        return Result.builder()
                .code(exception.getCode())
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
        log.warn("入参数错误---{}", exception.getMessage());
        exception.printStackTrace();
        return Result.builder()
                .code("UNPROCESSABLE_ENTITY")
                .message(exception.getMessage())
                .time(LocalDateTime.now())
                .build();
    }

    /**
     * 系统异常捕获
     * @return Result
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public Result handleException(Exception exception) {
        log.error("系统异常---{}", exception.getMessage());
        exception.printStackTrace();
        return Result.builder()
                .code("INTERNAL_SERVER_ERROR")
                .message(exception.getMessage())
                .time(LocalDateTime.now())
                .build();
    }
}
