package com.yuntongxun.itsys.base.dao;

import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.po.FitSynLogPO;
import com.yuntongxun.itsys.base.po.dto.ExeclData;
import com.yuntongxun.itsys.base.vo.FitResultVo;

import java.util.List;

public interface FitDao {

	public ListPageUtil query(FitResultVo queryCondition,int communityDeptId,int areaDeptId,int deptType,boolean isPage);

	public void updateFitCode(FitResultVo vo);

	public void updateFitResult(FitResultVo vo);

	public ListPageUtil queryArea(FitResultVo queryCondition, Integer communityDeptId, Integer areaDeptId,
			Integer hospitalType, boolean b);


	public ListPageUtil queryCountry(FitResultVo queryCondition,
								   boolean b);


	/**
	 * @func   新增fit入口
	 * @desc
	 * @author zongt
	 * @create 2018/5/6 下午12:02
	 * @request
	 * @response
	 **/
	Integer addFit(FitResultVo vo);

	FitResultVo queryByFit_code(String fitCode);

	FitResultVo queryById(Integer id);

	/**
	 * 获取用户某阶段最新的fit信息
	 * @param sid
	 * @param stage
	 * @return
	 */
	public FitResultVo queryLatestFitInfo(String sid,Integer stage);

	public FitResultVo queryLatestFitInfoForSynResult(String sid);

	/**
	 * 第三方同步fit结果记录日志
	 * @param fitSynLogPO
	 * @return
	 */
	public int addFitSynLog(final FitSynLogPO fitSynLogPO);

	void updateEditFitResult(FitResultVo vo,boolean isArea);

    Integer updateUrlByID(String filePath,Integer id);

	List<FitResultVo> queryCountryExecl(ExeclData execlData);

}
