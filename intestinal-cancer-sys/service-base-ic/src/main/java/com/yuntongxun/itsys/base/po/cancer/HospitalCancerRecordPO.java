package com.yuntongxun.itsys.base.po.cancer;

import java.io.Serializable;
import java.util.Date;

public class HospitalCancerRecordPO implements Serializable {
    private Integer id;

    private String sid;

    private Integer pathologyId;

    private Integer colonoscopyRecordId;

    private Integer communityDeptId;

    private Integer areaDeptId;

    private Integer cancerReportId;

    private String cancerReportStatus;

    private Date cancerReportInStatusDate;

    private Integer cancerDiagnoseId;

    private String cancerDiagnoseStatus;

    private Date cancerDiagnoseInStatusDate;

    private Integer colorectalCancerDiagnoseInformationId;

    private String colorectalCancerDiagnoseInformationStatus;

    private Date colorectalCancerDiagnoseInformationInStatusDate;

    private Integer colorectalCancerTreatmentInformationId;

    private String colorectalCancerTreatmentInformatioStatus;

    private Date colorectalCancerTreatmentInformatioInStatusDate;

    private Integer deathCertificateId;

    private String deathCertificateStatus;

    private String endEventType;

    private String validData;

    private Date dateCreated;

    private Date updateTime;

    private Integer deptCode;

    private Integer createUser;

    private Integer stage;

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

    public Integer getPathologyId() {
        return pathologyId;
    }

    public void setPathologyId(Integer pathologyId) {
        this.pathologyId = pathologyId;
    }

    public Integer getColonoscopyRecordId() {
        return colonoscopyRecordId;
    }

    public void setColonoscopyRecordId(Integer colonoscopyRecordId) {
        this.colonoscopyRecordId = colonoscopyRecordId;
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

    public Integer getCancerReportId() {
        return cancerReportId;
    }

    public void setCancerReportId(Integer cancerReportId) {
        this.cancerReportId = cancerReportId;
    }

    public String getCancerReportStatus() {
        return cancerReportStatus;
    }

    public void setCancerReportStatus(String cancerReportStatus) {
        this.cancerReportStatus = cancerReportStatus == null ? null : cancerReportStatus.trim();
    }

    public Date getCancerReportInStatusDate() {
        return cancerReportInStatusDate;
    }

    public void setCancerReportInStatusDate(Date cancerReportInStatusDate) {
        this.cancerReportInStatusDate = cancerReportInStatusDate;
    }

    public Integer getCancerDiagnoseId() {
        return cancerDiagnoseId;
    }

    public void setCancerDiagnoseId(Integer cancerDiagnoseId) {
        this.cancerDiagnoseId = cancerDiagnoseId;
    }

    public String getCancerDiagnoseStatus() {
        return cancerDiagnoseStatus;
    }

    public void setCancerDiagnoseStatus(String cancerDiagnoseStatus) {
        this.cancerDiagnoseStatus = cancerDiagnoseStatus == null ? null : cancerDiagnoseStatus.trim();
    }

    public Date getCancerDiagnoseInStatusDate() {
        return cancerDiagnoseInStatusDate;
    }

    public void setCancerDiagnoseInStatusDate(Date cancerDiagnoseInStatusDate) {
        this.cancerDiagnoseInStatusDate = cancerDiagnoseInStatusDate;
    }

    public Integer getColorectalCancerDiagnoseInformationId() {
        return colorectalCancerDiagnoseInformationId;
    }

    public void setColorectalCancerDiagnoseInformationId(Integer colorectalCancerDiagnoseInformationId) {
        this.colorectalCancerDiagnoseInformationId = colorectalCancerDiagnoseInformationId;
    }

    public String getColorectalCancerDiagnoseInformationStatus() {
        return colorectalCancerDiagnoseInformationStatus;
    }

    public void setColorectalCancerDiagnoseInformationStatus(String colorectalCancerDiagnoseInformationStatus) {
        this.colorectalCancerDiagnoseInformationStatus = colorectalCancerDiagnoseInformationStatus == null ? null : colorectalCancerDiagnoseInformationStatus.trim();
    }

    public Date getColorectalCancerDiagnoseInformationInStatusDate() {
        return colorectalCancerDiagnoseInformationInStatusDate;
    }

    public void setColorectalCancerDiagnoseInformationInStatusDate(Date colorectalCancerDiagnoseInformationInStatusDate) {
        this.colorectalCancerDiagnoseInformationInStatusDate = colorectalCancerDiagnoseInformationInStatusDate;
    }

    public Integer getColorectalCancerTreatmentInformationId() {
        return colorectalCancerTreatmentInformationId;
    }

    public void setColorectalCancerTreatmentInformationId(Integer colorectalCancerTreatmentInformationId) {
        this.colorectalCancerTreatmentInformationId = colorectalCancerTreatmentInformationId;
    }

    public String getColorectalCancerTreatmentInformatioStatus() {
        return colorectalCancerTreatmentInformatioStatus;
    }

    public void setColorectalCancerTreatmentInformatioStatus(String colorectalCancerTreatmentInformatioStatus) {
        this.colorectalCancerTreatmentInformatioStatus = colorectalCancerTreatmentInformatioStatus == null ? null : colorectalCancerTreatmentInformatioStatus.trim();
    }

    public Date getColorectalCancerTreatmentInformatioInStatusDate() {
        return colorectalCancerTreatmentInformatioInStatusDate;
    }

    public void setColorectalCancerTreatmentInformatioInStatusDate(Date colorectalCancerTreatmentInformatioInStatusDate) {
        this.colorectalCancerTreatmentInformatioInStatusDate = colorectalCancerTreatmentInformatioInStatusDate;
    }

    public Integer getDeathCertificateId() {
        return deathCertificateId;
    }

    public void setDeathCertificateId(Integer deathCertificateId) {
        this.deathCertificateId = deathCertificateId;
    }

    public String getDeathCertificateStatus() {
        return deathCertificateStatus;
    }

    public void setDeathCertificateStatus(String deathCertificateStatus) {
        this.deathCertificateStatus = deathCertificateStatus == null ? null : deathCertificateStatus.trim();
    }

    public String getEndEventType() {
        return endEventType;
    }

    public void setEndEventType(String endEventType) {
        this.endEventType = endEventType == null ? null : endEventType.trim();
    }

    public String getValidData() {
        return validData;
    }

    public void setValidData(String validData) {
        this.validData = validData == null ? null : validData.trim();
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

    public Integer getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(Integer deptCode) {
        this.deptCode = deptCode;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }
}