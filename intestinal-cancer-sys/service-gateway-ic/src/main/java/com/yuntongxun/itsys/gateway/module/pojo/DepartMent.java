package com.yuntongxun.itsys.gateway.module.pojo;

import java.util.List;

public class DepartMent {

	private String id, name, level, pId, desc, sort, screeningType, dateCreated, updateTime,type;
	private List<DepartMent> child;

	private String userId;
	
	public DepartMent(){
		super();
	}
//	public DepartMent(String id,String name, String pId,String desc,String sort,String type){
//		this(name,pId,desc,sort,type);
//		this.id=id;
//	}


	public DepartMent(String id, String name, String level, String pId, String desc, String sort, String screeningType, String dateCreated, String updateTime, String type, List<DepartMent> child, String userId) {
		this.id = id;
		this.name = name;
		this.level = level;
		this.pId = pId;
		this.desc = desc;
		this.sort = sort;
		this.screeningType = screeningType;
		this.dateCreated = dateCreated;
		this.updateTime = updateTime;
		this.type = type;
		this.child = child;
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<DepartMent> getChild() {
		return child;
	}


	public String getScreeningType() {
		return screeningType;
	}

	public void setScreeningType(String screeningType) {
		this.screeningType = screeningType;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setChild(List<DepartMent> child) {
		this.child = child;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}



	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	
}
