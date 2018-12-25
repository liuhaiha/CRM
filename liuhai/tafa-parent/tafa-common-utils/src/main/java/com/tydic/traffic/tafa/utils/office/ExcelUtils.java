package com.tydic.traffic.tafa.utils.office;

import com.tydic.traffic.tafa.utils.string.StringUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * ClassName:ExcelTools
 */
public class ExcelUtils {

	private static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

	/** 默认日期格式 */
	private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);

	/** 是否为数字正则表达式 */
	public static final String REGEX_NUMBER = "^\\d+(\\.\\d+)?$";

	/** 创建Excel元素样式处理类 */
	private ExcelStyleCreate excelStyleCreate;

	/**
	 * 无参构造方法
	 */
	public ExcelUtils() {
		this.excelStyleCreate = getExcelStyleCreate();
	}

	/**
	 * 构造方法
	 * 
	 * @param excelStyleCreate
	 *            Excel元素样式创建类
	 */
	public ExcelUtils(ExcelStyleCreate excelStyleCreate) {
		this.excelStyleCreate = excelStyleCreate;
	}

	/**
	 * 
	 * parse:(解析excel文件流，支持2003、2010). <br/>
	 * @param SheetAtNo
	 * 			- 指定读取第几张sheet，默认下标从0开始
	 * @param firstDateLine
	 * 			- 真实数据的起始行号		
	 * 				eg:若首行为标题行，则数据起始行号为2；若无标题行，则数据起始行号为1
	 * @return List<String[]>
	 * @since 1.0
	 */
	public static List<String[]> parse(InputStream is, int SheetAtNo, int firstDateLine) {
		List<String[]> dist = new ArrayList<>();
		Workbook workbook = null;  
		try{
			workbook = WorkbookFactory.create(is);			
			int sheetNumber = workbook.getNumberOfSheets();
			if(SheetAtNo >= sheetNumber){
				logger.warn("指定的sheetNumber不存在。");
				throw new IllegalArgumentException("指定的sheetNumber不存在，请重新输入。");
			}
		}catch(Exception ex){
			logger.error(ex.getMessage());
		}	
		
		Sheet sheet = workbook.getSheetAt(SheetAtNo);
		
		// sheet中真实数据起始行号
		int indexOfFieldName = firstDateLine - 1;
		// 获取sheet总行数
		int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
		
		String[] content = null;
		// 遍历每行数据
		for (int i = indexOfFieldName; i < physicalNumberOfRows; i++) {
			Row row = sheet.getRow(i);
			
			if(null != row){
				// 获取单元格列数
				int cells = row.getPhysicalNumberOfCells();	
				content = new String[cells];
				
				// 遍历每列数据
				for (int m = 0; m < cells; m++) {
					Cell cell = row.getCell(m);	
					if(null != cell){
						content[m] = match(cell);
					}
				}				

				dist.add(content);
			}			
		}
		return dist;
	}	
	
	
	/**
	 * 
	 * parse:(解析excel文件流，支持2003、2010). <br/>
	 * @param is
	 * 			- excel文件输入流
	 * @param SheetAtNo
	 * 			- 指定读取第几张sheet，默认下标从0开始
	 * @param clz
	 * 			- 指定的javaBean
	 * @param impFields
	 * 			- 导入的属性名String[]
	 * @param firstDateLine
	 * 			- 真实数据的起始行号		
	 * 				eg:若首行为标题行，则数据起始行号为2；若无标题行，则数据起始行号为1
	 * @return List<Object>
	 * @since 1.0
	 */
	public static List<Object> parse(InputStream is, int SheetAtNo, Class clz, String[] impFields, int firstDateLine) {
		List<Object> dist = new ArrayList<Object>();
		Workbook workbook = null;  
		try{
			workbook = WorkbookFactory.create(is);			
			int sheetNumber = workbook.getNumberOfSheets();
			if(SheetAtNo >= sheetNumber){
				logger.warn("指定的sheetNumber不存在。");
				throw new IllegalArgumentException("指定的sheetNumber不存在，请重新输入。");
			}
		}catch(Exception ex){
			logger.error(ex.getMessage());
		}	
		
		Sheet sheet = workbook.getSheetAt(SheetAtNo);
		
		// sheet中真实数据起始行号
		int indexOfFieldName = firstDateLine - 1;
		// 获取sheet总行数
		int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
		
		// 遍历每行数据
		for (int i = indexOfFieldName; i < physicalNumberOfRows; i++) {
			Row row = sheet.getRow(i);
			
			if(null != row){
				// 获取单元格列数
				int cells = row.getPhysicalNumberOfCells();
				
				Object obj = ReflectUtils.getNewInstance(clz);
				Object[] objValues = new Object[impFields.length];
				
				// 遍历每列数据
				for (int m = 0; m < cells; m++) {				
					Cell cell = row.getCell(m);	
					if(null != cell){
						Field field = ReflectUtils.getDeclaredField(obj, impFields[m]);
						objValues[m] = ReflectUtils.matchType(field, match(cell));
					}
				}				
				
				ReflectUtils.invokeSetterMethod(obj, impFields, objValues);
				dist.add(obj);
			}			
		}
		return dist;
	}	
	

	/**
	 * 根据单元格格式返回正确的结果
	 * 
	 * @param cell
	 *            - 单元格
	 */
	private static String match(Cell cell) {
		String value = "";
		if (cell != null)
			switch (cell.getCellType()) {
				case Cell.CELL_TYPE_BLANK:
					value = "";
					break;
				case Cell.CELL_TYPE_ERROR:
					value = "";
					break;
				case Cell.CELL_TYPE_STRING:
					value = cell.getRichStringCellValue().toString();
					break;
				case Cell.CELL_TYPE_FORMULA:
					value = cell.getCellFormula().toString();
					break;
				case Cell.CELL_TYPE_BOOLEAN:
					value = Boolean.toString(cell.getBooleanCellValue());
					break;
				case Cell.CELL_TYPE_NUMERIC:
					if (DateUtil.isCellDateFormatted(cell)) {// 是否为日期型
						value = sdf.format(cell.getDateCellValue());
					}
					else {// 是否为数值型
						double d = cell.getNumericCellValue();
						if (d - (int) d < Double.MIN_VALUE) { // 是否为int型
							value = Integer.toString((int) d);
						}
						else { // 是否为double型
							value = Double.toString(cell.getNumericCellValue());
						}
					}
					break;
			}
		return value;
	}	

	/**
	 * Excel导出，利用JAVA反射机制，导出格式为Excel 2003 除了第一行列标题加粗外无任何样式
	 * 
	 * @param sheetTitle
	 *            sheet标题
	 * @param headers
	 *            Excel表格属性列标题数组，标题列将按此顺序排版
	 * @param exportFields
	 *            数据对象属性名称数组，导出数据列将按此顺序排版，与headers对应 ，不在数组中的属性将不会被导出
	 * @param dataset
	 *            需要导出的数据集合,集合中的对象必需是标准的javabean，有get和set方法。支持基本数据类型及String,
	 *            Date,byte[](图片数据)
	 * @param out
	 *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
	 */
	public void export(String sheetTitle, String[] headers, String[] exportFields,
                       Collection<? extends Object> dataset, OutputStream out) {

		generalExport(sheetTitle, headers, exportFields, dataset, out, DEFAULT_DATE_FORMAT, -1);
	}

	/**
	 * Excel导出，利用JAVA反射机制，导出格式为Excel 2003 除了第一行列标题加粗外无任何样式
	 * 
	 * @param sheetTitle
	 *            sheet标题
	 * @param headers
	 *            Excel表格属性列标题数组，标题列将按此顺序排版
	 * @param exportFields
	 *            数据对象属性名称数组，导出数据列将按此顺序排版，与headers对应 ，不在数组中的属性将不会被导出
	 * @param dataset
	 *            需要导出的数据集合,集合中的对象必需是标准的javabean，有get和set方法。支持基本数据类型及String,
	 *            Date,byte[](图片数据)
	 * @param out
	 *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
	 * @param dateFormat
	 *            如果有时间数据，设定输出格式。如果不设置，默认使用数据集合中的数据
	 */
	public void export(String sheetTitle, String[] headers, String[] exportFields,
                       Collection<? extends Object> dataset, OutputStream out, String dateFormat) {

		/*if (StringUtils.isEmpty(dateFormat)) {
			throw new RuntimeException("参数[dateFormat]日期格式不能为空");
		}*/

		generalExport(sheetTitle, headers, exportFields, dataset, out, dateFormat, -1);
	}

	/**
	 * Excel导出，利用JAVA反射机制，导出格式为Excel 2003 除了第一行列标题加粗外无任何样式
	 * 
	 * @param sheetTitle
	 *            sheet标题
	 * @param headers
	 *            Excel表格属性列标题数组，标题列将按此顺序排版
	 * @param exportFields
	 *            数据对象属性名称数组，导出数据列将按此顺序排版，与headers对应 ，不在数组中的属性将不会被导出
	 * @param dataset
	 *            需要导出的数据集合,集合中的对象必需是标准的javabean，有get和set方法。支持基本数据类型及String,
	 *            Date,byte[](图片数据)
	 * @param out
	 *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
	 * @param sheetMaxRow
	 *            每个sheet数据的最多行数，超出时将分sheet，不包含标题
	 */
	public void export(String sheetTitle, String[] headers, String[] exportFields,
                       Collection<? extends Object> dataset, OutputStream out, int sheetMaxRow) {

		if (sheetMaxRow <= 0) {

			throw new RuntimeException("参数[sheetMaxRow]sheet的最大行数不正确");
		}
		generalExport(sheetTitle, headers, exportFields, dataset, out, DEFAULT_DATE_FORMAT, sheetMaxRow);

	}

	/**
	 * 通用Excel导出方法，利用JAVA反射机制，导出格式为Excel 2003 除了第一行列标题加粗外无任何样式
	 * 
	 * @param sheetTitle
	 *            sheet标题
	 * @param headers
	 *            Excel表格属性列标题数组，标题列将按此顺序排版
	 * @param exportFields
	 *            数据对象属性名称数组，导出数据列将按此顺序排版，与headers对应 ，不在数组中的属性将不会被导出
	 * @param dataset
	 *            需要导出的数据集合,集合中的对象必需是标准的javabean，有get和set方法。支持基本数据类型及String,
	 *            Date,byte[](图片数据)
	 * @param out
	 *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
	 * @param dateFormat
	 *            如果有时间数据，设定输出格式
	 * @param sheetMaxRow
	 *            每个sheet数据的最多行数，超出时将分sheet，不包含标题
	 */
	public void export(String sheetTitle, String[] headers, String[] exportFields,
                       Collection<? extends Object> dataset, OutputStream out, String dateFormat, int sheetMaxRow) {

		if (StringUtils.isEmpty(dateFormat)) {
			throw new RuntimeException("参数[dateFormat]日期格式不能为空");
		}

		if (sheetMaxRow <= 0) {

			throw new RuntimeException("参数[sheetMaxRow]sheet的最大行数不正确");
		}

		generalExport(sheetTitle, headers, exportFields, dataset, out, dateFormat, sheetMaxRow);

	}

	/**
	 * 对Excel添加一个sheet
	 * 
	 * @param sheetTitle
	 *            sheet标题
	 * @param headers
	 *            Excel表格属性列标题数组，标题列将按此顺序排版
	 * @param exportFields
	 *            数据对象属性名称数组，导出数据列将按此顺序排版，与headers对应 ，不在数组中的属性将不会被导出
	 * @param dataset
	 *            需要导出的数据集合,集合中的对象必需是标准的javabean，有get和set方法。支持基本数据类型及String,
	 *            Date,byte[](图片数据)
	 * @param dateFormat
	 *            如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
	 * @param filePath
	 *            excel文件路径,文件必须已经存在
	 */
	public void addSheet(String sheetTitle, String[] headers, String[] exportFields,
                         Collection<? extends Object> dataset, String dateFormat, String filePath) {

		generalAddSheet(sheetTitle, headers, exportFields, dataset, dateFormat, filePath);
	}

	/**
	 * 对Excel添加一个sheet
	 * 
	 * @param sheetTitle
	 *            sheet标题
	 * @param headers
	 *            Excel表格属性列标题数组，标题列将按此顺序排版
	 * @param exportFields
	 *            数据对象属性名称数组，导出数据列将按此顺序排版，与headers对应 ，不在数组中的属性将不会被导出
	 * @param dataset
	 *            需要导出的数据集合,集合中的对象必需是标准的javabean，有get和set方法。支持基本数据类型及String,
	 *            Date,byte[](图片数据)
	 * @param filePath
	 *            excel文件路径，文件必须已经存在
	 */
	public void addSheet(String sheetTitle, String[] headers, String[] exportFields,
                         Collection<? extends Object> dataset, String filePath) {

		generalAddSheet(sheetTitle, headers, exportFields, dataset, DEFAULT_DATE_FORMAT, filePath);
	}

	/**
	 * 通用添加sheet方法
	 * 
	 * @param sheetTitle
	 *            sheet标题
	 * @param headers
	 *            Excel表格属性列标题数组，标题列将按此顺序排版
	 * @param exportFields
	 *            数据对象属性名称数组，导出数据列将按此顺序排版，与headers对应 ，不在数组中的属性将不会被导出
	 * @param dataset
	 *            需要导出的数据集合,集合中的对象必需是标准的javabean，有get和set方法。支持基本数据类型及String,
	 *            Date,byte[](图片数据)
	 * @param dateFormat
	 *            如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
	 * @param filePath
	 *            excel文件路径
	 */
	private void generalAddSheet(String sheetTitle, String[] headers, String[] exportFields,
                                 Collection<? extends Object> dataset, String dateFormat, String filePath) {
		// 参数检查
		ExcelTools.checkAddSheetParams(sheetTitle, headers, exportFields, dataset, dateFormat, filePath);

		OutputStream out = null;

		try {

			// 声明一个工作薄
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filePath));

			// 如果有多个sheet，在标题后面加上序列
			HSSFSheet sheet = this.excelStyleCreate.createSheet(workbook, sheetTitle);

			// 声明一个sheet对应的画图管理器
			HSSFPatriarch patriarch = sheet.createDrawingPatriarch();

			// 产生表格标题行
			this.excelStyleCreate.createTitle(workbook, sheet, headers);

			// 生成文件体
			createBody(workbook, sheet, sheetTitle, headers, exportFields, dataset, dateFormat, patriarch, -1, -1);

			out = new FileOutputStream(filePath);

			// 导出
			workbook.write(out);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally {
			IOUtils.closeQuietly(out);
		}

	}

	/**
	 * 通用Excel导出方法，利用JAVA反射机制，导出格式为Excel 2003 除了第一行列标题加粗外无任何样式
	 * 
	 * @param sheetTitle
	 *            sheet标题
	 * @param headers
	 *            Excel表格属性列标题数组，标题列将按此顺序排版
	 * @param exportFields
	 *            数据对象属性名称数组，导出数据列将按此顺序排版，与headers对应 ，不在数组中的属性将不会被导出
	 * @param dataset
	 *            需要导出的数据集合,集合中的对象必需是标准的javabean，有get和set方法。支持基本数据类型及String,
	 *            Date,byte[](图片数据)
	 * @param out
	 *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
	 * @param dateFormat
	 *            如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
	 * @param sheetMaxRow
	 *            每个sheet数据的最多行数，超出时将分sheet，不包含标题。大于0时起效
	 */
	private void generalExport(String sheetTitle, String[] headers, String[] exportFields,
                               Collection<? extends Object> dataset, OutputStream out, String dateFormat, int sheetMaxRow) {

		// 参数检查
		ExcelTools.checkGeneralExportParams(sheetTitle, headers, exportFields, dataset, out);
		try {
			// 声明一个工作薄
			HSSFWorkbook workbook = new HSSFWorkbook();

			// sheet索引号
			int sheetIndex = 1;

			// 如果有多个sheet，在标题后面加上序列
			HSSFSheet sheet = this.excelStyleCreate.createSheet(workbook,
					(sheetMaxRow > 0 && dataset.size() > sheetMaxRow) ? (sheetTitle + sheetIndex) : sheetTitle);

			// 声明一个sheet对应的画图管理器
			HSSFPatriarch patriarch = sheet.createDrawingPatriarch();

			// 产生表格标题行
			this.excelStyleCreate.createTitle(workbook, sheet, headers);

			// 生成文件体
			createBody(workbook, sheet, sheetTitle, headers, exportFields, dataset, dateFormat, patriarch, sheetMaxRow,
					sheetIndex);

			// 导出
			workbook.write(out);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally {
			IOUtils.closeQuietly(out);
		}
	}

	/**
	 * 生成Excel数据文件体
	 * 
	 * @param workbook
	 *            工作薄
	 * @param sheet
	 *            sheet对象
	 * @param sheetTitle
	 *            sheet标题
	 * @param headers
	 *            列标题
	 * @param exportFields
	 *            属性排版数据
	 * @param dataset
	 *            导出数据集对象
	 * @param dateFormat
	 *            输出日期格式
	 * @param patriarch
	 *            sheet对应的画图管理器
	 * @param sheetMaxRow
	 *            每个sheet最大数据行数
	 * @param sheetIndex
	 *            sheet索引号
	 * @throws Exception
	 */
	private void createBody(HSSFWorkbook workbook, HSSFSheet sheet, String sheetTitle, String[] headers,
                            String[] exportFields, Collection<? extends Object> dataset, String dateFormat, HSSFPatriarch patriarch,
                            int sheetMaxRow, int sheetIndex) throws Exception {

		// 遍历集合数据，产生数据行
		Iterator<? extends Object> it = dataset.iterator();

		int rowIndex = 0;

		while (it.hasNext()) {

			rowIndex++;
			Object t = it.next();

			// 把所有的属性放入到map对象中，方便排版导出
			Map<String, PropertyDescriptor> propertyMap = getPropertyDescriptorForMap(t);

			// 创建一行
			this.excelStyleCreate.createRow(t, workbook, sheet, rowIndex, exportFields, dateFormat, propertyMap,
					patriarch);

			// 分sheet
			if (sheetMaxRow > 0 && rowIndex >= sheetMaxRow && it.hasNext()) {

				sheetIndex++;

				sheet = this.excelStyleCreate.createSheet(workbook, sheetTitle + sheetIndex);

				// 声明一个sheet对应的画图管理器
				patriarch = sheet.createDrawingPatriarch();

				// 创建表格标题行
				this.excelStyleCreate.createTitle(workbook, sheet, headers);

				rowIndex = 0;
			}
		}
	}

	/**
	 * 把所有的属性信息以属性名为key放到map对象
	 * 
	 * @param t
	 *            数据类对象
	 * @return
	 * @throws Exception
	 */
	private Map<String, PropertyDescriptor> getPropertyDescriptorForMap(Object t) throws Exception {

		// 把所有的属性放入到map对象中，方便排版导出
		Map<String, PropertyDescriptor> pdMap = new HashMap<String, PropertyDescriptor>();

		// 得到所有属性信息
		PropertyDescriptor[] pds = Introspector.getBeanInfo(t.getClass()).getPropertyDescriptors();

		for (PropertyDescriptor pd : pds) {
			pdMap.put(pd.getName(), pd);
		}

		return pdMap;
	}

	/**
	 * 获取生成Excel元素样式的处理类
	 * 
	 * @return
	 */
	public ExcelStyleCreate getExcelStyleCreate() {
		if (this.excelStyleCreate == null) {
			return new DefaultExcelStyleCreate();
		}
		return excelStyleCreate;
	}

	/**
	 * Setter method for property <tt>excelStyleCreate</tt>.
	 * 
	 * @param excelStyleCreate
	 *            value to be assigned to property excelStyleCreate
	 */
	public void setExcelStyleCreate(ExcelStyleCreate excelStyleCreate) {
		this.excelStyleCreate = excelStyleCreate;
	}

}