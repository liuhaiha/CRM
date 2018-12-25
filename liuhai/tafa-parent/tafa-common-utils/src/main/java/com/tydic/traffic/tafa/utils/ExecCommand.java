package com.tydic.traffic.tafa.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * ClassName:ExecCommand <br/>
 * Reason: 命令行执行 <br/>
 */

public class ExecCommand {

	private static final Logger logger = LoggerFactory.getLogger(ExecCommand.class);

	/**
	 * 
	 * execCommand:(执行win，unix\linux命令). <br/>
	 * 
	 * @param _strCommand
	 * @return int
	 * @since 1.0
	 */
	public static int execCommand(String _strCommand) {
		if (_strCommand == null || (_strCommand != null && _strCommand.length() <= 0)) {
			System.exit(1);
		}
		try {
			Process proc = null;
			Runtime rt = Runtime.getRuntime();
			String osName = System.getProperty("os.name");
			if (osName.indexOf("Windows") >= 0) {
				String[] cmd = new String[3];
				cmd[0] = "cmd.exe";
				cmd[1] = "/C";
				cmd[2] = _strCommand;
				proc = rt.exec(cmd);
			}
			else {
				String cmd = _strCommand;
				proc = rt.exec(cmd);
			}
			// any error message
			StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), "ERROR");
			// any output
			StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), "OUTPUT");
			// kick them off
			errorGobbler.start();
			outputGobbler.start();
			// any error
			int exitVal = proc.waitFor();
			if (exitVal != 0) {
				logger.info("ExitValue: " + exitVal);
			}
			return exitVal;
		}
		catch (Throwable t) {
			logger.error(t.getMessage());
		}
		return 0;
	}

	/**
	 * 
	 * execSunOSCommand:(执行solaris命令). <br/>
	 * 
	 * @param _strCommand
	 * @return int
	 * @since 1.0
	 */
	public static int execSunOSCommand(String _strCommand) {
		if (_strCommand == null || (_strCommand != null && _strCommand.length() <= 0)) {
			System.exit(1);
		}
		try {
			Process proc = null;
			Runtime rt = Runtime.getRuntime();

			String[] cmd = new String[3];
			cmd[0] = "/bin/sh";
			cmd[1] = "-c";
			cmd[2] = _strCommand;
			proc = rt.exec(cmd);
			
			// any error message
			StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), "ERROR");
			// any output
			StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), "OUTPUT");
			// kick them off
			errorGobbler.start();
			outputGobbler.start();
			// any error
			int exitVal = proc.waitFor();
			if (exitVal != 0) {
				logger.info("ExitValue: " + exitVal);
			}
			return exitVal;
		}
		catch (Throwable t) {
			logger.error(t.getMessage());
		}
		return 0;
	}	
}

class StreamGobbler extends Thread {

	InputStream is;

	String type;

	Logger logger = LoggerFactory.getLogger(StreamGobbler.class);

	StreamGobbler(InputStream is, String type) {
		this.is = is;
		this.type = type;
	}

	public void run() {
		try {
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				logger.info(type + ">" + line);
			}
		}
		catch (IOException ioe) {
			logger.error(ioe.getMessage());
		}
	}
}
