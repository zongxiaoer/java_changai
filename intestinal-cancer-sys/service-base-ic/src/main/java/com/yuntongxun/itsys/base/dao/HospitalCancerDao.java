package com.yuntongxun.itsys.base.dao;/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: HospitalCancerDao
 * Author:   zongtong
 * Date:     2018/9/6 上午10:42
 * History:
 * <author>          <time>                <version>
 * zongtong           2018/9/6 上午10:42           v1.0.0
 */

import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.po.dto.cancervo.*;

import java.util.List;

public interface HospitalCancerDao {

    int save(HospitalCancerRecordVo hospitalCancerRecordVo);

    ListPageUtil query(HospitalCancerRecordVo hospitalCancerRecordVo, boolean b);

    int saveReport(HospitalCancerReportVo hospitalCancerRecordVo);

    void saveReportMessage(List<HospitalCancerReportMessageVo> hospitalCancerReportMessagePOS);

    void updateReportById(String sqlId,int reportId,String sqlStatus,String status,String sqlDate, Integer cancerRecordId);

    List<HospitalCancerReportVo> queryReportById(Integer id);

    List<HospitalCancerReportMessageVo> queryReportMessageByReportId(Integer id);

    void updateReportByReportID(HospitalCancerReportVo hospitalCancerReportVo);

    void deleteReportMessageByReportId(Integer id,String table);

    int saveDiagnose(HospitalCancerDiagnoseVo hospitalCancerDiagnoseVo);

    List<HospitalCancerDiagnoseVo> queryDiagnoseById(Integer id);

    void updateDiagnose(HospitalCancerDiagnoseVo hospitalCancerDiagnoseVo);

    int saveTreatmentInformation(HospitalColorectalCancerTreatmentInformationVo hospitalColorectalCancerTreatmentInformationVo);

    void saveSurgicalOperations(List<HospitalCancerSurgicalOperationVo> hospitalCancerSurgicalOperationVos);

    void saveTreatmentInformations(List<HospitalCancerTreatmentInformationVo> hospitalCancerTreatmentInformationVos);

    //更新结直肠癌治疗信息摘录表
    void updateReportC4ByID(HospitalColorectalCancerTreatmentInformationVo hospitalColorectalCancerTreatmentInformationVo);
    //删除结直肠癌治疗信息摘录表的从表数据
    void deleteAddendumByC4Id(Integer id, String hospitalCancerSurgicalOperation);
    //查询从表信息   外科操作表
    List<HospitalCancerSurgicalOperationVo> queryAddendum1ByC4Id(Integer id, Integer communityDeptId, Integer areaDeptId);
    //查询从表信息   治疗信息表
    List<HospitalCancerTreatmentInformationVo> queryAddendum2ByC4Id(Integer id, Integer communityDeptId, Integer areaDeptId);
    //查询结直肠癌治疗信息摘录表数据
    List<HospitalColorectalCancerTreatmentInformationVo> queryReportC4ById(Integer id, Integer communityDeptId, Integer areaDeptId);

    int saveCancerDiagnoseInformation(HospitalColorectalCancerDiagnoseInformationVo hospitalColorectalCancerDiagnoseInformationVo);

    void saveInformationDiagnoseEvaluationVos(List<HospitalInformationDiagnoseEvaluationVo> hospitalInformationDiagnoseEvaluationVos);

    List<HospitalColorectalCancerDiagnoseInformationVo> queryDiagnoseInformationbyId(Integer id, Integer communityDeptId, Integer areaDeptId);

    List<HospitalInformationDiagnoseEvaluationVo> queryDiagnoseEvaluationbyId(Integer id, Integer communityDeptId, Integer areaDeptId);
    //删除结直肠癌诊断信息摘录表c3  诊断评估表 治疗信息表
    void deleteAddendumByC3Id(Integer id, String table);

    void saveInformationComplicationsVos(List<HospitalCancerInformationComplicationsVo> hospitalCancerInformationComplicationsVos);


    List<HospitalCancerInformationComplicationsVo> queryInformationComplicationsVosbyId(Integer id, Integer communityDeptId, Integer areaDeptId);

    //修改结直肠癌诊断信息摘录表c3
    void updateReportC3ByID(HospitalColorectalCancerDiagnoseInformationVo hospitalColorectalCancerDiagnoseInformationVo);

    void delCancerRecordForCJResult(Integer colonoscopyRecordId, String sid);
}
