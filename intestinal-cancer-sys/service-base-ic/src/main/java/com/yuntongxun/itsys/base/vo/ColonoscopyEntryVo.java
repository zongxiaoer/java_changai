package com.yuntongxun.itsys.base.vo;

import java.util.Date;

public class ColonoscopyEntryVo {

	private Integer notificationEntryStatus;
	private Date	notificationEntryDate;
	private Integer notificationEntryOperator;
	private Integer notificationId;
	private Integer notificationIssueStatus;
	private Integer colonoscopyRecordId;
	private Integer examinationStatus;//已检查 未检查
	private Integer examinationOperator;//确认就诊医生


	public Integer getNotificationEntryStatus() {
		return notificationEntryStatus;
	}

	public void setNotificationEntryStatus(Integer notificationEntryStatus) {
		this.notificationEntryStatus = notificationEntryStatus;
	}

	public Date getNotificationEntryDate() {
		return notificationEntryDate;
	}

	public void setNotificationEntryDate(Date notificationEntryDate) {
		this.notificationEntryDate = notificationEntryDate;
	}

	public Integer getNotificationEntryOperator() {
		return notificationEntryOperator;
	}

	public void setNotificationEntryOperator(Integer notificationEntryOperator) {
		this.notificationEntryOperator = notificationEntryOperator;
	}

	public Integer getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(Integer notificationId) {
		this.notificationId = notificationId;
	}

	public Integer getNotificationIssueStatus() {
		return notificationIssueStatus;
	}

	public void setNotificationIssueStatus(Integer notificationIssueStatus) {
		this.notificationIssueStatus = notificationIssueStatus;
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
