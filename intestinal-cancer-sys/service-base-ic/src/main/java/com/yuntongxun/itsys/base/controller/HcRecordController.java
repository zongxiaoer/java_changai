package com.yuntongxun.itsys.base.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.po.FileUploadLogPO;
import com.yuntongxun.itsys.base.service.FileUploadService;
import com.yuntongxun.itsys.base.vo.TodoVoPo;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.yuntongxun.itsys.base.cache.RedisManager;
import com.yuntongxun.itsys.base.common.AbstractController;
import com.yuntongxun.itsys.base.common.constans.RedisConstant;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.CookieUtil;
import com.yuntongxun.itsys.base.common.util.GlobalErrorCode;
import com.yuntongxun.itsys.base.common.util.JSONUtils;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.common.util.ResultUtil;
import com.yuntongxun.itsys.base.common.util.StringUtil;
import com.yuntongxun.itsys.base.service.HcRecordService;
import com.yuntongxun.itsys.base.service.UserService;
import com.yuntongxun.itsys.base.vo.DoctorInfo;
import com.yuntongxun.itsys.base.vo.Result;
import com.yuntongxun.itsys.base.vo.TodoVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 结肠镜检查记录表
 * @author liugb
 * Date 2018 4 16
 */
@RestController
public class HcRecordController extends AbstractController{
	
	@Autowired
	private HcRecordService hcRecordService;
	
	private final Logger log = LogManager.getLogger(HcRecordController.class.getName());
	
	@Autowired
	private UserService userService;

	@Autowired
	private FileUploadService fileUploadService;
	@Autowired
	private RedisManager redis;
	/**
	 * 返回社区id
	 * @param req
	 * @return
	 */
	public int deptId(HttpServletRequest req){
		//获取当前用户信息
		String key = RedisConstant.HOSPITAL_KEY_INFO+CookieUtil.getCookieByLoginName(req);
		String value = redis.get(key);
		DoctorInfo doctorInfo = null;
		if(StringUtil.isNotBlank(value)){
			doctorInfo = JSONUtils.toBean(value, DoctorInfo.class);
		}
		if(doctorInfo == null){
			doctorInfo = userService.getHospitalInfo(CookieUtil.getCookieByLoginName(req));
		}
		if(doctorInfo == null){
			throw new ItSysException(GlobalErrorCode.ERR_USER_INFO_INCOMPLETE_ERROR_CODE,GlobalErrorCode.ERR_USER_INFO_INCOMPLETE_ERROR_MSG);
		}
        int deptId = doctorInfo.getCommunityDeptId();
        return deptId;
	}
	
	/**
	 * 立即预约页面查询待预约受试者接口
	 * @param req
	 * @param resp
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/hospital/person/reserve/wait/query", method = RequestMethod.POST)
	public Result Hcrecord(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) {
		printStartTag("Hcrecord");
		printHttpPacket(req, null);
		Result result = null;
		log.info("@Controller HcRecordController接收参数为：{}", body);
		try {
			TodoVo vo = JSONUtils.toBean(body, TodoVo.class);
			String loginName=CookieUtil.getCookieByLoginName(req);
			result = (Result) hcRecordService.getRecordView(vo,loginName);
		} catch (Exception e) {
			log.error("@Controller HcRecordController   error={}", e.getMessage());
			result = ResultUtil.error(GlobalErrorCode.RUNTIME_ERROR_CODE, e.getMessage(), null);
		}
		log.info("返回查询对象：{}", JSONUtils.toJson(result));
		printEndTag("Hcrecord");
		return result;
	}
    /**
     * 查询 未按时完成结肠镜检查 受试者列表；
     * @param req
     * @param resp
     * @param body
     * @return
     */
	@RequestMapping(value = "/hospital/person/notfinish/colonoscopy/query", method = RequestMethod.POST)
	public Result notFinishColonoscopy(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) {
		printStartTag("查询 未按时完成结肠镜检查 待办");
		printHttpPacket(req, null);
		String loginName=CookieUtil.getCookieByLoginName(req);
		log.info("@Controller notIssueNotification接收参数为：{}", body);
		TodoVo vo = JSONUtils.toBean(body, TodoVo.class);
		try{
	       	vo = JSONUtils.toBean(body, TodoVo.class);
        }catch(Exception e){
			throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,"Parse request body error.");
        }
		ListPageUtil listPage = hcRecordService.notFinishColonoscopy(vo, loginName);
		
		Result result=new Result(listPage.getResultList(),listPage.getPageInfo());
		printEndTag("查询 未按时完成结肠镜检查 待办");
        log.info("返回查询对象个数={}", listPage.getResultList().size());
		return result;
	}
	/**
	 * 查询 未发放筛查结果告知书 受试者列表
	 * @param req
	 * @param resp
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/hospital/person/notissue/notification/query", method = RequestMethod.POST)
	public Result notIssueNotification(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) {
		printStartTag("查询未发放筛查结果告知书待办");
		printHttpPacket(req, null);
		String loginName=CookieUtil.getCookieByLoginName(req);
		log.info("@Controller notIssueNotification接收参数为：{}", body);
		TodoVo vo = JSONUtils.toBean(body, TodoVo.class);
		try{
	       	vo = JSONUtils.toBean(body, TodoVo.class);
        }catch(Exception e){
			throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,"Parse request body error.");
        }
		ListPageUtil listPage = hcRecordService.notIssueNotification(vo, loginName);
		
		Result result=new Result(listPage.getResultList(),listPage.getPageInfo());
		printEndTag("查询未发放筛查结果告知书待办");
        log.info("返回查询对象个数={}", listPage.getResultList().size());
		return result;
	}
	@Override
	protected String getClassName() {
		// TODO Auto-generated method stub
		return HcRecordController.class.getName();
	}
	
	
	/**
	 * 1.1.8、查询 未预约结肠镜检查 受试者列表；
	 * @param req
	 * @param resp
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/hospital/person/notreserve/colonoscopy/query", method = RequestMethod.POST)
	public Result notReserveColonoscopy(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) {
		printStartTag("查询待预约结肠镜待办");
		printHttpPacket(req, null);
		String loginName=CookieUtil.getCookieByLoginName(req);
		log.info("@Controller HFitResultController接收参数为：{}", body);
		TodoVo vo = JSONUtils.toBean(body, TodoVo.class);
		try{
	       	vo = JSONUtils.toBean(body, TodoVo.class);
        }catch(Exception e){
			throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,"Parse request body error.");
        }
		ListPageUtil listPage = hcRecordService.notReserveColonoscopy(vo, loginName);
		
		Result result=new Result(listPage.getResultList(),listPage.getPageInfo());
		printEndTag("查询待预约结肠镜待办");
        log.info("返回查询对象个数={}", listPage.getResultList().size());
		return result;
	}
	
	
	/**
	 * 查询 待录入肠镜结果 受试者列表
	 * @param req
	 * @param resp
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/hospital/person/notentry/colonoscopy/result/query", method = RequestMethod.POST)
	public Result notEntryColonoscopyResultQuery(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) {
		printStartTag("查询 待录入肠镜结果 受试者 待办");
		printHttpPacket(req, null);
		String loginName=CookieUtil.getCookieByLoginName(req);
		log.info("@Controller notentry colonoscopy result接收参数为：{}", body);
		TodoVo vo = JSONUtils.toBean(body, TodoVo.class);
		try{
	       	vo = JSONUtils.toBean(body, TodoVo.class);
			vo.setType(Constans.PERSON_TODO_EVENT_TYPE8.toString());
		}catch(Exception e){
			throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,"Parse request body error.");
        }
		ListPageUtil listPage = hcRecordService.notEntryColonoscopyResultQuery(vo, loginName);
		
		Result result=new Result(listPage.getResultList(),listPage.getPageInfo());
		printEndTag("查询 待录入肠镜结果 受试者 待办");
        log.info("返回查询对象个数={}", listPage.getResultList().size());
		return result;
	}

	/*
	 * 查询 待录入肠镜上传图片
	 *
	 * @author ${zongt}
	 * @since v1.0.0
	 */
	@RequestMapping(value = "/hospital/person/notentry/colonoscopy/result/picture/query", method = RequestMethod.POST)
	public Result notEntryColonoscopyResultPictureQuery(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) {
		printStartTag("查询 待录入肠镜结果 受试者 待办");
		printHttpPacket(req, null);
		String loginName=CookieUtil.getCookieByLoginName(req);
		log.info("@Controller notentry colonoscopy result接收参数为：{}", body);
		TodoVo vo = JSONUtils.toBean(body, TodoVo.class);
		try{
			vo = JSONUtils.toBean(body, TodoVo.class);
			vo.setType(Constans.PERSON_TODO_EVENT_TYPE19.toString());
		}catch(Exception e){
			throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,"Parse request body error.");
		}
		ListPageUtil listPage = hcRecordService.notEntryColonoscopyResultQuery(vo, loginName);

		Result result=new Result(listPage.getResultList(),listPage.getPageInfo());
		printEndTag("查询 待录入肠镜结果 受试者 待办");
		log.info("返回查询对象个数={}", listPage.getResultList().size());
		return result;
	}

	
	/**
	 * 查询 待录入病理结果 受试者列表
	 * @param req
	 * @param resp
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/hospital/person/notentry/pathology/result/query", method = RequestMethod.POST)
	public Result notEntryPathologyResultQuery(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) {
		printStartTag("查询 待录入病理结果 受试者 待办");
		printHttpPacket(req, null);
		String loginName=CookieUtil.getCookieByLoginName(req);
		log.info("@Controller notentry pathology result接收参数为：{}", body);
		TodoVo vo = JSONUtils.toBean(body, TodoVo.class);
		try{
	       	vo = JSONUtils.toBean(body, TodoVo.class);
        }catch(Exception e){
			throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,"Parse request body error.");
        }
		ListPageUtil listPage = hcRecordService.notEntryPathologyResultQuery(vo, loginName);
		
		Result result=new Result(listPage.getResultList(),listPage.getPageInfo());
		printEndTag("查询 待录入病理结果 受试者 待办");
        log.info("返回查询对象个数={}", listPage.getResultList().size());
		return result;
	}
	
	/**
	 * 查询 待录入告知书 受试者列表
	 * @param req
	 * @param resp
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/hospital/person/notentry/notification/result/query", method = RequestMethod.POST)
	public Result notEntryNotificationResultQuery(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) {
		printStartTag("查询待录入告知书 受试者 待办");
		printHttpPacket(req, null);
		String loginName=CookieUtil.getCookieByLoginName(req);
		log.info("@Controller notentry notification result接收参数为：{}", body);
		TodoVo vo = JSONUtils.toBean(body, TodoVo.class);
		try{
	       	vo = JSONUtils.toBean(body, TodoVo.class);
        }catch(Exception e){
			throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,"Parse request body error.");
        }
		ListPageUtil listPage = hcRecordService.notEntryNotificationResultQuery(vo, loginName);
		
		Result result=new Result(listPage.getResultList(),listPage.getPageInfo());
		printEndTag("查询 待录入告知书  受试者 待办");
        log.info("返回查询对象个数={}", listPage.getResultList().size());
		return result;
	}

	/**
	 * 查询  生物样本 受试者列表
	 * @param req
	 * @param resp
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/hospital/person/bloodSample/notification/result/query", method = RequestMethod.POST)
	public Result notEntryNoBloodSampleResultQuery(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) {
		printStartTag("查询待录入告知书 受试者 待办");
		printHttpPacket(req, null);
		String loginName=CookieUtil.getCookieByLoginName(req);
		log.info("@Controller bloodSample notification result接收参数为：{}", body);
		TodoVoPo vo;
		try{
			vo = JSONUtils.toBean(body, TodoVoPo.class);
		}catch(Exception e){
			throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,"Parse request body error.");
		}
		ListPageUtil listPage = hcRecordService.notEntryNoSampleResultQuery(vo, loginName);

		Result result=new Result(listPage.getResultList(),listPage.getPageInfo());
		printEndTag("查询 待录入告知书  受试者 待办");
		log.info("返回查询对象个数={}", listPage.getResultList().size());
		return result;
	}


	/*
	 * 查询 代办 癌症列表
	 *
	 * @author ${zongt}
	 * @since v2.2.1
	 */
	@RequestMapping(value = "/hospital/person/cancer/notification/result/query", method = RequestMethod.POST)
	public Result notEntryCancerQuery(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) {
		printStartTag("查询待录入癌症相关信息");
		printHttpPacket(req, null);
		String loginName=CookieUtil.getCookieByLoginName(req);
		log.info("@Controller /hospital/person/cancer/notification/result/query result接收参数为：{}", body);
		TodoVoPo vo;
		try{
			vo = JSONUtils.toBean(body, TodoVoPo.class);
		}catch(Exception e){
			throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,"Parse request body error.");
		}
		ListPageUtil listPage = hcRecordService.notEntryCancerQuery(vo, loginName);
		Result result=new Result(listPage.getResultList(),listPage.getPageInfo());
		printEndTag("查询 待录入告知书  受试者 待办");
		log.info("返回查询对象个数={}", listPage.getResultList().size());
		return result;
	}



	/**
	 * 根据市区id查询未预约结肠镜检查
	 * @param req
	 * @param resp
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/hospital/person/notreserve/colonoscopy/notEntryAlloction", method = RequestMethod.POST)
	public Result notEntryAlloction(HttpServletRequest req, HttpServletResponse resp) {
		printStartTag("根据市区id查询未预约结肠镜检查");
		printHttpPacket(req, null);
		String loginName=CookieUtil.getCookieByLoginName(req);
		Result result = null;
		try{
	       	result = hcRecordService.notEntryAllocation(loginName);
        }catch(Exception e){
			throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,"Parse request body error.");
        }
		printEndTag("根据市区id查询未预约结肠镜检查");
		return result;
	}
	/**
	 * 根据市区id查询未完成结肠镜检查
	 * @param req
	 * @param resp
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/hospital/person/notfinish/colonoscopy/notEntryFinishCheck", method = RequestMethod.POST)
	public Result notEntryFinishCheck(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) {
		printStartTag("根据市区id查询 未按时完成结肠镜检查 待办");
		printHttpPacket(req, null);
		String loginName=CookieUtil.getCookieByLoginName(req);
		log.info("@Controller notIssueNotification接收参数为：{}", body);
		TodoVo vo = JSONUtils.toBean(body, TodoVo.class);
		Result result = null;
		try{
	       	vo = JSONUtils.toBean(body, TodoVo.class);
	       	result = hcRecordService.notFinishCheck(vo, loginName);
        }catch(Exception e){
			throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,"Parse request body error.");
        }
		printEndTag("根据市区id查询 未按时完成结肠镜检查 待办");
		return result;
	}
	/**
	 * 未通知发放筛查结果告知书
	 * @param req
	 * @param resp
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/hospital/person/notissue/notification/notPutOutNotice", method = RequestMethod.POST)
	public Result notPutOutNotice(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) {
		printStartTag("查询未发放筛查结果告知书待办");
		printHttpPacket(req, null);
		String loginName=CookieUtil.getCookieByLoginName(req);
		log.info("@Controller notIssueNotification接收参数为：{}", body);
		TodoVo vo = JSONUtils.toBean(body, TodoVo.class);
		Result result = null;
		try{
	       	vo = JSONUtils.toBean(body, TodoVo.class);
	       	result = hcRecordService.notPutOutNotices(vo, loginName);
        }catch(Exception e){
			throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,"Parse request body error.");
        }
		printEndTag("查询未发放筛查结果告知书待办");
		return result;
	}


	/*
	 * 肠镜多图片上传
	 *
	 * @author ${zongt}
	 * @since v1.0.0
	 */
	@RequestMapping(value = "/colonoscopy/imgUpload", method = RequestMethod.POST)
	@ResponseBody
	public Result upload(@RequestParam("file") MultipartFile[] files, HttpServletRequest request) {
		log.debug("传入的文件参数：{}", JSON.toJSONString(files, true));
		Integer id=null;
		if(!StringUtils.isEmpty(request.getParameter("id"))){
			id = Integer.parseInt(request.getParameter("id"));
		}
		if (files!=null||files.length>0) {
			String loginName = CookieUtil.getCookieByLoginName(request);
			List<FileUploadLogPO> fileUploadLogPOs = fileUploadService.upLoadColonoscopyImgeFiles(files, loginName, Constans.FILE_SOURCE_TYPE_6,id);
			return ResultUtil.success(fileUploadLogPOs, "文件上传成功");
		} else {
			return ResultUtil.error(GlobalErrorCode.FILE_UPLOAD_FAIL_CODE, GlobalErrorCode.FILE_UPLOAD_FAIL_MSG);
		}
	}

}
