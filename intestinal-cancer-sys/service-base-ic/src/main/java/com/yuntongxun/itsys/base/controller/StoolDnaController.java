/**
 * Project Name:service-base-ic
 * File Name:StoolDnaController.java
 * Package Name:com.yuntongxun.itsys.base.controller
 * Date:2018年4月17日下午8:04:33
 * Copyright (c) 2018, ty All Rights Reserved.
 *
*/

package com.yuntongxun.itsys.base.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yuntongxun.itsys.base.service.HStollDnaService;
import com.yuntongxun.itsys.base.vo.Response;
import com.yuntongxun.itsys.base.vo.TodoVo;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.yuntongxun.itsys.base.common.AbstractController;
import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.CookieUtil;
import com.yuntongxun.itsys.base.common.util.GlobalErrorCode;
import com.yuntongxun.itsys.base.common.util.JSONUtils;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.common.util.ResultUtil;
import com.yuntongxun.itsys.base.po.HospitalReview;
import com.yuntongxun.itsys.base.po.HospitalStoolDNA;
import com.yuntongxun.itsys.base.po.ReviewResult;
import com.yuntongxun.itsys.base.po.StoolDna;
import com.yuntongxun.itsys.base.service.StoolDnaService;
import com.yuntongxun.itsys.base.vo.Result;

/**
 * 粪便DNA管理
 * ClassName:StoolDnaController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018年4月17日 下午8:04:33 <br/>
 * @author   ty
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@RestController
public class StoolDnaController extends AbstractController {

	@Autowired
	private StoolDnaService stoolDnaService;

	@Autowired
	private HStollDnaService hStollDnaService;

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
	@RequestMapping(value = "/stool/dna/query", method = RequestMethod.POST)
	public Result query(HttpServletRequest req, HttpServletResponse resp, @RequestBody StoolDna dna) throws ItSysException {

		printStartTag("/stool/dna/query");
		printHttpPacket(req, null);
		log.info("@Controller粪便DNA管理根据条件查询受试者接收参数为：{}", dna.toString());
		String loginName = CookieUtil.getCookieByLoginName(req);
		log.info("@Controller粪便DNA管理根据条件查询受试者 登陆者账号 loginName：{}", loginName);
		//StoolDna dna;
		try {
			//dna = JSONUtils.toBean(body, StoolDna.class);
		} catch (Exception e) {
			throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO, "Parse request body error.");
		}
		ListPageUtil listPage = stoolDnaService.query(dna, loginName);
		Result result = new Result(listPage.getResultList(), listPage.getPageInfo());
		printEndTag("/stool/dna/query");
		log.info("返回查询对象个数={}", listPage.getResultList() == null ? 0 : listPage.getResultList().size());
		return result;
	}

	/**
	 * 录入粪便DNA编码
	 *
	 * @param req
	 * @param resp
	 * @param body
	 * @return
	 * @throws ItSysException
	 */
	@RequestMapping(value = "/stool/dna/addDnaCode", method = RequestMethod.POST)
	public Result addDnaCode(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) throws ItSysException {

		printStartTag("/stool/dna/addDnaCode");
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
			log.info("/stool/dna/addDnaCode   DNA编码已经存在");
			Result result = new Result(GlobalErrorCode.ERR_HOSPITAL_STOOL_DNA_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_STOOL_DNA_ERROR_MSG);
			return result;
		}
		stoolDnaService.addDnaCode(dna);
		Result result = ResultUtil.success(Constans.KEY_SUCCESS_STATUS, Constans.KEY_SUCCESS_MSG);
		printEndTag("/stool/dna/addDnaCode");
		return result;
	}

	/**
	 * 为受试者新增一条粪便DNA记录---此方法没用作废调
	 *
	 * @param req
	 * @param resp
	 * @param body
	 * @return
	 * @throws ItSysException
	 */
	@Deprecated
	@RequestMapping(value = "/stool/dna/addDnaRecord", method = RequestMethod.POST)
	public Result addDnaRecord(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) throws ItSysException {

		printStartTag("/stool/dna/addDnaRecord");
		printHttpPacket(req, null);
		log.info("@Controller为受试者新增一条粪便DNA记录参数为：{}", body);
		String loginName = CookieUtil.getCookieByLoginName(req);
		log.info("@Controller粪便DNA管理根据条件查询受试者 登陆者账号 loginName：{}", loginName);
		StoolDna dna;
		try {
			dna = JSONUtils.toBean(body, StoolDna.class);
		} catch (Exception e) {
			throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO, e.getMessage());
		}
		if (dna == null) {
			throw new ItSysException(GlobalErrorCode.ERR_PARAM_NULL, "请求包体不能为空");
		}
		String sid = dna.getSid();
		dna.setSid(sid.toUpperCase());
		String dnaCode = dna.getDnaCode();
		String personCode = dna.getPersonCode();
		Date releaseDate = dna.getReleaseDate();
		if (StringUtils.isBlank(sid)) {
			throw new ItSysException(GlobalErrorCode.ERR_PARAM_NULL, "sid不能为空");
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
		dna.setOperationSource(Constans.OPERATION_SOURCE_TYPE);
		stoolDnaService.addDnaRecord(dna, loginName);
		Result result = ResultUtil.success(Constans.KEY_SUCCESS_STATUS, Constans.KEY_SUCCESS_MSG);
		printEndTag("/stool/dna/addDnaRecord");
		return result;
	}

	/**
	 * @func
	 * @desc  社区dna发放短信
	 * @author zongt
	 * @create 2018/5/20 下午1:55
	 * @request
	 * @response
	 **/
	@RequestMapping(value = "/dna/result/sendDna", method = RequestMethod.POST)
	public String sendDna(HttpServletRequest req, HttpServletResponse resp, @RequestBody StoolDna stoolDna) throws ItSysException {
		log.info("dna发放短信");
		log.info("传入包体，body:{}", stoolDna.toString());

		//校验结果
		if (StringUtils.isEmpty(stoolDna.getPhone())||stoolDna.getDnaCheckResult()==null) {
			return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
		}
		//发送短信
		boolean sendDna = stoolDnaService.sendDna(stoolDna.getPhone(),stoolDna.getDnaCheckResult());

		if (sendDna) {
			return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, null, null));
		}
		return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_FIT_RESULT_FIT_SEND_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_FIT_RESULT_FIT_SEND_ERROR_MSG));
	}

	@Override
	protected String getClassName() {
		return StoolDnaController.class.getName();
	}

	/**
	 * 查询 未发放dna结果列表 受试者列表;
	 *
	 * @param req
	 * @param resp
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/stool/dna/notIssueDna/query", method = RequestMethod.POST)
	public Result notIssueDna(HttpServletRequest req, HttpServletResponse resp, @RequestBody TodoVo vo) {
		printStartTag("查询 未发放dna结果列表 待办");
		printHttpPacket(req, null);
		String loginName = CookieUtil.getCookieByLoginName(req);
		log.info("@Controller notIssueDna：{}", vo.toString());
		//TodoVo vo = JSONUtils.toBean(body, TodoVo.class);
		try {
			//vo = JSONUtils.toBean(body, TodoVo.class);
		} catch (Exception e) {
			throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO, "Parse request body error.");
		}
		ListPageUtil listPage = stoolDnaService.notIssueDna(vo,loginName);

		Result result = new Result(listPage.getResultList(), listPage.getPageInfo());
		printEndTag("查询 未发放dna结果列表 待办");
		log.info("返回查询对象个数={}", listPage.getResultList().size());
		return result;
	}

	/**
	 * 1.3.7、为受试者发放结肠镜结果记录接口；
	 *
	 * @param req
	 * @param resp
	 * @return
	 * @throws ItSysException
	 */
	@RequestMapping(value = "/stool/dna/updateCommDnaInformStatus", method = RequestMethod.POST)
	public Result updateCommDnaInformStatus(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) throws ItSysException {
		printStartTag("dna发放告知书记录");
		log.info("传入包体，body:{}", body);
		StoolDna vo = null;
		try {
			vo = JSONUtils.toBean(body, StoolDna.class);
		} catch (Exception e) {
			throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO, "Parse request body error.");
		}
		stoolDnaService.updateCommDnaInformStatus(vo);
		Result result = new Result();
		log.info("返回查询对象：{}", JSONUtils.toJson(result));
		printEndTag("dna发放告知书记录");
		return result;
	}

	/**
	 * 查询dna详情
	 * @param req
	 * @param resp
	 * @param id
	 * @return
	 * @throws ItSysException
	 */
	@RequestMapping(value = "/stool/dna/get/{id}", method = RequestMethod.POST)
	public Result getNotification(HttpServletRequest req, HttpServletResponse resp, @PathVariable int id) throws ItSysException {
		printStartTag("查看dna详情");
		log.info("传入id:{}", id);
		if(id<=0){
			log.error("Request param error,id={}.",id);
			throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,"Request param error.");
		}
		Result result=new Result(stoolDnaService.getDnaInfoById(id));
		log.info("返回查询对象：{}", JSONUtils.toJson(result));
		printEndTag("查看dna详情");
		return result;
	}
}

