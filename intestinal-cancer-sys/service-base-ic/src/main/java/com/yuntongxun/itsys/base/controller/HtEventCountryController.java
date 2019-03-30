package com.yuntongxun.itsys.base.controller;

import com.yuntongxun.itsys.base.cache.RedisManager;
import com.yuntongxun.itsys.base.common.AbstractController;
import com.yuntongxun.itsys.base.common.constans.RedisConstant;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.*;
import com.yuntongxun.itsys.base.service.HcRecordService;
import com.yuntongxun.itsys.base.service.HtEventService;
import com.yuntongxun.itsys.base.service.UserService;
import com.yuntongxun.itsys.base.vo.DoctorInfo;
import com.yuntongxun.itsys.base.vo.Result;
import com.yuntongxun.itsys.base.vo.TodoVo;
import com.yuntongxun.itsys.base.vo.TodoVoPo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zongt
 * @date 2018/5/18
 */
@RestController
public class HtEventCountryController extends AbstractController {

    @Autowired
    private HtEventService htEventService;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisManager redis;

    @Autowired
    private HcRecordService hcRecordService;

    public static final int HOSPITAL_TYPE_COMMUNITY = 1;
    public static final int HOSPITAL_TYPE_AREA = 2;
    public static final int HOSPITAL_TYPE_NATION = 3;


    private final Logger log = LogManager.getLogger(HtEventController.class.getName());

    /**
     * @func
     * @desc    国家查看各种代办情况
     * @author zongt
     * @create 2018/5下午7:117:11
     * @request
     * @response
     **/
    @RequestMapping(value = "/hospital/country/todo/query", method = RequestMethod.POST)
    public Result HtEventGet(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) {
        printStartTag("HtEventGet");
        printHttpPacket(req, null);
        Result result = null;
        log.info("@Controller HtEventController接收参数为：{}", body);
        try {
            //获取当前用户信息
            String key = RedisConstant.HOSPITAL_KEY_INFO+ CookieUtil.getCookieByLoginName(req);
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
     * @func
     * @desc   DNA结果代办 受试者列表
     * @author zongt
     * @create 2018/5/下午2:51:51
     * @request
     * @response
     **/
    @RequestMapping(value = "/hospital/country/todo/result/nodnaquery", method = RequestMethod.POST)
    public Result notEntryNoDNAResultQuery(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) {
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
        ListPageUtil listPage = hcRecordService.notEntryNoDNAResultQuery(vo, loginName);

        Result result=new Result(listPage.getResultList(),listPage.getPageInfo());
        printEndTag("查询 待录入告知书  受试者 待办");
        log.info("返回查询对象个数={}", listPage.getResultList().size());
        return result;
    }

    @Override
    protected String getClassName() {
        // TODO Auto-generated method stub
        return HtEventController.class.getName();
    }

}
