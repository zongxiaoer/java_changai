package com.yuntongxun.itsys.base.dao.impl;

import com.yuntongxun.itsys.base.dao.ColonoscopyPathologyDiagnosisRecordDao;
import com.yuntongxun.itsys.base.po.ColonoscopyPathologyDiagnosisRecord;
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
 * 结肠镜病理诊断记录表DaoImpl
 */
@Repository
public class ColonoscopyPathologyDiagnosisRecordDaoImpl implements ColonoscopyPathologyDiagnosisRecordDao {

    @Autowired
    private JdbcTemplate jdbc;
    private final Logger log = LogManager.getLogger(ColonoscopyPathologyDiagnosisRecordDaoImpl.class);

    /**
     * 添加结肠镜病理诊断记录表
     * @param colonoscopyPathologyDiagnosisRecord
     * @return
     */
    @Override
    public Integer addColonoscopyPathologyDiagnosisRecord(final ColonoscopyPathologyDiagnosisRecord colonoscopyPathologyDiagnosisRecord) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final StringBuffer sql = new StringBuffer("insert into hospital_colonoscopy_pathology_diagnosis_record("+
                "sid,pathology_result_id,item1,item2,item3,item4,item5,item6_1,item6_2,item7,date_created,update_time,`index`"+
                ")values(" +
                "?,?,?,?,?,?,?,?,?,?"+
                ",now(),now(),?)");
        jdbc.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, colonoscopyPathologyDiagnosisRecord.getSid());
                ps.setInt(2, colonoscopyPathologyDiagnosisRecord.getPathologyResultId());
                ps.setObject(3, colonoscopyPathologyDiagnosisRecord.getItem1());
                ps.setObject(4, colonoscopyPathologyDiagnosisRecord.getItem2());
                ps.setObject(5, colonoscopyPathologyDiagnosisRecord.getItem3());
                ps.setObject(6, colonoscopyPathologyDiagnosisRecord.getItem4());
                ps.setObject(7, colonoscopyPathologyDiagnosisRecord.getItem5());
                ps.setObject(8, colonoscopyPathologyDiagnosisRecord.getItem6_1());
                ps.setObject(9, colonoscopyPathologyDiagnosisRecord.getItem6_2());
                ps.setObject(10, colonoscopyPathologyDiagnosisRecord.getItem7());
                ps.setObject(11, colonoscopyPathologyDiagnosisRecord.getIndex());
                return ps;
            }
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public List<ColonoscopyPathologyDiagnosisRecord> queryColonoscopyPathologyDiagnosisRecordsByPathologyResultId(Integer pathologyResultId) {
        String sql = "select * from hospital_colonoscopy_pathology_diagnosis_record  where pathology_result_id=? order by date_created asc";//+id+" and sid='"+sid+"'"
        List<ColonoscopyPathologyDiagnosisRecord> result = jdbc.query(sql, new BeanPropertyRowMapper<ColonoscopyPathologyDiagnosisRecord>(ColonoscopyPathologyDiagnosisRecord.class),pathologyResultId);
        //log.info(sql);
        return result;
    }


    /**
     * 根据肠镜病理id删除检查记录
     * @param Id
     */
    @Override
    public void deleteByResultId(Integer Id) {
        String sql = "DELETE FROM hospital_colonoscopy_pathology_diagnosis_record WHERE pathology_result_id=?";
        jdbc.update(sql,Id);
    }
}
