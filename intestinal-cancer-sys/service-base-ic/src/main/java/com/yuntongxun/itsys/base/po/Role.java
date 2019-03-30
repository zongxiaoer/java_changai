package com.yuntongxun.itsys.base.po;

public class Role {
	
	public int id;
	public String name;
	public String desc;
	public String dateCreated;
	public String updatetime;
	
	

	public Role(int id, String name, String desc, String dateCreated, String updatetime) {
		super();
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.dateCreated = dateCreated;
		this.updatetime = updatetime;
	}

	public Role() {
		super();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	
	
}
