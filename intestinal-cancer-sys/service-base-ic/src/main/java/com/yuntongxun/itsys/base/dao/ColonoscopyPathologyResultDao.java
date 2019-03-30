package com.yuntongxun.itsys.base.dao;

import com.yuntongxun.itsys.base.po.ColonoscopyPathologyResult;

import java.util.List;

/**
 * 结肠镜病理结果表Dao
 */
public interface ColonoscopyPathologyResultDao {
    public Integer addColonoscopyPathologyResult(ColonoscopyPathologyResult colonoscopyPathologyResult);

    ColonoscopyPathologyResult queryColonoscopyPathologyResultById(Integer id,String sid);

    List<ColonoscopyPathologyResult> queryColonoscopyPathologyResultById(String sid,Integer colonoscopyResultId);

    void deleteByIDs(String substring);

    Integer updateColonoscopyPathologyResult(ColonoscopyPathologyResult colonoscopyPathologyResult);

    void delPathologyById(Integer id);
}
