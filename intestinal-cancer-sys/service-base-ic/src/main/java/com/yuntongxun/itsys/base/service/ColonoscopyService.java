package com.yuntongxun.itsys.base.service;

import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.po.HospitalColonoscopyRecord;
import com.yuntongxun.itsys.base.po.HospitalColonoscopyResult;
import com.yuntongxun.itsys.base.po.dto.ItsysUserDto;
import com.yuntongxun.itsys.base.vo.*;

import java.util.List;

public interface ColonoscopyService {

	public ListPageUtil query(ColonoscopyVo queryCondition, String loginName);
	public ListPageUtil queryForArea(ColonoscopyVo queryCondition, String loginName);
	//新增地区肠镜列表导出
	public List<ColonoscopyVo> queryForAreaExcel(ColonoscopyVo queryCondition, String loginName);

	public void booking(ColonoscopyVo vo, String loginName);

	public ReserveDetail getReserveDetail(int id);

	public void issueNotification(ColonoscopyIssueVo vo, String loginName);

	public ColonoscopyNotificationVo getNotification(int id,HospitalColonoscopyResult hospitalColonoscopyResult);
	
	DoctorInfo getDoctorInfo(String loginName);

	public ReservationVo getReservation(int id);

	/**
	 * 批量或单个修改用户的检查状态
	 * maxiang
	 */
	public void updateExaminationStatus(String body,String loginName);
	
	/**
	 * 重新预约
	 * @param vo
	 * @param loginName
	 */
	public void rebooking(ColonoscopyVo vo, String loginName);

	/**
	 * 添加肠镜检查记录
	 * @param vo
	 * @param loginName
	 */
	public void addColonoscopyRecord(ColonoscopyVo vo, String loginName);
	/**
	 * 国家肠镜管理查询列表
	 * @param queryCondition
	 * @param loginName
	 * @return
	 */
	public ListPageUtil queryForNationList(ColonoscopyVo queryCondition, String loginName);
	public void cancelBooking(ColonoscopyVo vo,String loginName);
	public void cancelBooking(ColonoscopyVo vo);

    List<ItsysUserDto> queryloginNamesByloginName(String loginName);

	List<ItsysUserDto> queryAllloginNamesByloginName(String loginName);

	List<ItsysUserDto> queryloginNameRootByloginName(String loginName);

    HospitalColonoscopyRecord findRecordByResultId(Integer id);

	HospitalColonoscopyRecord findRecordByPathologyId(Integer id);

	HospitalColonoscopyRecord findRecordByNotificationId(Integer id);

    List<ItsysUserDto> getCreateUser(String sid);

	HospitalColonoscopyRecord queryByID(int communityDeptId);

    List<ItsysUserDto> querylowerLoginNamesByloginName(String loginName);

	List<ItsysUserDto> queryMyLoginNamesByloginName(String loginName);
}
