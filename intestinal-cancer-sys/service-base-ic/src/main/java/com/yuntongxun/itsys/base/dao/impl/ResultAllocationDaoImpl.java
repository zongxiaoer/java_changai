package com.yuntongxun.itsys.base.dao.impl;/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: ResultAllocationDaoImpl
 * Author:   zongtong
 * Date:     2018/12/28 下午5:25
 * History:
 * <author>          <time>                <version>
 * zongtong           2018/12/28 下午5:25           v1.0.0
 */

import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.dao.ReserveAllocationDao;
import com.yuntongxun.itsys.base.dao.ResultAllocationDao;
import com.yuntongxun.itsys.base.po.ReserveAllocation;
import com.yuntongxun.itsys.base.po.dto.allocation.ResultAllocation;
import com.yuntongxun.itsys.base.vo.HospitalBiologicalSampleResultVo;
import com.yuntongxun.itsys.base.vo.ReserveDetail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ResultAllocationDaoImpl implements ResultAllocationDao {
    @Autowired
    private JdbcTemplate jdbctemp;

    private final Logger log = LogManager.getLogger(ResultAllocationDaoImpl.class);

    @Override
    public List<ResultAllocation> queryBystatus(Integer beginStatus, Integer issuetype) {
        log.info("开始定时任务查询规则数据");
        StringBuffer sql=new StringBuffer();
        sql.append("select rule.*,dep.community_dept_id as communityDeptIdTo,dep.begin_time as beginTimeTo,dep.end_time as endTimeTo,dep.num as numTo,dep.community_dept_id_info  as communityDeptIdInfoTo  ");

        if(Constans.ISSUETYPE1.equals(issuetype)){
            sql.append(" ,rtime.num as numToTime,rtime.begin_time as beginTimeToTime,rtime.end_time as endTimeToTime ");
        }
        sql.append(" from ");
        sql.append(" hos_allocation_rule_info rule left join hos_allocation_rule_dept_info dep on  dep.rule_id=rule.id ");
        if(Constans.ISSUETYPE1.equals(issuetype)){
            sql.append(" left join  hos_allocation_rule_time_info rtime on rtime.rule_id=rule.id ");
        }
        sql.append(" where rule.begin_status=? and rule.issue_type=? and use_status=1");
        log.info("sql"+sql.toString());

        return jdbctemp.query(sql.toString(), new BeanPropertyRowMapper<ResultAllocation>(ResultAllocation.class), beginStatus,issuetype);
    }

    @Override
    public int getRecruitByRuleIdAndBookingDay(Integer id, String afterDay) {
        String sql="SELECT    COUNT(1)    FROM    hospital_colonoscopy_reservation_allocation    WHERE    rule_id = ?   AND reservation_date = ?";
        int result = jdbctemp.queryForObject(sql.toString(), new Object[]{id,afterDay}, Integer.class);
        return result;
    }

    @Override
    public void updateById(Integer id, Integer status) {
        String sql = "update hos_allocation_rule_info set begin_status= ?  where id= ? ";
        jdbctemp.update(sql, new Object[]{status,id});

    }

}
