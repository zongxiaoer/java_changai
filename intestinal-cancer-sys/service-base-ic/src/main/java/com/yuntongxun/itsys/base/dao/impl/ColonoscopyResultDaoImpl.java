package com.yuntongxun.itsys.base.dao.impl;

import com.yuntongxun.itsys.base.dao.ColonoscopyResultDao;
import com.yuntongxun.itsys.base.po.ColonoscopyResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
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
import java.util.List;

/**
 * 肠镜报告记录DaoImpl
 * maxiang
 */
@Repository
public class ColonoscopyResultDaoImpl implements ColonoscopyResultDao{

    @Autowired
    private JdbcTemplate jdbc;
    private final Logger log = LogManager.getLogger(ColonoscopyResultDaoImpl.class);
    /**
     * 添加肠镜结果记录
     * @param colonoscopyResult
     * @return
     */
    @Override
    public Integer addColonoscopyResult(final ColonoscopyResult colonoscopyResult) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final StringBuffer sql = new StringBuffer("insert into hospital_colonoscopy_result("+
                "sid,stage,survey_date,item_2_1,item_2_2,item_3_1,item_3_2,item_3_3,item_3_4"+
                ",item_3_5,item_3_6_1,item_3_6_2,item_3_6_3,item_3_6_3_1,item_3_6_3_2,item_3_6_4"+
                ",item_3_6_4_other,item_3_7,item_3_7_a,item_3_8,item_3_8_other,other_lesions,pathology"+
                ",area_dept_id,community_dept_id,diagnosis_doctor,endoscopic_diagnosis,item_2_1_a,item_2_1_b,item_2_1_c,item_2_1_d,item_2_1_e,date_created,update_time,editoperation_source,operation_source_id,image_path"+
                ")values(" +
                "?,?,?,?,?,?,?,?,?,?"+",?,?,?,?,?,?,?,?,?,?,?"+",?,?,?,?,?,?"+",?,?,?,?,?"+
                ",now(),now(),?,?,?)");
        jdbc.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, colonoscopyResult.getSid());
                ps.setInt(2, colonoscopyResult.getStage());
                if(colonoscopyResult.getSurveyDate()!=null) {
                    ps.setDate(3, new java.sql.Date(colonoscopyResult.getSurveyDate().getTime()));
                }else{
                    ps.setDate(3, null);
                }
                ps.setObject(4, colonoscopyResult.getItem_2_1());
                ps.setObject(5, colonoscopyResult.getItem_2_2());
                ps.setObject(6, colonoscopyResult.getItem_3_1());
                ps.setObject(7, colonoscopyResult.getItem_3_2());
                ps.setObject(8, colonoscopyResult.getItem_3_3());
                ps.setObject(9, colonoscopyResult.getItem_3_4());
                ps.setObject(10, colonoscopyResult.getItem_3_5());
                ps.setObject(11, colonoscopyResult.getItem_3_6_1());
                ps.setObject(12, colonoscopyResult.getItem_3_6_2());
                ps.setObject(13, colonoscopyResult.getItem_3_6_3());
                ps.setObject(14, colonoscopyResult.getItem_3_6_3_1());
                ps.setObject(15, colonoscopyResult.getItem_3_6_3_2());
                ps.setObject(16, colonoscopyResult.getItem_3_6_4());
                ps.setObject(17, colonoscopyResult.getItem_3_6_4_other());
                ps.setObject(18, colonoscopyResult.getItem_3_7());
                ps.setObject(19, colonoscopyResult.getItem_3_7_a());
                ps.setObject(20, colonoscopyResult.getItem_3_8());
                ps.setObject(21, colonoscopyResult.getItem_3_8_other());
                ps.setObject(22, colonoscopyResult.getOtherLesions());
                ps.setObject(23, colonoscopyResult.getPathology());
                ps.setObject(24, colonoscopyResult.getAreaDeptId());
                ps.setObject(25, colonoscopyResult.getCommunityDeptId());
                ps.setObject(26, colonoscopyResult.getDiagnosisDoctor());
                ps.setObject(27, colonoscopyResult.getEndoscopicDiagnosis());

                ps.setObject(28, colonoscopyResult.getItem_2_1_a());
                ps.setObject(29, colonoscopyResult.getItem_2_1_b());
                ps.setObject(30, colonoscopyResult.getItem_2_1_c());
                ps.setObject(31, colonoscopyResult.getItem_2_1_d());
                ps.setObject(32, colonoscopyResult.getItem_2_1_e());
                ps.setObject(33, colonoscopyResult.getEditoperationSource());
                ps.setObject(34, colonoscopyResult.getOperationSourceId());
                ps.setObject(35, colonoscopyResult.getImagePath());
                return ps;
            }
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public ColonoscopyResult queryHospitalColonoscopyResultById(Integer id, String sid) {
        ColonoscopyResult result;
        try {
            String sql = "select * from hospital_colonoscopy_result  where id=? and sid=?";//+id+" and sid='"+sid+"'"
             result=jdbc.queryForObject(sql,new BeanPropertyRowMapper<ColonoscopyResult>(ColonoscopyResult.class),id,sid);
        } catch (EmptyResultDataAccessException e) {
            log.info("queryHospitalColonoscopyResultById hospital_colonoscopy_result is error data is null");
            return null;
        }catch (IncorrectResultSizeDataAccessException e){
            log.info("queryHospitalColonoscopyResultById hospital_colonoscopy_result is error data size is >1");
            return null;
        }
        return result;
    }

    @Override
    public  List<ColonoscopyResult> queryById(Integer id) {
        String sql = "select * from hospital_colonoscopy_result where id=?";
        return jdbc.query(sql, new BeanPropertyRowMapper<ColonoscopyResult>(ColonoscopyResult.class),id);

    }

    /**
     * 修改肠镜表单
     * @param colonoscopyResult
     * @return
     */
    @Override
    public Integer updateColonoscopyResult(ColonoscopyResult colonoscopyResult) {
        String sql = "UPDATE hospital_colonoscopy_result set "+
                "survey_date=?,item_2_1=?,item_2_2=?,item_3_1=?,item_3_2=?,item_3_3=?,item_3_4"+
                "=?,item_3_5=?,item_3_6_1=?,item_3_6_2=?,item_3_6_3=?,item_3_6_3_1=?,item_3_6_3_2=?,item_3_6_4"+
                "=?,item_3_6_4_other=?,item_3_7=?,item_3_7_a=?,item_3_8=?,item_3_8_other=?,other_lesions=?,pathology"+
                "=?,diagnosis_doctor=?,endoscopic_diagnosis=?,item_2_1_a=?,item_2_1_b=?,item_2_1_c=?,item_2_1_d=?,item_2_1_e=?"+
                ",update_time=now(),apply_status=?,edit_status=?,approval_status=?,image_Path=? where id=?";
        log.info("@DaoImpl updateColonoscopyResult【肠镜结果表单-修改】 sql={}", sql);
        int result = jdbc.update(sql, colonoscopyResult.getSurveyDate()!=null?new java.sql.Date(colonoscopyResult.getSurveyDate().getTime()):null,
         colonoscopyResult.getItem_2_1(),
        colonoscopyResult.getItem_2_2(),
        colonoscopyResult.getItem_3_1(),
        colonoscopyResult.getItem_3_2(),
        colonoscopyResult.getItem_3_3(),
        colonoscopyResult.getItem_3_4(),
        colonoscopyResult.getItem_3_5(),
        colonoscopyResult.getItem_3_6_1(),
        colonoscopyResult.getItem_3_6_2(),
        colonoscopyResult.getItem_3_6_3(),
        colonoscopyResult.getItem_3_6_3_1(),
        colonoscopyResult.getItem_3_6_3_2(),
        colonoscopyResult.getItem_3_6_4(),
        colonoscopyResult.getItem_3_6_4_other(),
        colonoscopyResult.getItem_3_7(),
        colonoscopyResult.getItem_3_7_a(),
        colonoscopyResult.getItem_3_8(),
        colonoscopyResult.getItem_3_8_other(),
        colonoscopyResult.getOtherLesions(),
        colonoscopyResult.getPathology(),
        colonoscopyResult.getDiagnosisDoctor(),
        colonoscopyResult.getEndoscopicDiagnosis(),
        colonoscopyResult.getItem_2_1_a(),
        colonoscopyResult.getItem_2_1_b(),
        colonoscopyResult.getItem_2_1_c(),
        colonoscopyResult.getItem_2_1_d(),
        colonoscopyResult.getItem_2_1_e(), colonoscopyResult.getApplyStatus(),colonoscopyResult.getEditStatus(),colonoscopyResult.getApprovalStatus(),colonoscopyResult.getImagePath(),colonoscopyResult.getId());
        log.info("@Dao updateColonoscopyResult End  ");
        return result;
    }

    @Override
    public Integer updateUrlByID(String pathUrl, Integer id) {
        log.info("@Dao updateUrlByID 更新肠镜上传地址：参数 pathUrl={},id={} ", id,pathUrl);
        String sql = "UPDATE hospital_colonoscopy_result SET image_Path=?,update_time=now() WHERE id = ?";
        int result = jdbc.update(sql,pathUrl,id);
        log.info("@Dao updateUrlByID End  ");
        return result;
    }
}
