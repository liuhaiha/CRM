package com.tydic.traffic.tafa.utils.file;

import com.tydic.traffic.tafa.utils.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * ClassName:FileTypeJudge <br/>
 * Reason: 根据文件头判断文件类型 .（注：文本类文件无文件头标识） <br/>
 */
public final class FileTypeJudge {

	private static final Logger logger = LoggerFactory.getLogger(FileTypeJudge.class);

	/**
	 * Constructor
	 */
	private FileTypeJudge() {
	}

	/**
	 * 
	 * bytesToHexString:(将文件头转换成16进制字符串). <br/>
	 * @param src
	 * 			原生byte
	 * @return
	 * 			16进制字符串
	 * @since 1.0
	 */
	private static String bytesToHexString(byte[] src) {

		StringBuilder stringBuilder = new StringBuilder();
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}


	/**
	 * 
	 * getFileContent:(得到文件头). <br/>
	 * @param filePath
	 *            文件路径
	 * @return 文件头
	 * @throws IOException
	 * @since 1.0
	 */
	private static String getFileContent(String filePath) throws IOException {

		byte[] b = null;

		InputStream inputStream = null;

		try {
			File file = new File(filePath);
			long size = file.length() < 28 ? file.length() : 28;
			b = new byte[(int) size];
			inputStream = new FileInputStream(filePath);
			inputStream.read(b, 0, b.length);
		}
		catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
		finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				}
				catch (IOException e) {
					logger.error(e.getMessage());
					throw e;
				}
			}
		}
		return bytesToHexString(b);
	}
	
	/**
	 * 
	 * getFileContent:(取得文件内容). <br/>
	 * @param is
	 * @return
	 * @throws IOException
	 * @since 1.0
	 */
	private static String getFileContent(InputStream is) throws IOException {
		byte[] b = null;
		try {
			int size = is.available() < 28 ? is.available() : 28; 
			b = new byte[(int) size];
			is.read(b, 0, b.length);
		}
		catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}		
		return bytesToHexString(b);
	}

	/**
	 * 
	 * getType:(判断文件类型). <br/>
	 * @param filePath
	 *            文件路径
	 * @param extName 
	 *            文件扩展名
	 * @return 文件类型
	 * @throws IOException
	 * @since 1.0
	 */
	public static String getType(String filePath, String extName) throws IOException {

		String fileHead = getFileContent(filePath);

		if (fileHead == null || fileHead.length() == 0) {
			return null;
		}

		return matchType(fileHead, extName);	
	}
	

	/**
	 * 
	 * getType:(判断文件类型). <br/>
	 * @param ins 输入流
	 * @param extName 文件扩展名
	 * @return
	 * @throws IOException
	 * @since 1.0
	 */
	public static String getType(InputStream ins, String extName) throws IOException {

		String fileHead = getFileContent(ins);

		if (fileHead == null || fileHead.length() == 0) {
			return null;
		}
		
		return matchType(fileHead, extName);
	}
	
	/**
	 * 
	 * matchType:(因同一系列文件的文件头一致，故此处利用扩展名进行二次匹配). <br/>
	 * @param fileHead
	 * @param extName
	 * @return
	 * @since 1.0
	 */
	private static String matchType(String fileHead, String extName){
		fileHead = fileHead.toLowerCase();

		Config cfg = new Config("file_type.properties");
		Map.Entry[] entries = getSortedHashtableByValue(cfg.getConfig());
		Hashtable<String, String> ht = new Hashtable<String, String>();
		
		for (int i = 0; i < entries.length; i++) {
			if (fileHead.startsWith(entries[i].getValue().toString().toLowerCase())) {
				ht.put(entries[i].getKey().toString(), entries[i].getValue().toString());
			}
		}

		if (ht.size() > 0) {
			String key = null;
			for (Iterator<String> itr = ht.keySet().iterator(); itr.hasNext();) {
				key = itr.next().toString();
				if (key.toLowerCase().equals(extName.toLowerCase()) || extName.toLowerCase().startsWith(key.toLowerCase())) {
					break;
				}
				key = null;
			}
			if(key == null){
				key = ht.keySet().iterator().next();
			}
			return key;
		}
		else {
			return null;
		}	
	}

	@SuppressWarnings("unchecked")
	private static Map.Entry[] getSortedHashtableByValue(Hashtable<?, ?> h) {
		Set<?> set = h.entrySet();
		Map.Entry[] entries = (Map.Entry[]) set.toArray(new Map.Entry[set.size()]);
		Arrays.sort(entries, new Comparator<Object>() {

			public int compare(Object arg0, Object arg1) {
				String str1 = ((Map.Entry) arg0).getValue().toString();
				String str2 = ((Map.Entry) arg1).getValue().toString();
				return ((Comparable<String>) str2).compareTo(str1);
			}
		});
		return entries;
	}

}
