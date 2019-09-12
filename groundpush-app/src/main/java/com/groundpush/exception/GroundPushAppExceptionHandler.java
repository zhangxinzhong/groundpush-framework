package com.groundpush.exception;

import com.groundpush.core.common.JsonResp;
import com.groundpush.core.exception.BusinessException;
import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.core.exception.GroundPushMethodArgumentNotValidException;
import com.groundpush.core.exception.SystemException;
import com.groundpush.pay.exception.PayException;
import com.groundpush.security.core.exception.ValidateCodeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @description: 公共异常处理
 * @author: zhangxinzhong
 * @date: 2019-08-28 下午5:37
 */
@Slf4j
@ControllerAdvice
public class GroundPushAppExceptionHandler {

    /**
     * 处理业务异常
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    public JsonResp businessException(BusinessException e) {
        log.error(e.getMessage(),e);
        return JsonResp.failure(e.getCode(), e.getMessage());
    }

    /**
     * 处理系统异常
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(SystemException.class)
    public JsonResp systemException(SystemException e) {
        log.error(e.getMessage(),e);
        return JsonResp.failure(e.getMessage());
    }

    /**
     * 处理验证异常
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(ValidateCodeException.class)
    public JsonResp validateCodeException(ValidateCodeException e) {
        log.error(e.getMessage(),e);
        return JsonResp.failure(e.getMessage());
    }

    /**
     * 处理支付异常
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(PayException.class)
    public JsonResp payException(PayException e) {
        log.error(e.getMessage(),e);
        return JsonResp.failure(e.getMessage());
    }

    /**
     * 处理方法验证异常
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(GroundPushMethodArgumentNotValidException.class)
    public JsonResp methodArgumentNotValidException(GroundPushMethodArgumentNotValidException e) {
        StringBuilder sb = new StringBuilder();
        for (FieldError fieldError : e.getFieldErrors()) {
            sb.append(" [").append(fieldError.getDefaultMessage()).append("]");
        }
        return JsonResp.failure(sb);
    }

    /**
     * 处理异常
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public JsonResp exception(Exception e) {
        log.error(e.getMessage(),e);
        return JsonResp.failure(ExceptionEnum.EXCEPTION.getErrorMessage());
    }


}
