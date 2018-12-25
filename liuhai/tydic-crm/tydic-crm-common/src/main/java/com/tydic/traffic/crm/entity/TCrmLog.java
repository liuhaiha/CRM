package com.tydic.traffic.crm.entity;

import java.util.Date;
import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Table(name = "t_crm_log")
public class TCrmLog {
	@Id
	@Column(name = "logID")
	private Integer logid;

	private String content;

	/**
	 * 填报人姓名
	 */
	private String creator;

	/**
	 * 填报人工号
	 */
	@Column(name = "creator_no")
	private String creatorNo;

	/**
	 * 填报时间
	 */
	private Date createtime;

	/**
	 * 存在问题
	 */
	private String problem;

	/**
	 * 下一步计划
	 */
	private String nextplan;

	/**
	 * 需要协调内容
	 */
	private String needhelp;
	/**
	 * 批阅意见
	 */
	private String opinion;
	/**
	 * 是否已读 1已读 0未读
	 */
	private Integer state;
	/**
	 * 是否已批阅 1已阅 0未阅
	 */
	private Integer readstate;
	public Integer getReadstate() {
		return readstate;
	}

	public void setReadstate(Integer readstate) {
		this.readstate = readstate;
	}

	/**
	 * 日志时间
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date logtime;
	/**
	 * 
	 * 是否被删除 1删除 0未删除
	 */
	private Integer delstate;

	/**
	 * 扩展字段1
	 */
	private String extend1;

	/**
	 * 扩展字段2
	 */
	private String extend2;

	/**
	 * 扩展字段3
	 */
	private String extend3;

	/**
	 * 修改人
	 */
	private String modifier;

	/**
	 * 修改时间
	 */
	private Date modifytime;

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
	 * 获取填报人工号
	 *
	 * @return creator_no - 填报人工号
	 */
	public String getCreatorNo() {
		return creatorNo;
	}

	/**
	 * 设置填报人工号
	 *
	 * @param creatorNo
	 *            填报人工号
	 */
	public void setCreatorNo(String creatorNo) {
		this.creatorNo = creatorNo;
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
	 * 获取存在问题
	 *
	 * @return problem - 存在问题
	 */
	public String getProblem() {
		return problem;
	}

	/**
	 * 设置存在问题
	 *
	 * @param problem
	 *            存在问题
	 */
	public void setProblem(String problem) {
		this.problem = problem;
	}

	/**
	 * 获取下一步计划
	 *
	 * @return nextplan - 下一步计划
	 */
	public String getNextplan() {
		return nextplan;
	}

	/**
	 * 设置下一步计划
	 *
	 * @param nextplan
	 *            下一步计划
	 */
	public void setNextplan(String nextplan) {
		this.nextplan = nextplan;
	}

	/**
	 * 获取需要协调内容
	 *
	 * @return needhelp - 需要协调内容
	 */
	public String getNeedhelp() {
		return needhelp;
	}

	/**
	 * 设置需要协调内容
	 *
	 * @param needhelp
	 *            需要协调内容
	 */
	public void setNeedhelp(String needhelp) {
		this.needhelp = needhelp;
	}

	/**
	 * 获取扩展字段1
	 *
	 * @return extend1 - 扩展字段1
	 */
	public String getExtend1() {
		return extend1;
	}

	/**
	 * 设置扩展字段1
	 *
	 * @param extend1
	 *            扩展字段1
	 */
	public void setExtend1(String extend1) {
		this.extend1 = extend1;
	}

	/**
	 * 获取扩展字段2
	 *
	 * @return extend2 - 扩展字段2
	 */
	public String getExtend2() {
		return extend2;
	}

	/**
	 * 设置扩展字段2
	 *
	 * @param extend2
	 *            扩展字段2
	 */
	public void setExtend2(String extend2) {
		this.extend2 = extend2;
	}

	/**
	 * 获取扩展字段3
	 *
	 * @return extend3 - 扩展字段3
	 */
	public String getExtend3() {
		return extend3;
	}

	/**
	 * 设置扩展字段3
	 *
	 * @param extend3
	 *            扩展字段3
	 */
	public void setExtend3(String extend3) {
		this.extend3 = extend3;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getLogtime() {
		return logtime;
	}

	public void setLogtime(Date logtime) {
		this.logtime = logtime;
	}
	public Integer getDelstate() {
		return delstate;
	}

	public void setDelstate(Integer delstate) {
		this.delstate = delstate;
	}
}