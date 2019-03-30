package com.yuntongxun.itsys.base.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yuntongxun.itsys.base.dao.ReserveAllocationDao;
import com.yuntongxun.itsys.base.po.ReserveAllocation;

@Repository
public class ReserveAllocationDaoImpl implements ReserveAllocationDao {

    @Autowired
    private JdbcTemplate jdbctemp;

    private final Logger log = LogManager.getLogger(ReserveAllocationDaoImpl.class);

	@Override
	public ReserveAllocation getReserveAllocation(int allocationId) {
		String sql = "select t1.`id`,t1.area_dept_id as areaDeptId,t1.amount,t1.reservation_date as reservationDate,t1.start_time as startTime,t1.end_time as endTime,t1.examination_place as examinationPlace,t1.signature,t2.name as deptName from hospital_colonoscopy_reservation_allocation t1,itsys_department t2 where t1.area_dept_id=t2.`id` and t1.`id`=?";//t1.community_dept_id as communityDeptId,
		log.info("getReserveAllocation,sql:{}",sql);
		ReserveAllocation result=jdbctemp.queryForObject(sql,new BeanPropertyRowMapper<ReserveAllocation>(ReserveAllocation.class),allocationId);
		return result;
	}

}
