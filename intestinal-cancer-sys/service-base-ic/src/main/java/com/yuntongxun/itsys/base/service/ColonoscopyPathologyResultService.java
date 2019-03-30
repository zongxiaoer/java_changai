package com.yuntongxun.itsys.base.service;

import com.yuntongxun.itsys.base.po.ColonoscopyPathologyDiagnosisRecord;
import com.yuntongxun.itsys.base.po.ColonoscopyPathologyResult;
import com.yuntongxun.itsys.base.po.HospitalColonoscopyResult;
import com.yuntongxun.itsys.base.po.dto.courier.HospitalReferenceRecordDto;

import java.util.List;

/**
 * 肠镜病理报告 service
 * maxiang
 */
public interface ColonoscopyPathologyResultService {

    public void addColonoscopyPathologyResult(String body,String loginName);

    /**
     * @func
     * @desc    根据Id获取病理检查结果详情
     * @author zongt
     * @create 2018/4/20 上午11:26
     * @request
     * @response
     **/
    String queryColonoscopyPathologyResult(HospitalColonoscopyResult hospitalColonoscopyResult);

    ColonoscopyPathologyResult queryByID(Integer id, String sid);

    List<ColonoscopyPathologyDiagnosisRecord> queryColonoscopyPathologyDiagnosisRecordsByPathologyResultId(Integer id);

    void updatePathologyResult(ColonoscopyPathologyResult newResult, ColonoscopyPathologyResult oldResult, String loginName,HospitalReferenceRecordDto hospitalReferenceRecordDto);

    void updateColonoscopyPathologyResult(ColonoscopyPathologyResult colonoscopyPathologyResult1, ColonoscopyPathologyResult colonoscopyPathologyResult, HospitalReferenceRecordDto hospitalReferenceRecordDto);

}

