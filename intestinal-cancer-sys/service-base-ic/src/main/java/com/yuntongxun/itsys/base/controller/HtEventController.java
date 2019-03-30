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
import com.yuntongxun.itsys.base.cache.RedisManager;
import com.yuntongxun.itsys.base.common.AbstractController;
import com.yuntongxun.itsys.base.common.constans.RedisConstant;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.CookieUtil;
import com.yuntongxun.itsys.base.common.util.GlobalErrorCode;
import com.yuntongxun.itsys.base.common.util.JSONUtils;
import com.yuntongxun.itsys.base.common.util.ResultUtil;
import com.yuntongxun.itsys.base.common.util.StringUtil;
import com.yuntongxun.itsys.base.service.HtEventService;
import com.yuntongxun.itsys.base.service.UserService;
import com.yuntongxun.itsys.base.vo.DoctorInfo;
import com.yuntongxun.itsys.base.vo.Result;
import com.yuntongxun.itsys.base.vo.TodoVo;
/**
 * 查询社区医院各种待办事项个数；
 * @author liugb
 * Date 2018 4 16
 */
@RestController
public class HtEventController extends AbstractController{
	
	@Autowired
	private HtEventService htEventService;
	@Autowired
	private UserService userService;
	@Autowired
	private RedisManager redis;

	public static final int HOSPITAL_TYPE_COMMUNITY = 1;
	public static final int HOSPITAL_TYPE_AREA = 2;
	public static final int HOSPITAL_TYPE_NATION = 3;
	
	
	private final Logger log = LogManager.getLogger(HtEventController.class.getName());
    
	@RequestMapping(value = "/hospital/community/todo/query", method = RequestMethod.POST)
	public Result HtEventGet(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) {
		printStartTag("HtEventGet");
		printHttpPacket(req, null);
		Result result = null;
		log.info("@Controller HtEventController接收参数为：{}", body);
		try {
			//获取当前用户信息
			String key = RedisConstant.HOSPITAL_KEY_INFO+CookieUtil.getCookieByLoginName(req);
			String value = redis.get(key);
			DoctorInfo doctorInfo = null;
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
			
//            int deptId = doctorInfo.getCommunityDeptId();
            log.info("社区id"+deptId);
			int status = 1;
			TodoVo vo = htEventService.getHtEventSum(deptId, status,doctorInfo);
			result=new Result(vo);
		} catch (Exception e) {
			log.error("@Controller HtEventController   error={}", e.getMessage());
			result = ResultUtil.error(GlobalErrorCode.RUNTIME_ERROR_CODE, e.getMessage(), null);
		}
		log.info("返回查询对象：{}", JSONUtils.toJson(result));
		printEndTag("HtEventGet");
		return result;
	}
	/**
	 * 国家代办统计个数
	 * @param req
	 * @param resp
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/hospital/person/notentry/notAreaSumsByNationId", method = RequestMethod.POST)
	public String notAreaSumsByNationId(HttpServletRequest req, HttpServletResponse resp) {
		printStartTag("国家代办查询未录入个数");
		printHttpPacket(req, null);
		String loginName=CookieUtil.getCookieByLoginName(req);
		String result = null;
		try{
	       	result = htEventService.getSumByNationId(loginName);
        }catch(Exception e){
			throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,"Parse request body error.");
        }
		printEndTag("国家代办查询未录入个数");
		return result;
	}
	/**
	 * 国家统计代办社区未完成按照地区统计个数
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/hospital/person/notentry/notfinishSumsByNationId", method = RequestMethod.POST)
	public String notfinishSumsByNationId(HttpServletRequest req, HttpServletResponse resp){
		printStartTag("国家代办查询社区未录入地区个数==》notfinishSumsByNationId");
		printHttpPacket(req, null);
		String loginName=CookieUtil.getCookieByLoginName(req);
		String result = null;
		try{
	       	result = htEventService.getAreaSumsByNationId(loginName);
        }catch(Exception e){
			throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,"Parse request body error.");
        }
		printEndTag("国家代办查询社区未录入地区个数==》notfinishSumsByNationId");
		return result;
	}
	/**
	 * 查询详情
	 * @param req
	 * @param resp
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/hospital/type/detail/notListDetailById", method = RequestMethod.POST)
	public Result notListDetailById(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body){
		printStartTag("notListDetailById");
		printHttpPacket(req, null);
		Result result = null;
		TodoVo vo = JSONUtils.toBean(body, TodoVo.class);
		try{
			vo = JSONUtils.toBean(body, TodoVo.class);
			result = htEventService.getlist(vo);
		}catch(Exception e){
			throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,"Parse request body error.");
		}
		printEndTag("notListDetailById");
		return result;
	}
	
	@Override
	protected String getClassName() {
		// TODO Auto-generated method stub
		return HtEventController.class.getName();
	}

}
