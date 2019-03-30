package com.yuntongxun.itsys.base.dao.impl;

import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.dao.AbnormalEventDao;
import com.yuntongxun.itsys.base.po.AbnormalEvent;
import com.yuntongxun.itsys.base.po.ItsysDepartment;
import com.yuntongxun.itsys.base.po.dto.abnormalevent.AbnormalEventDto;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 异常事件信息DaoImpl
 */
@Repository
public class AbnormalEventDaoImpl implements AbnormalEventDao {

    private final Logger log = LogManager.getLogger(AbnormalEventDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbc;


    /**
     * 新增异常事件
     *
     * @param abnormalEvent
     * @return
     */
    @Override
    public Integer addAbnormalEvent(AbnormalEvent abnormalEvent) {
        String sql = "insert into hospital_abnormal_event(sid,stage,event_type,data_id,community_dept_id,area_dept_id,date_created,update_time) values(?,?,?,?,?,?,now(),now())";
        return jdbc.update(sql, new Object[]{abnormalEvent.getSid(), abnormalEvent.getStage(), abnormalEvent.getEventType(), abnormalEvent.getDataId(), abnormalEvent.getCommunityDeptId(), abnormalEvent.getAreaDeptId()});
    }

    /**
     * 查询异常事件列表
     *
     * @param abnormalEventDto
     * @param itsysDepartment
     * @param depHospitalType1
     * @return
     */
    @Override
    public ListPageUtil queryAbnormalEventByUser(AbnormalEventDto abnormalEventDto, ItsysDepartment itsysDepartment, Integer depHospitalType1) {
        log.info("@dao queryAbnormalEventByUser 查询参数 abnormalEventDto = {}", abnormalEventDto.toString());

        StringBuilder sql = new StringBuilder();
        @SuppressWarnings("rawtypes")
        List args = new ArrayList();
        sql.append(" SELECT  ir.sid,  vs.id AS 'D2_id', ir.overall_status_cy ,ir.community_dept_id, ir.area_dept_id,   ir.`name`,  id_a.`name` AS 'areaName',  id_c.`name` AS 'communityName',  iu.nickName,  ir.phone,  " +
                " CASE WHEN ir.`group` = 'A' THEN  'A' WHEN ir.`group` = 'B' THEN  'B' WHEN ir.`group` = 'C' AND ir.risk_level IS NULL THEN  'C' WHEN ir.`group` = 'C' AND ir.risk_level = 1 THEN  'Cd' WHEN ir.`group` = 'C' AND ir.risk_level = 2 THEN  'Cg' ELSE  NULL END `group`,  " +
                " ir.in_group_date,  vs.update_time AS 'D2_update_time' " +
                " FROM  " +
                " hospital_violation_scheme vs " +
                " LEFT JOIN hospital_intestine_review ir ON vs.sid = ir.sid " +
                " LEFT JOIN itsys_user iu ON ir.create_user = iu.loginName " +
                " LEFT JOIN itsys_department id_c ON id_c.id = ir.community_dept_id   " +
                " LEFT JOIN itsys_department id_a ON id_a.id = ir.area_dept_id  WHERE 1=1 ");

        if (StringUtils.isNotBlank(abnormalEventDto.getSid())) {
            sql.append(" and ir.sid like ? ");
            args.add("%" + abnormalEventDto.getSid() + "%");
        }

        if (StringUtils.isNotBlank(abnormalEventDto.getName())) {
            sql.append(" and ir.name like ? ");
            args.add("%" + abnormalEventDto.getName() + "%");
        }

        if (StringUtils.isNotBlank(abnormalEventDto.getPhone())) {
            sql.append(" and ir.phone like ? ");
            args.add("%" +abnormalEventDto.getPhone()+ "%");
        }

        if (StringUtils.isNotBlank(abnormalEventDto.getGroup())) {
            if (abnormalEventDto.getGroup().equals("Cg")) {
                sql.append(" and ir.`group` = 'C' and ir.risk_level = 2");
            } else if (abnormalEventDto.getGroup().equals("Cd")) {
                sql.append(" and ir.`group` = 'C' and ir.risk_level = 1");
            } else {
                sql.append(" and ir.`group` = ? ");
                args.add(abnormalEventDto.getGroup());
            }
        }

        if (null != abnormalEventDto.getCommunityDeptId()) {
            sql.append(" and ir.community_dept_id = ? ");
            args.add(abnormalEventDto.getCommunityDeptId());
        } else {
            if (Objects.equals(depHospitalType1, Constans.DEP_HOSPITAL_TYPE1)) {
                sql.append(" and ir.community_dept_id = ? ");
                args.add(itsysDepartment.getId());
            } else if (Objects.equals(depHospitalType1, Constans.DEP_HOSPITAL_TYPE2)) {
                sql.append(" and ir.area_dept_id = ? ");
                args.add(itsysDepartment.getId());
            }
        }

        if (StringUtils.isNotBlank(abnormalEventDto.getInGroupDateStart())) {
            sql.append(" and ir.in_group_date >= ? ");
            args.add(abnormalEventDto.getInGroupDateStart() + " 00:00:00");
        }

        if (StringUtils.isNotBlank(abnormalEventDto.getInGroupDateEND())) {
            sql.append(" and ir.in_group_date <= ? ");
            args.add(abnormalEventDto.getInGroupDateEND() + " 23:59:59");
        }


        if (null != abnormalEventDto.getOverallStatusCy()) {
            sql.append(" and ir.overall_status_cy = ? ");
            args.add(abnormalEventDto.getOverallStatusCy());
        }

        sql.append(" ORDER BY vs.update_time DESC");

        return new ListPageUtil(sql.toString(), args.toArray(), abnormalEventDto.getPageNo(), abnormalEventDto.getPageSize(), jdbc, null);
    }
}
