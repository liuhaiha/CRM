package com.tydic.traffic.crm.entity;

import javax.persistence.*;

@Table(name = "t_sys_user_ref_role")
public class TSysUserRefRole {
    @Id
    private Integer urid;

    private Integer rid;

    private Integer uid;

    /**
     * @return urid
     */
    public Integer getUrid() {
        return urid;
    }

    /**
     * @param urid
     */
    public void setUrid(Integer urid) {
        this.urid = urid;
    }

    /**
     * @return rid
     */
    public Integer getRid() {
        return rid;
    }

    /**
     * @param rid
     */
    public void setRid(Integer rid) {
        this.rid = rid;
    }

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
}