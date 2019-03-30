package com.yuntongxun.itsys.base.dao;

import com.yuntongxun.itsys.base.po.ReservationDetail;

public interface ReserveDetailDao {

	public int getReservedCount(int allocationId);

	public int save(ReservationDetail detail);

	public void delReserveDetail(Integer allocation_id,String sid);

	void delReserveDetailByids(int i, String sid, Integer reserve_id);

	void updateReserveDetailByids(int i, String sid, Integer reserve_id);
}
