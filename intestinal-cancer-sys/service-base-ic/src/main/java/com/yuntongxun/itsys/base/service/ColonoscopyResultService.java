package com.yuntongxun.itsys.base.service;

import com.yuntongxun.itsys.base.po.ColonoscopyLesionsRecord;
import com.yuntongxun.itsys.base.po.ColonoscopyResult;
import com.yuntongxun.itsys.base.po.HospitalColonoscopyRecord;
import com.yuntongxun.itsys.base.po.HospitalColonoscopyResult;
import com.yuntongxun.itsys.base.po.dto.courier.HospitalReferenceRecordDto;

import java.util.List;

/**
 * 肠镜报告表单 service
 * maxiang
 */
public interface ColonoscopyResultService {

    public void addColonoscopyResult(String body,String loginName);


    /**
     * @func
     * @desc   根据id结肠镜检查结果详情
     * @author zongt
     * @create 2018/4/上午11:34:34
     * @request
     * @response
     **/
    String queryColonoscopyResultToStringById(HospitalColonoscopyResult hospitalColonoscopyResult);


    List<ColonoscopyResult> queryById(Integer id);

    List<ColonoscopyLesionsRecord> queryByColonoscopyResultId(Integer id);

    HospitalColonoscopyRecord delNotificationState(ColonoscopyResult newResult);

    void updateColonoscopyResult(ColonoscopyResult newResult, ColonoscopyResult oldResult, String loginName,HospitalReferenceRecordDto hospitalReferenceRecordDto);

    void updateColonoscopyResult(ColonoscopyResult colonoscopyResult, ColonoscopyResult colonoscopyResult1,HospitalReferenceRecordDto hospitalReferenceRecordDto);

    void sendMsgForDelNotification(String loginName, HospitalColonoscopyRecord colonoscopyRecord, String formType);
}
