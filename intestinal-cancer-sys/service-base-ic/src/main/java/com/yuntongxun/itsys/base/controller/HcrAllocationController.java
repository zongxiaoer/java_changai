package com.yuntongxun.itsys.base.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.druid.util.StringUtils;
import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.util.*;
import com.yuntongxun.itsys.base.po.HospitalColonoscopyRecord;
import com.yuntongxun.itsys.base.po.dto.allocation.*;
import com.yuntongxun.itsys.base.service.ColonoscopyService;
import com.yuntongxun.itsys.base.service.HcRecordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yuntongxun.itsys.base.cache.RedisManager;
import com.yuntongxun.itsys.base.common.AbstractController;
import com.yuntongxun.itsys.base.common.constans.RedisConstant;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.po.AllocationTodo;
import com.yuntongxun.itsys.base.service.HcrAllocationService;
import com.yuntongxun.itsys.base.service.UserService;
import com.yuntongxun.itsys.base.vo.AreaListForExcelVo;
import com.yuntongxun.itsys.base.vo.ColonoscopyQueryResult;
import com.yuntongxun.itsys.base.vo.DoctorInfo;
import com.yuntongxun.itsys.base.vo.Result;
import com.yuntongxun.itsys.base.vo.TodoVo;

/**
 * 预约分配查询控制类
 *
 * @author liugb
 * Date 2018 4 16
 */
@RestController
public class HcrAllocationController extends AbstractController {

    @Autowired
    private HcrAllocationService hcrAllocationService;

    @Autowired
    private ColonoscopyService colonoscopyService;

    private final Logger log = LogManager.getLogger(HcrAllocationController.class.getName());

    @Autowired
    private UserService userService;
    @Autowired
    private RedisManager redis;

    public static final int HOSPITAL_TYPE_COMMUNITY = 1;
    public static final int HOSPITAL_TYPE_AREA = 2;
    public static final int HOSPITAL_TYPE_NATION = 3;

    /**
     * 返回社区id
     *
     * @param req
     * @return
     */
    public int deptId(HttpServletRequest req) {
        //获取当前用户信息
        String key = RedisConstant.HOSPITAL_KEY_INFO + CookieUtil.getCookieByLoginName(req);
        String value = redis.get(key);
        DoctorInfo doctorInfo = null;
        if (StringUtil.isNotBlank(value)) {
            doctorInfo = JSONUtils.toBean(value, DoctorInfo.class);
        }
        if (doctorInfo == null) {
            doctorInfo = userService.getHospitalInfo(CookieUtil.getCookieByLoginName(req));
        }
        if (doctorInfo == null) {
            throw new ItSysException(GlobalErrorCode.ERR_USER_INFO_INCOMPLETE_ERROR_CODE, GlobalErrorCode.ERR_USER_INFO_INCOMPLETE_ERROR_MSG);
        }
        int deptId = 0;
        if (HOSPITAL_TYPE_COMMUNITY == doctorInfo.getHospitalType()) {
            deptId = doctorInfo.getCommunityDeptId();
        } else if (HOSPITAL_TYPE_AREA == doctorInfo.getHospitalType()) {
            deptId = doctorInfo.getAreaDeptId();
        } else {
            deptId = doctorInfo.getNationDeptId();
        }
        return deptId;
    }

    /**
     * 根据社区医院ID获取放号数据列表
     *
     * @param req
     * @param resp
     * @param body
     * @return
     */
    @RequestMapping(value = "/hospital/community/allocation/query", method = RequestMethod.POST)
    public Result HcrallocationQuery(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) {
        printStartTag("HcrallocationQuery");
        printHttpPacket(req, null);
        Result result = null;
        log.info("@Controller HcrAllocationController接收参数为：{}", body);
        try {
            JsonObject json = new JsonParser().parse(body).getAsJsonObject();
            int communityDeptId = deptId(req);
            int pageNo = json.get("pageNo") == null ? 0 : json.get("pageNo").getAsInt();
            int pageSize = json.get("pageSize") == null ? -1 : json.get("pageSize").getAsInt();
            result = hcrAllocationService.query(communityDeptId, pageNo, pageSize);
        } catch (Exception e) {
            log.error("@Controller HcrAllocationController   error={}", e.getMessage());
            result = ResultUtil.error(GlobalErrorCode.RUNTIME_ERROR_CODE, e.getMessage(), null);
        }
        printEndTag("HcrallocationQuery");
        return result;
    }

    /**
     * 根据地区对应的社区医院ID获取放号数据列表
     *
     * @param req
     * @param resp
     * @param body
     * @return
     */
    @RequestMapping(value = "/hospital/area/allocation/query", method = RequestMethod.POST)
    public Result HcrallocationQueryInArea(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) {
        printStartTag("HcrallocationQueryInArea");
        printHttpPacket(req, null);
        Result result = null;
        log.info("@Controller HcrallocationQueryInArea：{}", body);
        try {
            JsonObject json = new JsonParser().parse(body).getAsJsonObject();
            //根据地区提供的id去查询对应的社区信息
            HospitalColonoscopyRecord hospitalColonoscopyRecord = colonoscopyService.queryByID(json.get("id").getAsInt());
            //int  communityDeptId = deptId(req);
            int communityDeptId = hospitalColonoscopyRecord.getCommunity_dept_id();
            int pageNo = json.get("pageNo") == null ? 0 : json.get("pageNo").getAsInt();
            int pageSize = json.get("pageSize") == null ? -1 : json.get("pageSize").getAsInt();
            result = hcrAllocationService.queryInArea(communityDeptId, pageNo, pageSize);
            //result = hcrAllocationService.query(communityDeptId, pageNo, pageSize);
        } catch (Exception e) {
            log.error("@Controller HcrAllocationController   error={}", e.getMessage());
            result = ResultUtil.error(GlobalErrorCode.RUNTIME_ERROR_CODE, e.getMessage(), null);
        }
        printEndTag("HcrallocationQuery");
        return result;
    }


    /**
     * 根据登陆社区id查询所对应的社区名称
     *
     * @param req
     * @param resp
     * @return
     */
    @RequestMapping(value = "/hospital/community/allocation/querycommdepts", method = RequestMethod.POST)
    public Result querycommdepts(HttpServletRequest req, HttpServletResponse resp) {
        printStartTag("Hcrallocationinsert");
        printHttpPacket(req, null);
        String loginName = CookieUtil.getCookieByLoginName(req);
//		String loginName = "weily@yuntongxun.com";
        Result result = null;
        try {
            result = hcrAllocationService.getCommdepts(loginName);
        } catch (Exception e) {
            throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO, "Parse request body error.");
        }
        printEndTag("Hcrallocationinsert");
        return result;
    }

    /**
     * 新预约放号方法
     *
     * @param req
     * @param resp
     * @param body
     * @return
     */
    @RequestMapping(value = "/hospital/community/allocation/newinsert", method = RequestMethod.POST)
    public Result Hcrallocationinsert(HttpServletRequest req, HttpServletResponse resp, @RequestBody HosAllocationRuleInfoDto hosAllocationRuleInfoDto) {
        printStartTag("新  预约放号方法Hcrallocationinsert");
        printHttpPacket(req, null);
        String loginName = CookieUtil.getCookieByLoginName(req);
//		String loginName = "weily@yuntongxun.com";
        log.info("@Controller HcrAllocationController接收参数为：{}", hosAllocationRuleInfoDto);
        //AllocationTodo allocation = JSONUtils.toBean(body, AllocationTodo.class);
        //HosAllocationRuleInfoDto allocationDto=JSONUtils.toBean(body, HosAllocationRuleInfoDto.class);;
        Result result = null;
        try {
            //allocationDto = JSONUtils.toBean(body, HosAllocationRuleInfoDto.class);
            //result = hcrAllocationService.insert(allocation,loginName);
            hosAllocationRuleInfoDto.setRuleBegin(DateUtil.formatDateStr(hosAllocationRuleInfoDto.getRuleBeginToString(), "yyyy-MM-dd"));
            hosAllocationRuleInfoDto.setRuleEnd(DateUtil.formatDateStr(hosAllocationRuleInfoDto.getRuleEndToString(), "yyyy-MM-dd"));
            hosAllocationRuleInfoDto.setOperationSource(Constans.OPERATION_TYPE_FANGHAO2);

            //放号加校验
            if(Constans.RULE_TYPE2.equals(hosAllocationRuleInfoDto.getRuleType())){
                int num=0;
                List<Date> perDay = DateUtil.getPerDay(hosAllocationRuleInfoDto.getRuleBegin(), hosAllocationRuleInfoDto.getRuleEnd());
                List<Integer> integers = hosAllocationRuleInfoDto.getWeeks();

                for (Date day:perDay) {
                    int number = DateUtil.getWeek(day).getNumber();
                    if (integers.contains(number)) {
                        num+=1;
                    }
                }
                if(num<1){
                    return new Result(GlobalErrorCode.ERR_BODY_ERRO, "放号时间与放号规则冲突，请重新选择！");
                }
            }

            result = hcrAllocationService.newInsert(hosAllocationRuleInfoDto, loginName);
        } catch (Exception e) {
            throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO, "Parse request body error.");
        }
        printEndTag("Hcrallocationinsert");
        return result;
    }

    /**
     * 预约放号方法
     *
     * @param req
     * @param resp
     * @param body
     * @return
     */
    @RequestMapping(value = "/hospital/community/allocation/insert", method = RequestMethod.POST)
    public Result Hcrallocationinsert(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) {
        printStartTag("Hcrallocationinsert");
        printHttpPacket(req, null);
        String loginName = CookieUtil.getCookieByLoginName(req);
//		String loginName = "weily@yuntongxun.com";
        AllocationTodo allocation = JSONUtils.toBean(body, AllocationTodo.class);
        //HosAllocationRuleInfoDto allocationDto=JSONUtils.toBean(body, HosAllocationRuleInfoDto.class);;
        Result result = null;
        try {
            //allocationDto = JSONUtils.toBean(body, HosAllocationRuleInfoDto.class);
            //将放号转成规则
            List<AllocationTodo> communityDept = allocation.getCommunityDept();
            HosAllocationRuleInfoDto hosAllocationRuleInfoDto = new HosAllocationRuleInfoDto();
            hosAllocationRuleInfoDto.setRuleBeginToString(allocation.getReservationDate());
            hosAllocationRuleInfoDto.setRuleEndToString(allocation.getReservationDate());
            hosAllocationRuleInfoDto.setExaminationPlace(allocation.getExaminationPlace());
            hosAllocationRuleInfoDto.setSignature(allocation.getSignature());
            hosAllocationRuleInfoDto.setRuleType(Constans.RULE_TYPE1);
            hosAllocationRuleInfoDto.setIssueType(Constans.ISSUETYPE2);
            hosAllocationRuleInfoDto.setOperationSource(Constans.OPERATION_TYPE_FANGHAO1);

            List<HosAllocationRuleTimeInfoDto> hosAllocationRuleTimeInfoDtos = new ArrayList<>();
            HosAllocationRuleTimeInfoDto hosAllocationRuleTimeInfoDto = new HosAllocationRuleTimeInfoDto();
            hosAllocationRuleTimeInfoDto.setBeginTime(allocation.getStartTime());
            hosAllocationRuleTimeInfoDto.setEndTime(allocation.getEndTime());
            hosAllocationRuleTimeInfoDtos.add(hosAllocationRuleTimeInfoDto);
            hosAllocationRuleInfoDto.setHosAllocationRuleTimeInfoDtos(hosAllocationRuleTimeInfoDtos);


            List<HosAllocationRuleDeptInfoDto> hosAllocationRuleDeptInfoDtos = new ArrayList<>();
            List<AllocationTodo> communityDept1 = allocation.getCommunityDept();
            for (AllocationTodo allocationTodo : communityDept1) {
                HosAllocationRuleDeptInfoDto allocationTodo1 = new HosAllocationRuleDeptInfoDto();
                allocationTodo1.setBeginTime(allocation.getStartTime());
                allocationTodo1.setEndTime(allocation.getEndTime());
                allocationTodo1.setNum(allocationTodo.getAmount());
                allocationTodo1.setCommunityDeptId(allocationTodo.getCommunityDeptId());
                hosAllocationRuleDeptInfoDtos.add(allocationTodo1);
            }
            hosAllocationRuleInfoDto.setHosAllocationRuleDeptInfoDtos(hosAllocationRuleDeptInfoDtos);
            hosAllocationRuleInfoDto.setRuleBegin(DateUtil.formatDateStr(hosAllocationRuleInfoDto.getRuleBeginToString(), "yyyy-MM-dd"));
            hosAllocationRuleInfoDto.setRuleEnd(DateUtil.formatDateStr(hosAllocationRuleInfoDto.getRuleEndToString(), "yyyy-MM-dd"));
            //result = hcrAllocationService.insert(allocation,loginName);
            result = hcrAllocationService.newInsert(hosAllocationRuleInfoDto, loginName);
        } catch (Exception e) {
            throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO, "Parse request body error.");
        }
        printEndTag("Hcrallocationinsert");
        return result;
    }

    /**
     * 根据市区id查询社区详情
     *
     * @param req
     * @param resp
     * @return
     */
    @RequestMapping(value = "/hospital/community/allocation/getHcrallocationById", method = RequestMethod.POST)
    public String getHcrallocationById(HttpServletRequest req, HttpServletResponse resp) {
        printStartTag("getHcrallocationById");
        printHttpPacket(req, null);
        String loginName = CookieUtil.getCookieByLoginName(req);
        Result result = null;
        return hcrAllocationService.getByareaId(loginName);
    }

    /**
     *
     *
     * @param req
     * @param resp
     * @param body,检索条件，放号时间
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/hospital/area/rule/query", method = RequestMethod.POST)
    public Result query(HttpServletRequest req, HttpServletResponse resp, @RequestBody HosAllocationRuleInfoDto hosAllocationRuleInfoDto) throws ItSysException {
        printStartTag("条件查询规则 （地区）");
        log.info("条件查询规则 （地区）");
        log.info("传入包体:{}", hosAllocationRuleInfoDto);

        String loginName=CookieUtil.getCookieByLoginName(req);
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        int areaId = doctorInfo.getAreaDeptId();
        hosAllocationRuleInfoDto.setAreaDeptId(areaId);
        ListPageUtil listPage = hcrAllocationService.queryRule(hosAllocationRuleInfoDto,loginName);
        Result result=new Result(listPage.getResultList(),listPage.getPageInfo());
        printEndTag("条件查询规则");
        log.info("返回查询对象个数={}", listPage.getResultList().size());
        return result;
    }




    /**
     * 立刻停诊
     *
     * @param req
     * @param resp
     * @param body,检索条件，放号时间
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/hospital/area/rule/diagnosisDown", method = RequestMethod.POST)
    public Result diagnosisDown(HttpServletRequest req, HttpServletResponse resp, @RequestBody ColonoscopyDto colonoscopyDto) throws ItSysException {
        printStartTag("立刻停诊");
        log.info("立刻停诊");
        log.info("传入包体:{}", colonoscopyDto);

        String loginName=CookieUtil.getCookieByLoginName(req);
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        int areaId = doctorInfo.getAreaDeptId();
        colonoscopyDto.setArea_dept_id(areaId);

        //校验当天+1规则
        if(StringUtils.isEmpty(colonoscopyDto.getRuleId())){
           if(DateUtil.getIntervalDays(colonoscopyDto.getReservationDate(),DateUtil.formatDate(new Date(),"yyyy-MM-dd"))<=0){
               return  new Result(GlobalErrorCode.DIAGNOSIS_ISERROR_TIME_CODE,GlobalErrorCode.DIAGNOSIS_ISERROR_TIME_MSG);
           }
        }
        try{
            hcrAllocationService.diagnosisDown(colonoscopyDto,loginName);
        }catch (Exception e){
            throw new ItSysException(GlobalErrorCode.DIAGNOSIS_ISERROR_CODE,GlobalErrorCode.DIAGNOSIS_ISERROR_MSG);
        }
        Result result=new Result();
        return result;
    }

    /**
     * 签到列表
     *
     * @param req
     * @param resp
     * @return
     */
    @RequestMapping(value = "/hospital/community/allocation/queryallnotlist", method = RequestMethod.POST)
    public Result queryallnotlist(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) {
        printStartTag("queryallnotlist");
        printHttpPacket(req, null);
        String loginName = CookieUtil.getCookieByLoginName(req);
        Result result = null;
        TodoVo vo = JSONUtils.toBean(body, TodoVo.class);
        try {
            vo = JSONUtils.toBean(body, TodoVo.class);
            result = hcrAllocationService.queryallnotlist(loginName, vo);
        } catch (Exception e) {
            throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO, "Parse request body error.");
        }
        printEndTag("queryallnotlist");
        return result;
    }

    /**
     * 地区放号一览表
     *
     * @param req
     * @param resp
     * @param body
     * @return
     */
    @RequestMapping(value = "/hospital/community/list/queryPutCodeByAreaId", method = RequestMethod.POST)
    public Result queryPutCodeByAreaId(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) {
        printStartTag("地区放号一览表==》queryPutCodeByAreaId");
        printHttpPacket(req, null);
        String loginName = CookieUtil.getCookieByLoginName(req);
//		String loginName = "hunanadmin";
        Result result = null;
        TodoVo vo = JSONUtils.toBean(body, TodoVo.class);
        try {
            vo = JSONUtils.toBean(body, TodoVo.class);
            result = hcrAllocationService.queryPutCodeByAreaId(loginName, vo);
            //result=hcrAllocationService.queryPutCodeByRuleId(loginName, vo);
        } catch (Exception e) {
            throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO, "Parse request body error.");
        }
        printEndTag("queryPutCodeByAreaId");
        return result;
    }


    /**
     * 地区放号详情 日历表
     *
     * @param req
     * @param resp
     * @param body
     * @return
     */
    @RequestMapping(value = "/hospital/num/list/queryCalendar", method = RequestMethod.POST)
    public Result queryCalendar(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) {
        printStartTag("地区放号详情 日历表 queryCalendar");
        printHttpPacket(req, null);
        String loginName = CookieUtil.getCookieByLoginName(req);
//		String loginName = "hunanadmin";
        Result result = null;
        TodoVo vo = JSONUtils.toBean(body, TodoVo.class);
        try {
            vo = JSONUtils.toBean(body, TodoVo.class);
            //result = hcrAllocationService.queryPutCodeByAreaId(loginName, vo);
            result=hcrAllocationService.queryCalendar(loginName, vo);
        } catch (Exception e) {
            throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO, "Parse request body error.");
        }
        printEndTag("queryPutCodeByAreaId");
        return result;
    }


    /**
     * 根据时间和规则id、规则 查询地区详情
     *
     * @param req
     * @param resp
     * @param body
     * @return
     */
    @RequestMapping(value = "/hospital/num/list/queryByRuleIdAndRule", method = RequestMethod.POST)
    public Result queryByRuleIdAndRule(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) {
        printStartTag("地区放号一览表 根据时间和规则id、规则 查询地区详情==》queryByRuleIdAndRule");
        printHttpPacket(req, null);
        String loginName = CookieUtil.getCookieByLoginName(req);
//		String loginName = "hunanadmin";
        Result result = null;
        TodoVo vo = JSONUtils.toBean(body, TodoVo.class);
        try {
            vo = JSONUtils.toBean(body, TodoVo.class);
            //根据
            result=hcrAllocationService.queryPutCodeByRuleId(loginName, vo);
        } catch (Exception e) {
            throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO, "Parse request body error.");
        }
        printEndTag("queryPutCodeByAreaId");
        return result;
    }


    /**
     * 社区详情 查询数量
     *
     * @param req
     * @param resp
     * @param body
     * @return
     */
    @RequestMapping(value = "/hospital/num/list/queryNumByRuleIdAndRule", method = RequestMethod.POST)
    public Result queryNumByRuleIdAndRule(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) {
        printStartTag("地区放号一览表 根据时间和规则id、规则 社区详情 查询数量==》queryNumByRuleIdAndRule");
        printHttpPacket(req, null);
        String loginName = CookieUtil.getCookieByLoginName(req);
//		String loginName = "hunanadmin";
        Result result = null;
        TodoVo vo = JSONUtils.toBean(body, TodoVo.class);
        try {
            vo = JSONUtils.toBean(body, TodoVo.class);
            result=hcrAllocationService.queryNumByRuleIdAndRule(loginName, vo);
        } catch (Exception e) {
            throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO, "Parse request body error.");
        }
        printEndTag("queryPutCodeByAreaId");
        return result;
    }

    /**
     * 社区详情 查询详情
     *
     * @param req
     * @param resp
     * @param body
     * @return
     */
    @RequestMapping(value = "/hospital/num/list/queryEntityByRuleIdAndRule", method = RequestMethod.POST)
    public Result queryEntityByRuleIdAndRule(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) {
        printStartTag("地区放号一览表 根据时间和规则id、规则 社区详情 查询对象列表==》queryEntityByRuleIdAndRule");
        printHttpPacket(req, null);
        String loginName = CookieUtil.getCookieByLoginName(req);
//		String loginName = "hunanadmin";
        Result result = null;
        TodoVo vo = JSONUtils.toBean(body, TodoVo.class);
        try {
            vo = JSONUtils.toBean(body, TodoVo.class);
            result=hcrAllocationService.queryEntityByRuleIdAndRule(loginName, vo);
        } catch (Exception e) {
            throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO, "Parse request body error.");
        }
        printEndTag("queryPutCodeByAreaId");
        return result;
    }

    /**
     * 社区详情查询详情下查看受试者
     *
     * @param req
     * @param resp
     * @param body
     * @return
     */
    @RequestMapping(value = "/hospital/num/list/queryPerSonByRuleIdAndRule", method = RequestMethod.POST)
    public Result queryPerSonByRuleIdAndRule(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) {
        printStartTag("地区放号一览表 新增的查询受试者==》queryPerSonByRuleIdAndRule");
        printHttpPacket(req, null);
        String loginName = CookieUtil.getCookieByLoginName(req);
//		String loginName = "hunanadmin";
        Result result = null;
        TodoVo vo = JSONUtils.toBean(body, TodoVo.class);
        try {
            vo = JSONUtils.toBean(body, TodoVo.class);
            result=hcrAllocationService.queryPerSonByRuleIdAndRule(loginName, vo);
        } catch (Exception e) {
            throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO, "Parse request body error.");
        }
        printEndTag("queryPutCodeByAreaId");
        return result;
    }



    /**
     * 预约放号记录表
     *
     * @param req
     * @param resp
     * @param body
     * @return
     */
    @RequestMapping(value = "/hospital/num/list/queryFanghao", method = RequestMethod.POST)
    public Result queryFanghao(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) {
        printStartTag("地区放号一览表 新增的查询受试者==》queryPerSonByRuleIdAndRule");
        printHttpPacket(req, null);
        String loginName = CookieUtil.getCookieByLoginName(req);
//		String loginName = "hunanadmin";
        Result result = null;
        TodoVo vo = JSONUtils.toBean(body, TodoVo.class);
        try {
            vo = JSONUtils.toBean(body, TodoVo.class);
            result=hcrAllocationService.queryFanghao(loginName, vo);
        } catch (Exception e) {
            throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO, "Parse request body error.");
        }
        printEndTag("queryPutCodeByAreaId");
        return result;
    }
    /**
     * 地区放号情况一览表详情查询
     *
     * @param req
     * @param resp
     * @param body
     * @return
     */
    @RequestMapping(value = "/hospital/community/detaillist/queryPutCodeDetailByCommId", method = RequestMethod.POST)
    public Result queryPutCodeDetailByCommId(HttpServletRequest req, HttpServletResponse resp, @RequestBody ColonoscopyQueryResult queryparam) {
        printStartTag("地区放号一览表查询详情==》queryPutCodeDetailByCommId");
        printHttpPacket(req, null);
        Result result = null;
        try {
            result = hcrAllocationService.queryPutCodeDetailListByCommitId(queryparam);
        } catch (Exception e) {
            throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO, "Parse request body error.");
        }
        printEndTag("queryPutCodeDetailByCommId");
        return result;
    }

    /**
     * 地区一览表查询导出
     *
     * @param req
     * @param response
     * @throws ItSysException
     */
    @RequestMapping(value = "/hospital/community/list/queryPutCodeForExcel", method = RequestMethod.GET)
    public void queryForAreaListExcel(HttpServletRequest req, HttpServletResponse response) throws ItSysException {
        printStartTag("地区一览表列表导出==》start");
        printHttpPacket(req, null);
        String loginName = CookieUtil.getCookieByLoginName(req);
//		String loginName = "hunanadmin";
        log.info("@Controller导出excel地区一览表列表导出 ");
//		AreaListForExcelVo vo = new AreaListForExcelVo();
        TodoVo vo = new TodoVo();
//		List<ColonoscopyVo> list = colonoscopyService.queryForAreaExcel(queryCondition, loginName);
        List<AreaListForExcelVo> list = hcrAllocationService.queryForAreaExcel(vo, loginName);
        //导出逻辑
        String[][] array = new String[][]{};
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/msexcel");
        String fileName = "地区放号一览表";
        try {
            response.setHeader("content-disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx\"");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        //获得表头
        Vector titleVec = new Vector();
        titleVec.add("所属社区");
        titleVec.add("预约时间");
        titleVec.add("放号数量");
        titleVec.add("已预约人数");
        titleVec.add("已检查人数");
        titleVec.add("sid");
        titleVec.add("姓名");
        titleVec.add("性别");
        titleVec.add("年龄");
        titleVec.add("手机号码");
        titleVec.add("分组");
        titleVec.add("年度状态");
        titleVec.add("预约状态");
        titleVec.add("检查状态");
        titleVec.add("完成情况");
        titleVec.add("创建者");
        String areaName = "放号记录一览表";
        //为表的每一列设定列宽.
        int[] titleWidthAry = {15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15};
        if (list != null && list.size() > 0) {
            array = new String[list.size()][16];
            for (int i = 0; i < list.size(); i++) {
                AreaListForExcelVo review = list.get(i);
                if (review.getCommName() != null) {
                    array[i][0] = review.getCommName() != null ? review.getCommName() : "";
                    array[i][1] = review.getPeriod() != null ? review.getPeriod() : "";
                    array[i][2] = review.getAmount() != null ? review.getAmount().toString() : "";
                    array[i][3] = review.getAlSums1() != null ? review.getAlSums1().toString() : "";
                    array[i][4] = review.getAlSums2() != null ? review.getAlSums2().toString() : "";
                } else if (review.getCommName() == null) {
                    array[i][0] = "";
                    array[i][1] = "";
                    array[i][2] = "";
                    array[i][3] = "";
                    array[i][4] = "";
                }
                if (review.getSid() != null) {
                    array[i][5] = review.getSid() != null ? review.getSid().toString() : "";
                    array[i][6] = review.getName() != null ? review.getName().toString() : "";
                    if (review.getSex() != null) {
                        if (review.getSex() == 1) {
                            array[i][7] = "男";
                        } else if (review.getSex() == 2) {
                            array[i][7] = "女";
                        }
                    } else {
                        array[i][7] = review.getSex() != null ? review.getSex().toString() : "";
                    }
                    array[i][8] = review.getAge() != null ? review.getAge().toString() : "";
                    array[i][9] = review.getPhone() != null ? review.getPhone().toString() : "";
                    if (review.getGroup() != null && !review.getGroup().equals("")) {
                        if (review.getGroup().equals("A")) {
                            array[i][10] = "A组";
                        } else if (review.getGroup().equals("B")) {
                            array[i][10] = "B组";
                        } else if (review.getGroup().equals("C")) {
                            array[i][10] = "C组";
                        } else if (review.getGroup().equals("Cg")) {
                            array[i][10] = "C组高危";
                        } else if (review.getGroup().equals("Cd")) {
                            array[i][10] = "C组低危";
                        } else if (review.getGroup().equals("Cp")) {
                            array[i][10] = "C组未评估";
                        }
                    } else {
                        array[i][10] = review.getGroup() != null ? review.getGroup() : "";
                    }
                    if (review.getOverallStatusCy() != null) {
                        if (review.getOverallStatusCy() == 1) {
                            array[i][11] = "正常";
                        } else if (review.getOverallStatusCy() == 2) {
                            array[i][11] = "退出";
                        } else if (review.getOverallStatusCy() == 3) {
                            array[i][11] = "肠癌";
                        } else if (review.getOverallStatusCy() == 4) {
                            array[i][11] = "死亡";
                        }
                    } else {
                        array[i][11] = review.getOverallStatusCy() != null ? review.getOverallStatusCy().toString() : "";
                    }

                    if (review.getReserveStatus() != null && !review.getReserveStatus().equals("")) {
                        if (review.getReserveStatus() == 1) {
                            array[i][12] = "未预约";
                        } else if (review.getReserveStatus() == 2) {
                            array[i][12] = "已预约";
                        }
                    } else {
                        array[i][12] = review.getReserveStatus() != null ? review.getReserveStatus().toString() : "";
                    }
                    if (review.getExaminationStatus() != null && !review.getExaminationStatus().equals("")) {
                        if (review.getExaminationStatus() == 1) {
                            array[i][13] = "未检查";
                        } else if (review.getExaminationStatus() == 2) {
                            array[i][13] = "已检查";
                        }
                    } else {
                        array[i][13] = review.getExaminationStatus() != null ? review.getExaminationStatus().toString() : "";
                    }
                    if (review.getFinishedStatus() != null && !review.getFinishedStatus().equals("")) {
                        if (review.getFinishedStatus() == 1) {
                            array[i][14] = "未完成";
                        } else if (review.getFinishedStatus() == 2) {
                            array[i][14] = "已完成";
                        }
                    } else {
                        array[i][14] = review.getFinishedStatus() != null ? review.getFinishedStatus().toString() : "";
                    }
                    array[i][15] = review.getNickName() != null ? review.getNickName().toString() : "";
                } else if (review.getSid() == null) {
                    array[i][5] = "";
                    array[i][6] = "";
                    array[i][7] = "";
                    array[i][8] = "";
                    array[i][9] = "";
                    array[i][10] = "";
                    array[i][11] = "";
                    array[i][12] = "";
                    array[i][13] = "";
                    array[i][14] = "";
                    array[i][15] = "";
                }
            }
        }
        try {
            ExportExcelUtil.excelOS("放号记录一览表", titleVec, titleWidthAry, array, response.getOutputStream(), areaName);
        } catch (IOException e) {
            e.printStackTrace();
            log.info(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
        }
        printEndTag("/hospital/community/list/queryPutCodeForExcel");
    }

    @Override
    protected String getClassName() {
        // TODO Auto-generated method stub
        return HcrAllocationController.class.getName();
    }
}
