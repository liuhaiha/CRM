package com.tydic.traffic.tafa.exception;

import com.alibaba.dubbo.rpc.RpcException;
import com.google.gson.Gson;
import com.tydic.traffic.tafa.exception.result.CodeUnit;
import com.tydic.traffic.tafa.exception.result.DataResult;
import com.tydic.traffic.tafa.web.JsonResult;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 统一异常处理类
 *
 * @author acer
 * @version
 * @since 2017.06.12
 * @see
 */
public class CustomExceptionHandler implements HandlerExceptionResolver {
	private static Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);
	private static final String COLON=":";
	private String defaultErrorView = "common/error";;
	private Gson gson=new Gson();
	private ConcurrentHashMap<String,String> messageMap =new ConcurrentHashMap<String,String>();

	public CustomExceptionHandler(Map<String, String> message) {
		Assert.notEmpty(message,"the exception messageMap resources not empty!!!");
		this.messageMap.putAll(message);
	}

	private String getMessage(BizException bizEx){
		String errorCode = bizEx.getErrCode();
		String message = bizEx.getMessage();

		Object[] args=bizEx.getArgs();
		if (StringUtils.isNotBlank(errorCode)) {
			message = messageMap.get(errorCode);
			if (StringUtils.isNotBlank(message) && (null != args && args.length>0)) {
				message = MessageFormatter.format(message, args).getMessage();
			}
			bizEx.setMessage(message);
		}
		return message;
	}

	/**
	 * 处理hiberante validation校验异常
	 * @param constraintViolations
	 * @return
     */
	protected List<JsonResult> processConstraintViolationException(Set<ConstraintViolation<?>> constraintViolations){
		List<JsonResult> ret=new ArrayList<>();
		for (ConstraintViolation<?> violation : constraintViolations) {
				JsonResult jr=new JsonResult();
				jr.setParamName(((PathImpl)violation.getPropertyPath()).asString());
				jr.setCode(CodeUnit.PARAM_ERROR);
				jr.setMessage(violation.getMessage());
				ret.add(jr);
		}

		return ret;
	}

	/**
	 * 展示异常处理结果
	 * @param response
	 * @param ser
     */
	private void showExceptionProcessResult(HttpServletResponse response, Object ser){
		try {
			response.setContentType(MediaType.APPLICATION_JSON+"; charset=utf-8");
			response.getOutputStream().write(gson.toJson(ser).getBytes());
		} catch (IOException e) {
			logger.error("展示异常处理结果",e);
		}
	}

	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {

		// 异常时HTTP状态码统一设置为500
		response.setStatus(500);

		String suffix = WebContext.getSufix(request); // 请求后缀
		String errorCode = CodeUnit.UNKNOWN_ERROR;
		String message = null;

		if (ex instanceof ConstraintViolationException) {
			Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) ex).getConstraintViolations();
			if ( constraintViolations!=null && constraintViolations.size() > 0) {
				List<JsonResult> ret=processConstraintViolationException(constraintViolations);
				response.setStatus(400);
				showExceptionProcessResult(response,ret);
				return new ModelAndView();
			}
		} else if (ex instanceof IllegalArgumentException) { // 参数校验异常
			errorCode = CodeUnit.PARAM_ERROR;
			message = ex.getMessage();
		} else if (ex instanceof BizException) { // 业务异常
			BizException e = (BizException) ex;
			errorCode = e.getErrCode();
			message = getMessage(e);
		} else if (ex instanceof RpcException) { // Rpc异常
			RpcException rpce = (RpcException) ex;
			Throwable e = rpce.getCause();
			if (e instanceof BizException) {
				errorCode = ((BizException) e).getErrCode();
			} else if (e instanceof ConstraintViolationException) {
				Set<ConstraintViolation<?>> set = ((ConstraintViolationException) e).getConstraintViolations();
				if (null != set && set.size() > 0) {
					for (Object obj : set) {
						ConstraintViolation<?> constraintViolation = (ConstraintViolation<?>) obj;
						errorCode = CodeUnit.PARAM_ERROR;
						message = constraintViolation.getMessage();
						break;
					}
				}
			}
		}

		// 记录错误日志
		if(logger.isErrorEnabled()){
			logger.error(MessageFormatter.format("errorCode {},message {}",errorCode,message).getMessage(), ex);
		}

		JsonResult jsonResult=new JsonResult();
		jsonResult.setCode(errorCode);
		jsonResult.setMessage(message);

		showExceptionProcessResult(response,jsonResult);

		return new ModelAndView();
	}

	private ModelAndView getModelAndView(String suffix, String errorCode, String message) {
		DataResult result = new DataResult();
		result.setCode(errorCode);
		result.setMessage(message);
		return result;

	}

	public String getDefaultErrorView() {
		return defaultErrorView;
	}

	public void setDefaultErrorView(String defaultErrorView) {
		this.defaultErrorView = defaultErrorView;
	}

}
