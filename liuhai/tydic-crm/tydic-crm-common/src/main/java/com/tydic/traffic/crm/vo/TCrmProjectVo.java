package com.tydic.traffic.crm.vo;

import com.tydic.traffic.crm.entity.TCrmProject;

/**
 * 
 * TCrmProjectVo
 * @desc 
 * @author wlhuang
 * @version V1.0 2018年7月30日
 * @since V1.0
 */
public class TCrmProjectVo extends TCrmProject {
	
	private String stageName;

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

}
