package com.tydic.traffic.tafa.utils.dbf;


import com.tydic.traffic.tafa.utils.charset.CharsetUtils;

public abstract class DBFBase {

	// 通常dbf使用excel打开时，gb2312的中文能正常显示，utf8的不行，所以默认字符集设置成gb2312
	protected String characterSetName = CharsetUtils.GB2312;
	
	protected final int END_OF_DATA = 0x1A;


	public String getCharactersetName() {

		return this.characterSetName;
	}

	/**
	 * 
	 * setCharactersetName:(设置字符集). <br/>
	 * @param characterSetName
	 * @since 1.0
	 */
	public void setCharactersetName( String characterSetName) {

		this.characterSetName = characterSetName;
	}

}
