package com.yuntongxun.itsys.base.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuntongxun.itsys.base.common.constans.RedisConstant;
import com.yuntongxun.itsys.base.common.util.CookieUtil;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.common.util.ResultUtil;
import com.yuntongxun.itsys.base.dao.SidRuleDao;
import com.yuntongxun.itsys.base.po.dto.sidrule.DepartmentSidRuleDto;
import com.yuntongxun.itsys.base.service.ColonoscopyService;
import com.yuntongxun.itsys.base.service.SidRuleService;
import com.yuntongxun.itsys.base.vo.DoctorInfo;
import com.yuntongxun.itsys.base.vo.Result;

@Service
public class SidRuleServiceImpl implements SidRuleService{
	@Autowired
	private ColonoscopyService colonoscopyService;
	
	@Autowired
	private SidRuleDao sidRuleDao;

	private final Logger log = LogManager.getLogger(SidRuleServiceImpl.class.getName());
    /**
     * 查询规则列表
     */
	@Override
	public List<DepartmentSidRuleDto> querySidRuleList(HttpServletRequest req) {
		// TODO Auto-generated method stub
		String loginName = CookieUtil.getCookieByLoginName(req);
		List<DepartmentSidRuleDto> po = sidRuleDao.querySidRuleList(loginName);
		log.info("querySidRuleList End");
		return po;
	}

}
