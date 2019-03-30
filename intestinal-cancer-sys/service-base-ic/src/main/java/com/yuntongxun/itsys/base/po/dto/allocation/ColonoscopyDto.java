package com.yuntongxun.itsys.base.po.dto.allocation;/*
 * Copyright (C), 2015-2019, 壹永科技有限公司
 * FileName: ColonoscopyDto
 * Author:   zongtong
 * Date:     2019/1/7 上午10:33
 * History:
 * <author>          <time>                <version>
 * zongtong           2019/1/7 上午10:33           v1.0.0
 */

import com.yuntongxun.itsys.base.po.HospitalColonoscopyRecord;

public class ColonoscopyDto extends HospitalColonoscopyRecord {
    private String reservationDate;
    private String startTime;
    private String endTime;
    private String ruleId;
    private String noticeStatus;
    private String phone;
    private String createUser;
    private String name;

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getNoticeStatus() {
        return noticeStatus;
    }

    public void setNoticeStatus(String noticeStatus) {
        this.noticeStatus = noticeStatus;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
