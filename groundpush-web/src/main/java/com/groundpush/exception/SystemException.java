package com.groundpush.exception;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-08-28 下午5:41
 */
public class SystemException extends RuntimeException {
    private String code;


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


}
