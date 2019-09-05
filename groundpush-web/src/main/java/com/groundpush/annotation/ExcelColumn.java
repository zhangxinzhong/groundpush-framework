package com.groundpush.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 中信银行信息技术部 技术平台开发部 @2015
 * <p/>
 * 作者: xuqingfeng
 * 创建时间: 2016-02-02 16:35
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelColumn {

    //对应的excel列名
    public String name();

    //对应的excel列宽度，用于excel导出
    public int width() default 20;

    //是否可以导出
    public boolean isExport() default true;
}
