package com.yuntongxun.itsys.gateway.module.dao;

import com.yuntongxun.itsys.gateway.module.pojo.DepartMent;
import com.yuntongxun.itsys.gateway.module.pojo.Employees;
import com.yuntongxun.itsys.gateway.module.pojo.Role;
import com.yuntongxun.itsys.gateway.module.pojo.User;

import java.util.List;

public interface UserDao {

    /**
     * 根据登录账号查询密码
     * @param loginName
     * @return
     */
    public User selectUserByLoginName(String loginName);


    /**
     * 根据输入手机号，判断是否是公司员工，如果不是员工在判断是否是平台用户
     * @param phone
     * @return
     */
    User findByPhone(String phone);

    /**
     * 根据用户名查找用户表是否存在用户
     * @param loginName
     * @return
     */
    List<User> findByLoginName(String loginName);


    List<Employees> findByEmployeePhone(String phone);

    /**
     * 更具用户名查找是否存在
     * @param phone
     * @return
     */
    List<Employees> findByEmployeeName(String phone);

    /**
     * 根据登录名查询该用户是否是负责人
     * @param loginName
     * @return
     */
    public boolean queryRoleByLoginName(String loginName);

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

    Integer updateUserByLoginName(User user);


    List<Role> queryRoleByUserId(Integer userID);
}
