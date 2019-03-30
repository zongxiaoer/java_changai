package com.yuntongxun.itsys.base.controller;

import com.yuntongxun.itsys.base.common.AbstractController;
import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.*;
import com.yuntongxun.itsys.base.po.ItsysDepartment;
import com.yuntongxun.itsys.base.po.ViolationScheme;
import com.yuntongxun.itsys.base.po.dto.abnormalevent.AbnormalEventDto;
import com.yuntongxun.itsys.base.service.AbnormalEventService;
import com.yuntongxun.itsys.base.service.UserService;
import com.yuntongxun.itsys.base.vo.Response;
import com.yuntongxun.itsys.base.vo.Result;
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
 * 异常事件1叔叔
 *
 * @author maxiang
 */
@RestController
public class AbnormalEventController extends AbstractController {

    private final Logger log = LogManager.getLogger(AbnormalEventController.class);

    @Autowired
    AbnormalEventService abnormalEventService;

    @Autowired
    UserService userService;

    /**
     * 录入异常事件
     *
     * @param
     * @param req
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/abnormal/event/addOrUpdateAbnormalEvent", method = RequestMethod.POST)
    public String addOrUpdateAbnormalEvent(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body)
            throws ItSysException {
        log.info("addOrUpdateAbnormalEvent 输入参数:{}", body);
        String loginName = CookieUtil.getCookieByLoginName(req);
        log.info("addOrUpdateAbnormalEvent loginName：{}", loginName);
        abnormalEventService.addOrUpdateAbnormalEvent(body, loginName);
        return JSONUtils.toJson(new Response());
    }

    /**
     * @func
     * @desc 根据ID查询违反方案事件详情
     * @author zongt
     * @create 2018/4/下午4:40:40
     * @request
     * @response
     **/
    @RequestMapping(value = "/abnormal/event/queryHospitalViolationScheme", method = RequestMethod.POST)
    public String queryHospitalViolationScheme(HttpServletRequest req, HttpServletResponse resp, @RequestBody ViolationScheme violationScheme)
            throws ItSysException {
        log.info("addOrUpdateAbnormalEvent 输入参数:{}", JSONUtils.toJson(violationScheme));
        return abnormalEventService.queryHospitalViolationSchemeToStringById(violationScheme);
    }


    /**
     * 根据登陆用户显示异常事件列表
     *
     * @param request
     * @param abnormalEventDto
     * @return
     */
    @RequestMapping(value = "/abnormal/event/queryAbnormalEventByUser", method = RequestMethod.POST)
    public Result queryAbnormalEventByUser(HttpServletRequest request, @RequestBody AbnormalEventDto abnormalEventDto) {

        String loginName = CookieUtil.getCookieByLoginName(request);
        ItsysDepartment itsysDepartment = userService.getCommunityIdAndAreaIdByLoginName(loginName);
        ListPageUtil listPage = abnormalEventService.queryAbnormalEventByUser(abnormalEventDto, itsysDepartment, itsysDepartment.getType());
        Result result = new Result(listPage.getResultList(), listPage.getPageInfo());
        log.info("返回查询对象个数={}", listPage.getResultList() == null ? 0 : listPage.getResultList().size());
        return result;
    }

    @Override
    protected String getClassName() {
        // TODO Auto-generated method stub
        return RoleController.class.getName();
    }
}
