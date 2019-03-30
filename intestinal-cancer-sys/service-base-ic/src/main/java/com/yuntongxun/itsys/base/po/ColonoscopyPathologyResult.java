package com.yuntongxun.itsys.base.po;

import java.util.Date;
import java.util.List;

/**
 *结肠镜病理结果表
 */
public class ColonoscopyPathologyResult {
    private Integer id;// int  物理主键，自增
    private String sid;//varchar(64) 受试者唯一标识
    private Integer todoId;//待办ID
    private Integer colonoscopyRecordId;//结肠镜检查记录
    private Integer colonoscopyResultId;// int  结肠镜结果记录表ID
    private Date surveyDate;// datetime 调查日期
    private Integer item1;// int 是否需要国家癌症中心会诊（复阅）？1：是，2：否
    private Integer item2;// int 是否诊断为结直肠癌前病变：1：是，2：否
    private Integer item3;// int 是否诊断为结直肠癌：1：是，2：否
    private Date diagnosisDate;// datetime 诊断日期
    private String doctorSign;// varchar(32) 医师签名
    private Integer stage;// int 阶段，1：T0，2：T1，3：T2
    private Integer communityDeptId;// int 社区ID
    private Integer areaDeptId;// int 地区医院ID
    private Date dateCreated;// datetime 创建时间
    private Integer updateTime;// datetime 更新时间
    private Integer ColonoscopyResultId;//结肠镜结果记录表ID
    private String editoperationSource;
    private Integer operationSourceId;
    private String applyStatus;
    private String editStatus;
    private String approvalStatus;
    private List<ColonoscopyPathologyDiagnosisRecord> colonoscopyPathologyDiagnosisRecordList;
    private Integer delNotification;//是否删除告知书 1是 2否
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

    public Integer getColonoscopyResultId() {
        return colonoscopyResultId;
    }

    public void setColonoscopyResultId(Integer colonoscopyResultId) {
        this.colonoscopyResultId = colonoscopyResultId;
    }

    public Date getSurveyDate() {
        return surveyDate;
    }

    public void setSurveyDate(Date surveyDate) {
        this.surveyDate = surveyDate;
    }

    public Integer getItem1() {
        return item1;
    }

    public void setItem1(Integer item1) {
        this.item1 = item1;
    }

    public Integer getItem2() {
        return item2;
    }

    public void setItem2(Integer item2) {
        this.item2 = item2;
    }

    public Integer getItem3() {
        return item3;
    }

    public void setItem3(Integer item3) {
        this.item3 = item3;
    }

    public Date getDiagnosisDate() {
        return diagnosisDate;
    }

    public void setDiagnosisDate(Date diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }

    public String getDoctorSign() {
        return doctorSign;
    }

    public void setDoctorSign(String doctorSign) {
        this.doctorSign = doctorSign;
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
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

    public Integer getTodoId() {
        return todoId;
    }

    public void setTodoId(Integer todoId) {
        this.todoId = todoId;
    }

    public Integer getColonoscopyRecordId() {
        return colonoscopyRecordId;
    }

    public void setColonoscopyRecordId(Integer colonoscopyRecordId) {
        this.colonoscopyRecordId = colonoscopyRecordId;
    }

    public List<ColonoscopyPathologyDiagnosisRecord> getColonoscopyPathologyDiagnosisRecordList() {
        return colonoscopyPathologyDiagnosisRecordList;
    }

    public void setColonoscopyPathologyDiagnosisRecordList(List<ColonoscopyPathologyDiagnosisRecord> colonoscopyPathologyDiagnosisRecordList) {
        this.colonoscopyPathologyDiagnosisRecordList = colonoscopyPathologyDiagnosisRecordList;
    }

    public String getEditoperationSource() {
        return editoperationSource;
    }

    public void setEditoperationSource(String editoperationSource) {
        this.editoperationSource = editoperationSource;
    }

    public Integer getOperationSourceId() {
        return operationSourceId;
    }

    public void setOperationSourceId(Integer operationSourceId) {
        this.operationSourceId = operationSourceId;
    }

    public String getApplyStatus() {
        return applyStatus;
    }

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

    public Integer getDelNotification() {
        return delNotification;
    }

    public void setDelNotification(Integer delNotification) {
        this.delNotification = delNotification;
    }
}
