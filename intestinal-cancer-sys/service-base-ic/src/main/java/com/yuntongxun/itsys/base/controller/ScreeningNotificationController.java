package com.yuntongxun.itsys.base.controller;

import com.yuntongxun.itsys.base.common.AbstractController;
import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.CookieUtil;
import com.yuntongxun.itsys.base.common.util.GlobalErrorCode;
import com.yuntongxun.itsys.base.common.util.JSONUtils;
import com.yuntongxun.itsys.base.po.ScreeningNotification;
import com.yuntongxun.itsys.base.po.dto.courier.HospitalReferenceRecordDto;
import com.yuntongxun.itsys.base.service.ScreeningNotificationService;
import com.yuntongxun.itsys.base.vo.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * 筛查报告单
 *
 * @author maxiang
 *
 */
@RestController
public class ScreeningNotificationController extends AbstractController {

    private final Logger log = LogManager.getLogger(ScreeningNotificationController.class);

    @Autowired
    private ScreeningNotificationService screeningNotificationService;

    /**
     * 添加肠镜报告单
     * @param
     * @param req
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/screening/notification/addScreeningNotification", method = RequestMethod.POST)
    public String addScreeningNotification(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body)
            throws ItSysException {
        log.info("addScreeningNotification 输入参数:{}", body);
        String loginName= CookieUtil.getCookieByLoginName(req);
        log.info("addScreeningNotification loginName：{}", loginName);
        screeningNotificationService.addScreeningNotificationt(body,loginName);
        return JSONUtils.toJson(new Response());
    }


    /*
     * 根据id查询数据
     *
     * @author ${zongt}
     * @since v1.0.0
     */
    @RequestMapping(value = "/screening/notification/queryScreeningNotificationById", method = RequestMethod.POST)
    public String queryScreeningNotificationById(HttpServletRequest req, HttpServletResponse resp, @RequestBody ScreeningNotification screeningNotification)
            throws ItSysException {
        log.info("addScreeningNotification 输入参数:{}", screeningNotification);
        String loginName= CookieUtil.getCookieByLoginName(req);
        log.info("addScreeningNotification loginName：{}", loginName);
        List<ScreeningNotification> screeningNotifications = screeningNotificationService.queryById(screeningNotification.getId());
        return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, screeningNotifications.get(0), null));
    }



    /*
     *    修改告知书
     *
     * @author ${zongt}
     * @since v1.0.0
     */
    @RequestMapping(value = "/screening/notification/updateScreeningNotification", method = RequestMethod.POST)
    public String updateScreeningNotification(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body)
            throws ItSysException {
        log.info("addScreeningNotification 输入参数:{}", body);
        String loginName= CookieUtil.getCookieByLoginName(req);
        log.info("addScreeningNotification loginName：{}", loginName);
        ScreeningNotification screeningNotification = JSONUtils.jsonToBeanDateSerializer(body, ScreeningNotification.class,"yyyy-MM-dd");

        //根据id获取信息
        List<ScreeningNotification> screeningNotifications = screeningNotificationService.queryById(screeningNotification.getId());
        if(screeningNotifications.size()!=1){
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
        }

        HospitalReferenceRecordDto hospitalReferenceRecordDto = new HospitalReferenceRecordDto();
        hospitalReferenceRecordDto.setStatus(Constans.HOSPITAL_REFERENCE_RECORD_STATUS_FINISH);
        hospitalReferenceRecordDto.setDataId(screeningNotification.getId());
        hospitalReferenceRecordDto.setFormType(Constans.HOSPITAL_SCREENING_NOTIFICATION);
        hospitalReferenceRecordDto.setEditPerson(loginName);
        String dataText = JSONUtils.toJson(screeningNotifications.get(0));
        hospitalReferenceRecordDto.setOldData(dataText);
        screeningNotificationService.updateScreeningNotificationt(screeningNotification,loginName,hospitalReferenceRecordDto);
        return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, null, null));
    }

    @Override
    protected String getClassName() {
        // TODO Auto-generated method stub
        return RoleController.class.getName();
    }
}
