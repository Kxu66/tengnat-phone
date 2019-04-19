package com.tengnat.imapi.utils.data.advice;


import com.tengnat.assistant.data.resp.InvokeResult;
import com.tengnat.assistant.exception.BusinessException;
import com.tengnat.assistant.exception.ExpiredException;
import com.tengnat.assistant.exception.PermissionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {
    private static Map<String, String> exceptionMap;

    static {
        exceptionMap = new HashMap<>();
        exceptionMap.put("MissingParameterException", "缺少参数");
        exceptionMap.put("MissingAttributeException", "缺少属性");
        exceptionMap.put("MethodArgumentTypeMismatchException", "参数不匹配");
        exceptionMap.put("HttpMessageNotReadableException", "无法读取信息");
        exceptionMap.put("MissingServletRequestPartException", "缺少部分请求参数");
        exceptionMap.put("MissingServletRequestParameterException", "缺少请求参数");
        exceptionMap.put("HttpRequestMethodNotSupportedException", "不支持该请求方式");
        exceptionMap.put("HttpMediaTypeNotSupportedException", "不支持该数据格式类型");
        exceptionMap.put("MethodArgumentNotValidException", "缺少部分请求参数");
    }
    @ExceptionHandler({BusinessException.class})
    public InvokeResult businessExceptionHandler(Exception e) {
        return new InvokeResult(InvokeResult.FAIL_CODE, e.getMessage());
    }

    @ExceptionHandler({PermissionException.class})
    public InvokeResult permissionExceptionHandler(Exception e) {
        return new InvokeResult(InvokeResult.NO_ALLOW, e.getMessage());
    }
    @ExceptionHandler({ExpiredException.class})
    public InvokeResult ExpiredExceptionHandler(Exception e) {
        return new InvokeResult(InvokeResult.LOGIN_EXPIRED, e.getMessage());
    }

    @ExceptionHandler({Exception.class})
    public InvokeResult runtimeExceptionHandler(Exception exception) {
        String key = exception.getClass().getSimpleName();

        if (exceptionMap.containsKey(key)) {

            log.error("http base exception, class = {}, description = {}, message = {}", key, exceptionMap.get(key), exception.getMessage());

            return new InvokeResult(InvokeResult.FAIL_CODE,exceptionMap.get(key));

        } else {

            log.error("internal server error", exception);

            // TODO 发邮件给开发人员，或者是发短信给开发人员

            return new InvokeResult(InvokeResult.FAIL_CODE, "系统繁忙，请稍后再试");

        }
    }

}
