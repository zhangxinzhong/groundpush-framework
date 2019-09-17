package com.groundpush.core.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @description: jsonResponse
 * @author: zhangxinzhong
 * @date: 2019-08-20 下午7:33
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonResp implements Serializable {
    public static final String SUCCESS = "200";
    public static final String FAILURE = "500";
    public static final String URL_NOT_FOUND = "404";
    public static final String MESSAGE_OK = "ok";
    public static final String MESSAGE_ERROR = "error";


    private String code;

    private String message;

    private Object data;

    private JsonResp() {
    }

    private JsonResp(String code, Object data) {
        this.code = code;
        this.data = data;
    }

    private JsonResp(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static JsonResp success() {
        return new JsonResp(SUCCESS, MESSAGE_OK, new HashMap());
    }

    public static JsonResp success(Object data) {
        return new JsonResp(SUCCESS, MESSAGE_OK, data);
    }

    public static JsonResp success(String message, Object data) {
        return new JsonResp(SUCCESS, message, data);
    }

    public static JsonResp failure() {
        return new JsonResp(FAILURE, MESSAGE_ERROR, new HashMap());
    }

    public static JsonResp failure(String code, String message, Object data) {
        return new JsonResp(code, message, data);
    }

    public static JsonResp failure(String code, String message) {
        return new JsonResp(code, message, new HashMap());
    }

    public static JsonResp failure(Object data) {
        return new JsonResp(FAILURE, data);
    }

    @JsonView(View.class)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @JsonView(View.class)
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @JsonView(View.class)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
