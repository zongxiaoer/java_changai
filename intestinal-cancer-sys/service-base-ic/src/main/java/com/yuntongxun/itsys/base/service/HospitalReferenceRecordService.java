package com.yuntongxun.itsys.base.service;/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: HospitalReferenceRecordService
 * Author:   zongtong
 * Date:     2018/7/17 上午10:48
 * History:
 * <author>          <time>                <version>
 * zongtong           2018/7/17 上午10:48           v1.0.0
 */

import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.po.dto.courier.HospitalReferenceRecordDto;

import java.util.List;

public interface HospitalReferenceRecordService {

    List<HospitalReferenceRecordDto> queryEntityByIdAndType(HospitalReferenceRecordDto hospitalReferenceRecordDtos);

    ListPageUtil queryById(Integer id, String hospitalCourierResult);
}
