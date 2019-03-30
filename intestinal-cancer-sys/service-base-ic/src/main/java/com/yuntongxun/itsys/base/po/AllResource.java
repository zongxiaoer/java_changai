package com.yuntongxun.itsys.base.po;

public class AllResource {
	
	public Object pages;
	public Object requests;
	
	public AllResource(){
		
	}
	
	public AllResource(Object pages,Object requests){
		this.pages=pages;
		this.requests=requests;
	}
	
	public Object getPages() {
		return pages;
	}
	public void setPages(Object pages) {
		this.pages = pages;
	}
	public Object getRequests() {
		return requests;
	}
	public void setRequests(Object requests) {
		this.requests = requests;
	}
	
	
	
}
