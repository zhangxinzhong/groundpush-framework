package com.groundpush.core.exception;

/**
 * @description: 业务异常
 * @author: zhangxinzhong
 * @date: 2019-08-27 上午11:21
 */
public class BusinessException extends RuntimeException {

    private String code;

    private String message;

    public BusinessException(String message) {
        this.message = message;
    }


    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
