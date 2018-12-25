package com.tydic.traffic.crm.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;


public class ExcelTool {
	
	private static Logger logger = Logger.getLogger(ExcelTool.class);
	
/**
 *导出excel
 * @param titles  标题
 * @param list
 * @param rowCounts
 * @return
 * @throws IOException
 */
	public static String getExcel(String titles[], List<LinkedHashMap<String, Object>> list,
			int rowCounts,String fileName, String saveDir) throws IOException {
		HSSFWorkbook web = new HSSFWorkbook();
		//如果每页的数量显示不完，则另外算生成sheet
		int lent = list.size() % (rowCounts+1) == 0 ? list.size() / (rowCounts+1)
				: (list.size() / (rowCounts+1)) + 1;
		//生成是定数量的sheet
		for (int i = 0; i < lent; i++) {
			HSSFSheet sheet = web.createSheet("sheet" + i);
			int index = 0;
			HSSFRow rowTitle = sheet.createRow(index);
			int tNum=titles.length;
			if(tNum!=0){
				for(int ti=0;ti<tNum;ti++){
					rowTitle.createCell(ti).setCellValue(titles[ti]);
				}
			}
			
			index++;
			List<HashMap<String, Object>> templist = new ArrayList<HashMap<String, Object>>();
			//遍历每条记录
			for (HashMap<String, Object> map : list) {
				HSSFRow row = sheet.createRow(index);
				//如果每个sheet达到一定的数量以后自动生成新的sheet页
				if (index != 0 && index % (rowCounts+1) == 0) {
					break;
				}
				index++;
				int cel = 0;
				//遍历调记录的map放入单元格里面
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					if (null != entry.getValue() && entry.getValue() != "")
					{
						row.createCell(cel).setCellValue(
						getCell(entry.getValue()));
//						sheet.setColumnWidth(cel, entry.getValue().toString().getBytes().length * 256);
					}
					else
						row.createCell(cel).setCellValue("");
					cel++;
					
				}
				//将已经写入表格的数据存起来
				templist.add(map);
			}
			//将已经写入表格的数据删掉
			list.removeAll(templist);
		}
		//将生成的文件放入指定的路径
		File fdir = new File(saveDir);
		if(!fdir.exists()){
			fdir.mkdir();
		}
		if(null==fileName||fileName.isEmpty()){
			fileName= String.valueOf(System.currentTimeMillis())+".xls";
		}
		File fl = new File(saveDir+File.separator+fileName);
		if (!fl.exists()) {
			try {
				fl.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			fl.delete();
		}
		FileOutputStream str = new FileOutputStream(fl);
		web.write(str);
		str.close();
		return fl.getPath();
	}
	//处理特殊字段
	private static String getCell(Object obj) {
		//Object clazz = obj.getClass();
		//clazz.equals("");
       return obj.toString();
        }

	/**
	 * 生成Excel
	 * 
	 * @param titles
	 *            Excel表头
	 * @param list
	 *            数据
	 * @param fixed
	 *            字段（对应表头的）
	 * @param rowCounts
	 *            一个sheet页多少条记录
	 * @param fileName
	 *            文件名
	 * @return
	 * @throws IOException
	 */
	public static String getExcel(String[] titles, String[] fields,List<HashMap<String, Object>> list, int rowCounts,String fileName) throws IOException {
		HSSFWorkbook web = new HSSFWorkbook();
		// 设置每列的样式居中
		HSSFCellStyle cellStyle = web.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 如果每页的数量显示不完，则另外算生成sheet
		int lent = list.size() % rowCounts == 0 ? list.size() / rowCounts
				: list.size() / rowCounts + 1;
		// 生成是定数量的sheet
		for (int i = 0; i < lent; i++) {
			HSSFSheet sheet = web.createSheet("Sheet" + (i + 1));
			HSSFRow titleRow = sheet.createRow(0);
			// 表头
			for (int column = 0; column < titles.length; column++) {
				sheet.setDefaultColumnStyle(column, cellStyle);
				titleRow.createCell(column).setCellValue(titles[column]);
			}

			List<HashMap<String, Object>> templist = new ArrayList<HashMap<String, Object>>();
			// 遍历每条记录
			for (int index = 1; index <= list.size(); index++) {
				HashMap<String, Object> map = list.get(index - 1);
				HSSFRow row = sheet.createRow(index);
				for (int column = 0; column < fields.length; column++) {
					Object obj = map.get(fields[column]);
					if (null != obj)
						row.createCell(column).setCellValue(obj.toString());
					else
						row.createCell(column).setCellValue("");
				}
				// 将已经写入表格的数据存起来
				templist.add(map);
				// 如果每个sheet达到一定的数量以后自动生成新的sheet页
				if (index % rowCounts == 0) {
					break;
				}
			}
			// 将已经写入表格的数据删掉
			list.removeAll(templist);
			autoSizeColumnCH(sheet, titles.length);
		}
		return writeToFile(web, fileName);
	}

	/**
	 * 自动适应中文宽度
	 * 
	 * @param sheet
	 * @param columns
	 */
	private static void autoSizeColumnCH(HSSFSheet sheet, int columns) {
		for (int j = 0; j < columns; j++) {
			sheet.autoSizeColumn(j);
			int columWidth = sheet.getColumnWidth(j) / 256;
			for (int k = 0; k <= sheet.getLastRowNum(); k++) {
				HSSFCell cell = sheet.getRow(k).getCell(j);
				if (cell != null) {
					int length = cell.toString().getBytes().length;
					if (columWidth < length) {
						columWidth = length;
					}
				}
			}
			columWidth = columWidth>64?64:columWidth;
			columWidth = columWidth<8?8:columWidth;
			sheet.setColumnWidth(j, columWidth * 256);
		}
	}

	/**
	 * 将Workbook写入文件
	 * 
	 * @param web
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	private static String writeToFile(HSSFWorkbook web, String fileName) {
		// 将生成的文件放入指定的路径
		String url = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("/")
				+ "upload";
		File fdir = new File(url);
		if (!fdir.exists()) {
			fdir.mkdir();
		}
		if (null == fileName || fileName.isEmpty()) {
			fileName = String.valueOf(System.currentTimeMillis()) + ".xls";
		}
		File fl = new File(url + File.separator + fileName);
		if (!fl.exists()) {
			try {
				fl.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			fl.delete();
		}
		FileOutputStream str = null;
		try {
			str = new FileOutputStream(fl);
			web.write(str);
		} catch (Exception e) {
			logger.error("", e);
		}finally {
			if(str != null){
				try {
					str.close();
				} catch (IOException e) {
					logger.error("", e);
				}
			}
		}
		
		return fl.getPath();
	}
}
