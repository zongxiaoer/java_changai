package com.yuntongxun.itsys.base.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.yuntongxun.itsys.base.po.dto.sidrule.DepartmentSidRuleDto;

public interface SidRuleService {
	//查询sid规则相关信息
	public List<DepartmentSidRuleDto> querySidRuleList(HttpServletRequest req);
}
