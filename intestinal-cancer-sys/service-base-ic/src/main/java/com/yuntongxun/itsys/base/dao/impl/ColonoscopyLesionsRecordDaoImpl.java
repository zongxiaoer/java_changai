package com.yuntongxun.itsys.base.dao.impl;

import com.yuntongxun.itsys.base.dao.ColonoscopyLesionsRecordDao;
import com.yuntongxun.itsys.base.po.ColonoscopyLesionsRecord;
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
 * 结肠镜结果病变记录表
 * maxiang
 */
@Repository
public class ColonoscopyLesionsRecordDaoImpl implements ColonoscopyLesionsRecordDao {

    private final Logger log = LogManager.getLogger(ColonoscopyLesionsRecordDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbc;

    /**
     * 添加结肠镜结果病变记录表
     * @param colonoscopyLesionsRecord
     * @return
     */
    @Override
    public Integer addColonoscopyLesionsRecord(final ColonoscopyLesionsRecord colonoscopyLesionsRecord) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final StringBuffer sql = new StringBuffer("insert into hospital_colonoscopy_lesions_record("+
                "sid,colonoscopy_result_id,item1,item2,item3,item4,item5,item6,item7,item8,item9,item10,date_created,update_time,`index`"+
                ")values(" +
                "?,?,?,?,?,?,?,?,?,?,?,?"+
                ",now(),now(),?)");
        jdbc.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, colonoscopyLesionsRecord.getSid());
                ps.setInt(2, colonoscopyLesionsRecord.getColonoscopyResultId());
                ps.setObject(3, colonoscopyLesionsRecord.getItem1());
                ps.setObject(4, colonoscopyLesionsRecord.getItem2());
                ps.setObject(5, colonoscopyLesionsRecord.getItem3());
                ps.setObject(6, colonoscopyLesionsRecord.getItem4());
                ps.setObject(7, colonoscopyLesionsRecord.getItem5());
                ps.setObject(8, colonoscopyLesionsRecord.getItem6());
                ps.setObject(9, colonoscopyLesionsRecord.getItem7());
                ps.setObject(10, colonoscopyLesionsRecord.getItem8());
                ps.setObject(11, colonoscopyLesionsRecord.getItem9());
                ps.setObject(12, colonoscopyLesionsRecord.getItem10());
                ps.setObject(13, colonoscopyLesionsRecord.getIndex());
                return ps;
            }
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public List<ColonoscopyLesionsRecord> queryByColonoscopyResultId(Integer colonoscopyResultId) {
        String sql = "select * from hospital_colonoscopy_lesions_record  where colonoscopy_result_id=? order by date_created asc";//+id+" and sid='"+sid+"'"
        List<ColonoscopyLesionsRecord> result = jdbc.query(sql, new BeanPropertyRowMapper<ColonoscopyLesionsRecord>(ColonoscopyLesionsRecord.class),colonoscopyResultId);
        //log.info(sql);
        return result;
    }

    @Override
    public void deleteByIDs(String substring) {
        String sql = "DELETE FROM hospital_colonoscopy_lesions_record WHERE id in("+substring+")";
        int result = jdbc.update(sql);
    }

    /**
     * 根据肠镜结果id删除病变记录
     * @param Id
     */
    @Override
    public void deleteByResultId(Integer Id) {
        String sql = "DELETE FROM hospital_colonoscopy_lesions_record WHERE colonoscopy_result_id=?";
        jdbc.update(sql,new Object[]{Id});
    }
}
