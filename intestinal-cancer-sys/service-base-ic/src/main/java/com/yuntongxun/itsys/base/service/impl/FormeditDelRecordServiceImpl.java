/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: FormeditDelRecordServiceImpl
 * Author:   sun
 * Date:     2018/8/7 18:06
 * History:
 * <author>          <time>                <version>
 * sun           2018/8/7 18:06           v1.0.0
 */
package com.yuntongxun.itsys.base.service.impl;
import com.yuntongxun.itsys.base.dao.FormeditDelRecordDao;
import com.yuntongxun.itsys.base.po.HospitalFormeditDelRecord;
import com.yuntongxun.itsys.base.service.FormeditDelRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 一句话功能简述
 *
 * @author sun
 * @create 2018/8/7
 * @since v1.0.0
 */
@Service
public class FormeditDelRecordServiceImpl implements FormeditDelRecordService {
    @Autowired
    private FormeditDelRecordDao formeditDelRecordDao;

    @Override
    public Integer addFormeditDelRecord(HospitalFormeditDelRecord hospitalFormeditDelRecord) {
        return formeditDelRecordDao.addFormeditDelRecord(hospitalFormeditDelRecord);
    }
}
