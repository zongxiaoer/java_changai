package com.yuntongxun.itsys.base.po;

import java.util.List;

public class DictionaryManyModel {
	
	public String key;
	public List info;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public List getInfo() {
		return info;
	}
	public void setInfo(List info) {
		this.info = info;
	}
	public DictionaryManyModel(String key, List info) {
		super();
		this.key = key;
		this.info = info;
	}
	public DictionaryManyModel() {
		super();
	}
	
}
