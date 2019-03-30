/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: ExcelDaoImpl
 * Author:   lcy
 * Date:     2018/8/23 19:31
 * History:
 * <author>          <time>                <version>
 *     lcy         2018/8/23 19:31           v1.0.0
 */
package com.yuntongxun.itsys.base.dao.impl;

import com.yuntongxun.itsys.base.common.util.StringUtil;
import com.yuntongxun.itsys.base.dao.ExcelDao;
import com.yuntongxun.itsys.base.po.*;
import com.yuntongxun.itsys.base.po.dto.ExeclData;
import com.yuntongxun.itsys.base.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 导出表单Dao
 *
 * @author lcy
 * @create 2018/8/23
 * @since v1.0.0
 */
@Repository
public class ExcelDaoImpl implements ExcelDao {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<StoolDna> getDnaexcel(ExeclData execlData) {
        List<Object> parm = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        //zl添加
        sql.append("SELECT " +
                " (@row_number :=@row_number + 1) AS id," +
                " ss.* " +
                " FROM " +
                " ( " +
                "SELECT " +
                " d.`name` AS depName, d2.`name` AS areaName,"+
                "  t2.area_dept_id AS areaDeptId, " +
                " t2.community_dept_id AS communityDeptId, " +
                " t1.sid, " +
                " t1.`name`, " +
                " u.nickName, " +
                " t1.phone, " +
                " CASE " +
                "WHEN t1.`group` = 'A' THEN " +
                " 'A组' " +
                "WHEN t1.`group` = 'B' THEN " +
                " 'B组' " +
                "WHEN t1.`group` = 'C' " +
                "AND t1.risk_level IS NULL THEN " +
                " 'C组' " +
                "WHEN t1.`group` = 'C' " +
                "AND t1.risk_level = 1 THEN " +
                " 'C组低危' " +
                "WHEN t1.`group` = 'C' " +
                "AND t1.risk_level = 2 THEN " +
                " 'C组高危' " +
                "ELSE " +
                " NULL " +
                "END 'group',  " +
                "DATE_FORMAT(t2.dna_community_inform_work_time,'%Y-%m-%d') AS dna_community_inform_work_time,    " +
                "t2.dna_code AS dnaCode, " +
                "t2.dna_check_result AS dnaCheckResult, " +
                "t2.dna_check_goal AS dnaCheckGoal, " +
                "t2.dna_check_quantum AS dnaCheckQuantum " +
                "FROM " +
                " hospital_intestine_review t1, " +
                " hospital_stool_dna t2, " +
                " itsys_department d," +
                " itsys_department d2," +
                " itsys_user u " +
                "WHERE " +
                " t1.sid = t2.sid " +
                "AND t1.overall_status_cy <> 2 " +
                "and t1.community_dept_id = d.id AND t1.area_dept_id = d2.id "+
                "AND t1.create_user = u.loginName");
        sql.append(" and t2.id in (select max(t.id) from hospital_stool_dna t group by t.sid) ");
        if (execlData.getAreaDeptId() != null) {
            sql.append(" and t2.area_dept_id = ?");
            parm.add(execlData.getAreaDeptId());
        }
        if (execlData.getCommunityDeptId() != null) {
            sql.append(" and t2.community_dept_id = ? ");
            parm.add(execlData.getCommunityDeptId());
        }
        if (!StringUtil.isEmpty(execlData.getLoginName())) {
            sql.append("  and u.loginName = ? ");
            parm.add(execlData.getLoginName());
        }

        if (!StringUtil.isEmpty(execlData.getGroup())) {
            if (execlData.getGroup().equals("Cg")) {
                sql.append(" and t1.`group` = 'C' and t1.risk_level = ? ");
                parm.add(2);
            } else if (execlData.getGroup().equals("Cd")) {
                sql.append(" and t1.`group` = 'C' and t1.risk_level = ? ");
                parm.add(1);
            } else {
                sql.append(" and t1.`group` = ? ");
                parm.add(execlData.getGroup());
            }
        }

        if (execlData.getInGroupDateStart() != null) {
            sql.append(" and t1.in_group_date >= ? ");
            parm.add(execlData.getInGroupDateStart());
        }

        if (execlData.getInGroupDateEnd() != null) {
            sql.append(" and t1.in_group_date <= ? ");
            parm.add(execlData.getInGroupDateEnd());
        }

        if (execlData.getStage_cy() != null) {
            sql.append(" and t1.stage_cy = ? ");
            parm.add(execlData.getStage_cy());
        }

        if (execlData.getSiteId() != null) {
            sql.append("  AND t1.site_id =  ? ");
            parm.add(execlData.getSiteId());
        }

        sql.append(" order by t2.date_created desc ");
        //zl添加
        sql.append(" ) AS ss," +
                " (SELECT @row_number := 0) AS tnum ");
        if (parm.size() > 0) {
            return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<StoolDna>(StoolDna.class), parm.toArray());
        }
        return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<StoolDna>(StoolDna.class));
    }

    @Override
    public List<ColonoscopyVo> getNotificationRecord(ExeclData execlData) {
        List<Object> parm = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        //zl添加
        sql.append("SELECT " +
                " (@row_number :=@row_number + 1) AS id," +
                " ss.* " +
                " FROM " +
                " (" +
                "SELECT " +
                "  t2.area_dept_id AS areaDeptId, " +
                " t2.community_dept_id AS communityDeptId, " +
                " d.`name` AS depName, d2.`name` AS areaName,"+
                " t1.sid, " +
                " t1.`name`, " +
                " u.nickName, " +
                " CASE " +
                "WHEN t1.`group` = 'A' THEN " +
                " 'A' " +
                "WHEN t1.`group` = 'B' THEN " +
                " 'B' " +
                "WHEN t1.`group` = 'C' " +
                "AND t1.risk_level IS NULL THEN " +
                " 'C' " +
                "WHEN t1.`group` = 'C' " +
                "AND t1.risk_level = 1 THEN " +
                " 'Cd' " +
                "WHEN t1.`group` = 'C' " +
                "AND t1.risk_level = 2 THEN " +
                " 'Cg' " +
                "ELSE " +
                " NULL " +
                "END 'group',  " +
                "notification_issue_mode AS notificationIssueMode, " +
                "DATE_FORMAT(t2.notification_issue_date,'%Y-%m-%d') AS notificationIssueDate,  " +
                "notification_issue_worker_code AS notificationIssueWorkerCode, " +
                "notification_issue_note AS notificationIssueNote " +
                "FROM " +
                "  (SELECT @rownum:=0) r, " +
                " hospital_intestine_review t1, " +
                " hospital_colonoscopy_record t2, " +
                " itsys_department d," +
                " itsys_department d2," +
                " itsys_user u " +
                "WHERE " +
                " t1.sid = t2.sid " +
                "AND t1.overall_status_cy <> 2 " +
                "and t1.community_dept_id = d.id AND t1.area_dept_id = d2.id "+
                "AND t1.create_user = u.loginName");

        sql.append(" and t2.id in (select max(t.id) from hospital_colonoscopy_record t group by t.sid) ");
        if (execlData.getAreaDeptId() != null) {
            sql.append(" and t2.area_dept_id = ?");
            parm.add(execlData.getAreaDeptId());
        }
        if (execlData.getCommunityDeptId() != null) {
            sql.append(" and t2.community_dept_id = ? ");
            parm.add(execlData.getCommunityDeptId());
        }
        if (!StringUtil.isEmpty(execlData.getLoginName())) {
            sql.append("  and u.loginName = ? ");
            parm.add(execlData.getLoginName());
        }

        if (!StringUtil.isEmpty(execlData.getGroup())) {
            if (execlData.getGroup().equals("Cg")) {
                sql.append(" and t1.`group` = 'C' and t1.risk_level = ? ");
                parm.add(2);
            } else if (execlData.getGroup().equals("Cd")) {
                sql.append(" and t1.`group` = 'C' and t1.risk_level = ? ");
                parm.add(1);
            } else {
                sql.append(" and t1.`group` = ? ");
                parm.add(execlData.getGroup());
            }
        }

        if (execlData.getInGroupDateStart() != null) {
            sql.append(" and t1.in_group_date >= ? ");
            parm.add(execlData.getInGroupDateStart());
        }

        if (execlData.getInGroupDateEnd() != null) {
            sql.append(" and t1.in_group_date <= ? ");
            parm.add(execlData.getInGroupDateEnd());
        }

        if (execlData.getStage_cy() != null) {
            sql.append(" and t1.stage_cy = ? ");
            parm.add(execlData.getStage_cy());
        }

        if (execlData.getSiteId() != null) {
            sql.append("  AND t1.site_id =  ? ");
            parm.add(execlData.getSiteId());
        }

        sql.append(" order by t2.date_created desc ");
        //zl添加
        sql.append(" ) AS ss," +
                " (SELECT @row_number := 0) AS tnum ");
        if (parm.size() > 0) {
            return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<ColonoscopyVo>(ColonoscopyVo.class), parm.toArray());
        }
        return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<ColonoscopyVo>(ColonoscopyVo.class));
    }

    @Override
    public List<HospitalRiskFactorPO> getReviewRiskFactorDetails(ExeclData execlData) {
        List<Object> parm = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT " +
                " @rownum :=@rownum + 1 AS id, " +
                " t1.sid,t1.sex, " +
                " CASE " +
                "WHEN t1.`group` = 'A' THEN " +
                " 'A' " +
                "WHEN t1.`group` = 'B' THEN " +
                " 'B' " +
                "WHEN t1.`group` = 'C' " +
                "AND t1.risk_level IS NULL THEN " +
                " 'C' " +
                "WHEN t1.`group` = 'C' " +
                "AND t1.risk_level = 1 THEN " +
                " 'Cd' " +
                "WHEN t1.`group` = 'C' " +
                "AND t1.risk_level = 2 THEN " +
                " 'Cg' " +
                "ELSE " +
                " NULL " +
                "END 'group', " +
                " t1.`name`, " +
                " t1.site_id, " +
                " t1.id_card AS idCard, " +
                " t1.phone, " +
                "\tt1.`township` as township,\n" +
                "\tt1.`province` as province,\n" +
                "\tt1.`city` as city,\n" +
                "\tt1.`village` as village,\n" +
                "\tt1.`city_other` as city_other,\n" +
                "\tt1.`area` as area,\n" +
                " t1.site_id, " +
                " t1.overall_status_cy AS overallStatusCy, " +
                " DATE_FORMAT(t2.survey_date, '%Y-%m-%d')AS surveyDate, " +
                " t2.*,DATE_FORMAT(t2.item_2_1_1, '%Y-%m-%d')AS item_2_1_1  " +
                "FROM " +
                " (SELECT @rownum := 0)r, " +
                " hospital_intestine_review t1, " +
                " hospital_intestine_risk_factor t2, " +
                " itsys_user u " +
                "WHERE " +
                " t1.sid = t2.sid " +
                "AND t1.create_user = u.loginName ");
        if (execlData.getAreaDeptId() != null) {
            sql.append(" and t1.area_dept_id = ?");
            parm.add(execlData.getAreaDeptId());
        }
        if (execlData.getCommunityDeptId() != null) {
            sql.append(" and t1.community_dept_id = ? ");
            parm.add(execlData.getCommunityDeptId());
        }
        if (!StringUtil.isEmpty(execlData.getLoginName())) {
            sql.append("  and u.loginName = ? ");
            parm.add(execlData.getLoginName());
        }

        if (!StringUtil.isEmpty(execlData.getGroup())) {
            if (execlData.getGroup().equals("Cg")) {
                sql.append(" and t1.`group` = 'C' and t1.risk_level = ? ");
                parm.add(2);
            } else if (execlData.getGroup().equals("Cd")) {
                sql.append(" and t1.`group` = 'C' and t1.risk_level = ? ");
                parm.add(1);
            } else {
                sql.append(" and t1.`group` = ? ");
                parm.add(execlData.getGroup());
            }
        }

        if (execlData.getInGroupDateStart() != null) {
            sql.append(" and t1.in_group_date >= ? ");
            parm.add(execlData.getInGroupDateStart());
        }

        if (execlData.getInGroupDateEnd() != null) {
            sql.append(" and t1.in_group_date <= ? ");
            parm.add(execlData.getInGroupDateEnd());
        }

        if (execlData.getStage_cy() != null) {
            sql.append(" and t1.stage_cy = ? ");
            parm.add(execlData.getStage_cy());
        }
        if (execlData.getSiteId() != null) {
            sql.append("  AND t1.site_id =  ? ");
            parm.add(execlData.getSiteId());
        }

        sql.append(" order by t2.date_created desc ");
        if (parm.size() > 0) {
            return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<HospitalRiskFactorPO>(HospitalRiskFactorPO.class), parm.toArray());
        }
        return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<HospitalRiskFactorPO>(HospitalRiskFactorPO.class));
    }

    @Override
    public List<ViolationSchemePO> getReviewViolationSchemeDetails(ExeclData execlData) {
        List<Object> parm = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT " +
                " @rownum :=@rownum + 1 AS id, " +
                " CASE " +
                "WHEN t1.`group` = 'A' THEN " +
                " 'A' " +
                "WHEN t1.`group` = 'B' THEN " +
                " 'B' " +
                "WHEN t1.`group` = 'C' " +
                "AND t1.risk_level IS NULL THEN " +
                " 'C' " +
                "WHEN t1.`group` = 'C' " +
                "AND t1.risk_level = 1 THEN " +
                " 'Cd' " +
                "WHEN t1.`group` = 'C' " +
                "AND t1.risk_level = 2 THEN " +
                " 'Cg' " +
                "ELSE " +
                " NULL " +
                "END 'group', " +
                " t1.`name`, " +
                " t1.overall_status_cy AS overallStatusCy, " +
                " t1.site_id AS siteId, " +
                " DATE_FORMAT(t2.tb_date, '%Y-%m-%d')AS tbDateS, " +
                " DATE_FORMAT(t2.item_2a_1, '%Y-%m-%d')AS item2a1, " +
                " DATE_FORMAT(t2.item_2b_1, '%Y-%m-%d')AS item2b1, " +
                " DATE_FORMAT(t2.item_3a_2_time, '%Y-%m-%d')AS item3a2Time, " +
                " t2.* " +
                "FROM " +
                " (SELECT @rownum := 0)r, " +
                " hospital_intestine_review t1, " +
                " hospital_violation_scheme t2, " +
                " itsys_user u " +
                "WHERE " +
                " t1.sid = t2.sid " +
                "AND t1.create_user = u.loginName");

        sql.append(" and t2.id in (select max(t.id) from hospital_violation_scheme t group by t.sid) ");
        if (execlData.getAreaDeptId() != null) {
            sql.append(" and t1.area_dept_id = ?");
            parm.add(execlData.getAreaDeptId());
        }
        if (execlData.getCommunityDeptId() != null) {
            sql.append(" and t1.community_dept_id = ? ");
            parm.add(execlData.getCommunityDeptId());
        }
        if (!StringUtil.isEmpty(execlData.getLoginName())) {
            sql.append("  and u.loginName = ? ");
            parm.add(execlData.getLoginName());
        }

        if (!StringUtil.isEmpty(execlData.getGroup())) {
            if (execlData.getGroup().equals("Cg")) {
                sql.append(" and t1.`group` = 'C' and t1.risk_level = ? ");
                parm.add(2);
            } else if (execlData.getGroup().equals("Cd")) {
                sql.append(" and t1.`group` = 'C' and t1.risk_level = ? ");
                parm.add(1);
            } else {
                sql.append(" and t1.`group` = ? ");
                parm.add(execlData.getGroup());
            }
        }

        if (execlData.getInGroupDateStart() != null) {
            sql.append(" and t1.in_group_date >= ? ");
            parm.add(execlData.getInGroupDateStart());
        }

        if (execlData.getInGroupDateEnd() != null) {
            sql.append(" and t1.in_group_date <= ? ");
            parm.add(execlData.getInGroupDateEnd());
        }

        if (execlData.getStage_cy() != null) {
            sql.append(" and t1.stage_cy = ? ");
            parm.add(execlData.getStage_cy());
        }

        if (execlData.getSiteId() != null) {
            sql.append("  AND t1.site_id =  ? ");
            parm.add(execlData.getSiteId());
        }

        sql.append(" order by t2.date_created desc ");
        if (parm.size() > 0) {
            return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<ViolationSchemePO>(ViolationSchemePO.class), parm.toArray());
        }
        return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<ViolationSchemePO>(ViolationSchemePO.class));
    }

    @Override
    public List<PathologyExcelVO> getPathologyQueryExcel(ExeclData execlData) {
        List<Object> parm = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT " +
                "  t1.sid, " +
                "  t1.id_card AS idCard, " +
                "  t1.`name`, " +
                "  t1.age, " +
                "  t1.sex, " +
                "  t1.overall_status_cy AS overallStatusCy, " +
                "  DATE_FORMAT(t2.survey_date, '%Y-%m-%d')AS surveyDate, " +
                "  t2.item1, " +
                "  t2.item2, " +
                "  t2.item3, " +
                "  DATE_FORMAT(t2.diagnosis_date, '%Y-%m-%d')AS diagnosisDate, " +
                "  t2.doctor_sign, " +
                "  t2.stage, " +
                "  max(case t3.`index` when '0' then t3.item1 else null end) 'item1_0', " +
                "  max(case t3.`index` when '0' then t3.item2 else null end) 'item2_0', " +
                "  max(case t3.`index` when '0' then t3.item3 else null end) 'item3_0', " +
                "  max(case t3.`index` when '0' then t3.item4 else null end) 'item4_0', " +
                "  max(case t3.`index` when '0' then t3.item5 else null end) 'item5_0', " +
                "  max(case t3.`index` when '0' then t3.item6_1 else null end) 'item6_1_0', " +
                "  max(case t3.`index` when '0' then t3.item6_2 else null end) 'item6_2_0', " +
                "  max(case t3.`index` when '0' then t3.item7 else null end) 'item7_0', " +
                " " +
                "  max(case t3.`index` when '1' then t3.item1 else null end) 'item1_1', " +
                "  max(case t3.`index` when '1' then t3.item2 else null end) 'item2_1', " +
                "  max(case t3.`index` when '1' then t3.item3 else null end) 'item3_1', " +
                "  max(case t3.`index` when '1' then t3.item4 else null end) 'item4_1', " +
                "  max(case t3.`index` when '1' then t3.item5 else null end) 'item5_1', " +
                "  max(case t3.`index` when '1' then t3.item6_1 else null end) 'item6_1_1', " +
                "  max(case t3.`index` when '1' then t3.item6_2 else null end) 'item6_2_1', " +
                "  max(case t3.`index` when '1' then t3.item7 else null end) 'item7_1', " +
                " " +
                "  max(case t3.`index` when '2' then t3.item1 else null end) 'item1_2', " +
                "  max(case t3.`index` when '2' then t3.item2 else null end) 'item2_2', " +
                "  max(case t3.`index` when '2' then t3.item3 else null end) 'item3_2', " +
                "  max(case t3.`index` when '2' then t3.item4 else null end) 'item4_2', " +
                "  max(case t3.`index` when '2' then t3.item5 else null end) 'item5_2', " +
                "  max(case t3.`index` when '2' then t3.item6_1 else null end) 'item6_1_2', " +
                "  max(case t3.`index` when '2' then t3.item6_2 else null end) 'item6_2_2', " +
                "  max(case t3.`index` when '2' then t3.item7 else null end) 'item7_2', " +
                " " +
                "  max(case t3.`index` when '3' then t3.item1 else null end) 'item1_3', " +
                "  max(case t3.`index` when '3' then t3.item2 else null end) 'item2_3', " +
                "  max(case t3.`index` when '3' then t3.item3 else null end) 'item3_3', " +
                "  max(case t3.`index` when '3' then t3.item4 else null end) 'item4_3', " +
                "  max(case t3.`index` when '3' then t3.item5 else null end) 'item5_3', " +
                "  max(case t3.`index` when '3' then t3.item6_1 else null end) 'item6_1_3', " +
                "  max(case t3.`index` when '3' then t3.item6_2 else null end) 'item6_2_3', " +
                "  max(case t3.`index` when '3' then t3.item7 else null end) 'item7_3', " +
                " " +
                "  max(case t3.`index` when '4' then t3.item1 else null end) 'item1_4', " +
                "  max(case t3.`index` when '4' then t3.item2 else null end) 'item2_4', " +
                "  max(case t3.`index` when '4' then t3.item3 else null end) 'item3_4', " +
                "  max(case t3.`index` when '4' then t3.item4 else null end) 'item4_4', " +
                "  max(case t3.`index` when '4' then t3.item5 else null end) 'item5_4', " +
                "  max(case t3.`index` when '4' then t3.item6_1 else null end) 'item6_1_4', " +
                "  max(case t3.`index` when '4' then t3.item6_2 else null end) 'item6_2_4', " +
                "  max(case t3.`index` when '4' then t3.item7 else null end) 'item7_4', " +
                " " +
                "  max(case t3.`index` when '5' then t3.item1 else null end) 'item1_5', " +
                "  max(case t3.`index` when '5' then t3.item2 else null end) 'item2_5', " +
                "  max(case t3.`index` when '5' then t3.item3 else null end) 'item3_5', " +
                "  max(case t3.`index` when '5' then t3.item4 else null end) 'item4_5', " +
                "  max(case t3.`index` when '5' then t3.item5 else null end) 'item5_5', " +
                "  max(case t3.`index` when '5' then t3.item6_1 else null end) 'item6_1_5', " +
                "  max(case t3.`index` when '5' then t3.item6_2 else null end) 'item6_2_5', " +
                "  max(case t3.`index` when '5' then t3.item7 else null end) 'item7_5', " +
                " " +
                "  max(case t3.`index` when '6' then t3.item1 else null end) 'item1_6', " +
                "  max(case t3.`index` when '6' then t3.item2 else null end) 'item2_6', " +
                "  max(case t3.`index` when '6' then t3.item3 else null end) 'item3_6', " +
                "  max(case t3.`index` when '6' then t3.item4 else null end) 'item4_6', " +
                "  max(case t3.`index` when '6' then t3.item5 else null end) 'item5_6', " +
                "  max(case t3.`index` when '6' then t3.item6_1 else null end) 'item6_1_6', " +
                "  max(case t3.`index` when '6' then t3.item6_2 else null end) 'item6_2_6', " +
                "  max(case t3.`index` when '6' then t3.item7 else null end) 'item7_6', " +
                " " +
                "  max(case t3.`index` when '7' then t3.item1 else null end) 'item1_7', " +
                "  max(case t3.`index` when '7' then t3.item2 else null end) 'item2_7', " +
                "  max(case t3.`index` when '7' then t3.item3 else null end) 'item3_7', " +
                "  max(case t3.`index` when '7' then t3.item4 else null end) 'item4_7', " +
                "  max(case t3.`index` when '7' then t3.item5 else null end) 'item5_7', " +
                "  max(case t3.`index` when '7' then t3.item6_1 else null end) 'item6_1_7', " +
                "  max(case t3.`index` when '7' then t3.item6_2 else null end) 'item6_2_7', " +
                "  max(case t3.`index` when '7' then t3.item7 else null end) 'item7_7', " +
                " " +
                "  max(case t3.`index` when '8' then t3.item1 else null end) 'item1_8', " +
                "  max(case t3.`index` when '8' then t3.item2 else null end) 'item2_8', " +
                "  max(case t3.`index` when '8' then t3.item3 else null end) 'item3_8', " +
                "  max(case t3.`index` when '8' then t3.item4 else null end) 'item4_8', " +
                "  max(case t3.`index` when '8' then t3.item5 else null end) 'item5_8', " +
                "  max(case t3.`index` when '8' then t3.item6_1 else null end) 'item6_1_8', " +
                "  max(case t3.`index` when '8' then t3.item6_2 else null end) 'item6_2_8', " +
                "  max(case t3.`index` when '8' then t3.item7 else null end) 'item7_8', " +
                " " +
                "  max(case t3.`index` when '9' then t3.item1 else null end) 'item1_9', " +
                "  max(case t3.`index` when '9' then t3.item2 else null end) 'item2_9', " +
                "  max(case t3.`index` when '9' then t3.item3 else null end) 'item3_9', " +
                "  max(case t3.`index` when '9' then t3.item4 else null end) 'item4_9', " +
                "  max(case t3.`index` when '9' then t3.item5 else null end) 'item5_9', " +
                "  max(case t3.`index` when '9' then t3.item6_1 else null end) 'item6_1_9', " +
                "  max(case t3.`index` when '9' then t3.item6_2 else null end) 'item6_2_9', " +
                "  max(case t3.`index` when '9' then t3.item7 else null end) 'item7_9' " +
                "FROM " +
                " hospital_colonoscopy_pathology_result t2 " +
                "LEFT JOIN hospital_intestine_review t1  ON t1.sid = t2.sid " +
                "LEFT JOIN hospital_colonoscopy_pathology_diagnosis_record t3 ON t2.id = t3.pathology_result_id " +
                "LEFT JOIN itsys_user u ON t1.create_user = u.loginName " +
                " WHERE 1 = 1 ");

        sql.append(" and t2.id in (select max(t.id) from hospital_colonoscopy_pathology_result t group by t.sid) ");
        if (execlData.getAreaDeptId() != null) {
            sql.append(" and t1.area_dept_id = ?");
            parm.add(execlData.getAreaDeptId());
        }
        if (execlData.getCommunityDeptId() != null) {
            sql.append(" and t1.community_dept_id = ? ");
            parm.add(execlData.getCommunityDeptId());
        }
        if (!StringUtil.isEmpty(execlData.getLoginName())) {
            sql.append("  and u.loginName = ? ");
            parm.add(execlData.getLoginName());
        }

        if (!StringUtil.isEmpty(execlData.getGroup())) {
            if (execlData.getGroup().equals("Cg")) {
                sql.append(" and t1.`group` = 'C' and t1.risk_level = ? ");
                parm.add(2);
            } else if (execlData.getGroup().equals("Cd")) {
                sql.append(" and t1.`group` = 'C' and t1.risk_level = ? ");
                parm.add(1);
            } else {
                sql.append(" and t1.`group` = ? ");
                parm.add(execlData.getGroup());
            }
        }

        if (execlData.getInGroupDateStart() != null) {
            sql.append(" and t1.in_group_date >= ? ");
            parm.add(execlData.getInGroupDateStart());
        }

        if (execlData.getInGroupDateEnd() != null) {
            sql.append(" and t1.in_group_date <= ? ");
            parm.add(execlData.getInGroupDateEnd());
        }

        if (execlData.getStage_cy() != null) {
            sql.append(" and t1.stage_cy = ? ");
            parm.add(execlData.getStage_cy());
        }
        if (execlData.getSiteId() != null) {
            sql.append("  AND t1.site_id =  ? ");
            parm.add(execlData.getSiteId());
        }
        sql.append(" GROUP BY t2.id");
        sql.append(" order by t2.date_created desc ");
        if (parm.size() > 0) {
            return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<PathologyExcelVO>(PathologyExcelVO.class), parm.toArray());
        }
        return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<PathologyExcelVO>(PathologyExcelVO.class));
    }


    @Override
    public List<HospitalBiologicalSampleResultVo> queryBySampleExcel(ExeclData execlData) {
        return null;
    }

    @Override
    public List<HospitalBiologicalSampleResultVo> querybloodSampleExcel(HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultVo, ExeclData execlData) {
        List<Object> parm = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT  " +
                " d2.name as areaName, " +
                " u.nickName as nickName, " +
                " sboold.sample_column  AS  sSampleColumn, " +
                " pboold.sample_column  AS  pSampleColumn, " +
                " wboold.sample_column  AS  wSampleColumn, " +
                " sboold.sample_line  AS  sSampleLine , " +
                " pboold.sample_line  AS  pSampleLine, " +
                " wboold.sample_line  AS  wSampleLine, " +
                " wboold.frozen_box_code AS wFrozenBoxCode, " +
                " sboold.frozen_box_code AS sFrozenBoxCode, " +
                " pboold.frozen_box_code AS pFrozenBoxCode,  " +
                " pboold.`courier_status` AS pCourierStatus,  " +
                " wboold.`courier_status` AS wCourierStatus,  " +
                " sboold.`courier_status` AS sCourierStatus, d.`name` AS commName, t2.`id` AS `id`, t1.sid, t1.`name` " +
                " , u.nickName, t1.phone " +
                " , CASE  " +
                "  WHEN t1.`group` = 'A' THEN 'A' " +
                "  WHEN t1.`group` = 'B' THEN 'B' " +
                "  WHEN t1.`group` = 'C' " +
                "  AND t1.risk_level IS NULL THEN 'C' " +
                "  WHEN t1.`group` = 'C' " +
                "  AND t1.risk_level = 1 THEN 'Cd' " +
                "  WHEN t1.`group` = 'C' " +
                "  AND t1.risk_level = 2 THEN 'Cg' " +
                "  ELSE NULL " +
                " END AS 'group', t1.overall_status_cy AS overallStatusCy, t2.sample_id AS sampleId, t2.sample_type AS sampleType, t2.collect_status AS collectStatus " +
                " , t2.collect_status_date AS collectStatusDate, t2.frozen_box_code AS frozenBoxCode, t2.sample_column AS sampleColumn, t2.sample_line AS sampleLine, t2.sample_note AS sampleNote " +
                " , t2.courier_status AS courierStatus, t2.associated_sample_id AS associatedSampleId " +
                "FROM hospital_intestine_review t1 " +
                " INNER JOIN hospital_biological_sample_result t2 ON t1.sid = t2.sid " +
                " LEFT JOIN itsys_department d ON t1.community_dept_id = d.id " +
                " LEFT JOIN itsys_department d2 ON  t1.area_dept_id =d2.id " +
                " LEFT JOIN itsys_user u ON t1.create_user = u.loginName  " +
                " LEFT JOIN ( " +
                "  SELECT * " +
                "  FROM hospital_biological_blood_sample_result " +
                "  WHERE sample_type = 'S' " +
                " ) sboold " +
                " ON sboold.blood_sample_id = t2.id " +
                " LEFT JOIN ( " +
                "  SELECT * " +
                "  FROM hospital_biological_blood_sample_result " +
                "  WHERE sample_type = 'W' " +
                " ) wboold " +
                " ON wboold.blood_sample_id = t2.id " +
                " LEFT JOIN ( " +
                "  SELECT * " +
                "  FROM hospital_biological_blood_sample_result " +
                "  WHERE sample_type = 'P' " +
                " ) pboold " +
                " ON pboold.blood_sample_id = t2.id " +
                "WHERE t1.overall_status_cy <> 2  ");
        sql.append(" AND t2.id IN (select max(t.id) from hospital_biological_sample_result t where t.sample_type='A' group by t.sid) ");

        if (execlData.getAreaDeptId() != null) {
            sql.append(" and t1.area_dept_id = ?");
            parm.add(execlData.getAreaDeptId());
        }
        if (execlData.getCommunityDeptId() != null) {
            sql.append(" and t1.community_dept_id = ? ");
            parm.add(execlData.getCommunityDeptId());
        }
        if (!StringUtil.isEmpty(execlData.getLoginName())) {
            sql.append("  and u.loginName = ? ");
            parm.add(execlData.getLoginName());
        }

        if (!StringUtil.isEmpty(execlData.getGroup())) {
            if (execlData.getGroup().equals("Cg")) {
                sql.append(" and t1.`group` = 'C' and t1.risk_level = ? ");
                parm.add(2);
            } else if (execlData.getGroup().equals("Cd")) {
                sql.append(" and t1.`group` = 'C' and t1.risk_level = ? ");
                parm.add(1);
            } else {
                sql.append(" and t1.`group` = ? ");
                parm.add(execlData.getGroup());
            }
        }

        if (execlData.getInGroupDateStart() != null) {
            sql.append(" and t1.in_group_date >= ? ");
            parm.add(execlData.getInGroupDateStart());
        }

        if (execlData.getInGroupDateEnd() != null) {
            sql.append(" and t1.in_group_date <= ? ");
            parm.add(execlData.getInGroupDateEnd());
        }

        if (execlData.getStage_cy() != null) {
            sql.append(" and t1.stage_cy = ? ");
            parm.add(execlData.getStage_cy());
        }
        if (execlData.getSiteId() != null) {
            sql.append("  AND t1.site_id =  ? ");
            parm.add(execlData.getSiteId());
        }

        sql.append(" order by t2.date_created desc");
        if (parm.size() > 0) {
            return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<HospitalBiologicalSampleResultVo>(HospitalBiologicalSampleResultVo.class), parm.toArray());
        }
        return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<HospitalBiologicalSampleResultVo>(HospitalBiologicalSampleResultVo.class));
    }

    @Override
    public List<HospitalBiologicalSampleResultVo> queryBiologicalSampleResultExcel(String sampleType, ExeclData execlData) {
        List<Object> parm = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT t1.sid AS sid," +
                " t2.`name` AS `name`," +
                " d.`name` as commName," +
                " d1.name as areaName," +
                " u.nickName as nickName," +
                " case when t2.`group`= 'A' then 'A' when t2.`group` = 'B'  then 'B' when t2.`group` = 'C' and t2.risk_level is null then 'C' when t2.`group` = 'C' and t2.risk_level = 1 then 'Cd' when t2.`group` = 'C' and  t2.risk_level = 2 then 'Cg' ELSE NULL END 'group'," +
                " t1.sample_id as sampleId," +
                " t1.collect_status_date as 'collectStatusDate'," +
                " t1.frozen_box_code as 'frozenBoxCode'," +
                " t1.sample_column as 'sampleColumn'," +
                " t1.sample_line as 'sampleLine'," +
                " t1.courier_status as 'courierStatus'," +
                " t1.sample_note as 'sampleNote'" +
                " FROM  hospital_biological_sample_result t1, hospital_intestine_review t2," +
                " itsys_department d,itsys_department d1,itsys_user u  WHERE t2.sid = t1.sid and t2.create_user = u.loginName" +
                " and t2.community_dept_id=d.id and t2.area_dept_id = d1.id and t2.overall_status_cy <> 2");

        if (!StringUtil.isEmpty(sampleType)) {
            sql.append(" AND t1.id IN ( SELECT max(t.id) FROM hospital_biological_sample_result t where t.sample_type = ? GROUP BY t.sid ) ");
//            sql.append(" and t1.sample_type=? ");
            parm.add(sampleType);
        }

        if (!StringUtil.isEmpty(execlData.getLoginName())) {
            sql.append("  and u.loginName = ? ");
            parm.add(execlData.getLoginName());
        }

        if (!StringUtil.isEmpty(execlData.getGroup())) {
            if (execlData.getGroup().equals("Cg")) {
                sql.append(" and t2.`group` = 'C' and t2.risk_level = ? ");
                parm.add(2);
            } else if (execlData.getGroup().equals("Cd")) {
                sql.append(" and t2.`group` = 'C' and t2.risk_level = ? ");
                parm.add(1);
            } else {
                sql.append(" and t2.`group` = ? ");
                parm.add(execlData.getGroup());
            }
        }

        if (!StringUtil.isEmpty(execlData.getInGroupDateStart())) {
            sql.append(" and t2.in_group_date >= ? ");
            parm.add(execlData.getInGroupDateStart());
        }

        if (!StringUtil.isEmpty(execlData.getInGroupDateEnd())) {
            sql.append(" and t2.in_group_date <= ? ");
            parm.add(execlData.getInGroupDateEnd());
        }

        if (execlData.getAreaDeptId() != null) {
            sql.append(" and t2.area_dept_id = ?");
            parm.add(execlData.getAreaDeptId());
        }
        if (execlData.getCommunityDeptId() != null) {
            sql.append(" and t2.community_dept_id = ? ");
            parm.add(execlData.getCommunityDeptId());
        }

        if (execlData.getStage_cy() != null) {
            sql.append(" and t2.stage_cy = ? ");
            parm.add(execlData.getStage_cy());
        }
        if (execlData.getSiteId() != null) {
            sql.append("  AND t2.site_id =  ? ");
            parm.add(execlData.getSiteId());
        }

        sql.append(" order by t1.date_created desc");
        if (parm.size() > 0) {
            return jdbcTemplate.query(sql.toString(), parm.toArray(), new BeanPropertyRowMapper<HospitalBiologicalSampleResultVo>(HospitalBiologicalSampleResultVo.class));
        }
        return jdbcTemplate.query(sql.toString(), parm.toArray(), new BeanPropertyRowMapper<HospitalBiologicalSampleResultVo>(HospitalBiologicalSampleResultVo.class));
    }

    @Override
    public List<HospitalColonoscopyResultVo> stoolColonoscopyResultQueryExcel(ExeclData execlData) {
        //select  approval_status as approvalStatus,edit_status as editStatus,apply_status as applyStatus,`id`,`name`,`phone`,`sex`,community_dept_id as communityDeptId,area_dept_id as areaDeptId,id_card as idCard,sid,`age`,birth_date as birthDate,item1,item2,item3,item4,item5,item6,item7,item8,item9,item10,investigator,survey_date as surveyDate,reviewer,address,in_group_date as inGroupDate,`group`,group_status as groupStatus,off_group_reason as offGroupReason,stage_cy as stageCy,review_status as reviewStatus,risk_factor_status as riskFactorStatus,overall_status_cy as overallStatusCy,overall_status_t0 as overallStatusT0, overall_status_t1 as overallStatusT1, overall_status_t2 as overallStatusT2, overall_status_t3 as overallStatusT3,violation_plan_status_cy as violationPlanStatusCy,violation_plan_status_t0 as violationPlanStatusT0,violation_plan_status_t1 as violationPlanStatusT1,violation_plan_status_t2 as violationPlanStatusT2,violation_plan_status_t3 as violationPlanStatusT3,risk_level as riskLevel,site_id as siteId  from hospital_intestine_review where sid=?";
        List<Object> parm = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT t1.name, t1.id_card, t1.sex, t1.age , t1.overall_status_cy AS  overallStatusCy " +
                "  , DATE_FORMAT(t2.survey_date, '%Y-%m-%d') AS survey_date, t2.*    " +
                "  , concat_ws('/', t2.item_2_1_d, t2.item_2_1_e) AS item_2_1_d_e    " +
                "  , MAX(CASE r.index    " +
                "    WHEN '1' THEN r.item1    " +
                "    ELSE NULL    " +
                "  END) AS item1_1, MAX(CASE r.index    " +
                "    WHEN '2' THEN r.item1    " +
                "    ELSE NULL    " +
                "  END) AS item1_2    " +
                "  , MAX(CASE r.index    " +
                "    WHEN '3' THEN r.item1    " +
                "    ELSE NULL    " +
                "  END) AS item1_3, MAX(CASE r.index    " +
                "    WHEN '4' THEN r.item1    " +
                "    ELSE NULL    " +
                "  END) AS item1_4    " +
                "  , MAX(CASE r.index    " +
                "    WHEN '5' THEN r.item1    " +
                "    ELSE NULL    " +
                "  END) AS item1_5, MAX(CASE r.index    " +
                "    WHEN '6' THEN r.item1    " +
                "    ELSE NULL    " +
                "  END) AS item1_6    " +
                "  , MAX(CASE r.index    " +
                "    WHEN '1' THEN r.item2    " +
                "    ELSE NULL    " +
                "  END) AS item2_1, MAX(CASE r.index    " +
                "    WHEN '2' THEN r.item2    " +
                "    ELSE NULL    " +
                "  END) AS item2_2    " +
                "  , MAX(CASE r.index    " +
                "    WHEN '3' THEN r.item2    " +
                "    ELSE NULL    " +
                "  END) AS item2_3, MAX(CASE r.index    " +
                "    WHEN '4' THEN r.item2    " +
                "    ELSE NULL    " +
                "  END) AS item2_4    " +
                "  , MAX(CASE r.index    " +
                "    WHEN '5' THEN r.item2    " +
                "    ELSE NULL    " +
                "  END) AS item2_5, MAX(CASE r.index    " +
                "    WHEN '6' THEN r.item2    " +
                "    ELSE NULL    " +
                "  END) AS item2_6    " +
                "  , MAX(CASE r.index    " +
                "    WHEN '1' THEN r.item3    " +
                "    ELSE NULL    " +
                "  END) AS item3_1, MAX(CASE r.index    " +
                "    WHEN '2' THEN r.item3    " +
                "    ELSE NULL    " +
                "  END) AS item3_2    " +
                "  , MAX(CASE r.index    " +
                "    WHEN '3' THEN r.item3    " +
                "    ELSE NULL    " +
                "  END) AS item3_3, MAX(CASE r.index    " +
                "    WHEN '4' THEN r.item3    " +
                "    ELSE NULL    " +
                "  END) AS item3_4    " +
                "  , MAX(CASE r.index    " +
                "    WHEN '5' THEN r.item3    " +
                "    ELSE NULL    " +
                "  END) AS item3_5, MAX(CASE r.index    " +
                "    WHEN '6' THEN r.item3    " +
                "    ELSE NULL    " +
                "  END) AS item3_6    " +
                "  , MAX(CASE r.index    " +
                "    WHEN '1' THEN r.item4    " +
                "    ELSE NULL    " +
                "  END) AS item4_1, MAX(CASE r.index    " +
                "    WHEN '2' THEN r.item4    " +
                "    ELSE NULL    " +
                "  END) AS item4_2    " +
                "  , MAX(CASE r.index    " +
                "    WHEN '3' THEN r.item4    " +
                "    ELSE NULL    " +
                "  END) AS item4_3, MAX(CASE r.index    " +
                "    WHEN '4' THEN r.item4    " +
                "    ELSE NULL    " +
                "  END) AS item4_4    " +
                "  , MAX(CASE r.index    " +
                "    WHEN '5' THEN r.item4    " +
                "    ELSE NULL    " +
                "  END) AS item4_5, MAX(CASE r.index    " +
                "    WHEN '6' THEN r.item4    " +
                "    ELSE NULL    " +
                "  END) AS item4_6    " +
                "  , MAX(CASE r.index    " +
                "    WHEN '1' THEN r.item5    " +
                "    ELSE NULL    " +
                "  END) AS item5_1, MAX(CASE r.index    " +
                "    WHEN '2' THEN r.item5    " +
                "    ELSE NULL    " +
                "  END) AS item5_2    " +
                "  , MAX(CASE r.index    " +
                "    WHEN '3' THEN r.item5    " +
                "    ELSE NULL    " +
                "  END) AS item5_3, MAX(CASE r.index    " +
                "    WHEN '4' THEN r.item5    " +
                "    ELSE NULL    " +
                "  END) AS item5_4    " +
                "  , MAX(CASE r.index    " +
                "    WHEN '5' THEN r.item5    " +
                "    ELSE NULL    " +
                "  END) AS item5_5, MAX(CASE r.index    " +
                "    WHEN '6' THEN r.item5    " +
                "    ELSE NULL    " +
                "  END) AS item5_6    " +
                "  , MAX(CASE r.index    " +
                "    WHEN '1' THEN r.item6    " +
                "    ELSE NULL    " +
                "  END) AS item6_1, MAX(CASE r.index    " +
                "    WHEN '2' THEN r.item6    " +
                "    ELSE NULL    " +
                "  END) AS item6_2    " +
                "  , MAX(CASE r.index    " +
                "    WHEN '3' THEN r.item6    " +
                "    ELSE NULL    " +
                "  END) AS item6_3, MAX(CASE r.index    " +
                "    WHEN '4' THEN r.item6    " +
                "    ELSE NULL    " +
                "  END) AS item6_4    " +
                "  , MAX(CASE r.index    " +
                "    WHEN '5' THEN r.item6    " +
                "    ELSE NULL    " +
                "  END) AS item6_5, MAX(CASE r.index    " +
                "    WHEN '6' THEN r.item6    " +
                "    ELSE NULL    " +
                "  END) AS item6_6    " +
                "  , MAX(CASE r.index    " +
                "    WHEN '1' THEN r.item7    " +
                "    ELSE NULL    " +
                "  END) AS item7_1, MAX(CASE r.index    " +
                "    WHEN '2' THEN r.item7    " +
                "    ELSE NULL    " +
                "  END) AS item7_2    " +
                "  , MAX(CASE r.index    " +
                "    WHEN '3' THEN r.item7    " +
                "    ELSE NULL    " +
                "  END) AS item7_3, MAX(CASE r.index    " +
                "    WHEN '4' THEN r.item7    " +
                "    ELSE NULL    " +
                "  END) AS item7_4    " +
                "  , MAX(CASE r.index    " +
                "    WHEN '5' THEN r.item7    " +
                "    ELSE NULL    " +
                "  END) AS item7_5, MAX(CASE r.index    " +
                "    WHEN '6' THEN r.item7    " +
                "    ELSE NULL    " +
                "  END) AS item7_6    " +
                "  , MAX(CASE r.index    " +
                "    WHEN '1' THEN r.item8    " +
                "    ELSE NULL    " +
                "  END) AS item8_1, MAX(CASE r.index    " +
                "    WHEN '2' THEN r.item8    " +
                "    ELSE NULL    " +
                "  END) AS item8_2    " +
                "  , MAX(CASE r.index    " +
                "    WHEN '3' THEN r.item8    " +
                "    ELSE NULL    " +
                "  END) AS item8_3, MAX(CASE r.index    " +
                "    WHEN '4' THEN r.item8    " +
                "    ELSE NULL    " +
                "  END) AS item8_4    " +
                "  , MAX(CASE r.index    " +
                "    WHEN '5' THEN r.item8    " +
                "    ELSE NULL    " +
                "  END) AS item8_5, MAX(CASE r.index    " +
                "    WHEN '6' THEN r.item8    " +
                "    ELSE NULL    " +
                "  END) AS item8_6    " +
                "  , MAX(CASE r.index    " +
                "    WHEN '1' THEN r.item9    " +
                "    ELSE NULL    " +
                "  END) AS item9_1, MAX(CASE r.index    " +
                "    WHEN '2' THEN r.item9    " +
                "    ELSE NULL    " +
                "  END) AS item9_2    " +
                "  , MAX(CASE r.index    " +
                "    WHEN '3' THEN r.item9    " +
                "    ELSE NULL    " +
                "  END) AS item9_3, MAX(CASE r.index    " +
                "    WHEN '4' THEN r.item9    " +
                "    ELSE NULL    " +
                "  END) AS item9_4    " +
                "  , MAX(CASE r.index    " +
                "    WHEN '5' THEN r.item9    " +
                "    ELSE NULL    " +
                "  END) AS item9_5, MAX(CASE r.index    " +
                "    WHEN '6' THEN r.item9    " +
                "    ELSE NULL    " +
                "  END) AS item9_6    " +
                "  , MAX(CASE r.index    " +
                "    WHEN '1' THEN r.item10    " +
                "    ELSE NULL    " +
                "  END) AS item10_1, MAX(CASE r.index    " +
                "    WHEN '2' THEN r.item10    " +
                "    ELSE NULL    " +
                "  END) AS item10_2    " +
                "  , MAX(CASE r.index    " +
                "    WHEN '3' THEN r.item10    " +
                "    ELSE NULL    " +
                "  END) AS item10_3, MAX(CASE r.index    " +
                "    WHEN '4' THEN r.item10    " +
                "    ELSE NULL    " +
                "  END) AS item10_4    " +
                "  , MAX(CASE r.index    " +
                "    WHEN '5' THEN r.item10    " +
                "    ELSE NULL    " +
                "  END) AS item10_5, MAX(CASE r.index    " +
                "    WHEN '6' THEN r.item10    " +
                "    ELSE NULL    " +
                "  END) AS item10_6    " +
                "FROM  hospital_colonoscopy_result t2  " +
                "  LEFT JOIN hospital_intestine_review t1  ON t1.sid = t2.sid    " +
                "  LEFT JOIN itsys_user u ON t1.create_user = u.loginName    " +
                "  LEFT JOIN hospital_colonoscopy_lesions_record r ON r.colonoscopy_result_id = t2.id    " +
                "WHERE 1=1 ");
        sql.append(" and t2.id in (select max(t.id) from hospital_colonoscopy_result t group by t.sid) ");

        if (!StringUtil.isEmpty(execlData.getGroup())) {
            if (execlData.getGroup().equals("Cg")) {
                sql.append(" and t1.`group` = 'C' and t1.risk_level = ? ");
                parm.add(2);
            } else if (execlData.getGroup().equals("Cd")) {
                sql.append(" and t1.`group` = 'C' and t1.risk_level = ? ");
                parm.add(1);
            } else {
                sql.append(" and t1.`group` = ? ");
                parm.add(execlData.getGroup());
            }
        }

        if (execlData.getInGroupDateStart() != null) {
            sql.append(" and t1.in_group_date >= ? ");
            parm.add(execlData.getInGroupDateStart());
        }

        if (execlData.getInGroupDateEnd() != null) {
            sql.append(" and t1.in_group_date <= ? ");
            parm.add(execlData.getInGroupDateEnd());
        }

        if (execlData.getStage_cy() != null) {
            sql.append(" and t1.stage_cy = ? ");
            parm.add(execlData.getStage_cy());
        }


        if (execlData.getSiteId() != null) {
            sql.append("  and t1.site_id = ? ");
            parm.add(execlData.getSiteId());
        }

        sql.append(" GROUP BY t2.id  ORDER BY t2.date_created DESC ");
        if (parm.size() > 0) {
            return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<HospitalColonoscopyResultVo>(HospitalColonoscopyResultVo.class), parm.toArray());
        }
        return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<HospitalColonoscopyResultVo>(HospitalColonoscopyResultVo.class));
    }

    @Override
    public List<HospitalReview> stoolReviewQueryExcel(ExeclData execlData) {
        //select  approval_status as approvalStatus,edit_status as editStatus,apply_status as applyStatus,`id`,`name`,`phone`,`sex`,community_dept_id as communityDeptId,area_dept_id as areaDeptId,id_card as idCard,sid,`age`,birth_date as birthDate,item1,item2,item3,item4,item5,item6,item7,item8,item9,item10,investigator,survey_date as surveyDate,reviewer,address,in_group_date as inGroupDate,`group`,group_status as groupStatus,off_group_reason as offGroupReason,stage_cy as stageCy,review_status as reviewStatus,risk_factor_status as riskFactorStatus,overall_status_cy as overallStatusCy,overall_status_t0 as overallStatusT0, overall_status_t1 as overallStatusT1, overall_status_t2 as overallStatusT2, overall_status_t3 as overallStatusT3,violation_plan_status_cy as violationPlanStatusCy,violation_plan_status_t0 as violationPlanStatusT0,violation_plan_status_t1 as violationPlanStatusT1,violation_plan_status_t2 as violationPlanStatusT2,violation_plan_status_t3 as violationPlanStatusT3,risk_level as riskLevel,site_id as siteId  from hospital_intestine_review where sid=?";
        List<Object> parm = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT " +
                " @rownum:=@rownum+1 AS number, " +
                " t1.*," +
                " t1.overall_status_cy AS overallStatusCy,  " +
                "DATE_FORMAT(t1.birth_date,'%Y-%m-%d') AS birthDateToString,  " +
                "DATE_FORMAT(t1.survey_date,'%Y-%m-%d') AS surveyDateToString,  " +
                " CASE " +
                "WHEN t1.`group` = 'A' THEN " +
                " 'A' " +
                "WHEN t1.`group` = 'B' THEN " +
                " 'B' " +
                "WHEN t1.`group` = 'C' " +
                "AND t1.risk_level IS NULL THEN " +
                " 'C' " +
                "WHEN t1.`group` = 'C' " +
                "AND t1.risk_level = 1 THEN " +
                " 'Cd' " +
                "WHEN t1.`group` = 'C' " +
                "AND t1.risk_level = 2 THEN " +
                " 'Cg' " +
                "ELSE " +
                " NULL " +
                "END 'group'  " +
                "FROM " +
                "  (SELECT @rownum:=0) r, " +
                " hospital_intestine_review t1, " +
                " itsys_user u " +
                "WHERE " +
                " 1 = 1 " +
                "AND t1.create_user = u.loginName");
        if (execlData.getAreaDeptId() != null) {
            sql.append(" and t1.area_dept_id = ?");
            parm.add(execlData.getAreaDeptId());
        }
        if (execlData.getCommunityDeptId() != null) {
            sql.append(" and t1.community_dept_id = ? ");
            parm.add(execlData.getCommunityDeptId());
        }
        if (!StringUtil.isEmpty(execlData.getLoginName())) {
            sql.append("  and u.loginName = ? ");
            parm.add(execlData.getLoginName());
        }

        if (!StringUtil.isEmpty(execlData.getGroup())) {
            if (execlData.getGroup().equals("Cg")) {
                sql.append(" and t1.`group` = 'C' and t1.risk_level = ? ");
                parm.add(2);
            } else if (execlData.getGroup().equals("Cd")) {
                sql.append(" and t1.`group` = 'C' and t1.risk_level = ? ");
                parm.add(1);
            } else {
                sql.append(" and t1.`group` = ? ");
                parm.add(execlData.getGroup());
            }
        }

        if (execlData.getInGroupDateStart() != null) {
            sql.append(" and t1.in_group_date >= ? ");
            parm.add(execlData.getInGroupDateStart());
        }

        if (execlData.getInGroupDateEnd() != null) {
            sql.append(" and t1.in_group_date <= ? ");
            parm.add(execlData.getInGroupDateEnd());
        }

        if (execlData.getStage_cy() != null) {
            sql.append(" and t1.stage_cy = ? ");
            parm.add(execlData.getStage_cy());
        }


        if (execlData.getSiteId() != null) {
            sql.append("  and t1.site_id = ? ");
            parm.add(execlData.getSiteId());
        }

        sql.append(" order by t1.date_created desc ");
        if (parm.size() > 0) {
            return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<HospitalReview>(HospitalReview.class), parm.toArray());
        }
        return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<HospitalReview>(HospitalReview.class));
    }

    @Override
    public List<ColonoscopyNotificationVo> stoolNotificationQueryExcel(ExeclData execlData) {
        //select t1.`name`,t2.*  from hospital_intestine_review t1,hospital_screening_notification t2 where t1.sid=t2.sid and t2.id=?";
        List<Object> parm = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT " +
                " @rownum:=@rownum+1 AS number, " +
                " t1.name," +
                " t1.overall_status_cy AS overallStatusCy," +
                " t2.*," +
                "DATE_FORMAT(t2.sc_date,'%Y-%m-%d') AS sc_date_tostring  " +
                "FROM " +
                "  (SELECT @rownum:=0) r, " +
                " hospital_intestine_review t1, " +
                "hospital_screening_notification t2," +
                " itsys_user u " +
                "WHERE " +
                " 1 = 1 " +
                "AND t1.sid=t2.sid " +
                "AND t1.create_user = u.loginName");

        sql.append(" and t2.id in (select max(t.id) from hospital_screening_notification t group by t.sid) ");
        if (execlData.getAreaDeptId() != null) {
            sql.append(" and t1.area_dept_id = ?");
            parm.add(execlData.getAreaDeptId());
        }
        if (execlData.getCommunityDeptId() != null) {
            sql.append(" and t1.community_dept_id = ? ");
            parm.add(execlData.getCommunityDeptId());
        }
        if (!StringUtil.isEmpty(execlData.getLoginName())) {
            sql.append("  and u.loginName = ? ");
            parm.add(execlData.getLoginName());
        }

        if (!StringUtil.isEmpty(execlData.getGroup())) {
            if (execlData.getGroup().equals("Cg")) {
                sql.append(" and t1.`group` = 'C' and t1.risk_level = ? ");
                parm.add(2);
            } else if (execlData.getGroup().equals("Cd")) {
                sql.append(" and t1.`group` = 'C' and t1.risk_level = ? ");
                parm.add(1);
            } else {
                sql.append(" and t1.`group` = ? ");
                parm.add(execlData.getGroup());
            }
        }

        if (execlData.getInGroupDateStart() != null) {
            sql.append(" and t1.in_group_date >= ? ");
            parm.add(execlData.getInGroupDateStart());
        }

        if (execlData.getInGroupDateEnd() != null) {
            sql.append(" and t1.in_group_date <= ? ");
            parm.add(execlData.getInGroupDateEnd());
        }

        if (execlData.getStage_cy() != null) {
            sql.append(" and t1.stage_cy = ? ");
            parm.add(execlData.getStage_cy());
        }


        if (execlData.getSiteId() != null) {
            sql.append("  and t1.site_id = ? ");
            parm.add(execlData.getSiteId());
        }

        sql.append(" order by t2.date_created desc ");
        if (parm.size() > 0) {
            return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<ColonoscopyNotificationVo>(ColonoscopyNotificationVo.class), parm.toArray());
        }
        return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<ColonoscopyNotificationVo>(ColonoscopyNotificationVo.class));
    }
}
