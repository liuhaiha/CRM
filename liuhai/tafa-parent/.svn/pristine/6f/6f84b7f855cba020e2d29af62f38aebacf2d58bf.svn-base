package com.tydic.traffic.tafa.utils.file;

import com.tydic.traffic.tafa.utils.charset.CharsetUtils;
import com.tydic.traffic.tafa.utils.io.StreamUtils;
import com.tydic.traffic.tafa.utils.string.StringUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.math.BigInteger;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * ClassName:FileUtils <br/>
 */
public class FileUtils {

	private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

	private static int cacheSize = 8192;

	/**
	 * The number of bytes in a kilobyte.
	 */
	public static final long ONE_KB = 1024;

	/**
	 * The number of bytes in a megabyte.
	 */
	public static final long ONE_MB = ONE_KB * ONE_KB;

	/**
	 * The number of bytes in a gigabyte.
	 */
	public static final long ONE_GB = ONE_KB * ONE_MB;

	/**
	 * The number of bytes in a terabyte.
	 */
	public static final long ONE_TB = ONE_KB * ONE_GB;

	/**
	 * The number of bytes in a petabyte.
	 */
	public static final long ONE_PB = ONE_KB * ONE_TB;

	/**
	 * The number of bytes in an exabyte.
	 */
	public static final long ONE_EB = ONE_KB * ONE_PB;

	/**
	 * The number of bytes in a zettabyte.
	 */
	public static final BigInteger ONE_ZB = BigInteger.valueOf(ONE_KB).multiply(BigInteger.valueOf(ONE_EB));

	/**
	 * The number of bytes in a yottabyte.
	 */
	public static final BigInteger ONE_YB = ONE_ZB.multiply(BigInteger.valueOf(ONE_EB));

	/**
	 * An empty array of type <code>File</code>.
	 */
	public static final File[] EMPTY_FILE_ARRAY = new File[0];

	/**
	 * The UTF-8 character set, used to decode octets in URLs.
	 */
	private static final Charset UTF8 = Charset.forName("UTF-8");

	/**
	 * 默认的过滤器
	 */
	private static FilenameFilter filter = null;
	
	/**
	 * 
	 * getAbsolutePathWithApp:(取得应用根目录的绝对路径). <br/>
	 */
	public static String getAbsolutePathWithApp(){
		return System.getProperty("user.dir");
	}
	
	/**
	 * 
	 * getAbsolutePathWithClass:(取得应用class目录的绝对路径). <br/>
	 */
	public static URL getAbsolutePathWithClass(){
		return FileUtils.class.getResource("/");
	}
	
	/**
	 * 
	 * getAbsolutePathWithClass:(取得应用class目录下的指定目录的绝对路径). <br/>
	 * @param path
	 */
	public static URL getAbsolutePathWithClass(String path){
		return FileUtils.class.getResource(path);
	}
	
	/**
	 * 
	 * getAbsolutePathWithClass:(取得指定类文件所在目录的绝对路径). <br/>
	 * @param clazz
	 */
	public static URL getAbsolutePathWithClass(Class clazz){
		return clazz.getResource("");
	}

	/**
	 * 
	 * getFileSizeStr:(把long个字节的文件大小转换成为可读性强的文件大小版本). <br/>
	 *
	 */
	public static String getFileSizeStr(long size) {
		String displaySize;

		if (size / ONE_GB > 0) {
			displaySize = String.valueOf(size / ONE_GB) + " GB";
		}
		else if (size / ONE_MB > 0) {
			displaySize = String.valueOf(size / ONE_MB) + " MB";
		}
		else if (size / ONE_KB > 0) {
			displaySize = String.valueOf(size / ONE_KB) + " KB";
		}
		else {
			displaySize = String.valueOf(size) + " bytes";
		}
		return displaySize;
	}

	/**
	 * 
	 * createFileIfNoExists:(创建一个不存在的文件). <br/>
	 */
	public static File createFileIfNoExists(String path) throws IOException {
		return createFileIfNoExists(new File(path));
	}

	/**
	 * 
	 * createFileIfNoExists:(创建一个不存在的文件). <br/>
	 */
	public static File createFileIfNoExists(File file) throws IOException {
		if (file == null || file.exists())
			return null;
		makeDir(file.getParentFile());
		boolean b = file.createNewFile();
		if (b)
			return file;
		else
			return null;
	}

	/**
	 * 
	 * createNewFile:(创建一个文件). <br/>
	 */
	public static boolean createNewFile(String fileName) throws IOException {
		return createNewFile(new File(fileName));
	}

	/**
	 * 创建一个文件（包括父类） 创建新文件，如果父目录不存在，也一并创建。可接受 null 参数
	 */
	public static boolean createNewFile(File file) throws IOException {
		return createFileIfNoExists(file) == null;
	}

	/**
	 * 创建新目录，如果父目录不存在，也一并创建。可接受 null 参数
	 */
	public static boolean makeDir(File dir) {
		if (dir == null || dir.exists())
			return false;
		return dir.mkdirs();
	}

	/**
	 * 
	 * getFileSize:(取得文件大小). <br/>
	 */
	public static long getFileSize(String fileName) throws Exception {
		return getFileSize(new File(fileName));
	}

	/**
	 * 
	 * getFileSize:(得到文件或者目录的大小). <br/>
	 */
	public static long getFileSize(File f) throws Exception {
		if (isFile(f))
			return f.length();
		long size = 0;
		if (!isDirectory(f))
			return size;
		File flist[] = f.listFiles();
		for (int i = 0; i < flist.length; i++)
			if (flist[i].isDirectory())
				size = size + getFileSize(flist[i]);
			else
				size = size + flist[i].length();
		return size;
	}

	/**
	 * 
	 * isDirectory:(文件对象是否是目录，可接受 null). <br/>
	 */
	public static boolean isDirectory(File f) {
		if (null == f)
			return false;
		if (!f.exists())
			return false;
		if (!f.isDirectory())
			return false;
		return true;
	}

	/**
	 * 
	 * isFile:(文件对象是否是文件，可接受 null). <br/>
	 */
	public static boolean isFile(File f) {
		return null != f && f.exists() && f.isFile();
	}

	/**
	 * 
	 * getFileList:(取得目录及子目录下的所有文件). <br/>
	 */
	public static List<String> getFileList(String strPath) {
		File dir = new File(strPath);
		File[] files = dir.listFiles();
		ArrayList<String> filelist = null;

		if (files != null) {
			filelist = new ArrayList<String>();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					filelist.addAll(getFileList(files[i].getAbsolutePath()));
				}
				else {
					filelist.add(files[i].getAbsolutePath());
				}
			}
		}
		return filelist;
	}

	/**
	 * 删除文件或者目录（文件夹）
	 */
	public static boolean delete(File f, FilenameFilter filter) {
		boolean bo = false;
		if (isFile(f))
			bo = f.delete();
		else if (isDirectory(f))
			bo = deleteDir(f, filter);
		return bo;
	}

	/**
	 * 删除一个目录包括目录下所有东西 如果不是目录或为null则返回false
	 */
	public static boolean deleteDir(File f, FilenameFilter filter) {
		if (!isDirectory(f))
			return false;
		boolean re = false;
		File[] fs = f.listFiles(filter);
		if (null != fs) {
			if (fs.length == 0)
				return f.delete();
			for (File file : fs) {
				if (file.isFile())
					re |= deleteFile(file);
				else
					re |= deleteDir(file, filter);
			}
			re |= f.delete();
		}
		return re;
	}

	/**
	 * 删除单个文件
	 */
	public static boolean deleteFile(File f) {
		if (isFile(f))
			return f.delete();
		return false;
	}

	/**
	 * 
	 * 复制文件（不包括目录） 默认如果目标父目录不存在，也一并创建
	 */
	public static boolean copyFile(File src, File target) throws IOException {
		return copyFile(src, target, cacheSize);
	}

	/**
	 * 复制文件（不包括目录） 默认如果目标父目录不存在，也一并创建
	 * @return 是否读取成功
	 */
	public static boolean copyFile(File src, File target, int cacheSize) throws IOException {
		if (!isFile(src) || target == null)
			return false;
		if (!target.exists())
			if (!createNewFile(target))
				return false;
		// 缓冲流
		BufferedInputStream inBuff = new BufferedInputStream(new FileInputStream(src));
		BufferedOutputStream outBuff = new BufferedOutputStream(new FileOutputStream(target));
		byte[] buff = new byte[cacheSize];
		int len = 0;
		while ((len = inBuff.read(buff)) != -1)
			outBuff.write(buff, 0, len);
		StreamUtils.safeClose(inBuff);
		StreamUtils.safeFlush(outBuff);
		StreamUtils.safeClose(outBuff);
		return target.setLastModified(src.lastModified());
	}

	/**
	 * 复制目录 默认如果目标父目录不存在，也一并创建
	 * @return 是否读取成功
	 */
	public static boolean copyDir(File src, File target) throws IOException {
		return copyDir(src, target, cacheSize);
	}

	/**
	 * 复制目录 默认如果目标父目录不存在，也一并创建
	 * @return 是否读取成功
	 */
	public static boolean copyDir(File src, File target, int cacheSize) throws IOException {
		if (src == null || target == null || !src.exists())
			return false;
		if (!src.isDirectory())
			throw new IOException(src.getAbsolutePath() + " should be a directory!");
		if (!target.exists())
			if (!makeDir(target))
				return false;
		boolean re = true;
		File[] files = src.listFiles();
		if (files != null)
			for (File f : files) {
				if (f.isFile())
					re &= copyFile(f, new File(target.getAbsolutePath() + "/" + f.getName()), cacheSize);
				else
					re &= copyDir(f, new File(target.getAbsolutePath() + "/" + f.getName()), cacheSize);
			}
		return re;
	}

	/**
	 * 复制文件或者目录 至于复制文件还是目录取决于源文件src 默认如果目标父目录不存在，也一并创建
	 * @return 是否读取成功
	 */
	public static boolean copy(File src, File target, int cacheSize) throws IOException {
		if (src == null || target == null || !src.exists())
			return false;
		boolean bo = false;
		if (src.isFile()) {
			bo |= copyFile(src, target, cacheSize);
		}
		else if (src.isDirectory()) {
			bo |= copyDir(src, target, cacheSize);
		}
		return bo;
	}

	/**
	 * 
	 * copy:(文件拷贝). <br/>
	 */
	public static boolean copy(File src, File target) throws IOException {
		return copy(src, target, cacheSize);
	}

	/**
	 * 移动一个文件 默认如果目标父目录不存在，也一并创建
	 *
	 */
	public static boolean moveFile(File src, File target, int cacheSize) throws IOException {
		if (!isFile(src) || target == null)
			return false;
		makeDir(target.getParentFile());
		boolean bo = src.renameTo(target);
		if (!bo) {
			bo |= copyFile(src, target, cacheSize);
			bo |= deleteFile(src);
		}
		return bo;
	}

	/**
	 * 移动一个文件 默认如果目标父目录不存在，也一并创建
	 *
	 */
	public static boolean moveFile(File src, File target) throws IOException {
		return moveFile(src, target, cacheSize);
	}

	/**
	 * 移动一个文件夹（目录） 默认如果目标父目录不存在，也一并创建
	 *
	 */
	public static boolean moveDir(File src, File target, int cacheSize) throws IOException {
		if (!isDirectory(src) || target == null)
			return false;
		makeDir(target.getParentFile());
		boolean bo = src.renameTo(target);
		if (!bo) {
			bo |= copyDir(src, target, cacheSize);
			bo |= deleteDir(src, filter);
		}
		return bo;
	}

	/**
	 * 移动一个文件夹（目录） 默认如果目标父目录不存在，也一并创建
	 *
	 */
	public static boolean moveDir(File src, File target) throws IOException {
		return moveDir(src, target, cacheSize);
	}

	/**
	 * 移动一个文件夹或者目录 默认如果目标父目录不存在，也一并创建
	 *
	 */
	public static boolean move(File src, File target, int cacheSize) throws IOException {
		if (src == null || target == null || !src.exists())
			return false;
		if (src.isFile()) {
			return moveFile(src, target, cacheSize);
		}
		else {
			return moveDir(src, target, cacheSize);
		}
	}

	/**
	 * 移动一个文件夹或者目录 默认如果目标父目录不存在，也一并创建
	 *
	 */
	public static boolean move(File src, File target) throws IOException {
		return move(src, target, cacheSize);
	}

	/**
	 * 将文件改名
	 * 
	 * @param src
	 *            文件
	 * @param newName
	 *            新名称
	 * @return 改名是否成功
	 */
	public static boolean rename(File src, String newName) {
		if (src == null || newName == null)
			return false;
		if (src.exists()) {
			File newFile = new File(src.getParent() + "/" + newName);
			if (newFile.exists())
				return false;
			makeDir(newFile.getParentFile());
			return src.renameTo(newFile);
		}
		return false;
	}

	/**
	 * 修改路径
	 * 
	 * @param path
	 *            路径
	 * @param newName
	 *            新名称
	 * @return 新路径
	 */
	public static String renamePath(String path, String newName) {
		if (!StringUtils.isEmpty(path)) {
			int pos = path.replace('\\', '/').lastIndexOf('/');
			if (pos > 0)
				return path.substring(0, pos) + "/" + newName;
		}
		return newName;
	}

	/**
	 * @param path
	 *            路径
	 * @return 父路径
	 */
	public static String getParent(String path) {
		if (StringUtils.isEmpty(path))
			return path;
		int pos = path.replace('\\', '/').lastIndexOf('/');
		if (pos > 0)
			return path.substring(0, pos);
		return "/";
	}

	/**
	 * @param path
	 *            全路径
	 * @return 文件或者目录名
	 */
	public static String getName(String path) {
		if (!StringUtils.isEmpty(path)) {
			int pos = path.replace('\\', '/').lastIndexOf('/');
			if (pos > 0)
				return path.substring(pos + 1);
		}
		return path;
	}

	/**
	 * 精确比较两个文件是否相等
	 * 
	 * @param f1
	 *            文件1
	 * @param f2
	 *            文件2
	 * @return 是否相等
	 */
	public static boolean isEquals(File f1, File f2) {
		if (!f1.isFile() || !f2.isFile())
			return false;
		InputStream ins1 = null;
		InputStream ins2 = null;
		try {
			ins1 = new BufferedInputStream(new FileInputStream(f1));
			ins2 = new BufferedInputStream(new FileInputStream(f2));
			return StreamUtils.equals(ins1,ins2);
		}
		catch (IOException e) {
			return false;
		}
		finally {
			StreamUtils.safeClose(ins1);
			StreamUtils.safeClose(ins2);
		}
	}

	/**
	 * 
	 * read:(读取 UTF-8 文件全部内容). <br/>
	 * @param file
	 * @return String
	 * @since 1.0
	 */
	public static String read(String file) {
		return read(file, CharsetUtils.UTF_8);
	}
	
	/**
	 * 原样读取文件全部内容
	 *            文件路径
	 * @return 文件内容
	 */
	public static String read(String file, String coding) {
		StringBuilder sb = new StringBuilder();

		InputStream is = null;
		InputStreamReader fileReader = null;
		BufferedReader br = null;
		try {
			is = FileUtils.findFileAsStream(file, FileUtils.class, coding);
			fileReader = new InputStreamReader(is);
			br = new BufferedReader(fileReader);
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line + StringUtils.CRIF);
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
	 * 读取 UTF-8 文件全部内容
	 * 
	 * @param f
	 *            文件
	 * @return 文件内容
	 */
	public static String read(File f) {
		try {
			return StreamUtils.read(StreamUtils.fileInr(f)).toString();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将内容写到一个文件内，内容对象可以是：
	 * <ul>
	 * <li>InputStream - 按二进制方式写入
	 * <li>byte[] - 按二进制方式写入
	 * <li>Reader - 按 UTF-8 方式写入
	 * <li>其他对象被 toString() 后按照 UTF-8 方式写入
	 * </ul>
	 * 
	 * @param path
	 *            文件路径，如果不存在，则创建
	 * @param obj
	 *            内容对象
	 */
	public static void write(String path, Object obj) {
		if (null == path || null == obj)
			return;
		try {
			write(createFileIfNoExists(path), obj);
		}
		catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 将内容写到一个文件内，内容对象可以是：
	 * 
	 * <ul>
	 * <li>InputStream - 按二进制方式写入
	 * <li>byte[] - 按二进制方式写入
	 * <li>Reader - 按 UTF-8 方式写入
	 * <li>其他对象被 toString() 后按照 UTF-8 方式写入
	 * </ul>
	 * 
	 * @param f
	 *            文件
	 * @param obj
	 *            内容
	 */
	public static void write(File f, Object obj) {
		if (null == f || null == obj)
			return;
		if (f.isDirectory()) {
			logger.warn("Directory '%s' can not be write as File");
			return;
		}

		try {
			// 保证文件存在
			if (!f.exists())
				createNewFile(f);
			// 输入流
			if (obj instanceof InputStream) {
				StreamUtils.writeAndClose(StreamUtils.fileOut(f), (InputStream) obj);
			}
			// 字节数组
			else if (obj instanceof byte[]) {
				StreamUtils.writeAndClose(StreamUtils.fileOut(f), (byte[]) obj);
			}
			// 文本输入流
			else if (obj instanceof Reader) {
				StreamUtils.writeAndClose(StreamUtils.fileOutw(f), (Reader) obj);
			}
			// 其他对象
			else {
				StreamUtils.writeAndClose(StreamUtils.fileOutw(f), obj.toString());
			}
		}
		catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 获取输出流
	 * 
	 * @param path
	 *            文件路径
	 * @param klass
	 *            参考的类， -- 会用这个类的 ClassLoader
	 * @param enc
	 *            文件路径编码
	 * 
	 * @return 输出流
	 */
	public static InputStream findFileAsStream(String path, Class<?> klass, String enc) {
		File f = new File(path);
		if (f.exists())
			try {
				return new FileInputStream(f);
			}
			catch (FileNotFoundException e1) {
				return null;
			}
		if (null != klass) {
			InputStream ins = klass.getClassLoader().getResourceAsStream(path);
			if (null == ins)
				ins = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
			if (null != ins)
				return ins;
		}
		return ClassLoader.getSystemResourceAsStream(path);
	}

	/**
	 * 获取输出流
	 * 
	 * @param path
	 *            文件路径
	 * @param enc
	 *            文件路径编码
	 * 
	 * @return 输出流
	 */
	public static InputStream findFileAsStream(String path, String enc) {
		return findFileAsStream(path, FileUtils.class, enc);
	}

	/**
	 * 获取输出流
	 * 
	 * @param path
	 *            文件路径
	 * @param klass
	 *            参考的类， -- 会用这个类的 ClassLoader
	 * 
	 * @return 输出流
	 */
	public static InputStream findFileAsStream(String path, Class<?> klass) {
		return findFileAsStream(path, klass, UTF8.name());
	}

	/**
	 * 获取输出流
	 * 
	 * @param path
	 *            文件路径
	 * 
	 * @return 输出流
	 */
	public static InputStream findFileAsStream(String path) {
		return findFileAsStream(path, FileUtils.class, UTF8.name());
	}

	/**
	 * 从 CLASSPATH 下寻找一个文件
	 * 
	 * @param path
	 *            文件路径
	 * @param klassLoader
	 *            参考 ClassLoader
	 * @param enc
	 *            文件路径编码
	 * 
	 * @return 文件对象，如果不存在，则为 null
	 */
	public static File findFile(String path, ClassLoader klassLoader, String enc) {
		if (null == path)
			return null;
		return new File(path);
	}

	/**
	 * 从 CLASSPATH 下寻找一个文件
	 * 
	 * @param path
	 *            文件路径
	 * @param enc
	 *            文件路径编码
	 * @return 文件对象，如果不存在，则为 null
	 */
	public static File findFile(String path, String enc) {
		return findFile(path, FileUtils.class.getClassLoader(), enc);
	}

	/**
	 * 从 CLASSPATH 下寻找一个文件
	 * 
	 * @param path
	 *            文件路径
	 * @param klassLoader
	 *            使用该 ClassLoader进行查找
	 * 
	 * @return 文件对象，如果不存在，则为 null
	 */
	public static File findFile(String path, ClassLoader klassLoader) {
		return findFile(path, klassLoader, UTF8.name());
	}

	/**
	 * 从 CLASSPATH 下寻找一个文件
	 * 
	 * @param path
	 *            文件路径
	 * 
	 * @return 文件对象，如果不存在，则为 null
	 */
	public static File findFile(String path) {
		return findFile(path, FileUtils.class.getClassLoader(), UTF8.name());
	}

	/**
	 * 在文件尾追加数据(关闭流)
	 * 
	 * @param path
	 * @param tempByte
	 */
	public static boolean skipFileAndClose(String path, byte[] tempByte) {
		return skipFileAndClose(new File(path), tempByte);
	}

	/**
	 * 在文件尾追加数据(关闭流)
	 * 
	 * @param file
	 * @param tempByte
	 */
	public static boolean skipFileAndClose(File file, byte[] tempByte) {
		return StreamUtils.safeClose(skipFile(file, tempByte));
	}

	/**
	 * 在文件尾追加数据(不关闭流)
	 * 
	 * @param fileName
	 * @param tempByte
	 */
	public static RandomAccessFile skipFile(String fileName, byte[] tempByte) {
		return skipFile(new File(fileName), tempByte);
	}

	/**
	 * 在文件尾追加数据(不关闭流)
	 * 
	 * @param file
	 * @param tempByte
	 * @return
	 */
	public static RandomAccessFile skipFile(File file, byte[] tempByte) {
		if (!isFile(file) || tempByte == null) {
			System.out.println("文件不存在，或者byte字符串为null");
			return null;
		}
		// 打开一个随机访问文件流，按读写方式 如果该文件尚不存在，则尝试创建该文件
		// "rw"可以查aip知道意思 读写的意思
		RandomAccessFile randomFile = null;
		try {
			randomFile = new RandomAccessFile(file, "rw");
			skipFile(randomFile, tempByte, randomFile.length());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return randomFile;
	}

	public static boolean skipFileAndClose(String fileName, byte[] tempByte, long seek) {
		return StreamUtils.safeClose(skipFile(new File(fileName), tempByte, seek));
	}

	public static RandomAccessFile skipFile(String fileName, byte[] tempByte, long seek) {
		return skipFile(new File(fileName), tempByte, seek);
	}

	/**
	 * 跳到某个地方添加数据不关闭流
	 * 
	 * @param file
	 * @param tempByte
	 * @param seek
	 *            =跳转到的字节数
	 * @return
	 */
	public static RandomAccessFile skipFile(File file, byte[] tempByte, long seek) {
		if (!isFile(file) || tempByte == null) {
			System.out.println("文件不存在，或者byte字符串为null");
			return null;
		}
		// 打开一个随机访问文件流，按读写方式 如果该文件尚不存在，则尝试创建该文件
		// "rw"可以查aip知道意思 读写的意思
		RandomAccessFile randomFile = null;
		try {
			randomFile = new RandomAccessFile(file, "rw");
			skipFile(randomFile, tempByte, seek);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return randomFile;
	}

	public static boolean skipFileAndClose(File file, byte[] tempByte, long seek) {
		return StreamUtils.safeClose(skipFile(file, tempByte, seek));
	}

	/**
	 * 跳到某个地方添加数据不关闭流
	 * 
	 * @param randomFile
	 * @param tempByte
	 * @return
	 */
	public static RandomAccessFile skipFile(RandomAccessFile randomFile, byte[] tempByte, long seek) {
		try {
			// 将写文件指针移到文件尾。
			randomFile.seek(seek);
			randomFile.write(tempByte);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return randomFile;
	}

	/**
	 * 跳到某个地方添加数据关闭流
	 * 
	 * @param randomFile
	 * @param tempByte
	 * @param seek
	 */
	public static boolean skipFileAndClose(RandomAccessFile randomFile, byte[] tempByte, long seek) {
		return StreamUtils.safeClose(skipFile(randomFile, tempByte, seek));
	}

	/**
	 * 大文件处理 写入的文件从position开始 （修改tempBytes个字节的文本）
	 * 
	 * @param file
	 * @param size
	 *            =内存映射的大小
	 * @param tempBytes
	 *            =要传入的byte[]
	 * @return
	 */
	public static MappedByteBuffer mappedByteBuffer(File file, int position, int size, byte[] tempBytes) {
		if (tempBytes == null || !isFile(file) || size < tempBytes.length)
			return null;
		MappedByteBuffer out = null;
		try {
			out = new RandomAccessFile(file, "rw").getChannel().map(FileChannel.MapMode.READ_WRITE, position, size);
			out.put(tempBytes);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return out;
	}

	/**
	 * 
	 * getExtName:(取得文件扩展名). <br/>
	 * 
	 * @param filename
	 * @return String
	 * @since 1.0
	 */
	public static String getExtName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	/**
	 * 
	 * getFileNameNoExt:(获取不带扩展名的文件名). <br/>
	 * 
	 * @param filename
	 * @return String
	 * @since 1.0
	 */
	public static String getFileNameNoExt(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			String name = filename;
			int separator = filename.lastIndexOf(File.separator);
			if ((separator > -1) && (separator < (filename.length()))) {
				name = filename.substring(separator + 1, filename.length());
			}
			int dot = name.lastIndexOf('.');
			if ((dot > -1) && (dot < (name.length()))) {
				return name.substring(0, dot);
			}
		}
		return filename;
	}

	/**
	 * 
	 * getFilePath:(得到文件路径). <br/>
	 * 
	 * @param fullFileName
	 * @return String
	 * @since 1.0
	 */
	public static String getFilePath(String fullFileName) {
		if ((fullFileName != null) && (fullFileName.length() > 0)) {
			File f = new File(fullFileName);
			return f.getParent();
		}
		return fullFileName;
	}

	/**
	 * 
	 * getFileType:(根据文件头取得文件类型). <br/>
	 * 
	 * @param filePath
	 * @return String[] String[0]:文件类型； String[1]:文件扩展名
	 * @since 1.0
	 */
	public static String[] getFileType(String filePath) {
		String[] types = new String[2];
		if (StringUtils.isEmpty(filePath)) {
			return null;
		}
		try {
			String extName = getExtName(filePath);
			String fileType = FileTypeJudge.getType(filePath, extName);
			types[1] = extName;
			if (null == fileType) {
				types[0] = null;
			}
			else {
				types[0] = fileType;
			}
		}
		catch (IOException e) {
			logger.error("get file type IOException:", e);
		}
		return types;
	}

	/**
	 * 
	 * getFileType:(根据文件头得到文件类型). <br/>
	 * 
	 * @param is
	 *            文件输入流
	 * @param extName
	 *            文件扩展名
	 * @return String[] String[0]:文件类型； String[1]:文件扩展名
	 * @since 1.0
	 */
	public static String[] getFileType(final InputStream is, String extName) {
		String[] types = new String[2];
		if (null == is || StringUtils.isEmpty(extName)) {
			return null;
		}
		try {
			String fileType = FileTypeJudge.getType(is, extName);
			types[1] = extName;
			if (null == fileType) {
				types[0] = null;
			}
			else {
				types[0] = fileType;
			}
		}
		catch (IOException e) {
			logger.error("get file type IOException:", e);
		}
		return types;
	}

	/**
	 * 查找文件（支持“?”，“*”通配符查找文件）。
	 * 
	 * @param baseDirName
	 *            待查找的目录
	 * @param targetFileName
	 *            目标文件名，支持通配符形式
	 * @param count
	 *            期望结果数目，如果畏0，则表示查找全部。
	 * @return 满足查询条件的文件名列表
	 */
	public static List findFiles(String baseDirName, String targetFileName, int count) {
		/**
		 * 算法简述： 从某个给定的需查找的文件夹出发，搜索该文件夹的所有子文件夹及文件，
		 * 若为文件，则进行匹配，匹配成功则加入结果集，若为子文件夹，则进队列。 队列不空，重复上述操作，队列为空，程序结束，返回结果。
		 */
		List fileList = new ArrayList();
		// 判断目录是否存在
		File baseDir = new File(baseDirName);
		if (!baseDir.exists() || !baseDir.isDirectory()) {
			System.out.println("文件查找失败：" + baseDirName + "不是一个目录！");
			return fileList;
		}
		String tempName = null;
		// 创建一个队列，Queue在第四章有定义
		Queue<File> queue = new LinkedList();// 实例化队列
		queue.offer(baseDir); // 入队
		File tempFile = null;
		while (!queue.isEmpty()) {
			// 从队列中取目录
			tempFile = queue.poll();
			if (tempFile.exists() && tempFile.isDirectory()) {
				File[] files = tempFile.listFiles();
				for (int i = 0; i < files.length; i++) {
					// 如果是目录则放进队列
					if (files[i].isDirectory()) {
						queue.offer(files[i]);
					}
					else {
						// 如果是文件则根据文件名与目标文件名进行匹配
						tempName = files[i].getName();
						if (wildcardMatch(targetFileName, tempName)) {
							// 匹配成功，将文件名添加到结果集
							fileList.add(files[i].getAbsoluteFile());
							// 如果已经达到指定的数目，则退出循环
							if ((count != 0) && (fileList.size() >= count)) {
								return fileList;
							}
						}
					}
				}
			}
		}

		return fileList;
	}

	/**
	 * 通配符匹配
	 * 
	 * @param pattern
	 *            通配符模式
	 * @param str
	 *            待匹配的字符串
	 * @return 匹配成功则返回true，否则返回false
	 */
	private static boolean wildcardMatch(String pattern, String str) {
		int patternLength = pattern.length();
		int strLength = str.length();
		int strIndex = 0;
		char ch;
		for (int patternIndex = 0; patternIndex < patternLength; patternIndex++) {
			ch = pattern.charAt(patternIndex);
			if (ch == '*') {
				// 通配符星号*表示可以匹配任意多个字符
				while (strIndex < strLength) {
					if (wildcardMatch(pattern.substring(patternIndex + 1), str.substring(strIndex))) {
						return true;
					}
					strIndex++;
				}
			}
			else if (ch == '?') {
				// 通配符问号?表示匹配任意一个字符
				strIndex++;
				if (strIndex > strLength) {
					// 表示str中已经没有字符匹配?了。
					return false;
				}
			}
			else {
				if ((strIndex >= strLength) || (ch != str.charAt(strIndex))) {
					return false;
				}
				strIndex++;
			}
		}
		return (strIndex == strLength);
	}
	
	/**
     * 判断访问路径是否匹配过滤条件（路径通配符支持“?”、“*”、“**”，通配符匹配不包括目录分隔符“/”：）
     * 配置路径中
     * 			"?"：匹配一个字符，" 
     * 			 "*":为通配符，匹配零个或多个字符串，用在结尾处，表示匹配该目录下的所有文件；
     *           "**":为通配符，匹配路径中的零个或多个目录，不可以应用在路径结尾; 
     * @param url   访问路径    
     * @param path  可包含通配符的配置路径
     * @return              匹配结果
     */
	public static boolean isFullMatching(String url, String path) {
		String[] urls = url.split("/");
		String[] paths = path.split("/");
		int urlLength = urls.length;
		int pathLength = paths.length;
		int urlNum = 0;
		for (int pathNum = 0; pathNum < pathLength; pathNum++) {
			if (paths[pathNum].equals("**")) {
				if (pathNum == pathLength - 1) {
					return false;
				}
				pathNum++;
				while (true) {
					if (urlNum == urlLength - 1) {
						if (pathNum == pathLength - 1 && isEndMatching(urls[urlNum], paths[pathNum])) {
							return true;
						}
						else {
							return false;
						}
					}
					if (isMatching(urls[urlNum], paths[pathNum])) {
						urlNum++;
						break;
					}
					urlNum++;
				}
			}
			else if (pathNum == pathLength - 1 && paths[pathNum].equals("*")) {
				return true;
			}
			else {
				if (urlNum == urlLength - 1) {
					if (pathNum == pathLength - 1 && isEndMatching(urls[urlNum], paths[pathNum])) {
						return true;
					}
					else {
						return false;
					}
				}
				else {
					if (!isMatching(urls[urlNum], paths[pathNum])) {
						return false;
					}
				}
				urlNum++;
			}
		}
		return true;
	}
	
	private static boolean isEndMatching(String url, String path) {
		if (isMatching(url, path)) {
			return true;
		}
		String[] urls = url.split("[.]");
		String[] paths = path.split("[.]");
		int ul = urls.length;
		int pl = paths.length;
		if (ul != pl) {
			return false;
		}
		for (int i = 0; i < ul; i++) {
			if (!isMatching(urls[i], paths[i])) {
				return false;
			}
		}
		return true;
	}

	private static boolean isMatching(String url, String path) {
		return StringUtils.isMatching(url, path);
	}
	
	/**
	 * 
	 * dirIsExist:(目录是否存在). <br/>
	 * @param _strDir
	 * @return
	 * @since 1.0
	 */
	public static boolean dirIsExist(String _strDir){
		File dir = new File(_strDir);
		if(dir.exists())	return true;
		
		return false;
	}
}
