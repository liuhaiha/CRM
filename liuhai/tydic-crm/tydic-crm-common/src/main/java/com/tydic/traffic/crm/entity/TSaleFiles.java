package com.tydic.traffic.crm.entity;

import java.util.Date;

import javax.persistence.*;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;


@Table(name = "t_sale_files")
public class TSaleFiles {
    @Id
    private Integer fid;

    private String fname;

    /**
     * 文件后缀，如docppt
     */
    private String ftype;

    /**
     * 单位：K
     */
    private Float fsize;

    /**
     * 提醒：保存路径中不要包含中文
     */
    private String fpath;

    /**
     * 如：交通大数据，来源数据字典
     */
    private String fclass;

    private Float fstar;

    /**
     * 1:是 0:否
     */
    private String common;

    private String creator;

    private Date createtime;

    private String modifier;

    private Date modifytime;

    /**
     * @return fid
     */
    public Integer getFid() {
        return fid;
    }

    /**
     * @param fid
     */
    public void setFid(Integer fid) {
        this.fid = fid;
    }

    /**
     * @return fname
     */
    public String getFname() {
        return fname;
    }

    /**
     * @param fname
     */
    public void setFname(String fname) {
        this.fname = fname;
    }

    /**
     * 获取文件后缀，如docppt
     *
     * @return ftype - 文件后缀，如docppt
     */
    public String getFtype() {
        return ftype;
    }

    /**
     * 设置文件后缀，如docppt
     *
     * @param ftype 文件后缀，如docppt
     */
    public void setFtype(String ftype) {
        this.ftype = ftype;
    }

    /**
     * 获取单位：K
     *
     * @return fsize - 单位：K
     */
    public Float getFsize() {
        return fsize;
    }

    /**
     * 设置单位：K
     *
     * @param fsize 单位：K
     */
    public void setFsize(Float fsize) {
        this.fsize = fsize;
    }

    /**
     * 获取提醒：保存路径中不要包含中文
     *
     * @return fpath - 提醒：保存路径中不要包含中文
     */
    public String getFpath() {
        return fpath;
    }

    /**
     * 设置提醒：保存路径中不要包含中文
     *
     * @param fpath 提醒：保存路径中不要包含中文
     */
    public void setFpath(String fpath) {
        this.fpath = fpath;
    }

    /**
     * 获取如：交通大数据，来源数据字典
     *
     * @return fclass - 如：交通大数据，来源数据字典
     */
    public String getFclass() {
        return fclass;
    }

    /**
     * 设置如：交通大数据，来源数据字典
     *
     * @param fclass 如：交通大数据，来源数据字典
     */
    public void setFclass(String fclass) {
        this.fclass = fclass;
    }

    /**
     * @return fstar
     */
    public Float getFstar() {
        return fstar;
    }

    /**
     * @param fstar
     */
    public void setFstar(Float fstar) {
        this.fstar = fstar;
    }

    /**
     * 获取1:是 0:否
     *
     * @return common - 1:是 0:否
     */
    public String getCommon() {
        return common;
    }

    /**
     * 设置1:是 0:否
     *
     * @param common 1:是 0:否
     */
    public void setCommon(String common) {
        this.common = common;
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