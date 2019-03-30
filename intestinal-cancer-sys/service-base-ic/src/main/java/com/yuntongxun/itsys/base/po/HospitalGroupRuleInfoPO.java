package com.yuntongxun.itsys.base.po;

import java.io.Serializable;

public class HospitalGroupRuleInfoPO implements Serializable {
    private Integer id;

    private String msg;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }
}