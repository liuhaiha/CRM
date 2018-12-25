package com.tydic.traffic.tafa.utils.file;

/**
 * ClassName:UnfixedLenLine <br/>
 * Reason: 用来拆分定长数据行的对象. <br/>
 */
public class UnfixedLenLine {

	private String data = ""; // 要拆分的字符串

	private String[] fielddatas; // 拆分后各字段的数据

	private String separator = ","; // 各字段分隔符

	private String fielddata_begin_flag = null; // 每个字段数据的起始标识

	private String fielddata_end_flag = null; // 每个字段数据的结束标识

	private int fieldNum = 0; // 字段数

	/**
	 * 构造器1，默认用逗号“,”做分隔符
	 */
	public UnfixedLenLine() {

	}

	/**
	 * 构造器2，拆分变长字段（带分隔符)数据行的对象
	 * 
	 * @param _separator
	 *            分隔符
	 */
	public UnfixedLenLine(String _separator) {
		if (_separator != null && separator.length() > 0) {
			this.separator = _separator;
		}
	}

	/**
	 * 构造器3，拆分变长字段（带分隔符)数据行的对象，每个字段的数据有起始和结束标识字符，如< >
	 * 
	 * @param _separator
	 *            分隔符
	 */
	public UnfixedLenLine(String _separator, String _fielddata_begin_flag, String _fielddata_end_flag) {
		if (_separator != null && separator.length() > 0) {
			this.separator = _separator;
		}

		if (_fielddata_begin_flag != null && _fielddata_begin_flag.length() > 0) {
			this.fielddata_begin_flag = _fielddata_begin_flag;
		}

		if (_fielddata_end_flag != null && _fielddata_end_flag.length() > 0) {
			this.fielddata_end_flag = _fielddata_end_flag;
		}
	}

	/**
	 * 将各字段数据初始化
	 */
	public void initFieldsData(int _fieldCount) {
		fielddatas = new String[_fieldCount];
		for (int i = 0; i < fielddatas.length; i++) {
			fielddatas[i] = "";
		}
	}

	/**
	 * 取得某字段的数据
	 * 
	 * @param fieldno
	 *            字段号，从1开始
	 * 
	 * @return String
	 */
	public String getFieldData(int fieldno) {
		if (fielddatas == null) {
			return "";
		}
		if (fieldno < 0 || fieldno > fielddatas.length) {
			return "";
		}
		if (data == null) {
			return "";
		}

		if (fielddatas[fieldno - 1] == null) {
			return "";
		}
		else {
			String fieldData = fielddatas[fieldno - 1];
			if (fielddata_begin_flag != null) {
				fieldData = fieldData.replaceAll(this.fielddata_begin_flag, "");
			}
			if (fielddata_end_flag != null) {
				fieldData = fieldData.replaceAll(this.fielddata_end_flag, "");
			}
			return fieldData.trim().replaceAll("", "");
		}
	}

	/**
	 * 设置某字段的数据，数据长度不限，不区分左右对齐
	 * 
	 * @param fieldno
	 *            字段号，从1开始
	 * 
	 * @param fieldData
	 *            字段的数据
	 */
	public void setFieldData(int fieldno, String fieldData) {
		if (fieldno <= 0) {
			return;
		}

		if (fieldData == null) {
			fieldData = "";
		}

		// 将逗号转换成全角

		if (this.separator.equals(",")) {
			fieldData = fieldData.replaceAll(",", "，");
		}

		int idx = fieldno - 1;

		if (fielddata_begin_flag != null) {
			fielddatas[idx] = fielddata_begin_flag;
		}

		fielddatas[idx] += fieldData;

		if (fielddata_end_flag != null) {
			fielddatas[idx] += fielddata_end_flag;
		}
	}

	/**
	 * 分解字符串到字段数组
	 * 
	 * @param _data
	 *            the data of a line
	 */
	public void setData(String _data) {
		if (_data == null) {
			_data = "";
		}
		data = _data;
		fielddatas = data.split(this.separator);
		byte[] d = data.getBytes();
		byte lastbyte = d[data.length() - 1];
		if (lastbyte == 9) {
			this.fieldNum = fielddatas.length + 1;
		}
		else {
			this.fieldNum = fielddatas.length;
		}

	}

	public void setData2(String _data) {
		if (_data == null) {
			_data = "";
		}
		data = _data;
		fielddatas = data.split(this.separator);

		this.fieldNum = fielddatas.length;
		char lastchar = 0;
		for (int index = data.length() - 1; index >= 0; index--) {
			lastchar = data.charAt(index);
			if (lastchar == 9) {
				this.fieldNum++;
			}
			else {
				break;
			}
		}
	}

	/**
	 * 
	 * getData:(这里用一句话描述这个方法的作用). <br/>
	 * @return String
	 * @since 1.0
	 */
	public String getData() {
		if (fielddatas == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < fielddatas.length; i++) {
			if (i == fielddatas.length - 1) {
				sb.append(fielddatas[i]);
			}
			else {
				sb.append(fielddatas[i] + this.separator);
			}
		}
		return sb.toString();
	}

	public int getFieldNum() {
		return fieldNum;
	}

	
	public String[] getFielddatas() {
		return fielddatas;
	}	
}
