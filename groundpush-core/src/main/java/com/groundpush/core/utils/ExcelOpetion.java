package com.groundpush.core.utils;

/**
 * .
 * <p>
 * -----------------------------------------------------------------------------
 * <p>
 * 工程名： groundpush-framework
 * <p>
 * 授 权： (C) Copyright Unimedia Corporation 2019-2050
 * <p>
 * 公 司： 北京玖众传媒股份有限公司
 * <p>
 * -----------------------------------------------------------------------------
 * 注 意： 本内容仅限于北京玖众传媒股份有限公司公司内部使用，禁止转发
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
    void excelRowResult(String sheetName,Integer countRow,Integer resultCount,Object[] result);
}
