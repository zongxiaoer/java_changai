package com.yuntongxun.itsys.base.dao;/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: HosAllocationRuleInfoDao
 * Author:   zongtong
 * Date:     2018/12/27 下午3:15
 * History:
 * <author>          <time>                <version>
 * zongtong           2018/12/27 下午3:15           v1.0.0
 */

import com.yuntongxun.itsys.base.po.dto.allocation.HosAllocationRuleInfoDto;

public interface HosAllocationRuleInfoDao {
    int save(HosAllocationRuleInfoDto allocationDto);

}
