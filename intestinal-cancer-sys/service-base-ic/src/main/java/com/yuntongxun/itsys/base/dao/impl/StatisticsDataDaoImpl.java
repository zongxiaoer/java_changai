package com.yuntongxun.itsys.base.dao.impl;

import com.yuntongxun.itsys.base.dao.StatisticsDataDao;
import com.yuntongxun.itsys.base.po.HospitalColonoscopyRecord;
import com.yuntongxun.itsys.base.vo.StatisticsDataVo;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统计DaoImpl
 */
public class StatisticsDataDaoImpl implements StatisticsDataDao {

    private final Logger log = LogManager.getLogger(StatisticsDataDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbc;

    /**
     * 社区入组人员统计
     * @param sqId
     * @return
     */
    @Override
    public Map<String,StatisticsDataVo> statisticsSQInsertGroupData(Integer sqId,String inGroupDateStart,String inGroupDateEnd){
        StringBuffer sql = new StringBuffer("");
        List args = new ArrayList();
        sql.append("SELECT r.`group` AS groupName,count(sid) AS perpson FROM hospital_intestine_review r WHERE r.`group` IS NOT NULL ");
        if(sqId != null){
            sql.append(" and r.community_dept_id = ? ");
            args.add(sqId);
        }
        if(StringUtils.isNotBlank(inGroupDateStart)){
            sql.append(" and r.in_group_date >= ? ");
            args.add(inGroupDateStart + " 00:00:00");
        }
        if(StringUtils.isNotBlank(inGroupDateEnd)){
            sql.append(" and r.in_group_date <= ? ");
            args.add(inGroupDateEnd+" 23:59:59");
        }
        sql.append(" GROUP BY r.`group` UNION " +
                "SELECT CASE WHEN (r.risk_level = 1) THEN 'C低危' ELSE 'C高危' END AS groupName, count(sid) AS perpson " +
                "FROM hospital_intestine_review r WHERE r.`group` IS NOT NULL AND r.risk_level IS NOT NULL AND r.`group` = 'C' ");
        if(sqId != null){
            sql.append(" and r.community_dept_id = ? ");
            args.add(sqId);
        }
        if(StringUtils.isNotBlank(inGroupDateStart)){
            sql.append(" and r.in_group_date >= ? ");
            args.add(inGroupDateStart + " 00:00:00");
        }
        if(StringUtils.isNotBlank(inGroupDateEnd)){
            sql.append(" and r.in_group_date <= ? ");
            args.add(inGroupDateEnd+" 23:59:59");
        }
        sql.append(" GROUP BY r.risk_level");

        List<StatisticsDataVo> statisticsDataVoList =
                jdbc.query(sql.toString(),args.toArray(),new BeanPropertyRowMapper<StatisticsDataVo>(StatisticsDataVo.class));
        Map<String,StatisticsDataVo> result = new HashMap<String,StatisticsDataVo>();
        if(statisticsDataVoList!=null&&statisticsDataVoList.size()>0){
            for(StatisticsDataVo vo : statisticsDataVoList){
                result.put(vo.getGroupName(),vo);
            }
        }
        return result;
    }


    /**
     * 社区肠镜人员统计
     * @param sqId
     * @return
     */
    public Map<String,StatisticsDataVo> statisticsSQEnteroscopyData(Integer sqId,String inGroupDateStart,String inGroupDateEnd){
        StringBuffer sql = new StringBuffer("");
        List args = new ArrayList();
        sql.append("SELECT r.`group` AS groupName,count(sid) AS perpson FROM hospital_intestine_review r WHERE r.`group` IS NOT NULL ");
        if(sqId != null){
            sql.append(" and r.community_dept_id = ? ");
            args.add(sqId);
        }
        if(StringUtils.isNotBlank(inGroupDateStart)){
            sql.append(" and r.in_group_date >= ? ");
            args.add(inGroupDateStart + " 00:00:00");
        }
        if(StringUtils.isNotBlank(inGroupDateEnd)){
            sql.append(" and r.in_group_date <= ? ");
            args.add(inGroupDateEnd+" 23:59:59");
        }
        sql.append(" GROUP BY r.`group` UNION " +
                "SELECT CASE WHEN (r.risk_level = 1) THEN 'C低危' ELSE 'C高危' END AS groupName, count(sid) AS perpson " +
                "FROM hospital_intestine_review r WHERE r.`group` IS NOT NULL AND r.risk_level IS NOT NULL AND r.`group` = 'C' ");
        if(sqId != null){
            sql.append(" and r.community_dept_id = ? ");
            args.add(sqId);
        }
        if(StringUtils.isNotBlank(inGroupDateStart)){
            sql.append(" and r.in_group_date >= ? ");
            args.add(inGroupDateStart + " 00:00:00");
        }
        if(StringUtils.isNotBlank(inGroupDateEnd)){
            sql.append(" and r.in_group_date <= ? ");
            args.add(inGroupDateEnd+" 23:59:59");
        }
        sql.append(" GROUP BY r.risk_level");

        List<StatisticsDataVo> statisticsDataVoList =
                jdbc.query(sql.toString(),args.toArray(),new BeanPropertyRowMapper<StatisticsDataVo>(StatisticsDataVo.class));
        Map<String,StatisticsDataVo> result = new HashMap<String,StatisticsDataVo>();
        if(statisticsDataVoList!=null&&statisticsDataVoList.size()>0){
            for(StatisticsDataVo vo : statisticsDataVoList){
                result.put(vo.getGroupName(),vo);
            }
        }
        return result;
    }


}
