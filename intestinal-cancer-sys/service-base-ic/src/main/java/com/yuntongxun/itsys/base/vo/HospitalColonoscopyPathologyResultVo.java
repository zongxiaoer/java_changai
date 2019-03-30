package com.yuntongxun.itsys.base.vo;

import java.util.List;

/**
 * @author zongt
 * @date 2018/4/19
 */
public class HospitalColonoscopyPathologyResultVo {


    private Integer id;// int  物理主键，自增
    private String sid;//varchar(64) 受试者唯一标识
    private Integer colonoscopyResultId;// int  结肠镜结果记录表ID
    private String surveyDate;// datetime 调查日期
    private Integer item1;// int 是否需要国家癌症中心会诊（复阅）？1：是，2：否
    private Integer item2;// int 是否诊断为结直肠癌前病变：1：是，2：否
    private Integer item3;// int 是否诊断为结直肠癌：1：是，2：否
    private String diagnosisDate;// datetime 诊断日期
    private String doctorSign;// varchar(32) 医师签名
    private Integer stage;// int 阶段，1：T0，2：T1，3：T2
    private Integer communityDeptId;// int 社区ID
    private Integer areaDeptId;// int 地区医院ID
    private List<ColonoscopyPathologyDiagnosisRecordVo> colonoscopyPathologyDiagnosisRecordList;

    private String applyStatus;

    private String editStatus;

    private String approvalStatus;

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



    public List<ColonoscopyPathologyDiagnosisRecordVo> getColonoscopyPathologyDiagnosisRecordList() {
        return colonoscopyPathologyDiagnosisRecordList;
    }

    public void setColonoscopyPathologyDiagnosisRecordList(List<ColonoscopyPathologyDiagnosisRecordVo> colonoscopyPathologyDiagnosisRecordList) {
        this.colonoscopyPathologyDiagnosisRecordList = colonoscopyPathologyDiagnosisRecordList;
    }

    public Integer getColonoscopyResultId() {
        return colonoscopyResultId;
    }

    public void setColonoscopyResultId(Integer colonoscopyResultId) {
        this.colonoscopyResultId = colonoscopyResultId;
    }

    public String getSurveyDate() {
        return surveyDate;
    }

    public void setSurveyDate(String surveyDate) {
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

    public String getDiagnosisDate() {
        return diagnosisDate;
    }

    public void setDiagnosisDate(String diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }

    public String getDoctorSign() {
        return doctorSign;
    }

    public void setDoctorSign(String doctorSign) {
        this.doctorSign = doctorSign;
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
}
