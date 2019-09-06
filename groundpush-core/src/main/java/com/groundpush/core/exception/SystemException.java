package com.groundpush.core.exception;

/**
 * @description: 系统异常
 * @author: zhangxinzhong
 * @date: 2019-08-28 下午5:41
 */
public class SystemException extends RuntimeException {
    private String code;

    private String message;

    public SystemException(String message) {
        this.message = message;
    }

    public SystemException(String code, String message) {
        super(message);
        this.code = code;
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
