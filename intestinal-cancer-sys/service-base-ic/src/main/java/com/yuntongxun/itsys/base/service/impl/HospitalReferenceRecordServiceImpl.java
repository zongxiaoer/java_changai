package com.yuntongxun.itsys.base.service.impl;/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: HospitalReferenceRecordServiceImpl
 * Author:   zongtong
 * Date:     2018/7/17 上午10:53
 * History:
 * <author>          <time>                <version>
 * zongtong           2018/7/17 上午10:53           v1.0.0
 */

import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.dao.HospitalReferenceRecordDao;
import com.yuntongxun.itsys.base.po.dto.courier.HospitalReferenceRecordDto;
import com.yuntongxun.itsys.base.service.HospitalReferenceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospitalReferenceRecordServiceImpl  implements HospitalReferenceRecordService {
    @Autowired
    private HospitalReferenceRecordDao hospitalReferenceRecordDao;



    @Override
    public List<HospitalReferenceRecordDto> queryEntityByIdAndType(HospitalReferenceRecordDto hospitalReferenceRecordDtos) {
        return hospitalReferenceRecordDao.queryEntityByIdAndType(hospitalReferenceRecordDtos);
    }

    @Override
    public ListPageUtil queryById(Integer id, String hospitalCourierResult) {
        return hospitalReferenceRecordDao.queryById(id,hospitalCourierResult);
    }
}
