package com.yuntongxun.itsys.base.dao;

import java.util.List;

import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.dao.impl.DataEnti;
import com.yuntongxun.itsys.base.po.Role;

public interface RoleDao {

	public ListPageUtil queryRole(String name, int pageNo, int pageSize) throws ItSysException;

	public List getRoleById(String id);

	public void saveRole(Role role);

	public void updateRole(Role role);

	public List getRoleMenuById(String id);

	public void saveRoleMenu(String[] parm, String id);

	public List getRoleResourceById(String roleid);

	public void saveRoleResource(String[] pram, String id);

	public void deleteRole(String roleid) throws ItSysException;
	/**
	 * 根据菜单id删除角色菜单
	 * @param menuid
	 * @throws ItSysException
	 */
	public void delRoleMenuRByMenuId(String menuid) throws ItSysException;
	
	/**
	 * 根据资源id删除角色资源
	 * @param resourceid
	 * @throws ItSysException
	 */
	public void delRoleResourceByResourceId(String resourceid) throws ItSysException;
	
	/**
	 * 根据用户id删除用户角色
	 * @param userid
	 * @throws ItSysException
	 */
	public void delUserRoleRByUserId(String userid)throws ItSysException;
	/**
	 * 根据角色id删除角色菜单
	 * @param roleid
	 * @throws ItSysException
	 */
	public void delRoleMenuRByRoleId(String roleid) throws ItSysException;
	/**
	 * 根据角色id删除角色资源
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

	List<DataEnti> queryRole1(int i, int i1);

	void insert(DataEnti dataEnti);

	void insert1(DataEnti dataEnti);

	void insert2(DataEnti dataEnti);

	void insert3(DataEnti dataEnti);
}
