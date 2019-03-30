package com.yuntongxun.itsys.base.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yuntongxun.itsys.base.cache.RedisManager;
import com.yuntongxun.itsys.base.common.AbstractController;
import com.yuntongxun.itsys.base.common.constans.RedisConstant;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.CookieUtil;
import com.yuntongxun.itsys.base.common.util.GlobalErrorCode;
import com.yuntongxun.itsys.base.common.util.JSONUtils;
import com.yuntongxun.itsys.base.common.util.StringUtil;
import com.yuntongxun.itsys.base.po.dto.sidrule.DepartmentSidRuleDto;
import com.yuntongxun.itsys.base.service.SidRuleService;
import com.yuntongxun.itsys.base.service.UserService;
import com.yuntongxun.itsys.base.vo.DoctorInfo;
import com.yuntongxun.itsys.base.vo.Result;
import com.yuntongxun.itsys.base.vo.TodoVo;
/**
 * sid生成规操作
 * @author liugb
 * Date 2018、6、6
 */
@RestController
public class SidRuleController extends AbstractController{
	
	@Autowired
	private RedisManager redis;

	public static final int HOSPITAL_TYPE_COMMUNITY = 1;
	public static final int HOSPITAL_TYPE_AREA = 2;
	public static final int HOSPITAL_TYPE_NATION = 3;
	
	
	private final Logger log = LogManager.getLogger(HtEventController.class.getName());
	@Autowired
	private UserService userService;
	@Autowired 
	private SidRuleService sidRuleService;
	/**
	 * 根据登陆用户名称查询相关信息
	 * @param req
	 * @param resp
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/hospital/sidrule/list/getSidRuleList", method = RequestMethod.POST)
	public Result getSidRuleList(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body){
		//获取当前用户信息
		String key = RedisConstant.HOSPITAL_KEY_INFO+CookieUtil.getCookieByLoginName(req);
		String value = redis.get(key);
		DoctorInfo doctorInfo = null;
		DepartmentSidRuleDto dto = JSONUtils.toBean(body, DepartmentSidRuleDto.class);
		int deptId = 0;
		if(StringUtil.isNotBlank(value)){
			doctorInfo = JSONUtils.toBean(value, DoctorInfo.class);
			log.info("doctorInfo==>"+doctorInfo);
		}
		if(doctorInfo == null){
			doctorInfo = userService.getHospitalInfo(CookieUtil.getCookieByLoginName(req));
		}
		if(doctorInfo == null){
			throw new ItSysException(GlobalErrorCode.ERR_USER_INFO_INCOMPLETE_ERROR_CODE,GlobalErrorCode.ERR_USER_INFO_INCOMPLETE_ERROR_MSG);
		}	
		if(HOSPITAL_TYPE_COMMUNITY == doctorInfo.getHospitalType()){
			deptId = doctorInfo.getCommunityDeptId();
		}else if(HOSPITAL_TYPE_AREA == doctorInfo.getHospitalType()){
			deptId = doctorInfo.getAreaDeptId();
		}else{
			deptId = doctorInfo.getNationDeptId();
		}
//		List<DepartmentSidRuleDto> list = sidRuleService.querySidRuleList(deptId, dto);
		List<DepartmentSidRuleDto> list = new ArrayList<DepartmentSidRuleDto>();
		Result result = new Result(list);
		return result;
	}
	@Override
	protected String getClassName() {
		// TODO Auto-generated method stub
		return null;
	}

}
