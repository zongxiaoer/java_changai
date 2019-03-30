package com.yuntongxun.itsys.base.po;

import java.util.Date;
import java.util.List;

public class AllocationTodo {
	private int id;
	private String areaDeptId;
	private int amount;
	private int communityDeptId;
	private String reservationDate;
	private String startTime;
	private String endTime;
	private String examinationPlace;
	private String signature;
	private Date dateCreated;
	private Date updateTime;
	private String commdeptName;
	private String nickName;
	private String loginName;
	
	private List<AllocationTodo> communityDept;
	
	private String deptName;

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

	public String getReservationDate() {
		return reservationDate;
	}

	public void setReservationDate(String reservationDate) {
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

	public List<AllocationTodo> getCommunityDept() {
		return communityDept;
	}

	public void setCommunityDept(List<AllocationTodo> communityDept) {
		this.communityDept = communityDept;
	}

	public String getCommdeptName() {
		return commdeptName;
	}

	public void setCommdeptName(String commdeptName) {
		this.commdeptName = commdeptName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}



}
