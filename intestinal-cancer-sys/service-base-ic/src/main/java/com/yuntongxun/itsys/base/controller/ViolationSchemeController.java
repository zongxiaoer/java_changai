package com.yuntongxun.itsys.base.controller;

import com.yuntongxun.itsys.base.common.AbstractController;
import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.CookieUtil;
import com.yuntongxun.itsys.base.common.util.GlobalErrorCode;
import com.yuntongxun.itsys.base.common.util.JSONUtils;
import com.yuntongxun.itsys.base.common.util.SendMessageCenter;
import com.yuntongxun.itsys.base.po.HospitalReview;
import com.yuntongxun.itsys.base.po.ViolationScheme;
import com.yuntongxun.itsys.base.po.dto.ItsysUserDto;
import com.yuntongxun.itsys.base.po.dto.courier.HospitalReferenceRecordDto;
import com.yuntongxun.itsys.base.po.dto.message.HospitalMessageCenterDto;
import com.yuntongxun.itsys.base.service.AbnormalEventService;
import com.yuntongxun.itsys.base.service.ColonoscopyService;
import com.yuntongxun.itsys.base.service.HospitalMessageCenterService;
import com.yuntongxun.itsys.base.service.PersonService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 违反方案
 *
 * @author maxiang
 *
 */
@RestController
public class ViolationSchemeController extends AbstractController {

    private final Logger log = LogManager.getLogger(ViolationSchemeController.class);
    @Autowired
    private AbnormalEventService abnormalEventService;

    @Autowired
    private ColonoscopyService colonoscopyService;

    @Autowired
    private HospitalMessageCenterService hospitalMessageCenterService;

    @Autowired
    private PersonService personService;
    /**
     * 违反方案第一次
     * @param req
     * @param resp
     * @param body
     * @return
     */
    @RequestMapping(value = "add/violation/savescheme", method = RequestMethod.POST)
    public String saveviolationScheme(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) {
        log.info("@Controller saveviolationScheme接收参数为:{}", body);
        Object obj = null;
        String loginName="";
        List<ItsysUserDto> itsysUserDtos=new ArrayList<>();
        List<ItsysUserDto> itsysUserDtoList=new ArrayList<>();
        ViolationScheme violationScheme;

        try {
            violationScheme = JSONUtils.toBean(body, ViolationScheme.class);
            loginName=CookieUtil.getCookieByLoginName(req);
            obj =  abnormalEventService.addVScheme(violationScheme,loginName);
            itsysUserDtos = colonoscopyService.queryloginNamesByloginName(loginName);
            List<ItsysUserDto> itsysUserDtoss =colonoscopyService.queryloginNameRootByloginName(loginName);
            itsysUserDtos.addAll(itsysUserDtoss);
        } catch (ItSysException e) {
            log.info("saveviolationScheme"+e.getMessage());
            return JSONUtils.toJson(new Response("error",e.getMessage()));
        }

        //添加消息中心
        List<HospitalMessageCenterDto> hospitalMessageCenterDtoList = new ArrayList<>();
        String meaasge_typpe=Constans.meaasge_typpe2;
        //获取退出原因
        String text="";
        String sid=violationScheme.getSid();
        String meaasge_text_typpe=Constans.meaasge_text_typpe1;
        String courierNumber="";
        String remark="";
        Map<String, Object> map=(Map<String, Object>)obj;
        for (ItsysUserDto itsysUserDto : itsysUserDtos) {
            HospitalMessageCenterDto hospitalMessageCenterDto = new HospitalMessageCenterDto();
            hospitalMessageCenterDto.setSendUser(loginName);
            String accpetName = itsysUserDto.getLoginname();
            hospitalMessageCenterDto.setAcceptUser(accpetName);
            hospitalMessageCenterDto.setMessageType(Constans.meaasge_typpe2);
            hospitalMessageCenterDto.setSid(sid);
            hospitalMessageCenterDto.setForm_type(Constans.HOSPITAL_VIOLATION_SCHEME);
            hospitalMessageCenterDto.setData_id(Integer.parseInt(map.get("id").toString()));
            /**
             * 1  异常    违反方案
             *           sid
             *           已经退出研究
             *           sid、text
             *           诊断为结直肠癌
             *           sid
             *申请|发放编辑
             *          快递模块
             *          sendUser、acceptUser、courierNumber
             *          管理模块
             *          sendUser、acceptUser、text（模块+sid）
             *通知发放
             *    public static String getMessage(String sendUser, String acceptUser, String meaasgeType, String text, String sid, String meaasgeTextType, String courierNumber) {
             *
             *
             *
             *
             */
            String message = SendMessageCenter.getMessage(loginName, accpetName, meaasge_typpe, text, sid,meaasge_text_typpe,courierNumber,remark);
            hospitalMessageCenterDto.setMessageText(message);
            hospitalMessageCenterDto.setId(UUID.randomUUID().toString().replace("-", ""));
            hospitalMessageCenterDtoList.add(hospitalMessageCenterDto);
        }
        hospitalMessageCenterService.save(hospitalMessageCenterDtoList, null,Constans.APPLY_EDIT_STATUS2,Constans.EDIT_STATUS1,Constans.APPROVAL_STATUS1,"","",true,null);

        return JSONUtils.toJson(new Response(obj));
//        return JSONUtils.toJson(new Response());
    }
    /**
     * 填充违反方案空值
     * @param req
     * @param resp
     * @param body
     * @return
     */
    @RequestMapping(value = "update/violation/savescheme", method = RequestMethod.POST)
    public String updateviolationScheme(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) {
        log.info("@Controller updateviolationScheme接收参数为:{}", body);
        try {
//            ViolationScheme violationScheme = JSONUtils.toBean(body, ViolationScheme.class);
            ViolationScheme violationScheme = JSONUtils.jsonToBeanDateSerializer(body, ViolationScheme.class, "yyyy-MM-dd");
            String loginName=CookieUtil.getCookieByLoginName(req);

            HospitalReferenceRecordDto hospitalReferenceRecordDto = new HospitalReferenceRecordDto();
            //根据id获取信息
            ViolationScheme violationScheme1 = abnormalEventService.queryHospitalViolationSchemeById(violationScheme.getId(), violationScheme.getSid());
            if(Constans.HOSPITAL_VIOLATION_SCHEME_ENTRY_STATUS1==violationScheme1.getEntryStatus()){
                if(!Constans.EDIT_STATUS2.equals(violationScheme1.getEditStatus())){
                    return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
                }
                hospitalReferenceRecordDto.setStatus(Constans.HOSPITAL_REFERENCE_RECORD_STATUS_FINISH);
                hospitalReferenceRecordDto.setDataId(violationScheme1.getId());
                hospitalReferenceRecordDto.setFormType(Constans.HOSPITAL_VIOLATION_SCHEME);
                hospitalReferenceRecordDto.setEditPerson(loginName);
                String dataText = JSONUtils.toJson(violationScheme1);
                hospitalReferenceRecordDto.setOldData(dataText);
            }
            abnormalEventService.updateVScheme(violationScheme,loginName,hospitalReferenceRecordDto);
        } catch (ItSysException e) {
            log.info("saveviolationScheme"+e.getMessage());
            return JSONUtils.toJson(new Response("error",e.getMessage()));
        }
        return JSONUtils.toJson(new Response());
    }

    /**
     * 地区填充违反方案空值
     * @param req
     * @param resp
     * @param body
     * @return
     */
    @RequestMapping(value = "update/violation/saveschemeInArea", method = RequestMethod.POST)
    public String updateviolationSchemeInArea(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) {
        log.info("@Controller 地区 saveschemeInArea:{}", body);
        try {
//            ViolationScheme violationScheme = JSONUtils.toBean(body, ViolationScheme.class);
            ViolationScheme violationScheme = JSONUtils.jsonToBeanDateSerializer(body, ViolationScheme.class, "yyyy-MM-dd");
            String loginName=CookieUtil.getCookieByLoginName(req);

            HospitalReferenceRecordDto hospitalReferenceRecordDto = new HospitalReferenceRecordDto();
            //根据id获取信息
            ViolationScheme violationScheme1 = abnormalEventService.queryHospitalViolationSchemeById(violationScheme.getId(), violationScheme.getSid());
            if(Constans.HOSPITAL_VIOLATION_SCHEME_ENTRY_STATUS1==violationScheme1.getEntryStatus()){
                if(!Constans.EDIT_STATUS2.equals(violationScheme1.getEditStatus())){
                    return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
                }
                hospitalReferenceRecordDto.setStatus(Constans.HOSPITAL_REFERENCE_RECORD_STATUS_FINISH);
                hospitalReferenceRecordDto.setDataId(violationScheme1.getId());
                hospitalReferenceRecordDto.setFormType(Constans.HOSPITAL_VIOLATION_SCHEME);
                hospitalReferenceRecordDto.setEditPerson(loginName);
                String dataText = JSONUtils.toJson(violationScheme1);
                hospitalReferenceRecordDto.setOldData(dataText);
            }
            HospitalReview hospitalReview = personService.getBySid(violationScheme.getSid());
            abnormalEventService.updateVSchemeInArea(violationScheme,hospitalReview.getCommunityDeptId(),hospitalReview.getAreaDeptId(),hospitalReferenceRecordDto);
        } catch (ItSysException e) {
            log.info("saveschemeInArea"+e.getMessage());
            return JSONUtils.toJson(new Response("error",e.getMessage()));
        }
        return JSONUtils.toJson(new Response());
    }

    /**
     * 获取当前系统时间
     * @return
     */
    @RequestMapping(value = "get/violation/getTime", method = RequestMethod.POST)
    public String getTime(HttpServletRequest req, HttpServletResponse resp){
        printStartTag("获取系统当前时间");
        Object obj = abnormalEventService.getTime();
        log.info("返回查询对象：{}", obj);
        printEndTag("获取系统当前时间");
        return JSONUtils.toJson(new Response(obj));
    }
    @Override
    protected String getClassName() {
        // TODO Auto-generated method stub
        return RoleController.class.getName();
    }
}
