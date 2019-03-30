package com.yuntongxun.itsys.base.po;

import java.util.List;

public class AuthModel {

	private int id;
	private String displayName;
	private int level;
	private String pId;
	private String operation;
	private String url;
	private String desc;
	private int sort;
	private String dateCreated;
	private String updateTime;
	private String name;
	private int type;
	private List<AuthModel> child;
	// private List<AuthModel> menu;
	// private List<AuthModel> pages;
	// private List<AuthModel> buttons;

	public AuthModel() {
		super();
	}

	public AuthModel(int id, String displayName, int level, String pId, String operation, String url, String desc,
			int sort, String dateCreated, String updateTime, String name, int type, List<AuthModel> child) {
		super();
		this.id = id;
		this.displayName = displayName;
		this.level = level;
		this.pId = pId;
		this.operation = operation;
		this.url = url;
		this.desc = desc;
		this.sort = sort;
		this.dateCreated = dateCreated;
		this.updateTime = updateTime;
		this.name = name;
		this.type = type;
		this.child = child;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
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

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<AuthModel> getChild() {
		return child;
	}

	public void setChild(List<AuthModel> child) {
		this.child = child;
	}

}
