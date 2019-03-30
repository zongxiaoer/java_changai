/**
 * Project Name:service-base-ic
 * File Name:ReviewResult.java
 * Package Name:com.yuntongxun.itsys.base.po
 * Date:2018年4月16日下午7:45:53
 * Copyright (c) 2018, ty All Rights Reserved.
 *
*/

package com.yuntongxun.itsys.base.po;

import java.util.Date;

/**
 * 受试者提交资格审核表返回结果对象
 * ClassName:ReviewResult <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018年4月16日 下午7:45:53 <br/>
 * @author   ty
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
public class ReviewResult {
	private int id;
	private String sid;
	private Date inGroupDate;
	private String group;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public Date getInGroupDate() {
		return inGroupDate;
	}
	public void setInGroupDate(Date inGroupDate) {
		this.inGroupDate = inGroupDate;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	@Override
	public String toString() {
		return "ReviewResult [id=" + id + ", sid=" + sid + ", inGroupDate=" + inGroupDate + ", group=" + group + "]";
	}
	
	
	
}

