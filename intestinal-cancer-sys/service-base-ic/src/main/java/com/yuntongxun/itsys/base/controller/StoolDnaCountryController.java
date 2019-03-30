/**
 * Project Name:service-base-ic
 * File Name:StoolDnaAreaController.java
 * Package Name:com.yuntongxun.itsys.base.controller
 * Date:2018年4月19日下午6:23:54
 * Copyright (c) 2018, ty All Rights Reserved.
 *
*/

package com.yuntongxun.itsys.base.controller;

import com.yuntongxun.itsys.base.common.AbstractController;
import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.*;
import com.yuntongxun.itsys.base.po.StoolDna;
import com.yuntongxun.itsys.base.po.dto.ItsysUserDto;
import com.yuntongxun.itsys.base.po.dto.message.HospitalMessageCenterDto;
import com.yuntongxun.itsys.base.service.HospitalMessageCenterService;
import com.yuntongxun.itsys.base.service.StoolDnaService;
import com.yuntongxun.itsys.base.vo.Result;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author zongt
 * @func
 * @desc
 * @create 2018/5/下午8:13:13
 * @request
 * @response
 **/
@RestController
public class StoolDnaCountryController extends AbstractController{

	@Autowired
	private StoolDnaService stoolDnaService;

	@Autowired
	private HospitalMessageCenterService hospitalMessageCenterService;
	
	private final Logger log = LogManager.getLogger(StoolDnaController.class);
	
	/**
	 * 粪便DNA管理根据条件查询受试者接口
	 * 
	 * @param req
	 * @param resp
	 * @param body
	 * @return
	 * @throws ItSysException
	 */
	@RequestMapping(value = "/country/stool/dna/query", method = RequestMethod.POST)
	public Result query(HttpServletRequest req, HttpServletResponse resp, @RequestBody StoolDna dna) throws ItSysException{

		printStartTag("/country/stool/dna/query");
		printHttpPacket(req, null);
		log.info("@Controller 国家粪便DNA管理根据条件查询受试者接收参数为：{}", dna);
		String loginName=CookieUtil.getCookieByLoginName(req);
		log.info("@Controller国家粪便DNA管理根据条件查询受试者 登陆者账号 loginName：{}", loginName);
		ListPageUtil listPage = stoolDnaService.queryCountryDna(dna,loginName);
		Result result=new Result(listPage.getResultList(),listPage.getPageInfo());
		printEndTag("/area/stool/dna/query");
		 log.info("返回查询对象个数={}", listPage.getResultList()==null ? 0 : listPage.getResultList().size());
		return result;
	}

	/**
	 * 修改发布状态  1--
	 *
	 * @param req
	 * @param resp
	 * @param body
	 * @return
	 * @throws ItSysException
	 */
	@RequestMapping(value = "/country/stool/dna/updateIssuedStatus", method = RequestMethod.POST)
	public Result updateIssuedStatus(HttpServletRequest req, HttpServletResponse resp, @RequestBody StoolDna dna) throws ItSysException{

		printStartTag("/country/stool/dna/query");
		printHttpPacket(req, null);
		log.info("@Controller 国家粪便DNA管理根据条件查询受试者接收参数为：{}", dna);

		String loginName = CookieUtil.getCookieByLoginName(req);
		//校验参数
		if(dna.getId()==null||dna.getDnaCheckInformStatus()==null||StringUtils.isEmpty(dna.getSid())){
			return  new Result(GlobalErrorCode.PARAMETER_ERR_CODE,GlobalErrorCode.PARAMETER_ERR_MSG);
		}

		try{
			stoolDnaService.updateIssuedStatus(dna);
		}catch (Exception e){
			return  new Result(GlobalErrorCode.PARAMETER_ERR_CODE,GlobalErrorCode.PARAMETER_ERR_MSG);
		}
		//根据id找到对应的登录名
		//select * from itsys_user where id in ( select t1.`userId` from itsys_department_member t1 left join   hospital_stool_dna t2 on t1.`deptId`=t2.`community_dept_id`  where t2.id=1)
		List<ItsysUserDto> itsysUserDtos =stoolDnaService.queryloginNameById(dna.getId());
		List<ItsysUserDto> itsysUserDtoss =stoolDnaService.queryloginNameByDId(dna.getId());
		//根据
		itsysUserDtos.addAll(itsysUserDtoss);

		String meaasge_typpe=Constans.meaasge_typpe5;
		//获取退出原因
		String text="";
		String sid=dna.getSid();
		String meaasge_text_typpe="";
		String courierNumber="";
		String remark="";

		//添加消息中心
		List<HospitalMessageCenterDto> hospitalMessageCenterDtoList = new ArrayList<>();
		for (ItsysUserDto itsysUserDto : itsysUserDtos) {
			HospitalMessageCenterDto hospitalMessageCenterDto = new HospitalMessageCenterDto();
			hospitalMessageCenterDto.setSendUser(loginName);
			String accpetName = itsysUserDto.getLoginname();
			hospitalMessageCenterDto.setAcceptUser(accpetName);
			hospitalMessageCenterDto.setMessageType(meaasge_typpe);
			hospitalMessageCenterDto.setData_id(dna.getId());
			hospitalMessageCenterDto.setForm_type(Constans.HOSPITAL_STOOL_DNA);
			hospitalMessageCenterDto.setSid(sid);
			/**
			 * 1  异常    违反方案
			 *           sid
			 *           已经退出研究
			 *           sid、text
			 *           诊断为结直肠癌
			 *           sid
			 *申请|发放编辑
			 *          快递模块
			 *          sendUser、acceptUser、courierNumber
			 *          管理模块
			 *          sendUser、acceptUser、text（模块+sid）
			 *通知发放
			 *    public static String getMessage(String sendUser, String acceptUser, String meaasgeType, String text, String sid, String meaasgeTextType, String courierNumber) {
			 *
			 *
			 *
			 *
			 */
			String message = SendMessageCenter.getMessage(loginName, accpetName, meaasge_typpe, text, sid,meaasge_text_typpe,courierNumber,remark);
			hospitalMessageCenterDto.setMessageText(message);
			hospitalMessageCenterDto.setId(UUID.randomUUID().toString().replace("-", ""));
			hospitalMessageCenterDtoList.add(hospitalMessageCenterDto);
		}
		Boolean isok=false;
		if(dna.getDnaCheckInformStatus().equals(Constans.DNA_COUNTRY_CHECK_INFORM_STATUS2)){
			isok=true;
		}
		hospitalMessageCenterService.save(hospitalMessageCenterDtoList, null,Constans.APPLY_EDIT_STATUS2,Constans.EDIT_STATUS1,Constans.APPROVAL_STATUS1,"","",isok,null);

		return new Result(GlobalErrorCode.NORMAL_RESPONSE,GlobalErrorCode.SUCCESS);
	}


	/*
	 * 批量修改
	 *
	 * @author ${zongt}
	 * @since v1.0.0
	 */
	@RequestMapping(value = "/country/stool/dna/updateListIssuedStatus", method = RequestMethod.POST)
	public Result updateListIssuedStatus(HttpServletRequest req, HttpServletResponse resp, @RequestBody List<StoolDna> dnas) throws ItSysException{
		printStartTag("/country/stool/dna/query");
		printHttpPacket(req, null);
		log.info("@Controller 国家粪便DNA管理根据条件查询受试者接收参数为：{}", dnas);

		String loginName = CookieUtil.getCookieByLoginName(req);
		for (StoolDna dna:dnas) {
			//校验参数
			if(dna.getId()==null||dna.getDnaCheckInformStatus()==null||StringUtils.isEmpty(dna.getSid())){
				return  new Result(GlobalErrorCode.PARAMETER_ERR_CODE,GlobalErrorCode.PARAMETER_ERR_MSG);
			}
		}

		try{
			stoolDnaService.updateIssuedStatusList(dnas);
		}catch (Exception e){
			return  new Result(GlobalErrorCode.PARAMETER_ERR_CODE,GlobalErrorCode.PARAMETER_ERR_MSG);
		}
		//根据id找到对应的登录名
		for (StoolDna dna:dnas) {
			List<ItsysUserDto> itsysUserDtos =stoolDnaService.queryloginNameById(dna.getId());
			List<ItsysUserDto> itsysUserDtoss =stoolDnaService.queryloginNameByDId(dna.getId());
			//根据
			itsysUserDtos.addAll(itsysUserDtoss);

			String meaasge_typpe=Constans.meaasge_typpe5;
			//获取退出原因
			String text="";
			String sid=dna.getSid();
			String meaasge_text_typpe="";
			String courierNumber="";
			String remark="";

			//添加消息中心
			List<HospitalMessageCenterDto> hospitalMessageCenterDtoList = new ArrayList<>();
			for (ItsysUserDto itsysUserDto : itsysUserDtos) {
				HospitalMessageCenterDto hospitalMessageCenterDto = new HospitalMessageCenterDto();
				hospitalMessageCenterDto.setSendUser(loginName);
				String accpetName = itsysUserDto.getLoginname();
				hospitalMessageCenterDto.setAcceptUser(accpetName);
				hospitalMessageCenterDto.setMessageType(meaasge_typpe);
				/**
				 * 1  异常    违反方案
				 *           sid
				 *           已经退出研究
				 *           sid、text
				 *           诊断为结直肠癌
				 *           sid
				 *申请|发放编辑
				 *          快递模块
				 *          sendUser、acceptUser、courierNumber
				 *          管理模块
				 *          sendUser、acceptUser、text（模块+sid）
				 *通知发放
				 *    public static String getMessage(String sendUser, String acceptUser, String meaasgeType, String text, String sid, String meaasgeTextType, String courierNumber) {
				 *
				 *
				 *
				 *
				 */
				String message = SendMessageCenter.getMessage(loginName, accpetName, meaasge_typpe, text, sid,meaasge_text_typpe,courierNumber,remark);
				hospitalMessageCenterDto.setMessageText(message);
				hospitalMessageCenterDto.setId(UUID.randomUUID().toString().replace("-", ""));
				hospitalMessageCenterDtoList.add(hospitalMessageCenterDto);
			}
			Boolean isok=false;
			if(dna.getDnaCheckInformStatus().equals(Constans.DNA_COUNTRY_CHECK_INFORM_STATUS2)){
				isok=true;
			}
			hospitalMessageCenterService.save(hospitalMessageCenterDtoList, null,Constans.APPLY_EDIT_STATUS2,Constans.EDIT_STATUS1,Constans.APPROVAL_STATUS1,"","",isok,null);
		}
		return new Result(GlobalErrorCode.NORMAL_RESPONSE,GlobalErrorCode.SUCCESS);

	}


	@Override
	protected String getClassName() {
		return StoolDnaCountryController.class.getName();
	}

}

