package com.yuntongxun.itsys.base.po;

public class ResourceAttr {
	private Object menu;
	private Object pages;
	private Object buttons;
	private Object requests;
	
	public ResourceAttr(){
		
	}
	
	public ResourceAttr(Object menu,Object pages,Object buttons,Object requests){
		super();
		this.menu=menu;
		this.pages=pages;
		this.buttons=buttons;
		this.requests=requests;
	}
	
	
	

	public Object getMenu() {
		return menu;
	}

	public void setMenu(Object menu) {
		this.menu = menu;
	}

	public Object getPages() {
		return pages;
	}

	public void setPages(Object pages) {
		this.pages = pages;
	}

	public Object getButtons() {
		return buttons;
	}

	public void setButtons(Object buttons) {
		this.buttons = buttons;
	}

	public Object getRequests() {
		return requests;
	}

	public void setRequests(Object requests) {
		this.requests = requests;
	}

	



	
	
	
}
