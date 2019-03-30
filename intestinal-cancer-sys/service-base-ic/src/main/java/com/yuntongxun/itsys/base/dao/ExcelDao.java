package com.yuntongxun.itsys.base.dao;/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: ExcelDao
 * Author:   zongtong
 * Date:     2018/8/23 下午3:06
 * History:
 * <author>          <time>                <version>
 * zongtong           2018/8/23 下午3:06           v1.0.0
 */

import com.yuntongxun.itsys.base.po.*;
import com.yuntongxun.itsys.base.po.dto.ExeclData;
import com.yuntongxun.itsys.base.vo.*;

import java.util.List;

public interface ExcelDao {
    List<StoolDna> getDnaexcel(ExeclData execlData);

    List<ColonoscopyVo> getNotificationRecord(ExeclData execlData);

    List<HospitalReview> stoolReviewQueryExcel(ExeclData execlData);

    List<ColonoscopyNotificationVo> stoolNotificationQueryExcel(ExeclData execlData);

    List<HospitalRiskFactorPO> getReviewRiskFactorDetails(ExeclData execlData);

    List<ViolationSchemePO> getReviewViolationSchemeDetails(ExeclData execlData);

    List<PathologyExcelVO> getPathologyQueryExcel(ExeclData execlData);

    List<HospitalBiologicalSampleResultVo> queryBySampleExcel(ExeclData execlData);

    List<HospitalBiologicalSampleResultVo> querybloodSampleExcel(HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultVo, ExeclData execlData);

    List<HospitalBiologicalSampleResultVo> queryBiologicalSampleResultExcel(String sampleType, ExeclData execlData);

    List<HospitalColonoscopyResultVo> stoolColonoscopyResultQueryExcel(ExeclData execlData);
}
