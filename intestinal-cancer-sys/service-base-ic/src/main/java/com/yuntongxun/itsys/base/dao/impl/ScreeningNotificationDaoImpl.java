package com.yuntongxun.itsys.base.dao.impl;

import com.yuntongxun.itsys.base.dao.ScreeningNotificationDao;
import com.yuntongxun.itsys.base.po.ScreeningNotification;
import com.yuntongxun.itsys.base.vo.ColonoscopyNotificationVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 筛查告知书DaoImpl
 */
@Repository
public class ScreeningNotificationDaoImpl implements ScreeningNotificationDao {

    @Autowired
    private JdbcTemplate jdbc;
    private final Logger log = LogManager.getLogger(ScreeningNotificationDaoImpl.class);

    /**
     * 添加告知书 返回主键
     *
     * @param screeningNotification
     * @return
     */
    @Override
    public Integer addScreeningNotification(final ScreeningNotification screeningNotification) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final StringBuffer sql = new StringBuffer("insert into hospital_screening_notification(" +
                "sid,sc_date,stage,xty_name,xty_phone,hos_name,item_a_type,item_a_r_1,item_a_r_2,item_a_r_3" +
                ",item_a_r_4,item_a_r_5,item_a_r_6,item_a_r_7,item_a_r_8,item_a_r_9,item_a_r_10,item_a_r_11" +
                ",item_a_r_12,item_a_r_13,item_a_r_14,item_a_r_15,item_a_r_15_other,item_a_s_1,item_a_s_2" +
                ",item_a_s_3,item_a_s_4,item_a_s_5,item_a_s_6,item_a_s_6_other,item_b_type,item_b_r_1" +
                ",item_b_r_2,item_b_r_3,item_b_s_1,item_b_s_2,item_b_s_3,item_c_type,item_c_r_1,item_c_r_2" +
                ",item_c_r_3,item_c_r_4,item_c_r_5,item_c_r_6,item_c_r_7,item_c_r_8,item_c_r_9,item_c_r_9_other" +
                ",item_c_r_10,item_c_r_11,item_c_r_12,item_c_r_13,item_c_r_13_other,item_c_r_14,item_c_r_15" +
                ",item_c_r_16,item_c_r_17,item_c_r_18,item_c_r_19,item_c_r_19_other,item_c_s_1,item_c_s_2" +
                ",item_c_s_3,item_c_s_4,item_c_s_5,item_c_s_5_other,community_dept_id,area_dept_id,date_created,update_time" +
                ")values(" +
                "?,?,?,?,?,?,?,?,?,?" +
                ",?,?,?,?,?,?,?,?,?,?" +
                ",?,?,?,?,?,?,?,?,?,?" +
                ",?,?,?,?,?,?,?,?,?,?" +
                ",?,?,?,?,?,?,?,?,?,?" +
                ",?,?,?,?,?,?,?,?,?,?" +
                ",?,?,?,?,?,?,?,?" +
                ",now(),now())");
        jdbc.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, screeningNotification.getSid());
                if (screeningNotification.getScDate() != null) {
                    ps.setDate(2, new java.sql.Date(screeningNotification.getScDate().getTime()));
                } else {
                    ps.setDate(2, null);
                }
                ps.setInt(3, screeningNotification.getStage());
                ps.setString(4, screeningNotification.getXtyName());
                ps.setString(5, screeningNotification.getXtyPhone());
                ps.setString(6, screeningNotification.getHosName());
                ps.setObject(7, screeningNotification.getItem_a_type());
                ps.setObject(8, screeningNotification.getItem_a_r_1());
                ps.setObject(9, screeningNotification.getItem_a_r_2());
                ps.setObject(10, screeningNotification.getItem_a_r_3());

                ps.setObject(11, screeningNotification.getItem_a_r_4());
                ps.setObject(12, screeningNotification.getItem_a_r_5());
                ps.setObject(13, screeningNotification.getItem_a_r_6());
                ps.setObject(14, screeningNotification.getItem_a_r_7());
                ps.setObject(15, screeningNotification.getItem_a_r_8());
                ps.setObject(16, screeningNotification.getItem_a_r_9());
                ps.setObject(17, screeningNotification.getItem_a_r_10());
                ps.setObject(18, screeningNotification.getItem_a_r_11());
                ps.setObject(19, screeningNotification.getItem_a_r_12());
                ps.setObject(20, screeningNotification.getItem_a_r_13());
                ps.setObject(21, screeningNotification.getItem_a_r_14());
                ps.setObject(22, screeningNotification.getItem_a_r_15());
                ps.setObject(23, screeningNotification.getItem_a_r_15_other());
                ps.setObject(24, screeningNotification.getItem_a_s_1());
                ps.setObject(25, screeningNotification.getItem_a_s_2());
                ps.setObject(26, screeningNotification.getItem_a_s_3());
                ps.setObject(27, screeningNotification.getItem_a_s_4());
                ps.setObject(28, screeningNotification.getItem_a_s_5());
                ps.setObject(29, screeningNotification.getItem_a_s_6());
                ps.setObject(30, screeningNotification.getItem_a_s_6_other());
                ps.setObject(31, screeningNotification.getItem_b_type());
                ps.setObject(32, screeningNotification.getItem_b_r_1());
                ps.setObject(33, screeningNotification.getItem_b_r_2());
                ps.setObject(34, screeningNotification.getItem_b_r_3());
                ps.setObject(35, screeningNotification.getItem_b_s_1());
                ps.setObject(36, screeningNotification.getItem_b_s_2());
                ps.setObject(37, screeningNotification.getItem_b_s_3());
                ps.setObject(38, screeningNotification.getItem_c_type());
                ps.setObject(39, screeningNotification.getItem_c_r_1());
                ps.setObject(40, screeningNotification.getItem_c_r_2());
                ps.setObject(41, screeningNotification.getItem_c_r_3());
                ps.setObject(42, screeningNotification.getItem_c_r_4());
                ps.setObject(43, screeningNotification.getItem_c_r_5());
                ps.setObject(44, screeningNotification.getItem_c_r_6());
                ps.setObject(45, screeningNotification.getItem_c_r_7());
                ps.setObject(46, screeningNotification.getItem_c_r_8());
                ps.setObject(47, screeningNotification.getItem_c_r_9());
                ps.setObject(48, screeningNotification.getItem_c_r_9_other());

                ps.setObject(49, screeningNotification.getItem_c_r_10());
                ps.setObject(50, screeningNotification.getItem_c_r_11());
                ps.setObject(51, screeningNotification.getItem_c_r_12());
                ps.setObject(52, screeningNotification.getItem_c_r_13());
                ps.setObject(53, screeningNotification.getItem_c_r_13_other());
                ps.setObject(54, screeningNotification.getItem_c_r_14());
                ps.setObject(55, screeningNotification.getItem_c_r_15());
                ps.setObject(56, screeningNotification.getItem_c_r_16());
                ps.setObject(57, screeningNotification.getItem_c_r_17());
                ps.setObject(58, screeningNotification.getItem_c_r_18());

                ps.setObject(59, screeningNotification.getItem_c_r_19());
                ps.setObject(60, screeningNotification.getItem_c_r_19_other());
                ps.setObject(61, screeningNotification.getItem_c_s_1());
                ps.setObject(62, screeningNotification.getItem_c_s_2());
                ps.setObject(63, screeningNotification.getItem_c_s_3());
                ps.setObject(64, screeningNotification.getItem_c_s_4());
                ps.setObject(65, screeningNotification.getItem_c_s_5());
                ps.setObject(66, screeningNotification.getItem_c_s_5_other());
                ps.setObject(67, screeningNotification.getCommunityDeptId());
                ps.setObject(68, screeningNotification.getAreaDeptId());
                return ps;
            }
        }, keyHolder);
        return keyHolder.getKey().intValue();

    }

    @Override
    public ColonoscopyNotificationVo get(int id) {
        String sql = "select t1.`name`,t2.*  from hospital_intestine_review t1,hospital_screening_notification t2 where t1.sid=t2.sid and t2.id=?";
        ColonoscopyNotificationVo result = jdbc.queryForObject(sql, new BeanPropertyRowMapper<ColonoscopyNotificationVo>(ColonoscopyNotificationVo.class), id);
        return result;
    }

    @Override
    public List<ScreeningNotification> queryById(Integer id) {
        String sql = "select * from  hospital_screening_notification where id=?";//+id+" and sid='"+sid+"'"
        //log.info(sql);
        return jdbc.query(sql, new BeanPropertyRowMapper<ScreeningNotification>(ScreeningNotification.class), id);
    }


    @Override
    public void updateScreeningNotificationt(ScreeningNotification screeningNotification) {
        String sql = "update hospital_screening_notification set " +
                "sc_date=?,xty_name=?,xty_phone=?,hos_name=?,item_a_type=?,item_a_r_1=?,item_a_r_2=?,item_a_r_3"+
                "=?,item_a_r_4=?,item_a_r_5=?,item_a_r_6=?,item_a_r_7=?,item_a_r_8=?,item_a_r_9=?,item_a_r_10=?,item_a_r_11"+
                "=?,item_a_r_12=?,item_a_r_13=?,item_a_r_14=?,item_a_r_15=?,item_a_r_15_other=?,item_a_s_1=?,item_a_s_2"+
                "=?,item_a_s_3=?,item_a_s_4=?,item_a_s_5=?,item_a_s_6=?,item_a_s_6_other=?,item_b_type=?,item_b_r_1"+
                "=?,item_b_r_2=?,item_b_r_3=?,item_b_s_1=?,item_b_s_2=?,item_b_s_3=?,item_c_type=?,item_c_r_1=?,item_c_r_2"+
                "=?,item_c_r_3=?,item_c_r_4=?,item_c_r_5=?,item_c_r_6=?,item_c_r_7=?,item_c_r_8=?,item_c_r_9=?,item_c_r_9_other"+
                "=?,item_c_r_10=?,item_c_r_11=?,item_c_r_12=?,item_c_r_13=?,item_c_r_13_other=?,item_c_r_14=?,item_c_r_15"+
                "=?,item_c_r_16=?,item_c_r_17=?,item_c_r_18=?,item_c_r_19=?,item_c_r_19_other=?,item_c_s_1=?,item_c_s_2"+
                "=?,item_c_s_3=?,item_c_s_4=?,item_c_s_5=?,item_c_s_5_other=?,update_time=now(),apply_status=?,edit_status=?,approval_status=? where id=?";
        jdbc.update(sql, new Object[]{
                screeningNotification.getScDate() != null?new java.sql.Date(screeningNotification.getScDate().getTime()):null
                , screeningNotification.getXtyName()
                , screeningNotification.getXtyPhone()
                , screeningNotification.getHosName()
                , screeningNotification.getItem_a_type()
                , screeningNotification.getItem_a_r_1()
                , screeningNotification.getItem_a_r_2()
                , screeningNotification.getItem_a_r_3()
                , screeningNotification.getItem_a_r_4()
                , screeningNotification.getItem_a_r_5()
                , screeningNotification.getItem_a_r_6()
                , screeningNotification.getItem_a_r_7()
                , screeningNotification.getItem_a_r_8()
                , screeningNotification.getItem_a_r_9()
                , screeningNotification.getItem_a_r_10()
                , screeningNotification.getItem_a_r_11()
                , screeningNotification.getItem_a_r_12()
                , screeningNotification.getItem_a_r_13()
                , screeningNotification.getItem_a_r_14()
                , screeningNotification.getItem_a_r_15()
                , screeningNotification.getItem_a_r_15_other()
                , screeningNotification.getItem_a_s_1()
                , screeningNotification.getItem_a_s_2()
                , screeningNotification.getItem_a_s_3()
                , screeningNotification.getItem_a_s_4()
                , screeningNotification.getItem_a_s_5()
                , screeningNotification.getItem_a_s_6()
                , screeningNotification.getItem_a_s_6_other()
                , screeningNotification.getItem_b_type()
                , screeningNotification.getItem_b_r_1()
                , screeningNotification.getItem_b_r_2()
                , screeningNotification.getItem_b_r_3()
                , screeningNotification.getItem_b_s_1()
                , screeningNotification.getItem_b_s_2()
                , screeningNotification.getItem_b_s_3()
                , screeningNotification.getItem_c_type()
                , screeningNotification.getItem_c_r_1()
                , screeningNotification.getItem_c_r_2()
                , screeningNotification.getItem_c_r_3()
                , screeningNotification.getItem_c_r_4()
                , screeningNotification.getItem_c_r_5()
                , screeningNotification.getItem_c_r_6()
                , screeningNotification.getItem_c_r_7()
                , screeningNotification.getItem_c_r_8()
                , screeningNotification.getItem_c_r_9()
                , screeningNotification.getItem_c_r_9_other()
                , screeningNotification.getItem_c_r_10()
                , screeningNotification.getItem_c_r_11()
                , screeningNotification.getItem_c_r_12()
                , screeningNotification.getItem_c_r_13()
                , screeningNotification.getItem_c_r_13_other()
                , screeningNotification.getItem_c_r_14()
                , screeningNotification.getItem_c_r_15()
                , screeningNotification.getItem_c_r_16()
                , screeningNotification.getItem_c_r_17()
                , screeningNotification.getItem_c_r_18()
                , screeningNotification.getItem_c_r_19()
                , screeningNotification.getItem_c_r_19_other()
                , screeningNotification.getItem_c_s_1()
                , screeningNotification.getItem_c_s_2()
                , screeningNotification.getItem_c_s_3()
                , screeningNotification.getItem_c_s_4()
                , screeningNotification.getItem_c_s_5()
                , screeningNotification.getItem_c_s_5_other()
                , screeningNotification.getApplyStatus()
                , screeningNotification.getEditStatus()
                , screeningNotification.getApprovalStatus(),
                screeningNotification.getId()});

    }

    /**
     * 删除告知书 信息
     * @param
     * @param id
     * @return
     */
    @Override
    public void delNotificationById(Integer id) {
        if(id != null && id > 0) {
            String sql = "DELETE FROM hospital_screening_notification  WHERE id = ?";
            jdbc.update(sql, id);
        }
    }
}
