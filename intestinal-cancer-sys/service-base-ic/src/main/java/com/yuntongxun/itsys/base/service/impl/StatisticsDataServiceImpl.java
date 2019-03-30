package com.yuntongxun.itsys.base.service.impl;

import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.GlobalErrorCode;
import com.yuntongxun.itsys.base.common.util.JSONUtils;
import com.yuntongxun.itsys.base.dao.StatisticsDataDao;
import com.yuntongxun.itsys.base.service.StatisticsDataService;
import com.yuntongxun.itsys.base.vo.Response;
import com.yuntongxun.itsys.base.vo.StatisticsDataVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 统计serviceImpl
 */
public class StatisticsDataServiceImpl implements StatisticsDataService{

    final Logger log = LogManager.getLogger(StatisticsDataServiceImpl.class);
    @Autowired
    private StatisticsDataDao statisticsDataDao;

    /**
     * 社区统计
     * @param sqId
     * @return
     */
    @Override
    public List<StatisticsDataVo> statisticsSQData(Integer sqId, String inGroupDateStart, String inGroupDateEnd){

        if(sqId==null){
            log.info("statisticsSQData 输入参数sqId不存在/错误");
            throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE,GlobalErrorCode.PARAMETER_ERR_MSG);
        }
        //入组人员查询
        Map<String,StatisticsDataVo> insertGroupMap = statisticsDataDao.statisticsSQInsertGroupData(sqId,inGroupDateStart,inGroupDateEnd);
        //已预约肠镜+实际接受肠镜
        //封装数据
        List<StatisticsDataVo> result = getStatisticsList();
        for(int i=0;i<result.size();i++){
            //入组人员
            StatisticsDataVo vo = result.get(i);
            StatisticsDataVo insertGroupVo = insertGroupMap.get(vo.getGroupName());
            if(insertGroupVo!=null){
                vo.setPerpson(insertGroupVo.getPerpson());
            }
            //已预约肠镜+实际接受肠镜

            //dna

            //实际接受fit+fit阳性+fit阴性
        }
        return result;
    }

    /*
   获取统计返回集合
     */
    private  List<StatisticsDataVo> getStatisticsList(){
        List<StatisticsDataVo> result = new ArrayList<StatisticsDataVo>();
        StatisticsDataVo vo = new StatisticsDataVo();
        vo.setGroupName("A");
        StatisticsDataVo vo2 = new StatisticsDataVo();
        vo.setGroupName("B");
        StatisticsDataVo vo3 = new StatisticsDataVo();
        vo.setGroupName("C");
        StatisticsDataVo vo4 = new StatisticsDataVo();
        vo.setGroupName("C高危");
        StatisticsDataVo vo5 = new StatisticsDataVo();
        vo.setGroupName("C低危");
        return  result;
    }

}
