package com.yuntongxun.itsys.base.service;/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: HospitalCancerService
 * Author:   zongtong
 * Date:     2018/9/6 下午2:29
 * History:
 * <author>          <time>                <version>
 * zongtong           2018/9/6 下午2:29           v1.0.0
 */

import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.po.dto.cancervo.*;

import java.util.List;

public interface HospitalCancerService {
    ListPageUtil query(HospitalCancerRecordVo hospitalCancerRecordVo, String loginName);

    void saveReport(HospitalCancerReportVo hospitalCancerRecordVo);


    HospitalCancerReportVo  queryReportById(Integer id, Integer communityDeptId, Integer areaDeptId);

    void updateReport(HospitalCancerReportVo hospitalCancerReportVo);

    /**
     * 根据id查询结直肠癌治疗信息摘录表及从表信息
     * @param id
     * @param communityDeptId
     * @param areaDeptId
     * @return
     */
    HospitalColorectalCancerTreatmentInformationVo queryReportC4ById(Integer id, Integer communityDeptId, Integer areaDeptId);

    /**
     * 根据id修改结直肠癌治疗信息摘录表及从表信息
     * @param param
     */
    void updateReportC4(HospitalColorectalCancerTreatmentInformationVo param);
    void saveDiagnose(HospitalCancerDiagnoseVo hospitalCancerDiagnoseVo);

    HospitalCancerDiagnoseVo queryDiagnoseById(Integer id, Integer communityDeptId, Integer areaDeptId);

    void updateDiagnose(HospitalCancerDiagnoseVo hospitalCancerDiagnoseVo);

    void saveTreatmentInformation(HospitalColorectalCancerTreatmentInformationVo hospitalColorectalCancerTreatmentInformationVo);

    void saveDiagnoseInformation(HospitalColorectalCancerDiagnoseInformationVo hospitalColorectalCancerDiagnoseInformationVo);


    HospitalColorectalCancerDiagnoseInformationVo queryDiagnoseInformationbyId(Integer id, Integer communityDeptId, Integer areaDeptId);

    /**
     * 根据id修改结直肠癌诊断信息摘录表c3及从表信息
     * @param param
     */
    void updateReportC3(HospitalColorectalCancerDiagnoseInformationVo param);
}
