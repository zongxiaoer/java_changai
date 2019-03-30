package com.yuntongxun.itsys.base.po;

import java.io.Serializable;
import java.util.Date;

public class HospitalReferenceRecordPO implements Serializable {
    private Integer id;

    private Integer dataId;

    private String formType;

    private String remark;

    private String applyStatus;

    private Date applyDate;

    private String noApplyStatus;

    private Date noApplyDate;

    private String sendPerson;

    private String acceptPerson;

    private String oldData;

    private String status;

    private Integer mainDataId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDataId() {
        return dataId;
    }

    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType == null ? null : formType.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus == null ? null : applyStatus.trim();
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getNoApplyStatus() {
        return noApplyStatus;
    }

    public void setNoApplyStatus(String noApplyStatus) {
        this.noApplyStatus = noApplyStatus == null ? null : noApplyStatus.trim();
    }

    public Date getNoApplyDate() {
        return noApplyDate;
    }

    public void setNoApplyDate(Date noApplyDate) {
        this.noApplyDate = noApplyDate;
    }

    public String getSendPerson() {
        return sendPerson;
    }

    public void setSendPerson(String sendPerson) {
        this.sendPerson = sendPerson == null ? null : sendPerson.trim();
    }

    public String getAcceptPerson() {
        return acceptPerson;
    }

    public void setAcceptPerson(String acceptPerson) {
        this.acceptPerson = acceptPerson == null ? null : acceptPerson.trim();
    }

    public String getOldData() {
        return oldData;
    }

    public void setOldData(String oldData) {
        this.oldData = oldData == null ? null : oldData.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Integer getMainDataId() {
        return mainDataId;
    }

    public void setMainDataId(Integer mainDataId) {
        this.mainDataId = mainDataId;
    }
}