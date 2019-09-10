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
public class GroundPushAppExceptionHandler {

    /**
     * 处理业务异常
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    public JsonResp businessExcetion(BusinessException e) {
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
    public JsonResp systemExcetion(SystemException e) {
        log.error(e.getMessage(),e);
        return JsonResp.failure(e.getMessage());
    }

    /**
     * 处理支付异常
     *
     * @param e
     * @return
     */
//    @ResponseBody
//    @ExceptionHandler(PayException.class)
//    public JsonResp payExcetion(PayException e) {
//        log.error(e.getMessage(),e);
//        return JsonResp.failure(e.getMessage());
//    }

    /**
     * 处理异常
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public JsonResp excetion(Exception e) {
        log.error(e.getMessage(),e);
        return JsonResp.failure(ExceptionEnum.EXCEPTION.getErrorMessage());
    }
}
