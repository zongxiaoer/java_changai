package com.yuntongxun.itsys.base.po;

/**
 * 操作类型
 * @author zhangzl
 *
 */
public class Operation {
	
	public int id;
	public String user;
	public String type;
	public String content;
	public int result;
	public String dateCreated;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public String getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Operation(int id, String user, String type, String content, int result, String dateCreated) {
		super();
		this.id = id;
		this.user = user;
		this.type = type;
		this.content = content;
		this.result = result;
		this.dateCreated = dateCreated;
	}
	public Operation() {
		super();
	}
	
}
