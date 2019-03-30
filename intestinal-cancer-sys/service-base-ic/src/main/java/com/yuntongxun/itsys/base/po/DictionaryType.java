package com.yuntongxun.itsys.base.po;

/**
 * 数据字典类型 model
 * @author Lake.zhang
 *
 */
public class DictionaryType {
	
	public int id;
	public String enName,cnName,dateCreated,updateTime;
	public DictionaryType() {
		super();
	}
	public DictionaryType(int id, String enName, String cnName, String dateCreated, String updateTime) {
		super();
		this.id = id;
		this.enName = enName;
		this.cnName = cnName;
		this.dateCreated = dateCreated;
		this.updateTime = updateTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	public String getCnName() {
		return cnName;
	}
	public void setCnName(String cnName) {
		this.cnName = cnName;
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
