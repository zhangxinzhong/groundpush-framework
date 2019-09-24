package com.groundpush.security.core.exception;


import org.springframework.security.core.AuthenticationException;

/**
 * @description: 自定义验证 异常
 * @author: zhangxinzhong
 * @date: 2019-08-30 下午6:20
 */
public class ValidateCodeException extends AuthenticationException {

    private String code;
    private String message;

    public ValidateCodeException(String code, String message) {
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
