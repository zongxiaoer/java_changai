package com.yuntongxun.itsys.base.po;

public class ResourcePages {
	
	public String id;
	public String name;
	public String type;
	public String url;
	public String desc;
	public Object buttons;
	
	public ResourcePages(){
		
	}
	
	public ResourcePages(String id,String name,String type,String url,String desc,String buttons)
	{
		this.id=id;
		this.name=name;
		this.type=type;
		this.url=url;
		this.desc=desc;
		this.buttons=buttons;
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
	public Object getButtons() {
		return buttons;
	}
	public void setButtons(Object buttons) {
		this.buttons = buttons;
	}
	
	
	
}
