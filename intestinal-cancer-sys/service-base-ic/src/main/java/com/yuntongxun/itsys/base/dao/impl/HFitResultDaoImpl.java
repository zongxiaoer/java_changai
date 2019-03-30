package com.yuntongxun.itsys.base.dao.impl;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.yuntongxun.itsys.base.common.exception.DaoException;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.common.util.StringUtil;
import com.yuntongxun.itsys.base.dao.HFitResultDao;
import com.yuntongxun.itsys.base.po.HospitalFitResult;
import com.yuntongxun.itsys.base.vo.TodoVo;
@Repository
public class HFitResultDaoImpl implements HFitResultDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private final Logger log = LogManager.getLogger(HFitResultDaoImpl.class.getName());
    //查询 未录入FIT编号 受试者列表
	@Override
	public ListPageUtil notEntryFitCodeList(int dept_id, int code_entry_status, TodoVo vo)
			throws ItSysException {
		// TODO Auto-generated method stub
		log.info("@Dao notEntryFitCodeList query  Start ");
		String sql = "select t2.data_id as id, t1.sid,t1.`name`,u.nickName,t1.sex,t1.age,t1.phone as cellPhone,t3.name as commdeptName,"
				+ "case when t1.`group`= 'A' then 'A' when t1.`group` = 'B'  then 'B' when t1.`group` = 'C' and t1.risk_level is null then 'C'   when t1.`group` = 'C' and t1.risk_level = 1 then 'Cd' when t1.`group` = 'C' and  t1.risk_level = 2 then 'Cg' ELSE NULL END `group`,"
				+ "t1.in_group_date as inGroupDate from hospital_intestine_review t1,hospital_todo_event t2 , itsys_user u,itsys_department t3 where"
				+ "     t1.sid = t2.sid and t1.community_dept_id = t3.id"
				+ " AND t1.create_user = u.loginName "
				+ " and t2.community_dept_id=? "
				+ " and t2.type=?"
				+ " and t2.`status`=?";
		List param=new ArrayList();
		param.add(dept_id);
		param.add(2);//2：未录入FIT编号
		param.add(code_entry_status);

		if (!StringUtil.isEmpty(vo.getSid())) {
            sql += " and t1.sid like ?";
            param.add("%"+vo.getSid()+"%");
        }

		if (!StringUtil.isEmpty(vo.getName())) {
            sql += " and t1.`name` like ?";
            param.add("%"+vo.getName()+"%");
        }

		if (!StringUtil.isEmpty(vo.getPhone())) {
            sql += " and t1.phone like ?";
            param.add("%"+vo.getPhone()+"%");
        }

		if (!StringUtil.isEmpty(vo.getGroup())) {
			if(vo.getGroup().equals("Cg")){
				sql += " and t1.`group` = 'C' and t1.risk_level = ?";
				param.add(2);
			}else if(vo.getGroup().equals("Cd")){
				sql += " and t1.`group` = 'C' and t1.risk_level = ?";
				param.add(1);
			}else{
				sql += " and t1.`group` like ?";
				param.add("%"+vo.getGroup()+"%");				
			}
        }
		if(!StringUtil.isEmpty(vo.getLoginName())){
			sql += " and u.loginName = ?";
			param.add(vo.getLoginName());
		}
		log.info("@Dao HFitResultDaoImpl query  End ");
		ListPageUtil listPage=new ListPageUtil(sql,param.toArray(),vo.getPageNo(), vo.getPageSize(),jdbcTemplate,null);
		return listPage;
	}

	/**
	 * 按照地区查询未录入fit编码
	 * @param areaId
	 * @param code_entry_status
	 * @param vo
	 * @return
	 * @throws ItSysException
	 */
	@Override
	public ListPageUtil notEntryFitCodeList1(int areaId, int code_entry_status, TodoVo vo) throws ItSysException {
		log.info("@Dao notEntryFitCodeList query  Start ");
		String sql = "select t2.data_id as id, t1.sid,t1.`name`,u.nickName,t1.sex,t1.age,t1.phone as cellPhone,t3.name as commdeptName,"
				+ "case when t1.`group`= 'A' then 'A' when t1.`group` = 'B'  then 'B' when t1.`group` = 'C' and t1.risk_level is null then 'C'   when t1.`group` = 'C' and t1.risk_level = 1 then 'Cd' when t1.`group` = 'C' and  t1.risk_level = 2 then 'Cg' ELSE NULL END `group`,"
				+ "t1.in_group_date as inGroupDate from hospital_intestine_review t1,hospital_todo_event t2 ,itsys_department t3, itsys_user u where"
				+ "     t1.sid = t2.sid and t1.community_dept_id = t3.id"
				+ " AND t1.create_user = u.loginName "
				+ " and t2.area_dept_id=? "
				+ " and t2.type=?"
				+ " and t2.`status`=?";
		List param=new ArrayList();
		param.add(areaId);
		param.add(2);//2：未录入FIT编号
		param.add(code_entry_status);

		if (!StringUtil.isEmpty(vo.getSid())) {
			sql += " and t1.sid like ?";
			param.add("%"+vo.getSid()+"%");
		}

		if (!StringUtil.isEmpty(vo.getName())) {
			sql += " and t1.`name` like ?";
			param.add("%"+vo.getName()+"%");
		}

		if (!StringUtil.isEmpty(vo.getPhone())) {
			sql += " and t1.phone like ?";
			param.add("%"+vo.getPhone()+"%");
		}

		if (!StringUtil.isEmpty(vo.getGroup())) {
			if(vo.getGroup().equals("Cg")){
				sql += " and t1.`group` = 'C' and t1.risk_level = ?";
				param.add(2);
			}else if(vo.getGroup().equals("Cd")){
				sql += " and t1.`group` = 'C' and t1.risk_level = ?";
				param.add(1);
			}else{
				sql += " and t1.`group` like ?";
				param.add("%"+vo.getGroup()+"%");
			}
		}
		if(vo.getCommunityDeptId() != null){
			sql += " and t2.community_dept_id = ?";
			param.add(vo.getCommunityDeptId());
		}
		if(!StringUtil.isEmpty(vo.getLoginName())){
			sql += " and u.loginName = ?";
			param.add(vo.getLoginName());
		}
		log.info("@Dao HFitResultDaoImpl query  End ");
		ListPageUtil listPage=new ListPageUtil(sql,param.toArray(),vo.getPageNo(), vo.getPageSize(),jdbcTemplate,null);
		return listPage;
	}

	//查询 未录入FIT结果 受试者列表
	@Override
	public ListPageUtil notEntryFitResultList(int dept_id, int insert_status, TodoVo vo)
			throws ItSysException {
		log.info("@Dao HFitResultDaoImpl query  Start ");
		String sql = "select t2.data_id as id,  t1.sid,t1.`name`,u.nickName,t1.sex,t1.age,t1.phone as cellPhone,t3.name as commdeptName,"
				+ "case when t1.`group`= 'A' then 'A' when t1.`group` = 'B'  then 'B' when t1.`group` = 'C' and t1.risk_level is null then 'C'   when t1.`group` = 'C' and t1.risk_level = 1 then 'Cd' when t1.`group` = 'C' and  t1.risk_level = 2 then 'Cg' ELSE NULL END `group`,"
				+ "t1.in_group_date as inGroupDate,t1.overall_status_cy as overallStatusCy from hospital_intestine_review t1,hospital_todo_event t2 , itsys_user u,itsys_department t3 where"
				+ "     t1.sid = t2.sid and t1.community_dept_id = t3.id"
				+ " AND t1.create_user = u.loginName "
				+ " and t2.community_dept_id=? "
				+ " and t2.type=?"
				+ " and t2.`status`=?";
		List param=new ArrayList();
		param.add(dept_id);
		param.add(3);//3：未录入FIT结果
		param.add(insert_status);
		
		if (!StringUtil.isEmpty(vo.getSid())) {
            sql += " and t1.sid like ?";
            param.add("%"+vo.getSid()+"%");
        }

		if (!StringUtil.isEmpty(vo.getName())) {
            sql += " and t1.`name` like ?";
            param.add("%"+vo.getName()+"%");
        }

		if (!StringUtil.isEmpty(vo.getPhone())) {
            sql += " and t1.phone like ?";
            param.add("%"+vo.getPhone()+"%");
        }

		if (!StringUtil.isEmpty(vo.getGroup())) {
			if(vo.getGroup().equals("Cg")){
				sql += " and t1.`group` = 'C' and t1.risk_level = ?";
				param.add(2);
			}else if(vo.getGroup().equals("Cd")){
				sql += " and t1.`group` = 'C' and t1.risk_level = ?";
				param.add(1);
			}else{
				 sql += " and t1.`group` like ?";
		         param.add("%"+vo.getGroup()+"%");
			}   
        }
		if(!StringUtil.isEmpty(vo.getLoginName())){
			sql += " and u.loginName = ?";
			param.add(vo.getLoginName());
		}
		log.info("@Dao HFitResultDaoImpl query  End ");
		ListPageUtil listPage=new ListPageUtil(sql,param.toArray(),vo.getPageNo(), vo.getPageSize(),jdbcTemplate,null);
		return listPage;
	}

	/**
	 * 查询未录入fit结果按照地区
	 * @param areaId
	 * @param insert_status
	 * @param vo
	 * @return
	 * @throws ItSysException
	 */
	@Override
	public ListPageUtil notEntryFitResultList1(int areaId, int insert_status, TodoVo vo) throws ItSysException {
		log.info("@Dao HFitResultDaoImpl query  Start ");
		String sql = "select t2.data_id as id,  t1.sid,t1.`name`,u.nickName,t1.sex,t1.age,t1.phone as cellPhone,t3.name as commdeptName,"
				+ "case when t1.`group`= 'A' then 'A' when t1.`group` = 'B'  then 'B' when t1.`group` = 'C' and t1.risk_level is null then 'C'   when t1.`group` = 'C' and t1.risk_level = 1 then 'Cd' when t1.`group` = 'C' and  t1.risk_level = 2 then 'Cg' ELSE NULL END `group`,"
				+ "t1.in_group_date as inGroupDate,t1.overall_status_cy as overallStatusCy from hospital_intestine_review t1,hospital_todo_event t2 ,itsys_department t3, itsys_user u where"
				+ "     t1.sid = t2.sid and t1.community_dept_id = t3.id"
				+ " AND t1.create_user = u.loginName "
				+ " and t2.area_dept_id=? "
				+ " and t2.type=?"
				+ " and t2.`status`=?";
		List param=new ArrayList();
		param.add(areaId);
		param.add(3);//3：未录入FIT结果
		param.add(insert_status);

		if (!StringUtil.isEmpty(vo.getSid())) {
			sql += " and t1.sid like ?";
			param.add("%"+vo.getSid()+"%");
		}

		if (!StringUtil.isEmpty(vo.getName())) {
			sql += " and t1.`name` like ?";
			param.add("%"+vo.getName()+"%");
		}

		if (!StringUtil.isEmpty(vo.getPhone())) {
			sql += " and t1.phone like ?";
			param.add("%"+vo.getPhone()+"%");
		}

		if (!StringUtil.isEmpty(vo.getGroup())) {
			if(vo.getGroup().equals("Cg")){
				sql += " and t1.`group` = 'C' and t1.risk_level = ?";
				param.add(2);
			}else if(vo.getGroup().equals("Cd")){
				sql += " and t1.`group` = 'C' and t1.risk_level = ?";
				param.add(1);
			}else{
				sql += " and t1.`group` like ?";
				param.add("%"+vo.getGroup()+"%");
			}
		}
		if(vo.getCommunityDeptId() != null){
			sql += " and t2.community_dept_id = ?";
			param.add(vo.getCommunityDeptId());
		}
		if(!StringUtil.isEmpty(vo.getLoginName())){
			sql += " and u.loginName = ?";
			param.add(vo.getLoginName());
		}
		log.info("@Dao HFitResultDaoImpl query  End ");
		ListPageUtil listPage=new ListPageUtil(sql,param.toArray(),vo.getPageNo(), vo.getPageSize(),jdbcTemplate,null);
		return listPage;
	}

	//根据id查询详情
		@Override
		public HospitalFitResult get(int id) throws DaoException {
			// TODO Auto-generated method stub
			log.info("@Dao HFitResultDaoImpl get query  Start ");
			String sql = "select path_url as pathUrl,in_ten_min as inTenMin,`id`,fit_code as fitCode,sid,stage,release_date as releaseDate,release_person_code as releasePersonCode,test_date as testDate,result_status as resultStatus,result_date as resultDate,up_line_value as upLineValue,down_line_value as downLineValue,result,no_reson_result as noResonResult,code_entry_status as codeEntryStatus,insert_status as insertStatus,community_dept_id as communityDeptId,area_dept_id as areaDeptId,date_created as dateCreated,update_time as updateTime from hospital_fit_result where 1=1 and id = ?";
			HospitalFitResult result=jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<HospitalFitResult>(HospitalFitResult.class),id);
			log.info("@Dao HFitResultDaoImpl get query  End ");
			return result;
		}
		//新增fit结果
		@Override
		public void insert(HospitalFitResult fitResult) throws DaoException {
			// TODO Auto-generated method stub
			log.info("@Dao fit结果insert Start ");
			String sql="insert into hospital_fit_result(sid,fit_code,stage,release_date,"
					+ "release_person_code,test_date,result_status,result_date,up_line_value,down_line_value,"
					+ "result,no_reson_result,code_entry_status,insert_status,community_dept_id,area_dept_id,"
					+ "date_created,update_time) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),now())";
			jdbcTemplate.update(sql, new Object[] {fitResult.getSid(),fitResult.getFitCode(),
					fitResult.getStage(),fitResult.getReleaseDate(),fitResult.getRelPerCode(),fitResult.getTestDate(),
					fitResult.getResultStatus(),fitResult.getResultDate(),fitResult.getUpLineValue(),fitResult.getDownLineValue(),fitResult.getResult(),
					fitResult.getNoResonResult(),fitResult.getCodeEntryStatus(),fitResult.getInsertStatus(),fitResult.getCommunityDeptId(),fitResult.getAreaDeptId()});
			log.info("@Dao fit结果insert End ");
		}
		//根据市区id查询所对应的社区未录入的FIT编号
		@Override
		public List notFitCodeByAreaIdList(int areaId, int code_entry_status, TodoVo vo) throws ItSysException {
			// TODO Auto-generated method stub
			log.info("@Dao notFitCodeByAreaIdList query  Start ");
			String sql = "select count(distinct(t1.sid)) as notFitcodes from hospital_intestine_review t1,hospital_todo_event t2,"
					+ " itsys_department t3 where t1.sid = t2.sid and t2.area_dept_id = t3.pid"
					+ " and t2.area_dept_id=? "
					+ " and t2.type=?"
					+ " and t2.`status`=?";
			List arrs = jdbcTemplate.queryForList(sql,areaId,2,code_entry_status);
			log.info("@Dao notFitCodeByAreaIdList query  End ");
			return arrs;
		}
		//根据市区id查询所属社区未录入FIT结果
		@Override
		public List notFitResultByAreaIdlist(int areaId, int insert_status, TodoVo vo) throws DaoException {
			// TODO Auto-generated method stub
			log.info("@Dao HFitResultDaoImpl query  Start ");
			String sql = "select count(distinct(t1.sid)) as notFitResult from hospital_intestine_review t1,"
					+ " hospital_todo_event t2,itsys_department t3 where"
					+ " t1.sid = t2.sid and t2.area_dept_id = t3.pid"
					+ " and t2.area_dept_id=? "
					+ " and t2.type=?"
					+ " and t2.`status`=?";
			List arrs = jdbcTemplate.queryForList(sql,areaId,3,insert_status);
			log.info("@Dao HFitResultDaoImpl query  End ");
			return arrs;
		}

}
