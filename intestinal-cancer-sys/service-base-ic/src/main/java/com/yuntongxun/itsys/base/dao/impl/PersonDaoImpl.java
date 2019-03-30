/**
 * Project Name:service-base-yl
 * File Name:PersonDaoImpl.java
 * Package Name:com.yuntongxun.itsys.base.dao.impl
 * Date:2018年4月9日下午6:32:38
 * Copyright (c) 2018, ty All Rights Reserved.
 */

package com.yuntongxun.itsys.base.dao.impl;

import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.util.JSONUtils;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.dao.PersonDao;
import com.yuntongxun.itsys.base.po.*;
import com.yuntongxun.itsys.base.po.dto.HospitalRiskFactorDto;
import com.yuntongxun.itsys.base.po.dto.quitlog.QuitInResultDto;
import com.yuntongxun.itsys.base.po.dto.quitlog.QuitSearchDto;
import com.yuntongxun.itsys.base.vo.HospitalBiologicalSampleResultVo;
import com.yuntongxun.itsys.base.vo.IntestineReason;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * ClassName:PersonDaoImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2018年4月9日 下午6:32:38 <br/>
 *
 * @author ty
 * @see
 * @since JDK 1.8
 */
@Repository
public class PersonDaoImpl implements PersonDao {
    private final Logger log = LogManager.getLogger(PersonDaoImpl.class);
    @Autowired
    private JdbcTemplate jdbc;

    public static final Integer PERSON_STOOL_DNA_STAGE0 = 1;// 阶段t0
    public static final Integer PERSON_ROMOVE_STATUS1 = 1;// 1.未移除
    public static final Integer PERSON_ROMOVE_STATUS2 = 2;// 2：已移除

    @SuppressWarnings("unchecked")
    @Override
    public List<HospitalReview> query(HospitalReview person) {
        log.info("@dao query 查询参数 person = {}", person.toString());
        StringBuilder sql = new StringBuilder();
        @SuppressWarnings("rawtypes")
        List args = new ArrayList();
        sql.append(
                " SELECT r.id,r.name,r.phone,r.community_dept_id communityDeptId,r.area_dept_id areaDeptId,r.id_card idCard,r.sid,r.age,r.sex,r.birth_date birthDate,r.item1,r.item2,r.item3,r.item4,r.item5,r.item6,r.item7,r.item8,r.item9,r.item10,r.investigator,r.survey_date surveyDate,r.reviewer,r.address,r.in_group_date inGroupDate,r.`group`,r.group_status groupStatus,r.off_group_reason offGroupReason,r.stage_cy stageCy,r.review_status reviewStatus,r.risk_factor_status riskFactorStatus,r.overall_status_cy overallStatusCy,r.overall_status_t0 overallStatusT0,r.overall_status_t1 overallStatusT1,r.overall_status_t2 overallStatusT2,r.overall_status_t3 overallStatusT3,r.violation_plan_status_cy violationPlanStatusCy,r.violation_plan_status_t0 violationPlanStatusT0,r.violation_plan_status_t1 violationPlanStatusT1,r.violation_plan_status_t2 violationPlanStatusT2,r.violation_plan_status_t3 violationPlanStatusT3,r.risk_level riskLevel,r.site_id siteId,r.delete_flag deleteFlag,r.date_created dateCreated,r.update_time updateTime FROM hospital_intestine_review r,itsys_user u WHERE 1=1 and r.create_user = u.loginName ");

        if (StringUtils.isNotBlank(person.getSid())) {
            sql.append(" and r.sid like ? ");
            args.add("%" + person.getSid() + "%");
        }

        if (StringUtils.isNotBlank(person.getName())) {
            sql.append(" and r.name like ? ");
            args.add("%" + person.getName() + "%");
        }

        if (StringUtils.isNotBlank(person.getPhone())) {
            sql.append(" and r.phone = ? ");
            args.add(person.getPhone());
        }

        if (StringUtils.isNotBlank(person.getIdCard())) {
            sql.append(" and r.id_card = ? ");
            args.add(person.getIdCard());
        }

        if (person.getSex() != null) {
            sql.append(" and r.sex = ? ");
            args.add(person.getSex());
        }

        if (StringUtils.isNotBlank(person.getGroup())) {
            sql.append(" and r.group = ? ");
            args.add(person.getGroup());
        }

        if (person.getOverallStatusCy() != null) {
            sql.append(" and r.overall_status_cy = ? ");
            args.add(person.getOverallStatusCy());
        }

        if (person.getViolationPlanStatusCy() != null) {
            sql.append(" and r.violation_plan_status_cy = ? ");
            args.add(person.getViolationPlanStatusCy());
        }

        if (StringUtils.isNotEmpty(person.getSid())) {
            sql.append(" and r.sid = ? ");
            args.add(person.getSid());
        }

        if (StringUtils.isNotBlank(person.getInGroupDateStart())) {
            sql.append(" and r.in_group_date >= ? ");
            args.add(person.getInGroupDateStart() + " 00:00:00");
        }

        if (StringUtils.isNotBlank(person.getInGroupDateEnd())) {
            sql.append(" and r.in_group_date <= ? ");
            args.add(person.getInGroupDateEnd() + " 23:59:59");
        }
        if (person.getCommunityDeptId() != null) {
            sql.append(" and r.community_dept_id = ?");
            args.add(person.getCommunityDeptId());
        }
        if (StringUtils.isNotBlank(person.getLoginName())) {
            sql.append(" and u.loginName = ?");
            args.add(person.getLoginName());
        }
        List<HospitalReview> list = jdbc.query(sql.toString(), args.toArray(),
                new BeanPropertyRowMapper<HospitalReview>(HospitalReview.class));
        return list;
    }

    @Override
    public HospitalReview queryByCardId(String CardId) {
        HospitalReview result;
        try {
            String sql = "select * from hospital_intestine_review_no_conform where id_card=?";
            result = jdbc.queryForObject(sql, new BeanPropertyRowMapper<HospitalReview>(HospitalReview.class), CardId);
        } catch (EmptyResultDataAccessException e) {
            log.info("queryByFit_code hospital_fit_result is error data is null");
            return null;
        } catch (IncorrectResultSizeDataAccessException e) {
            log.info("queryByFit_code hospital_fit_result is error data size is >1");
            return null;
        }
        return result;
    }

    @Override
    public void quit(String sid) {
        String sql = "UPDATE hospital_person_info r set r.group_status = ? WHERE r.sid = ?";
        jdbc.update(sql, new Object[]{Constans.PERSON_GROUPSTATUS_QUIT, sid});
    }

    @Override
    public List<QuitInResultDto> updateState(List<HospitalReview> hospitalReviews, IntestineReason intestineReason) {

        List<QuitInResultDto> quitResultDtos = new ArrayList<>();

        // sid 集合
        // 更新受试者状态sql集合
        String[] sqls = new String[hospitalReviews.size()];
        // 更新受试者待办事项sql集合
        String[] eventUpdataSqls = new String[hospitalReviews.size()];
        // 新增退出日志集合
        String[] quitLogSqls = new String[hospitalReviews.size()];
        // 退出试验理由
        String[] resaon = intestineReason.getReason();
        StringBuilder resaonSb = new StringBuilder();
        if (resaon.length > 0) {

            for (int i = 0; i < resaon.length; i++) {
                resaonSb.append(resaon[i]).append(",");
            }
        }
        String resaonString = resaonSb.substring(0, resaonSb.length() - 1);

        log.info("updateState 退出理由  参数：resaonString={}", resaonString);

        if (hospitalReviews.size() > 0) {
            for (int i = 0; i < hospitalReviews.size(); i++) {
                QuitInResultDto quitResultDto = new QuitInResultDto();
                quitResultDto.setSid(hospitalReviews.get(i).getSid());
                quitResultDto.setQuitLogId(UUID.randomUUID().toString().replaceAll("-", ""));
                quitLogSqls[i] = "INSERT INTO `hospital_intestine_review_quit_log` (`id`,`area_id`,`community_dept_id`,`sid`, `type`, `off_group_reason`, `quit_date`, `date_created`)"
                        + " VALUES ('" + quitResultDto.getQuitLogId() + "','" + intestineReason.getDepartmentPId()
                        + "','" + intestineReason.getDepartmentId() + "','" + hospitalReviews.get(i).getSid() + "', '"
                        + Constans.PERSON_QUIT_LOG_TYPE_0 + "', '" + resaonString + "', '"
                        + intestineReason.getQuitDate() + "', now())";
                quitResultDtos.add(quitResultDto);
                int cy = hospitalReviews.get(i).getStageCy() - 1;
                log.info("SID={} 的 cy  参数：cy={}", hospitalReviews.get(i).getSid(), cy);
                String overallStatusSql = "overall_status_t" + cy;
                sqls[i] = "update hospital_intestine_review set overall_status_cy=" + Constans.PERSON_OVERALL_STATUS2
                        + ", " + overallStatusSql + " = " + Constans.PERSON_OVERALL_STATUS2 + ",off_group_reason='"
                        + resaonString + "',update_time=now() where `sid` = '" + hospitalReviews.get(i).getSid() + "'";
                eventUpdataSqls[i] = "update hospital_todo_event set status=" + Constans.PERSON_TODO_EVENT_STATUS4
                        + " where `sid` = '" + hospitalReviews.get(i).getSid() + "' and status = 1";
            }
        }
        log.info("退出试验数据库执行sql 列表 参数：quitLogSqls={}", quitLogSqls);
        jdbc.batchUpdate(quitLogSqls);
        log.info("退出试验数据库执行sql 列表 参数：sqls={}", sqls);
        jdbc.batchUpdate(sqls);
        log.info("退出试验数据库执行sql 列表 参数：eventUpdataSqls={}", eventUpdataSqls);
        jdbc.batchUpdate(eventUpdataSqls);

        return quitResultDtos;
    }

    @Override
    public void reresearchUpdateState(List<HospitalReview> hospitalReviews, IntestineReason intestineReason) {

        String[] sqls = new String[hospitalReviews.size()];
        String[] eventUpdataSqls = new String[hospitalReviews.size()];
        // 新增重新入组日志集合
        String[] quitLogSqls = new String[hospitalReviews.size()];
        if (hospitalReviews.size() > 0) {
            for (int i = 0; i < hospitalReviews.size(); i++) {
                quitLogSqls[i] = "UPDATE hospital_intestine_review_quit_log SET again_group_date = now() WHERE sid = '"
                        + hospitalReviews.get(i).getSid() + "' ORDER BY date_created DESC LIMIT 1";
                int cy = hospitalReviews.get(i).getStageCy() - 1;
                String overallStatusSql = "overall_status_t" + cy;
                sqls[i] = "update hospital_intestine_review set overall_status_cy=" + Constans.PERSON_OVERALL_STATUS1
                        + ", " + overallStatusSql + " = '" + Constans.PERSON_OVERALL_STATUS1
                        + "',update_time=now() where `sid` = '" + hospitalReviews.get(i).getSid() + "'";
                eventUpdataSqls[i] = "update hospital_todo_event set status=" + Constans.PERSON_TODO_EVENT_STATUS1
                        + " where `sid` = '" + hospitalReviews.get(i).getSid() + "' and status = 4";
            }
        }
        jdbc.batchUpdate(quitLogSqls);
        jdbc.batchUpdate(sqls);
        jdbc.batchUpdate(eventUpdataSqls);
    }

    @Override
    public HospitalReview findInfoBySid(String sid) {
        log.info("@dao findInfoBySid 查询参数 sid = {}", sid);
        String sql = "SELECT d.`name` as depName,t.* from hospital_intestine_review t,itsys_department d  WHERE t.sid = ? and t.community_dept_id=d.id";
        List<HospitalReview> deptMemberList = jdbc.query(sql, new Object[]{sid},
                new BeanPropertyRowMapper<HospitalReview>(HospitalReview.class));
        HospitalReview hospitalReview1 = null;
        if (deptMemberList != null && deptMemberList.size() > 0) {
            hospitalReview1 = deptMemberList.get(0);
        }
        return hospitalReview1;
    }

    @Override
    public List<HospitalFitResult> findFitBySidAndState(String sid) {
        log.info("@dao findFitBySidAndState 查询参数 sid = {}", sid);
        String sql = "SELECT t.* from hospital_fit_result t WHERE t.sid = ?";
        List<HospitalFitResult> fitResultList = jdbc.query(sql, new Object[]{sid},
                new BeanPropertyRowMapper<HospitalFitResult>(HospitalFitResult.class));

        return fitResultList;
    }

    @Override
    public List<HospitalStoolDNA> findDNABySidAndState(String sid) {
        log.info("@dao findDNABySidAndState 查询参数 sid = {}", sid);
        String sql = "SELECT t.* from hospital_stool_dna t WHERE t.sid = ?";
        List<HospitalStoolDNA> hospitalStoolDNAList = jdbc.query(sql, new Object[]{sid},
                new BeanPropertyRowMapper<HospitalStoolDNA>(HospitalStoolDNA.class));

        return hospitalStoolDNAList;
    }

    // 新增生物样本查询列表
    @Override
    public List<HospitalBiologicalSampleResultVo> findSamPle(String sid) {
        // TODO Auto-generated method stub
        log.info("@dao findSamPle 查询参数sid = {}", sid);
        String sql = "select t.id as asid,t.collect_status as sample_A,"
                + "t4.id as msid,t4.collect_status as sample_M,t5.id as fsid,t5.collect_status as sample_F  from "
                /*
                 * +
                 * " (select id, collect_status from hospital_biological_sample_result where sample_type = 'S' and sid ='"
                 * + sid + "') as t1," +
                 * "(select id, collect_status from hospital_biological_sample_result where sample_type = 'P' and sid = '"
                 * + sid + "') as t2," +
                 * "(select id, collect_status from hospital_biological_sample_result where sample_type = 'W' and sid = '"
                 * + sid + "') as t3,"
                 */
                + "(select id, collect_status from hospital_biological_sample_result where sample_type = 'A' and sid = '"
                + sid + "') as t,"
                + "(select id, collect_status from hospital_biological_sample_result where sample_type = 'M' and sid = '"
                + sid + "') as t4,"
                + "(select id, collect_status from hospital_biological_sample_result where sample_type = 'F' and sid = '"
                + sid + "') as t5";
        List<HospitalBiologicalSampleResultVo> sample = jdbc.query(sql,
                new BeanPropertyRowMapper<HospitalBiologicalSampleResultVo>(HospitalBiologicalSampleResultVo.class));
        return sample;
    }

    @Override
    public List<HospitalColonoscopyRecord> findRecordBySidAndState(String sid) {
        log.info("@dao findRecordBySidAndState 查询参数 sid = {}", sid);
        String sql = "SELECT t.* from hospital_colonoscopy_record t WHERE t.sid = ?";
        List<HospitalColonoscopyRecord> hospitalColonoscopyRecordList = jdbc.query(sql, new Object[]{sid},
                new BeanPropertyRowMapper<HospitalColonoscopyRecord>(HospitalColonoscopyRecord.class));

        return hospitalColonoscopyRecordList;
    }

    @Override
    public List<HospitalAbnormalEvent> findAbnormalBySidAndState(String sid) {
        log.info("@dao findAbnormalBySidAndState 查询参数 sid = {}", sid);
        String sql = "SELECT t.* from hospital_abnormal_event t WHERE t.sid = ?";
        List<HospitalAbnormalEvent> hospitalAbnormalEventList = jdbc.query(sql, new Object[]{sid},
                new BeanPropertyRowMapper<HospitalAbnormalEvent>(HospitalAbnormalEvent.class));

        return hospitalAbnormalEventList;
    }

    @Override
    public int updateRiskLevelBySid(Integer level, String sid, Integer score) {
        log.info("@Dao updateRiskLevelBySid 更新危险等级：参数 sid={},危险等级={} ", sid, level);
        String sql = "UPDATE hospital_intestine_review SET risk_level=?,update_time=now(),risk_level_time=now(),score=? WHERE sid = ?";
        int result = jdbc.update(sql, level, score, sid);
        log.info("@Dao updateRiskLevelBySid End  ");
        return result;
    }

    @Override
    public int updateRiskStatusBySid(String sid, Integer status) {
        log.info("@Dao updateRiskStatusBySid 更新危险等级：参数 sid={},收集状态={} ", sid, status);
        String sql = "UPDATE hospital_intestine_review SET risk_factor_status=?,update_time=now() WHERE sid = ?";
        int result = jdbc.update(sql, status, sid);
        log.info("@Dao updateRiskStatusBySid End  ");
        return result;
    }

    @Override
    public int addTodoEvent(HospitalTodoEvent hospitalTodoEvent) {
        StringBuffer stringBuffer = new StringBuffer();
        log.info("@Dao addTodoEvent Start 新增待办数据：{} ", JSONUtils.toJson(hospitalTodoEvent));
        stringBuffer.append("INSERT INTO hospital_todo_event(type,community_dept_id,area_dept_id,sid,status,data_id");
        if (!StringUtils.isEmpty(hospitalTodoEvent.getOperationSource())) {
            stringBuffer.append(",operation_source,operation_source_id");
        }

        stringBuffer.append(",date_created,update_time)");
        stringBuffer.append(" VALUES(?,?,?,?,?,?,");
        if (!StringUtils.isEmpty(hospitalTodoEvent.getOperationSource())) {
            stringBuffer.append("?,?,");
        }

        stringBuffer.append("now(),now())");
        //String sql = "INSERT INTO hospital_todo_event(type,community_dept_id,area_dept_id,sid,status,data_id,date_created,update_time)"
        //		+ " VALUES(?,?,?,?,?,?,now(),now())";
        if (!StringUtils.isEmpty(hospitalTodoEvent.getOperationSource())) {
            return jdbc.update(stringBuffer.toString(), hospitalTodoEvent.getType(), hospitalTodoEvent.getCommunityDeptId(),
                    hospitalTodoEvent.getAreaDeptId(), hospitalTodoEvent.getSid(), hospitalTodoEvent.getStatus(),
                    hospitalTodoEvent.getDataId(), hospitalTodoEvent.getOperationSource(), hospitalTodoEvent.getOperationSourceId());
        } else {
            return jdbc.update(stringBuffer.toString(), hospitalTodoEvent.getType(), hospitalTodoEvent.getCommunityDeptId(),
                    hospitalTodoEvent.getAreaDeptId(), hospitalTodoEvent.getSid(), hospitalTodoEvent.getStatus(),
                    hospitalTodoEvent.getDataId());
        }
    }

    @Override
    public int addRiskFactor(HospitalRiskFactor hospitalRiskFactor) {
        log.info("@Dao addRiskFactor Start 新增危险因素数据：{} ", JSONUtils.toJson(hospitalRiskFactor));
        String sql = "INSERT INTO hospital_intestine_risk_factor("
                + "sid,`investigator`,`survey_date`,`reviewer`,`investigator_code`,`contact_relationship`,`contact_cell_phone`,`contact_telephone`,"
                + "height,weight,yao_wei,education,marriage,marriage_other,item_2_1,item_2_1_1,item_2_1_2_1,item_2_1_2_2,item_2_1_2_3,item_2_1_2_4,"
                + "item_2_1_2_4_other,item_2_2,item_2_2_1,item_2_3,item_2_3_1,"
                + "item_2_3_2,item_2_4,item_2_4_1,item_2_4_2,item_2_4_3,item_3_1,item_3_1_1,item_3_1_2,item_3_1_2_1,item_3_1_2_2,item_3_2,"
                + "item_3_2_1,item_3_2_2,item_3_2_2_1,item_3_2_2_2,item_3_3,item_3_3_years,"
                + "item_3_3_1,item_3_3_1_1,item_3_3_1_2,item_4_1,item_4_2,item_4_3,item_4_3_1,item_4_4,item_4_5,item_4_6,item_4_7,item_4_8,"
                + "item_4_9,item_4_10,item_4_11_1,item_4_11_2,item_4_11_3,item_4_11_4,item_4_11_5,"
                + "item_4_11_6,item_4_11_7,item_4_11_8,item_4_11_9,item_4_12_1,item_4_12_2,item_4_12_3,item_4_12_4,item_4_12_5,item_4_12_6,"
                + "item_4_12_7,item_4_12_8,item_4_12_9,item_4_12_10,item_5_1,item_5_1_1,item_5_1_1_age,"
                + "item_5_1_2,item_5_1_2_age,item_5_1_3,item_5_1_3_age,item_5_1_4,item_5_1_4_age,item_5_1_5,item_5_1_5_age,item_5_1_6,"
                + "item_5_1_6_age,item_5_1_7,item_5_1_7_age,item_5_1_8,item_5_1_8_age,item_5_1_9,item_5_1_9_age,"
                + "item_5_1_10,item_5_1_10_age,item_5_2,item_5_2_1,item_5_2_1_age,item_5_2_2,item_5_2_2_age,item_5_2_3,item_5_2_3_age,"
                + "item_5_2_4,item_5_2_4_age,item_5_2_5,item_5_2_5_age,item_5_2_6,item_5_2_6_age,item_5_2_7,"
                + "item_5_2_7_age,item_5_2_8,item_5_2_8_age,item_5_2_9,item_5_2_9_age,item_5_2_10,item_5_2_10_age,item_5_2_11,"
                + "item_5_2_11_age,item_5_2_12,item_5_2_12_age,item_5_2_13,item_5_2_13_age,item_5_3,item_5_3_1,item_5_3_1_age,"
                + "item_5_3_2,item_5_3_2_age,item_5_3_3,item_5_3_3_age,item_5_3_4,item_5_3_4_age,area_dept_id,community_dept_id"
                + ",date_created,update_time,score)"
                + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?"
                + ",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),now(),?)";
        log.info("@DaoImpl 【危险因素数据-新增】 sql={}", sql);
        int result = jdbc.update(sql, hospitalRiskFactor.getSid(), hospitalRiskFactor.getInvestigator(),
                hospitalRiskFactor.getSurvey_date(), hospitalRiskFactor.getReviewer(),
                hospitalRiskFactor.getInvestigator_code(), hospitalRiskFactor.getContact_relationship(),
                hospitalRiskFactor.getContact_cell_phone(), hospitalRiskFactor.getContact_telephone(),
                hospitalRiskFactor.getHeight(), hospitalRiskFactor.getWeight(), hospitalRiskFactor.getYao_wei(),
                hospitalRiskFactor.getEducation(), hospitalRiskFactor.getMarriage(),
                hospitalRiskFactor.getMarriage_other(), hospitalRiskFactor.getItem_2_1(),
                hospitalRiskFactor.getItem_2_1_1(), hospitalRiskFactor.getItem_2_1_2_1(),
                hospitalRiskFactor.getItem_2_1_2_2(), hospitalRiskFactor.getItem_2_1_2_3(),
                hospitalRiskFactor.getItem_2_1_2_4(), hospitalRiskFactor.getItem_2_1_2_4_other(),
                hospitalRiskFactor.getItem_2_2(), hospitalRiskFactor.getItem_2_2_1(), hospitalRiskFactor.getItem_2_3(),
                hospitalRiskFactor.getItem_2_3_1(), hospitalRiskFactor.getItem_2_3_2(),
                hospitalRiskFactor.getItem_2_4(), hospitalRiskFactor.getItem_2_4_1(),
                hospitalRiskFactor.getItem_2_4_2(), hospitalRiskFactor.getItem_2_4_3(),
                hospitalRiskFactor.getItem_3_1(), hospitalRiskFactor.getItem_3_1_1(),
                hospitalRiskFactor.getItem_3_1_2(), hospitalRiskFactor.getItem_3_1_2_1(),
                hospitalRiskFactor.getItem_3_1_2_2(), hospitalRiskFactor.getItem_3_2(),
                hospitalRiskFactor.getItem_3_2_1(), hospitalRiskFactor.getItem_3_2_2(),
                hospitalRiskFactor.getItem_3_2_2_1(), hospitalRiskFactor.getItem_3_2_2_2(),
                hospitalRiskFactor.getItem_3_3(), hospitalRiskFactor.getItem_3_3_years(),
                hospitalRiskFactor.getItem_3_3_1(), hospitalRiskFactor.getItem_3_3_1_1(),
                hospitalRiskFactor.getItem_3_3_1_2(), hospitalRiskFactor.getItem_4_1(),
                hospitalRiskFactor.getItem_4_2(), hospitalRiskFactor.getItem_4_3(), hospitalRiskFactor.getItem_4_3_1(),
                hospitalRiskFactor.getItem_4_4(), hospitalRiskFactor.getItem_4_5(), hospitalRiskFactor.getItem_4_6(),
                hospitalRiskFactor.getItem_4_7(), hospitalRiskFactor.getItem_4_8(), hospitalRiskFactor.getItem_4_9(),
                hospitalRiskFactor.getItem_4_10(), hospitalRiskFactor.getItem_4_11_1(),
                hospitalRiskFactor.getItem_4_11_2(), hospitalRiskFactor.getItem_4_11_3(),
                hospitalRiskFactor.getItem_4_11_4(), hospitalRiskFactor.getItem_4_11_5(),
                hospitalRiskFactor.getItem_4_11_6(), hospitalRiskFactor.getItem_4_11_7(),
                hospitalRiskFactor.getItem_4_11_8(), hospitalRiskFactor.getItem_4_11_9(),
                hospitalRiskFactor.getItem_4_12_1(), hospitalRiskFactor.getItem_4_12_2(),
                hospitalRiskFactor.getItem_4_12_3(), hospitalRiskFactor.getItem_4_12_4(),
                hospitalRiskFactor.getItem_4_12_5(), hospitalRiskFactor.getItem_4_12_6(),
                hospitalRiskFactor.getItem_4_12_7(), hospitalRiskFactor.getItem_4_12_8(),
                hospitalRiskFactor.getItem_4_12_9(), hospitalRiskFactor.getItem_4_12_10(),
                hospitalRiskFactor.getItem_5_1(), hospitalRiskFactor.getItem_5_1_1(),
                hospitalRiskFactor.getItem_5_1_1_age(), hospitalRiskFactor.getItem_5_1_2(),
                hospitalRiskFactor.getItem_5_1_2_age(), hospitalRiskFactor.getItem_5_1_3(),
                hospitalRiskFactor.getItem_5_1_3_age(), hospitalRiskFactor.getItem_5_1_4(),
                hospitalRiskFactor.getItem_5_1_4_age(), hospitalRiskFactor.getItem_5_1_5(),
                hospitalRiskFactor.getItem_5_1_5_age(), hospitalRiskFactor.getItem_5_1_6(),
                hospitalRiskFactor.getItem_5_1_6_age(), hospitalRiskFactor.getItem_5_1_7(),
                hospitalRiskFactor.getItem_5_1_7_age(), hospitalRiskFactor.getItem_5_1_8(),
                hospitalRiskFactor.getItem_5_1_8_age(), hospitalRiskFactor.getItem_5_1_9(),
                hospitalRiskFactor.getItem_5_1_9_age(), hospitalRiskFactor.getItem_5_1_10(),
                hospitalRiskFactor.getItem_5_1_10_age(), hospitalRiskFactor.getItem_5_2(),
                hospitalRiskFactor.getItem_5_2_1(), hospitalRiskFactor.getItem_5_2_1_age(),
                hospitalRiskFactor.getItem_5_2_2(), hospitalRiskFactor.getItem_5_2_2_age(),
                hospitalRiskFactor.getItem_5_2_3(), hospitalRiskFactor.getItem_5_2_3_age(),
                hospitalRiskFactor.getItem_5_2_4(), hospitalRiskFactor.getItem_5_2_4_age(),
                hospitalRiskFactor.getItem_5_2_5(), hospitalRiskFactor.getItem_5_2_5_age(),
                hospitalRiskFactor.getItem_5_2_6(), hospitalRiskFactor.getItem_5_2_6_age(),
                hospitalRiskFactor.getItem_5_2_7(), hospitalRiskFactor.getItem_5_2_7_age(),
                hospitalRiskFactor.getItem_5_2_8(), hospitalRiskFactor.getItem_5_2_8_age(),
                hospitalRiskFactor.getItem_5_2_9(), hospitalRiskFactor.getItem_5_2_9_age(),
                hospitalRiskFactor.getItem_5_2_10(), hospitalRiskFactor.getItem_5_2_10_age(),
                hospitalRiskFactor.getItem_5_2_11(), hospitalRiskFactor.getItem_5_2_11_age(),
                hospitalRiskFactor.getItem_5_2_12(), hospitalRiskFactor.getItem_5_2_12_age(),
                hospitalRiskFactor.getItem_5_2_13(), hospitalRiskFactor.getItem_5_2_13_age(),
                hospitalRiskFactor.getItem_5_3(), hospitalRiskFactor.getItem_5_3_1(),
                hospitalRiskFactor.getItem_5_3_1_age(), hospitalRiskFactor.getItem_5_3_2(),
                hospitalRiskFactor.getItem_5_3_2_age(), hospitalRiskFactor.getItem_5_3_3(),
                hospitalRiskFactor.getItem_5_3_3_age(), hospitalRiskFactor.getItem_5_3_4(),
                hospitalRiskFactor.getItem_5_3_4_age(), hospitalRiskFactor.getArea_dept_id(),
                hospitalRiskFactor.getCommunity_dept_id(), hospitalRiskFactor.getScore());
        log.info("@Dao addRiskFactor End  ");
        return result;
    }


    @Override
    public HospitalRiskFactor findHospitalRiskFactorBySid(String sid) {
        log.info("@dao findHospitalRiskFactorBySid 查询参数 sid = {}", sid);
        String sql = "SELECT t.* from hospital_intestine_risk_factor t WHERE t.sid = ?";
        RowMapper<HospitalRiskFactor> rowMapper = new BeanPropertyRowMapper<HospitalRiskFactor>(
                HospitalRiskFactor.class);

        List<HospitalRiskFactor> hospitalRiskFactorList = jdbc.query(sql, new Object[]{sid}, rowMapper);

        HospitalRiskFactor hospitalRiskFactor = null;
        if (hospitalRiskFactorList != null && hospitalRiskFactorList.size() > 0) {
            hospitalRiskFactor = hospitalRiskFactorList.get(0);
        }
        return hospitalRiskFactor;
    }

    @Override
    public void updateTodoEventByType(String sid) {
        log.info("@Dao updateTodoEventByType 更新待办：参数 sid={} ", sid);
        String sql = "UPDATE hospital_todo_event SET status = 2,update_time=now() WHERE type = 1 and data_id = (SELECT ir.id as id from hospital_intestine_review ir WHERE ir.sid = ?)";
        @SuppressWarnings("unused")
        int result = jdbc.update(sql, sid);
        log.info("@DaoImpl 【待办状态-更新】 sql={}", sql);
        log.info("@Dao updateTodoEventByType End  ");
    }

    @Override
    public Integer getIdByLoginName(String loginName) {
        log.info("@dao getIdByLoginName 查询参数 loginName = {}.", loginName);
        String sql = "SELECT r.id FROM itsys_user r WHERE r.loginName = ? ";
        List<Integer> list = jdbc.queryForList(sql, new Object[]{loginName}, Integer.class);
        if (list == null || list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public Integer getIdByUserId(Integer userId) {
        log.info("@dao getIdByUserId 查询参数 userId = {}.", userId);
        String sql = "SELECT r.deptId FROM itsys_department_member r WHERE r.userId = ? ";
        List<Integer> list = jdbc.queryForList(sql, new Object[]{userId}, Integer.class);
        if (list == null || list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public Integer getAreaIdBydepId(Integer depMebId) {
        log.info("@dao getAreaIdBydepId 查询参数 depMebId = {}.", depMebId);
        String sql = "SELECT r.pid FROM itsys_department r WHERE r.id = ? ";
        List<Integer> list = jdbc.queryForList(sql, new Object[]{depMebId}, Integer.class);
        if (list == null || list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public void addGroupRecord(Integer areaId, Long personNumber) {
        log.info("@dao addGroupRecord 新增记录参数 areaId = {},personNumber={}.", areaId, personNumber);
        String sql = "INSERT INTO hospital_group_record(dept_id,person_order) values(?,?)";
        jdbc.update(sql, new Object[]{areaId, personNumber});
    }

    @Override
    public String getGroupRuleByPNumber(int personNumber, Integer groupRuleId) {
        log.info("@dao getGroupRuleByPNumber 查询参数 personNumber = {}.", personNumber);
        String sql = "SELECT r.group FROM hospital_group_rule  r WHERE r.order = ? AND r.ruleid = ?";
        List<String> list = jdbc.queryForList(sql, new Object[]{personNumber, groupRuleId}, String.class);
        if (list == null || list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public Integer getSiteIdByAreaId(Integer areaId) {
        log.info("@dao getSiteIdByAreaId 查询参数 areaId = {}.", areaId);
        String sql = "SELECT r.screeningType FROM itsys_department r WHERE r.id = ? ";
        List<Integer> list = jdbc.queryForList(sql, new Object[]{areaId}, Integer.class);
        if (list == null || list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    //zhaoli    添加省市县等6个字段
    @Override
    public int addReview(final HospitalReview review, String table) {
        log.info("@dao addReview 新增资格审核参数 review={}.", review.toString());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final String sql = "INSERT INTO " + table
                + "(name,phone,community_dept_id,area_dept_id,sex,id_card,sid,age,birth_date,item1,item2,item3, "
                + " item4,item5,item6,item7,item8,item9,item10,investigator,survey_date,reviewer, "
                + " address,in_group_date,`group`,group_status,off_group_reason,stage_cy,review_status,risk_factor_status,overall_status_cy,site_id,create_user,date_created,update_time,province,city,area,township,village,city_other) "
                + " values " + "(?,?,?,?,?,?,?,?,?,?" + ",?,?,?,?,?,?,?,?,?,?,"
                + "?,?,?,?,?,?,?,?,?,?,?,?,?,now(),now(),?,?,?,?,?,?)";
        jdbc.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setObject(1, review.getName());
                ps.setObject(2, review.getPhone());
                ps.setObject(3, review.getCommunityDeptId());
                ps.setObject(4, review.getAreaDeptId());
                ps.setObject(5, review.getSex());
                ps.setObject(6, review.getIdCard());
                ps.setObject(7, review.getSid());
                ps.setObject(8, review.getAge());
                ps.setObject(9, review.getBirthDate());
                ps.setObject(10, review.getItem1());
                ps.setObject(11, review.getItem2());
                ps.setObject(12, review.getItem3());
                ps.setObject(13, review.getItem4());
                ps.setObject(14, review.getItem5());
                ps.setObject(15, review.getItem6());
                ps.setObject(16, review.getItem7());
                ps.setObject(17, review.getItem8());
                ps.setObject(18, review.getItem9());
                ps.setObject(19, review.getItem10());
                ps.setObject(20, review.getInvestigator());
                ps.setObject(21, review.getSurveyDate());
                ps.setObject(22, review.getReviewer());
                ps.setObject(23, review.getAddress());
                ps.setObject(24, review.getInGroupDate());
                ps.setObject(25, review.getGroup());
                ps.setObject(26, review.getGroupStatus());
                ps.setObject(27, review.getOffGroupReason());
                ps.setObject(28, review.getStageCy());
                ps.setObject(29, review.getReviewStatus());
                ps.setObject(30, review.getRiskFactorStatus());
                ps.setObject(31, review.getOverallStatusCy());
                ps.setObject(32, review.getSiteId());
                ps.setObject(33, review.getCreateUser());
                ps.setObject(34, review.getProvince());
                ps.setObject(35, review.getCity());
                ps.setObject(36, review.getArea());
                ps.setObject(37, review.getTownship());
                ps.setObject(38, review.getVillage());
                ps.setObject(39, review.getCityOther());
                return ps;
            }

        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public int addColonoscopyResult(final HospitalColonoscopyResult result) {
        log.info("@dao addColonoscopyResult 新增记录参数 result={}.", result.toString());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final String sql = "INSERT INTO hospital_colonoscopy_result(sid,stage,survey_date,date_created,update_time) values(?,?,?,now(),now())";
        jdbc.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setObject(1, result.getSid());
                ps.setObject(2, result.getStage());
                ps.setObject(3, result.getSurveyDate());
                return ps;
            }

        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void addTodoEvent(HtEvent todo) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("INSERT INTO hospital_todo_event(type,community_dept_id,area_dept_id,sid,status,data_id");
        if (!StringUtils.isEmpty(todo.getOperationSource())) {
            stringBuffer.append(",operation_source,operation_source_id");
        }

        stringBuffer.append(",date_created,update_time)");
        stringBuffer.append(" VALUES(?,?,?,?,?,?,");
        if (!StringUtils.isEmpty(todo.getOperationSource())) {
            stringBuffer.append("?,?,");
        }
        stringBuffer.append("now(),now())");
        //String sql = "INSERT INTO hospital_todo_event(type,community_dept_id,area_dept_id,sid,status,data_id,date_created,update_time)"
        //		+ " VALUES(?,?,?,?,?,?,now(),now())";
        if (!StringUtils.isEmpty(todo.getOperationSource())) {
            jdbc.update(stringBuffer.toString(), todo.getType(), todo.getCommunityDeptId(),
                    todo.getAreaDeptId(), todo.getSid(), todo.getStatus(),
                    todo.getDataId(), todo.getOperationSource(), todo.getOperationSourceId());
        } else {
            jdbc.update(stringBuffer.toString(), todo.getType(), todo.getCommunityDeptId(),
                    todo.getAreaDeptId(), todo.getSid(), todo.getStatus(),
                    todo.getDataId());
        }
/*		String sql = "INSERT INTO hospital_todo_event(community_dept_id,area_dept_id,type,data_id,sid,status,date_created,update_time) values(?,?,?,?,?,?,now(),now())";
		jdbc.update(sql, new Object[] { todo.getCommunityDeptId(), todo.getAreaDeptId(), todo.getType(),
				todo.getDataId(), todo.getSid(), todo.getStatus() });*/
    }

    //zl  添加省市县等相关6个字段的查询操作
    @Override
    public HospitalReview getBySid(String sid) {
        String sql = "select  approval_status as approvalStatus,edit_status as editStatus,apply_status as applyStatus,`id`,`name`,`phone`,`sex`,community_dept_id as communityDeptId,area_dept_id as areaDeptId,id_card as idCard,sid,`age`,birth_date as birthDate,item1,item2,item3,item4,item5,item6,item7,item8,item9,item10,investigator,survey_date as surveyDate,reviewer,address,in_group_date as inGroupDate,`group`,group_status as groupStatus,off_group_reason as offGroupReason,stage_cy as stageCy,review_status as reviewStatus,risk_factor_status as riskFactorStatus,overall_status_cy as overallStatusCy,overall_status_t0 as overallStatusT0, overall_status_t1 as overallStatusT1, overall_status_t2 as overallStatusT2, overall_status_t3 as overallStatusT3,violation_plan_status_cy as violationPlanStatusCy,violation_plan_status_t0 as violationPlanStatusT0,violation_plan_status_t1 as violationPlanStatusT1,violation_plan_status_t2 as violationPlanStatusT2,violation_plan_status_t3 as violationPlanStatusT3,risk_level as riskLevel,site_id as siteId,province,city,area,township,village,city_other as cityOther from hospital_intestine_review where sid=?";
        HospitalReview result = jdbc.queryForObject(sql,
                new BeanPropertyRowMapper<HospitalReview>(HospitalReview.class), sid);
        return result;
    }

    @Override
    public int addStoolDna(final StoolDna dna) {
        log.info("@dao addStoolDna 新增记录参数 dna={}.", dna.toString());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final String sql = "INSERT INTO hospital_stool_dna(sid,stage,code_entry_status,community_dept_id,area_dept_id,dna_code,person_code,release_date,remove_status,operation_source,date_created,update_time,editoperation_source,operation_source_id) values(?,?,?,?,?,?,?,?,?,?,now(),now(),?,?)";
        jdbc.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setObject(1, dna.getSid());
                ps.setObject(2, PERSON_STOOL_DNA_STAGE0);
                ps.setObject(3, StringUtils.isBlank(dna.getDnaCode()) ? Constans.STOOL_CODE_ENTRY_STATUS1
                        : Constans.STOOL_CODE_ENTRY_STATUS2);
                ps.setObject(4, dna.getCommunityDeptId());
                ps.setObject(5, dna.getAreaDeptId());
                ps.setObject(6, dna.getDnaCode());
                ps.setObject(7, dna.getPersonCode());
                ps.setObject(8, dna.getReleaseDate());
                ps.setObject(9, PERSON_ROMOVE_STATUS1);
                ps.setObject(10, dna.getOperationSource());
                ps.setObject(11, dna.getEditoperationSource());
                ps.setObject(12, dna.getOperationSourceId());
                return ps;
            }

        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public int addFitResult(final HospitalFitResult fitResult) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final String sql = "INSERT INTO hospital_fit_result(sid,stage,code_entry_status,community_dept_id,area_dept_id,operation_source,date_created,update_time,editoperation_source,operation_source_id) values(?,?,?,?,?,?,now(),now(),?,?)";
        jdbc.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setObject(1, fitResult.getSid());
                ps.setObject(2, fitResult.getStage());
                ps.setObject(3, fitResult.getCodeEntryStatus());
                ps.setObject(4, fitResult.getCommunityDeptId());
                ps.setObject(5, fitResult.getAreaDeptId());
                ps.setObject(6, fitResult.getOperationSource());
                ps.setObject(7, fitResult.getEditoperationSource());
                ps.setObject(8, fitResult.getOperationSourceId());
                return ps;
            }

        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public int addColonoscopyRecord(final HospitalColonoscopyRecord result) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final String sql = "INSERT INTO hospital_colonoscopy_record(sid,stage,reserve_status,examination_status,finished_status,result_status,pathology_status,notification_entry_status,notification_issue_status,community_dept_id,area_dept_id,allocation_id,reserve_id,reserve_date,reserve_operator,examination_date,examination_operator,result_id,result_date,result_operator,pathology_id,pathology_date,pathology_operator,notification_id,notification_entry_date,notification_entry_operator,notification_issue_date,notification_issue_operator,notification_issue_mode,notification_issue_worker_code,notification_issue_note,source_type,date_created,update_time,reserve_status_date,examination_check_date,editoperation_source,operation_source_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),now(),?,?,?,?)";
        jdbc.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setObject(1, result.getSid());
                ps.setObject(2, result.getStage());
                ps.setObject(3, result.getReserve_status());
                ps.setObject(4, result.getExamination_status());
                ps.setObject(5, result.getFinished_status());
                ps.setObject(6, result.getResult_status());
                ps.setObject(7, result.getPathology_status());
                ps.setObject(8, result.getNotification_entry_status());
                ps.setObject(9, result.getNotification_issue_status());
                ps.setObject(10, result.getCommunity_dept_id());
                ps.setObject(11, result.getArea_dept_id());
                ps.setObject(12, result.getAllocation_id());
                ps.setObject(13, result.getReserve_id());
                ps.setObject(14, result.getReserve_date());
                ps.setObject(15, result.getReserve_operator());
                ps.setObject(16, result.getExamination_date());
                ps.setObject(17, result.getExamination_operator());
                ps.setObject(18, result.getResult_id());
                ps.setObject(19, result.getResult_date());
                ps.setObject(20, result.getResult_operator());
                ps.setObject(21, result.getPathology_id());
                ps.setObject(22, result.getPathology_date());
                ps.setObject(23, result.getPathology_operator());
                ps.setObject(24, result.getNotification_id());
                ps.setObject(25, result.getNotification_entry_date());
                ps.setObject(26, result.getNotification_entry_operator());
                ps.setObject(27, result.getNotification_issue_date());
                ps.setObject(28, result.getNotification_issue_operator());
                ps.setObject(29, result.getNotification_issue_mode());
                ps.setObject(30, result.getNotification_issue_worker_code());
                ps.setObject(31, result.getNotification_issue_note());
                ps.setObject(32, result.getSource_type());
                ps.setObject(33, result.getReserve_status_date());
                ps.setObject(34, result.getExamination_check_date());
                ps.setObject(35, result.getOperationSource());
                ps.setObject(36, result.getOperationSourceId());
                return ps;
            }

        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public ListPageUtil queryPage(HospitalReview person) {
        log.info("@dao queryPage 查询参数 person = {}", person.toString());
        StringBuilder sql = new StringBuilder();
        @SuppressWarnings("rawtypes")
        List args = new ArrayList();
        sql.append(
                "  SELECT ( select  CASE  WHEN dna_check_inform_status=2 THEN dna_check_result ELSE NULL END from  hospital_stool_dna where sid=r.sid order by date_created desc limit 0,1) as dnaResult,(select result from  hospital_fit_result where sid=r.sid order by date_created desc limit 0,1) as fitResult,r.id,r.name,u.nickName,r.phone,r.community_dept_id communityDeptId,d.`name` as depName,"
                        + "r.edit_status as editStatus,r.approval_status as approvalStatus,r.apply_status as applyStatus,r.area_dept_id areaDeptId,r.id_card idCard,r.sid,r.age,r.sex,r.birth_date birthDate,r.item1,"
                        + "r.item2,r.item3,r.item4,r.item5,r.item6,r.item7,r.item8,r.item9,r.item10,r.investigator,"
                        + "r.survey_date surveyDate,r.reviewer,r.address,r.in_group_date inGroupDate,"
                        + "case when r.`group`= 'A' then 'A' when r.`group` = 'B'  then 'B' when r.`group` = 'C' and r.risk_level is null then 'C'   when r.`group` = 'C' and r.risk_level = 1 then 'Cd' when r.`group` = 'C' and  r.risk_level = 2 then 'Cg' ELSE NULL END `group`,"
                        + "r.group_status groupStatus,"
                        + "r.off_group_reason offGroupReason,r.stage_cy stageCy,r.review_status reviewStatus,r.risk_factor_status riskFactorStatus,"
                        + "r.overall_status_cy overallStatusCy,r.overall_status_t0 overallStatusT0,r.overall_status_t1 overallStatusT1,r.overall_status_t2 overallStatusT2,"
                        + "r.overall_status_t3 overallStatusT3,r.violation_plan_status_cy violationPlanStatusCy,r.violation_plan_status_t0 violationPlanStatusT0,"
                        + "r.violation_plan_status_t1 violationPlanStatusT1,r.violation_plan_status_t2 violationPlanStatusT2,r.violation_plan_status_t3 violationPlanStatusT3,"
                        + "r.risk_level riskLevel,r.site_id siteId,r.delete_flag deleteFlag,r.date_created dateCreated,r.update_time updateTime FROM hospital_intestine_review r, "
                        + "itsys_department d , itsys_user u WHERE 1=1 and \n"
                        + "r.community_dept_id=d.id  AND r.create_user = u.loginName  ");

        if (StringUtils.isNotBlank(person.getSid())) {
            sql.append(" AND r.sid like ? ");
            args.add("%" + person.getSid() + "%");
        }

        if (StringUtils.isNotBlank(person.getName())) {
            sql.append(" AND r.name like ? ");
            args.add("%" + person.getName() + "%");
        }

        if (StringUtils.isNotBlank(person.getPhone())) {
            sql.append(" AND r.phone like ? ");
            args.add("%" + person.getPhone() + "%");
        }

        if (StringUtils.isNotBlank(person.getIdCard())) {
            sql.append(" AND r.id_card = ? ");
            args.add(person.getIdCard());
        }

        if (person.getSex() != null) {
            sql.append(" AND r.sex = ? ");
            args.add(person.getSex());
        }

        if (StringUtils.isNotBlank(person.getGroup())) {
            if (person.getGroup().equals("Cg")) {
                sql.append(" AND r.`group` = 'C' and r.risk_level = ? ");
                args.add(2);
            } else if (person.getGroup().equals("Cd")) {
                sql.append(" AND r.`group` = 'C' and r.risk_level = ? ");
                args.add(1);
            } else {
                sql.append(" AND r.`group` = ? ");
                args.add(person.getGroup());
            }
        }

        if (person.getOverallStatusCy() != null) {
            sql.append(" AND r.overall_status_cy = ? ");
            args.add(person.getOverallStatusCy());
        }

        if (person.getViolationPlanStatusCy() != null) {
            sql.append(" AND r.violation_plan_status_cy = ? ");
            args.add(person.getViolationPlanStatusCy());
        }

        if (person.getCommunityDeptId() != null) {
            sql.append(" AND r.community_dept_id = ? ");
            args.add(person.getCommunityDeptId());
        }

        if (person.getAreaDeptId() != null) {
            sql.append(" AND r.area_dept_id = ? ");
            args.add(person.getAreaDeptId());
        }
        if (StringUtils.isNotBlank(person.getLoginName())) {
            sql.append(" and u.loginName = ?");
            args.add(person.getLoginName());
        }
        // if (StringUtils.isNotEmpty(person.getSid())) {
        // sql.append(" AND r.sid = ? ");
        // args.add(person.getSid());
        // }

        if (StringUtils.isNotBlank(person.getInGroupDateStart())) {
            sql.append(" AND r.in_group_date >= ? ");
            args.add(person.getInGroupDateStart() + " 00:00:00 ");
        }

        if (StringUtils.isNotBlank(person.getInGroupDateEnd())) {
            sql.append(" AND r.in_group_date <= ? ");
            args.add(person.getInGroupDateEnd() + " 23:59:59 ");
        }

        if (StringUtils.isNotBlank(person.getSortParameter()) && StringUtils.isNotBlank(person.getSortRule())) {
            sql.append("  ORDER BY  r." + person.getSortParameter() + " " + person.getSortRule().toUpperCase());
        } else {
            sql.append("  ORDER BY  r.date_created DESC ");
        }

        ListPageUtil listPage = new ListPageUtil(sql.toString(), args.toArray(), person.getPageNo(),
                person.getPageSize(), jdbc, null, Constans.SUBQUERY);
        return listPage;
    }

    @Override
    public List<String> getPersonNumByAreaId(Integer areaId) {
        log.info("@dao getPersonNumByAreaId 查询参数 areaId = {}", areaId);
        String sql = "SELECT r.person_order from hospital_group_record r WHERE r.dept_id = ?";
        List<String> list = jdbc.query(sql, new Object[]{areaId}, new BeanPropertyRowMapper<String>(String.class));
        return list;
    }

    @Override
    public void updateGroupRecord(Integer areaId, Long personNumber) {
        log.info("@Dao updateGroupRecord 更新分组纪录数据：参数 areaId={},personNumber={} ", areaId, personNumber);
        String sql = "UPDATE hospital_group_record SET person_order=? WHERE dept_id = ?";
        jdbc.update(sql, personNumber, areaId);
        log.info("@Dao updateGroupRecord End  ");
    }

    /**
     * 更新违反方案状态
     *
     * @param status
     * @param sid
     * @param stageCy
     * @return
     */
    @Override
    public int updateViolationPlanStatus(Integer status, String sid, String stageCy) {
        log.info("@Dao updateViolationPlanStatus 更新违反方案状态：参数 sid={},是否触发违反方案={},触发阶段={}", sid, status, stageCy);
        String sql = "UPDATE hospital_intestine_review SET violation_plan_status_cy=?," + stageCy
                + "=?,update_time=now() WHERE sid = ?";
        int result = jdbc.update(sql, status, status, sid);
        log.info("@Dao updateViolationPlanStatus End  ");
        return result;
    }

    /**
     * 更新总体状态
     *
     * @param status
     * @param sid
     * @param stageCy
     * @return
     */
    @Override
    public int updateOverallStatus(Integer status, String sid, String stageCy) {
        log.info("@Dao updateOverallStatus 更新总体状态：参数 sid={},状态={},触发阶段={}", sid, status, stageCy);
        String sql = "UPDATE hospital_intestine_review SET overall_status_cy=?," + stageCy
                + "=?,update_time=now() WHERE sid = ?";
        int result = jdbc.update(sql, status, status, sid);
        log.info("@Dao updateOverallStatus End  ");
        return result;
    }

    @Override
    public HospitalColonoscopyRecord findRecordByRecordId(Integer id) {
        log.info("@dao findRecordByRecordId 查询参数 id = {}", id);
        String sql = "SELECT t.* from hospital_colonoscopy_record t WHERE t.id = ?";
        List<HospitalColonoscopyRecord> hospitalColonoscopyRecordList = jdbc.query(sql, new Object[]{id},
                new BeanPropertyRowMapper<HospitalColonoscopyRecord>(HospitalColonoscopyRecord.class));
        if (hospitalColonoscopyRecordList != null && hospitalColonoscopyRecordList.size() > 0) {
            return hospitalColonoscopyRecordList.get(0);
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<HospitalReview> queryExcel(HospitalReview person) {
        log.info("@dao queryExcel 查询参数 person = {}", person.toString());
        StringBuilder sql = new StringBuilder();
        List args = new ArrayList();
        sql.append(
                " SELECT r.`sid` ,r.`name`,r.`phone`,r.`age`,r.`sex`,r.`group`,r.overall_status_cy overallStatusCy,r.in_group_date as inGroupDate,r.create_user as createUser FROM hospital_intestine_review r,itsys_user u WHERE 1=1 AND r.create_user = u.loginName ");

        if (StringUtils.isNotBlank(person.getSid())) {
            sql.append(" and r.sid like ? ");
            args.add("%" + person.getSid() + "%");
        }

        if (StringUtils.isNotBlank(person.getName())) {
            sql.append(" and r.name like ? ");
            args.add("%" + person.getName() + "%");
        }

        if (StringUtils.isNotBlank(person.getPhone())) {
            sql.append(" and r.phone = ? ");
            args.add(person.getPhone());
        }

        if (StringUtils.isNotBlank(person.getIdCard())) {
            sql.append(" and r.id_card = ? ");
            args.add(person.getIdCard());
        }

        if (person.getSex() != null) {
            sql.append(" and r.sex = ? ");
            args.add(person.getSex());
        }

        if (StringUtils.isNotBlank(person.getGroup())) {
            sql.append(" and r.group = ? ");
            args.add(person.getGroup());
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

        if (StringUtils.isNotEmpty(person.getSid())) {
            sql.append(" and r.sid = ? ");
            args.add(person.getSid());
        }

        if (StringUtils.isNotBlank(person.getInGroupDateStart())) {
            sql.append(" and r.in_group_date >= ? ");
            args.add(person.getInGroupDateStart() + " 00:00:00");
        }

        if (StringUtils.isNotBlank(person.getInGroupDateEnd())) {
            sql.append(" and r.in_group_date <= ? ");
            args.add(person.getInGroupDateEnd() + " 23:59:59");
        }
        if (StringUtils.isNotBlank(person.getLoginName())) {
            sql.append(" and u.loginName = ?");
            args.add(person.getLoginName());
        }
        sql.append("order by r.date_created desc");
        List<HospitalReview> list = jdbc.query(sql.toString(), args.toArray(),
                new BeanPropertyRowMapper<HospitalReview>(HospitalReview.class));
        return list;
    }

    @Override
    public List<HospitalReview> queryBySidAreaDeptId(String sid, Integer area_dept_id, Integer community_dept_id,String userName) {
        String sql = "select * from hospital_intestine_review  where sid=? and  area_dept_id=? and  community_dept_id=?";// +id+"
        // and
        // sid='"+sid+"'"
        if(!StringUtils.isEmpty(userName)){
            sql+=" and create_user=? ";
            return jdbc.query(sql, new BeanPropertyRowMapper<HospitalReview>(HospitalReview.class),
                    sid, area_dept_id, community_dept_id,userName);
        }

        return jdbc.query(sql, new BeanPropertyRowMapper<HospitalReview>(HospitalReview.class),
                sid, area_dept_id, community_dept_id);
    }

    @Override
    public List<HospitalReview> queryAreaBySid(String sid, Integer area_dept_id) {
        String sql = "select * from hospital_intestine_review  where sid=? and  area_dept_id=? ";// +id+"
        // and
        // sid='"+sid+"'"
        List<HospitalReview> result = jdbc.query(sql, new BeanPropertyRowMapper<HospitalReview>(HospitalReview.class),
                sid, area_dept_id);
        // log.info(sql);
        return result;
    }

    @Override
    public List<HospitalReview> queryByAreaId(String sid, Integer area_dept_id) {
        String sql = "select * from hospital_intestine_review  where sid=? and  area_dept_id=?";// +id+"
        // and
        // sid='"+sid+"'"
        List<HospitalReview> result = jdbc.query(sql, new BeanPropertyRowMapper<HospitalReview>(HospitalReview.class),
                sid, area_dept_id);
        // log.info(sql);
        return result;
    }

    @Override
    public List<IdentityBlacklistPO> checkIdentityBlacklist(String ident, String type) {
        String sql = "select * from identity_blacklist  where identity=? and  type=?";// +id+"
        // and
        // sid='"+sid+"'"
        List<IdentityBlacklistPO> result = jdbc.query(sql,
                new BeanPropertyRowMapper<IdentityBlacklistPO>(IdentityBlacklistPO.class), ident, type);
        // log.info(sql);
        return result;
    }

    @Override
    public ListPageUtil queryQuitLog(QuitSearchDto quitSearchDto, ItsysDepartment itsysDepartment,
                                     Integer HOSPITAL_TYPE) {
        log.info("@dao queryQuitLog 查询参数 quitSearchDto = {}", quitSearchDto.toString());

        StringBuilder Bigsql = new StringBuilder();
        /*
         * Bigsql.append(
         * "SELECT B2.`name` AS areaName, u.nickName ,B1.* FROM ( SELECT A1.*, A3.`name` AS communityName, A2.id AS D2_id , A2.update_time as 'D2_update_time'  FROM ( "
         * );
         */
        Bigsql.append(
                "SELECT B2.`name` AS areaName, u.nickName ,B1.* FROM ( SELECT A1.*, A3.`name` AS communityName, A2.id AS D2_id , A2.update_time as 'D2_update_time', A2.scheme_type, A2.entry_status  FROM ( ");

        StringBuilder sql = new StringBuilder();
        @SuppressWarnings("rawtypes")
        List args = new ArrayList();
        sql.append(
                "SELECT DATE_FORMAT(t2.again_group_date,'%Y-%m-%d') as again_group_date ,t2.id AS qiut_log_id , t1.`name`, t1.phone, t1.age, t1.sex,t1.create_user, "
                        + " case when t1.`group`= 'A' then 'A' when t1.`group` = 'B'  then 'B' when t1.`group` = 'C' and t1.risk_level is null then 'C'   when t1.`group` = 'C' and t1.risk_level = 1 then 'Cd' when t1.`group` = 'C' and  t1.risk_level = 2 then 'Cg' ELSE NULL END `group`, "
                        + "t1.risk_level,t1.overall_status_cy ,  t1.in_group_date, t2.quit_date, t2.off_group_reason, t2.sid ,t1.area_dept_id,  t1.community_dept_id "
                        + " FROM hospital_intestine_review AS t1 , hospital_intestine_review_quit_log AS t2,itsys_user u WHERE t1.sid = t2.sid and t1.create_user = u.loginName");

        if (StringUtils.isNotBlank(quitSearchDto.getSid())) {
            sql.append(" and t1.sid like ? ");
            args.add("%" + quitSearchDto.getSid() + "%");
        }

        if (StringUtils.isNotBlank(quitSearchDto.getName())) {
            sql.append(" and t1.name like ? ");
            args.add("%" + quitSearchDto.getName() + "%");
        }

        if (quitSearchDto.getCommunityDeptId() != null) {
            sql.append(" and t1.community_dept_id = ? ");
            args.add(quitSearchDto.getCommunityDeptId());
        }

        if (quitSearchDto.getOverallStatusCy() != null) {
            sql.append(" and t1.overall_status_cy = ? ");
            args.add(quitSearchDto.getOverallStatusCy());
        }

        if (quitSearchDto.getAreaDeptId() != null) {
            sql.append(" and t1.area_dept_id = ? ");
            args.add(quitSearchDto.getAreaDeptId());
        }

        if (StringUtils.isNotBlank(quitSearchDto.getPhone())) {
            sql.append(" and t1.phone like ? ");
            args.add("%" + quitSearchDto.getPhone() + "%");
        }

        if (StringUtils.isNotBlank(quitSearchDto.getGroup())) {
            if (quitSearchDto.getGroup().equals("Cg")) {
                sql.append(" and t1.`group` = 'C' and t1.risk_level = 2");
            } else if (quitSearchDto.getGroup().equals("Cd")) {
                sql.append(" and t1.`group` = 'C' and t1.risk_level = 1");
            } else {
                sql.append(" and t1.`group` = ? ");
                args.add(quitSearchDto.getGroup());
            }
        }

        if (itsysDepartment.getId() != null) {
            if (Objects.equals(HOSPITAL_TYPE, Constans.DEP_HOSPITAL_TYPE1)) {
                sql.append(" and t1.community_dept_id = ? ");
                args.add(itsysDepartment.getId());
            } else if (Objects.equals(HOSPITAL_TYPE, Constans.DEP_HOSPITAL_TYPE2)) {
                sql.append(" and t1.area_dept_id = ? ");
                args.add(itsysDepartment.getId());
            }
        }

        //
        // if (StringUtils.isNotEmpty(quitSearchDto.getSid())) {
        // sql.append(" and t2.sid = ? ");
        // args.add(quitSearchDto.getSid());
        // }

        if (StringUtils.isNotBlank(quitSearchDto.getInGroupDateStart())) {
            sql.append(" and t1.in_group_date >= ? ");
            args.add(quitSearchDto.getInGroupDateStart() + " 00:00:00");
        }

        if (StringUtils.isNotBlank(quitSearchDto.getInGroupDateEND())) {
            sql.append(" and t1.in_group_date <= ? ");
            args.add(quitSearchDto.getInGroupDateEND() + " 23:59:59");
        }

        if (StringUtils.isNotBlank(quitSearchDto.getQuitGroupDateStart())) {
            sql.append(" and t2.quit_date >= ? ");
            args.add(quitSearchDto.getQuitGroupDateStart());
        }

        if (StringUtils.isNotBlank(quitSearchDto.getQuitGroupDateEnd())) {
            sql.append(" and t2.quit_date <= ? ");
            args.add(quitSearchDto.getQuitGroupDateEnd());
        }

        if (StringUtils.isNotBlank(quitSearchDto.getOffGroupReason())) {
            sql.append(" and t2.off_group_reason like ? ");
            args.add("%" + quitSearchDto.getOffGroupReason() + "%");
        }
        if (StringUtils.isNotBlank(quitSearchDto.getLoginName())) {
            sql.append(" and u.loginName = ?");
            args.add(quitSearchDto.getLoginName());
        }

        sql.append(" ORDER BY t2.date_created DESC");

        Bigsql.append(sql);

        Bigsql.append(
                " ) A1 LEFT JOIN hospital_violation_scheme AS A2 ON A1.qiut_log_id = A2.quit_log_id LEFT JOIN itsys_department AS A3 ON A3.id = A1.community_dept_id ");

        // 修改是否已录入条件判断方式
        /*
         * if (null != quitSearchDto.getInD2Status() &&
         * quitSearchDto.getInD2Status().equals(Constans.
         * PERSON_QUIT_LOG_D2_IN_STATUS_0)) { Bigsql.append(
         * " WHERE A2.quit_log_id is NULL"); } else if (null !=
         * quitSearchDto.getInD2Status() &&
         * quitSearchDto.getInD2Status().equals(Constans.
         * PERSON_QUIT_LOG_D2_IN_STATUS_1)) { Bigsql.append(
         * " WHERE A2.quit_log_id is NOT NULL"); }
         */

        if (null != quitSearchDto.getInD2Status()
                && quitSearchDto.getInD2Status().equals(Constans.PERSON_QUIT_LOG_D2_IN_STATUS_0)) {
            Bigsql.append(" WHERE A2.entry_status = ?");
            args.add("2");
        } else if (null != quitSearchDto.getInD2Status()
                && quitSearchDto.getInD2Status().equals(Constans.PERSON_QUIT_LOG_D2_IN_STATUS_1)) {
            Bigsql.append(" WHERE A2.entry_status = ?");
            args.add("1");
        }
        // 修改是否已录入条件判断方式
        Bigsql.append(
                ") B1 LEFT JOIN itsys_department B2 ON B2.id = B1.area_dept_id  LEFT JOIN itsys_user u ON B1.create_user = u.loginName ");
        log.info(Bigsql.toString());
        return new ListPageUtil(Bigsql.toString(), args.toArray(), quitSearchDto.getPageNo(),
                quitSearchDto.getPageSize(), jdbc, null);
    }

    @Override
    public List<HospitalBiologicalSampleResultVo> findSampleBySid(String sid) {
        String sql = "select * from hospital_biological_sample_result  where sid=? ";// +id+"
        // and
        // sid='"+sid+"'"
        return jdbc.query(sql,
                new BeanPropertyRowMapper<HospitalBiologicalSampleResultVo>(HospitalBiologicalSampleResultVo.class),
                sid);
    }

    @Override
    public List getHospitalReviewBySid(String sid) {
        String sql = "select * from hospital_intestine_review where sid=?";
        List result = jdbc.queryForList(sql, new Object[]{sid});
        return result;
    }

    @Override
    public ListPageUtil queryQuitList(QuitSearchDto quitSearchDto, ItsysDepartment itsysDepartment,
                                      Integer HOSPITAL_TYPE) {
        log.info("@dao queryQuitList 查询参数 quitSearchDto = {}", quitSearchDto.toString());

        StringBuilder Bigsql = new StringBuilder();
        Bigsql.append(
                "SELECT B2.`name` AS areaName, u.nickName ,B1.* FROM ( SELECT A1.*, A3.`name` AS communityName FROM ( ");

        StringBuilder sql = new StringBuilder();
        @SuppressWarnings("rawtypes")
        List args = new ArrayList();
        sql.append(
                "SELECT t3.approval_status as approvalStatus,t3.edit_status as editStatus,t3.apply_status as applyStatus,t3.quit_log_id as qiut_log_id, t1.`name`, t1.phone, t1.age, t1.sex,t1.create_user, t3.id AS D2_id, t3.update_time AS 'D2_update_time', t3.entry_status, t3.scheme_type,"
                        + " case when t1.`group`= 'A' then 'A' when t1.`group` = 'B'  then 'B' when t1.`group` = 'C' and t1.risk_level is null then 'C'   when t1.`group` = 'C' and t1.risk_level = 1 then 'Cd' when t1.`group` = 'C' and  t1.risk_level = 2 then 'Cg' ELSE NULL END `group`, "
                        + "t1.risk_level,t1.overall_status_cy ,  t1.in_group_date, t3.sid, t1.area_dept_id,  t1.community_dept_id "
                        + " FROM hospital_intestine_review AS t1 , hospital_violation_scheme t3,itsys_user u WHERE t1.sid = t3.sid and t1.create_user = u.loginName");

        if (StringUtils.isNotBlank(quitSearchDto.getSid())) {
            sql.append(" and t1.sid like ? ");
            args.add("%" + quitSearchDto.getSid() + "%");
        }

        if (StringUtils.isNotBlank(quitSearchDto.getName())) {
            sql.append(" and t1.name like ? ");
            args.add("%" + quitSearchDto.getName() + "%");
        }

        if (quitSearchDto.getCommunityDeptId() != null) {
            sql.append(" and t1.community_dept_id = ? ");
            args.add(quitSearchDto.getCommunityDeptId());
        }

        if (quitSearchDto.getOverallStatusCy() != null) {
            sql.append(" and t1.overall_status_cy = ? ");
            args.add(quitSearchDto.getOverallStatusCy());
        }

        if (quitSearchDto.getAreaDeptId() != null) {
            sql.append(" and t1.area_dept_id = ? ");
            args.add(quitSearchDto.getAreaDeptId());
        }

        if (StringUtils.isNotBlank(quitSearchDto.getPhone())) {
            sql.append(" and t1.phone like ? ");
            args.add("%" + quitSearchDto.getPhone() + "%");
        }

        if (StringUtils.isNotBlank(quitSearchDto.getGroup())) {
            if (quitSearchDto.getGroup().equals("Cg")) {
                sql.append(" and t1.`group` = 'C' and t1.risk_level = 2");
            } else if (quitSearchDto.getGroup().equals("Cd")) {
                sql.append(" and t1.`group` = 'C' and t1.risk_level = 1");
            } else {
                sql.append(" and t1.`group` = ? ");
                args.add(quitSearchDto.getGroup());
            }
        }

        if (itsysDepartment.getId() != null) {
            if (Objects.equals(HOSPITAL_TYPE, Constans.DEP_HOSPITAL_TYPE1)) {
                sql.append(" and t1.community_dept_id = ? ");
                args.add(itsysDepartment.getId());
            } else if (Objects.equals(HOSPITAL_TYPE, Constans.DEP_HOSPITAL_TYPE2)) {
                sql.append(" and t1.area_dept_id = ? ");
                args.add(itsysDepartment.getId());
            }
        }

        if (StringUtils.isNotBlank(quitSearchDto.getInGroupDateStart())) {
            sql.append(" and t1.in_group_date >= ? ");
            args.add(quitSearchDto.getInGroupDateStart() + " 00:00:00");
        }

        if (StringUtils.isNotBlank(quitSearchDto.getInGroupDateEND())) {
            sql.append(" and t1.in_group_date <= ? ");
            args.add(quitSearchDto.getInGroupDateEND() + " 23:59:59");
        }

        if (StringUtils.isNotBlank(quitSearchDto.getLoginName())) {
            sql.append(" and u.loginName = ?");
            args.add(quitSearchDto.getLoginName());
        }

        sql.append(" ORDER BY t3.date_created DESC");

        /* sql.append(" ORDER BY t2.date_created DESC"); */

        Bigsql.append(sql);

        Bigsql.append(" ) A1 LEFT JOIN itsys_department AS A3 ON A3.id = A1.community_dept_id ");

        Bigsql.append(
                ") B1 LEFT JOIN itsys_department B2 ON B2.id = B1.area_dept_id  LEFT JOIN itsys_user u ON B1.create_user = u.loginName ");

        return new ListPageUtil(Bigsql.toString(), args.toArray(), quitSearchDto.getPageNo(),
                quitSearchDto.getPageSize(), jdbc, null);
    }

    @Override
    public Integer saveScheme(final HospitalReview hospitalReviews, final QuitInResultDto quitResultDtos) {
        final String sql = "insert into hospital_violation_scheme(sid,stage,quit_log_id,scheme_type,area_dept_id,community_dept_id,date_created,update_time) values(?,?,?,?,?,?,now(),now())";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        // return jdbc.update(sql,new Object[]{hospitalReviews.getSid(),
        // hospitalReviews.getStageCy(),
        // quitResultDtos.getQuitLogId(),hospitalReviews.getAreaDeptId(),hospitalReviews.getCommunityDeptId()});
        // KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
                ps.setObject(1, hospitalReviews.getSid());
                ps.setObject(2, hospitalReviews.getStageCy());
                ps.setObject(3, quitResultDtos.getQuitLogId());
                ps.setObject(4, Constans.PERSON_QUIT_SCHEME_TYPE1);
                ps.setObject(5, hospitalReviews.getAreaDeptId());
                ps.setObject(6, hospitalReviews.getCommunityDeptId());
                return ps;
            }
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public List<HospitalReview> queryBySidIDAndAreaDeptId(String sid, Integer area_dept_id) {
        String sql = "select * from hospital_intestine_review  where sid=? and  area_dept_id=?";
        List<HospitalReview> result = jdbc.query(sql, new BeanPropertyRowMapper<HospitalReview>(HospitalReview.class),
                sid, area_dept_id);
        return result;
    }

    //  zhaoli  添加  省市县等6个字段信息
    @Override
    public void updateReview(HospitalReview review, String hospital_intestine_review) {
        log.info("@Dao updateReview 更新受试者 sid={} ", review);
        String sql = "UPDATE " + hospital_intestine_review + " SET name = ?,phone=?,sex=?,id_card=?,age=?,birth_date=?,item1=?,item2=?,item3=?,item4=?,item5=?,item6=?,item7=?,item8=?,item9=?,item10=?,investigator=?,survey_date=?,reviewer=?,address=?,update_time=now(),apply_status=?,edit_status=?,approval_status=?,province=?,city=?,area=?,township=?,village=?,city_other=? WHERE id=? ";
        int result = jdbc.update(sql, review.getName(), review.getPhone(), review.getSex(), review.getIdCard(), review.getAge(), review.getBirthDate(), review.getItem1(), review.getItem2(), review.getItem3(), review.getItem4(), review.getItem5(), review.getItem6(), review.getItem7(), review.getItem8(), review.getItem9(), review.getItem10(), review.getInvestigator(), review.getSurveyDate(), review.getReviewer(), review.getAddress(), review.getApplyStatus(), review.getEditStatus(), review.getApprovalStatus(),review.getProvince(),review.getCity(),review.getArea(),review.getTownship(),review.getVillage(),review.getCityOther(), review.getId());
    }

    @Override
    public List<HospitalRiskFactorDto> findRiskfactorById(Integer id) {
        String sql = "select * from hospital_intestine_risk_factor  where id=?";
        return jdbc.query(sql, new BeanPropertyRowMapper<HospitalRiskFactorDto>(HospitalRiskFactorDto.class), id);
    }

    @Override
    public List<HospitalColonoscopyRecord> queryRecordByIdAndOperation(Integer id, String cgSystemSaveHospitalIntestineReview) {
        String sql = "select * from hospital_colonoscopy_record  where editoperation_source=? and operation_source_id=?";
        return jdbc.query(sql, new BeanPropertyRowMapper<HospitalColonoscopyRecord>(HospitalColonoscopyRecord.class), cgSystemSaveHospitalIntestineReview, id);
    }

    @Override
    public int deleteEventBySourceIdAndType(Integer id, String cgSystemSaveHospitalIntestineReview, Integer personTodoEventType5) {
        String sql = "UPDATE  hospital_todo_event SET status = ?,update_time=now() WHERE operation_source=? and operation_source_id=? and type=? and status=1";
        int result = jdbc.update(sql, Constans.PERSON_TODO_EVENT_STATUS3, cgSystemSaveHospitalIntestineReview, id, personTodoEventType5);
        return result;
    }

    @Override
    public int deleeBySourceId(Integer id, String cgSystemSaveHospitalIntestineReview, String table) {
        String sql = "DELETE FROM " + table + " where editoperation_source=? and operation_source_id=?";
        int result = jdbc.update(sql, cgSystemSaveHospitalIntestineReview, id);
        return result;
    }

    @Override
    public List<StoolDna> queryDNAByIdAndOperation(Integer id, String cgSystemSaveHospitalIntestineReview) {
        String sql = "select * from hospital_stool_dna  where editoperation_source=? and operation_source_id=?";
        return jdbc.query(sql, new BeanPropertyRowMapper<StoolDna>(StoolDna.class), cgSystemSaveHospitalIntestineReview, id);
    }

    @Override
    public List<HospitalBiologicalSampleResultVo> querySampleByIdAndOperation(Integer id, String cgSystemSaveHospitalIntestineReview) {
        String sql = "select * from hospital_biological_sample_result  where editoperation_source=? and operation_source_id=?";
        return jdbc.query(sql, new BeanPropertyRowMapper<HospitalBiologicalSampleResultVo>(HospitalBiologicalSampleResultVo.class), cgSystemSaveHospitalIntestineReview, id);
    }

    @Override
    public List<HospitalFitResult> queryFitByIdAndOperation(Integer id, String cgSystemSaveHospitalIntestineReview) {
        String sql = "select * from hospital_fit_result  where editoperation_source=? and operation_source_id=?";
        return jdbc.query(sql, new BeanPropertyRowMapper<HospitalFitResult>(HospitalFitResult.class), cgSystemSaveHospitalIntestineReview, id);
    }

    @Override
    public int updateRiskFactor(HospitalRiskFactor hospitalRiskFactor) {

        log.info("@Dao updateRiskFactor Start 修改危险因素数据：{} ", JSONUtils.toJson(hospitalRiskFactor));
        String sql = "UPDATE hospital_intestine_risk_factor set "
                + "`investigator`=?,`survey_date`=?,`reviewer`=?,`investigator_code`=?,`contact_relationship`=?,`contact_cell_phone`=?,`contact_telephone`=?,"
                + "height=?,weight=?,yao_wei=?,education=?,marriage=?,marriage_other=?,item_2_1=?,item_2_1_1=?,item_2_1_2_1=?,item_2_1_2_2=?,item_2_1_2_3=?,item_2_1_2_4=?,"
                + "item_2_1_2_4_other=?,item_2_2=?,item_2_2_1=?,item_2_3=?,item_2_3_1=?,"
                + "item_2_3_2=?,item_2_4=?,item_2_4_1=?,item_2_4_2=?,item_2_4_3=?,item_3_1=?,item_3_1_1=?,item_3_1_2=?,item_3_1_2_1=?,item_3_1_2_2=?,item_3_2=?,"
                + "item_3_2_1=?,item_3_2_2=?,item_3_2_2_1=?,item_3_2_2_2=?,item_3_3=?,item_3_3_years=?,"
                + "item_3_3_1=?,item_3_3_1_1=?,item_3_3_1_2=?,item_4_1=?,item_4_2=?,item_4_3=?,item_4_3_1=?,item_4_4=?,item_4_5=?,item_4_6=?,item_4_7=?,item_4_8=?,"
                + "item_4_9=?,item_4_10=?,item_4_11_1=?,item_4_11_2=?,item_4_11_3=?,item_4_11_4=?,item_4_11_5=?,"
                + "item_4_11_6=?,item_4_11_7=?,item_4_11_8=?,item_4_11_9=?,item_4_12_1=?,item_4_12_2=?,item_4_12_3=?,item_4_12_4=?,item_4_12_5=?,item_4_12_6=?,"
                + "item_4_12_7=?,item_4_12_8=?,item_4_12_9=?,item_4_12_10=?,item_5_1=?,item_5_1_1=?,item_5_1_1_age=?,"
                + "item_5_1_2=?,item_5_1_2_age=?,item_5_1_3=?,item_5_1_3_age=?,item_5_1_4=?,item_5_1_4_age=?,item_5_1_5=?,item_5_1_5_age=?,item_5_1_6=?,"
                + "item_5_1_6_age=?,item_5_1_7=?,item_5_1_7_age=?,item_5_1_8=?,item_5_1_8_age=?,item_5_1_9=?,item_5_1_9_age=?,"
                + "item_5_1_10=?,item_5_1_10_age=?,item_5_2=?,item_5_2_1=?,item_5_2_1_age=?,item_5_2_2=?,item_5_2_2_age=?,item_5_2_3=?,item_5_2_3_age=?,"
                + "item_5_2_4=?,item_5_2_4_age=?,item_5_2_5=?,item_5_2_5_age=?,item_5_2_6=?,item_5_2_6_age=?,item_5_2_7=?,"
                + "item_5_2_7_age=?,item_5_2_8=?,item_5_2_8_age=?,item_5_2_9=?,item_5_2_9_age=?,item_5_2_10=?,item_5_2_10_age=?,item_5_2_11=?,"
                + "item_5_2_11_age=?,item_5_2_12=?,item_5_2_12_age=?,item_5_2_13=?,item_5_2_13_age=?,item_5_3=?,item_5_3_1=?,item_5_3_1_age=?,"
                + "item_5_3_2=?,item_5_3_2_age=?,item_5_3_3=?,item_5_3_3_age=?,item_5_3_4=?,item_5_3_4_age=?,area_dept_id=?,community_dept_id=?"
                + ",update_time=now(),score=?,apply_status=?,edit_status=?,approval_status=? where id=?";
        log.info("@DaoImpl 【危险因素数据-修改全量】 sql={}", sql);
        int result = jdbc.update(sql, hospitalRiskFactor.getInvestigator(),
                hospitalRiskFactor.getSurvey_date(), hospitalRiskFactor.getReviewer(),
                hospitalRiskFactor.getInvestigator_code(), hospitalRiskFactor.getContact_relationship(),
                hospitalRiskFactor.getContact_cell_phone(), hospitalRiskFactor.getContact_telephone(),
                hospitalRiskFactor.getHeight(), hospitalRiskFactor.getWeight(), hospitalRiskFactor.getYao_wei(),
                hospitalRiskFactor.getEducation(), hospitalRiskFactor.getMarriage(),
                hospitalRiskFactor.getMarriage_other(), hospitalRiskFactor.getItem_2_1(),
                hospitalRiskFactor.getItem_2_1_1(), hospitalRiskFactor.getItem_2_1_2_1(),
                hospitalRiskFactor.getItem_2_1_2_2(), hospitalRiskFactor.getItem_2_1_2_3(),
                hospitalRiskFactor.getItem_2_1_2_4(), hospitalRiskFactor.getItem_2_1_2_4_other(),
                hospitalRiskFactor.getItem_2_2(), hospitalRiskFactor.getItem_2_2_1(), hospitalRiskFactor.getItem_2_3(),
                hospitalRiskFactor.getItem_2_3_1(), hospitalRiskFactor.getItem_2_3_2(),
                hospitalRiskFactor.getItem_2_4(), hospitalRiskFactor.getItem_2_4_1(),
                hospitalRiskFactor.getItem_2_4_2(), hospitalRiskFactor.getItem_2_4_3(),
                hospitalRiskFactor.getItem_3_1(), hospitalRiskFactor.getItem_3_1_1(),
                hospitalRiskFactor.getItem_3_1_2(), hospitalRiskFactor.getItem_3_1_2_1(),
                hospitalRiskFactor.getItem_3_1_2_2(), hospitalRiskFactor.getItem_3_2(),
                hospitalRiskFactor.getItem_3_2_1(), hospitalRiskFactor.getItem_3_2_2(),
                hospitalRiskFactor.getItem_3_2_2_1(), hospitalRiskFactor.getItem_3_2_2_2(),
                hospitalRiskFactor.getItem_3_3(), hospitalRiskFactor.getItem_3_3_years(),
                hospitalRiskFactor.getItem_3_3_1(), hospitalRiskFactor.getItem_3_3_1_1(),
                hospitalRiskFactor.getItem_3_3_1_2(), hospitalRiskFactor.getItem_4_1(),
                hospitalRiskFactor.getItem_4_2(), hospitalRiskFactor.getItem_4_3(), hospitalRiskFactor.getItem_4_3_1(),
                hospitalRiskFactor.getItem_4_4(), hospitalRiskFactor.getItem_4_5(), hospitalRiskFactor.getItem_4_6(),
                hospitalRiskFactor.getItem_4_7(), hospitalRiskFactor.getItem_4_8(), hospitalRiskFactor.getItem_4_9(),
                hospitalRiskFactor.getItem_4_10(), hospitalRiskFactor.getItem_4_11_1(),
                hospitalRiskFactor.getItem_4_11_2(), hospitalRiskFactor.getItem_4_11_3(),
                hospitalRiskFactor.getItem_4_11_4(), hospitalRiskFactor.getItem_4_11_5(),
                hospitalRiskFactor.getItem_4_11_6(), hospitalRiskFactor.getItem_4_11_7(),
                hospitalRiskFactor.getItem_4_11_8(), hospitalRiskFactor.getItem_4_11_9(),
                hospitalRiskFactor.getItem_4_12_1(), hospitalRiskFactor.getItem_4_12_2(),
                hospitalRiskFactor.getItem_4_12_3(), hospitalRiskFactor.getItem_4_12_4(),
                hospitalRiskFactor.getItem_4_12_5(), hospitalRiskFactor.getItem_4_12_6(),
                hospitalRiskFactor.getItem_4_12_7(), hospitalRiskFactor.getItem_4_12_8(),
                hospitalRiskFactor.getItem_4_12_9(), hospitalRiskFactor.getItem_4_12_10(),
                hospitalRiskFactor.getItem_5_1(), hospitalRiskFactor.getItem_5_1_1(),
                hospitalRiskFactor.getItem_5_1_1_age(), hospitalRiskFactor.getItem_5_1_2(),
                hospitalRiskFactor.getItem_5_1_2_age(), hospitalRiskFactor.getItem_5_1_3(),
                hospitalRiskFactor.getItem_5_1_3_age(), hospitalRiskFactor.getItem_5_1_4(),
                hospitalRiskFactor.getItem_5_1_4_age(), hospitalRiskFactor.getItem_5_1_5(),
                hospitalRiskFactor.getItem_5_1_5_age(), hospitalRiskFactor.getItem_5_1_6(),
                hospitalRiskFactor.getItem_5_1_6_age(), hospitalRiskFactor.getItem_5_1_7(),
                hospitalRiskFactor.getItem_5_1_7_age(), hospitalRiskFactor.getItem_5_1_8(),
                hospitalRiskFactor.getItem_5_1_8_age(), hospitalRiskFactor.getItem_5_1_9(),
                hospitalRiskFactor.getItem_5_1_9_age(), hospitalRiskFactor.getItem_5_1_10(),
                hospitalRiskFactor.getItem_5_1_10_age(), hospitalRiskFactor.getItem_5_2(),
                hospitalRiskFactor.getItem_5_2_1(), hospitalRiskFactor.getItem_5_2_1_age(),
                hospitalRiskFactor.getItem_5_2_2(), hospitalRiskFactor.getItem_5_2_2_age(),
                hospitalRiskFactor.getItem_5_2_3(), hospitalRiskFactor.getItem_5_2_3_age(),
                hospitalRiskFactor.getItem_5_2_4(), hospitalRiskFactor.getItem_5_2_4_age(),
                hospitalRiskFactor.getItem_5_2_5(), hospitalRiskFactor.getItem_5_2_5_age(),
                hospitalRiskFactor.getItem_5_2_6(), hospitalRiskFactor.getItem_5_2_6_age(),
                hospitalRiskFactor.getItem_5_2_7(), hospitalRiskFactor.getItem_5_2_7_age(),
                hospitalRiskFactor.getItem_5_2_8(), hospitalRiskFactor.getItem_5_2_8_age(),
                hospitalRiskFactor.getItem_5_2_9(), hospitalRiskFactor.getItem_5_2_9_age(),
                hospitalRiskFactor.getItem_5_2_10(), hospitalRiskFactor.getItem_5_2_10_age(),
                hospitalRiskFactor.getItem_5_2_11(), hospitalRiskFactor.getItem_5_2_11_age(),
                hospitalRiskFactor.getItem_5_2_12(), hospitalRiskFactor.getItem_5_2_12_age(),
                hospitalRiskFactor.getItem_5_2_13(), hospitalRiskFactor.getItem_5_2_13_age(),
                hospitalRiskFactor.getItem_5_3(), hospitalRiskFactor.getItem_5_3_1(),
                hospitalRiskFactor.getItem_5_3_1_age(), hospitalRiskFactor.getItem_5_3_2(),
                hospitalRiskFactor.getItem_5_3_2_age(), hospitalRiskFactor.getItem_5_3_3(),
                hospitalRiskFactor.getItem_5_3_3_age(), hospitalRiskFactor.getItem_5_3_4(),
                hospitalRiskFactor.getItem_5_3_4_age(), hospitalRiskFactor.getArea_dept_id(),
                hospitalRiskFactor.getCommunity_dept_id(), hospitalRiskFactor.getScore(), hospitalRiskFactor.getApplyStatus(), hospitalRiskFactor.getEditStatus(), hospitalRiskFactor.getApprovalStatus(), hospitalRiskFactor.getId());
        log.info("@Dao updateRiskFactor End  ");
        return result;

    }

    @Override
    public void updateEdirStatus(String applyEditStatus1, String editStatus1, Object o, Integer id, String table) {
        String sql = "update " + table + " set apply_status= ?, edit_status= ?,approval_status=?  where id=? ";
        jdbc.update(sql, new Object[]{applyEditStatus1, editStatus1, o, id});
    }

    @Override
    public Integer queryByCommunityDeptId(Integer deptId,Integer type ) {
        String sql;
        if (type == 1){
             sql = "select count(*) from hospital_intestine_review where area_dept_id=?";
        }else if (type == 2){
             sql = "select count(*) from hospital_intestine_review where community_dept_id=?";
        }else {
            return 0;
        }
        return jdbc.queryForObject(sql, Integer.class, deptId) + 1;
    }

    @Override
    public int updateSroceRiskFactor(HospitalRiskFactor hospitalRiskFactor) {
        log.info("@Dao updateSroceRiskFactor Start 修改危险因素数据：{} ", JSONUtils.toJson(hospitalRiskFactor));
        String sql = "UPDATE hospital_intestine_risk_factor set update_time=now(),score=? where sid=?";
        log.info("@DaoImpl 【危险因素数据-修改分数】 sql={}", sql);
        int result = jdbc.update(sql, hospitalRiskFactor.getScore(), hospitalRiskFactor.getSid());
        log.info("@Dao updateSroceRiskFactor End  ");
        return result;

    }

    @Override
    public int updateHospitalIntestineReview(HospitalRiskFactor hospitalRiskFactor) {
        log.info("@Dao updateHospitalIntestineReview Start 修改受试者：{} ", JSONUtils.toJson(hospitalRiskFactor));
        String sql = "UPDATE hospital_intestine_review set update_time=now(),score=? where sid=?";
        log.info("@DaoImpl 【危险因素数据-修改分数】 sql={}", sql);
        int result = jdbc.update(sql, hospitalRiskFactor.getScore(), hospitalRiskFactor.getSid());
        log.info("@Dao updateSroceRiskFactor End  ");
        return result;
    }

    @Override
    public void addTodoEventList(final List<HtEvent> todoList) {
        String sql = "INSERT INTO hospital_todo_event(type,community_dept_id,area_dept_id,sid,status,data_id,date_created,update_time)VALUES(?,?,?,?,?,?,now(),now())";
        jdbc.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i)
                    throws SQLException {
                HtEvent arg0 = (HtEvent) todoList.get(i);
                ps.setObject(1, arg0.getType());
                ps.setObject(2, arg0.getCommunityDeptId());
                ps.setObject(3, arg0.getAreaDeptId());
                ps.setObject(4, arg0.getSid());
                ps.setObject(5, arg0.getStatus());
                ps.setObject(6, arg0.getDataId());
            }

            @Override
            public int getBatchSize() {
                // TODO Auto-generated method stub
                return todoList.size();
            }
        });
    }

    @Override
    public ListPageUtil queryReviewByNationIdPageByG(HospitalReview person) {
        // TODO Auto-generated method stub
        log.info("@dao queryReviewByNationIdPageByG 查询参数 person = {}", person.toString());
        StringBuilder sql = new StringBuilder();
        @SuppressWarnings("rawtypes")
        List args = new ArrayList();
        sql.append("select * from (select dna_info.dna_check_result as dnaResult,fit_info.result as fitResult,user_info.*,changjing_info.examination_status as examinationStatus,changjing_info.finished_status as finishedStatus,changjing_info.result_status as resultStatus,dna_info.dna_code as dnaCode,dna_info.dna_check_inform_status as dnaCheckInformStatus,dna_info.code_entry_status as codeEntryStatus,fit_info.fit_code as fitCode,fit_info.insert_status as insertStatus\n" +
                "from (SELECT\n" +
                "r.id, r.name, u.nickName, r.phone\n" +
                "\t, r.community_dept_id AS communityDeptId, d.`name` AS depName, d2.`name` AS areaName, r.area_dept_id AS areaDeptId" +
                "\t, r.sid, r.age, r.sex, r.birth_date AS birthDate, r.item1\n" +
                "\t, r.item2, r.item3, r.item4, r.item5, r.item6\n" +
                "\t, r.item7, r.item8, r.item9, r.item10, r.investigator\n" +
                "\t, r.survey_date AS surveyDate, r.reviewer, r.address, r.in_group_date AS inGroupDate\n" +
                "\t, CASE \n" +
                "\t\tWHEN r.`group` = 'A' THEN 'A'\n" +
                "\t\tWHEN r.`group` = 'B' THEN 'B'\n" +
                "\t\tWHEN r.`group` = 'C'\n" +
                "\t\tAND r.risk_level IS NULL THEN 'C'\n" +
                "\t\tWHEN r.`group` = 'C'\n" +
                "\t\tAND r.risk_level = 1 THEN 'Cd'\n" +
                "\t\tWHEN r.`group` = 'C'\n" +
                "\t\tAND r.risk_level = 2 THEN 'Cg'\n" +
                "\t\tELSE NULL\n" +
                "\tEND AS `group`, r.group_status AS groupStatus, r.off_group_reason AS offGroupReason, r.stage_cy AS stageCy, r.review_status AS reviewStatus\n" +
                "\t, r.risk_factor_status AS riskFactorStatus, r.overall_status_cy AS overallStatusCy, r.overall_status_t0 AS overallStatusT0, r.overall_status_t1 AS overallStatusT1, r.overall_status_t2 AS overallStatusT2\n" +
                "\t, r.overall_status_t3 AS overallStatusT3, r.violation_plan_status_cy AS violationPlanStatusCy, r.violation_plan_status_t0 AS violationPlanStatusT0, r.violation_plan_status_t1 AS violationPlanStatusT1, r.violation_plan_status_t2 AS violationPlanStatusT2\n" +
                "\t, r.violation_plan_status_t3 AS violationPlanStatusT3, r.risk_level AS riskLevel, r.site_id AS siteId, r.delete_flag AS deleteFlag, r.date_created AS dateCreated\n" +
                "\t, r.update_time AS updateTime, r.score AS score\n" +
                "FROM\n" +
                "\thospital_intestine_review r,\n" +
                "\titsys_department d,\n" +
                "\titsys_department d2,\n" +
                "\titsys_user u\n" +
                "WHERE\n" +
                "\t1 = 1 and \n" +
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

        sql.append(" ) as user_info\n" +
                "LEFT JOIN (select tab.sid,tab.examination_status,tab.finished_status,tab.result_status from hospital_colonoscopy_record tab where tab.id in(SELECT MAX(hcr.id) FROM hospital_colonoscopy_record hcr GROUP BY hcr.sid)) as changjing_info on user_info.sid = changjing_info.sid \n" +
                "LEFT JOIN (select sid,dna_code,dna_check_result,dna_check_inform_status,code_entry_status from hospital_stool_dna tab where tab.id in(SELECT MAX(hsd.id) FROM hospital_stool_dna hsd GROUP BY hsd.sid)) as dna_info on user_info.sid = dna_info.sid\n" +
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
            sql.append(" and  user_all.dnaCheckInformStatus=2  and user_all.dnaResult = ? ");
            args.add(person.getDnaResult());
        }

        if(person.getDnaCheckInformStatus()!=null){
            sql.append(" and user_all.dnaCheckInformStatus = ? ");
            args.add(person.getDnaCheckInformStatus());
        }

        //修改宗曈
        if(person.getCodeEntryStatus()!=null){
            //已返回为2 待返回为1
            //社区查询已返回和未返回，根据表中dna_check_inform_status字段，如果=2表示已返回，!=2表示未返回
            if(person.getCodeEntryStatus()==1){
                sql.append("  and user_all.dnaCheckInformStatus != 2 and user_all.codeEntryStatus= 2 ");
            }
            if(person.getCodeEntryStatus()==2){
                sql.append(" and user_all.dnaCheckInformStatus = 2 ");
            }
        }
/*
        if(person.getCodeEntryStatus()!=null){
            sql.append(" and user_all.dnaCheckInformStatus=2 and user_all.codeEntryStatus = ? ");
            args.add(person.getCodeEntryStatus());
        }*/

        if(StringUtils.isNotBlank(person.getSortParameter())&&StringUtils.isNotBlank(person.getSortRule())){
            sql.append("  ORDER BY  user_all."+person.getSortParameter()+" "+person.getSortRule().toUpperCase());
        }else{
            sql.append("  ORDER BY  user_all.dateCreated DESC ");
        }

        ListPageUtil listPage = new ListPageUtil(sql.toString(), args.toArray(), person.getPageNo(), person.getPageSize(), jdbc, null, null);
        return listPage;
    }


    /**
     *根据结果id查询检查信息
     * @param id
     * @return
     */
    @Override
    public HospitalColonoscopyRecord findRecordByResultId(Integer id) {
        log.info("@dao findRecordByResultId 查询参数 id = {}", id);
        String sql = "SELECT t.* from hospital_colonoscopy_record t WHERE t.result_id = ?";
        List<HospitalColonoscopyRecord> hospitalColonoscopyRecordList = jdbc.query(sql, new Object[]{id},
                new BeanPropertyRowMapper<HospitalColonoscopyRecord>(HospitalColonoscopyRecord.class));
        if (hospitalColonoscopyRecordList != null && hospitalColonoscopyRecordList.size() == 1) {
            return hospitalColonoscopyRecordList.get(0);
        } else {
            return null;
        }
    }


    /**
     *根据病理id查询检查信息
     * @param id
     * @return
     */
    @Override
    public HospitalColonoscopyRecord findRecordByPathologyId(Integer id) {
        log.info("@dao findRecordByPathologyId 查询参数 id = {}", id);
        String sql = "SELECT t.* from hospital_colonoscopy_record t WHERE t.pathology_id = ?";
        List<HospitalColonoscopyRecord> hospitalColonoscopyRecordList = jdbc.query(sql, new Object[]{id},
                new BeanPropertyRowMapper<HospitalColonoscopyRecord>(HospitalColonoscopyRecord.class));
        if (hospitalColonoscopyRecordList != null && hospitalColonoscopyRecordList.size() == 1) {
            return hospitalColonoscopyRecordList.get(0);
        } else {
            return null;
        }
    }

    /**
     *根据告知书id查询检查信息
     * @param id
     * @return
     */
    @Override
    public HospitalColonoscopyRecord findRecordByNotificationId(Integer id) {
        log.info("@dao findRecordByNotificationId 查询参数 id = {}", id);
        String sql = "SELECT t.* from hospital_colonoscopy_record t WHERE t.notification_id = ?";
        List<HospitalColonoscopyRecord> hospitalColonoscopyRecordList = jdbc.query(sql, new Object[]{id},
                new BeanPropertyRowMapper<HospitalColonoscopyRecord>(HospitalColonoscopyRecord.class));
        if (hospitalColonoscopyRecordList != null && hospitalColonoscopyRecordList.size() == 1) {
            return hospitalColonoscopyRecordList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public HospitalColonoscopyRecord findRecordById(int communityDeptId) {
        log.info("@dao findRecordById 查询参数 id = {}", communityDeptId);
        String sql = "SELECT t.* from hospital_colonoscopy_record t WHERE t.id = ?";
        List<HospitalColonoscopyRecord> hospitalColonoscopyRecordList = jdbc.query(sql, new Object[]{communityDeptId},
                new BeanPropertyRowMapper<HospitalColonoscopyRecord>(HospitalColonoscopyRecord.class));
        if (hospitalColonoscopyRecordList != null && hospitalColonoscopyRecordList.size() == 1) {
            return hospitalColonoscopyRecordList.get(0);
        } else {
            return null;
        }
    }

}
