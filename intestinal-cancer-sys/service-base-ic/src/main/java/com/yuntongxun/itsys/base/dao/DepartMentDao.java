package com.yuntongxun.itsys.base.dao;

import java.util.List;

import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.po.DepartMent;
import com.yuntongxun.itsys.base.po.DeptMember;
import com.yuntongxun.itsys.base.po.Role;

public interface DepartMentDao {

	public ListPageUtil queryDepartMent(String id, String name, boolean nPage,boolean isTranslate, int pageNo, int pageSize) throws ItSysException;

	public List getDepartMentById(String id) throws ItSysException;

	public void insert(DepartMent depart) throws ItSysException;

	public void delDepartMentById(String id) throws ItSysException;

	public void updateDepartMent(DepartMent depart) throws ItSysException;

	public void saveDepartMentMemberOnList(List<DeptMember> dm);
	
	public void delDepartMentMemberByUser(List user) throws ItSysException;

	public void delDepartMentMemberByDept(String deptid) throws ItSysException;

	public List getDepartMentMember(String departId) throws ItSysException;
	
	public String getPidByDepartId(String departId);
	/**
	 * 根据部门成员表id修改部门成员关系表中的数据
	 * @throws ItSysException
	 */
	public void updateDepartMember(String id,String deptId,String employeeId,String position);
	
	/**
	 * 查询未绑定部门的员工
	 * @return
	 */
	public ListPageUtil queryNotExistsDepartEmployee(int pageNo,int pageSize);

	/**
	 * pid查询所有下一级医院
	 * @param pid
	 * @return
	 */
	List<DepartMent> findHospitalByPid(Integer pid);
	/**
	 * 根据id查询所属的子集合
	 * @param pid
	 * @return
	 */
	public List<DepartMent> findAllNodesByPid(String id);
	/**
	 * 根据id查询所对应的创建者
	 * @param id
	 * @return
	 */
	public List<DepartMent> findNickNameById(String id,Integer userid);

	List<Role> queryRoleByUserId(Integer userID);

	List<DepartMent> queryDepartMentByID(String communityDeptId);

	List<DepartMent> queryDepartMentInID(String substring);
}
