package com.tydic.traffic.crm.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_crm_customer")
public class TCrmCustomer {
    @Id
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
    private String resource;

    /**
     * 编码,来源数据字典
     */
    @Column(name = "unit_type")
    private String unitType;

    private String extend1;

    private String extend2;

    private String extend3;

    private String extend4;

    private String extend5;

    private String extend6;

    private String creator;

    private Date createtime;

    private String modifier;

    private Date modifytime;

    /**
     * @return cid
     */
    public Integer getCid() {
        return cid;
    }

    /**
     * @param cid
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
     * @return resource - 编码,来源数据字典
     */
    public String getResource() {
        return resource;
    }

    /**
     * 设置编码,来源数据字典
     *
     * @param resource 编码,来源数据字典
     */
    public void setResource(String resource) {
        this.resource = resource;
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
}