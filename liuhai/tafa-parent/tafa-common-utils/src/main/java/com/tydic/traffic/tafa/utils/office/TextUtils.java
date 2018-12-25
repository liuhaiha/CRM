package com.tydic.traffic.tafa.utils.office;


import com.tydic.traffic.tafa.utils.charset.CharsetUtils;
import com.tydic.traffic.tafa.utils.file.FileObject;
import com.tydic.traffic.tafa.utils.file.FileUtils;
import com.tydic.traffic.tafa.utils.file.UnfixedLenLine;
import com.tydic.traffic.tafa.utils.string.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:TextUtils <br/>
 * Function: 数据集输入/输出工具类（eg:CSV、TXT...）. <br/>
 * @version
 */
public class TextUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(TextUtils.class);
	
	private static final int STEP = 50000;	
	
	/**
	 * 
	 * exportTextFile:(将数据导出到指定以“,”分隔符的文本文件里。无title数据列名称，默认以GB2312编码). <br/>
	 * @param exportData	待导出的数据
	 * @param expFile	待导出文件
	 * @since 1.0
	 */
	public static void exportTextFile(List<String[]> exportData, String expFile) {
		exportTextFile(exportData, null, expFile, ",", CharsetUtils.GB2312);
	}

	/**
	 * 
	 * exportTextFile:(将数据导出到指定分隔符的文本文件里，如csv、txt等). <br/>
	 * @param exportData 待导出的数据
	 * @param headers	数据列名称
	 * @param expFile	待导出文件
	 * @param separator 	数据间的分隔符 （eg:  , \t  | 等）
	 * @since 1.0
	 */
	public static void exportTextFile(List<String[]> exportData, String[] headers, String expFile, String separator, String charset) {
		File textFile = null;
		BufferedWriter bufferedWriter = null;
		int total = 0;
		
		try {
			textFile = new File(expFile);
			File parent = textFile.getParentFile();
			if (parent != null && !parent.exists()) {
				parent.mkdirs();
			}
			textFile.createNewFile();
			
			// 此处若用UTF8编码，EXCELl打开文件会乱码
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(textFile), charset), 1024);

			if (null != headers && headers.length > 0) {
				for (int i = 0; i < headers.length; i++) {
					bufferedWriter.write(headers[i]);
					if(i != headers.length - 1)
						bufferedWriter.write(separator);				
				}				
				bufferedWriter.newLine();
			}

			if(null != exportData && exportData.size() > 0){
				for(String[] data : exportData){
					for(int i = 0; i < data.length; i++){
						bufferedWriter.write(data[i]);
						if(i != data.length -1)
							bufferedWriter.write(separator);	
					}					
					bufferedWriter.newLine();
					total++;
				}
				
				if(total % STEP == 0){
					bufferedWriter.flush();
				}				
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage());
		}
		finally {
			try {
				if(null != bufferedWriter){
					bufferedWriter.close();
				}				
			}
			catch (IOException e) {
				logger.error(e.getMessage());
			}
		}	
	}
	
	/**
	 * 
	 * importTextFile:(导入指定分隔符的文本文件，如csv、txt等，默认以GB2312编码). <br/>
	 * @param filePath		待导入文件
	 * @param readHeaded	是否读取title列标题         true:读取title； false不读取title
	 * @param separator		指定的数据分隔符
	 * @return List<String[]>
	 * @since 1.0
	 */
	public static List<String[]> importTextFile(String filePath, boolean readHeaded, String separator){
		return importTextFile(filePath, readHeaded, separator, CharsetUtils.GB2312);
	}
	
	/**
	 * 
	 * importTextFile:(导入指定分隔符的文本文件，如csv、txt等). <br/>
	 * @param filePath		待导入文件
	 * @param readHeaded	是否读取title列标题         true:读取title； false不读取title
	 * @param separator		指定的数据分隔符
	 * @param charset		文件原始编码
	 * @return List<String[]>
	 * @since 1.0
	 */
	public static List<String[]> importTextFile(String filePath, boolean readHeaded, String separator, String charset){
		List<String[]> impData = null;
		FileObject fileIn = null;
		try{
			fileIn = new FileObject(filePath, "R", charset);				
		}catch(Exception e){
			logger.error(e.getMessage());
			return null;
		}		
		impData = new ArrayList<String[]>();
		UnfixedLenLine lineData = new UnfixedLenLine(separator);
		String strLine = null;
		int iLines = 0;		
		int iTotalLines = 0;
		while ((strLine = fileIn.readLine()) != null) {
			iLines++;
			if(iLines == 1 && !readHeaded){
				continue;
			}
			lineData.setData2(strLine);		
			impData.add(lineData.getFielddatas());
			iTotalLines++;
		}
		
		if(fileIn != null){
			fileIn.close();
		}
		
		logger.info("import file:" + filePath + " done. total line:" + iTotalLines);
		return impData;
	}		
	
	/**
	 * 
	 * exp2Html:(导出数据集到html文件). <br/>
	 * @param docTitle	
	 * 				html页面标题
	 * @param headers
	 * 				表头数据列
	 * @param exportData
	 * 				数据集
	 * @param expFile 
	 *				html导出文件路径			
	 * @since 1.0
	 */
	public static void exp2Html(String docTitle, String[] headers, List<String[]> exportData, String expFile){
		try{
			String templateContent = FileUtils.read("htmlTemplet.html");
			String content = templateContent.replace("${document.title}", docTitle);
			StringBuilder sb = new StringBuilder();
			if (null != headers && headers.length > 0) {
				sb.append("<tr>");
				for (int i = 0; i < headers.length; i++) {
					sb.append("<th align='center' height='35' bgcolor='#e2e2e2'>"); 
					sb.append(headers[i]);
					sb.append("</th>"); 
				}
				sb.append("</tr>");
				sb.append(StringUtils.CRIF);
			}
			
			if(null != exportData && exportData.size() > 0){				
				for(String[] strs : exportData){
					sb.append("<tr>");
					for(int i = 0; i < strs.length; i++){
						sb.append("<td>");
						sb.append(strs[i]);
						sb.append("</td>");
					}
					sb.append("</tr>");
					sb.append(StringUtils.CRIF);
				}
			}
			
			content = content.replace("${message.content}", sb.toString());			
			FileUtils.write(new File(expFile), content);
		}catch(Exception ex){
			logger.error(ex.getMessage());
		}
	}
}