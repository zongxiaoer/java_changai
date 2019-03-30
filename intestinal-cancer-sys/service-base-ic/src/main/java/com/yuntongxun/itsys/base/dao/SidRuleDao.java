package com.yuntongxun.itsys.base.dao;

import java.util.List;

import com.yuntongxun.itsys.base.po.dto.sidrule.DepartmentSidRuleDto;

public interface SidRuleDao {
	//查询相关生成sid规则信息
	public List<DepartmentSidRuleDto> querySidRuleList(String loginName);	
	//根据类型和最大值校验
	public Boolean flag(String loginName);

    List<DepartmentSidRuleDto> findById(Integer id);
}
