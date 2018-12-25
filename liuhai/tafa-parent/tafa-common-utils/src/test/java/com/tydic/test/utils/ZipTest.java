package com.tydic.test.utils;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import com.tydic.traffic.tafa.utils.Zip;
import com.tydic.traffic.tafa.utils.file.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * ClassName:ZipTest <br/>
 */
public class ZipTest {
	private static final String dir=FileUtils.getAbsolutePathWithApp()+"\\Data\\zipTest";
	private static final String unzipPath= FileUtils.getAbsolutePathWithApp()+"\\Data\\zipTest\\unzip";
	private static final String path=FileUtils.getAbsolutePathWithApp()+"\\Data\\zipTest\\a.txt";
	private static final String path1=FileUtils.getAbsolutePathWithApp()+"\\Data\\zipTest\\b.txt";
	private static final String zipPath=FileUtils.getAbsolutePathWithApp()+"\\Data\\zipTest\\ziptest.zip";
	
	@Before
	public void createFile(){
		try {
			FileUtils.createNewFile(path);
			FileUtils.createNewFile(path1);
			FileUtils.write(new File(path), new StringReader("aaaaaaa"));
			FileUtils.write(new File(path), new StringReader("bbbbb"));
		}
		catch (IOException e) {
			

		}
	}
	
	@After
	public void deleteFile(){
			FileUtils.deleteDir(new File(dir),null);
	}
	

	@Test
	public void testZipFiles() {
		String[] src = { path, path1 };
		Zip.ZipFiles(null, zipPath);
		Zip.ZipFiles(new String[]{}, zipPath);
		String[] src1 = { path, path1};
		Zip.ZipFiles(src1, zipPath);
		Zip.ZipFiles(src, zipPath);
		assertTrue(new File(zipPath).exists());
	}

	@Test
	public void testUnZipFiles() {
		String[] src = { path, path1 };
		Zip.ZipFiles(src, zipPath);
		Zip.UnZipFiles(zipPath+"1", unzipPath);
		Zip.UnZipFiles(zipPath, unzipPath+"1");
		Zip.UnZipFiles(zipPath, unzipPath);
	}
	

}

