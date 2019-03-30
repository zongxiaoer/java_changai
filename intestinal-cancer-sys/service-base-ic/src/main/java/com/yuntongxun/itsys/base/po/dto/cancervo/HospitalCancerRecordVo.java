package com.yuntongxun.itsys.base.po.dto.cancervo;/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: HospitalCancerRecordVo
 * Author:   zongtong
 * Date:     2018/9/6 上午10:50
 * History:
 * <author>          <time>                <version>
 * zongtong           2018/9/6 上午10:50           v1.0.0
 */

import com.yuntongxun.itsys.base.po.cancer.HospitalCancerRecordPO;

public class HospitalCancerRecordVo extends HospitalCancerRecordPO {

    private int pageNo;
    private int pageSize;

    private String name;
    private String phone;
    private String  group;

    private String loginName;

    private Integer overallStatusCy;
    private String  inGroupDateStart;//入组开始时间
    private String  inGroupDateEnd;//入组结束时间

    private String cancerType;//表单类型 1--c1 2--c2 3--c3 4--c4

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group == null ? null : group.trim();
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getOverallStatusCy() {
        return overallStatusCy;
    }

    public void setOverallStatusCy(Integer overallStatusCy) {
        this.overallStatusCy = overallStatusCy;
    }

    public String getInGroupDateStart() {
        return inGroupDateStart;
    }

    public void setInGroupDateStart(String inGroupDateStart) {
        this.inGroupDateStart = inGroupDateStart == null ? null : inGroupDateStart.trim();

    }

    public String getInGroupDateEnd() {
        return inGroupDateEnd;
    }

    public void setInGroupDateEnd(String inGroupDateEnd) {
        this.inGroupDateEnd = inGroupDateEnd == null ? null : inGroupDateEnd.trim();
    }

    public String getCancerType() {
        return cancerType;
    }

    public void setCancerType(String cancerType) {
        this.cancerType = cancerType == null ? null : cancerType.trim();
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
}
