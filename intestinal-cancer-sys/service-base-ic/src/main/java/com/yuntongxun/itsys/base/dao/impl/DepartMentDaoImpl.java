package com.yuntongxun.itsys.base.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuntongxun.itsys.base.po.HospitalColonoscopyRecord;
import com.yuntongxun.itsys.base.po.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.common.util.StringUtil;
import com.yuntongxun.itsys.base.dao.DepartMentDao;
import com.yuntongxun.itsys.base.po.DepartMent;
import com.yuntongxun.itsys.base.po.DeptMember;
import com.yuntongxun.itsys.base.vo.AreaListForExcelVo;

@Repository
public class DepartMentDaoImpl implements DepartMentDao {

    @Autowired
    private JdbcTemplate jdbctemp;

    private final Logger log = LogManager.getLogger(DepartMentDaoImpl.class);

    @Override
    public ListPageUtil queryDepartMent(String id, String name, boolean nPage, boolean isTranslate, int pageNo, int pageSize) throws ItSysException {
        // TODO Auto-generated method stub
        List list = new ArrayList();
        String sql = "select `id`,`name`,`level`,`pId`,`desc`,`sort`,`order`,`screeningType`,`type` from itsys_department where 1=1 ";
        List parm = new ArrayList();
        if (!StringUtil.isEmpty(id)) {
            sql += " and id=?";
            parm.add(id);
        }
        if (!StringUtil.isEmpty(name)) {
            sql += " and name like ? ";
            parm.add("%" + name + "%");
        }

        Map<String, String> translateParm = new HashMap();
        if (isTranslate) {
            translateParm.put("type", "DEPARTMENT_TYPE");
            translateParm.put("screeningType", "screeningType");
        }
        ListPageUtil listPage = new ListPageUtil(sql, parm.toArray(), pageNo, pageSize, jdbctemp, translateParm);

        if (nPage) {
            return listPage;
        } else {
            ListPageUtil listPageNoPaging = new ListPageUtil(sql, parm.toArray(), 1, -1, jdbctemp, translateParm);
            return listPageNoPaging;
        }
    }

    @Override
    public List getDepartMentById(String id) throws ItSysException {
        // TODO Auto-generated method stub
        List list = new ArrayList();
        String sql = "select `id`,`name`,`level`,`pId`,`desc`,`sort`,`type`,`screeningType` from itsys_department where 1=1 and id=? ";
        list = jdbctemp.queryForList(sql, new String[]{id});
        return list;
    }

    @Override
    public void insert(DepartMent depart) throws ItSysException {
        // TODO Auto-generated method stub
        String sql = "insert into itsys_department(`name`,`level`,`pId`,`desc`,`sort`,`screeningType`,`datecreated`,`updatetime`,`type`) values(?,?,?,?,?,?,now(),now(),?)";
        Object[] obj = new Object[7];
        obj[0] = depart.getName();
        obj[1] = depart.getLevel();
        obj[2] = depart.getpId();
        obj[3] = depart.getDesc();
        obj[4] = "".equals(depart.getSort())?null:depart.getSort();
        obj[5] = depart.getScreeningType();
        obj[6] = depart.getType();
        jdbctemp.update(sql, obj);

    }

    @Override
    public void delDepartMentById(String id) throws ItSysException {
        // TODO Auto-generated method stub
        Object[] obj = new Object[]{id};
        String sql = "delete from itsys_department where id=? ";
        jdbctemp.update(sql, obj);
    }

    @Override
    public void updateDepartMent(DepartMent depart) throws ItSysException {
        // TODO Auto-generated method stub
        Object[] obj = new Object[8];
        obj[0] = depart.getName();
        obj[1] = depart.getLevel();
        obj[2] = depart.getpId();
        obj[3] = depart.getDesc();
        obj[4] = "".equals(depart.getSort())?null:depart.getSort();
        obj[5] = depart.getScreeningType();
        obj[6] = depart.getType();
        obj[7] = depart.getId();
        String sql = "update itsys_department set `name`=?,`level`=?,`pId`=?,`desc`=?,`sort`=?,`screeningType`=?,`updatetime`=now(),`type`=? where `id`=?";
        jdbctemp.update(sql, obj);
    }


    @Override
    public void delDepartMentMemberByUser(List user) throws ItSysException {
        // TODO Auto-generated method stub
        String sqls[] = new String[user.size()];
        for (int i = 0; i < user.size(); i++) {
            String employeeId = user.get(i).toString();
            sqls[i] = "delete from itsys_department_member where userid=" + employeeId + "";
        }
        jdbctemp.batchUpdate(sqls);
    }

    @Override
    public void delDepartMentMemberByDept(String deptid) throws ItSysException {
        // TODO Auto-generated method stub
        String sql = "delete from itsys_department_member where deptid=?";
        jdbctemp.update(sql, new Object[]{deptid});
    }

    @Override
    public List getDepartMentMember(String departId) throws ItSysException {
        // TODO Auto-generated method stub
        String sql = "select u.id,u.nickName as name,u.address,u.phone,u.qq,dm.deptId,dm.title,dm.position,dep.`name` as deptName,dm.sort,dm.id as memberId from itsys_user u,itsys_department_member dm,itsys_department dep where u.id=dm.userId and dep.id=dm.deptId and dm.deptId=? order by u.nickName ";
        List list = jdbctemp.queryForList(sql, new Object[]{departId});
        return list;
    }

    @Override
    public String getPidByDepartId(String departId) {
        // TODO Auto-generated method stub
        String sql = "select pid from itsys_department where 1=1 and `id`=?";
        List pid = jdbctemp.queryForList(sql, new Object[]{departId}, String.class);
        if (pid.size() > 0 && pid != null) {
            return pid.get(0).toString();
        }
        return null;
    }

    @Override
    public void saveDepartMentMemberOnList(List<DeptMember> dm) {
        // TODO Auto-generated method stub
        String[] sqls = new String[dm.size()];
        for (int i = 0; i < dm.size(); i++) {
            DeptMember dept = dm.get(i);
            String userid = dept.getUserId() + "";
            String deptid = dept.getDeptId() + "";
            String position = dept.getPosition() + "";
            String title = dept.getTitle();
            String sort = dept.getSort() + "";
            String sql = "insert into itsys_department_member(`deptid`,`userid`,`position`,`title`,`sort`,`datecreated`) values(" + deptid + "," + userid + "," + position + "," + title + "," + sort + ",now())";
            sqls[i] = sql;
        }
        jdbctemp.batchUpdate(sqls);

    }

    @Override
    public void updateDepartMember(String id, String deptId, String employeeId, String position) {
        String sql = "update itsys_department_member set deptid=?,userid=?,position=? ,datecreated=now() where `id`=?";
        Object obj[] = new Object[]{deptId, employeeId, position, id};
        jdbctemp.update(sql, obj);
    }

    @Override
    public ListPageUtil queryNotExistsDepartEmployee(int pageNo, int pageSize) {
        String sql = "SELECT emp.id AS id, emp.nickName AS name, dep.id AS departId, dep.`name` AS departName FROM itsys_user emp LEFT JOIN itsys_department_member depm ON emp.id = depm.userId LEFT JOIN itsys_department dep ON depm.deptId = dep.id WHERE 1 = 1 AND NOT EXISTS ( SELECT * FROM itsys_department_member xx WHERE emp.id = xx.userid )";
        Map<String, String> translateParm = new HashMap();
        List<String> parm = new ArrayList();
        ListPageUtil listPage = new ListPageUtil(sql, parm.toArray(), pageNo, pageSize, jdbctemp, translateParm);
        return listPage;
    }

    @Override
    public List<DepartMent> findHospitalByPid(Integer pid) {
        String sql = "SELECT dep.* from itsys_department dep WHERE dep.pid = ?";

        List<DepartMent> departMentList = jdbctemp.query(sql, new Object[]{pid}, new BeanPropertyRowMapper<DepartMent>(DepartMent.class));

        return departMentList;
    }
    /**
     * 新增查询所属子级
     */
	@Override
	public List<DepartMent> findAllNodesByPid(String id) {
		// TODO Auto-generated method stub
		String sql = "select distinct d.`id`,d.`name`,d.`level`,d.`pId`,d.`desc`,d.`sort`,d.`type`,d.`screeningType` from itsys_department d WHERE 1 = 1 ";
		List parm = new ArrayList();
		if (!StringUtil.isEmpty(id)) {
			sql += " and d.pid= ?";
			parm.add(id);
		}
		List<DepartMent> list = jdbctemp.query(sql.toString(), parm.toArray(), new BeanPropertyRowMapper<DepartMent>(DepartMent.class));
		return list;
	}
    /**
     * 根据id查询创建者(区下属的社区)
     */
	@Override
	public List<DepartMent> findNickNameById(String id,Integer userid) {
		// TODO Auto-generated method stub
		String sql = "SELECT d.*,u.id as uid,u.nickName,u.loginName FROM itsys_user u, itsys_department d , itsys_department_member dm WHERE u.id = dm.userId AND dm.deptId = d.id";
		List parm = new ArrayList();
		if (!StringUtil.isEmpty(id)) {
			sql += " and d.id = ?";
			parm.add(id);
		}

		if(userid!=null){
            sql += " and u.id = ?";
            parm.add(userid);
        }

		List<DepartMent> list = jdbctemp.query(sql.toString(), parm.toArray(), new BeanPropertyRowMapper<DepartMent>(DepartMent.class));
		return list;
	}

    @Override
    public List<Role> queryRoleByUserId(Integer userID) {
        String sql = "select * from itsys_role r1,itsys_user_role_r r2 where r1.id=r2.roleId and r2.userId=? and r1.id=132 ";
        return jdbctemp.query(sql, new BeanPropertyRowMapper<Role>(Role.class),userID);

    }

    @Override
    public List<DepartMent> queryDepartMentByID(String communityDeptId) {
        String sql = "select * from itsys_department where id=?";
        return jdbctemp.query(sql, new BeanPropertyRowMapper<DepartMent>(DepartMent.class),communityDeptId);
    }

    @Override
    public List<DepartMent> queryDepartMentInID(String substring) {
        String sql = "select * from itsys_department where id in ("+substring+")";
        return jdbctemp.query(sql, new BeanPropertyRowMapper<DepartMent>(DepartMent.class));
    }
}
