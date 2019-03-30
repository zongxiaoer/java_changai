package com.yuntongxun.itsys.base.po;

public class Dictionary {

	public int id;
	public String key;
	public String value;
	public int sort;
	public int pId;
	public int ishidden;
	public String label;
	public String dateCreated;
	public String datetime;

	public Dictionary() {
		super();
	}

	public Dictionary(int id, String key, String value, int sort, int pId, int ishidden, String label,
			String dateCreated, String datetime) {
		super();
		this.id = id;
		this.key = key;
		this.value = value;
		this.sort = sort;
		this.pId = pId;
		this.ishidden = ishidden;
		this.label = label;
		this.dateCreated = dateCreated;
		this.datetime = datetime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public int getpId() {
		return pId;
	}

	public void setpId(int pId) {
		this.pId = pId;
	}

	public int getIshidden() {
		return ishidden;
	}

	public void setIshidden(int ishidden) {
		this.ishidden = ishidden;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

}
