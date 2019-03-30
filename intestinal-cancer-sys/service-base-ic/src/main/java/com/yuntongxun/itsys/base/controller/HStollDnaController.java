package com.yuntongxun.itsys.base.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.po.HospitalReview;
import com.yuntongxun.itsys.base.po.Role;
import com.yuntongxun.itsys.base.service.*;
import com.yuntongxun.itsys.base.vo.*;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yuntongxun.itsys.base.common.AbstractController;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.CookieUtil;
import com.yuntongxun.itsys.base.common.util.GlobalErrorCode;
import com.yuntongxun.itsys.base.common.util.JSONUtils;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.common.util.ResultUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 查询 未录入粪便DNA装置编号 受试者列表
 * @author liugb
 * Date 2018 4 17
 */
@RestController
public class HStollDnaController extends AbstractController{
	 @Autowired
	 private HStollDnaService hStollDnaService;

	@Autowired
	private PersonService personService;

	@Autowired
	private ColonoscopyService colonoscopyService;

	@Autowired
	private UserService userService;

	 private final Logger log = LogManager.getLogger(HStollDnaController.class.getName()); 
	 /**
	  * 查询 未录入粪便DNA装置编号 受试者列表
	  * @param req
	  * @param resp
	  * @param body
	  * @return
	  */
	 @RequestMapping(value = "/hospital/person/notentry/dnacode/query", method = RequestMethod.POST)
	 public Result notEntryDNACodeList(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) {
		 printStartTag("查询待录入粪便DNA装置编号待办");
	   		printHttpPacket(req, null);
			String loginName=CookieUtil.getCookieByLoginName(req);
			log.info("@Controller HFitResultController接收参数为：{}", body);
			TodoVo vo = JSONUtils.toBean(body, TodoVo.class);
			try{
		       	vo = JSONUtils.toBean(body, TodoVo.class);
	        }catch(Exception e){
				throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,"Parse request body error.");
	        }
			ListPageUtil listPage = hStollDnaService.notEntryDNACodeList(vo, loginName);
			
			Result result=new Result(listPage.getResultList(),listPage.getPageInfo());
	   		printEndTag("查询待录入粪便DNA装置编号待办");
	   		return result;
		}
	 /**
	  * 根据市区id查询未录入粪便DNA编号
	  * @param req
	  * @param resp
	  * @param body
	  * @return
	  */
	 @RequestMapping(value = "/hospital/person/notentry/dnacode/notEntryDNAResultList", method = RequestMethod.POST)
	 public Result notEntryDNAResultList(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) {
		 printStartTag("根据市区id查询待录入粪便DNA装置编号待办");
	   		printHttpPacket(req, null);
			String loginName=CookieUtil.getCookieByLoginName(req);
			log.info("@Controller HFitResultController接收参数为：{}", body);
			TodoVo vo = JSONUtils.toBean(body, TodoVo.class);
			Result result = null;
			try{
		       	vo = JSONUtils.toBean(body, TodoVo.class);
		       	result = hStollDnaService.notEntryDNAResultList(vo, loginName);
	        }catch(Exception e){
				throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,"Parse request body error.");
	        }
	   		printEndTag("根据市区id查询待录入粪便DNA装置编号待办");
	   		return result;
		}  
	@Override
	protected String getClassName() {
		// TODO Auto-generated method stub
		return HStollDnaController.class.getName();
	}

	/**
	 * @func
	 * @desc    新增DNA
	 * @author zongt
	 * @create 2018/5/下午3:4747
	 * @request
	 * @response
	 **/
	@RequestMapping(value = "/hospital/person/notentry/dnacode/addDNA", method = RequestMethod.POST)
	public String addDNA(HttpServletRequest req, HttpServletResponse resp, @RequestBody TodoVo todoVo) {
		log.info("录入DNA编码");
		log.info("传入包体，body:{}", todoVo.toString());

		if (StringUtils.isEmpty(todoVo.getSid()) || StringUtils.isEmpty(todoVo.getDnaCode()) || StringUtils.isEmpty(todoVo.getPersonCode()) || StringUtils.isEmpty(todoVo.getReleaseDate())||todoVo.getDnaCode().length()!=12) {
			return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
		}
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date releaseDate = df.parse(todoVo.getReleaseDate());
			todoVo.setReleaseDateForSql(releaseDate);
		} catch (Exception e) {
			throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO, "Parse request body error.");
		}


		//校验sid 是否存在
		HospitalReview hospitalReview;
		try {
			hospitalReview = personService.getBySid(todoVo.getSid());
		} catch (EmptyResultDataAccessException e) {
			log.info("getBySid hospital_intestine_review is error data is null");
			hospitalReview= null;
		} catch (IncorrectResultSizeDataAccessException e) {
			log.info("getBySid hospital_intestine_review is error data size is >1");
			hospitalReview= null;
		}
		if (hospitalReview == null) {
			log.info("/hospital/person/notentry/dnacode/addDNA  对应受试者sid不存在");
			return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_PERSON_NULL_CODE, GlobalErrorCode.ERR_PERSON_NULL_MSG));
		}


		String loginName = CookieUtil.getCookieByLoginName(req);
		String sid = todoVo.getSid();
		//判断是否是本区sid
		DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
		Integer areaDeptId1 = doctorInfo.getAreaDeptId();
		Integer communityDeptId1 = doctorInfo.getCommunityDeptId();

		List<HospitalReview> hospitalReviews=null;
		if(areaDeptId1!=null&&communityDeptId1!=null){
			List<Role> roles = userService.queryRoleByUserId(doctorInfo.getId());
			String userName="";
			if(roles!=null&&roles.size()>0){
				userName=loginName;
			}
			hospitalReviews = personService.queryBySidID(sid, areaDeptId1, communityDeptId1,userName);

		}else if(communityDeptId1==null&&areaDeptId1!=null){
			hospitalReviews = personService.queryAreaBySidID(sid, areaDeptId1);
		}

		if(hospitalReviews.size()<1){
			log.info("/fit/result/addFit  该区不存在sid");
			return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_STOOL_VIEW_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_STOOL_VIEW_ERROR_MSG));
		}

		//校验DNA编码
		String dnaCode = todoVo.getDnaCode();
		TodoVo todoVoByCode = hStollDnaService.queryByDnaCode(dnaCode);
		if(todoVoByCode !=null){
			log.info("/hospital/person/notentry/dnacode/addDNA   DNA编码已经存在");
			return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_STOOL_DNA_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_STOOL_DNA_ERROR_MSG));
		}
		//获取stage
		Integer communityDeptId = hospitalReview.getCommunityDeptId();
		Integer areaDeptId = hospitalReview.getAreaDeptId();
		Integer stageCy = hospitalReview.getStageCy();
		todoVo.setCommunityDeptId(communityDeptId);
		todoVo.setAreaDeptId(areaDeptId);
		todoVo.setStage(stageCy);
		todoVo.setOperationSource(Constans.OPERATION_SOURCE_TYPE);
		todoVo.setCodeEntryStatus(2);
		todoVo.setSid(todoVo.getSid().toUpperCase());
		//新增
		Integer integer = hStollDnaService.saveDNA(todoVo);
		if (integer > 0) {
			return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, null, null));
		}
		return JSONUtils.toJson(new Response(GlobalErrorCode.UNKNOWN_ERROR, GlobalErrorCode.UNKNOWN_ERROR_MSG));
	}

}
