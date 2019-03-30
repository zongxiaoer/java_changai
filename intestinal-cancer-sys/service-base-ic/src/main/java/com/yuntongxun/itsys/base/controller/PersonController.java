/**
 * Project Name:service-base-yl
 * File Name:PersonController.java
 * Package Name:com.yuntongxun.itsys.base.controller
 * Date:2018年4月9日下午5:41:37
 * Copyright (c) 2018, ty All Rights Reserved.
 */

package com.yuntongxun.itsys.base.controller;

import com.yuntongxun.itsys.base.cache.RedisManager;
import com.yuntongxun.itsys.base.common.AbstractController;
import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.constans.RedisConstant;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.*;
import com.yuntongxun.itsys.base.po.*;
import com.yuntongxun.itsys.base.po.dto.HospitalRiskFactorDto;
import com.yuntongxun.itsys.base.po.dto.ItsysUserDto;
import com.yuntongxun.itsys.base.po.dto.courier.HospitalReferenceRecordDto;
import com.yuntongxun.itsys.base.po.dto.message.HospitalMessageCenterDto;
import com.yuntongxun.itsys.base.po.dto.quitlog.QuitInResultDto;
import com.yuntongxun.itsys.base.po.dto.quitlog.QuitSearchDto;
import com.yuntongxun.itsys.base.service.ColonoscopyService;
import com.yuntongxun.itsys.base.service.HospitalMessageCenterService;
import com.yuntongxun.itsys.base.service.PersonService;
import com.yuntongxun.itsys.base.service.UserService;
import com.yuntongxun.itsys.base.vo.DoctorInfo;
import com.yuntongxun.itsys.base.vo.IntestineReason;
import com.yuntongxun.itsys.base.vo.Response;
import com.yuntongxun.itsys.base.vo.Result;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

/**
 * 社区受试者管理eee
 * ClassName:PersonController <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018年4月9日 下午5:41:37 <br/>
 *
 * @author ty
 * @see
 * @since JDK 1.8
 */
@RestController
public class PersonController extends AbstractController {

    @Autowired
    private PersonService personService;

    @Autowired
    private RedisManager redisManager;

    @Autowired
    private ColonoscopyService colonoscopyService;


    @Autowired
    private UserService userService;

    @Autowired
    private HospitalMessageCenterService hospitalMessageCenterService;

    private final Logger log = LogManager.getLogger(PersonController.class);

    /**
     * 分页查询社区受试者列表
     *
     * @param
     * @param
     * @param
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/hospital/person/query", method = RequestMethod.POST)
    public Result query(HttpServletRequest req, HttpServletResponse resp, @RequestBody HospitalReview person) throws ItSysException {

        printStartTag("/hospital/person/query");
        printHttpPacket(req, null);
        log.info("@Controller分页查询受试者列表接收参数为：{}", person.toString());
        String loginName = CookieUtil.getCookieByLoginName(req);
        log.info("@Controller分页查询受试者列表登陆者账号 loginName：{}", loginName);
        //HospitalReview person;
        try {
            //person = JSONUtils.toBean(body, HospitalReview.class);
        } catch (Exception e) {
            throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO, e.getMessage());
        }
        ListPageUtil listPage = personService.queryPage(person, loginName);
        Result result = new Result(listPage.getResultList(), listPage.getPageInfo());
        printEndTag("/hospital/person/query");
        log.info("返回查询对象个数={}", listPage.getResultList() == null ? 0 : listPage.getResultList().size());
        return result;
    }

    /**
     * 查询社区受试者列表 导出excel
     *
     * @param req
     * @param
     * @param
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/hospital/person/queryExcel", method = RequestMethod.GET)
    public void queryExcel(HttpServletRequest req, HttpServletResponse response) throws ItSysException {
        printStartTag("/hospital/person/queryExcel");
        printHttpPacket(req, null);
        //log.info("@Controller导出excel查询受试者列表接收参数为：{}", body);
        String loginName = CookieUtil.getCookieByLoginName(req);
        log.info("@Controller导出excel查询受试者列表登陆者账号 loginName：{}", loginName);
        HospitalReview person = new HospitalReview();
//		try {
//			person = JSONUtils.toBean(body, HospitalReview.class);
//		} catch (Exception e) {
//			throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO,e.getMessage());
//		}
        List<HospitalReview> list = personService.queryExcel(person, loginName);
        //导出逻辑
        String[][] array = new String[][]{};
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/msexcel");
        String fileName = "受试者列表";
        try {
            response.setHeader("content-disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx\"");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        //获得表头
        Vector titleVec = new Vector();
        titleVec.add("SID");
        titleVec.add("姓名");
        titleVec.add("性别(性别，1：男，2：女)");
        titleVec.add("年龄");
        titleVec.add("手机号");
        titleVec.add("分组");
        titleVec.add("入组日期");
        titleVec.add("总体状态(1：正常，2：退出，3：肠癌，4：死亡)");
        titleVec.add("创建者");
        //为表的每一列设定列宽.
        int[] titleWidthAry = {15, 15, 15, 15, 15, 15, 15, 60, 15};
        if (list != null && list.size() > 0) {
            array = new String[list.size()][9];
            for (int i = 0; i < list.size(); i++) {
                HospitalReview review = list.get(i);
                array[i][0] = review.getSid() != null ? review.getSid() : "";
                array[i][1] = review.getName() != null ? review.getName() : "";
                if (review.getSex() != null) {
                    if (review.getSex() == 1) {
                        array[i][2] = "男";
                    } else {
                        array[i][2] = "女";
                    }
                } else {
                    array[i][2] = review.getSex() != null ? review.getSex().toString() : "";
                }
                array[i][3] = review.getAge() != null ? review.getAge().toString() : "";
                array[i][4] = review.getPhone() != null ? review.getPhone() : "";

                if (review.getGroup() != null && !review.getGroup().equals("")) {
                    if (review.getGroup().equals("A")) {
                        array[i][5] = "A组";
                    } else if (review.getGroup().equals("B")) {
                        array[i][5] = "B组";
                    } else if (review.getGroup().equals("C")) {
                        array[i][5] = "C组";
                    } else if (review.getGroup().equals("Cg")) {
                        array[i][5] = "C组高危";
                    } else if (review.getGroup().equals("Cd")) {
                        array[i][5] = "C组低危";
                    } else if (review.getGroup().equals("Cp")) {
                        array[i][5] = "C组未评估";
                    }
                } else {
                    array[i][5] = review.getGroup() != null ? review.getGroup() : "";
                }

                array[i][6] = review.getInGroupDate() != null ? DateUtil.dateToStr(review.getInGroupDate(), 11) : "";
//                array[i][7] = review.getOverallStatusCy() != null ? review.getOverallStatusCy().toString() : "";

                if (review.getOverallStatusCy() != null) {
                    if (review.getOverallStatusCy() == 1) {
                        array[i][7] = "正常";
                    } else if (review.getOverallStatusCy() == 2) {
                        array[i][7] = "退出";
                    } else if (review.getOverallStatusCy() == 3) {
                        array[i][7] = "肠癌";
                    } else if (review.getOverallStatusCy() == 4) {
                        array[i][7] = "死亡";
                    }
                } else {
                    array[i][7] = review.getOverallStatusCy() != null ? review.getOverallStatusCy().toString() : "";
                }
                array[i][8] = review.getCreateUser() != null ? review.getCreateUser() : "";
            }
        }

        try {
            ExportExcelUtil.excelOS("受试者列表", titleVec, titleWidthAry, array, response.getOutputStream(), "sheet1");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        printEndTag("/hospital/person/queryExcel");
    }


    /**
     * 查询社区受试者列表
     *
     * @param req
     * @param resp
     * @param body
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/hospital/person/getPersonList", method = RequestMethod.POST)
    public Result getPersonList(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) throws ItSysException {

        printStartTag("/hospital/person/getPersonList");
        printHttpPacket(req, null);
        log.info("@Controller查询社区受试者列表接收参数为：{}", body);
        String loginName = CookieUtil.getCookieByLoginName(req);
        log.info("@Controller查询受试者列表登陆者账号 loginName：{}", loginName);
        HospitalReview person;
        try {
            person = JSONUtils.toBean(body, HospitalReview.class);
        } catch (Exception e) {
            throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO, e.getMessage());
        }
        List<HospitalReview> personList = personService.query(person, loginName);
        Result result = new Result(personList);
        printEndTag("/hospital/person/getPersonList");
        return result;
    }


    /**
     * 提交资格审核表单（新增受试者）
     *
     * @param req
     * @param resp
     * @param body
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/hospital/review/insert", method = RequestMethod.POST)
    public Result personInsert(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) throws ItSysException {
        printStartTag("/hospital/review/insert");
        printHttpPacket(req, null);
        log.info("@Controller提交资格审核表单参数为：{}", body);
        String loginName = CookieUtil.getCookieByLoginName(req);
        log.info("@Controller提交资格审核表单登陆者账号 loginName：{}", loginName);
        HospitalReview review;
        try {
            review = JSONUtils.jsonToBeanDateSerializer(body, HospitalReview.class, "yyyy-MM-dd");
        } catch (Exception e) {
            throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO, e.getMessage());
        }
        if (review == null) {
            throw new ItSysException(GlobalErrorCode.ERR_PARAM_NULL, "请求包体不能为空");
        }
        ReviewResult reviewResult = personService.personInsert_test(review, loginName);
        if (reviewResult == null) {
            return ResultUtil.error(GlobalErrorCode.ERR_PERSON_INSERT_PARAM_MUST_2_CODE, GlobalErrorCode.ERR_PERSON_INSERT_PARAM_MUST_2_MSG);
        }
        Result result = ResultUtil.success(Constans.KEY_SUCCESS_STATUS, Constans.KEY_SUCCESS_MSG, reviewResult);
        printEndTag("/hospital/review/insert");
        return result;
    }


    /*
     * 修改A1表单
     *
     * @author ${zongt}
     * @since v1.0.0
     */
    @RequestMapping(value = "/hospital/review/update", method = RequestMethod.POST)
    public Result personUpdate(HttpServletRequest req, HttpServletResponse resp, @RequestBody String body) throws ItSysException {
        printStartTag("/hospital/review/update");
        printHttpPacket(req, null);
        log.info("@Controller提交资格审核表单参数为1：{}", body);
        String loginName = CookieUtil.getCookieByLoginName(req);
        log.info("@Controller提交资格审核表单登陆者账号 loginName：{}", loginName);
        HospitalReview review;
        try {
            review = JSONUtils.jsonToBeanDateSerializer(body, HospitalReview.class, "yyyy-MM-dd");
        } catch (Exception e) {
            throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO, e.getMessage());
        }
        if (review == null) {
            throw new ItSysException(GlobalErrorCode.ERR_PARAM_NULL, "请求包体不能为空");
        }
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        Integer areaDeptId = doctorInfo.getAreaDeptId();
        Integer communityDeptId = doctorInfo.getCommunityDeptId();
        review.setCommunityDeptId(communityDeptId);
        review.setAreaDeptId(areaDeptId);
        review.setCreateUser(loginName);//添加受试者名称
        try {
            boolean isok = true;
            HospitalReview bySid = personService.getBySid(review.getSid());
            if (bySid == null) {
                throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG);
            }
            if (!Constans.EDIT_STATUS2.equals(bySid.getEditStatus())) {
                throw new ItSysException(GlobalErrorCode.HOSPITAL_REFERENCE_RECORD_EDIT_IS_ERROR_CODE1, GlobalErrorCode.HOSPITAL_REFERENCE_RECORD_EDIT_IS_ERROR_MSG1);
            }
            if (bySid.getIdCard().equals(review.getIdCard())) {
                isok = false;
            }
            HospitalReferenceRecordDto hospitalReferenceRecordDto = new HospitalReferenceRecordDto();
            hospitalReferenceRecordDto.setStatus(Constans.HOSPITAL_REFERENCE_RECORD_STATUS_FINISH);
            hospitalReferenceRecordDto.setDataId(bySid.getId());
            hospitalReferenceRecordDto.setFormType(Constans.HOSPITAL_INTESTINE_REVIEW);
            hospitalReferenceRecordDto.setEditPerson(loginName);
            String dataText = JSONUtils.toJson(bySid);
            hospitalReferenceRecordDto.setOldData(dataText);

            //2018-12-13  zongt  校验如果来自地区，修改地区信息
            if (doctorInfo.getHospitalType().equals(Constans.DEP_HOSPITAL_TYPE2)) {
                review.setCommunityDeptId(bySid.getCommunityDeptId());
                review.setAreaDeptId(bySid.getAreaDeptId());
                review.setCreateUser(bySid.getCreateUser());//添加受试者名称
            }
            personService.update(review, hospitalReferenceRecordDto, isok);
        } catch (Exception e) {
            return ResultUtil.error(GlobalErrorCode.ERR_PERSON_INSERT_PARAM_MUST_2_CODE, GlobalErrorCode.ERR_PERSON_INSERT_PARAM_MUST_2_MSG);
        }
        Result result = ResultUtil.success(Constans.KEY_SUCCESS_STATUS, Constans.KEY_SUCCESS_MSG, null);
        return result;
    }


    /**
     * 受试者校验身份证号
     *
     * @param req
     * @param resp
     * @param
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/hospital/personinfo/verify/idcard/{idcard}", method = RequestMethod.POST)
    public Result checkIdCard(HttpServletRequest req, HttpServletResponse resp, @PathVariable String idcard) throws ItSysException {

        printStartTag("/hospital/personinfo/verify/idcard");
        printHttpPacket(req, null);
        log.info("@Controller 受试者校验身份证号参数为：{}", idcard);
        if (StringUtils.isEmpty(idcard)) {
            throw new ItSysException(GlobalErrorCode.ERR_PARAM_NULL, "受试者校验身份证号不能为空");
        }
        int length = idcard.length();//获取身份证号长度
        if (length != 15 && length != 18) {
            throw new ItSysException(GlobalErrorCode.ERR_PARAM_NULL, "身份证号长度不合法");
        }


        //校验身份证黑名单是否存在
        boolean checkIdentity = personService.checkIdentityBlacklist(idcard, Constans.IDENTITY_BLACKLIST_TYPE);
        if (!checkIdentity) {
            throw new ItSysException(GlobalErrorCode.ERR_PERSON_INSERT_ID_CARD_BLACK_CODE, GlobalErrorCode.ERR_PERSON_INSERT_ID_CARD_BLACK_MSG);
        }
        int age = personService.checkIdCard(idcard, true);
        Result result = ResultUtil.success(Constans.KEY_SUCCESS_STATUS, Constans.KEY_SUCCESS_MSG, age);
        printEndTag("/hospital/personinfo/verify/idcard");
        return result;
    }


    /*
     * 编辑校验身份证
     *
     * @author ${zongt}
     * @since v1.0.0
     */
    @RequestMapping(value = "/hospital/personinfo/verify/updateidcard/{idcard}/sid/{sid}", method = RequestMethod.POST)
    public Result checkUpdateIdCard(HttpServletRequest req, HttpServletResponse resp, @PathVariable String idcard, @PathVariable String sid) throws ItSysException {

        printStartTag("/hospital/personinfo/verify/updateidcard/");
        printHttpPacket(req, null);
        log.info("@Controller 受试者校验身份证号参数为：{}", idcard);
        if (StringUtils.isEmpty(idcard)) {
            throw new ItSysException(GlobalErrorCode.ERR_PARAM_NULL, "受试者校验身份证号不能为空");
        }
        int length = idcard.length();//获取身份证号长度
        if (length != 15 && length != 18) {
            throw new ItSysException(GlobalErrorCode.ERR_PARAM_NULL, "身份证号长度不合法");
        }
        boolean isok = true;
        if (!StringUtils.isEmpty(sid)) {
            HospitalReview bySid = personService.getBySid(sid);
            if (bySid == null) {
                throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG);
            }
            if (idcard.equals(bySid.getIdCard())) {
                isok = false;
            }
        }
        //校验身份证黑名单是否存在
        boolean checkIdentity = personService.checkIdentityBlacklist(idcard, Constans.IDENTITY_BLACKLIST_TYPE);
        if (!checkIdentity) {
            throw new ItSysException(GlobalErrorCode.ERR_PERSON_INSERT_ID_CARD_BLACK_CODE, GlobalErrorCode.ERR_PERSON_INSERT_ID_CARD_BLACK_MSG);
        }

        int age = personService.checkIdCard(idcard, isok);
        Result result = ResultUtil.success(Constans.KEY_SUCCESS_STATUS, Constans.KEY_SUCCESS_MSG, age);
        printEndTag("/hospital/personinfo/verify/updateidcard/");
        return result;
    }

    /**
     * 受试者校验手机号
     *
     * @param req
     * @param resp
     * @param body
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/hospital/personinfo/verify/phone/{phone}", method = RequestMethod.POST)
    public Result checkPhone(HttpServletRequest req, HttpServletResponse resp, @PathVariable String phone) throws ItSysException {

        printStartTag("/hospital/personinfo/verify/phone");
        printHttpPacket(req, null);
        log.info("@Controller 受试者校验手机号参数为：{}", phone);
        if (StringUtils.isEmpty(phone)) {
            throw new ItSysException(GlobalErrorCode.ERR_PERSON_INSERT_PHONE_IS_NULL_CODE, GlobalErrorCode.ERR_PERSON_INSERT_PHONE_IS_NULL_MSG);
        }
        personService.checkPhone(phone);
        Result result = ResultUtil.success(Constans.KEY_SUCCESS_STATUS, Constans.KEY_SUCCESS_MSG);
        printEndTag("/hospital/personinfo/verify/phone");
        return result;
    }

    /**
     * 受试者编辑校验手机号
     *
     * @param req
     * @param resp
     * @param body
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/hospital/personinfo/verify/updatephone/{phone}/sid/{sid}", method = RequestMethod.POST)
    public Result checkUpdatePhone(HttpServletRequest req, HttpServletResponse resp, @PathVariable String phone, @PathVariable String sid) throws ItSysException {

        printStartTag("/hospital/personinfo/verify/updatephone/{phone}/sid");
        printHttpPacket(req, null);
        log.info("@Controller 受试者校验手机号参数为：{}", phone);
        if (StringUtils.isEmpty(phone)) {
            throw new ItSysException(GlobalErrorCode.ERR_PERSON_INSERT_PHONE_IS_NULL_CODE, GlobalErrorCode.ERR_PERSON_INSERT_PHONE_IS_NULL_MSG);
        }
        boolean isok = true;
        if (!StringUtils.isEmpty(sid)) {
            HospitalReview bySid = personService.getBySid(sid);
            if (bySid == null) {
                throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG);
            }
            if (phone.equals(bySid.getPhone())) {
                isok = false;
            }
        }
        if (isok) {
            personService.checkPhone(phone);
        }
        Result result = ResultUtil.success(Constans.KEY_SUCCESS_STATUS, Constans.KEY_SUCCESS_MSG);
        printEndTag("/hospital/personinfo/verify/updatephone/{phone}/sid");
        return result;
    }

    private void syso() {
        System.out.println("==================================================================");
    }


    /**
     * 受试者退出研究
     *
     * @param request
     * @return
     * @throws ItSysException
     */
    @PostMapping(value = "/hospital/person/quit")
    public Result queryBacklogDetailCount(@RequestBody IntestineReason intestineReason, HttpServletRequest request) throws ItSysException {
        log.info("======@Controller queryBacklogDetailCount(受试者退出研究) ----传入参数 = {} ======", JSONUtils.toJson(intestineReason));

        String loginName = CookieUtil.getCookieByLoginName(request);
        DoctorInfo hospitalInfo = userService.getHospitalInfo(loginName);

        //2018-12-12  zongt  修改，获取社区信息和地区信息
        intestineReason.setDepartmentId(hospitalInfo.getCommunityDeptId());
        intestineReason.setDepartmentPId(hospitalInfo.getAreaDeptId());

        //2018-12-12  zongt  修改 判断是否是地区
        if(hospitalInfo.getHospitalType().equals(Constans.DEP_HOSPITAL_TYPE2)){
            String[] sid = intestineReason.getSid();
            List<HospitalReview> hospitalReviews = personService.queryAreaBySidID(sid[0], hospitalInfo.getAreaDeptId());
            if(hospitalReviews.size()!=1){
                return  ResultUtil.error(GlobalErrorCode.RUNTIME_ERROR_CODE,GlobalErrorCode.PARAMETER_ERR_MSG);
            }
            intestineReason.setDepartmentId(hospitalReviews.get(0).getCommunityDeptId());
        }



        Result result = personService.quitResearch(intestineReason);


        List<ItsysUserDto> itsysUserDtos=new ArrayList<>();

        //判断社区还是地区
        if (hospitalInfo.getHospitalType().equals(Constans.DEP_HOSPITAL_TYPE2)) {//地区
            //上级
            List<ItsysUserDto> onitsysUserDtos = colonoscopyService.queryloginNamesByloginName(loginName);

            //下级
            List<ItsysUserDto> downitsysUserDtos = colonoscopyService.querylowerLoginNamesByloginName(loginName);

            //自己那一级
            List<ItsysUserDto> myitsysUserDtos = colonoscopyService.queryMyLoginNamesByloginName(loginName);
            if (onitsysUserDtos.size() > 0) {
                itsysUserDtos.addAll(onitsysUserDtos);
            }
/*            if (downitsysUserDtos.size() > 0) {
                itsysUserDtos.addAll(downitsysUserDtos);
            }*/

            for (ItsysUserDto my:downitsysUserDtos) {
                //判断是不是社区操作者
                List<Role> roles = userService.queryRoleByUserId(my.getId());
                String[] sid = intestineReason.getSid();
                List<HospitalReview> hospitalReviews = personService.queryAreaBySidID(sid[0], hospitalInfo.getAreaDeptId());
                if(roles!=null&&roles.size()>0){//是社区操作者
                    if(my.getLoginname().equals(hospitalReviews.get(0).getCreateUser())&&!loginName.equals(my.getLoginname())){
                        ItsysUserDto noitsysUserDtos=new ItsysUserDto();
                        noitsysUserDtos.setLoginname(my.getLoginname());
                        itsysUserDtos.add(noitsysUserDtos);
                    }
                }else{
                    if(!loginName.equals(my.getLoginname())){
                        DoctorInfo dd = userService.getHospitalInfo(my.getLoginname());
                        if (dd.getCommunityDeptId().equals(hospitalReviews.get(0).getCommunityDeptId())) {
                            ItsysUserDto noitsysUserDtos = new ItsysUserDto();
                            noitsysUserDtos.setLoginname(my.getLoginname());
                            itsysUserDtos.add(noitsysUserDtos);
                        }
                    }
                }
            }

            if (myitsysUserDtos.size() > 0) {
                itsysUserDtos.addAll(myitsysUserDtos);
            }

        } else if (hospitalInfo.getHospitalType().equals(Constans.DEP_HOSPITAL_TYPE1)) {//社区
            //上级
            List<ItsysUserDto> onitsysUserDtos = colonoscopyService.queryloginNamesByloginName(loginName);
            //上级的上级
            List<ItsysUserDto> ononitsysUserDtos = colonoscopyService.queryloginNameRootByloginName(loginName);

            //自己那一级
            List<ItsysUserDto> myitsysUserDtos = colonoscopyService.queryMyLoginNamesByloginName(loginName);
            if (onitsysUserDtos.size() > 0) {
                itsysUserDtos.addAll(onitsysUserDtos);
            }
            if (ononitsysUserDtos.size() > 0) {
                itsysUserDtos.addAll(ononitsysUserDtos);
            }

            //添加消息给自己
            ItsysUserDto itsysUserDto = new ItsysUserDto();
            itsysUserDto.setLoginname(loginName);
            itsysUserDtos.add(itsysUserDto);

            for (ItsysUserDto my:myitsysUserDtos) {
                //判断是不是社区操作者
                List<Role> roles = userService.queryRoleByUserId(my.getId());
                String[] sid = intestineReason.getSid();
                List<HospitalReview> hospitalReviews = personService.queryAreaBySidID(sid[0], hospitalInfo.getAreaDeptId());
                if(roles!=null&&roles.size()>0){//是社区操作者
                    if(my.getLoginname().equals(hospitalReviews.get(0).getCreateUser())&&!loginName.equals(my.getLoginname())){
                        ItsysUserDto noitsysUserDtos=new ItsysUserDto();
                        noitsysUserDtos.setLoginname(my.getLoginname());
                        itsysUserDtos.add(noitsysUserDtos);
                    }
                }else{
                    if(!loginName.equals(my.getLoginname())){
                        DoctorInfo dd = userService.getHospitalInfo(my.getLoginname());
                        if (dd.getCommunityDeptId().equals(hospitalReviews.get(0).getCommunityDeptId())) {
                            ItsysUserDto noitsysUserDtos = new ItsysUserDto();
                            noitsysUserDtos.setLoginname(my.getLoginname());
                            itsysUserDtos.add(noitsysUserDtos);
                        }
                    }
                }
            }
        }




        String meaasge_typpe = Constans.meaasge_typpe2;
        //获取退出原因
        String[] reason = intestineReason.getReason();
        String text = reason[0];
        String sid = intestineReason.getSid()[0];
        String meaasge_text_typpe = Constans.meaasge_text_typpe2;
        String courierNumber = "";
        String remaerk = "";
        List<QuitInResultDto> quitInResultDtos = (List<QuitInResultDto>) result.getData();
        //添加消息中心
        List<HospitalMessageCenterDto> hospitalMessageCenterDtoList = new ArrayList<>();
        for (ItsysUserDto itsysUserDto : itsysUserDtos) {
            HospitalMessageCenterDto hospitalMessageCenterDto = new HospitalMessageCenterDto();
            hospitalMessageCenterDto.setSendUser(loginName);
            String accpetName = itsysUserDto.getLoginname();
            hospitalMessageCenterDto.setAcceptUser(accpetName);
            hospitalMessageCenterDto.setMessageType(Constans.meaasge_typpe2);
            hospitalMessageCenterDto.setSid(sid);
            hospitalMessageCenterDto.setForm_type(Constans.HOSPITAL_VIOLATION_SCHEME);
            hospitalMessageCenterDto.setData_id(Integer.parseInt(quitInResultDtos.get(0).getSchemeId()));
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
            String message = SendMessageCenter.getMessage(loginName, accpetName, meaasge_typpe, text, sid, meaasge_text_typpe, courierNumber, remaerk);
            hospitalMessageCenterDto.setMessageText(message);
            hospitalMessageCenterDto.setId(UUID.randomUUID().toString().replace("-", ""));
            hospitalMessageCenterDtoList.add(hospitalMessageCenterDto);
        }
        hospitalMessageCenterService.save(hospitalMessageCenterDtoList, null, Constans.APPLY_EDIT_STATUS2, Constans.EDIT_STATUS1, Constans.APPROVAL_STATUS1, "", "", true, null);

        log.info("======@Controller queryBacklogDetailCount(受试者退出研究) ----返回数据result = {}======", JSONUtils.toJson(result));
        return result;
    }


    /**
     * 受试者重新入组
     *
     * @param
     * @return
     * @throws ItSysException
     */
    @PostMapping(value = "/hospital/person/reresearch")
    public Result reresearch(@RequestBody IntestineReason intestineReason, HttpServletRequest request) throws ItSysException {
        log.info("======@Controller queryBacklogDetailCount(受试者重新入组) ----传入参数 = {} ======", JSONUtils.toJson(intestineReason));

        //判断如果是地区，发送消息
        String loginName = CookieUtil.getCookieByLoginName(request);
        DoctorInfo hospitalInfo = userService.getHospitalInfo(loginName);

        List<ItsysUserDto> itsysUserDtos = new ArrayList<>();
        //判断社区还是地区
        if (hospitalInfo.getHospitalType().equals(Constans.DEP_HOSPITAL_TYPE2)) {//地区
            //上级
            List<ItsysUserDto> onitsysUserDtos = colonoscopyService.queryloginNamesByloginName(loginName);

            //下级
            List<ItsysUserDto> downitsysUserDtos = colonoscopyService.querylowerLoginNamesByloginName(loginName);

            //自己那一级
            List<ItsysUserDto> myitsysUserDtos = colonoscopyService.queryMyLoginNamesByloginName(loginName);
            if (onitsysUserDtos.size() > 0) {
                itsysUserDtos.addAll(onitsysUserDtos);
            }
/*
            if (downitsysUserDtos.size() > 0) {
                itsysUserDtos.addAll(downitsysUserDtos);
            }
*/



            for (ItsysUserDto my:downitsysUserDtos) {
                //判断是不是社区操作者
                List<Role> roles = userService.queryRoleByUserId(my.getId());
                String[] sid = intestineReason.getSid();
                List<HospitalReview> hospitalReviews = personService.queryAreaBySidID(sid[0], hospitalInfo.getAreaDeptId());
                if(roles!=null&&roles.size()>0){//是社区操作者
                    if(my.getLoginname().equals(hospitalReviews.get(0).getCreateUser())&&!loginName.equals(my.getLoginname())){
                        ItsysUserDto noitsysUserDtos=new ItsysUserDto();
                        noitsysUserDtos.setLoginname(my.getLoginname());
                        itsysUserDtos.add(noitsysUserDtos);
                    }
                }else{
                    if(!loginName.equals(my.getLoginname())){
                        DoctorInfo dd = userService.getHospitalInfo(my.getLoginname());
                        if (dd.getCommunityDeptId().equals(hospitalReviews.get(0).getCommunityDeptId())) {
                            ItsysUserDto noitsysUserDtos = new ItsysUserDto();
                            noitsysUserDtos.setLoginname(my.getLoginname());
                            itsysUserDtos.add(noitsysUserDtos);
                        }
                    }
                }
            }

            if (myitsysUserDtos.size() > 0) {
                itsysUserDtos.addAll(myitsysUserDtos);
            }

        } else if (hospitalInfo.getHospitalType().equals(Constans.DEP_HOSPITAL_TYPE1)) {//社区
            //上级
            List<ItsysUserDto> onitsysUserDtos = colonoscopyService.queryloginNamesByloginName(loginName);
            //上级的上级
            List<ItsysUserDto> ononitsysUserDtos = colonoscopyService.queryloginNameRootByloginName(loginName);

            //自己那一级
            List<ItsysUserDto> myitsysUserDtos = colonoscopyService.queryMyLoginNamesByloginName(loginName);
            if (onitsysUserDtos.size() > 0) {
                itsysUserDtos.addAll(onitsysUserDtos);
            }
            if (ononitsysUserDtos.size() > 0) {
                itsysUserDtos.addAll(ononitsysUserDtos);
            }

            //添加消息给自己
            ItsysUserDto itsysUserDto = new ItsysUserDto();
            itsysUserDto.setLoginname(loginName);
            itsysUserDtos.add(itsysUserDto);

            for (ItsysUserDto my:myitsysUserDtos) {
                //判断是不是社区操作者
                List<Role> roles = userService.queryRoleByUserId(my.getId());
                String[] sid = intestineReason.getSid();
                List<HospitalReview> hospitalReviews = personService.queryAreaBySidID(sid[0], hospitalInfo.getAreaDeptId());
                if(roles!=null&&roles.size()>0){//是社区操作者
                    if(my.getLoginname().equals(hospitalReviews.get(0).getCreateUser())&&!loginName.equals(my.getLoginname())){
                        ItsysUserDto noitsysUserDtos=new ItsysUserDto();
                        noitsysUserDtos.setLoginname(my.getLoginname());
                        itsysUserDtos.add(noitsysUserDtos);
                    }
                }else{
                    if(!loginName.equals(my.getLoginname())){
                        DoctorInfo dd = userService.getHospitalInfo(my.getLoginname());
                        if (dd.getCommunityDeptId().equals(hospitalReviews.get(0).getCommunityDeptId())) {
                            ItsysUserDto noitsysUserDtos = new ItsysUserDto();
                            noitsysUserDtos.setLoginname(my.getLoginname());
                            itsysUserDtos.add(noitsysUserDtos);
                        }
                    }
                }
            }
        }


        String meaasge_typpe = Constans.meaasge_typpe2;
        //获取退出原因
        String text = Constans.meaasge_text_typpe7;
        String sid = intestineReason.getSid()[0];
        String meaasge_text_typpe = Constans.meaasge_text_typpe7;
        String courierNumber = "";
        String remaerk = "";
        //添加消息中心
        List<HospitalMessageCenterDto> hospitalMessageCenterDtoList = new ArrayList<>();
        for (ItsysUserDto itsysUserDto : itsysUserDtos) {
            HospitalMessageCenterDto hospitalMessageCenterDto = new HospitalMessageCenterDto();
            hospitalMessageCenterDto.setSendUser(loginName);
            String accpetName = itsysUserDto.getLoginname();
            hospitalMessageCenterDto.setAcceptUser(accpetName);
            hospitalMessageCenterDto.setMessageType(Constans.meaasge_typpe2);
            hospitalMessageCenterDto.setSid(sid);
            hospitalMessageCenterDto.setForm_type(Constans.HOSPITAL_VIOLATION_SCHEME);
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
            String message = SendMessageCenter.getMessage(loginName, accpetName, meaasge_typpe, text, sid, meaasge_text_typpe, courierNumber, remaerk);
            hospitalMessageCenterDto.setMessageText(message);
            hospitalMessageCenterDto.setId(UUID.randomUUID().toString().replace("-", ""));
            hospitalMessageCenterDtoList.add(hospitalMessageCenterDto);
        }
        if (hospitalMessageCenterDtoList.size() > 0) {
            hospitalMessageCenterService.save(hospitalMessageCenterDtoList, null, Constans.APPLY_EDIT_STATUS2, Constans.EDIT_STATUS1, Constans.APPROVAL_STATUS1, "", "", true, null);
        }


        Result result = personService.reresearch(intestineReason);

        log.info("======@Controller queryBacklogDetailCount(受试者重新入组) ----返回数据result = {}======", JSONUtils.toJson(result));
        return result;
    }

    /**
     * 查看受试者详情
     *
     * @param
     * @return
     * @throws ItSysException
     */
    @PostMapping(value = "/hospital/person/detail/get/{sid}")
    public Result querySubjectDetail(@PathVariable String sid) throws ItSysException {
        log.info("======@Controller querySubjectDetail(受试者详情) ----传入参数 sid = {} ======", sid);

        Result result = personService.queryPersonDetail(sid);

        log.info("======@Controller querySubjectDetail(受试者详情) ----返回数据result = {}======", JSONUtils.toJson(result));
        return result;
    }

    /**
     * @func
     * @desc 根据sid查询该区的受试者信息
     * @author zongt
     * @create 2018/5上午10:13:13
     * @request
     * @response
     **/
    @RequestMapping(value = "/hospital/person/detail/getSid", method = RequestMethod.POST)
    public String querySubjectDetailByAreaDeptId(@RequestBody HospitalReview sid, HttpServletRequest req) throws ItSysException {
        log.info("======@Controller /hospital/person/detail/getSid ----传入参数 sid = {} ======", sid);
        if (StringUtils.isEmpty(sid.getSid())) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
        }
        String loginName = CookieUtil.getCookieByLoginName(req);
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        Integer areaDeptId1 = doctorInfo.getAreaDeptId();
        Integer communityDeptId1 = doctorInfo.getCommunityDeptId();
        List<Role> roles = userService.queryRoleByUserId(doctorInfo.getId());
        String userName = "";
        if (roles != null && roles.size() > 0) {
            userName = loginName;
        }
        List<HospitalReview> hospitalReviews = personService.queryBySidID(sid.getSid(), areaDeptId1, communityDeptId1, userName);
        if (hospitalReviews.size() < 1) {
            log.info("/fit/result/addFit  该区不存在sid");
            return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_STOOL_VIEW_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_STOOL_VIEW_ERROR_MSG));
        }

        for (HospitalReview hospitalReview : hospitalReviews) {
            if (Constans.PERSON_OVERALL_STATUS2.equals(hospitalReview.getOverallStatusCy())) {
                return JSONUtils.toJson(new Response(GlobalErrorCode.PERSON_OVERALL_STATUS2_ERROR_CODE, GlobalErrorCode.PERSON_OVERALL_STATUS2_ERROR_MSG));
            }
        }

        log.info("======@Controller querySubjectDetail(受试者详情) ----返回数据result = {}======", JSONUtils.toJson(hospitalReviews));
        return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, hospitalReviews, null));
    }


    /**
     * 新增危险因素
     *
     * @param request
     * @return
     * @throws ItSysException
     */
    @PostMapping(value = "/hospital/riskfactor/insert")
    public Result addRiskfactor(@RequestBody HospitalRiskFactor hospitalRiskFactor, HttpServletRequest request) throws ItSysException {
        log.info("======@Controller addRiskfactor(新增危险因素) ----传入参数 = {} ======", JSONUtils.toJson(hospitalRiskFactor));
        String loginName = CookieUtil.getCookieByLoginName(request);//cookie获取loginName
        Result result = new Result();
        if (StringUtil.notEmpty(loginName)) {

            result = personService.addHospitalRiskfactor(hospitalRiskFactor, loginName);
        }
        log.info("======@Controller addRiskfactor(新增危险因素) ----返回数据result = {}======", JSONUtils.toJson(result));
        return result;
    }

    /*
     * 修改危险因素
     *
     * @author ${zongt}
     * @since v1.0.0
     */
    @PostMapping(value = "/hospital/riskfactor/updateRiskfactor")
    public String updateRiskfactor(@RequestBody HospitalRiskFactor hospitalRiskFactor, HttpServletRequest request) throws ItSysException {
        log.info("======@Controller addRiskfactor(新增危险因素) ----传入参数 = {} ======", JSONUtils.toJson(hospitalRiskFactor));
        String loginName = CookieUtil.getCookieByLoginName(request);//cookie获取loginName
        Result result = new Result();
        if (hospitalRiskFactor.getId() == null) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
        }
        //根据id查询危险因素表
        List<HospitalRiskFactorDto> riskfactorById = personService.findRiskfactorById(hospitalRiskFactor.getId());
        if (riskfactorById.size() != 1) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
        }
        if (!Constans.EDIT_STATUS2.equals(riskfactorById.get(0).getEditStatus())) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
        }

        HospitalReferenceRecordDto hospitalReferenceRecordDto = new HospitalReferenceRecordDto();
        hospitalReferenceRecordDto.setStatus(Constans.HOSPITAL_REFERENCE_RECORD_STATUS_FINISH);
        hospitalReferenceRecordDto.setDataId(riskfactorById.get(0).getId());
        hospitalReferenceRecordDto.setFormType(Constans.HOSPITAL_INTESTINE_RISK_FACTOR);
        hospitalReferenceRecordDto.setEditPerson(loginName);
        String dataText = JSONUtils.toJson(riskfactorById.get(0));
        hospitalReferenceRecordDto.setOldData(dataText);
        personService.updateHospitalRiskfactor(riskfactorById.get(0), hospitalRiskFactor, hospitalReferenceRecordDto);
        return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, null, null));
    }

    /**
     * 页面渲染时获取筛查现场id
     *
     * @param request
     * @return
     * @throws ItSysException
     */
    @PostMapping(value = "/hospital/screeningtype/get")
    public Result getScreeningType(HttpServletRequest request) throws ItSysException {
        log.info("======@Controller getScreeningType(获取现场id) ----传入参数 = 无 ======");
        Result result = new Result();

        String loginName = CookieUtil.getCookieByLoginName(request);//cookie获取loginName

        String redisJson = redisManager.get(RedisConstant.HOSPITAL_KEY_INFO + loginName);

        DoctorInfo doctorInfo = JSONUtils.toBean(redisJson, DoctorInfo.class);
        if (doctorInfo != null) {
            Integer screeningType = doctorInfo.getScreeningType();
            result = ResultUtil.success(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, screeningType);
        }
        log.info("======@Controller getScreeningType(获取现场id) ----返回数据result = {}======", JSONUtils.toJson(result));
        return result;
    }

    /**
     * sid查询危险因素
     *
     * @param
     * @return
     * @throws ItSysException
     */
    @PostMapping(value = "/hospital/riskfactor/findAll/{sid}")
    public Result findRiskfactor(@PathVariable String sid) throws ItSysException {
        log.info("======@Controller findRiskfactor(查询危险因素) ----传入参数 sid = {} ======", sid);

        Result result = this.personService.findRiskfactorBySid(sid);

        log.info("======@Controller findRiskfactor(查询危险因素) ----返回数据result = {}======", JSONUtils.toJson(result));
        return result;
    }

    /**
     * 受试者短信通知
     *
     * @param sid
     * @return
     * @throws ItSysException
     */
    @PostMapping(value = "/hospital/person/sms/noticesid/{sid}")
    public Result sendHospitalSms(@PathVariable String sid) throws ItSysException {
        log.info("======@Controller sendHospitalSms(受试者发送短信) ----传入参数 sid = {} ======", sid);

        Result result = personService.sendMesage(sid);

        log.info("======@Controller sendHospitalSms(受试者发送短信) ----返回数据result = {}======", JSONUtils.toJson(result));
        return result;
    }

    /**
     * 肠镜短信通知
     *
     * @param sid
     * @return
     * @throws ItSysException
     */
    @PostMapping(value = "/hospital/person/sms/colonoscopy/{sid}")
    public Result sendHospitalColonoscopySms(@PathVariable String sid) throws ItSysException {
        log.info("======@Controller sendHospitalColonoscopySms(肠镜短信通知) ----传入参数 sid = {} ======", sid);

        Result result = personService.sendColonoscopyMesage(sid);

        log.info("======@Controller sendHospitalColonoscopySms(肠镜短信通知) ----返回数据result = {}======", JSONUtils.toJson(result));
        return result;
    }


    @Override
    protected String getClassName() {
        // TODO Auto-generated method stub
        return PersonController.class.getName();
    }

    /**
     * 1.2.7、根据sid查询资格审核表；
     *
     * @param req
     * @param resp
     * @return
     * @throws ItSysException
     */
    @RequestMapping(value = "/hospital/review/get/{sid}", method = RequestMethod.POST)
    public Result getBySid(HttpServletRequest req, HttpServletResponse resp, @PathVariable String sid) throws ItSysException {
        log.info("查询受试者资格审核表信息");
        log.info("传入sid:{}", sid);

//        String loginName=CookieUtil.getCookieByLoginName(req);
        if (StringUtil.isEmpty(sid)) {
            log.error("Request param error,sid={}.", sid);
            throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO, "Request param error.");
        }
        HospitalReview hospitalReview = personService.getBySid(sid);
        Result result = new Result(hospitalReview);
        printEndTag("查询受试者资格审核表信息");
        return result;
    }

    /**
     * 查看受试者个人信息
     *
     * @param sid
     * @return
     * @throws ItSysException
     */
    @PostMapping(value = "/hospital/person/info/get/{sid}")
    public Result queryPersonInfo(@PathVariable String sid, HttpServletRequest req) throws ItSysException {
        log.info("======@Controller queryPersonInfo(个人信息) ----传入参数 sid = {} ======", sid);
        Result result = new Result();
        String loginName = CookieUtil.getCookieByLoginName(req);
        result = personService.queryPersonInfo(sid, loginName);
        log.info("======@Controller queryPersonInfo(个人信息) ----返回数据result = {}======", JSONUtils.toJson(result));
        return result;
    }

    /**
     * @func
     * @desc 校验sid是否存在于地区
     * @author zongt
     * @create 2018/5/16 下午3:30
     * @request
     * @response
     **/
    @RequestMapping(value = "/hospital/person/info/checkSid/{sid}")
    public String addFit(@PathVariable String sid, HttpServletRequest req) throws ItSysException {
        //判断是否是本区sid
        String loginName = CookieUtil.getCookieByLoginName(req);
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        Integer areaDeptId1 = doctorInfo.getAreaDeptId();
        Integer communityDeptId1 = doctorInfo.getCommunityDeptId();
        List<HospitalReview> hospitalReviews = personService.queryAreaBySidID(sid, areaDeptId1);
        if (hospitalReviews.size() < 1) {
            log.info("/hospital/person/info/checkSid/{sid}  该区不存在sid");
            return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_STOOL_VIEW_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_STOOL_VIEW_ERROR_MSG));
        }

        for (HospitalReview hospitalReview : hospitalReviews) {
            if (Constans.PERSON_OVERALL_STATUS2.equals(hospitalReview.getOverallStatusCy())) {
                return JSONUtils.toJson(new Response(GlobalErrorCode.PERSON_OVERALL_STATUS2_ERROR_CODE, GlobalErrorCode.PERSON_OVERALL_STATUS2_ERROR_MSG));
            }
        }
        return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, null, null));
    }


    /**
     * 社区查看退出研究受试者列表
     *
     * @return
     * @throws ItSysException
     */
    @PostMapping(value = "/hospital/person/quitlog/query")
    public Result queryQuitLog(@RequestBody QuitSearchDto quitSearchDto, HttpServletRequest req) throws ItSysException {
        log.info("======@Controller queryQuitLog(查看退出研究受试者列表) ----传入参数 req = {} ======", req);
        String loginName = CookieUtil.getCookieByLoginName(req);
        ItsysDepartment itsysDepartment = userService.getCommunityIdAndAreaIdByLoginName(loginName);
        ListPageUtil listPage = personService.queryQuitLog(quitSearchDto, itsysDepartment, Constans.DEP_HOSPITAL_TYPE1);
        Result result = new Result(listPage.getResultList(), listPage.getPageInfo());
        log.info("======@Controller queryQuitLog(查看退出研究受试者列表) ----返回数据result = {}======", JSONUtils.toJson(result));
        return result;
    }

    /**
     * 地区查看退出研究受试者列表
     *
     * @return
     * @throws ItSysException
     */
    @PostMapping(value = "/hospital/person/quitarealog/query")
    public Result queryAreaQuitLog(@RequestBody QuitSearchDto quitSearchDto, HttpServletRequest req) throws ItSysException {
        log.info("======@Controller queryQuitLog(查看退出研究受试者列表) ----传入参数 req = {} ======", req);
        String loginName = CookieUtil.getCookieByLoginName(req);
        ItsysDepartment itsysDepartment = userService.getCommunityIdAndAreaIdByLoginName(loginName);
        ListPageUtil listPage = personService.queryQuitLog(quitSearchDto, itsysDepartment, Constans.DEP_HOSPITAL_TYPE2);
        Result result = new Result(listPage.getResultList(), listPage.getPageInfo());
        log.info("======@Controller queryQuitLog(查看退出研究受试者列表) ----返回数据result = {}======", JSONUtils.toJson(result));
        return result;
    }


    /**
     * 国家查看退出研究受试者列表
     *
     * @return
     * @throws ItSysException
     */
    @PostMapping(value = "/hospital/person/quitalllog/query")
    public Result queryAllQuitLog(@RequestBody QuitSearchDto quitSearchDto, HttpServletRequest req) throws ItSysException {
        log.info("======@Controller queryQuitLog(查看退出研究受试者列表) ----传入参数 req = {} ======", req);
        String loginName = CookieUtil.getCookieByLoginName(req);
        ItsysDepartment itsysDepartment = userService.getCommunityIdAndAreaIdByLoginName(loginName);
        ListPageUtil listPage = personService.queryQuitLog(quitSearchDto, itsysDepartment, Constans.DEP_HOSPITAL_TYPE3);
        Result result = new Result(listPage.getResultList(), listPage.getPageInfo());
        log.info("======@Controller queryQuitLog(查看退出研究受试者列表) ----返回数据result = {}======", JSONUtils.toJson(result));
        return result;
    }

    /**
     * 社区查看异常管理事件列表
     *
     * @return
     * @throws ItSysException
     */
    @PostMapping(value = "/hospital/person/quitList/query")
    public Result queryQuitList(@RequestBody QuitSearchDto quitSearchDto, HttpServletRequest req) throws ItSysException {
        log.info("======@Controller queryQuitList(查看异常管理事件列表) ----传入参数 req = {} ======", req);
        String loginName = CookieUtil.getCookieByLoginName(req);
        ItsysDepartment itsysDepartment = userService.getCommunityIdAndAreaIdByLoginName(loginName);
        ListPageUtil listPage = personService.queryQuitList(quitSearchDto, itsysDepartment, Constans.DEP_HOSPITAL_TYPE1);
        Result result = new Result(listPage.getResultList(), listPage.getPageInfo());
        log.info("======@Controller queryQuitList(查看异常管理事件列表) ----返回数据result = {}======", JSONUtils.toJson(result));
        return result;
    }

    /**
     * 地区查看异常管理事件列表
     *
     * @return
     * @throws ItSysException
     */
    @PostMapping(value = "/hospital/person/quitareaList/query")
    public Result queryAreaQuitList(@RequestBody QuitSearchDto quitSearchDto, HttpServletRequest req) throws ItSysException {
        log.info("======@Controller queryAreaQuitList(查看异常管理事件列表) ----传入参数 req = {} ======", req);
        String loginName = CookieUtil.getCookieByLoginName(req);
        ItsysDepartment itsysDepartment = userService.getCommunityIdAndAreaIdByLoginName(loginName);
        ListPageUtil listPage = personService.queryQuitList(quitSearchDto, itsysDepartment, Constans.DEP_HOSPITAL_TYPE2);
        Result result = new Result(listPage.getResultList(), listPage.getPageInfo());
        log.info("======@Controller queryAreaQuitList(查看异常管理事件列表) ----返回数据result = {}======", JSONUtils.toJson(result));
        return result;
    }


    /**
     * 国家查看异常管理事件列表
     *
     * @return
     * @throws ItSysException
     */
    @PostMapping(value = "/hospital/person/quitallList/query")
    public Result queryAllQuitList(@RequestBody QuitSearchDto quitSearchDto, HttpServletRequest req) throws ItSysException {
        log.info("======@Controller queryAllQuitList(查看异常管理事件列表) ----传入参数 req = {} ======", req);
        String loginName = CookieUtil.getCookieByLoginName(req);
        ItsysDepartment itsysDepartment = userService.getCommunityIdAndAreaIdByLoginName(loginName);
        ListPageUtil listPage = personService.queryQuitList(quitSearchDto, itsysDepartment, Constans.DEP_HOSPITAL_TYPE3);
        Result result = new Result(listPage.getResultList(), listPage.getPageInfo());
        log.info("======@Controller queryAllQuitList(查看异常管理事件列表) ----返回数据result = {}======", JSONUtils.toJson(result));
        return result;
    }

    /**
     * @func
     * @desc 根据sid查询该地区的受试者信息
     * @author lcy
     * @request sid
     * @response
     **/
    @RequestMapping(value = "/area/hospital/person/detail/getSid", method = RequestMethod.POST)
    public String querySubjectDetailByAreaId(@RequestBody HospitalReview sid, HttpServletRequest req) throws ItSysException {
        log.info("======@Controller querySubjectDetailByAreaId(受试者详情) ----传入参数 sid = {} ======", sid);
        if (StringUtils.isEmpty(sid.getSid())) {
            return JSONUtils.toJson(new Response(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG));
        }
        String loginName = CookieUtil.getCookieByLoginName(req);
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        Integer areaDeptId1 = doctorInfo.getAreaDeptId();
        List<HospitalReview> hospitalReviews = personService.queryBySidIDAndAreaDeptId(sid.getSid(), areaDeptId1);
        if (hospitalReviews.size() < 1) {
            log.info("area/fit/result/addFit  该区不存在sid");
            return JSONUtils.toJson(new Response(GlobalErrorCode.ERR_HOSPITAL_STOOL_VIEW_ERROR_CODE, GlobalErrorCode.ERR_HOSPITAL_STOOL_VIEW_ERROR_MSG));
        }

        for (HospitalReview hospitalReview : hospitalReviews) {
            if (Constans.PERSON_OVERALL_STATUS2.equals(hospitalReview.getOverallStatusCy())) {
                return JSONUtils.toJson(new Response(GlobalErrorCode.PERSON_OVERALL_STATUS2_ERROR_CODE, GlobalErrorCode.PERSON_OVERALL_STATUS2_ERROR_MSG));
            }
        }

        log.info("======@Controller querySubjectDetail(受试者详情) ----返回数据result = {}======", JSONUtils.toJson(hospitalReviews));
        return JSONUtils.toJson(new Response(GlobalErrorCode.NORMAL_RESPONSE, GlobalErrorCode.SUCCESS, hospitalReviews, null));
    }
}

