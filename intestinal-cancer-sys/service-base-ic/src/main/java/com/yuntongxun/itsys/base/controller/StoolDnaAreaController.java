/**
 * Project Name:service-base-ic
 * File Name:StoolDnaAreaController.java
 * Package Name:com.yuntongxun.itsys.base.controller
 * Date:2018年4月19日下午6:23:54
 * Copyright (c) 2018, ty All Rights Reserved.
 *
*/

package com.yuntongxun.itsys.base.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yuntongxun.itsys.base.cache.RedisManager;
import com.yuntongxun.itsys.base.common.constans.RedisConstant;
import com.yuntongxun.itsys.base.common.util.*;
import com.yuntongxun.itsys.base.po.dto.courier.HospitalReferenceRecordDto;
import com.yuntongxun.itsys.base.service.UserService;
import com.yuntongxun.itsys.base.vo.DoctorInfo;
import com.yuntongxun.itsys.base.vo.Response;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yuntongxun.itsys.base.common.AbstractController;
import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.po.StoolDna;
import com.yuntongxun.itsys.base.service.HStollDnaService;
import com.yuntongxun.itsys.base.service.StoolDnaService;
import com.yuntongxun.itsys.base.vo.Result;
import com.yuntongxun.itsys.base.vo.TodoVo;

/**
 * ClassName:StoolDnaAreaController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018年4月19日 下午6:23:54 <br/>
 * @author   ty
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@RestController
public class StoolDnaAreaController extends AbstractController{

	@Autowired
	private StoolDnaService stoolDnaService;
	@Autowired
	private HStollDnaService hStollDnaService;

	@Autowired
	private RedisManager redisManager;

	@Autowired
	private UserService userService;
	
	private final Logger log = LogManager.getLogger(StoolDnaController.class);
	
	/**
	 * 粪便DNA管理根据条件查询受试者接口
	 * 
	 * @param req
	 * @param resp
	 * @param dna
	 * @return
	 * @throws ItSysException
	 */
	@RequestMapping(value = "/area/stool/dna/query", method = RequestMethod.POST)
	public Result query(HttpServletRequest req, HttpServletResponse resp, @RequestBody StoolDna dna) throws ItSysException{

		printStartTag("/area/stool/dna/query");
		printHttpPacket(req, null);
		log.info("@Controller地区粪便DNA管理根据条件查询受试者接收参数为：{}", dna);
		String loginName=CookieUtil.getCookieByLoginName(req);
		log.info("@Controller地区粪便DNA管理根据条件查询受试者 登陆者账号 loginName：{}", loginName);
//		StoolDna dna;
//		try {
//			dna = JSONUtils.toBean(body, StoolDna.class);
//		} catch (Exception e) {
//			throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,"Parse request body error.");
//		}
		ListPageUtil listPage = stoolDnaService.queryAreaDna(dna,loginName);
		Result result=new Result(listPage.getResultList(),listPage.getPageInfo());
		printEndTag("/area/stool/dna/query");
		 log.info("返回查询对象个数={}", listPage.getResultList()==null ? 0 : listPage.getResultList().size());
		return result;
	}

	@RequestMapping(value = "/area/stool/dna/queryById", method = RequestMethod.POST)
	public String queryById(HttpServletRequest req, HttpServletResponse resp, @RequestBody StoolDna dna) throws ItSysException{

		//根据id查询DNA信息
		List<StoolDna> stoolDnas = stoolDnaService.queryById(dna.getId());
		return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, stoolDnas.get(0), null));

	}

	/**
	 * 录入粪便DNA编码(地区)
	 *
	 * @param req
	 * @param resp
	 * @param body
	 * @return
	 * @throws ItSysException
	 */
	@RequestMapping(value = "/area/stool/dna/addDnaCode", method = RequestMethod.POST)
	public Result areaAddDnaCode(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) throws ItSysException {

		printStartTag("/area/stool/dna/addDnaCode");
		printHttpPacket(req, null);
		log.info("@Controller录入粪便DNA编码参数为：{}", body);
		StoolDna dna;
		try {
			dna = JSONUtils.jsonToBeanDateSerializer(body, StoolDna.class, "yyyy-MM-dd");
		} catch (Exception e) {
			throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO, e.getMessage());
		}
		if (dna == null) {
			throw new ItSysException(GlobalErrorCode.ERR_PARAM_NULL, "请求包体不能为空");
		}
		Integer id = dna.getId();
		String dnaCode = dna.getDnaCode();
		String personCode = dna.getPersonCode();
		Date releaseDate = dna.getReleaseDate();
		if (id == null) {
			throw new ItSysException(GlobalErrorCode.ERR_PARAM_NULL, "id不能为空");
		}
		if (StringUtils.isBlank(dnaCode)) {
			throw new ItSysException(GlobalErrorCode.ERR_PARAM_NULL, "dnaCode编码不能为空");
		}
		if (StringUtils.isBlank(personCode)) {
			throw new ItSysException(GlobalErrorCode.ERR_PARAM_NULL, "personCode编码不能为空");
		}
		if (releaseDate == null) {
			throw new ItSysException(GlobalErrorCode.ERR_PARAM_NULL, "releaseDate不能为空");
		}

		//校验DNA编码
		TodoVo todoVoByCode = hStollDnaService.queryByDnaCode(dnaCode);
		if (todoVoByCode != null) {
			log.info("/area/stool/dna/addDnaCode   DNA编码已经存在");
			Result result = new Result(GlobalErrorCode.ERR_HOSPITAL_STOOL_DNA_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_STOOL_DNA_ERROR_MSG);
			return result;
		}
		stoolDnaService.addDnaCode(dna);
		Result result = ResultUtil.success(Constans.KEY_SUCCESS_STATUS, Constans.KEY_SUCCESS_MSG);
		printEndTag("/area/stool/dna/addDnaCode");
		return result;
	}


	/*
	 * 修改DNA编码
	 *
	 * @author ${zongt}
	 * @since v1.0.0
	 */
	@RequestMapping(value = "/area/stool/dna/updateDnaCode", method = RequestMethod.POST)
	public Result updateDnaCode(HttpServletRequest req, HttpServletResponse resp, @RequestBody String  body) throws ItSysException {

		StoolDna dna;
		try {
			dna = JSONUtils.jsonToBeanDateSerializer(body, StoolDna.class, "yyyy-MM-dd");
		} catch (Exception e) {
			throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO, e.getMessage());
		}
		if (dna == null) {
			throw new ItSysException(GlobalErrorCode.ERR_PARAM_NULL, "请求包体不能为空");
		}

		String loginName=CookieUtil.getCookieByLoginName(req);


		DoctorInfo doctorInfo = new DoctorInfo();
		String infoString = redisManager.get(RedisConstant.HOSPITAL_KEY_INFO + loginName);
		if (StringUtil.isEmpty(infoString)) {
			doctorInfo = userService.getHospitalInfo(loginName);
		} else {
			doctorInfo = JSONUtils.toBean(infoString, DoctorInfo.class);
		}

		boolean isArea=false;

		if(doctorInfo.getCommunityDeptId()==null){
			isArea=true;
		}

		//根据id查询DNA信息
		Integer id = dna.getId();
		String dnaCode = dna.getDnaCode();
		String personCode = dna.getPersonCode();
		Date releaseDate = dna.getReleaseDate();
		if (id == null) {
			throw new ItSysException(GlobalErrorCode.ERR_PARAM_NULL, "id不能为空");
		}
		if (StringUtils.isBlank(dnaCode)) {
			throw new ItSysException(GlobalErrorCode.ERR_PARAM_NULL, "dnaCode编码不能为空");
		}
		if (StringUtils.isBlank(personCode)) {
			throw new ItSysException(GlobalErrorCode.ERR_PARAM_NULL, "personCode编码不能为空");
		}
		if (releaseDate == null) {
			throw new ItSysException(GlobalErrorCode.ERR_PARAM_NULL, "releaseDate不能为空");
		}
		//根据id查询DNA信息
		List<StoolDna> stoolDnas = stoolDnaService.queryById(id);
		if(stoolDnas.size()!=1){
			throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG);
		}
		if(!stoolDnas.get(0).getDnaCode().equals(dna.getDnaCode())){
			//校验DNA编码
			TodoVo todoVoByCode = hStollDnaService.queryByDnaCode(dnaCode);
			if (todoVoByCode != null) {
				log.info("/area/stool/dna/addDnaCode   DNA编码已经存在");
				Result result = new Result(GlobalErrorCode.ERR_HOSPITAL_STOOL_DNA_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_STOOL_DNA_ERROR_MSG);
				return result;
			}
		}

		HospitalReferenceRecordDto hospitalReferenceRecordDto = new HospitalReferenceRecordDto();
		hospitalReferenceRecordDto.setStatus(Constans.HOSPITAL_REFERENCE_RECORD_STATUS_FINISH);
		hospitalReferenceRecordDto.setDataId(id);
		hospitalReferenceRecordDto.setFormType(Constans.HOSPITAL_STOOL_DNA);
		hospitalReferenceRecordDto.setEditPerson(loginName);
		String dataText = JSONUtils.toJson(stoolDnas.get(0));
		hospitalReferenceRecordDto.setOldData(dataText);
		stoolDnaService.updateDnaCode(dna,hospitalReferenceRecordDto,isArea);
		Result result = ResultUtil.success(Constans.KEY_SUCCESS_STATUS, Constans.KEY_SUCCESS_MSG);
		printEndTag("/area/stool/dna/addDnaCode");
		return result;
	}

	@Override
	protected String getClassName() {
		return StoolDnaAreaController.class.getName();
	}

}

