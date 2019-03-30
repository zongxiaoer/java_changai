package com.yuntongxun.itsys.base.dao.impl;

import com.yuntongxun.itsys.base.dao.ColonoscopyPathologyResultDao;
import com.yuntongxun.itsys.base.po.ColonoscopyPathologyResult;
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
 * 结肠镜病理结果表DaoImpl
 */
@Repository
public class ColonoscopyPathologyResultDaoImpl implements ColonoscopyPathologyResultDao {

    @Autowired
    private JdbcTemplate jdbc;
    private final Logger log = LogManager.getLogger(ColonoscopyPathologyResultDaoImpl.class);

    /**
     * 添加肠镜病历结果
     * @param colonoscopyPathologyResult
     * @return
     */
    @Override
    public Integer addColonoscopyPathologyResult(final ColonoscopyPathologyResult colonoscopyPathologyResult) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final StringBuffer sql = new StringBuffer("insert into hospital_colonoscopy_pathology_result("+
                "sid,stage,colonoscopy_result_id,survey_date,item1,item2,item3,diagnosis_date,doctor_sign"+
                ",area_dept_id,community_dept_id,date_created,update_time,editoperation_source,operation_source_id"+
                ")values(" +
                "?,?,?,?,?,?,?,?,?,?,?"+
                ",now(),now(),?,?)");
        jdbc.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, colonoscopyPathologyResult.getSid());
                ps.setInt(2, colonoscopyPathologyResult.getStage());
                ps.setObject(3, colonoscopyPathologyResult.getColonoscopyResultId());
                if(colonoscopyPathologyResult.getSurveyDate()!=null) {
                    ps.setDate(4, new java.sql.Date(colonoscopyPathologyResult.getSurveyDate().getTime()));
                }else{
                    ps.setDate(4,null);
                }
                ps.setObject(5, colonoscopyPathologyResult.getItem1());
                ps.setObject(6, colonoscopyPathologyResult.getItem2());
                ps.setObject(7, colonoscopyPathologyResult.getItem3());
                if(colonoscopyPathologyResult.getDiagnosisDate()!=null) {
                    ps.setDate(8, new java.sql.Date(colonoscopyPathologyResult.getDiagnosisDate().getTime()));
                }else{
                    ps.setDate(8,null);
                }
                ps.setObject(9, colonoscopyPathologyResult.getDoctorSign());
                ps.setObject(10, colonoscopyPathologyResult.getAreaDeptId());
                ps.setObject(11, colonoscopyPathologyResult.getCommunityDeptId());
                ps.setObject(12, colonoscopyPathologyResult.getEditoperationSource());
                ps.setObject(13, colonoscopyPathologyResult.getColonoscopyRecordId());
                return ps;
            }
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public ColonoscopyPathologyResult queryColonoscopyPathologyResultById(Integer id, String sid) {
        ColonoscopyPathologyResult result;
        try {
            String sql = "select * from hospital_colonoscopy_pathology_result  where id=? and sid=? order by date_created asc";//+id+" and sid='"+sid+"'"
            result=jdbc.queryForObject(sql,new BeanPropertyRowMapper<ColonoscopyPathologyResult>(ColonoscopyPathologyResult.class),id,sid);
        } catch (EmptyResultDataAccessException e) {
            log.info("queryColonoscopyPathologyResultById hospital_colonoscopy_pathology_result is error data is null");
            return null;
        }catch (IncorrectResultSizeDataAccessException e){
            log.info("queryColonoscopyPathologyResultById hospital_colonoscopy_pathology_result is error data size is >1");
            return null;
        }
        return result;
    }

    @Override
    public List<ColonoscopyPathologyResult> queryColonoscopyPathologyResultById(String sid,Integer colonoscopyResultId) {
        //增加根据colonoscopy_result_id 	结肠镜结果记录表ID字段查询
        String sql = "select * from hospital_colonoscopy_pathology_result where sid=? and colonoscopy_result_id=?";//+id+" and sid='"+sid+"'"
        //log.info(sql);
        return jdbc.query(sql, new BeanPropertyRowMapper<ColonoscopyPathologyResult>(ColonoscopyPathologyResult.class),sid,colonoscopyResultId);
    }

    @Override
    public void deleteByIDs(String substring) {
        String sql = "DELETE FROM hospital_colonoscopy_pathology_diagnosis_record WHERE id in("+substring+")";
        int result = jdbc.update(sql);
    }

    @Override
    public Integer updateColonoscopyPathologyResult(ColonoscopyPathologyResult colonoscopyPathologyResult) {
        String sql = "UPDATE hospital_colonoscopy_pathology_result set "+
                "survey_date=?,item1=?,item2=?,item3=?,diagnosis_date=?,doctor_sign=?"+
                ",update_time=now(),apply_status=?,edit_status=?,approval_status=? where id=?";
        log.info("@DaoImpl 【危险因素数据-新增】 sql={}", sql);
        int result = jdbc.update(sql,
                colonoscopyPathologyResult.getSurveyDate()!=null?new java.sql.Date(colonoscopyPathologyResult.getSurveyDate().getTime()):null,
                colonoscopyPathologyResult.getItem1(),
                colonoscopyPathologyResult.getItem2(),
                colonoscopyPathologyResult.getItem3(),
                colonoscopyPathologyResult.getDiagnosisDate()!=null?new java.sql.Date(colonoscopyPathologyResult.getDiagnosisDate().getTime()):null,
                colonoscopyPathologyResult.getDoctorSign(),
                colonoscopyPathologyResult.getApplyStatus(),
                colonoscopyPathologyResult.getEditStatus(),
                colonoscopyPathologyResult.getApprovalStatus(),
                colonoscopyPathologyResult.getId());
        log.info("@Dao addRiskFactor End  ");
        return result;
    }


    /**
     * 删除病理信息
     * @param
     * @param id
     * @return
     */
    @Override
    public void delPathologyById(Integer id) {
        if(id != null && id>0) {
            String sql = "DELETE FROM hospital_colonoscopy_pathology_result WHERE id = ?";
            jdbc.update(sql, id);

            String sql1 = "DELETE FROM hospital_colonoscopy_pathology_diagnosis_record WHERE pathology_result_id = ?";
            jdbc.update(sql1, id);
        }
    }
}
