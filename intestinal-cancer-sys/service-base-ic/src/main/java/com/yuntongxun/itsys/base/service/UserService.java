package com.yuntongxun.itsys.base.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.po.ItsysDepartment;
import com.yuntongxun.itsys.base.po.ResultMsg;
import com.yuntongxun.itsys.base.po.Role;
import com.yuntongxun.itsys.base.po.User;
import com.yuntongxun.itsys.base.po.dto.sidrule.DepartmentSidRuleDto;
import com.yuntongxun.itsys.base.vo.DoctorInfo;

public interface UserService {

	public ListPageUtil queryUser(String body) throws ItSysException;

	public List getUserById(String id) throws ItSysException;

	public void addUser(String body) throws ItSysException, NoSuchAlgorithmException;

	public void updateUser(String body) throws ItSysException;

	public void delUser(String id) throws ItSysException;

	public void makeUser(String body) throws ItSysException, NoSuchAlgorithmException;

	public List getUserPwd(String LoginName) throws ItSysException;

	public List getUserRoleById(String id) throws ItSysException;

	public void addUserRole(String Employeeid, String[] roleids) throws ItSysException;

	public void changepwd(String Employeeid, String pwd, String oldPwd) throws ItSysException;

	/**
	 * 根据手机号查找是否存在用户
	 * @param phone
	 * @return
	 */
	User findByPhone(String phone);

	/**
	 * 设置医院相关信息到redis中
	 * @param loginName
	 * @return
	 */
	DoctorInfo getHospitalInfo(String loginName);

	/**
	 * 获取部门ID和上级部门ID
	 *
	 * @param loginName
	 * @return
	 */
	ItsysDepartment getCommunityIdAndAreaIdByLoginName(String loginName);
	/**添加接口**/
	ItsysDepartment getAllDepts(String loginName);

	/**
	 * 获取受试者编号
	 *
	 * @param areaId
	 * @return
	 */
	int getPersonNumber(ItsysDepartment itsysDepartment);

	int getPersonNumberByRule(DepartmentSidRuleDto departmentSidRuleDto);

	ResultMsg updateUserByLoginName(User user);

	List<Role> queryRoleByUserId(Integer id);
}
