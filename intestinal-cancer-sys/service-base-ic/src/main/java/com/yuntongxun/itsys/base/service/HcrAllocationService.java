package com.yuntongxun.itsys.base.service;

import java.util.List;

import com.yuntongxun.itsys.base.common.exception.ServiceException;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.po.AllocationTodo;
import com.yuntongxun.itsys.base.po.dto.allocation.AllocationDto;
import com.yuntongxun.itsys.base.po.dto.allocation.ColonoscopyDto;
import com.yuntongxun.itsys.base.po.dto.allocation.HosAllocationRuleInfoDto;
import com.yuntongxun.itsys.base.po.dto.allocation.ResultAllocation;
import com.yuntongxun.itsys.base.vo.*;

/**
 * 查询预约分配预约
 * @author liugb
 * Date 2018 4 16
 */
public interface HcrAllocationService {
	//查询预约分配接口
	public Result query(int communityDeptId,int pageNo,int pageSize) throws ServiceException;
	//根据登陆用户查询所对应的社区
	public Result getCommdepts(String loginName);
	//预约分配放号新增
	public Result insert(AllocationTodo allocationTodo,String loginName) throws ServiceException;


	//根据市区id查询社区详情
	public String getByareaId(String loginName);
	//签到列表
	public Result queryallnotlist(String loginName,TodoVo vo);
	//根据地区查询放号一览表
	public Result queryPutCodeByAreaId(String loginName,TodoVo vo);
	//地区一览表查询详情
	public Result queryPutCodeDetailListByCommitId(ColonoscopyQueryResult queryparam);
	//新增地区肠镜列表导出
    public List<AreaListForExcelVo> queryForAreaExcel(TodoVo vo, String loginName);


	Result queryInArea(int communityDeptId, int pageNo, int pageSize);
	//预约分配放号新增
    Result newInsert(HosAllocationRuleInfoDto allocationDto, String loginName);

	List<ResultAllocation> queryByRuleSataus(Integer beginStatus1, Integer issuetype1);

	int getRecruitByRuleIdAndBookingDay(Integer id, String afterDay);

    void updateById(Integer id,Integer status);

	ListPageUtil queryRule(HosAllocationRuleInfoDto hosAllocationRuleInfoDto,String loginName);

    void diagnosisDown(ColonoscopyDto colonoscopyDto,String loginName);

	Result queryPutCodeByRuleId(String loginName, TodoVo vo);

	Result queryCalendar(String loginName, TodoVo vo);

	Result queryNumByRuleIdAndRule(String loginName, TodoVo vo);

	Result queryEntityByRuleIdAndRule(String loginName, TodoVo vo);

	Result queryPerSonByRuleIdAndRule(String loginName, TodoVo vo);

	Result queryFanghao(String loginName, TodoVo vo);



}
