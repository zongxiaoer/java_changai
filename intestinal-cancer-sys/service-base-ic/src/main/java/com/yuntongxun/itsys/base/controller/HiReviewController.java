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
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.common.util.StringUtil;
import com.yuntongxun.itsys.base.po.HospitalReview;
import com.yuntongxun.itsys.base.service.HiReviewService;
import com.yuntongxun.itsys.base.service.UserService;
import com.yuntongxun.itsys.base.vo.DoctorInfo;
import com.yuntongxun.itsys.base.vo.Result;
import com.yuntongxun.itsys.base.vo.TodoVo;
@RestController
public class HiReviewController extends AbstractController{
	@Autowired
	private HiReviewService hiReviewService;
	private final Logger log = LogManager.getLogger(HiReviewController.class.getName());
	@Autowired
	private UserService userService;
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
	 * 1.1.4、查询 未完成危险因素调查表 受试者列表；
	 * @param req
	 * @param resp
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/hospital/person/notentry/riskfactors/query", method = RequestMethod.POST)
	public Result notEntryRiskfactors(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) {
		printStartTag("未完成危险因素调查表待办");
		printHttpPacket(req, null);
		String loginName=CookieUtil.getCookieByLoginName(req);
		log.info("@Controller HFitResultController接收参数为：{}", body);
		TodoVo vo = JSONUtils.toBean(body, TodoVo.class);
		try{
	       	vo = JSONUtils.toBean(body, TodoVo.class);
        }catch(Exception e){
			throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,"Parse request body error.");
        }
		ListPageUtil listPage = hiReviewService.notEntryRiskfactors(vo, loginName);
		
		Result result=new Result(listPage.getResultList(),listPage.getPageInfo());
		printEndTag("未完成危险因素调查表待办");
        log.info("返回查询对象个数={}", listPage.getResultList().size());
		return result;
	}
	/**
	 * 社区登陆统计各类型总人数
	 * @param req
	 * @param resp
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/hospital/person/notentry/riskfactors/groupByAcounts", method = RequestMethod.POST)
	public String groupByAcounts(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body){
		printStartTag("入组的人数");
		printHttpPacket(req, null);
		String loginName=CookieUtil.getCookieByLoginName(req);
//		String loginName = "weily@yuntongxun.com";
		log.info("@Controller groupByAcounts：{}");
		TodoVo vo = JSONUtils.toBean(body, TodoVo.class);
		String result = null;
		try {
			vo = JSONUtils.toBean(body, TodoVo.class);
			result = hiReviewService.getGroupBycounts(loginName, vo);
		} catch (Exception e) {
			// TODO: handle exception
			throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,"Parse request body error.");
		}
		printEndTag("入组的人数");
		return result;
	}
	/**
	 * 根据市区id查询所对应的社区人数
	 * @param req
	 * @param resp
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/hospital/person/notentry/riskfactors/getGroupsByAreaId", method = RequestMethod.POST)
	public String getGroupsByAreaId(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body){
		printStartTag("根据市区id查询所对应的社区人数");
		printHttpPacket(req, null);
		String loginName=CookieUtil.getCookieByLoginName(req);
		log.info("@Controller groupByAcounts：{}");
		TodoVo vo = JSONUtils.toBean(body, TodoVo.class);
		String result = null;
		try {
			vo = JSONUtils.toBean(body, TodoVo.class);
			result = hiReviewService.getGroupByAreaIds(loginName, vo);
		} catch (Exception e) {
			// TODO: handle exception
			throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,"Parse request body error.");
		}
		printEndTag("根据市区id查询所对应的社区人数");
		return result;
	}
	/**
	 * 根据市区id查询所对应的社区代办未完成危险因素调查表
	 * 根据市区id查询所有代办个数
	 * @param req
	 * @param resp
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/hospital/person/notentry/riskfactors/notRiskfactorsByAreaId", method = RequestMethod.POST)
	public String notRiskfactorsByAreaId(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) {
		printStartTag("根据市区id未完成危险因素调查表待办");
		printHttpPacket(req, null);
		String loginName=CookieUtil.getCookieByLoginName(req);
//		String loginName = "hunanadmin";
		log.info("@Controller HFitResultController接收参数为：{}", body);
		TodoVo vo = JSONUtils.toBean(body, TodoVo.class);
		String result = null;
		try{
	       	vo = JSONUtils.toBean(body, TodoVo.class);
	       	result = hiReviewService.notRiskFactorsByAreaId(vo, loginName);
        }catch(Exception e){
			throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,"Parse request body error.");
        }
		printEndTag("根据市区id未完成危险因素调查表待办");
		return result;
	}
	/**
	 * 国家受试者管理列表
	 * @param req
	 * @param resp
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/hospital/person/notentry/riskfactors/ReviewByNationIdList", method = RequestMethod.POST)
	public Result ReviewByNationIdList(HttpServletRequest req, HttpServletResponse resp, @RequestBody HospitalReview hospitalReview){
		printStartTag("国家受试者管理列表");
		printHttpPacket(req, null);
		String loginName=CookieUtil.getCookieByLoginName(req);
//		String loginName = "user3";
		log.info("@Controller HFitResultController接收参数为：{}", JSONUtils.toJson(hospitalReview));
/*		HospitalReview review = JSONUtils.toBean(body, HospitalReview.class);
		try{
			review = JSONUtils.toBean(body, HospitalReview.class);
        }catch(Exception e){
			throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,"Parse request body error.");
        }*/
		ListPageUtil listPage = hiReviewService.queryReviewByNationIdPage(hospitalReview, loginName);
		
		Result result=new Result(listPage.getResultList(),listPage.getPageInfo());
		printEndTag("国家受试者管理列表");
        log.info("返回查询对象个数={}", listPage.getResultList().size());
		return result;
	}
	/**
	 * (国家受试者)根据sid查询患者基本信息
	 * @return
	 */
	@RequestMapping(value = "/hospital/person/getPeopleListBySid", method = RequestMethod.POST)
	public Result getPeopleListBySid(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body){
		printStartTag("国家受试者管理列表根据sid查询患者基本信息");
		printHttpPacket(req, null);
		HospitalReview review = JSONUtils.toBean(body, HospitalReview.class);
		Result result = null;
		try{
			review = JSONUtils.toBean(body, HospitalReview.class);
			result = hiReviewService.getAreaListById(review.getSid());
        }catch(Exception e){
			throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,"Parse request body error.");
        }
		return result;
	}
	/**
	 * (国家受试者)查询fit列表
	 * @return
	 */
	@RequestMapping(value = "/hospital/person/getFitListByNationId", method = RequestMethod.POST)
	public Result getFitListByNationId(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body){
		printStartTag("国家受试者管理列表根据sid查询fit列表");
		printHttpPacket(req, null);
		HospitalReview review = JSONUtils.toBean(body, HospitalReview.class);
		Result result = null;
		try{
			review = JSONUtils.toBean(body, HospitalReview.class);
			result = hiReviewService.getFitListByNationId(review.getSid());
        }catch(Exception e){
			throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,"Parse request body error.");
        }
		return result;
	}
	/**
	 * (国家受试者)查询粪便DNA
	 * @param req
	 * @param resp
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/hospital/person/getStollDNAListByNationId", method = RequestMethod.POST)
	public Result getStollDNAListByNationId(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body){
		printStartTag("国家受试者管理列表根据sid查询DNA列表");
		printHttpPacket(req, null);
		HospitalReview review = JSONUtils.toBean(body, HospitalReview.class);
		Result result = null;
		try{
			review = JSONUtils.toBean(body, HospitalReview.class);
			result = hiReviewService.getStollDNABySid(review.getSid());
        }catch(Exception e){
			throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,"Parse request body error.");
        }
		return result;
	}
	/**
	 * (国家受试者管理)sid查询结肠镜列表
	 * @param req
	 * @param resp
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/hospital/person/getColonoscopyByNationId", method = RequestMethod.POST)
	public Result getColonoscopyByNationId(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body){
		printStartTag("国家受试者管理列表根据sid查询结肠镜列表");
		printHttpPacket(req, null);
		HospitalReview review = JSONUtils.toBean(body, HospitalReview.class);
		Result result = null;
		try{
			review = JSONUtils.toBean(body, HospitalReview.class);
			result = hiReviewService.getColonoscopyBySid(review.getSid());
        }catch(Exception e){
			throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,"Parse request body error.");
        }
		return result;
	}
	/**
	 * 国家受试者管理根据sid查询生物样本列表
	 * @param req
	 * @param resp
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/hospital/person/getSampleType", method = RequestMethod.POST)
	public String getSampleType(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body){
		printStartTag("国家受试者管理列表根据sid查询生物列表");
		printHttpPacket(req, null);
		HospitalReview review = JSONUtils.toBean(body, HospitalReview.class);
		String result = null;
		try{
			review = JSONUtils.toBean(body, HospitalReview.class);
			result = hiReviewService.getSampleType(review.getSid());
        }catch(Exception e){
			throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,"Parse request body error.");
        }
		return result;
	}
	/**
	 * 国家受试者生物样本详情查看
	 * @param req
	 * @param resp
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/hospital/person/getSampleTypeDetailById", method = RequestMethod.POST)
	public Result getSampleTypeDetailById(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body){
		printStartTag("国家受试者管理列表根据sid查询生物列表详情");
		printHttpPacket(req, null);
		HospitalReview review = JSONUtils.toBean(body, HospitalReview.class);
		Result result = null;
		try{
			review = JSONUtils.toBean(body, HospitalReview.class);
			result = hiReviewService.getSampleDetailById(review.getId());
        }catch(Exception e){
			throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,"Parse request body error.");
        }
		return result;
	}
	/**
	 * 国家统计按照地区统计各人数
	 * @param req
	 * @param resp
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/hospital/person/listGroups/groupSumsByNation", method = RequestMethod.POST)
	public String groupSumsByNation(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body){
		printStartTag("按照地区统计各人数");
		printHttpPacket(req, null);
		String loginName=CookieUtil.getCookieByLoginName(req);
//		String loginName = "nationaladmin";
		log.info("@Controller groupByAcounts：{}");
		TodoVo vo = JSONUtils.toBean(body, TodoVo.class);
		String result = null;
		try {
			vo = JSONUtils.toBean(body, TodoVo.class);
			result = hiReviewService.getGroupSumsByNationId(loginName, vo);
		} catch (Exception e) {
			// TODO: handle exception
			throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,"Parse request body error.");
		}
		printEndTag("按照地区统计各人数");
		return result;
	}

    /**
     * 国家统计病变结果各组人数
     * @param req
     * @param resp
     * @param body
     * @return
     */
    @RequestMapping(value = "/hospital/person/listGroups/lesionStatisticsByNation", method = RequestMethod.POST)
    public String lesionStatisticsByNation(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body){
        printStartTag("按照国家统计各地区人数");
        printHttpPacket(req, null);
		String loginName=CookieUtil.getCookieByLoginName(req);
        TodoVo vo = new TodoVo();
        String result = null;
        try {
            vo = JSONUtils.toBean(body, TodoVo.class);
            result = hiReviewService.lesionStatisticsByNation(loginName,vo);
        } catch (Exception e) {
            throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,"Parse request body error.");
        }
        printEndTag("按照国家统计各地区人数");
        return result;
    }

    /**
     * 根据地区id统计病变结果各组人数
     * @param req
     * @param resp
     * @param body
     * @return
     */
    @RequestMapping(value = "/hospital/person/notentry/riskfactors/lesionStatisticsByAreaId", method = RequestMethod.POST)
    public String lesionStatisticsByAreaId(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body){
        printStartTag("根据地区id查询所对应的各社区人数");
        printHttpPacket(req, null);
		String loginName=CookieUtil.getCookieByLoginName(req);
        TodoVo vo = new TodoVo();
        String result = null;
        try {
            vo = JSONUtils.toBean(body, TodoVo.class);
            result = hiReviewService.lesionStatisticsByAreaId(loginName, vo);
        } catch (Exception e) {
            throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,"Parse request body error.");
        }
        printEndTag("根据地区id查询所对应的各社区人数");
        return result;
    }

	@Override
	protected String getClassName() {
		// TODO Auto-generated method stub
		return HiReviewController.class.getName();
	}

}
