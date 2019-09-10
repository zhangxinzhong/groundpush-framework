package com.groundpush.pay.exception;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-09-07 下午1:39
 */
public class PayException extends RuntimeException {
    private String code;

    private String message;

    public PayException(String code) {
        this.code = code;
    }

    public PayException(String code, String message) {
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