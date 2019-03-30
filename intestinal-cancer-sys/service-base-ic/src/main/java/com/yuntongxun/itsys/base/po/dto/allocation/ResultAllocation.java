package com.yuntongxun.itsys.base.po.dto.allocation;/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: ResultAllocation
 * Author:   zongtong
 * Date:     2018/12/28 下午5:20
 * History:
 * <author>          <time>                <version>
 * zongtong           2018/12/28 下午5:20           v1.0.0
 */

import com.yuntongxun.itsys.base.po.HospitalColonoscopyReservationAllocationPO;

public class ResultAllocation extends HosAllocationRuleInfoDto {
    private Integer numTo;
    private Integer numToTime;
    private String communityDeptIdTo;
    private String beginTimeTo;
    private String endTimeTo;
    private String communityDeptIdInfoTo;
    private String beginTimeToTime;
    private String endTimeToTime;

    private Integer sumAmount;

    public Integer getNumTo() {
        return numTo;
    }

    public void setNumTo(Integer numTo) {
        this.numTo = numTo;
    }

    public Integer getNumToTime() {
        return numToTime;
    }

    public void setNumToTime(Integer numToTime) {
        this.numToTime = numToTime;
    }

    public String getCommunityDeptIdTo() {
        return communityDeptIdTo;
    }

    public void setCommunityDeptIdTo(String communityDeptIdTo) {
        this.communityDeptIdTo = communityDeptIdTo;
    }

    public String getBeginTimeTo() {
        return beginTimeTo;
    }

    public void setBeginTimeTo(String beginTimeTo) {
        this.beginTimeTo = beginTimeTo;
    }

    public String getEndTimeTo() {
        return endTimeTo;
    }

    public void setEndTimeTo(String endTimeTo) {
        this.endTimeTo = endTimeTo;
    }

    public String getCommunityDeptIdInfoTo() {
        return communityDeptIdInfoTo;
    }

    public void setCommunityDeptIdInfoTo(String communityDeptIdInfoTo) {
        this.communityDeptIdInfoTo = communityDeptIdInfoTo;
    }

    public String getBeginTimeToTime() {
        return beginTimeToTime;
    }

    public void setBeginTimeToTime(String beginTimeToTime) {
        this.beginTimeToTime = beginTimeToTime;
    }

    public String getEndTimeToTime() {
        return endTimeToTime;
    }

    public void setEndTimeToTime(String endTimeToTime) {
        this.endTimeToTime = endTimeToTime;
    }

    public Integer getSumAmount() {
        return sumAmount;
    }

    public void setSumAmount(Integer sumAmount) {
        this.sumAmount = sumAmount;
    }
}
