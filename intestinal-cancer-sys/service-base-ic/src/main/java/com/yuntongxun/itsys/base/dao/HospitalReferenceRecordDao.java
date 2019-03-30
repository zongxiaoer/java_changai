package com.yuntongxun.itsys.base.dao;/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: HospitalReferenceRecordDao
 * Author:   zongtong
 * Date:     2018/7/16 下午11:59
 * History:
 * <author>          <time>                <version>
 * zongtong           2018/7/16 下午11:59           v1.0.0
 */

import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.po.dto.courier.HospitalReferenceRecordDto;

import java.util.List;

public interface HospitalReferenceRecordDao {
    Integer save(HospitalReferenceRecordDto hospitalReferenceRecordDto);

    List<HospitalReferenceRecordDto> queryEntityByIdAndType(HospitalReferenceRecordDto hospitalReferenceRecordDtos);

    void update(HospitalReferenceRecordDto hospitalReferenceRecordDtos);

    ListPageUtil queryById(Integer id, String hospitalCourierResult);

    void updateForEdir(HospitalReferenceRecordDto hospitalReferenceRecordDto);

    void updateForEdirStatus(String applyEditStatus1, String editStatus1, Integer id);

    void saveArea(HospitalReferenceRecordDto hospitalReferenceRecordDto);

}
