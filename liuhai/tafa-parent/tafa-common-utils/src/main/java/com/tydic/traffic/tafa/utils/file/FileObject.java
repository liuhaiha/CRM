package com.tydic.traffic.tafa.utils.file;

import com.tydic.traffic.tafa.utils.charset.CharsetUtils;
import com.tydic.traffic.tafa.utils.string.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;

/**
 * ClassName:FileObject <br/>
 *
 */
public class FileObject {

	private static Logger logger = LoggerFactory.getLogger(FileObject.class);

	private InputStreamReader fr = null;

	private BufferedReader br = null;

	private OutputStreamWriter fw = null;

	private BufferedWriter bw = null;

	private String absolutepath = "";

	private String filename = "";

	/**
	 * 每行数据的结束字符
	 */
	private String line_end_string = null;

	/**
	 * 
	 * Creates a new instance of FileObject.
	 *
	 * @param strFilePath	
	 * 			文件路径
	 * @param strRW
	 * 			文件操作模式，R:读文件、W：写文件
	 */
	public FileObject(String strFilePath, String strRW) {
		if (strRW.trim().toUpperCase().equals("R")) {
			initFileObject(strFilePath, strRW, CharsetUtils.UTF_8);
		}
		else if (strRW.trim().toUpperCase().equals("W")) {
			initFileObject(strFilePath, strRW, CharsetUtils.UTF_8);
		}
		else {
			throw new RuntimeException("FileObject: Invalid RW-TYPE! (" + strRW + ")");
		}
	}

	/**
	 * 
	 * Creates a new instance of FileObject.
	 *
	 * @param strFilePath	
	 * 			文件路径
	 * @param strRW
	 * 			文件操作模式，R:读文件、W：写文件
	 * @param strEncoding
	 * 			文件字符编码集
	 */
	public FileObject(String strFilePath, String strRW, String strEncoding) {
		initFileObject(strFilePath, strRW, strEncoding);
	}

	/**
	 * 
	 * initFileObject:(初始化文件对象). <br/>
	 * @param strFilePath
	 * @param strRW
	 * @param strEncoding
	 */
	private void initFileObject(String strFilePath, String strRW, String strEncoding) {
		try {
			if (StringUtils.nvl(strFilePath).equals("")) {
				throw new RuntimeException("FileObject: Invalid File Path! (" + strFilePath + ")");
			}

			File file = new File(strFilePath);
			absolutepath = file.getAbsolutePath();
			filename = file.getName();

			if (strRW.trim().toUpperCase().equals("R")) {
				if (StringUtils.nvl(strEncoding).equals("")) {
					fr = new InputStreamReader(new FileInputStream(strFilePath), CharsetUtils.UTF_8);
				}
				else {
					fr = new InputStreamReader(new FileInputStream(strFilePath), strEncoding);
				}
			}
			else if (strRW.trim().toUpperCase().equals("W")) {
				if (StringUtils.nvl(strEncoding).equals("")) {
					fw = new OutputStreamWriter(new FileOutputStream(strFilePath), CharsetUtils.UTF_8);
				}
				else {
					fw = new OutputStreamWriter(new FileOutputStream(strFilePath), strEncoding);
				}
			}
			else {
				throw new RuntimeException("FileObject: Invalid RW-TYPE! (" + strRW + ")");
			}
		}
		catch (IOException e) {
			logger.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}
	
	/**
	 * 
	 * readLine:(读取一行). <br/>
	 * @return
	 * @since 1.0
	 */
	public String readLine() {
		String strLine = null;
		try {
			if (br == null) {
				br = new BufferedReader(fr);
			}
			strLine = br.readLine();
		}
		catch (Exception e) {
			close();
			logger.error("readline: " + e.getMessage());
			throw new RuntimeException(e);
		}
		return strLine;
	}

	/**
	 * 
	 * writeLine:(写一行). <br/>
	 * @param strLine
	 * @since 1.0
	 */
	public void writeLine(String strLine) {
		try {
			if (bw == null) {
				bw = new BufferedWriter(fw);
			}
			bw.write(strLine + StringUtils.nvl(this.line_end_string));
			bw.newLine();
		}
		catch (Exception e) {
			close();
			logger.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 
	 * flushWriteBuffer:(flush). <br/>
	 * @since 1.0
	 */
	public void flushWriteBuffer() {
		try {
			if (bw != null) {
				bw.flush();
			}
		}
		catch (Exception e) {
			close();
			logger.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 
	 * close:(执行close操作). <br/>
	 * @since 1.0
	 */
	public void close() {
		try {
			if (br != null) {
				br.close();
			}
			if (bw != null) {
				bw.close();
			}
			if (fr != null) {
				fr.close();
			}
			if (fw != null) {
				fw.close();
			}
		}
		catch (IOException e) {
			logger.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 
	 * getAbsolutepath:(返回绝对路径). <br/>
	 * @return
	 * @since 1.0
	 */
	public String getAbsolutepath() {
		return absolutepath;
	}

	/**
	 * 
	 * getFilename:(取得文件名称). <br/>
	 * @return
	 * @since 1.0
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * 
	 * getLine_end_string:(取得每行数据的结束字符). <br/>
	 * @return
	 * @since 1.0
	 */
	public String getLine_end_string() {
		return line_end_string;
	}

	/**
	 * 
	 * setLine_end_string:(设置line_end_string为每行数据的结束字符). <br/>
	 * @param line_end_string
	 * @since 1.0
	 */
	public void setLine_end_string(String line_end_string) {
		this.line_end_string = line_end_string;
	}
}
