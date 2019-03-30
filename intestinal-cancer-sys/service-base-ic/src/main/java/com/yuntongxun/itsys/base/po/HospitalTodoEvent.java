/**
 * Project Name:service-base-ic
 * File Name:HospitalTodoEvent.java
 * Package Name:com.yuntongxun.itsys.base.po
 * Date:2018年4月16日下午7:15:00
 * Copyright (c) 2018, ty All Rights Reserved.
 *
*/

package com.yuntongxun.itsys.base.po;

import java.util.Date;

/**
 * 待办事件表
 * ClassName:HospitalTodoEvent <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018年4月16日 下午7:15:00 <br/>
 * @author   ty
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public class HospitalTodoEvent {

	private Integer id;
	private Integer type;//事件类型，1：未完成危险因素调查表，2：未录入FIT编号，3：未录入FIT结果，4：未录入粪便DNA装置编号，5：未预约结肠镜检查，6：未完成结肠镜检查，7：未发放筛查结果告知书，8：待录入肠镜结果，9：待录入病理结果，10：待录入筛查结果告知书，11：待接收生物样本
	private Integer deptId;
	private String sid;
	private Integer status;//待办状态，1：未办，2：已办，3：移除待办
	private Integer dataId;
	private Integer communityDeptId;
	private Integer areaDeptId;
	private Date dateCreated;
	private Date updateTime;


	private String operationSource;//操作来源
	private Integer operationSourceId;//操作id


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
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

	public Integer getDeptId() {
		return deptId;
	}
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
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
	public Integer getDataId() {
		return dataId;
	}
	public void setDataId(Integer dataId) {
		this.dataId = dataId;
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
		return "HospitalTodoEvent [id=" + id + ", type=" + type + ", deptId=" + deptId + ", sid=" + sid + ", status="
				+ status + ", dataId=" + dataId + ", dateCreated=" + dateCreated + ", updateTime=" + updateTime + "]";
	}
	
	
}

