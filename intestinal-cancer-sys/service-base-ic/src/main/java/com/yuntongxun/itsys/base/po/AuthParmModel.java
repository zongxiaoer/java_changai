package com.yuntongxun.itsys.base.po;

import java.util.List;

public class AuthParmModel {
	
	public List<AuthModel> menus;
	public List<AuthModel> pages;
	public List<AuthModel> buttons;
	
	
	
	
	public AuthParmModel() {
		super();
	}
	public AuthParmModel(List<AuthModel> menus, List<AuthModel> pages, List<AuthModel> buttons) {
		super();
		this.menus = menus;
		this.pages = pages;
		this.buttons = buttons;
	}
	public List<AuthModel> getMenus() {
		return menus;
	}
	public void setMenus(List<AuthModel> menus) {
		this.menus = menus;
	}
	public List<AuthModel> getPages() {
		return pages;
	}
	public void setPages(List<AuthModel> pages) {
		this.pages = pages;
	}
	public List<AuthModel> getButtons() {
		return buttons;
	}
	public void setButtons(List<AuthModel> buttons) {
		this.buttons = buttons;
	}
	
	
}	
