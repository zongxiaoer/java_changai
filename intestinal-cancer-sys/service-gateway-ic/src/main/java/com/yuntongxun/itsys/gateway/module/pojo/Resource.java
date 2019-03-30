package com.yuntongxun.itsys.gateway.module.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Resource {
	public int id;
	public String name;
	public String type;
	public String url;
	public String desc;
	public String saveLog;
	public String dateCreated;
	public String updateTime;
	public String[] ascriptionArr;

	public String[] getAscriptionArr() {
		return ascriptionArr;
	}

	public void setAscriptionArr(String[] ascriptionArr) {
		this.ascriptionArr = ascriptionArr;
	}

	public Resource(int id, String name, String type, String url, String desc, String saveLog, String dateCreated,
			String updateTime, String[] ascriptionArr) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.url = url;
		this.desc = desc;
		this.saveLog = saveLog;
		this.dateCreated = dateCreated;
		this.updateTime = updateTime;
		this.ascriptionArr = ascriptionArr;
	}

	public static Resource toObject(Map<String, Object> map) {
		Resource resource = new Resource();
		resource.setId((Integer) map.get("id"));
		resource.setName((String) map.get("name"));
		resource.setType((String) map.get("type"));
		resource.setUrl((String) map.get("url"));
		resource.setDesc((String) map.get("desc"));
		resource.setSaveLog((String) map.get("savelog"));
		return resource;
	}

	public static List<Resource> toObject(List<Map<String, Object>> lists){
		List<Resource> resourceList = new ArrayList<Resource>();
		for (Map<String, Object> map : lists) {
			Resource resource =  Resource.toObject(map);
			if (resource != null) {
				resourceList.add(resource);
			}
		}
		return resourceList;
	}

	public Resource() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getSaveLog() {
		return saveLog;
	}

	public void setSaveLog(String saveLog) {
		this.saveLog = saveLog;
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
