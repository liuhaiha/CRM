package com.tydic.traffic.crm.vo;

import java.util.List;

import com.tydic.traffic.crm.entity.TSysUser;

public class UserInfo extends TSysUser {
	// 员工编号
	private String eno;
	// 员工姓名
	private String ename;
	// 部门编号/名称
	private String dno;
	private String dname;
	// 员工头像地址
	private String faceUrl;
	// 岗位编码/岗位名称
	private String station;
	private String stationName;
	
	private List<String> perCodeList;
	
	public String getEno() {
		return eno;
	}
	public void setEno(String eno) {
		this.eno = eno;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getDno() {
		return dno;
	}
	public void setDno(String dno) {
		this.dno = dno;
	}
	public String getDname() {
		return dname;
	}
	public void setDname(String dname) {
		this.dname = dname;
	}
	public String getFaceUrl() {
		return faceUrl;
	}
	public void setFaceUrl(String faceUrl) {
		this.faceUrl = faceUrl;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public List<String> getPerCodeList() {
		return perCodeList;
	}
	public void setPerCodeList(List<String> perCodeList) {
		this.perCodeList = perCodeList;
	}
	
}
