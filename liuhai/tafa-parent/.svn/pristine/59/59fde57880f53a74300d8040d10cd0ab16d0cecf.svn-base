package com.tydic.traffic.tafa.utils.office;

import com.tydic.traffic.tafa.utils.string.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * ClassName:ExcelToolsUtil
 */
public final class ExcelTools {

	private ExcelTools() {
	}

	/**
	 * 生成excel参数检查
	 * 
	 * @param sheetTitle
	 *            sheet标题
	 * @param headers
	 *            列标题
	 * @param exportFields
	 *            排版属性数组
	 * @param dataset
	 *            导出数据集
	 * @param out
	 *            导出流对象
	 */
	public static void checkGeneralExportParams(String sheetTitle, String[] headers, String[] exportFields,
                                                Collection<? extends Object> dataset, OutputStream out) {

		// 检查常规参数
		checkNormalParmas(sheetTitle, headers, exportFields, dataset);

		if (out == null) {

			throw new RuntimeException("参数[out]导出Excel表格的文件流对象为null");
		}

	}

	/**
	 * excel添加sheet参数检查
	 * 
	 * @param sheetTitle
	 * @param headers
	 * @param exportFields
	 * @param dataset
	 * @param filePath
	 */
	public static void checkAddSheetParams(String sheetTitle, String[] headers, String[] exportFields,
                                           Collection<? extends Object> dataset, String dateFormat, String filePath) {

		// 常规参数
		checkNormalParmas(sheetTitle, headers, exportFields, dataset);

		if (StringUtils.isEmpty(dateFormat)) {

			throw new RuntimeException("参数[dateFormat]日期格式不能为空");
		}

		if (StringUtils.isEmpty(filePath)) {
			throw new RuntimeException("参数[filePath]导出Excel表格文件路径不能为空");
		}
	}

	/**
	 * 检查常规参数
	 * 
	 * @param sheetTitle
	 * @param headers
	 * @param exportFields
	 * @param dataset
	 */
	private static void checkNormalParmas(String sheetTitle, String[] headers, String[] exportFields,
                                          Collection<? extends Object> dataset) {

		if (StringUtils.isEmpty(sheetTitle)) {

			throw new RuntimeException("参数[sheetTitle]导出Excel表格sheet标题不能为空");
		}

		if (headers == null || headers.length == 0) {

			throw new RuntimeException("参数[headers]导出Excel表格列标题不能为空");
		}

		if (exportFields == null || exportFields.length == 0) {

			throw new RuntimeException("参数[exportFields]数据对象属性名称数组不能为空");
		}

		if (dataset == null || dataset.size() == 0) {

			throw new RuntimeException("参数[dataset]导出Excel表格数据不能为空");
		}

		if (headers.length != exportFields.length) {

			throw new RuntimeException("参数[headers,exportFields]定义的Excel列标题和排版的属性不一致");
		}

		Field[] fields = dataset.iterator().next().getClass().getDeclaredFields();

		if (fields == null || fields.length == 0) {

			throw new RuntimeException("数据对象中没有可以导出的属性");
		}

		List<String> list = new ArrayList<String>();

		for (Field field : fields) {
			list.add(field.getName());
		}

		if (!list.containsAll(Arrays.asList(exportFields))) {

			throw new RuntimeException("参数[exportFields]定义的属性在数据对象中不存在");
		}

	}

	/**
	 * 获取工作表的行数
	 * 
	 * @param sheet
	 *            HSSFSheet表对象
	 * @return 表行数
	 */
	public static int getLastRowNum(HSSFSheet sheet) {
		int lastRowNum = sheet.getLastRowNum();
		if (lastRowNum == 0) {
			lastRowNum = sheet.getPhysicalNumberOfRows() - 1;
		}
		return lastRowNum;
	}

	/**
	 * 获取该行第一个单元格的下标
	 * 
	 * @param row
	 *            行对象
	 * @return 第一个单元格下标，从0开始
	 */
	public static int getFirstCellNum(HSSFRow row) {
		return row.getFirstCellNum();
	}

	/**
	 * 获取该行最后一个单元格的下标
	 * 
	 * @param row
	 *            行对象
	 * @return 最后一个单元格下标，从0开始
	 */
	public static int getLastCellNum(HSSFRow row) {
		return row.getLastCellNum();
	}

	/**
	 * 获取POI的行对象
	 * 
	 * @param sheet
	 *            表对象
	 * @param row
	 *            行号，从0开始
	 * @return
	 */
	public static HSSFRow getHSSFRow(HSSFSheet sheet, int row) {
		if (row < 0) {
			row = 0;
		}
		HSSFRow r = sheet.getRow(row);
		if (r == null) {
			r = sheet.createRow(row);
		}
		return r;
	}

	/**
	 * 获取单元格对象
	 * 
	 * @param sheet
	 *            表对象
	 * @param row
	 *            行，从0开始
	 * @param col
	 *            列，从0开始
	 * @return row行col列的单元格对象
	 */
	public static HSSFCell getHSSFCell(HSSFSheet sheet, int row, int col) {
		HSSFRow r = getHSSFRow(sheet, row);
		return getHSSFCell(r, col);
	}

	/**
	 * 获取单元格对象
	 * 
	 * @param row
	 *            行，从0开始
	 * @param col
	 *            列，从0开始
	 * @return 指定行对象上第col行的单元格
	 */
	public static HSSFCell getHSSFCell(HSSFRow row, int col) {
		if (col < 0) {
			col = 0;
		}
		HSSFCell c = row.getCell(col);
		c = c == null ? row.createCell(col) : c;
		return c;
	}

	/**
	 * 获取工作表对象
	 * 
	 * @param workbook
	 *            工作簿对象
	 * @param index
	 *            表下标，从0开始
	 * @return
	 */
	public static HSSFSheet getHSSFSheet(HSSFWorkbook workbook, int index) {
		if (index < 0) {
			index = 0;
		}
		if (index > workbook.getNumberOfSheets() - 1) {
			workbook.createSheet();
			return workbook.getSheetAt(workbook.getNumberOfSheets() - 1);
		}
		else {
			return workbook.getSheetAt(index);
		}
	}

}