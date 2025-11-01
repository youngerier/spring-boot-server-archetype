#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.web.common.advice;

import ${package}.biz.system.constant.CodeConstant;
import ${package}.infra.business.CustomerExceptionFactory;
import ${package}.infra.business.CustomerExceptionFactory.ExceptionInfo;
import ${package}.infra.business.Result;
import ${package}.infra.toolkits.exception.CustomerException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.Serializable;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @description 
 * @Date        2025/9/24 14:50
 * @author      zhoubobing
 */

@RestControllerAdvice
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    /**
     * 处理参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Serializable> handleValidateException(MethodArgumentNotValidException e,
                                                        HttpServletResponse res) {
        log.error("参数校验异常:", e);
        res.setStatus(HttpStatus.BAD_REQUEST.value());

        String errorMsg = e.getBindingResult().getAllErrors().stream()
                .map(error -> (error instanceof FieldError fieldError)
                        ? fieldError.getField() + ": " + fieldError.getDefaultMessage()
                        : error.getDefaultMessage())
                .collect(Collectors.joining("; "));

        ExceptionInfo info = CustomerExceptionFactory.getMessage(CodeConstant.CODE_400011, errorMsg);
        return Result.fail(info.getCode(), info.getMessage());
    }

    /**
     * 处理请求方法不支持异常
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result<Serializable> handleNotSupported(HttpRequestMethodNotSupportedException e,
                                                   HttpServletResponse res) {
        log.error("请求方法不支持:", e);
        res.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());

        ExceptionInfo info = CustomerExceptionFactory.getMessage(CodeConstant.CODE_405001, (Object) null);
        return Result.fail(info.getCode(), info.getMessage());
    }

    /**
     * 处理业务异常
     */
    @ExceptionHandler(CustomerException.class)
    public Result<Serializable> handleCustomerException(CustomerException e, HttpServletResponse res) {
        log.error("业务异常:", e);
        int status = Optional.ofNullable(e.getHttpStatus()).orElse(HttpStatus.INTERNAL_SERVER_ERROR).value();
        res.setStatus(status);

        return Result.fail(e.getCode(), e.getMessage());
    }

    /**
     * 处理 404 异常（兼容 NoHandlerFoundException / NoResourceFoundException）
     */
    @ExceptionHandler(Exception.class)
    public Result<Serializable> handleException(Exception e, HttpServletResponse res) {
        log.error("系统异常:", e);

        // 动态判断 404 类型
        if (isInstanceOf(e, "org.springframework.web.servlet.NoHandlerFoundException")
                || isInstanceOf(e, "org.springframework.web.servlet.resource.NoResourceFoundException")) {
            res.setStatus(HttpStatus.NOT_FOUND.value());
            ExceptionInfo info = CustomerExceptionFactory.getMessage(CodeConstant.CODE_404001, "资源不存在");
            return Result.fail(info.getCode(), info.getMessage());
        }

        // 默认 500
        res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return Result.fail(CodeConstant.CODE_500000, e.getMessage());
    }

    /**
     * 判断异常是否是某个类的实例（兼容缺少依赖场景）
     */
    private boolean isInstanceOf(Exception e, String className) {
        try {
            Class<?> clazz = Class.forName(className);
            return clazz.isInstance(e);
        } catch (ClassNotFoundException ex) {
            return false;
        }
    }
}
