package com.yuntongxun.itsys.base.dao;

import java.util.List;

import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.po.*;
import com.yuntongxun.itsys.base.po.dto.ItsysUserDto;

public interface UserDao {

	public ListPageUtil queryUser(String id, String nickName, String loginName, String userId, int pageNo, int pageSize)
			throws ItSysException;

	public List getUserById(String id) throws ItSysException;

	public void addUser(User user) throws ItSysException;

	public void updateUser(User user) throws ItSysException;

	public void delUser(String id) throws ItSysException;

	public void addUserByPosition(User user) throws ItSysException;

	public List getUserPwd(String loginName) throws ItSysException;

	public List getUserRoleById(String id);

	public void addUserRole(String userid,String[] roleids);

	public void changepwd(String userid,String pwd,String oldPwd);

	/**
	 * 根据输入手机号，判断是否是公司员工，如果不是员工在判断是否是平台用户
	 * @param phone
	 * @return
	 */
	List<User> findByPhone(String phone);

	/**
	 * 找回密码，重设密码
	 * @param resetPwd
	 * @return
	 */
	int updateReset(ResetPwd resetPwd);

	/**
	 * 更具当前用户查询所在医院
	 *
	 * @param userName
	 * @return
	 */
	DepartMent findbyUserId(String userName);

	/**
	 * 根据id查询pid
	 * @param id
	 * @return
	 */
	DepartMent findByPid(String id);

	/**
	 * 根据登陆名获取部门信息
	 *
	 * @param loginName
	 * @return
	 */
	ItsysDepartment getCommunityIdAndAreaIdByLoginName(String loginName);
	
	/**新增接口**/
	ItsysDepartment getAllDepts(String loginName);

	String finAllSidBySiteId(Integer communityId,Integer areaId);

    String findEndSidByAreaIdOrComunityId(Integer areaId, Integer communityId);


    List<ItsysUserDto> getParentName(String loginName);

	List<ItsysUserDto> getAllParentName(String loginName);

	List<ItsysUserDto> queryloginNameRootByloginName(String loginName);

	User selectUserByLoginName(String loginName);

	Integer updateUserByLoginName(User user);

	List<BiMenu> getAllMenuByLoginName(String loginName);

	List<BiBusinessScenario> getAllBusinessScenario(String loginName);

	List<BiAction> getAllAction(String loginName);

    List<ItsysUserDto> getCreateUser(String loginName);

    List<ItsysUserDto> querylowerLoginNamesByloginName(String loginName);

	List<ItsysUserDto> queryMyLoginNamesByloginName(String loginName);
}
