package com.yuntongxun.itsys.base.dao.impl;/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: HospitalCancerDaoImpl
 * Author:   zongtong
 * Date:     2018/9/6 上午11:03
 * History:
 * <author>          <time>                <version>
 * zongtong           2018/9/6 上午11:03           v1.0.0
 */

import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.common.util.StringUtil;
import com.yuntongxun.itsys.base.dao.HospitalCancerDao;
import com.yuntongxun.itsys.base.po.cancer.HospitalCancerRecordPO;
import com.yuntongxun.itsys.base.po.dto.cancervo.*;
import com.yuntongxun.itsys.base.service.impl.BiologSampleServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class HospitalCancerDaoImpl implements HospitalCancerDao {

    @Autowired
    private JdbcTemplate jdbctemp;

    private final Logger log = LogManager.getLogger(BiologSampleServiceImpl.class);

    @Override
    public int save(final HospitalCancerRecordVo hospitalCancerRecordVo) {
        log.info("@dao 新增癌症检查结果记录 hospitalCancerRecordVo={}.", hospitalCancerRecordVo.toString());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("insert into hospital_cancer_record(sid,pathology_id,colonoscopy_record_id,community_dept_id,area_dept_id,cancer_report_status,cancer_diagnose_status,colorectal_cancer_diagnose_information_status,colorectal_cancer_treatment_informatio_status,end_event_type,valid_data,date_created,update_time,dept_code,create_user,stage) values(?,?,?,?,?,?,?,?,?,?,?,now(),now(),?,?,?)");
        jdbctemp.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(stringBuffer.toString(), Statement.RETURN_GENERATED_KEYS);
                ps.setObject(1, hospitalCancerRecordVo.getSid());
                ps.setObject(2, hospitalCancerRecordVo.getPathologyId());
                ps.setObject(3, hospitalCancerRecordVo.getColonoscopyRecordId());
                ps.setObject(4, hospitalCancerRecordVo.getCommunityDeptId());
                ps.setObject(5, hospitalCancerRecordVo.getAreaDeptId());
                ps.setObject(6, hospitalCancerRecordVo.getCancerReportStatus());
                ps.setObject(7, hospitalCancerRecordVo.getCancerDiagnoseStatus());
                ps.setObject(8, hospitalCancerRecordVo.getColorectalCancerDiagnoseInformationStatus());
                ps.setObject(9, hospitalCancerRecordVo.getColorectalCancerTreatmentInformatioStatus());
                ps.setObject(10, hospitalCancerRecordVo.getEndEventType());
                ps.setObject(11, hospitalCancerRecordVo.getValidData());
                ps.setObject(12, hospitalCancerRecordVo.getDeptCode());
                ps.setObject(13, hospitalCancerRecordVo.getCreateUser());
                ps.setObject(14, hospitalCancerRecordVo.getStage());
                return ps;
            }

        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public ListPageUtil query(HospitalCancerRecordVo hospitalCancerRecordVo, boolean b) {
        List<Object> parm = new ArrayList<Object>();//宗曈修改已签到为签到

        StringBuffer sql=new StringBuffer();
        sql.append("SELECT  t1.`id` AS `id`, t1.sid , t2.`name` AS `name`, t2.age ,t2.phone,t2.in_group_date, u.nickName,d.name as depName,d1.name as areaName  " +
                " , t2.`age` AS `age`, t2.`phone` AS `phone`, t2.`sex`  " +
                " ,d.`name` AS depName, CASE   " +
                "  WHEN t2.`group` = 'A' THEN 'A'  " +
                "  WHEN t2.`group` = 'B' THEN 'B'  " +
                "  WHEN t2.`group` = 'C'  " +
                "  AND t2.risk_level IS NULL THEN 'C'  " +
                "  WHEN t2.`group` = 'C'  " +
                "  AND t2.risk_level = 1 THEN 'Cd'  " +
                "  WHEN t2.`group` = 'C'  " +
                "  AND t2.risk_level = 2 THEN 'Cg'  " +
                "  ELSE NULL  " +
                " END AS 'group', t2.`in_group_date` AS `inGroupDate`, t2.overall_status_cy AS overallStatusCy,t1.cancer_report_id as  cancerReportId,t1.cancer_report_status as cancerReportStatus,t1.cancer_report_in_status_date as cancerReportInStatusDate,t1.cancer_diagnose_id as cancerDiagnoseId,t1.cancer_diagnose_status as cancerDiagnoseStatus,t1.cancer_diagnose_in_status_date as cancerDiagnoseInStatusDate,t1.colorectal_cancer_diagnose_information_id as colorectalCancerDiagnoseInformationId,t1.colorectal_cancer_diagnose_information_status as colorectalCancerDiagnoseInformationStatus,t1.colorectal_cancer_diagnose_information_in_status_date as colorectalCancerDiagnoseInformationInStatusDate,t1.colorectal_cancer_treatment_information_id as colorectalCancerTreatmentInformationId,t1.colorectal_cancer_treatment_informatio_status as colorectalCancerTreatmentInformatioStatus,t1.colorectal_cancer_treatment_informatio_in_status_date as colorectalCancerTreatmentInformatioInStatusDate  " +
                "FROM hospital_cancer_record t1, hospital_intestine_review t2, itsys_department d, itsys_department d1 ,itsys_user u   " +
                "WHERE t2.sid = t1.sid  " +
                " AND t2.community_dept_id = d.id  " +
                " AND t2.area_dept_id = d1.id  " +
                " AND t2.create_user = u.loginName  " +
                " ");

        if(hospitalCancerRecordVo.getAreaDeptId()!=null){
            sql.append(" and t1.area_dept_id = ? ");
            parm.add(hospitalCancerRecordVo.getAreaDeptId());
        }

        if (hospitalCancerRecordVo.getCommunityDeptId() != null) {
            sql.append(" and t1.community_dept_id = ? ");
            parm.add(hospitalCancerRecordVo.getCommunityDeptId());
        }

        if (!StringUtil.isEmpty(hospitalCancerRecordVo.getSid())) {
            sql.append(" and t1.sid like ? ");
            parm.add("%" + hospitalCancerRecordVo.getSid() + "%");
        }
        if (!StringUtil.isEmpty(hospitalCancerRecordVo.getName())) {
            sql.append(" and t2.`name` like ? ");
            parm.add("%" + hospitalCancerRecordVo.getName() + "%");
        }
        if (!StringUtil.isEmpty(hospitalCancerRecordVo.getPhone())) {
            sql.append(" and t2.`phone` like ? ");
            parm.add("%" + hospitalCancerRecordVo.getPhone() + "%");
        }
        if (!StringUtil.isEmpty(hospitalCancerRecordVo.getGroup())) {
            if (hospitalCancerRecordVo.getGroup().equals("Cg")) {
                sql.append( " and t2.`group` = 'C' and t2.risk_level = ?");
                parm.add(2);
            } else if (hospitalCancerRecordVo.getGroup().equals("Cd")) {
                sql.append( " and t2.`group` = 'C' and t2.risk_level = ?");
                parm.add(1);
            } else {
                sql.append( " and t2.`group` = ? ");
                parm.add(hospitalCancerRecordVo.getGroup());
            }
        }
        if(hospitalCancerRecordVo.getOverallStatusCy()!=null){
            sql.append( " and t2.overall_status_cy = ? ");
            parm.add(hospitalCancerRecordVo.getOverallStatusCy());
        }

        if (!StringUtils.isEmpty(hospitalCancerRecordVo.getInGroupDateStart())) {
            sql.append(" and t2.in_group_date >= ? ");
            parm.add(hospitalCancerRecordVo.getInGroupDateStart());
        }
        if (!StringUtils.isEmpty(hospitalCancerRecordVo.getInGroupDateEnd())) {
            sql.append(" and t2.in_group_date <= ? ");
            parm.add(hospitalCancerRecordVo.getInGroupDateEnd());
        }

        if(!StringUtils.isEmpty(hospitalCancerRecordVo.getCancerReportStatus())){
            sql.append(" and t1.cancer_report_status= ? ");
            parm.add(hospitalCancerRecordVo.getCancerReportStatus());
        }

        if(!StringUtils.isEmpty(hospitalCancerRecordVo.getCancerDiagnoseStatus())){
            sql.append(" and t1.cancer_diagnose_status= ? ");
            parm.add(hospitalCancerRecordVo.getCancerDiagnoseStatus());
        }

        if(!StringUtils.isEmpty(hospitalCancerRecordVo.getColorectalCancerDiagnoseInformationStatus())){
            sql.append(" and t1.colorectal_cancer_diagnose_information_status= ? ");
            parm.add(hospitalCancerRecordVo.getColorectalCancerDiagnoseInformationStatus());
        }


        if(!StringUtils.isEmpty(hospitalCancerRecordVo.getColorectalCancerTreatmentInformatioStatus())){
            sql.append(" and t1.colorectal_cancer_treatment_informatio_status= ? ");
            parm.add(hospitalCancerRecordVo.getColorectalCancerTreatmentInformatioStatus());
        }


        if(!StringUtils.isEmpty(hospitalCancerRecordVo.getCancerType())){
            if(Constans.CANCERTYPE1.equals(hospitalCancerRecordVo.getCancerType())){
                sql.append(" and t1.cancer_report_status is not null ");
            }else if(Constans.CANCERTYPE2.equals(hospitalCancerRecordVo.getCancerType())){
                sql.append(" and t1.cancer_diagnose_status is not null ");
            }else if(Constans.CANCERTYPE3.equals(hospitalCancerRecordVo.getCancerType())){
                sql.append(" and t1.colorectal_cancer_diagnose_information_status is not null ");
            }else if(Constans.CANCERTYPE4.equals(hospitalCancerRecordVo.getCancerType())){
                sql.append(" and t1.colorectal_cancer_treatment_informatio_status is not null ");
            }
        }

        if (!StringUtil.isEmpty(hospitalCancerRecordVo.getLoginName())) {
            sql.append(" and u.loginName = ?");
            parm.add(hospitalCancerRecordVo.getLoginName());
        }
        if (b) {
            ListPageUtil listPage = new ListPageUtil(sql.toString(), parm.toArray(), hospitalCancerRecordVo.getPageNo(), hospitalCancerRecordVo.getPageSize(), jdbctemp, null);
            return listPage;
        } else {
            ListPageUtil listPageNoPaging = new ListPageUtil(sql.toString(), parm.toArray(), 1, -1, jdbctemp, null);
            return listPageNoPaging;
        }
    }

    @Override
    public int saveReport(final HospitalCancerReportVo hospitalCancerRecordVo) {
        log.info("@dao 新增癌症报告表 hospitalCancerRecordVo={}.", hospitalCancerRecordVo.toString());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("insert into hospital_cancer_report(sid,tb_table_date,check_years,investigator_code,tb_table_person,quality_control_person,date_created,update_time,dept_code,create_user,stage) values(?,?,?,?,?,?,now(),now(),?,?,?)");
        jdbctemp.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(stringBuffer.toString(), Statement.RETURN_GENERATED_KEYS);
                ps.setObject(1, hospitalCancerRecordVo.getSid());
                ps.setObject(2, hospitalCancerRecordVo.getTbTableDate());
                ps.setObject(3, hospitalCancerRecordVo.getCheckYears());
                ps.setObject(4, hospitalCancerRecordVo.getInvestigatorCode());
                ps.setObject(5, hospitalCancerRecordVo.getTbTablePerson());
                ps.setObject(6, hospitalCancerRecordVo.getQualityControlPerson());
                ps.setObject(7, hospitalCancerRecordVo.getDeptCode());
                ps.setObject(8, hospitalCancerRecordVo.getCreateUser());
                ps.setObject(9, hospitalCancerRecordVo.getStage());
                return ps;
            }

        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void saveReportMessage(final List<HospitalCancerReportMessageVo> hospitalCancerReportMessagePOS) {
        String sql = "insert into hospital_cancer_report_message(cancer_report_id,cancer_type_site,report_date,diagnose_source,diagnose_source_other,date_created,update_time) values(?,?,?,?,?,now(),now())";
        jdbctemp.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i)
                    throws SQLException {
                HospitalCancerReportMessageVo hospitalCancerReportMessageVo = hospitalCancerReportMessagePOS.get(i);
                ps.setObject(1, hospitalCancerReportMessageVo.getCancerReportId());
                ps.setObject(2, hospitalCancerReportMessageVo.getCancerTypeSite());
                ps.setObject(3, hospitalCancerReportMessageVo.getReportDate());
                ps.setObject(4, hospitalCancerReportMessageVo.getDiagnoseSource());
                ps.setObject(5, hospitalCancerReportMessageVo.getDiagnoseSourceOther());
            }
            @Override
            public int getBatchSize() {
                // TODO Auto-generated method stub
                return hospitalCancerReportMessagePOS.size();
            }
        });
    }

    @Override
    public void updateReportById(String sqlId,int reportId,String sqlStatus,String status,String sqlDate, Integer cancerRecordId) {
        String sql = "update hospital_cancer_record set "+sqlId+"=?,"+sqlStatus+"=?,"+sqlDate+"=now()"
                + ",update_time=now() where `id`=?";
        jdbctemp.update(sql, new Object[]{reportId,status,cancerRecordId});
    }

    @Override
    public List<HospitalCancerReportVo> queryReportById(Integer id) {
        String sql = "select hr.*,hr.tb_table_date as tbTableDateToString from hospital_cancer_report  hr where id=?";//+id+" and sid='"+sid+"'"
        return jdbctemp.query(sql, new BeanPropertyRowMapper<HospitalCancerReportVo>(HospitalCancerReportVo.class), id);

    }

    @Override
    public List<HospitalCancerReportMessageVo> queryReportMessageByReportId(Integer id) {
        String sql = "select h.*,h.report_date as reportDateToString from hospital_cancer_report_message h where cancer_report_id=?";//+id+" and sid='"+sid+"'"
        return jdbctemp.query(sql, new BeanPropertyRowMapper<HospitalCancerReportMessageVo>(HospitalCancerReportMessageVo.class), id);
    }

    @Override
    public void updateReportByReportID(HospitalCancerReportVo hospitalCancerReportVo) {
        String sql = "update hospital_cancer_report set tb_table_date=? ,check_years=? ,investigator_code=? ,tb_table_person=? ,quality_control_person=?,update_time=now() where  id=?";
        jdbctemp.update(sql, new Object[]{hospitalCancerReportVo.getTbTableDate(), hospitalCancerReportVo.getCheckYears(),hospitalCancerReportVo.getInvestigatorCode(),hospitalCancerReportVo.getTbTablePerson(),hospitalCancerReportVo.getQualityControlPerson(),hospitalCancerReportVo.getId()});

    }

    @Override
    public void deleteReportMessageByReportId(Integer id,String table) {
        String sql = "DELETE FROM " + table + " where cancer_report_id=?";
        int result = jdbctemp.update(sql, id);
    }

    @Override
    public void updateReportC4ByID(HospitalColorectalCancerTreatmentInformationVo hospitalColorectalCancerTreatmentInformationVo) {
    String sql = "UPDATE hospital_colorectal_cancer_treatment_information" +
            " SET " +
            " `tb_table_date` = ?," +
            " `check_years` = ?," +
            " `investigator_code` = ?," +
            " `tb_table_person` = ?," +
            " `quality_control_person` = ?," +
            " `item1` = ?," +
            " `item2` = ?," +
            " `item2_1` = ?," +
            " `item3` = ?," +
            " `item3_1` = ?," +
            " `item4` = ?," +
            " `item4_1` = ?," +
            " `item5` = ?," +
            " `item5_1` = ?," +
            " `item6` = ?," +
            " `item6_1` = ?," +
            " `item6_2` = ?," +
            " `update_time` = now()," +
            " `excerpt_purpose` = ?" +
            " WHERE `id` = ?";
        jdbctemp.update(sql, new Object[]{hospitalColorectalCancerTreatmentInformationVo.getTbTableDateToString(),
                hospitalColorectalCancerTreatmentInformationVo.getCheckYears(),
                hospitalColorectalCancerTreatmentInformationVo.getInvestigatorCode(),
                hospitalColorectalCancerTreatmentInformationVo.getTbTablePerson(),
                hospitalColorectalCancerTreatmentInformationVo.getQualityControlPerson(),
                hospitalColorectalCancerTreatmentInformationVo.getItem1(),
                hospitalColorectalCancerTreatmentInformationVo.getItem2(),
                hospitalColorectalCancerTreatmentInformationVo.getItem21(),
                hospitalColorectalCancerTreatmentInformationVo.getItem3(),
                hospitalColorectalCancerTreatmentInformationVo.getItem31ToString(),
                hospitalColorectalCancerTreatmentInformationVo.getItem4(),
                hospitalColorectalCancerTreatmentInformationVo.getItem41ToString(),
                hospitalColorectalCancerTreatmentInformationVo.getItem5(),
                hospitalColorectalCancerTreatmentInformationVo.getItem51(),
                hospitalColorectalCancerTreatmentInformationVo.getItem6(),
                hospitalColorectalCancerTreatmentInformationVo.getItem61(),
                hospitalColorectalCancerTreatmentInformationVo.getItem62(),
                hospitalColorectalCancerTreatmentInformationVo.getExcerptPurpose(),
                hospitalColorectalCancerTreatmentInformationVo.getId()});
    }

    @Override
    public void deleteAddendumByC4Id(Integer id, String table) {
        String sql = "DELETE FROM " + table + " where colorectal_cancer_treatment_information_id=?";
        int result = jdbctemp.update(sql, id);
    }

    @Override
    public List<HospitalCancerSurgicalOperationVo> queryAddendum1ByC4Id(Integer id, Integer communityDeptId, Integer areaDeptId) {
        String sql = "SELECT" +
                        "`id`," +
                        "`colorectal_cancer_treatment_information_id`," +
                        "surgical_operation_code_other,"+
                        "`surgical_operation_code`," +
                        "date_format(finsh_date,'%Y-%c-%d') as finshDateToString" +
                        " FROM" +
                        " hospital_cancer_surgical_operation " +
                        "WHERE colorectal_cancer_treatment_information_id=?";
        return jdbctemp.query(sql, new BeanPropertyRowMapper<HospitalCancerSurgicalOperationVo>(HospitalCancerSurgicalOperationVo.class), id);
    }

    @Override
    public List<HospitalCancerTreatmentInformationVo> queryAddendum2ByC4Id(Integer id, Integer communityDeptId, Integer areaDeptId) {
        String sql ="SELECT" +
                    "`id`," +
                    "`colorectal_cancer_treatment_information_id`," +
                    "`doctor_name`," +
                    "`medical_institution_name`," +
                    "`address`," +
                    "`email`," +
                    "`fax`," +
                    "`tel_phone1`," +
                    "`tel_phone2`," +
                    "`bl_number`" +
                    " FROM" +
                    " hospital_cancer_treatment_information " +
                    "WHERE colorectal_cancer_treatment_information_id=?";
        return jdbctemp.query(sql, new BeanPropertyRowMapper<HospitalCancerTreatmentInformationVo>(HospitalCancerTreatmentInformationVo.class), id);
    }

    @Override
    public List<HospitalColorectalCancerTreatmentInformationVo> queryReportC4ById(Integer id, Integer communityDeptId, Integer areaDeptId) {
        String sql = "SELECT" +
                "`id`," +
                "`sid`," +
                "date_format(tb_table_date,'%Y-%c-%d') as tbTableDateToString," +
                "`dept_code`," +
                "`check_years`," +
                "`investigator_code`," +
                "`tb_table_person`," +
                "`quality_control_person`," +
                "`item1`," +
                "`item2`," +
                "`item2_1` as item21," +
                "`item3`," +
                "date_format(item3_1,'%Y-%c-%d') as item31ToString," +
                "`item4`," +
                "date_format(item4_1,'%Y-%c-%d') as item41ToString," +
                "`item5`," +
                "date_format(item5_1,'%Y-%c-%d') as item51ToString," +
                "`item6`," +
                "`item6_1` as item61," +
                "`item6_2` as item62," +
                "`create_user`," +
                "`stage`," +
                "`excerpt_purpose`" +
                " FROM" +
                " hospital_colorectal_cancer_treatment_information " +
                "where id=?";
        return jdbctemp.query(sql, new BeanPropertyRowMapper<HospitalColorectalCancerTreatmentInformationVo>(HospitalColorectalCancerTreatmentInformationVo.class), id);
    }

    @Override
    public int saveCancerDiagnoseInformation(final HospitalColorectalCancerDiagnoseInformationVo hospitalColorectalCancerDiagnoseInformationVo) {
        log.info("@dao 新增结直肠癌诊断信息摘录表 hospitalColorectalCancerDiagnoseInformationVo={}.", hospitalColorectalCancerDiagnoseInformationVo.toString());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("insert into hospital_colorectal_cancer_diagnose_information(sid,excerpts_date,excerpt_purpose,cf_screening,excerpt_person_id,dept_code,research_year,investigator_code,tb_table_person,quality_control_person,item1,item2_1,item2_2,item2_3,item2_3_other,item3_check_date,item3_1,item3_2,item3_3,item4_1,item4_2_1,item4_2_2,item4_3_1,item4_3_2,item4_4,item4_5,item4_6,item6_1,item7_1,item8a_1,item8a_2,item8b_1,item8b_2,item8c_1,item8c_1_1,item8c_1_2,item8c_1_3,item8c_1_4,item8c_1_5,item8d_1_1,item8d_1_2,item8e_1_1,item9,item10,item11,report_url,item12_1,item12_2,item12_3,item12_4,item12_5,item13_1,item13_2,item13_3,item13_4,item13_5,item13_6,item13_7,item13_8,item13_9,item13_10,item13_11,item14_1,item14_2,item14_3,item14_4,item14_5,item14_6,item14_7,item14_8,item14_9,item14_10,item15_1,item15_2,item15_3,item15_4,item15_5,item15_6,item16a_1,item16a_1_1,item16a_1_2,item16a_1_3,item16b_1,item16b_1_1,item16b_2,item16b_1_2,item16b_1_3,item17_1_1,item17_1_2,item17_1_3,item18,date_created,update_time,create_user,stage,item15_7) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),now(),?,?,?)");
        jdbctemp.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(stringBuffer.toString(), Statement.RETURN_GENERATED_KEYS);
                ps.setObject(1, hospitalColorectalCancerDiagnoseInformationVo.getSid());
                ps.setObject(2, StringUtils.isEmpty(hospitalColorectalCancerDiagnoseInformationVo.getExcerptsDateToString())?null:hospitalColorectalCancerDiagnoseInformationVo.getExcerptsDateToString());
                ps.setObject(3, hospitalColorectalCancerDiagnoseInformationVo.getExcerptPurpose());
                ps.setObject(4, hospitalColorectalCancerDiagnoseInformationVo.getCfScreening());
                ps.setObject(5, hospitalColorectalCancerDiagnoseInformationVo.getExcerptPersonId());
                ps.setObject(6, hospitalColorectalCancerDiagnoseInformationVo.getDeptCode());
                ps.setObject(7, hospitalColorectalCancerDiagnoseInformationVo.getResearchYear());
                ps.setObject(8, hospitalColorectalCancerDiagnoseInformationVo.getInvestigatorCode());
                ps.setObject(9, hospitalColorectalCancerDiagnoseInformationVo.getTbTablePerson());
                ps.setObject(10, hospitalColorectalCancerDiagnoseInformationVo.getQualityControlPerson());
                ps.setObject(11, hospitalColorectalCancerDiagnoseInformationVo.getItem1());
                ps.setObject(12, hospitalColorectalCancerDiagnoseInformationVo.getItem21());
                ps.setObject(13, hospitalColorectalCancerDiagnoseInformationVo.getItem22());
                ps.setObject(14, hospitalColorectalCancerDiagnoseInformationVo.getItem23());
                ps.setObject(15, hospitalColorectalCancerDiagnoseInformationVo.getItem23Other());
                ps.setObject(16, StringUtils.isEmpty(hospitalColorectalCancerDiagnoseInformationVo.getItem3CheckDateToString())?null:hospitalColorectalCancerDiagnoseInformationVo.getItem3CheckDateToString());
                ps.setObject(17, hospitalColorectalCancerDiagnoseInformationVo.getItem31());
                ps.setObject(18, hospitalColorectalCancerDiagnoseInformationVo.getItem32());
                ps.setObject(19, hospitalColorectalCancerDiagnoseInformationVo.getItem33());
                ps.setObject(20, hospitalColorectalCancerDiagnoseInformationVo.getItem41());
                ps.setObject(21, hospitalColorectalCancerDiagnoseInformationVo.getItem421());
                ps.setObject(22, hospitalColorectalCancerDiagnoseInformationVo.getItem422());
                ps.setObject(23, hospitalColorectalCancerDiagnoseInformationVo.getItem431());
                ps.setObject(24, hospitalColorectalCancerDiagnoseInformationVo.getItem432());
                ps.setObject(25, hospitalColorectalCancerDiagnoseInformationVo.getItem44());
                ps.setObject(26, hospitalColorectalCancerDiagnoseInformationVo.getItem45());
                ps.setObject(27, hospitalColorectalCancerDiagnoseInformationVo.getItem46());
                ps.setObject(28, hospitalColorectalCancerDiagnoseInformationVo.getItem61());
                ps.setObject(29, hospitalColorectalCancerDiagnoseInformationVo.getItem71());
                ps.setObject(30, hospitalColorectalCancerDiagnoseInformationVo.getItem8a1());
                ps.setObject(31, hospitalColorectalCancerDiagnoseInformationVo.getItem8a2());
                ps.setObject(32, hospitalColorectalCancerDiagnoseInformationVo.getItem8b1ToString());
                ps.setObject(33, hospitalColorectalCancerDiagnoseInformationVo.getItem8b2());
                ps.setObject(34, hospitalColorectalCancerDiagnoseInformationVo.getItem8c1());
                ps.setObject(35, hospitalColorectalCancerDiagnoseInformationVo.getItem8c11());
                ps.setObject(36, hospitalColorectalCancerDiagnoseInformationVo.getItem8c12());
                ps.setObject(37, hospitalColorectalCancerDiagnoseInformationVo.getItem8c13());
                ps.setObject(38, hospitalColorectalCancerDiagnoseInformationVo.getItem8c14());
                ps.setObject(39, hospitalColorectalCancerDiagnoseInformationVo.getItem8c15());
                ps.setObject(40, hospitalColorectalCancerDiagnoseInformationVo.getItem8d11ToString());
                ps.setObject(41, hospitalColorectalCancerDiagnoseInformationVo.getItem8d12());
                ps.setObject(42, hospitalColorectalCancerDiagnoseInformationVo.getItem8e11());
                ps.setObject(43, hospitalColorectalCancerDiagnoseInformationVo.getItem9ToString());
                ps.setObject(44, hospitalColorectalCancerDiagnoseInformationVo.getItem10());
                ps.setObject(45, hospitalColorectalCancerDiagnoseInformationVo.getItem11());
                ps.setObject(46, hospitalColorectalCancerDiagnoseInformationVo.getReportUrlToString());
                ps.setObject(47, hospitalColorectalCancerDiagnoseInformationVo.getItem121());
                ps.setObject(48, hospitalColorectalCancerDiagnoseInformationVo.getItem122());
                ps.setObject(49, hospitalColorectalCancerDiagnoseInformationVo.getItem123());
                ps.setObject(50, hospitalColorectalCancerDiagnoseInformationVo.getItem124());
                ps.setObject(51, hospitalColorectalCancerDiagnoseInformationVo.getItem125());
                ps.setObject(52, hospitalColorectalCancerDiagnoseInformationVo.getItem131());
                ps.setObject(53, hospitalColorectalCancerDiagnoseInformationVo.getItem132());
                ps.setObject(54, hospitalColorectalCancerDiagnoseInformationVo.getItem133());
                ps.setObject(55, hospitalColorectalCancerDiagnoseInformationVo.getItem134());
                ps.setObject(56, hospitalColorectalCancerDiagnoseInformationVo.getItem135());
                ps.setObject(57, hospitalColorectalCancerDiagnoseInformationVo.getItem136());
                ps.setObject(58, hospitalColorectalCancerDiagnoseInformationVo.getItem137());
                ps.setObject(59, hospitalColorectalCancerDiagnoseInformationVo.getItem138());
                ps.setObject(60, hospitalColorectalCancerDiagnoseInformationVo.getItem139());
                ps.setObject(61, hospitalColorectalCancerDiagnoseInformationVo.getItem1310());
                ps.setObject(62, hospitalColorectalCancerDiagnoseInformationVo.getItem1311());
                ps.setObject(63, hospitalColorectalCancerDiagnoseInformationVo.getItem141());
                ps.setObject(64, hospitalColorectalCancerDiagnoseInformationVo.getItem142());
                ps.setObject(65, hospitalColorectalCancerDiagnoseInformationVo.getItem143());
                ps.setObject(66, hospitalColorectalCancerDiagnoseInformationVo.getItem144());
                ps.setObject(67, hospitalColorectalCancerDiagnoseInformationVo.getItem145());
                ps.setObject(68, hospitalColorectalCancerDiagnoseInformationVo.getItem146());
                ps.setObject(69, hospitalColorectalCancerDiagnoseInformationVo.getItem147());
                ps.setObject(70, hospitalColorectalCancerDiagnoseInformationVo.getItem148());
                ps.setObject(71, hospitalColorectalCancerDiagnoseInformationVo.getItem149());
                ps.setObject(72, hospitalColorectalCancerDiagnoseInformationVo.getItem1410());
                ps.setObject(73, hospitalColorectalCancerDiagnoseInformationVo.getItem151());
                ps.setObject(74, hospitalColorectalCancerDiagnoseInformationVo.getItem152());
                ps.setObject(75, hospitalColorectalCancerDiagnoseInformationVo.getItem153());
                ps.setObject(76, hospitalColorectalCancerDiagnoseInformationVo.getItem154());
                ps.setObject(77, hospitalColorectalCancerDiagnoseInformationVo.getItem155());
                ps.setObject(78, hospitalColorectalCancerDiagnoseInformationVo.getItem156());
                ps.setObject(79, hospitalColorectalCancerDiagnoseInformationVo.getItem16a1());
                ps.setObject(80, hospitalColorectalCancerDiagnoseInformationVo.getItem16a11());
                ps.setObject(81, hospitalColorectalCancerDiagnoseInformationVo.getItem16a12());
                ps.setObject(82, hospitalColorectalCancerDiagnoseInformationVo.getItem16a13());
                ps.setObject(83, hospitalColorectalCancerDiagnoseInformationVo.getItem16b1());
                ps.setObject(84, hospitalColorectalCancerDiagnoseInformationVo.getItem16b11());
                ps.setObject(85, hospitalColorectalCancerDiagnoseInformationVo.getItem16b2());
                ps.setObject(86, hospitalColorectalCancerDiagnoseInformationVo.getItem16b12());
                ps.setObject(87, hospitalColorectalCancerDiagnoseInformationVo.getItem16b13());
                ps.setObject(88, hospitalColorectalCancerDiagnoseInformationVo.getItem1711());
                ps.setObject(89, hospitalColorectalCancerDiagnoseInformationVo.getItem1712());
                ps.setObject(90, hospitalColorectalCancerDiagnoseInformationVo.getItem1713());
                ps.setObject(91, hospitalColorectalCancerDiagnoseInformationVo.getItem18());
                ps.setObject(92, hospitalColorectalCancerDiagnoseInformationVo.getCreateUser());
                ps.setObject(93, hospitalColorectalCancerDiagnoseInformationVo.getStage());
                ps.setObject(94, hospitalColorectalCancerDiagnoseInformationVo.getItem157());
                return ps;
            }

        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void saveInformationDiagnoseEvaluationVos(final List<HospitalInformationDiagnoseEvaluationVo> hospitalInformationDiagnoseEvaluationVos) {
        String sql = "insert into hospital_information_diagnose_evaluation(colorectal_cancer_diagnose_information_id,check_date,diagnostic_methods,diagnostic_methods_other,update_time,date_created) values(?,?,?,?,now(),now())";
        jdbctemp.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i)
                    throws SQLException {
                HospitalInformationDiagnoseEvaluationVo hospitalInformationDiagnoseEvaluationVo = hospitalInformationDiagnoseEvaluationVos.get(i);
                ps.setObject(1, hospitalInformationDiagnoseEvaluationVo.getColorectalCancerDiagnoseInformationId());
                ps.setObject(2, hospitalInformationDiagnoseEvaluationVo.getCheckDateToString());
                ps.setObject(3, hospitalInformationDiagnoseEvaluationVo.getDiagnosticMethods());
                ps.setObject(4, hospitalInformationDiagnoseEvaluationVo.getDiagnosticMethodsOther());
            }
            @Override
            public int getBatchSize() {
                // TODO Auto-generated method stub
                return hospitalInformationDiagnoseEvaluationVos.size();
            }
        });
    }

    @Override
    public List<HospitalColorectalCancerDiagnoseInformationVo> queryDiagnoseInformationbyId(Integer id, Integer communityDeptId, Integer areaDeptId) {
        String sql = "select hr.*,hr.report_url as reportUrlToString , hr.excerpts_date  as excerptsDateToString,hr.item3_check_date as item3CheckDateToString,hr.item8b_1 as item8b1ToString,hr.item8d_1_1 as item8d11ToString,hr.item9 as item9ToString,hr.item2_1 as item21,hr.item2_2 as item22,hr.item2_3 as item23,hr.item2_3_other as item23Other,hr.item3_1 as item31,hr.item3_2 as item32,hr.item3_3 as item33,hr.item4_1 as item41,hr.item4_2_1 as item421,hr.item4_2_2 as item422,hr.item4_3_1 as item431,hr.item4_3_2 as item432,hr.item4_4 as item44,hr.item4_5 as item45,hr.item4_6 as item46,hr.item6_1 as item61,hr.item7_1 as item71,hr.item8a_1 as item8a1,hr.item8a_2 as item8a2,hr.item8b_1 as item8b1,hr.item8b_2 as item8b2,hr.item8c_1 as item8c1,hr.item8c_1_1 as item8c11,hr.item8c_1_2 as item8c12,hr.item8c_1_3 as item8c13,hr.item8c_1_4 as item8c14,hr.item8c_1_5 as item8c15,hr.item8d_1_1 as item8d11,hr.item8d_1_2 as item8d12,hr.item8c_1_3 as item8c13,hr.item8c_1_4 as item8c14,hr.item8c_1_5 as item8c15,hr.item8d_1_1 as item8d11,hr.item8d_1_2 as item8d12,hr.item8e_1_1 as item8e11,hr.item12_1 as item121,hr.item12_2 as item122,hr.item12_3 as item123,hr.item12_4 as item124,hr.item12_5 as item125,hr.item13_1 as item131,hr.item13_2 as item132,hr.item13_3 as item133,hr.item13_4 as item134,hr.item13_5 as item135,hr.item13_6 as item136,hr.item13_7 as item137,hr.item13_8 as item138,hr.item13_9 as item139,hr.item13_10 as item1310,hr.item13_11 as item1311,hr.item14_1 as item141,hr.item14_2 as item142,hr.item14_3 as item143,hr.item14_4 as item144,hr.item14_5 as item145,hr.item14_6 as item146,hr.item14_7 as item147,hr.item14_8 as item148,hr.item14_9 as item149,hr.item14_10 as item1410,hr.item15_1 as item151,hr.item15_2 as item152,hr.item15_3 as item153,hr.item15_4 as item154,hr.item15_5 as item155,hr.item15_6 as item156," +
                "hr.item16a_1 as item16a1,hr.item16a_1_1 as item16a11,hr.item16a_1_2 as item16a12,hr.item16a_1_3 as item16a13,hr.item16b_1 as item16b1,hr.item16b_1_1 as item16b11,hr.item16b_2 as item16b2,hr.item16b_1_2 as item16b12,hr.item16b_1_3 as item16b13,\n" +
                "hr.item17_1_1 as item1711,hr.item17_1_2 as item1712,hr.item17_1_3 as item1713,hr.item15_7 as item157 from hospital_colorectal_cancer_diagnose_information  hr where id=?";//+id+" and sid='"+sid+"'"
        return jdbctemp.query(sql, new BeanPropertyRowMapper<HospitalColorectalCancerDiagnoseInformationVo>(HospitalColorectalCancerDiagnoseInformationVo.class), id);

    }

    @Override
    public List<HospitalInformationDiagnoseEvaluationVo> queryDiagnoseEvaluationbyId(Integer id, Integer communityDeptId, Integer areaDeptId) {
        String sql = "select hr.*,hr.check_date as checkDateToString from hospital_information_diagnose_evaluation  hr where colorectal_cancer_diagnose_information_id=?";//+id+" and sid='"+sid+"'"
        return jdbctemp.query(sql, new BeanPropertyRowMapper<HospitalInformationDiagnoseEvaluationVo>(HospitalInformationDiagnoseEvaluationVo.class), id);
    }



    @Override
    public void saveInformationComplicationsVos(final List<HospitalCancerInformationComplicationsVo> hospitalCancerInformationComplicationsVos) {
        String sql = "insert into hospital_cancer_information_complications(colorectal_cancer_diagnose_information_id,complications_date,complications_code,complications_code_other,update_time,date_created) values(?,?,?,?,now(),now())";
        jdbctemp.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i)
                    throws SQLException {
                HospitalCancerInformationComplicationsVo hospitalCancerInformationComplicationsVo = hospitalCancerInformationComplicationsVos.get(i);
                ps.setObject(1, hospitalCancerInformationComplicationsVo.getColorectalCancerDiagnoseInformationId());
                ps.setObject(2, StringUtils.isEmpty(hospitalCancerInformationComplicationsVo.getComplicationsDateToString())?null:hospitalCancerInformationComplicationsVo.getComplicationsDateToString());
                ps.setObject(3, hospitalCancerInformationComplicationsVo.getComplicationsCode());
                ps.setObject(4, hospitalCancerInformationComplicationsVo.getComplicationsCodeOther());
            }
            @Override
            public int getBatchSize() {
                // TODO Auto-generated method stub
                return hospitalCancerInformationComplicationsVos.size();
            }
        });
    }

    @Override
    public List<HospitalCancerInformationComplicationsVo> queryInformationComplicationsVosbyId(Integer id, Integer communityDeptId, Integer areaDeptId) {
        String sql = "select hr.*,hr.complications_date as complicationsDateToString from hospital_cancer_information_complications  hr where colorectal_cancer_diagnose_information_id=?";//+id+" and sid='"+sid+"'"
        return jdbctemp.query(sql, new BeanPropertyRowMapper<HospitalCancerInformationComplicationsVo>(HospitalCancerInformationComplicationsVo.class), id);

    }


    @Override
    public int saveDiagnose(final HospitalCancerDiagnoseVo hospitalCancerDiagnoseVo) {
        log.info("@dao 新增癌症诊断表 hospitalCancerDiagnoseVo={}.", hospitalCancerDiagnoseVo.toString());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("insert into hospital_cancer_diagnose(sid,write_table_date,ys_cancer_report_date,check_years,investigator_code,tb_table_person,quality_control_person,date_created,update_time,dept_code,create_user,stage,item1,item2_1,item2_2,item3,item3a,yf_cancer_diagnosis_date,item5_1,item5_2,item5_3,item5_4,item5_5,information_zl_person) values(?,?,?,?,?,?,?,now(),now(),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        jdbctemp.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(stringBuffer.toString(), Statement.RETURN_GENERATED_KEYS);
                ps.setObject(1, hospitalCancerDiagnoseVo.getSid());
                ps.setObject(2, hospitalCancerDiagnoseVo.getWriteTableDate());
                ps.setObject(3, hospitalCancerDiagnoseVo.getYsCancerReportDate());
                ps.setObject(4, hospitalCancerDiagnoseVo.getCheckYears());
                ps.setObject(5, hospitalCancerDiagnoseVo.getInvestigatorCode());
                ps.setObject(6, hospitalCancerDiagnoseVo.getTbTablePerson());
                ps.setObject(7, hospitalCancerDiagnoseVo.getQualityControlPerson());
                ps.setObject(8, hospitalCancerDiagnoseVo.getDeptCode());
                ps.setObject(9, hospitalCancerDiagnoseVo.getCreateUser());
                ps.setObject(10, hospitalCancerDiagnoseVo.getStage());
                ps.setObject(11, hospitalCancerDiagnoseVo.getItem1());
                ps.setObject(12, hospitalCancerDiagnoseVo.getItem21());
                ps.setObject(13, hospitalCancerDiagnoseVo.getItem22());
                ps.setObject(14, hospitalCancerDiagnoseVo.getItem3());
                ps.setObject(15, hospitalCancerDiagnoseVo.getItem3a());
                ps.setObject(16, hospitalCancerDiagnoseVo.getYfCancerDiagnosisDate());
                ps.setObject(17, hospitalCancerDiagnoseVo.getItem51());
                ps.setObject(18, hospitalCancerDiagnoseVo.getItem52());
                ps.setObject(19, hospitalCancerDiagnoseVo.getItem53());
                ps.setObject(20, hospitalCancerDiagnoseVo.getItem54());
                ps.setObject(21, hospitalCancerDiagnoseVo.getItem55());
                ps.setObject(22, hospitalCancerDiagnoseVo.getInformationZlPerson());
                return ps;
            }

        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public List<HospitalCancerDiagnoseVo> queryDiagnoseById(Integer id) {
        String sql = "select hr.item2_1 as item21,hr.item2_2 as item22,hr.item5_1 as item51,hr.item5_2 as item52,hr.item5_3 as item53,hr.item5_4 as item54,hr.item5_5 as item55,hr.*,hr.write_table_date as writeTableDateToString,hr.ys_cancer_report_date as ysCancerReportDateToString,hr.yf_cancer_diagnosis_date as yfCancerDiagnosisDateToString from hospital_cancer_diagnose  hr where id=?";//+id+" and sid='"+sid+"'"
        return jdbctemp.query(sql, new BeanPropertyRowMapper<HospitalCancerDiagnoseVo>(HospitalCancerDiagnoseVo.class), id);

    }

    @Override
    public void updateDiagnose(HospitalCancerDiagnoseVo hospitalCancerDiagnoseVo) {
        String sql = "update hospital_cancer_diagnose set write_table_date=? ,ys_cancer_report_date=? ,check_years=? ,investigator_code=? ,tb_table_person=? ,quality_control_person=? ,item1=? ,item2_1=? ,item2_2=? ,item3=? ,item3a=? ,yf_cancer_diagnosis_date=? ,item5_1=? ,item5_2=? ,item5_3=? ,item5_4=? ,item5_5=? ,Information_zl_person=?,update_time=now() where  id=?";
        jdbctemp.update(sql, new Object[]{hospitalCancerDiagnoseVo.getWriteTableDate(), hospitalCancerDiagnoseVo.getYsCancerReportDate(),hospitalCancerDiagnoseVo.getCheckYears(),hospitalCancerDiagnoseVo.getInvestigatorCode(),hospitalCancerDiagnoseVo.getTbTablePerson(),hospitalCancerDiagnoseVo.getQualityControlPerson(),hospitalCancerDiagnoseVo.getItem1(),hospitalCancerDiagnoseVo.getItem21(),hospitalCancerDiagnoseVo.getItem22(),hospitalCancerDiagnoseVo.getItem3(),hospitalCancerDiagnoseVo.getItem3a(),hospitalCancerDiagnoseVo.getYfCancerDiagnosisDate(),hospitalCancerDiagnoseVo.getItem51(),hospitalCancerDiagnoseVo.getItem52(),hospitalCancerDiagnoseVo.getItem53(), hospitalCancerDiagnoseVo.getItem54(),hospitalCancerDiagnoseVo.getItem55(),hospitalCancerDiagnoseVo.getInformationZlPerson(),hospitalCancerDiagnoseVo.getId()});

    }

    @Override
    public int saveTreatmentInformation(final HospitalColorectalCancerTreatmentInformationVo hospitalColorectalCancerTreatmentInformationVo) {
        log.info("@dao 结直肠癌治疗信息摘录表 hospitalColorectalCancerTreatmentInformationVo={}.", hospitalColorectalCancerTreatmentInformationVo.toString());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("insert into hospital_colorectal_cancer_treatment_information(sid,tb_table_date,dept_code,check_years,investigator_code,tb_table_person,quality_control_person,date_created,update_time,create_user,stage,excerpt_purpose,item1,item2,item2_1,item3,item3_1,item4,item4_1,item5,item5_1,item6,item6_1,item6_2) values(?,?,?,?,?,?,?,now(),now(),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        jdbctemp.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(stringBuffer.toString(), Statement.RETURN_GENERATED_KEYS);
                ps.setObject(1, hospitalColorectalCancerTreatmentInformationVo.getSid());
                ps.setObject(2, StringUtils.isEmpty(hospitalColorectalCancerTreatmentInformationVo.getTbTableDateToString())?null:hospitalColorectalCancerTreatmentInformationVo.getTbTableDateToString());
                ps.setObject(3, hospitalColorectalCancerTreatmentInformationVo.getDeptCode());
                ps.setObject(4, hospitalColorectalCancerTreatmentInformationVo.getCheckYears());
                ps.setObject(5, hospitalColorectalCancerTreatmentInformationVo.getInvestigatorCode());
                ps.setObject(6, hospitalColorectalCancerTreatmentInformationVo.getTbTablePerson());
                ps.setObject(7, hospitalColorectalCancerTreatmentInformationVo.getQualityControlPerson());
                ps.setObject(8, hospitalColorectalCancerTreatmentInformationVo.getCreateUser());
                ps.setObject(9, hospitalColorectalCancerTreatmentInformationVo.getStage());
                ps.setObject(10, hospitalColorectalCancerTreatmentInformationVo.getExcerptPurpose());
                ps.setObject(11, hospitalColorectalCancerTreatmentInformationVo.getItem1());
                ps.setObject(12, hospitalColorectalCancerTreatmentInformationVo.getItem2());
                ps.setObject(13, hospitalColorectalCancerTreatmentInformationVo.getItem21());
                ps.setObject(14, hospitalColorectalCancerTreatmentInformationVo.getItem3());
                ps.setObject(15, StringUtils.isEmpty(hospitalColorectalCancerTreatmentInformationVo.getItem31ToString())?null:hospitalColorectalCancerTreatmentInformationVo.getItem31ToString());
                ps.setObject(16, hospitalColorectalCancerTreatmentInformationVo.getItem4());
                ps.setObject(17, StringUtils.isEmpty(hospitalColorectalCancerTreatmentInformationVo.getItem41ToString())?null:hospitalColorectalCancerTreatmentInformationVo.getItem41ToString());
                ps.setObject(18, hospitalColorectalCancerTreatmentInformationVo.getItem5());
                ps.setObject(19, StringUtils.isEmpty(hospitalColorectalCancerTreatmentInformationVo.getItem51ToString())?null:hospitalColorectalCancerTreatmentInformationVo.getItem51ToString());
                ps.setObject(20, hospitalColorectalCancerTreatmentInformationVo.getItem6());
                ps.setObject(21, hospitalColorectalCancerTreatmentInformationVo.getItem61());
                ps.setObject(22, hospitalColorectalCancerTreatmentInformationVo.getItem62());
                return ps;
            }

        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void saveSurgicalOperations(final List<HospitalCancerSurgicalOperationVo> hospitalCancerSurgicalOperationVos) {
        String sql = "insert into hospital_cancer_surgical_operation(colorectal_cancer_treatment_information_id,surgical_operation_code,finsh_date,date_created,update_time,surgical_operation_code_other) values(?,?,?,now(),now(),?)";
        jdbctemp.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i)
                    throws SQLException {
                HospitalCancerSurgicalOperationVo hospitalCancerSurgicalOperationVo = hospitalCancerSurgicalOperationVos.get(i);
                ps.setObject(1, hospitalCancerSurgicalOperationVo.getColorectalCancerTreatmentInformationId());
                ps.setObject(2, hospitalCancerSurgicalOperationVo.getSurgicalOperationCode());
                ps.setObject(3, hospitalCancerSurgicalOperationVo.getFinshDateToString());
                ps.setObject(4, hospitalCancerSurgicalOperationVo.getSurgicalOperationCodeOther());
            }
            @Override
            public int getBatchSize() {
                // TODO Auto-generated method stub
                return hospitalCancerSurgicalOperationVos.size();
            }
        });
    }

    @Override
    public void saveTreatmentInformations(final List<HospitalCancerTreatmentInformationVo> hospitalCancerTreatmentInformationVos) {
        String sql = "insert into hospital_cancer_treatment_information(colorectal_cancer_treatment_information_id,doctor_name,medical_institution_name,address,email,fax,tel_phone1,tel_phone2,bl_number,date_created,update_time) values(?,?,?,?,?,?,?,?,?,now(),now())";
        jdbctemp.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i)
                    throws SQLException {
                HospitalCancerTreatmentInformationVo hospitalCancerTreatmentInformationVo = hospitalCancerTreatmentInformationVos.get(i);
                ps.setObject(1, hospitalCancerTreatmentInformationVo.getColorectalCancerTreatmentInformationId());
                ps.setObject(2, hospitalCancerTreatmentInformationVo.getDoctorName());
                ps.setObject(3, hospitalCancerTreatmentInformationVo.getMedicalInstitutionName());
                ps.setObject(4, hospitalCancerTreatmentInformationVo.getAddress());
                ps.setObject(5, hospitalCancerTreatmentInformationVo.getEmail());
                ps.setObject(6, hospitalCancerTreatmentInformationVo.getFax());
                ps.setObject(7, hospitalCancerTreatmentInformationVo.getTelPhone1());
                ps.setObject(8, hospitalCancerTreatmentInformationVo.getTelPhone2());
                ps.setObject(9, hospitalCancerTreatmentInformationVo.getBlNumber());
            }
            @Override
            public int getBatchSize() {
                // TODO Auto-generated method stub
                return hospitalCancerTreatmentInformationVos.size();
            }
        });
    }

    @Override
    public void deleteAddendumByC3Id(Integer id, String table) {
        String sql = "DELETE FROM " + table + " where colorectal_cancer_diagnose_information_id=?";
        int result = jdbctemp.update(sql, id);
    }


    @Override
    public void updateReportC3ByID(HospitalColorectalCancerDiagnoseInformationVo hospitalColorectalCancerDiagnoseInformationVo) {
        log.info("@dao 修改结直肠癌诊断信息摘录表c3 hospitalColorectalCancerDiagnoseInformationVo={}.", hospitalColorectalCancerDiagnoseInformationVo.toString());
        String sql ="UPDATE hospital_colorectal_cancer_diagnose_information " +
                " SET  " +
                " `excerpts_date` = ?, " +
                " `excerpt_purpose` = ?, " +
                " `cf_screening` = ?, " +
                " `excerpt_person_id` = ?,  " +
                " `dept_code` = ?, " +
                " `research_year` = ?, " +
                " `investigator_code` = ?, " +
                " `tb_table_person` = ?, " +
                " `quality_control_person` = ?, " +
                " `item1` = ?, " +
                " `item2_1` = ?, " +
                " `item2_2` = ?," +
                " `item2_3` = ?," +
                " `item2_3_other` = ?," +
                " `item3_check_date` = ?," +
                " `item3_1` = ?," +
                " `item3_2` = ?," +
                " `item3_3` = ?," +
                " `item4_1` = ?," +
                " `item4_2_1` = ?," +
                " `item4_2_2` = ?," +
                " `item4_3_1` = ?," +
                " `item4_3_2` = ?," +
                " `item4_4` = ?," +
                " `item4_5` = ?," +
                " `item4_6` = ?," +
                " `item6_1` = ?," +
                " `item7_1` = ?," +
                " `item8a_1` = ?," +
                " `item8a_2` = ?," +
                " `item8b_1` = ?," +
                " `item8b_2` = ?," +
                " `item8c_1` = ?," +
                " `item8c_1_1` = ?," +
                " `item8c_1_2` = ?," +
                " `item8c_1_3` = ?," +
                " `item8c_1_4` = ?," +
                " `item8c_1_5` = ?," +
                " `item8d_1_1` = ?," +
                " `item8d_1_2` = ?," +
                " `item8e_1_1` = ?," +
                " `item9` = ?," +
                " `item10` = ?," +
                " `item11` = ?," +
                " `report_url` = ?," +
                " `item12_1` = ?," +
                " `item12_2` = ?," +
                " `item12_3` = ?," +
                " `item12_4` = ?," +
                " `item12_5` = ?," +
                " `item13_1` = ?," +
                " `item13_2` = ?," +
                " `item13_3` = ?," +
                " `item13_4` = ?," +
                " `item13_5` = ?," +
                " `item13_6` = ?," +
                " `item13_7` = ?," +
                " `item13_8` = ?," +
                " `item13_9` = ?," +
                " `item13_10` = ?," +
                " `item13_11` = ?," +
                " `item14_1` = ?," +
                " `item14_2` = ?," +
                " `item14_3` = ?," +
                " `item14_4` = ?," +
                " `item14_5` = ?," +
                " `item14_6` = ?," +
                " `item14_7` = ?," +
                " `item14_8` = ?," +
                " `item14_9` = ?," +
                " `item14_10` = ?," +
                " `item15_1` = ?," +
                " `item15_2` = ?," +
                " `item15_3` = ?," +
                " `item15_4` = ?," +
                " `item15_5` = ?," +
                " `item15_6` = ?," +
                " `item15_7` = ?," +
                " `item16a_1` = ?," +
                " `item16a_1_1` = ?," +
                " `item16a_1_2` = ?," +
                " `item16a_1_3` = ?," +
                " `item16b_1` = ?," +
                " `item16b_1_1` = ?," +
                " `item16b_2` = ?," +
                " `item16b_1_2` = ?," +
                " `item16b_1_3` = ?," +
                " `item17_1_1` = ?," +
                " `item17_1_2` = ?," +
                " `item17_1_3` = ?," +
                " `item18` = ?," +
                " `update_time` = now()" +
                " WHERE " +
                "`id` = ?;" ;

        jdbctemp.update(sql, new Object[]{StringUtils.isEmpty(hospitalColorectalCancerDiagnoseInformationVo.getExcerptsDateToString())?null:hospitalColorectalCancerDiagnoseInformationVo.getExcerptsDateToString(),
                            hospitalColorectalCancerDiagnoseInformationVo.getExcerptPurpose(),
                            hospitalColorectalCancerDiagnoseInformationVo.getCfScreening(),
                            hospitalColorectalCancerDiagnoseInformationVo.getExcerptPersonId(),
                            hospitalColorectalCancerDiagnoseInformationVo.getDeptCode(),
                            hospitalColorectalCancerDiagnoseInformationVo.getResearchYear(),
                            hospitalColorectalCancerDiagnoseInformationVo.getInvestigatorCode(),
                            hospitalColorectalCancerDiagnoseInformationVo.getTbTablePerson(),
                            hospitalColorectalCancerDiagnoseInformationVo.getQualityControlPerson(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem1(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem21(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem22(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem23(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem23Other(),
                            StringUtils.isEmpty(hospitalColorectalCancerDiagnoseInformationVo.getItem3CheckDateToString())?null:hospitalColorectalCancerDiagnoseInformationVo.getItem3CheckDateToString(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem31(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem32(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem33(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem41(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem421(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem422(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem431(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem432(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem44(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem45(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem46(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem61(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem71(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem8a1(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem8a2(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem8b1ToString(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem8b2(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem8c1(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem8c11(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem8c12(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem8c13(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem8c14(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem8c15(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem8d11ToString(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem8d12(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem8e11(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem9ToString(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem10(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem11(),
                            hospitalColorectalCancerDiagnoseInformationVo.getReportUrlToString(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem121(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem122(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem123(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem124(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem125(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem131(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem132(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem133(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem134(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem135(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem136(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem137(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem138(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem139(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem1310(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem1311(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem141(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem142(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem143(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem144(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem145(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem146(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem147(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem148(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem149(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem1410(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem151(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem152(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem153(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem154(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem155(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem157(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem156(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem16a1(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem16a11(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem16a12(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem16a13(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem16b1(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem16b11(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem16b2(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem16b12(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem16b13(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem1711(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem1712(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem1713(),
                            hospitalColorectalCancerDiagnoseInformationVo.getItem18(),
                            hospitalColorectalCancerDiagnoseInformationVo.getId()});

    }

    /**
     *修改通过肠镜患癌终点事件相关联表
     */
    @Override
    public void delCancerRecordForCJResult(Integer colonoscopyRecordId,String sid){
        //查询
        String sql = "SELECT t.* from hospital_cancer_record t WHERE t.colonoscopy_record_id = ? and t.sid= ?";
        List<HospitalCancerRecordPO> cancerRecordList = jdbctemp.query(sql, new Object[]{colonoscopyRecordId,sid},new BeanPropertyRowMapper<HospitalCancerRecordPO>(HospitalCancerRecordPO.class));
        HospitalCancerRecordPO cancerRecordPO = new HospitalCancerRecordPO();
        if (cancerRecordList != null && cancerRecordList.size() > 0) {
            cancerRecordPO = cancerRecordList.get(0);
            //删除c1-c4的待办
            String sql1 = "delete from hospital_todo_event where `type`=? and data_id=? ";
            jdbctemp.update(sql1,new Object[]{Constans.PERSON_TODO_EVENT_TYPE20,cancerRecordPO.getId()});
            jdbctemp.update(sql1,new Object[]{Constans.PERSON_TODO_EVENT_TYPE21,cancerRecordPO.getId()});
            jdbctemp.update(sql1,new Object[]{Constans.PERSON_TODO_EVENT_TYPE22,cancerRecordPO.getId()});
            jdbctemp.update(sql1,new Object[]{Constans.PERSON_TODO_EVENT_TYPE23,cancerRecordPO.getId()});
            //删除c1-c4表单数据
            Integer cancer_report_id = cancerRecordPO.getCancerReportId();
            Integer cancer_diagnose_id = cancerRecordPO.getCancerDiagnoseId();
            Integer colorectal_cancer_diagnose_information_id = cancerRecordPO.getColorectalCancerDiagnoseInformationId();
            Integer colorectal_cancer_treatment_information_id = cancerRecordPO.getColorectalCancerTreatmentInformationId();
            //删除终点事件大表
            jdbctemp.update("delete from hospital_cancer_record  WHERE id = ?",new Object[]{cancerRecordPO.getId()});
            //c1
            jdbctemp.update("delete from hospital_cancer_report where id=?",new Object[]{cancer_report_id});
            jdbctemp.update("delete from hospital_cancer_report_message where cancer_report_id=?",new Object[]{cancer_report_id});
            //c2
            jdbctemp.update("delete from hospital_cancer_diagnose where id=?",new Object[]{cancer_diagnose_id});
            //c3
            jdbctemp.update("delete from hospital_colorectal_cancer_diagnose_information where id=?",new Object[]{colorectal_cancer_diagnose_information_id});
            jdbctemp.update("delete from hospital_cancer_information_complications where colorectal_cancer_diagnose_information_id=?",new Object[]{colorectal_cancer_diagnose_information_id});
            jdbctemp.update("delete from hospital_information_diagnose_evaluation where colorectal_cancer_diagnose_information_id=?",new Object[]{colorectal_cancer_diagnose_information_id});
            //c4
            jdbctemp.update("delete from hospital_colorectal_cancer_treatment_information  where id=?",new Object[]{colorectal_cancer_treatment_information_id});
            jdbctemp.update("delete from hospital_cancer_treatment_information where colorectal_cancer_treatment_information_id=?",new Object[]{colorectal_cancer_treatment_information_id});
            jdbctemp.update("delete from hospital_cancer_surgical_operation where colorectal_cancer_treatment_information_id=?",new Object[]{colorectal_cancer_treatment_information_id});
        }
    }

}
