package com.tydic.traffic.crm.entity;

import java.util.Date;
import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Table(name = "t_crm_meeting_record")
public class TCrmMeetingRecord {
    @Id
    private Integer id;

    /**
     * 关联客户表cid
     */
    private Integer cid;

    /**
     * 关联项目表pid
     */
    private Integer pid;

    /**
     * 关联客户交流申请表aid
     */
    private Integer aid;

    private String theme;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date mtime;

    private String maddr;

    private String peer;

    private String customers;

    @Column(name = "thirdMan")
    private String thirdman;

    private String recordno;

    private String recordname;

    private String process;

    private String summary;

    @Column(name = "next_plan")
    private String nextPlan;

    private String creator;

    private Date createtime;

    private String modifier;

    private Date modifytime;

    private Integer fileno;

    @Column(name = "is_delete")
    private String isDelete;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取关联客户表cid
     *
     * @return cid - 关联客户表cid
     */
    public Integer getCid() {
        return cid;
    }

    /**
     * 设置关联客户表cid
     *
     * @param cid 关联客户表cid
     */
    public void setCid(Integer cid) {
        this.cid = cid;
    }

    /**
     * 获取关联项目表pid
     *
     * @return pid - 关联项目表pid
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * 设置关联项目表pid
     *
     * @param pid 关联项目表pid
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * 获取关联客户交流申请表aid
     *
     * @return aid - 关联客户交流申请表aid
     */
    public Integer getAid() {
        return aid;
    }

    /**
     * 设置关联客户交流申请表aid
     *
     * @param aid 关联客户交流申请表aid
     */
    public void setAid(Integer aid) {
        this.aid = aid;
    }

    /**
     * @return theme
     */
    public String getTheme() {
        return theme;
    }

    /**
     * @param theme
     */
    public void setTheme(String theme) {
        this.theme = theme;
    }

    /**
     * @return mtime
     */
    public Date getMtime() {
        return mtime;
    }

    /**
     * @param mtime
     */
    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }

    /**
     * @return maddr
     */
    public String getMaddr() {
        return maddr;
    }

    /**
     * @param maddr
     */
    public void setMaddr(String maddr) {
        this.maddr = maddr;
    }

    /**
     * @return peer
     */
    public String getPeer() {
        return peer;
    }

    /**
     * @param peer
     */
    public void setPeer(String peer) {
        this.peer = peer;
    }

    /**
     * @return customers
     */
    public String getCustomers() {
        return customers;
    }

    /**
     * @param customers
     */
    public void setCustomers(String customers) {
        this.customers = customers;
    }

    /**
     * @return thirdMan
     */
    public String getThirdman() {
        return thirdman;
    }

    /**
     * @param thirdman
     */
    public void setThirdman(String thirdman) {
        this.thirdman = thirdman;
    }

    /**
     * @return recordno
     */
    public String getRecordno() {
        return recordno;
    }

    /**
     * @param recordno
     */
    public void setRecordno(String recordno) {
        this.recordno = recordno;
    }

    /**
     * @return recordname
     */
    public String getRecordname() {
        return recordname;
    }

    /**
     * @param recordname
     */
    public void setRecordname(String recordname) {
        this.recordname = recordname;
    }

    /**
     * @return process
     */
    public String getProcess() {
        return process;
    }

    /**
     * @param process
     */
    public void setProcess(String process) {
        this.process = process;
    }

    /**
     * @return summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * @param summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * @return next_plan
     */
    public String getNextPlan() {
        return nextPlan;
    }

    /**
     * @param nextPlan
     */
    public void setNextPlan(String nextPlan) {
        this.nextPlan = nextPlan;
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

    /**
     * @return fileno
     */
    public Integer getFileno() {
        return fileno;
    }

    /**
     * @param fileno
     */
    public void setFileno(Integer fileno) {
        this.fileno = fileno;
    }

    /**
     * @return is_delete
     */
    public String getIsDelete() {
        return isDelete;
    }

    /**
     * @param isDelete
     */
    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }
}