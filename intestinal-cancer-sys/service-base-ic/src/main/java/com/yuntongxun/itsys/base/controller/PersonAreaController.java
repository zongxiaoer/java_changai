/**
 * Project Name:service-base-ic
 * File Name:PersonAreaController.java
 * Package Name:com.yuntongxun.itsys.base.controller
 * Date:2018年4月19日下午5:27:15
 * Copyright (c) 2018, ty All Rights Reserved.
 *
*/

package com.yuntongxun.itsys.base.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yuntongxun.itsys.base.common.AbstractController;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.CookieUtil;
import com.yuntongxun.itsys.base.common.util.GlobalErrorCode;
import com.yuntongxun.itsys.base.common.util.JSONUtils;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.po.HospitalReview;
import com.yuntongxun.itsys.base.service.PersonService;
import com.yuntongxun.itsys.base.vo.Result;

/**
 * 地区受试者管理
 * ClassName:PersonAreaController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018年4月19日 下午5:27:15 <br/>
 * @author   ty
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@RestController
public class PersonAreaController extends AbstractController {

	@Autowired
	private PersonService personService;
	
	private final Logger log = LogManager.getLogger(PersonAreaController.class);

	/**
	 * 查询受试者列表
	 * 
	 * @param req
	 * @param resp
	 * @param body
	 * @return
	 * @throws ItSysException
	 */
	@RequestMapping(value = "/area/hospital/person/query", method = RequestMethod.POST)
	public Result query(HttpServletRequest req, HttpServletResponse resp, @RequestBody HospitalReview person) throws ItSysException{

		printStartTag("/area/hospital/person/query");
		printHttpPacket(req, null);
		log.info("@Controller受试者列表接收参数为：{}", JSONUtils.toJson(person));
		String loginName=CookieUtil.getCookieByLoginName(req);
		log.info("@Controller提交资格审核表单登陆者账号 loginName：{}", loginName);
		//HospitalReview person;
/*		try {
			person = JSONUtils.jsonToBeanDateSerializer(body, HospitalReview.class,"yyyy-MM-dd");
		} catch (Exception e) {
			throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,e.getMessage());
		}*/
		ListPageUtil listPage = personService.queryAreaPage(person,loginName);
		Result result=new Result(listPage.getResultList(),listPage.getPageInfo());
		printEndTag("/area/hospital/person/query");
		 log.info("返回查询对象个数={}", listPage.getResultList()==null ? 0 : listPage.getResultList().size());
		return result;
	}
	
	@Override
	protected String getClassName() {
		return PersonAreaController.class.getName();
	}
}

