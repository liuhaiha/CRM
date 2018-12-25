package com.tydic.traffic.crm.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreeMark {
	
	private Configuration configuration;
	private String templateName;
	private String fileName;
	private String filePath;

	public static void main(String[] args) {
//		FreeMark freeMark = new FreeMark("template/");
//		freeMark.setTemplateName("record.xml");
//		freeMark.setFileName("record.doc");
//		freeMark.setFilePath("E:/");
//		freeMark.createWord();
	}
	
	public FreeMark(String templatePath) {
		configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
		configuration.setClassForTemplateLoading(this.getClass(), templatePath);
	}
	
	public void createWord(Map<String, Object> map) {
		Template t = null;
		
		try {
			t = configuration.getTemplate(templateName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		File outFile = new File(filePath + fileName);
		Writer out = null;
		try {
			out = new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8");
			System.out.println("===============================================会议纪要保存成功===============================================");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			t.process(map, out);
			out.flush();
			out.close();
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
}
