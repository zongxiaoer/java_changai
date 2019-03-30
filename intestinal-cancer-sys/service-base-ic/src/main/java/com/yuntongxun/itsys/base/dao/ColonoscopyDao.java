package com.yuntongxun.itsys.base.dao;

import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.po.HospitalColonoscopyRecord;
import com.yuntongxun.itsys.base.po.ReservationDetail;
import com.yuntongxun.itsys.base.po.ReserveAllocation;
import com.yuntongxun.itsys.base.po.dto.allocation.AllocationDto;
import com.yuntongxun.itsys.base.po.dto.courier.HospitalReferenceRecordDto;
import com.yuntongxun.itsys.base.vo.*;

import java.util.Date;
import java.util.List;

public interface ColonoscopyDao {

	public ListPageUtil query(ColonoscopyVo queryCondition,Integer communityDeptId,Integer areaDeptId,Integer deptType,boolean isPage);
	public ListPageUtil queryForArea(ColonoscopyVo queryCondition,Integer communityDeptId,Integer areaDeptId,Integer deptType,boolean isPage);
	//导出肠镜管理列表地区
	public List<ColonoscopyVo> queryForAreaExcel(ColonoscopyVo queryCondition,Integer communityDeptId,Integer areaDeptId,Integer deptType,boolean isPage);

	public void updateReserveStatus(int colonoscopyRecordId,Date reserveDate,int reserveOperator,int reserveId, int reserveStatus, int allocationId);

	public ReserveDetail getReserveDetail(int id);

	public void updateNotificationIssueStatus(ColonoscopyIssueVo vo, Integer id, int colonoscopyNotificationIssued);

	/**
	 * 修改肠镜检查记录中的录入告知单状态
	 * maxiang
	 * @param vo
	 */
	public void updateNotificationEntryStatus(ColonoscopyEntryVo vo);

	/**
	 * 修改结肠镜检查记录表中的肠镜录入状态
	 * @param vo
	 */
	public void updateNotificationResultStatus(ColonoscopyResultVo vo);

	/**
	 * 修改结肠镜检查记录表中的病理状态
	 * @param vo
	 */
	public void updateNotificationPathologyStatus(ColonoscopyPathologyStatusVo vo);

	public int add(HospitalColonoscopyRecord result);

	/**
	 * 批量或单个修改结肠镜检查记录表中检查（就诊）状态
	 *
	 * examination_status int 结肠镜检查就诊状态，1：未就诊，2：已就诊
	 examination_date datetime 就诊时间 当前时间
	 examination_operator int 确认就诊医生 当前登录医生
	 * @param vo
	 */
	public void updateExaminationStatus(ColonoscopyExaminationVo vo);

	public void updateReserveStatusByOtherSys(int colonoscopyRecordId,int reserveOperator, int reserveStatus,Date reserveDate);
	/**
	 * 国家 肠镜管理
	 * @param queryCondition
	 * @param deptType
	 * @param isPage
	 * @return
	 */
	public ListPageUtil queryForNationList(ColonoscopyVo queryCondition,Integer nationId,Integer deptType,boolean isPage);

	public void updateReserveStatusForCancelBooking(int colonoscopyRecordId);

	List<HospitalColonoscopyRecord> queryById(Integer colonoscopyRecordId);

	List<HospitalColonoscopyRecord> queryBySourceAndId(String editoperationSource, Integer operationSourceId);

    void updateColonoscopyRecordForCheckStatus(HospitalColonoscopyRecord colonoscopyRecord);

    void updateCJFormUpdateStatus(HospitalReferenceRecordDto dto);

    List<ReserveAllocation> queryAllocationByrecordId(Integer id);

	List<AllocationDto> queryAllocationById(Integer allocationId);

	List<ReservationDetail> queryHospitalColonoscopyReservationDetail(Integer allocationId, String sid);
}
