package com.yuntongxun.itsys.base.vo;

import java.util.Date;

public class ColonoscopyIssueVo {

	private Integer id;
	private String sid;
	private Integer mode;
	private String issueDate;
	private String workerCode;
	private String note;
	private Date issueDateForSql;
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
	public Integer getMode() {
		return mode;
	}
	public void setMode(Integer mode) {
		this.mode = mode;
	}
	public String getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}
	public String getWorkerCode() {
		return workerCode;
	}
	public void setWorkerCode(String workerCode) {
		this.workerCode = workerCode;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Date getIssueDateForSql() {
		return issueDateForSql;
	}
	public void setIssueDateForSql(Date issueDateForSql) {
		this.issueDateForSql = issueDateForSql;
	}

	

}
