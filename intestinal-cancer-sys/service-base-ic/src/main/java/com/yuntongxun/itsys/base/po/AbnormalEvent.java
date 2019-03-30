package com.yuntongxun.itsys.base.po;

import java.util.Date;

/**
 * 异常事件
 */
public class AbnormalEvent {

    private Integer id;// 物理主键，自增
    private String sid;//受试者唯一标识
    private Integer stage;//阶段，1：T0，2：T1，3：T2，4：T3
    private Integer eventType;//1.违反方案   2.不良事件
    private Integer dataId;//对应违反方案表或者不良事件表中id
    private Integer communityDeptId;
    private Integer areaDeptId;
    private Date dateCreated; //创建时间
    private Date updateTime; //更新时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public Integer getDataId() {
        return dataId;
    }

    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getCommunityDeptId() {
        return communityDeptId;
    }

    public void setCommunityDeptId(Integer communityDeptId) {
        this.communityDeptId = communityDeptId;
    }

    public Integer getAreaDeptId() {
        return areaDeptId;
    }

    public void setAreaDeptId(Integer areaDeptId) {
        this.areaDeptId = areaDeptId;
    }
}
