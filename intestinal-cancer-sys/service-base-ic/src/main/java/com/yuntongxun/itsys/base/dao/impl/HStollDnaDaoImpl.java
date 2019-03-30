package com.yuntongxun.itsys.base.dao.impl;

import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.common.util.StringUtil;
import com.yuntongxun.itsys.base.dao.HStollDnaDao;
import com.yuntongxun.itsys.base.vo.TodoVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class HStollDnaDaoImpl implements HStollDnaDao{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private final Logger log = LogManager.getLogger(HStollDnaDaoImpl.class.getName());
	@Override
	public ListPageUtil notEntryDNACodeList(int dept_id, int code_entry_status, TodoVo vo) throws ItSysException {
		log.info("@Dao notEntryDNACodeList query  Start ");
		String sql = "select t2.data_id as `id`, t1.sid,t1.`name`,u.nickName,t1.sex,t1.age,t1.phone as cellPhone,t3.name as commdeptName,"
				+ "case when t1.`group`= 'A' then 'A' when t1.`group` = 'B'  then 'B' when t1.`group` = 'C' and t1.risk_level is null then 'C'   when t1.`group` = 'C' and t1.risk_level = 1 then 'Cd' when t1.`group` = 'C' and  t1.risk_level = 2 then 'Cg' ELSE NULL END `group`,"
				+ "t1.in_group_date as inGroupDate from hospital_intestine_review t1,hospital_todo_event t2 ,itsys_department t3, itsys_user u where"
				+ "     t1.sid = t2.sid and t1.community_dept_id = t3.id "
				+ " AND t1.create_user = u.loginName "
				+ " and t2.community_dept_id=? "
				+ " and t2.type=?"
				+ " and t2.`status`=?";
		List param=new ArrayList();
		param.add(dept_id);
		param.add(4);//4：未录入粪便DNA装置编号
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
		log.info("@Dao notEntryDNACodeList query  End ");
		ListPageUtil listPage=new ListPageUtil(sql,param.toArray(),vo.getPageNo(), vo.getPageSize(),jdbcTemplate,null);
		return listPage;
	}

	/**
	 * 查询未录入粪便DNA装置编号按照地区
	 * @param areaId
	 * @param code_entry_status
	 * @param vo
	 * @return
	 * @throws ItSysException
	 */
	@Override
	public ListPageUtil notEntryDNACodeList1(int areaId, int code_entry_status, TodoVo vo) throws ItSysException {
		log.info("@Dao notEntryDNACodeList query  Start ");
		String sql = "select t2.data_id as `id`, t1.sid,t1.`name`,u.nickName,t1.sex,t1.age,t1.phone as cellPhone,t3.name as commdeptName,"
				+ "case when t1.`group`= 'A' then 'A' when t1.`group` = 'B'  then 'B' when t1.`group` = 'C' and t1.risk_level is null then 'C'   when t1.`group` = 'C' and t1.risk_level = 1 then 'Cd' when t1.`group` = 'C' and  t1.risk_level = 2 then 'Cg' ELSE NULL END `group`,"
				+ "t1.in_group_date as inGroupDate from hospital_intestine_review t1,hospital_todo_event t2 ,itsys_department t3, itsys_user u where"
				+ "     t1.sid = t2.sid and t1.community_dept_id = t3.id"
				+ " AND t1.create_user = u.loginName "
				+ " and t2.area_dept_id=? "
				+ " and t2.type=?"
				+ " and t2.`status`=?";
		List param=new ArrayList();
		param.add(areaId);
		param.add(4);//4：未录入粪便DNA装置编号
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
		log.info("@Dao notEntryDNACodeList query  End ");
		ListPageUtil listPage=new ListPageUtil(sql,param.toArray(),vo.getPageNo(), vo.getPageSize(),jdbcTemplate,null);
		return listPage;
	}

	/**
	 * 根据市区id查询未录入粪便DNA编号
	 */
	@Override
	public List notStollDNAByAreaId(int areaId, int code_entry_status, TodoVo vo) {
		// TODO Auto-generated method stub
		log.info("@Dao notEntryDNACodeList query  Start ");
		String sql = "select count(distinct(t1.sid)) as notStollDNA from hospital_intestine_review t1,hospital_todo_event t2,"
				+ " itsys_department t3 where"
				+ "     t1.sid = t2.sid and t2.area_dept_id = t2.pid "
				+ " and t2.area_dept_id = ? "
				+ " and t2.type=?"
				+ " and t2.`status`=?";
		List list = jdbcTemplate.queryForList(sql,areaId,4,code_entry_status);
		return list;
	}
	@Override
	public Integer addDNA(TodoVo todoVo) {
		String sql = "insert into hospital_stool_dna(sid,dna_code,person_code,release_date,stage,code_entry_status,area_dept_id,community_dept_id,operation_source,entry_time,date_created,update_time) values(?,?,?,?,?,?,?,?,?,now(),now(),now())";
		return jdbcTemplate.update(sql, new Object[]{todoVo.getSid(),todoVo.getDnaCode(), todoVo.getPersonCode(), todoVo.getReleaseDateForSql(), todoVo.getStage(), todoVo.getCodeEntryStatus(), todoVo.getAreaDeptId(), todoVo.getCommunityDeptId(), todoVo.getOperationSource(),});

	}

	@Override
	public TodoVo queryByDNA(String dNACode) {
		TodoVo result;
		try {
			String sql = "select * from hospital_stool_dna where dna_code=?";
			result = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<TodoVo>(TodoVo.class), dNACode);
		} catch (EmptyResultDataAccessException e) {
			log.info("queryByDNA hospital_stool_dna is error data is null");
			return null;
		} catch (IncorrectResultSizeDataAccessException e) {
			log.info("queryByDNA hospital_stool_dna is error data size is >1");
			return new TodoVo();
		}
		return result;
	}

}
