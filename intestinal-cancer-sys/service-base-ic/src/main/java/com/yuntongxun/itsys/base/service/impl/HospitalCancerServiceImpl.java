package com.yuntongxun.itsys.base.service.impl;/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: HospitalCancerServiceImpl
 * Author:   zongtong
 * Date:     2018/9/6 下午2:30
 * History:
 * <author>          <time>                <version>
 * zongtong           2018/9/6 下午2:30           v1.0.0
 */

import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.GlobalErrorCode;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.dao.HospitalCancerDao;
import com.yuntongxun.itsys.base.dao.HtEventDao;
import com.yuntongxun.itsys.base.dao.PersonDao;
import com.yuntongxun.itsys.base.po.FileUploadLogPO;
import com.yuntongxun.itsys.base.po.HospitalReview;
import com.yuntongxun.itsys.base.po.cancer.HospitalCancerReportMessagePO;
import com.yuntongxun.itsys.base.po.dto.cancervo.*;
import com.yuntongxun.itsys.base.service.ColonoscopyService;
import com.yuntongxun.itsys.base.service.HospitalCancerService;
import com.yuntongxun.itsys.base.vo.DoctorInfo;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HospitalCancerServiceImpl implements HospitalCancerService {

    @Autowired
    private HospitalCancerDao hospitalCancerDao;

    @Autowired
    private ColonoscopyService colonoscopyService;

    @Autowired
    private HtEventDao htEventDao;

    @Autowired
    private PersonDao personDao;

    @Override
    public ListPageUtil query(HospitalCancerRecordVo hospitalCancerRecordVo, String loginName) {
        //查询登录用户信息
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        if(doctorInfo.getCommunityDeptId()!=null){
            hospitalCancerRecordVo.setCommunityDeptId(doctorInfo.getCommunityDeptId());

        }
        if(doctorInfo.getAreaDeptId()!=null){
            hospitalCancerRecordVo.setAreaDeptId(doctorInfo.getAreaDeptId());
        }
        //分页查询结肠镜检查数据
        ListPageUtil listPage = hospitalCancerDao.query(hospitalCancerRecordVo, true);
        return listPage;
    }

    @Override
    @Transactional
    public void saveReport(HospitalCancerReportVo hospitalCancerReportVo) {
        //hospital/review/get（获取基础信息）+癌症检查结果ID

        try {
            //根据sid获取受试者
            HospitalReview hospitalReview = personDao.getBySid(hospitalCancerReportVo.getSid());
            hospitalCancerReportVo.setStage(hospitalReview.getStageCy());
            hospitalCancerReportVo.setDeptCode(hospitalReview.getSiteId());
            //	录入c1表、录入癌症信息表、
            int reportId = hospitalCancerDao.saveReport(hospitalCancerReportVo);
            if (hospitalCancerReportVo.getHospitalCancerReportMessagePOS() != null && hospitalCancerReportVo.getHospitalCancerReportMessagePOS().size() > 0) {
                List<HospitalCancerReportMessageVo> hospitalCancerReportMessagePOS = hospitalCancerReportVo.getHospitalCancerReportMessagePOS();
                for (HospitalCancerReportMessageVo HospitalCancerReportMessageVo : hospitalCancerReportMessagePOS) {
                    HospitalCancerReportMessageVo.setCancerReportId(reportId);
                }
                hospitalCancerDao.saveReportMessage(hospitalCancerReportMessagePOS);
            }
            //	根据癌症检查结果ID修改代办
            htEventDao.updateStatus(hospitalCancerReportVo.getSid(), hospitalCancerReportVo.getCancerRecordId(), Constans.PERSON_TODO_EVENT_TYPE20, Constans.PERSON_TODO_EVENT_STATUS2);
            //	癌症检查结果ID修改癌症检查结果
            hospitalCancerDao.updateReportById(Constans.CANCER_REPORT_ID, reportId, Constans.CANCER_REPORT_STATUS, Constans.cancerReportStatus2, Constans.CANCER_REPORT_IN_STATUS_DATE, hospitalCancerReportVo.getCancerRecordId());
        } catch (Exception e) {
            throw new ItSysException(GlobalErrorCode.HOSPITAL_CANCER_RECORD_SAVE_EVENT_CODE, GlobalErrorCode.HOSPITAL_CANCER_RECORD_SAVE_CODE_EVENT_MSG);
        }
    }

    @Override
    public  HospitalCancerReportVo queryReportById(Integer id, Integer communityDeptId, Integer areaDeptId) {
        List<HospitalCancerReportVo> vo= hospitalCancerDao.queryReportById(id);
        if(vo.size()!=1){
            throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.UNKNOWN_ERROR_MSG);
        }
        HospitalCancerReportVo hospitalCancerReportVo = vo.get(0);
        List<HospitalCancerReportMessageVo>  hospitalCancerReportMessageVos= hospitalCancerDao.queryReportMessageByReportId(id);
        if(hospitalCancerReportMessageVos.size()>0){
            hospitalCancerReportVo.setHospitalCancerReportMessagePOS(hospitalCancerReportMessageVos);
        }
        return hospitalCancerReportVo;
    }

    @Override
    @Transactional
    public void updateReport(HospitalCancerReportVo hospitalCancerReportVo) {
        try {
            //修改c1表、录入癌症信息表、
            hospitalCancerDao.updateReportByReportID(hospitalCancerReportVo);
            //根据ReportID删除愿数据
            hospitalCancerDao.deleteReportMessageByReportId(hospitalCancerReportVo.getId(),Constans.HOSPITAL_CANCER_REPORT_MESSAGE);
            if (hospitalCancerReportVo.getHospitalCancerReportMessagePOS() != null && hospitalCancerReportVo.getHospitalCancerReportMessagePOS().size() > 0) {
                List<HospitalCancerReportMessageVo> hospitalCancerReportMessagePOS = hospitalCancerReportVo.getHospitalCancerReportMessagePOS();
                for (HospitalCancerReportMessageVo HospitalCancerReportMessageVo : hospitalCancerReportMessagePOS) {
                    HospitalCancerReportMessageVo.setCancerReportId(hospitalCancerReportVo.getId());
                }
                hospitalCancerDao.saveReportMessage(hospitalCancerReportMessagePOS);
            }
        } catch (Exception e) {
            System.out.println("dao is error   "+e.toString());
            throw new ItSysException(GlobalErrorCode.HOSPITAL_CANCER_RECORD_UPDATE_EVENT_CODE, GlobalErrorCode.HOSPITAL_CANCER_RECORD_UPDATE_CODE_EVENT_MSG);
        }
    }

    @Override
    @Transactional
    public HospitalColorectalCancerTreatmentInformationVo queryReportC4ById(Integer id, Integer communityDeptId, Integer areaDeptId) {
        List<HospitalColorectalCancerTreatmentInformationVo> res = hospitalCancerDao.queryReportC4ById(id,communityDeptId,areaDeptId);
        List<HospitalCancerSurgicalOperationVo> surgicalOperationVoList = hospitalCancerDao.queryAddendum1ByC4Id(id,communityDeptId,areaDeptId);
        if(surgicalOperationVoList!=null&&surgicalOperationVoList.size()>0){
            res.get(0).setHospitalCancerSurgicalOperationVos(surgicalOperationVoList);
        }
        List<HospitalCancerTreatmentInformationVo> treatmentInformationVoList = hospitalCancerDao.queryAddendum2ByC4Id(id,communityDeptId,areaDeptId);
        if(treatmentInformationVoList!=null&&treatmentInformationVoList.size()>0){
            res.get(0).setHospitalCancerTreatmentInformationVos(treatmentInformationVoList);
        }
        return res.get(0);
    }

    @Override
    @Transactional
    public void updateReportC4(HospitalColorectalCancerTreatmentInformationVo hospitalColorectalCancerTreatmentInformationVo) {
        try {
            Integer mainId = hospitalColorectalCancerTreatmentInformationVo.getId();
            //修改结直肠癌治疗信息摘录表c4表及从表信息
            hospitalCancerDao.updateReportC4ByID(hospitalColorectalCancerTreatmentInformationVo);
            //根据c4 ID删除原数据
            hospitalCancerDao.deleteAddendumByC4Id(hospitalColorectalCancerTreatmentInformationVo.getId(),Constans.HOSPITAL_CANCER_SURGICAL_OPERATION);
            hospitalCancerDao.deleteAddendumByC4Id(hospitalColorectalCancerTreatmentInformationVo.getId(),Constans.HOSPITAL_CANCER_TREATMENT_INFORMATION);
            //保存从表信息   外科操作表
            if(hospitalColorectalCancerTreatmentInformationVo.getHospitalCancerSurgicalOperationVos()!=null&&hospitalColorectalCancerTreatmentInformationVo.getHospitalCancerSurgicalOperationVos().size()>0){
                List<HospitalCancerSurgicalOperationVo> hospitalCancerSurgicalOperationVos = hospitalColorectalCancerTreatmentInformationVo.getHospitalCancerSurgicalOperationVos();
                for (HospitalCancerSurgicalOperationVo hospitalCancerSurgicalOperationVo:hospitalCancerSurgicalOperationVos) {
                    hospitalCancerSurgicalOperationVo.setColorectalCancerTreatmentInformationId(mainId);
                }
                hospitalCancerDao.saveSurgicalOperations(hospitalCancerSurgicalOperationVos);
            }
            //保存从表信息   治疗信息表
            if(hospitalColorectalCancerTreatmentInformationVo.getHospitalCancerTreatmentInformationVos()!=null&&hospitalColorectalCancerTreatmentInformationVo.getHospitalCancerTreatmentInformationVos().size()>0){
                List<HospitalCancerTreatmentInformationVo> hospitalCancerTreatmentInformationVos = hospitalColorectalCancerTreatmentInformationVo.getHospitalCancerTreatmentInformationVos();
                for (HospitalCancerTreatmentInformationVo hospitalCancerTreatmentInformationVo:hospitalCancerTreatmentInformationVos) {
                    hospitalCancerTreatmentInformationVo.setColorectalCancerTreatmentInformationId(mainId);
                }
                hospitalCancerDao.saveTreatmentInformations(hospitalCancerTreatmentInformationVos);
            }
        } catch (Exception e) {
            System.out.println("dao is error   "+e.toString());
            throw new ItSysException(GlobalErrorCode.HOSPITAL_CANCER_RECORD_UPDATE_EVENT_CODE, GlobalErrorCode.HOSPITAL_CANCER_RECORD_UPDATE_CODE_EVENT_MSG);
        }
    }

    @Override
    @Transactional
    public void saveDiagnose(HospitalCancerDiagnoseVo hospitalCancerDiagnoseVo) {
        try {
            //根据sid获取受试者
            HospitalReview hospitalReview = personDao.getBySid(hospitalCancerDiagnoseVo.getSid());
            hospitalCancerDiagnoseVo.setStage(hospitalReview.getStageCy());
            hospitalCancerDiagnoseVo.setDeptCode(hospitalReview.getSiteId());
            //	录入c2表、录入癌症信息表、
            int diagnoseId = hospitalCancerDao.saveDiagnose(hospitalCancerDiagnoseVo);
            //	根据癌症检查结果ID修改代办
            htEventDao.updateStatus(hospitalCancerDiagnoseVo.getSid(), hospitalCancerDiagnoseVo.getCancerRecordId(), Constans.PERSON_TODO_EVENT_TYPE21, Constans.PERSON_TODO_EVENT_STATUS2);
            //	癌症检查结果ID修改癌症检查结果
            hospitalCancerDao.updateReportById(Constans.CANCER_DIAGNOSE_ID, diagnoseId, Constans.CANCER_DIAGNOSE_STATUS, Constans.cancerReportStatus2, Constans.CANCER_DIAGNOSE_IN_STATUS_DATE, hospitalCancerDiagnoseVo.getCancerRecordId());
        } catch (Exception e) {
            throw new ItSysException(GlobalErrorCode.HOSPITAL_CANCER_RECORD_SAVE_EVENT_CODE, GlobalErrorCode.HOSPITAL_CANCER_RECORD_SAVE_CODE_EVENT_MSG);
        }
    }

    @Override
    public HospitalCancerDiagnoseVo queryDiagnoseById(Integer id, Integer communityDeptId, Integer areaDeptId) {
        List<HospitalCancerDiagnoseVo> vo= hospitalCancerDao.queryDiagnoseById(id);
        if(vo.size()!=1){
            throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.UNKNOWN_ERROR_MSG);
        }
        return vo.get(0);
    }

    @Override
    public void updateDiagnose(HospitalCancerDiagnoseVo hospitalCancerDiagnoseVo) {
        try {
            //	录入c2表、录入癌症信息表、
            hospitalCancerDao.updateDiagnose(hospitalCancerDiagnoseVo);
        } catch (Exception e) {
            throw new ItSysException(GlobalErrorCode.HOSPITAL_CANCER_RECORD_SAVE_EVENT_CODE, GlobalErrorCode.HOSPITAL_CANCER_RECORD_SAVE_CODE_EVENT_MSG);
        }
    }

    @Override
    @Transactional
    public void saveTreatmentInformation(HospitalColorectalCancerTreatmentInformationVo hospitalColorectalCancerTreatmentInformationVo) {
        try {
            //根据sid获取受试者
            HospitalReview hospitalReview = personDao.getBySid(hospitalColorectalCancerTreatmentInformationVo.getSid());
            hospitalColorectalCancerTreatmentInformationVo.setStage(hospitalReview.getStageCy());
            hospitalColorectalCancerTreatmentInformationVo.setDeptCode(hospitalReview.getSiteId());
            //	录入c2表、录入癌症信息表、
            int treatmentInformationId = hospitalCancerDao.saveTreatmentInformation(hospitalColorectalCancerTreatmentInformationVo);
            if(hospitalColorectalCancerTreatmentInformationVo.getHospitalCancerSurgicalOperationVos()!=null&&hospitalColorectalCancerTreatmentInformationVo.getHospitalCancerSurgicalOperationVos().size()>0){
                List<HospitalCancerSurgicalOperationVo> hospitalCancerSurgicalOperationVos = hospitalColorectalCancerTreatmentInformationVo.getHospitalCancerSurgicalOperationVos();
                for (HospitalCancerSurgicalOperationVo hospitalCancerSurgicalOperationVo:hospitalCancerSurgicalOperationVos) {
                    hospitalCancerSurgicalOperationVo.setColorectalCancerTreatmentInformationId(treatmentInformationId);
                }
                hospitalCancerDao.saveSurgicalOperations(hospitalCancerSurgicalOperationVos);
            }
            if(hospitalColorectalCancerTreatmentInformationVo.getHospitalCancerTreatmentInformationVos()!=null&&hospitalColorectalCancerTreatmentInformationVo.getHospitalCancerTreatmentInformationVos().size()>0){
                List<HospitalCancerTreatmentInformationVo> hospitalCancerTreatmentInformationVos = hospitalColorectalCancerTreatmentInformationVo.getHospitalCancerTreatmentInformationVos();
                for (HospitalCancerTreatmentInformationVo hospitalCancerTreatmentInformationVo:hospitalCancerTreatmentInformationVos) {
                    hospitalCancerTreatmentInformationVo.setColorectalCancerTreatmentInformationId(treatmentInformationId);
                }
                hospitalCancerDao.saveTreatmentInformations(hospitalCancerTreatmentInformationVos);
            }
            //hospital_cancer_surgical_operation
            //	根据癌症检查结果ID修改代办
            htEventDao.updateStatus(hospitalColorectalCancerTreatmentInformationVo.getSid(), hospitalColorectalCancerTreatmentInformationVo.getCancerRecordId(), Constans.PERSON_TODO_EVENT_TYPE23, Constans.PERSON_TODO_EVENT_STATUS2);
            //	癌症检查结果ID修改癌症检查结果
            hospitalCancerDao.updateReportById(Constans.COLORECTAL_CANCER_TREATMENT_INFORMATION_ID, treatmentInformationId, Constans.COLORECTAL_CANCER_TREATMENT_INFORMATIO_STATUS, Constans.cancerReportStatus2, Constans.COLORECTAL_CANCER_TREATMENT_INFORMATIO_IN_STATUS_DATE, hospitalColorectalCancerTreatmentInformationVo.getCancerRecordId());
        } catch (Exception e) {
            throw new ItSysException(GlobalErrorCode.HOSPITAL_CANCER_RECORD_SAVE_EVENT_CODE, GlobalErrorCode.HOSPITAL_CANCER_RECORD_SAVE_CODE_EVENT_MSG);
        }
    }

    @Override
    @Transactional
    public void saveDiagnoseInformation(HospitalColorectalCancerDiagnoseInformationVo hospitalColorectalCancerDiagnoseInformationVo) {
        try {
            //根据sid获取受试者
            HospitalReview hospitalReview = personDao.getBySid(hospitalColorectalCancerDiagnoseInformationVo.getSid());
            hospitalColorectalCancerDiagnoseInformationVo.setStage(hospitalReview.getStageCy());
            hospitalColorectalCancerDiagnoseInformationVo.setDeptCode(hospitalReview.getSiteId());
            //	录入c2表、录入癌症信息表、
            int cancerDiagnoseInformationId = hospitalCancerDao.saveCancerDiagnoseInformation(hospitalColorectalCancerDiagnoseInformationVo);

            if(hospitalColorectalCancerDiagnoseInformationVo.getHospitalInformationDiagnoseEvaluationVos()!=null&&hospitalColorectalCancerDiagnoseInformationVo.getHospitalInformationDiagnoseEvaluationVos().size()>0){
                List<HospitalInformationDiagnoseEvaluationVo> hospitalInformationDiagnoseEvaluationVos = hospitalColorectalCancerDiagnoseInformationVo.getHospitalInformationDiagnoseEvaluationVos();
                for (HospitalInformationDiagnoseEvaluationVo hospitalCancerTreatmentInformationVos:hospitalInformationDiagnoseEvaluationVos) {
                    hospitalCancerTreatmentInformationVos.setColorectalCancerDiagnoseInformationId(cancerDiagnoseInformationId);
                }
                hospitalCancerDao.saveInformationDiagnoseEvaluationVos(hospitalInformationDiagnoseEvaluationVos);
            }

            if(hospitalColorectalCancerDiagnoseInformationVo.getHospitalCancerInformationComplicationsVos()!=null&&hospitalColorectalCancerDiagnoseInformationVo.getHospitalCancerInformationComplicationsVos().size()>0){
                List<HospitalCancerInformationComplicationsVo> hospitalCancerInformationComplicationsVos = hospitalColorectalCancerDiagnoseInformationVo.getHospitalCancerInformationComplicationsVos();
                for (HospitalCancerInformationComplicationsVo hospitalCancerInformationComplicationsVo:hospitalCancerInformationComplicationsVos) {
                    hospitalCancerInformationComplicationsVo.setColorectalCancerDiagnoseInformationId(cancerDiagnoseInformationId);
                }
                hospitalCancerDao.saveInformationComplicationsVos(hospitalCancerInformationComplicationsVos);
            }

            //hospital_cancer_surgical_operation
            //	根据癌症检查结果ID修改代办
            htEventDao.updateStatus(hospitalColorectalCancerDiagnoseInformationVo.getSid(), hospitalColorectalCancerDiagnoseInformationVo.getCancerRecordId(), Constans.PERSON_TODO_EVENT_TYPE22, Constans.PERSON_TODO_EVENT_STATUS2);
            //	癌症检查结果ID修改癌症检查结果
            hospitalCancerDao.updateReportById(Constans.COLORECTAL_CANCER_DIAGNOSE_INFORMATION_ID, cancerDiagnoseInformationId, Constans.COLORECTAL_CANCER_DIAGNOSE_INFORMATION_STATUS, Constans.cancerReportStatus2, Constans.COLORECTAL_CANCER_DIAGNOSE_INFORMATION_IN_STATUS_DATE, hospitalColorectalCancerDiagnoseInformationVo.getCancerRecordId());
        } catch (Exception e) {
            System.out.println("错误"+e.toString());
            throw new ItSysException(GlobalErrorCode.HOSPITAL_CANCER_RECORD_SAVE_EVENT_CODE, GlobalErrorCode.HOSPITAL_CANCER_RECORD_SAVE_CODE_EVENT_MSG);
        }
    }

    @Override
    public HospitalColorectalCancerDiagnoseInformationVo queryDiagnoseInformationbyId(Integer id, Integer communityDeptId, Integer areaDeptId) {
        List<HospitalColorectalCancerDiagnoseInformationVo> res = hospitalCancerDao.queryDiagnoseInformationbyId(id,communityDeptId,areaDeptId);
        List<HospitalInformationDiagnoseEvaluationVo> hospitalInformationDiagnoseEvaluationVos= hospitalCancerDao.queryDiagnoseEvaluationbyId(id,communityDeptId,areaDeptId);
        if(hospitalInformationDiagnoseEvaluationVos!=null&&hospitalInformationDiagnoseEvaluationVos.size()>0){
            res.get(0).setHospitalInformationDiagnoseEvaluationVos(hospitalInformationDiagnoseEvaluationVos);
        }

        List<HospitalCancerInformationComplicationsVo> hospitalCancerInformationComplicationsVos= hospitalCancerDao.queryInformationComplicationsVosbyId(id,communityDeptId,areaDeptId);
        if(hospitalCancerInformationComplicationsVos!=null&&hospitalCancerInformationComplicationsVos.size()>0){
            res.get(0).setHospitalCancerInformationComplicationsVos(hospitalCancerInformationComplicationsVos);
        }
        HospitalColorectalCancerDiagnoseInformationVo hospitalColorectalCancerDiagnoseInformationVo = res.get(0);
        String reportUrlToString = hospitalColorectalCancerDiagnoseInformationVo.getReportUrlToString();
        if(!StringUtils.isEmpty(reportUrlToString)){
            List<FileUploadLogPO> list=  (List<FileUploadLogPO>) JSONArray.toList(JSONArray.fromObject(reportUrlToString),FileUploadLogPO.class);
            hospitalColorectalCancerDiagnoseInformationVo.setReportUrls(list);
        }
        return res.get(0);
    }

    @Override
    @Transactional
    public void updateReportC3(HospitalColorectalCancerDiagnoseInformationVo hospitalColorectalCancerDiagnoseInformationVo) {
        try {
            Integer mainId = hospitalColorectalCancerDiagnoseInformationVo.getId();
            //修改结直肠癌诊断信息摘录表c3表及从表信息
            hospitalCancerDao.updateReportC3ByID(hospitalColorectalCancerDiagnoseInformationVo);
            //根据c3 ID删除原数据
            hospitalCancerDao.deleteAddendumByC3Id(mainId,Constans.HOSPITAL_INFORMATION_DIAGNOSE_EVALUATION);
            //根据c3 ID删除原数据
            hospitalCancerDao.deleteAddendumByC3Id(mainId,Constans.HOSPITAL_CANCER_INFORMATION_COMPLICATIONS);

            //保存从表信息   诊断评估表
            if(hospitalColorectalCancerDiagnoseInformationVo.getHospitalInformationDiagnoseEvaluationVos()!=null&&hospitalColorectalCancerDiagnoseInformationVo.getHospitalInformationDiagnoseEvaluationVos().size()>0){
                List<HospitalInformationDiagnoseEvaluationVo> hospitalInformationDiagnoseEvaluationVos = hospitalColorectalCancerDiagnoseInformationVo.getHospitalInformationDiagnoseEvaluationVos();
                for (HospitalInformationDiagnoseEvaluationVo hospitalCancerTreatmentInformationVos:hospitalInformationDiagnoseEvaluationVos) {
                    hospitalCancerTreatmentInformationVos.setColorectalCancerDiagnoseInformationId(mainId);
                }
                hospitalCancerDao.saveInformationDiagnoseEvaluationVos(hospitalInformationDiagnoseEvaluationVos);
            }
            //保存从表信息   并发症信息表
            if(hospitalColorectalCancerDiagnoseInformationVo.getHospitalCancerInformationComplicationsVos()!=null&&hospitalColorectalCancerDiagnoseInformationVo.getHospitalCancerInformationComplicationsVos().size()>0){
                List<HospitalCancerInformationComplicationsVo> hospitalCancerInformationComplicationsVos = hospitalColorectalCancerDiagnoseInformationVo.getHospitalCancerInformationComplicationsVos();
                for (HospitalCancerInformationComplicationsVo hospitalCancerInformationComplicationsVo:hospitalCancerInformationComplicationsVos) {
                    hospitalCancerInformationComplicationsVo.setColorectalCancerDiagnoseInformationId(mainId);
                }
                hospitalCancerDao.saveInformationComplicationsVos(hospitalCancerInformationComplicationsVos);
            }

        } catch (Exception e) {
            System.out.println("dao is error   "+e.toString());
            throw new ItSysException(GlobalErrorCode.HOSPITAL_CANCER_RECORD_UPDATE_EVENT_CODE, GlobalErrorCode.HOSPITAL_CANCER_RECORD_UPDATE_CODE_EVENT_MSG);
        }
    }


}
