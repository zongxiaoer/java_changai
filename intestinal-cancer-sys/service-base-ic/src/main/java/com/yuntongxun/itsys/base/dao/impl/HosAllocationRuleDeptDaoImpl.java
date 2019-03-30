package com.yuntongxun.itsys.base.dao.impl;/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: HosAllocationRuleDeptDaoImpl
 * Author:   zongtong
 * Date:     2018/12/27 下午5:29
 * History:
 * <author>          <time>                <version>
 * zongtong           2018/12/27 下午5:29           v1.0.0
 */

import com.yuntongxun.itsys.base.dao.HosAllocationRuleDeptDao;
import com.yuntongxun.itsys.base.po.dto.allocation.HosAllocationRuleDeptInfoDto;
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
public class HosAllocationRuleDeptDaoImpl implements HosAllocationRuleDeptDao {

    @Autowired
    private JdbcTemplate jdbctemp;

    private final Logger log = LogManager.getLogger(HosAllocationRuleDeptDaoImpl.class);

    @Override
    public int save(final HosAllocationRuleDeptInfoDto hosAllocationRuleDeptInfoDto) {
        log.info("@dao HosAllocationRuleDeptDaoImpl 放号规则部门信息表 hosAllocationRuleDeptInfoDto={}.", hosAllocationRuleDeptInfoDto.toString());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("insert into hos_allocation_rule_dept_info(community_dept_id,rule_id,begin_time,end_time,num,community_dept_id_info,issue_type) values(?,?,?,?,?,?,?)");
        jdbctemp.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(stringBuffer.toString(), Statement.RETURN_GENERATED_KEYS);
                ps.setObject(1, hosAllocationRuleDeptInfoDto.getCommunityDeptId());
                ps.setObject(2, hosAllocationRuleDeptInfoDto.getRuleId());
                ps.setObject(3, hosAllocationRuleDeptInfoDto.getBeginTime());
                ps.setObject(4, hosAllocationRuleDeptInfoDto.getEndTime());
                ps.setObject(5, hosAllocationRuleDeptInfoDto.getNum());
                ps.setObject(6, hosAllocationRuleDeptInfoDto.getCommunityDeptIdInfo());
                ps.setObject(7, hosAllocationRuleDeptInfoDto.getIssueType());
                return ps;
            }
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }
}
