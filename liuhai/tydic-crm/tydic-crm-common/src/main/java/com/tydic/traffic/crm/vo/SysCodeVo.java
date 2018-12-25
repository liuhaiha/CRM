package com.tydic.traffic.crm.vo;

import java.util.ArrayList;
import java.util.List;

import com.tydic.traffic.crm.entity.TSysCode;

public class SysCodeVo extends TSysCode{
	private List<SysCodeVo> childList = null;

	public List<SysCodeVo> getChildList() {
		if (null == childList)
		{
			childList = new ArrayList<>();
		}
		return childList;
	}

	public void setChildList(List<SysCodeVo> childList) {
		this.childList = childList;
	}
	
	
}
