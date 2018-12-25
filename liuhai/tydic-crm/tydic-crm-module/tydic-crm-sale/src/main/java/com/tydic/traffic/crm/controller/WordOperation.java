package com.tydic.traffic.crm.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.util.ClassUtils;

/**
 * 通过word模板生成word文件的类
 * 
 */
public class WordOperation {
	
	private static Logger logger = Logger.getLogger(WordOperation.class);
	
	private static WordOperation instance;

	/**
     * 项目相对于系统的绝对路径
     */
//	private static final String PATH = ServletActionContext.getServletContext()
//			.getRealPath(File.separator).replace("\\", File.separator);
//	private static final String PATH = System.getProperty("user.dir");
//	private static final String PATH = ClassUtils.getDefaultClassLoader().getResource("").getPath().replace("\\", File.separator);
	
	 /**
     * template文件夹路径
     */
	private static final String MYTEMPLATEPATH = "E:\\jcj\\itmssub_optimize\\web\\template\\";
//	private static final String MYTEMPLATEPATH = PATH +File.separator+ "template" + File.separator;
	
	 /**
     * temp下的document.xml临时加载数据的文件
     */
    private static final String DOCUMENT_XML = "document.xml";
    
    /**
     * word文件夹路径
     */
    private static final String WORDPATH = MYTEMPLATEPATH + "word" + File.separator;

    /**
     * temp文件夹路径
     */ 
    private  static final String TEMPPATH = WORDPATH + "temp" + File.separator;
    /**
     * 
    * 输出word编码
    */
    private static final String ENCODING = "UTF-8";

    /**
     * Velocity装载模版路径
     */
    private static final String LODERPATH = "file.resource.loader.path";
    
    /**
     * 分隔符
     */
    private static final String SPLITER = "/";

    /**
     * word字符串常量
     */
    private static final String WORD = "word";
	
	private WordOperation() {
	}

	public static WordOperation getInstance() {
		if (null == instance) {
			instance = new WordOperation();
		}
		return instance;
	}
	
	public static void main(String[] args) {
		
		String templateName = "jq_detail";
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("jqbh", "0125455785888");
		dataMap.put("jqnr", "深圳莲花支路发生警情");
		dataMap.put("xzqh", "福田区");
		dataMap.put("zgdw", "交警支队");
		try {
			String str = WordOperation.getInstance().createWord(templateName, dataMap);
			System.out.println(str);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("", e);
		}
	}

	public static void test() {
		String templateName = "jq_detail";
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("jqbh", "0125455785888");
		dataMap.put("jqnr", "深圳莲花支路发生警情");
		dataMap.put("xzqh", "福田区");
		dataMap.put("zgdw", "交警支队");
		try {
			WordOperation.getInstance().createWord(templateName, dataMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("", e);
		}
		// generaterDoc();
	}

	/**
	 * 使用word模板文件生成word文件
	 * @param templateName 模板文件,不带后缀
	 * @param dataMap 填充的数据
	 * @throws Exception
	 * @return 生成文件路径
	 */
	public String createWord(String templateName, HashMap<String, Object> dataMap)
			throws Exception {
		// 定义加入数据后的document.xml文件
		File documentFile = new File(injectData(dataMap, templateName));
		// 生成word文件
		File targetFile = new File(MYTEMPLATEPATH + System.currentTimeMillis() + ".docx");
		// word模板文件
		File templateFile = new File(MYTEMPLATEPATH
				+ templateName + ".docx");
		// 将模板文件拷贝一份作为目标文件
		fileCopy(templateFile, targetFile);
		// 在目标文件中加入新的document.xml文件
		addDocumentToTemplate(targetFile, documentFile);
		documentFile.delete();
		return targetFile.getName();
	}

	/**
	 * 提供下载
	 * @param downloadName 下载时显示的文件名称
	 * @param fileName 下载的文件
	 * @throws Exception
	 */
	public static String generaterDoc(String downloadName, String fileName)
			throws Exception {
		HttpServletResponse resp = ServletActionContext.getResponse();
		// 输出响应正文的输出流
		OutputStream out;
		// 读取本地文件的输入流
		InputStream in;
		File file = new File(MYTEMPLATEPATH + fileName);
		in = new FileInputStream(file);
		// 设置响应正文的MIME类型
		resp.setContentType("Content-Disposition;charset=utf-8");
		resp.setHeader("Content-Disposition", "attachment;" + " filename=\""
				+ URLEncoder.encode(downloadName, "UTF-8") + "\"");
		// 把本地文件发送给客户端
		out = resp.getOutputStream();
		int byteRead = 0;
		byte[] buffer = new byte[512];
		while ((byteRead = in.read(buffer)) != -1) {
			out.write(buffer, 0, byteRead);
		}
		in.close();
		out.close();
		file.delete();
		return null;
	}

	/**
	 * 往模板绑定数据
	 * 
	 * @param dataMap  要装入的数据
	 * @param templateName 模板名称
	 * @return 返回装入数据完成的xml文件路径
	 * @throws Exception
	 */
	private String injectData(HashMap<String, Object> dataMap,
			String templateName) throws Exception {

		// Init Velocity
		Properties p = new Properties();

		p.setProperty(LODERPATH, WORDPATH);
		Velocity.init(p);

		VelocityContext context = new VelocityContext();
		for (Iterator<Map.Entry<String, Object>> iter = dataMap.entrySet()
				.iterator(); iter.hasNext();) {
			Map.Entry<String, Object> entry = iter.next();
			context.put(entry.getKey(), entry.getValue());
		}

		Template template = null;
		template = Velocity.getTemplate(templateName + ".xml",
				ENCODING);
		
		String templateDocPath = TEMPPATH + System.currentTimeMillis()+".xml";
		File tempdir = new File(TEMPPATH);
		tempdir.mkdirs();
		OutputStreamWriter writer = new OutputStreamWriter(
				new FileOutputStream(templateDocPath), ENCODING);
		if (template != null) {
			template.merge(context, writer);
		}
		writer.flush();
		writer.close();
		return templateDocPath;
	}

	/**
	 * 将要document.xml文件的数据装入到模板中
	 * @param targetFile 目标模板文件
	 * @param document  document.xml文件
	 * @throws IOException
	 */
	private static void addDocumentToTemplate(File targetFile, File document)
			throws IOException {
		File tempFile = File.createTempFile(targetFile.getName(), null);
		tempFile.delete();
		try {
			writeTo(targetFile, tempFile);
			targetFile.delete();

		} catch (Exception e) {
			throw new RuntimeException("could not rename the file "
					+ targetFile.getAbsolutePath() + " to "
					+ tempFile.getAbsolutePath());
		}
		byte[] buf = new byte[1024];

		ZipInputStream zin = new ZipInputStream(new FileInputStream(tempFile));
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
				targetFile));

		ZipEntry entry = zin.getNextEntry();
		while (entry != null) {
			String name = entry.getName();
			boolean notInFiles = true;
			if (document != null) {
				if (document.getName().equals(name)) {
					notInFiles = false;
					break;
				}
			}
			if (notInFiles) {
				out.putNextEntry(new ZipEntry(name));
				int len;
				while ((len = zin.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
			}
			entry = zin.getNextEntry();
		}
		zin.close();
		if (document != null) {
			InputStream in = new FileInputStream(document);
			out.putNextEntry(new ZipEntry(WORD + SPLITER
					+ DOCUMENT_XML));
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.closeEntry();
			in.close();
		}
		out.close();
		tempFile.delete();
	}

	/**
	 * 文件拷贝
	 * 
	 * @param srcFile
	 *            源文件
	 * @param destFile
	 *            目标文件
	 * @throws IOException
	 */
	public static void fileCopy(File srcFile, File destFile) throws IOException {
		FileInputStream input = new FileInputStream(srcFile);
		try {
			FileOutputStream output = new FileOutputStream(destFile);
			try {
				byte[] buffer = new byte[4096];
				int n = 0;
				while (-1 != (n = input.read(buffer))) {
					output.write(buffer, 0, n);
				}
			} finally {
				try {
					if (output != null) {
						output.flush();
						output.close();
					}
				} catch (IOException ioe) {
					// ignore
				}
			}
		} finally {
			try {
				if (input != null) {

					input.close();
				}
			} catch (IOException ioe) {
				// ignore
			}
		}
	}

	/**
	 * 得到classPath绝对路径
	 * 
	 * @param classPath
	 *            相对路径
	 * @return
	 */
	public static String getAbsoluteClassPath(String classPath) {

		String path = null;
		try {
			File file = new File(Thread.currentThread().getContextClassLoader()
					.getResource(classPath).getPath());
			path = URLDecoder.decode(file.getAbsolutePath(), ENCODING);
		} catch (UnsupportedEncodingException e) {
			logger.error("", e);
		}
		return path;
	}

	private static void writeTo(File f, File newFile) throws IOException {
		FileInputStream fis = new FileInputStream(f);
		FileOutputStream fos = new FileOutputStream(newFile);
		try {
			byte[] buf = new byte[8192];
			do {
				int rc = fis.read(buf);
				if (rc == -1)
					break;
				fos.write(buf, 0, rc);
				if (rc < buf.length)
					break;
			} while (true);
		} finally {
			fis.close();
			fos.close();
		}
	}

}
