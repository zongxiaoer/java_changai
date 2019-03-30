package com.yuntongxun.itsys.base.dao;

import java.util.List;

import com.yuntongxun.itsys.base.common.exception.ItSysDaoException;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.po.AuthModel;

public interface AuthDao {
	
	//获取菜单、页面和按钮关系数据
	public Object getAuthTree()throws ItSysDaoException;
	//获取菜单关系树
	public List getMenuTree() throws ItSysException;
	//获取菜单页面关系树
	public List getMenuPageTree()throws ItSysException;
	//保存权限数据
	public void saveAuthParm(AuthModel auth) throws ItSysException;
	//根据ID获取一条权限数据
	public List getAuthParmById(String id) throws ItSysException;
	//修改权限数据
	public void updateAuthParm(AuthModel auth) throws ItSysException;
	//删除权限数据
	public void delAuthParm(String id) throws ItSysException;
	//查询权限数据
	public ListPageUtil queryAuthParm(String id,String name,String type,String url,int pageNo,int pageSize) throws ItSysException;
	
	/**
	 * 根据name查询是否重复
	 * @param name
	 * @return
	 * @throws ItSysException
	 */
	public List queryAuthRepeat(String name) throws ItSysException;
	
	//获取登录权限数据-所有
	public List menuQueryOnLogin(String name);
	//获取登录权限数据-页面
	public List getPageResource(String Name);
	//获取登录权限数据-按钮
	public List getButtonResource(String Name);
	//获取登录权限数据-请求
	public List getMenuRequests(String Name);
}
