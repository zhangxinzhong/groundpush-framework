package com.groundpush.exception;


import org.springframework.security.core.AuthenticationException;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-08-30 下午6:20
 */
public class ValidateCodeException extends AuthenticationException {

    private String errorCode;

    private String errorMessage;

    public ValidateCodeException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.errorMessage = message;
    }


    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
