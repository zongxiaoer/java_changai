package com.yuntongxun.itsys.gateway.module.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.yuntongxun.itsys.gateway.common.vo.ResultMsg;
import com.yuntongxun.itsys.gateway.module.pojo.DoctorInfo;
import com.yuntongxun.itsys.gateway.module.pojo.Employees;
import com.yuntongxun.itsys.gateway.module.pojo.Role;
import com.yuntongxun.itsys.gateway.module.pojo.User;

public interface UserService {

    /**
     * 根据登录账号查询密码
     * @param loginName
     * @return
     * @throws UnsupportedEncodingException 
     */
    public ResultMsg selectUserByLoginName(User user,HttpServletResponse response) throws UnsupportedEncodingException;
    /**
     * 查询权限
     * @param loginName
     * @return
     */
    public String selectPermissions(String loginName);

    /**
     * 获取短信验证码
     * @param phoneNum
     * @return
     */
    ResultMsg getAuthCode(String phoneNum, String loginName);

    /**
     * 根据手机号查找是否存在用户
     * @param phone
     * @return
     */
    User findByPhone(String phone);


    /**
     * 更具用户名email查找用户表是否存在该用户
     * @param loginName
     * @return
     */
    List<User> findByName(String loginName);

    /**
     * 根据手机号查找员工表是否存在该员工
     * @param phone
     * @return
     */
    List<Employees> findByEmployeePhone(String phone);

    /**
     * 根据手机号查找员工表是否存在该员工
     * @param phone
     * @return
     */
    List<Employees> findByEmployeeName(String loginName);
    
    /**
     * 根据登录名查询该用户是否是负责人
     * @param loginName
     * @return
     */
    public boolean queryRoleByLoginName(String loginName);


    /**
     * 设置医院相关信息到redis中
     * @param loginName
     */
    DoctorInfo getHospitalInfo(String loginName);

    ResultMsg updateUserByLoginName(User user);

    List<Role> queryRoleByUserId(Integer userID);
}
