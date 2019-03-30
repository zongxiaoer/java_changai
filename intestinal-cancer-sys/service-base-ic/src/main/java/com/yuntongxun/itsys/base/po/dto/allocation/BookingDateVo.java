package com.yuntongxun.itsys.base.po.dto.allocation;/*
 * Copyright (C), 2015-2019, 壹永科技有限公司
 * FileName: BookingDateVo
 * Author:   zongtong
 * Date:     2019/1/2 下午6:33
 * History:
 * <author>          <time>                <version>
 * zongtong           2019/1/2 下午6:33           v1.0.0
 */

import java.util.Date;

public class BookingDateVo {
    private Integer week;
    private Integer num;
    private Date date;
    private String day;
    private String status;

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
