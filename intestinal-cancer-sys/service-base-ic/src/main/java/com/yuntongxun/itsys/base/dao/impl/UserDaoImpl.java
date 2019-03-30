package com.yuntongxun.itsys.base.dao.impl;

import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.EncryptUtil;
import com.yuntongxun.itsys.base.common.util.JSONUtils;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.common.util.StringUtil;
import com.yuntongxun.itsys.base.dao.UserDao;
import com.yuntongxun.itsys.base.po.*;
import com.yuntongxun.itsys.base.po.dto.ItsysUserDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private JdbcTemplate jdbc;
    private final Logger log = LogManager.getLogger(UserDaoImpl.class);

    @Override
    public ListPageUtil queryUser(String id, String nickName, String loginName, String userId, int pageNo, int pageSize)
            throws ItSysException {
        String sql = "select `id`,nickName,loginName,employeeId,phone,tel,encode,address,facsimile,email,qq from itsys_user where 1=1 ";
        List parm = new ArrayList();
        if (!StringUtil.isEmpty(id)) {
            sql = sql + " and id=?";
            parm.add(id);
        }
        if (!StringUtil.isEmpty(userId)) {
            sql = sql + " and employeeId=?";
            parm.add(userId);
        }
        if (!StringUtil.isEmpty(nickName)) {
            sql = sql + " and nickName like ?";
            parm.add("%" + nickName + "%");
        }
        if (!StringUtil.isEmpty(loginName)) {
            sql = sql + " and loginName like ?";
            parm.add("%" + loginName + "%");
        }

        Map<String, String> translateParm = new HashMap();
        ListPageUtil listPage = new ListPageUtil(sql, parm.toArray(), pageNo, pageSize, jdbc, translateParm);
        return listPage;

    }

    @Override
    public List getUserById(String id) throws ItSysException {
        String sql = "select `id`,nickName,loginName,employeeId,phone,tel,encode,address,facsimile,email,qq from itsys_user where 1=1 and `id`=?";
        List list = jdbc.queryForList(sql, new Object[]{id});
        return list;
    }

    @Override
    public void addUser(User user) throws ItSysException {
        String sql = "insert into itsys_user(nickName,loginName,pwd,phone,tel,encode,address,facsimile,email,qq,dateCreated,updateTime) values(?,?,?,?,?,?,?,?,?,?,now(),now())";
        jdbc.update(sql, new Object[]{user.getNickName(), user.getLoginName(), user.getPwd(), user.getPhone(), user.getTel(), user.getEncode(), user.getAddress(), user.getFacsimile(), user.getEmail(), user.getQq()});
    }

    @Override
    public void updateUser(User user) throws ItSysException {
        String sql = "update itsys_user set nickName=?,loginName=?,phone=?,tel=?,encode=?,address=?,facsimile=?,email=?,qq=?,updateTime=now() where `id`=?";
        jdbc.update(sql, new Object[]{user.getNickName(), user.getLoginName(), user.getPhone(), user.getTel(), user.getEncode(), user.getAddress(), user.getFacsimile(), user.getEmail(), user.getQq(), user.getId()});
    }

    @Override
    public void delUser(String id) throws ItSysException {
        String sql = "delete from itsys_user where `id`=?";
        jdbc.update(sql, new Object[]{id});
    }

    @Override
    public void addUserByPosition(User user) throws ItSysException {
        String sql = "insert into itsys_user(nickName,loginName,pwd,employeeId,dateCreated,updateTime) values(?,?,?,?,now(),now())";
        jdbc.update(sql, new Object[]{user.getNickName(), user.getLoginName(), user.getPwd(), user.getEmployeeId()});
    }

    @Override
    public List getUserPwd(String loginName) throws ItSysException {
        String sql = "select `id`,nickName,loginName,employeeId,pwd from itsys_user where 1=1 and `loginName`=?";
        List list = jdbc.queryForList(sql, new Object[]{loginName});
        return list;
    }

    @Override
    public List getUserRoleById(String id) {
        String sql = "select r.roleid from itsys_user_role_r r where r.userid='" + id + "'";
        List<Integer> list = jdbc.queryForList(sql, Integer.class);
        return list;
    }

    @Override
    public void addUserRole(String userid, String[] roleids) {
        int length = roleids.length;
        String[] sqls = new String[length];
        for (int i = 0; i < length; i++) {
            String role = roleids[i];
            if (StringUtil.isEmpty(role)) {
                continue;
            } else {
                sqls[i] = "insert into itsys_user_role_r(roleid,userid) values('" + role + "','" + userid + "')";
            }
        }
        jdbc.batchUpdate(sqls);
    }

    @Override
    public void changepwd(String loginName, String pwd, String oldPwd) {
        String sql = "update itsys_user u set u.pwd=?,u.updatetime=now() where u.loginName=?";
        jdbc.update(sql, new Object[]{pwd, loginName});
    }

    @Override
    public List<User> findByPhone(String phone) {
        String sql = "SELECT t.* FROM itsys_user t WHERE t.phone = ?";
        List<User> userList = jdbc.query(sql, new Object[]{phone}, new BeanPropertyRowMapper<User>(User.class));
        User user = null;
        if (userList != null && userList.size() > 0) {
            user = userList.get(0);
        }
        return userList;
    }

    @Override
    public int updateReset(ResetPwd resetPwd) {
        log.info("@Dao updateReset Start 重新设置密码：{} ", JSONUtils.toJson(resetPwd));
        String sql = "UPDATE itsys_user SET pwd=?,updateTime=now() WHERE phone=?";
        int result = jdbc.update(sql, EncryptUtil.md5(resetPwd.getPwd()), resetPwd.getPhone());
        log.info("@Dao updateReset End  ");
        return result;
    }

    @Override
    public DepartMent findbyUserId(String userName) {
        String sql = "SELECT dept.*,user.id as userId from itsys_department_member dep LEFT JOIN itsys_user user on user.id = dep.userId LEFT JOIN itsys_department dept on dept.id = dep.deptId WHERE user.loginName = ?";
        List<DepartMent> departMentList = jdbc.query(sql, new Object[]{userName}, new BeanPropertyRowMapper<DepartMent>(DepartMent.class));
        DepartMent departMent = null;
        if (departMentList != null && departMentList.size() > 0) {
            departMent = departMentList.get(0);
        }
        return departMent;
    }

    @Override
    public DepartMent findByPid(String id) {
        String sql = "SELECT dep.* from itsys_department dep WHERE dep.id = ?";
        List<DepartMent> departMentList = jdbc.query(sql, new Object[]{id}, new BeanPropertyRowMapper<DepartMent>(DepartMent.class));
        DepartMent departMent = null;
        if (departMentList != null && departMentList.size() > 0) {
            departMent = departMentList.get(0);
        }
        return departMent;
    }

    @Override
    public ItsysDepartment getCommunityIdAndAreaIdByLoginName(String loginName) {
        String sql = "SELECT d.* FROM itsys_user u, itsys_department d , itsys_department_member dm WHERE u.id = dm.userId AND dm.deptId = d.id AND u.loginName = ?";
        List<ItsysDepartment> itsysDepartmentList = jdbc.query(sql, new Object[]{loginName}, new BeanPropertyRowMapper<>(ItsysDepartment.class));
        if (itsysDepartmentList != null && itsysDepartmentList.size() == 1) {
            return itsysDepartmentList.get(0);
        }
        return null;
    }

    @Override
    public String finAllSidBySiteId(Integer communityId, Integer areaId) {
        String sql = "SELECT sid FROM hospital_intestine_review WHERE area_dept_id = ? AND community_dept_id = ? ORDER BY sid DESC LIMIT 0,1";
        List<String> sid = jdbc.queryForList(sql, new Object[]{areaId, communityId}, String.class);
        if (sid != null && sid.size() == 1) {
            return sid.get(0);
        }
        return null;
    }

    @Override
    public String findEndSidByAreaIdOrComunityId(Integer areaId, Integer communityId) {
        StringBuffer sql=new StringBuffer();
        sql.append("SELECT sid FROM hospital_intestine_review WHERE 1=1 ");
        Object[] objects = {areaId ,communityId };
        if(areaId!=null){
            sql.append(" and area_dept_id = ? ");
            objects= new Object[]{areaId};
        }
        if(communityId!=null){
            sql.append(" and community_dept_id = ? ");
            if(areaId!=null){

                objects = new Object[]{areaId,communityId};
            }else {
                objects = new Object[]{communityId};
            }
        }

        sql.append(" ORDER BY sid DESC LIMIT 0,1");

        List<String> sid = jdbc.queryForList(sql.toString(),objects , String.class);
        if (sid != null && sid.size() == 1) {
            return sid.get(0);
        }
        return null;
    }

    @Override
    public List<ItsysUserDto> getParentName(String loginName) {
        String sql ="select * from itsys_user where id in (select userId from itsys_department_member where deptId=(SELECT dept.pid from itsys_department_member dep LEFT JOIN itsys_user user on user.id = dep.userId LEFT JOIN itsys_department dept on dept.id = dep.deptId   WHERE user.loginName = ?))";
        return jdbc.query(sql, new BeanPropertyRowMapper<ItsysUserDto>(ItsysUserDto.class), loginName);
    }

    @Override
    public List<ItsysUserDto> getAllParentName(String loginName) {
        String sql="select * from itsys_user where id in (select userId from itsys_department_member where deptId=?)";
        return jdbc.query(sql, new BeanPropertyRowMapper<ItsysUserDto>(ItsysUserDto.class), loginName);
    }

    @Override
    public List<ItsysUserDto> queryloginNameRootByloginName(String loginName) {
        String sql="select * from itsys_user where id in (select userId from itsys_department_member where deptId=(SELECT depts.pid from itsys_department_member dep LEFT JOIN itsys_user user on user.id = dep.userId LEFT JOIN itsys_department dept on dept.id = dep.deptId left join  itsys_department depts on dept.pid=depts.id WHERE user.loginName = ?))";
        return jdbc.query(sql, new BeanPropertyRowMapper<ItsysUserDto>(ItsysUserDto.class), loginName);
    }

    @Override
    public User selectUserByLoginName(String loginName) {
        StringBuilder sb = new StringBuilder("select first_login as firstLogin,loginName,pwd,facsimile,xty_phone,xty_name from itsys_user where loginName = ?");
        String sql = sb.toString();
        try {
            return jdbc.queryForObject(sql,new  RowMapper<User>() {

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
            log.debug("未查询出此用户,[loginName:{}]", loginName);
            return null;
        } catch (Exception e) {
            log.error("查询错误",e);
            return null;
        }
    }

    @Override
    public Integer updateUserByLoginName(User user) {
        String sql = "UPDATE itsys_user SET pwd=?,first_login=? WHERE loginName = ?";
        int result = jdbc.update(sql, user.getNewPwd(), user.getFirstLogin(),user.getLoginName());
        return result;
    }

    @Override
	public ItsysDepartment getAllDepts(String loginName) {
		// TODO Auto-generated method stub
		 String sql = "SELECT d.* FROM itsys_user u, itsys_department d , itsys_department_member dm WHERE u.id = dm.userId AND dm.deptId = d.id AND u.loginName = ?";
	        List<ItsysDepartment> itsysDepartmentList = jdbc.query(sql, new Object[]{loginName}, new BeanPropertyRowMapper<>(ItsysDepartment.class));
	        if (itsysDepartmentList != null && itsysDepartmentList.size() == 1) {
	            return itsysDepartmentList.get(0);
	        }
	        return null;
	}

    @Override
    public List<BiMenu> getAllMenuByLoginName(String loginName) {
        String sql="SELECT m.* FROM itsys_user u LEFT JOIN bi_user_menu um ON u.id = um.userid LEFT JOIN bi_menu m ON um.menuid = m.id WHERE u.loginName = ?";
        return jdbc.query(sql, new BeanPropertyRowMapper<BiMenu>(BiMenu.class), loginName);
    }

    @Override
    public List<BiBusinessScenario> getAllBusinessScenario(String loginName) {
        String sql="SELECT bs.* FROM itsys_user u LEFT JOIN bi_user_business ub ON u.id = ub.userid LEFT JOIN bi_business_scenario bs ON ub.businessid = bs.id WHERE u.loginName = ?";
        return jdbc.query(sql, new BeanPropertyRowMapper<BiBusinessScenario>(BiBusinessScenario.class), loginName);
    }

    @Override
    public List<BiAction> getAllAction(String loginName) {
        String sql="SELECT a.* FROM itsys_user u LEFT JOIN bi_user_action ua ON u.id = ua.userid LEFT JOIN bi_action a ON ua.actionid = a.id WHERE u.loginName = ?";
        return jdbc.query(sql, new BeanPropertyRowMapper<BiAction>(BiAction.class), loginName);
    }

    /**
     * 根据受试者创建者获取创建者信息
     * @param sid
     * @return
     */
    @Override
    public List<ItsysUserDto> getCreateUser(String sid) {
        String sql="select * from itsys_user where loginName in (select create_user from hospital_intestine_review where sid=?)";
        return jdbc.query(sql, new BeanPropertyRowMapper<ItsysUserDto>(ItsysUserDto.class), sid);
    }

    @Override
    public List<ItsysUserDto> querylowerLoginNamesByloginName(String loginName) {
        String sql="select * from itsys_user where id in (select userId from itsys_department_member where deptId in (SELECT dept.id " +
                "FROM itsys_department_member dep " +
                " LEFT JOIN itsys_user user ON user.id = dep.userId " +
                " LEFT JOIN itsys_department dept ON dept.pid = dep.deptId " +
                "WHERE user.loginName = ?))";
        return jdbc.query(sql, new BeanPropertyRowMapper<ItsysUserDto>(ItsysUserDto.class), loginName);
    }

    @Override
    public List<ItsysUserDto> queryMyLoginNamesByloginName(String loginName) {
        String sql ="select * from itsys_user where id in (select userId from itsys_department_member where deptId=(SELECT dept.id from itsys_department_member dep LEFT JOIN itsys_user user on user.id = dep.userId LEFT JOIN itsys_department dept on dept.id = dep.deptId   WHERE user.loginName = ?))";
        return jdbc.query(sql, new BeanPropertyRowMapper<ItsysUserDto>(ItsysUserDto.class), loginName);
    }


}
