package com.yuntongxun.itsys.base.dao.impl;

import com.yuntongxun.itsys.base.dao.ViolationSchemeDao;
import com.yuntongxun.itsys.base.po.ColonoscopyPathologyResult;
import com.yuntongxun.itsys.base.po.ViolationScheme;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 违反方案DaoImpl
 */
@Repository
public class ViolationSchemeDaoImpl implements ViolationSchemeDao {

    @Autowired
    private JdbcTemplate jdbc;
    private final Logger log = LogManager.getLogger(ViolationSchemeDaoImpl.class);

    /**
     * 添加违反方案
     *
     * @param violationScheme
     * @return
     */
    @Override
    public Integer addViolationScheme(final ViolationScheme violationScheme) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final StringBuffer sql = new StringBuffer("insert into hospital_violation_scheme(" +
                "sid,stage,tb_date,tbr_name,zkz_name,dept_code,investigator_code,check_year" +
                ",item_1a_1,item_1a_2,item_1a_2_id,item_1a_3,item_1a_4,item_1a_5,item_1a_5_des" +
                ",item_1a_6,item_1a_7,item_1a_8,item_1a_9,item_1a_9_cause,item_1a_9_des" +
                ",item_1a_10,item_2a_1,item_2b_1,item_3a_1,item_3a_2,item_3a_2_time" +
                ",item_3a_3,item_3a_4,item_3a_5,item_3a_6,item_3a_7,item_3a_8,item_3a_9" +
                ",item_3a_10,item_3a_10_cause,item_4a_1,community_dept_id,area_dept_id,item_1a_10_other,item_3a_2_estimate,date_created,update_time,scheme_type,quit_log_id" +
                ")values(" +
                "?,?,?,?,?,?,?,?" +
                ",?,?,?,?,?,?,?" +
                ",?,?,?,?,?,?" +
                ",?,?,?,?,?,?" +
                ",?,?,?,?,?,?,?" +
                ",?,?,?,?,?,?,?" +
                ",now(),now(),?,?)");
        jdbc.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, violationScheme.getSid());
                ps.setInt(2, violationScheme.getStage());
                if (violationScheme.getTbDate() != null) {
                    ps.setDate(3, new java.sql.Date(violationScheme.getTbDate().getTime()));
                } else {
                    ps.setDate(3, null);
                }
                ps.setString(4, violationScheme.getTbrName());
                ps.setString(5, violationScheme.getZkzName());
                ps.setObject(6, violationScheme.getDeptCode());
                ps.setObject(7, violationScheme.getInvestigatorCode());
                ps.setObject(8, violationScheme.getCheckYear());
                ps.setObject(9, violationScheme.getItem_1a_1());
                ps.setObject(10, violationScheme.getItem_1a_2());
                ps.setObject(11, violationScheme.getItem_1a_2_id());
                ps.setObject(12, violationScheme.getItem_1a_3());
                ps.setObject(13, violationScheme.getItem_1a_4());
                ps.setObject(14, violationScheme.getItem_1a_5());
                ps.setObject(15, violationScheme.getItem_1a_5_des());
                ps.setObject(16, violationScheme.getItem_1a_6());
                ps.setObject(17, violationScheme.getItem_1a_7());
                ps.setObject(18, violationScheme.getItem_1a_8());
                ps.setObject(19, violationScheme.getItem_1a_9());
                ps.setObject(20, violationScheme.getItem_1a_9_cause());
                ps.setObject(21, violationScheme.getItem_1a_9_des());
                ps.setObject(22, violationScheme.getItem_1a_10());
                if (violationScheme.getItem_2a_1() != null) {
                    ps.setDate(23, new java.sql.Date(violationScheme.getItem_2a_1().getTime()));
                } else {
                    ps.setDate(23, null);
                }
                if (violationScheme.getItem_2b_1() != null) {
                    ps.setObject(24, new java.sql.Date(violationScheme.getItem_2b_1().getTime()));
                } else {
                    ps.setDate(24, null);
                }
                ps.setObject(25, violationScheme.getItem_3a_1());
                ps.setObject(26, violationScheme.getItem_3a_2());
                if (violationScheme.getItem_3a_2_time() != null) {
                    ps.setObject(27, new java.sql.Date(violationScheme.getItem_3a_2_time().getTime()));
                } else {
                    ps.setDate(27, null);
                }
                ps.setObject(28, violationScheme.getItem_3a_3());
                ps.setObject(29, violationScheme.getItem_3a_4());
                ps.setObject(30, violationScheme.getItem_3a_5());
                ps.setObject(31, violationScheme.getItem_3a_6());
                ps.setObject(32, violationScheme.getItem_3a_7());
                ps.setObject(33, violationScheme.getItem_3a_8());
                ps.setObject(34, violationScheme.getItem_3a_9());
                ps.setObject(35, violationScheme.getItem_3a_10());
                ps.setObject(36, violationScheme.getItem_3a_10_cause());
                ps.setObject(37, violationScheme.getItem_4a_1());
                ps.setObject(38, violationScheme.getCommunityDeptId());
                ps.setObject(39, violationScheme.getAreaDeptId());
                ps.setObject(40, violationScheme.getItem_1a_10_other());
                ps.setObject(41, violationScheme.getItem_3a_2_estimate());
                ps.setObject(42, violationScheme.getSchemeType());
                ps.setObject(43, violationScheme.getQuitLogId());
                return ps;
            }
        }, keyHolder);
        return keyHolder.getKey().intValue();

    }

    @Override
    public ViolationScheme queryHospitalViolationSchemeById(Integer id, String sid) {
        ViolationScheme result;
        try {
            String sql = "select * from hospital_violation_scheme  where id=? and sid=?";//+id+" and sid='"+sid+"'"
            result = jdbc.queryForObject(sql, new BeanPropertyRowMapper<ViolationScheme>(ViolationScheme.class), id, sid);
        } catch (EmptyResultDataAccessException e) {
            log.info("queryHospitalViolationSchemeById hospital_violation_scheme is error data is null");
            return null;
        } catch (IncorrectResultSizeDataAccessException e) {
            log.info("queryHospitalViolationSchemeById hospital_violation_scheme is error data size is >1");
            return null;
        }
        return result;
    }
    /**
     * 新增修改违反方案内容
     * @param violationScheme
     */
    @Override
    public void updateViolationScheme(ViolationScheme violationScheme) {
         String sql = "update hospital_violation_scheme set tb_date = ?,tbr_name = ?,zkz_name = ?,dept_code = ?,investigator_code = ?,check_year = ?,item_1a_1 = ?,item_1a_2 = ?," +
                 "item_1a_2_id = ?,item_1a_3 = ?,item_1a_4 = ?,item_1a_5 = ?,item_1a_5_des = ?,item_1a_6 = ?,item_1a_7 = ?,item_1a_8 = ?,item_1a_9 = ?," +
                 "item_1a_9_cause = ?,item_1a_9_des = ?,item_1a_10 = ?,item_1a_10_other = ?,item_2a_1 = ?,item_2b_1 = ?,item_3a_1 = ?,item_3a_2 = ?," +
                 " item_3a_2_time = ?,item_3a_2_estimate = ?,item_3a_3 = ?,item_3a_4 = ?,item_3a_5 = ?,item_3a_6 = ?,item_3a_7 = ?,item_3a_8 = ?,item_3a_9 = ?,item_3a_10 = ?," +
                 " item_3a_10_cause = ?,item_4a_1 = ?,update_time = now(),entry_status = ? where id = ?";
         jdbc.update(sql,new Object[]{
                 violationScheme.getTbDate(),
                 violationScheme.getTbrName(),
                 violationScheme.getZkzName(),
                 violationScheme.getDeptCode(),
                 violationScheme.getInvestigatorCode(),
                 violationScheme.getCheckYear(),
                 violationScheme.getItem_1a_1(),
                 violationScheme.getItem_1a_2(),
                 violationScheme.getItem_1a_2_id(),
                 violationScheme.getItem_1a_3(),
                 violationScheme.getItem_1a_4(),
                 violationScheme.getItem_1a_5(),
                 violationScheme.getItem_1a_5_des(),
                 violationScheme.getItem_1a_6(),
                 violationScheme.getItem_1a_7(),
                 violationScheme.getItem_1a_8(),
                 violationScheme.getItem_1a_9(),
                 violationScheme.getItem_1a_9_cause(),
                 violationScheme.getItem_1a_9_des(),
                 violationScheme.getItem_1a_10(),
                 violationScheme.getItem_1a_10_other(),
                 violationScheme.getItem_2a_1(),
                 violationScheme.getItem_2b_1(),
                 violationScheme.getItem_3a_1(),
                 violationScheme.getItem_3a_2(),
                 violationScheme.getItem_3a_2_time(),
                 violationScheme.getItem_3a_2_estimate(),
                 violationScheme.getItem_3a_3(),
                 violationScheme.getItem_3a_4(),
                 violationScheme.getItem_3a_5(),
                 violationScheme.getItem_3a_6(),
                 violationScheme.getItem_3a_7(),
                 violationScheme.getItem_3a_8(),
                 violationScheme.getItem_3a_9(),
                 violationScheme.getItem_3a_10(),
                 violationScheme.getItem_3a_10_cause(),
                 violationScheme.getItem_4a_1(),
                 /*violationScheme.getCommunityDeptId(),
                 violationScheme.getAreaDeptId(),*/
                 1,
                 violationScheme.getId()});
    }
    /**
     * 单独新增违反方案id，sid，status
     * @param violationScheme
     */
    @Override
    public int addVScheme(final ViolationScheme violationScheme) {
        Calendar  cal = Calendar.getInstance();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final Date date = cal.getTime();
        final String sql = "insert into hospital_violation_scheme(sid,stage,tb_date,tbr_name,zkz_name,dept_code,investigator_code,check_year,item_1a_1,item_1a_2,item_1a_2_id,item_1a_3," +
                "item_1a_4,item_1a_5,item_1a_5_des,item_1a_6,item_1a_7,item_1a_8,item_1a_9,item_1a_9_cause,item_1a_9_des,item_1a_10,item_1a_10_other,item_2a_1," +
                "item_2b_1,item_3a_1,item_3a_2,item_3a_2_time,item_3a_2_estimate,item_3a_3,item_3a_4,item_3a_5,item_3a_6,item_3a_7,item_3a_8,item_3a_9,item_3a_10," +
                "item_3a_10_cause,item_4a_1,community_dept_id,area_dept_id,date_created,update_time,scheme_type,quit_log_id,entry_status) values ("+
                "?,?,?,?,?,?,?,?" +
                ",?,?,?,?,?,?,?" +
                ",?,?,?,?,?,?" +
                ",?,?,?,?,?,?" +
                ",?,?,?,?,?,?,?" +
                ",?,?,?,?,?,?,?" +
                ",now(),now(),?,?,?)";

        jdbc.update(new PreparedStatementCreator(){

            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setObject(1, violationScheme.getSid());
                ps.setObject(2, violationScheme.getStage());
                ps.setObject(3, date);
                ps.setObject(4, violationScheme.getTbrName());
                ps.setObject(5, violationScheme.getZkzName());
                ps.setObject(6, violationScheme.getDeptCode());
                ps.setObject(7, violationScheme.getInvestigatorCode());
                ps.setObject(8, violationScheme.getCheckYear());
                ps.setObject(9, violationScheme.getItem_1a_1());
                ps.setObject(10, violationScheme.getItem_1a_2());
                ps.setObject(11, violationScheme.getItem_1a_2_id());
                ps.setObject(12, violationScheme.getItem_1a_3());
                ps.setObject(13, violationScheme.getItem_1a_4());
                ps.setObject(14, violationScheme.getItem_1a_5());
                ps.setObject(15, violationScheme.getItem_1a_5_des());
                ps.setObject(16, violationScheme.getItem_1a_6());
                ps.setObject(17, violationScheme.getItem_1a_7());
                ps.setObject(18, violationScheme.getItem_1a_8());
                ps.setObject(19, violationScheme.getItem_1a_9());
                ps.setObject(20, violationScheme.getItem_1a_9_cause());
                ps.setObject(21, violationScheme.getItem_1a_9_des());
                ps.setObject(22, violationScheme.getItem_1a_10());
                ps.setObject(23, violationScheme.getItem_1a_10_other());
                ps.setObject(24, date);
                ps.setObject(25, date);
                ps.setObject(26, violationScheme.getItem_3a_1());
                ps.setObject(27, violationScheme.getItem_3a_2());
                ps.setObject(28, date);
                ps.setObject(29, violationScheme.getItem_3a_2_estimate());
                ps.setObject(30, violationScheme.getItem_3a_3());
                ps.setObject(31, violationScheme.getItem_3a_4());
                ps.setObject(32, violationScheme.getItem_3a_5());
                ps.setObject(33, violationScheme.getItem_3a_6());
                ps.setObject(34, violationScheme.getItem_3a_7());
                ps.setObject(35, violationScheme.getItem_3a_8());
                ps.setObject(36, violationScheme.getItem_3a_9());
                ps.setObject(37, violationScheme.getItem_3a_10());
                ps.setObject(38, violationScheme.getItem_3a_10_cause());
                ps.setObject(39, violationScheme.getItem_4a_1());
                ps.setObject(40, violationScheme.getCommunityDeptId());
                ps.setObject(41, violationScheme.getAreaDeptId());
                ps.setObject(42, violationScheme.getSchemeType());
                ps.setObject(43, violationScheme.getQuitLogId());
                ps.setObject(44, violationScheme.getEntryStatus());
                return ps;
            }

        }, keyHolder);
        return keyHolder.getKey().intValue();
    }
    /**
     * 查询最大的id
     * @return
     */
    @Override
    public ViolationScheme searchByMax() {
        String sql = "select max(id) from hospital_violation_scheme";
        ViolationScheme result = jdbc.queryForObject(sql, new BeanPropertyRowMapper<ViolationScheme>(ViolationScheme.class));
        return result;
    }

}
