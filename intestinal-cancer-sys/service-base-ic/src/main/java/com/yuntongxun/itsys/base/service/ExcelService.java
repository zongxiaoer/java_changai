package com.yuntongxun.itsys.base.service;/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: ExcelService
 * Author:   zongtong
 * Date:     2018/8/23 下午3:05
 * History:
 * <author>          <time>                <version>
 * zongtong           2018/8/23 下午3:05           v1.0.0
 */

import com.yuntongxun.itsys.base.common.util.ExcelData;
import com.yuntongxun.itsys.base.po.*;
import com.yuntongxun.itsys.base.po.dto.ExeclData;
import com.yuntongxun.itsys.base.vo.ColonoscopyNotificationVo;
import com.yuntongxun.itsys.base.vo.ColonoscopyVo;
import com.yuntongxun.itsys.base.vo.PathologyExcelVO;
import com.yuntongxun.itsys.base.vo.HospitalBiologicalSampleResultVo;
import com.yuntongxun.itsys.base.vo.HospitalColonoscopyResultVo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ExcelService {

    /**
     * 粪便DNA检测装置发放记录列表导出
     * @param execlData
     * @return
     */
    List<StoolDna> getDnaexcel(ExeclData execlData);


    /**
     * 筛查结果告知书发放记录列表导出
     * @param execlData
     * @return
     */
    List<ColonoscopyVo> getNotificationRecord(ExeclData execlData);

    List<HospitalReview> stoolReviewQueryExcel(ExeclData execlData);

    List<ColonoscopyNotificationVo> stoolNotificationQueryExcel(ExeclData execlData);

    /**
     * 导出危险因素调查表
     * @param execlData
     * @return
     */
    List<HospitalRiskFactorPO> getReviewRiskFactorDetails(ExeclData execlData);

    /**
     * 导出受试者违反方案表
     * @param execlData
     * @return
     */
    List<ViolationSchemePO> getReviewViolationSchemeDetails(ExeclData execlData);

    /**
     * 导出肠镜病理结果表
     * @param execlData
     * @return
     */
    List<PathologyExcelVO> getPathologyQueryExcel(ExeclData execlData);

    List<HospitalBiologicalSampleResultVo> queryBySampleExcel(ExeclData execlData);

    List<HospitalColonoscopyResultVo> stoolColonoscopyResultQueryExcel(ExeclData execlData);

    /**
     * 导出血液样本采集与快递信息列表
     * @param hospitalBiologicalSampleResultVo
     * @param response
     * @param execlData
     * @return
     */
    ExcelData bloodSampleAndExpressQueryExcel(HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultVo, HttpServletResponse response, ExeclData execlData);

    /**
     * 导出生物样本与快递信息列表
     * @param execlData
     * @param response
     * @return
     */
    ExcelData queryBiologicalSampleResultExcel(ExeclData execlData, HttpServletResponse response);
}
