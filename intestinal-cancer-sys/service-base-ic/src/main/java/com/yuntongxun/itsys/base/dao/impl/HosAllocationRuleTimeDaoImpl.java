package com.yuntongxun.itsys.base.dao.impl;/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: HosAllocationRuleTimeDaoImpl
 * Author:   zongtong
 * Date:     2018/12/27 下午5:15
 * History:
 * <author>          <time>                <version>
 * zongtong           2018/12/27 下午5:15           v1.0.0
 */

import com.yuntongxun.itsys.base.dao.HosAllocationRuleTimeDao;
import com.yuntongxun.itsys.base.po.dto.allocation.HosAllocationRuleTimeInfoDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class HosAllocationRuleTimeDaoImpl implements HosAllocationRuleTimeDao {
    @Autowired
    private JdbcTemplate jdbctemp;

    private final Logger log = LogManager.getLogger(HosAllocationRuleTimeDaoImpl.class);
    @Override
    public int save(final HosAllocationRuleTimeInfoDto hosAllocationRuleTimeInfoDto) {
        log.info("@dao HosAllocationRuleTimeDaoImpl 部门放号规则时间信息表 hosAllocationRuleTimeInfoDto={}.", hosAllocationRuleTimeInfoDto.toString());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("insert into hos_allocation_rule_time_info(rule_id,begin_time,end_time,num) values(?,?,?,?)");
        jdbctemp.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(stringBuffer.toString(), Statement.RETURN_GENERATED_KEYS);
                ps.setObject(1, hosAllocationRuleTimeInfoDto.getRuleId());
                ps.setObject(2, hosAllocationRuleTimeInfoDto.getBeginTime());
                ps.setObject(3, hosAllocationRuleTimeInfoDto.getEndTime());
                ps.setObject(4, hosAllocationRuleTimeInfoDto.getNum());
                return ps;
            }
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }
}
