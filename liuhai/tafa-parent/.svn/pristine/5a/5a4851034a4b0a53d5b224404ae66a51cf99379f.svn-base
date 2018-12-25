package com.tydic.traffic.tafa.utils;

import com.tydic.traffic.tafa.utils.string.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * ClassName:SystemUtils <br/>
 * Reason: 系统工具类 <br/>
 */

public class SystemUtils {

	private static final Logger logger = LoggerFactory.getLogger(SystemUtils.class);
	
	/**
	 * 
	 * getConfigHome:(取得应用配置目录环境变量). <br/>
	 */
	public static String getConfigHome(){
		return getEnv("APP_CONFIG_HOME");
	}
	
	/**
	 * 
	 * getEnv:(取得环境变量对应的的值). <br/>
	 * @param envName	环境变量名称
	 */
	public static String getEnv(String envName){
		if(StringUtils.isEmpty(envName)){
			return null;
		}
		return System.getenv().get(envName);
	}

	/**
	 * 
	 * copyFile:(利用系统命令进行文件拷贝). <br/>
	 * 
	 * @param _strSrcFilePath
	 * @param _strDesFilePath
	 * @return int
	 * @since 1.0
	 */
	public static int copyFile(String _strSrcFilePath, String _strDesFilePath) {
		String osName = System.getProperty("os.name");
		String strCommand = "";
		if (osName.indexOf("Windows") >= 0) {
			strCommand = "copy " + _strSrcFilePath + " " + _strDesFilePath;
		}
		else {
			strCommand = "cp " + _strSrcFilePath + " " + _strDesFilePath;
		}

		logger.info("Command: " + strCommand);
		int iRtn = ExecCommand.execCommand(strCommand);

		return iRtn;
	}

	/**
	 * 
	 * moveFile:(利用系统命令进行移动文件). <br/>
	 * 
	 * @param _strSrcFilePath
	 * @param _strDesFilePath
	 * @return int
	 * @since 1.0
	 */
	public static int moveFile(String _strSrcFilePath, String _strDesFilePath) {
		String osName = System.getProperty("os.name");
		String strCommand = "";
		if (osName.indexOf("Windows") >= 0) {
			strCommand = "move " + _strSrcFilePath + " " + _strDesFilePath;
		}
		else {
			strCommand = "mv " + _strSrcFilePath + " " + _strDesFilePath;
		}

		logger.info("Command: " + strCommand);
		int iRtn = ExecCommand.execCommand(strCommand);
		return iRtn;
	}

	/**
	 * 
	 * deleteFile:(利用系统命令进行文件删除). <br/>
	 * 
	 * @param _strFilePath
	 * @return int
	 * @since 1.0
	 */
	public static int deleteFile(String _strFilePath) {
		String osName = System.getProperty("os.name");
		String strCommand = "";
		if (osName.indexOf("Windows") >= 0) {
			strCommand = "del " + _strFilePath;
		}
		else {
			strCommand = "rm -f " + _strFilePath;
		}
		logger.info("Command: " + strCommand);
		int iRtn = ExecCommand.execCommand(strCommand);
		return iRtn;
	}

	/**
	 * 
	 * createDir:(利用系统命令进行创建目录). <br/>
	 * 
	 * @param _strDirPath
	 * @return int
	 * @since 1.0
	 */
	public static int createDir(String _strDirPath) {
		File dir = new File(_strDirPath);
		if (dir.exists()) {
			return 0;
		}

		String osName = System.getProperty("os.name");
		String strCommand = "";
		if (osName.indexOf("Windows") >= 0) {
			strCommand = "md " + _strDirPath;
		}
		else {
			strCommand = "mkdir -p " + _strDirPath;
		}

		logger.info("Command: " + strCommand);
		int iRtn = ExecCommand.execCommand(strCommand);
		return iRtn;
	}

	/**
	 * 
	 * makeDir:(利用系统命令进行目录创建). <br/>
	 * 
	 * @param dirpath
	 * @return String
	 * @since 1.0
	 */
	public static String makeDir(String dirpath) {
		if (StringUtils.nvl(dirpath).trim().length() == 0) {
			return "";
		}

		if (StringUtils.nvl(dirpath).trim().toLowerCase().equals("null")) {
			return "";
		}

		String osName = System.getProperty("os.name");
		if (osName.indexOf("Windows") >= 0) {
			dirpath = dirpath.replaceAll("\\/", "\\" + File.separator);
		}
		createDir(dirpath);
		return dirpath;
	}

	/**
	 * 
	 * deleteDir:(利用系统命令进行目录删除). <br/>
	 * 
	 * @param _strFilePath
	 * @return int
	 * @since 1.0
	 */
	public static int deleteDir(String _strFilePath) {
		String osName = System.getProperty("os.name");
		String strCommand = "";
		if (osName.indexOf("Windows") >= 0) {
			strCommand = "rmdir " + _strFilePath + " /S /Q";
		}
		else {
			strCommand = "rm -rf " + _strFilePath;
		}
		logger.info("Command: " + strCommand);
		int iRtn = ExecCommand.execCommand(strCommand);
		return iRtn;
	}

	/**
	 * 
	 * changeFilePathOwner:(改变文件或目录的所有者). <br/>
	 * 
	 * @param _strOwner
	 * @param _strFilePath
	 * @return int
	 * @since 1.0
	 */
	public static int changeFilePathOwner(String _strOwner, String _strFilePath) {
		String osName = System.getProperty("os.name");
		String strCommand = "";
		if (osName.indexOf("Windows") >= 0) {
			strCommand = "dir " + _strFilePath;
		}
		else {
			if (isDir(_strFilePath)) {
				strCommand = "chown  -R " + _strOwner + " " + _strFilePath;
			}
			else {
				strCommand = "chown  " + _strOwner + " " + _strFilePath;
			}
		}

		logger.info("Command: " + strCommand);
		int iRtn = ExecCommand.execCommand(strCommand);
		return iRtn;
	}

	/**
	 * 
	 * changeFilePathGroup:(改变文件或目录的所有组). <br/>
	 * 
	 * @param _strOGroup
	 * @param _strFilePath
	 * @return int
	 * @since 1.0
	 */
	public static int changeFilePathGroup(String _strOGroup, String _strFilePath) {
		String osName = System.getProperty("os.name");
		String strCommand = "";
		if (osName.indexOf("Windows") >= 0) {
			strCommand = "dir " + _strFilePath;
		}
		else {
			if (isDir(_strFilePath)) {
				strCommand = "chgrp  -R " + _strOGroup + " " + _strFilePath;
			}
			else {
				strCommand = "chgrp  " + _strOGroup + " " + _strFilePath;
			}
		}

		logger.info("Command: " + strCommand);
		int iRtn = ExecCommand.execCommand(strCommand);
		return iRtn;
	}

	/**
	 * 
	 * changeFilePathMode:(改变文件或目录的权限). <br/>
	 * 
	 * @param _strMode
	 * @param _strFilePath
	 * @return int
	 * @since 1.0
	 */
	public static int changeFilePathMode(String _strMode, String _strFilePath) {
		String osName = System.getProperty("os.name");
		String strCommand = "";
		if (osName.indexOf("Windows") >= 0) {
			strCommand = "dir " + _strFilePath;
		}
		else {
			if (isDir(_strFilePath)) {
				strCommand = "chmod  -R " + _strMode + " " + _strFilePath;
			}
			else {
				strCommand = "chmod  " + _strMode + " " + _strFilePath;
			}
		}

		logger.info("Command: " + strCommand);
		int iRtn = ExecCommand.execCommand(strCommand);
		return iRtn;
	}

	/**
	 * 
	 * createUserAccount:(新增用户). <br/>
	 * 
	 * @param _strUserName
	 * @param _strUserHomeDir
	 * @param _strUserGroup
	 * @return int
	 * @since 1.0
	 */
	public static int createUserAccount(String _strUserName, String _strUserHomeDir, String _strUserGroup) {
		if (_strUserName.length() <= 0 || _strUserHomeDir.length() <= 0) {
			logger.info("createUserAccount: UserName or UserHomeDir is empty!!");
			return -1;
		}
		String osName = System.getProperty("os.name");
		String strCommand = "";
		if (osName.indexOf("Windows") >= 0) {
			logger.info("Can't call this function in " + osName);
			return -1;
		}
		else {
			if (_strUserGroup.trim().length() > 0) {
				strCommand = "useradd -d " + _strUserHomeDir + " -g " + _strUserGroup + " " + _strUserName;
			}
			else {
				strCommand = "useradd -d " + _strUserHomeDir + " " + _strUserName;
			}
		}

		logger.info("Command: " + strCommand);
		int iRtn = ExecCommand.execCommand(strCommand);
		return iRtn;
	}

	/**
	 * 
	 * changeUserPasswd:(更改用户密码). <br/>
	 * 
	 * @param _strExecShellFilePath
	 * @param _strUserName
	 * @param _strPasswd
	 * @return int
	 * @since 1.0
	 */
	public static int changeUserPasswd(String _strExecShellFilePath, String _strUserName, String _strPasswd) {
		if (_strExecShellFilePath == null || _strExecShellFilePath.length() <= 0) {
			logger.info("changeUserPasswd: ExecShell is empty!");
		}

		String osName = System.getProperty("os.name");
		String strCommand = "";
		if (osName.indexOf("Windows") >= 0) {
			logger.info("changeUserPasswd: Can't call this function in " + osName);
			return -1;
		}
		else {
			strCommand = _strExecShellFilePath + " " + _strUserName + " " + _strPasswd;
		}

		logger.info("Command: " + strCommand);
		int iRtn = ExecCommand.execCommand(strCommand);
		return iRtn;
	}

	/**
	 * 
	 * findStrInFile:(利用系统命令在文件中查找字符串). <br/>
	 * 
	 * @param filepath
	 * @param str
	 * @return boolean
	 * @since 1.0
	 */
	public static boolean findStrInFile(String filepath, String str) {

		String osName = System.getProperty("os.name");
		String strCommand = "";
		if (osName.indexOf("Windows") >= 0) {
			return false;
		}
		else {
			strCommand = "cat " + filepath + " | grep " + str + " > /dev/null";
		}
		int iRtn = ExecCommand.execCommand(strCommand);
		if (iRtn == 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * 
	 * getHostName:(取得本机主机名). <br/>
	 * @return String
	 * @since 1.0
	 */
	public static String getHostName() {
		String OS = System.getProperty("os.name").toLowerCase();
		Process p = null;
		BufferedReader br = null;
		String hostname = "";
		try {
			if (OS.indexOf("windows") > -1) {
				p = Runtime.getRuntime().exec("cmd /c hostname");
			}
			else {
				p = Runtime.getRuntime().exec("sh -c hostname");
			}
			br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				hostname = line;
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(null != br){
					br.close();
				}				
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}

		p.destroy();
		return hostname;
	}

	/**
	 * 
	 * isDir:(这里用一句话描述这个方法的作用). <br/>
	 * 
	 * @param _strFilePath
	 * @return boolean
	 * @since 1.0
	 */
	private static boolean isDir(String _strFilePath) {
		File file = new File(_strFilePath);
		return file.isDirectory();
	}

}
