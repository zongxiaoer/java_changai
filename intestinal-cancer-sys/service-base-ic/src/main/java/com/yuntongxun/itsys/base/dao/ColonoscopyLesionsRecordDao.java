package com.yuntongxun.itsys.base.dao;

import com.yuntongxun.itsys.base.po.ColonoscopyLesionsRecord;

import java.util.List;

/**
 * maxiang
 * 结肠镜结果病变记录表
 */
public interface ColonoscopyLesionsRecordDao {
    /**
     * 添加结肠镜结果病变记录表
     * @param colonoscopyLesionsRecord
     * @return
     */
    public Integer addColonoscopyLesionsRecord(ColonoscopyLesionsRecord colonoscopyLesionsRecord);

    /**
     * @func
     * @desc    根据结肠镜结果记录表id获取结肠镜结果病变记录表集合
     * @author zongt
     * @create 2018/4/20 上午11:36
     * @request
     * @response
     **/
    List<ColonoscopyLesionsRecord> queryByColonoscopyResultId(Integer colonoscopyResultId);

    void deleteByIDs(String substring);

    void deleteByResultId(Integer Id);
}
