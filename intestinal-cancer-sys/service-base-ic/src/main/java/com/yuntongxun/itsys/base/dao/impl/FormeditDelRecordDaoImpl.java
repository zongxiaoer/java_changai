/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: FormeditDelRecordDaoImpl
 * Author:   sun
 * Date:     2018/8/7 17:58
 * History:
 * <author>          <time>                <version>
 * sun           2018/8/7 17:58           v1.0.0
 */
package com.yuntongxun.itsys.base.dao.impl;
import com.yuntongxun.itsys.base.dao.FormeditDelRecordDao;
import com.yuntongxun.itsys.base.po.HospitalFormeditDelRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 一句话功能简述
 *
 * @author sun
 * @create 2018/8/7
 * @since v1.0.0
 */
@Repository
public class FormeditDelRecordDaoImpl implements FormeditDelRecordDao {

    @Autowired
    private JdbcTemplate jdbctemp;

    private final Logger log = LogManager.getLogger(FormeditDelRecordDaoImpl.class);

    @Override
    public Integer addFormeditDelRecord(HospitalFormeditDelRecord hospitalFormeditDelRecord) {
        String sql = "insert into hospital_formedit_del_record(id,reference_id,del_json,date_created,update_time) values (?,?,?,?,?)";
        return jdbctemp.update(sql, new Object[]{hospitalFormeditDelRecord.getId(), hospitalFormeditDelRecord.getReferenceId(), hospitalFormeditDelRecord.getDelJson(), hospitalFormeditDelRecord.getDateCreated(), hospitalFormeditDelRecord.getUpdateTime()});
    }
}
