package com.yuntongxun.itsys.base.vo;

import com.yuntongxun.itsys.base.common.constans.Constans;

public class Response {
	private String statusCode;
	private String statusMsg;
	private Object data;
	private Object pageInfo;
	public Response(Object data){
		this(Constans.KEY_SUCCESS_STATUS,Constans.KEY_SUCCESS_MSG);
		this.data=data;
	}
	
	public Response(String statuscode,String statusmsg,Object data,Object pageInfo)
	{
		this(statuscode,statusmsg,data);
		this.pageInfo=pageInfo;
	}
	
	public Response(String statuscode,String statusmsg,Object data){
		this(statuscode,statusmsg);
		this.data=data;
	}
	
	public Response(){
		this(Constans.KEY_SUCCESS_STATUS,Constans.KEY_SUCCESS_MSG);
	}
	
	public Response(String statuscode, String statusmsg) {
		this.statusCode = statuscode;
		this.statusMsg = statusmsg;
	}
	
	public Response(Object data,Object pageInfo){
		this(Constans.KEY_SUCCESS_STATUS,Constans.KEY_SUCCESS_MSG);
		this.data=data;
		this.pageInfo=pageInfo;
	}

	

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Object getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(Object pageInfo) {
		this.pageInfo = pageInfo;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMsg() {
		return statusMsg;
	}

	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}


}
