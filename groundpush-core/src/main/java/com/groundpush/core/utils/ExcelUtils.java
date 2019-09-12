package com.groundpush.core.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public final class ExcelUtils {
    /** 初始列 */
    private int initColIndex;
    /** 当前行 */
    private int curRowIndex = 2;
    /** 当前列 */
    private int curColIndex;
    /** 表格 */
    private Sheet sheet = null;
    /** 当前行 */
    private Row curRow = null;
    /** 工作薄 */
    private Workbook workbook = null;
    /** 使用单例 */
    private static ExcelUtils excelHelper = new ExcelUtils();

    /**
     * 解析Excel
     *
     * @param filePath 文件路径
     * @return Workbook
     */
    public ExcelUtils parseExcel(String filePath) throws Exception {
        try {
            workbook = WorkbookFactory.create(new File(filePath));
        }
        catch (InvalidFormatException e) {
            throw new InvalidFormatException("Excel文件格式错误 : " + e.getMessage());
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException("找不到文件 : " + e.getMessage());
        }
        catch (IOException e) {
            throw new IOException("Excel文件读取失败 : " + e.getMessage());
        }

        // 返回数据
        return this;
    }

    public ExcelUtils parseExcel(InputStream inputStream) throws Exception{
        try {
            workbook=WorkbookFactory.create(inputStream);
        }
        catch (InvalidFormatException e) {
            throw new InvalidFormatException("Excel文件格式错误 : " + e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw new FileNotFoundException("输入的流格式不正确 : " + e.getMessage());
        }
        catch (IOException e) {
            throw new IOException("Excel文件读取失败 : " + e.getMessage());
        }

        // 返回数据
        return this;
    }

    /**
     * 创建工作薄
     *
     * @return Workbook
     */
    public ExcelUtils createWorkbook() throws Exception {
        // 创建工作薄
        workbook = new SXSSFWorkbook();

        // 创建sheet页
        workbook.createSheet();

        // 返回数据
        return this;
    }

    /**
     * 添加表头
     *
     * @param titles 表头属性列表
     */
    public void createTitle(String[] titles) {
        // 检查有效性
        if (titles == null || titles.length == 0) {
            return;
        }

        // 新建行
        creatNewRow();

        // 添加表头
        for (String title : titles) {
            createNewCol(title);
        }
    }

    /**
     * 获取标题
     * @return
     */
    public String[] getTitle(){
        sheet=workbook.getSheetAt(0);
        Row currRow=sheet.getRow(0);//获取第一个行，默认第一行为标题
        List<String> result=new ArrayList<>(currRow.getLastCellNum());
        currRow.forEach(cell -> result.add(cell.getStringCellValue()));

        return result.toArray(new String[currRow.getLastCellNum()]);
    }

    /**
     * 获取json格式标题
     * @return
     */
    public String[] getJsonTitle(){
        sheet=workbook.getSheetAt(0);
        Row currRow=sheet.getRow(0);//获取第一个行，默认第一行为标题
        List<String> result=new ArrayList<>(currRow.getLastCellNum());
        currRow.forEach(cell -> result.add("{\"key\":"+cell.getColumnIndex()+",\"value\":\""+cell.getStringCellValue()+"\"}"));

        return result.toArray(new String[currRow.getLastCellNum()]);
    }

    /**
     * 保存Excel
     *
     * @param filePath 路径
     */
    public void save(String filePath) throws Exception {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filePath);
            workbook.write(out);
            out.close();
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException("文件输出异常:" + e.getMessage());
        }
        catch (IOException e) {
            throw new IOException("文件输出异常:" + e.getMessage());
        }
        finally {
            try {
                if (out != null) {
                    out.close();
                    out = null;
                }
            }
            catch (IOException e) {
                throw new IOException("文件输出异常:" + e.getMessage());
            }
        }
    }

    /**
     * 转换Cell值
     *
     * @param cell
     * @return 返回String类型值
     */
    public String getCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        String ret = null;
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_BLANK:
                ret = null;
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                ret = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_ERROR:
                ret = null;
                break;
            case Cell.CELL_TYPE_FORMULA:
                Workbook wb = cell.getSheet().getWorkbook();
                CreationHelper crateHelper = wb.getCreationHelper();
                FormulaEvaluator evaluator = crateHelper.createFormulaEvaluator();
                ret = getCellValue(evaluator.evaluateInCell(cell));
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    ret = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cell.getDateCellValue());
                }
                else {
                    ret = new DecimalFormat("###################.###########").format(cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_STRING:
                ret = cell.getRichStringCellValue().getString();
                break;
            default:
                ret = null;
        }
        return ret == null ? null : ret.trim();
    }

    /**
     * 创建新的一行
     */
    public void creatNewRow() {
        sheet = workbook.getSheetAt(0);
        curRow = sheet.createRow(curRowIndex);
        curRowIndex++;
        curColIndex = initColIndex;
    }

    /**
     * 创建 String型数据
     *
     * @param value 数据值
     */
    public void createNewCol(String value) {
        Cell cell = curRow.createCell(curColIndex);
        setStyle(cell);
        cell.setCellValue(value);
        curColIndex++;
    }

    /**
     * 创建 String型数据
     *
     * @param value
     * @param style
     */
    public void createNewColStyle(String value, CellStyle style) {
        Cell cell = curRow.createCell(curColIndex);
        if (style != null) {
            cell.setCellStyle(style);
        }
        cell.setCellValue(value);
        curColIndex++;
    }

    /**
     * 创建 double型数据
     *
     * @param value 数据值
     */
    public void createNewCol(double value) {
        Cell cell = curRow.createCell(curColIndex);
        setStyle(cell);
        cell.setCellValue(value);
        curColIndex++;
    }

    /**
     * 创建 boolean型数据
     *
     * @param value 数据值
     */
    public void createNewCol(boolean value) {
        Cell cell = curRow.createCell(curColIndex);
        setStyle(cell);
        cell.setCellValue(value);
        curColIndex++;
    }

    /**
     * 创建 Date型数据
     *
     * @param value 数据值
     */
    public void createNewCol(Date value) {
        Cell cell = curRow.createCell(curColIndex);
        setStyle(cell);
        if (value != null) {
            cell.setCellValue(value);
        }
        curColIndex++;
    }

    /**
     * 创建 Long型数据
     *
     * @param value 数据值
     */
    public void createNewCol(RichTextString value) {
        Cell cell = curRow.createCell(curColIndex);
        setStyle(cell);
        if (value != null) {
            cell.setCellValue(value);
        }
        curColIndex++;
    }

    /**
     * 创建 Long型数据
     *
     * @param value 数据值
     */
    public void createNewCol(Long value) {
        Cell cell = curRow.createCell(curColIndex);
        setStyle(cell);
        if (value != null) {
            cell.setCellValue(value);
        }
        curColIndex++;
    }

    /**
     * 创建 空数据
     *
     */
    public void createNewCol() {
        curColIndex++;
    }

    /**
     * 获取workbook
     *
     * @return workbook
     */
    public Workbook getWorkbook() {
        return workbook;
    }

    /**
     * 使用单例模式
     */
    private ExcelUtils() {
    }

    public static ExcelUtils getInstance() {
        return excelHelper;
    }

    /**
     * 设置样式
     *
     * @param cell Cell
     */
    private void setStyle(Cell cell) {
        // 初始化
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();

        // 居中
        style.setAlignment(CellStyle.ALIGN_CENTER);
        //垂直
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        font.setFontName("微软雅黑");
        font.setFontHeightInPoints((short) 10);
        style.setFont(font);

        cell.setCellStyle(style);
    }

    /**
     * 获取初始列号
     *
     * @return 初始列号
     */
    public int getInitColIndex() {
        return initColIndex;
    }

    /**
     * 设置初始列号
     *
     * @param initColIndex 初始列号
     */
    public void setInitColIndex(int initColIndex) {
        this.initColIndex = initColIndex;
    }

    /**
     * 获取当前行号
     *
     * @return 当前行号
     */
    public int getCurRowIndex() {
        return curRowIndex;
    }

    /**
     * 设置当前行号
     *
     * @param curRowIndex 当前行号
     */
    public void setCurRowIndex(int curRowIndex) {
        this.curRowIndex = curRowIndex;
    }

    /**
     * 设置列宽度自适应
     *
     * @param columnIndex 列序号(null表示所有列)
     */
    public void setAutoSizeColumn(Integer columnIndex) {
        if (columnIndex != null) {
            sheet.autoSizeColumn(columnIndex);
        }
        else {
            for (int i = 0; i < curColIndex; i++) {
                sheet.autoSizeColumn(i);
            }
        }
    }

    /**
     * 单元格合并
     *
     * @param firstRow 起始行号
     * @param lastRow  终止行号
     * @param firstCol 起始列号
     * @param lastCol  终止列号
     */
    public void addMergedRegion(int firstRow, int lastRow, int firstCol, int lastCol){
        sheet.addMergedRegion(new CellRangeAddress(firstRow,lastRow,firstCol,lastCol));
    }
}
