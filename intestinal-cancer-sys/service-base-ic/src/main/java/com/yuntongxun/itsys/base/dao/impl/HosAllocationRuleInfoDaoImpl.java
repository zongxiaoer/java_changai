package com.yuntongxun.itsys.base.dao.impl;/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: HosAllocationRuleInfoDaoImpl
 * Author:   zongtong
 * Date:     2018/12/27 下午3:16
 * History:
 * <author>          <time>                <version>
 * zongtong           2018/12/27 下午3:16           v1.0.0
 */

import com.yuntongxun.itsys.base.dao.HosAllocationRuleInfoDao;
import com.yuntongxun.itsys.base.po.dto.allocation.HosAllocationRuleInfoDto;
import com.yuntongxun.itsys.base.service.impl.BiologSampleServiceImpl;
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
public class HosAllocationRuleInfoDaoImpl implements HosAllocationRuleInfoDao {

    @Autowired
    private JdbcTemplate jdbctemp;

    private final Logger log = LogManager.getLogger(HosAllocationRuleInfoDaoImpl.class);

    @Override
    public int save(final HosAllocationRuleInfoDto allocationDto) {
        log.info("@dao HosAllocationRuleInfoDaoImpl 新增放号规则 HosAllocationRuleInfoDto={}.", allocationDto.toString());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("insert into hos_allocation_rule_info(area_dept_id,rule_begin,rule_end,rule_type,week_info,begin_status,a_time,c_time,a_user,c_user,use_status,issue_type,signature,examination_place,operation_type) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        jdbctemp.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(stringBuffer.toString(), Statement.RETURN_GENERATED_KEYS);
                ps.setObject(1, allocationDto.getAreaDeptId());
                ps.setObject(2, allocationDto.getRuleBegin());
                ps.setObject(3, allocationDto.getRuleEnd());
                ps.setObject(4, allocationDto.getRuleType());
                ps.setObject(5, allocationDto.getWeekInfo());
                ps.setObject(6, allocationDto.getBeginStatus());
                ps.setObject(7, allocationDto.getaTime());
                ps.setObject(8, allocationDto.getcTime());
                ps.setObject(9, allocationDto.getaUser());
                ps.setObject(10, allocationDto.getcUser());
                ps.setObject(11, allocationDto.getUseStatus());
                ps.setObject(12, allocationDto.getIssueType());
                ps.setObject(13, allocationDto.getSignature());
                ps.setObject(14, allocationDto.getExaminationPlace());
                ps.setObject(15, allocationDto.getOperationSource());
                return ps;
            }
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }
}
