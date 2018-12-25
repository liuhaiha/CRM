package com.tydic.traffic.tafa.exception.result;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.Map;



/**
 * ClassName:Result <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2013年9月12日 下午2:52:00 <br/>
 * 
 * @author acer
 * @version
 * @since 1.0
 * @see
 */
public class DataResult extends ModelAndView implements Result {

	private ModelMap data = new ModelMap();

	public DataResult() {
		getModelMap().put(Constants.CODE, CodeUnit.SUCCESS);
		getModelMap().put(Constants.MESSAGE, "");
		getModelMap().put(Constants.DATA, data);
	}

	public void setCode(String code) {
		getModelMap().put(Constants.CODE, code);
	}

	public void setMessage(String message) {
		getModelMap().put(Constants.MESSAGE, message);
	}

	@Override
	public Result addData(Object dataValue) {
		data.addAttribute(dataValue);
		return this;
	}

	@Override
	public Result addData(String key, Object value) {
		data.addAttribute(key, value);
		return this;
	}

	@Override
	public Result addDatas(Map<String, ?> dataMap) {
		data.addAllAttributes(dataMap);
		return this;
	}

	@Override
	public void setView(String viewName) {
		this.setViewName(viewName);
	}

	@Override
	public Result addDatas(Collection<?> dataList) {
		data.addAllAttributes(dataList);
		return this;
	}

}
