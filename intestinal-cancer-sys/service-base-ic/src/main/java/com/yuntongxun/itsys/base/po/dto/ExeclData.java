package com.yuntongxun.itsys.base.po.dto;/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: ExeclData
 * Author:   zongtong
 * Date:     2018/8/22 上午10:58
 * History:
 * <author>          <time>                <version>
 * zongtong           2018/8/22 上午10:58           v1.0.0
 */

public class ExeclData {
    private Integer stage_cy;
    private String loginName;
    private Integer areaDeptId;
    private Integer communityDeptId;
    private String  group;
    private String  inGroupDateStart;//入组开始时间
    private String  inGroupDateEnd;//入组结束时间
    private Integer  siteId;//筛查ID
    private String type;   //类型



    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Integer getStage_cy() {
        return stage_cy;
    }

    public void setStage_cy(Integer stage_cy) {
        this.stage_cy = stage_cy;
    }

    public Integer getAreaDeptId() {
        return areaDeptId;
    }

    public void setAreaDeptId(Integer areaDeptId) {
        this.areaDeptId = areaDeptId;
    }

    public Integer getCommunityDeptId() {
        return communityDeptId;
    }

    public void setCommunityDeptId(Integer communityDeptId) {
        this.communityDeptId = communityDeptId;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getInGroupDateStart() {
        return inGroupDateStart;
    }

    public void setInGroupDateStart(String inGroupDateStart) {
        this.inGroupDateStart = inGroupDateStart;
    }

    public String getInGroupDateEnd() {
        return inGroupDateEnd;
    }

    public void setInGroupDateEnd(String inGroupDateEnd) {
        this.inGroupDateEnd = inGroupDateEnd;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
