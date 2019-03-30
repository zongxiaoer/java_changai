package com.yuntongxun.itsys.base.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.yuntongxun.itsys.base.dao.SidRuleDao;
import com.yuntongxun.itsys.base.po.dto.sidrule.DepartmentSidRuleDto;
@Repository
public class SidRuleDaoImpl implements SidRuleDao{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private final Logger log = LogManager.getLogger(SidRuleDaoImpl.class.getName());
	/**
	 * 根据登陆名称查询相关信息
	 */
	@Override
	public List<DepartmentSidRuleDto> querySidRuleList(String loginName) {
		// TODO Auto-generated method stub
		log.info("querySidRuleList query Start");
		String sql = "select  t1.id,t1.department_id as departmentId,t1.max_person as maxPerson,t1.community_scope as communityScope,t1.rule_type as ruleType,"
				+ "t1.community_scope_status as communityScopeStatus,d.name,d.pid,d.type,t1.group_rule_id as groupRuleId from itsys_sid_rule t1, "
				+ "itsys_user u, itsys_department d ,itsys_department_member dm where t1.department_id = d.id"
				+ " and  u.id =dm.userId AND dm.deptId = d.id AND u.loginName = ?";
		log.info("@Dao querySidRuleList query  End ");
		List param1=new ArrayList();
		param1.add(loginName);
		List<DepartmentSidRuleDto> list = jdbcTemplate.query(sql.toString(), param1.toArray(),new BeanPropertyRowMapper<DepartmentSidRuleDto>(DepartmentSidRuleDto.class));
		return list;
	}
	/**
	 * 校验规则
	 */
	/*@Override
	public Boolean flag(String loginName) {
		// TODO Auto-generated method stub
		List<DepartmentSidRuleDto> list = querySidRuleList(loginName);
		for (DepartmentSidRuleDto departmentSidRuleDto : list) {
			if(departmentSidRuleDto.getType() == 1){       //1：社区
				int pid = departmentSidRuleDto.getPid();   //获取社区父id(地区id)
				int sumsByArea = ruleCounts(pid);              //获取地区总招募人数
				List<Map<String,Object>> commids = queryByAreaId(pid);  //根据pid查询社区id
				int sumspersons = 0;
				for (Map<String, Object> map : commids) {
					int commId = Integer.parseInt(map.get("id").toString());
					int sums1 = queryrivewByCommId(commId);
					sumspersons += sums1;
				}
				int id = departmentSidRuleDto.getDepartmentId(); //获取社区id;
				int maxpersons = departmentSidRuleDto.getMaxPerson();
				int sums = queryrivewByCommId(id);
				if(maxpersons > sums && sumsByArea > sumspersons){
					return true;
				}else{
					return false;
				}
			}else if(departmentSidRuleDto.getType() == 2){ //2：地区
				int id = departmentSidRuleDto.getDepartmentId();  //获取地区id
				List<Map<String,Object>> commids = queryByAreaId(id);  //根据地区id查询所属社区id
				int sumspersons = 0;
				int maxs = 0;
				int sums1 = 0;
				for (Map<String, Object> map : commids) {
					int commId = Integer.parseInt(map.get("id").toString());
					sums1 = queryrivewByCommId(commId);
					maxs =  ruleCounts(commId);
					if(sums1 >= maxs){
						return false;
					}
					sumspersons += sums1;
				}
				int maxpersons = departmentSidRuleDto.getMaxPerson();  //地区招募人数
				int sums = queryrivewByAreaId(id);
				if(maxpersons>sums && maxpersons > sumspersons){
					return true;
				}else{
					return false;
				}
			}
		}
		return false;
	}*/
	/**
	 * 优化校验
	 * @param loginName
	 * @return
	 */
	public Boolean flag(String loginName) {
		// TODO Auto-generated method stub
		List<DepartmentSidRuleDto> list = querySidRuleList(loginName);
		if (list.get(0).getType() == 1) { // 1：社区
			int pid = list.get(0).getPid(); // 获取社区父id(地区id)
			int sumsByArea = ruleCounts(pid); // 获取地区总招募人数
			int sumspersons = queryrivewByAreaId(pid);   //根据`area_dept_id` 统计总人数
			int id = list.get(0).getDepartmentId(); // 获取社区id;
			int maxpersons = list.get(0).getMaxPerson();
			int sums = queryrivewByCommId(id);
			if (maxpersons > sums && sumsByArea > sumspersons) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	} 
	/**
	 * 查询地区受试者人数
	 * @return
	 */
	public int queryrivewByAreaId(Integer id){
		int persons = 0;
		String sql = "select count(distinct(sid))as persons from hospital_intestine_review where area_dept_id = ?";
		List<Map<String,Object>> rs = jdbcTemplate.queryForList(sql,id);
		for (Map<String, Object> map : rs) {
			persons = Integer.parseInt(map.get("persons").toString());
		}
		return persons;
	}
	/**
	 * 按照社区统计生成人数
	 * @param id
	 * @return
	 */
	public int queryrivewByCommId(Integer id){
		int persons = 0;
		String sql = "select count(distinct(sid))as persons from hospital_intestine_review where community_dept_id = ?";
		List<Map<String,Object>> rs = jdbcTemplate.queryForList(sql,id);
		for (Map<String, Object> map : rs) {
			persons = Integer.parseInt(map.get("persons").toString());
		}
		return persons;
	}
	/**
	 * 根据地区id查询
	 * @return
	 */
	public List<Map<String,Object>> queryByAreaId(Integer pid){
		String sql = "select id from itsys_department where pid = ?";
		List<Map<String,Object>> arrs = jdbcTemplate.queryForList(sql,pid);
		return arrs;
	}
	/**
	 * 根据id查询规定招募人数
	 * @return
	 */
	public int ruleCounts(Integer pid){
		String sql = "select max_person from itsys_sid_rule where department_id = ?";
		List<Map<String,Object>> ars = jdbcTemplate.queryForList(sql,pid);
		int maxs = 0;
		for (Map<String, Object> map : ars) {
			maxs = Integer.parseInt(map.get("max_person").toString());
		}
		return maxs;
	}

	@Override
	public List<DepartmentSidRuleDto> findById(Integer id) {
		String sql = "select * from itsys_sid_rule  where department_id=?";//+id+" and sid='"+sid+"'"
		//log.info(sql);
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<DepartmentSidRuleDto>(DepartmentSidRuleDto.class), id);
	}
}
