package com.yuntongxun.itsys.base.service;

import com.google.gson.JsonObject;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.po.Role;
import com.yuntongxun.itsys.base.vo.Result;

import java.util.List;

public interface DepartMentService {

	public ListPageUtil queryDepartMent(JsonObject json) throws ItSysException;

	public List getDepartMentById(String id) throws ItSysException;

	public void insert(String body) throws ItSysException;

	public void delDepartMentById(String id) throws ItSysException;

	public void updateDepartMent(String body) throws ItSysException;

	public void saveDepartMentMemBer(String body) throws ItSysException;

	public void delDepartMentMemberByUser(String userid) throws ItSysException;

	public void delDepartMentMemberByDept(String deptid) throws ItSysException;

	public Object queryDepartOnTree(JsonObject json) throws ItSysException;

	public List getDepartMentMember(String departId) throws ItSysException;

	// 根据部门id查询副经理
	public List getManagerByDepartId(String departId) throws ItSysException;

	// 根据部门id查询总裁
	public List getCeoByDepartId(String departId) throws ItSysException;

	// 根据部门id查询面试官
	public List getInterviewerByDepartId(String departId) throws ItSysException;

	// 根据本部门id查询入职引导人
	public List getInductionByDepartId(String departId) throws ItSysException;

	public Object getOtherDepartByPid(String pid) throws ItSysException;
	/**新增接口**/
	public Object getAllDepartByPid(String pid,Integer level,Integer userID);

	/**
	 * 根据部门成员表id修改部门成员关系表中的数据
	 * 
	 * @throws ItSysException
	 */
	public void updateDepartMember(String body) throws ItSysException;

	// 新增接口，查询公司未绑定部门的员工
	public Result queryNotExistsDepartEmployee(String body);

	/**
	 * 获取当前医院所有下属医院
	 * @param loginName
	 * @return
	 */
	Result findHospitalByPid(String loginName);

	/**
	 * 根据当前登录用户取所在医院
	 * @param loginName
	 * @return
	 */
	public Object findHospitalByLoginName(String loginName);


}
