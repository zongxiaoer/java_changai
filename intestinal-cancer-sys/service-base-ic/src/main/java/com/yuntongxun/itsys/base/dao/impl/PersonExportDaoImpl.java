/**
 * Project Name:service-base-yl
 * File Name:PersonDaoImpl.java
 * Package Name:com.yuntongxun.itsys.base.dao.impl
 * Date:2018年4月9日下午6:32:38
 * Copyright (c) 2018, ty All Rights Reserved.
 */

package com.yuntongxun.itsys.base.dao.impl;

import com.yuntongxun.itsys.base.common.util.StringUtil;
import com.yuntongxun.itsys.base.dao.PersonExportDao;
import com.yuntongxun.itsys.base.po.dto.ExeclData;
import com.yuntongxun.itsys.base.po.dto.HospitalReviewExport;
import com.yuntongxun.itsys.base.vo.ColonoscopyVo;
import com.yuntongxun.itsys.base.vo.HospitalBiologicalSampleResultVo;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:PersonDaoImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018年4月9日 下午6:32:38 <br/>
 *
 * @author ty
 * @see
 * @since JDK 1.8
 */
@Repository
public class PersonExportDaoImpl implements PersonExportDao {

    private final Logger log = LogManager.getLogger(PersonExportDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbc;

    /**
     * 导出受试者
     * @param person
     * @param level 管理员级别  社区 地区 国家
     * @param depId 社区id 或者地区id  国家查询所有
     * @return
     */
    @Override
    public List<HospitalReviewExport> queryExcel(HospitalReviewExport person,String level,Integer depId,String userId) {
        log.info("@dao queryExcel 查询参数 person = {}", person.toString());
        StringBuilder sql = new StringBuilder();
        List args = new ArrayList();
        sql.append("select DATE_FORMAT(fit_info.result_date,'%Y-%m-%d') as fit_result_date,DATE_FORMAT(changjing_info.resultSurveyDate,'%Y-%m-%d') as resultSurveyDate,DATE_FORMAT(changjing_info.reserve_date,'%Y-%m-%d') as reserve_date,user_info.*,dna_info.dna_code,dna_info.dna_check_result,fit_info.fit_code,DATE_FORMAT(fit_info.code_entry_time,'%Y-%m-%d') as fit_code_entry_time,fit_info.result as fit_result,\n" +
                "case when changjing_info.reserve_status=1 then '未预约' when changjing_info.reserve_status=2 then '已预约' else NULL end as changjing_reserve_status,\n" +
                "case when changjing_info.examination_status=1 then '未检查' when changjing_info.examination_status=2 then '已检查' else NULL end as changjing_examination_status,\n" +
                "case when changjing_info.finished_status=1 then '未完成' when changjing_info.finished_status=2 then '已完成' else NULL end as changjing_finished_status,\n" +
                "case when changjing_info.result_status=1 then '未录入' when changjing_info.result_status=2 then '已录入' else NULL end as changjing_result_status,\n" +
                "case when changjing_info.pathology_status=1 then '未录入' when changjing_info.pathology_status=2 then '已录入' else NULL end as changjing_pathology_status,\n" +
                "case when changjing_info.notification_entry_status=1 then '未录入' when changjing_info.notification_entry_status=2 then '已录入' else NULL end as changjing_notification_entry_status,\n" +
                "case when changjing_info.notification_issue_status=1 then '未发放' when changjing_info.notification_issue_status=2 then '已发放' else NULL end as changjing_notification_issue_status,\n" +
                //2018-8-31 ZT
                "case when changjing_info.item2=1 then '是' when changjing_info.item2!=1 then '否' else NULL end as item2,\n" +
                "case when changjing_info.item3=1 then '是' when changjing_info.item3!=1 then '否'  else NULL end as item3,\n" +
                "case when changjing_info.item1=1 then '是' when changjing_info.item1!=1 then '否'  else NULL end as item1,\n" +

                "case when xueye_info.collect_status=-1 then '未录入' when xueye_info.collect_status=1 then '已录入' when xueye_info.collect_status=2 then '未提供' else NULL end xueye_collect_status,\n" +
                "case when tuoye_info.collect_status=-1 then '未录入' when tuoye_info.collect_status=1 then '已录入' when tuoye_info.collect_status=2 then '未提供' else NULL end tuoye_collect_status,\n" +
                "case when fenbian_info.collect_status=-1 then '未录入' when fenbian_info.collect_status=1 then '已录入' when fenbian_info.collect_status=2 then '未提供' else NULL end fenbian_collect_status,\n" +
                "case when dna_info.code_entry_status=1 then '未录入' when dna_info.code_entry_status=2 then '已录入' else NULL end as dna_code_entry_status,\n" +
                "case when dna_info.dna_check_enter_status=1 then '未录入' when dna_info.dna_check_enter_status=2 then '已录入' else NULL end as dna_gj_dna_check_enter_status,\n" +
                "case when dna_info.dna_check_inform_status=1 then '未通过' when dna_info.dna_check_inform_status=2 then '已通过' when dna_info.dna_check_inform_status=3 then '未审核' else NULL end as dna_dna_check_inform_status,\n" +
                "case when dna_info.dna_community_inform_status=1 then '已发放' when dna_info.dna_community_inform_status=2 then '未发放' else NULL end as dna_dna_community_inform_status,\n" +
                "case when dna_info.dna_check_inform_status=2 then '已返回' when dna_info.dna_check_inform_status!=2 and dna_info.code_entry_status=2 then '待返回' else NULL end as dna_other_dna_check_enter_status,\n" +
                "case when dna_info.dna_check_inform_status=2 then dna_info.dna_check_result else NULL end as no_dna_check_result,\n" +
                "case when fit_info.code_entry_status=1 then '未录入' when fit_info.code_entry_status=2 then '已录入' else NULL end as fit_code_entry_status,\n" +
                "case when fit_info.insert_status=1 then '未录入' when fit_info.insert_status=2 then '已录入' else NULL end as fit_insert_status\n" +
                "from (SELECT\n" +
                "\tr.`sid` as sid,\n" +
                "\tr.`township` as township,\n" +
                "\tr.`province` as province,\n" +
                "\tr.`city` as city,\n" +
                "\tr.`village` as village,\n" +
                "\tr.`city_other` as city_other,\n" +
                "\tr.`area` as area,\n" +
                "\tr.`id_card` as idCard,\n" +
                "\tr.`name` as `name`,\n" +
                "  r.`sex` as sex,\n" +
                "\tr.`age` as age,\n" +
                "\tr.`phone` as phone,\n" +
                "\td2.`name` as areaName,\n" +
                "\td.`name` as commName,\n" +
                "\tu.nickName as nickName,\n" +
                "\tr.address as address,\n" +
                "  case when r.`group`= 'A' then 'A' when r.`group` = 'B'  then 'B' when r.`group` = 'C' and r.risk_level is null then 'C'   when r.`group` = 'C' and r.risk_level = 1 then 'C组低危' when r.`group` = 'C' and  r.risk_level = 2 then 'C组高危' ELSE NULL END `group`,\n" +
                "\tr.overall_status_cy overallStatusCy,\n" +
                "\tDATE_FORMAT(r.in_group_date,'%Y-%m-%d') AS inGroupDate,\n" +
                "  '已录入' as qrsState,\n" +
                "\tcase WHEN r.risk_level>0 then '已录入' else '未录入' end as riskState,r.score as score\n" +
                "FROM\n" +
                "\thospital_intestine_review r,\n" +
                "\titsys_department d,\n" +
                "\titsys_department d2,\n" +
                "\titsys_user u\n" +
                "WHERE\n" +
                "\t1 = 1 and \n" +
                "r.community_dept_id = d.id AND r.area_dept_id = d2.id and r.create_user = u.loginName " );
                if(level!=null&&level.equals("comm")){
                   sql.append(" and r.community_dept_id=? ");
                    args.add(depId);
                }

                if(level!=null&&level.equals("area")){
                    sql.append(" and r.area_dept_id=? ");
                    args.add(depId);
                }
                if(!StringUtils.isEmpty(userId)){
                    sql.append(" and r.create_user=? ");
                    args.add(userId);
                }
                sql.append(" ) as user_info\n" +
                "LEFT JOIN (select tab.*,hcpr.item1,hcpr.item2,hcpr.item3,hcrr.survey_date as resultSurveyDate from hospital_colonoscopy_record tab left join hospital_colonoscopy_pathology_result hcpr on tab.pathology_id=hcpr.id left join hospital_colonoscopy_result hcrr on tab.result_id=hcrr.id where tab.id in(SELECT MAX(hcr.id) FROM hospital_colonoscopy_record hcr GROUP BY hcr.sid)) as changjing_info on user_info.sid = changjing_info.sid " +
                "LEFT JOIN (select sid,collect_status from hospital_biological_sample_result tab where tab.id in(SELECT MAX(hbsr.id) FROM hospital_biological_sample_result hbsr where hbsr.sample_type='A' GROUP BY hbsr.sid)) as xueye_info on user_info.sid = xueye_info.sid\n" +
                "LEFT JOIN (select sid,collect_status from hospital_biological_sample_result tab where tab.id in(SELECT MAX(hbsr.id) FROM hospital_biological_sample_result hbsr where hbsr.sample_type='M' GROUP BY hbsr.sid)) as tuoye_info on user_info.sid = tuoye_info.sid\n" +
                "LEFT JOIN (select sid,collect_status from hospital_biological_sample_result tab where tab.id in(SELECT MAX(hbsr.id) FROM hospital_biological_sample_result hbsr where hbsr.sample_type='F' GROUP BY hbsr.sid)) as fenbian_info on user_info.sid = fenbian_info.sid\n" +
                "LEFT JOIN (select * from hospital_stool_dna tab where tab.id in(SELECT MAX(hsd.id) FROM hospital_stool_dna hsd GROUP BY hsd.sid)) as dna_info on user_info.sid = dna_info.sid\n" +
                "LEFT JOIN (select * from hospital_fit_result tab where tab.id in(SELECT MAX(hfr.id) FROM hospital_fit_result hfr GROUP BY hfr.sid)) as fit_info on user_info.sid = fit_info.sid\n" +
                "ORDER BY user_info.sid\n");
        List<HospitalReviewExport> list = jdbc.query(sql.toString(), args.toArray(), new BeanPropertyRowMapper<HospitalReviewExport>(HospitalReviewExport.class));
        return list;
    }


    /**
     * 国家肠镜导出
     * @param queryCondition
     * @param communityDeptId
     * @param areaDeptId
     * @param deptType
     * @param isPage
     * @return
     */
    @Override
    public List<ColonoscopyVo> queryForChangjingExcel(ColonoscopyVo queryCondition, Integer communityDeptId,
                                                 Integer areaDeptId, Integer deptType, boolean isPage) {
        List<Object> parm = new ArrayList<Object>();
        String sql = "SELECT d.`name` as depName,d1.name as areaName,t1.`id` AS `id`, t1.sid AS sid,"
                + " t2.`name` AS `name`, t2.`sex` AS `sex`, t2.`age` AS `age`, t2.`phone` AS `phone`,"
                + "  case when t2.`group`= 'A' then 'A' when t2.`group` = 'B'  then 'B' when t2.`group` = 'C' and t2.risk_level is null then 'C' when t2.`group` = 'C' and t2.risk_level = 1 then 'Cd' when t2.`group` = 'C' and  t2.risk_level = 2 then 'Cg' ELSE NULL END 'group', "
                + "t2.`in_group_date` AS `inGroupDate`, t2.overall_status_cy AS overallStatusCy, "
                + "t1.reserve_status AS reserveStatus, t1.reserve_date AS reserveDate, "
                + "t1.examination_status AS examinationStatus, t1.finished_status AS finishedStatus, "
                + "t1.examination_date AS examinationDate,t1.notification_entry_status as notificationEntryStatus,"
                + "t1.result_status as resultStatus,t1.pathology_status as pathologyStatus,t1.notification_issue_status AS notificationIssueStatus,"
                + "u.nickName as createUser"
                + " FROM hospital_colonoscopy_record t1, hospital_intestine_review t2,"
                + "itsys_department d,itsys_department d1,itsys_user u  WHERE t2.sid = t1.sid and t2.create_user = u.loginName "
                + " and t2.community_dept_id=d.id and t2.area_dept_id = d1.id and t2.overall_status_cy <> 2 ";

        List<ColonoscopyVo> list = jdbc.query(sql.toString(), parm.toArray(), new BeanPropertyRowMapper<ColonoscopyVo>(ColonoscopyVo.class));
        return list;
    }

    /**
     * 导出生物样本（唾液/粪便）
     * @param areaDeptId
     * @param sampleType 导出类型
     * @return
     */
    @Override
    public List<HospitalBiologicalSampleResultVo> queryForBiologicalSampleExcel(Integer areaDeptId, String sampleType) {
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

        if(sampleType != null){
            sql.append(" and t1.sample_type=? ");
            parm.add(sampleType);
        }
        if(areaDeptId!=null){
            sql.append(" and t1.area_dept_id=? ");
            parm.add(areaDeptId);
        }
        sql.append(" order by t1.date_created desc");

        List<HospitalBiologicalSampleResultVo> list = jdbc.query(sql.toString(), parm.toArray(), new BeanPropertyRowMapper<HospitalBiologicalSampleResultVo>(HospitalBiologicalSampleResultVo.class));
        return list;
    }

    @Override
    public List<HospitalReviewExport> stollnationUsersQueryExcel(ExeclData execlData) {
        log.info("@dao queryExcel 查询参数 person = {}", execlData.toString());
        StringBuilder sql = new StringBuilder();
        List args = new ArrayList();
        sql.append("select DATE_FORMAT(fit_info.result_date,'%Y-%m-%d') as fit_result_date,DATE_FORMAT(changjing_info.resultSurveyDate,'%Y-%m-%d') as resultSurveyDate,DATE_FORMAT(changjing_info.reserve_date,'%Y-%m-%d') as reserve_date,user_info.*,dna_info.dna_code,dna_info.dna_check_result,fit_info.fit_code,DATE_FORMAT(fit_info.code_entry_time,'%Y-%m-%d') as fit_code_entry_time,fit_info.result as fit_result,\n" +
                "case when changjing_info.reserve_status=1 then '未预约' when changjing_info.reserve_status=2 then '已预约' else NULL end as changjing_reserve_status,\n" +
                "case when changjing_info.examination_status=1 then '未检查' when changjing_info.examination_status=2 then '已检查' else NULL end as changjing_examination_status,\n" +
                "case when changjing_info.finished_status=1 then '未完成' when changjing_info.finished_status=2 then '已完成' else NULL end as changjing_finished_status,\n" +
                "case when changjing_info.result_status=1 then '未录入' when changjing_info.result_status=2 then '已录入' else NULL end as changjing_result_status,\n" +
                "case when changjing_info.pathology_status=1 then '未录入' when changjing_info.pathology_status=2 then '已录入' else NULL end as changjing_pathology_status,\n" +
                "case when changjing_info.notification_entry_status=1 then '未录入' when changjing_info.notification_entry_status=2 then '已录入' else NULL end as changjing_notification_entry_status,\n" +
                "case when changjing_info.notification_issue_status=1 then '未发放' when changjing_info.notification_issue_status=2 then '已发放' else NULL end as changjing_notification_issue_status,\n" +
                "case when xueye_info.collect_status=-1 then '未录入' when xueye_info.collect_status=1 then '已录入' when xueye_info.collect_status=2 then '未提供' else NULL end xueye_collect_status,\n" +
                "case when tuoye_info.collect_status=-1 then '未录入' when tuoye_info.collect_status=1 then '已录入' when tuoye_info.collect_status=2 then '未提供' else NULL end tuoye_collect_status,\n" +
                "case when fenbian_info.collect_status=-1 then '未录入' when fenbian_info.collect_status=1 then '已录入' when fenbian_info.collect_status=2 then '未提供' else NULL end fenbian_collect_status,\n" +
                "case when dna_info.code_entry_status=1 then '未录入' when dna_info.code_entry_status=2 then '已录入' else NULL end as dna_code_entry_status,\n" +
                "case when dna_info.dna_check_enter_status=1 then '未录入' when dna_info.dna_check_enter_status=2 then '已录入' else NULL end as dna_gj_dna_check_enter_status,\n" +
                "case when dna_info.dna_check_inform_status=1 then '未通过' when dna_info.dna_check_inform_status=2 then '已通过' when dna_info.dna_check_inform_status=3 then '未审核' else NULL end as dna_dna_check_inform_status,\n" +
                "case when dna_info.dna_community_inform_status=1 then '已发放' when dna_info.dna_community_inform_status=2 then '未发放' else NULL end as dna_dna_community_inform_status,\n" +
                "case when dna_info.dna_check_inform_status=2 then '已返回' when dna_info.dna_check_inform_status!=2 and dna_info.code_entry_status=2 then '待返回' else NULL end as dna_other_dna_check_enter_status,\n" +
                //2018-08-31 zongtong
                "case when changjing_info.item2=1 then '是' when changjing_info.item2!=1 then '否' else NULL end as item2,\n" +
                "case when changjing_info.item3=1 then '是' when changjing_info.item3!=1 then '否'  else NULL end as item3,\n" +
                "case when changjing_info.item1=1 then '是' when changjing_info.item1!=1 then '否'  else NULL end as item1,\n" +

                "case when fit_info.code_entry_status=1 then '未录入' when fit_info.code_entry_status=2 then '已录入' else NULL end as fit_code_entry_status,\n" +
                "case when fit_info.insert_status=1 then '未录入' when fit_info.insert_status=2 then '已录入' else NULL end as fit_insert_status\n" +
                "from (SELECT\n" +
                "\tr.`sid` as sid,\n" +
                "\tr.id_card as idCard,\n" +
                "\tr.`township` as township,\n" +
                "\tr.`province` as province,\n" +
                "\tr.`city` as city,\n" +
                "\tr.`village` as village,\n" +
                "\tr.`city_other` as city_other,\n" +
                "\tr.`area` as area,\n" +
                "\tr.`name` as `name`,\n" +
                "  r.`sex` as sex,\n" +
                "\tr.`age` as age,\n" +
                "\tr.`phone` as phone,\n" +
                "\td2.`name` as areaName,\n" +
                "\td.`name` as commName,\n" +
                "\tu.nickName as nickName,\n" +
                "\tr.address as address,\n" +
                "  case when r.`group`= 'A' then 'A' when r.`group` = 'B'  then 'B' when r.`group` = 'C' and r.risk_level is null then 'C'   when r.`group` = 'C' and r.risk_level = 1 then 'C组低危' when r.`group` = 'C' and  r.risk_level = 2 then 'C组高危' ELSE NULL END `group`,\n" +
                "\tr.overall_status_cy overallStatusCy,\n" +
                "\tDATE_FORMAT(r.in_group_date,'%Y-%m-%d') AS inGroupDate,\n" +
                "  '已录入' as qrsState,\n" +
                "\tcase WHEN r.risk_level>0 then '已录入' else '未录入' end as riskState,r.score as score\n" +
                "FROM\n" +
                "\thospital_intestine_review r,\n" +
                "\titsys_department d,\n" +
                "\titsys_department d2,\n" +
                "\titsys_user u\n" +
                "WHERE\n" +
                "\t1 = 1 and \n" +
                "r.community_dept_id = d.id AND r.area_dept_id = d2.id and r.create_user = u.loginName " );

        if (!StringUtil.isEmpty(execlData.getGroup())) {
            if (execlData.getGroup().equals("Cg")) {
                sql.append(" and r.`group` = 'C' and r.risk_level = ? ");
                args.add(2);
            } else if (execlData.getGroup().equals("Cd")) {
                sql.append(" and r.`group` = 'C' and r.risk_level = ? ");
                args.add(1);
            } else {
                sql.append(" and r.`group` = ? ");
                args.add(execlData.getGroup());
            }
        }

        if (execlData.getInGroupDateStart() != null) {
            sql.append(" and r.in_group_date >= ? ");
            args.add(execlData.getInGroupDateStart());
        }

        if (execlData.getInGroupDateEnd() != null) {
            sql.append(" and r.in_group_date <= ? ");
            args.add(execlData.getInGroupDateEnd());
        }

        if (execlData.getStage_cy() != null) {
            sql.append(" and r.stage_cy = ? ");
            args.add(execlData.getStage_cy());
        }
        if (execlData.getSiteId() != null) {
            sql.append("  AND r.site_id =  ? ");
            args.add(execlData.getSiteId());
        }

        sql.append(" ) as user_info\n" +
                "LEFT JOIN (select tab.*,hcpr.item1,hcpr.item2,hcpr.item3,hcrr.survey_date as resultSurveyDate from hospital_colonoscopy_record tab left join hospital_colonoscopy_pathology_result hcpr on tab.pathology_id=hcpr.id left join hospital_colonoscopy_result hcrr on tab.result_id=hcrr.id where tab.id in(SELECT MAX(hcr.id) FROM hospital_colonoscopy_record hcr GROUP BY hcr.sid)) as changjing_info on user_info.sid = changjing_info.sid " +
                "LEFT JOIN (select sid,collect_status from hospital_biological_sample_result tab where tab.id in(SELECT MAX(hbsr.id) FROM hospital_biological_sample_result hbsr where hbsr.sample_type='A' GROUP BY hbsr.sid)) as xueye_info on user_info.sid = xueye_info.sid\n" +
                "LEFT JOIN (select sid,collect_status from hospital_biological_sample_result tab where tab.id in(SELECT MAX(hbsr.id) FROM hospital_biological_sample_result hbsr where hbsr.sample_type='M' GROUP BY hbsr.sid)) as tuoye_info on user_info.sid = tuoye_info.sid\n" +
                "LEFT JOIN (select sid,collect_status from hospital_biological_sample_result tab where tab.id in(SELECT MAX(hbsr.id) FROM hospital_biological_sample_result hbsr where hbsr.sample_type='F' GROUP BY hbsr.sid)) as fenbian_info on user_info.sid = fenbian_info.sid\n" +
                "LEFT JOIN (select * from hospital_stool_dna tab where tab.id in(SELECT MAX(hsd.id) FROM hospital_stool_dna hsd GROUP BY hsd.sid)) as dna_info on user_info.sid = dna_info.sid\n" +
                "LEFT JOIN (select * from hospital_fit_result tab where tab.id in(SELECT MAX(hfr.id) FROM hospital_fit_result hfr GROUP BY hfr.sid)) as fit_info on user_info.sid = fit_info.sid\n" +
                "ORDER BY user_info.sid\n");
        log.info("sql"+sql.toString());
        if (args.size() > 0) {
            return jdbc.query(sql.toString(), args.toArray(), new BeanPropertyRowMapper<HospitalReviewExport>(HospitalReviewExport.class));
        }
        return  jdbc.query(sql.toString(), args.toArray(), new BeanPropertyRowMapper<HospitalReviewExport>(HospitalReviewExport.class));

    }

    @Override
    public List<ColonoscopyVo> queryFoStollCountryCJExcel(ExeclData execlData) {
        List<Object> parm = new ArrayList<Object>();
        StringBuffer sql=new StringBuffer();
        sql.append("SELECT d.`name` as depName,d1.name as areaName,t1.`id` AS `id`, t1.sid AS sid,");
        sql.append( " t2.`name` AS `name`, t2.`sex` AS `sex`, t2.`age` AS `age`, t2.`phone` AS `phone`,");
        sql.append(  "  case when t2.`group`= 'A' then 'A' when t2.`group` = 'B'  then 'B' when t2.`group` = 'C' and t2.risk_level is null then 'C' when t2.`group` = 'C' and t2.risk_level = 1 then 'Cd' when t2.`group` = 'C' and  t2.risk_level = 2 then 'Cg' ELSE NULL END 'group', ");
        sql.append( "t2.`in_group_date` AS `inGroupDate`, t2.overall_status_cy AS overallStatusCy, ");
        sql.append(  "t1.reserve_status AS reserveStatus, t1.reserve_date AS reserveDate, ");
        sql.append( "t1.examination_status AS examinationStatus, t1.finished_status AS finishedStatus, ");
        sql.append( "t1.examination_date AS examinationDate,t1.notification_entry_status as notificationEntryStatus,");
        sql.append(  "t1.result_status as resultStatus,t1.pathology_status as pathologyStatus,t1.notification_issue_status AS notificationIssueStatus,");
        sql.append( "u.nickName as createUser");
        sql.append( " FROM hospital_colonoscopy_record t1, hospital_intestine_review t2,");
        sql.append( "itsys_department d,itsys_department d1,itsys_user u  WHERE t2.sid = t1.sid and t2.create_user = u.loginName ");
        sql.append( " and t1.id in (select max(t.id) from hospital_colonoscopy_record t group by t.sid) ");
        sql.append( " and t2.community_dept_id=d.id and t2.area_dept_id = d1.id and t2.overall_status_cy <> 2 ");

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

        if (execlData.getInGroupDateStart() != null) {
            sql.append(" and t2.in_group_date >= ? ");
            parm.add(execlData.getInGroupDateStart());
        }

        if (execlData.getInGroupDateEnd() != null) {
            sql.append(" and t2.in_group_date <= ? ");
            parm.add(execlData.getInGroupDateEnd());
        }

        if (execlData.getStage_cy() != null) {
            sql.append(" and t2.stage_cy = ? ");
            parm.add(execlData.getStage_cy());
        }
        if (execlData.getSiteId() != null) {
            sql.append("  AND t2.site_id =  ? ");
            parm.add(execlData.getSiteId());
        }
        log.info("sql"+sql.toString());

        if (parm.size() > 0) {
            return jdbc.query(sql.toString(), parm.toArray(), new BeanPropertyRowMapper<ColonoscopyVo>(ColonoscopyVo.class));
        }
        return  jdbc.query(sql.toString(), parm.toArray(), new BeanPropertyRowMapper<ColonoscopyVo>(ColonoscopyVo.class));
    }

    public static void main(String [] args){
        StringBuffer sql = new StringBuffer();
        sql.append("select DATE_FORMAT(fit_info.result_date,'%Y-%m-%d') as fit_result_date,DATE_FORMAT(changjing_info.resultSurveyDate,'%Y-%m-%d') as resultSurveyDate,DATE_FORMAT(changjing_info.reserve_date,'%Y-%m-%d') as reserve_date,user_info.*,dna_info.dna_code,dna_info.dna_check_result,fit_info.fit_code,DATE_FORMAT(fit_info.code_entry_time,'%Y-%m-%d') as fit_code_entry_time,fit_info.result as fit_result,\n" +
                "case when changjing_info.reserve_status=1 then '未预约' when changjing_info.reserve_status=2 then '已预约' else NULL end as changjing_reserve_status,\n" +
                "case when changjing_info.examination_status=1 then '未检查' when changjing_info.examination_status=2 then '已检查' else NULL end as changjing_examination_status,\n" +
                "case when changjing_info.finished_status=1 then '未完成' when changjing_info.finished_status=2 then '已完成' else NULL end as changjing_finished_status,\n" +
                "case when changjing_info.result_status=1 then '未录入' when changjing_info.result_status=2 then '已录入' else NULL end as changjing_result_status,\n" +
                "case when changjing_info.pathology_status=1 then '未录入' when changjing_info.pathology_status=2 then '已录入' else NULL end as changjing_pathology_status,\n" +
                "case when changjing_info.notification_entry_status=1 then '未录入' when changjing_info.notification_entry_status=2 then '已录入' else NULL end as changjing_notification_entry_status,\n" +
                "case when changjing_info.notification_issue_status=1 then '未发放' when changjing_info.notification_issue_status=2 then '已发放' else NULL end as changjing_notification_issue_status,\n" +
                //2018-8-31 ZT
                "case when changjing_info.item2=1 then '是' when changjing_info.item2!=1 then '否' else NULL end as item2,\n" +
                "case when changjing_info.item3=1 then '是' when changjing_info.item3!=1 then '否'  else NULL end as item3,\n" +
                "case when changjing_info.item1=1 then '是' when changjing_info.item1!=1 then '否'  else NULL end as item1,\n" +

                "case when xueye_info.collect_status=-1 then '未录入' when xueye_info.collect_status=1 then '已录入' when xueye_info.collect_status=2 then '未提供' else NULL end xueye_collect_status,\n" +
                "case when tuoye_info.collect_status=-1 then '未录入' when tuoye_info.collect_status=1 then '已录入' when tuoye_info.collect_status=2 then '未提供' else NULL end tuoye_collect_status,\n" +
                "case when fenbian_info.collect_status=-1 then '未录入' when fenbian_info.collect_status=1 then '已录入' when fenbian_info.collect_status=2 then '未提供' else NULL end fenbian_collect_status,\n" +
                "case when dna_info.code_entry_status=1 then '未录入' when dna_info.code_entry_status=2 then '已录入' else NULL end as dna_code_entry_status,\n" +
                "case when dna_info.dna_check_enter_status=1 then '未录入' when dna_info.dna_check_enter_status=2 then '已录入' else NULL end as dna_gj_dna_check_enter_status,\n" +
                "case when dna_info.dna_check_inform_status=1 then '未通过' when dna_info.dna_check_inform_status=2 then '已通过' when dna_info.dna_check_inform_status=3 then '未审核' else NULL end as dna_dna_check_inform_status,\n" +
                "case when dna_info.dna_community_inform_status=1 then '已发放' when dna_info.dna_community_inform_status=2 then '未发放' else NULL end as dna_dna_community_inform_status,\n" +
                "case when dna_info.dna_check_inform_status=2 then '已返回' when dna_info.dna_check_inform_status!=2 and dna_info.code_entry_status=2 then '待返回' else NULL end as dna_other_dna_check_enter_status,\n" +
                "case when dna_info.dna_check_inform_status=2 then dna_info.dna_check_result else NULL end as no_dna_check_result,\n" +
                "case when fit_info.code_entry_status=1 then '未录入' when fit_info.code_entry_status=2 then '已录入' else NULL end as fit_code_entry_status,\n" +
                "case when fit_info.insert_status=1 then '未录入' when fit_info.insert_status=2 then '已录入' else NULL end as fit_insert_status\n" +
                "from (SELECT\n" +
                "\tr.`sid` as sid,\n" +
                "\tr.`township` as township,\n" +
                "\tr.`province` as province,\n" +
                "\tr.`city` as city,\n" +
                "\tr.`village` as village,\n" +
                "\tr.`city_other` as city_other,\n" +
                "\tr.`area` as area,\n" +
                "\tr.`id_card` as idCard,\n" +
                "\tr.`name` as `name`,\n" +
                "  r.`sex` as sex,\n" +
                "\tr.`age` as age,\n" +
                "\tr.`phone` as phone,\n" +
                "\td2.`name` as areaName,\n" +
                "\td.`name` as commName,\n" +
                "\tu.nickName as nickName,\n" +
                "\tr.address as address,\n" +
                "  case when r.`group`= 'A' then 'A' when r.`group` = 'B'  then 'B' when r.`group` = 'C' and r.risk_level is null then 'C'   when r.`group` = 'C' and r.risk_level = 1 then 'C组低危' when r.`group` = 'C' and  r.risk_level = 2 then 'C组高危' ELSE NULL END `group`,\n" +
                "\tr.overall_status_cy overallStatusCy,\n" +
                "\tDATE_FORMAT(r.in_group_date,'%Y-%m-%d') AS inGroupDate,\n" +
                "  '已录入' as qrsState,\n" +
                "\tcase WHEN r.risk_level>0 then '已录入' else '未录入' end as riskState,r.score as score\n" +
                "FROM\n" +
                "\thospital_intestine_review r,\n" +
                "\titsys_department d,\n" +
                "\titsys_department d2,\n" +
                "\titsys_user u\n" +
                "WHERE\n" +
                "\t1 = 1 and \n" +
                "r.community_dept_id = d.id AND r.area_dept_id = d2.id and r.create_user = u.loginName " );

        sql.append(" ) as user_info\n" +
                "LEFT JOIN (select tab.*,hcpr.item1,hcpr.item2,hcpr.item3,hcrr.survey_date as resultSurveyDate from hospital_colonoscopy_record tab left join hospital_colonoscopy_pathology_result hcpr on tab.pathology_id=hcpr.id left join hospital_colonoscopy_result hcrr on tab.result_id=hcrr.id where tab.id in(SELECT MAX(hcr.id) FROM hospital_colonoscopy_record hcr GROUP BY hcr.sid)) as changjing_info on user_info.sid = changjing_info.sid " +
                "LEFT JOIN (select sid,collect_status from hospital_biological_sample_result tab where tab.id in(SELECT MAX(hbsr.id) FROM hospital_biological_sample_result hbsr where hbsr.sample_type='A' GROUP BY hbsr.sid)) as xueye_info on user_info.sid = xueye_info.sid\n" +
                "LEFT JOIN (select sid,collect_status from hospital_biological_sample_result tab where tab.id in(SELECT MAX(hbsr.id) FROM hospital_biological_sample_result hbsr where hbsr.sample_type='M' GROUP BY hbsr.sid)) as tuoye_info on user_info.sid = tuoye_info.sid\n" +
                "LEFT JOIN (select sid,collect_status from hospital_biological_sample_result tab where tab.id in(SELECT MAX(hbsr.id) FROM hospital_biological_sample_result hbsr where hbsr.sample_type='F' GROUP BY hbsr.sid)) as fenbian_info on user_info.sid = fenbian_info.sid\n" +
                "LEFT JOIN (select * from hospital_stool_dna tab where tab.id in(SELECT MAX(hsd.id) FROM hospital_stool_dna hsd GROUP BY hsd.sid)) as dna_info on user_info.sid = dna_info.sid\n" +
                "LEFT JOIN (select * from hospital_fit_result tab where tab.id in(SELECT MAX(hfr.id) FROM hospital_fit_result hfr GROUP BY hfr.sid)) as fit_info on user_info.sid = fit_info.sid\n" +
                "ORDER BY user_info.sid\n");

        System.out.println(sql.toString());
    }

}

