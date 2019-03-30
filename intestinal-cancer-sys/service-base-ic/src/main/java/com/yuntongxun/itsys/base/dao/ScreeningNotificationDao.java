package com.yuntongxun.itsys.base.dao;

import com.yuntongxun.itsys.base.po.ScreeningNotification;
import com.yuntongxun.itsys.base.vo.ColonoscopyNotificationVo;

import java.util.List;

/**
 * 病历筛查告知书Dao接口
 * maxiang
 */
public interface ScreeningNotificationDao {
    public Integer addScreeningNotification(ScreeningNotification screeningNotification);

	public ColonoscopyNotificationVo get(int id);

    List<ScreeningNotification> queryById(Integer id);

    void updateScreeningNotificationt(ScreeningNotification screeningNotification);

    void delNotificationById(Integer id);
}
