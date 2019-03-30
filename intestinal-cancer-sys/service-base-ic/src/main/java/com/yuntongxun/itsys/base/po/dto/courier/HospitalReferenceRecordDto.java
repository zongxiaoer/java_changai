package com.yuntongxun.itsys.base.po.dto.courier;/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: HospitalReferenceRecordDto
 * Author:   zongtong
 * Date:     2018/7/16 下午4:05
 * History:
 * <author>          <time>                <version>
 * zongtong           2018/7/16 下午4:05           v1.0.0
 */

import com.yuntongxun.itsys.base.po.HospitalReferenceRecordPO;

public class HospitalReferenceRecordDto extends HospitalReferenceRecordPO {

    private String applyStatus;

    private String editStatus;

    private String approvalStatus;

    private String editPerson;

    private String sid;

    private String fitType;

    private Integer recordId;//肠镜还需要一个检查id

    @Override
    public String getApplyStatus() {
        return applyStatus;
    }

    @Override
    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getEditStatus() {
        return editStatus;
    }

    public void setEditStatus(String editStatus) {
        this.editStatus = editStatus;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public HospitalReferenceRecordDto(String applyStatus, String editStatus, String approvalStatus) {
        this.applyStatus = applyStatus;
        this.editStatus = editStatus;
        this.approvalStatus = approvalStatus;
    }

    public String getEditPerson() {
        return editPerson;
    }

    public void setEditPerson(String editPerson) {
        this.editPerson = editPerson;
    }

    public HospitalReferenceRecordDto() {
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid == null ? null : sid.trim();
    }

    public String getFitType() {
        return fitType;
    }

    public void setFitType(String fitType) {
        this.fitType = fitType;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }
}
