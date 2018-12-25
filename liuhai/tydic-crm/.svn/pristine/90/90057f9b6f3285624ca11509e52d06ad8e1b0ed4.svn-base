package com.tydic.traffic.crm.entity;

import java.util.Date;
import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Table(name = "t_sys_employee")
public class TSysEmployee {
	@Id
	private Integer eid;

	private String eno;

	private String dno;

	private String ename;

	@Column(name = "face_url")
	private String faceUrl;

	/**
	 * 1:男 0:女
	 */
	private String sex;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date hiredate;

	private String station;

	@Column(name = "upper_leader")
	private String upperLeader;

	@Column(name = "upper_leader_link")
	private String upperLeaderLink;

	private String linkphone;

	private String telephone;

	@Column(name = "e_mail")
	private String eMail;

	private String creator;

	private Date createtime;

	private String modifier;

	private Date modifytime;

	/**
	 * @return eid
	 */
	public Integer getEid() {
		return eid;
	}

	/**
	 * @param eid
	 */
	public void setEid(Integer eid) {
		this.eid = eid;
	}

	/**
	 * @return eno
	 */
	public String getEno() {
		return eno;
	}

	/**
	 * @param eno
	 */
	public void setEno(String eno) {
		this.eno = eno;
	}

	/**
	 * @return dno
	 */
	public String getDno() {
		return dno;
	}

	/**
	 * @param dno
	 */
	public void setDno(String dno) {
		this.dno = dno;
	}

	/**
	 * @return ename
	 */
	public String getEname() {
		return ename;
	}

	/**
	 * @param ename
	 */
	public void setEname(String ename) {
		this.ename = ename;
	}

	/**
	 * @return face_url
	 */
	public String getFaceUrl() {
		return faceUrl;
	}

	/**
	 * @param faceUrl
	 */
	public void setFaceUrl(String faceUrl) {
		this.faceUrl = faceUrl;
	}

	/**
	 * 获取1:男 0:女
	 *
	 * @return sex - 1:男 0:女
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * 设置1:男 0:女
	 *
	 * @param sex
	 *            1:男 0:女
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * @return hiredate
	 */
	public Date getHiredate() {
		return hiredate;
	}

	/**
	 * @param hiredate
	 */
	public void setHiredate(Date hiredate) {
		this.hiredate = hiredate;
	}

	/**
	 * @return station
	 */
	public String getStation() {
		return station;
	}

	/**
	 * @param station
	 */
	public void setStation(String station) {
		this.station = station;
	}

	/**
	 * @return linkphone
	 */
	public String getLinkphone() {
		return linkphone;
	}

	/**
	 * @param linkphone
	 */
	public void setLinkphone(String linkphone) {
		this.linkphone = linkphone;
	}

	/**
	 * @return telephone
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * @param telephone
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * @return e_mail
	 */
	public String geteMail() {
		return eMail;
	}

	/**
	 * @param eMail
	 */
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	/**
	 * @return creator
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * @param creator
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}

	/**
	 * @return createtime
	 */
	public Date getCreatetime() {
		return createtime;
	}

	/**
	 * @param createtime
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	/**
	 * @return modifier
	 */
	public String getModifier() {
		return modifier;
	}

	/**
	 * @param modifier
	 */
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	/**
	 * @return modifytime
	 */
	public Date getModifytime() {
		return modifytime;
	}

	/**
	 * @param modifytime
	 */
	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}

	@Override
	public String toString() {
		return "TSysEmployee [eid=" + eid + ", eno=" + eno + ", dno=" + dno + ", ename=" + ename + ", faceUrl="
				+ faceUrl + ", sex=" + sex + ", hiredate=" + hiredate + ", station=" + station + ", linkphone="
				+ linkphone + ", telephone=" + telephone + ", eMail=" + eMail + ", creator=" + creator + ", createtime="
				+ createtime + ", modifier=" + modifier + ", modifytime=" + modifytime + "]";
	}

	public String getUpperLeader() {
		return upperLeader;
	}

	public void setUpperLeader(String upperLeader) {
		this.upperLeader = upperLeader;
	}

	public String getUpperLeaderLink() {
		return upperLeaderLink;
	}

	public void setUpperLeaderLink(String upperLeaderLink) {
		this.upperLeaderLink = upperLeaderLink;
	}

}