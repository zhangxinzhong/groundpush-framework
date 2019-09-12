package com.groundpush.core.exception;

import org.springframework.validation.FieldError;

import java.util.List;

/**
 * @description:
 * @author: zhangxinzhong
 * @date: 2019-09-11 上午11:22
 */
public class GroundPushMethodArgumentNotValidException extends RuntimeException {

    private List<FieldError> fieldErrors;

    public GroundPushMethodArgumentNotValidException(List<FieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(List<FieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
}
