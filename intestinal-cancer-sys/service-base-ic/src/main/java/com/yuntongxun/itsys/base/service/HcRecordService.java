package com.yuntongxun.itsys.base.service;

import com.yuntongxun.itsys.base.vo.Result;
import com.yuntongxun.itsys.base.vo.TodoVo;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.vo.TodoVoPo;

public interface HcRecordService {
	//立即预约页面查询待预约受试者
	public Result getRecordView(TodoVo vo,String loginName);
	//查询 未按时完成结肠镜检查 受试者列表
	public ListPageUtil notFinishColonoscopy(TodoVo vo, String loginName);
	//查询 未发放筛查结果告知书 受试者列表
	public ListPageUtil notIssueNotification(TodoVo vo, String loginName);
	
	//查询 未完成危险因素待办受试者
	public ListPageUtil notReserveColonoscopy(TodoVo vo, String loginName);
	
	
	public ListPageUtil notEntryColonoscopyResultQuery(TodoVo vo, String loginName);
	
	
	public ListPageUtil notEntryPathologyResultQuery(TodoVo vo, String loginName);
	
	public ListPageUtil notEntryNotificationResultQuery(TodoVo vo, String loginName);

	public ListPageUtil  notEntryNoSampleResultQuery(TodoVoPo vo, String loginName);


	//根据市区id查询未预约结肠镜检查
	public Result notEntryAllocation(String loginName);
	//根据市区id查询未完成结肠镜检查
	public Result notFinishCheck(TodoVo vo, String loginName);
	//未通知筛查结果告知书
	public Result notPutOutNotices(TodoVo vo, String loginName);

    ListPageUtil notEntryNoDNAResultQuery(TodoVoPo vo, String loginName);

	ListPageUtil notEntryCancerQuery(TodoVoPo vo, String loginName);
}
