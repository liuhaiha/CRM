package com.tydic.traffic.tafa.web;

import java.io.Serializable;

/**
 * 后端统一返回结果
 *
 * @author  acer
 * @since  2017-06-13
 *
 */
public class JsonResult implements Serializable{
    private static final long serialVersionUID = 1706468039555846578L;
    /**代码*/
    private String code;
    /**属性名称*/
    private String paramName;
    /**提示信息*/
    private String message;
    /**结果集*/
    private Object data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }
}
