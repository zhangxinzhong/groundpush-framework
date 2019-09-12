package com.groundpush.exception;

import com.groundpush.core.common.JsonResp;
import com.groundpush.core.exception.BusinessException;
import com.groundpush.core.exception.ExceptionEnum;
import com.groundpush.core.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description: 公共异常处理
 * @author: zhangxinzhong
 * @date: 2019-08-28 下午5:37
 */
@Slf4j
@ControllerAdvice
public class GroundPushWebExceptionHandler {

    /**
     * 处理业务异常
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    public JsonResp businessException(BusinessException e) {
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
        return JsonResp.failure(e.getMessage());
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
        return JsonResp.failure(ExceptionEnum.EXCEPTION.getErrorMessage());
    }
}
