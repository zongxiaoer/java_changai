package com.yuntongxun.itsys.base.vo;

import java.util.Date;

public class ColonoscopyPathologyStatusVo {

	private Integer pathologyId;
	private Integer pathologyStatus;
	private Date pathologyDate;
	private Integer pathologyOperator;
	private Integer notificationEntryStatus;
	private Integer colonoscopyRecordId;


	private Integer examinationStatus;//已检查 未检查
	private Integer examinationOperator;//确认就诊医生

	public Integer getPathologyId() {
		return pathologyId;
	}

	public void setPathologyId(Integer pathologyId) {
		this.pathologyId = pathologyId;
	}

	public Integer getPathologyStatus() {
		return pathologyStatus;
	}

	public void setPathologyStatus(Integer pathologyStatus) {
		this.pathologyStatus = pathologyStatus;
	}

	public Date getPathologyDate() {
		return pathologyDate;
	}

	public void setPathologyDate(Date pathologyDate) {
		this.pathologyDate = pathologyDate;
	}

	public Integer getPathologyOperator() {
		return pathologyOperator;
	}

	public void setPathologyOperator(Integer pathologyOperator) {
		this.pathologyOperator = pathologyOperator;
	}

	public Integer getNotificationEntryStatus() {
		return notificationEntryStatus;
	}

	public void setNotificationEntryStatus(Integer notificationEntryStatus) {
		this.notificationEntryStatus = notificationEntryStatus;
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
}
