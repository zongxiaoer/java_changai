/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: ExcelServiceImpl
 * Author:   lcy
 * Date:     2018/8/23 19:28
 * History:
 * <author>          <time>                <version>
 *     lcy         2018/8/23 19:28           v1.0.0
 */
package com.yuntongxun.itsys.base.service.impl;

import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.util.DateUtil;
import com.yuntongxun.itsys.base.common.util.ExcelData;
import com.yuntongxun.itsys.base.dao.ExcelDao;
import com.yuntongxun.itsys.base.po.*;
import com.yuntongxun.itsys.base.po.dto.ExeclData;
import com.yuntongxun.itsys.base.service.ExcelService;
import com.yuntongxun.itsys.base.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * 导出Excel实现类
 *
 * @author lcy
 * @create 2018/8/23
 * @since v1.0.0
 */
@Service
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    private ExcelDao excelDao;

    @Override
    public List<StoolDna> getDnaexcel(ExeclData execlData) {
        return excelDao.getDnaexcel(execlData);
    }

    @Override
    public List<ColonoscopyVo> getNotificationRecord(ExeclData execlData) {
        return excelDao.getNotificationRecord(execlData);
    }

    @Override
    public List<HospitalReview> stoolReviewQueryExcel(ExeclData execlData) {
        return excelDao.stoolReviewQueryExcel(execlData);
    }

    @Override
    public List<ColonoscopyNotificationVo> stoolNotificationQueryExcel(ExeclData execlData) {
        return excelDao.stoolNotificationQueryExcel(execlData);
    }

    @Override
    public List<HospitalRiskFactorPO> getReviewRiskFactorDetails(ExeclData execlData) {
        return excelDao.getReviewRiskFactorDetails(execlData);
    }

    @Override
    public List<ViolationSchemePO> getReviewViolationSchemeDetails(ExeclData execlData) {
        return excelDao.getReviewViolationSchemeDetails(execlData);
    }

    @Override
    public List<PathologyExcelVO> getPathologyQueryExcel(ExeclData execlData) {
        return excelDao.getPathologyQueryExcel(execlData);
    }

    @Override
    public List<HospitalBiologicalSampleResultVo> queryBySampleExcel(ExeclData execlData) {
        return excelDao.queryBySampleExcel(execlData);
    }

    @Override
    public List<HospitalColonoscopyResultVo> stoolColonoscopyResultQueryExcel(ExeclData execlData) {
        return excelDao.stoolColonoscopyResultQueryExcel(execlData);
    }

    @Override
    public ExcelData bloodSampleAndExpressQueryExcel(HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultVo, HttpServletResponse response, ExeclData execlData) {
        List<HospitalBiologicalSampleResultVo> list = excelDao.querybloodSampleExcel(hospitalBiologicalSampleResultVo,execlData);
        //导出逻辑
        String[][] array = new String[][]{};
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/msexcel");
        String name = "国家";
        if (hospitalBiologicalSampleResultVo.getAreaDeptId() != null) {
            name = "地区";
        }
        String fileName = name + "血液样本采集与处理信息列表";
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
        if (hospitalBiologicalSampleResultVo.getAreaDeptId() == null) {
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
        int[] titleWidthAry = null;
        //为表的每一列设定列宽.
        if (hospitalBiologicalSampleResultVo.getAreaDeptId() == null) {
            titleWidthAry = new int[]{15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15};
        } else {
            titleWidthAry = new int[]{15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15};
        }
        if (list != null && list.size() > 0) {
            array = new String[list.size()][30];
            for (int i = 0; i < list.size(); i++) {
                if (hospitalBiologicalSampleResultVo.getAreaDeptId() == null) {
                    HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultVo1 = list.get(i);
                    array[i][0] = String.valueOf(i + 1);
                    array[i][1] = hospitalBiologicalSampleResultVo1.getSid() != null ? hospitalBiologicalSampleResultVo1.getSid() : "";
                    array[i][2] = hospitalBiologicalSampleResultVo1.getName() != null ? hospitalBiologicalSampleResultVo1.getName() : "";
                    array[i][3] = hospitalBiologicalSampleResultVo1.getAreaName() != null ? hospitalBiologicalSampleResultVo1.getAreaName() : "";
                    array[i][4] = hospitalBiologicalSampleResultVo1.getCommName() != null ? hospitalBiologicalSampleResultVo1.getCommName() : "";
                    array[i][5] = hospitalBiologicalSampleResultVo1.getNickName() != null ? hospitalBiologicalSampleResultVo1.getNickName() : "";
                    if (hospitalBiologicalSampleResultVo1.getGroup() != null && !hospitalBiologicalSampleResultVo1.getGroup().equals("")) {
                        if (hospitalBiologicalSampleResultVo1.getGroup().equals("A")) {
                            array[i][6] = "A组";
                        } else if (hospitalBiologicalSampleResultVo1.getGroup().equals("B")) {
                            array[i][6] = "B组";
                        } else if (hospitalBiologicalSampleResultVo1.getGroup().equals("C")) {
                            array[i][6] = "C组";
                        } else if (hospitalBiologicalSampleResultVo1.getGroup().equals("Cg")) {
                            array[i][6] = "C组高危";
                        } else if (hospitalBiologicalSampleResultVo1.getGroup().equals("Cd")) {
                            array[i][6] = "C组低危";
                        } else if (hospitalBiologicalSampleResultVo1.getGroup().equals("Cp")) {
                            array[i][6] = "C组未评估";
                        }
                    } else {
                        array[i][6] = hospitalBiologicalSampleResultVo1.getGroup() != null ? hospitalBiologicalSampleResultVo1.getGroup() : "";
                    }
                    array[i][7] = hospitalBiologicalSampleResultVo1.getSampleId() != null ? hospitalBiologicalSampleResultVo1.getSampleId() : "";
                    array[i][8] = hospitalBiologicalSampleResultVo1.getCollectStatusDate() != null ? DateUtil.formatDate(hospitalBiologicalSampleResultVo1.getCollectStatusDate(), "yyyy-MM-dd") : "";
                    array[i][9] = (hospitalBiologicalSampleResultVo1.getsFrozenBoxCode() != null && hospitalBiologicalSampleResultVo1.getsFrozenBoxCode().length() == 7) ? hospitalBiologicalSampleResultVo1.getsFrozenBoxCode() : "";
                    array[i][10] = hospitalBiologicalSampleResultVo1.getsSampleColumn() != null ? hospitalBiologicalSampleResultVo1.getsSampleColumn() + hospitalBiologicalSampleResultVo1.getsSampleLine() : "";
                    String sCourierStatus = "";
                    if (hospitalBiologicalSampleResultVo1.getsCourierStatus() != null) {
                        if (Constans.COURIER_STATUS_CODE1.equals(hospitalBiologicalSampleResultVo1.getsCourierStatus())) {
                            sCourierStatus = "已接受";
                        } else if (Constans.COURIER_STATUS_CODE2.equals(hospitalBiologicalSampleResultVo1.getsCourierStatus())) {
                            sCourierStatus = "未寄出";
                        } else if (Constans.COURIER_STATUS_CODE3.equals(hospitalBiologicalSampleResultVo1.getsCourierStatus())) {
                            sCourierStatus = "已寄出";
                        }
                    }
                    array[i][11] = sCourierStatus;
                    array[i][12] = (hospitalBiologicalSampleResultVo1.getpFrozenBoxCode() != null && hospitalBiologicalSampleResultVo1.getpFrozenBoxCode().length() == 7) ? hospitalBiologicalSampleResultVo1.getpFrozenBoxCode() : "";
                    array[i][13] = hospitalBiologicalSampleResultVo1.getpSampleColumn() != null ? hospitalBiologicalSampleResultVo1.getpSampleColumn() + hospitalBiologicalSampleResultVo1.getpSampleLine() : "";
                    String pCourierStatus = "";
                    if (hospitalBiologicalSampleResultVo1.getpCourierStatus() != null) {
                        if (Constans.COURIER_STATUS_CODE1.equals(hospitalBiologicalSampleResultVo1.getpCourierStatus())) {
                            pCourierStatus = "已接受";
                        } else if (Constans.COURIER_STATUS_CODE2.equals(hospitalBiologicalSampleResultVo1.getpCourierStatus())) {
                            pCourierStatus = "未寄出";
                        } else if (Constans.COURIER_STATUS_CODE3.equals(hospitalBiologicalSampleResultVo1.getpCourierStatus())) {
                            pCourierStatus = "已寄出";
                        }
                    }
                    array[i][14] = pCourierStatus;
                    array[i][15] = (hospitalBiologicalSampleResultVo1.getwFrozenBoxCode() != null && hospitalBiologicalSampleResultVo1.getwFrozenBoxCode().length() == 7) ? hospitalBiologicalSampleResultVo1.getwFrozenBoxCode() : "";
                    array[i][16] = hospitalBiologicalSampleResultVo1.getwSampleColumn() != null ? hospitalBiologicalSampleResultVo1.getwSampleColumn() + hospitalBiologicalSampleResultVo1.getwSampleLine() : "";
                    String wCourierStatus = "";
                    if (hospitalBiologicalSampleResultVo1.getwCourierStatus() != null) {
                        if (Constans.COURIER_STATUS_CODE1.equals(hospitalBiologicalSampleResultVo1.getwCourierStatus())) {
                            wCourierStatus = "已接受";
                        } else if (Constans.COURIER_STATUS_CODE2.equals(hospitalBiologicalSampleResultVo1.getwCourierStatus())) {
                            wCourierStatus = "未寄出";
                        } else if (Constans.COURIER_STATUS_CODE3.equals(hospitalBiologicalSampleResultVo1.getwCourierStatus())) {
                            wCourierStatus = "已寄出";
                        }
                    }
                    array[i][17] = wCourierStatus;
                    array[i][18] = hospitalBiologicalSampleResultVo1.getSampleNote() != null ? hospitalBiologicalSampleResultVo1.getSampleNote() : "";
                } else {
                    HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultVo1 = list.get(i);
                    array[i][0] = String.valueOf(i + 1);
                    array[i][1] = hospitalBiologicalSampleResultVo1.getSid() != null ? hospitalBiologicalSampleResultVo1.getSid() : "";
                    array[i][2] = hospitalBiologicalSampleResultVo1.getName() != null ? hospitalBiologicalSampleResultVo1.getName() : "";
                    array[i][3] = hospitalBiologicalSampleResultVo1.getCommName() != null ? hospitalBiologicalSampleResultVo1.getCommName() : "";
                    array[i][4] = hospitalBiologicalSampleResultVo1.getNickName() != null ? hospitalBiologicalSampleResultVo1.getNickName() : "";
                    if (hospitalBiologicalSampleResultVo1.getGroup() != null && !hospitalBiologicalSampleResultVo1.getGroup().equals("")) {
                        if (hospitalBiologicalSampleResultVo1.getGroup().equals("A")) {
                            array[i][5] = "A组";
                        } else if (hospitalBiologicalSampleResultVo1.getGroup().equals("B")) {
                            array[i][5] = "B组";
                        } else if (hospitalBiologicalSampleResultVo1.getGroup().equals("C")) {
                            array[i][5] = "C组";
                        } else if (hospitalBiologicalSampleResultVo1.getGroup().equals("Cg")) {
                            array[i][5] = "C组高危";
                        } else if (hospitalBiologicalSampleResultVo1.getGroup().equals("Cd")) {
                            array[i][5] = "C组低危";
                        } else if (hospitalBiologicalSampleResultVo1.getGroup().equals("Cp")) {
                            array[i][5] = "C组未评估";
                        }
                    } else {
                        array[i][5] = hospitalBiologicalSampleResultVo1.getGroup() != null ? hospitalBiologicalSampleResultVo1.getGroup() : "";
                    }
                    array[i][6] = hospitalBiologicalSampleResultVo1.getSampleId() != null ? hospitalBiologicalSampleResultVo1.getSampleId() : "";
                    array[i][7] = hospitalBiologicalSampleResultVo1.getCollectStatusDate() != null ? DateUtil.formatDate(hospitalBiologicalSampleResultVo1.getCollectStatusDate(), "yyyy-MM-dd") : "";
                    array[i][8] = (hospitalBiologicalSampleResultVo1.getsFrozenBoxCode() != null && hospitalBiologicalSampleResultVo1.getsFrozenBoxCode().length() == 7) ? hospitalBiologicalSampleResultVo1.getsFrozenBoxCode() : "";
                    array[i][9] = hospitalBiologicalSampleResultVo1.getsSampleColumn() != null ? hospitalBiologicalSampleResultVo1.getsSampleColumn() + hospitalBiologicalSampleResultVo1.getsSampleLine() : "";
                    String sCourierStatus = "";
                    if (hospitalBiologicalSampleResultVo1.getsCourierStatus() != null) {
                        if (Constans.COURIER_STATUS_CODE1.equals(hospitalBiologicalSampleResultVo1.getsCourierStatus())) {
                            sCourierStatus = "已接受";
                        } else if (Constans.COURIER_STATUS_CODE2.equals(hospitalBiologicalSampleResultVo1.getsCourierStatus())) {
                            sCourierStatus = "未寄出";
                        } else if (Constans.COURIER_STATUS_CODE3.equals(hospitalBiologicalSampleResultVo1.getsCourierStatus())) {
                            sCourierStatus = "已寄出";
                        }
                    }
                    array[i][10] = sCourierStatus;
                    array[i][11] = (hospitalBiologicalSampleResultVo1.getpFrozenBoxCode() != null && hospitalBiologicalSampleResultVo1.getpFrozenBoxCode().length() == 7) ? hospitalBiologicalSampleResultVo1.getpFrozenBoxCode() : "";
                    array[i][12] = hospitalBiologicalSampleResultVo1.getpSampleColumn() != null ? hospitalBiologicalSampleResultVo1.getpSampleColumn() + hospitalBiologicalSampleResultVo1.getpSampleLine() : "";
                    String pCourierStatus = "";
                    if (hospitalBiologicalSampleResultVo1.getpCourierStatus() != null) {
                        if (Constans.COURIER_STATUS_CODE1.equals(hospitalBiologicalSampleResultVo1.getpCourierStatus())) {
                            pCourierStatus = "已接受";
                        } else if (Constans.COURIER_STATUS_CODE2.equals(hospitalBiologicalSampleResultVo1.getpCourierStatus())) {
                            pCourierStatus = "未寄出";
                        } else if (Constans.COURIER_STATUS_CODE3.equals(hospitalBiologicalSampleResultVo1.getpCourierStatus())) {
                            pCourierStatus = "已寄出";
                        }
                    }
                    array[i][13] = pCourierStatus;
                    array[i][14] = (hospitalBiologicalSampleResultVo1.getwFrozenBoxCode() != null && hospitalBiologicalSampleResultVo1.getwFrozenBoxCode().length() == 7) ? hospitalBiologicalSampleResultVo1.getwFrozenBoxCode() : "";
                    array[i][15] = hospitalBiologicalSampleResultVo1.getwSampleColumn() != null ? hospitalBiologicalSampleResultVo1.getwSampleColumn() + hospitalBiologicalSampleResultVo1.getwSampleLine() : "";
                    String wCourierStatus = "";
                    if (hospitalBiologicalSampleResultVo1.getwCourierStatus() != null) {
                        if (Constans.COURIER_STATUS_CODE1.equals(hospitalBiologicalSampleResultVo1.getwCourierStatus())) {
                            wCourierStatus = "已接受";
                        } else if (Constans.COURIER_STATUS_CODE2.equals(hospitalBiologicalSampleResultVo1.getwCourierStatus())) {
                            wCourierStatus = "未寄出";
                        } else if (Constans.COURIER_STATUS_CODE3.equals(hospitalBiologicalSampleResultVo1.getwCourierStatus())) {
                            wCourierStatus = "已寄出";
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
    public ExcelData queryBiologicalSampleResultExcel(ExeclData execlData, HttpServletResponse response) {

        String sampleType = "";
        if(execlData.getType() != null && "stool".equals(execlData.getType())){//粪便
            sampleType = Constans.SAMPLE_TYPE5;
        }
        if(execlData.getType() != null && "saliva".equals(execlData.getType())){//唾液
            sampleType = Constans.SAMPLE_TYPE4;
        }
        List<HospitalBiologicalSampleResultVo> list = excelDao.queryBiologicalSampleResultExcel(sampleType,execlData);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/msexcel");
        StringBuilder fileName = new StringBuilder();

        if(execlData.getType()!=null && "stool".equals(execlData.getType())){//粪便
            fileName.append("粪便样本采集与处理信息列表");
        }
        if(execlData.getType() != null && "saliva".equals(execlData.getType())){//唾液
            fileName.append("唾液样本采集与处理信息列表");
        }
        try {
            response.setHeader("content-disposition", "attachment; filename=\"" + URLEncoder.encode(fileName.toString(), "UTF-8") + ".xlsx\"");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
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
            excelData.setHeadStr("粪便样本采集与处理信息表");
        }
        if(Constans.SAMPLE_TYPE4.equals(sampleType)){
            excelData.setHeadStr("唾液样本采集与处理信息列表");
        }
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
}
