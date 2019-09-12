package com.groundpush.pay.exception;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-09-07 下午1:39
 */
public class PayException extends RuntimeException {

    private String message;

    public PayException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
