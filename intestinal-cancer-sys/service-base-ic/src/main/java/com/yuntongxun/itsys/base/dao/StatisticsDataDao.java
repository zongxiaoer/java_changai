package com.yuntongxun.itsys.base.dao;

import com.yuntongxun.itsys.base.vo.StatisticsDataVo;

import java.util.List;
import java.util.Map;

/**
 * 统计
 */
public interface StatisticsDataDao {
    /**
     * 社区入组人员统计
     * @param sqId
     * @return
     */
    public Map<String,StatisticsDataVo> statisticsSQInsertGroupData(Integer sqId, String inGroupDateStart, String inGroupDateEnd);

}
