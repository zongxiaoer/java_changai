package com.yuntongxun.itsys.base.po;

import java.io.Serializable;
import java.util.Date;

public class HospitalBiologicalSampleResultPO implements Serializable {
    private Integer id;

    private String sid;

    private Integer stage;

    private String sampleId;

    private String sampleType;

    private Integer collectStatus;

    private Date collectStatusDate;

    private String frozenBoxCode;

    private String sampleColumn;

    private String sampleLine;

    private Integer communityDeptId;

    private Integer areaDeptId;

    private String sampleNote;

    private String courierId;

    private Integer courierStatus;

    private Date dateCreated;

    private Date updateTime;

    private String associatedSampleId;

    private String operationSource;

    private String applyStatus;

    private String editStatus;

    private String approvalStatus;

    private static final long serialVersionUID = 1L;

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
        this.sid = sid == null ? null : sid.trim();
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId == null ? null : sampleId.trim();
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType == null ? null : sampleType.trim();
    }

    public Integer getCollectStatus() {
        return collectStatus;
    }

    public void setCollectStatus(Integer collectStatus) {
        this.collectStatus = collectStatus;
    }

    public Date getCollectStatusDate() {
        return collectStatusDate;
    }

    public void setCollectStatusDate(Date collectStatusDate) {
        this.collectStatusDate = collectStatusDate;
    }

    public String getFrozenBoxCode() {
        return frozenBoxCode;
    }

    public void setFrozenBoxCode(String frozenBoxCode) {
        this.frozenBoxCode = frozenBoxCode == null ? null : frozenBoxCode.trim();
    }

    public String getSampleColumn() {
        return sampleColumn;
    }

    public void setSampleColumn(String sampleColumn) {
        this.sampleColumn = sampleColumn == null ? null : sampleColumn.trim();
    }

    public String getSampleLine() {
        return sampleLine;
    }

    public void setSampleLine(String sampleLine) {
        this.sampleLine = sampleLine == null ? null : sampleLine.trim();
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

    public String getSampleNote() {
        return sampleNote;
    }

    public void setSampleNote(String sampleNote) {
        this.sampleNote = sampleNote == null ? null : sampleNote.trim();
    }

    public String getCourierId() {
        return courierId;
    }

    public void setCourierId(String courierId) {
        this.courierId = courierId == null ? null : courierId.trim();
    }

    public Integer getCourierStatus() {
        return courierStatus;
    }

    public void setCourierStatus(Integer courierStatus) {
        this.courierStatus = courierStatus;
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

    public String getAssociatedSampleId() {
        return associatedSampleId;
    }

    public void setAssociatedSampleId(String associatedSampleId) {
        this.associatedSampleId = associatedSampleId == null ? null : associatedSampleId.trim();
    }

    public String getOperationSource() {
        return operationSource;
    }

    public void setOperationSource(String operationSource) {
        this.operationSource = operationSource == null ? null : operationSource.trim();
    }

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus == null ? null : applyStatus.trim();
    }

    public String getEditStatus() {
        return editStatus;
    }

    public void setEditStatus(String editStatus) {
        this.editStatus = editStatus == null ? null : editStatus.trim();
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus == null ? null : approvalStatus.trim();
    }
}