/**
 * Project Name:service-base-ic
 * File Name:StoolDnaDaoImpl.java
 * Package Name:com.yuntongxun.itsys.base.dao.impl
 * Date:2018年4月17日下午8:11:53
 * Copyright (c) 2018, ty All Rights Reserved.
 */

package com.yuntongxun.itsys.base.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.yuntongxun.itsys.base.po.DnaSynLogPO;
import com.yuntongxun.itsys.base.po.HtEvent;
import com.yuntongxun.itsys.base.po.dto.ItsysUserDto;
import com.yuntongxun.itsys.base.vo.HospitalBiologicalSampleResultVo;
import com.yuntongxun.itsys.base.common.util.DateUtil;
import com.yuntongxun.itsys.base.common.util.StringUtil;
import com.yuntongxun.itsys.base.vo.ColonoscopyIssueVo;
import com.yuntongxun.itsys.base.vo.ColonoscopyNotificationVo;
import com.yuntongxun.itsys.base.vo.TodoVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.dao.StoolDnaDao;
import com.yuntongxun.itsys.base.po.HospitalReview;
import com.yuntongxun.itsys.base.po.StoolDna;

/**
 * ClassName:StoolDnaDaoImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018年4月17日 下午8:11:53 <br/>
 * @author ty
 * @version
 * @since JDK 1.8
 * @see
 */
@Repository
public class StoolDnaDaoImpl implements StoolDnaDao {

    @Autowired
    private JdbcTemplate jdbc;

	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public ListPageUtil query(StoolDna dna) {
		StringBuilder sql = new StringBuilder();
		List args = new ArrayList();
		sql.append(" SELECT r.apply_status as applyStatus,r.edit_status as editStatus,r.approval_status as approvalStatus,r.dna_check_inform_status as dnaCheckInformStatus,r.id as id,r.sid as sid,r.dna_code as dnaCode,r.person_code as personCode,r.release_date as releaseDate,r.stage,r.code_entry_status as codeEntryStatus,r.dna_check_result as dnaCheckResult,r.dna_check_filePath as dnaCheckFilePath,r.dna_check_enter_status as dnaCheckEnterStatus,r.dna_community_inform_status as dnaCommunityInformStatus,r.dna_check_result as dnaCheckResult,a.name,u.nickName,a.phone,"
				+ "case when a.`group`= 'A' then 'A' when a.`group` = 'B'  then 'B' when a.`group` = 'C' and a.risk_level is null then 'C'   when a.`group` = 'C' and a.risk_level = 1 then 'Cd' when a.`group` = 'C' and  a.risk_level = 2 then 'Cg' ELSE NULL END `group`,a.overall_status_cy  "
				+ "	 FROM hospital_stool_dna r"
				+ "  LEFT JOIN hospital_intestine_review a ON r.sid = a.sid  LEFT JOIN itsys_user u ON a.create_user = u.loginName  WHERE 1=1 and a.overall_status_cy <> 2 ");

		if(StringUtils.isNotBlank(dna.getName())){
			sql.append(" and a.name like ? ");
			args.add("%"+dna.getName()+"%");
		}

		if(StringUtils.isNotBlank(dna.getSid())){
			sql.append(" and r.sid like ? ");
			args.add("%"+dna.getSid()+"%");
		}

		if (StringUtils.isNotBlank(dna.getDnaCode())){
			sql.append(" and r.dna_code like ? ");
			args.add("%"+dna.getDnaCode()+"%");
		}

		if (StringUtils.isNotBlank(dna.getPersonCode())){
			sql.append(" and r.person_code = ? ");
			args.add(dna.getPersonCode());
		}

		if (StringUtils.isNotBlank(dna.getPhone())){
			sql.append(" and a.phone like ? ");
			args.add("%"+dna.getPhone()+"%");
		}

		if (StringUtils.isNotBlank(dna.getGroup())){
			if(dna.getGroup().equals("Cg")){
				sql.append(" and a.`group` = 'C' and a.risk_level = ?");
				args.add(2);
			}else if (dna.getGroup().equals("Cd")){
				sql.append(" and a.`group` = 'C' and a.risk_level = ?");
				args.add(1);
			}else {
				sql.append(" and a.`group` = ? ");
				args.add(dna.getGroup());
			}
		}

		if(dna.getCodeEntryStatus() != null){
			sql.append(" and r.code_entry_status = ? ");
			args.add(dna.getCodeEntryStatus());
		}

		if(dna.getStage() != null){
			sql.append(" and r.stage = ? ");
			args.add(dna.getStage());
		}

		if(dna.getRemoveStatus() != null){
			sql.append(" and r.remove_status = ? ");
			args.add(dna.getRemoveStatus());
		}

		if (dna.getOverall_status_cy() != null){
			sql.append(" and a.overall_status_cy = ? ");
			args.add(dna.getOverall_status_cy());
		}

		if (dna.getCommunityDeptId() != null){
			sql.append(" and r.community_dept_id = ? ");
			args.add(dna.getCommunityDeptId());
		}

		if (dna.getAreaDeptId() != null){
			sql.append(" and r.area_dept_id = ? ");
			args.add(dna.getAreaDeptId());
		}

		if(dna.getDna_check_inform_status()!=null){
		    //已返回为2 待返回为1
            //社区查询已返回和未返回，根据表中dna_check_inform_status字段，如果=2表示已返回，!=2表示未返回
            if(dna.getDna_check_inform_status()==1){
				dna.setDna_check_result(null);
				sql.append(" and code_entry_status is not null and r.dna_check_inform_status != 2 and code_entry_status= 2 ");
            }
            if(dna.getDna_check_inform_status()==2){
                sql.append(" and r.dna_check_inform_status = 2 ");
            }
        }
		//如果查询阴性 阳性 必须是国家已审核（dna_check_inform_status=2）
        if(dna.getDna_check_result()!=null){
			sql.append(" and r.dna_check_inform_status = 2 and r.dna_check_result=? ");
			args.add(dna.getDna_check_result());
		}
		//社区发放状态
		if(dna.getDna_community_inform_status()!=null){
			sql.append(" and r.dna_community_inform_status=? ");
			args.add(dna.getDna_community_inform_status());
		}
        if(StringUtils.isNotBlank(dna.getLoginName())){
        	sql.append(" and u.loginName = ?");
        	args.add(dna.getLoginName());
        }
		sql.append(" order by r.date_created desc");

		ListPageUtil listPage = new ListPageUtil(sql.toString(), args.toArray(), dna.getPageNo(), dna.getPageSize(), jdbc, null);
        return listPage;
    }

    @Override
    public void updateDnaById(StoolDna dna) {
        String sql = "UPDATE hospital_stool_dna set dna_code=?,person_code=?,release_date=?,code_entry_status=?,update_time=now(),entry_time=now() where `id`=?";
        jdbc.update(sql, new Object[]{dna.getDnaCode(), dna.getPersonCode(), dna.getReleaseDate(), Constans.FINISHED_STATUS2, dna.getId()});
    }

    @Override
    public void updateDnaEvent(StoolDna dna) {
        String sql = "UPDATE hospital_todo_event set status =?,update_time=now() where data_id=? and type=?";
        jdbc.update(sql, new Object[]{Constans.PERSON_TODO_EVENT_STATUS2, dna.getId(), Constans.PERSON_TODO_EVENT_TYPE4});
    }

    @Override
    public List<StoolDna> getStoolList(StoolDna dna) {
        StringBuilder sql = new StringBuilder();
        List args = new ArrayList();
        sql.append(" SELECT r.id,r.sid,r.dna_code dnaCode,r.person_code personCode,r.release_date releaseDate,r.stage,r.code_entry_status codeEntryStatus,r.remove_status removeStatus,r.community_dept_id communityDeptId,r.area_dept_id areaDeptId,r.date_created,r.update_time,a.name,a.phone,a.`group`,a.overall_status_cy  "
                + "	 FROM hospital_stool_dna r"
                + "  LEFT JOIN hospital_intestine_review a ON r.sid = a.sid left join itsys_user u on a.create_user = u.loginName WHERE 1=1 ");

        if (StringUtils.isNotBlank(dna.getName())) {
            sql.append(" and a.name like ? ");
            args.add("%" + dna.getName() + "%");
        }

        if (StringUtils.isNotBlank(dna.getSid())) {
            sql.append(" and r.sid like ? ");
            args.add("%" +dna.getSid()+ "%");
        }

        if (StringUtils.isNotBlank(dna.getDnaCode())) {
            sql.append(" and r.dna_code like ? ");
            args.add("%" +dna.getDnaCode()+ "%");
        }

        if (StringUtils.isNotBlank(dna.getPersonCode())) {
            sql.append(" and r.person_code = ? ");
            args.add(dna.getPersonCode());
        }

        if (StringUtils.isNotBlank(dna.getPhone())) {
            sql.append(" and a.phone like ? ");
            args.add("%" +dna.getPhone()+ "%");
        }

        if (StringUtils.isNotBlank(dna.getGroup())) {
            sql.append(" and a.`group` = ? ");
            args.add(dna.getGroup());
        }

        if (dna.getCodeEntryStatus() != null) {
            sql.append(" and r.code_entry_status = ? ");
            args.add(dna.getCodeEntryStatus());
        }

        if (dna.getStage() != null) {
            sql.append(" and r.stage = ? ");
            args.add(dna.getStage());
        }

        if (dna.getRemoveStatus() != null) {
            sql.append(" and r.remove_status = ? ");
            args.add(dna.getRemoveStatus());
        }

        if (dna.getOverall_status_cy() != null) {
            sql.append(" and a.overall_status_cy = ? ");
            args.add(dna.getOverall_status_cy());
        }

        if (dna.getCommunityDeptId() != null) {
            sql.append(" and r.community_dept_id = ? ");
            args.add(dna.getCommunityDeptId());
        }

        if (dna.getAreaDeptId() != null) {
            sql.append(" and r.area_dept_id = ? ");
            args.add(dna.getAreaDeptId());
        }
        if(StringUtils.isNotBlank(dna.getLoginName())){
        	sql.append(" and u.loginName = ?");
        	args.add(dna.getLoginName());
        }
        sql.append("order by r.date_created desc");

        List<StoolDna> list = jdbc.query(sql.toString(), args.toArray(), new BeanPropertyRowMapper<StoolDna>(StoolDna.class));
        return list;
    }

	@Override
	public ListPageUtil queryAreaDnaPage(StoolDna dna) {
		StringBuilder sql = new StringBuilder();
		List args = new ArrayList();
		sql.append(" SELECT r.apply_status as applyStatus,r.edit_status as editStatus,r.approval_status as approvalStatus,r.dna_check_inform_status as dnaCheckInformStatus,r.id as id,r.sid as sid,r.dna_code as dnaCode,r.person_code as personCode,r.release_date as releaseDate,r.stage,r.code_entry_status as codeEntryStatus,r.dna_check_result as dnaCheckResult,r.dna_check_filePath as dnaCheckFilePath,r.dna_check_enter_status as dnaCheckEnterStatus,r.dna_community_inform_status as dnaCommunityInformStatus,r.dna_check_result as dnaCheckResult,b.`name` as depName,a.name,u.nickName,a.phone,"
				+ "case when a.`group`= 'A' then 'A' when a.`group` = 'B'  then 'B' when a.`group` = 'C' and a.risk_level is null then 'C'   when a.`group` = 'C' and a.risk_level = 1 then 'Cd' when a.`group` = 'C' and  a.risk_level = 2 then 'Cg' ELSE NULL END `group`,"
				+ "a.overall_status_cy,b.name communityName  "
				+ "	 FROM hospital_stool_dna r"
				+ "  LEFT JOIN itsys_department b on b.id = r.community_dept_id "
				+ "  LEFT JOIN hospital_intestine_review a ON r.sid = a.sid " +
				" LEFT JOIN itsys_user u ON a.create_user = u.loginName  " +
				" WHERE 1=1 and a.overall_status_cy <> 2 ");
		
		if(StringUtils.isNotBlank(dna.getName())){
			sql.append(" and a.name like ? ");
			args.add("%"+dna.getName()+"%");
		}
		
		if(StringUtils.isNotBlank(dna.getSid())){
			sql.append(" and r.sid like ? ");
			args.add("%"+dna.getSid()+"%");
		}
		
		if(StringUtils.isNotBlank(dna.getDnaCode())){
			sql.append(" and r.dna_code like ? ");
			args.add("%"+dna.getDnaCode()+"%");
		}
		
		if(StringUtils.isNotBlank(dna.getPersonCode())){
			sql.append(" and r.person_code = ? ");
			args.add(dna.getPersonCode());
		}
		
		if(StringUtils.isNotBlank(dna.getPhone())){
			sql.append(" and a.phone like ? ");
			args.add("%"+dna.getPhone()+"%");
		}
		
		if(StringUtils.isNotBlank(dna.getGroup())){
			if(dna.getGroup().equals("Cg")){
				sql.append(" and a.`group` = 'C' and a.risk_level = ?");
				args.add(2);
			}else if(dna.getGroup().equals("Cd")){
				sql.append(" and a.`group` = 'C' and a.risk_level = ?");
				args.add(1);
			}else{
				sql.append(" and a.`group` = ? ");
				args.add(dna.getGroup());				
			}
		}
		
		if(dna.getCodeEntryStatus() != null){
			sql.append(" and r.code_entry_status = ? ");
			args.add(dna.getCodeEntryStatus());
		}
		
		if(dna.getStage() != null){
			sql.append(" and r.stage = ? ");
			args.add(dna.getStage());
		}
		
		if(dna.getRemoveStatus() != null){
			sql.append(" and r.remove_status = ? ");
			args.add(dna.getRemoveStatus());
		}
		
		if(dna.getOverall_status_cy() != null){
			sql.append(" and a.overall_status_cy = ? ");
			args.add(dna.getOverall_status_cy());
		}
		
		if(dna.getCommunityDeptId() != null){
			sql.append(" and r.community_dept_id = ? ");
			args.add(dna.getCommunityDeptId());
		}
		
		if(dna.getAreaDeptId() != null){
			sql.append(" and r.area_dept_id = ? ");
			args.add(dna.getAreaDeptId());
		}

		if(dna.getDna_check_inform_status()!=null){
			//已返回为2 待返回为1
			//社区查询已返回和未返回，根据表中dna_check_inform_status字段，如果=2表示已返回，!=2表示未返回
			if(dna.getDna_check_inform_status()==1){
				dna.setDna_check_result(null);
				sql.append(" and code_entry_status is not null and r.dna_check_inform_status != 2 and code_entry_status= 2 ");
			}
			if(dna.getDna_check_inform_status()==2){
				sql.append(" and r.dna_check_inform_status = 2 ");
			}
		}
		//如果查询阴性 阳性 必须是国家已审核（dna_check_inform_status=2）
		if(dna.getDna_check_result()!=null){
			sql.append(" and r.dna_check_inform_status = 2 and r.dna_check_result=? ");
			args.add(dna.getDna_check_result());
		}
		//社区发放状态
		if(dna.getDna_community_inform_status()!=null){
			sql.append(" and r.dna_community_inform_status=? ");
			args.add(dna.getDna_community_inform_status());
		}
        if(StringUtils.isNotBlank(dna.getLoginName())){
        	sql.append(" and u.loginName = ?");
        	args.add(dna.getLoginName());
        }
		sql.append("order by r.date_created desc");
		
		ListPageUtil listPage = new ListPageUtil(sql.toString(), args.toArray(), dna.getPageNo(), dna.getPageSize(), jdbc, null);
        return listPage;
	}

	/**
	 * 查询 未发放dna结果列表 受试者列表
	 */
	@Override
	public ListPageUtil notIssueDna(Integer communityDeptId, int status, TodoVo vo) {
		String sql = "select t2.dna_check_filePath as dnaCheckFilePath,t2.dna_check_result as dnaCheckResult,t1.id,t3.sid,t3.`name`,u.nickName,t3.age,t3.sex, case when t3.`group`= 'A' then 'A' when t3.`group` = 'B'  then 'B' when t3.`group` = 'C' and t3.risk_level is null then 'C'   when t3.`group` = 'C' and t3.risk_level = 1 then 'Cd' when t3.`group` = 'C' and  t3.risk_level = 2 then 'Cg' ELSE NULL END 'group',"
				+ "t3.phone,t3.overall_status_cy as overallStatusCy,t3.in_group_date as inGroupDate,t2.id as dataId,t2.dna_check_result as dnaCheckResult " +
				" from hospital_todo_event t1,hospital_stool_dna t2,hospital_intestine_review t3 , itsys_user u "
				+ " where t1.sid = t2.sid and t3.sid=t2.sid "
				+ " AND t3.create_user = u.loginName "
				+ " and t1.data_id = t2.id "
				+ " and t1.community_dept_id = ? "
				+ " and t1.type = ? "
				+ " and t1.`status`=? ";

		List param = new ArrayList();
		param.add(communityDeptId);
		param.add(17);//7：未发放dna结果列表
		param.add(status);

		if (!StringUtil.isEmpty(vo.getSid())) {
			sql += " and t3.sid like ? ";
			param.add("%" + vo.getSid() + "%");
		}

		if (!StringUtil.isEmpty(vo.getName())) {
			sql += " and t3.`name` like ? ";
			param.add("%" + vo.getName() + "%");
		}

		if (!StringUtil.isEmpty(vo.getPhone())) {
			sql += " and t3.phone like ? ";
			param.add("%" + vo.getPhone() + "%");
		}

		if(vo.getNoStoolDNAResult()!=null){
			sql += " and t2.dna_check_result=? ";
			param.add(vo.getNoStoolDNAResult());
		}
		if (!StringUtil.isEmpty(vo.getGroup())) {
			if (vo.getGroup().equals("Cg")) {
				sql += " and t3.`group` = 'C' and t3.risk_level = ? ";
				param.add(2);
			} else if (vo.getGroup().equals("Cd")) {
				sql += " and t3.`group` = 'C' and t3.risk_level = ? ";
				param.add(1);
			} else {
				sql += " and t3.`group` like ? ";
				param.add("%" + vo.getGroup() + "%");
			}
		}
		if(vo.getDnaCheckResult()!= null){
			sql += " and t2.dna_check_result = ? ";
			param.add(vo.getDnaCheckResult());
		}
		if(!StringUtil.isEmpty(vo.getLoginName())){
			sql += " and u.loginName = ? ";
			param.add(vo.getLoginName());
		}
		ListPageUtil listPage = new ListPageUtil(sql, param.toArray(), vo.getPageNo(), vo.getPageSize(), jdbc, null);
		return listPage;
	}

	/**
	 * 发放dna告知书
	 * @param dna
	 */
	@Override
	public void updateCommDnaInformStatus(StoolDna dna) {
		/** 更新字段
		 dna_community_inform_status=1
		 dna_check_inform_community_date=当前时间
		 dna_community_inform_mode
		 dna_community_inform_worker_code
		 dna_community_inform_note
		 dna_community_inform_work_time
		 */
		Date workTime=null;
		if(dna.getDna_community_inform_work_time()!=null){
			workTime = DateUtil.formatDateStr(dna.getDna_community_inform_work_time(),"yyyy-MM-dd");
		}
		String sql = "update hospital_stool_dna set dna_community_inform_status=1,dna_check_inform_community_date=now(),dna_community_inform_mode=?,dna_community_inform_worker_code=?,dna_community_inform_note=?,dna_community_inform_work_time=?,update_time=now() where `id`=?";
		jdbc.update(sql, new Object[]{dna.getDna_community_inform_mode(),dna.getDna_community_inform_worker_code(),dna.getDna_community_inform_note(),workTime,dna.getId()});
	}

	/**
	 * 查询单个dna详情
	 * @param id
	 * @return
	 */
	@Override
	public Object getDnaInfoById (int id) {
		String sql = "select t1.`name`,t2.* from hospital_intestine_review t1,hospital_stool_dna t2 where t1.sid=t2.sid and t2.id=?";
		List list = jdbc.queryForList(sql.toString(), new Object[]{id});
		if(list!=null&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public List<ItsysUserDto> queryloginNameById(Integer id) {
		String sql ="select * from itsys_user where id in ( select t1.`userId` from itsys_department_member t1 left join  hospital_stool_dna t2 on t1.`deptId`=t2.`community_dept_id`  where t2.id=?)";
		return jdbc.query(sql, new BeanPropertyRowMapper<ItsysUserDto>(ItsysUserDto.class), id);

	}

	@Override
	public void updateEditDnaById(StoolDna dna,boolean isArea) {
		if(isArea){
			String sql = "UPDATE hospital_stool_dna set dna_code=?,person_code=?,release_date=?,code_entry_status=?,update_time=now(),entry_time=now() where `id`=?";
			jdbc.update(sql, new Object[]{dna.getDnaCode(), dna.getPersonCode(), dna.getReleaseDate(), Constans.FINISHED_STATUS2,dna.getId(),});

		}else {
			String sql = "UPDATE hospital_stool_dna set dna_code=?,person_code=?,release_date=?,code_entry_status=?,update_time=now(),entry_time=now(),apply_status=?,edit_status=?,approval_status=? where `id`=?";
			jdbc.update(sql, new Object[]{dna.getDnaCode(), dna.getPersonCode(), dna.getReleaseDate(), Constans.FINISHED_STATUS2, Constans.APPLY_EDIT_STATUS1,Constans.EDIT_STATUS1,null,dna.getId(),});

		}
	}

	@Override
	public List<ItsysUserDto> queryloginNameByDId(Integer id) {
		String sql ="select * from itsys_user where id in ( select t1.`userId` from itsys_department_member t1 left join  hospital_stool_dna t2 on t1.`deptId`=t2.`area_dept_id`  where t2.id=?)";
		return jdbc.query(sql, new BeanPropertyRowMapper<ItsysUserDto>(ItsysUserDto.class), id);
	}

	@Override
	public void updateDNATodoEventList(Integer status, String substring, Integer type) {
		String sql = "UPDATE hospital_todo_event set status =?,update_time=now() where data_id in("+substring+") and type=?";
		jdbc.update(sql, new Object[]{status, type});
	}

	@Override
	public List<StoolDna> queryByIdS(String substring) {
		String sql = "select * from hospital_stool_dna  where id in ("+substring+")";//+id+" and sid='"+sid+"'"
		return jdbc.query(sql, new BeanPropertyRowMapper<StoolDna>(StoolDna.class));
	}

	@Override
	public void updateIssuedStatusList(final List<StoolDna> listOk) {
		String sql = "update hospital_stool_dna set dna_check_inform_status=?,dna_check_inform_nation_date=?,dna_community_inform_status=?,update_time=now() where `id`=?";
		jdbc.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				StoolDna arg0 = (StoolDna) listOk.get(i);
				ps.setObject(1, arg0.getDnaCheckInformStatus());
				ps.setObject(2, arg0.getDnaCheckInformNationDate());
				ps.setObject(3, arg0.getDnaCommunityInformStatus());
				ps.setObject(4, arg0.getId());
			}
			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return listOk.size();
			}
		});
	}

	@Override
	public List<StoolDna> queryByDNACode(String dnaCode) {
		String sql = "select * from hospital_stool_dna  where dna_code=? and apply_status=0 and edit_status=0 and approval_status is null";//+id+" and sid='"+sid+"'"
		return jdbc.query(sql, new BeanPropertyRowMapper<StoolDna>(StoolDna.class), dnaCode);
	}

	@Override
	public int addDnaSynLog(final DnaSynLogPO synLogPO) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		final String sql = "INSERT INTO hospital_dna_result_log(dna_id,param_value,result,result_code,result_cont,date_created,update_time,dna_code,dataNumber) values(?,?,?,?,?,now(),now(),?,?)";
		jdbc.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setObject(1, synLogPO.getDnaId());
				ps.setObject(2, synLogPO.getParamValue());
				ps.setObject(3, synLogPO.getResult());
				ps.setObject(4, synLogPO.getResultCode());
				ps.setObject(5, synLogPO.getResultCont());
				ps.setObject(6, synLogPO.getDnaCode());
				ps.setObject(7, synLogPO.getDataNumber());
				return ps;
			}

		}, keyHolder);
		return keyHolder.getKey().intValue();
	}


	@Override
    public ListPageUtil queryCountryDnaPage(StoolDna dna) {
        StringBuilder sql = new StringBuilder();
        List args = new ArrayList();
        sql.append(" SELECT r.apply_status as applyStatus,r.edit_status as editStatus,r.approval_status as approvalStatus,r.dna_check_filePath as dnaCheckFilePath,r.dna_community_inform_status as dnaCommunityInformStatus,r.dna_check_inform_status as dnaCheckInformStatus,r.dna_check_enter_status as dnaCheckEnterStatus,r.dna_check_goal as dnaCheckGoal,r.dna_check_quantum as dnaCheckQuantum,b2.`name` as AreaName,r.dna_check_result as dnaCheckResult,b.`name` as depName,r.id,r.sid, u.nickName ,r.dna_code dnaCode,r.person_code personCode,r.release_date releaseDate,r.stage,"
                + "r.code_entry_status codeEntryStatus,r.remove_status removeStatus,r.community_dept_id communityDeptId,"
                + "r.area_dept_id areaDeptId,r.date_created,r.update_time,a.name,a.phone,"
                + " case when a.`group`= 'A' then 'A' when a.`group` = 'B'  then 'B' when a.`group` = 'C' and a.risk_level is null then 'C'   when a.`group` = 'C' and a.risk_level = 1 then 'Cd' when a.`group` = 'C' and  a.risk_level = 2 then 'Cg' ELSE NULL END 'group',"
                + "	a.overall_status_cy,b.name communityName,case when record.result_status is not null then 1 else 2 end as resultStatus  "
                + "	 FROM hospital_stool_dna r"
                + "  LEFT JOIN itsys_department b on b.id = r.community_dept_id "
                + "  LEFT JOIN hospital_intestine_review a ON r.sid = a.sid LEFT JOIN itsys_department b2 ON b2.id = r.area_dept_id  LEFT JOIN itsys_user u ON a.create_user = u.loginName "
				+ "  LEFT JOIN( select DISTINCT(sid) sid,r.result_status from hospital_colonoscopy_record r where r.result_status=2 ) as record ON record.sid = r.sid " +
				" WHERE 1=1 and a.overall_status_cy <> 2 ");

        if (StringUtils.isNotBlank(dna.getName())) {
            sql.append(" and a.name like ? ");
            args.add("%" + dna.getName() + "%");
        }

        if (StringUtils.isNotBlank(dna.getSid())) {
            sql.append(" and r.sid  like ? ");
            args.add("%"+dna.getSid()+"%");
        }

        if (StringUtils.isNotBlank(dna.getDnaCode())) {
            sql.append(" and r.dna_code like ? ");
            args.add("%"+dna.getDnaCode()+"%");
        }

        if (StringUtils.isNotBlank(dna.getPersonCode())) {
            sql.append(" and r.person_code = ? ");
            args.add(dna.getPersonCode());
        }

        if (StringUtils.isNotBlank(dna.getPhone())) {
            sql.append(" and a.phone like ? ");
            args.add("%" + dna.getPhone()+"%");
        }

        if (StringUtils.isNotBlank(dna.getGroup())) {
            if (dna.getGroup().equals("Cg")) {
                sql.append(" and a.`group` = 'C' and a.risk_level = ? ");
                args.add(2);
            } else if (dna.getGroup().equals("Cd")) {
                sql.append(" and a.`group` = 'C' and a.risk_level = ? ");
                args.add(1);
            } else {
                sql.append(" and a.`group` = ? ");
                args.add(dna.getGroup());
            }
        }

        if (dna.getCodeEntryStatus() != null) {
            sql.append(" and r.code_entry_status = ? ");
            args.add(dna.getCodeEntryStatus());
        }

        if (dna.getStage() != null) {
            sql.append(" and r.stage = ? ");
            args.add(dna.getStage());
        }

        if (dna.getRemoveStatus() != null) {
            sql.append(" and r.remove_status = ? ");
            args.add(dna.getRemoveStatus());
        }

        if (dna.getOverall_status_cy() != null) {
            sql.append(" and a.overall_status_cy = ? ");
            args.add(dna.getOverall_status_cy());
        }

        if (dna.getCommunityDeptId() != null) {
            sql.append(" and r.community_dept_id = ? ");
            args.add(dna.getCommunityDeptId());
        }

        if (dna.getAreaDeptId() != null) {
            sql.append(" and r.area_dept_id = ? ");
            args.add(dna.getAreaDeptId());
        }

        if (dna.getDnaCheckResult() != null) {
            sql.append(" and r.dna_check_result = ? ");
            args.add(dna.getDnaCheckResult());
        }

        if (dna.getDnaCheckQuantum() != null) {
            sql.append(" and r.dna_check_quantum = ? ");
            args.add(dna.getDnaCheckQuantum());
        }

        if (dna.getDnaCheckGoal() != null) {
            sql.append(" and r.dna_check_goal = ? ");
            args.add(dna.getDnaCheckGoal());
        }

        if (dna.getPersonCode() != null) {
            sql.append(" and r.person_code = ? ");
            args.add(dna.getPersonCode());
        }

        if (dna.getDnaCheckResult() != null) {
            sql.append(" and r.dna_check_result = ? ");
            args.add(dna.getDnaCheckResult());
        }

        if (StringUtils.isNotBlank(dna.getDnaCheckEnterStartDate())) {
            sql.append(" and r.dna_check_enter_date >= ? ");
            args.add(dna.getDnaCheckEnterStartDate() + " 00:00:00");
        }

        if (StringUtils.isNotBlank(dna.getDnaCheckEnterEndDate())) {
            sql.append(" and r.dna_check_enter_date <= ? ");
            args.add(dna.getDnaCheckEnterEndDate() + " 23:59:59");
        }

        if (dna.getDnaCheckEnterStatus() != null) {
            sql.append(" and r.dna_check_enter_status=? and code_entry_status=2 ");
            args.add(dna.getDnaCheckEnterStatus());
        }
        if(dna.getDnaCheckInformStatus()!=null){
			sql.append(" and r.dna_check_inform_status=? ");
			args.add(dna.getDnaCheckInformStatus());
		}
		if(dna.getDnaCommunityInformStatus()!=null){
			sql.append(" and r.dna_community_inform_status=? ");
			args.add(dna.getDnaCommunityInformStatus());
		}
        if(StringUtils.isNotBlank(dna.getLoginName())){
        	sql.append(" and u.loginName = ?");
        	args.add(dna.getLoginName());
        }
        //肠镜报告已录入的状态
        if(dna.getResultStatus()!=null&&dna.getResultStatus()==1){
			sql.append(" and record.result_status = 2 ");
		}else if(dna.getResultStatus()!=null&&dna.getResultStatus()==2){
			sql.append(" and record.result_status is null ");
		}
        sql.append("order by r.date_created desc");

        ListPageUtil listPage = new ListPageUtil(sql.toString(), args.toArray(), dna.getPageNo(), dna.getPageSize(), jdbc, null);
        return listPage;
    }

    @Override
    public List<StoolDna> queryById(StoolDna dna) {
        String sql = "select * from hospital_stool_dna  where id=?";//+id+" and sid='"+sid+"'"
        return jdbc.query(sql, new BeanPropertyRowMapper<StoolDna>(StoolDna.class), dna.getId());

    }

    @Override
    public void updateIssuedStatus(StoolDna dna) {
        String sql = "update hospital_stool_dna set dna_check_inform_status=?,dna_check_inform_nation_date=?,dna_community_inform_status=?,update_time=now() where `id`=?";
        jdbc.update(sql, new Object[]{dna.getDnaCheckInformStatus(),dna.getDnaCheckInformNationDate(),dna.getDnaCommunityInformStatus(), dna.getId()});
    }

    @Override
    public void updateDNATodoEvent(Integer status, Integer id, Integer type) {
        String sql = "UPDATE hospital_todo_event set status =?,update_time=now() where data_id=? and type=?";
        jdbc.update(sql, new Object[]{status, id, type});
    }
}

