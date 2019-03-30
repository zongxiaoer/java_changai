package com.yuntongxun.itsys.base.dao;

import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.po.AbnormalEvent;
import com.yuntongxun.itsys.base.po.ItsysDepartment;
import com.yuntongxun.itsys.base.po.dto.abnormalevent.AbnormalEventDto;

/**
 * 异常事件表
 * maxiang
 */
public interface AbnormalEventDao {
    /**
     * 新增异常事件
     * @param abnormalEvent
     * @return
     */
    public Integer addAbnormalEvent(AbnormalEvent abnormalEvent);

    /**
     * 根据登陆用户查询异常事件列表
     * @param abnormalEventDto
     * @param itsysDepartment
     * @param depHospitalType1
     * @return
     */
    ListPageUtil queryAbnormalEventByUser(AbnormalEventDto abnormalEventDto, ItsysDepartment itsysDepartment, Integer depHospitalType1);
}
