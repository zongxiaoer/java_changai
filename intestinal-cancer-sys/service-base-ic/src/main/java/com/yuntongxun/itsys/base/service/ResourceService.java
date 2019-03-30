package com.yuntongxun.itsys.base.service;

import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.po.AuthRequestR;
import com.yuntongxun.itsys.base.po.Resource;
import com.yuntongxun.itsys.base.vo.CityResourceVo;

public interface ResourceService {
	
	public ListPageUtil QueryResource(JsonObject json) throws ItSysException;
	
	public List getResourceById(String id) throws ItSysException;

	public Resource getResourceObjectById(String id) throws ItSysException;
	
	public void addResource(Resource res) throws ItSysException;
	
	public void updateResource(Resource res) throws ItSysException;
	
	public List getAllRequest()throws ItSysException;
	
	public List getAllPages() throws ItSysException;
	
	public List getAllButtonByPage(String page) throws ItSysException;
	
	public Object getAllResource()throws ItSysException;
	
	public void deleteResource(String resourceid) throws ItSysException;
	
	public List getResourceByType(String type)throws ItSysException;
	//绑定请求对应页面和按钮的关系数据  操作表为：itsys_auth_request_r
	public void bindAuthRequestR(String requestId,String [] authIds)throws ItSysException;
	//删除请求对应页面和按钮的关系数据  操作表为：itsys_auth_request_r
	public void delAuthRequestR(String requestId)throws ItSysException;

	//根据请求页面id查找关联表数据authid 用于前端反显用 操作表：itsys_auth_request_r
	public List<AuthRequestR> getAuthRequestByRequestId(String requestId) throws ItSysException;

	List<Map<String,Object>> queryCity(List<Map<String,Object>> maps, Integer pid);


}
