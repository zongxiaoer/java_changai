package com.yuntongxun.itsys.base.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.yuntongxun.itsys.base.dao.ReserveDetailDao;
import com.yuntongxun.itsys.base.po.ReservationDetail;

@Repository
public class ReserveDetailDaoImpl implements ReserveDetailDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final Logger log = LogManager.getLogger(ReserveDetailDaoImpl.class);

	@Override
	public int getReservedCount(int allocationId) {
		log.info("getReservedCount,allocationId:{}");
		String sql = "select count(id) from hospital_colonoscopy_reservation_detail where allocation_id = ?";
		int result = jdbcTemplate.queryForObject(sql.toString(), new Object[]{allocationId}, Integer.class);
		return result;
	}

	@Override
	public int save(final ReservationDetail detail) {
		final String sql = "insert into hospital_colonoscopy_reservation_detail(allocation_id,sid,status,stage,community_dept_id,area_dept_id,date_created,update_time)"
				+ " values(?,?,?,?,?,?,now(),now())";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setObject(1, detail.getAllocationId());
				ps.setString(2, detail.getSid());
				ps.setObject(3, detail.getStatus());
				ps.setObject(4, detail.getStage());
				ps.setObject(5, detail.getCommunityDeptId());
				ps.setObject(6, detail.getAreaDeptId());
				return ps;
			}
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	/**
	 * 删除预约信息
	 * @param allocation_id
	 */
	@Override
	public void delReserveDetail(Integer allocation_id,String sid){
		Object[] obj = new Object[]{allocation_id,sid};
		String sql = "delete from hospital_colonoscopy_reservation_detail where allocation_id=? and sid=? ";
		jdbcTemplate.update(sql, obj);
	}

	@Override
	public void delReserveDetailByids(int i, String sid, Integer reserve_id) {
		Object[] obj = new Object[]{i,sid,reserve_id};
		String sql = "delete from hospital_colonoscopy_reservation_detail where allocation_id=? and sid=? and id=? ";
		jdbcTemplate.update(sql, obj);
	}

	@Override
	public void updateReserveDetailByids(int i, String sid, Integer reserve_id) {
		Object[] obj = new Object[]{i,sid,reserve_id};
		String sql = "update  hospital_colonoscopy_reservation_detail set examine_status=1  where allocation_id=? and sid=? and id=? ";
		jdbcTemplate.update(sql, obj);
	}


}
