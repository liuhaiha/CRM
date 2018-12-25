package com.tydic.traffic.crm.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_crm_log_item")
public class TCrmLogItem {
	@Id
	@Column(name = "itemID")
	private Integer itemid;

	@Column(name = "logID")
	private Integer logid;

	private String logtype;
	
	private Integer pid;


	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	private String content;

	public Integer getDelstate() {
		return delstate;
	}

	public void setDelstate(Integer delstate) {
		this.delstate = delstate;
	}

	private Integer delstate;

	/**
	 * 填报人姓名
	 */
	private String creator;

	/**
	 * 填报时间
	 */
	private Date createtime;

	/**
	 * 修改人
	 */
	private String modifier;

	/**
	 * 修改时间
	 */
	private Date modifytime;

	/**
	 * @return itemID
	 */
	public Integer getItemid() {
		return itemid;
	}

	/**
	 * @param itemid
	 */
	public void setItemid(Integer itemid) {
		this.itemid = itemid;
	}

	/**
	 * @return logID
	 */
	public Integer getLogid() {
		return logid;
	}

	/**
	 * @param logid
	 */
	public void setLogid(Integer logid) {
		this.logid = logid;
	}

	/**
	 * @return logtype
	 */
	public String getLogtype() {
		return logtype;
	}

	/**
	 * @param logtype
	 */
	public void setLogtype(String logtype) {
		this.logtype = logtype;
	}

	/**
	 * @return content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 获取填报人姓名
	 *
	 * @return creator - 填报人姓名
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * 设置填报人姓名
	 *
	 * @param creator
	 *            填报人姓名
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}

	/**
	 * 获取填报时间
	 *
	 * @return createtime - 填报时间
	 */
	public Date getCreatetime() {
		return createtime;
	}

	/**
	 * 设置填报时间
	 *
	 * @param createtime
	 *            填报时间
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	/**
	 * 获取修改人
	 *
	 * @return modifier - 修改人
	 */
	public String getModifier() {
		return modifier;
	}

	/**
	 * 设置修改人
	 *
	 * @param modifier
	 *            修改人
	 */
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	/**
	 * 获取修改时间
	 *
	 * @return modifytime - 修改时间
	 */
	public Date getModifytime() {
		return modifytime;
	}

	/**
	 * 设置修改时间
	 *
	 * @param modifytime
	 *            修改时间
	 */
	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}
}