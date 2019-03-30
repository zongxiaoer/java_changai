package com.yuntongxun.itsys.base.dao.impl;

import java.math.BigDecimal;
import java.util.*;

import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.util.IntegerUtil;
import com.yuntongxun.itsys.base.common.util.JSONUtils;
import com.yuntongxun.itsys.base.vo.LesionStatisticsVo;
import org.apache.commons.lang.StringUtils;
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
import com.yuntongxun.itsys.base.dao.HcrAllocationDao;
import com.yuntongxun.itsys.base.dao.HiReviewDao;
import com.yuntongxun.itsys.base.po.HospitalReview;
import com.yuntongxun.itsys.base.vo.TodoVo;
@Repository
public class HiReviewDaoImpl implements HiReviewDao{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private HcrAllocationDao hcrAllocationDao;

	private final Logger log = LogManager.getLogger(HiReviewDaoImpl.class.getName());

	@Override
	public ListPageUtil notEntryRiskfactors(int dept_id,int reserve_status,TodoVo vo)
			throws ItSysException {
		log.info("@Dao notEntryRiskfactors query  Start ");
		String sql = "select t2.data_id as `id`, t1.sid,t1.`name`,u.nickName,t1.sex,t1.age,t1.phone as cellPhone,t3.name as commdeptName,"
				+ "case when t1.`group`= 'A' then 'A' when t1.`group` = 'B'  then 'B' when t1.`group` = 'C' and t1.risk_level is null then 'C'   when t1.`group` = 'C' and t1.risk_level = 1 then 'Cd' when t1.`group` = 'C' and  t1.risk_level = 2 then 'Cg' ELSE NULL END `group`,"
				+ "t1.in_group_date as inGroupDate,t1.overall_status_cy as overallStatusCy from hospital_intestine_review t1,hospital_todo_event t2 , itsys_user u,itsys_department t3 where"
				+ "     t1.sid = t2.sid and t1.community_dept_id = t3.id"
				+ " AND t1.create_user  = u.loginName "
				+ " and t2.community_dept_id=? "
				+ " and t2.type=?"
				+ " and t2.`status`=?";
		List param=new ArrayList();
		param.add(dept_id);
		param.add(1);//1：未完成危险因素调查表
		param.add(reserve_status);

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
				sql += " and t1.`group` = 'C' and t1.`risk_level` = ?";
				param.add(2);
			}else if(vo.getGroup().equals("Cd")){
				sql += " and t1.`group` = 'C' and t1.`risk_level` = ?";
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
		log.info("@Dao notEntryRiskfactors query  End ");
		ListPageUtil listPage=new ListPageUtil(sql,param.toArray(),vo.getPageNo(), vo.getPageSize(),jdbcTemplate,null);
		return listPage;
	}
	/**
	 * 按照地区查询代办个数详情
	 * @param areaId
	 * @param reserve_status
	 * @param vo
	 * @return
	 * @throws ItSysException
	 */
	@Override
	public ListPageUtil notEntryRiskfactors1(int areaId, int reserve_status, TodoVo vo) throws ItSysException {
		log.info("@Dao notEntryRiskfactors query  Start ");
		String sql = "select t2.data_id as `id`, t1.sid,t1.`name`,u.nickName,t1.sex,t1.age,t1.phone as cellPhone,t3.name as commdeptName, "
				+ "case when t1.`group`= 'A' then 'A' when t1.`group` = 'B'  then 'B' when t1.`group` = 'C' and t1.risk_level is null then 'C'   when t1.`group` = 'C' and t1.risk_level = 1 then 'Cd' when t1.`group` = 'C' and  t1.risk_level = 2 then 'Cg' ELSE NULL END `group`,"
				+ "t1.in_group_date as inGroupDate,t1.overall_status_cy as overallStatusCy from hospital_intestine_review t1,hospital_todo_event t2 ,itsys_department t3, itsys_user u where"
				+ "     t1.sid = t2.sid and t1.community_dept_id = t3.id"
				+ " AND t1.create_user  = u.loginName "
				+ " and t2.area_dept_id=? "
				+ " and t2.type=?"
				+ " and t2.`status`=?";
		List param=new ArrayList();
		param.add(areaId);
		param.add(1);//1：未完成危险因素调查表
		param.add(reserve_status);

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
				sql += " and t1.`group` = 'C' and t1.`risk_level` = ?";
				param.add(2);
			}else if(vo.getGroup().equals("Cd")){
				sql += " and t1.`group` = 'C' and t1.`risk_level` = ?";
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
		log.info("@Dao notEntryRiskfactors query  End ");
		ListPageUtil listPage=new ListPageUtil(sql,param.toArray(),vo.getPageNo(), vo.getPageSize(),jdbcTemplate,null);
		return listPage;
	}
	/**
	 * 社区登陆统计各类型总人数
	 */
	@Override
	public List getGroupBycounts(int communityDeptId,TodoVo vo,String userName) throws DaoException {
		// TODO Auto-generated method stub
		log.info("@Dao getGroupByAcounts query  Start ");
		List<String> rs = new ArrayList<String>();
		String S1 = "";
		String S2 = "";
		String S3 = "";
		String S4 = "";
		String S5 = "";
		String S6 = "";
		String S7 = "";
		String S8 = "";
		String S9="";//社区操作人
		String S10 = "";	//粪便DNA检查已返回
		String S11 = "";	//FIT结果无效
		if(!StringUtils.isEmpty(userName)){
			S9=" and t1.create_user= '"+userName+"' ";
		}
		if(!StringUtil.isEmpty(vo.getStartDate()) && !StringUtil.isEmpty(vo.getEndDate())){
			S1 = " and date_format(in_group_date,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(in_group_date,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
			S2 = " and date_format(t2.reserve_status_date,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.reserve_status_date,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
			S3 = " and date_format(t2.examination_check_date,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.examination_check_date,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
			S4 = " and date_format(t2.entry_time,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.entry_time,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
			S5 = " and date_format(t2.code_entry_time,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.code_entry_time,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
			S6 = " and date_format(t2.result_sys_date,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.result_sys_date,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
			S7 = " and date_format(t2.result_sys_date,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.result_sys_date,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
			S8 = " and date_format(t2.quit_date,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.quit_date,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
			S10 = " and date_format(t2.dna_check_inform_nation_date,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.dna_check_inform_nation_date,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
			S11 = " and date_format(t2.result_sys_date,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.result_sys_date,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
		}
		//入组人数
		String sql1 = "select (select count(distinct(sid)) as groupByA from hospital_intestine_review t1 where t1.group_status = 2 and t1.`group` = 'A' and t1.community_dept_id = "+communityDeptId+" "+S1+S9+") as A ,"
				+ "(select count(distinct(sid)) as groupByB from hospital_intestine_review t1 where t1.group_status = 2 and t1.`group` = 'B' and t1.community_dept_id = "+communityDeptId+" "+S1+S9+") as B,"
				+ "(select count(distinct(sid)) as groupByC from hospital_intestine_review t1 where t1.group_status = 2 and t1.`group` = 'C' and t1.risk_level = 2 and t1.community_dept_id = "+communityDeptId+" "+S1+S9+") as cg,"
				+ "(select count(distinct(sid)) as groupByC from hospital_intestine_review t1 where t1.group_status = 2 and t1.`group` = 'C' and t1.risk_level = 1 and t1.community_dept_id = "+communityDeptId+" "+S1+S9+") as cd,"
				+ "(select count(distinct(sid)) as groupByC from hospital_intestine_review t1 where t1.group_status = 2 and t1.`group` = 'C' and t1.risk_level  is null and t1.community_dept_id = "+communityDeptId+" "+S1+S9+")as cp from dual;";
		log.info("sql==>"+sql1);
		List<Map<String,Object>> list1 = jdbcTemplate.queryForList(sql1);
		String jsons1 = list1.toString().substring(1,list1.toString().length());
		String sumdepts1 = jsons1.substring(0, jsons1.length()-1);
		rs.add(sumdepts1);
		//已预约肠镜检查人数
		String sql2 =  "select " +
                " (SELECT count(DISTINCT(t1.sid)) " +
                "  FROM " +
                "   hospital_intestine_review t1, " +
                "   hospital_colonoscopy_record t2 " +
                "  WHERE t1.sid = t2.sid " +
                "  AND t2.id IN ( " +
                "   SELECT max(ss.id) " +
                "   FROM hospital_colonoscopy_record ss " +
                "   GROUP BY ss.sid " +
                "  ) " +
                "  AND t1.group_status = 2 " +
                "  AND t1.`group` = 'A' " +
                "  AND t1.community_dept_id = "+communityDeptId+
                "  AND t2.reserve_status = 2 "+S2+S9+
                " ) AS A,"

                + " (SELECT count(DISTINCT(t1.sid)) " +
                "  FROM " +
                "   hospital_intestine_review t1, " +
                "   hospital_colonoscopy_record t2 " +
                "  WHERE t1.sid = t2.sid " +
                "  AND t2.id IN ( " +
                "   SELECT max(ss.id) " +
                "   FROM hospital_colonoscopy_record ss " +
                "   GROUP BY ss.sid " +
                "  ) " +
                "  AND t1.group_status = 2 " +
                "  AND t1.`group` = 'B' " +
                "  AND t1.community_dept_id = "+communityDeptId+
                "  AND t2.reserve_status = 2 "+S2+S9+
                " )as B,"

				+ " (SELECT count(DISTINCT(t1.sid)) " +
                "  FROM " +
                "   hospital_intestine_review t1, " +
                "   hospital_colonoscopy_record t2 " +
                "  WHERE t1.sid = t2.sid " +
                "  AND t2.id IN ( " +
                "   SELECT max(ss.id) " +
                "   FROM hospital_colonoscopy_record ss " +
                "   GROUP BY ss.sid " +
                "  ) " +
                "  AND t1.group_status = 2 " +
                "  AND t1.`group` = 'C' AND t1.risk_level = 2 " +
                "  AND t1.community_dept_id = "+communityDeptId+
                "  AND t2.reserve_status = 2 "+S2+S9+
                " )as cg,"

				+" (SELECT count(DISTINCT(t1.sid)) " +
                "  FROM " +
                "   hospital_intestine_review t1, " +
                "   hospital_colonoscopy_record t2 " +
                "  WHERE t1.sid = t2.sid " +
                "  AND t2.id IN ( " +
                "   SELECT max(ss.id) " +
                "   FROM hospital_colonoscopy_record ss " +
                "   GROUP BY ss.sid " +
                "  ) " +
                "  AND t1.group_status = 2 " +
                "  AND t1.`group` = 'C' AND t1.risk_level = 1 " +
                "  AND t1.community_dept_id = "+communityDeptId+
                "  AND t2.reserve_status = 2 "+S2+S9+
                " )as cd,"

				+" (SELECT count(DISTINCT(t1.sid)) " +
                "  FROM " +
                "   hospital_intestine_review t1, " +
                "   hospital_colonoscopy_record t2 " +
                "  WHERE t1.sid = t2.sid " +
                "  AND t2.id IN ( " +
                "   SELECT max(ss.id) " +
                "   FROM hospital_colonoscopy_record ss " +
                "   GROUP BY ss.sid " +
                "  ) " +
                "  AND t1.group_status = 2 " +
                "  AND t1.`group` = 'C' AND t1.risk_level is null " +
                "  AND t1.community_dept_id = "+communityDeptId+
                "  AND t2.reserve_status = 2 "+S2+S9+
                " ) as cp "
				+ " from dual;";
		log.info("sql==>"+sql2);
		List<Map<String,Object>> list2 = jdbcTemplate.queryForList(sql2);
		String jsons2 = list2.toString().substring(1,list2.toString().length());
		String sumdepts2 = jsons2.substring(0, jsons2.length()-1);
		rs.add(sumdepts2);
		//实际接受肠镜检查人数
		String sql3 = "select "
//                + "(select count(distinct(t1.sid))as groupByA from hospital_intestine_review t1,hospital_colonoscopy_record t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'A'  and t1.community_dept_id = "+communityDeptId+" and t2.examination_status = 2 "+S3+S9+")as A,"
//				+ "(select count(distinct(t1.sid))as groupByB from hospital_intestine_review t1,hospital_colonoscopy_record t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'B'  and t1.community_dept_id = "+communityDeptId+" and t2.examination_status = 2 "+S3+S9+")as B,"
//				+ "(select count(distinct(t1.sid))as groupByC from hospital_intestine_review t1,hospital_colonoscopy_record t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'C'  and t1.community_dept_id = "+communityDeptId+" and t2.examination_status = 2 and t1.risk_level = 2 "+S3+S9+")as cg,"
//				+ "(select count(distinct(t1.sid))as groupByC from hospital_intestine_review t1,hospital_colonoscopy_record t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'C'  and t1.community_dept_id = "+communityDeptId+" and t2.examination_status = 2 and t1.risk_level = 1 "+S3+S9+")as cd,"
//				+ "(select count(distinct(t1.sid))as groupByC from hospital_intestine_review t1,hospital_colonoscopy_record t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'C'  and t1.community_dept_id = "+communityDeptId+" and t2.examination_status = 2 and t1.risk_level is null "+S3+S9+") as cp"

                + "(SELECT count(DISTINCT(t1.sid)) " +
                " FROM " +
                "  hospital_intestine_review t1, " +
                "  hospital_colonoscopy_record t2 " +
                " WHERE t1.sid = t2.sid " +
                " AND t2.id IN ( " +
                "  SELECT max(ss.id) " +
                "  FROM hospital_colonoscopy_record ss " +
                "  GROUP BY ss.sid " +
                " ) " +
                " AND t1.group_status = 2 " +
                " AND t1.`group` = 'A' " +
                " AND t1.community_dept_id = "+communityDeptId+
                " AND t2.examination_status = 2 "+S3+S9+
                ") AS A,"
                + "(SELECT count(DISTINCT(t1.sid)) " +
                " FROM " +
                "  hospital_intestine_review t1, " +
                "  hospital_colonoscopy_record t2 " +
                " WHERE t1.sid = t2.sid " +
                " AND t2.id IN ( " +
                "  SELECT max(ss.id) " +
                "  FROM hospital_colonoscopy_record ss " +
                "  GROUP BY ss.sid " +
                " ) " +
                " AND t1.group_status = 2 " +
                " AND t1.`group` = 'B' " +
                " AND t1.community_dept_id = "+communityDeptId+
                " AND t2.examination_status = 2 "+S3+S9+
                ") AS B,"
                + "(SELECT count(DISTINCT(t1.sid)) " +
                " FROM " +
                "  hospital_intestine_review t1, " +
                "  hospital_colonoscopy_record t2 " +
                " WHERE t1.sid = t2.sid " +
                " AND t2.id IN ( " +
                "  SELECT max(ss.id) " +
                "  FROM hospital_colonoscopy_record ss " +
                "  GROUP BY ss.sid " +
                " ) " +
                " AND t1.group_status = 2 " +
                " AND t1.`group` = 'C' AND t1.risk_level = 2 " +
                " AND t1.community_dept_id = "+communityDeptId+
                " AND t2.examination_status = 2 "+S3+S9+
                ") AS cg,"
                + "(SELECT count(DISTINCT(t1.sid)) " +
                " FROM " +
                "  hospital_intestine_review t1, " +
                "  hospital_colonoscopy_record t2 " +
                " WHERE t1.sid = t2.sid " +
                " AND t2.id IN ( " +
                "  SELECT max(ss.id) " +
                "  FROM hospital_colonoscopy_record ss " +
                "  GROUP BY ss.sid " +
                " ) " +
                " AND t1.group_status = 2 " +
                " AND t1.`group` = 'C' AND t1.risk_level = 1 " +
                " AND t1.community_dept_id = "+communityDeptId+
                " AND t2.examination_status = 2 "+S3+S9+
                ") AS cd,"
                + "(SELECT count(DISTINCT(t1.sid)) " +
                " FROM " +
                "  hospital_intestine_review t1, " +
                "  hospital_colonoscopy_record t2 " +
                " WHERE t1.sid = t2.sid " +
                " AND t2.id IN ( " +
                "  SELECT max(ss.id) " +
                "  FROM hospital_colonoscopy_record ss " +
                "  GROUP BY ss.sid " +
                " ) " +
                " AND t1.group_status = 2 " +
                " AND t1.`group` = 'C' AND t1.risk_level is null " +
                " AND t1.community_dept_id = "+communityDeptId+
                " AND t2.examination_status = 2 "+S3+S9+
                ") AS cp"
				+ " from dual;";
		log.info("sql==>"+sql3);
		List<Map<String,Object>> list3 = jdbcTemplate.queryForList(sql3);
		String jsons3 = list3.toString().substring(1,list3.toString().length());
		String sumdepts3 = jsons3.substring(0, jsons3.length()-1);
		rs.add(sumdepts3);

		//接受粪便DNA检测人数
		String sql4 = "select "
//                + "(select count(distinct(t1.sid))as groupByA from hospital_intestine_review t1,hospital_stool_dna t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'A'  and t1.community_dept_id = "+communityDeptId+" and t2.code_entry_status = 2 "+S4+S9+")as A,"
//				+ "(select count(distinct(t1.sid))as groupByB from hospital_intestine_review t1,hospital_stool_dna t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'B'  and t1.community_dept_id = "+communityDeptId+" and t2.code_entry_status = 2 "+S4+S9+")as B,"
//				+ "(select count(distinct(t1.sid))as groupByC from hospital_intestine_review t1,hospital_stool_dna t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'C'  and t1.community_dept_id = "+communityDeptId+" and t2.code_entry_status = 2 and t1.risk_level = 2 "+S4+S9+")as cg,"
//				+ "(select count(distinct(t1.sid))as groupByC from hospital_intestine_review t1,hospital_stool_dna t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'C'  and t1.community_dept_id = "+communityDeptId+" and t2.code_entry_status = 2 and t1.risk_level = 1 "+S4+S9+")as cd,"
//				+ "(select count(distinct(t1.sid))as groupByC from hospital_intestine_review t1,hospital_stool_dna t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'C'  and t1.community_dept_id = "+communityDeptId+" and t2.code_entry_status = 2 and t1.risk_level is null "+S4+S9+") as cp"
                + " ( " +
                " SELECT count(DISTINCT(t1.sid)) " +
                " FROM " +
                "  hospital_intestine_review t1, " +
                "  hospital_stool_dna t2 " +
                " WHERE t1.sid = t2.sid " +
                " AND t2.id IN ( " +
                "  SELECT max(s.id) " +
                "  FROM hospital_stool_dna s " +
                "  GROUP BY s.sid " +
                " ) " +
                " AND t1.group_status = 2 " +
                " AND t1.`group` = 'A' " +
                " AND t1.community_dept_id = "+communityDeptId+
                " AND t2.code_entry_status = 2 "+S4+S9+
                ") AS A, "
                + " ( " +
                " SELECT count(DISTINCT(t1.sid)) " +
                " FROM " +
                "  hospital_intestine_review t1, " +
                "  hospital_stool_dna t2 " +
                " WHERE t1.sid = t2.sid " +
                " AND t2.id IN ( " +
                "  SELECT max(s.id) " +
                "  FROM hospital_stool_dna s " +
                "  GROUP BY s.sid " +
                " ) " +
                " AND t1.group_status = 2 " +
                " AND t1.`group` = 'B' " +
                " AND t1.community_dept_id = "+communityDeptId+
                " AND t2.code_entry_status = 2 "+S4+S9+
                ") AS B, "
                + " ( " +
                " SELECT count(DISTINCT(t1.sid)) " +
                " FROM " +
                "  hospital_intestine_review t1, " +
                "  hospital_stool_dna t2 " +
                " WHERE t1.sid = t2.sid " +
                " AND t2.id IN ( " +
                "  SELECT max(s.id) " +
                "  FROM hospital_stool_dna s " +
                "  GROUP BY s.sid " +
                " ) " +
                " AND t1.group_status = 2 " +
                " AND t1.`group` = 'C' AND t1.risk_level = 2 " +
                " AND t1.community_dept_id = "+communityDeptId+
                " AND t2.code_entry_status = 2 "+S4+S9+
                ") AS cg, "
                + " ( " +
                " SELECT count(DISTINCT(t1.sid)) " +
                " FROM " +
                "  hospital_intestine_review t1, " +
                "  hospital_stool_dna t2 " +
                " WHERE t1.sid = t2.sid " +
                " AND t2.id IN ( " +
                "  SELECT max(s.id) " +
                "  FROM hospital_stool_dna s " +
                "  GROUP BY s.sid " +
                " ) " +
                " AND t1.group_status = 2 " +
                " AND t1.`group` = 'C' AND t1.risk_level = 1 " +
                " AND t1.community_dept_id = "+communityDeptId+
                " AND t2.code_entry_status = 2 "+S4+S9+
                ") AS cd, "
                + " ( " +
                " SELECT count(DISTINCT(t1.sid)) " +
                " FROM " +
                "  hospital_intestine_review t1, " +
                "  hospital_stool_dna t2 " +
                " WHERE t1.sid = t2.sid " +
                " AND t2.id IN ( " +
                "  SELECT max(s.id) " +
                "  FROM hospital_stool_dna s " +
                "  GROUP BY s.sid " +
                " ) " +
                " AND t1.group_status = 2 " +
                " AND t1.`group` = 'C' AND t1.risk_level is null " +
                " AND t1.community_dept_id = "+communityDeptId+
                " AND t2.code_entry_status = 2 "+S4+S9+
                ") AS cp "
				+ " from dual;";
		log.info("sql==>"+sql4);
		List<Map<String,Object>> list4 = jdbcTemplate.queryForList(sql4);
		String jsons4 = list4.toString().substring(1,list4.toString().length());
		String sumdepts4 = jsons4.substring(0, jsons4.length()-1);
		rs.add(sumdepts4);
		//实际接受FIT检查人数
		String sql5 = "select "
//                + "(select count(distinct(t1.sid))as groupByA from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'A'  and t1.community_dept_id = "+communityDeptId+" and t2.code_entry_status = 2 "+S5+S9+")as A,"
//				+ "(select count(distinct(t1.sid))as groupByB from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'B'  and t1.community_dept_id = "+communityDeptId+" and t2.code_entry_status = 2 "+S5+S9+")as B,"
//				+ "(select count(distinct(t1.sid))as groupByC from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'C'  and t1.community_dept_id = "+communityDeptId+" and t2.code_entry_status = 2 and t1.risk_level = 2  "+S5+S9+")as cg,"
//				+ "(select count(distinct(t1.sid))as groupByC from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'C'  and t1.community_dept_id = "+communityDeptId+" and t2.code_entry_status = 2 and t1.risk_level = 1  "+S5+S9+")as cd,"
//				+ "(select count(distinct(t1.sid))as groupByC from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'C'  and t1.community_dept_id = "+communityDeptId+" and t2.code_entry_status = 2 and t1.risk_level is null "+S5+S9+") as cp"
                + "(SELECT count(DISTINCT(t1.sid)) " +
                " FROM " +
                "  hospital_intestine_review t1, " +
                "  hospital_fit_result t2 " +
                " WHERE t1.sid = t2.sid " +
                " AND t2.id IN ( " +
                "  SELECT max(s.id) " +
                "  FROM hospital_fit_result s " +
                "  GROUP BY s.sid " +
                " ) " +
                " AND t1.group_status = 2 " +
                " AND t1.`group` = 'A' " +
                " AND t1.community_dept_id = "+communityDeptId+
                " AND t2.code_entry_status = 2 "+S5+S9+
                ") AS A,"
                + "(SELECT count(DISTINCT(t1.sid)) " +
                " FROM " +
                "  hospital_intestine_review t1, " +
                "  hospital_fit_result t2 " +
                " WHERE t1.sid = t2.sid " +
                " AND t2.id IN ( " +
                "  SELECT max(s.id) " +
                "  FROM hospital_fit_result s " +
                "  GROUP BY s.sid " +
                " ) " +
                " AND t1.group_status = 2 " +
                " AND t1.`group` = 'B' " +
                " AND t1.community_dept_id = "+communityDeptId+
                " AND t2.code_entry_status = 2 "+S5+S9+
                ") AS B,"
                + "(SELECT count(DISTINCT(t1.sid)) " +
                " FROM " +
                "  hospital_intestine_review t1, " +
                "  hospital_fit_result t2 " +
                " WHERE t1.sid = t2.sid " +
                " AND t2.id IN ( " +
                "  SELECT max(s.id) " +
                "  FROM hospital_fit_result s " +
                "  GROUP BY s.sid " +
                " ) " +
                " AND t1.group_status = 2 " +
                " AND t1.`group` = 'C' AND t1.risk_level = 2 " +
                " AND t1.community_dept_id = "+communityDeptId+
                " AND t2.code_entry_status = 2 "+S5+S9+
                ") AS cg,"
                + "(SELECT count(DISTINCT(t1.sid)) " +
                " FROM " +
                "  hospital_intestine_review t1, " +
                "  hospital_fit_result t2 " +
                " WHERE t1.sid = t2.sid " +
                " AND t2.id IN ( " +
                "  SELECT max(s.id) " +
                "  FROM hospital_fit_result s " +
                "  GROUP BY s.sid " +
                " ) " +
                " AND t1.group_status = 2 " +
                " AND t1.`group` = 'C' AND t1.risk_level = 1 " +
                " AND t1.community_dept_id = "+communityDeptId+
                " AND t2.code_entry_status = 2 "+S5+S9+
                ") AS cd,"
                + "(SELECT count(DISTINCT(t1.sid)) " +
                " FROM " +
                "  hospital_intestine_review t1, " +
                "  hospital_fit_result t2 " +
                " WHERE t1.sid = t2.sid " +
                " AND t2.id IN ( " +
                "  SELECT max(s.id) " +
                "  FROM hospital_fit_result s " +
                "  GROUP BY s.sid " +
                " ) " +
                " AND t1.group_status = 2 " +
                " AND t1.`group` = 'C' AND t1.risk_level is null " +
                " AND t1.community_dept_id = "+communityDeptId+
                " AND t2.code_entry_status = 2 "+S5+S9+
                ") AS cp "
				+ " from dual;";
		log.info("sql==>"+sql5);
		List<Map<String,Object>> list5 = jdbcTemplate.queryForList(sql5);
		String jsons5 = list5.toString().substring(1,list5.toString().length());
		String sumdepts5 = jsons5.substring(0, jsons5.length()-1);
		rs.add(sumdepts5);


		//FIT阳性人数
/*		String sql6 = "select (select count(distinct(t1.sid))as groupByA from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'A'  and t1.community_dept_id = "+communityDeptId+" and t2.result = 2 "+S6+")as A,"
				+ "(select count(distinct(t1.sid))as groupByB from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'B'  and t1.community_dept_id = "+communityDeptId+" and t2.result = 2 "+S6+")as B,"
				+ "(select count(distinct(t1.sid))as groupByC from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'C'  and t1.community_dept_id = "+communityDeptId+" and t2.result = 2 and t1.risk_level = 2 "+S6+")as cg,"
				+ "(select count(distinct(t1.sid))as groupByC from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'C'  and t1.community_dept_id = "+communityDeptId+" and t2.result = 2 and t1.risk_level = 1 "+S6+")as cd,"
				+ "(select count(distinct(t1.sid))as groupByC from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'C'  and t1.community_dept_id = "+communityDeptId+" and t2.result = 2 and t1.risk_level is null "+S6+") as cp"
				+ " from dual;";*/

/*        SELECT count(*) from hospital_fit_result ss where ss.id in (SELECT MAX(t2.id)
        FROM hospital_intestine_review t1, hospital_fit_result t2
        WHERE t1.sid = t2.sid
        AND t1.group_status = 2
        AND t1.`group` = 'B'
        AND t1.community_dept_id = 25
        GROUP BY t2.sid)   and 	 ss.result = 2*/
		String sql6 = "select " +
//				"       (SELECT count(*) from hospital_fit_result ss where ss.id in (SELECT MAX(t2.id)    " +
//                "            FROM hospital_intestine_review t1, hospital_fit_result t2    " +
//                "            WHERE t1.sid = t2.sid    " +
//                "                AND t1.group_status = 2    " +
//                "                AND t1.`group` = 'A'    " +
//                "                AND t1.community_dept_id =     " +communityDeptId+S6+S9+
//                "            GROUP BY t2.sid)   and      ss.result = 2" +")as A,"+
//                "       (SELECT count(*) from hospital_fit_result ss where ss.id in (SELECT MAX(t2.id)    " +
//                "            FROM hospital_intestine_review t1, hospital_fit_result t2    " +
//                "            WHERE t1.sid = t2.sid    " +
//                "                AND t1.group_status = 2    " +
//                "                AND t1.`group` = 'B'    " +
//                "                AND t1.community_dept_id =     " +communityDeptId+S6+S9+
//                "            GROUP BY t2.sid)   and      ss.result = 2" +")as B,"+
//				"       (SELECT count(*) from hospital_fit_result ss where ss.id in (SELECT MAX(t2.id)    " +
//				"            FROM hospital_intestine_review t1, hospital_fit_result t2    " +
//				"            WHERE t1.sid = t2.sid    " +
//				"                AND t1.group_status = 2    " +
//				"      AND t1.`group` = 'C'   and t1.risk_level = 2  " +
//				"                AND t1.community_dept_id =     " +communityDeptId+S6+S9+
//				"            GROUP BY t2.sid)   and      ss.result = 2" +")as cg,"+
//				"       (SELECT count(*) from hospital_fit_result ss where ss.id in (SELECT MAX(t2.id)    " +
//				"            FROM hospital_intestine_review t1, hospital_fit_result t2    " +
//				"            WHERE t1.sid = t2.sid    " +
//				"                AND t1.group_status = 2    " +
//				"      AND t1.`group` = 'C'   and t1.risk_level = 1  " +
//				"                AND t1.community_dept_id =     " +communityDeptId+S6+S9+
//				"            GROUP BY t2.sid)   and      ss.result = 2" +")as cd,"+
//				"       (SELECT count(*) from hospital_fit_result ss where ss.id in (SELECT MAX(t2.id)    " +
//				"            FROM hospital_intestine_review t1, hospital_fit_result t2    " +
//				"            WHERE t1.sid = t2.sid    " +
//				"                AND t1.group_status = 2    " +
//				"      AND t1.`group` = 'C'   and t1.risk_level is null  " +
//				"                AND t1.community_dept_id =     " +communityDeptId+S6+S9+
//				"            GROUP BY t2.sid)   and      ss.result = 2" +")as cp"

                " (SELECT count(DISTINCT(t1.sid)) " +
                " FROM " +
                "  hospital_intestine_review t1, " +
                "  hospital_fit_result t2 " +
                " WHERE t1.sid = t2.sid " +
                " AND t2.id IN ( " +
                "  SELECT MAX(s.id) " +
                "  FROM hospital_fit_result s " +
                "  GROUP BY s.sid " +
                " ) " +
                " AND t1.group_status = 2 " +
                " AND t1.`group` = 'A' " +
                " AND t1.community_dept_id = " +communityDeptId+S6+S9+" " +
                " AND t2.result = 2 " +
                ") AS A,"
                + " (SELECT count(DISTINCT(t1.sid)) " +
                " FROM " +
                "  hospital_intestine_review t1, " +
                "  hospital_fit_result t2 " +
                " WHERE t1.sid = t2.sid " +
                " AND t2.id IN ( " +
                "  SELECT MAX(s.id) " +
                "  FROM hospital_fit_result s " +
                "  GROUP BY s.sid " +
                " ) " +
                " AND t1.group_status = 2 " +
                " AND t1.`group` = 'B' " +
                " AND t1.community_dept_id = " +communityDeptId+S6+S9+" " +
                " AND t2.result = 2 " +
                ") AS B,"
                + " (SELECT count(DISTINCT(t1.sid)) " +
                " FROM " +
                "  hospital_intestine_review t1, " +
                "  hospital_fit_result t2 " +
                " WHERE t1.sid = t2.sid " +
                " AND t2.id IN ( " +
                "  SELECT MAX(s.id) " +
                "  FROM hospital_fit_result s " +
                "  GROUP BY s.sid " +
                " ) " +
                " AND t1.group_status = 2 " +
                " AND t1.`group` = 'C' AND t1.risk_level = 2 " +
                " AND t1.community_dept_id = " +communityDeptId+S6+S9+" " +
                " AND t2.result = 2 " +
                ") AS cg,"
                + " (SELECT count(DISTINCT(t1.sid)) " +
                " FROM " +
                "  hospital_intestine_review t1, " +
                "  hospital_fit_result t2 " +
                " WHERE t1.sid = t2.sid " +
                " AND t2.id IN ( " +
                "  SELECT MAX(s.id) " +
                "  FROM hospital_fit_result s " +
                "  GROUP BY s.sid " +
                " ) " +
                " AND t1.group_status = 2 " +
                " AND t1.`group` = 'C' AND t1.risk_level = 1 " +
                " AND t1.community_dept_id = " +communityDeptId+S6+S9+" " +
                " AND t2.result = 2 " +
                ") AS cd,"
                + " (SELECT count(DISTINCT(t1.sid)) " +
                " FROM " +
                "  hospital_intestine_review t1, " +
                "  hospital_fit_result t2 " +
                " WHERE t1.sid = t2.sid " +
                " AND t2.id IN ( " +
                "  SELECT MAX(s.id) " +
                "  FROM hospital_fit_result s " +
                "  GROUP BY s.sid " +
                " ) " +
                " AND t1.group_status = 2 " +
                " AND t1.`group` = 'C' AND t1.risk_level is null " +
                " AND t1.community_dept_id = " +communityDeptId+S6+S9+" " +
                " AND t2.result = 2 " +
                ") AS cp "
				//+ "(select count(distinct(t1.sid))as groupByB from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'B'  and t1.community_dept_id = "+communityDeptId+" and t2.result = 2 "+S6+")as B,"
				//+ "(select count(distinct(t1.sid))as groupByC from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'C'  and t1.community_dept_id = "+communityDeptId+" and t2.result = 2 and t1.risk_level = 2 "+S6+")as cg,"
				//+ "(select count(distinct(t1.sid))as groupByC from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'C'  and t1.community_dept_id = "+communityDeptId+" and t2.result = 2 and t1.risk_level = 1 "+S6+")as cd,"
				//+ "(select count(distinct(t1.sid))as groupByC from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'C'  and t1.community_dept_id = "+communityDeptId+" and t2.result = 2 and t1.risk_level is null "+S6+") as cp"
				+ " from dual;";
		log.info("sql==>"+sql6);
		List<Map<String,Object>> list6 = jdbcTemplate.queryForList(sql6);
		String jsons6 = list6.toString().substring(1,list6.toString().length());
		String sumdepts6 = jsons6.substring(0, jsons6.length()-1);
		rs.add(sumdepts6);
		String sql7 = "select " +
//				"       (SELECT count(*) from hospital_fit_result ss where ss.id in (SELECT MAX(t2.id)    " +
//				"            FROM hospital_intestine_review t1, hospital_fit_result t2    " +
//				"            WHERE t1.sid = t2.sid    " +
//				"                AND t1.group_status = 2    " +
//				"                AND t1.`group` = 'A'    " +
//				"                AND t1.community_dept_id =     " +communityDeptId+S6+S9+
//				"            GROUP BY t2.sid)   and      ss.result = 1" +")as A,"+
//				"       (SELECT count(*) from hospital_fit_result ss where ss.id in (SELECT MAX(t2.id)    " +
//				"            FROM hospital_intestine_review t1, hospital_fit_result t2    " +
//				"            WHERE t1.sid = t2.sid    " +
//				"                AND t1.group_status = 2    " +
//				"                AND t1.`group` = 'B'    " +
//				"                AND t1.community_dept_id =     " +communityDeptId+S6+S9+
//				"            GROUP BY t2.sid)   and      ss.result = 1" +")as B,"+
//				"       (SELECT count(*) from hospital_fit_result ss where ss.id in (SELECT MAX(t2.id)    " +
//				"            FROM hospital_intestine_review t1, hospital_fit_result t2    " +
//				"            WHERE t1.sid = t2.sid    " +
//				"                AND t1.group_status = 2    " +
//				"      AND t1.`group` = 'C'   and t1.risk_level = 2  " +
//				"                AND t1.community_dept_id =     " +communityDeptId+S6+S9+
//				"            GROUP BY t2.sid)   and      ss.result = 1" +")as cg,"+
//				"       (SELECT count(*) from hospital_fit_result ss where ss.id in (SELECT MAX(t2.id)    " +
//				"            FROM hospital_intestine_review t1, hospital_fit_result t2    " +
//				"            WHERE t1.sid = t2.sid    " +
//				"                AND t1.group_status = 2    " +
//				"      AND t1.`group` = 'C'   and t1.risk_level = 1  " +
//				"                AND t1.community_dept_id =     " +communityDeptId+S6+S9+
//				"            GROUP BY t2.sid)   and      ss.result = 1" +")as cd,"+
//				"       (SELECT count(*) from hospital_fit_result ss where ss.id in (SELECT MAX(t2.id)    " +
//				"            FROM hospital_intestine_review t1, hospital_fit_result t2    " +
//				"            WHERE t1.sid = t2.sid    " +
//				"                AND t1.group_status = 2    " +
//				"      AND t1.`group` = 'C'   and t1.risk_level is null  " +
//				"                AND t1.community_dept_id =     " +communityDeptId+S6+S9+
//				"            GROUP BY t2.sid)   and      ss.result = 1" +")as cp"

                " (SELECT count(DISTINCT(t1.sid)) " +
                " FROM " +
                "  hospital_intestine_review t1, " +
                "  hospital_fit_result t2 " +
                " WHERE t1.sid = t2.sid " +
                " AND t2.id IN ( " +
                "  SELECT MAX(s.id) " +
                "  FROM hospital_fit_result s " +
                "  GROUP BY s.sid " +
                " ) " +
                " AND t1.group_status = 2 " +
                " AND t1.`group` = 'A' " +
                " AND t1.community_dept_id = " +communityDeptId+S6+S9+" " +
                " AND t2.result = 1 " +
                ") AS A,"
                + " (SELECT count(DISTINCT(t1.sid)) " +
                " FROM " +
                "  hospital_intestine_review t1, " +
                "  hospital_fit_result t2 " +
                " WHERE t1.sid = t2.sid " +
                " AND t2.id IN ( " +
                "  SELECT MAX(s.id) " +
                "  FROM hospital_fit_result s " +
                "  GROUP BY s.sid " +
                " ) " +
                " AND t1.group_status = 2 " +
                " AND t1.`group` = 'B' " +
                " AND t1.community_dept_id = " +communityDeptId+S6+S9+" " +
                " AND t2.result = 1 " +
                ") AS B,"
                + " (SELECT count(DISTINCT(t1.sid)) " +
                " FROM " +
                "  hospital_intestine_review t1, " +
                "  hospital_fit_result t2 " +
                " WHERE t1.sid = t2.sid " +
                " AND t2.id IN ( " +
                "  SELECT MAX(s.id) " +
                "  FROM hospital_fit_result s " +
                "  GROUP BY s.sid " +
                " ) " +
                " AND t1.group_status = 2 " +
                " AND t1.`group` = 'C' AND t1.risk_level = 2 " +
                " AND t1.community_dept_id = " +communityDeptId+S6+S9+" " +
                " AND t2.result = 1 " +
                ") AS cg,"
                + " (SELECT count(DISTINCT(t1.sid)) " +
                " FROM " +
                "  hospital_intestine_review t1, " +
                "  hospital_fit_result t2 " +
                " WHERE t1.sid = t2.sid " +
                " AND t2.id IN ( " +
                "  SELECT MAX(s.id) " +
                "  FROM hospital_fit_result s " +
                "  GROUP BY s.sid " +
                " ) " +
                " AND t1.group_status = 2 " +
                " AND t1.`group` = 'C' AND t1.risk_level = 1 " +
                " AND t1.community_dept_id = " +communityDeptId+S6+S9+" " +
                " AND t2.result = 1 " +
                ") AS cd,"
                + " (SELECT count(DISTINCT(t1.sid)) " +
                " FROM " +
                "  hospital_intestine_review t1, " +
                "  hospital_fit_result t2 " +
                " WHERE t1.sid = t2.sid " +
                " AND t2.id IN ( " +
                "  SELECT MAX(s.id) " +
                "  FROM hospital_fit_result s " +
                "  GROUP BY s.sid " +
                " ) " +
                " AND t1.group_status = 2 " +
                " AND t1.`group` = 'C' AND t1.risk_level is null " +
                " AND t1.community_dept_id = " +communityDeptId+S6+S9+" " +
                " AND t2.result = 1 " +
                ") AS cp "
				//+ "(select count(distinct(t1.sid))as groupByB from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'B'  and t1.community_dept_id = "+communityDeptId+" and t2.result = 2 "+S6+")as B,"
				//+ "(select count(distinct(t1.sid))as groupByC from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'C'  and t1.community_dept_id = "+communityDeptId+" and t2.result = 2 and t1.risk_level = 2 "+S6+")as cg,"
				//+ "(select count(distinct(t1.sid))as groupByC from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'C'  and t1.community_dept_id = "+communityDeptId+" and t2.result = 2 and t1.risk_level = 1 "+S6+")as cd,"
				//+ "(select count(distinct(t1.sid))as groupByC from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'C'  and t1.community_dept_id = "+communityDeptId+" and t2.result = 2 and t1.risk_level is null "+S6+") as cp"
				+ " from dual;";
		//FIT阴性人数
/*		String sql7 = "select (select count(distinct(t1.sid))as groupByA from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'A'  and t1.community_dept_id = "+communityDeptId+" and t2.result = 1 "+S7+")as A,"
				+ "(select count(distinct(t1.sid))as groupByB from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'B'  and t1.community_dept_id = "+communityDeptId+" and t2.result = 1 "+S7+")as B,"
				+ "(select count(distinct(t1.sid))as groupByC from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'C'  and t1.community_dept_id = "+communityDeptId+" and t2.result = 1 and t1.risk_level = 2 "+S7+")as cg,"
				+ "(select count(distinct(t1.sid))as groupByC from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'C'  and t1.community_dept_id = "+communityDeptId+" and t2.result = 1 and t1.risk_level = 1 "+S7+")as cd,"
				+ "(select count(distinct(t1.sid))as groupByC from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'C'  and t1.community_dept_id = "+communityDeptId+" and t2.result = 1 and t1.risk_level is null "+S7+") as cp"
				+ " from dual;";*/

		log.info("sql==>"+sql7);
		List<Map<String,Object>> list7 = jdbcTemplate.queryForList(sql7);
		String jsons7 = list7.toString().substring(1,list7.toString().length());
		String sumdepts7 = jsons7.substring(0, jsons7.length()-1);
		rs.add(sumdepts7);
		//社区退出总人数
		String sql8 = "select "
//                + "(select count(distinct(t1.sid))as groupByA from hospital_intestine_review t1,hospital_intestine_review_quit_log t2 where t1.sid = t2.sid and t1.`group` = 'A' and t1.community_dept_id = "+communityDeptId+" and t1.overall_status_cy = 2 "+S8+S9+")as A,"
//				+ "(select count(distinct(t1.sid))as groupByB from hospital_intestine_review t1,hospital_intestine_review_quit_log t2 where t1.sid = t2.sid and t1.`group` = 'B' and t1.community_dept_id = "+communityDeptId+" and t1.overall_status_cy = 2 "+S8+S9+")as B,"
//				+ "(select count(distinct(t1.sid))as groupByC from hospital_intestine_review t1,hospital_intestine_review_quit_log t2 where t1.sid = t2.sid and t1.`group` = 'C'  and t1.community_dept_id = "+communityDeptId+" and t1.overall_status_cy = 2 and t1.risk_level = 2 "+S8+S9+")as cg,"
//				+ "(select count(distinct(t1.sid))as groupByC from hospital_intestine_review t1,hospital_intestine_review_quit_log t2 where t1.sid = t2.sid and t1.`group` = 'C'  and t1.community_dept_id = "+communityDeptId+" and t1.overall_status_cy = 2 and t1.risk_level = 1 "+S8+S9+")as cd,"
//				+ "(select count(distinct(t1.sid))as groupByC from hospital_intestine_review t1,hospital_intestine_review_quit_log t2 where t1.sid = t2.sid and t1.`group` = 'C'  and t1.community_dept_id = "+communityDeptId+" and t1.overall_status_cy = 2 and t1.risk_level is null "+S8+S9+")as cp"

                + " (SELECT count(DISTINCT(t1.sid))" +
                " FROM " +
                "  hospital_intestine_review t1, " +
                "  hospital_intestine_review_quit_log t2 " +
                " WHERE t1.sid = t2.sid " +
                " AND t2.id IN ( " +
                "  SELECT max(s.id) " +
                "  FROM hospital_intestine_review_quit_log s " +
                "  GROUP BY s.sid " +
                " ) " +
                " AND t1.`group` = 'A' " +
                " AND t1.community_dept_id = "+communityDeptId+
                " AND t1.overall_status_cy = 2 "+S8+S9+
                ") AS A, "
                + " (SELECT count(DISTINCT(t1.sid))" +
                " FROM " +
                "  hospital_intestine_review t1, " +
                "  hospital_intestine_review_quit_log t2 " +
                " WHERE t1.sid = t2.sid " +
                " AND t2.id IN ( " +
                "  SELECT max(s.id) " +
                "  FROM hospital_intestine_review_quit_log s " +
                "  GROUP BY s.sid " +
                " ) " +
                " AND t1.`group` = 'B' " +
                " AND t1.community_dept_id = "+communityDeptId+
                " AND t1.overall_status_cy = 2 "+S8+S9+
                ") AS B, "
                + " (SELECT count(DISTINCT(t1.sid))" +
                " FROM " +
                "  hospital_intestine_review t1, " +
                "  hospital_intestine_review_quit_log t2 " +
                " WHERE t1.sid = t2.sid " +
                " AND t2.id IN ( " +
                "  SELECT max(s.id) " +
                "  FROM hospital_intestine_review_quit_log s " +
                "  GROUP BY s.sid " +
                " ) " +
                " AND t1.`group` = 'C' AND t1.risk_level = 2 " +
                " AND t1.community_dept_id = "+communityDeptId+
                " AND t1.overall_status_cy = 2 "+S8+S9+
                ") AS cg, "
                + " (SELECT count(DISTINCT(t1.sid))" +
                " FROM " +
                "  hospital_intestine_review t1, " +
                "  hospital_intestine_review_quit_log t2 " +
                " WHERE t1.sid = t2.sid " +
                " AND t2.id IN ( " +
                "  SELECT max(s.id) " +
                "  FROM hospital_intestine_review_quit_log s " +
                "  GROUP BY s.sid " +
                " ) " +
                " AND t1.`group` = 'C' AND t1.risk_level = 1 " +
                " AND t1.community_dept_id = "+communityDeptId+
                " AND t1.overall_status_cy = 2 "+S8+S9+
                ") AS cd, "
                + " (SELECT count(DISTINCT(t1.sid))" +
                " FROM " +
                "  hospital_intestine_review t1, " +
                "  hospital_intestine_review_quit_log t2 " +
                " WHERE t1.sid = t2.sid " +
                " AND t2.id IN ( " +
                "  SELECT max(s.id) " +
                "  FROM hospital_intestine_review_quit_log s " +
                "  GROUP BY s.sid " +
                " ) " +
                " AND t1.`group` = 'C' AND t1.risk_level is null " +
                " AND t1.community_dept_id = "+communityDeptId+
                " AND t1.overall_status_cy = 2 "+S8+S9+
                ") AS cp "
				+ " from dual;";
		log.info("sql==>"+sql8);
		List<Map<String, Object>> list8 = jdbcTemplate.queryForList(sql8);
		String jsons8 = list8.toString().substring(1,list8.toString().length());
		String sumdepts8 = jsons8.substring(0, jsons8.length()-1);
		rs.add(sumdepts8);
		//粪便DNA检查已返回人数
		String sql9 = "select " +
//				"      (SELECT count(*) from hospital_stool_dna ss where ss.id in (SELECT MAX(t2.id)    " +
//				"            FROM hospital_intestine_review t1, hospital_stool_dna t2    " +
//				"            WHERE t1.sid = t2.sid    " +
//				"                AND t1.group_status = 2    " +
//				"                AND t1.`group` = 'A'    " +
//				"                AND t1.community_dept_id =     " +communityDeptId+S10+S9+
//				"            GROUP BY t2.sid)   and      ss.dna_check_inform_status = 2" +")as A,"+
//				"       (SELECT count(*) from hospital_stool_dna ss where ss.id in (SELECT MAX(t2.id)    " +
//				"            FROM hospital_intestine_review t1, hospital_stool_dna t2    " +
//				"            WHERE t1.sid = t2.sid    " +
//				"                AND t1.group_status = 2    " +
//				"                AND t1.`group` = 'B'    " +
//				"                AND t1.community_dept_id =     " +communityDeptId+S10+S9+
//				"            GROUP BY t2.sid)   and      ss.dna_check_inform_status = 2" +")as B,"+
//				"       (SELECT count(*) from hospital_stool_dna ss where ss.id in (SELECT MAX(t2.id)    " +
//				"            FROM hospital_intestine_review t1, hospital_stool_dna t2    " +
//				"            WHERE t1.sid = t2.sid    " +
//				"                AND t1.group_status = 2    " +
//				"      AND t1.`group` = 'C'   and t1.risk_level = 2  " +
//				"                AND t1.community_dept_id =     " +communityDeptId+S10+S9+
//				"            GROUP BY t2.sid)   and      ss.dna_check_inform_status = 2" +")as cg,"+
//				"       (SELECT count(*) from hospital_stool_dna ss where ss.id in (SELECT MAX(t2.id)    " +
//				"            FROM hospital_intestine_review t1, hospital_stool_dna t2    " +
//				"            WHERE t1.sid = t2.sid    " +
//				"                AND t1.group_status = 2    " +
//				"      AND t1.`group` = 'C'   and t1.risk_level = 1  " +
//				"                AND t1.community_dept_id =     " +communityDeptId+S10+S9+
//				"            GROUP BY t2.sid)   and      ss.dna_check_inform_status = 2" +")as cd,"+
//				"       (SELECT count(*) from hospital_stool_dna ss where ss.id in (SELECT MAX(t2.id)    " +
//				"            FROM hospital_intestine_review t1, hospital_stool_dna t2    " +
//				"            WHERE t1.sid = t2.sid    " +
//				"                AND t1.group_status = 2    " +
//				"      AND t1.`group` = 'C'   and t1.risk_level is null  " +
//				"                AND t1.community_dept_id =     " +communityDeptId+S10+S9+
//				"            GROUP BY t2.sid)   and      ss.dna_check_inform_status = 2" +")as cp"

                " (SELECT count(*) " +
                " FROM " +
                "  hospital_intestine_review t1, " +
                "  hospital_stool_dna t2 " +
                " WHERE t1.sid = t2.sid " +
                " AND t2.id IN ( " +
                "  SELECT MAX(s.id) " +
                "  FROM hospital_stool_dna s " +
                "  GROUP BY s.sid " +
                " ) " +
                " AND t1.group_status = 2 " +
                " AND t1.`group` = 'A' " +
                " AND t1.community_dept_id = " +communityDeptId+S10+S9+
                " AND t2.dna_check_inform_status = 2 " +
                ") AS A, "
                + " (SELECT count(*) " +
                " FROM " +
                "  hospital_intestine_review t1, " +
                "  hospital_stool_dna t2 " +
                " WHERE t1.sid = t2.sid " +
                " AND t2.id IN ( " +
                "  SELECT MAX(s.id) " +
                "  FROM hospital_stool_dna s " +
                "  GROUP BY s.sid " +
                " ) " +
                " AND t1.group_status = 2 " +
                " AND t1.`group` = 'B' " +
                " AND t1.community_dept_id = " +communityDeptId+S10+S9+
                " AND t2.dna_check_inform_status = 2 " +
                ") AS B, "
                + " (SELECT count(*) " +
                " FROM " +
                "  hospital_intestine_review t1, " +
                "  hospital_stool_dna t2 " +
                " WHERE t1.sid = t2.sid " +
                " AND t2.id IN ( " +
                "  SELECT MAX(s.id) " +
                "  FROM hospital_stool_dna s " +
                "  GROUP BY s.sid " +
                " ) " +
                " AND t1.group_status = 2 " +
                " AND t1.`group` = 'C' AND t1.risk_level = 2 " +
                " AND t1.community_dept_id = " +communityDeptId+S10+S9+
                " AND t2.dna_check_inform_status = 2 " +
                ") AS cg, "
                + " (SELECT count(*) " +
                " FROM " +
                "  hospital_intestine_review t1, " +
                "  hospital_stool_dna t2 " +
                " WHERE t1.sid = t2.sid " +
                " AND t2.id IN ( " +
                "  SELECT MAX(s.id) " +
                "  FROM hospital_stool_dna s " +
                "  GROUP BY s.sid " +
                " ) " +
                " AND t1.group_status = 2 " +
                " AND t1.`group` = 'C' AND t1.risk_level = 1 " +
                " AND t1.community_dept_id = " +communityDeptId+S10+S9+
                " AND t2.dna_check_inform_status = 2 " +
                ") AS cd, "
                + " (SELECT count(*) " +
                " FROM " +
                "  hospital_intestine_review t1, " +
                "  hospital_stool_dna t2 " +
                " WHERE t1.sid = t2.sid " +
                " AND t2.id IN ( " +
                "  SELECT MAX(s.id) " +
                "  FROM hospital_stool_dna s " +
                "  GROUP BY s.sid " +
                " ) " +
                " AND t1.group_status = 2 " +
                " AND t1.`group` = 'C' AND t1.risk_level is null " +
                " AND t1.community_dept_id = " +communityDeptId+S10+S9+
                " AND t2.dna_check_inform_status = 2 " +
                ") AS cp "
				+ " from dual;";
		log.info("sql==>"+sql9);
		List<Map<String, Object>> list9 = jdbcTemplate.queryForList(sql9);
		String jsons9 = list9.toString().substring(1,list9.toString().length());
		String sumdepts9 = jsons9.substring(0, jsons9.length()-1);
		rs.add(sumdepts9);
		//FIT结果无效人数
		String sql10 = "select " +
//				"      (SELECT count(*) from hospital_fit_result ss where ss.id in (SELECT MAX(t2.id)    " +
//				"            FROM hospital_intestine_review t1, hospital_fit_result t2    " +
//				"            WHERE t1.sid = t2.sid    " +
//				"                AND t1.group_status = 2    " +
//				"                AND t1.`group` = 'A'    " +
//				"                AND t1.community_dept_id =     " +communityDeptId+S6+S9+
//				"            GROUP BY t2.sid)   and      ss.result = 3" +")as A,"+
//				"       (SELECT count(*) from hospital_fit_result ss where ss.id in (SELECT MAX(t2.id)    " +
//				"            FROM hospital_intestine_review t1, hospital_fit_result t2    " +
//				"            WHERE t1.sid = t2.sid    " +
//				"                AND t1.group_status = 2    " +
//				"                AND t1.`group` = 'B'    " +
//				"                AND t1.community_dept_id =     " +communityDeptId+S6+S9+
//				"            GROUP BY t2.sid)   and      ss.result = 3" +")as B,"+
//				"       (SELECT count(*) from hospital_fit_result ss where ss.id in (SELECT MAX(t2.id)    " +
//				"            FROM hospital_intestine_review t1, hospital_fit_result t2    " +
//				"            WHERE t1.sid = t2.sid    " +
//				"                AND t1.group_status = 2    " +
//				"      AND t1.`group` = 'C'   and t1.risk_level = 2  " +
//				"                AND t1.community_dept_id =     " +communityDeptId+S6+S9+
//				"            GROUP BY t2.sid)   and      ss.result = 3" +")as cg,"+
//				"       (SELECT count(*) from hospital_fit_result ss where ss.id in (SELECT MAX(t2.id)    " +
//				"            FROM hospital_intestine_review t1, hospital_fit_result t2    " +
//				"            WHERE t1.sid = t2.sid    " +
//				"                AND t1.group_status = 2    " +
//				"      AND t1.`group` = 'C'   and t1.risk_level = 1  " +
//				"                AND t1.community_dept_id =     " +communityDeptId+S6+S9+
//				"            GROUP BY t2.sid)   and      ss.result = 3" +")as cd,"+
//				"       (SELECT count(*) from hospital_fit_result ss where ss.id in (SELECT MAX(t2.id)    " +
//				"            FROM hospital_intestine_review t1, hospital_fit_result t2    " +
//				"            WHERE t1.sid = t2.sid    " +
//				"                AND t1.group_status = 2    " +
//				"      AND t1.`group` = 'C'   and t1.risk_level is null  " +
//				"                AND t1.community_dept_id =     " +communityDeptId+S6+S9+
//				"            GROUP BY t2.sid)   and      ss.result = 1" +")as cp"

                " (SELECT count(DISTINCT(t1.sid)) " +
                " FROM " +
                "  hospital_intestine_review t1, " +
                "  hospital_fit_result t2 " +
                " WHERE t1.sid = t2.sid " +
                " AND t2.id IN ( " +
                "  SELECT MAX(s.id) " +
                "  FROM hospital_fit_result s " +
                "  GROUP BY s.sid " +
                " ) " +
                " AND t1.group_status = 2 " +
                " AND t1.`group` = 'A' " +
                " AND t1.community_dept_id = " +communityDeptId+S6+S9+" " +
                " AND t2.result = 3 " +
                ") AS A,"
                + " (SELECT count(DISTINCT(t1.sid)) " +
                " FROM " +
                "  hospital_intestine_review t1, " +
                "  hospital_fit_result t2 " +
                " WHERE t1.sid = t2.sid " +
                " AND t2.id IN ( " +
                "  SELECT MAX(s.id) " +
                "  FROM hospital_fit_result s " +
                "  GROUP BY s.sid " +
                " ) " +
                " AND t1.group_status = 2 " +
                " AND t1.`group` = 'B' " +
                " AND t1.community_dept_id = " +communityDeptId+S6+S9+" " +
                " AND t2.result = 3 " +
                ") AS B,"
                + " (SELECT count(DISTINCT(t1.sid)) " +
                " FROM " +
                "  hospital_intestine_review t1, " +
                "  hospital_fit_result t2 " +
                " WHERE t1.sid = t2.sid " +
                " AND t2.id IN ( " +
                "  SELECT MAX(s.id) " +
                "  FROM hospital_fit_result s " +
                "  GROUP BY s.sid " +
                " ) " +
                " AND t1.group_status = 2 " +
                " AND t1.`group` = 'C' AND t1.risk_level = 2 " +
                " AND t1.community_dept_id = " +communityDeptId+S6+S9+" " +
                " AND t2.result = 3 " +
                ") AS cg,"
                + " (SELECT count(DISTINCT(t1.sid)) " +
                " FROM " +
                "  hospital_intestine_review t1, " +
                "  hospital_fit_result t2 " +
                " WHERE t1.sid = t2.sid " +
                " AND t2.id IN ( " +
                "  SELECT MAX(s.id) " +
                "  FROM hospital_fit_result s " +
                "  GROUP BY s.sid " +
                " ) " +
                " AND t1.group_status = 2 " +
                " AND t1.`group` = 'C' AND t1.risk_level = 1 " +
                " AND t1.community_dept_id = " +communityDeptId+S6+S9+" " +
                " AND t2.result = 3 " +
                ") AS cd,"
                + " (SELECT count(DISTINCT(t1.sid)) " +
                " FROM " +
                "  hospital_intestine_review t1, " +
                "  hospital_fit_result t2 " +
                " WHERE t1.sid = t2.sid " +
                " AND t2.id IN ( " +
                "  SELECT MAX(s.id) " +
                "  FROM hospital_fit_result s " +
                "  GROUP BY s.sid " +
                " ) " +
                " AND t1.group_status = 2 " +
                " AND t1.`group` = 'C' AND t1.risk_level is null " +
                " AND t1.community_dept_id = " +communityDeptId+S6+S9+" " +
                " AND t2.result = 3 " +
                ") AS cp "
				+ " from dual;";
		log.info("sql==>"+sql10);
		List<Map<String, Object>> list10 = jdbcTemplate.queryForList(sql10);
		String jsons10 = list10.toString().substring(1,list10.toString().length());
		String sumdepts10 = jsons10.substring(0, jsons10.length()-1);
		rs.add(sumdepts10);

		//FIT已返回数
        String sql11 = "select " +
                " (SELECT count(DISTINCT(t1.sid)) " +
                " FROM " +
                "  hospital_intestine_review t1, " +
                "  hospital_fit_result t2 " +
                " WHERE t1.sid = t2.sid " +
                " AND t2.id IN ( " +
                "  SELECT MAX(s.id) " +
                "  FROM hospital_fit_result s " +
                "  GROUP BY s.sid " +
                " ) " +
                " AND t1.group_status = 2 " +
                " AND t1.`group` = 'A' " +
                " AND t1.community_dept_id = " +communityDeptId+S6+S9+" " +
                " AND t2.result in (1,2,3) " +
                ") AS A,"
                + " (SELECT count(DISTINCT(t1.sid)) " +
                " FROM " +
                "  hospital_intestine_review t1, " +
                "  hospital_fit_result t2 " +
                " WHERE t1.sid = t2.sid " +
                " AND t2.id IN ( " +
                "  SELECT MAX(s.id) " +
                "  FROM hospital_fit_result s " +
                "  GROUP BY s.sid " +
                " ) " +
                " AND t1.group_status = 2 " +
                " AND t1.`group` = 'B' " +
                " AND t1.community_dept_id = " +communityDeptId+S6+S9+" " +
                " AND t2.result  in (1,2,3) " +
                ") AS B,"
                + " (SELECT count(DISTINCT(t1.sid)) " +
                " FROM " +
                "  hospital_intestine_review t1, " +
                "  hospital_fit_result t2 " +
                " WHERE t1.sid = t2.sid " +
                " AND t2.id IN ( " +
                "  SELECT MAX(s.id) " +
                "  FROM hospital_fit_result s " +
                "  GROUP BY s.sid " +
                " ) " +
                " AND t1.group_status = 2 " +
                " AND t1.`group` = 'C' AND t1.risk_level = 2 " +
                " AND t1.community_dept_id = " +communityDeptId+S6+S9+" " +
                " AND t2.result  in (1,2,3) " +
                ") AS cg,"
                + " (SELECT count(DISTINCT(t1.sid)) " +
                " FROM " +
                "  hospital_intestine_review t1, " +
                "  hospital_fit_result t2 " +
                " WHERE t1.sid = t2.sid " +
                " AND t2.id IN ( " +
                "  SELECT MAX(s.id) " +
                "  FROM hospital_fit_result s " +
                "  GROUP BY s.sid " +
                " ) " +
                " AND t1.group_status = 2 " +
                " AND t1.`group` = 'C' AND t1.risk_level = 1 " +
                " AND t1.community_dept_id = " +communityDeptId+S6+S9+" " +
                " AND t2.result  in (1,2,3) " +
                ") AS cd,"
                + " (SELECT count(DISTINCT(t1.sid)) " +
                " FROM " +
                "  hospital_intestine_review t1, " +
                "  hospital_fit_result t2 " +
                " WHERE t1.sid = t2.sid " +
                " AND t2.id IN ( " +
                "  SELECT MAX(s.id) " +
                "  FROM hospital_fit_result s " +
                "  GROUP BY s.sid " +
                " ) " +
                " AND t1.group_status = 2 " +
                " AND t1.`group` = 'C' AND t1.risk_level is null " +
                " AND t1.community_dept_id = " +communityDeptId+S6+S9+" " +
                " AND t2.result  in (1,2,3) " +
                ") AS cp "
                + " from dual;";
        log.info("sql==>"+sql11);
        List<Map<String, Object>> list11 = jdbcTemplate.queryForList(sql11);
        String jsons11 = list11.toString().substring(1,list11.toString().length());
        String sumdepts11 = jsons11.substring(0, jsons11.length()-1);
        rs.add(sumdepts11);

        log.info("@Dao getGroupByAcounts query  End ");
		return rs;
	}
	/**
	 * 根据市区id查询所对应的社区所入组的人数
	 */
	@Override
	public List getGroupByAreaId(int areaId, TodoVo vo) throws DaoException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> communits = hcrAllocationDao.getcommdeptsByAreaId(areaId);
		List<Map<String,Object>> amp = new ArrayList<Map<String,Object>>();
		String sql1 = "";
		String sql2 = "";
		String sql3 = "";
		String sql4 = "";
		String sql5 = "";
		String sql6 = "";
		String sql7 = "";
		String sql8 = "";
		String sql9 = "";	//粪便DNA检查已返回
		String sql10 = "";	//FIT结果无效
		if(!StringUtil.isEmpty(vo.getStartDate()) && !StringUtil.isEmpty(vo.getEndDate())){
			sql1 = " and date_format(in_group_date,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(in_group_date,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
			sql2 = " and date_format(t2.reserve_status_date,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.reserve_status_date,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
			sql3 = " and date_format(t2.examination_check_date,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.examination_check_date,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
			sql4 = " and date_format(t2.entry_time,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.entry_time,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
			sql5 = " and date_format(t2.code_entry_time,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.code_entry_time,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
			sql6 = " and date_format(t2.result_sys_date,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.result_sys_date,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
			sql7 = " and date_format(t2.result_sys_date,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.result_sys_date,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
			sql8 = " and date_format(t2.quit_date,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.quit_date,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
			sql9 = " and date_format(t2.dna_check_inform_nation_date,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.dna_check_inform_nation_date,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
			sql10 = " and date_format(t2.result_sys_date,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.result_sys_date,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
		}
		//根据市区id查询入组人数
		//第一次循环A
		for (Map<String, Object> map : communits) {
			int sumByA1 = 0;
			int sumByA2 = 0;
			int sumByA3 = 0;
			int sumByA4 = 0;
			int sumByA5 = 0;
			int sumByA6 = 0;
			int sumByA7 = 0;
			int sumByA8 = 0;
			int sumByA9 = 0; 	//A组粪便DNA检查已反馈
			int sumByA10 = 0;	//A组FIT无效数
			int sumByA11 = 0;	//A组FIT结果返回数 = 阳性+阴性+无效
			int sumByB1 = 0;
			int sumByB2 = 0;
			int sumByB3 = 0;
			int sumByB4 = 0;
			int sumByB5 = 0;
			int sumByB6 = 0;
			int sumByB7 = 0;
			int sumByB8 = 0;
			int sumByB9 = 0; 	//B组粪便DNA检查已反馈
			int sumByB10 = 0;	//B组FIT无效数
			int sumByB11 = 0;	//B组FIT结果返回数 = 阳性+阴性+无效
			int sumByCg1 = 0;
			int sumByCg2 = 0;
			int sumByCg3 = 0;
			int sumByCg4 = 0;
			int sumByCg5 = 0;
			int sumByCg6 = 0;
			int sumByCg7 = 0;
			int sumByCg8 = 0;
			int sumByCg9 = 0;	//C组高危粪便DNA检查已反馈
			int sumByCg10 = 0;	//C组高危FIT无效数
			int sumByCg11 = 0;	//C组高危FIT结果返回数 = 阳性+阴性+无效
			int sumByCd1 = 0;
			int sumByCd2 = 0;
			int sumByCd3 = 0;
			int sumByCd4 = 0;
			int sumByCd5 = 0;
			int sumByCd6 = 0;
			int sumByCd7 = 0;
			int sumByCd8 = 0;
			int sumByCd9 = 0;	//C组低危粪便DNA检查已反馈
			int sumByCd10 = 0;	//C组低危FIT无效数
			int sumByCd11 = 0;	//C组低危FIT结果返回数 = 阳性+阴性+无效
			int sumByCp1 = 0;
			int sumByCp2 = 0;
			int sumByCp3 = 0;
			int sumByCp4 = 0;
			int sumByCp5 = 0;
			int sumByCp6 = 0;
			int sumByCp7 = 0;
			int sumByCp8 = 0;
			int sumByCp9 = 0;	//C组未评估粪便DNA检查已反馈
			int sumByCp10 = 0;	//C组未评估FIT无效数
			int sumByCp11 = 0;	//C组未评估FIT结果返回数 = 阳性+阴性+无效
			List<String> rs = new ArrayList<>();
			List<Map<String,Integer>> sumsByGroup = new ArrayList<Map<String,Integer>>();
			Map<String, Object> arrs = new LinkedHashMap<String,Object>();
			Map<String,Integer> ams = new LinkedHashMap<String,Integer>();
			int communityDeptId = Integer.parseInt(map.get("id").toString()) ;
			String commityName = map.get("commdeptName").toString();
			log.info("@Dao getGroupByAreaId query  Start ");
			//第一个A
			String msq1 = "select"
					+ " (select count(distinct(sid)) as groupByA from hospital_intestine_review where group_status = 2 and `group` = 'A' and community_dept_id = "+communityDeptId+" "+sql1+" ) as groupsNum ,"
                    //已预约肠镜检查人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_colonoscopy_record t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_colonoscopy_record ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'A' " +
                    " AND t1.community_dept_id = "+communityDeptId+
                    " AND t2.reserve_status = 2 "+sql2+
                    " ) AS bookingColonscopyNum,"

                    //实际接受肠镜检查人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_colonoscopy_record t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_colonoscopy_record ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'A' " +
                    " AND t1.community_dept_id = "+communityDeptId+
                    " AND t2.examination_status = 2 "+sql3+
                    ") AS inspectColonscopyNum,"

                    //接受粪便DNA检测人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_stool_dna t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT max(ss.id) " +
                    "  FROM hospital_stool_dna ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'A' " +
                    " AND t1.community_dept_id = "+communityDeptId+
                    " AND t2.code_entry_status = 2 "+sql4+
                    ") AS DNANum,"

                    //实际接受FIT检查人数
                    + " (SELECT count(DISTINCT(t1.sid))" +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT max(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'A' " +
                    " AND t1.community_dept_id = "+communityDeptId+
                    " AND t2.code_entry_status = 2 "+sql5+
                    ") AS FitNum,"

                    //FIT阳性人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'A' " +
                    " AND t1.community_dept_id = " +communityDeptId+sql6+
                    " AND t2.result = 2 " +
                    ") AS FitPositiveNum,"

                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'A' " +
                    " AND t1.community_dept_id = " +communityDeptId+sql7+
                    " AND t2.result = 1" +
                    ")as FitNegativeNum,"
					//+ " (select count(distinct(t1.sid))as groupByA from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'A'  and t1.community_dept_id = "+communityDeptId+" and t2.result = 1 "+sql7+")as FitNegativeNum,"

                    //退出研究
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_intestine_review_quit_log t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT max(ss.id) " +
                    "  FROM hospital_intestine_review_quit_log ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'A' " +
                    " AND t1.community_dept_id = "+communityDeptId+
                    " AND t1.overall_status_cy = 2 "+sql8+
                    ") AS SearchOutNum, "

                    //粪便DNA检查已反馈
                    + " ( SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_stool_dna t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_stool_dna ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'A' " +
                    " AND t1.community_dept_id = "+ communityDeptId + sql9 +
                    " AND t2.dna_check_inform_status = 2 " +
                    ") AS DNAFeedbackNum, "

                    //FIT无效
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'A' " +
                    " AND t1.community_dept_id = " + communityDeptId + sql10 +
                    " AND t2.result = 3 " +
                    ") AS FitInvalidNum "
					+ " from dual;";
			log.info("msq1==>"+msq1);
			List<Map<String,Object>> list1 = jdbcTemplate.queryForList(msq1);
			for (Map<String, Object> ma : list1) {
				sumByA1 = Integer.parseInt(ma.get("groupsNum").toString());
				sumByA2 = Integer.parseInt(ma.get("bookingColonscopyNum").toString());
				sumByA3 = Integer.parseInt(ma.get("inspectColonscopyNum").toString());
				sumByA4 = Integer.parseInt(ma.get("DNANum").toString());
				sumByA5 = Integer.parseInt(ma.get("FitNum").toString());
				sumByA6 = Integer.parseInt(ma.get("FitPositiveNum").toString());
				sumByA7 = Integer.parseInt(ma.get("FitNegativeNum").toString());
				sumByA8 = Integer.parseInt(ma.get("SearchOutNum").toString());
				sumByA9 = Integer.parseInt(ma.get("DNAFeedbackNum").toString());
				sumByA10 = Integer.parseInt(ma.get("FitInvalidNum").toString());
				sumByA11 = sumByA6 + sumByA7 + sumByA10;
                ma.put("FitResultReturnNum", sumByA11);
			};
			String jsons1 = list1.toString().substring(1,list1.toString().length());
			String sumdepts1 = jsons1.substring(0, jsons1.length()-1);
			rs.add(sumdepts1);
			//第二次B
			String msq2 = "select "
					+ " (select count(distinct(sid)) as groupByB from hospital_intestine_review  where group_status = 2 and `group` = 'B' and community_dept_id = "+communityDeptId+" "+sql1+") as groupsNum,"
                    //已预约肠镜检查人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_colonoscopy_record t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_colonoscopy_record ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'B' " +
                    " AND t1.community_dept_id = "+communityDeptId+
                    " AND t2.reserve_status = 2 "+sql2+
                    " ) AS bookingColonscopyNum,"

                    //实际接受肠镜检查人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_colonoscopy_record t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_colonoscopy_record ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'B' " +
                    " AND t1.community_dept_id = "+communityDeptId+
                    " AND t2.examination_status = 2 "+sql3+
                    ") AS inspectColonscopyNum,"

                    //接受粪便DNA检测人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_stool_dna t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT max(ss.id) " +
                    "  FROM hospital_stool_dna ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'B' " +
                    " AND t1.community_dept_id = "+communityDeptId+
                    " AND t2.code_entry_status = 2 "+sql4+
                    ") AS DNANum,"

                    //实际接受FIT检查人数
                    + " (SELECT count(DISTINCT(t1.sid))" +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT max(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'B' " +
                    " AND t1.community_dept_id = "+communityDeptId+
                    " AND t2.code_entry_status = 2 "+sql5+
                    ") AS FitNum,"

                    //FIT阳性人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'B' " +
                    " AND t1.community_dept_id = " +communityDeptId+sql6+
                    " AND t2.result = 2 " +
                    ") AS FitPositiveNum,"

                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'B' " +
                    " AND t1.community_dept_id = " +communityDeptId+sql7+
                    " AND t2.result = 1" +
                    ")as FitNegativeNum,"

					//+ " (select count(distinct(t1.sid))as groupByB from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'B'  and t1.community_dept_id = "+communityDeptId+" and t2.result = 2 "+sql6+")as FitPositiveNum,"
					//+ " (select count(distinct(t1.sid))as groupByB from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'B'  and t1.community_dept_id = "+communityDeptId+" and t2.result = 1 "+sql7+")as FitNegativeNum,"

                    //退出研究
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_intestine_review_quit_log t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT max(ss.id) " +
                    "  FROM hospital_intestine_review_quit_log ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'B' " +
                    " AND t1.community_dept_id = "+communityDeptId+
                    " AND t1.overall_status_cy = 2 "+sql8+
                    ") AS SearchOutNum, "

                    //粪便DNA检查已反馈
                    + " ( SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_stool_dna t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_stool_dna ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'B' " +
                    " AND t1.community_dept_id = "+ communityDeptId + sql9 +
                    " AND t2.dna_check_inform_status = 2 " +
                    ") AS DNAFeedbackNum, "

                    //FIT无效
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'B' " +
                    " AND t1.community_dept_id = " + communityDeptId + sql10 +
                    " AND t2.result = 3 " +
                    ") AS FitInvalidNum "
					+ " from dual;";
            log.info("msq1=>"+msq2);
			List<Map<String,Object>> list2 = jdbcTemplate.queryForList(msq2);
			for (Map<String, Object> mb : list2) {
				sumByB1 = Integer.parseInt(mb.get("groupsNum").toString());
				sumByB2 = Integer.parseInt(mb.get("bookingColonscopyNum").toString());
				sumByB3 = Integer.parseInt(mb.get("inspectColonscopyNum").toString());
				sumByB4 = Integer.parseInt(mb.get("DNANum").toString());
				sumByB5 = Integer.parseInt(mb.get("FitNum").toString());
				sumByB6 = Integer.parseInt(mb.get("FitPositiveNum").toString());
				sumByB7 = Integer.parseInt(mb.get("FitNegativeNum").toString());
				sumByB8 = Integer.parseInt(mb.get("SearchOutNum").toString());
				sumByB9 = Integer.parseInt(mb.get("DNAFeedbackNum").toString());
				sumByB10 = Integer.parseInt(mb.get("FitInvalidNum").toString());
				sumByB11 = sumByB6 + sumByB7 + sumByB10;
                mb.put("FitResultReturnNum", sumByB11);
			}
			String jsons2 = list2.toString().substring(1,list2.toString().length());
			String sumdepts2 = jsons2.substring(0, jsons2.length()-1);
			rs.add(sumdepts2);
			//第三次C高危
			String msq3 = "select "
					+ " (select count(distinct(sid)) as groupByC from hospital_intestine_review where group_status = 2 and `group` = 'C' and risk_level = 2 and community_dept_id = "+communityDeptId+" "+sql1+") as groupsNum,"
                    //已预约肠镜检查人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_colonoscopy_record t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_colonoscopy_record ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 2 " +
                    " AND t1.community_dept_id = "+communityDeptId+
                    " AND t2.reserve_status = 2 "+sql2+
                    " ) AS bookingColonscopyNum,"

                    //实际接受肠镜检查人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_colonoscopy_record t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_colonoscopy_record ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 2 " +
                    " AND t1.community_dept_id = "+communityDeptId+
                    " AND t2.examination_status = 2 "+sql3+
                    ") AS inspectColonscopyNum,"

                    //接受粪便DNA检测人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_stool_dna t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT max(ss.id) " +
                    "  FROM hospital_stool_dna ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 2 " +
                    " AND t1.community_dept_id = "+communityDeptId+
                    " AND t2.code_entry_status = 2 "+sql4+
                    ") AS DNANum,"

                    //实际接受FIT检查人数
                    + " (SELECT count(DISTINCT(t1.sid))" +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT max(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 2 " +
                    " AND t1.community_dept_id = "+communityDeptId+
                    " AND t2.code_entry_status = 2 "+sql5+
                    ") AS FitNum,"

                    //FIT阳性人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 2 " +
                    " AND t1.community_dept_id = " +communityDeptId+sql6+
                    " AND t2.result = 2 " +
                    ") AS FitPositiveNum,"

                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 2 " +
                    " AND t1.community_dept_id = " +communityDeptId+sql7+
                    " AND t2.result = 1" +
                    ")as FitNegativeNum,"
					//+ " (select count(distinct(t1.sid))as groupByC from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'C'  and t1.community_dept_id = "+communityDeptId+" and t2.result = 2 and t1.risk_level = 2 "+sql6+")as FitPositiveNum,"
					//+ " (select count(distinct(t1.sid))as groupByC from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'C'  and t1.community_dept_id = "+communityDeptId+" and t2.result = 1 and t1.risk_level = 2 "+sql7+")as FitNegativeNum,"

                    //退出研究
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_intestine_review_quit_log t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT max(ss.id) " +
                    "  FROM hospital_intestine_review_quit_log ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 2 " +
                    " AND t1.community_dept_id = "+communityDeptId+
                    " AND t1.overall_status_cy = 2 "+sql8+
                    ") AS SearchOutNum, "

                    //粪便DNA检查已反馈
                    + " ( SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_stool_dna t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_stool_dna ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 2 " +
                    " AND t1.community_dept_id = "+ communityDeptId + sql9 +
                    " AND t2.dna_check_inform_status = 2 " +
                    ") AS DNAFeedbackNum, "

                    //FIT无效
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 2 " +
                    " AND t1.community_dept_id = " + communityDeptId + sql10 +
                    " AND t2.result = 3 " +
                    ") AS FitInvalidNum "
					+ " from dual;";
			log.info("sql==>"+msq3);
			List<Map<String,Object>> list3 = jdbcTemplate.queryForList(msq3);
			for (Map<String, Object> mc1 : list3) {
				sumByCg1 = Integer.parseInt(mc1.get("groupsNum").toString());
				sumByCg2 = Integer.parseInt(mc1.get("bookingColonscopyNum").toString());
				sumByCg3 = Integer.parseInt(mc1.get("inspectColonscopyNum").toString());
				sumByCg4 = Integer.parseInt(mc1.get("DNANum").toString());
				sumByCg5 = Integer.parseInt(mc1.get("FitNum").toString());
				sumByCg6 = Integer.parseInt(mc1.get("FitPositiveNum").toString());
				sumByCg7 = Integer.parseInt(mc1.get("FitNegativeNum").toString());
				sumByCg8 = Integer.parseInt(mc1.get("SearchOutNum").toString());
				sumByCg9 = Integer.parseInt(mc1.get("DNAFeedbackNum").toString());
				sumByCg10 = Integer.parseInt(mc1.get("FitInvalidNum").toString());
				sumByCg11 = sumByCg6 + sumByCg7 + sumByCg10;
                mc1.put("FitResultReturnNum", sumByCg11);
			}
			String jsons3 = list3.toString().substring(1,list3.toString().length());
			String sumdepts3 = jsons3.substring(0, jsons3.length()-1);
			rs.add(sumdepts3);
			//接受粪便DNA人数
			//第四次低危
			String msq4 = "select "
					+ " (select count(distinct(sid)) as groupByC from hospital_intestine_review where group_status = 2 and `group` = 'C' and risk_level = 1 and community_dept_id = "+communityDeptId+" "+sql1+") as groupsNum,"
                    //已预约肠镜检查人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_colonoscopy_record t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_colonoscopy_record ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 1 " +
                    " AND t1.community_dept_id = "+communityDeptId+
                    " AND t2.reserve_status = 2 "+sql2+
                    " ) AS bookingColonscopyNum,"

                    //实际接受肠镜检查人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_colonoscopy_record t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_colonoscopy_record ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 1 " +
                    " AND t1.community_dept_id = "+communityDeptId+
                    " AND t2.examination_status = 2 "+sql3+
                    ") AS inspectColonscopyNum,"

                    //接受粪便DNA检测人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_stool_dna t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT max(ss.id) " +
                    "  FROM hospital_stool_dna ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 1 " +
                    " AND t1.community_dept_id = "+communityDeptId+
                    " AND t2.code_entry_status = 2 "+sql4+
                    ") AS DNANum,"

                    //实际接受FIT检查人数
                    + " (SELECT count(DISTINCT(t1.sid))" +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT max(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 1 " +
                    " AND t1.community_dept_id = "+communityDeptId+
                    " AND t2.code_entry_status = 2 "+sql5+
                    ") AS FitNum,"

                    //FIT阳性人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 1 " +
                    " AND t1.community_dept_id = " +communityDeptId+sql6+
                    " AND t2.result = 2 " +
                    ") AS FitPositiveNum,"

                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 1 " +
                    " AND t1.community_dept_id = " +communityDeptId+sql7+
                    " AND t2.result = 1" +
                    ")as FitNegativeNum,"
					//+ " (select count(distinct(t1.sid))as groupByC from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'C'  and t1.community_dept_id = "+communityDeptId+" and t2.result = 2 and t1.risk_level = 1 "+sql6+")as FitPositiveNum,"
					//+ " (select count(distinct(t1.sid))as groupByC from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'C'  and t1.community_dept_id = "+communityDeptId+" and t2.result = 1 and t1.risk_level = 1 "+sql7+")as FitNegativeNum,"

                    //退出研究
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_intestine_review_quit_log t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT max(ss.id) " +
                    "  FROM hospital_intestine_review_quit_log ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 1 " +
                    " AND t1.community_dept_id = "+communityDeptId+
                    " AND t1.overall_status_cy = 2 "+sql8+
                    ") AS SearchOutNum, "

                    //粪便DNA检查已反馈
                    + " ( SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_stool_dna t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_stool_dna ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 1 " +
                    " AND t1.community_dept_id = "+ communityDeptId + sql9 +
                    " AND t2.dna_check_inform_status = 2 " +
                    ") AS DNAFeedbackNum, "

                    //FIT无效
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 1 " +
                    " AND t1.community_dept_id = " + communityDeptId + sql10 +
                    " AND t2.result = 3 " +
                    ") AS FitInvalidNum "
					+ " from dual;";
			log.info("sql==>"+msq4);
			List<Map<String,Object>> list4 = jdbcTemplate.queryForList(msq4);
			for (Map<String, Object> mc2 : list4) {
				sumByCd1 = Integer.parseInt(mc2.get("groupsNum").toString());
				sumByCd2 = Integer.parseInt(mc2.get("bookingColonscopyNum").toString());
				sumByCd3 = Integer.parseInt(mc2.get("inspectColonscopyNum").toString());
				sumByCd4 = Integer.parseInt(mc2.get("DNANum").toString());
				sumByCd5 = Integer.parseInt(mc2.get("FitNum").toString());
				sumByCd6 = Integer.parseInt(mc2.get("FitPositiveNum").toString());
				sumByCd7 = Integer.parseInt(mc2.get("FitNegativeNum").toString());
				sumByCd8 = Integer.parseInt(mc2.get("SearchOutNum").toString());
				sumByCd9 = Integer.parseInt(mc2.get("DNAFeedbackNum").toString());
				sumByCd10 = Integer.parseInt(mc2.get("FitInvalidNum").toString());
				sumByCd11 = sumByCd6 + sumByCd7 + sumByCd10;
                mc2.put("FitResultReturnNum", sumByCd11);
			}
			String jsons4 = list4.toString().substring(1,list4.toString().length());
			String sumdepts4 = jsons4.substring(0, jsons4.length()-1);
			rs.add(sumdepts4);
			//实际接受FIT检查人数
			//第五次未评估
			String msq5 = "select "
					+ " (select count(distinct(sid)) as groupByC from hospital_intestine_review where group_status = 2 and `group` = 'C' and risk_level  is null and community_dept_id = "+communityDeptId+" "+sql1+")as groupsNum,"
                    //已预约肠镜检查人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_colonoscopy_record t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_colonoscopy_record ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level is null " +
                    " AND t1.community_dept_id = "+communityDeptId+
                    " AND t2.reserve_status = 2 "+sql2+
                    " ) AS bookingColonscopyNum,"

                    //实际接受肠镜检查人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_colonoscopy_record t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_colonoscopy_record ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level is null " +
                    " AND t1.community_dept_id = "+communityDeptId+
                    " AND t2.examination_status = 2 "+sql3+
                    ") AS inspectColonscopyNum,"

                    //接受粪便DNA检测人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_stool_dna t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT max(ss.id) " +
                    "  FROM hospital_stool_dna ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level is null " +
                    " AND t1.community_dept_id = "+communityDeptId+
                    " AND t2.code_entry_status = 2 "+sql4+
                    ") AS DNANum,"

                    //实际接受FIT检查人数
                    + " (SELECT count(DISTINCT(t1.sid))" +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT max(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level is null " +
                    " AND t1.community_dept_id = "+communityDeptId+
                    " AND t2.code_entry_status = 2 "+sql5+
                    ") AS FitNum,"

                    //FIT阳性人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level is null " +
                    " AND t1.community_dept_id = " +communityDeptId+sql6+
                    " AND t2.result = 2 " +
                    ") AS FitPositiveNum,"

                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level is null " +
                    " AND t1.community_dept_id = " +communityDeptId+sql7+
                    " AND t2.result = 1" +
                    ")as FitNegativeNum,"
					//+ " (select count(distinct(t1.sid))as groupByC from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'C'  and t1.community_dept_id = "+communityDeptId+" and t2.result = 2 and t1.risk_level is null "+sql6+") as FitPositiveNum,"
					//+ " (select count(distinct(t1.sid))as groupByC from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'C'  and t1.community_dept_id = "+communityDeptId+" and t2.result = 1 and t1.risk_level is null "+sql7+") as FitNegativeNum,"

                    //退出研究
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_intestine_review_quit_log t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT max(ss.id) " +
                    "  FROM hospital_intestine_review_quit_log ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level is null " +
                    " AND t1.community_dept_id = "+communityDeptId+
                    " AND t1.overall_status_cy = 2 "+sql8+
                    ") AS SearchOutNum, "

                    //粪便DNA检查已反馈
                    + " ( SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_stool_dna t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_stool_dna ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level is null " +
                    " AND t1.community_dept_id = "+ communityDeptId + sql9 +
                    " AND t2.dna_check_inform_status = 2 " +
                    ") AS DNAFeedbackNum, "

                    //FIT无效
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level is null " +
                    " AND t1.community_dept_id = " + communityDeptId + sql10 +
                    " AND t2.result = 3 " +
                    ") AS FitInvalidNum "
					+ " from dual;";
			log.info("sql==>"+msq5);
			List<Map<String,Object>> list5 = jdbcTemplate.queryForList(msq5);
			for (Map<String, Object> mc3 : list5) {
				sumByCp1 = Integer.parseInt(mc3.get("groupsNum").toString());
				sumByCp2 = Integer.parseInt(mc3.get("bookingColonscopyNum").toString());
				sumByCp3 = Integer.parseInt(mc3.get("inspectColonscopyNum").toString());
				sumByCp4 = Integer.parseInt(mc3.get("DNANum").toString());
				sumByCp5 = Integer.parseInt(mc3.get("FitNum").toString());
				sumByCp6 = Integer.parseInt(mc3.get("FitPositiveNum").toString());
				sumByCp7 = Integer.parseInt(mc3.get("FitNegativeNum").toString());
				sumByCp8 = Integer.parseInt(mc3.get("SearchOutNum").toString());
				sumByCp9 = Integer.parseInt(mc3.get("DNAFeedbackNum").toString());
				sumByCp10 = Integer.parseInt(mc3.get("FitInvalidNum").toString());
				sumByCp11 = sumByCp6 + sumByCp7 + sumByCp10;
                mc3.put("FitResultReturnNum", sumByCp11);
			}
			String jsons5 = list5.toString().substring(1,list5.toString().length());
			String sumdepts5 = jsons5.substring(0, jsons5.length()-1);
			rs.add(sumdepts5);
			int sums1 = sumByA1 + sumByB1 + sumByCg1 + sumByCd1 + sumByCp1;   //统计入组总人数
			int sums2 = sumByA2 + sumByB2 + sumByCg2 + sumByCd2 + sumByCp2;   //已预约肠镜检查人数
			int sums3 = sumByA3 + sumByB3 + sumByCg3 + sumByCd3 + sumByCp3;   //实际接受肠镜检查人数
			int sums4 = sumByA4 + sumByB4 + sumByCg4 + sumByCd4 + sumByCp4;   //接受粪便DNA检测人数
			int sums5 = sumByA5 + sumByB5 + sumByCg5 + sumByCd5 + sumByCp5;   //实际接受FIT检查人数
			int sums6 = sumByA6 + sumByB6 + sumByCg6 + sumByCd6 + sumByCp6;   //FIT阳性人数
			int sums7 = sumByA7 + sumByB7 + sumByCg7 + sumByCd7 + sumByCp7;   //FIT阴性人数
			int sums8 = sumByA8 + sumByB8 + sumByCg8 + sumByCd8 + sumByCp8;
			int sums9 = sumByA9 + sumByB9 + sumByCg9 + sumByCd9 + sumByCp9;	//粪便DNA检查已返回人数
			int sums10 = sumByA10 + sumByB10 + sumByCg10 + sumByCd10 + sumByCp10;	//FIT阴性人数
			int sums11 = sumByA11 + sumByB11 + sumByCg11 + sumByCd11 + sumByCp11;						//FIT结果返回人数
			ams.put("groupsNum", sums1);
			ams.put("bookingColonscopyNum", sums2);
			ams.put("inspectColonscopyNum", sums3);
			ams.put("DNANum", sums4);
			ams.put("FitNum", sums5);
			ams.put("FitPositiveNum", sums6);
			ams.put("FitNegativeNum", sums7);
			ams.put("SearchOutNum", sums8);
			ams.put("DNAFeedbackNum",sums9);
			ams.put("FitInvalidNum",sums10);
			ams.put("FitResultReturnNum",sums11);
			sumsByGroup.add(ams);
			//统计总人数
			String jsons6 = sumsByGroup.toString().substring(1,sumsByGroup.toString().length());
			String sumdepts6 = jsons6.substring(0, jsons6.length()-1);
			rs.add(sumdepts6);

			//存放数据格式
			arrs.put("commdtName", commityName);
			arrs.put("list", rs);
			amp.add(arrs);
			log.info("@Dao getGroupByAreaId query  End " + rs);
		}
		return amp;
	}
	/**
	 * 按照地区查询所对应的社区未完成危险因素调查表
	 */
	@Override
	public List notRiskFactorsByAreaId(int areaId, int reserve_status, TodoVo vo) {
		// TODO Auto-generated method stub
		List<Map<String,Object>> rsp = new ArrayList<Map<String,Object>>();
		int notRiskFactors = 0;
		int notFitcodes = 0;
		int notFitResult = 0;
		int notStollDNA = 0;
		int notEntryallocations = 0;
		int notFinishes = 0;
		int notNotices = 0;
		int notDNAResults = 0;
		List<Map<String, Object>> communits = hcrAllocationDao.getcommdeptsByAreaId(areaId);
		for (Map<String, Object> map : communits) {
			Map<String,Object> msp1 = new LinkedHashMap<String,Object>();
			int communityDeptId = Integer.parseInt(map.get("id").toString()) ;
			String commityName = map.get("commdeptName").toString();
			msp1.put("commityName", commityName);
			msp1.put("communityDeptId",communityDeptId);
			//未完成危险因素调查表
			log.info("@Dao notRiskFactorsByAreaId query  Start ");
			/*String sql1 = "select count(t3.sid) as notRiskFactors from itsys_department t1,"
					+ " hospital_intestine_review t2,hospital_todo_event t3"
					+ " where t2.sid = t3.sid and t3.community_dept_id = ? "
					+ " and t3.type = ? and t3.`status`=?";*/
			String sql1 = "select count(t2.sid)as notRiskFactors from hospital_intestine_review t1,hospital_todo_event t2 , itsys_user u" +
					" where t1.sid = t2.sid AND t1.create_user  = u.loginName and t2.community_dept_id=?  and t2.type=? and t2.`status`=?";
			List<Map<String,Object>> arrs1 = jdbcTemplate.queryForList(sql1,communityDeptId,1,1);
			log.info("@Dao notRiskFactorsByAreaId query  End ");
			for (Map<String, Object> map1 : arrs1) {
				notRiskFactors = Integer.parseInt(map1.get("notRiskFactors").toString()) ;
				msp1.put("notRiskFactors", notRiskFactors);
				msp1.put("communityDeptId",communityDeptId);
			}
			//未录入fit编号
			/*String sql2 = "select count(t2.sid) as notFitcodes from itsys_department t1, hospital_intestine_review t2,hospital_todo_event t3"
					+ " where t1.pid = t3.area_dept_id and t2.sid = t3.sid "
					+ " and t3.community_dept_id=? "
					+ " and t3.type=?"
					+ " and t3.`status`=?";*/
			String sql2 = "select count(t2.sid)as notFitcodes from hospital_intestine_review t1,hospital_todo_event t2 , itsys_user u " +
					" where t1.sid = t2.sid AND t1.create_user = u.loginName and t2.community_dept_id=? and t2.type=? and t2.`status`=?";
			List<Map<String,Object>> arrs2 = jdbcTemplate.queryForList(sql2,communityDeptId,2,1);
			for (Map<String, Object> map2 : arrs2) {
				notFitcodes = Integer.parseInt(map2.get("notFitcodes").toString());
				msp1.put("notFitcodes", notFitcodes);
				msp1.put("communityDeptId",communityDeptId);
			}
			//未录入fit结果
			/*String sql3 = "select count(t2.sid) as notFitResult from itsys_department t1,hospital_intestine_review  t2,"
					+ " hospital_todo_event t3 where t1.pid = t3.area_dept_id and "
					+ " t2.sid = t3.sid"
					+ " and t3.community_dept_id=? "
					+ " and t3.type=?"
					+ " and t3.`status`=?";*/
			String sql3 = "select count(t2.sid)as notFitResult from hospital_intestine_review t1,hospital_todo_event t2 , itsys_user u" +
					" where t1.sid = t2.sid AND t1.create_user = u.loginName and t2.community_dept_id=? and t2.type=? and t2.`status`=?";
			List<Map<String,Object>> arrs3 = jdbcTemplate.queryForList(sql3,communityDeptId,3,1);
			for (Map<String, Object> map3 : arrs3) {
				notFitResult = Integer.parseInt(map3.get("notFitResult").toString());
				msp1.put("notFitResult", notFitResult);
				msp1.put("communityDeptId",communityDeptId);
			}
			//未录入粪便DNA编号
			/*String sql4 = "select  count(t2.sid) as notStollDNA from itsys_department t1, hospital_intestine_review t2,hospital_todo_event t3"
					+ "  where t1.pid = t3.area_dept_id"
					+ "    and t2.sid = t3.sid "
					+ " and t3.community_dept_id = ? "
					+ " and t3.type=?"
					+ " and t3.`status`=? ";*/
			String sql4 = "select count(t2.sid) as notStollDNA from hospital_intestine_review t1,hospital_todo_event t2 ,itsys_user u " +
					" where t1.sid = t2.sid AND t1.create_user = u.loginName and t2.community_dept_id= ? and t2.type= ? and t2.`status`= ?";
			List<Map<String,Object>> arrs4 = jdbcTemplate.queryForList(sql4,communityDeptId,4,1);
			for (Map<String, Object> map4 : arrs4) {
				notStollDNA = Integer.parseInt(map4.get("notStollDNA").toString());
				msp1.put("notStollDNA", notStollDNA);
				msp1.put("communityDeptId",communityDeptId);
			}
			//未预约结肠镜检查
			/*String sql5 = "select  count(t2.sid) as notEntryallocations"
					+ "  from itsys_department t1, hospital_intestine_review t2,hospital_todo_event t3 where"
					+ " t1.pid = t3.area_dept_id and t2.sid = t3.sid"
					+ " and t3.community_dept_id=? "
					+ " and t3.type=?"
					+ " and t3.`status`=? ";*/
			String sql5 = "select count(t2.sid)as notEntryallocations from hospital_intestine_review t1,hospital_todo_event t2 ,itsys_department t3," +
					" itsys_user u where t1.sid = t2.sid and t1.community_dept_id = t3.id AND t1.create_user = u.loginName and " +
					" t2.community_dept_id=? and t2.type=? and t2.`status`=?";
			log.info("@Dao notEntryAllocation  End ");
			List<Map<String,Object>> arrs5 = jdbcTemplate.queryForList(sql5,communityDeptId,5,1);
			for (Map<String, Object> map5 : arrs5) {
				notEntryallocations = Integer.parseInt(map5.get("notEntryallocations").toString());
				msp1.put("notEntryallocations", notEntryallocations);
				msp1.put("communityDeptId",communityDeptId);
			}
			//未完成结肠镜检查
			/*String sql6 = "select count(t2.sid) as notFinishes from itsys_department t, hospital_todo_event t1,hospital_intestine_review t2,hospital_colonoscopy_reservation_allocation t3,hospital_colonoscopy_record t4 where "
					+ "  t.pid = t1.area_dept_id and t1.sid = t2.sid "
					+ " and t1.data_id = t4.id "
					+ " and t4.allocation_id = t3.id "
					+ " and t1.community_dept_id = ? "
					+ " and t1.type = ? "
					+ " and t1.`status`=? ";*/
			String sql6 = "select count(t1.sid)as notFinishes from hospital_todo_event t1,hospital_intestine_review t2,itsys_user u" +
					" ,hospital_colonoscopy_record t4 LEFT JOIN hospital_colonoscopy_reservation_allocation t3 on t4.allocation_id = t3.id" +
					" where t1.sid = t2.sid AND t2.create_user = u.loginName and t1.data_id = t4.id " +
					" and t1.community_dept_id = ? and t1.type = ? and t1.`status`= ? ";
			List<Map<String,Object>> arrs6 = jdbcTemplate.queryForList(sql6,communityDeptId,6,1);
			for (Map<String, Object> map6 : arrs6) {
				notFinishes = Integer.parseInt(map6.get("notFinishes").toString());
				msp1.put("notFinishes", notFinishes);
				msp1.put("communityDeptId",communityDeptId);
			}
			//未通知筛查结果告知书
			/*String sql7 = "select count(t1.sid) as notNotices from itsys_department t, hospital_intestine_review t1,hospital_todo_event t2 where"
					+ "  t.pid = t2.area_dept_id and t1.sid = t2.sid "
					+ " and t2.community_dept_id=? "
					+ " and t2.type=?"
					+ " and t2.`status`=? ";*/
			String sql7 = "select count(t2.sid)as notNotices from hospital_intestine_review t1,hospital_todo_event t2 ,itsys_department t3," +
					" itsys_user u where t1.sid = t2.sid and t1.community_dept_id = t3.id AND t1.create_user = u.loginName" +
					" and t2.community_dept_id=? and t2.type=? and t2.`status`=? ";
			List<Map<String,Object>> arrs7 = jdbcTemplate.queryForList(sql7,communityDeptId,7,1);
			for (Map<String, Object> map7 : arrs7) {
				notNotices = Integer.parseInt(map7.get("notNotices").toString());
				msp1.put("notNotices", notNotices);
				msp1.put("communityDeptId",communityDeptId);
			}
			//地区新增未发放粪便DNA结果
			/*String sql8 = "select count(t2.sid) as notDNAResults from itsys_department t1, hospital_intestine_review t2,hospital_todo_event t3 where"
					+ " t1.pid = t3.area_dept_id and t2.sid = t3.sid"
					+ " and t3.community_dept_id=? "
					+ " and t3.type=?"
					+ " and t3.`status`=? ";*/
			String sql8 = "select count(t2.sid)as notDNAResults from hospital_intestine_review t1,hospital_todo_event t2 ,itsys_department t3,itsys_user u" +
                    " where t1.sid = t2.sid and t1.community_dept_id = t3.id AND t1.create_user = u.loginName" +
                    " and t2.community_dept_id= ? and t2.type=? and t2.`status`= ?";
			List<Map<String,Object>> arrs8 = jdbcTemplate.queryForList(sql8,communityDeptId,17,1);
			for (Map<String, Object> map8 : arrs8) {
				notDNAResults = Integer.parseInt(map8.get("notDNAResults").toString());
				msp1.put("notDNAResults", notDNAResults);
				msp1.put("communityDeptId",communityDeptId);
			}
			//如果msp1存储数据不为空
			if(!msp1.isEmpty()){
				rsp.add(msp1);
			}
		}
		return rsp;
	}
	/**
	 * (国家受试者)国家统计查询受试者列表
	 */
	@Override
	public ListPageUtil queryReviewByNationIdPage(HospitalReview person,int nationId) {
		// TODO Auto-generated method stub
		log.info("@dao queryReviewByNationIdPage 查询参数 person = {}", person.toString());
		StringBuilder sql = new StringBuilder();
		@SuppressWarnings("rawtypes")
		List args = new ArrayList();
		sql.append("  SELECT ( select  dna_check_result from  hospital_stool_dna where sid=r.sid  order by date_created desc limit 0,1) as dnaResult,(select result from  hospital_fit_result where sid=r.sid   order by date_created desc limit 0,1) as fitResult,r.id,r.name, u.nickName, r.phone,r.community_dept_id communityDeptId,d.`name` as depName,"
				+ "d2.`name` as areaName,"
				+ "r.area_dept_id areaDeptId,r.id_card idCard,r.sid,r.age,r.sex,r.birth_date birthDate,r.item1,"
				+ "r.item2,r.item3,r.item4,r.item5,r.item6,r.item7,r.item8,r.item9,r.item10,r.investigator,r.survey_date surveyDate,"
				+ "r.reviewer,r.address,r.in_group_date inGroupDate,"
				+ "case when r.`group`= 'A' then 'A' when r.`group` = 'B'  then 'B' when r.`group` = 'C' and r.risk_level is null then 'C'   when r.`group` = 'C' and r.risk_level = 1 then 'Cd' when r.`group` = 'C' and  r.risk_level = 2 then 'Cg' ELSE NULL END `group`,"
				+ "r.group_status groupStatus,r.off_group_reason offGroupReason,"
				+ "r.stage_cy stageCy,r.review_status reviewStatus,r.risk_factor_status riskFactorStatus,r.overall_status_cy overallStatusCy,"
				+ "r.overall_status_t0 overallStatusT0,r.overall_status_t1 overallStatusT1,r.overall_status_t2 overallStatusT2,"
				+ "r.overall_status_t3 overallStatusT3,r.violation_plan_status_cy violationPlanStatusCy,r.violation_plan_status_t0 violationPlanStatusT0,"
				+ "r.violation_plan_status_t1 violationPlanStatusT1,r.violation_plan_status_t2 violationPlanStatusT2,r.violation_plan_status_t3 violationPlanStatusT3,"
				+ "r.risk_level riskLevel,r.site_id siteId,r.delete_flag deleteFlag,r.date_created dateCreated,r.update_time updateTime,r.score score FROM hospital_intestine_review r, "
				+ "itsys_department d,itsys_department d2,itsys_user u WHERE 1=1 and r.community_dept_id=d.id and r.area_dept_id=d2.id AND r.create_user = u.loginName  ");
//        args.add(nationId);
		if (StringUtils.isNotBlank(person.getSid())) {
			sql.append(" and r.sid like ? ");
			args.add("%" + person.getSid() + "%");
		}

		if (StringUtils.isNotBlank(person.getName())) {
			sql.append(" and r.name like ? ");
			args.add("%" + person.getName() + "%");
		}

		if (StringUtils.isNotBlank(person.getPhone())) {
			sql.append(" and r.phone like ? ");
			args.add("%" +person.getPhone() + "%");
		}

		if (StringUtils.isNotBlank(person.getIdCard())) {
			sql.append(" and r.id_card like ? ");
			args.add("%" +person.getIdCard()+ "%");
		}

		if (person.getSex() != null) {
			sql.append(" and r.sex = ? ");
			args.add(person.getSex());
		}

		if (StringUtils.isNotBlank(person.getGroup())) {
			if(person.getGroup().equals("Cg")){
				sql.append(" and r.group = 'C' and r.risk_level = ?");
				args.add(2);
			}else if(person.getGroup().equals("Cd")){
				sql.append(" and r.group = 'C' and r.risk_level = ?");
				args.add(1);
			}else{
				sql.append(" and r.group = ? ");
				args.add(person.getGroup());
			}
		}

		if (person.getOverallStatusCy() != null) {
			sql.append(" and r.overall_status_cy = ? ");
			args.add(person.getOverallStatusCy());
		}

		if (person.getViolationPlanStatusCy() != null) {
			sql.append(" and r.violation_plan_status_cy = ? ");
			args.add(person.getViolationPlanStatusCy());
		}

		if (person.getCommunityDeptId() != null) {
			sql.append(" and r.community_dept_id = ? ");
			args.add(person.getCommunityDeptId());
		}

		if (person.getAreaDeptId() != null) {
			sql.append(" and r.area_dept_id = ? ");
			args.add(person.getAreaDeptId());
		}

		if (StringUtils.isNotBlank(person.getLoginName())) {
			sql.append(" and u.loginName = ? ");
			args.add(person.getLoginName());
		}

		if (StringUtils.isNotBlank(person.getInGroupDateStart())) {
			sql.append(" and r.in_group_date >= ? ");
			args.add(person.getInGroupDateStart() + " 00:00:00");
		}

		if (StringUtils.isNotBlank(person.getInGroupDateEnd())) {
			sql.append(" and r.in_group_date <= ? ");
			args.add(person.getInGroupDateEnd() + " 23:59:59");
		}


		if(StringUtils.isNotBlank(person.getSortParameter())&&StringUtils.isNotBlank(person.getSortRule())){
			sql.append("  ORDER BY  r."+person.getSortParameter()+" "+person.getSortRule().toUpperCase());
		}else{
			sql.append("  ORDER BY  r.date_created DESC ");
		}

		ListPageUtil listPage = new ListPageUtil(sql.toString(), args.toArray(), person.getPageNo(), person.getPageSize(), jdbcTemplate, null, Constans.SUBQUERY);
		return listPage;
	}
	//根据国家id查询所对应的地区医院
	@Override
	public List<Map<String,Object>> getAreaByNationId(int nationId) {
		// TODO Auto-generated method stub
		try {
			String sql = "select id,name from itsys_department where pid = ?";
			List<Map<String,Object>> areas = jdbcTemplate.queryForList(sql,nationId);
			return areas;
		} catch (Exception e) {
			// TODO: handle exception
			log.info(e.getMessage());
			return null;
		}
	}

	@Override
	public List getAreaByAreaId(int areaId) {
		try {
			String sql = "select id,name from itsys_department where id = ?";
			List<Map<String,Object>> areas = jdbcTemplate.queryForList(sql,areaId);
			return areas;
		} catch (Exception e) {
			// TODO: handle exception
			log.info(e.getMessage());
			return null;
		}
	}

	//(国家受试者)根据id查询受试者基本信息
	@Override
	public List getNationListByid(String sid) {
		// TODO Auto-generated method stub
		log.info("getNationListByid ==》",sid);
		String sql = "select t.sid,t.overall_status_cy as overallStatusCy,t.name,t.sex,t.phone,t.in_group_date, d.name as communDeptName,t.review_status as reviewStatus,t.risk_factor_status as riskFactorStatus,"
				+ " case when t.`group`= 'A' then 'A' when t.`group` = 'B'  then 'B' when t.`group` = 'C' and t.risk_level is null then 'C'   when t.`group` = 'C' and t.risk_level = 1 then 'Cd' when t.`group` = 'C' and  t.risk_level = 2 then 'Cg' ELSE NULL END `group`,"
				+ " (select d1.name from itsys_department d1 where d1.id = t.area_dept_id) as areaName"
				+ " from hospital_intestine_review t,itsys_department d where t.community_dept_id = d.id"
				+ " and t.sid = ?";
		List arrs = jdbcTemplate.queryForList(sql,sid);
		log.info("getNationListByid ==》",arrs);
		return arrs;
	}
	/**
	 * (国家受试者)查询fit列表
	 */
	@Override
	public List getFitListByNationId(String sid) {
		// TODO Auto-generated method stub
		String sql = "select f.code_entry_status as codeEntryStatus,f.fit_code as fitCode,"
				+ " f.code_entry_time as codeEntryTime,f.release_person_code as releasePersonCode,"
				+ " f.insert_status as insertStatus,f.result_date as resultDate,f.result_status as resultStatus,"
				+ " f.up_line_value as upLineValue,f.down_line_value as downLineValue,f.result,f.no_reson_result as noResonResul "
				+ " from hospital_fit_result f where f.sid = ?";
		List fits = jdbcTemplate.queryForList(sql,sid);
		return fits;
	}
	/**
	 * (国家受试者)查询粪便DNA编号列表
	 */
	@Override
	public List getStollDNAListByNationId(String sid) {
		// TODO Auto-generated method stub
		String sql = "select code_entry_status as codeEntryStatus,dna_code as dnaCode,dna_check_enter_status as dnaCheckStatus,"
				+ " dna_check_result as dnaCheckResult,dna_check_goal as dnaCheckGoal,dna_check_quantum as dnaCheckQuantnum,dna_check_filePath as dnaCheckFilePath"
				+ " from hospital_stool_dna where sid = ?";
		List stolls = jdbcTemplate.queryForList(sql,sid);
		return stolls;
	}
	/**
	 * (国家受试者)结肠镜列表
	 */
	@Override
	public List getColonoscopyRecordBy(String sid) {
		// TODO Auto-generated method stub
		String sql = "select id as id,sid as sid,pathology_id as pathology_id,result_id as result_id,reserve_status as reserverStatus,reserve_date as reserveDate,"
				+ " examination_status as examinationStatus,finished_status as finishedStatus,result_status as resultStatus,"
				+ " pathology_status as pathologyStatus,notification_entry_status as notificationEntryStatus,notification_issue_status as notificationIssueStatus "
				+ " from hospital_colonoscopy_record where sid = ?";
		List colonos = jdbcTemplate.queryForList(sql,sid);
		return colonos;
	}
	/**
	 * (国家受试者)根据sid查询生物样本
	 */
	@Override
	public List getSampleResult(String sid) {
		// TODO Auto-generated method stub

		String sql = "select t.id as asid,t.collect_status as sample_A,"
				+ "t4.id as msid,t4.collect_status as sample_M,t5.id as fsid,t5.collect_status as sample_F from "
			/*		+ " (select id, collect_status from hospital_biological_sample_result where sample_type = 'S' and sid ='"+sid+"') as t1,"
					+ "(select id, collect_status from hospital_biological_sample_result where sample_type = 'P' and sid = '"+sid+"') as t2,"
					+ "(select id, collect_status from hospital_biological_sample_result where sample_type = 'W' and sid = '"+sid+"') as t3,"*/
				+ "(select id, collect_status from hospital_biological_sample_result where sample_type = 'A' and sid = '"+sid+"') as t,"
				+ "(select id, collect_status from hospital_biological_sample_result where sample_type = 'M' and sid = '"+sid+"') as t4,"
				+ "(select id, collect_status from hospital_biological_sample_result where sample_type = 'F' and sid = '"+sid+"') as t5";
		List arrs = jdbcTemplate.queryForList(sql);
		return arrs;



				/*mps.put("asid", sets.add(map.get("asid").toString()) != false ? map.get("asid").toString() : null);
				if(sets.add(map.get("asid").toString()) == true){
					mps.put("sample_A", map.get("sample_A"));
				}else if(sets.add(map.get("asid").toString()) == false){
					mps.put("sample_A", map.get("sample_A"));
				}
				mps.put("msid", sets.add(map.get("msid").toString()) != false ? map.get("msid").toString() : null);
				if(sets.add(map.get("msid").toString()) == true){
					mps.put("sample_M", map.get("sample_M"));
				}else if(sets.add(map.get("msid").toString()) == false){
					mps.put("sample_M", map.get("sample_A"));
				}
				mps.put("fsid", sets.add(map.get("fsid").toString()) != false ? map.get("fsid").toString() : null);
				if(sets.add(map.get("fsid").toString()) == true){
					mps.put("sample_F", map.get("sample_F"));
				}else if(sets.add(map.get("fsid").toString()) == false){
					mps.put("sample_F", map.get("sample_A"));
				}*/
//				rs.add(mps);
			/*String sql_A = "select id as asid,collect_status as sample_A from hospital_biological_sample_result where sample_type = 'A' and sid = '"+sid+"'";
			List<Map<String,Object>> arr_a = jdbcTemplate.queryForList(sql_A);
			String jsonsa = arr_a.toString().substring(1,arr_a.toString().length());
			String sumdeptsa = jsonsa.substring(0, jsonsa.length()-1);
			rs.add(sumdeptsa);
			String sql_B = "select id as msid,collect_status as sample_M from hospital_biological_sample_result where sample_type = 'M' and sid = '"+sid+"'";
			List<Map<String,Object>> arr_b = jdbcTemplate.queryForList(sql_B);
			String jsonsb = arr_b.toString().substring(1,arr_b.toString().length());
			String sumdeptsb = jsonsb.substring(0, jsonsb.length()-1);
			rs.add(sumdeptsb);

			String sql_C = "select id as fsid,collect_status as sample_F from hospital_biological_sample_result where sample_type = 'F' and sid = '"+sid+"'";
			List<Map<String,Object>> arr_c = jdbcTemplate.queryForList(sql_C);
			String jsonsc = arr_c.toString().substring(1,arr_c.toString().length());
			String sumdeptsc = jsonsc.substring(0, jsonsc.length()-1);
			rs.add(sumdeptsc);*/

//			return rs;
	}
	/**
	 * 根据id进行查看生物样本
	 */
	@Override
	public List getSampleDetailById(int id) {
		// TODO Auto-generated method stub
		String sql = "select id ,sid,sample_id as sampleId,sample_type as sampleType,collect_status_date as collectDate,frozen_box_code as frozenboxCode,"
				+ "sample_column as sampleColumn,sample_line as sampleLine,sample_note as sampleNote from hospital_biological_sample_result "
				+ " where id = ?";
		List arrs = jdbcTemplate.queryForList(sql,id);
		return arrs;
	}
	/**
	 * 国家统计地区人数返回
	 */
	@Override
	public List getGroupSumsByNationId(int nationId, TodoVo vo) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> arerlists = getAreaByNationId(nationId);
		List<Map<String,Object>> amp = new ArrayList<Map<String,Object>>();
		String sql1 = "";
		String sql2 = "";
		String sql3 = "";
		String sql4 = "";
		String sql5 = "";
		String sql6 = "";
		String sql7 = "";
		String sql8 = "";
		String sql9 = ""; 	//粪便DNA检查已反馈
		String sql10 = "";	//粪便DNA检查未反馈
		String sql11 = "";	//粪便DNA检查未生成
		String sql12 = "";	//FIT结果无效
		if(!StringUtil.isEmpty(vo.getStartDate()) && !StringUtil.isEmpty(vo.getEndDate())){
			sql1 = " and date_format(in_group_date,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(in_group_date,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
			sql2 = " and date_format(t2.reserve_status_date,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.reserve_status_date,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
			sql3 = " and date_format(t2.examination_check_date,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.examination_check_date,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
			sql4 = " and date_format(t2.entry_time,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.entry_time,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
			sql5 = " and date_format(t2.code_entry_time,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.code_entry_time,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
			sql6 = " and date_format(t2.result_sys_date,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.result_sys_date,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
			sql7 = " and date_format(t2.result_sys_date,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.result_sys_date,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
			sql8 = " and date_format(t2.quit_date,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.quit_date,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
			sql9 = " and date_format(t2.dna_check_inform_nation_date,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.dna_check_inform_nation_date,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
			sql10 = " and date_format(t2.dna_check_enter_date,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.dna_check_enter_date,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
			sql11 = " and date_format(t2.entry_time,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.entry_time,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
			sql12 = " and date_format(t2.result_sys_date,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.result_sys_date,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
		}
		for (Map<String, Object> map : arerlists) {
			int sumByA1 = 0;
			int sumByA2 = 0;
			int sumByA3 = 0;
			int sumByA4 = 0;
			int sumByA5 = 0;
			int sumByA6 = 0;
			int sumByA7 = 0;
			int sumByA8 = 0;
			int sumByA9 = 0; 	//A组粪便DNA检查已反馈
			int sumByA10 = 0;	//A组粪便DNA检查未反馈
			int sumByA11 = 0;	//A组粪便DNA检查未生成
			int sumByA12 = 0;	//A组FIT无效数
			int sumByA13 = 0;	//A组FIT结果返回数 = 阳性+阴性+无效
			int sumByB1 = 0;
			int sumByB2 = 0;
			int sumByB3 = 0;
			int sumByB4 = 0;
			int sumByB5 = 0;
			int sumByB6 = 0;
			int sumByB7 = 0;
			int sumByB8 = 0;
			int sumByB9 = 0;	//B组粪便DNA检查已反馈
			int sumByB10 = 0;	//B组粪便DNA检查未反馈
			int sumByB11 = 0;	//B组粪便DNA检查未生成
			int sumByB12 = 0;	//B组FIT无效数
			int sumByB13 = 0;	//B组FIT结果返回数 = 阳性+阴性+无效
			int sumByCg1 = 0;
			int sumByCg2 = 0;
			int sumByCg3 = 0;
			int sumByCg4 = 0;
			int sumByCg5 = 0;
			int sumByCg6 = 0;
			int sumByCg7 = 0;
			int sumByCg8 = 0;
			int sumByCg9 = 0;	//C组高危粪便DNA检查已反馈
			int sumByCg10 = 0;	//C组高危粪便DNA检查未反馈
			int sumByCg11 = 0;	//C组高危粪便DNA检查未生成
			int sumByCg12 = 0;	//C组高危FIT无效数
			int sumByCg13 = 0;	//C组高危FIT结果返回数 = 阳性+阴性+无效
			int sumByCd1 = 0;
			int sumByCd2 = 0;
			int sumByCd3 = 0;
			int sumByCd4 = 0;
			int sumByCd5 = 0;
			int sumByCd6 = 0;
			int sumByCd7 = 0;
			int sumByCd8 = 0;
			int sumByCd9 = 0;	//C组低危粪便DNA检查已反馈
			int sumByCd10 = 0;	//C组低危粪便DNA检查未反馈
			int sumByCd11 = 0;	//C组低危粪便DNA检查未生成
			int sumByCd12 = 0;	//C组低危FIT无效数
			int sumByCd13 = 0;	//C组低危FIT结果返回数 = 阳性+阴性+无效
			int sumByCp1 = 0;
			int sumByCp2 = 0;
			int sumByCp3 = 0;
			int sumByCp4 = 0;
			int sumByCp5 = 0;
			int sumByCp6 = 0;
			int sumByCp7 = 0;
			int sumByCp8 = 0;
			int sumByCp9 = 0;	//C组未评估粪便DNA检查已反馈
			int sumByCp10 = 0;	//C组未评估粪便DNA检查未反馈
			int sumByCp11 = 0;	//C组未评估粪便DNA检查未生成
			int sumByCp12 = 0;	//C组未评估FIT无效数
			int sumByCp13 = 0;	//C组未评估FIT结果返回数 = 阳性+阴性+无效
			List<String> rs = new ArrayList<>();
			List<Map<String,Integer>> sumsByGroup = new ArrayList<Map<String,Integer>>();
			Map<String, Object> arrs = new LinkedHashMap<String,Object>();
			Map<String,Integer> ams = new LinkedHashMap<String,Integer>();
			int areaIds = Integer.parseInt(map.get("id").toString()) ;
			String areaName = map.get("name").toString();
			log.info("@Dao getGroupByAreaId query  Start ");
			//第一个A
			String msq1 = "select"
                    + " (select count(distinct(sid)) as groupByA from hospital_intestine_review where group_status = 2 and `group` = 'A' and area_dept_id = "+areaIds+" "+sql1+" ) as groupsNum ,"

                    //已预约肠镜检查人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_colonoscopy_record t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_colonoscopy_record ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'A' " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t2.reserve_status = 2 "+sql2+
                    " ) AS bookingColonscopyNum,"

                    //实际接受肠镜检查人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_colonoscopy_record t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_colonoscopy_record ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'A' " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t2.examination_status = 2 "+sql3+
                    ") AS inspectColonscopyNum,"

                    //接受粪便DNA检测人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_stool_dna t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT max(ss.id) " +
                    "  FROM hospital_stool_dna ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'A' " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t2.code_entry_status = 2 "+sql4+
                    ") AS DNANum,"

                    //实际接受FIT检查人数
                    + " (SELECT count(DISTINCT(t1.sid))" +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT max(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'A' " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t2.code_entry_status = 2 "+sql5+
                    ") AS FitNum,"

                    //FIT阳性人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'A' " +
                    " AND t1.area_dept_id = " +areaIds+sql6+
                    " AND t2.result = 2 " +
                    ") AS FitPositiveNum,"

                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'A' " +
                    " AND t1.area_dept_id = " +areaIds+sql7+
                    " AND t2.result = 1" +
                    ")as FitNegativeNum,"

                    //+ " (select count(distinct(t1.sid))as groupByA from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'A'  and t1.area_dept_id = "+areaIds+" and t2.result = 2 "+sql6+")as FitPositiveNum,"
                    //+ " (select count(distinct(t1.sid))as groupByA from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'A'  and t1.area_dept_id = "+areaIds+" and t2.result = 1 "+sql7+")as FitNegativeNum,"

                    //退出研究
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_intestine_review_quit_log t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT max(ss.id) " +
                    "  FROM hospital_intestine_review_quit_log ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'A' " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t1.overall_status_cy = 2 "+sql8+
                    ") AS SearchOutNum, "

                    //粪便DNA检查已反馈
                    + " ( SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_stool_dna t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_stool_dna ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'A' " +
                    " AND t1.area_dept_id = "+ areaIds + sql9 +
                    " AND t2.dna_check_inform_status = 2 " +
                    ") AS DNAFeedbackNum, "

                    //粪便DNA检查未反馈
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_stool_dna t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_stool_dna ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'A' " +
                    " AND t1.area_dept_id = " + areaIds + sql10 +
                    " AND ( " +
                    "  t2.dna_check_inform_status = 3 " +
                    "  OR t2.dna_check_inform_status = 1 " +
                    " ) " +
                    ") AS DNANoFeedbackNum,"

                    //粪便DNA检查未生成
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_stool_dna t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_stool_dna ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'A' " +
                    " AND t1.area_dept_id = " + areaIds + sql11 +
                    " AND t2.dna_check_enter_status = 1 " +
                    " AND t2.code_entry_status = 2 " +
                    ") AS DNANoGenerate,"

                    //FIT无效
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'A' " +
                    " AND t1.area_dept_id = " + areaIds + sql12 +
                    " AND t2.result = 3 " +
                    ") AS FitInvalidNum "
                    + " from dual;";
            log.info("msq1==>"+msq1);
			List<Map<String,Object>> list1 = jdbcTemplate.queryForList(msq1);
			for (Map<String, Object> ma : list1) {
				sumByA1 = Integer.parseInt(ma.get("groupsNum").toString());
				sumByA2 = Integer.parseInt(ma.get("bookingColonscopyNum").toString());
				sumByA3 = Integer.parseInt(ma.get("inspectColonscopyNum").toString());
				sumByA4 = Integer.parseInt(ma.get("DNANum").toString());
				sumByA5 = Integer.parseInt(ma.get("FitNum").toString());
				sumByA6 = Integer.parseInt(ma.get("FitPositiveNum").toString());
				sumByA7 = Integer.parseInt(ma.get("FitNegativeNum").toString());
				sumByA8 = Integer.parseInt(ma.get("SearchOutNum").toString());
				sumByA9 = Integer.parseInt(ma.get("DNAFeedbackNum").toString());
				sumByA10 = Integer.parseInt(ma.get("DNANoFeedbackNum").toString());
				sumByA11 = Integer.parseInt(ma.get("DNANoGenerate").toString());
				sumByA12 = Integer.parseInt(ma.get("FitInvalidNum").toString());
				sumByA13 = sumByA6 + sumByA7 + sumByA12;
                ma.put("FitResultReturnNum", sumByA13);
			};
			String jsons1 = list1.toString().substring(1,list1.toString().length());
			String sumdepts1 = jsons1.substring(0, jsons1.length()-1);
			rs.add(sumdepts1);
			//第二次B
			String msq2 = "select "
					+ " (select count(distinct(sid)) as groupByB from hospital_intestine_review  where group_status = 2 and `group` = 'B' and area_dept_id = "+areaIds+" "+sql1+") as groupsNum,"
                    //已预约肠镜检查人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_colonoscopy_record t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_colonoscopy_record ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'B' " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t2.reserve_status = 2 "+sql2+
                    " ) AS bookingColonscopyNum,"

                    //实际接受肠镜检查人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_colonoscopy_record t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_colonoscopy_record ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'B' " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t2.examination_status = 2 "+sql3+
                    ") AS inspectColonscopyNum,"

                    //接受粪便DNA检测人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_stool_dna t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT max(ss.id) " +
                    "  FROM hospital_stool_dna ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'B' " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t2.code_entry_status = 2 "+sql4+
                    ") AS DNANum,"

                    //实际接受FIT检查人数
                    + " (SELECT count(DISTINCT(t1.sid))" +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT max(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'B' " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t2.code_entry_status = 2 "+sql5+
                    ") AS FitNum,"

                    //FIT阳性人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'B' " +
                    " AND t1.area_dept_id = " +areaIds+sql6+
                    " AND t2.result = 2 " +
                    ") AS FitPositiveNum,"

                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'B' " +
                    " AND t1.area_dept_id = " +areaIds+sql7+
                    " AND t2.result = 1" +
                    ")as FitNegativeNum,"
					//+ " (select count(distinct(t1.sid))as groupByB from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'B'  and t1.area_dept_id = "+areaIds+" and t2.result = 2 "+sql6+")as FitPositiveNum,"
					//+ " (select count(distinct(t1.sid))as groupByB from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'B'  and t1.area_dept_id = "+areaIds+" and t2.result = 1 "+sql7+")as FitNegativeNum,"

                    //退出研究
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_intestine_review_quit_log t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT max(ss.id) " +
                    "  FROM hospital_intestine_review_quit_log ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'B' " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t1.overall_status_cy = 2 "+sql8+
                    ") AS SearchOutNum, "

                    //粪便DNA检查已反馈
                    + " ( SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_stool_dna t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_stool_dna ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'B' " +
                    " AND t1.area_dept_id = "+ areaIds + sql9 +
                    " AND t2.dna_check_inform_status = 2 " +
                    ") AS DNAFeedbackNum, "

                    //粪便DNA检查未反馈
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_stool_dna t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_stool_dna ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'B' " +
                    " AND t1.area_dept_id = " + areaIds + sql10 +
                    " AND ( " +
                    "  t2.dna_check_inform_status = 3 " +
                    "  OR t2.dna_check_inform_status = 1 " +
                    " ) " +
                    ") AS DNANoFeedbackNum,"

                    //粪便DNA检查未生成
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_stool_dna t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_stool_dna ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'B' " +
                    " AND t1.area_dept_id = " + areaIds + sql11 +
                    " AND t2.dna_check_enter_status = 1 " +
                    " AND t2.code_entry_status = 2 " +
                    ") AS DNANoGenerate,"

                    //FIT无效
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'B' " +
                    " AND t1.area_dept_id = " + areaIds + sql12 +
                    " AND t2.result = 3 " +
                    ") AS FitInvalidNum "
					+ " from dual;";
            log.info("msq1=>"+msq2);
			List<Map<String,Object>> list2 = jdbcTemplate.queryForList(msq2);
			for (Map<String, Object> mb : list2) {
				sumByB1 = Integer.parseInt(mb.get("groupsNum").toString());
				sumByB2 = Integer.parseInt(mb.get("bookingColonscopyNum").toString());
				sumByB3 = Integer.parseInt(mb.get("inspectColonscopyNum").toString());
				sumByB4 = Integer.parseInt(mb.get("DNANum").toString());
				sumByB5 = Integer.parseInt(mb.get("FitNum").toString());
				sumByB6 = Integer.parseInt(mb.get("FitPositiveNum").toString());
				sumByB7 = Integer.parseInt(mb.get("FitNegativeNum").toString());
				sumByB8 = Integer.parseInt(mb.get("SearchOutNum").toString());
				sumByB9 = Integer.parseInt(mb.get("DNAFeedbackNum").toString());
				sumByB10 = Integer.parseInt(mb.get("DNANoFeedbackNum").toString());
				sumByB11 = Integer.parseInt(mb.get("DNANoGenerate").toString());
				sumByB12 = Integer.parseInt(mb.get("FitInvalidNum").toString());
				sumByB13 = sumByB6 + sumByB7 + sumByB12;
                mb.put("FitResultReturnNum", sumByB13);
			}
			String jsons2 = list2.toString().substring(1,list2.toString().length());
			String sumdepts2 = jsons2.substring(0, jsons2.length()-1);
			rs.add(sumdepts2);
			//第三次C高危
			String msq3 = "select "
					+ " (select count(distinct(sid)) as groupByC from hospital_intestine_review where group_status = 2 and `group` = 'C' and risk_level = 2 and area_dept_id = "+areaIds+" "+sql1+") as groupsNum,"
                    //已预约肠镜检查人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_colonoscopy_record t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_colonoscopy_record ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 2 " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t2.reserve_status = 2 "+sql2+
                    " ) AS bookingColonscopyNum,"

                    //实际接受肠镜检查人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_colonoscopy_record t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_colonoscopy_record ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 2 " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t2.examination_status = 2 "+sql3+
                    ") AS inspectColonscopyNum,"

                    //接受粪便DNA检测人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_stool_dna t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT max(ss.id) " +
                    "  FROM hospital_stool_dna ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 2 " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t2.code_entry_status = 2 "+sql4+
                    ") AS DNANum,"

                    //实际接受FIT检查人数
                    + " (SELECT count(DISTINCT(t1.sid))" +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT max(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 2 " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t2.code_entry_status = 2 "+sql5+
                    ") AS FitNum,"

                    //FIT阳性人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 2 " +
                    " AND t1.area_dept_id = " +areaIds+sql6+
                    " AND t2.result = 2 " +
                    ") AS FitPositiveNum,"

                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 2 " +
                    " AND t1.area_dept_id = " +areaIds+sql7+
                    " AND t2.result = 1" +
                    ")as FitNegativeNum,"
					//+ " (select count(distinct(t1.sid))as groupByC from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'C'  and t1.area_dept_id = "+areaIds+" and t2.result = 2 and t1.risk_level = 2 "+sql6+")as FitPositiveNum,"
					//+ " (select count(distinct(t1.sid))as groupByC from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'C'  and t1.area_dept_id = "+areaIds+" and t2.result = 1 and t1.risk_level = 2 "+sql7+")as FitNegativeNum, "

                    //退出研究
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_intestine_review_quit_log t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT max(ss.id) " +
                    "  FROM hospital_intestine_review_quit_log ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 2 " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t1.overall_status_cy = 2 "+sql8+
                    ") AS SearchOutNum, "

                    //粪便DNA检查已反馈
                    + " ( SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_stool_dna t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_stool_dna ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 2 " +
                    " AND t1.area_dept_id = "+ areaIds + sql9 +
                    " AND t2.dna_check_inform_status = 2 " +
                    ") AS DNAFeedbackNum, "

                    //粪便DNA检查未反馈
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_stool_dna t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_stool_dna ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 2 " +
                    " AND t1.area_dept_id = " + areaIds + sql10 +
                    " AND ( " +
                    "  t2.dna_check_inform_status = 3 " +
                    "  OR t2.dna_check_inform_status = 1 " +
                    " ) " +
                    ") AS DNANoFeedbackNum,"

                    //粪便DNA检查未生成
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_stool_dna t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_stool_dna ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 2 " +
                    " AND t1.area_dept_id = " + areaIds + sql11 +
                    " AND t2.dna_check_enter_status = 1 " +
                    " AND t2.code_entry_status = 2 " +
                    ") AS DNANoGenerate,"

                    //FIT无效
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 2 " +
                    " AND t1.area_dept_id = " + areaIds + sql12 +
                    " AND t2.result = 3 " +
                    ") AS FitInvalidNum "
					+ " from dual;";
			log.info("sql==>"+msq3);
			List<Map<String,Object>> list3 = jdbcTemplate.queryForList(msq3);
			for (Map<String, Object> mc1 : list3) {
				sumByCg1 = Integer.parseInt(mc1.get("groupsNum").toString());
				sumByCg2 = Integer.parseInt(mc1.get("bookingColonscopyNum").toString());
				sumByCg3 = Integer.parseInt(mc1.get("inspectColonscopyNum").toString());
				sumByCg4 = Integer.parseInt(mc1.get("DNANum").toString());
				sumByCg5 = Integer.parseInt(mc1.get("FitNum").toString());
				sumByCg6 = Integer.parseInt(mc1.get("FitPositiveNum").toString());
				sumByCg7 = Integer.parseInt(mc1.get("FitNegativeNum").toString());
				sumByCg8 = Integer.parseInt(mc1.get("SearchOutNum").toString());
				sumByCg9 = Integer.parseInt(mc1.get("DNAFeedbackNum").toString());
				sumByCg10 = Integer.parseInt(mc1.get("DNANoFeedbackNum").toString());
				sumByCg11 = Integer.parseInt(mc1.get("DNANoGenerate").toString());
				sumByCg12 = Integer.parseInt(mc1.get("FitInvalidNum").toString());
				sumByCg13 = sumByCg6 + sumByCg7 + sumByCg12;
                mc1.put("FitResultReturnNum", sumByCg13);
			}
			String jsons3 = list3.toString().substring(1,list3.toString().length());
			String sumdepts3 = jsons3.substring(0, jsons3.length()-1);
			rs.add(sumdepts3);
			//接受粪便DNA人数
			//第四次低危
			String msq4 = "select "
					+ " (select count(distinct(sid)) as groupByC from hospital_intestine_review where group_status = 2 and `group` = 'C' and risk_level = 1 and area_dept_id = "+areaIds+" "+sql1+") as groupsNum,"
                    //已预约肠镜检查人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_colonoscopy_record t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_colonoscopy_record ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 1 " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t2.reserve_status = 2 "+sql2+
                    " ) AS bookingColonscopyNum,"

                    //实际接受肠镜检查人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_colonoscopy_record t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_colonoscopy_record ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 1 " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t2.examination_status = 2 "+sql3+
                    ") AS inspectColonscopyNum,"

                    //接受粪便DNA检测人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_stool_dna t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT max(ss.id) " +
                    "  FROM hospital_stool_dna ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 1 " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t2.code_entry_status = 2 "+sql4+
                    ") AS DNANum,"

                    //实际接受FIT检查人数
                    + " (SELECT count(DISTINCT(t1.sid))" +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT max(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 1 " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t2.code_entry_status = 2 "+sql5+
                    ") AS FitNum,"

                    //FIT阳性人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 1 " +
                    " AND t1.area_dept_id = " +areaIds+sql6+
                    " AND t2.result = 2 " +
                    ") AS FitPositiveNum,"

                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 1 " +
                    " AND t1.area_dept_id = " +areaIds+sql7+
                    " AND t2.result = 1" +
                    ")as FitNegativeNum,"
					//+ " (select count(distinct(t1.sid))as groupByC from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'C'  and t1.area_dept_id = "+areaIds+" and t2.result = 2 and t1.risk_level = 1 "+sql6+")as FitPositiveNum,"
					//+ " (select count(distinct(t1.sid))as groupByC from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'C'  and t1.area_dept_id = "+areaIds+" and t2.result = 1 and t1.risk_level = 1 "+sql7+")as FitNegativeNum,"

                    //退出研究
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_intestine_review_quit_log t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT max(ss.id) " +
                    "  FROM hospital_intestine_review_quit_log ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 1 " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t1.overall_status_cy = 2 "+sql8+
                    ") AS SearchOutNum, "

                    //粪便DNA检查已反馈
                    + " ( SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_stool_dna t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_stool_dna ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 1 " +
                    " AND t1.area_dept_id = "+ areaIds + sql9 +
                    " AND t2.dna_check_inform_status = 2 " +
                    ") AS DNAFeedbackNum, "

                    //粪便DNA检查未反馈
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_stool_dna t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_stool_dna ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 1 " +
                    " AND t1.area_dept_id = " + areaIds + sql10 +
                    " AND ( " +
                    "  t2.dna_check_inform_status = 3 " +
                    "  OR t2.dna_check_inform_status = 1 " +
                    " ) " +
                    ") AS DNANoFeedbackNum,"

                    //粪便DNA检查未生成
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_stool_dna t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_stool_dna ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 1 " +
                    " AND t1.area_dept_id = " + areaIds + sql11 +
                    " AND t2.dna_check_enter_status = 1 " +
                    " AND t2.code_entry_status = 2 " +
                    ") AS DNANoGenerate,"

                    //FIT无效
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 1 " +
                    " AND t1.area_dept_id = " + areaIds + sql12 +
                    " AND t2.result = 3 " +
                    ") AS FitInvalidNum "
					+ " from dual;";
			log.info("sql==>"+msq4);
			List<Map<String,Object>> list4 = jdbcTemplate.queryForList(msq4);
			for (Map<String, Object> mc2 : list4) {
				sumByCd1 = Integer.parseInt(mc2.get("groupsNum").toString());
				sumByCd2 = Integer.parseInt(mc2.get("bookingColonscopyNum").toString());
				sumByCd3 = Integer.parseInt(mc2.get("inspectColonscopyNum").toString());
				sumByCd4 = Integer.parseInt(mc2.get("DNANum").toString());
				sumByCd5 = Integer.parseInt(mc2.get("FitNum").toString());
				sumByCd6 = Integer.parseInt(mc2.get("FitPositiveNum").toString());
				sumByCd7 = Integer.parseInt(mc2.get("FitNegativeNum").toString());
				sumByCd8 = Integer.parseInt(mc2.get("SearchOutNum").toString());
				sumByCd9 = Integer.parseInt(mc2.get("DNAFeedbackNum").toString());
				sumByCd10 = Integer.parseInt(mc2.get("DNANoFeedbackNum").toString());
				sumByCd11 = Integer.parseInt(mc2.get("DNANoGenerate").toString());
				sumByCd12 = Integer.parseInt(mc2.get("FitInvalidNum").toString());
				sumByCd13 = sumByCd6 + sumByCd7 + sumByCd12;
                mc2.put("FitResultReturnNum", sumByCd13);
			}
			String jsons4 = list4.toString().substring(1,list4.toString().length());
			String sumdepts4 = jsons4.substring(0, jsons4.length()-1);
			rs.add(sumdepts4);
			//实际接受FIT检查人数
			//第五次未评估
			String msq5 = "select "
					+ " (select count(distinct(sid)) as groupByC from hospital_intestine_review where group_status = 2 and `group` = 'C' and risk_level  is null and area_dept_id = "+areaIds+" "+sql1+")as groupsNum,"
                    //已预约肠镜检查人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_colonoscopy_record t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_colonoscopy_record ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level is null " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t2.reserve_status = 2 "+sql2+
                    " ) AS bookingColonscopyNum,"

                    //实际接受肠镜检查人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_colonoscopy_record t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_colonoscopy_record ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level is null " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t2.examination_status = 2 "+sql3+
                    ") AS inspectColonscopyNum,"

                    //接受粪便DNA检测人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_stool_dna t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT max(ss.id) " +
                    "  FROM hospital_stool_dna ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level is null " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t2.code_entry_status = 2 "+sql4+
                    ") AS DNANum,"

                    //实际接受FIT检查人数
                    + " (SELECT count(DISTINCT(t1.sid))" +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT max(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level is null " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t2.code_entry_status = 2 "+sql5+
                    ") AS FitNum,"

                    //FIT阳性人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level is null " +
                    " AND t1.area_dept_id = " +areaIds+sql6+
                    " AND t2.result = 2 " +
                    ") AS FitPositiveNum,"

                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level is null " +
                    " AND t1.area_dept_id = " +areaIds+sql7+
                    " AND t2.result = 1" +
                    ")as FitNegativeNum,"
					//+ " (select count(distinct(t1.sid))as groupByC from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'C'  and t1.area_dept_id = "+areaIds+" and t2.result = 2 and t1.risk_level is null "+sql6+") as FitPositiveNum,"
					//+ " (select count(distinct(t1.sid))as groupByC from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'C'  and t1.area_dept_id = "+areaIds+" and t2.result = 1 and t1.risk_level is null "+sql7+") as FitNegativeNum,"

                    //退出研究
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_intestine_review_quit_log t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT max(ss.id) " +
                    "  FROM hospital_intestine_review_quit_log ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level is null " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t1.overall_status_cy = 2 "+sql8+
                    ") AS SearchOutNum, "

                    //粪便DNA检查已反馈
                    + " ( SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_stool_dna t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_stool_dna ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level is null " +
                    " AND t1.area_dept_id = "+ areaIds + sql9 +
                    " AND t2.dna_check_inform_status = 2 " +
                    ") AS DNAFeedbackNum, "

                    //粪便DNA检查未反馈
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_stool_dna t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_stool_dna ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level is null " +
                    " AND t1.area_dept_id = " + areaIds + sql10 +
                    " AND ( " +
                    "  t2.dna_check_inform_status = 3 " +
                    "  OR t2.dna_check_inform_status = 1 " +
                    " ) " +
                    ") AS DNANoFeedbackNum,"

                    //粪便DNA检查未生成
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_stool_dna t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_stool_dna ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level is null " +
                    " AND t1.area_dept_id = " + areaIds + sql11 +
                    " AND t2.dna_check_enter_status = 1 " +
                    " AND t2.code_entry_status = 2 " +
                    ") AS DNANoGenerate,"

                    //FIT无效
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level is null " +
                    " AND t1.area_dept_id = " + areaIds + sql12 +
                    " AND t2.result = 3 " +
                    ") AS FitInvalidNum "
					+ " from dual;";
			log.info("sql==>"+msq5);
			List<Map<String,Object>> list5 = jdbcTemplate.queryForList(msq5);
			for (Map<String, Object> mc3 : list5) {
				sumByCp1 = Integer.parseInt(mc3.get("groupsNum").toString());
				sumByCp2 = Integer.parseInt(mc3.get("bookingColonscopyNum").toString());
				sumByCp3 = Integer.parseInt(mc3.get("inspectColonscopyNum").toString());
				sumByCp4 = Integer.parseInt(mc3.get("DNANum").toString());
				sumByCp5 = Integer.parseInt(mc3.get("FitNum").toString());
				sumByCp6 = Integer.parseInt(mc3.get("FitPositiveNum").toString());
				sumByCp7 = Integer.parseInt(mc3.get("FitNegativeNum").toString());
				sumByCp8 = Integer.parseInt(mc3.get("SearchOutNum").toString());
				sumByCp9 = Integer.parseInt(mc3.get("DNAFeedbackNum").toString());
				sumByCp10 = Integer.parseInt(mc3.get("DNANoFeedbackNum").toString());
				sumByCp11 = Integer.parseInt(mc3.get("DNANoGenerate").toString());
				sumByCp12 = Integer.parseInt(mc3.get("FitInvalidNum").toString());
				sumByCp13 = sumByCp6 + sumByCp7 + sumByCp12;
                mc3.put("FitResultReturnNum", sumByCp13);
			}
			String jsons5 = list5.toString().substring(1,list5.toString().length());
			String sumdepts5 = jsons5.substring(0, jsons5.length()-1);
			rs.add(sumdepts5);
			int sums1 = sumByA1 + sumByB1 + sumByCg1 + sumByCd1 + sumByCp1;   //统计入组总人数
			int sums2 = sumByA2 + sumByB2 + sumByCg2 + sumByCd2 + sumByCp2;   //已预约肠镜检查人数
			int sums3 = sumByA3 + sumByB3 + sumByCg3 + sumByCd3 + sumByCp3;   //实际接受肠镜检查人数
			int sums4 = sumByA4 + sumByB4 + sumByCg4 + sumByCd4 + sumByCp4;   //接受粪便DNA检测人数
			int sums5 = sumByA5 + sumByB5 + sumByCg5 + sumByCd5 + sumByCp5;   //实际接受FIT检查人数
			int sums6 = sumByA6 + sumByB6 + sumByCg6 + sumByCd6 + sumByCp6;   //FIT阳性人数
			int sums7 = sumByA7 + sumByB7 + sumByCg7 + sumByCd7 + sumByCp7;   //FIT阴性人数
			int sums8 = sumByA8 + sumByB8 + sumByCg8 + sumByCd8 + sumByCp8;   //退出总人数
			int sums9 = sumByA9 + sumByB9 + sumByCg9 + sumByCd9 + sumByCp9;   //粪便DNA检查已反馈总人数
			int sums10 = sumByA10 + sumByB10 + sumByCg10 + sumByCd10 + sumByCp10;   //粪便DNA检查未反馈总人数
			int sums11 = sumByA11 + sumByB11 + sumByCg11 + sumByCd11 + sumByCp11;   //粪便DNA检查未生成总人数
			int sums12 = sumByA12 + sumByB12 + sumByCg12 + sumByCd12 + sumByCp12;   //FIT阴性数总人数
			int sums13 = sumByA13 + sumByB13 + sumByCg13 + sumByCd13 + sumByCp13;   //FIT结果返回总人数
			ams.put("groupsNum", sums1);
			ams.put("bookingColonscopyNum", sums2);
			ams.put("inspectColonscopyNum", sums3);
			ams.put("DNANum", sums4);
			ams.put("FitNum", sums5);
			ams.put("FitPositiveNum", sums6);
			ams.put("FitNegativeNum", sums7);
			ams.put("SearchOutNum", sums8);
			ams.put("DNAFeedbackNum",sums9);
			ams.put("DNANoFeedbackNum",sums10);
			ams.put("DNANoGenerate",sums11);
			ams.put("FitInvalidNum",sums12);
			ams.put("FitResultReturnNum",sums13);
			sumsByGroup.add(ams);
			//统计总人数
			String jsons6 = sumsByGroup.toString().substring(1,sumsByGroup.toString().length());
			String sumdepts6 = jsons6.substring(0, jsons6.length()-1);
			rs.add(sumdepts6);

			//存放数据格式
			arrs.put("areaName", areaName);
			arrs.put("list", rs);
			amp.add(arrs);
		}
		return amp;
	}
    /**
     * 国家统计地区各组人数返回
     */
    @Override
    public List getGroupSumsByNationId1(int nationId, TodoVo vo) {
        // TODO Auto-generated method stub
        List<Map<String, Object>> arerlists1 = getAreaByNationId(nationId);
        List<Map<String, Object>> arerlists = getAreaByAreaId(nationId);
        List<Map<String,Object>> amp = new ArrayList<Map<String,Object>>();
        String sql1 = "";
        String sql2 = "";
        String sql3 = "";
        String sql4 = "";
        String sql5 = "";
        String sql6 = "";
        String sql7 = "";
        String sql8 = "";
		String sql9 = ""; 	//粪便DNA检查已反馈
		String sql10 = "";	//粪便DNA检查未反馈
		String sql11 = "";	//粪便DNA检查未生成
		String sql12 = "";	//FIT结果无效
        if(!StringUtil.isEmpty(vo.getStartDate()) && !StringUtil.isEmpty(vo.getEndDate())){
            sql1 = " and date_format(in_group_date,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(in_group_date,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
            sql2 = " and date_format(t2.reserve_status_date,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.reserve_status_date,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
            sql3 = " and date_format(t2.examination_check_date,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.examination_check_date,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
            sql4 = " and date_format(t2.entry_time,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.entry_time,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
            sql5 = " and date_format(t2.code_entry_time,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.code_entry_time,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
            sql6 = " and date_format(t2.result_sys_date,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.result_sys_date,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
            sql7 = " and date_format(t2.result_sys_date,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.result_sys_date,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
            sql8 = " and date_format(t2.quit_date,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.quit_date,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
			sql9 = " and date_format(t2.dna_check_inform_nation_date,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.dna_check_inform_nation_date,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
			sql10 = " and date_format(t2.dna_check_enter_date,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.dna_check_enter_date,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
			sql11 = " and date_format(t2.entry_time,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.entry_time,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
			sql12 = " and date_format(t2.result_sys_date,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.result_sys_date,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
		}
        for (Map<String, Object> map : arerlists) {
            int sumByA1 = 0;
            int sumByA2 = 0;
            int sumByA3 = 0;
            int sumByA4 = 0;
            int sumByA5 = 0;
            int sumByA6 = 0;
            int sumByA7 = 0;
            int sumByA8 = 0;
			int sumByA9 = 0; 	//A组粪便DNA检查已反馈
			int sumByA10 = 0;	//A组粪便DNA检查未反馈
			int sumByA11 = 0;	//A组粪便DNA检查未生成
			int sumByA12 = 0;	//A组FIT无效数
			int sumByA13 = 0;	//A组FIT结果返回数 = 阳性+阴性+无效
            int sumByB1 = 0;
            int sumByB2 = 0;
            int sumByB3 = 0;
            int sumByB4 = 0;
            int sumByB5 = 0;
            int sumByB6 = 0;
            int sumByB7 = 0;
            int sumByB8 = 0;
			int sumByB9 = 0;	//B组粪便DNA检查已反馈
			int sumByB10 = 0;	//B组粪便DNA检查未反馈
			int sumByB11 = 0;	//B组粪便DNA检查未生成
			int sumByB12 = 0;	//B组FIT无效数
			int sumByB13 = 0;	//B组FIT结果返回数 = 阳性+阴性+无效
            int sumByCg1 = 0;
            int sumByCg2 = 0;
            int sumByCg3 = 0;
            int sumByCg4 = 0;
            int sumByCg5 = 0;
            int sumByCg6 = 0;
            int sumByCg7 = 0;
            int sumByCg8 = 0;
			int sumByCg9 = 0;	//C组高危粪便DNA检查已反馈
			int sumByCg10 = 0;	//C组高危粪便DNA检查未反馈
			int sumByCg11 = 0;	//C组高危粪便DNA检查未生成
			int sumByCg12 = 0;	//C组高危FIT无效数
			int sumByCg13 = 0;	//C组高危FIT结果返回数 = 阳性+阴性+无效
            int sumByCd1 = 0;
            int sumByCd2 = 0;
            int sumByCd3 = 0;
            int sumByCd4 = 0;
            int sumByCd5 = 0;
            int sumByCd6 = 0;
            int sumByCd7 = 0;
            int sumByCd8 = 0;
			int sumByCd9 = 0;	//C组低危粪便DNA检查已反馈
			int sumByCd10 = 0;	//C组低危粪便DNA检查未反馈
			int sumByCd11 = 0;	//C组低危粪便DNA检查未生成
			int sumByCd12 = 0;	//C组低危FIT无效数
			int sumByCd13 = 0;	//C组低危FIT结果返回数 = 阳性+阴性+无效
            int sumByCp1 = 0;
            int sumByCp2 = 0;
            int sumByCp3 = 0;
            int sumByCp4 = 0;
            int sumByCp5 = 0;
            int sumByCp6 = 0;
            int sumByCp7 = 0;
            int sumByCp8 = 0;
			int sumByCp9 = 0;	//C组未评估粪便DNA检查已反馈
			int sumByCp10 = 0;	//C组未评估粪便DNA检查未反馈
			int sumByCp11 = 0;	//C组未评估粪便DNA检查未生成
			int sumByCp12 = 0;	//C组未评估FIT无效数
			int sumByCp13 = 0;	//C组未评估FIT结果返回数 = 阳性+阴性+无效

            List<String> rs = new ArrayList<>();
            List<Map<String,Integer>> sumsByGroup = new ArrayList<Map<String,Integer>>();
            List<Map<String,Integer>> sumsByCountry = new ArrayList<>();
            Map<String, Object> arrs = new LinkedHashMap<String,Object>();
            Map<String,Integer> ams = new LinkedHashMap<String,Integer>();
            Map<String,Integer> c_map= new LinkedHashMap<String,Integer>();
            int nationsId = Integer.parseInt(map.get("id").toString()) ;
            List<Map<String, Object>> areasIds = getAreaByNationId(nationsId);
            String areaName = map.get("name").toString();
            int c_sumsa1 = 0;
            int c_sumsb1 = 0;
            int c_sumscg1 = 0;
            int c_sumscd1 = 0;
            int c_sumscp1 = 0;
            int c_sumst1 = 0;
            int c_sumsa2= 0;
            int c_sumsb2 = 0;
            int c_sumscg2 = 0;
            int c_sumscd2 = 0;
            int c_sumscp2 = 0;
            int c_sumst2 = 0;
            int c_sumsa3= 0;
            int c_sumsb3 = 0;
            int c_sumscg3 = 0;
            int c_sumscd3 = 0;
            int c_sumscp3 = 0;
            int c_sumst3 = 0;
            int c_sumsa4= 0;
            int c_sumsb4 = 0;
            int c_sumscg4 = 0;
            int c_sumscd4 = 0;
            int c_sumscp4 = 0;
            int c_sumst4 = 0;
            int c_sumsa5= 0;
            int c_sumsb5 = 0;
            int c_sumscg5 = 0;
            int c_sumscd5 = 0;
            int c_sumscp5 = 0;
            int c_sumst5 = 0;
            int c_sumsa6= 0;
            int c_sumsb6 = 0;
            int c_sumscg6 = 0;
            int c_sumscd6 = 0;
            int c_sumscp6 = 0;
            int c_sumst6 = 0;
            int c_sumsa7= 0;
            int c_sumsb7 = 0;
            int c_sumscg7 = 0;
            int c_sumscd7 = 0;
            int c_sumscp7 = 0;
            int c_sumst7 = 0;
            int c_sumsa8= 0;
            int c_sumsb8 = 0;
            int c_sumscg8 = 0;
            int c_sumscd8 = 0;
            int c_sumscp8 = 0;
            int c_sumst8 = 0;
			int c_sumsa9= 0;
			int c_sumsb9 = 0;
			int c_sumscg9 = 0;
			int c_sumscd9 = 0;
			int c_sumscp9 = 0;
			int c_sumst9 = 0;
			int c_sumsa10= 0;
			int c_sumsb10 = 0;
			int c_sumscg10 = 0;
			int c_sumscd10 = 0;
			int c_sumscp10 = 0;
			int c_sumst10 = 0;
			int c_sumsa11= 0;
			int c_sumsb11 = 0;
			int c_sumscg11 = 0;
			int c_sumscd11 = 0;
			int c_sumscp11 = 0;
			int c_sumst11 = 0;
			int c_sumsa12= 0;
			int c_sumsb12 = 0;
			int c_sumscg12 = 0;
			int c_sumscd12 = 0;
			int c_sumscp12 = 0;
			int c_sumst12 = 0;
			int c_sumsa13= 0;
			int c_sumsb13 = 0;
			int c_sumscg13 = 0;
			int c_sumscd13 = 0;
			int c_sumscp13 = 0;
			int c_sumst13 = 0;
            for(Map<String, Object> mapareas : areasIds){
//                int areaIds = Integer.parseInt(areasIds.get(0).get("id").toString());
                int areaIds = Integer.parseInt(mapareas.get("id").toString());
                log.info("@Dao getGroupByAreaId query  Start ");
                //第一个A
                String msq1 = "select"
                        + " (select count(distinct(sid)) as groupByA from hospital_intestine_review where group_status = 2 and `group` = 'A' and area_dept_id = "+areaIds+" "+sql1+" ) as groupsNum ,"
                        //已预约肠镜检查人数
                        + " (SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_colonoscopy_record t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT MAX(ss.id) " +
                        "  FROM hospital_colonoscopy_record ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'A' " +
                        " AND t1.area_dept_id = "+areaIds+
                        " AND t2.reserve_status = 2 "+sql2+
                        " ) AS bookingColonscopyNum,"

                        //实际接受肠镜检查人数
                        + " (SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_colonoscopy_record t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT MAX(ss.id) " +
                        "  FROM hospital_colonoscopy_record ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'A' " +
                        " AND t1.area_dept_id = "+areaIds+
                        " AND t2.examination_status = 2 "+sql3+
                        ") AS inspectColonscopyNum,"

                        //接受粪便DNA检测人数
                        + " (SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_stool_dna t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT max(ss.id) " +
                        "  FROM hospital_stool_dna ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'A' " +
                        " AND t1.area_dept_id = "+areaIds+
                        " AND t2.code_entry_status = 2 "+sql4+
                        ") AS DNANum,"

                        //实际接受FIT检查人数
                        + " (SELECT count(DISTINCT(t1.sid))" +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_fit_result t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT max(ss.id) " +
                        "  FROM hospital_fit_result ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'A' " +
                        " AND t1.area_dept_id = "+areaIds+
                        " AND t2.code_entry_status = 2 "+sql5+
                        ") AS FitNum,"

                        //FIT阳性人数
                        + " (SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_fit_result t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT MAX(ss.id) " +
                        "  FROM hospital_fit_result ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'A' " +
                        " AND t1.area_dept_id = " +areaIds+sql6+
                        " AND t2.result = 2 " +
                        ") AS FitPositiveNum,"

                        + " (SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_fit_result t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT MAX(ss.id) " +
                        "  FROM hospital_fit_result ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'A' " +
                        " AND t1.area_dept_id = " +areaIds+sql7+
                        " AND t2.result = 1" +
                        ")as FitNegativeNum,"
						//+ " (select count(distinct(t1.sid))as groupByA from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'A'  and t1.area_dept_id = "+areaIds+" and t2.result = 2 "+sql6+")as FitPositiveNum,"
                        //+ " (select count(distinct(t1.sid))as groupByA from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'A'  and t1.area_dept_id = "+areaIds+" and t2.result = 1 "+sql7+")as FitNegativeNum,"

                        //退出研究
                        + " (SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_intestine_review_quit_log t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT max(ss.id) " +
                        "  FROM hospital_intestine_review_quit_log ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'A' " +
                        " AND t1.area_dept_id = "+areaIds+
                        " AND t1.overall_status_cy = 2 "+sql8+
                        ") AS SearchOutNum, "

                        //粪便DNA检查已反馈
                        + " ( SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_stool_dna t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT MAX(ss.id) " +
                        "  FROM hospital_stool_dna ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'A' " +
                        " AND t1.area_dept_id = "+ areaIds + sql9 +
                        " AND t2.dna_check_inform_status = 2 " +
                        ") AS DNAFeedbackNum, "

                        //粪便DNA检查未反馈
                        + " (SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_stool_dna t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT MAX(ss.id) " +
                        "  FROM hospital_stool_dna ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'A' " +
                        " AND t1.area_dept_id = " + areaIds + sql10 +
                        " AND ( " +
                        "  t2.dna_check_inform_status = 3 " +
                        "  OR t2.dna_check_inform_status = 1 " +
                        " ) " +
                        ") AS DNANoFeedbackNum,"

                        //粪便DNA检查未生成
                        + " (SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_stool_dna t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT MAX(ss.id) " +
                        "  FROM hospital_stool_dna ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'A' " +
                        " AND t1.area_dept_id = " + areaIds + sql11 +
                        " AND t2.dna_check_enter_status = 1 " +
                        " AND t2.code_entry_status = 2 " +
                        ") AS DNANoGenerate,"

                        //FIT无效
                        + " (SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_fit_result t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT MAX(ss.id) " +
                        "  FROM hospital_fit_result ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'A' " +
                        " AND t1.area_dept_id = " + areaIds + sql12 +
                        " AND t2.result = 3 " +
                        ") AS FitInvalidNum "
                        + " from dual;";
                log.info("msq1==>"+msq1);
                List<Map<String,Object>> list1 = jdbcTemplate.queryForList(msq1);
                for (Map<String, Object> ma : list1) {
                    sumByA1 = Integer.parseInt(ma.get("groupsNum").toString());
                    sumByA2 = Integer.parseInt(ma.get("bookingColonscopyNum").toString());
                    sumByA3 = Integer.parseInt(ma.get("inspectColonscopyNum").toString());
                    sumByA4 = Integer.parseInt(ma.get("DNANum").toString());
                    sumByA5 = Integer.parseInt(ma.get("FitNum").toString());
                    sumByA6 = Integer.parseInt(ma.get("FitPositiveNum").toString());
                    sumByA7 = Integer.parseInt(ma.get("FitNegativeNum").toString());
                    sumByA8 = Integer.parseInt(ma.get("SearchOutNum").toString());
					sumByA9 = Integer.parseInt(ma.get("DNAFeedbackNum").toString());
					sumByA10 = Integer.parseInt(ma.get("DNANoFeedbackNum").toString());
					sumByA11 = Integer.parseInt(ma.get("DNANoGenerate").toString());
					sumByA12 = Integer.parseInt(ma.get("FitInvalidNum").toString());
					sumByA13 = sumByA6 + sumByA7 + sumByA12;
                    ma.put("FitResultReturnNum",sumByA13);
                };
                String jsons1 = list1.toString().substring(1,list1.toString().length());
                String sumdepts1 = jsons1.substring(0, jsons1.length()-1);
//                rs.add(sumdepts1);
                //第二次B
                String msq2 = "select "
                        + " (select count(distinct(sid)) as groupByB from hospital_intestine_review  where group_status = 2 and `group` = 'B' and area_dept_id = "+areaIds+" "+sql1+") as groupsNum,"
                        //已预约肠镜检查人数
                        + " (SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_colonoscopy_record t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT MAX(ss.id) " +
                        "  FROM hospital_colonoscopy_record ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'B' " +
                        " AND t1.area_dept_id = "+areaIds+
                        " AND t2.reserve_status = 2 "+sql2+
                        " ) AS bookingColonscopyNum,"

                        //实际接受肠镜检查人数
                        + " (SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_colonoscopy_record t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT MAX(ss.id) " +
                        "  FROM hospital_colonoscopy_record ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'B' " +
                        " AND t1.area_dept_id = "+areaIds+
                        " AND t2.examination_status = 2 "+sql3+
                        ") AS inspectColonscopyNum,"

                        //接受粪便DNA检测人数
                        + " (SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_stool_dna t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT max(ss.id) " +
                        "  FROM hospital_stool_dna ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'B' " +
                        " AND t1.area_dept_id = "+areaIds+
                        " AND t2.code_entry_status = 2 "+sql4+
                        ") AS DNANum,"

                        //实际接受FIT检查人数
                        + " (SELECT count(DISTINCT(t1.sid))" +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_fit_result t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT max(ss.id) " +
                        "  FROM hospital_fit_result ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'B' " +
                        " AND t1.area_dept_id = "+areaIds+
                        " AND t2.code_entry_status = 2 "+sql5+
                        ") AS FitNum,"

                        //FIT阳性人数
                        + " (SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_fit_result t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT MAX(ss.id) " +
                        "  FROM hospital_fit_result ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'B' " +
                        " AND t1.area_dept_id = " +areaIds+sql6+
                        " AND t2.result = 2 " +
                        ") AS FitPositiveNum,"

                        + " (SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_fit_result t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT MAX(ss.id) " +
                        "  FROM hospital_fit_result ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'B' " +
                        " AND t1.area_dept_id = " +areaIds+sql7+
                        " AND t2.result = 1" +
                        ")as FitNegativeNum,"
                        //+ " (select count(distinct(t1.sid))as groupByB from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'B'  and t1.area_dept_id = "+areaIds+" and t2.result = 2 "+sql6+")as FitPositiveNum,"
                        //+ " (select count(distinct(t1.sid))as groupByB from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'B'  and t1.area_dept_id = "+areaIds+" and t2.result = 1 "+sql7+")as FitNegativeNum,"

                        //退出研究
                        + " (SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_intestine_review_quit_log t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT max(ss.id) " +
                        "  FROM hospital_intestine_review_quit_log ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'B' " +
                        " AND t1.area_dept_id = "+areaIds+
                        " AND t1.overall_status_cy = 2 "+sql8+
                        ") AS SearchOutNum, "

                        //粪便DNA检查已反馈
                        + " ( SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_stool_dna t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT MAX(ss.id) " +
                        "  FROM hospital_stool_dna ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'B' " +
                        " AND t1.area_dept_id = "+ areaIds + sql9 +
                        " AND t2.dna_check_inform_status = 2 " +
                        ") AS DNAFeedbackNum, "

                        //粪便DNA检查未反馈
                        + " (SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_stool_dna t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT MAX(ss.id) " +
                        "  FROM hospital_stool_dna ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'B' " +
                        " AND t1.area_dept_id = " + areaIds + sql10 +
                        " AND ( " +
                        "  t2.dna_check_inform_status = 3 " +
                        "  OR t2.dna_check_inform_status = 1 " +
                        " ) " +
                        ") AS DNANoFeedbackNum,"

                        //粪便DNA检查未生成
                        + " (SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_stool_dna t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT MAX(ss.id) " +
                        "  FROM hospital_stool_dna ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'B' " +
                        " AND t1.area_dept_id = " + areaIds + sql11 +
                        " AND t2.dna_check_enter_status = 1 " +
                        " AND t2.code_entry_status = 2 " +
                        ") AS DNANoGenerate,"

                        //FIT无效
                        + " (SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_fit_result t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT MAX(ss.id) " +
                        "  FROM hospital_fit_result ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'B' " +
                        " AND t1.area_dept_id = " + areaIds + sql12 +
                        " AND t2.result = 3 " +
                        ") AS FitInvalidNum "
                        + " from dual;";
                log.info("msq1=>"+msq2);
                List<Map<String,Object>> list2 = jdbcTemplate.queryForList(msq2);
                for (Map<String, Object> mb : list2) {
                    sumByB1 = Integer.parseInt(mb.get("groupsNum").toString());
                    sumByB2 = Integer.parseInt(mb.get("bookingColonscopyNum").toString());
                    sumByB3 = Integer.parseInt(mb.get("inspectColonscopyNum").toString());
                    sumByB4 = Integer.parseInt(mb.get("DNANum").toString());
                    sumByB5 = Integer.parseInt(mb.get("FitNum").toString());
                    sumByB6 = Integer.parseInt(mb.get("FitPositiveNum").toString());
                    sumByB7 = Integer.parseInt(mb.get("FitNegativeNum").toString());
                    sumByB8 = Integer.parseInt(mb.get("SearchOutNum").toString());
					sumByB9 = Integer.parseInt(mb.get("DNAFeedbackNum").toString());
					sumByB10 = Integer.parseInt(mb.get("DNANoFeedbackNum").toString());
					sumByB11 = Integer.parseInt(mb.get("DNANoGenerate").toString());
					sumByB12 = Integer.parseInt(mb.get("FitInvalidNum").toString());
					sumByB13 = sumByB6 + sumByB7 + sumByB12;
                    mb.put("FitResultReturnNum",sumByB13);
                }
                String jsons2 = list2.toString().substring(1,list2.toString().length());
                String sumdepts2 = jsons2.substring(0, jsons2.length()-1);
//                rs.add(sumdepts2);
                //第三次C高危
                String msq3 = "select "
                        + " (select count(distinct(sid)) as groupByC from hospital_intestine_review where group_status = 2 and `group` = 'C' and risk_level = 2 and area_dept_id = "+areaIds+" "+sql1+") as groupsNum,"
                        //已预约肠镜检查人数
                        + " (SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_colonoscopy_record t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT MAX(ss.id) " +
                        "  FROM hospital_colonoscopy_record ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'C' and t1.risk_level = 2 " +
                        " AND t1.area_dept_id = "+areaIds+
                        " AND t2.reserve_status = 2 "+sql2+
                        " ) AS bookingColonscopyNum,"

                        //实际接受肠镜检查人数
                        + " (SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_colonoscopy_record t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT MAX(ss.id) " +
                        "  FROM hospital_colonoscopy_record ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'C' and t1.risk_level = 2 " +
                        " AND t1.area_dept_id = "+areaIds+
                        " AND t2.examination_status = 2 "+sql3+
                        ") AS inspectColonscopyNum,"

                        //接受粪便DNA检测人数
                        + " (SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_stool_dna t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT max(ss.id) " +
                        "  FROM hospital_stool_dna ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'C' and t1.risk_level = 2 " +
                        " AND t1.area_dept_id = "+areaIds+
                        " AND t2.code_entry_status = 2 "+sql4+
                        ") AS DNANum,"

                        //实际接受FIT检查人数
                        + " (SELECT count(DISTINCT(t1.sid))" +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_fit_result t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT max(ss.id) " +
                        "  FROM hospital_fit_result ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'C' and t1.risk_level = 2 " +
                        " AND t1.area_dept_id = "+areaIds+
                        " AND t2.code_entry_status = 2 "+sql5+
                        ") AS FitNum,"

                        //FIT阳性人数
                        + " (SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_fit_result t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT MAX(ss.id) " +
                        "  FROM hospital_fit_result ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'C' and t1.risk_level = 2 " +
                        " AND t1.area_dept_id = " +areaIds+sql6+
                        " AND t2.result = 2 " +
                        ") AS FitPositiveNum,"

                        + " (SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_fit_result t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT MAX(ss.id) " +
                        "  FROM hospital_fit_result ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'C' and t1.risk_level = 2 " +
                        " AND t1.area_dept_id = " +areaIds+sql7+
                        " AND t2.result = 1" +
                        ")as FitNegativeNum,"
                        //+ " (select count(distinct(t1.sid))as groupByC from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'C'  and t1.area_dept_id = "+areaIds+" and t2.result = 2 and t1.risk_level = 2 "+sql6+")as FitPositiveNum,"
                        //+ " (select count(distinct(t1.sid))as groupByC from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'C'  and t1.area_dept_id = "+areaIds+" and t2.result = 1 and t1.risk_level = 2 "+sql7+")as FitNegativeNum, "

                        //退出研究
                        + " (SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_intestine_review_quit_log t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT max(ss.id) " +
                        "  FROM hospital_intestine_review_quit_log ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'C' and t1.risk_level = 2 " +
                        " AND t1.area_dept_id = "+areaIds+
                        " AND t1.overall_status_cy = 2 "+sql8+
                        ") AS SearchOutNum, "

                        //粪便DNA检查已反馈
                        + " ( SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_stool_dna t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT MAX(ss.id) " +
                        "  FROM hospital_stool_dna ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'C' and t1.risk_level = 2 " +
                        " AND t1.area_dept_id = "+ areaIds + sql9 +
                        " AND t2.dna_check_inform_status = 2 " +
                        ") AS DNAFeedbackNum, "

                        //粪便DNA检查未反馈
                        + " (SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_stool_dna t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT MAX(ss.id) " +
                        "  FROM hospital_stool_dna ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'C' and t1.risk_level = 2 " +
                        " AND t1.area_dept_id = " + areaIds + sql10 +
                        " AND ( " +
                        "  t2.dna_check_inform_status = 3 " +
                        "  OR t2.dna_check_inform_status = 1 " +
                        " ) " +
                        ") AS DNANoFeedbackNum,"

                        //粪便DNA检查未生成
                        + " (SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_stool_dna t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT MAX(ss.id) " +
                        "  FROM hospital_stool_dna ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'C' and t1.risk_level = 2 " +
                        " AND t1.area_dept_id = " + areaIds + sql11 +
                        " AND t2.dna_check_enter_status = 1 " +
                        " AND t2.code_entry_status = 2 " +
                        ") AS DNANoGenerate,"

                        //FIT无效
                        + " (SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_fit_result t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT MAX(ss.id) " +
                        "  FROM hospital_fit_result ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'C' and t1.risk_level = 2 " +
                        " AND t1.area_dept_id = " + areaIds + sql12 +
                        " AND t2.result = 3 " +
                        ") AS FitInvalidNum "
                        + " from dual;";
                log.info("sql==>"+msq3);
                List<Map<String,Object>> list3 = jdbcTemplate.queryForList(msq3);
                for (Map<String, Object> mc1 : list3) {
                    sumByCg1 = Integer.parseInt(mc1.get("groupsNum").toString());
                    sumByCg2 = Integer.parseInt(mc1.get("bookingColonscopyNum").toString());
                    sumByCg3 = Integer.parseInt(mc1.get("inspectColonscopyNum").toString());
                    sumByCg4 = Integer.parseInt(mc1.get("DNANum").toString());
                    sumByCg5 = Integer.parseInt(mc1.get("FitNum").toString());
                    sumByCg6 = Integer.parseInt(mc1.get("FitPositiveNum").toString());
                    sumByCg7 = Integer.parseInt(mc1.get("FitNegativeNum").toString());
                    sumByCg8 = Integer.parseInt(mc1.get("SearchOutNum").toString());
					sumByCg9 = Integer.parseInt(mc1.get("DNAFeedbackNum").toString());
					sumByCg10 = Integer.parseInt(mc1.get("DNANoFeedbackNum").toString());
					sumByCg11 = Integer.parseInt(mc1.get("DNANoGenerate").toString());
					sumByCg12 = Integer.parseInt(mc1.get("FitInvalidNum").toString());
					sumByCg13 = sumByCg6 + sumByCg7 + sumByCg12;
                    mc1.put("FitResultReturnNum",sumByCg13);
                }
                String jsons3 = list3.toString().substring(1,list3.toString().length());
                String sumdepts3 = jsons3.substring(0, jsons3.length()-1);
//                rs.add(sumdepts3);
                //接受粪便DNA人数
                //第四次低危
                String msq4 = "select "
                        + " (select count(distinct(sid)) as groupByC from hospital_intestine_review where group_status = 2 and `group` = 'C' and risk_level = 1 and area_dept_id = "+areaIds+" "+sql1+") as groupsNum,"
                        //已预约肠镜检查人数
                        + " (SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_colonoscopy_record t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT MAX(ss.id) " +
                        "  FROM hospital_colonoscopy_record ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'C' and t1.risk_level = 1 " +
                        " AND t1.area_dept_id = "+areaIds+
                        " AND t2.reserve_status = 2 "+sql2+
                        " ) AS bookingColonscopyNum,"

                        //实际接受肠镜检查人数
                        + " (SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_colonoscopy_record t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT MAX(ss.id) " +
                        "  FROM hospital_colonoscopy_record ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'C' and t1.risk_level = 1 " +
                        " AND t1.area_dept_id = "+areaIds+
                        " AND t2.examination_status = 2 "+sql3+
                        ") AS inspectColonscopyNum,"

                        //接受粪便DNA检测人数
                        + " (SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_stool_dna t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT max(ss.id) " +
                        "  FROM hospital_stool_dna ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'C' and t1.risk_level = 1 " +
                        " AND t1.area_dept_id = "+areaIds+
                        " AND t2.code_entry_status = 2 "+sql4+
                        ") AS DNANum,"

                        //实际接受FIT检查人数
                        + " (SELECT count(DISTINCT(t1.sid))" +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_fit_result t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT max(ss.id) " +
                        "  FROM hospital_fit_result ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'C' and t1.risk_level = 1 " +
                        " AND t1.area_dept_id = "+areaIds+
                        " AND t2.code_entry_status = 2 "+sql5+
                        ") AS FitNum,"

                        //FIT阳性人数
                        + " (SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_fit_result t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT MAX(ss.id) " +
                        "  FROM hospital_fit_result ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'C' and t1.risk_level = 1 " +
                        " AND t1.area_dept_id = " +areaIds+sql6+
                        " AND t2.result = 2 " +
                        ") AS FitPositiveNum,"

                        + " (SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_fit_result t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT MAX(ss.id) " +
                        "  FROM hospital_fit_result ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'C' and t1.risk_level = 1 " +
                        " AND t1.area_dept_id = " +areaIds+sql7+
                        " AND t2.result = 1" +
                        ")as FitNegativeNum,"
						//+ " (select count(distinct(t1.sid))as groupByC from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'C'  and t1.area_dept_id = "+areaIds+" and t2.result = 2 and t1.risk_level = 1 "+sql6+")as FitPositiveNum,"
                        //+ " (select count(distinct(t1.sid))as groupByC from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'C'  and t1.area_dept_id = "+areaIds+" and t2.result = 1 and t1.risk_level = 1 "+sql7+")as FitNegativeNum,"

                        //退出研究
                        + " (SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_intestine_review_quit_log t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT max(ss.id) " +
                        "  FROM hospital_intestine_review_quit_log ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'C' and t1.risk_level = 1 " +
                        " AND t1.area_dept_id = "+areaIds+
                        " AND t1.overall_status_cy = 2 "+sql8+
                        ") AS SearchOutNum, "

                        //粪便DNA检查已反馈
                        + " ( SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_stool_dna t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT MAX(ss.id) " +
                        "  FROM hospital_stool_dna ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'C' and t1.risk_level = 1 " +
                        " AND t1.area_dept_id = "+ areaIds + sql9 +
                        " AND t2.dna_check_inform_status = 2 " +
                        ") AS DNAFeedbackNum, "

                        //粪便DNA检查未反馈
                        + " (SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_stool_dna t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT MAX(ss.id) " +
                        "  FROM hospital_stool_dna ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'C' and t1.risk_level = 1 " +
                        " AND t1.area_dept_id = " + areaIds + sql10 +
                        " AND ( " +
                        "  t2.dna_check_inform_status = 3 " +
                        "  OR t2.dna_check_inform_status = 1 " +
                        " ) " +
                        ") AS DNANoFeedbackNum,"

                        //粪便DNA检查未生成
                        + " (SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_stool_dna t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT MAX(ss.id) " +
                        "  FROM hospital_stool_dna ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'C' and t1.risk_level = 1 " +
                        " AND t1.area_dept_id = " + areaIds + sql11 +
                        " AND t2.dna_check_enter_status = 1 " +
                        " AND t2.code_entry_status = 2 " +
                        ") AS DNANoGenerate,"

                        //FIT无效
                        + " (SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_fit_result t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT MAX(ss.id) " +
                        "  FROM hospital_fit_result ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'C' and t1.risk_level = 1 " +
                        " AND t1.area_dept_id = " + areaIds + sql12 +
                        " AND t2.result = 3 " +
                        ") AS FitInvalidNum "
                        + " from dual;";
                log.info("sql==>"+msq4);
                List<Map<String,Object>> list4 = jdbcTemplate.queryForList(msq4);
                for (Map<String, Object> mc2 : list4) {
                    sumByCd1 = Integer.parseInt(mc2.get("groupsNum").toString());
                    sumByCd2 = Integer.parseInt(mc2.get("bookingColonscopyNum").toString());
                    sumByCd3 = Integer.parseInt(mc2.get("inspectColonscopyNum").toString());
                    sumByCd4 = Integer.parseInt(mc2.get("DNANum").toString());
                    sumByCd5 = Integer.parseInt(mc2.get("FitNum").toString());
                    sumByCd6 = Integer.parseInt(mc2.get("FitPositiveNum").toString());
                    sumByCd7 = Integer.parseInt(mc2.get("FitNegativeNum").toString());
                    sumByCd8 = Integer.parseInt(mc2.get("SearchOutNum").toString());
					sumByCd9 = Integer.parseInt(mc2.get("DNAFeedbackNum").toString());
					sumByCd10 = Integer.parseInt(mc2.get("DNANoFeedbackNum").toString());
					sumByCd11 = Integer.parseInt(mc2.get("DNANoGenerate").toString());
					sumByCd12 = Integer.parseInt(mc2.get("FitInvalidNum").toString());
					sumByCd13 = sumByCd6 + sumByCd7 + sumByCd12;
                    mc2.put("FitResultReturnNum",sumByCd13);
                }
                String jsons4 = list4.toString().substring(1,list4.toString().length());
                String sumdepts4 = jsons4.substring(0, jsons4.length()-1);
//                rs.add(sumdepts4);
                //实际接受FIT检查人数
                //第五次未评估
                String msq5 = "select "
                        + " (select count(distinct(sid)) as groupByC from hospital_intestine_review where group_status = 2 and `group` = 'C' and risk_level  is null and area_dept_id = "+areaIds+" "+sql1+")as groupsNum,"
                        //已预约肠镜检查人数
                        + " (SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_colonoscopy_record t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT MAX(ss.id) " +
                        "  FROM hospital_colonoscopy_record ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'C' and t1.risk_level is null " +
                        " AND t1.area_dept_id = "+areaIds+
                        " AND t2.reserve_status = 2 "+sql2+
                        " ) AS bookingColonscopyNum,"

                        //实际接受肠镜检查人数
                        + " (SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_colonoscopy_record t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT MAX(ss.id) " +
                        "  FROM hospital_colonoscopy_record ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'C' and t1.risk_level is null " +
                        " AND t1.area_dept_id = "+areaIds+
                        " AND t2.examination_status = 2 "+sql3+
                        ") AS inspectColonscopyNum,"

                        //接受粪便DNA检测人数
                        + " (SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_stool_dna t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT max(ss.id) " +
                        "  FROM hospital_stool_dna ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'C' and t1.risk_level is null " +
                        " AND t1.area_dept_id = "+areaIds+
                        " AND t2.code_entry_status = 2 "+sql4+
                        ") AS DNANum,"

                        //实际接受FIT检查人数
                        + " (SELECT count(DISTINCT(t1.sid))" +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_fit_result t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT max(ss.id) " +
                        "  FROM hospital_fit_result ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'C' and t1.risk_level is null " +
                        " AND t1.area_dept_id = "+areaIds+
                        " AND t2.code_entry_status = 2 "+sql5+
                        ") AS FitNum,"

                        //FIT阳性人数
                        + " (SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_fit_result t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT MAX(ss.id) " +
                        "  FROM hospital_fit_result ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'C' and t1.risk_level is null " +
                        " AND t1.area_dept_id = " +areaIds+sql6+
                        " AND t2.result = 2 " +
                        ") AS FitPositiveNum,"

                        + " (SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_fit_result t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT MAX(ss.id) " +
                        "  FROM hospital_fit_result ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'C' and t1.risk_level is null " +
                        " AND t1.area_dept_id = " +areaIds+sql7+
                        " AND t2.result = 1" +
                        ")as FitNegativeNum,"
						//+ " (select count(distinct(t1.sid))as groupByC from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'C'  and t1.area_dept_id = "+areaIds+" and t2.result = 2 and t1.risk_level is null "+sql6+") as FitPositiveNum,"
                        //+ " (select count(distinct(t1.sid))as groupByC from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'C'  and t1.area_dept_id = "+areaIds+" and t2.result = 1 and t1.risk_level is null "+sql7+") as FitNegativeNum,"

                        //退出研究
                        + " (SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_intestine_review_quit_log t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT max(ss.id) " +
                        "  FROM hospital_intestine_review_quit_log ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'C' and t1.risk_level is null " +
                        " AND t1.area_dept_id = "+areaIds+
                        " AND t1.overall_status_cy = 2 "+sql8+
                        ") AS SearchOutNum, "

                        //粪便DNA检查已反馈
                        + " ( SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_stool_dna t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT MAX(ss.id) " +
                        "  FROM hospital_stool_dna ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'C' and t1.risk_level is null " +
                        " AND t1.area_dept_id = "+ areaIds + sql9 +
                        " AND t2.dna_check_inform_status = 2 " +
                        ") AS DNAFeedbackNum, "

                        //粪便DNA检查未反馈
                        + " (SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_stool_dna t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT MAX(ss.id) " +
                        "  FROM hospital_stool_dna ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'C' and t1.risk_level is null " +
                        " AND t1.area_dept_id = " + areaIds + sql10 +
                        " AND ( " +
                        "  t2.dna_check_inform_status = 3 " +
                        "  OR t2.dna_check_inform_status = 1 " +
                        " ) " +
                        ") AS DNANoFeedbackNum,"

                        //粪便DNA检查未生成
                        + " (SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_stool_dna t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT MAX(ss.id) " +
                        "  FROM hospital_stool_dna ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'C' and t1.risk_level is null " +
                        " AND t1.area_dept_id = " + areaIds + sql11 +
                        " AND t2.dna_check_enter_status = 1 " +
                        " AND t2.code_entry_status = 2 " +
                        ") AS DNANoGenerate,"

                        //FIT无效
                        + " (SELECT count(DISTINCT(t1.sid)) " +
                        " FROM " +
                        "  hospital_intestine_review t1, " +
                        "  hospital_fit_result t2 " +
                        " WHERE t1.sid = t2.sid " +
                        " AND t2.id IN ( " +
                        "  SELECT MAX(ss.id) " +
                        "  FROM hospital_fit_result ss " +
                        "  GROUP BY ss.sid " +
                        " ) " +
                        " AND t1.group_status = 2 " +
                        " AND t1.`group` = 'C' and t1.risk_level is null " +
                        " AND t1.area_dept_id = " + areaIds + sql12 +
                        " AND t2.result = 3 " +
                        ") AS FitInvalidNum "
                        + " from dual;";
                log.info("sql==>"+msq5);
                List<Map<String,Object>> list5 = jdbcTemplate.queryForList(msq5);
                for (Map<String, Object> mc3 : list5) {
                    sumByCp1 = Integer.parseInt(mc3.get("groupsNum").toString());
                    sumByCp2 = Integer.parseInt(mc3.get("bookingColonscopyNum").toString());
                    sumByCp3 = Integer.parseInt(mc3.get("inspectColonscopyNum").toString());
                    sumByCp4 = Integer.parseInt(mc3.get("DNANum").toString());
                    sumByCp5 = Integer.parseInt(mc3.get("FitNum").toString());
                    sumByCp6 = Integer.parseInt(mc3.get("FitPositiveNum").toString());
                    sumByCp7 = Integer.parseInt(mc3.get("FitNegativeNum").toString());
                    sumByCp8 = Integer.parseInt(mc3.get("SearchOutNum").toString());
					sumByCp9 = Integer.parseInt(mc3.get("DNAFeedbackNum").toString());
					sumByCp10 = Integer.parseInt(mc3.get("DNANoFeedbackNum").toString());
					sumByCp11 = Integer.parseInt(mc3.get("DNANoGenerate").toString());
					sumByCp12 = Integer.parseInt(mc3.get("FitInvalidNum").toString());
					sumByCp13 = sumByCp6 + sumByCp7 + sumByCp12;
                    mc3.put("FitResultReturnNum",sumByCp13);
                }
                String jsons5 = list5.toString().substring(1,list5.toString().length());
                String sumdepts5 = jsons5.substring(0, jsons5.length()-1);
//                rs.add(sumdepts5);
                int sums1 = sumByA1 + sumByB1 + sumByCg1 + sumByCd1 + sumByCp1;   //统计入组总人数
                int sums2 = sumByA2 + sumByB2 + sumByCg2 + sumByCd2 + sumByCp2;   //已预约肠镜检查人数
                int sums3 = sumByA3 + sumByB3 + sumByCg3 + sumByCd3 + sumByCp3;   //实际接受肠镜检查人数
                int sums4 = sumByA4 + sumByB4 + sumByCg4 + sumByCd4 + sumByCp4;   //接受粪便DNA检测人数
                int sums5 = sumByA5 + sumByB5 + sumByCg5 + sumByCd5 + sumByCp5;   //实际接受FIT检查人数
                int sums6 = sumByA6 + sumByB6 + sumByCg6 + sumByCd6 + sumByCp6;   //FIT阳性人数
                int sums7 = sumByA7 + sumByB7 + sumByCg7 + sumByCd7 + sumByCp7;   //FIT阴性人数
                int sums8 = sumByA8 + sumByB8 + sumByCg8 + sumByCd8 + sumByCp8;   //退出总人数
				int sums9 = sumByA9 + sumByB9 + sumByCg9 + sumByCd9 + sumByCp9;   //粪便DNA检查已反馈总人数
				int sums10 = sumByA10 + sumByB10 + sumByCg10 + sumByCd10 + sumByCp10;   //粪便DNA检查未反馈总人数
				int sums11 = sumByA11 + sumByB11 + sumByCg11 + sumByCd11 + sumByCp11;   //粪便DNA检查未生成总人数
				int sums12 = sumByA12 + sumByB12 + sumByCg12 + sumByCd12 + sumByCp12;   //FIT阴性数总人数
				int sums13 = sumByA13 + sumByB13 + sumByCg13 + sumByCd13 + sumByCp13;   //FIT结果返回总人数

                //新增国家统计相对于的组别
                c_sumsa1 = sumByA1 + c_sumsa1;
                c_sumsb1= sumByB1 + c_sumsb1;
                c_sumscg1 = sumByCg1 + c_sumscg1;
                c_sumscd1 = sumByCd1 + c_sumscd1;
                c_sumscp1 = sumByCp1 + c_sumscp1;
                //第二次
                c_sumsa2 = sumByA2 + c_sumsa2;
                c_sumsb2= sumByB2 + c_sumsb2;
                c_sumscg2 = sumByCg2 + c_sumscg2;
                c_sumscd2 = sumByCd2 + c_sumscd2;
                c_sumscp2 = sumByCp2 + c_sumscp2;
                //第三次
                c_sumsa3 = sumByA3 + c_sumsa3;
                c_sumsb3= sumByB3 + c_sumsb3;
                c_sumscg3 = sumByCg3 + c_sumscg3;
                c_sumscd3 = sumByCd3 + c_sumscd3;
                c_sumscp3 = sumByCp3 + c_sumscp3;
                //第四次
                c_sumsa4 = sumByA4 + c_sumsa4;
                c_sumsb4= sumByB4 + c_sumsb4;
                c_sumscg4 = sumByCg4 + c_sumscg4;
                c_sumscd4 = sumByCd4 + c_sumscd4;
                c_sumscp4 = sumByCp4 + c_sumscp4;
                //第五次
                c_sumsa5 = sumByA5 + c_sumsa5;
                c_sumsb5= sumByB5 + c_sumsb5;
                c_sumscg5 = sumByCg5 + c_sumscg5;
                c_sumscd5 = sumByCd5 + c_sumscd5;
                c_sumscp5 = sumByCp5 + c_sumscp5;
                //第六次
                c_sumsa6 = sumByA6 + c_sumsa6;
                c_sumsb6= sumByB6 + c_sumsb6;
                c_sumscg6 = sumByCg6 + c_sumscg6;
                c_sumscd6 = sumByCd6 + c_sumscd6;
                c_sumscp6 = sumByCp6 + c_sumscp6;
                //第七次
                c_sumsa7 = sumByA7 + c_sumsa7;
                c_sumsb7= sumByB7 + c_sumsb7;
                c_sumscg7 = sumByCg7 + c_sumscg7;
                c_sumscd7 = sumByCd7 + c_sumscd7;
                c_sumscp7 = sumByCp7 + c_sumscp7;
                //第八次
                c_sumsa8 = sumByA8 + c_sumsa8;
                c_sumsb8= sumByB8 + c_sumsb8;
                c_sumscg8 = sumByCg8 + c_sumscg8;
                c_sumscd8 = sumByCd8 + c_sumscd8;
                c_sumscp8 = sumByCp8 + c_sumscp8;
                //第九次
				c_sumsa9 = sumByA9 + c_sumsa9;
				c_sumsb9= sumByB9 + c_sumsb9;
				c_sumscg9 = sumByCg9 + c_sumscg9;
				c_sumscd9 = sumByCd9 + c_sumscd9;
				c_sumscp9 = sumByCp9 + c_sumscp9;
				//第十次
				c_sumsa10 = sumByA10 + c_sumsa10;
				c_sumsb10= sumByB10 + c_sumsb10;
				c_sumscg10 = sumByCg10 + c_sumscg10;
				c_sumscd10 = sumByCd10 + c_sumscd10;
				c_sumscp10 = sumByCp10 + c_sumscp10;
				//第十一次
				c_sumsa11 = sumByA11 + c_sumsa11;
				c_sumsb11= sumByB11 + c_sumsb11;
				c_sumscg11 = sumByCg11 + c_sumscg11;
				c_sumscd11 = sumByCd11 + c_sumscd11;
				c_sumscp11 = sumByCp11 + c_sumscp11;
				//第十二次
				c_sumsa12 = sumByA12 + c_sumsa12;
				c_sumsb12= sumByB12 + c_sumsb12;
				c_sumscg12 = sumByCg12 + c_sumscg12;
				c_sumscd12 = sumByCd12 + c_sumscd12;
				c_sumscp12 = sumByCp12 + c_sumscp12;
				//第十三次
				c_sumsa13 = sumByA13 + c_sumsa13;
				c_sumsb13= sumByB13 + c_sumsb13;
				c_sumscg13 = sumByCg13 + c_sumscg13;
				c_sumscd13 = sumByCd13 + c_sumscd13;
				c_sumscp13 = sumByCp13 + c_sumscp13;
                //总计
                c_sumst1 = c_sumsa1 + c_sumsb1 + c_sumscg1 + c_sumscd1 + c_sumscp1 ;
                c_sumst2 = c_sumsa2 + c_sumsb2 + c_sumscg2 + c_sumscd2 + c_sumscp2 ;
                c_sumst3 = c_sumsa3 + c_sumsb3 + c_sumscg3 + c_sumscd3 + c_sumscp3 ;
                c_sumst4 = c_sumsa4 + c_sumsb4 + c_sumscg4 + c_sumscd4 + c_sumscp4 ;
                c_sumst5 = c_sumsa5 + c_sumsb5 + c_sumscg5 + c_sumscd5 + c_sumscp5 ;
                c_sumst6 = c_sumsa6 + c_sumsb6 + c_sumscg6 + c_sumscd6 + c_sumscp6 ;
                c_sumst7 = c_sumsa7 + c_sumsb7 + c_sumscg7 + c_sumscd7 + c_sumscp7 ;
                c_sumst8 = c_sumsa8 + c_sumsb8 + c_sumscg8 + c_sumscd8 + c_sumscp8 ;
				c_sumst9 = c_sumsa9 + c_sumsb9 + c_sumscg9 + c_sumscd9 + c_sumscp9 ;
				c_sumst10 = c_sumsa10 + c_sumsb10 + c_sumscg10 + c_sumscd10 + c_sumscp10 ;
				c_sumst11 = c_sumsa11 + c_sumsb11 + c_sumscg11 + c_sumscd11 + c_sumscp11 ;
				c_sumst12 = c_sumsa12 + c_sumsb12 + c_sumscg12 + c_sumscd12 + c_sumscp12 ;
				c_sumst13 = c_sumsa13 + c_sumsb13 + c_sumscg13 + c_sumscd13 + c_sumscp13 ;

            }
            //新增统计各组总人数
            c_map.put("groupsNum",c_sumsa1);
            c_map.put("bookingColonscopyNum",c_sumsa2);
            c_map.put("inspectColonscopyNum",c_sumsa3);
            c_map.put("DNANum",c_sumsa4);
            c_map.put("FitNum",c_sumsa5);
            c_map.put("FitPositiveNum",c_sumsa6);
            c_map.put("FitNegativeNum", c_sumsa7);
            c_map.put("SearchOutNum", c_sumsa8);
			c_map.put("DNAFeedbackNum",c_sumsa9);
			c_map.put("DNANoFeedbackNum",c_sumsa10);
			c_map.put("DNANoGenerate",c_sumsa11);
			c_map.put("FitInvalidNum",c_sumsa12);
			c_map.put("FitResultReturnNum",c_sumsa13);
            sumsByCountry.add(c_map);
            //统计总人数
            String jsons1 = sumsByCountry.toString().substring(1,sumsByCountry.toString().length());
            String sumdepts1 = jsons1.substring(0, jsons1.length()-1);
            rs.add(sumdepts1);
            //第二次
            c_map.put("groupsNum",c_sumsb1);
            c_map.put("bookingColonscopyNum",c_sumsb2);
            c_map.put("inspectColonscopyNum",c_sumsb3);
            c_map.put("DNANum",c_sumsb4);
            c_map.put("FitNum",c_sumsb5);
            c_map.put("FitPositiveNum",c_sumsb6);
            c_map.put("FitNegativeNum", c_sumsb7);
            c_map.put("SearchOutNum", c_sumsb8);
			c_map.put("DNAFeedbackNum",c_sumsb9);
			c_map.put("DNANoFeedbackNum",c_sumsb10);
			c_map.put("DNANoGenerate",c_sumsb11);
			c_map.put("FitInvalidNum",c_sumsb12);
			c_map.put("FitResultReturnNum",c_sumsb13);
            sumsByCountry.add(c_map);
            //统计总人数
            String jsons2 = sumsByCountry.toString().substring(1,sumsByCountry.toString().length());
            String sumdepts2 = jsons2.substring(0, jsons2.length()-1);
            rs.add(sumdepts2);

            c_map.put("groupsNum",c_sumscg1);
            c_map.put("bookingColonscopyNum",c_sumscg2);
            c_map.put("inspectColonscopyNum",c_sumscg3);
            c_map.put("DNANum",c_sumscg4);
            c_map.put("FitNum",c_sumscg5);
            c_map.put("FitPositiveNum",c_sumscg6);
            c_map.put("FitNegativeNum", c_sumscg7);
            c_map.put("SearchOutNum", c_sumscg8);
			c_map.put("DNAFeedbackNum",c_sumscg9);
			c_map.put("DNANoFeedbackNum",c_sumscg10);
			c_map.put("DNANoGenerate",c_sumscg11);
			c_map.put("FitInvalidNum",c_sumscg12);
			c_map.put("FitResultReturnNum",c_sumscg13);
            sumsByCountry.add(c_map);
            //统计总人数
            String jsons3 = sumsByCountry.toString().substring(1,sumsByCountry.toString().length());
            String sumdepts3 = jsons3.substring(0, jsons3.length()-1);
            rs.add(sumdepts3);

            c_map.put("groupsNum",c_sumscd1);
            c_map.put("bookingColonscopyNum",c_sumscd2);
            c_map.put("inspectColonscopyNum",c_sumscd3);
            c_map.put("DNANum",c_sumscd4);
            c_map.put("FitNum",c_sumscd5);
            c_map.put("FitPositiveNum",c_sumscd6);
            c_map.put("FitNegativeNum", c_sumscd7);
            c_map.put("SearchOutNum", c_sumscd8);
			c_map.put("DNAFeedbackNum",c_sumscd9);
			c_map.put("DNANoFeedbackNum",c_sumscd10);
			c_map.put("DNANoGenerate",c_sumscd11);
			c_map.put("FitInvalidNum",c_sumscd12);
			c_map.put("FitResultReturnNum",c_sumscd13);
            sumsByCountry.add(c_map);
            //统计总人数
            String jsons4 = sumsByCountry.toString().substring(1,sumsByCountry.toString().length());
            String sumdepts4 = jsons4.substring(0, jsons4.length()-1);
            rs.add(sumdepts4);

            c_map.put("groupsNum",c_sumscp1);
            c_map.put("bookingColonscopyNum",c_sumscp2);
            c_map.put("inspectColonscopyNum",c_sumscp3);
            c_map.put("DNANum",c_sumscp4);
            c_map.put("FitNum",c_sumscp5);
            c_map.put("FitPositiveNum",c_sumscp6);
            c_map.put("FitNegativeNum", c_sumscp7);
            c_map.put("SearchOutNum", c_sumscp8);
            c_map.put("DNAFeedbackNum",c_sumscp9);
			c_map.put("DNANoFeedbackNum",c_sumscp10);
			c_map.put("DNANoGenerate",c_sumscp11);
			c_map.put("FitInvalidNum",c_sumscp12);
			c_map.put("FitResultReturnNum",c_sumscp13);
            sumsByCountry.add(c_map);
            //统计总人数
            String jsons5 = sumsByCountry.toString().substring(1,sumsByCountry.toString().length());
            String sumdepts5 = jsons5.substring(0, jsons5.length()-1);
            rs.add(sumdepts5);

            //新增统计各组总人数
           c_map.put("groupsNum",c_sumst1);
            c_map.put("bookingColonscopyNum",c_sumst2);
            c_map.put("inspectColonscopyNum",c_sumst3);
            c_map.put("DNANum",c_sumst4);
            c_map.put("FitNum",c_sumst5);
            c_map.put("FitPositiveNum",c_sumst6);
            c_map.put("FitNegativeNum", c_sumst7);
            c_map.put("SearchOutNum", c_sumst8);
			c_map.put("DNAFeedbackNum",c_sumst9);
			c_map.put("DNANoFeedbackNum",c_sumst10);
			c_map.put("DNANoGenerate",c_sumst11);
			c_map.put("FitInvalidNum",c_sumst12);
			c_map.put("FitResultReturnNum",c_sumst13);
            sumsByCountry.add(c_map);
            //统计总人数
            String jsons7 = sumsByCountry.toString().substring(1,sumsByCountry.toString().length());
            String sumdepts7 = jsons7.substring(0, jsons7.length()-1);
            rs.add(sumdepts7);
            //存放数据格式
            arrs.put("areaName", areaName);
            arrs.put("list", rs);
            amp.add(arrs);
        }
        return amp;
    }
	@Override
	public List getGroupSumsByAreaId(int areaId, TodoVo vo) {
		List<Map<String, Object>> arerlists = getAreaByAreaId(areaId);
		List<Map<String,Object>> amp = new ArrayList<Map<String,Object>>();
		String sql1 = "";
		String sql2 = "";
		String sql3 = "";
		String sql4 = "";
		String sql5 = "";
		String sql6 = "";
		String sql7 = "";
		String sql8 = "";
		String sql9 = "";	//粪便DNA检查已返回
		String sql10 = "";	//FIT结果无效
		if(!StringUtil.isEmpty(vo.getStartDate()) && !StringUtil.isEmpty(vo.getEndDate())){
			sql1 = " and date_format(in_group_date,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(in_group_date,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
			sql2 = " and date_format(t2.reserve_status_date,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.reserve_status_date,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
			sql3 = " and date_format(t2.examination_check_date,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.examination_check_date,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
			sql4 = " and date_format(t2.entry_time,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.entry_time,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
			sql5 = " and date_format(t2.code_entry_time,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.code_entry_time,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
			sql6 = " and date_format(t2.result_sys_date,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.result_sys_date,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
			sql7 = " and date_format(t2.result_sys_date,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.result_sys_date,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
			sql8 = " and date_format(t2.quit_date,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.quit_date,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
			sql9 = " and date_format(t2.dna_check_inform_nation_date,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.dna_check_inform_nation_date,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
			sql10 = " and date_format(t2.result_sys_date,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(t2.result_sys_date,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
		}
		for (Map<String, Object> map : arerlists) {
			int sumByA1 = 0;
			int sumByA2 = 0;
			int sumByA3 = 0;
			int sumByA4 = 0;
			int sumByA5 = 0;
			int sumByA6 = 0;
			int sumByA7 = 0;
			int sumByA8 = 0;
			int sumByA9 = 0; 	//A组粪便DNA检查已反馈
			int sumByA10 = 0;	//A组FIT无效数
			int sumByA11 = 0;	//A组FIT结果返回数 = 阳性+阴性+无效
			int sumByB1 = 0;
			int sumByB2 = 0;
			int sumByB3 = 0;
			int sumByB4 = 0;
			int sumByB5 = 0;
			int sumByB6 = 0;
			int sumByB7 = 0;
			int sumByB8 = 0;
			int sumByB9 = 0; 	//B组粪便DNA检查已反馈
			int sumByB10 = 0;	//B组FIT无效数
			int sumByB11 = 0;	//B组FIT结果返回数 = 阳性+阴性+无效
			int sumByCg1 = 0;
			int sumByCg2 = 0;
			int sumByCg3 = 0;
			int sumByCg4 = 0;
			int sumByCg5 = 0;
			int sumByCg6 = 0;
			int sumByCg7 = 0;
			int sumByCg8 = 0;
			int sumByCg9 = 0;	//C组高危粪便DNA检查已反馈
			int sumByCg10 = 0;	//C组高危FIT无效数
			int sumByCg11 = 0;	//C组高危FIT结果返回数 = 阳性+阴性+无效
			int sumByCd1 = 0;
			int sumByCd2 = 0;
			int sumByCd3 = 0;
			int sumByCd4 = 0;
			int sumByCd5 = 0;
			int sumByCd6 = 0;
			int sumByCd7 = 0;
			int sumByCd8 = 0;
			int sumByCd9 = 0;	//C组低危粪便DNA检查已反馈
			int sumByCd10 = 0;	//C组低危FIT无效数
			int sumByCd11 = 0;	//C组低危FIT结果返回数 = 阳性+阴性+无效
			int sumByCp1 = 0;
			int sumByCp2 = 0;
			int sumByCp3 = 0;
			int sumByCp4 = 0;
			int sumByCp5 = 0;
			int sumByCp6 = 0;
			int sumByCp7 = 0;
			int sumByCp8 = 0;
			int sumByCp9 = 0;	//C组未评估粪便DNA检查已反馈
			int sumByCp10 = 0;	//C组未评估FIT无效数
			int sumByCp11 = 0;	//C组未评估FIT结果返回数 = 阳性+阴性+无效
			List<String> rs = new ArrayList<>();
			List<Map<String,Integer>> sumsByGroup = new ArrayList<Map<String,Integer>>();
			Map<String, Object> arrs = new LinkedHashMap<String,Object>();
			Map<String,Integer> ams = new LinkedHashMap<String,Integer>();
			int areaIds = Integer.parseInt(map.get("id").toString()) ;
			String areaName = map.get("name").toString();
			log.info("@Dao getGroupByAreaId query  Start ");
			//第一个A
			String msq1 = "select"
					+ " (select count(distinct(sid)) as groupByA from hospital_intestine_review where group_status = 2 and `group` = 'A' and area_dept_id = "+areaIds+" "+sql1+" ) as groupsNum ,"
                    //已预约肠镜检查人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_colonoscopy_record t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_colonoscopy_record ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'A' " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t2.reserve_status = 2 "+sql2+
                    " ) AS bookingColonscopyNum,"

                    //实际接受肠镜检查人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_colonoscopy_record t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_colonoscopy_record ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'A' " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t2.examination_status = 2 "+sql3+
                    ") AS inspectColonscopyNum,"

                    //接受粪便DNA检测人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_stool_dna t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT max(ss.id) " +
                    "  FROM hospital_stool_dna ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'A' " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t2.code_entry_status = 2 "+sql4+
                    ") AS DNANum,"

                    //实际接受FIT检查人数
                    + " (SELECT count(DISTINCT(t1.sid))" +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT max(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'A' " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t2.code_entry_status = 2 "+sql5+
                    ") AS FitNum,"

                    //FIT阳性人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'A' " +
                    " AND t1.area_dept_id = " +areaIds+sql6+
                    " AND t2.result = 2 " +
                    ") AS FitPositiveNum,"

                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'A' " +
                    " AND t1.area_dept_id = " +areaIds+sql7+
                    " AND t2.result = 1" +
                    ")as FitNegativeNum,"
					//+ " (select count(distinct(t1.sid))as groupByA from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'A'  and t1.area_dept_id = "+areaIds+" and t2.result = 2 "+sql6+")as FitPositiveNum,"
					//+ " (select count(distinct(t1.sid))as groupByA from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'A'  and t1.area_dept_id = "+areaIds+" and t2.result = 1 "+sql7+")as FitNegativeNum,"

                    //退出研究
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_intestine_review_quit_log t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT max(ss.id) " +
                    "  FROM hospital_intestine_review_quit_log ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'A' " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t1.overall_status_cy = 2 "+sql8+
                    ") AS SearchOutNum, "

                    //粪便DNA检查已反馈
                    + " ( SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_stool_dna t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_stool_dna ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'A' " +
                    " AND t1.area_dept_id = "+ areaIds + sql9 +
                    " AND t2.dna_check_inform_status = 2 " +
                    ") AS DNAFeedbackNum, "

                    //FIT无效
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'A' " +
                    " AND t1.area_dept_id = " + areaIds + sql10 +
                    " AND t2.result = 3 " +
                    ") AS FitInvalidNum "
					+ " from dual;";
			log.info("msq1==>"+msq1);
			List<Map<String,Object>> list1 = jdbcTemplate.queryForList(msq1);
			for (Map<String, Object> ma : list1) {
				sumByA1 = Integer.parseInt(ma.get("groupsNum").toString());
				sumByA2 = Integer.parseInt(ma.get("bookingColonscopyNum").toString());
				sumByA3 = Integer.parseInt(ma.get("inspectColonscopyNum").toString());
				sumByA4 = Integer.parseInt(ma.get("DNANum").toString());
				sumByA5 = Integer.parseInt(ma.get("FitNum").toString());
				sumByA6 = Integer.parseInt(ma.get("FitPositiveNum").toString());
				sumByA7 = Integer.parseInt(ma.get("FitNegativeNum").toString());
				sumByA8 = Integer.parseInt(ma.get("SearchOutNum").toString());
				sumByA9 = Integer.parseInt(ma.get("DNAFeedbackNum").toString());
				sumByA10 = Integer.parseInt(ma.get("FitInvalidNum").toString());
				sumByA11 = sumByA6 + sumByA7 + sumByA10;
                ma.put("FitResultReturnNum",sumByA11);
			};
			String jsons1 = list1.toString().substring(1,list1.toString().length());
			String sumdepts1 = jsons1.substring(0, jsons1.length()-1);
			rs.add(sumdepts1);
			//第二次B
			String msq2 = "select "
					+ " (select count(distinct(sid)) as groupByB from hospital_intestine_review  where group_status = 2 and `group` = 'B' and area_dept_id = "+areaIds+" "+sql1+") as groupsNum,"
                    //已预约肠镜检查人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_colonoscopy_record t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_colonoscopy_record ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'B' " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t2.reserve_status = 2 "+sql2+
                    " ) AS bookingColonscopyNum,"

                    //实际接受肠镜检查人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_colonoscopy_record t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_colonoscopy_record ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'B' " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t2.examination_status = 2 "+sql3+
                    ") AS inspectColonscopyNum,"

                    //接受粪便DNA检测人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_stool_dna t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT max(ss.id) " +
                    "  FROM hospital_stool_dna ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'B' " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t2.code_entry_status = 2 "+sql4+
                    ") AS DNANum,"

                    //实际接受FIT检查人数
                    + " (SELECT count(DISTINCT(t1.sid))" +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT max(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'B' " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t2.code_entry_status = 2 "+sql5+
                    ") AS FitNum,"

                    //FIT阳性人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'B' " +
                    " AND t1.area_dept_id = " +areaIds+sql6+
                    " AND t2.result = 2 " +
                    ") AS FitPositiveNum,"

                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'B' " +
                    " AND t1.area_dept_id = " +areaIds+sql7+
                    " AND t2.result = 1" +
                    ")as FitNegativeNum,"
					//+ " (select count(distinct(t1.sid))as groupByB from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'B'  and t1.area_dept_id = "+areaIds+" and t2.result = 2 "+sql6+")as FitPositiveNum,"
					//+ " (select count(distinct(t1.sid))as groupByB from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'B'  and t1.area_dept_id = "+areaIds+" and t2.result = 1 "+sql7+")as FitNegativeNum,"

//					+ " (select count(distinct(t1.sid))as groupByB from hospital_intestine_review t1,hospital_intestine_review_quit_log t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'B' and t1.area_dept_id = "+areaIds+" and t1.overall_status_cy = 2 "+sql8+")as SearchOutNum, "

                    //退出研究
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_intestine_review_quit_log t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT max(ss.id) " +
                    "  FROM hospital_intestine_review_quit_log ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'B' " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t1.overall_status_cy = 2 "+sql8+
                    ") AS SearchOutNum, "

                    //粪便DNA检查已反馈
                    + " ( SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_stool_dna t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_stool_dna ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'B' " +
                    " AND t1.area_dept_id = "+ areaIds + sql9 +
                    " AND t2.dna_check_inform_status = 2 " +
                    ") AS DNAFeedbackNum, "

                    //FIT无效
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'B' " +
                    " AND t1.area_dept_id = " + areaIds + sql10 +
                    " AND t2.result = 3 " +
                    ") AS FitInvalidNum "
					+ " from dual;";
            log.info("msq1=>"+msq2);
			List<Map<String,Object>> list2 = jdbcTemplate.queryForList(msq2);
			for (Map<String, Object> mb : list2) {
				sumByB1 = Integer.parseInt(mb.get("groupsNum").toString());
				sumByB2 = Integer.parseInt(mb.get("bookingColonscopyNum").toString());
				sumByB3 = Integer.parseInt(mb.get("inspectColonscopyNum").toString());
				sumByB4 = Integer.parseInt(mb.get("DNANum").toString());
				sumByB5 = Integer.parseInt(mb.get("FitNum").toString());
				sumByB6 = Integer.parseInt(mb.get("FitPositiveNum").toString());
				sumByB7 = Integer.parseInt(mb.get("FitNegativeNum").toString());
				sumByB8 = Integer.parseInt(mb.get("SearchOutNum").toString());
				sumByB9 = Integer.parseInt(mb.get("DNAFeedbackNum").toString());
				sumByB10 = Integer.parseInt(mb.get("FitInvalidNum").toString());
				sumByB11 = sumByB6 + sumByB7 + sumByB10;
                mb.put("FitResultReturnNum",sumByB11);
			}
			String jsons2 = list2.toString().substring(1,list2.toString().length());
			String sumdepts2 = jsons2.substring(0, jsons2.length()-1);
			rs.add(sumdepts2);
			//第三次C高危
			String msq3 = "select "
					+ " (select count(distinct(sid)) as groupByC from hospital_intestine_review where group_status = 2 and `group` = 'C' and risk_level = 2 and area_dept_id = "+areaIds+" "+sql1+") as groupsNum,"
                    //已预约肠镜检查人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_colonoscopy_record t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_colonoscopy_record ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 2 " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t2.reserve_status = 2 "+sql2+
                    " ) AS bookingColonscopyNum,"

                    //实际接受肠镜检查人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_colonoscopy_record t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_colonoscopy_record ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 2 " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t2.examination_status = 2 "+sql3+
                    ") AS inspectColonscopyNum,"

                    //接受粪便DNA检测人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_stool_dna t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT max(ss.id) " +
                    "  FROM hospital_stool_dna ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 2 " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t2.code_entry_status = 2 "+sql4+
                    ") AS DNANum,"

                    //实际接受FIT检查人数
                    + " (SELECT count(DISTINCT(t1.sid))" +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT max(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 2 " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t2.code_entry_status = 2 "+sql5+
                    ") AS FitNum,"

                    //FIT阳性人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 2 " +
                    " AND t1.area_dept_id = " +areaIds+sql6+
                    " AND t2.result = 2 " +
                    ") AS FitPositiveNum,"

                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 2 " +
                    " AND t1.area_dept_id = " +areaIds+sql7+
                    " AND t2.result = 1" +
                    ")as FitNegativeNum,"
					//+ " (select count(distinct(t1.sid))as groupByC from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'C'  and t1.area_dept_id = "+areaIds+" and t2.result = 2 and t1.risk_level = 2 "+sql6+")as FitPositiveNum,"
					//+ " (select count(distinct(t1.sid))as groupByC from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'C'  and t1.area_dept_id = "+areaIds+" and t2.result = 1 and t1.risk_level = 2 "+sql7+")as FitNegativeNum, "
                    //退出研究
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_intestine_review_quit_log t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT max(ss.id) " +
                    "  FROM hospital_intestine_review_quit_log ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 2 " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t1.overall_status_cy = 2 "+sql8+
                    ") AS SearchOutNum, "

                    //粪便DNA检查已反馈
                    + " ( SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_stool_dna t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_stool_dna ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 2 " +
                    " AND t1.area_dept_id = "+ areaIds + sql9 +
                    " AND t2.dna_check_inform_status = 2 " +
                    ") AS DNAFeedbackNum, "

                    //FIT无效
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 2 " +
                    " AND t1.area_dept_id = " + areaIds + sql10 +
                    " AND t2.result = 3 " +
                    ") AS FitInvalidNum "
					+ " from dual;";
			log.info("sql==>"+msq3);
			List<Map<String,Object>> list3 = jdbcTemplate.queryForList(msq3);
			for (Map<String, Object> mc1 : list3) {
				sumByCg1 = Integer.parseInt(mc1.get("groupsNum").toString());
				sumByCg2 = Integer.parseInt(mc1.get("bookingColonscopyNum").toString());
				sumByCg3 = Integer.parseInt(mc1.get("inspectColonscopyNum").toString());
				sumByCg4 = Integer.parseInt(mc1.get("DNANum").toString());
				sumByCg5 = Integer.parseInt(mc1.get("FitNum").toString());
				sumByCg6 = Integer.parseInt(mc1.get("FitPositiveNum").toString());
				sumByCg7 = Integer.parseInt(mc1.get("FitNegativeNum").toString());
				sumByCg8 = Integer.parseInt(mc1.get("SearchOutNum").toString());
				sumByCg9 = Integer.parseInt(mc1.get("DNAFeedbackNum").toString());
				sumByCg10 = Integer.parseInt(mc1.get("FitInvalidNum").toString());
				sumByCg11 = sumByCg6 + sumByCg7 + sumByCg10;
                mc1.put("FitResultReturnNum",sumByCg11);
			}
			String jsons3 = list3.toString().substring(1,list3.toString().length());
			String sumdepts3 = jsons3.substring(0, jsons3.length()-1);
			rs.add(sumdepts3);
			//接受粪便DNA人数
			//第四次低危
			String msq4 = "select "
					+ " (select count(distinct(sid)) as groupByC from hospital_intestine_review where group_status = 2 and `group` = 'C' and risk_level = 1 and area_dept_id = "+areaIds+" "+sql1+") as groupsNum,"
                    //已预约肠镜检查人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_colonoscopy_record t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_colonoscopy_record ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 1 " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t2.reserve_status = 2 "+sql2+
                    " ) AS bookingColonscopyNum,"

                    //实际接受肠镜检查人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_colonoscopy_record t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_colonoscopy_record ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 1 " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t2.examination_status = 2 "+sql3+
                    ") AS inspectColonscopyNum,"

                    //接受粪便DNA检测人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_stool_dna t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT max(ss.id) " +
                    "  FROM hospital_stool_dna ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 1 " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t2.code_entry_status = 2 "+sql4+
                    ") AS DNANum,"

                    //实际接受FIT检查人数
                    + " (SELECT count(DISTINCT(t1.sid))" +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT max(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 1 " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t2.code_entry_status = 2 "+sql5+
                    ") AS FitNum,"

                    //FIT阳性人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 1 " +
                    " AND t1.area_dept_id = " +areaIds+sql6+
                    " AND t2.result = 2 " +
                    ") AS FitPositiveNum,"

                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 1 " +
                    " AND t1.area_dept_id = " +areaIds+sql7+
                    " AND t2.result = 1" +
                    ")as FitNegativeNum,"
					//+ " (select count(distinct(t1.sid))as groupByC from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'C'  and t1.area_dept_id = "+areaIds+" and t2.result = 2 and t1.risk_level = 1 "+sql6+")as FitPositiveNum,"
					//+ " (select count(distinct(t1.sid))as groupByC from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'C'  and t1.area_dept_id = "+areaIds+" and t2.result = 1 and t1.risk_level = 1 "+sql7+")as FitNegativeNum,"
                    //退出研究
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_intestine_review_quit_log t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT max(ss.id) " +
                    "  FROM hospital_intestine_review_quit_log ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 1 " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t1.overall_status_cy = 2 "+sql8+
                    ") AS SearchOutNum, "

                    //粪便DNA检查已反馈
                    + " ( SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_stool_dna t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_stool_dna ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 1 " +
                    " AND t1.area_dept_id = "+ areaIds + sql9 +
                    " AND t2.dna_check_inform_status = 2 " +
                    ") AS DNAFeedbackNum, "

                    //FIT无效
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level = 1 " +
                    " AND t1.area_dept_id = " + areaIds + sql10 +
                    " AND t2.result = 3 " +
                    ") AS FitInvalidNum "
					+ " from dual;";
			log.info("sql==>"+msq4);
			List<Map<String,Object>> list4 = jdbcTemplate.queryForList(msq4);
			for (Map<String, Object> mc2 : list4) {
				sumByCd1 = Integer.parseInt(mc2.get("groupsNum").toString());
				sumByCd2 = Integer.parseInt(mc2.get("bookingColonscopyNum").toString());
				sumByCd3 = Integer.parseInt(mc2.get("inspectColonscopyNum").toString());
				sumByCd4 = Integer.parseInt(mc2.get("DNANum").toString());
				sumByCd5 = Integer.parseInt(mc2.get("FitNum").toString());
				sumByCd6 = Integer.parseInt(mc2.get("FitPositiveNum").toString());
				sumByCd7 = Integer.parseInt(mc2.get("FitNegativeNum").toString());
				sumByCd8 = Integer.parseInt(mc2.get("SearchOutNum").toString());
				sumByCd9 = Integer.parseInt(mc2.get("DNAFeedbackNum").toString());
				sumByCd10 = Integer.parseInt(mc2.get("FitInvalidNum").toString());
				sumByCd11 = sumByCd6 + sumByCd7 + sumByCd10;
                mc2.put("FitResultReturnNum",sumByCd11);
			}
			String jsons4 = list4.toString().substring(1,list4.toString().length());
			String sumdepts4 = jsons4.substring(0, jsons4.length()-1);
			rs.add(sumdepts4);
			//实际接受FIT检查人数
			//第五次未评估
			String msq5 = "select "
					+ " (select count(distinct(sid)) as groupByC from hospital_intestine_review where group_status = 2 and `group` = 'C' and risk_level  is null and area_dept_id = "+areaIds+" "+sql1+")as groupsNum,"
                    //已预约肠镜检查人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_colonoscopy_record t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_colonoscopy_record ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level  is null " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t2.reserve_status = 2 "+sql2+
                    " ) AS bookingColonscopyNum,"

                    //实际接受肠镜检查人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_colonoscopy_record t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_colonoscopy_record ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level  is null " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t2.examination_status = 2 "+sql3+
                    ") AS inspectColonscopyNum,"

                    //接受粪便DNA检测人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_stool_dna t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT max(ss.id) " +
                    "  FROM hospital_stool_dna ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level  is null " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t2.code_entry_status = 2 "+sql4+
                    ") AS DNANum,"

                    //实际接受FIT检查人数
                    + " (SELECT count(DISTINCT(t1.sid))" +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT max(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level  is null " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t2.code_entry_status = 2 "+sql5+
                    ") AS FitNum,"

                    //FIT阳性人数
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level  is null " +
                    " AND t1.area_dept_id = " +areaIds+sql6+
                    " AND t2.result = 2 " +
                    ") AS FitPositiveNum,"

                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level  is null " +
                    " AND t1.area_dept_id = " +areaIds+sql7+
                    " AND t2.result = 1" +
                    ")as FitNegativeNum,"
					//+ " (select count(distinct(t1.sid))as groupByC from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'C'  and t1.area_dept_id = "+areaIds+" and t2.result = 2 and t1.risk_level is null "+sql6+") as FitPositiveNum,"
					//+ " (select count(distinct(t1.sid))as groupByC from hospital_intestine_review t1,hospital_fit_result t2 where t1.sid = t2.sid and t1.group_status = 2 and t1.`group` = 'C'  and t1.area_dept_id = "+areaIds+" and t2.result = 1 and t1.risk_level is null "+sql7+") as FitNegativeNum,"
                    //退出研究
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_intestine_review_quit_log t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT max(ss.id) " +
                    "  FROM hospital_intestine_review_quit_log ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level  is null " +
                    " AND t1.area_dept_id = "+areaIds+
                    " AND t1.overall_status_cy = 2 "+sql8+
                    ") AS SearchOutNum, "

                    //粪便DNA检查已反馈
                    + " ( SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_stool_dna t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_stool_dna ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level  is null " +
                    " AND t1.area_dept_id = "+ areaIds + sql9 +
                    " AND t2.dna_check_inform_status = 2 " +
                    ") AS DNAFeedbackNum, "

                    //FIT无效
                    + " (SELECT count(DISTINCT(t1.sid)) " +
                    " FROM " +
                    "  hospital_intestine_review t1, " +
                    "  hospital_fit_result t2 " +
                    " WHERE t1.sid = t2.sid " +
                    " AND t2.id IN ( " +
                    "  SELECT MAX(ss.id) " +
                    "  FROM hospital_fit_result ss " +
                    "  GROUP BY ss.sid " +
                    " ) " +
                    " AND t1.group_status = 2 " +
                    " AND t1.`group` = 'C' and t1.risk_level  is null " +
                    " AND t1.area_dept_id = " + areaIds + sql10 +
                    " AND t2.result = 3 " +
                    ") AS FitInvalidNum "
					+ " from dual;";
			log.info("sql==>"+msq5);
			List<Map<String,Object>> list5 = jdbcTemplate.queryForList(msq5);
			for (Map<String, Object> mc3 : list5) {
				sumByCp1 = Integer.parseInt(mc3.get("groupsNum").toString());
				sumByCp2 = Integer.parseInt(mc3.get("bookingColonscopyNum").toString());
				sumByCp3 = Integer.parseInt(mc3.get("inspectColonscopyNum").toString());
				sumByCp4 = Integer.parseInt(mc3.get("DNANum").toString());
				sumByCp5 = Integer.parseInt(mc3.get("FitNum").toString());
				sumByCp6 = Integer.parseInt(mc3.get("FitPositiveNum").toString());
				sumByCp7 = Integer.parseInt(mc3.get("FitNegativeNum").toString());
				sumByCp8 = Integer.parseInt(mc3.get("SearchOutNum").toString());
				sumByCp9 = Integer.parseInt(mc3.get("DNAFeedbackNum").toString());
				sumByCp10 = Integer.parseInt(mc3.get("FitInvalidNum").toString());
				sumByCp11 = sumByCp6 + sumByCp7 + sumByCp10;
                mc3.put("FitResultReturnNum",sumByCp11);
			}
			String jsons5 = list5.toString().substring(1,list5.toString().length());
			String sumdepts5 = jsons5.substring(0, jsons5.length()-1);
			rs.add(sumdepts5);
			int sums1 = sumByA1 + sumByB1 + sumByCg1 + sumByCd1 + sumByCp1;   //统计入组总人数
			int sums2 = sumByA2 + sumByB2 + sumByCg2 + sumByCd2 + sumByCp2;   //已预约肠镜检查人数
			int sums3 = sumByA3 + sumByB3 + sumByCg3 + sumByCd3 + sumByCp3;   //实际接受肠镜检查人数
			int sums4 = sumByA4 + sumByB4 + sumByCg4 + sumByCd4 + sumByCp4;   //接受粪便DNA检测人数
			int sums5 = sumByA5 + sumByB5 + sumByCg5 + sumByCd5 + sumByCp5;   //实际接受FIT检查人数
			int sums6 = sumByA6 + sumByB6 + sumByCg6 + sumByCd6 + sumByCp6;   //FIT阳性人数
			int sums7 = sumByA7 + sumByB7 + sumByCg7 + sumByCd7 + sumByCp7;   //FIT阴性人数
			int sums8 = sumByA8 + sumByB8 + sumByCg8 + sumByCd8 + sumByCp8;   //退出总人数
			int sums9 = sumByA9 + sumByB9 + sumByCg9 + sumByCd9 + sumByCp9;	//粪便DNA检查已返回人数
			int sums10 = sumByA10 + sumByB10 + sumByCg10 + sumByCd10 + sumByCp10;	//FIT阴性人数
			int sums11 = sumByA11 + sumByB11 + sumByCg11 + sumByCd11 + sumByCp11;
			ams.put("groupsNum", sums1);
			ams.put("bookingColonscopyNum", sums2);
			ams.put("inspectColonscopyNum", sums3);
			ams.put("DNANum", sums4);
			ams.put("FitNum", sums5);
			ams.put("FitPositiveNum", sums6);
			ams.put("FitNegativeNum", sums7);
			ams.put("SearchOutNum", sums8);
			ams.put("DNAFeedbackNum",sums9);
			ams.put("FitInvalidNum",sums10);
			ams.put("FitResultReturnNum",sums11);
			sumsByGroup.add(ams);
			//统计总人数
			String jsons6 = sumsByGroup.toString().substring(1,sumsByGroup.toString().length());
			String sumdepts6 = jsons6.substring(0, jsons6.length()-1);
			rs.add(sumdepts6);

			//存放数据格式
			arrs.put("commdtName", areaName);
			arrs.put("list", rs);
			amp.add(arrs);
		}
		return amp;
	}

	@Override
	public ListPageUtil queryReviewByNationIdPageByG(HospitalReview person, int nationId) {
		// TODO Auto-generated method stub
		log.info("@dao queryReviewByNationIdPageByG 查询参数 person = {}", person.toString());
		StringBuilder sql = new StringBuilder();
		@SuppressWarnings("rawtypes")
		List args = new ArrayList();
		sql.append("select * from (select dna_info.dna_check_result as dnaResult,fit_info.result as fitResult,user_info.*,changjing_info.examination_status as examinationStatus,changjing_info.finished_status as finishedStatus,changjing_info.result_status as resultStatus,dna_info.dna_code as dnaCode,dna_info.dna_check_inform_status as dnaCheckInformStatus,dna_info.code_entry_status as codeEntryStatus,fit_info.fit_code as fitCode,fit_info.insert_status as insertStatus   " +
				"from (SELECT   " +
				"r.id, r.name, u.nickName, r.phone   " +
				"   , r.community_dept_id AS communityDeptId, d.`name` AS depName, d2.`name` AS areaName, r.area_dept_id AS areaDeptId" +
				"   , r.sid, r.age, r.sex, r.birth_date AS birthDate, r.item1   " +
				"   , r.item2, r.item3, r.item4, r.item5, r.item6   " +
				"   , r.item7, r.item8, r.item9, r.item10, r.investigator   " +
				"   , r.survey_date AS surveyDate, r.reviewer, r.address, r.in_group_date AS inGroupDate   " +
				"   , CASE    " +
				"      WHEN r.`group` = 'A' THEN 'A'   " +
				"      WHEN r.`group` = 'B' THEN 'B'   " +
				"      WHEN r.`group` = 'C'   " +
				"      AND r.risk_level IS NULL THEN 'C'   " +
				"      WHEN r.`group` = 'C'   " +
				"      AND r.risk_level = 1 THEN 'Cd'   " +
				"      WHEN r.`group` = 'C'   " +
				"      AND r.risk_level = 2 THEN 'Cg'   " +
				"      ELSE NULL   " +
				"   END AS `group`, r.group_status AS groupStatus, r.off_group_reason AS offGroupReason, r.stage_cy AS stageCy, r.review_status AS reviewStatus   " +
				"   , r.risk_factor_status AS riskFactorStatus, r.overall_status_cy AS overallStatusCy, r.overall_status_t0 AS overallStatusT0, r.overall_status_t1 AS overallStatusT1, r.overall_status_t2 AS overallStatusT2   " +
				"   , r.overall_status_t3 AS overallStatusT3, r.violation_plan_status_cy AS violationPlanStatusCy, r.violation_plan_status_t0 AS violationPlanStatusT0, r.violation_plan_status_t1 AS violationPlanStatusT1, r.violation_plan_status_t2 AS violationPlanStatusT2   " +
				"   , r.violation_plan_status_t3 AS violationPlanStatusT3, r.risk_level AS riskLevel, r.site_id AS siteId, r.delete_flag AS deleteFlag, r.date_created AS dateCreated   " +
				"   , r.update_time AS updateTime, r.score AS score   " +
				"FROM   " +
				"   hospital_intestine_review r,   " +
				"   itsys_department d,   " +
				"   itsys_department d2,   " +
				"   itsys_user u   " +
				"WHERE   " +
				"   1 = 1 and    " +
				"r.community_dept_id = d.id AND r.area_dept_id = d2.id and r.create_user = u.loginName");

		if (StringUtils.isNotBlank(person.getSid())) {
			sql.append(" and r.sid like ? ");
			args.add("%" + person.getSid() + "%");
		}

		if (StringUtils.isNotBlank(person.getName())) {
			sql.append(" and r.name like ? ");
			args.add("%" + person.getName() + "%");
		}

		if (StringUtils.isNotBlank(person.getPhone())) {
			sql.append(" and r.phone like ? ");
			args.add("%" +person.getPhone() + "%");
		}

		if (StringUtils.isNotBlank(person.getIdCard())) {
			sql.append(" and r.id_card like ? ");
			args.add("%"+person.getIdCard()+"%");
		}

		if (person.getSex() != null) {
			sql.append(" and r.sex = ? ");
			args.add(person.getSex());
		}

		if (StringUtils.isNotBlank(person.getGroup())) {
			if(person.getGroup().equals("Cg")){
				sql.append(" and r.group = 'C' and r.risk_level = ?");
				args.add(2);
			}else if(person.getGroup().equals("Cd")){
				sql.append(" and r.group = 'C' and r.risk_level = ?");
				args.add(1);
			}else{
				sql.append(" and r.group = ? ");
				args.add(person.getGroup());
			}
		}

		if (person.getOverallStatusCy() != null) {
			sql.append(" and r.overall_status_cy = ? ");
			args.add(person.getOverallStatusCy());
		}

		if (person.getViolationPlanStatusCy() != null) {
			sql.append(" and r.violation_plan_status_cy = ? ");
			args.add(person.getViolationPlanStatusCy());
		}

		if (person.getCommunityDeptId() != null) {
			sql.append(" and r.community_dept_id = ? ");
			args.add(person.getCommunityDeptId());
		}

		if (person.getAreaDeptId() != null) {
			sql.append(" and r.area_dept_id = ? ");
			args.add(person.getAreaDeptId());
		}

		if (StringUtils.isNotBlank(person.getLoginName())) {
			sql.append(" and u.loginName = ? ");
			args.add(person.getLoginName());
		}

		if (StringUtils.isNotBlank(person.getInGroupDateStart())) {
			sql.append(" and r.in_group_date >= ? ");
			args.add(person.getInGroupDateStart() + " 00:00:00");
		}

		if (StringUtils.isNotBlank(person.getInGroupDateEnd())) {
			sql.append(" and r.in_group_date <= ? ");
			args.add(person.getInGroupDateEnd() + " 23:59:59");
		}

		sql.append(" ) as user_info   " +
				"LEFT JOIN (select tab.sid,tab.examination_status,tab.finished_status,tab.result_status from hospital_colonoscopy_record tab where tab.id in(SELECT MAX(hcr.id) FROM hospital_colonoscopy_record hcr GROUP BY hcr.sid)) as changjing_info on user_info.sid = changjing_info.sid    " +
				"LEFT JOIN (select sid,dna_code,dna_check_result,dna_check_inform_status,code_entry_status from hospital_stool_dna tab where tab.id in(SELECT MAX(hsd.id) FROM hospital_stool_dna hsd GROUP BY hsd.sid)) as dna_info on user_info.sid = dna_info.sid   " +
				"LEFT JOIN (select sid,fit_code,result,insert_status from hospital_fit_result tab where tab.id in(SELECT MAX(hfr.id) FROM hospital_fit_result hfr GROUP BY hfr.sid)) as fit_info on user_info.sid = fit_info.sid) as user_all  where  1=1");

		if (person.getExaminationStatus()!=null) {
			if(person.getExaminationStatus()==0){
				sql.append(" and user_all.examinationStatus is null ");
			}else if(person.getExaminationStatus()!=0){
				sql.append(" and user_all.examinationStatus = ? ");
				args.add(person.getExaminationStatus());
			}
		}
		if (person.getFinishedStatus()!=null) {
			sql.append(" and user_all.finishedStatus = ? ");
			args.add(person.getFinishedStatus());
		}



		if(person.getResultStatus()!=null){
			sql.append(" and user_all.resultStatus = ? ");
			args.add(person.getResultStatus());
		}



		if(!StringUtils.isEmpty(person.getFitCode())){
			sql.append(" and user_all.fitCode like ? ");
			args.add("%"+person.getFitCode()+"%");
		}

		if(person.getFitResult()!=null){
			sql.append(" and user_all.fitResult = ? ");
			args.add(person.getFitResult());
		}

		if(person.getInsertStatus()!=null){
			sql.append(" and user_all.insertStatus = ? ");
			args.add(person.getInsertStatus());
		}


		if(!StringUtils.isEmpty(person.getDnaCode())) {
			sql.append(" and user_all.dnaCode like ? ");
			args.add("%" + person.getDnaCode() + "%");
		}

		if(person.getDnaResult()!=null){
			sql.append(" and user_all.dnaResult = ? ");
			args.add(person.getDnaResult());
		}

		if(person.getDnaCheckInformStatus()!=null){
			sql.append(" and user_all.dnaCheckInformStatus = ? ");
			args.add(person.getDnaCheckInformStatus());
		}

		if(person.getCodeEntryStatus()!=null){
			sql.append(" and user_all.codeEntryStatus = ? ");
			args.add(person.getCodeEntryStatus());
		}

		if(StringUtils.isNotBlank(person.getSortParameter())&&StringUtils.isNotBlank(person.getSortRule())){
			sql.append("  ORDER BY  user_all."+person.getSortParameter()+" "+person.getSortRule().toUpperCase());
		}else{
			sql.append("  ORDER BY  user_all.dateCreated DESC ");
		}

		ListPageUtil listPage = new ListPageUtil(sql.toString(), args.toArray(), person.getPageNo(), person.getPageSize(), jdbcTemplate, null, null);
		return listPage;
	}

    @Override
    public List lesionStatisticsByNation(int nationId, TodoVo vo) {

        String s1 = "";
        if(!StringUtil.isEmpty(vo.getStartDate()) && !StringUtil.isEmpty(vo.getEndDate())){
            s1 = " and date_format(result.date_created,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(result.date_created,'%Y-%m-%d') <= '"+vo.getEndDate()+"' ";
        }
        List<Map<String, Object>> arerlists = getAreaByNationId(nationId);
        List<Map<String,Object>> rtnlist = new ArrayList<>();
        List<Map<String, Object>> natilnlists = getAreaByAreaId(nationId);
        String sql = "SELECT " +
                " finishTab.sid AS sid, " +
                " finishTab.`group` AS `group`, " +
                " finishTab.risk_level AS riskLevel, " +
                " finishTab.community_dept_id AS communityDeptId, " +
                " finishTab.area_dept_id AS areaDeptId, " +
                " finishTab.id AS recordId, " +
                " finishTab.pathology_id AS pathologyId, " +
                " finishTab.result_id AS resultId, " +
                " pathologyTab.bm AS bm, " +
                " recordTab.zj AS zj " +
                "FROM " +
                " ( " +
                "  SELECT " +
                "   r.id, " +
                "   r.pathology_id, " +
                "   r.result_id, " +
                "   r.sid, " +
                "   h.`group`, " +
                "   h.risk_level, " +
                "   r.community_dept_id, " +
                "   r.area_dept_id " +
                "  FROM " +
                "   hospital_colonoscopy_record r, " +
                "   hospital_intestine_review h " +
                "  WHERE " +
                "   r.sid = h.sid " +
                "  AND r.id IN ( " +
                "   SELECT " +
                "    max(r.id) " +
                "   FROM " +
                "    hospital_colonoscopy_record r " +
                "   GROUP BY " +
                "    r.sid " +
                "  ) " +
                "  AND r.finished_status = 2 " +
                " ) AS finishTab " +
                "LEFT JOIN ( " +
                " SELECT " +
                "  pr.pathology_result_id, " +
                "  GROUP_CONCAT(pr.item4 SEPARATOR ',') AS bm " +
                " FROM " +
                "  hospital_colonoscopy_pathology_diagnosis_record pr " +
                " GROUP BY " +
                "  pr.pathology_result_id " +
                ") AS pathologyTab ON pathologyTab.pathology_result_id = finishTab.pathology_id " +
                "LEFT JOIN ( " +
                " SELECT " +
                "  lr.colonoscopy_result_id, " +
                "  GROUP_CONCAT(lr.item5 SEPARATOR ',') AS zj " +
                " FROM " +
                "  hospital_colonoscopy_lesions_record lr " +
                " GROUP BY " +
                "  lr.colonoscopy_result_id " +
                ") AS recordTab ON recordTab.colonoscopy_result_id = finishTab.result_id " +
                " LEFT JOIN hospital_colonoscopy_result result ON finishTab.result_id = result.id " +
                " WHERE " +
                " 1 = 1 "
                + s1;
        List<LesionStatisticsVo> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(LesionStatisticsVo.class));
        for(Map<String, Object> nationMap : natilnlists){
            String nationName = nationMap.get("name").toString();
            Integer nationA1 = 0;   //A组结直肠癌
            Integer nationA2 = 0;   //A组进展期腺瘤
            Integer nationA3 = 0;   //A组非进展期腺瘤
            Integer nationA4 = 0;   //A组其他息肉性病变或者炎症
            Integer nationA5 = 0;   //A组肠镜数

            Integer nationB1 = 0;   //B组结直肠癌
            Integer nationB2 = 0;   //B组进展期腺瘤
            Integer nationB3 = 0;   //B组非进展期腺瘤
            Integer nationB4 = 0;   //B组其他息肉性病变或者炎症
            Integer nationB5 = 0;   //B组肠镜数

            Integer nationCg1 = 0;  //C组高危结直肠癌
            Integer nationCg2 = 0;  //C组高危进展期腺瘤
            Integer nationCg3 = 0;  //C组高危非进展期腺瘤
            Integer nationCg4 = 0;  //C组高危其他息肉性病变或者炎症
            Integer nationCg5 = 0;  //C组高危肠镜数

            Integer nationCd1 = 0;  //C组低危结直肠癌
            Integer nationCd2 = 0;  //C组低危进展期腺瘤
            Integer nationCd3 = 0;  //C组低危非进展期腺瘤
            Integer nationCd4 = 0;  //C组低危其他息肉性病变或者炎症
            Integer nationCd5 = 0;  //C组低危肠镜数

            Integer nationCp1 = 0;  //C组未评估结直肠癌
            Integer nationCp2 = 0;  //C组未评估进展期腺瘤
            Integer nationCp3 = 0;  //C组未评估非进展期腺瘤
            Integer nationCp4 = 0;  //C组未评估其他息肉性病变或者炎症
            Integer nationCp5 = 0;  //C组未评估肠镜数

            Integer nationC1 = 0;  //C组结直肠癌
            Integer nationC2 = 0;  //C组进展期腺瘤
            Integer nationC3 = 0;  //C组非进展期腺瘤
            Integer nationC4 = 0;  //C组其他息肉性病变或者炎症
            Integer nationC5 = 0;  //C组肠镜数

            Integer nationSum1 = 0; //总计结直肠癌
            Integer nationSum2 = 0; //总计进展期腺瘤
            Integer nationSum3 = 0; //总计非进展期腺瘤
            Integer nationSum4 = 0; //总计其他息肉性病变或者炎症
            Integer nationSum5 = 0; //总计肠镜数

            for(Map<String, Object> map : arerlists){
                Map<String, Object> arrs = new LinkedHashMap<String,Object>();
                List<String> rs = new ArrayList<>();
                int areaIds = Integer.parseInt(map.get("id").toString()) ;
                String areaName = map.get("name").toString();
                Integer a1 = 0;     //A组结直肠癌
                Integer a2 = 0;     //A组进展期腺瘤
                Integer a3 = 0;     //A组非进展期腺瘤
                Integer a4 = 0;     //A组其他息肉性病变或者炎症
                Integer a5 = 0;     //A组肠镜数

                Integer b1 = 0;     //B组结直肠癌
                Integer b2 = 0;     //B组进展期腺瘤
                Integer b3 = 0;     //B组非进展期腺瘤
                Integer b4 = 0;     //B组其他息肉性病变或者炎症
                Integer b5 = 0;     //B组肠镜数

                Integer cg1 = 0;    //C组高危结直肠癌
                Integer cg2 = 0;    //C组高危进展期腺瘤
                Integer cg3 = 0;    //C组高危非进展期腺瘤
                Integer cg4 = 0;    //C组高危其他息肉性病变或者炎症
                Integer cg5 = 0;    //C组高危肠镜数

                Integer cd1 = 0;    //C组低危结直肠癌
                Integer cd2 = 0;    //C组低危进展期腺瘤
                Integer cd3 = 0;    //C组低危非进展期腺瘤
                Integer cd4 = 0;    //C组低危其他息肉性病变或者炎症
                Integer cd5 = 0;    //C组低危肠镜数

                Integer cp1 = 0;    //C组未评估结直肠癌
                Integer cp2 = 0;    //C组未评估进展期腺瘤
                Integer cp3 = 0;    //C组未评估非进展期腺瘤
                Integer cp4 = 0;    //C组未评估其他息肉性病变或者炎症
                Integer cp5 = 0;    //C组未评估肠镜数

                Integer c1 = 0;    //C组结直肠癌
                Integer c2 = 0;    //C组进展期腺瘤
                Integer c3 = 0;    //C组非进展期腺瘤
                Integer c4 = 0;    //C组其他息肉性病变或者炎症
                Integer c5 = 0;    //C组肠镜数

                Integer sum1 = 0;   //总计结直肠癌
                Integer sum2 = 0;   //总计进展期腺瘤
                Integer sum3 = 0;   //总计非进展期腺瘤
                Integer sum4 = 0;   //总计其他息肉性病变或者炎症
                Integer sum5 = 0;   //总计肠镜数

                for(LesionStatisticsVo obj : list){
                    if(obj.getAreaDeptId() == areaIds){
                        Set<String> set = new HashSet<>();      //编码集合

                        BigDecimal bigDecimal = new BigDecimal(0);//最大直径
                        if(obj.getZj() != null && !"".equals(obj.getZj())){
                            bigDecimal = new BigDecimal(Collections.max(Arrays.asList(obj.getZj().split(","))));
                        }
                        if(obj.getBm() != null && !"".equals(obj.getBm())){
                            set = new HashSet<>(Arrays.asList(obj.getBm().split(",")));
                            if(set.contains("17") || set.contains("18") || set.contains("19")){
                                if("A".equals(obj.getGroup())){
                                    a1 ++;
                                }else if("B".equals(obj.getGroup())){
                                    b1++;
                                }else if("C".equals(obj.getGroup()) && obj.getRiskLevel() == 2){
                                    cg1++;
                                }else if("C".equals(obj.getGroup()) && obj.getRiskLevel() == 1){
                                    cd1++;
                                }else if("C".equals(obj.getGroup()) && obj.getRiskLevel() == null){
                                    cp1++;
                                }
                            }else if((set.contains("07") || set.contains("11") || set.contains("12") || set.contains("14") || set.contains("15")) ||
                                    ((set.contains("08") || set.contains("09") || set.contains("10") || set.contains("13") || set.contains("16")) && bigDecimal.compareTo(new BigDecimal(1.0)) >= 0)
                                    ){
                                if("A".equals(obj.getGroup())){
                                    a2 ++;
                                }else if("B".equals(obj.getGroup())){
                                    b2++;
                                }else if("C".equals(obj.getGroup()) && obj.getRiskLevel() == 2){
                                    cg2++;
                                }else if("C".equals(obj.getGroup()) && obj.getRiskLevel() == 1){
                                    cd2++;
                                }else if("C".equals(obj.getGroup()) && obj.getRiskLevel() == null){
                                    cp2++;
                                }
                            }else if((set.contains("08") || set.contains("10") || set.contains("13")) && bigDecimal.compareTo(new BigDecimal(1.0)) < 0){
                                if("A".equals(obj.getGroup())){
                                    a3 ++;
                                }else if("B".equals(obj.getGroup())){
                                    b3++;
                                }else if("C".equals(obj.getGroup()) && obj.getRiskLevel() == 2){
                                    cg3++;
                                }else if("C".equals(obj.getGroup()) && obj.getRiskLevel() == 1){
                                    cd3++;
                                }else if("C".equals(obj.getGroup()) && obj.getRiskLevel() == null){
                                    cp3++;
                                }
                            }else if((set.contains("02") || set.contains("03") || set.contains("04") || set.contains("05") || set.contains("06")) && bigDecimal.compareTo(new BigDecimal(1.0)) < 0){
                                if("A".equals(obj.getGroup())){
                                    a4 ++;
                                }else if("B".equals(obj.getGroup())){
                                    b4++;
                                }else if("C".equals(obj.getGroup()) && obj.getRiskLevel() == 2){
                                    cg4++;
                                }else if("C".equals(obj.getGroup()) && obj.getRiskLevel() == 1){
                                    cd4++;
                                }else if("C".equals(obj.getGroup()) && obj.getRiskLevel() == null){
                                    cp4++;
                                }
                            }
                        }


                        //计算肠镜数
                        if("A".equals(obj.getGroup())){
                            a5 ++;
                        }else if("B".equals(obj.getGroup())){
                            b5++;
                        }else if("C".equals(obj.getGroup()) && obj.getRiskLevel() == 2){
                            cg5++;
                        }else if("C".equals(obj.getGroup()) && obj.getRiskLevel() == 1){
                            cd5++;
                        }else if("C".equals(obj.getGroup()) && obj.getRiskLevel() == null){
                            cp5++;
                        }

                        //计算C组
                        c1 = cg1 + cd1 + cp1;
                        c2 = cg2 + cd2 + cp2;
                        c3 = cg3 + cd3 + cp3;
                        c4 = cg4 + cd4 + cp4;
                        c5 = cg5 + cd5 + cp5;

                    }
                }

                List resultA = new ArrayList();
                Map map1 = new HashMap();
                map1.put("colorectalCancerNum",a1 + "(" + IntegerUtil.calculationProportion(a1,a5) + ")");
                map1.put("advancedAdenomaNum",a2 + "(" + IntegerUtil.calculationProportion(a2,a5) + ")");
                map1.put("nonAdvancedAdenomaNum",a3 + "(" + IntegerUtil.calculationProportion(a3,a5) + ")");
                map1.put("otherLesionsNum",a4 + "(" + IntegerUtil.calculationProportion(a4,a5) + ")");
                map1.put("colonoscopyNum",a5);
                resultA.add(map1);
                String jsons1 = resultA.toString().substring(1,resultA.toString().length());
                String sumdepts1 = jsons1.substring(0, jsons1.length()-1);
                rs.add(sumdepts1);

                List resultB = new ArrayList();
                Map map2 = new HashMap();
                map2.put("colorectalCancerNum",b1 + "(" + IntegerUtil.calculationProportion(b1,b5) + ")");
                map2.put("advancedAdenomaNum",b2 + "(" + IntegerUtil.calculationProportion(b2,b5) + ")");
                map2.put("nonAdvancedAdenomaNum",b3 + "(" + IntegerUtil.calculationProportion(b3,b5) + ")");
                map2.put("otherLesionsNum",b4 + "(" + IntegerUtil.calculationProportion(b4,b5) + ")");
                map2.put("colonoscopyNum",b5);
                resultB.add(map2);
                String jsons2 = resultB.toString().substring(1,resultB.toString().length());
                String sumdepts2 = jsons2.substring(0, jsons2.length()-1);
                rs.add(sumdepts2);

                List resultCg = new ArrayList();
                Map map3 = new HashMap();
                map3.put("colorectalCancerNum",cg1 + "(" + IntegerUtil.calculationProportion(cg1,cg5) + ")");
                map3.put("advancedAdenomaNum",cg2 + "(" + IntegerUtil.calculationProportion(cg2,cg5) + ")");
                map3.put("nonAdvancedAdenomaNum",cg3 + "(" + IntegerUtil.calculationProportion(cg3,cg5) + ")");
                map3.put("otherLesionsNum",cg4 + "(" + IntegerUtil.calculationProportion(cg4,cg5) + ")");
                map3.put("colonoscopyNum",cg5);
                resultCg.add(map3);
                String jsons3 = resultCg.toString().substring(1,resultCg.toString().length());
                String sumdepts3 = jsons3.substring(0, jsons3.length()-1);
                rs.add(sumdepts3);

                List resultCd = new ArrayList();
                Map map4 = new HashMap();
                map4.put("colorectalCancerNum",cd1 + "(" + IntegerUtil.calculationProportion(cd1,cd5) + ")");
                map4.put("advancedAdenomaNum",cd2 + "(" + IntegerUtil.calculationProportion(cd2,cd5) + ")");
                map4.put("nonAdvancedAdenomaNum",cd3 + "(" + IntegerUtil.calculationProportion(cd3,cd5) + ")");
                map4.put("otherLesionsNum",cd4 + "(" + IntegerUtil.calculationProportion(cd4,cd5) + ")");
                map4.put("colonoscopyNum",cd5);
                resultCd.add(map4);
                String jsons4 = resultCd.toString().substring(1,resultCd.toString().length());
                String sumdepts4 = jsons4.substring(0, jsons4.length()-1);
                rs.add(sumdepts4);


                List resultCp = new ArrayList();
                Map map5 = new HashMap();
                map5.put("colorectalCancerNum",cp1 + "(" + IntegerUtil.calculationProportion(cp1,cp5) + ")");
                map5.put("advancedAdenomaNum",cp2 + "(" + IntegerUtil.calculationProportion(cp2,cp5) + ")");
                map5.put("nonAdvancedAdenomaNum",cp3 + "(" + IntegerUtil.calculationProportion(cp3,cp5) + ")");
                map5.put("otherLesionsNum",cp4 + "(" + IntegerUtil.calculationProportion(cp4,cp5) + ")");
                map5.put("colonoscopyNum",cp5);
                resultCp.add(map5);
                String jsons5 = resultCp.toString().substring(1,resultCp.toString().length());
                String sumdepts5 = jsons5.substring(0, jsons5.length()-1);
                rs.add(sumdepts5);

                List resultC = new ArrayList();
                Map map6 = new HashMap();
                map6.put("colorectalCancerNum",c1 + "(" + IntegerUtil.calculationProportion(c1,c5) + ")");
                map6.put("advancedAdenomaNum",c2 + "(" + IntegerUtil.calculationProportion(c2,c5) + ")");
                map6.put("nonAdvancedAdenomaNum",c3 + "(" + IntegerUtil.calculationProportion(c3,c5) + ")");
                map6.put("otherLesionsNum",c4 + "(" + IntegerUtil.calculationProportion(c4,c5) + ")");
                map6.put("colonoscopyNum",c5);
                resultC.add(map6);
                String jsons6 = resultC.toString().substring(1,resultC.toString().length());
                String sumdepts6 = jsons6.substring(0, jsons6.length()-1);
                rs.add(sumdepts6);

                //计算地区总计
                sum1 = a1 + b1 + cg1 + cd1 + cp1;
                sum2 = a2 + b2 + cg2 + cd2 + cp2;
                sum3 = a3 + b3 + cg3 + cd3 + cp3;
                sum4 = a4 + b4 + cg4 + cd4 + cp4;
                sum5 = a5 + b5 + cg5 + cd5 + cp5;

                List resultSum = new ArrayList();
                Map map7 = new HashMap();
                map7.put("colorectalCancerNum",sum1 + "(" + IntegerUtil.calculationProportion(sum1,sum5) + ")");
                map7.put("advancedAdenomaNum",sum2 + "(" + IntegerUtil.calculationProportion(sum2,sum5) + ")");
                map7.put("nonAdvancedAdenomaNum",sum3 + "(" + IntegerUtil.calculationProportion(sum3,sum5) + ")");
                map7.put("otherLesionsNum",sum4 + "(" + IntegerUtil.calculationProportion(sum4,sum5) + ")");
                map7.put("colonoscopyNum",sum5);
                resultSum.add(map7);
                String jsons7 = resultSum.toString().substring(1,resultSum.toString().length());
                String sumdepts7 = jsons7.substring(0, jsons7.length()-1);
                rs.add(sumdepts7);

                arrs.put("dept",areaName);
                arrs.put("list",rs);
                rtnlist.add(arrs);

                //计算国家总计
                nationA1 += a1;
                nationA2 += a2;
                nationA3 += a3;
                nationA4 += a4;
                nationA5 += a5;

                nationB1 += b1;
                nationB2 += b2;
                nationB3 += b3;
                nationB4 += b4;
                nationB5 += b5;

                nationCg1 += cg1;
                nationCg2 += cg2;
                nationCg3 += cg3;
                nationCg4 += cg4;
                nationCg5 += cg5;

                nationCd1 += cd1;
                nationCd2 += cd2;
                nationCd3 += cd3;
                nationCd4 += cd4;
                nationCd5 += cd5;

                nationCp1 += cp1;
                nationCp2 += cp2;
                nationCp3 += cp3;
                nationCp4 += cp4;
                nationCp5 += cp5;

                nationC1 += c1;
                nationC2 += c2;
                nationC3 += c3;
                nationC4 += c4;
                nationC5 += c5;

                nationSum1 += sum1;
                nationSum2 += sum2;
                nationSum3 += sum3;
                nationSum4 += sum4;
                nationSum5 += sum5;
            }

            List<String> rs = new ArrayList<>();
            Map<String, Object> arrs = new LinkedHashMap<String,Object>();
            List resultA = new ArrayList();
            Map map1 = new HashMap();
            map1.put("colorectalCancerNum",nationA1 + "(" + IntegerUtil.calculationProportion(nationA1,nationA5) + ")");
            map1.put("advancedAdenomaNum",nationA2 + "(" + IntegerUtil.calculationProportion(nationA2,nationA5) + ")");
            map1.put("nonAdvancedAdenomaNum",nationA3 + "(" + IntegerUtil.calculationProportion(nationA3,nationA5) + ")");
            map1.put("otherLesionsNum",nationA4 + "(" + IntegerUtil.calculationProportion(nationA4,nationA5) + ")");
            map1.put("colonoscopyNum",nationA5);
            resultA.add(map1);
            String jsons1 = resultA.toString().substring(1,resultA.toString().length());
            String sumdepts1 = jsons1.substring(0, jsons1.length()-1);
            rs.add(sumdepts1);

            List resultB = new ArrayList();
            Map map2 = new HashMap();
            map2.put("colorectalCancerNum",nationB1 + "(" + IntegerUtil.calculationProportion(nationB1,nationB5) + ")");
            map2.put("advancedAdenomaNum",nationB2 + "(" + IntegerUtil.calculationProportion(nationB2,nationB5) + ")");
            map2.put("nonAdvancedAdenomaNum",nationB3 + "(" + IntegerUtil.calculationProportion(nationB3,nationB5) + ")");
            map2.put("otherLesionsNum",nationB4 + "(" + IntegerUtil.calculationProportion(nationB4,nationB5) + ")");
            map2.put("colonoscopyNum",nationB5);
            resultB.add(map2);
            String jsons2 = resultB.toString().substring(1,resultB.toString().length());
            String sumdepts2 = jsons2.substring(0, jsons2.length()-1);
            rs.add(sumdepts2);

            List resultCg = new ArrayList();
            Map map3 = new HashMap();
            map3.put("colorectalCancerNum",nationCg1 + "(" + IntegerUtil.calculationProportion(nationCg1,nationCg5) + ")");
            map3.put("advancedAdenomaNum",nationCg2 + "(" + IntegerUtil.calculationProportion(nationCg2,nationCg5) + ")");
            map3.put("nonAdvancedAdenomaNum",nationCg3 + "(" + IntegerUtil.calculationProportion(nationCg3,nationCg5) + ")");
            map3.put("otherLesionsNum",nationCg4 + "(" + IntegerUtil.calculationProportion(nationCg4,nationCg5) + ")");
            map3.put("colonoscopyNum",nationCg5);
            resultCg.add(map3);
            String jsons3 = resultCg.toString().substring(1,resultCg.toString().length());
            String sumdepts3 = jsons3.substring(0, jsons3.length()-1);
            rs.add(sumdepts3);

            List resultCd = new ArrayList();
            Map map4 = new HashMap();
            map4.put("colorectalCancerNum",nationCd1 + "(" + IntegerUtil.calculationProportion(nationCd1,nationCd5) + ")");
            map4.put("advancedAdenomaNum",nationCd2 + "(" + IntegerUtil.calculationProportion(nationCd2,nationCd5) + ")");
            map4.put("nonAdvancedAdenomaNum",nationCd3 + "(" + IntegerUtil.calculationProportion(nationCd3,nationCd5) + ")");
            map4.put("otherLesionsNum",nationCd4 + "(" + IntegerUtil.calculationProportion(nationCd4,nationCd5) + ")");
            map4.put("colonoscopyNum",nationCd5);
            resultCd.add(map4);
            String jsons4 = resultCd.toString().substring(1,resultCd.toString().length());
            String sumdepts4 = jsons4.substring(0, jsons4.length()-1);
            rs.add(sumdepts4);


            List resultCp = new ArrayList();
            Map map5 = new HashMap();
            map5.put("colorectalCancerNum",nationCp1 + "(" + IntegerUtil.calculationProportion(nationCp1,nationCp5) + ")");
            map5.put("advancedAdenomaNum",nationCp2 + "(" + IntegerUtil.calculationProportion(nationCp2,nationCp5) + ")");
            map5.put("nonAdvancedAdenomaNum",nationCp3 + "(" + IntegerUtil.calculationProportion(nationCp3,nationCp5) + ")");
            map5.put("otherLesionsNum",nationCp4 + "(" + IntegerUtil.calculationProportion(nationCp4,nationCp5) + ")");
            map5.put("colonoscopyNum",nationCp5);
            resultCp.add(map5);
            String jsons5 = resultCp.toString().substring(1,resultCp.toString().length());
            String sumdepts5 = jsons5.substring(0, jsons5.length()-1);
            rs.add(sumdepts5);

            List resultC = new ArrayList();
            Map map6 = new HashMap();
            map6.put("colorectalCancerNum",nationC1 + "(" + IntegerUtil.calculationProportion(nationC1,nationC5) + ")");
            map6.put("advancedAdenomaNum",nationC2 + "(" + IntegerUtil.calculationProportion(nationC2,nationC5) + ")");
            map6.put("nonAdvancedAdenomaNum",nationC3 + "(" + IntegerUtil.calculationProportion(nationC3,nationC5) + ")");
            map6.put("otherLesionsNum",nationC4 + "(" + IntegerUtil.calculationProportion(nationC4,nationC5) + ")");
            map6.put("colonoscopyNum",nationC5);
            resultC.add(map6);
            String jsons6 = resultC.toString().substring(1,resultC.toString().length());
            String sumdepts6 = jsons6.substring(0, jsons6.length()-1);
            rs.add(sumdepts6);

            List resultSum = new ArrayList();
            Map map7 = new HashMap();
            map7.put("colorectalCancerNum",nationSum1 + "(" + IntegerUtil.calculationProportion(nationSum1,nationSum5) + ")");
            map7.put("advancedAdenomaNum",nationSum2 + "(" + IntegerUtil.calculationProportion(nationSum2,nationSum5) + ")");
            map7.put("nonAdvancedAdenomaNum",nationSum3 + "(" + IntegerUtil.calculationProportion(nationSum3,nationSum5) + ")");
            map7.put("otherLesionsNum",nationSum4 + "(" + IntegerUtil.calculationProportion(nationSum4,nationSum5) + ")");
            map7.put("colonoscopyNum",nationSum5);
            resultSum.add(map7);
            String jsons7 = resultSum.toString().substring(1,resultSum.toString().length());
            String sumdepts7 = jsons7.substring(0, jsons7.length()-1);
            rs.add(sumdepts7);

            arrs.put("dept",nationName);
            arrs.put("list",rs);
            rtnlist.add(arrs);
        }
        return rtnlist;
    }

    @Override
    public List lesionStatisticsByArea(int areaId, TodoVo vo) {

        String s1 = "";
        if(!StringUtil.isEmpty(vo.getStartDate()) && !StringUtil.isEmpty(vo.getEndDate())){
            s1 = " and date_format(result.date_created,'%Y-%m-%d') >= '"+vo.getStartDate()+"' and date_format(result.date_created,'%Y-%m-%d') <= '"+vo.getEndDate()+"'";
        }
        List<Map<String, Object>> communits = hcrAllocationDao.getcommdeptsByAreaId(areaId);
        List<Map<String,Object>> rtnlist = new ArrayList<>();
        List<Map<String, Object>> arealists = getAreaByAreaId(areaId);
        String sql = "SELECT " +
                "  finishTab.sid AS sid, " +
                "  finishTab.`group` AS `group`, " +
                "  finishTab.risk_level AS riskLevel, " +
                "  finishTab.community_dept_id AS communityDeptId, " +
                "  finishTab.area_dept_id AS areaDeptId, " +
                "  finishTab.id AS recordId, " +
                "  finishTab.pathology_id AS pathologyId, " +
                "  finishTab.result_id AS resultId, " +
                "  pathologyTab.bm AS bm, " +
                "  recordTab.zj AS zj " +
                " FROM " +
                "  ( " +
                "   SELECT " +
                "    r.id, " +
                "    r.pathology_id, " +
                "    r.result_id, " +
                "    r.sid, " +
                "    h.`group`, " +
                "    h.risk_level, " +
                "    r.community_dept_id, " +
                "    r.area_dept_id " +
                "   FROM " +
                "    hospital_colonoscopy_record r, " +
                "    hospital_intestine_review h " +
                "   WHERE " +
                "    r.sid = h.sid " +
                "   AND r.id IN ( " +
                "    SELECT " +
                "     max(r.id) " +
                "    FROM " +
                "     hospital_colonoscopy_record r " +
                "    GROUP BY " +
                "     r.sid " +
                "   ) " +
                "   AND r.finished_status = 2 " +
                "  ) AS finishTab " +
                " LEFT JOIN ( " +
                "  SELECT " +
                "   pr.pathology_result_id, " +
                "   GROUP_CONCAT(pr.item4 SEPARATOR ',') AS bm " +
                "  FROM " +
                "   hospital_colonoscopy_pathology_diagnosis_record pr " +
                "  GROUP BY " +
                "   pr.pathology_result_id " +
                " ) AS pathologyTab ON pathologyTab.pathology_result_id = finishTab.pathology_id " +
                " LEFT JOIN ( " +
                "  SELECT " +
                "   lr.colonoscopy_result_id, " +
                "   GROUP_CONCAT(lr.item5 SEPARATOR ',') AS zj " +
                "  FROM " +
                "   hospital_colonoscopy_lesions_record lr " +
                "  GROUP BY " +
                "   lr.colonoscopy_result_id " +
                " ) AS recordTab ON recordTab.colonoscopy_result_id = finishTab.result_id "+
                " LEFT JOIN hospital_colonoscopy_result result ON finishTab.result_id = result.id " +
                " WHERE " +
                " 1 = 1 "
                + s1;
        List<LesionStatisticsVo> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(LesionStatisticsVo.class));
        for(Map<String, Object> areaMap : arealists){
            String areaName = areaMap.get("name").toString();
            Integer areaA1 = 0;   //A组结直肠癌
            Integer areaA2 = 0;   //A组进展期腺瘤
            Integer areaA3 = 0;   //A组非进展期腺瘤
            Integer areaA4 = 0;   //A组其他息肉性病变或者炎症
            Integer areaA5 = 0;   //A组肠镜数

            Integer areaB1 = 0;   //B组结直肠癌
            Integer areaB2 = 0;   //B组进展期腺瘤
            Integer areaB3 = 0;   //B组非进展期腺瘤
            Integer areaB4 = 0;   //B组其他息肉性病变或者炎症
            Integer areaB5 = 0;   //B组肠镜数

            Integer areaCg1 = 0;  //C组高危结直肠癌
            Integer areaCg2 = 0;  //C组高危进展期腺瘤
            Integer areaCg3 = 0;  //C组高危非进展期腺瘤
            Integer areaCg4 = 0;  //C组高危其他息肉性病变或者炎症
            Integer areaCg5 = 0;  //C组高危肠镜数

            Integer areaCd1 = 0;  //C组低危结直肠癌
            Integer areaCd2 = 0;  //C组低危进展期腺瘤
            Integer areaCd3 = 0;  //C组低危非进展期腺瘤
            Integer areaCd4 = 0;  //C组低危其他息肉性病变或者炎症
            Integer areaCd5 = 0;  //C组低危肠镜数

            Integer areaCp1 = 0;  //C组未评估结直肠癌
            Integer areaCp2 = 0;  //C组未评估进展期腺瘤
            Integer areaCp3 = 0;  //C组未评估非进展期腺瘤
            Integer areaCp4 = 0;  //C组未评估其他息肉性病变或者炎症
            Integer areaCp5 = 0;  //C组未评估肠镜数

            Integer areaC1 = 0;  //C组结直肠癌
            Integer areaC2 = 0;  //C组进展期腺瘤
            Integer areaC3 = 0;  //C组非进展期腺瘤
            Integer areaC4 = 0;  //C组其他息肉性病变或者炎症
            Integer areaC5 = 0;  //C组肠镜数

            Integer areaSum1 = 0; //总计结直肠癌
            Integer areaSum2 = 0; //总计进展期腺瘤
            Integer areaSum3 = 0; //总计非进展期腺瘤
            Integer areaSum4 = 0; //总计其他息肉性病变或者炎症
            Integer areaSum5 = 0; //总计肠镜数

            for(Map<String, Object> map : communits){
                Map<String, Object> arrs = new LinkedHashMap<String,Object>();
                List<String> rs = new ArrayList<>();
                int communitId = Integer.parseInt(map.get("id").toString()) ;
                String communitName = map.get("commdeptName").toString();
                Integer a1 = 0;     //A组结直肠癌
                Integer a2 = 0;     //A组进展期腺瘤
                Integer a3 = 0;     //A组非进展期腺瘤
                Integer a4 = 0;     //A组其他息肉性病变或者炎症
                Integer a5 = 0;     //A组肠镜数

                Integer b1 = 0;     //B组结直肠癌
                Integer b2 = 0;     //B组进展期腺瘤
                Integer b3 = 0;     //B组非进展期腺瘤
                Integer b4 = 0;     //B组其他息肉性病变或者炎症
                Integer b5 = 0;     //B组肠镜数

                Integer cg1 = 0;    //C组高危结直肠癌
                Integer cg2 = 0;    //C组高危进展期腺瘤
                Integer cg3 = 0;    //C组高危非进展期腺瘤
                Integer cg4 = 0;    //C组高危其他息肉性病变或者炎症
                Integer cg5 = 0;    //C组高危肠镜数

                Integer cd1 = 0;    //C组低危结直肠癌
                Integer cd2 = 0;    //C组低危进展期腺瘤
                Integer cd3 = 0;    //C组低危非进展期腺瘤
                Integer cd4 = 0;    //C组低危其他息肉性病变或者炎症
                Integer cd5 = 0;    //C组低危肠镜数

                Integer cp1 = 0;    //C组未评估结直肠癌
                Integer cp2 = 0;    //C组未评估进展期腺瘤
                Integer cp3 = 0;    //C组未评估非进展期腺瘤
                Integer cp4 = 0;    //C组未评估其他息肉性病变或者炎症
                Integer cp5 = 0;    //C组未评估肠镜数

                Integer c1 = 0;    //C组结直肠癌
                Integer c2 = 0;    //C组进展期腺瘤
                Integer c3 = 0;    //C组非进展期腺瘤
                Integer c4 = 0;    //C组其他息肉性病变或者炎症
                Integer c5 = 0;    //C组肠镜数

                Integer sum1 = 0;   //总计结直肠癌
                Integer sum2 = 0;   //总计进展期腺瘤
                Integer sum3 = 0;   //总计非进展期腺瘤
                Integer sum4 = 0;   //总计其他息肉性病变或者炎症
                Integer sum5 = 0;   //总计肠镜数

                for(LesionStatisticsVo obj : list){
                	if(obj.getCommunityDeptId().equals(communitId)){
						Set<String> set = new HashSet<>();      //编码集合

                        BigDecimal bigDecimal = new BigDecimal(0);//最大直径
                        if(obj.getZj() != null && !"".equals(obj.getZj())){
                            bigDecimal = new BigDecimal(Collections.max(Arrays.asList( obj.getZj().split(","))));
                        }
                        if(obj.getBm() != null && !"".equals(obj.getBm())){
                            set = new HashSet<>(Arrays.asList(obj.getBm().split(",")));
                            if(set.contains("17") || set.contains("18") || set.contains("19")){
                                if("A".equals(obj.getGroup())){
                                    a1 ++;
                                }else if("B".equals(obj.getGroup())){
                                    b1++;
                                }else if("C".equals(obj.getGroup()) && obj.getRiskLevel() == 2){
                                    cg1++;
                                }else if("C".equals(obj.getGroup()) && obj.getRiskLevel() == 1){

                                    cd1++;
                                }else if("C".equals(obj.getGroup()) && obj.getRiskLevel() == null){
                                    cp1++;
                                }
                            }else if((set.contains("07") || set.contains("11") || set.contains("12") || set.contains("14") || set.contains("15")) ||
                                    ((set.contains("08") || set.contains("09") || set.contains("10") || set.contains("13") || set.contains("16")) )
                                    ){
                                if("A".equals(obj.getGroup())){
                                    a2 ++;
                                }else if("B".equals(obj.getGroup())){
                                    b2++;
                                }else if("C".equals(obj.getGroup()) && obj.getRiskLevel() == 2){
                                    cg2++;
                                }else if("C".equals(obj.getGroup()) && obj.getRiskLevel() == 1){
                                    cd2++;
                                }else if("C".equals(obj.getGroup()) && obj.getRiskLevel() == null){
                                    cp2++;
                                }
                            }else if((set.contains("08") || set.contains("10") || set.contains("13")) && bigDecimal.compareTo(new BigDecimal(1.0)) < 0){
                                if("A".equals(obj.getGroup())){
                                    a3 ++;
                                }else if("B".equals(obj.getGroup())){
                                    b3++;
                                }else if("C".equals(obj.getGroup()) && obj.getRiskLevel() == 2){
                                    cg3++;
                                }else if("C".equals(obj.getGroup()) && obj.getRiskLevel() == 1){
                                    cd3++;
                                }else if("C".equals(obj.getGroup()) && obj.getRiskLevel() == null){
                                    cp3++;
                                }
                            }else if(set.contains("02") || set.contains("03") || set.contains("04") || set.contains("05") || set.contains("06")){
                                if("A".equals(obj.getGroup())){
                                    a4 ++;
                                }else if("B".equals(obj.getGroup())){
                                    b4++;
                                }else if("C".equals(obj.getGroup()) && obj.getRiskLevel() == 2){
                                    cg4++;
                                }else if("C".equals(obj.getGroup()) && obj.getRiskLevel() == 1){
                                    cd4++;
                                }else if("C".equals(obj.getGroup()) && obj.getRiskLevel() == null){
                                    cp4++;
                                }
                            }
                        }


                        //计算肠镜数
                        if("A".equals(obj.getGroup())){
                            a5 ++;
                        }else if("B".equals(obj.getGroup())){
                            b5++;
                        }else if("C".equals(obj.getGroup()) && obj.getRiskLevel() == 2){
                            cg5++;
                        }else if("C".equals(obj.getGroup()) && obj.getRiskLevel() == 1){
                            cd5++;
                        }else if("C".equals(obj.getGroup()) && obj.getRiskLevel() == null){
                            cp5++;
                        }

                        //计算C组
                        c1 = cg1 + cd1 + cp1;
                        c2 = cg2 + cd2 + cp2;
                        c3 = cg3 + cd3 + cp3;
                        c4 = cg4 + cd4 + cp4;
                        c5 = cg5 + cd5 + cp5;
                    }
                }

                List resultA = new ArrayList();
                Map map1 = new HashMap();
                map1.put("colorectalCancerNum",a1 + "(" + IntegerUtil.calculationProportion(a1,a5) + ")");
                map1.put("advancedAdenomaNum",a2 + "(" + IntegerUtil.calculationProportion(a2,a5) + ")");
                map1.put("nonAdvancedAdenomaNum",a3 + "(" + IntegerUtil.calculationProportion(a3,a5) + ")");
                map1.put("otherLesionsNum",a4 + "(" + IntegerUtil.calculationProportion(a4,a5) + ")");
                map1.put("colonoscopyNum",a5);
                resultA.add(map1);
                String jsons1 = resultA.toString().substring(1,resultA.toString().length());
                String sumdepts1 = jsons1.substring(0, jsons1.length()-1);
                rs.add(sumdepts1);

                List resultB = new ArrayList();
                Map map2 = new HashMap();
                map2.put("colorectalCancerNum",b1 + "(" + IntegerUtil.calculationProportion(b1,b5) + ")");
                map2.put("advancedAdenomaNum",b2 + "(" + IntegerUtil.calculationProportion(b2,b5) + ")");
                map2.put("nonAdvancedAdenomaNum",b3 + "(" + IntegerUtil.calculationProportion(b3,b5) + ")");
                map2.put("otherLesionsNum",b4 + "(" + IntegerUtil.calculationProportion(b4,b5) + ")");
                map2.put("colonoscopyNum",b5);
                resultB.add(map2);
                String jsons2 = resultB.toString().substring(1,resultB.toString().length());
                String sumdepts2 = jsons2.substring(0, jsons2.length()-1);
                rs.add(sumdepts2);

                List resultCg = new ArrayList();
                Map map3 = new HashMap();
                map3.put("colorectalCancerNum",cg1 + "(" + IntegerUtil.calculationProportion(cg1,cg5) + ")");
                map3.put("advancedAdenomaNum",cg2 + "(" + IntegerUtil.calculationProportion(cg2,cg5) + ")");
                map3.put("nonAdvancedAdenomaNum",cg3 + "(" + IntegerUtil.calculationProportion(cg3,cg5) + ")");
                map3.put("otherLesionsNum",cg4 + "(" + IntegerUtil.calculationProportion(cg4,cg5) + ")");
                map3.put("colonoscopyNum",cg5);
                resultCg.add(map3);
                String jsons3 = resultCg.toString().substring(1,resultCg.toString().length());
                String sumdepts3 = jsons3.substring(0, jsons3.length()-1);
                rs.add(sumdepts3);

                List resultCd = new ArrayList();
                Map map4 = new HashMap();
                map4.put("colorectalCancerNum",cd1 + "(" + IntegerUtil.calculationProportion(cd1,cd5) + ")");
                map4.put("advancedAdenomaNum",cd2 + "(" + IntegerUtil.calculationProportion(cd2,cd5) + ")");
                map4.put("nonAdvancedAdenomaNum",cd3 + "(" + IntegerUtil.calculationProportion(cd3,cd5) + ")");
                map4.put("otherLesionsNum",cd4 + "(" + IntegerUtil.calculationProportion(cd4,cd5) + ")");
                map4.put("colonoscopyNum",cd5);
                resultCd.add(map4);
                String jsons4 = resultCd.toString().substring(1,resultCd.toString().length());
                String sumdepts4 = jsons4.substring(0, jsons4.length()-1);
                rs.add(sumdepts4);


                List resultCp = new ArrayList();
                Map map5 = new HashMap();
                map5.put("colorectalCancerNum",cp1 + "(" + IntegerUtil.calculationProportion(cp1,cp5) + ")");
                map5.put("advancedAdenomaNum",cp2 + "(" + IntegerUtil.calculationProportion(cp2,cp5) + ")");
                map5.put("nonAdvancedAdenomaNum",cp3 + "(" + IntegerUtil.calculationProportion(cp3,cp5) + ")");
                map5.put("otherLesionsNum",cp4 + "(" + IntegerUtil.calculationProportion(cp4,cp5) + ")");
                map5.put("colonoscopyNum",cp5);
                resultCp.add(map5);
                String jsons5 = resultCp.toString().substring(1,resultCp.toString().length());
                String sumdepts5 = jsons5.substring(0, jsons5.length()-1);
                rs.add(sumdepts5);

                List resultC = new ArrayList();
                Map map6 = new HashMap();
                map6.put("colorectalCancerNum",c1 + "(" + IntegerUtil.calculationProportion(c1,c5) + ")");
                map6.put("advancedAdenomaNum",c2 + "(" + IntegerUtil.calculationProportion(c2,c5) + ")");
                map6.put("nonAdvancedAdenomaNum",c3 + "(" + IntegerUtil.calculationProportion(c3,c5) + ")");
                map6.put("otherLesionsNum",c4 + "(" + IntegerUtil.calculationProportion(c4,c5) + ")");
                map6.put("colonoscopyNum",c5);
                resultC.add(map6);
                String jsons6 = resultC.toString().substring(1,resultC.toString().length());
                String sumdepts6 = jsons6.substring(0, jsons6.length()-1);
                rs.add(sumdepts6);

                //计算社区总计
                sum1 = a1 + b1 + cg1 + cd1 + cp1;
                sum2 = a2 + b2 + cg2 + cd2 + cp2;
                sum3 = a3 + b3 + cg3 + cd3 + cp3;
                sum4 = a4 + b4 + cg4 + cd4 + cp4;
                sum5 = a5 + b5 + cg5 + cd5 + cp5;

                List resultSum = new ArrayList();
                Map map7 = new HashMap();
                map7.put("colorectalCancerNum",sum1 + "(" + IntegerUtil.calculationProportion(sum1,sum5) + ")");
                map7.put("advancedAdenomaNum",sum2 + "(" + IntegerUtil.calculationProportion(sum2,sum5) + ")");
                map7.put("nonAdvancedAdenomaNum",sum3 + "(" + IntegerUtil.calculationProportion(sum3,sum5) + ")");
                map7.put("otherLesionsNum",sum4 + "(" + IntegerUtil.calculationProportion(sum4,sum5) + ")");
                map7.put("colonoscopyNum",sum5);
                resultSum.add(map7);
                String jsons7 = resultSum.toString().substring(1,resultSum.toString().length());
                String sumdepts7 = jsons7.substring(0, jsons7.length()-1);
                rs.add(sumdepts7);

                arrs.put("dept",communitName);
                arrs.put("list",rs);
                rtnlist.add(arrs);

                //计算地区总计
                areaA1 += a1;
                areaA2 += a2;
                areaA3 += a3;
                areaA4 += a4;
                areaA5 += a5;

                areaB1 += b1;
                areaB2 += b2;
                areaB3 += b3;
                areaB4 += b4;
                areaB5 += b5;

                areaCg1 += cg1;
                areaCg2 += cg2;
                areaCg3 += cg3;
                areaCg4 += cg4;
                areaCg5 += cg5;

                areaCd1 += cd1;
                areaCd2 += cd2;
                areaCd3 += cd3;
                areaCd4 += cd4;
                areaCd5 += cd5;

                areaCp1 += cp1;
                areaCp2 += cp2;
                areaCp3 += cp3;
                areaCp4 += cp4;
                areaCp5 += cp5;

                areaC1 += c1;
                areaC2 += c2;
                areaC3 += c3;
                areaC4 += c4;
                areaC5 += c5;

                areaSum1 += sum1;
                areaSum2 += sum2;
                areaSum3 += sum3;
                areaSum4 += sum4;
                areaSum5 += sum5;
            }

            List<String> rs = new ArrayList<>();
            Map<String, Object> arrs = new LinkedHashMap<String,Object>();
            List resultA = new ArrayList();
            Map map1 = new HashMap();
            map1.put("colorectalCancerNum",areaA1 + "(" + IntegerUtil.calculationProportion(areaA1,areaA5) + ")");
            map1.put("advancedAdenomaNum",areaA2 + "(" + IntegerUtil.calculationProportion(areaA2,areaA5) + ")");
            map1.put("nonAdvancedAdenomaNum",areaA3 + "(" + IntegerUtil.calculationProportion(areaA3,areaA5) + ")");
            map1.put("otherLesionsNum",areaA4 + "(" + IntegerUtil.calculationProportion(areaA4,areaA5) + ")");
            map1.put("colonoscopyNum",areaA5);
            resultA.add(map1);
            String jsons1 = resultA.toString().substring(1,resultA.toString().length());
            String sumdepts1 = jsons1.substring(0, jsons1.length()-1);
            rs.add(sumdepts1);

            List resultB = new ArrayList();
            Map map2 = new HashMap();
            map2.put("colorectalCancerNum",areaB1 + "(" + IntegerUtil.calculationProportion(areaB1,areaB5) + ")");
            map2.put("advancedAdenomaNum",areaB2 + "(" + IntegerUtil.calculationProportion(areaB2,areaB5) + ")");
            map2.put("nonAdvancedAdenomaNum",areaB3 + "(" + IntegerUtil.calculationProportion(areaB3,areaB5) + ")");
            map2.put("otherLesionsNum",areaB4 + "(" + IntegerUtil.calculationProportion(areaB4,areaB5) + ")");
            map2.put("colonoscopyNum",areaB5);
            resultB.add(map2);
            String jsons2 = resultB.toString().substring(1,resultB.toString().length());
            String sumdepts2 = jsons2.substring(0, jsons2.length()-1);
            rs.add(sumdepts2);

            List resultCg = new ArrayList();
            Map map3 = new HashMap();
            map3.put("colorectalCancerNum",areaCg1 + "(" + IntegerUtil.calculationProportion(areaCg1,areaCg5) + ")");
            map3.put("advancedAdenomaNum",areaCg2 + "(" + IntegerUtil.calculationProportion(areaCg2,areaCg5) + ")");
            map3.put("nonAdvancedAdenomaNum",areaCg3 + "(" + IntegerUtil.calculationProportion(areaCg3,areaCg5) + ")");
            map3.put("otherLesionsNum",areaCg4 + "(" + IntegerUtil.calculationProportion(areaCg4,areaCg5) + ")");
            map3.put("colonoscopyNum",areaCg5);
            resultCg.add(map3);
            String jsons3 = resultCg.toString().substring(1,resultCg.toString().length());
            String sumdepts3 = jsons3.substring(0, jsons3.length()-1);
            rs.add(sumdepts3);

            List resultCd = new ArrayList();
            Map map4 = new HashMap();
            map4.put("colorectalCancerNum",areaCd1 + "(" + IntegerUtil.calculationProportion(areaCd1,areaCd5) + ")");
            map4.put("advancedAdenomaNum",areaCd2 + "(" + IntegerUtil.calculationProportion(areaCd2,areaCd5) + ")");
            map4.put("nonAdvancedAdenomaNum",areaCd3 + "(" + IntegerUtil.calculationProportion(areaCd3,areaCd5) + ")");
            map4.put("otherLesionsNum",areaCd4 + "(" + IntegerUtil.calculationProportion(areaCd4,areaCd5) + ")");
            map4.put("colonoscopyNum",areaCd5);
            resultCd.add(map4);
            String jsons4 = resultCd.toString().substring(1,resultCd.toString().length());
            String sumdepts4 = jsons4.substring(0, jsons4.length()-1);
            rs.add(sumdepts4);


            List resultCp = new ArrayList();
            Map map5 = new HashMap();
            map5.put("colorectalCancerNum",areaCp1 + "(" + IntegerUtil.calculationProportion(areaCp1,areaCp5) + ")");
            map5.put("advancedAdenomaNum",areaCp2 + "(" + IntegerUtil.calculationProportion(areaCp2,areaCp5) + ")");
            map5.put("nonAdvancedAdenomaNum",areaCp3 + "(" + IntegerUtil.calculationProportion(areaCp3,areaCp5) + ")");
            map5.put("otherLesionsNum",areaCp4 + "(" + IntegerUtil.calculationProportion(areaCp4,areaCp5) + ")");
            map5.put("colonoscopyNum",areaCp5);
            resultCp.add(map5);
            String jsons5 = resultCp.toString().substring(1,resultCp.toString().length());
            String sumdepts5 = jsons5.substring(0, jsons5.length()-1);
            rs.add(sumdepts5);

            List resultC = new ArrayList();
            Map map6 = new HashMap();
            map6.put("colorectalCancerNum",areaC1 + "(" + IntegerUtil.calculationProportion(areaC1,areaC5) + ")");
            map6.put("advancedAdenomaNum",areaC2 + "(" + IntegerUtil.calculationProportion(areaC2,areaC5) + ")");
            map6.put("nonAdvancedAdenomaNum",areaC3 + "(" + IntegerUtil.calculationProportion(areaC3,areaC5) + ")");
            map6.put("otherLesionsNum",areaC4 + "(" + IntegerUtil.calculationProportion(areaC4,areaC5) + ")");
            map6.put("colonoscopyNum",areaC5);
            resultC.add(map6);
            String jsons6 = resultC.toString().substring(1,resultC.toString().length());
            String sumdepts6 = jsons6.substring(0, jsons6.length()-1);
            rs.add(sumdepts6);

            List resultSum = new ArrayList();
            Map map7 = new HashMap();
            map7.put("colorectalCancerNum",areaSum1 + "(" + IntegerUtil.calculationProportion(areaSum1,areaSum5) + ")");
            map7.put("advancedAdenomaNum",areaSum2 + "(" + IntegerUtil.calculationProportion(areaSum2,areaSum5) + ")");
            map7.put("nonAdvancedAdenomaNum",areaSum3 + "(" + IntegerUtil.calculationProportion(areaSum3,areaSum5) + ")");
            map7.put("otherLesionsNum",areaSum4 + "(" + IntegerUtil.calculationProportion(areaSum4,areaSum5) + ")");
            map7.put("colonoscopyNum",areaSum5);
            resultSum.add(map6);
            String jsons7 = resultSum.toString().substring(1,resultSum.toString().length());
            String sumdepts7 = jsons7.substring(0, jsons7.length()-1);
            rs.add(sumdepts7);

            arrs.put("dept",areaName);
            arrs.put("list",rs);
            rtnlist.add(arrs);
        }
        return rtnlist;
    }
}
