package com.groundpush.security.core.exception;


import org.springframework.security.core.AuthenticationException;

/**
 * @description: 自定义验证 异常
 * @author: zhangxinzhong
 * @date: 2019-08-30 下午6:20
 */
public class ValidateCodeException extends AuthenticationException {

    private String errorMessage;

    public ValidateCodeException( String message) {
        super(message);
        this.errorMessage = message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
