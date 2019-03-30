package com.yuntongxun.itsys.base.po;

import java.util.Date;

/**
 * 待办事件
 * @author liugb
 * Date 2018 4 16
 */
public class HtEvent {
	
	private int id;
	private int type;
	//private int deptId;
	private int communityDeptId;//社区ID
	private int areaDeptId;//地区医院ID
	private String sid;
	private int status;
	private int dataId;
	private Date dateCreated;
	private Date updateTime;


	private String operationSource;//操作来源
	private Integer operationSourceId;//操作id
	
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	public int getCommunityDeptId() {
		return communityDeptId;
	}
	public void setCommunityDeptId(int communityDeptId) {
		this.communityDeptId = communityDeptId;
	}
	public int getAreaDeptId() {
		return areaDeptId;
	}
	public void setAreaDeptId(int areaDeptId) {
		this.areaDeptId = areaDeptId;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getDataId() {
		return dataId;
	}
	public void setDataId(int dataId) {
		this.dataId = dataId;
	}

	public String getOperationSource() {
		return operationSource;
	}

	public void setOperationSource(String operationSource) {
		this.operationSource = operationSource;
	}

	public Integer getOperationSourceId() {
		return operationSourceId;
	}

	public void setOperationSourceId(Integer operationSourceId) {
		this.operationSourceId = operationSourceId;
	}

	@Override
	public String toString() {
		return "HtEvent [id=" + id + ", type=" + type + ", communityDeptId=" + communityDeptId + ", areaDeptId="
				+ areaDeptId + ", sid=" + sid + ", status=" + status + ", dataId=" + dataId + ", dateCreated="
				+ dateCreated + ", updateTime=" + updateTime + "]";
	}

}
