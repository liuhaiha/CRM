package com.tydic.test.utils.file;

import com.tydic.traffic.tafa.utils.file.FileObject;
import com.tydic.traffic.tafa.utils.file.FileUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static org.junit.Assert.assertNotNull;

/**
 * ClassName:FileObjectTest
 */
public class FileObjectTest {
	static String basePath=FileUtils.getAbsolutePathWithApp()+"/Data/";
	static Logger logger = LoggerFactory.getLogger(FileObjectTest.class);
	@Test
	public void testFileObjectStringString() throws Exception {
		File file = new File(basePath+"file.txt");
		FileUtils.createNewFile(file);
		FileUtils.write(file, "AAAAA");
		FileObject fileObject = new FileObject(basePath+"file.txt", "R");
		assertNotNull(fileObject);
		fileObject = new FileObject(basePath+"file.txt", "w");
		assertNotNull(fileObject);
		file.delete();
	}

	@Test(expected = Exception.class)
	public void testFileObjectStringString2() {
		FileObject fileObject = new FileObject(basePath+"file.txt", "s");
	}

	@Test
	public void testFileObjectStringStringString() throws Exception {
		File file = new File(basePath+"file.txt");
		FileUtils.createNewFile(file);
		FileUtils.write(file, "AAAAA");
		FileObject fileObject = new FileObject(basePath+"file.txt", "R", "UTF-8");
		fileObject = new FileObject(basePath+"file.txt", "W", "UTF-8");
		assertNotNull(fileObject);
		//file.delete();
	}

	@Test
	public void testFileObjectStringStringString1() throws Exception {
		File file = new File(basePath+"file.txt");
		FileUtils.createNewFile(file);
		FileUtils.write(file, "AAAAA");
		FileObject fileObject = new FileObject(basePath+"file.txt", "R", "UTF-8");
		//fileObject = new FileObject(basePath+"file.txt", "W", "utf-8");
		assertNotNull(fileObject);
		file.delete();
	}

	@Test
	public void testFileObjectStringStringString3() throws Exception {
		FileObject fileObject = new FileObject(basePath+"file.txt", "s", "utf-8");
		logger.debug(fileObject.getLine_end_string());
	}

	@Test(expected = Exception.class)
	public void testReadLine() throws Exception {
		File file = new File(basePath+"file.txt");
		FileUtils.createNewFile(file);
		FileUtils.write(file, "AAAAA");
		FileObject fileObject = new FileObject(basePath+"file.txt", "R");
		String readLine = fileObject.readLine();
		fileObject.close();
		readLine = fileObject.readLine();
	}

	@Test(expected=Exception.class)
	public void testWriteLine() throws Exception {
		File file = new File(basePath+"file.txt");
		FileUtils.createNewFile(file);
		//FileUtils.write(file, "AAAAA");
		FileObject fileObject = new FileObject(basePath+"file.txt", "w");
		fileObject.writeLine("DDDDDDDD");
		fileObject.close();
		fileObject.writeLine("sdsss");
	}

	@Test(expected=Exception.class)
	public void testFlushWriteBuffer() {
		FileObject fileObject = new FileObject(basePath+"file.txt", "w");
		fileObject.writeLine("sss");
		fileObject.flushWriteBuffer();
		fileObject.close();
		fileObject.flushWriteBuffer();
	}

	@Test
	public void testClose() {
		FileObject fileObject = new FileObject(basePath+"file.txt", "w");
		fileObject.writeLine("sss");
		fileObject.close();
		FileObject fileObject2 = new FileObject(basePath+"file.txt", "r");
		fileObject2.readLine();
		fileObject2.close();
	}

	@Test
	public void testGetAbsolutepath() {
		FileObject fileObject = new FileObject(basePath+"file.txt", "r");
		String absolutepath = fileObject.getAbsolutepath();
		assertNotNull(absolutepath);
	}

	@Test
	public void testGetFilename() {
		FileObject fileObject = new FileObject(basePath+"file.txt", "r");
		String filename = fileObject.getFilename();
		assertNotNull(filename);
	}

	@Test
	public void testGetLine_end_string() {
		FileObject fileObject = new FileObject(basePath+"file.txt", "r");
		fileObject.setLine_end_string("xx");
		String filename = fileObject.getLine_end_string();
		assertNotNull(filename);
	}

	@Test
	public void testSetLine_end_string() {
		FileObject fileObject = new FileObject(basePath+"file.txt", "w");
		fileObject.setLine_end_string("xx");
		File file=new File(basePath+"file.txt");
		file.delete();
	}

}
