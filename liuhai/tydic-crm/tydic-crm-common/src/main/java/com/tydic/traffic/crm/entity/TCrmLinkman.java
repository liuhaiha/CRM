package com.tydic.traffic.crm.entity;

import java.util.Date;
import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

@Table(name = "t_crm_linkman")
public class TCrmLinkman {
    @Id
    private Integer lid;

    private Integer cid;
    
    private Integer aid;

    public Integer getAid() {
		return aid;
	}

	public void setAid(Integer aid) {
		this.aid = aid;
	}

	private String name;

    /**
     * 编码,来源数据字典
     */
    private String sex;

    private String dept;

    @Column(name = "super_dept")
    private String superDept;

    /**
     * 编码,来源数据字典
     */
    private String post;

    private String officephone;

    /**
     * 可以有多个号码
     */
    private String telephone;

    private String email;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthday;

    private String hobby;

    private String remark;

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

    @Column(name = "is_delete")
    private String isDelete;

    /**
     * @return lid
     */
    public Integer getLid() {
        return lid;
    }

    /**
     * @param lid
     */
    public void setLid(Integer lid) {
        this.lid = lid;
    }

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
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取编码,来源数据字典
     *
     * @return sex - 编码,来源数据字典
     */
    public String getSex() {
        return sex;
    }

    /**
     * 设置编码,来源数据字典
     *
     * @param sex 编码,来源数据字典
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * @return dept
     */
    public String getDept() {
        return dept;
    }

    /**
     * @param dept
     */
    public void setDept(String dept) {
        this.dept = dept;
    }

    /**
     * @return super_dept
     */
    public String getSuperDept() {
        return superDept;
    }

    /**
     * @param superDept
     */
    public void setSuperDept(String superDept) {
        this.superDept = superDept;
    }

    /**
     * 获取编码,来源数据字典
     *
     * @return post - 编码,来源数据字典
     */
    public String getPost() {
        return post;
    }

    /**
     * 设置编码,来源数据字典
     *
     * @param post 编码,来源数据字典
     */
    public void setPost(String post) {
        this.post = post;
    }

    /**
     * @return officephone
     */
    public String getOfficephone() {
        return officephone;
    }

    /**
     * @param officephone
     */
    public void setOfficephone(String officephone) {
        this.officephone = officephone;
    }

    /**
     * 获取可以有多个号码
     *
     * @return telephone - 可以有多个号码
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * 设置可以有多个号码
     *
     * @param telephone 可以有多个号码
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return birthday
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * @param birthday
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * @return hobby
     */
    public String getHobby() {
        return hobby;
    }

    /**
     * @param hobby
     */
    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    /**
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
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