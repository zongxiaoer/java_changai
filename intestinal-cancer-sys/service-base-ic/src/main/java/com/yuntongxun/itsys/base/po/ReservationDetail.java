package com.yuntongxun.itsys.base.po;

import java.sql.Date;

public class ReservationDetail {

	private Integer id;
	private Integer allocationId;
	private String sid;
	private Integer status;
	private Integer stage;
	private Integer communityDeptId;
	private Integer areaDeptId;
	private Date dateCreated;
	private Date updateTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAllocationId() {
		return allocationId;
	}
	public void setAllocationId(Integer allocationId) {
		this.allocationId = allocationId;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getStage() {
		return stage;
	}
	public void setStage(Integer stage) {
		this.stage = stage;
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

	

}
