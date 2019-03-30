package com.yuntongxun.itsys.base.service;

import com.yuntongxun.itsys.base.vo.StatisticsDataVo;

import java.util.List;

/**
 * 统计service
 */
public interface StatisticsDataService {
    /**
     * 社区统计
     * @param sqId
     * @return
     */
    public List<StatisticsDataVo> statisticsSQData(Integer sqId, String inGroupDateStart, String inGroupDateEnd);
}
