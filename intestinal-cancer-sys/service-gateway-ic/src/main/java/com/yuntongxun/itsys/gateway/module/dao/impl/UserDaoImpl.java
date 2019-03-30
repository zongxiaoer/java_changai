package com.yuntongxun.itsys.gateway.module.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.yuntongxun.itsys.gateway.module.pojo.DepartMent;
import com.yuntongxun.itsys.gateway.module.pojo.Role;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.yuntongxun.itsys.gateway.common.base.AbstractDaoImpl;
import com.yuntongxun.itsys.gateway.module.dao.UserDao;
import com.yuntongxun.itsys.gateway.module.pojo.Employees;
import com.yuntongxun.itsys.gateway.module.pojo.User;

@Repository
public class UserDaoImpl extends AbstractDaoImpl implements UserDao{

    @Autowired
    public JdbcTemplate jdbcTemplate;

    @Override
    public User selectUserByLoginName(String loginName) {
        StringBuilder sb = new StringBuilder("select first_login as firstLogin,loginName,pwd,facsimile,xty_phone,xty_name from itsys_user where loginName = ?");
        String sql = sb.toString();
        try {
            return jdbcTemplate.queryForObject(sql,new  RowMapper<User>() {

                @Override
                public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                    User user = new User();
                    user.setLoginName(rs.getString("loginName"));
                    user.setPwd(rs.getString("pwd"));
                    user.setNickName(rs.getString("facsimile"));
                    user.setFirstLogin(rs.getString("firstLogin"));
                    if(rs.getString("xty_name")!=null){
                        user.setXtyName(rs.getString("xty_name"));
                    }else{
                        user.setXtyName("");
                    }

                    if(rs.getString("xty_phone")!=null){
                        user.setXtyPhone(rs.getString("xty_phone"));
                    }else{
                        user.setXtyPhone("");
                    }
                    return user;
                }},loginName);
        }catch(EmptyResultDataAccessException e){//没有此用户
            logger.debug("未查询出此用户,[loginName:{}]", loginName);
            return null;
        } catch (Exception e) {
            logger.error("查询错误",e);
            return null;
        }
    }

    @Override
    public User findByPhone(String phone) {
        String sql = "SELECT t.* FROM itsys_user t WHERE t.phone = ?";
        List<User> userList = jdbcTemplate.query(sql, new Object[]{phone}, new BeanPropertyRowMapper<User>(User.class));
        User user = null;
        if (userList != null && userList.size() > 0) {
            user = userList.get(0);
        }
        return user;
    }


    @Override
    public List<User> findByLoginName(String loginName) {

        String sql = "SELECT t.* FROM itsys_user t WHERE t.loginName = ?";
        List<User> userList = jdbcTemplate.query(sql, new Object[]{loginName}, new BeanPropertyRowMapper<User>(User.class));

        return userList;
    }

    @Override
    public List<Employees> findByEmployeePhone(String phone) {
        String sql = "SELECT id,name,email,phone FROM itsys_employees WHERE phone = ?";
        List<Employees> employeesList = jdbcTemplate.query(sql, new Object[]{phone}, new BeanPropertyRowMapper<Employees>(Employees.class));

        return employeesList;
    }

    @Override
    public List<Employees> findByEmployeeName(String loginName) {
        String sql = "SELECT id,name,email,phone FROM itsys_employees WHERE email = ?";
        List<Employees> employeesList = jdbcTemplate.query(sql, new Object[]{loginName}, new BeanPropertyRowMapper<Employees>(Employees.class));

        return employeesList;
    }

    @Override
    protected Class<?> getClassName() {
        return UserDaoImpl.class;
    }

	@Override
	public boolean queryRoleByLoginName(String loginName) {
//		StringBuffer sb=new StringBuffer("SELECT u.loginName,dm.position FROM `itsys_department_member` dm,itsys_user u where u.employeeId=dm.userId  and u.loginName=? and dm.position=1;");
		StringBuffer sb=new StringBuffer("SELECT u.loginName,dm.position FROM `itsys_department_member` dm,itsys_user u where u.employeeId=dm.userId  and  u.loginName=? and dm.position='1' UNION ALL SELECT u.loginName,dm.position FROM `itsys_department_member` dm,itsys_user u where u.employeeId=dm.userId  and  u.phone=? and dm.position='1'");
		List<String> param=new ArrayList();
		param.add(loginName);
		param.add(loginName);
		logger.info("@DaoImpl 根据登录名查询该用户是否是部门负责人.......sql={},param={}",sb.toString(),param);
		List queryResult=this.jdbcTemplate.queryForList(sb.toString(),param.toArray());
		logger.info("@DaoImpl 根据登录名查询该用户是否是部门负责人.......数据库返回结果={}",queryResult);
		boolean role=false;
		if(queryResult!=null&&queryResult.size()>0){
			role=true;
		}
		return role;
	}

    @Override
    public DepartMent findbyUserId(String userName) {
        String sql = "SELECT dept.*,user.id as userId from itsys_department_member dep LEFT JOIN itsys_user user on user.id = dep.userId LEFT JOIN itsys_department dept on dept.id = dep.deptId WHERE user.loginName = ?";
        List<DepartMent> departMentList = jdbcTemplate.query(sql, new Object[]{userName}, new BeanPropertyRowMapper<DepartMent>(DepartMent.class));
        DepartMent departMent = null;
        if (departMentList != null && departMentList.size() > 0) {
            departMent = departMentList.get(0);
        }
        return departMent;
    }

    @Override
    public DepartMent findByPid(String id) {
        String sql = "SELECT dep.* from itsys_department dep WHERE dep.id = ?";
        List<DepartMent> departMentList = jdbcTemplate.query(sql, new Object[]{id}, new BeanPropertyRowMapper<DepartMent>(DepartMent.class));
        DepartMent departMent = null;
        if (departMentList != null && departMentList.size() > 0) {
            departMent = departMentList.get(0);
        }
        return departMent;
    }

    @Override
    public Integer updateUserByLoginName(User user) {
        String sql = "UPDATE itsys_user SET pwd=?,first_login=? WHERE loginName = ?";
        int result = jdbcTemplate.update(sql, user.getNewPwd(), user.getFirstLogin(),user.getLoginName());
        return result;
    }

    @Override
    public List<Role> queryRoleByUserId(Integer userID) {
        String sql = "select * from itsys_role r1,itsys_user_role_r r2 where r1.id=r2.roleId and r2.userId=? and r1.id=132 ";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Role>(Role.class),userID);
    }
}
