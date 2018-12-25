package com.tydic.traffic.tafa.utils.file;


import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPTransferType;
import com.tydic.traffic.tafa.utils.CommonUtils;
import com.tydic.traffic.tafa.utils.SystemUtils;
import com.tydic.traffic.tafa.utils.string.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:FtpUtils <br/>
 * Function: FTP操作工具类.
 */
public class FtpUtils {

	private static final Logger logger = LoggerFactory.getLogger(FtpUtils.class);

	private static final int FTP_TIMEOUT = 30000;

	public static final int FTP_OK = 0;

	public static final int FTP_CONNECT_ERROR = -1;

	public static final int FTP_LOGIN_ERROR = -2;

	public static final int FTP_LOCAL_FILE_ERROR = -3;

	public static final int FTP_LOGOUT_ERROR = -4;

	public static final int FTP_TRANSFER_ERROR = -9;

	public static final int FTP_NO_FILE_TRANSFER = 99;

	/**
	 * 
	 * ftpPutFile:(上传文件至FTP). <br/>
	 * @param _strHost	ftp主机IP
	 * @param _strPort	ftp端口
	 * @param _strFtpUser	ftp用户名
	 * @param _strFtpPwd	ftp密码
	 * @param _strSrcFiles	上传文件列表
	 * @return
	 * @since 1.0
	 */
	public static int ftpPutFile(String _strHost, String _strPort, String _strFtpUser, String _strFtpPwd,
                                 String[] _strSrcFiles) {
		return ftpPutFile(_strHost, _strPort, _strFtpUser, _strFtpPwd, _strSrcFiles, "");
	}

	/**
	 * 
	 * ftpPutFile:(上传文件至FTP). <br/>
	 * @param _strHost	ftp主机IP
	 * @param _strPort	ftp端口
	 * @param _strFtpUser	ftp用户名
	 * @param _strFtpPwd	ftp密码
	 * @param _strSrcFiles	上传文件列表
	 * @param _strEntryDesDir	上传文件目的文件夹
	 * @return
	 * @since 1.0
	 */
	public static int ftpPutFile(String _strHost, String _strPort, String _strFtpUser, String _strFtpPwd,
                                 String[] _strSrcFiles, String _strEntryDesDir) {
		int iRtn = FTP_NO_FILE_TRANSFER;
		logger.info("ip=" + _strHost + ", port=" + _strPort + ", user=" + _strFtpUser + ", passwd=" + _strFtpPwd
				+ ", files=" + _strSrcFiles.length + ", entryDir=" + _strEntryDesDir);
		String strfilename = "";
		if (_strSrcFiles.length <= 0) {
			return iRtn;
		}

		FTPClient ftp = null;

		try {
			iRtn = FTP_CONNECT_ERROR;
			ftp = new FTPClient();
			ftp.setRemoteHost(_strHost);
			ftp.setRemotePort(Integer.parseInt(_strPort));
			ftp.setTimeout(FTP_TIMEOUT);
			ftp.connect();
			iRtn = FTP_LOGIN_ERROR;
			ftp.login(_strFtpUser, _strFtpPwd);
			logger.info("Login OK![" + _strHost + ":" + _strPort + ":" + _strFtpUser + "]");
			ftp.setType(FTPTransferType.BINARY);
			if (_strEntryDesDir.trim().length() > 0) {
				String[] strDirs = _strEntryDesDir.split("/", -1);
				if (strDirs != null) {
					String strCurDir = "";
					for (int i = 0; i < strDirs.length; i++) {
						if (strCurDir.length() > 0) {
							strCurDir = strCurDir + "/" + strDirs[i];
						}
						else {
							strCurDir = strDirs[i];
						}
						try {
							ftp.mkdir(strDirs[i]);
							logger.info("Create Dir : " + strCurDir + " OK!");
						}
						catch (FTPException ex1) {
						}
						ftp.chdir(strDirs[i]);
					}
					logger.info("Change Dir : " + strCurDir + " OK!");

				}
			}

			ftp.setConnectMode(FTPConnectMode.PASV);

			for (int i = 0; i < _strSrcFiles.length; i++) {
				if (_strSrcFiles[i].length() <= 0) {
					continue;
				}
				else {
					if (!FileUtils.dirIsExist(_strSrcFiles[i])) {
						iRtn = FTP_LOCAL_FILE_ERROR;
						throw new IOException(_strSrcFiles[i] + " : not such file!");
					}
				}

				int index = _strSrcFiles[i].lastIndexOf(File.separator);
				strfilename = _strSrcFiles[i].substring(index + 1);
				iRtn = FTP_TRANSFER_ERROR;
				ftp.put(_strSrcFiles[i], strfilename);
				logger.info("Put file " + _strSrcFiles[i] + " OK!");
			}
			iRtn = FTP_LOGOUT_ERROR;
			ftp.quit();
			logger.info("Logout!" + "\n");
			iRtn = FTP_OK;
		}
		catch (FTPException ex) {
			logger.error("ftpPutFile : " + ex.getMessage());
		}
		catch (IOException ex1) {
			logger.error("ftpPutFile : " + ex1.getMessage());
		}
		catch (NumberFormatException ex2) {
			logger.error("ftpPutFile : " + ex2.getMessage());
		}

		logger.info("ftpFile end. iRtn = " + iRtn);
		return iRtn;
	}

	/**
	 * 
	 * ftpGetFileNameList:(取得ftp指定目录文件列表). <br/>
	 * @param _strHost	ftp主机IP
	 * @param _strPort	ftp端口
	 * @param _strFtpUser	ftp用户名
	 * @param _strFtpPwd	ftp密码
	 * @param _strEntryDesDir	ftp指定文件夹
	 * @param _strFileName		文件名表达式
	 * @return
	 * @since 1.0
	 */
	public static List ftpGetFileNameList(String _strHost, String _strPort, String _strFtpUser, String _strFtpPwd,
                                          String _strEntryDesDir, String _strFileName) {

		List<String> fileNameList = new ArrayList<String>();

		logger.info("ip=" + _strHost + ", port=" + _strPort + ", user=" + _strFtpUser + ", passwd=" + _strFtpPwd
				+ ", entryDir=" + _strEntryDesDir + ", file=" + _strFileName);

		if (_strFileName == null) {
			return null;
		}

		FTPClient ftp = null;

		try {
			ftp = new FTPClient();
			ftp.setRemoteHost(_strHost);
			ftp.setRemotePort(Integer.parseInt(_strPort));
			ftp.setTimeout(FTP_TIMEOUT);
			ftp.connect();
			ftp.login(_strFtpUser, _strFtpPwd);
			logger.info("Login OK![" + _strHost + ":" + _strPort + ":" + _strFtpUser + "]");
			ftp.setType(FTPTransferType.BINARY);
			if (_strEntryDesDir.trim().length() > 0 && !_strEntryDesDir.trim().equals(".")) {
				if (_strEntryDesDir.substring(0, 1).equals("/")) { // 绝对路径
					ftp.chdir(_strEntryDesDir.trim());
					logger.info("Change Dir : " + _strEntryDesDir.trim() + " OK!");
				}
				else { // 相对路径
					String[] strDirs = _strEntryDesDir.split("/", -1);
					if (strDirs != null) {
						String strCurDir = "";
						for (int i = 0; i < strDirs.length; i++) {
							if (strCurDir.length() > 0) {
								strCurDir = strCurDir + "/" + strDirs[i];
							}
							else {
								strCurDir = strDirs[i];
							}
							try {
								ftp.mkdir(strDirs[i]);
								logger.info("Create Dir : " + strCurDir + " OK!");
							}
							catch (FTPException ex1) {
							}
							ftp.chdir(strDirs[i]);
						}
						logger.info("Change Dir : " + strCurDir + " OK!");
					}
				}
			}

			// ftp.setConnectMode(FTPConnectMode.PASV);
			ftp.setConnectMode(FTPConnectMode.ACTIVE);

			String[] remortFileNames = ftp.dir(_strFileName + "*");
			for (int i = 0; i < remortFileNames.length; i++) {
				String fileName = remortFileNames[i];
				fileNameList.add(fileName);
			}

			ftp.quit();
			logger.info("Logout!" + "\n");
		}
		catch (FTPException ex) {
			if (ex.getMessage().indexOf("No such file") >= 0) {
				return fileNameList;
			}
			else if (ex.getMessage().indexOf("Bad directory") >= 0) {
				return fileNameList;
			}
			else if (ex.getMessage().indexOf("Unexpected null reply received") >= 0) {
				return fileNameList;
			}
			else {
				logger.info("ftpGetFileNameList :" + ex.getMessage());
				return null;
			}
		}
		catch (IOException ex1) {
			logger.info("ftpGetFileNameList :" + ex1.getMessage());
			return null;
		}
		catch (NumberFormatException ex2) {
			logger.info("ftpGetFileNameList :" + ex2.getMessage());
			return null;
		}

		logger.info("ftpGetFileNameList end. get filenames num=" + fileNameList.size());
		return fileNameList;
	}

	/**
	 * 
	 * ftpGetDirList:(取得ftp指定目录列表). <br/>
	 * @param _strHost
	 * @param _strPort
	 * @param _strFtpUser
	 * @param _strFtpPwd
	 * @param _strEntryDesDir
	 * @return
	 * @throws ParseException
	 * @since 1.0
	 */
	public static List ftpGetDirList(String _strHost, String _strPort, String _strFtpUser, String _strFtpPwd,
                                     String _strEntryDesDir) throws ParseException {

		List<String> fileNameList = new ArrayList<String>();

		logger.info("ip=" + _strHost + ", port=" + _strPort + ", user=" + _strFtpUser + ", passwd=" + _strFtpPwd
				+ ", entryDir=" + _strEntryDesDir);

		FTPClient ftp = null;

		try {
			ftp = new FTPClient();
			ftp.setRemoteHost(_strHost);
			ftp.setRemotePort(Integer.parseInt(_strPort));
			ftp.setTimeout(FTP_TIMEOUT);
			ftp.connect();
			ftp.login(_strFtpUser, _strFtpPwd);
			logger.info("Login OK![" + _strHost + ":" + _strPort + ":" + _strFtpUser + "]");
			ftp.setType(FTPTransferType.BINARY);
			if (_strEntryDesDir.trim().length() > 0 && !_strEntryDesDir.trim().equals(".")) {
				if (_strEntryDesDir.substring(0, 1).equals("/")) { // 绝对路径
					ftp.chdir(_strEntryDesDir.trim());
					logger.info("Change Dir : " + _strEntryDesDir.trim() + " OK!");
				}
				else { // 相对路径
					String[] strDirs = _strEntryDesDir.split("/", -1);
					if (strDirs != null) {
						String strCurDir = "";
						for (int i = 0; i < strDirs.length; i++) {
							if (strCurDir.length() > 0) {
								strCurDir = strCurDir + "/" + strDirs[i];
							}
							else {
								strCurDir = strDirs[i];
							}
							try {
								ftp.mkdir(strDirs[i]);
								logger.info("Create Dir : " + strCurDir + " OK!");
							}
							catch (FTPException ex1) {
							}
							ftp.chdir(strDirs[i]);
						}
						logger.info("Change Dir : " + strCurDir + " OK!");
					}
				}
			}

			ftp.setConnectMode(FTPConnectMode.PASV);
			// ftp.setConnectMode(FTPConnectMode.ACTIVE);

			String[] remortFileNames = ftp.dir(".", true);
			String[] remortFileNameList = ftp.dir();

			for (int i = 0; i < remortFileNames.length; i++) {
				String fileName = remortFileNames[i];
				if (isDir(fileName)) {
					logger.info("show remort dir info[" + i + "]:" + remortFileNames[i]);
					fileNameList.add(remortFileNameList[i]);
				}
			}

			// for (int i = 0; i < remortFileNameList.length; i++) {
			// logger.info("display remort list info[" + i + "]:" +
			// remortFileNameList[i]);
			// }

			ftp.quit();
			logger.info("Logout!" + "\n");
		}
		catch (FTPException ex) {
			if (ex.getMessage().indexOf("No such file") >= 0) {
				return fileNameList;
			}
			else if (ex.getMessage().indexOf("Bad directory") >= 0) {
				return fileNameList;
			}
			else if (ex.getMessage().indexOf("Unexpected null reply received") >= 0) {
				return fileNameList;
			}
			else {
				logger.info("ftpGetDirList :" + ex.getMessage());
				return null;
			}
		}
		catch (IOException ex1) {
			logger.info("ftpGetDirList :" + ex1.getMessage());
			return null;
		}
		catch (NumberFormatException ex2) {
			logger.info("ftpGetDirList :" + ex2.getMessage());
			return null;
		}

		logger.info("ftpGetDirList end. get dirnames num=" + fileNameList.size());
		return fileNameList;
	}

	private static boolean isDir(String remortFileName) {
		boolean flag = false;
		if ("d".equals(remortFileName.substring(0, 1))) {
			flag = true;
		}
		return flag;
	}
	
	private static boolean isDirectory(String remortFileName) {
		boolean flag = false;
		if (remortFileName.length() >= 5 && remortFileName.length() <= 8 && CommonUtils.isInt(remortFileName)) {
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 
	 * ftpGetDirectoryList:(取得FTP目录列表). <br/>
	 * @param _strHost
	 * @param _strPort
	 * @param _strFtpUser
	 * @param _strFtpPwd
	 * @param _strEntryDesDir
	 * @return
	 * @since 1.0
	 */
	public static List ftpGetDirectoryList(String _strHost, String _strPort, String _strFtpUser, String _strFtpPwd,
                                           String _strEntryDesDir) {

		List<String> fileNameList = new ArrayList<String>();

		logger.info("ip=" + _strHost + ", port=" + _strPort + ", user=" + _strFtpUser + ", passwd=" + _strFtpPwd
				+ ", entryDir=" + _strEntryDesDir);

		FTPClient ftp = null;

		try {
			ftp = new FTPClient();
			ftp.setRemoteHost(_strHost);
			ftp.setRemotePort(Integer.parseInt(_strPort));
			ftp.setTimeout(FTP_TIMEOUT);
			ftp.connect();
			ftp.login(_strFtpUser, _strFtpPwd);
			logger.info("Login OK![" + _strHost + ":" + _strPort + ":" + _strFtpUser + "]");
			ftp.setType(FTPTransferType.BINARY);
			if (_strEntryDesDir.trim().length() > 0 && !_strEntryDesDir.trim().equals(".")) {
				if (_strEntryDesDir.substring(0, 1).equals("/")) { // 绝对路径
					ftp.chdir(_strEntryDesDir.trim());
					logger.info("Change Dir : " + _strEntryDesDir.trim() + " OK!");
				}
				else { // 相对路径
					String[] strDirs = _strEntryDesDir.split("/", -1);
					if (strDirs != null) {
						String strCurDir = "";
						for (int i = 0; i < strDirs.length; i++) {
							if (strCurDir.length() > 0) {
								strCurDir = strCurDir + "/" + strDirs[i];
							}
							else {
								strCurDir = strDirs[i];
							}
							try {
								ftp.mkdir(strDirs[i]);
								logger.info("Create Dir : " + strCurDir + " OK!");
							}
							catch (FTPException ex1) {
							}
							ftp.chdir(strDirs[i]);
						}
						logger.info("Change Dir : " + strCurDir + " OK!");
					}
				}
			}

			ftp.setConnectMode(FTPConnectMode.PASV);
			// ftp.setConnectMode(FTPConnectMode.ACTIVE);

			String[] remortFileNameList = ftp.dir();
			for (int i = 0; i < remortFileNameList.length; i++) {
				if (isDirectory(remortFileNameList[i])) {
					logger.info("display remort directory info[" + i + "]:" + remortFileNameList[i]);
					fileNameList.add(remortFileNameList[i]);
				}
			}

			ftp.quit();
			logger.info("Logout!" + "\n");
		}
		catch (FTPException ex) {
			if (ex.getMessage().indexOf("No such file") >= 0) {
				return fileNameList;
			}
			else if (ex.getMessage().indexOf("Bad directory") >= 0) {
				return fileNameList;
			}
			else if (ex.getMessage().indexOf("Unexpected null reply received") >= 0) {
				return fileNameList;
			}
			else {
				logger.info("ftpGetDirList :" + ex.getMessage());
				return null;
			}
		}
		catch (IOException ex1) {
			logger.info("ftpGetDirList :" + ex1.getMessage());
			return null;
		}
		catch (NumberFormatException ex2) {
			logger.info("ftpGetDirList :" + ex2.getMessage());
			return null;
		}

		logger.info("ftpGetDirList end. get dirnames num=" + fileNameList.size());
		return fileNameList;
	}

	/**
	 * 
	 * ftpGetFile:(获取ftp文件). <br/>
	 * @param _strHost	ftp主机
	 * @param _strPort	ftp端口
	 * @param _strFtpUser	ftp用户名
	 * @param _strFtpPwd	ftp密码
	 * @param _strEntryDesDir	ftp指定目录
	 * @param _strFileName		获取的ftp文件名表达式
	 * @param _iFileNum			单次获取文件数量
	 * @param _strLocalFileDir	本地的下载目录
	 * @return
	 * @since 1.0
	 */
	public static List ftpGetFile(String _strHost, String _strPort, String _strFtpUser, String _strFtpPwd,
                                  String _strEntryDesDir, String _strFileName, int _iFileNum, String _strLocalFileDir) {
		return ftpGetFile(_strHost, _strPort, _strFtpUser, _strFtpPwd, _strEntryDesDir, _strFileName, _iFileNum,
				_strLocalFileDir, "");
	}

	/**
	 * 
	 * ftpGetFile:(获取ftp文件). <br/>
	 * @param _strHost	ftp主机
	 * @param _strPort	ftp端口
	 * @param _strFtpUser	ftp用户名
	 * @param _strFtpPwd	ftp密码
	 * @param _strEntryDesDir	ftp指定目录
	 * @param _strFileName		获取的ftp文件名表达式
	 * @param _iFileNum			单次获取文件数量
	 * @param _strLocalFileDir	本地的下载目录
	 * @param _strIgnoreFileName	不下载的文件
	 * @return
	 * @since 1.0
	 */
	public static List ftpGetFile(String _strHost, String _strPort, String _strFtpUser, String _strFtpPwd,
                                  String _strEntryDesDir, String _strFileName, int _iFileNum, String _strLocalFileDir,
                                  String _strIgnoreFileName) {

		int iRtn = FTP_NO_FILE_TRANSFER;
		List<String> fileList = new ArrayList<String>();

		logger.info("ip=" + _strHost + ", port=" + _strPort + ", user=" + _strFtpUser + ", passwd=" + _strFtpPwd
				+ ", entryDir=" + _strEntryDesDir + ", file=" + _strFileName + ", num=" + _iFileNum);

		if (_strFileName == null || _iFileNum == 0 || _strLocalFileDir == null) {
			return null;
		}

		FTPClient ftp = null;

		try {
			iRtn = FTP_CONNECT_ERROR;
			ftp = new FTPClient();
			ftp.setRemoteHost(_strHost);
			ftp.setRemotePort(Integer.parseInt(_strPort));
			ftp.setTimeout(FTP_TIMEOUT);
			ftp.connect();
			iRtn = FTP_LOGIN_ERROR;
			ftp.login(_strFtpUser, _strFtpPwd);
			logger.info("Login OK![" + _strHost + ":" + _strPort + ":" + _strFtpUser + "]");
			ftp.setType(FTPTransferType.BINARY);
			if (_strEntryDesDir.trim().length() > 0 && !_strEntryDesDir.trim().equals(".")) {
				if (_strEntryDesDir.substring(0, 1).equals("/")) { // 绝对路径
					ftp.chdir(_strEntryDesDir.trim());
					logger.info("Change Dir : " + _strEntryDesDir.trim() + " OK!");
				}
				else { // 相对路径
					String[] strDirs = _strEntryDesDir.split("/", -1);
					if (strDirs != null) {
						String strCurDir = "";
						for (int i = 0; i < strDirs.length; i++) {
							if (strCurDir.length() > 0) {
								strCurDir = strCurDir + "/" + strDirs[i];
							}
							else {
								strCurDir = strDirs[i];
							}
							try {
								ftp.mkdir(strDirs[i]);
								logger.info("Create Dir : " + strCurDir + " OK!");
							}
							catch (FTPException ex1) {
							}
							ftp.chdir(strDirs[i]);
						}
						logger.info("Change Dir : " + strCurDir + " OK!");
					}
				}
			}

			if (!FileUtils.dirIsExist(_strLocalFileDir)) {
				SystemUtils.makeDir(_strLocalFileDir);
			}

			// ftp.setConnectMode(FTPConnectMode.PASV);
			ftp.setConnectMode(FTPConnectMode.ACTIVE);

			String[] remortFileNames = ftp.dir(_strFileName + "*");
			for (int i = 0; i < remortFileNames.length; i++) {
				if (!StringUtils.nvl(_strIgnoreFileName).equals("")) {
					if (remortFileNames[i].indexOf(_strIgnoreFileName) >= 0) {
						continue;
					}
				}
				String localFile = _strLocalFileDir + File.separator + remortFileNames[i];
				iRtn = FTP_TRANSFER_ERROR;
				try {
					ftp.get(localFile, remortFileNames[i]);
				}
				catch (Exception e) {
				}
				logger.info("Get file " + remortFileNames[i]);
				fileList.add(localFile);

				if (i + 1 == _iFileNum) {
					break;
				}
			}
			iRtn = FTP_LOGOUT_ERROR;
			ftp.quit();
			logger.info("Logout!" + "\n");
		}
		catch (FTPException ex) {
			if (iRtn == FTP_LOGOUT_ERROR) {
				return fileList;
			}
			else {
				if (ex.getMessage().indexOf("No such file") >= 0) {
					return fileList;
				}
				else if (ex.getMessage().indexOf("Bad directory") >= 0) {
					return fileList;
				}
				else {
					logger.info("ftpGetFile :" + ex.getMessage());
					return fileList;
				}
			}
		}
		catch (IOException ex1) {
			if (iRtn == FTP_LOGOUT_ERROR) {
				return fileList;
			}
			else {
				logger.info("ftpGetFile :" + ex1.getMessage());
				return null;
			}
		}
		catch (NumberFormatException ex2) {
			if (iRtn == FTP_LOGOUT_ERROR) {
				return fileList;
			}
			else {
				logger.info("ftpGetFile :" + ex2.getMessage());
				return null;
			}
		}

		logger.info("ftpGetFile end. get files num=" + fileList.size());
		return fileList;
	}

	/**
	 * 
	 * ftpDeleteFile:(删除ftp上的文件). <br/>
	 * @param _strHost
	 * @param _strPort
	 * @param _strFtpUser
	 * @param _strFtpPwd
	 * @param _strEntryDesDir
	 * @param _strFileNames		需删除的文件列表
	 * @return
	 * @since 1.0
	 */
	public static int ftpDeleteFile(String _strHost, String _strPort, String _strFtpUser, String _strFtpPwd,
                                    String _strEntryDesDir, String[] _strFileNames) {
		int iRtn = FTP_NO_FILE_TRANSFER;
		if (_strFileNames.length <= 0) {
			return 0;
		}

		FTPClient ftp = null;

		try {
			ftp = new FTPClient();
			ftp.setRemoteHost(_strHost);
			ftp.setRemotePort(Integer.parseInt(_strPort));
			ftp.setTimeout(FTP_TIMEOUT);
			ftp.connect();
			ftp.login(_strFtpUser, _strFtpPwd);
			logger.info("Login OK![" + _strHost + ":" + _strPort + ":" + _strFtpUser + "]");
			ftp.setType(FTPTransferType.BINARY);
			ftp.setConnectMode(FTPConnectMode.PASV);
			if (_strEntryDesDir.trim().length() > 0) {
				if (_strEntryDesDir.substring(0, 1).equals("/")) { // 绝对路径
					ftp.chdir(_strEntryDesDir.trim());
					logger.info("Change Dir : " + _strEntryDesDir.trim() + " OK!");
				}
				else { // 相对路径
					String[] strDirs = _strEntryDesDir.split("/", -1);
					if (strDirs != null) {
						String strCurDir = "";
						for (int i = 0; i < strDirs.length; i++) {
							if (strCurDir.length() > 0) {
								strCurDir = strCurDir + "/" + strDirs[i];
							}
							else {
								strCurDir = strDirs[i];
							}
							ftp.chdir(strDirs[i]);
						}
						logger.info("Change Dir : " + strCurDir + " OK!");
					}
				}
			}

			for (int i = 0; i < _strFileNames.length; i++) {
				if (_strFileNames[i].length() <= 0)
					continue;

				try {
					ftp.delete(_strFileNames[i]);
					logger.info("Delete file " + _strFileNames[i] + " OK!");
				}
				catch (FTPException ex1) {
					logger.info("Delete file " + _strFileNames[i] + " FAIL!" + ex1.getMessage());
				}
				catch (IOException ex1) {
					logger.info("Delete file " + _strFileNames[i] + " FAIL!" + ex1.getMessage());
				}
			}
			iRtn = FTP_LOGOUT_ERROR;
			ftp.quit();
			logger.info("Logout!" + "\n");
		}
		catch (FTPException ex) {
			logger.info("ftpDeleteFile : " + ex.getMessage());
			return -1;
		}
		catch (IOException ex1) {
			logger.info("ftpDeleteFile : " + ex1.getMessage());
			return -2;
		}
		catch (NumberFormatException ex2) {
			logger.info("ftpDeleteFile : " + ex2.getMessage());
			return -3;
		}

		return 0;
	}

	/**
	 * 
	 * ftpRenameFile:(ftp文件更名). <br/>
	 * @param _strHost
	 * @param _strPort
	 * @param _strFtpUser
	 * @param _strFtpPwd
	 * @param _strEntryDir
	 * @param _strEntrySrcFilePaths		原文件名列表
	 * @param _strEntryDesFilePaths		新文件名列表
	 * @return
	 * @since 1.0
	 */
	public static int ftpRenameFile(String _strHost, String _strPort, String _strFtpUser, String _strFtpPwd,
                                    String _strEntryDir, String[] _strEntrySrcFilePaths, String[] _strEntryDesFilePaths) {
		int iRtn = FTP_NO_FILE_TRANSFER;
		if (_strEntrySrcFilePaths.length <= 0) {
			return 0;
		}

		if (_strEntryDesFilePaths.length <= 0) {
			return 0;
		}

		FTPClient ftp = null;

		try {
			iRtn = FTP_CONNECT_ERROR;
			ftp = new FTPClient();
			ftp.setRemoteHost(_strHost);
			ftp.setRemotePort(Integer.parseInt(_strPort));
			ftp.setTimeout(FTP_TIMEOUT);
			ftp.connect();
			iRtn = FTP_LOGIN_ERROR;
			ftp.login(_strFtpUser, _strFtpPwd);
			logger.info("Login OK![" + _strHost + ":" + _strPort + ":" + _strFtpUser + "]");
			ftp.setType(FTPTransferType.BINARY);
			ftp.setConnectMode(FTPConnectMode.PASV);
			if (_strEntryDir.trim().length() > 0) {
				String[] strDirs = _strEntryDir.split("/", -1);
				if (strDirs != null) {
					String strCurDir = "";
					for (int i = 0; i < strDirs.length; i++) {
						if (strCurDir.length() > 0) {
							strCurDir = strCurDir + "/" + strDirs[i];
						}
						else {
							strCurDir = strDirs[i];
						}
						ftp.chdir(strDirs[i]);
					}
					logger.info("Change Dir : " + strCurDir + " OK!");
				}
			}

			for (int i = 0; i < _strEntrySrcFilePaths.length; i++) {
				if (_strEntrySrcFilePaths[i].length() <= 0)
					continue;
				if (_strEntryDesFilePaths[i].length() <= 0)
					continue;

				String[] strDirs = _strEntryDesFilePaths[i].split("/", -1);
				if (strDirs != null) {
					String strDesDir = "";
					for (int j = 0; j < strDirs.length - 1; j++) {
						if (j == 0) {
							strDesDir += strDirs[j];
						}
						else {
							strDesDir += "/" + strDirs[j];
						}

						try {
							ftp.mkdir(strDesDir);
							logger.info("Create Dir : " + strDesDir + " OK!");
						}
						catch (FTPException ex1) {
						}
					}
				}

				try {
					iRtn = FTP_TRANSFER_ERROR;
					ftp.rename(_strEntrySrcFilePaths[i], _strEntryDesFilePaths[i]);
					logger.info("Rename file " + _strEntrySrcFilePaths[i] + " to " + _strEntryDesFilePaths[i] + " OK!");
				}
				catch (FTPException ex1) {
					logger.error("Rename file " + _strEntrySrcFilePaths[i] + " to " + _strEntryDesFilePaths[i]
							+ " FAIL! " + ex1.getMessage());
				}
				catch (IOException ex1) {
					logger.error("Rename file " + _strEntrySrcFilePaths[i] + " to " + _strEntryDesFilePaths[i]
							+ " FAIL! b" + ex1.getMessage());
				}
			}
			iRtn = FTP_LOGOUT_ERROR;
			ftp.quit();
			iRtn = FTP_OK;
			logger.info("Logout!" + "\n");
		}
		catch (FTPException ex) {
			logger.error(ex.getMessage(), ex);
		}
		catch (IOException ex) {
			logger.error(ex.getMessage(), ex);
			ex.printStackTrace();
			// return -2;
		}
		catch (NumberFormatException ex2) {
			logger.error(ex2.getMessage(), ex2);
			ex2.printStackTrace();
			// return -3;
		}
		return iRtn;
	}
}
