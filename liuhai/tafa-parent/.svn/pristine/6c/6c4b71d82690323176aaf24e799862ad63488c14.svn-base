package com.tydic.traffic.tafa.utils.office;

import com.tydic.traffic.tafa.utils.SystemUtils;
import com.tydic.traffic.tafa.utils.file.FileUtils;
import com.tydic.traffic.tafa.utils.string.StringUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;

/**
 * ClassName:WordTools
 */
public class WordUtils {

	/** word HTML格式文档模板名称 */
	private static final String WORD_HTML_TEMPLATE = "htmlWord.xml";

	/** word word格式文件模板名称 */
	private static final String WORD_EMPTY_TEMPLATE = "emptyWord.xml";

	/** word文档内容 */
	private static final String WORD_CONTENT = "${word_content}";

	/** HTTP */
	private static final String HTTP = "http";

	/** 读取文件编码 */
	private static final String DEFAULT_CHARSET = "UTF8";

	private static final Logger logger = LoggerFactory.getLogger(WordUtils.class);
	
	
	/**
	 * 
	 * readDoc:(读取标准的doc文件). <br/>
	 * @param fis
	 * @return
	 * @throws Exception String
	 * @since 1.0
	 */
	public static String readDoc(FileInputStream fis) throws Exception {
		WordExtractor extractor = new WordExtractor(fis);
		String doctext = extractor.getText();
		return doctext;
	}

	/**
	 * 
	 * readDocx:(读取标准的docx文件). <br/>
	 * @param filepath
	 * @return
	 * @throws Exception String
	 * @since 1.0
	 */
	public static String readDocx(String filepath) throws Exception {
        OPCPackage opcPackage = POIXMLDocument.openPackage(filepath);
        POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
        return extractor.getText();
	}
	
	/**
	 * 以文本方式导出word
	 * 
	 * @param content
	 *            内容
	 * @param os
	 *            文件输出流
	 */
	public void exportText(String content, OutputStream os) {
		generalExport(StringUtils.htmlEncode(content), os, DEFAULT_CHARSET, WORD_EMPTY_TEMPLATE);
	}

	/**
	 * 根据文本，生成word文件
	 * 
	 * @param content
	 *            内容
	 * @param os
	 *            文件输出流
	 */
	public void export(String content, OutputStream os) {

		generalExport(content, os, DEFAULT_CHARSET, WORD_HTML_TEMPLATE);
	}

	/**
	 * 根据uri地址，生成word文件
	 * 
	 * @param content
	 *            内容
	 * @param os
	 *            输出流
	 * @param uriCharset
	 *            读取uri时的编码
	 */
	public void export(String content, OutputStream os, String uriCharset) {

		generalExport(content, os, uriCharset, WORD_HTML_TEMPLATE);
	}

	/**
	 * 根据uri地址，生成word文件
	 * 
	 * @param content
	 *            内容
	 * @param os
	 *            输出流
	 * @param charset
	 *            读取uri时的编码
	 */
	private void generalExport(String content, OutputStream os, String charset, String wordHtmlTemplate) {

		// uri路径的网页内容
		StringBuilder sb = new StringBuilder();

		// 文件reader
		BufferedReader reader = null;

		try {

			if (StringUtils.startsWithIgnoreCase(content, HTTP)) {

				URL url = new URL(content);

				reader = new BufferedReader(new InputStreamReader(url.openStream(), charset));

				// 读取url网页内容
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
			}
			else {
				sb.append(content);
			}

			// 读取word模板
			String template = this.readFile(wordHtmlTemplate);

			// 在模板中渲染word内容
			template = template.replace(WORD_CONTENT, sb.toString());

			os.write(template.getBytes());
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally {
			IOUtils.closeQuietly(reader);
			IOUtils.closeQuietly(os);
		}
	}

	/**
	 * 读取html文件到word
	 * 
	 * @param filepath
	 *            html文件的路径
	 * @param targetPath
	 *            word文件生成的路径
	 * @return
	 * @throws Exception
	 */
	public static boolean write2WordFile(String filepath, String targetPath) {
		boolean flag = false;
		ByteArrayInputStream bais = null;
		FileOutputStream fos = null;
		try {
			if (!StringUtils.isEmpty(targetPath)) {
				SystemUtils.makeDir(targetPath);
				String fileName = FileUtils.getFileNameNoExt(filepath);
				String content = readFile(filepath);
				byte b[] = content.getBytes();
				bais = new ByteArrayInputStream(b);
				POIFSFileSystem poifs = new POIFSFileSystem();
				DirectoryEntry directory = poifs.getRoot();
				DocumentEntry documentEntry = directory.createDocument("WordDocument", bais);
				fos = new FileOutputStream(targetPath + File.separator + fileName + "_temp.doc");
				poifs.writeFilesystem(fos);
				bais.close();
				fos.close();
				flag = true;
			}
		}
		catch (IOException e) {
			logger.error(e.getMessage());
		}
		finally {
			try {
				if (fos != null) {
					fos.close();
				}
			}
			catch (IOException e) {
				logger.error(e.getMessage());
			}
			try {
				if (bais != null) {
					bais.close();
				}
			}
			catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		return flag;
	}

	/**
	 * 读取文件
	 * 
	 * @param fileName
	 *            文件名
	 * @return 文件内容字符串
	 */
	private static String readFile(String fileName) {

		// 最终内容
		StringBuilder sb = new StringBuilder();

		InputStream is = null;
		InputStreamReader fileReader = null;
		BufferedReader br = null;
		try {
			// is = WordTools.class.getResourceAsStream(fileName);
			is = FileUtils.findFileAsStream(fileName, WordUtils.class, DEFAULT_CHARSET);

			fileReader = new InputStreamReader(is);

			br = new BufferedReader(fileReader);

			// 一行
			String line = null;

			while ((line = br.readLine()) != null) {

				sb.append(line);
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally {
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(fileReader);
			IOUtils.closeQuietly(br);
		}

		return sb.toString();
	}

	/**
	 * 
	 * writeFile:(这里用一句话描述这个方法的作用). <br/>
	 * @param content
	 * @param path void
	 * @since 1.0
	 */
	public static void writeFile(String content, String path) {
		FileOutputStream fos = null;
		BufferedWriter bw = null;
		try {
			File file = new File(path);
			fos = new FileOutputStream(file);
			bw = new BufferedWriter(new OutputStreamWriter(fos, "GB2312"));
			bw.write(content);
		}
		catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
		finally {
			try {
				if (bw != null)
					bw.close();
				if (fos != null)
					fos.close();
			}
			catch (IOException ie) {
				logger.error(ie.getMessage());
			}
		}
	}

}