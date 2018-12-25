package com.tydic.traffic.tafa.utils.file;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * ClassName:FileLock <br/>
 * Reason: 文件进程锁 <br/>
 */
public class FileLock {

	private static final Logger logger = LoggerFactory.getLogger(FileLock.class);

	private RandomAccessFile ranStream = null;

	private FileChannel filechannel = null;

	private String filepath = null;

	public FileLock(String filepath) {
		super();
		this.filepath = filepath;
	}

	public String getFilepath() {
		return filepath;
	}

	/**
	 * 
	 * blockLock:(阻塞方式进行文件加锁（会一直等待文件加锁成功才返回）). <br/>
	 * @return boolean
	 * @since 1.0
	 */
	public boolean blockLock() {
		if (ranStream != null || filechannel != null) {
			logger.info("block lock error! " + filepath + " is already locked!");
			return false;
		}

		try {
			logger.info("block lock " + filepath + " ... ");
			File lockfile = new File(filepath);
			ranStream = new RandomAccessFile(lockfile, "rw");
			filechannel = ranStream.getChannel();
			filechannel.lock();
			logger.info("block lock " + filepath + " Success.");
		}
		catch (Exception e) {
			try {
				if (filechannel != null) {
					filechannel.close();
				}
				if (ranStream != null) {
					ranStream.close();
				}
			}
			catch (Exception e2) {
			}
			filechannel = null;
			ranStream = null;
			logger.info("block lock " + filepath + " Fail.");
			return false;
		}
		return true;
	}

	/**
	 * 
	 * nonBlockLock:(非阻塞方式进行文件加锁（立即返回）). <br/>
	 * @return boolean
	 * @since 1.0
	 */
	public boolean nonBlockLock() {
		if (ranStream != null || filechannel != null) {
			logger.info("non-block lock error! " + filepath + " is already locked!");
			return false;
		}

		try {
			logger.info("non-block lock " + filepath + " ... ");
			File lockfile = new File(filepath);
			ranStream = new RandomAccessFile(lockfile, "rw");
			filechannel = ranStream.getChannel();
			if (filechannel.tryLock() != null) {
				logger.info("non-block lock " + this.filepath + " Success.");
				return true;
			}
			else {
				logger.info("non-block lock " + this.filepath + " Fail.");
				return false;
			}
		}
		catch (Exception e) {
			try {
				if (filechannel != null) {
					filechannel.close();
				}
				if (ranStream != null) {
					ranStream.close();
				}
			}
			catch (Exception e2) {
			}
			filechannel = null;
			ranStream = null;
			logger.info("non-block lock " + this.filepath + " Fail.");
			return false;
		}
	}

	/**
	 * 
	 * unlock:(文件解锁). <br/> void
	 * @since 1.0
	 */
	public void unlock() {
		if (ranStream == null || filechannel == null) {
			// logger.info(filepath + " is not locked!");
			return;
		}

		try {
			filechannel.close();
			ranStream.close();
			filechannel = null;
			ranStream = null;
			logger.info("unlock " + filepath + " Success.");
		}
		catch (Exception e) {
			logger.info("unlock " + filepath + " error! " + e.getMessage());
			throw new RuntimeException(e);
		}
	}
}
