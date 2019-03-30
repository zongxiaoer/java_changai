/**
 * Project Name:service-base-yl
 * File Name:PersonService.java
 * Package Name:com.yuntongxun.itsys.base.service.impl
 * Date:2018年4月9日下午6:29:22
 * Copyright (c) 2018, ty All Rights Reserved.
 */

package com.yuntongxun.itsys.base.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.yuntongxun.itsys.base.cache.RedisManager;
import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.constans.RedisConstant;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.DateUtil;
import com.yuntongxun.itsys.base.common.util.ExcelData;
import com.yuntongxun.itsys.base.common.util.GlobalErrorCode;
import com.yuntongxun.itsys.base.common.util.JSONUtils;
import com.yuntongxun.itsys.base.dao.BiologSampleDao;
import com.yuntongxun.itsys.base.dao.DepartMentDao;
import com.yuntongxun.itsys.base.dao.PersonExportDao;
import com.yuntongxun.itsys.base.po.Role;
import com.yuntongxun.itsys.base.po.dto.ExeclData;
import com.yuntongxun.itsys.base.po.dto.HospitalReviewExport;
import com.yuntongxun.itsys.base.service.ColonoscopyService;
import com.yuntongxun.itsys.base.service.PersonExportService;
import com.yuntongxun.itsys.base.service.UserService;
import com.yuntongxun.itsys.base.vo.ColonoscopyVo;
import com.yuntongxun.itsys.base.vo.DoctorInfo;
import com.yuntongxun.itsys.base.vo.HospitalBiologicalSampleResultVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Vector;

/**
 * ClassName:PersonService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018年4月9日 下午6:29:22 <br/>
 *
 * @author ty
 * @see
 * @since JDK 1.8
 */
@Service
public class PersonExportServiceImpl implements PersonExportService {

    private final Logger log = LogManager.getLogger(PersonExportServiceImpl.class);

    @Autowired
    private PersonExportDao personExportDao;

    @Autowired
    private RedisManager redisManager;

    @Autowired
    private UserService userService;

    @Autowired
    private BiologSampleDao biologSampleDao;

    @Autowired
    private ColonoscopyService colonoscopyService;

    @Autowired
    private DepartMentDao departMentDao;

    /**
     * 社区导出人员
     * @param person
     * @param loginName
     * @param response
     * @return
     */
    @Override
    public ExcelData commUsersQueryExcel(HospitalReviewExport person, String loginName,HttpServletResponse response) {
        Object[] arr = getRedisKeyByLoginName(loginName);
        Integer communityId = (Integer) arr[0];//社区id
        Integer areaId = (Integer) arr[1];//地区
        Integer userId =(Integer) arr[2];//用户id
        String userName="";
        List<Role> roles = departMentDao.queryRoleByUserId(userId);
        if(roles!=null&&roles.size()>0){
            userName=loginName;
        }
        List<HospitalReviewExport> list = personExportDao.queryExcel(person,"comm",communityId,userName);
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
        titleVec.add("身份证号码");
        titleVec.add("所属区");
        titleVec.add("所属社区");
        titleVec.add("家庭住址(省)");
        titleVec.add("家庭住址(市)");
        titleVec.add("家庭住址(区/县）");
        titleVec.add("家庭住址（镇/乡）");
        titleVec.add("家庭住址（村）");
        titleVec.add("家庭住址(详情)");
        titleVec.add("分组");
        titleVec.add("入组日期");
        titleVec.add("总体状态(1：正常，2：退出，3：肠癌，4：死亡)");
        titleVec.add("资格审核表");
        titleVec.add("危险因素调查表");
        titleVec.add("危险因素得分");
        titleVec.add("肠镜预约时间");
        titleVec.add("肠镜预约状态");
        titleVec.add("肠镜检查日期");
        titleVec.add("肠镜检查状态");
        titleVec.add("肠镜完成状态");
        titleVec.add("告知书发放状态");
        titleVec.add("粪便DNA编码录入状态");
        titleVec.add("粪便DNA编码");
        titleVec.add("粪便DNA结果返回状态");
        titleVec.add("粪便DNA发放状态");
        titleVec.add("粪便DNA结果（1：阴性 2：阳性 3：无效）");
        titleVec.add("FIT编码录入状态");
        titleVec.add("FIT编码");
        titleVec.add("FIT结果录入状态");
        titleVec.add("FIT结果录入时间");
        titleVec.add("FIT结果（1：阴性 2：阳性 3：无效 4：无结果）");
        //为表的每一列设定列宽.
        int[] titleWidthAry = {15,15,15,15,15,15 ,15, 15, 15, 22, 22, 15, 22, 15, 15, 15,15, 15, 15, 15, 15,15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15};
        if (list != null && list.size() > 0) {
            array = new String[list.size()][36];
            for (int i = 0; i < list.size(); i++) {
                HospitalReviewExport review = list.get(i);
                array[i][0] = review.getSid() != null ? review.getSid() : "";
                array[i][1] = review.getName() != null ? review.getName() : "";
                array[i][2] = review.getSex() != null ? review.getSex() : "";
                array[i][3] = review.getAge() != null ? review.getAge() : "";
                array[i][4] = review.getPhone() != null ? review.getPhone() : "";
                array[i][5] = review.getIdCard() != null ? review.getIdCard() : "";
                array[i][6] = review.getCommName()!= null ? review.getCommName() : "";
                array[i][7] = review.getNickName() != null ? review.getNickName() : "";
                array[i][8] = review.getProvince() !=  null ? review.getProvince() : "";
                array[i][9] = review.getCity() !=  null ? review.getCity() : "";
                array[i][10] = review.getArea() !=  null ? review.getArea() : "";
                array[i][11] = review.getTownship() !=  null ? review.getTownship() : "";
                array[i][12] = review.getVillage() !=  null ? review.getVillage() : "";
                array[i][13] = review.getCity_other() !=  null ? review.getCity_other() : "";
                array[i][14] = review.getGroup() != null ? review.getGroup() : "";
                array[i][15] = review.getInGroupDate() != null ? review.getInGroupDate() : "";
                array[i][16] = review.getOverallStatusCy() != null ? review.getOverallStatusCy() : "";
                array[i][17] = review.getQrsState() != null ? review.getQrsState() : "";
                array[i][18] = review.getRiskState() != null ? review.getRiskState() : "";;
                array[i][19] = review.getScore() != null ? review.getScore() : "";
                array[i][20] = review.getReserve_date() != null ? review.getReserve_date() : "";
                array[i][21] = review.getChangjing_reserve_status() != null ? review.getChangjing_reserve_status() : "";
                array[i][22] = review.getResultSurveyDate() != null ? review.getResultSurveyDate() : "";
                array[i][23] = review.getChangjing_examination_status() != null ? review.getChangjing_examination_status() : "";
                array[i][24] = review.getChangjing_finished_status() != null ? review.getChangjing_finished_status() : "";
                array[i][25] = review.getChangjing_notification_issue_status() != null ? review.getChangjing_notification_issue_status() : "";
                array[i][26] = review.getDna_code_entry_status() != null ? review.getDna_code_entry_status() : "";
                array[i][27] = review.getDna_code() != null ? review.getDna_code() : "";
                array[i][28] = review.getDna_other_dna_check_enter_status() != null ? review.getDna_other_dna_check_enter_status() : "";
                array[i][29] = review.getDna_dna_community_inform_status() != null ? review.getDna_dna_community_inform_status() : "";
                array[i][30] = review.getNo_dna_check_result() != null ? review.getNo_dna_check_result() : "";
                array[i][31] = review.getFit_code_entry_status() != null ? review.getFit_code_entry_status() : "";
                array[i][32] = review.getFit_code() != null ? review.getFit_code() : "";
                array[i][33] = review.getFit_insert_status() != null ? review.getFit_insert_status() : "";
                array[i][34] = review.getFit_result_date() != null ? review.getFit_result_date() : "";
                array[i][35] = review.getFit_result() != null ? review.getFit_result() : "";
            }
        }
        //拼装返回数据
        ExcelData excelData = new ExcelData();
        excelData.setHeadStr("社区受试者列表");
        excelData.setTitleVec(titleVec);
        excelData.setTitleWidthAry(titleWidthAry);
        excelData.setBodyAry(array);
        try {
            excelData.setOs(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        excelData.setSheetName("sheet1");
        return excelData;
    }

    @Override
    public ExcelData areaUsersQueryExcel(HospitalReviewExport person, String loginName,HttpServletResponse response) {
        Integer areaId = getAreaIdByLoginName(loginName);//获取地区医院id
        List<HospitalReviewExport> list = personExportDao.queryExcel(person,"area",areaId,null);
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
        titleVec.add("身份证号码");
        titleVec.add("所属区");
        titleVec.add("所属社区");
        titleVec.add("家庭住址(省)");
        titleVec.add("家庭住址(市)");
        titleVec.add("家庭住址(区/县）");
        titleVec.add("家庭住址（镇/乡）");
        titleVec.add("家庭住址（村）");
        titleVec.add("家庭住址(详情)");
        titleVec.add("分组");
        titleVec.add("入组日期");
        titleVec.add("总体状态(1：正常，2：退出，3：肠癌，4：死亡)");
        titleVec.add("资格审核表");
        titleVec.add("危险因素调查表");
        titleVec.add("危险因素得分");
        titleVec.add("肠镜预约时间");
        titleVec.add("肠镜预约状态");
        titleVec.add("肠镜检查日期");
        titleVec.add("肠镜检查状态");
        titleVec.add("肠镜完成状态");
        titleVec.add("肠镜结果录入状态");
        titleVec.add("病理结果录入状态");
        titleVec.add("是否诊断为结直肠癌前病变");
        titleVec.add("是否诊断为结直肠癌");
        titleVec.add("病理是否会诊");
        titleVec.add("告知书录入状态");
        titleVec.add("告知书发放状态");
        titleVec.add("血液样本录入状态");
        titleVec.add("唾液样本录入状态");
        titleVec.add("粪便样本录入状态");
        titleVec.add("粪便DNA编码录入状态");
        titleVec.add("粪便DNA编码");
        titleVec.add("粪便DNA结果返回状态");
        titleVec.add("粪便DNA发放状态");
        titleVec.add("粪便DNA结果（1：阴性 2：阳性 3：无效）");
        titleVec.add("FIT编码录入状态");
        titleVec.add("FIT编码");
        titleVec.add("FIT结果录入状态");
        titleVec.add("FIT结果录入时间");
        titleVec.add("FIT结果（1：阴性 2：阳性 3：无效 4：无结果）");
        //为表的每一列设定列宽.
        int[] titleWidthAry = {15, 15, 15, 15,15,15,15,15,15, 15, 22, 22, 22, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15 ,15, 15, 15, 15,15,15,15,15,15,15,15,15,15,15,15,15};
        if (list != null && list.size() > 0) {
            array = new String[list.size()][45];
            for (int i = 0; i < list.size(); i++) {
                HospitalReviewExport review = list.get(i);
                array[i][0] = review.getSid() != null ? review.getSid() : "";
                array[i][1] = review.getName() != null ? review.getName() : "";
                array[i][2] = review.getSex() != null ? review.getSex() : "";
                array[i][3] = review.getAge() != null ? review.getAge() : "";
                array[i][4] = review.getPhone() != null ? review.getPhone() : "";
                array[i][5] = review.getIdCard() != null ? review.getIdCard() : "";
                array[i][6] = review.getCommName() != null ? review.getCommName() : "";
                array[i][7] = review.getNickName() != null ? review.getNickName() : "";
                array[i][8] = review.getProvince() !=  null ? review.getProvince() : "";
                array[i][9] = review.getCity() !=  null ? review.getCity() : "";
                array[i][10] = review.getArea() !=  null ? review.getArea() : "";
                array[i][11] = review.getTownship() !=  null ? review.getTownship() : "";
                array[i][12] = review.getVillage() !=  null ? review.getVillage() : "";
                array[i][13] = review.getCity_other() !=  null ? review.getCity_other() : "";
                array[i][14] = review.getGroup() != null ? review.getGroup() : "";
                array[i][15] = review.getInGroupDate() != null ? review.getInGroupDate() : "";
                array[i][16] = review.getOverallStatusCy() != null ? review.getOverallStatusCy() : "";
                array[i][17] = review.getQrsState() != null ? review.getQrsState() : "";
                array[i][18] = review.getRiskState() != null ? review.getRiskState() : "";
                array[i][19] = review.getScore() != null ? review.getScore() : "";
                array[i][20] = review.getReserve_date() != null ? review.getReserve_date() : "";
                array[i][21] = review.getChangjing_reserve_status() != null ? review.getChangjing_reserve_status() : "";
                array[i][22] = review.getResultSurveyDate() != null ? review.getResultSurveyDate() : "";
                array[i][23] = review.getChangjing_examination_status() != null ? review.getChangjing_examination_status() : "";
                array[i][24] = review.getChangjing_finished_status() != null ? review.getChangjing_finished_status() : "";
                array[i][25] = review.getChangjing_result_status()!= null ? review.getChangjing_result_status() : "";
                array[i][26] = review.getChangjing_pathology_status() != null ? review.getChangjing_pathology_status() : "";
                array[i][27] = review.getItem2() != null ? review.getItem2() : "";
                array[i][28] = review.getItem3() != null ? review.getItem3() : "";
                array[i][29] = review.getItem1() != null ? review.getItem1() : "";
                array[i][30] = review.getChangjing_notification_entry_status() != null ? review.getChangjing_notification_entry_status() : "";
                array[i][31] = review.getChangjing_notification_issue_status() != null ? review.getChangjing_notification_issue_status() : "";
                array[i][32] = review.getXueye_collect_status() != null ? review.getXueye_collect_status() : "";
                array[i][33] = review.getTuoye_collect_status() != null ? review.getTuoye_collect_status() : "";
                array[i][34] = review.getFenbian_collect_status() != null ? review.getFenbian_collect_status() : "";
                array[i][35] = review.getDna_code_entry_status() != null ? review.getDna_code_entry_status() : "";
                array[i][36] = review.getDna_code() != null ? review.getDna_code() : "";
                array[i][37] = review.getDna_other_dna_check_enter_status() != null ? review.getDna_other_dna_check_enter_status() : "";
                array[i][38] = review.getDna_dna_community_inform_status() != null ? review.getDna_dna_community_inform_status() : "";
                array[i][39] = review.getNo_dna_check_result() != null ? review.getNo_dna_check_result() : "";
                array[i][40] = review.getFit_code_entry_status() != null ? review.getFit_code_entry_status() : "";
                array[i][41] = review.getFit_code() != null ? review.getFit_code() : "";
                array[i][42] = review.getFit_insert_status() != null ? review.getFit_insert_status() : "";
                array[i][43] = review.getFit_result_date() != null ? review.getFit_result_date() : "";
                array[i][44] = review.getFit_result() != null ? review.getFit_result() : "";
            }
        }
        //拼装返回数据
        ExcelData excelData = new ExcelData();
        excelData.setHeadStr("地区受试者列表");
        excelData.setTitleVec(titleVec);
        excelData.setTitleWidthAry(titleWidthAry);
        excelData.setBodyAry(array);
        try {
            excelData.setOs(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        excelData.setSheetName("sheet1");
        return excelData;
    }

    @Override
    public ExcelData nationUsersQueryExcel(HospitalReviewExport person, String loginName,HttpServletResponse response) {
        List<HospitalReviewExport> list = personExportDao.queryExcel(person,"nation",null,null);
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
        titleVec.add("身份证号码");
        titleVec.add("所属地区");
        titleVec.add("所属区");
        titleVec.add("所属社区");
        titleVec.add("家庭住址(省)");
        titleVec.add("家庭住址(市)");
        titleVec.add("家庭住址(区/县）");
        titleVec.add("家庭住址（镇/乡）");
        titleVec.add("家庭住址（村）");
        titleVec.add("家庭住址(详情)");
        titleVec.add("分组");
        titleVec.add("入组日期");
        titleVec.add("总体状态(1：正常，2：退出，3：肠癌，4：死亡)");
        titleVec.add("资格审核表");
        titleVec.add("危险因素调查表");
        titleVec.add("危险因素得分");
        titleVec.add("肠镜预约时间");
        titleVec.add("肠镜预约状态");
        titleVec.add("肠镜检查日期");
        titleVec.add("肠镜检查状态");
        titleVec.add("肠镜完成状态");
        titleVec.add("肠镜结果录入状态");
        titleVec.add("病理结果录入状态");
        //2018-8-30 zongtong

        titleVec.add("是否诊断为结直肠癌前病变");
        titleVec.add("是否诊断为结直肠癌");
        titleVec.add("病理是否会诊");
        titleVec.add("告知书录入状态");
        titleVec.add("告知书发放状态");
        titleVec.add("血液样本录入状态");
        titleVec.add("唾液样本录入状态");
        titleVec.add("粪便样本录入状态");
        titleVec.add("粪便DNA编码录入状态");
        titleVec.add("粪便DNA编码");
        titleVec.add("粪便DNA结果返回状态");
        titleVec.add("粪便DNA结果审核状态");
        titleVec.add("粪便DNA发放状态");
        titleVec.add("粪便DNA结果（1：阴性 2：阳性 3：无效）");
        titleVec.add("FIT编码录入状态");
        titleVec.add("FIT编码");
        titleVec.add("FIT结果录入状态");
        titleVec.add("FIT结果录入时间");
        titleVec.add("FIT结果（1：阴性 2：阳性 3：无效 4：无结果）");
        //为表的每一列设定列宽.
        int[] titleWidthAry = {15, 15, 22,15, 15, 15, 15, 15, 15, 22, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15,15,15,15,15,15,15,15,15,15,15,15,15};
        if (list != null && list.size() > 0) {
            array = new String[list.size()][47];
            for (int i = 0; i < list.size(); i++) {
                HospitalReviewExport review = list.get(i);
                array[i][0] = review.getSid() != null ? review.getSid() : "";
                array[i][1] = review.getName() != null ? review.getName() : "";
                array[i][2] = review.getSex() != null ? review.getSex() : "";
                array[i][3] = review.getAge() != null ? review.getAge() : "";
                array[i][4] = review.getPhone() != null ? review.getPhone() : "";
                array[i][5] = review.getIdCard() != null ? review.getIdCard() : "";
                array[i][6] = review.getAreaName() != null ? review.getAreaName() : "";
                array[i][7] = review.getCommName() != null ? review.getCommName() : "";
                array[i][8] = review.getNickName() != null ? review.getNickName() : "";
                array[i][9] = review.getProvince() !=  null ? review.getProvince() : "";
                array[i][10] = review.getCity() !=  null ? review.getCity() : "";
                array[i][11] = review.getArea() !=  null ? review.getArea() : "";
                array[i][12] = review.getTownship() !=  null ? review.getTownship() : "";
                array[i][13] = review.getVillage() !=  null ? review.getVillage() : "";
                array[i][14] = review.getCity_other() !=  null ? review.getCity_other() : "";
                array[i][15] = review.getGroup() != null ? review.getGroup() : "";
                array[i][16] = review.getInGroupDate() != null ? review.getInGroupDate() : "";
                array[i][17] = review.getOverallStatusCy() != null ? review.getOverallStatusCy() : "";
                array[i][18] = review.getQrsState() != null ? review.getQrsState() : "";
                array[i][19] = review.getRiskState() != null ? review.getRiskState() : "";
                array[i][20] = review.getScore() != null ? review.getScore() : "";
                array[i][21] = review.getReserve_date() != null ? review.getReserve_date() : "";
                array[i][22] = review.getChangjing_reserve_status() != null ? review.getChangjing_reserve_status() : "";
                array[i][23] = review.getResultSurveyDate() != null ? review.getResultSurveyDate() : "";
                array[i][24] = review.getChangjing_examination_status() != null ? review.getChangjing_examination_status() : "";
                array[i][25] = review.getChangjing_finished_status() != null ? review.getChangjing_finished_status() : "";
                array[i][26] = review.getChangjing_result_status()!= null ? review.getChangjing_result_status() : "";
                array[i][27] = review.getChangjing_pathology_status() != null ? review.getChangjing_pathology_status() : "";
                array[i][28] = review.getItem2() != null ? review.getItem2() : "";
                array[i][29] = review.getItem3() != null ? review.getItem3() : "";
                array[i][30] = review.getItem1() != null ? review.getItem1() : "";
                array[i][31] = review.getChangjing_notification_entry_status() != null ? review.getChangjing_notification_entry_status() : "";
                array[i][32] = review.getChangjing_notification_issue_status() != null ? review.getChangjing_notification_issue_status() : "";
                array[i][33] = review.getXueye_collect_status() != null ? review.getXueye_collect_status() : "";
                array[i][34] = review.getTuoye_collect_status() != null ? review.getTuoye_collect_status() : "";
                array[i][35] = review.getFenbian_collect_status() != null ? review.getFenbian_collect_status() : "";
                array[i][36] = review.getDna_code_entry_status() != null ? review.getDna_code_entry_status() : "";
                array[i][37] = review.getDna_code() != null ? review.getDna_code() : "";
                array[i][38] = review.getDna_gj_dna_check_enter_status() != null ? review.getDna_gj_dna_check_enter_status() : "";
                array[i][39] = review.getDna_dna_check_inform_status() != null ? review.getDna_dna_check_inform_status() : "";
                array[i][40] = review.getDna_dna_community_inform_status() != null ? review.getDna_dna_community_inform_status() : "";
                array[i][41] = review.getDna_check_result() != null ? review.getDna_check_result() : "";
                array[i][42] = review.getFit_code_entry_status() != null ? review.getFit_code_entry_status() : "";
                array[i][43] = review.getFit_code() != null ? review.getFit_code() : "";
                array[i][44] = review.getFit_insert_status() != null ? review.getFit_insert_status() : "";
                array[i][45] = review.getFit_result_date() != null ? review.getFit_result_date() : "";
                array[i][46] = review.getFit_result() != null ? review.getFit_result() : "";
            }
        }
        //拼装返回数据
        ExcelData excelData = new ExcelData();
        excelData.setHeadStr("国家受试者列表");
        excelData.setTitleVec(titleVec);
        excelData.setTitleWidthAry(titleWidthAry);
        excelData.setBodyAry(array);
        try {
            excelData.setOs(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        excelData.setSheetName("sheet1");
        return excelData;
    }


    public Object[] getRedisKeyByLoginName(String loginName) {
        if (StringUtils.isEmpty(loginName)) {
            throw new ItSysException(
                    GlobalErrorCode.ERR_PARAM_NULL, "用户名为空");
        }
        //拼接rediskey
        String result = redisManager.get(RedisConstant.HOSPITAL_KEY_INFO + loginName);
        log.info("@Service-person-getRedisKeyByLoginName redis key  参数：key={}.", RedisConstant.HOSPITAL_KEY_INFO + loginName);
        DoctorInfo hospitalInfo;
        if (StringUtils.isEmpty(result)) {
            hospitalInfo = userService.getHospitalInfo(loginName);
        } else {
            try {
                hospitalInfo = JSONUtils.toBean(result, DoctorInfo.class);
            } catch (Exception e) {
                throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO, e.getMessage());
            }
        }
        if (hospitalInfo == null) {
            throw new ItSysException(GlobalErrorCode.ERR_AREA_DEPID_NULL_CODE, GlobalErrorCode.ERR_AREA_DEPID_NULL_MSG);
        }
        log.info("@Service-person-getRedisKeyByLoginName redis value值  参数：value={}.", hospitalInfo.toString());
        Integer communityDeptId = hospitalInfo.getCommunityDeptId();
        Integer areaDeptId = hospitalInfo.getAreaDeptId();
        Integer userId=hospitalInfo.getId();
        if (areaDeptId == null || areaDeptId == null||userId==null) {//判断地区医院id是否为null
            hospitalInfo = userService.getHospitalInfo(loginName);
            if (hospitalInfo == null) {
                throw new ItSysException(GlobalErrorCode.ERR_USER_DEPID_NULL_CODE, GlobalErrorCode.ERR_USER_DEPID_NULL_MSG);
            }
            areaDeptId = hospitalInfo.getAreaDeptId();
            communityDeptId = hospitalInfo.getCommunityDeptId();
            userId=hospitalInfo.getId();
        }
        log.info("@Service-person-getRedisKeyByLoginName 社区医院id和地区医院id的值分别为  参数：communityDeptId={},areaDeptId={}.", communityDeptId, areaDeptId);
        if (areaDeptId == null) {
            throw new ItSysException(GlobalErrorCode.ERR_USER_DEPID_NULL_CODE, GlobalErrorCode.ERR_USER_DEPID_NULL_MSG);
        }
        if (communityDeptId == null) {
            throw new ItSysException(GlobalErrorCode.ERR_COMMUNITY_DEPID_NULL_CODE, GlobalErrorCode.ERR_COMMUNITY_DEPID_NULL_MSG);
        }
        if(userId==null){
            throw new ItSysException(GlobalErrorCode.ERR_COMMUNITY_DEPID_NULL_CODE, GlobalErrorCode.ERR_COMMUNITY_DEPID_NULL_MSG);
        }
        Object[] arr = {communityDeptId, areaDeptId,userId};
        return arr;
    }

    public Integer getAreaIdByLoginName(String loginName) {
        if (StringUtils.isEmpty(loginName)) {
            throw new ItSysException(
                    GlobalErrorCode.ERR_PARAM_NULL, "用户名为空");
        }
        //拼接rediskey
        String result = redisManager.get(RedisConstant.HOSPITAL_KEY_INFO + loginName);
        log.info("@Service-person-getAreaIdByLoginName redis key  参数：key={}.", RedisConstant.HOSPITAL_KEY_INFO + loginName);
        DoctorInfo hospitalInfo;
        if (StringUtils.isEmpty(result)) {
            hospitalInfo = userService.getHospitalInfo(loginName);
        } else {
            try {
                hospitalInfo = JSONUtils.toBean(result, DoctorInfo.class);
            } catch (Exception e) {
                throw new ItSysException(GlobalErrorCode.ERR_BODY_ERRO, e.getMessage());
            }
        }
        if (hospitalInfo == null) {
            throw new ItSysException(GlobalErrorCode.ERR_AREA_DEPID_NULL_CODE, GlobalErrorCode.ERR_AREA_DEPID_NULL_MSG);
        }
        log.info("@Service-person-getAreaIdByLoginName redis value值  参数：value={}.", hospitalInfo.toString());
        Integer areaDeptId = hospitalInfo.getAreaDeptId();
        Integer communityDeptId = hospitalInfo.getCommunityDeptId();
        if (areaDeptId == null) {//判断地区医院id是否为null
            hospitalInfo = userService.getHospitalInfo(loginName);
            if (hospitalInfo == null) {
                throw new ItSysException(GlobalErrorCode.ERR_USER_DEPID_NULL_CODE, GlobalErrorCode.ERR_USER_DEPID_NULL_MSG);
            }
            areaDeptId = hospitalInfo.getAreaDeptId();
            communityDeptId = hospitalInfo.getCommunityDeptId();
        }
        log.info("@Service-person-getAreaIdByLoginName 地区医院id的值为  参数：areaDeptId={}.", areaDeptId);
        if (areaDeptId == null) {
            throw new ItSysException(GlobalErrorCode.ERR_USER_DEPID_NULL_CODE, GlobalErrorCode.ERR_USER_DEPID_NULL_MSG);
        }
        if (communityDeptId != null) {
            throw new ItSysException(GlobalErrorCode.ERR_USER_DEPID_ERRO_CODE, GlobalErrorCode.ERR_USER_DEPID_ERRO_MSG);
        }
        return areaDeptId;
    }

    /**
     * 国家导出肠镜信息
     * @param queryCondition
     * @param loginName
     * @return
     */
    @Override
    public List<ColonoscopyVo> queryFoCountryCJExcel(ColonoscopyVo queryCondition, String loginName) {
        List<ColonoscopyVo> list = personExportDao.queryForChangjingExcel(queryCondition, queryCondition.getCommunityDeptId(), null, null, true);
        return list;
    }

    /**
     * 导出生物样本（唾液/粪便）信息
     * @param loginName
     * @param type
     * @param response
     * @return
     */
    @Override
    public ExcelData queryBiologicalSampleResultExcel(String loginName,String type, HttpServletResponse response) {
        // 查询登录用户信息
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        Integer areaId = doctorInfo.getAreaDeptId();//地区id

        if(doctorInfo.getHospitalType().equals(Constans.DEP_HOSPITAL_TYPE3)){//登录人为国家
            areaId = null;
        }
        String sampleType = "";
        if(type != null && "stool".equals(type)){//粪便
            sampleType = Constans.SAMPLE_TYPE5;
        }
        if(type != null && "saliva".equals(type)){//唾液
            sampleType = Constans.SAMPLE_TYPE4;
        }

        List<HospitalBiologicalSampleResultVo> list = personExportDao.queryForBiologicalSampleExcel(areaId,sampleType);
        //导出逻辑
        String[][] array = new String[][]{};
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/msexcel");
        StringBuilder fileName = new StringBuilder();

        if(areaId == null){
            fileName.append("国家");
        }else{
            fileName.append("地区");
        }

        if(type != null && "stool".equals(type)){//粪便
            fileName.append("粪便样本采集与快递信息列表");
        }
        if(type != null && "saliva".equals(type)){//唾液
            fileName.append("唾液样本采集与快递信息列表");
        }
        try {
            response.setHeader("content-disposition", "attachment; filename=\"" + URLEncoder.encode(fileName.toString(), "UTF-8") + ".xlsx\"");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        ExcelData excelData = new ExcelData();
        if(areaId == null){
            excelData = queryBiologicalSampleByNationResultExcel(list, sampleType);
        }else {
            excelData = queryBiologicalSampleByAreaResultExcel(list, sampleType);
        }

        try {
            excelData.setOs(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        excelData.setSheetName("sheet1");

        return excelData;

    }

    /**
     * 导出地区生物样本
     * @param list 数据列表
     * @param sampleType 导出类型
     * @return
     */
    ExcelData queryBiologicalSampleByAreaResultExcel( List<HospitalBiologicalSampleResultVo> list,String sampleType){
        Vector titleVec = new Vector();
        titleVec.add("序号");
        titleVec.add("SID");
        titleVec.add("姓名");
        titleVec.add("所属区");
        titleVec.add("所属社区");
        titleVec.add("分组");
        titleVec.add("样本ID");
        if(Constans.SAMPLE_TYPE4.equals(sampleType)){
            titleVec.add("唾液采样日期");
            titleVec.add("唾液冷冻盒编号");
            titleVec.add("唾液位置");
            titleVec.add("唾液快递状态");
            titleVec.add("唾液样本备注");
        }else if(Constans.SAMPLE_TYPE5.equals(sampleType)){
            titleVec.add("粪便采样日期");
            titleVec.add("粪便冷冻盒编号");
            titleVec.add("粪便位置");
            titleVec.add("粪便快递状态");
            titleVec.add("粪便样本备注");
        }


        //导出逻辑
        String[][] array = new String[][]{};
        //为表的每一列设定列宽.
        int[] titleWidthAry = {15, 15, 15, 15, 15, 15, 15, 15, 15, 15,15, 15};
        if (list != null && list.size() > 0) {
            array = new String[list.size()][12];
            for (int i = 0; i < list.size(); i++) {
                HospitalBiologicalSampleResultVo review = list.get(i);
                array[i][0] = String.valueOf(i+1);
                array[i][1] = review.getSid() != null ? review.getSid() : "";
                array[i][2] = review.getName() != null ? review.getName() : "";
                array[i][3] = review.getCommName() != null ? review.getCommName() : "";
                array[i][4] = review.getNickName() != null ? review.getNickName() : "";
                if(review.getGroup() != null && !review.getGroup().equals("")){
                    if(review.getGroup().equals("A")){
                        array[i][5] = "A组";
                    }else if(review.getGroup().equals("B")){
                        array[i][5] = "B组";
                    }else if(review.getGroup().equals("C")){
                        array[i][5] = "C组";
                    }else if(review.getGroup().equals("Cg")){
                        array[i][5] = "C组高危";
                    }else if(review.getGroup().equals("Cd")){
                        array[i][5] = "C组低危";
                    }else if(review.getGroup().equals("Cp")){
                        array[i][5] = "C组未评估";
                    }
                }else{
                    array[i][5] = review.getGroup() != null ? review.getGroup() : "";
                }
                String sampleId = review.getSampleId() != null ? review.getSampleId() : "";
                if(sampleId .length() > 7){
                    sampleId = sampleId.substring(0,7);
                }
                array[i][6] = sampleId;
                array[i][7] = review.getCollectStatusDate() != null ? DateUtil.formatDate(review.getCollectStatusDate(), "yyyy-MM-dd") : "";
                array[i][8] = review.getFrozenBoxCode() != null ? review.getFrozenBoxCode() : "";

                String sampleColumn = review.getSampleColumn() != null ? review.getSampleColumn() : "";
                String sampleLine = review.getSampleLine() != null ? review.getSampleLine() : "";
                array[i][9] = sampleColumn+sampleLine;
                if(review.getCourierStatus() != null && !"".equals(review.getCourierStatus().toString())) {
                    if ("1".equals(review.getCourierStatus().toString())) {
                        array[i][10] = "已接收";
                    } else if ("2".equals(review.getCourierStatus().toString())) {
                        array[i][10] = "未寄出";
                    } else if ("3".equals(review.getCourierStatus().toString())) {
                        array[i][10] = "已寄出";
                    }
                }else{
                    array[i][10] = review.getCourierStatus() != null ? review.getCourierStatus().toString() : "";
                }
                array[i][11] = review.getSampleNote() != null ? review.getSampleNote() : "";
            }
        }
        //拼装返回数据
        ExcelData excelData = new ExcelData();
        if(Constans.SAMPLE_TYPE5.equals(sampleType)){
            excelData.setHeadStr("地区粪便样本采集与快递信息列表");
        }
        if(Constans.SAMPLE_TYPE4.equals(sampleType)){
            excelData.setHeadStr("地区唾液样本采集与快递信息列表");
        }
        excelData.setTitleVec(titleVec);
        excelData.setTitleWidthAry(titleWidthAry);
        excelData.setBodyAry(array);

        return excelData;
    }

    /**
     * 导出国家生物样本
     * @param list 数据列表
     * @param sampleType 导出类型
     * @return
     */
    ExcelData queryBiologicalSampleByNationResultExcel( List<HospitalBiologicalSampleResultVo> list,String sampleType){
        Vector titleVec = new Vector();
        titleVec.add("序号");
        titleVec.add("SID");
        titleVec.add("姓名");
        titleVec.add("所属地区");
        titleVec.add("所属区");
        titleVec.add("所属社区");
        titleVec.add("分组");
        titleVec.add("样本ID");
        if(Constans.SAMPLE_TYPE4.equals(sampleType)){
            titleVec.add("唾液采样日期");
            titleVec.add("唾液冷冻盒编号");
            titleVec.add("唾液位置");
            titleVec.add("唾液快递状态");
            titleVec.add("唾液样本备注");
        }else if(Constans.SAMPLE_TYPE5.equals(sampleType)){
            titleVec.add("粪便采样日期");
            titleVec.add("粪便冷冻盒编号");
            titleVec.add("粪便位置");
            titleVec.add("粪便快递状态");
            titleVec.add("粪便样本备注");
        }
        //导出逻辑
        String[][] array = new String[][]{};
        //为表的每一列设定列宽.
        int[] titleWidthAry = {15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15};
        if (list != null && list.size() > 0) {
            array = new String[list.size()][13];
            for (int i = 0; i < list.size(); i++) {
                HospitalBiologicalSampleResultVo review = list.get(i);
                array[i][0] = String.valueOf(i+1);
                array[i][1] = review.getSid() != null ? review.getSid() : "";
                array[i][2] = review.getName() != null ? review.getName() : "";
                array[i][3] = review.getAreaName() != null ? review.getAreaName() : "";
                array[i][4] = review.getCommName() != null ? review.getCommName() : "";
                array[i][5] = review.getNickName() != null ? review.getNickName() : "";
                if(review.getGroup() != null && !review.getGroup().equals("")){
                    if(review.getGroup().equals("A")){
                        array[i][6] = "A组";
                    }else if(review.getGroup().equals("B")){
                        array[i][6] = "B组";
                    }else if(review.getGroup().equals("C")){
                        array[i][6] = "C组";
                    }else if(review.getGroup().equals("Cg")){
                        array[i][6] = "C组高危";
                    }else if(review.getGroup().equals("Cd")){
                        array[i][6] = "C组低危";
                    }else if(review.getGroup().equals("Cp")){
                        array[i][6] = "C组未评估";
                    }
                }else{
                    array[i][6] = review.getGroup() != null ? review.getGroup() : "";
                }
                String sampleId = review.getSampleId() != null ? review.getSampleId() : "";
                if(sampleId .length() > 7){
                    sampleId = sampleId.substring(0,7);
                }
                array[i][7] = sampleId;
                array[i][8] = review.getCollectStatusDate() != null ? DateUtil.formatDate(review.getCollectStatusDate(), "yyyy-MM-dd") : "";
                array[i][9] = review.getFrozenBoxCode() != null ? review.getFrozenBoxCode() : "";

                String sampleColumn = review.getSampleColumn() != null ? review.getSampleColumn() : "";
                String sampleLine = review.getSampleLine() != null ? review.getSampleLine() : "";
                array[i][10] = sampleColumn+sampleLine;
                if(review.getCourierStatus() != null && !"".equals(review.getCourierStatus().toString())){
                    if("1".equals(review.getCourierStatus().toString())){
                        array[i][11] = "已接收";
                    }else if("2".equals(review.getCourierStatus().toString())){
                        array[i][11] = "未寄出";
                    }else if("3".equals(review.getCourierStatus().toString())){
                        array[i][11] = "已寄出";
                    }
                }else{
                    array[i][11] = review.getCourierStatus() != null ? review.getCourierStatus().toString() : "";
                }
                array[i][12] = review.getSampleNote() != null ? review.getSampleNote() : "";
            }
        }
        //拼装返回数据
        ExcelData excelData = new ExcelData();
        if(Constans.SAMPLE_TYPE5.equals(sampleType)){
            excelData.setHeadStr("国家粪便样本采集与快递信息列表");
        }
        if(Constans.SAMPLE_TYPE4.equals(sampleType)){
            excelData.setHeadStr("国家唾液样本采集与快递信息列表");
        }
        excelData.setTitleVec(titleVec);
        excelData.setTitleWidthAry(titleWidthAry);
        excelData.setBodyAry(array);

        return excelData;
    }

    @Override
    public ExcelData bloodSampleQueryExcel(HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultVo, HttpServletResponse response) {
        List<HospitalBiologicalSampleResultVo> list = biologSampleDao.querybloodSampleExcel(hospitalBiologicalSampleResultVo);
        //导出逻辑
        String[][] array = new String[][]{};
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/msexcel");
        String name="国家";
        if(hospitalBiologicalSampleResultVo.getAreaDeptId()!=null){
            name="地区";
        }
        String fileName = name+"血液样本采集与处理信息列表";
        try {
            response.setHeader("content-disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx\"");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        //获得表头
        Vector titleVec = new Vector();
        titleVec.add("序号");
        titleVec.add("SID");
        titleVec.add("姓名");
        if(hospitalBiologicalSampleResultVo.getAreaDeptId()==null){
            titleVec.add("所属地区");
        }
        titleVec.add("所属区");
        titleVec.add("所属社区");
        titleVec.add("分组");
        titleVec.add("样本ID");
        titleVec.add("血液采样日期");
        titleVec.add("血清冷冻盒编号");
        titleVec.add("血清位置");
        titleVec.add("血清快递状态");
        titleVec.add("血浆冷冻盒编号");
        titleVec.add("血浆位置");
        titleVec.add("血浆快递状态");
        titleVec.add("白细胞冷冻盒编号");
        titleVec.add("白细胞位置");
        titleVec.add("白细胞快递状态");
        titleVec.add("血液样本备注");
        int[] titleWidthAry=null;
        //为表的每一列设定列宽.
        if(hospitalBiologicalSampleResultVo.getAreaDeptId()==null){
             titleWidthAry = new int[]{15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15};
        }else{
             titleWidthAry = new int[]{15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15};
        }
        if (list != null && list.size() > 0) {
            array = new String[list.size()][30];
            for (int i = 0; i < list.size(); i++) {
                if(hospitalBiologicalSampleResultVo.getAreaDeptId()==null){
                    HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultVo1 = list.get(i);
                    array[i][0] = String.valueOf(i+1);
                    array[i][1] = hospitalBiologicalSampleResultVo1.getSid() != null ? hospitalBiologicalSampleResultVo1.getSid() : "";
                    array[i][2] = hospitalBiologicalSampleResultVo1.getName() != null ? hospitalBiologicalSampleResultVo1.getName() : "";
                    array[i][3] = hospitalBiologicalSampleResultVo1.getAreaName() != null ? hospitalBiologicalSampleResultVo1.getAreaName() : "";
                    array[i][4] = hospitalBiologicalSampleResultVo1.getCommName() != null ? hospitalBiologicalSampleResultVo1.getCommName() : "";
                    array[i][5] = hospitalBiologicalSampleResultVo1.getNickName() != null ? hospitalBiologicalSampleResultVo1.getNickName() : "";
                    if(hospitalBiologicalSampleResultVo1.getGroup() != null && !hospitalBiologicalSampleResultVo1.getGroup().equals("")){
                        if(hospitalBiologicalSampleResultVo1.getGroup().equals("A")){
                            array[i][6] = "A组";
                        }else if(hospitalBiologicalSampleResultVo1.getGroup().equals("B")){
                            array[i][6] = "B组";
                        }else if(hospitalBiologicalSampleResultVo1.getGroup().equals("C")){
                            array[i][6] = "C组";
                        }else if(hospitalBiologicalSampleResultVo1.getGroup().equals("Cg")){
                            array[i][6] = "C组高危";
                        }else if(hospitalBiologicalSampleResultVo1.getGroup().equals("Cd")){
                            array[i][6] = "C组低危";
                        }else if(hospitalBiologicalSampleResultVo1.getGroup().equals("Cp")){
                            array[i][6] = "C组未评估";
                        }
                    }else{
                        array[i][6] = hospitalBiologicalSampleResultVo1.getGroup() != null ? hospitalBiologicalSampleResultVo1.getGroup() : "";
                    }
                    array[i][7] = hospitalBiologicalSampleResultVo1.getSampleId() != null ? hospitalBiologicalSampleResultVo1.getSampleId() : "";
                    array[i][8] = hospitalBiologicalSampleResultVo1.getCollectStatusDate() != null ? DateUtil.formatDate(hospitalBiologicalSampleResultVo1.getCollectStatusDate(),"yyyy-MM-dd") : "";
                    array[i][9] = (hospitalBiologicalSampleResultVo1.getsFrozenBoxCode() != null && hospitalBiologicalSampleResultVo1.getsFrozenBoxCode().length()==7)? hospitalBiologicalSampleResultVo1.getsFrozenBoxCode() : "";
                    array[i][10] = hospitalBiologicalSampleResultVo1.getsSampleColumn() != null ? hospitalBiologicalSampleResultVo1.getsSampleColumn()+hospitalBiologicalSampleResultVo1.getsSampleLine() : "";
                    String  sCourierStatus="";
                    if(hospitalBiologicalSampleResultVo1.getsCourierStatus() != null){
                        if(Constans.COURIER_STATUS_CODE1.equals(hospitalBiologicalSampleResultVo1.getsCourierStatus())){
                            sCourierStatus="已接收";
                        }else if(Constans.COURIER_STATUS_CODE2.equals(hospitalBiologicalSampleResultVo1.getsCourierStatus())){
                            sCourierStatus="未寄出";
                        }else if(Constans.COURIER_STATUS_CODE3.equals(hospitalBiologicalSampleResultVo1.getsCourierStatus())){
                            sCourierStatus="已寄出";
                        }
                    }
                    array[i][11] = sCourierStatus;
                    array[i][12] = (hospitalBiologicalSampleResultVo1.getpFrozenBoxCode() != null && hospitalBiologicalSampleResultVo1.getpFrozenBoxCode().length()==7) ? hospitalBiologicalSampleResultVo1.getpFrozenBoxCode():"";
                    array[i][13] = hospitalBiologicalSampleResultVo1.getpSampleColumn() != null ? hospitalBiologicalSampleResultVo1.getpSampleColumn()+hospitalBiologicalSampleResultVo1.getpSampleLine() : "";
                    String  pCourierStatus="";
                    if(hospitalBiologicalSampleResultVo1.getpCourierStatus() != null){
                        if(Constans.COURIER_STATUS_CODE1.equals(hospitalBiologicalSampleResultVo1.getpCourierStatus())){
                            pCourierStatus="已接收";
                        }else if(Constans.COURIER_STATUS_CODE2.equals(hospitalBiologicalSampleResultVo1.getpCourierStatus())){
                            pCourierStatus="未寄出";
                        }else if(Constans.COURIER_STATUS_CODE3.equals(hospitalBiologicalSampleResultVo1.getpCourierStatus())){
                            pCourierStatus="已寄出";
                        }
                    }
                    array[i][14] = pCourierStatus;
                    array[i][15] = (hospitalBiologicalSampleResultVo1.getwFrozenBoxCode()!= null && hospitalBiologicalSampleResultVo1.getwFrozenBoxCode().length()==7 )? hospitalBiologicalSampleResultVo1.getwFrozenBoxCode() : "";
                    array[i][16] = hospitalBiologicalSampleResultVo1.getwSampleColumn() != null ? hospitalBiologicalSampleResultVo1.getwSampleColumn()+hospitalBiologicalSampleResultVo1.getwSampleLine() : "";
                    String  wCourierStatus="";
                    if(hospitalBiologicalSampleResultVo1.getwCourierStatus() != null){
                        if(Constans.COURIER_STATUS_CODE1.equals(hospitalBiologicalSampleResultVo1.getwCourierStatus())){
                            wCourierStatus="已接收";
                        }else if(Constans.COURIER_STATUS_CODE2.equals(hospitalBiologicalSampleResultVo1.getwCourierStatus())){
                            wCourierStatus="未寄出";
                        }else if(Constans.COURIER_STATUS_CODE3.equals(hospitalBiologicalSampleResultVo1.getwCourierStatus())){
                            wCourierStatus="已寄出";
                        }
                    }
                    array[i][17] = wCourierStatus;
                    array[i][18] = hospitalBiologicalSampleResultVo1.getSampleNote() != null ? hospitalBiologicalSampleResultVo1.getSampleNote() : "";
                }else{
                    HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultVo1 = list.get(i);
                    array[i][0] = String.valueOf(i+1);
                    array[i][1] = hospitalBiologicalSampleResultVo1.getSid() != null ? hospitalBiologicalSampleResultVo1.getSid() : "";
                    array[i][2] = hospitalBiologicalSampleResultVo1.getName() != null ? hospitalBiologicalSampleResultVo1.getName() : "";
                    array[i][3] = hospitalBiologicalSampleResultVo1.getCommName() != null ? hospitalBiologicalSampleResultVo1.getCommName() : "";
                    array[i][4] = hospitalBiologicalSampleResultVo1.getNickName() != null ? hospitalBiologicalSampleResultVo1.getNickName() : "";
                    if(hospitalBiologicalSampleResultVo1.getGroup() != null && !hospitalBiologicalSampleResultVo1.getGroup().equals("")){
                        if(hospitalBiologicalSampleResultVo1.getGroup().equals("A")){
                            array[i][5] = "A组";
                        }else if(hospitalBiologicalSampleResultVo1.getGroup().equals("B")){
                            array[i][5] = "B组";
                        }else if(hospitalBiologicalSampleResultVo1.getGroup().equals("C")){
                            array[i][5] = "C组";
                        }else if(hospitalBiologicalSampleResultVo1.getGroup().equals("Cg")){
                            array[i][5] = "C组高危";
                        }else if(hospitalBiologicalSampleResultVo1.getGroup().equals("Cd")){
                            array[i][5] = "C组低危";
                        }else if(hospitalBiologicalSampleResultVo1.getGroup().equals("Cp")){
                            array[i][5] = "C组未评估";
                        }
                    }else{
                        array[i][5] = hospitalBiologicalSampleResultVo1.getGroup() != null ? hospitalBiologicalSampleResultVo1.getGroup() : "";
                    }
                    array[i][6] = hospitalBiologicalSampleResultVo1.getSampleId() != null ? hospitalBiologicalSampleResultVo1.getSampleId() : "";
                    array[i][7] = hospitalBiologicalSampleResultVo1.getCollectStatusDate() != null ? DateUtil.formatDate(hospitalBiologicalSampleResultVo1.getCollectStatusDate(),"yyyy-MM-dd") : "";
                    array[i][8] = (hospitalBiologicalSampleResultVo1.getsFrozenBoxCode() != null && hospitalBiologicalSampleResultVo1.getsFrozenBoxCode().length()==7)? hospitalBiologicalSampleResultVo1.getsFrozenBoxCode() : "";
                    array[i][9] = hospitalBiologicalSampleResultVo1.getsSampleColumn() != null ? hospitalBiologicalSampleResultVo1.getsSampleColumn()+hospitalBiologicalSampleResultVo1.getsSampleLine() : "";
                    String  sCourierStatus="";
                    if(hospitalBiologicalSampleResultVo1.getsCourierStatus() != null){
                        if(Constans.COURIER_STATUS_CODE1.equals(hospitalBiologicalSampleResultVo1.getsCourierStatus())){
                            sCourierStatus="已接受";
                        }else if(Constans.COURIER_STATUS_CODE2.equals(hospitalBiologicalSampleResultVo1.getsCourierStatus())){
                            sCourierStatus="未寄出";
                        }else if(Constans.COURIER_STATUS_CODE3.equals(hospitalBiologicalSampleResultVo1.getsCourierStatus())){
                            sCourierStatus="已寄出";
                        }
                    }
                    array[i][10] = sCourierStatus;
                    array[i][11] = (hospitalBiologicalSampleResultVo1.getpFrozenBoxCode() != null && hospitalBiologicalSampleResultVo1.getpFrozenBoxCode().length()==7) ? hospitalBiologicalSampleResultVo1.getpFrozenBoxCode():"";
                    array[i][12] = hospitalBiologicalSampleResultVo1.getpSampleColumn() != null ? hospitalBiologicalSampleResultVo1.getpSampleColumn()+hospitalBiologicalSampleResultVo1.getpSampleLine() : "";
                    String  pCourierStatus="";
                    if(hospitalBiologicalSampleResultVo1.getpCourierStatus() != null){
                        if(Constans.COURIER_STATUS_CODE1.equals(hospitalBiologicalSampleResultVo1.getpCourierStatus())){
                            pCourierStatus="已接受";
                        }else if(Constans.COURIER_STATUS_CODE2.equals(hospitalBiologicalSampleResultVo1.getpCourierStatus())){
                            pCourierStatus="未寄出";
                        }else if(Constans.COURIER_STATUS_CODE3.equals(hospitalBiologicalSampleResultVo1.getpCourierStatus())){
                            pCourierStatus="已寄出";
                        }
                    }
                    array[i][13] = pCourierStatus;
                    array[i][14] = (hospitalBiologicalSampleResultVo1.getwFrozenBoxCode()!= null && hospitalBiologicalSampleResultVo1.getwFrozenBoxCode().length()==7 )? hospitalBiologicalSampleResultVo1.getwFrozenBoxCode() : "";
                    array[i][15] = hospitalBiologicalSampleResultVo1.getwSampleColumn() != null ? hospitalBiologicalSampleResultVo1.getwSampleColumn()+hospitalBiologicalSampleResultVo1.getwSampleLine() : "";
                    String  wCourierStatus="";
                    if(hospitalBiologicalSampleResultVo1.getwCourierStatus() != null){
                        if(Constans.COURIER_STATUS_CODE1.equals(hospitalBiologicalSampleResultVo1.getwCourierStatus())){
                            wCourierStatus="已接受";
                        }else if(Constans.COURIER_STATUS_CODE2.equals(hospitalBiologicalSampleResultVo1.getwCourierStatus())){
                            wCourierStatus="未寄出";
                        }else if(Constans.COURIER_STATUS_CODE3.equals(hospitalBiologicalSampleResultVo1.getwCourierStatus())){
                            wCourierStatus="已寄出";
                        }
                    }
                    array[i][16] = wCourierStatus;
                    array[i][17] = hospitalBiologicalSampleResultVo1.getSampleNote() != null ? hospitalBiologicalSampleResultVo1.getSampleNote() : "";
                }
            }
        }
        //拼装返回数据
        ExcelData excelData = new ExcelData();
        excelData.setHeadStr("血液样本采集与处理信息列表");
        excelData.setTitleVec(titleVec);
        excelData.setTitleWidthAry(titleWidthAry);
        excelData.setBodyAry(array);
        try {
            excelData.setOs(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        excelData.setSheetName("sheet1");
        return excelData;
    }

    @Override
    public ExcelData stollbloodSampleQueryExcel(ExeclData execlData, HttpServletResponse response) {
        List<HospitalBiologicalSampleResultVo> list = biologSampleDao.stollbloodSampleQueryExcel(execlData);
        //导出逻辑
        String[][] array = new String[][]{};
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/msexcel");
        String fileName = "血液样本采集与处理信息列表";
        try {
            response.setHeader("content-disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx\"");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        //获得表头
        Vector titleVec = new Vector();
        titleVec.add("序号");
        titleVec.add("SID");
        titleVec.add("姓名");
        titleVec.add("所属地区");
        titleVec.add("所属区");
        titleVec.add("所属社区");
        titleVec.add("分组");
        titleVec.add("样本ID");
        titleVec.add("血液采样日期");
        titleVec.add("血清冷冻盒编号");
        titleVec.add("血清位置");
        titleVec.add("血清快递状态");
        titleVec.add("血浆冷冻盒编号");
        titleVec.add("血浆位置");
        titleVec.add("血浆快递状态");
        titleVec.add("白细胞冷冻盒编号");
        titleVec.add("白细胞位置");
        titleVec.add("白细胞快递状态");
        titleVec.add("血液样本备注");
        int[] titleWidthAry=null;
        //为表的每一列设定列宽.
        titleWidthAry = new int[]{15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15};
        if (list != null && list.size() > 0) {
            array = new String[list.size()][30];
            for (int i = 0; i < list.size(); i++) {
                    HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultVo1 = list.get(i);
                    array[i][0] = String.valueOf(i+1);
                    array[i][1] = hospitalBiologicalSampleResultVo1.getSid() != null ? hospitalBiologicalSampleResultVo1.getSid() : "";
                    array[i][2] = hospitalBiologicalSampleResultVo1.getName() != null ? hospitalBiologicalSampleResultVo1.getName() : "";
                    array[i][3] = hospitalBiologicalSampleResultVo1.getCommName() != null ? hospitalBiologicalSampleResultVo1.getCommName() : "";
                    array[i][4] = hospitalBiologicalSampleResultVo1.getNickName() != null ? hospitalBiologicalSampleResultVo1.getNickName() : "";
                    if(hospitalBiologicalSampleResultVo1.getGroup() != null && !hospitalBiologicalSampleResultVo1.getGroup().equals("")){
                        if(hospitalBiologicalSampleResultVo1.getGroup().equals("A")){
                            array[i][5] = "A组";
                        }else if(hospitalBiologicalSampleResultVo1.getGroup().equals("B")){
                            array[i][5] = "B组";
                        }else if(hospitalBiologicalSampleResultVo1.getGroup().equals("C")){
                            array[i][5] = "C组";
                        }else if(hospitalBiologicalSampleResultVo1.getGroup().equals("Cg")){
                            array[i][5] = "C组高危";
                        }else if(hospitalBiologicalSampleResultVo1.getGroup().equals("Cd")){
                            array[i][5] = "C组低危";
                        }else if(hospitalBiologicalSampleResultVo1.getGroup().equals("Cp")){
                            array[i][5] = "C组未评估";
                        }
                    }else{
                        array[i][5] = hospitalBiologicalSampleResultVo1.getGroup() != null ? hospitalBiologicalSampleResultVo1.getGroup() : "";
                    }
                    array[i][6] = hospitalBiologicalSampleResultVo1.getSampleId() != null ? hospitalBiologicalSampleResultVo1.getSampleId() : "";
                    array[i][7] = hospitalBiologicalSampleResultVo1.getCollectStatusDate() != null ? DateUtil.formatDate(hospitalBiologicalSampleResultVo1.getCollectStatusDate(),"yyyy-MM-dd") : "";
                    array[i][8] = (hospitalBiologicalSampleResultVo1.getsFrozenBoxCode() != null && hospitalBiologicalSampleResultVo1.getsFrozenBoxCode().length()==7)? hospitalBiologicalSampleResultVo1.getsFrozenBoxCode() : "";
                    array[i][9] = hospitalBiologicalSampleResultVo1.getsSampleColumn() != null ? hospitalBiologicalSampleResultVo1.getsSampleColumn()+hospitalBiologicalSampleResultVo1.getsSampleLine() : "";
                    String  sCourierStatus="";
                    if(hospitalBiologicalSampleResultVo1.getsCourierStatus() != null){
                        if(Constans.COURIER_STATUS_CODE1.equals(hospitalBiologicalSampleResultVo1.getsCourierStatus())){
                            sCourierStatus="已接收";
                        }else if(Constans.COURIER_STATUS_CODE2.equals(hospitalBiologicalSampleResultVo1.getsCourierStatus())){
                            sCourierStatus="未寄出";
                        }else if(Constans.COURIER_STATUS_CODE3.equals(hospitalBiologicalSampleResultVo1.getsCourierStatus())){
                            sCourierStatus="已寄出";
                        }
                    }
                    array[i][10] = sCourierStatus;
                    array[i][11] = (hospitalBiologicalSampleResultVo1.getpFrozenBoxCode() != null && hospitalBiologicalSampleResultVo1.getpFrozenBoxCode().length()==7) ? hospitalBiologicalSampleResultVo1.getpFrozenBoxCode():"";
                    array[i][12] = hospitalBiologicalSampleResultVo1.getpSampleColumn() != null ? hospitalBiologicalSampleResultVo1.getpSampleColumn()+hospitalBiologicalSampleResultVo1.getpSampleLine() : "";
                    String  pCourierStatus="";
                    if(hospitalBiologicalSampleResultVo1.getpCourierStatus() != null){
                        if(Constans.COURIER_STATUS_CODE1.equals(hospitalBiologicalSampleResultVo1.getpCourierStatus())){
                            pCourierStatus="已接收";
                        }else if(Constans.COURIER_STATUS_CODE2.equals(hospitalBiologicalSampleResultVo1.getpCourierStatus())){
                            pCourierStatus="未寄出";
                        }else if(Constans.COURIER_STATUS_CODE3.equals(hospitalBiologicalSampleResultVo1.getpCourierStatus())){
                            pCourierStatus="已寄出";
                        }
                    }
                    array[i][13] = pCourierStatus;
                    array[i][14] = (hospitalBiologicalSampleResultVo1.getwFrozenBoxCode()!= null && hospitalBiologicalSampleResultVo1.getwFrozenBoxCode().length()==7 )? hospitalBiologicalSampleResultVo1.getwFrozenBoxCode() : "";
                    array[i][15] = hospitalBiologicalSampleResultVo1.getwSampleColumn() != null ? hospitalBiologicalSampleResultVo1.getwSampleColumn()+hospitalBiologicalSampleResultVo1.getwSampleLine() : "";
                    String  wCourierStatus="";
                    if(hospitalBiologicalSampleResultVo1.getwCourierStatus() != null){
                        if(Constans.COURIER_STATUS_CODE1.equals(hospitalBiologicalSampleResultVo1.getwCourierStatus())){
                            wCourierStatus="已接收";
                        }else if(Constans.COURIER_STATUS_CODE2.equals(hospitalBiologicalSampleResultVo1.getwCourierStatus())){
                            wCourierStatus="未寄出";
                        }else if(Constans.COURIER_STATUS_CODE3.equals(hospitalBiologicalSampleResultVo1.getwCourierStatus())){
                            wCourierStatus="已寄出";
                        }
                    }
                    array[i][16] = wCourierStatus;
                    array[i][17] = hospitalBiologicalSampleResultVo1.getSampleNote() != null ? hospitalBiologicalSampleResultVo1.getSampleNote() : "";
            }
        }
        //拼装返回数据
        ExcelData excelData = new ExcelData();
        excelData.setHeadStr("血液样本采集与处理信息列表");
        excelData.setTitleVec(titleVec);
        excelData.setTitleWidthAry(titleWidthAry);
        excelData.setBodyAry(array);
        try {
            excelData.setOs(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        excelData.setSheetName("sheet1");
        return excelData;
    }

    @Override
    public ExcelData stollnationUsersQueryExcel(ExeclData execlData, HttpServletResponse response) {
        List<HospitalReviewExport> list = personExportDao.stollnationUsersQueryExcel(execlData);
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
        titleVec.add("身份证号码");
        titleVec.add("所属地区");
        titleVec.add("所属区");
        titleVec.add("所属社区");
        titleVec.add("家庭住址(省)");
        titleVec.add("家庭住址(市)");
        titleVec.add("家庭住址(区/县）");
        titleVec.add("家庭住址（镇/乡）");
        titleVec.add("家庭住址（村）");
        titleVec.add("家庭住址(详情)");
        titleVec.add("分组");
        titleVec.add("入组日期");
        titleVec.add("总体状态(1：正常，2：退出，3：肠癌，4：死亡)");
        titleVec.add("资格审核表");
        titleVec.add("危险因素调查表");
        titleVec.add("危险因素得分");
        titleVec.add("肠镜预约时间");
        titleVec.add("肠镜预约状态");
        titleVec.add("肠镜检查日期");
        titleVec.add("肠镜检查状态");
        titleVec.add("肠镜完成状态");
        titleVec.add("肠镜结果录入状态");
        titleVec.add("病理结果录入状态");
        //2018-8-30 zongtong

        titleVec.add("是否诊断为结直肠癌前病变");
        titleVec.add("是否诊断为结直肠癌");
        titleVec.add("病理是否会诊");
        titleVec.add("告知书录入状态");
        titleVec.add("告知书发放状态");
        titleVec.add("血液样本录入状态");
        titleVec.add("唾液样本录入状态");
        titleVec.add("粪便样本录入状态");
        titleVec.add("粪便DNA编码录入状态");
        titleVec.add("粪便DNA编码");
        titleVec.add("粪便DNA结果返回状态");
        titleVec.add("粪便DNA结果审核状态");
        titleVec.add("粪便DNA发放状态");
        titleVec.add("粪便DNA结果（1：阴性 2：阳性 3：无效）");
        titleVec.add("FIT编码录入状态");
        titleVec.add("FIT编码");
        titleVec.add("FIT结果录入状态");
        titleVec.add("FIT结果录入时间");
        titleVec.add("FIT结果（1：阴性 2：阳性 3：无效 4：无结果）");
        //为表的每一列设定列宽.
        int[] titleWidthAry = {15, 15, 22,22,22,22,22,22,15, 15, 15, 15, 15, 15, 22, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15,15,15,15,15,15,15,15,15,15,15,15,15};
        if (list != null && list.size() > 0) {
            array = new String[list.size()][47];
            for (int i = 0; i < list.size(); i++) {
                HospitalReviewExport review = list.get(i);
                array[i][0] = review.getSid() != null ? review.getSid() : "";
                array[i][1] = review.getName() != null ? review.getName() : "";
                array[i][2] = review.getSex() != null ? review.getSex() : "";
                array[i][3] = review.getAge() != null ? review.getAge() : "";
                array[i][4] = review.getPhone() != null ? review.getPhone() : "";
                array[i][5] = review.getIdCard() != null ? review.getIdCard() : "";
                array[i][6] = review.getAreaName() != null ? review.getAreaName() : "";
                array[i][7] = review.getCommName() != null ? review.getCommName() : "";
                array[i][8] = review.getNickName() != null ? review.getNickName() : "";
                array[i][9] = review.getProvince() !=  null ? review.getProvince() : "";
                array[i][10] = review.getCity() !=  null ? review.getCity() : "";
                array[i][11] = review.getArea() !=  null ? review.getArea() : "";
                array[i][12] = review.getTownship() !=  null ? review.getTownship() : "";
                array[i][13] = review.getVillage() !=  null ? review.getVillage() : "";
                array[i][14] = review.getCity_other() !=  null ? review.getCity_other() : "";
                array[i][15] = review.getGroup() != null ? review.getGroup() : "";
                array[i][16] = review.getInGroupDate() != null ? review.getInGroupDate() : "";
                array[i][17] = review.getOverallStatusCy() != null ? review.getOverallStatusCy() : "";
                array[i][18] = review.getQrsState() != null ? review.getQrsState() : "";
                array[i][19] = review.getRiskState() != null ? review.getRiskState() : "";
                array[i][20] = review.getScore() != null ? review.getScore() : "";
                array[i][21] = review.getReserve_date() != null ? review.getReserve_date() : "";
                array[i][22] = review.getChangjing_reserve_status() != null ? review.getChangjing_reserve_status() : "";
                array[i][23] = review.getResultSurveyDate() != null ? review.getResultSurveyDate() : "";
                array[i][24] = review.getChangjing_examination_status() != null ? review.getChangjing_examination_status() : "";
                array[i][25] = review.getChangjing_finished_status() != null ? review.getChangjing_finished_status() : "";
                array[i][26] = review.getChangjing_result_status()!= null ? review.getChangjing_result_status() : "";
                array[i][27] = review.getChangjing_pathology_status() != null ? review.getChangjing_pathology_status() : "";
                array[i][28] = review.getItem2() != null ? review.getItem2() : "";
                array[i][29] = review.getItem3() != null ? review.getItem3() : "";
                array[i][30] = review.getItem1() != null ? review.getItem1() : "";
                array[i][31] = review.getChangjing_notification_entry_status() != null ? review.getChangjing_notification_entry_status() : "";
                array[i][32] = review.getChangjing_notification_issue_status() != null ? review.getChangjing_notification_issue_status() : "";
                array[i][33] = review.getXueye_collect_status() != null ? review.getXueye_collect_status() : "";
                array[i][34] = review.getTuoye_collect_status() != null ? review.getTuoye_collect_status() : "";
                array[i][35] = review.getFenbian_collect_status() != null ? review.getFenbian_collect_status() : "";
                array[i][36] = review.getDna_code_entry_status() != null ? review.getDna_code_entry_status() : "";
                array[i][37] = review.getDna_code() != null ? review.getDna_code() : "";
                array[i][38] = review.getDna_gj_dna_check_enter_status() != null ? review.getDna_gj_dna_check_enter_status() : "";
                array[i][39] = review.getDna_dna_check_inform_status() != null ? review.getDna_dna_check_inform_status() : "";
                array[i][40] = review.getDna_dna_community_inform_status() != null ? review.getDna_dna_community_inform_status() : "";
                array[i][41] = review.getDna_check_result() != null ? review.getDna_check_result() : "";
                array[i][42] = review.getFit_code_entry_status() != null ? review.getFit_code_entry_status() : "";
                array[i][43] = review.getFit_code() != null ? review.getFit_code() : "";
                array[i][44] = review.getFit_insert_status() != null ? review.getFit_insert_status() : "";
                array[i][45] = review.getFit_result_date() != null ? review.getFit_result_date() : "";
                array[i][46] = review.getFit_result() != null ? review.getFit_result() : "";
            }
        }
        //拼装返回数据
        ExcelData excelData = new ExcelData();
        excelData.setHeadStr("国家受试者列表");
        excelData.setTitleVec(titleVec);
        excelData.setTitleWidthAry(titleWidthAry);
        excelData.setBodyAry(array);
        try {
            excelData.setOs(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        excelData.setSheetName("sheet1");
        return excelData;
    }

    @Override
    public List<ColonoscopyVo> queryFoStollCountryCJExcel(ExeclData execlData) {
        return personExportDao.queryFoStollCountryCJExcel(execlData);
    }

}

