package com.yuntongxun.itsys.base.po;

import java.sql.Date;

public class ReserveAllocation {
	private int id;
	private String areaDeptId;
	private int amount;
	private int communityDeptId;
	private Date reservationDate;
	private String startTime;
	private String endTime;
	private String examinationPlace;
	private String signature;
	private Date dateCreated;
	private Date updateTime;
	
	private String deptName;

	private String communityName;//社区名称

	private String atime;

	public String getAtime() {
		return atime;
	}

	public void setAtime(String atime) {
		this.atime = atime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAreaDeptId() {
		return areaDeptId;
	}

	public void setAreaDeptId(String areaDeptId) {
		this.areaDeptId = areaDeptId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getCommunityDeptId() {
		return communityDeptId;
	}

	public void setCommunityDeptId(int communityDeptId) {
		this.communityDeptId = communityDeptId;
	}

	public Date getReservationDate() {
		return reservationDate;
	}

	public void setReservationDate(Date reservationDate) {
		this.reservationDate = reservationDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getExaminationPlace() {
		return examinationPlace;
	}

	public void setExaminationPlace(String examinationPlace) {
		this.examinationPlace = examinationPlace;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
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

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}
}
