package com.yuntongxun.itsys.base.service;

import java.util.List;

import com.google.gson.JsonObject;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;

public interface RoleService {
	
	public ListPageUtil queryRole(String body) throws ItSysException;
	
	public List getRoleById(String id ) throws ItSysException;
	
	public void saveRole(String json) throws ItSysException;
	
	public void updateRole(String json) throws ItSysException;
	
	public Object getRoleMenuById(String id) throws ItSysException;
	
	public void saveRoleMenu(JsonObject json) throws ItSysException;
	
	public Object getRoleResourceById(String roleid) throws ItSysException;
	
	public void saveRoleResource(JsonObject json)throws ItSysException;
	
	public void deleteRole(String roleid) throws ItSysException;
	
	/**
	 * 根据菜单id删除角色菜单关系
	 * @param menuid
	 * @throws ItSysException
	 */
	public void delRoleMenuRByMenuId(String menuid) throws ItSysException;
	
	/**
	 * 根据资源id删除角色资源关系
	 * @param resourceid
	 * @throws ItSysException
	 */
	public void delRoleResourceByResourceId(String resourceid) throws ItSysException;
	
	/**
	 * 根据用户id删除用户角色关系
	 * @param userid
	 * @throws ItSysException
	 */
	public void delUserRoleRByUserId(String userid)throws ItSysException;
	/**
	 * 根据角色id删除角色菜单关系
	 * @param roleid
	 * @throws ItSysException
	 */
	public void delRoleMenuRByRoleId(String roleid) throws ItSysException;
	/**
	 * 根据角色id删除角色资源关系
	 * @param roleid
	 * @throws ItSysException
	 */
	public void delRoleResourceRByRoleId(String roleid) throws ItSysException;
	/**
	 *	根据角色id删除用户角色关系
	 * @param roleid
	 * @throws ItSysException
	 */
	public void delRoleUserRByRoleId(String roleid) throws ItSysException;
	
}
