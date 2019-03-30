package com.yuntongxun.itsys.base.service;

import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.vo.DoctorInfo;
import com.yuntongxun.itsys.base.vo.Result;
import com.yuntongxun.itsys.base.vo.TodoVo;
/**
 * 查询代办接口
 * @author liugb
 * Date 2018 4 16
 */
public interface HtEventService {
	//根据社区ID查询待办事件表
	public TodoVo getHtEventSum(int deptId,int status,DoctorInfo doctorInfo) throws ItSysException;
	
	//根据国家查询未录入肠镜结果人数、未录入病理结果人数、未录入筛查告知书人数
	public String getSumByNationId(String loginName);
	//统计社区代办按照地区统计个人数
	public String getAreaSumsByNationId(String loginName);
	//查询列表
	public Result getlist(TodoVo vo);
}
