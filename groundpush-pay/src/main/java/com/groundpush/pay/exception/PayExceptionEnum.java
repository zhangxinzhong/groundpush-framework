package com.groundpush.pay.exception;

/**
 * @description: 支付异常
 * @author: zhangxinzhong
 * @date: 2019-09-24 下午7:48
 */
public enum PayExceptionEnum {

    PAY_EXCETPION("PAY0000", "提现异常！");


    private String errorCode;

    private String errorMessage;

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

    PayExceptionEnum(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
