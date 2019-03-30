package com.yuntongxun.itsys.base.service;

import com.yuntongxun.itsys.base.po.ScreeningNotification;
import com.yuntongxun.itsys.base.po.dto.courier.HospitalReferenceRecordDto;

import java.util.List;

/**
 * 肠镜筛查报告书service
 * maxiang
 */
public interface ScreeningNotificationService {

    public void addScreeningNotificationt(String body,String loginName);

    List<ScreeningNotification>  queryById(Integer id);

    void updateScreeningNotificationt(ScreeningNotification screeningNotification, HospitalReferenceRecordDto hospitalReferenceRecordDto);

    void updateScreeningNotificationt(ScreeningNotification newResult, String loginName,HospitalReferenceRecordDto hospitalReferenceRecordDto);
}
