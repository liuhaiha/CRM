package com.tydic.traffic.tafa.utils.json;


import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * ClassName:JSONUtil <br/>
 */
public class JSONUtil {

	private static final Logger log = LoggerFactory.getLogger(JSONUtil.class);

	private ObjectMapper mapper;

	public JSONUtil() {
		this(null);
	}

	public JSONUtil(Include include) {
		mapper = new ObjectMapper();
		if (include != null) {
			// 设置输出时包含属性的风格
			mapper.setSerializationInclusion(include);
		}
		// 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		// 设置默认DateFormat yyyy-MM-dd HH:mm:ss
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * 
	 * nonEmptyMapper:(创建只输出非Null且非Empty(如List.isEmpty)的属性到Json字符串的Mapper,建议在外部接口中使用。). <br/>
	 * @return  只输出非Null且非Empty的属性到Json字符串的Mapper
	 */
	public static JSONUtil nonEmptyMapper() {
		return new JSONUtil(Include.NON_EMPTY);
	}


	/**
	 * 
	 * nonDefaultMapper:(创建只输出初始值被改变的属性到Json字符串的Mapper, 最节约的存储方式，建议在内部接口中使用。). <br/>
	 * @return   只输出初始值被改变的属性到Json字符串的Mapper
	 */
	public static JSONUtil nonDefaultMapper() {
		return new JSONUtil(Include.NON_DEFAULT);
	}

	/**
	 * 
	 * toJson:(Object可以是POJO，也可以是Collection或数组。 如果对象为Null, 返回"null". 如果集合为空集合, 返回"[]"). <br/>
	 * @param object
	 */
	public String toJson(Object object) {
		try {
			return mapper.writeValueAsString(object);
		}
		catch (IOException e) {
			log.warn("write to json string error:" + object, e);
			return null;
		}
	}

	/**
	 * 
	 * fromJson:(这里用一句话描述这个方法的作用). <br/>
	 * 
	 * 反序列化POJO或简单Collection如List<String>.
	 * 
	 * 如果JSON字符串为Null或"null"字符串, 返回Null. 如果JSON字符串为"[]", 返回空集合.
	 * 
	 * 如需反序列化复杂Collection如List<MyBean>, 请使用fromJson(String,JavaType)
	 */
	public <T> T fromJson(String jsonString, Class<T> clazz) {
		if (StringUtils.isEmpty(jsonString)) {
			return null;
		}

		try {
			return mapper.readValue(jsonString, clazz);
		}
		catch (IOException e) {
			log.warn("parse json string error:" + jsonString, e);
			return null;
		}
	}


	/**
	 * 
	 * fromJson:(反序列化复杂Collection如List<Bean>, 先使用函数createCollectionType构造类型,然后调用本函数.). <br/>
	 * @see #createCollectionType(Class, Class...)
	 * @param jsonString
	 * @param javaType
	 * @return
	 * @since 1.0
	 */
	public <T> T fromJson(String jsonString, JavaType javaType) {
		if (StringUtils.isEmpty(jsonString)) {
			return null;
		}

		try {
			return mapper.readValue(jsonString, javaType);
		}
		catch (IOException e) {
			log.warn("parse json string error:" + jsonString, e);
			return null;
		}
	}

	
	/**
	 * 
	 * createCollectionType:(构造泛型的Collection). <br/>
	 * Type如: ArrayList<MyBean>,
	 * 则调用constructCollectionType(ArrayList.class,MyBean.class)
	 * HashMap<String,MyBean>, 则调用(HashMap.class,String.class, MyBean.class)
	 * @param collectionClass
	 * @param elementClasses
	 * @return
	 * @since 1.0
	 */
	public JavaType createCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
		return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
	}


	/**
	 * 
	 * update:(更新json数据). <br/>
	 *  当JSON里只有Bean的部分属性时，更新一个已存在的Bean，只覆盖该部分的值。
	 * @param jsonString
	 * @param object
	 * @return
	 * @since 1.0
	 */
	public <T> T update(String jsonString, T object) {
		try {
			return mapper.readerForUpdating(object).readValue(jsonString);
		}
		catch (JsonProcessingException e) {
			log.warn("update json string:" + jsonString + " to object:" + object + " error.",e);
		}
		catch (IOException e) {
			log.warn("update json string:" + jsonString + " to object:" + object + " error.", e);
		}
		return null;
	}


	/**
	 * 
	 * toJsonP:(输出JSONP格式Text，格式为functionName(json)). <br/>
	 * @param functionName
	 * @param object
	 * @return
	 * @since 1.0
	 */
	public String toJsonP(String functionName, Object object) {
		return toJson(new JSONPObject(functionName, object));
	}


	/**
	 * 
	 * enableEnumUseToString:(设定是否使用Enum的toString函数来读写Enum). <br/>
	 * 为False时，使用Enum的name()函数来读写Enum, 默认为False.
	 * 注意本函数一定要在Mapper创建后, 所有的读写动作之前调用.
	 * @since 1.0
	 */
	public void enableEnumUseToString() {
		mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
		mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
	}

	/**
	 * 
	 */
	/**
	 * 
	 * setDateFormat:(设定格式化Date类型数据格式 在所有读写动作之前调用). <br/>
	 * @param dateFormat
	 * @since 1.0
	 */
	public void setDateFormat(String dateFormat) {
		mapper.setDateFormat(new SimpleDateFormat(dateFormat));
	}

	/**
	 * 
	 * getMapper:(取出Mapper做进一步的设置或使用其他序列化API). <br/>
	 * @return
	 * @since 1.0
	 */
	public ObjectMapper getMapper() {
		return mapper;
	}
}
