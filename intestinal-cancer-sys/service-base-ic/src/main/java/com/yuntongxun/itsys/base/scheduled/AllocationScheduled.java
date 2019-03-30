package com.yuntongxun.itsys.base.scheduled;/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: AllocationScheduled
 * Author:   zongtong
 * Date:     2018/12/28 下午2:52
 * History:
 * <author>          <time>                <version>
 * zongtong           2018/12/28 下午2:52           v1.0.0
 */

import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.DateUtil;
import com.yuntongxun.itsys.base.common.util.GlobalErrorCode;
import com.yuntongxun.itsys.base.common.util.JSONUtils;
import com.yuntongxun.itsys.base.dao.HcrAllocationDao;
import com.yuntongxun.itsys.base.po.dto.allocation.AllocationDto;
import com.yuntongxun.itsys.base.po.dto.allocation.HosAllocationRuleInfoDto;
import com.yuntongxun.itsys.base.po.dto.allocation.ResultAllocation;
import com.yuntongxun.itsys.base.service.HcrAllocationService;
import com.yuntongxun.itsys.base.service.impl.HcrAllocationServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class AllocationScheduled {
    private final Logger logger = LogManager.getLogger(AllocationScheduled.class);

    @Value("${scheduling.enabled.allocation}")
    private Boolean allocation;

    @Autowired
    private HcrAllocationService hcrAllocationService;

    @Autowired
    private HcrAllocationDao hcrAllocationDao;


    /**
     * 开始没有放号任务
     */
    @Scheduled(cron = " 0 0 1 * * ?")//0 0 1 * * ?  0 0/1 * * * ?
    public void checkRuning() {
        if (allocation) {
            allocationRuleNoStart();
        }
    }

    public void allocationRuleNoStart() {
        logger.info("start booking job");
        /**
         * 判断今天  今天+13天
         *
         *查询规则1、规则2
         *循环
         *  都是开始的，所以开头到14天肯定跑了，所以
         *      new Date()+13
         *      判断是否已经放过号，放过停止
         *      走规则1 和 规则2 的逻辑 返回（1。放号数据  2。状态变更 结束 ）
         *
         */

        //规则1

        //规则集合
        List<HosAllocationRuleInfoDto> hosAllocationRuleInfoDtos = new ArrayList<>();

        //放号集合
        List<AllocationDto> allocationDtos = new ArrayList<>();

        List<ResultAllocation> resultAllocations = hcrAllocationService.queryByRuleSataus(Constans.BEGIN_STATUS1, Constans.ISSUETYPE1);
        for (ResultAllocation resultAllocation : resultAllocations) {

            String ruleEnd = DateUtil.DateToString(resultAllocation.getRuleEnd(), "yyyy-MM-dd");
            String afterDay = DateUtil.DateToString(DateUtil.addDaytoDate(new Date(), 13), "yyyy-MM-dd");
            String ruleBegin = DateUtil.DateToString(resultAllocation.getRuleBegin(), "yyyy-MM-dd");
            String newDay = DateUtil.DateToString(new Date(), "yyyy-MM-dd");

            long beginIntervalDays = DateUtil.getIntervalDays(afterDay, newDay);
            for (int i = 0; i <= beginIntervalDays; i++) {
                String date = DateUtil.DateToString(DateUtil.addDaytoDate(new Date(), i), "yyyy-MM-dd");
                //校验时间是否在放号时间里面
                long beginDays = DateUtil.getIntervalDays(date, ruleBegin);
                long endDays = DateUtil.getIntervalDays(date, ruleEnd);

                if (beginDays >= 0 && endDays <= 0) {

                } else {
                    continue;
                }

                //修改规则状态
                HosAllocationRuleInfoDto hosAllocationRuleInfoDto = checkHosAllocationRule(resultAllocation.getId(), date, ruleEnd);
                if (hosAllocationRuleInfoDto != null) {
                    hosAllocationRuleInfoDtos.add(hosAllocationRuleInfoDto);
                }
                //放号对象
                AllocationDto allocationDto = checkAllocationDto(resultAllocation.getId(), date, resultAllocation);
                if (allocationDto != null) {
                    allocationDtos.add(allocationDto);
                }

            }

        }

        //规则2
        List<ResultAllocation> resultAllocations1 = hcrAllocationService.queryByRuleSataus(Constans.BEGIN_STATUS1, Constans.ISSUETYPE2);
        for (ResultAllocation resultAllocation : resultAllocations1) {

            String ruleEnd = DateUtil.DateToString(resultAllocation.getRuleEnd(), "yyyy-MM-dd");
            String ruleBegin = DateUtil.DateToString(resultAllocation.getRuleBegin(), "yyyy-MM-dd");
            String afterDay = DateUtil.DateToString(DateUtil.addDaytoDate(new Date(), 13), "yyyy-MM-dd");
            String newDay = DateUtil.DateToString(new Date(), "yyyy-MM-dd");


            long beginIntervalDays = DateUtil.getIntervalDays(afterDay, newDay);
            for (int i = 0; i <= beginIntervalDays; i++) {
                String date = DateUtil.DateToString(DateUtil.addDaytoDate(new Date(), i), "yyyy-MM-dd");
                //校验时间是否在放号时间里面
                long beginDays = DateUtil.getIntervalDays(date, ruleBegin);
                long endDays = DateUtil.getIntervalDays(date, ruleEnd);

                if (beginDays >= 0 && endDays <= 0) {

                } else {
                    continue;
                }

                //修改规则状态
                HosAllocationRuleInfoDto hosAllocationRuleInfoDto = checkHosAllocationRule(resultAllocation.getId(), date, ruleEnd);
                if (hosAllocationRuleInfoDto != null) {
                    hosAllocationRuleInfoDtos.add(hosAllocationRuleInfoDto);
                }
                //放号对象
                AllocationDto allocationDto = checkAllocationDto(resultAllocation.getId(), date, resultAllocation);
                if (allocationDto != null) {
                    allocationDtos.add(allocationDto);
                }

            }
        }

        //批量插入放号
        for (AllocationDto allocationDto1 : allocationDtos) {
            try {
                //根据放号冗余表去判断是否停诊
                int i=hcrAllocationDao.queryByUseStatus(allocationDto1);
                if(i>0){

                }else {
                    hcrAllocationDao.save(allocationDto1);
                }
            } catch (Exception e) {
                logger.info("newInsert  定时任务根据时间录入放号表 " + e.toString());
                throw new ItSysException(GlobalErrorCode.NEWINSERT_HOSALLOCATION_RULE_ISERROR_CODE, GlobalErrorCode.NEWINSERT_HOSALLOCATION_RULE_ISERROR_MSG);
            }
        }

        //批量修改规则
        for (HosAllocationRuleInfoDto hosAllocationRuleInfoDto : hosAllocationRuleInfoDtos) {
            hcrAllocationService.updateById(hosAllocationRuleInfoDto.getId(), hosAllocationRuleInfoDto.getBeginStatus());
        }
        logger.info("end booking job");
    }

    AllocationDto add(ResultAllocation resultAllocation, Date day) {
        AllocationDto allocationDtott = new AllocationDto();
        if (Constans.ISSUETYPE1.equals(resultAllocation.getIssueType())) {
            allocationDtott.setAmount(resultAllocation.getNumToTime());
            allocationDtott.setAreaDeptId(String.valueOf(resultAllocation.getAreaDeptId()));
            allocationDtott.setStartTime(resultAllocation.getBeginTimeToTime());
            allocationDtott.setEndTime(resultAllocation.getEndTimeToTime());
            allocationDtott.setExaminationPlace(resultAllocation.getExaminationPlace());
            allocationDtott.setDateCreated(new Date());
            allocationDtott.setUpdateTime(new Date());
            allocationDtott.setRuleId(resultAllocation.getId());
            allocationDtott.setUseStatus(Constans.USESTATUS1);
            allocationDtott.setIssueType(resultAllocation.getIssueType());
            allocationDtott.setCommunityDeptIdInfo(resultAllocation.getCommunityDeptIdInfoTo());
            allocationDtott.setSignature(resultAllocation.getSignature());
            allocationDtott.setReservationDate(day);
        } else if (Constans.ISSUETYPE2.equals(resultAllocation.getIssueType())) {
            allocationDtott.setAmount(resultAllocation.getNumTo());
            allocationDtott.setAreaDeptId(String.valueOf(resultAllocation.getAreaDeptId()));
            allocationDtott.setStartTime(resultAllocation.getBeginTimeTo());
            allocationDtott.setEndTime(resultAllocation.getEndTimeTo());
            allocationDtott.setExaminationPlace(resultAllocation.getExaminationPlace());
            allocationDtott.setSignature(resultAllocation.getSignature());
            allocationDtott.setDateCreated(new Date());
            allocationDtott.setUpdateTime(new Date());
            allocationDtott.setRuleId(resultAllocation.getId());
            allocationDtott.setUseStatus(Constans.USESTATUS1);
            allocationDtott.setIssueType(resultAllocation.getIssueType());
            allocationDtott.setCommunityDeptId(String.valueOf(resultAllocation.getCommunityDeptIdTo()));
            allocationDtott.setReservationDate(day);
        }

        return allocationDtott;
    }

    HosAllocationRuleInfoDto checkHosAllocationRule(Integer ruleId, String afterDay, String ruleEnd) {
        long endIntervalDays = DateUtil.getIntervalDays(afterDay, ruleEnd);
        if (endIntervalDays == 0) {
            HosAllocationRuleInfoDto hosAllocationRuleInfoDto = new HosAllocationRuleInfoDto();
            hosAllocationRuleInfoDto.setId(ruleId);
            hosAllocationRuleInfoDto.setBeginStatus(Constans.BEGIN_STATUS3);
            return hosAllocationRuleInfoDto;
        }
        return null;
    }

    AllocationDto checkAllocationDto(Integer ruleId, String days, ResultAllocation resultAllocation) {

        //根据时间和规则id查看是否存在
        int count = hcrAllocationService.getRecruitByRuleIdAndBookingDay(ruleId, days);

        if (count > 0) {
            return null;
        }
        //放号数量
        if (Constans.RULE_TYPE1 == resultAllocation.getRuleType()) {
            return add(resultAllocation, DateUtil.formatDateStr(days,"yyyy-MM-dd"));

        } else if (Constans.RULE_TYPE2 == resultAllocation.getRuleType()) {
            List<Integer> list = JSONUtils.toList(resultAllocation.getWeekInfo(), Integer.class);
            int number = DateUtil.getWeek(days).getNumber();
            if (list.contains(number)) {
                return add(resultAllocation,DateUtil.formatDateStr(days,"yyyy-MM-dd"));
            }
        }
        return null;
    }


}
