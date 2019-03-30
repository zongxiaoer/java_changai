/**
 * Project Name:service-base-yl
 * File Name:PersonService.java
 * Package Name:com.yuntongxun.itsys.base.service
 * Date:2018年4月9日下午6:28:46
 * Copyright (c) 2018, ty All Rights Reserved.
 */

package com.yuntongxun.itsys.base.service;

import com.yuntongxun.itsys.base.common.util.ExcelData;
import com.yuntongxun.itsys.base.po.HospitalReview;
import com.yuntongxun.itsys.base.po.dto.ExeclData;
import com.yuntongxun.itsys.base.po.dto.HospitalReviewExport;
import com.yuntongxun.itsys.base.vo.ColonoscopyVo;
import com.yuntongxun.itsys.base.vo.HospitalBiologicalSampleResultVo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * ClassName:PersonService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018年4月9日 下午6:28:46 <br/>
 *
 * @author ty
 * @see
 * @since JDK 1.8
 */
public interface PersonExportService {

    /**
     * 社区人员导出
     *
     * @param person
     * @param loginName
     * @return
     */
    public ExcelData commUsersQueryExcel(HospitalReviewExport person, String loginName, HttpServletResponse response);

    /**
     * 地区人员导出
     *
     * @param person
     * @param loginName
     * @return
     */
    public ExcelData areaUsersQueryExcel(HospitalReviewExport person, String loginName, HttpServletResponse response);

    /**
     * 国家人员导出
     *
     * @param person
     * @param loginName
     * @return
     */
    public ExcelData nationUsersQueryExcel(HospitalReviewExport person, String loginName, HttpServletResponse response);

    /**
     * 国家肠镜信息导出
     *
     * @param queryCondition
     * @param loginName
     * @return
     */
    public List<ColonoscopyVo> queryFoCountryCJExcel(ColonoscopyVo queryCondition, String loginName);

    /**
     * 导出生物样本（唾液/粪便）
     *
     * @param loginName
     * @param response
     * @return
     */
    public ExcelData queryBiologicalSampleResultExcel(String loginName, String body, HttpServletResponse response);

    ExcelData bloodSampleQueryExcel(HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultVo, HttpServletResponse response);

    ExcelData stollbloodSampleQueryExcel(ExeclData execlData, HttpServletResponse response);

    ExcelData stollnationUsersQueryExcel(ExeclData execlData, HttpServletResponse response);

    List<ColonoscopyVo> queryFoStollCountryCJExcel(ExeclData execlData);
}
