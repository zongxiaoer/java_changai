package com.yuntongxun.itsys.base.po.dto.allocation;/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: HosAllocationRuleInfoDto
 * Author:   zongtong
 * Date:     2018/12/27 下午2:04
 * History:
 * <author>          <time>                <version>
 * zongtong           2018/12/27 下午2:04           v1.0.0
 */

import com.yuntongxun.itsys.base.po.HosAllocationRuleInfoPO;

import java.util.Date;
import java.util.List;

public class HosAllocationRuleInfoDto extends HosAllocationRuleInfoPO {

    private List<HosAllocationRuleTimeInfoDto> hosAllocationRuleTimeInfoDtos;
    private List<HosAllocationRuleDeptInfoDto> hosAllocationRuleDeptInfoDtos;


    List<Integer> weeks;

    private String examinationPlace;
    private String signature;


    private String ruleBeginToString;

    private String ruleEndToString;

    private Integer operationSource;

    private Integer pageSize;
    private Integer pageNo;

    private String noticeStatus;


    public List<HosAllocationRuleTimeInfoDto> getHosAllocationRuleTimeInfoDtos() {
        return hosAllocationRuleTimeInfoDtos;
    }

    public void setHosAllocationRuleTimeInfoDtos(List<HosAllocationRuleTimeInfoDto> hosAllocationRuleTimeInfoDtos) {
        this.hosAllocationRuleTimeInfoDtos = hosAllocationRuleTimeInfoDtos;
    }

    public List<HosAllocationRuleDeptInfoDto> getHosAllocationRuleDeptInfoDtos() {
        return hosAllocationRuleDeptInfoDtos;
    }

    public void setHosAllocationRuleDeptInfoDtos(List<HosAllocationRuleDeptInfoDto> hosAllocationRuleDeptInfoDtos) {
        this.hosAllocationRuleDeptInfoDtos = hosAllocationRuleDeptInfoDtos;
    }


    public List<Integer> getWeeks() {
        return weeks;
    }

    public void setWeeks(List<Integer> weeks) {
        this.weeks = weeks;
    }

    public String getExaminationPlace() {
        return examinationPlace;
    }

    public void setExaminationPlace(String examinationPlace) {
        this.examinationPlace = examinationPlace;
    }

    public String getRuleBeginToString() {
        return ruleBeginToString;
    }

    public void setRuleBeginToString(String ruleBeginToString) {
        this.ruleBeginToString = ruleBeginToString;
    }

    public String getRuleEndToString() {
        return ruleEndToString;
    }

    public void setRuleEndToString(String ruleEndToString) {
        this.ruleEndToString = ruleEndToString;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Integer getOperationSource() {
        return operationSource;
    }

    public void setOperationSource(Integer operationSource) {
        this.operationSource = operationSource;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public String getNoticeStatus() {
        return noticeStatus;
    }

    public void setNoticeStatus(String noticeStatus) {
        this.noticeStatus = noticeStatus;
    }
}
