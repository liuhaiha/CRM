package com.tydic.traffic.crm.entity;

import javax.persistence.*;

@Table(name = "t_sys_role_ref_permission")
public class TSysRoleRefPermission {
    @Id
    private Integer rpid;

    private Integer rid;

    private Integer id;

    /**
     * @return rpid
     */
    public Integer getRpid() {
        return rpid;
    }

    /**
     * @param rpid
     */
    public void setRpid(Integer rpid) {
        this.rpid = rpid;
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
}