package com.tydic.traffic.crm.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_sys_user")
public class TSysUser {
    @Id
    private Integer uid;

    private Integer eid;

    /**
     * 默认使用员工编号
     */
    private String uname;

    private String upass;

    /**
     * 1、正常 2、停用 3、锁定
     */
    private Integer ustatus;

    /**
     * 记录帐号被锁定时间，根据安全策略，可设置每隔20分数自动解锁
     */
    @Column(name = "lock_time")
    private Date lockTime;

    /**
     * 记录登录失败次数，出于安全考虑，超过一定次数，锁定该帐号。
     */
    @Column(name = "fail_num")
    private Integer failNum;

    /**
     * 1:是 0:否
     */
    @Column(name = "first_login")
    private String firstLogin;

    private String creator;

    private Date createtime;

    private String modifier;

    private Date modifytime;

    /**
     * @return uid
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * @param uid
     */
    public void setUid(Integer uid) {
        this.uid = uid;
    }

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
     * 获取默认使用员工编号
     *
     * @return uname - 默认使用员工编号
     */
    public String getUname() {
        return uname;
    }

    /**
     * 设置默认使用员工编号
     *
     * @param uname 默认使用员工编号
     */
    public void setUname(String uname) {
        this.uname = uname;
    }

    /**
     * @return upass
     */
    public String getUpass() {
        return upass;
    }

    /**
     * @param upass
     */
    public void setUpass(String upass) {
        this.upass = upass;
    }

    /**
     * 获取1、正常 2、停用 3、锁定
     *
     * @return ustatus - 1、正常 2、停用 3、锁定
     */
    public Integer getUstatus() {
        return ustatus;
    }

    /**
     * 设置1、正常 2、停用 3、锁定
     *
     * @param ustatus 1、正常 2、停用 3、锁定
     */
    public void setUstatus(Integer ustatus) {
        this.ustatus = ustatus;
    }

    /**
     * 获取记录帐号被锁定时间，根据安全策略，可设置每隔20分数自动解锁
     *
     * @return lockTime - 记录帐号被锁定时间，根据安全策略，可设置每隔20分数自动解锁
     */
    public Date getLockTime() {
        return lockTime;
    }

    /**
     * 设置记录帐号被锁定时间，根据安全策略，可设置每隔20分数自动解锁
     *
     * @param lockTime 记录帐号被锁定时间，根据安全策略，可设置每隔20分数自动解锁
     */
    public void setLockTime(Date lockTime) {
        this.lockTime = lockTime;
    }

    /**
     * 获取记录登录失败次数，出于安全考虑，超过一定次数，锁定该帐号。
     *
     * @return station - 记录登录失败次数，出于安全考虑，超过一定次数，锁定该帐号。
     */
    public Integer getFailNum() {
        return failNum;
    }

    /**
     * 设置记录登录失败次数，出于安全考虑，超过一定次数，锁定该帐号。
     *
     * @param station 记录登录失败次数，出于安全考虑，超过一定次数，锁定该帐号。
     */
    public void setFailNum(Integer failNum) {
        this.failNum = failNum;
    }

    /**
     * 获取1:是 0:否
     *
     * @return first_login - 1:是 0:否
     */
    public String getFirstLogin() {
        return firstLogin;
    }

    /**
     * 设置1:是 0:否
     *
     * @param firstLogin 1:是 0:否
     */
    public void setFirstLogin(String firstLogin) {
        this.firstLogin = firstLogin;
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
		return "TSysUser [uid=" + uid + ", eid=" + eid + ", uname=" + uname + ", upass=" + upass + ", ustatus="
				+ ustatus + ", lockTime=" + lockTime + ", failNum=" + failNum + ", firstLogin=" + firstLogin
				+ ", creator=" + creator + ", createtime=" + createtime + ", modifier=" + modifier + ", modifytime="
				+ modifytime + "]";
	}
}