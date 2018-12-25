package com.tydic.traffic.tafa.exception;


import org.slf4j.helpers.MessageFormatter;

/**
 * 业务异常基类
 * 
 */
public class BizException extends RuntimeException {

	private static final long serialVersionUID = -227778972165715649L;

	/** 错误码 */
	private String errCode;

	/** 错误码对应的参数 */
	private Object[] args;
	
	/** 错误消息 */
	private String message;

	/**
	 *
	 * @param errCode 错误码
     */
	public BizException(String errCode){
		this.errCode=errCode;
	}

	/**
	 *
	 * @param errCode 错误码
	 * @param message 提示信息
     */
	public BizException(String errCode,String message){
		this.errCode=errCode;
		this.message=message;
	}

	/**
	 *
	 * @param errCode 错误码
	 * @param cause	异常信息
	 * @param message 提示信息
     */
	public BizException(String errCode, Throwable cause,String message){
		super(cause);
		this.errCode=errCode;
		this.message=message;
	}

	/**
	 *
	 * @param cause 异常信息
	 * @param errCode 错误码
     */
	public BizException(Throwable cause,String errCode){
		super(cause);
		this.errCode=errCode;
	}

	/**
	 *
	 * @param message 提示信息
	 * @param cause	异常信息
     */
	public BizException(String message,Throwable cause){
		super(cause);
		this.message=message;
	}

	/**
	 * 
	 * @param errCode 错误码
	 * @param args 异常模板中对应的参数
	 */
	public BizException(String errCode, Object... args) {
		this(errCode, null, args);
	}
	
	/**
	 * 
	 * @param errCode 错误码
	 * @param cause 异常信息
	 * @param args 异常模板中对应的参数
	 */
	public BizException(String errCode, Throwable cause, Object... args) {
		super(cause);
		this.errCode = errCode;
		this.args = args;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public String getErrCode() {
		return errCode;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return this.getClass() + "{ errCode='" + getErrCode() + '\'' + ", message='" + getMessage() + '\'' + '}';
	}

}
