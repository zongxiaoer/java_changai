package com.yuntongxun.itsys.base.service;

import java.util.List;

import com.google.gson.JsonObject;
import com.yuntongxun.itsys.base.common.exception.ItSysDaoException;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.exception.ItSysServiceException;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.po.ResetPwd;
import com.yuntongxun.itsys.base.po.User;
import com.yuntongxun.itsys.base.vo.Result;

public interface AuthService {
	//获取菜单、页面和按钮关系数据
	public Object getAuthTree()throws ItSysServiceException, ItSysDaoException;
	//获取菜单关系树
	public Object getMenuTree()throws ItSysException;
	//获取菜单页面关系树
	public Object getMenuPageTree() throws ItSysException;
	//保存权限数据
	public void saveAuthParm(String body) throws ItSysException;
	//根据ID获取一条权限数据
	public List getAuthParmById(String id) throws ItSysException;
	//修改权限数据
	public void updateAuthParm(String body) throws ItSysException;
	//删除权限数据
	public void delAuthParm(String id) throws ItSysException;
	//查询权限数据
	public List queryAuthParm(String body) throws ItSysException;

	public ListPageUtil queryAuthPage(JsonObject json) throws ItSysException;
	//登录获取权限所有数据
	public Object getUserResource(String Name) throws ItSysException;
	//获取登录权限-页面数据
	public Object getPageResource(String Name) throws ItSysException;
	//获取登录权限-按钮数据
	public Object getButtonResource(String Name) throws ItSysException;
	//获取登录权限-请求数据
	public Object getMenuRequests(String Name) throws ItSysException;

	/**
	 * 重置密码
	 * @param user
	 * @return
	 * @throws ItSysException
	 */
	Result resetUserPwd(ResetPwd resetPwd) throws ItSysException;

	/**
	 * 发送验证码
	 * @param user
	 * @return
	 * @throws ItSysException
	 */
	Result sendAuthCode(User user) throws  ItSysException;

}
