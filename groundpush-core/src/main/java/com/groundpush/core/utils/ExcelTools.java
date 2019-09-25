package com.groundpush.core.utils;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFColor;

import java.io.*;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * .
 * <p>
 * 工程名： groundpush-framework
 *
 * @author luzq
 * @version 1.0
 * @createDate 2019-09-18 10:23
 * @since JDK  1.8
 */
public final class ExcelTools {
    private ExcelTools() {
    }

    private volatile static ExcelTools excelUtils;
    private Workbook excelBook = null;
    /**
     * 当前表单
     */
    private Sheet curSheet = null;

    /**
     * 当前行
     */
    private Row curRow = null;

    /**
     * 当前行位置，默认第一行
     */
    private int curRowIndex = ExcelConstant.DEFAULT_COLL_INDEX;

    /**
     * 当前列位置
     */
    private int curColIndex = ExcelConstant.DEFAULT_ROW_INDEX;

    /**
     * 根据文件路径打开Excel文件
     *
     * @param filePath
     * @return
     * @throws IOException
     * @throws InvalidFormatException
     */
    public ExcelTools openExcel(String filePath) throws IOException, InvalidFormatException {
        if (filePath == null || filePath.length() <= 0) {
            throw new NullPointerException("参数" + filePath + "不能为空");
        }
        openExcel(new File(filePath));

        return this;
    }

    /**
     * 通过File对象打开Excel文件
     *
     * @param file
     * @return
     * @throws IOException
     * @throws InvalidFormatException
     */
    public ExcelTools openExcel(File file)
            throws IOException, InvalidFormatException {
        if (!file.exists()) {
            throw new FileNotFoundException("找不到文件:" + file.toString());
        }
        openExcel(new FileInputStream(file));

        return this;
    }

    /**
     * 通过InputStream打开Excel文件
     *
     * @param inputStream OLE2流 或 OOXML流
     * @return
     * @throws IllegalArgumentException
     * @throws InvalidFormatException
     * @throws IOException
     */
    public ExcelTools openExcel(InputStream inputStream)
            throws IllegalArgumentException, InvalidFormatException, IOException {
        try {
            excelBook = WorkbookFactory.create(inputStream);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("非法数据流格式,详情：" + e.getMessage());
        } catch (InvalidFormatException e) {
            throw new InvalidFormatException("Excel文件格式错误,详情：" + e.getMessage());
        } catch (IOException e) {
            throw new IOException("Excel文件读取失败,详情：" + e.getMessage());
        }

        return this;
    }

    /**
     * 获取Excel标题
     *
     * @return
     */
    public Object[] getExcelTitle() {
        Row titleRow = excelBook.getSheetAt(0).getRow(0);
        List<Object> tmpCache = new LinkedList<>();
        titleRow.forEach(cell -> {
            tmpCache.add(getCellValue(cell));
        });

        return tmpCache.toArray();
    }

    /**
     * 设置表格行数据返回
     *
     * @param resultCount  每次返回数量
     * @param excelOpetion 委托对象
     * @return
     */
    public void setRowResult(int resultCount, ExcelOpetion excelOpetion) {
        int sheetCount = excelBook.getNumberOfSheets();
        for (int i = 0; i < sheetCount; i++) {
            curSheet = excelBook.getSheetAt(i);
            int countRow = curSheet.getLastRowNum() + 1;
            int curResCount = ExcelConstant.DEFAULT_ROW_INDEX;//当前返回总数
            int pushCount = 0;
            List<Object[]> result = new LinkedList<>();
            for (curRowIndex = 0; curRowIndex < countRow; curRowIndex++) {
                curRow = curSheet.getRow(curRowIndex);
                result.add(getRowDate());
                ++curResCount;
                if ((curResCount + resultCount * pushCount) == countRow || curResCount == resultCount) {
                    excelOpetion.excelRowResult(curSheet.getSheetName(), countRow,
                            curResCount, result);
                    curResCount = ExcelConstant.DEFAULT_ROW_INDEX;
                    pushCount++;
                    result = new LinkedList<>();
                }
            }
        }
    }

    /**
     * 获取到列值
     *
     * @return
     */
    private Object[] getRowDate() {
        curRow = curSheet.getRow(curRowIndex);
        int countCell = curRow.getLastCellNum();
        List<Object> result = new ArrayList<>(countCell);
        curRow.forEach(cell -> {
            result.add(getCellValue(cell));
        });
        curColIndex = ExcelConstant.DEFAULT_COLL_INDEX;

        return result.toArray(new Object[countCell]);
    }

    /**
     * 获取单元格数据
     *
     * @param cell
     * @return
     */
    private Object getCellValue(Cell cell) {
        Object res = null;
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_BLANK:
                res = null;
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                res = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_ERROR:
                res = null;
                break;
            case Cell.CELL_TYPE_FORMULA:
                Workbook wb = cell.getSheet().getWorkbook();
                CreationHelper crateHelper = wb.getCreationHelper();
                FormulaEvaluator evaluator = crateHelper.createFormulaEvaluator();
                res = getCellValue(evaluator.evaluateInCell(cell));
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    res = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cell.getDateCellValue());
                } else {
                    res = new DecimalFormat("###################.###########").format(cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_STRING:
                res = cell.getRichStringCellValue().getString();
                break;
            default:
                res = null;
        }

        return res;
    }

    /**
     * 创建表格
     *
     * @return
     */
    public ExcelTools createNewSheet() {
        return createNewSheet("sheet1");
    }

    /**
     * 根据表格名称创建表格
     *
     * @param sheetName
     * @return
     */
    public ExcelTools createNewSheet(String sheetName) {
        if (excelBook == null) {
            excelBook = new SXSSFWorkbook();
        }
        curSheet = excelBook.createSheet(sheetName);
        curRowIndex = ExcelConstant.DEFAULT_ROW_INDEX;

        return this;
    }

    /**
     * 设置表格标题
     *
     * @param titles
     * @return
     */
    public ExcelTools setSheetRowTitle(String[] titles) {
        if (titles == null || titles.length == 0) {
            throw new NullPointerException("参数不能为空！");
        }

        createNewRow();
        for (int i = 0; i < titles.length; i++) {
            createNewColl(titles[i]);
        }

        return this;
    }

    /**
     * 创建表格行
     */
    private void createNewRow() {
        curRow = curSheet.createRow(curRowIndex);
        curColIndex = ExcelConstant.DEFAULT_COLL_INDEX;
        curRowIndex++;
    }

    /**
     * 创建新单元格
     *
     * @param value
     */
    private void createNewColl(String value) {
        Cell cell = curRow.createCell(curColIndex);
        defaultCellStyle(cell);
        cell.setCellValue(value);
        curColIndex++;
    }

    /**
     * 默认单元格样式
     */
    private void defaultCellStyle(Cell cell) {
        setCellStyle(cell, ExcelConstant.DEFAULT_FONT_NAME, ExcelConstant.ALIGN_CENTER, ExcelConstant.VERTICAL_CENTER,
                ExcelConstant.DEFAULT_FONT_SIZE, false, false, ExcelConstant.DEFAULT_FONT_COLOR);
    }

    /**
     * 设置字体样式
     *
     * @param cell         需要设置的单元格
     * @param fontName     字体名称
     * @param align        水平位置
     * @param vertical     垂直位置
     * @param fontHeight   字体大小（采用10\12\14等方式进行匹配，非点位位置）
     * @param isBoldweight 是否加粗
     * @param isItalic     是否斜体
     * @param fontColor    字体颜色
     */
    private void setCellStyle(Cell cell, String fontName, short align, short vertical, short fontHeight,
                              boolean isBoldweight, boolean isItalic, short fontColor) {
        short boldweight = isBoldweight ? ExcelConstant.BOLDWEIGHT_BOLD : ExcelConstant.BOLDWEIGHT_NORMAL;
        /** 设置字体 */
        Font font = excelBook.createFont();
        font.setFontName(fontName);
        font.setItalic(isItalic);
        font.setColor(fontColor);
        /** 是否加粗 */
        font.setBoldweight(boldweight);
        /** 采用非点位方式设置字体大小 */
        font.setFontHeight(fontHeight);

        /** 设置单元格样式 */
        CellStyle cellStyle = cell.getCellStyle();
        cellStyle.setAlignment(align);
        cellStyle.setVerticalAlignment(vertical);
        cellStyle.setFont(font);
    }


    /**
     * 根据颜色的rgb值创建颜色，如：白色（r:255,g:255,b:255）
     *
     * @param r
     * @param g
     * @param b
     * @return
     */
    private short setFontColor(int r, int g, int b) {
        return new XSSFColor(new java.awt.Color(r, g, b)).getIndexed();
    }

    /***
     * 根据颜色的rgb值创建颜色，如：白色（rgb:#FFFFFF）
     * @param rgb
     * @return
     */
    private short setFontColor(int rgb) {
        return new XSSFColor(new java.awt.Color(rgb)).getIndexed();
    }

    /**
     * 采用双检锁/双重效验锁,保证线程安全
     *
     * @return
     */
    public static ExcelTools getInstance() {
        if (excelUtils == null) {
            synchronized (ExcelUtils.class) {
                if (excelUtils == null) {
                    excelUtils = new ExcelTools();
                }
            }
        }

        return excelUtils;
    }

    /**
     * 根据文件路径保存Excel文件
     *
     * @param filePath
     */
    public void save(String filePath) throws IOException {
        if (filePath == null || filePath.length() <= 0) {
            throw new NullPointerException("文件路径" + filePath + "不能为空");
        }

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(filePath);
            excelBook.write(out);
            out.close();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("文件输出异常:" + e.getMessage());
        } catch (IOException e) {
            throw new IOException("文件输出异常:" + e.getMessage());
        } finally {
            try {
                if (out != null) {
                    out.close();
                    out = null;
                }
            } catch (IOException e) {
                throw new IOException("文件输出异常:" + e.getMessage());
            }
        }
    }

    /**
     *
     */
    public class ExcelConstant {
        /**
         * 水平居中
         */
        final static short ALIGN_CENTER = CellStyle.ALIGN_CENTER;
        /**
         * 水平靠右
         */
        final static short ALIGN_RIGHT = CellStyle.ALIGN_RIGHT;
        /**
         * 水平靠左
         */
        final static short ALIGN_LEFT = CellStyle.ALIGN_LEFT;
        /**
         * 垂直居中
         */
        final static short VERTICAL_CENTER = CellStyle.VERTICAL_CENTER;
        /**
         * 垂直向上
         */
        final static short VERTICAL_TOP = CellStyle.VERTICAL_TOP;
        /**
         * 垂直向下
         */
        final static short VERTICAL_BOTTOM = CellStyle.VERTICAL_BOTTOM;
        /**
         * 加粗
         */
        final static short BOLDWEIGHT_BOLD = Font.BOLDWEIGHT_BOLD;
        /**
         * 不加粗
         */
        final static short BOLDWEIGHT_NORMAL = Font.BOLDWEIGHT_NORMAL;

        /**
         * 默认字体大小 12
         */
        final static short DEFAULT_FONT_SIZE = 0x0C;

        /**
         * 默认字体
         */
        final static String DEFAULT_FONT_NAME = "微软雅黑";

        /**
         * 默认字体颜色
         */
        final static short DEFAULT_FONT_COLOR = HSSFColor.BLACK.index;

        /**
         * 默认初始列数位置
         */
        final static short DEFAULT_COLL_INDEX = 0x00;

        /**
         * 默认初始行数位置
         */
        final static short DEFAULT_ROW_INDEX = 0x00;
    }

    public static void main(String[] args) throws IOException, InvalidFormatException {
        ExcelTools excelTools = ExcelTools.getInstance();
        excelTools.openExcel("/home/lzq/win7/tb_gd_stock_20190818.xls");
        excelTools.setRowResult(380, (sheetName, countRow, resultCount, result) -> {
            result.forEach(res -> {
                System.out.println(res[0]);
            });
            String resultShow = "result:[sheetName:{0},count:{1},resCount:{2},result:{3}]";
            System.out.println("=======================================");
            System.out.println(MessageFormat.format(resultShow, sheetName, countRow, resultCount, result.toString()));
        });
    }
}
