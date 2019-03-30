package com.yuntongxun.itsys.base.po.dto.allocation;/*
 * Copyright (C), 2015-2019, 壹永科技有限公司
 * FileName: RestAllocation
 * Author:   zongtong
 * Date:     2019/1/3 上午10:19
 * History:
 * <author>          <time>                <version>
 * zongtong           2019/1/3 上午10:19           v1.0.0
 */

public class RestAllocation {
    private String name;
    private String communityDeptId;

    private String project;
    private Integer fanghaoNum;
    private Integer yuyueNum;
    private String  ruleId;
    private String levelName;
    private String reservationDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommunityDeptId() {
        return communityDeptId;
    }

    public void setCommunityDeptId(String communityDeptId) {
        this.communityDeptId = communityDeptId;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public Integer getFanghaoNum() {
        return fanghaoNum;
    }

    public void setFanghaoNum(Integer fanghaoNum) {
        this.fanghaoNum = fanghaoNum;
    }

    public Integer getYuyueNum() {
        return yuyueNum;
    }

    public void setYuyueNum(Integer yuyueNum) {
        this.yuyueNum = yuyueNum;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }
}
