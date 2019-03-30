package com.yuntongxun.itsys.base.po;

import java.io.Serializable;
import java.util.Date;

public class HospitalMessageCenterPO implements Serializable {
    private String id;

    private String sendUser;

    private String acceptUser;

    private String messageType;

    private String messageText;

    private String sid;

    private String readStatus;

    private String recycleStatus;

    private Date dateCreated;

    private Date updateTime;

    private Integer mainDataId;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSendUser() {
        return sendUser;
    }

    public void setSendUser(String sendUser) {
        this.sendUser = sendUser == null ? null : sendUser.trim();
    }

    public String getAcceptUser() {
        return acceptUser;
    }

    public void setAcceptUser(String acceptUser) {
        this.acceptUser = acceptUser == null ? null : acceptUser.trim();
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType == null ? null : messageType.trim();
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText == null ? null : messageText.trim();
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid == null ? null : sid.trim();
    }

    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus == null ? null : readStatus.trim();
    }

    public String getRecycleStatus() {
        return recycleStatus;
    }

    public void setRecycleStatus(String recycleStatus) {
        this.recycleStatus = recycleStatus == null ? null : recycleStatus.trim();
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

    public Integer getMainDataId() {
        return mainDataId;
    }

    public void setMainDataId(Integer mainDataId) {
        this.mainDataId = mainDataId;
    }
}