package com.yuntongxun.itsys.base.dao;/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: ResultAllocationDao
 * Author:   zongtong
 * Date:     2018/12/28 下午5:23
 * History:
 * <author>          <time>                <version>
 * zongtong           2018/12/28 下午5:23           v1.0.0
 */

import com.yuntongxun.itsys.base.po.dto.allocation.ResultAllocation;

import java.util.List;

public interface ResultAllocationDao {
    List<ResultAllocation> queryBystatus(Integer beginStatus, Integer issuetype);

    int getRecruitByRuleIdAndBookingDay(Integer id, String afterDay);

    void updateById(Integer id, Integer status);

}
