package com.yuntongxun.itsys.base.vo;

import java.util.Date;

public class ColonoscopyResultVo {

    private Integer finishedStatus;
    private Integer resultId;
    private Integer resultStatus;
    private Date resultDate;
    private Integer resultOperator;
    private Integer pathologyStatus;
    private Integer notificationEntryStatus;
    private Integer colonoscopyRecordId;//结肠镜检查记录

    private Integer allState;
    private Integer examinationStatus;//已检查 未检查
    private Integer examinationOperator;//确认就诊医生
    private Date examination_check_date;
    private Date examination_date;

    private Integer operationSourceId;//操作id

    private String editoperationSource;//渲染操作来源


    public Integer getFinishedStatus() {
        return finishedStatus;
    }

    public void setFinishedStatus(Integer finishedStatus) {
        this.finishedStatus = finishedStatus;
    }

    public Integer getResultId() {
        return resultId;
    }

    public void setResultId(Integer resultId) {
        this.resultId = resultId;
    }

    public Integer getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(Integer resultStatus) {
        this.resultStatus = resultStatus;
    }

    public Date getResultDate() {
        return resultDate;
    }

    public void setResultDate(Date resultDate) {
        this.resultDate = resultDate;
    }

    public Integer getResultOperator() {
        return resultOperator;
    }

    public void setResultOperator(Integer resultOperator) {
        this.resultOperator = resultOperator;
    }

    public Integer getPathologyStatus() {
        return pathologyStatus;
    }

    public void setPathologyStatus(Integer pathologyStatus) {
        this.pathologyStatus = pathologyStatus;
    }

    public Integer getNotificationEntryStatus() {
        return notificationEntryStatus;
    }

    public void setNotificationEntryStatus(Integer notificationEntryStatus) {
        this.notificationEntryStatus = notificationEntryStatus;
    }

    public Integer getAllState() {
        return allState;
    }

    public void setAllState(Integer allState) {
        this.allState = allState;
    }

    public Integer getColonoscopyRecordId() {
        return colonoscopyRecordId;
    }

    public void setColonoscopyRecordId(Integer colonoscopyRecordId) {
        this.colonoscopyRecordId = colonoscopyRecordId;
    }

    public Integer getExaminationStatus() {
        return examinationStatus;
    }

    public void setExaminationStatus(Integer examinationStatus) {
        this.examinationStatus = examinationStatus;
    }

    public Integer getExaminationOperator() {
        return examinationOperator;
    }

    public void setExaminationOperator(Integer examinationOperator) {
        this.examinationOperator = examinationOperator;
    }

    public Date getExamination_check_date() {
        return examination_check_date;
    }

    public void setExamination_check_date(Date examination_check_date) {
        this.examination_check_date = examination_check_date;
    }

    public Date getExamination_date() {
        return examination_date;
    }

    public void setExamination_date(Date examination_date) {
        this.examination_date = examination_date;
    }

    public Integer getOperationSourceId() {
        return operationSourceId;
    }

    public void setOperationSourceId(Integer operationSourceId) {
        this.operationSourceId = operationSourceId;
    }

    public String getEditoperationSource() {
        return editoperationSource;
    }

    public void setEditoperationSource(String editoperationSource) {
        this.editoperationSource = editoperationSource;
    }
}
