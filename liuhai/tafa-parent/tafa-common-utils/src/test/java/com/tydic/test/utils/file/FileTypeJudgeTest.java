package com.tydic.test.utils.file;

import com.tydic.traffic.tafa.utils.file.FileTypeJudge;
import com.tydic.traffic.tafa.utils.file.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;



/**
 * ClassName:FileTypeJudgeTest
 */
public class FileTypeJudgeTest {
	static String basePath= FileUtils.getAbsolutePathWithApp()+"/Data/";
	@Test
	public void testGetTypeStringString() throws Exception {
		FileTypeJudge.getType(basePath+"file.txt", "txt");
	}

	@Test
	public void testGetTypeInputStreamString() throws Exception {
		File file=new File(basePath+"file.txt");
		FileInputStream ins=new FileInputStream(file);
		String type = FileTypeJudge.getType(ins, "txt");
		FileUtils.write(file, "xxxxxx");
		ins=new FileInputStream(file);
		type = FileTypeJudge.getType(ins, "txt");
		System.out.println(type);
		file.delete();
	}

}

