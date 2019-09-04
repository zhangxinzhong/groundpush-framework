package com.groundpush.core.exception;

/**
 * @description: 业务异常
 * @author: zhangxinzhong
 * @date: 2019-08-27 上午11:21
 */
public class BusinessException extends RuntimeException {

    private String code;

    private String messsage;

    public BusinessException(String messsage) {
        this.messsage = messsage;
    }


    public BusinessException(String code, String messsage) {
        super(messsage);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMesssage() {
        return messsage;
    }

    public void setMesssage(String messsage) {
        this.messsage = messsage;
    }
}
