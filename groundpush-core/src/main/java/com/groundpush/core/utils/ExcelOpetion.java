package com.groundpush.core.utils;

import java.util.List;

/**
 * .
 *
 * 工程名： groundpush-framework
 *
 *
 * @author luzq
 * @version 1.0
 * @createDate 2019-09-18 10:28
 * @since JDK  1.8
 */
@FunctionalInterface
public interface ExcelOpetion {
    /**
     * excel解析返回数据
     * @param sheetName 表格名称
     * @param countRow 总行数
     * @param resultCount 返回总数
     * @param result 返回结果
     */
    void excelRowResult(String sheetName,Integer countRow,Integer resultCount,List<Object[]> result);
}
