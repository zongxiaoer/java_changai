package com.yuntongxun.itsys.base.service;

import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.po.FitSynLogPO;
import com.yuntongxun.itsys.base.po.dto.ExeclData;
import com.yuntongxun.itsys.base.po.dto.courier.HospitalReferenceRecordDto;
import com.yuntongxun.itsys.base.vo.FitResultSynVo;
import com.yuntongxun.itsys.base.vo.FitResultVo;

import java.util.List;

public interface FitService {

	public ListPageUtil query(FitResultVo queryCondition, String loginName);

	public void entryCode(FitResultVo vo, String loginName);

	public FitResultVo entryResult(FitResultVo vo);

	
	public ListPageUtil queryArea(FitResultVo queryCondition, String loginName);


	public ListPageUtil queryCountry(FitResultVo queryCondition);

	public Integer  saveFit(FitResultVo fitResultVo);


	FitResultVo queryByFit_code(String fit_code);

	FitResultVo queryById(Integer id);


	boolean sendFit(FitResultVo fitResultVo);

	/**
	 * 获取用户某阶段最新的fit信息
	 * @param sid
	 * @param stage
	 * @return
	 */
	public FitResultVo queryLatestFitInfo(String sid,String loginName);

	public Integer synResult(FitResultSynVo vo );
	/**
	 * 第三方同步fit结果记录
	 * @param fitSynLogPO
	 */
	public void addFitSynLog(FitSynLogPO fitSynLogPO);

	public void areaEntryCode(FitResultVo vo, String loginName);


	void updateFitCode(FitResultVo vo, HospitalReferenceRecordDto hospitalReferenceRecordDto,boolean isArea);

	FitResultVo  updateFitResult(FitResultVo fitResultVo, FitResultVo vo, HospitalReferenceRecordDto hospitalReferenceRecordDto,boolean isArea);

	List<FitResultVo> queryCountryExecl(ExeclData execlData);

}
