package com.tydic.traffic.crm.entity;

import java.util.Date;
import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Table(name = "t_crm_ext_apply")
public class TCrmExtApply {
    @Id
    private Integer aid;

    /**
     * --交流类型  01:新客户交流 02：已有客户交流
     */
    private String atype;

    /**
     *  --客户id
     */
    private Integer cid;

    private String cname;

    /**
     * 文件后缀，如docppt
     */
    private String addr;

    /**
     * 编码,来源数据字典
     */
    private String region;

    /**
     * 编码,来源数据字典
     */
    private String province;

    /**
     * 编码,来源数据字典
     */
    private String source;

    /**
     * 编码,来源数据字典
     */
    @Column(name = "unit_type")
    private String unitType;

    @Column(name = "ext_important")
    private String extImportant;

    /**
     * 编码,来源数据字典
     */
    @Column(name = "ext_theme")
    private String extTheme;

    @Column(name = "ext_other")
    private String extOther;

    /**
     * 1:已有项目 0:无项目
     */
    private String status;

    /**
     * 项目状态为1时需要填写
     */
    private String pname;

    /**
     * 编码,来源数据字典
     */
    private String stage;

    /**
     * 单位：万
     */
    private Float money;

    @Column(name = "est_invite")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date estInvite;

    private Integer period;

    /**
     * 客户是否明确要求与第三方公司合作,1:是 0:否
     */
    @Column(name = "cooper_other")
    private String cooperOther;

    /**
     * 我方最可能中标的角色是 1、总包   2、 分包   3、 其他
     */
    @Column(name = "win_role")
    private String winRole;

    private String hardware;

    private String technology;

    private String cloud;

    private String bigdata;

    @Column(name = "apply_no")
    private String applyNo;

    @Column(name = "apply_name")
    private String applyName;

    @Column(name = "apply_time")
    private Date applyTime;

    /**
     * 1：同意 0：不同意
     */
    @Column(name = "appr_status")
    private String apprStatus;

    @Column(name = "appr_no")
    private String apprNo;

    @Column(name = "appr_name")
    private String apprName;

    @Column(name = "appr_content")
    private String apprContent;

    private String extend1;

    private String extend2;

    private String extend3;

    private String extend4;

    private String extend5;

    private String extend6;

    @Column(name = "ext_time")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date extTime;

    @Column(name = "ext_addr")
    private String extAddr;
    
    @Column(name = "approvalOpinion")
    private String approvalOpinion;

    public String getApprovalOpinion() {
		return approvalOpinion;
	}

	public void setApprovalOpinion(String approvalOpinion) {
		this.approvalOpinion = approvalOpinion;
	}

	/**
     * --记录我方参与人工号，用','分隔
     */
    @Column(name = "ext_owner")
    private String extOwner;

    /**
     * -客户方参与人
     */
    @Column(name = "ext_cus")
    private String extCus;

    /**
     *  --第三方参与人
     */
    @Column(name = "ext_third")
    private String extThird;

    private Integer pid;

    @Column(name = "apply_state")
    private String applyState;

    /**
     * @return aid
     */
    public Integer getAid() {
        return aid;
    }

    /**
     * @param aid
     */
    public void setAid(Integer aid) {
        this.aid = aid;
    }

    /**
     * 获取--交流类型  01:新客户交流 02：已有客户交流
     *
     * @return atype - --交流类型  01:新客户交流 02：已有客户交流
     */
    public String getAtype() {
        return atype;
    }

    /**
     * 设置--交流类型  01:新客户交流 02：已有客户交流
     *
     * @param atype --交流类型  01:新客户交流 02：已有客户交流
     */
    public void setAtype(String atype) {
        this.atype = atype;
    }

    /**
     * 获取 --客户id
     *
     * @return cid -  --客户id
     */
    public Integer getCid() {
        return cid;
    }

    /**
     * 设置 --客户id
     *
     * @param cid  --客户id
     */
    public void setCid(Integer cid) {
        this.cid = cid;
    }

    /**
     * @return cname
     */
    public String getCname() {
        return cname;
    }

    /**
     * @param cname
     */
    public void setCname(String cname) {
        this.cname = cname;
    }

    /**
     * 获取文件后缀，如docppt
     *
     * @return addr - 文件后缀，如docppt
     */
    public String getAddr() {
        return addr;
    }

    /**
     * 设置文件后缀，如docppt
     *
     * @param addr 文件后缀，如docppt
     */
    public void setAddr(String addr) {
        this.addr = addr;
    }

    /**
     * 获取编码,来源数据字典
     *
     * @return region - 编码,来源数据字典
     */
    public String getRegion() {
        return region;
    }

    /**
     * 设置编码,来源数据字典
     *
     * @param region 编码,来源数据字典
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * 获取编码,来源数据字典
     *
     * @return province - 编码,来源数据字典
     */
    public String getProvince() {
        return province;
    }

    /**
     * 设置编码,来源数据字典
     *
     * @param province 编码,来源数据字典
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * 获取编码,来源数据字典
     *
     * @return source - 编码,来源数据字典
     */
    public String getSource() {
        return source;
    }

    /**
     * 设置编码,来源数据字典
     *
     * @param source 编码,来源数据字典
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * 获取编码,来源数据字典
     *
     * @return unit_type - 编码,来源数据字典
     */
    public String getUnitType() {
        return unitType;
    }

    /**
     * 设置编码,来源数据字典
     *
     * @param unitType 编码,来源数据字典
     */
    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    /**
     * @return ext_important
     */
    public String getExtImportant() {
        return extImportant;
    }

    /**
     * @param extImportant
     */
    public void setExtImportant(String extImportant) {
        this.extImportant = extImportant;
    }

    /**
     * 获取编码,来源数据字典
     *
     * @return ext_theme - 编码,来源数据字典
     */
    public String getExtTheme() {
        return extTheme;
    }

    /**
     * 设置编码,来源数据字典
     *
     * @param extTheme 编码,来源数据字典
     */
    public void setExtTheme(String extTheme) {
        this.extTheme = extTheme;
    }

    /**
     * @return ext_other
     */
    public String getExtOther() {
        return extOther;
    }

    /**
     * @param extOther
     */
    public void setExtOther(String extOther) {
        this.extOther = extOther;
    }

    /**
     * 获取1:已有项目 0:无项目
     *
     * @return status - 1:已有项目 0:无项目
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置1:已有项目 0:无项目
     *
     * @param status 1:已有项目 0:无项目
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取项目状态为1时需要填写
     *
     * @return pname - 项目状态为1时需要填写
     */
    public String getPname() {
        return pname;
    }

    /**
     * 设置项目状态为1时需要填写
     *
     * @param pname 项目状态为1时需要填写
     */
    public void setPname(String pname) {
        this.pname = pname;
    }

    /**
     * 获取编码,来源数据字典
     *
     * @return stage - 编码,来源数据字典
     */
    public String getStage() {
        return stage;
    }

    /**
     * 设置编码,来源数据字典
     *
     * @param stage 编码,来源数据字典
     */
    public void setStage(String stage) {
        this.stage = stage;
    }

    /**
     * 获取单位：万
     *
     * @return money - 单位：万
     */
    public Float getMoney() {
        return money;
    }

    /**
     * 设置单位：万
     *
     * @param money 单位：万
     */
    public void setMoney(Float money) {
        this.money = money;
    }

    /**
     * @return est_invite
     */
    public Date getEstInvite() {
        return estInvite;
    }

    /**
     * @param estInvite
     */
    public void setEstInvite(Date estInvite) {
        this.estInvite = estInvite;
    }

    /**
     * @return period
     */
    public Integer getPeriod() {
        return period;
    }

    /**
     * @param period
     */
    public void setPeriod(Integer period) {
        this.period = period;
    }

    /**
     * 获取客户是否明确要求与第三方公司合作,1:是 0:否
     *
     * @return cooper_other - 客户是否明确要求与第三方公司合作,1:是 0:否
     */
    public String getCooperOther() {
        return cooperOther;
    }

    /**
     * 设置客户是否明确要求与第三方公司合作,1:是 0:否
     *
     * @param cooperOther 客户是否明确要求与第三方公司合作,1:是 0:否
     */
    public void setCooperOther(String cooperOther) {
        this.cooperOther = cooperOther;
    }

    /**
     * 获取我方最可能中标的角色是 1、总包   2、 分包   3、 其他
     *
     * @return win_role - 我方最可能中标的角色是 1、总包   2、 分包   3、 其他
     */
    public String getWinRole() {
        return winRole;
    }

    /**
     * 设置我方最可能中标的角色是 1、总包   2、 分包   3、 其他
     *
     * @param winRole 我方最可能中标的角色是 1、总包   2、 分包   3、 其他
     */
    public void setWinRole(String winRole) {
        this.winRole = winRole;
    }

    /**
     * @return hardware
     */
    public String getHardware() {
        return hardware;
    }

    /**
     * @param hardware
     */
    public void setHardware(String hardware) {
        this.hardware = hardware;
    }

    /**
     * @return technology
     */
    public String getTechnology() {
        return technology;
    }

    /**
     * @param technology
     */
    public void setTechnology(String technology) {
        this.technology = technology;
    }

    /**
     * @return cloud
     */
    public String getCloud() {
        return cloud;
    }

    /**
     * @param cloud
     */
    public void setCloud(String cloud) {
        this.cloud = cloud;
    }

    /**
     * @return bigdata
     */
    public String getBigdata() {
        return bigdata;
    }

    /**
     * @param bigdata
     */
    public void setBigdata(String bigdata) {
        this.bigdata = bigdata;
    }

    /**
     * @return apply_no
     */
    public String getApplyNo() {
        return applyNo;
    }

    /**
     * @param applyNo
     */
    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
    }

    /**
     * @return apply_name
     */
    public String getApplyName() {
        return applyName;
    }

    /**
     * @param applyName
     */
    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    /**
     * @return apply_time
     */
    public Date getApplyTime() {
        return applyTime;
    }

    /**
     * @param applyTime
     */
    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    /**
     * 获取1：同意 0：不同意
     *
     * @return appr_status - 1：同意 0：不同意
     */
    public String getApprStatus() {
        return apprStatus;
    }

    /**
     * 设置1：同意 0：不同意
     *
     * @param apprStatus 1：同意 0：不同意
     */
    public void setApprStatus(String apprStatus) {
        this.apprStatus = apprStatus;
    }

    /**
     * @return appr_no
     */
    public String getApprNo() {
        return apprNo;
    }

    /**
     * @param apprNo
     */
    public void setApprNo(String apprNo) {
        this.apprNo = apprNo;
    }

    /**
     * @return appr_name
     */
    public String getApprName() {
        return apprName;
    }

    /**
     * @param apprName
     */
    public void setApprName(String apprName) {
        this.apprName = apprName;
    }

    /**
     * @return appr_content
     */
    public String getApprContent() {
        return apprContent;
    }

    /**
     * @param apprContent
     */
    public void setApprContent(String apprContent) {
        this.apprContent = apprContent;
    }

    /**
     * @return extend1
     */
    public String getExtend1() {
        return extend1;
    }

    /**
     * @param extend1
     */
    public void setExtend1(String extend1) {
        this.extend1 = extend1;
    }

    /**
     * @return extend2
     */
    public String getExtend2() {
        return extend2;
    }

    /**
     * @param extend2
     */
    public void setExtend2(String extend2) {
        this.extend2 = extend2;
    }

    /**
     * @return extend3
     */
    public String getExtend3() {
        return extend3;
    }

    /**
     * @param extend3
     */
    public void setExtend3(String extend3) {
        this.extend3 = extend3;
    }

    /**
     * @return extend4
     */
    public String getExtend4() {
        return extend4;
    }

    /**
     * @param extend4
     */
    public void setExtend4(String extend4) {
        this.extend4 = extend4;
    }

    /**
     * @return extend5
     */
    public String getExtend5() {
        return extend5;
    }

    /**
     * @param extend5
     */
    public void setExtend5(String extend5) {
        this.extend5 = extend5;
    }

    /**
     * @return extend6
     */
    public String getExtend6() {
        return extend6;
    }

    /**
     * @param extend6
     */
    public void setExtend6(String extend6) {
        this.extend6 = extend6;
    }

    /**
     * @return ext_time
     */
    public Date getExtTime() {
        return extTime;
    }

    /**
     * @param extTime
     */
    public void setExtTime(Date extTime) {
        this.extTime = extTime;
    }

    /**
     * @return ext_addr
     */
    public String getExtAddr() {
        return extAddr;
    }

    /**
     * @param extAddr
     */
    public void setExtAddr(String extAddr) {
        this.extAddr = extAddr;
    }

    /**
     * 获取--记录我方参与人工号，用','分隔
     *
     * @return ext_owner - --记录我方参与人工号，用','分隔
     */
    public String getExtOwner() {
        return extOwner;
    }

    /**
     * 设置--记录我方参与人工号，用','分隔
     *
     * @param extOwner --记录我方参与人工号，用','分隔
     */
    public void setExtOwner(String extOwner) {
        this.extOwner = extOwner;
    }

    /**
     * 获取-客户方参与人
     *
     * @return ext_cus - -客户方参与人
     */
    public String getExtCus() {
        return extCus;
    }

    /**
     * 设置-客户方参与人
     *
     * @param extCus -客户方参与人
     */
    public void setExtCus(String extCus) {
        this.extCus = extCus;
    }

    /**
     * 获取 --第三方参与人
     *
     * @return ext_third -  --第三方参与人
     */
    public String getExtThird() {
        return extThird;
    }

    /**
     * 设置 --第三方参与人
     *
     * @param extThird  --第三方参与人
     */
    public void setExtThird(String extThird) {
        this.extThird = extThird;
    }

    /**
     * @return pid
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * @param pid
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * @return apply_state
     */
    public String getApplyState() {
        return applyState;
    }

    /**
     * @param applyState
     */
    public void setApplyState(String applyState) {
        this.applyState = applyState;
    }
}