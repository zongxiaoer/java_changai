package com.yuntongxun.itsys.base.service;

import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.po.ItsysDepartment;
import com.yuntongxun.itsys.base.po.ViolationScheme;
import com.yuntongxun.itsys.base.po.dto.abnormalevent.AbnormalEventDto;
import com.yuntongxun.itsys.base.po.dto.courier.HospitalReferenceRecordDto;

import java.util.Date;

public interface AbnormalEventService {

    public void addOrUpdateAbnormalEvent(String body,String loginName) throws ItSysException;

    /**
     * @func
     * @desc    根据ID查询违反方案详情
     * @author zongt
     * @create 2018/4/上午11:22:22
     * @request
     * @response
     **/
    String queryHospitalViolationSchemeToStringById(ViolationScheme violationScheme);

    /**
     * 社区医院查看全部异常事件
     * @param abnormalEventDto
     * @param itsysDepartment
     * @param depHospitalType1
     * @return
     */
    ListPageUtil queryAbnormalEventByUser(AbnormalEventDto abnormalEventDto, ItsysDepartment itsysDepartment, Integer depHospitalType1);

    /**
     * 第一次新增违反方案
     * @param violationScheme
     */
    public Object addVScheme(ViolationScheme violationScheme,String loginName);

    /**
     * 修改增添违反方案内容
     * @param violationScheme
     */
    public void updateVScheme(ViolationScheme violationScheme,String loginName,HospitalReferenceRecordDto hospitalReferenceRecordDto);

    //获取当前系统时间
    public Object getTime();

    ViolationScheme queryHospitalViolationSchemeById(Integer id,String sid);

    void updateVSchemeInArea(ViolationScheme violationScheme, Integer communityDeptId, Integer areaDeptId, HospitalReferenceRecordDto hospitalReferenceRecordDto);

}
