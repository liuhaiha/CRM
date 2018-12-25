package com.tydic.traffic.tafa.utils;

import com.tydic.traffic.tafa.utils.file.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * ClassName:Zip
 */
public class Zip {

	private static final Logger logger = LoggerFactory.getLogger(Zip.class);

	/**
	 * 
	 * ZipFiles:(生成ZIP文件). <br/>
	 * @param srcfilepaths
	 * 				需要压缩的文件绝对路径列表
	 * @param zipfilepath
	 * 				压缩后的文件绝对路径
	 * @since 1.0
	 */
	public static void ZipFiles(String[] srcfilepaths, String zipfilepath) {
		if (srcfilepaths == null) {
			return;
		}
		if (srcfilepaths.length == 0) {
			return;
		}

		ZipOutputStream out = null;
		FileInputStream in = null;
		byte[] buf = new byte[1024]; 

		try {
			FileUtils.deleteFile(new File(zipfilepath));
			FileUtils.makeDir(new File(FileUtils.getFilePath(zipfilepath)));

			// Create the ZIP file
			out = new ZipOutputStream(new FileOutputStream(zipfilepath));
			// Compress the files
			for (int i = 0; i < srcfilepaths.length; i++) {
				File srcfile = new File(srcfilepaths[i]);
				in = new FileInputStream(srcfile);
				// Add ZIP entry to output stream.
				out.putNextEntry(new ZipEntry(srcfile.getName()));
				// Transfer bytes from the file to the ZIP file
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				// Complete the entry
				out.closeEntry();
				in.close();
			}
			// Complete the ZIP file
			out.close();
		}
		catch (IOException e) {
			logger.error(e.getMessage());
		}
		finally {
			try {
				if (out != null) {
					out.closeEntry();
					out.close();
				}
				if (in != null) {
					in.close();
				}
			}
			catch (Exception e) {
			}
		}
	}

	/**
	 * 
	 * UnZipFiles:(解压ZIP文件). <br/>
	 * @param zipfilepath
	 *            要解压缩的文件的绝对路径
	 *            　解压缩后的文件保存路径
	 * @since 1.0
	 */
	public static void UnZipFiles(String zipfilepath, String descdir) {
		ZipFile zf = null;
		InputStream in = null;
		OutputStream out = null;
		try {
			File zipFile = new File(zipfilepath);
			if(!zipFile.exists()){
				logger.info(zipfilepath + " is not exists.");
				return;
			}			
			
			File dir = new File(descdir);
			if (!dir.exists()) {
				dir.mkdir();
			}

			// Open the ZIP file
			zf = new ZipFile(zipfilepath);
			for (Enumeration<?> entries = zf.entries(); entries.hasMoreElements();) {
				// Get the entry name
				ZipEntry entry = ((ZipEntry) entries.nextElement());
				String zipEntryName = entry.getName();
				in = zf.getInputStream(entry);

				out = new FileOutputStream(descdir + File.separator + zipEntryName);
				byte[] buf1 = new byte[1024];
				int len;
				while ((len = in.read(buf1)) > 0) {
					out.write(buf1, 0, len);
				}
				// Close the file and stream
				in.close();
				out.close();
			}
		}
		catch (IOException e) {
			logger.error(e.getMessage());
		}
		finally {
			try {
				if (zf != null) {
					zf.close();
				}
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			}
			catch (Exception e) {
			}
		}
	}

}
