package com.yuntongxun.itsys.base.dao;

import java.util.List;

import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.po.AuthRequestR;
import com.yuntongxun.itsys.base.po.Resource;
import com.yuntongxun.itsys.base.vo.CityResourceVo;

public interface ResourceDao {

	public ListPageUtil QueryResource(String name, String url, String type, int pageNo, int pageSize) throws ItSysException;

	public List getResourceById(String id) throws ItSysException;

	public Resource getResourceObjectById(String id) throws ItSysException;

	public void addResource(Object[] parm);

	public void addPageButtonR(String pageid, String buttonid);

	public String getResourceIdByName(String name);

	public void updateResource(Object[] parm);

	public String getPageButtonOnId(String buttonid);

	public void updatePageButtonR(String pageid, String buttonid, String id);

	public List getAllRequest() throws ItSysException;

	public List getAllPages() throws ItSysException;

	public List getAllButtonByPage(String page) throws ItSysException;

	public void deleteResource(String resourceid) throws ItSysException;

	public List getResourceByType(String type) throws ItSysException;

	// 绑定请求对应页面和按钮的关系数据 操作表为：itsys_auth_request_r
	public void bindAuthRequestR(String requestId, String[] authIds) throws ItSysException;

	// 删除请求对应页面和按钮的关系数据 操作表为：itsys_auth_request_r
	public void delAuthRequestR(String requestId) throws ItSysException;

	public List<AuthRequestR> getAuthRequestByRequestId(String requestId) throws ItSysException;

    List<CityResourceVo> queryCity();


}
