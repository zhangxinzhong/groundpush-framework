package com.groundpush.core.annotation;

import com.groundpush.core.enums.OperationType;

import java.lang.annotation.*;

/**
 * Created by IntelliJ IDEA
 *
 * @author weiwenjun
 * @date 2018/9/12
 */
//@OperationLogDetail(operationType = OperationType.TASK_ADD,type = 1)
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationLogDetail {

    /**
     * 操作类型(enum)
     */
    OperationType operationType() default OperationType.DEFAULTLOG;

    /**
     * 类型（0-APP，1-PC）
     */
    int type() default 0;
}