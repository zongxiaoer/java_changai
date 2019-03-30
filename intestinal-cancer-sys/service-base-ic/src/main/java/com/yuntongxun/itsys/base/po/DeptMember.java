package com.yuntongxun.itsys.base.po;

public class DeptMember {
	
	public int id;
	public int deptId;
	public int userId;
	public int position;
	public String title;
	public int sort;
	public String dateCreated;

	public DeptMember() {
		super();
	}

	public DeptMember(int deptId, int userId, int position, String title, int sort) {
		super();
		this.deptId = deptId;
		this.userId = userId;
		this.position = position;
		this.title = title;
		this.sort = sort;
	}
	public DeptMember(int id, int deptId, int userId, int position, String title, int sort, String dateCreated) {
		super();
		this.id = id;
		this.deptId = deptId;
		this.userId = userId;
		this.position = position;
		this.title = title;
		this.sort = sort;
		this.dateCreated = dateCreated;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDeptId() {
		return deptId;
	}
	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	
	
}
