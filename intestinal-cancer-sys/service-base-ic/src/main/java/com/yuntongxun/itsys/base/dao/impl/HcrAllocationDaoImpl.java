package com.yuntongxun.itsys.base.dao.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.alibaba.druid.util.StringUtils;
import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.po.*;
import com.yuntongxun.itsys.base.po.dto.allocation.*;
import com.yuntongxun.itsys.base.vo.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yuntongxun.itsys.base.common.exception.DaoException;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.DateUtil;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.common.util.StringUtil;
import com.yuntongxun.itsys.base.dao.HcrAllocationDao;

/**
 * 结肠镜预约分配实现方法
 *
 * @author liugb
 * Date 2018 4 16
 */
@Repository
public class HcrAllocationDaoImpl implements HcrAllocationDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final Logger log = LogManager.getLogger(HcrAllocationDaoImpl.class.getName());

    /**
     * 展示社区医生首页的未过期预约分配列表
     */
    @Override
    public ListPageUtil queryAllocation(int communityDeptId, int pageNo, int pageSize) throws ItSysException {
        // TODO Auto-generated method stub
        log.info("@Dao HcrAllocationDaoImpl query  Start ");
        String sql = "select t1.`id` as id, t1.amount as reserveable,concat_ws(' ',t1.reservation_date, concat_ws('-',t1.start_time,t1.end_time) )as period,t1.examination_place as name,t2.name as deptName,'结肠镜检查' as examinationName from hospital_colonoscopy_reservation_allocation t1,itsys_department t2 "
                + "where t1.use_status=1 and t1.area_dept_id=t2.id and" +
                " (community_dept_id = ? or find_in_set(" + communityDeptId + ",community_dept_id_info)) " +
                "and reservation_date> now() and reservation_date<=addtime(now(),'13 0:0:0') order by reservation_date ASC";
        List param = new ArrayList();
        param.add(communityDeptId);
        ListPageUtil listPage = new ListPageUtil(sql, param.toArray(), pageNo, pageSize, jdbcTemplate, null);
        return listPage;
    }

    /**
     * 市区放号方法实现
     */
    @Override
    public void insert(AllocationTodo allocationTodo, int areaId) throws DaoException {
        // TODO Auto-generated method stub
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format1.parse(allocationTodo.getReservationDate());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        log.info("@Dao 结肠镜预约分配 insert Start ");
        String sql = "insert into hospital_colonoscopy_reservation_allocation(area_dept_id,amount,community_dept_id,"
                + "reservation_date,start_time,end_time,examination_place,signature,date_created,update_time) "
                + "values(?,?,?,?,?,?,?,?,now(),now())";
        jdbcTemplate.update(sql, new Object[]{areaId, allocationTodo.getAmount(),
                allocationTodo.getCommunityDeptId(), date, allocationTodo.getStartTime(),
                allocationTodo.getEndTime(), allocationTodo.getExaminationPlace(), allocationTodo.getSignature()});
        log.info("@Dao 结肠镜预约分配 insert End");
    }

    /**
     * 根据市区id查询社区信息
     */
    @Override
    public List<ItsysDepartmentVo> getByAreaId(int id) throws DaoException {
        List<ItsysDepartmentVo> map;
        // TODO Auto-generated method stub
        log.info("@Dao 结肠镜预约分配 getByAreaId Start ");
//		String sql = "select t1.id,t1.amount,t2.name as communityName,count(t3.id)as allsums from "
//				+ " hospital_colonoscopy_reservation_allocation t1,itsys_department t2,hospital_colonoscopy_record t3 "
//				+ " where t1.area_dept_id=t2.id and t3.allocation_id = t1.id and t1.community_dept_id  = t2.pid and t3.reserve_status = 1 and t1.area_dept_id = ?";
        //String sql = "select * from itsys_department where  pid="+id;
        String sql = "select itsys_department.name as communityName,(select count(*) from hospital_colonoscopy_record where itsys_department.id = hospital_colonoscopy_record.community_dept_id and reserve_status=1 ) as allsums from itsys_department where  pid=" + id;
        log.info("@Dao 结肠镜预约分配 getByAreaId End" + sql);
        map = jdbcTemplate.query(sql, new BeanPropertyRowMapper<ItsysDepartmentVo>(ItsysDepartmentVo.class));
        return map;


    }

    /**
     * 签到管理查询详情
     */
    @Override
    public ListPageUtil getlistByAreaId(Integer areaDeptId, TodoVo vo) throws DaoException {
        // TODO Auto-generated method stub
        log.info("@Dao 签到管理 getlistByAreaId Start");
        String sql = "select t4.id, t1.sid,t1.`name` as peopleName,t1.age,t1.sex,t1.`group`,t1.phone,t2.name as departName,"
                + "concat_ws(' ',t3.reservation_date, concat_ws('-',t3.start_time,t3.end_time) )as period "
                + " from hospital_intestine_review t1,itsys_department t2,hospital_colonoscopy_reservation_allocation t3,hospital_colonoscopy_record t4,itsys_user u "
                + " where t1.sid = t4.sid and t3.id = t4.allocation_id and t2.id = t1.community_dept_id AND t1.create_user = u.loginName and t4.reserve_status = 2 and isNull(t4.examination_status)"
                + " and t1.area_dept_id = ? ";
        log.info("sql ==>" + sql);
        List param = new ArrayList();
        param.add(areaDeptId);
        if (vo.getCommunityDeptId() != null) {
            sql += " and t1.`community_dept_id` = ?";
            param.add(vo.getCommunityDeptId());
        }
        if (!StringUtil.isEmpty(vo.getSid())) {
            sql += " and t1.sid like ?";
            param.add("%" + vo.getSid() + "%");
        }
        if (!StringUtil.isEmpty(vo.getName())) {
            sql += " and t1.`name` like ?";
            param.add("%" + vo.getName() + "%");
        }
        if (!StringUtil.isEmpty(vo.getPhone())) {
            sql += " and t1.phone like ?";
            param.add("%" + vo.getPhone() + "%");
        }
        if (!StringUtil.isEmpty(vo.getStartDate()) && !StringUtil.isEmpty(vo.getEndDate())) {
            sql += " and date_format(t3.reservation_date,'%Y-%m-%d') >= ? and date_format(t3.reservation_date,'%Y-%m-%d') <= ?";
            param.add(vo.getStartDate());
            param.add(vo.getEndDate());
        }
        if (!StringUtil.isEmpty(vo.getLoginName())) {
            sql += " and u.loginName = ?";
            param.add(vo.getLoginName());
        }
        log.info("@Dao 签到管理 getlistByAreaId End");
        ListPageUtil listPage = new ListPageUtil(sql, param.toArray(), vo.getPageNo(), vo.getPageSize(), jdbcTemplate, null);
        return listPage;
    }

    @Override
    public ListPageUtil queryColonoscopyReservationAllocation(String startTime, String endTime, String communityDeptId, Integer areaDeptId, int pageNo, int pageSize) {

        //examination_status=2  结肠镜检查就诊状态，1：未就诊，2：已就诊,(select count(*) from hospital_colonoscopy_record where hospital_colonoscopy_record.allocation_id=hospital_colonoscopy_reservation_allocation.id and examination_status=2) as examination_status
        //reserve_status=2  结肠镜检查预约状态，1：未预约，2：已预约(select count(*) from hospital_colonoscopy_record where hospital_colonoscopy_record.allocation_id=hospital_colonoscopy_reservation_allocation.id and reserve_status=2) as reserve_status
        StringBuffer sql = new StringBuffer(
                "select hospital_colonoscopy_reservation_allocation.id,hospital_colonoscopy_reservation_allocation.area_dept_id as areaDeptId,hospital_colonoscopy_reservation_allocation.community_dept_id as communityDeptId,hospital_colonoscopy_reservation_allocation.reservation_date as reservationDate,hospital_colonoscopy_reservation_allocation.start_time as startTime,hospital_colonoscopy_reservation_allocation.end_time as endTime,hospital_colonoscopy_reservation_allocation.amount,itsys_department.name as communityName  from hospital_colonoscopy_reservation_allocation,itsys_department ");
        List parm = new ArrayList();
        //  where reservation_date>="2018-04-25" and reservation_date<="2018-04-25"

        //判断添加where
        sql.append(" where itsys_department.id=hospital_colonoscopy_reservation_allocation.community_dept_id and  hospital_colonoscopy_reservation_allocation.area_dept_id=?");
        parm.add(areaDeptId);
/*        if(!StringUtils.isEmpty(startTime)||!StringUtils.isEmpty(endTime)||!StringUtils.isEmpty(communityDeptId)){
            sql.append(" where ");
        }*/
        if (!StringUtils.isEmpty(startTime)) {
            sql.append(" and  hospital_colonoscopy_reservation_allocation.reservation_date>=? ");
            parm.add(startTime);
        }

        if (!StringUtils.isEmpty(endTime)) {
            //sql=!StringUtils.isEmpty(startTime)?sql.append(" and reservation_date<=? "):sql.append(" reservation_date<=? ");
            sql.append(" and hospital_colonoscopy_reservation_allocation.reservation_date<=? ");
            parm.add(endTime);
        }
        if (!StringUtils.isEmpty(communityDeptId)) {
            //sql= !StringUtils.isEmpty(startTime)||!StringUtils.isEmpty(endTime)?sql.append(" and community_dept_id=? "):sql.append(" community_dept_id=? ");
            sql.append(" and hospital_colonoscopy_reservation_allocation.community_dept_id=? ");
            parm.add(communityDeptId);
        }
        sql.append(" order by date_created asc");
        Map<String, String> translateParm = new HashMap();
        log.info("测试---------------------------》" + sql.toString());
        ListPageUtil listPage = new ListPageUtil(sql.toString(), parm.toArray(), pageNo, pageSize, jdbcTemplate, translateParm);
        return listPage;
    }

    /**
     * 根据登陆市区id查询所对应的社区名称
     */
    @Override
    public List getcommdeptsByAreaId(int areaId) throws DaoException {
        // TODO Auto-generated method stub
        log.info("@Dao getcommdeptsByAreaId Start ");
        String sql = "select t.id, t.name as commdeptName from itsys_department t where 1=1  and t.pid = ?";
        log.info("@Dao getcommdeptsByAreaId End");
        List list = jdbcTemplate.queryForList(sql, areaId);
        return list;
    }

    @Override
    public List<HospitalColonoscopyRecord> queryByCommunityDeptId(String communityDeptId) {
        String sql = "select count(*) as reserve_status,community_dept_id from hospital_colonoscopy_record where community_dept_id in (" + communityDeptId + ") and reserve_status=1 group by community_dept_id";
        List<HospitalColonoscopyRecord> list;
        try {
            list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<HospitalColonoscopyRecord>(HospitalColonoscopyRecord.class));
        } catch (Exception e) {
            log.info(e.getMessage());
            return null;
        }
        return list;
    }

    /**
     * 地区放号情况一览表1
     */
    @Override
    public ListPageUtil queryPutCodeByAreaId(Integer areaId, TodoVo vo) {
        // TODO Auto-generated method stub
        String sql = "select t4.id,t4.communityDeptId,t4.commName,t4.period,t4.amount,t4.alSums1,t4.alSums2,t4.reservation_date from  (select distinct t1.id,t1.community_dept_id as communityDeptId,t2.name as commName,concat_ws(' ',t1.reservation_date, concat_ws('-',t1.start_time,t1.end_time) )as period,"
                + "t1.amount,t1.reservation_date,(select count(*) from hospital_colonoscopy_record t,hospital_colonoscopy_reservation_detail t3 where t.reserve_status = 2 and t.allocation_id = t1.id and t.sid = t3.sid)as alSums1,"
                + "(select count(*) from hospital_colonoscopy_record t where t.examination_status = 2 and t.allocation_id = t1.id) as alSums2 "
                + " from hospital_colonoscopy_reservation_allocation t1,itsys_department t2 where t1.community_dept_id = t2.id and t1.area_dept_id = " + areaId;
        List param = new ArrayList();

        if (vo.getCommunityDeptId() != null) {
            sql += " and t1.community_dept_id = ?";
            param.add(vo.getCommunityDeptId());
        }
        if (!StringUtil.isEmpty(vo.getStartDate())) {
            if (!StringUtil.isEmpty(vo.getEndDate())) {
                sql += " and date_format(t1.reservation_date,'%Y-%m-%d') >= ? and date_format(t1.reservation_date,'%Y-%m-%d') <= ?";
                param.add(vo.getStartDate());
                param.add(vo.getEndDate());
            } else {
                sql += " and date_format(t1.reservation_date,'%Y-%m-%d') >= ?";
                param.add(vo.getStartDate());
            }
        }
        sql += ") as t4 where 1=1 order by  t4.period desc";
        ListPageUtil listPage = new ListPageUtil(sql, param.toArray(), vo.getPageNo(), vo.getPageSize(), jdbcTemplate, null);
        return listPage;
    }

    /**
     * 地区一览表查看详情
     */
    @Override
    public ListPageUtil queryPutCodeDetailListByCommitId(ColonoscopyQueryResult queryParam) {
        // TODO Auto-generated method stub
        String sql = "select t1.sid,t1.name,t1.phone,t1.sex,t1.age,"
                + "case when t1.`group`= 'A' then 'A' when t1.`group` = 'B'  then 'B' when t1.`group` = 'C' and t1.risk_level is null then 'C' when t1.`group` = 'C' and t1.risk_level = 1 then 'Cd' when t1.`group` = 'C' and  t1.risk_level = 2 then 'Cg' ELSE NULL END 'group',"
                + "t1.overall_status_cy as overallStatusCy,t2.reserve_status as reserveStatus,t2.examination_status as examinationStatus,t2.finished_status as finishedStatus"
                + " from hospital_intestine_review t1,hospital_colonoscopy_record t2,hospital_colonoscopy_reservation_allocation t3,hospital_colonoscopy_reservation_detail t4,itsys_user u where t1.sid = t2.sid and t2.sid = t4.sid and t2.allocation_id = t3.id and t3.id = t4.allocation_id and t1.create_user = u.loginName"
                + " and t1.community_dept_id = ? "
                + " and t3.id = ?";
        List param = new ArrayList();
        param.add(queryParam.getCommunityDeptId());
        param.add(queryParam.getId());
        if (!StringUtil.isEmpty(queryParam.getSid())) {
            sql += " and t1.sid = ?";
            param.add(queryParam.getSid());
        }
        if (!StringUtil.isEmpty(queryParam.getName())) {
            sql += " and t1.name = ?";
            param.add(queryParam.getName());
        }
        if (!StringUtil.isEmpty(queryParam.getPhone())) {
            sql += " and t1.phone = ?";
            param.add(queryParam.getPhone());
        }
        if (queryParam.getReserveStatus() != null) {
            sql += " and t2.reserve_status = ?";
            param.add(queryParam.getReserveStatus());
        }
        if (queryParam.getExaminationStatus() != null) {
            sql += " and t2.examination_status = ?";
            param.add(queryParam.getExaminationStatus());
        }
        if (queryParam.getFinishedStatus() != null) {
            sql += " and t2.finished_status = ?";
            param.add(queryParam.getFinishedStatus());
        }
        if (queryParam.getCommunityDeptId() != null) {
            sql += " and t1.community_dept_id = ?";
            param.add(queryParam.getCommunityDeptId());
        }
        if (!StringUtil.isEmpty(queryParam.getLoginName())) {
            sql += " and t1.create_user = ?";
            param.add(queryParam.getLoginName());
        }
        if (!StringUtil.isEmpty(queryParam.getLoginName())) {
            sql += " and u.loginName = ?";
            param.add(queryParam.getLoginName());
        }
        ListPageUtil listPage = new ListPageUtil(sql, param.toArray(), queryParam.getPageNo(), queryParam.getPageSize(), jdbcTemplate, null);
        return listPage;
    }

    /**
     * 地区一览表导出方法
     */
    @Override
    public List<AreaListForExcelVo> queryForAreaExcel(Integer areaId, TodoVo vo) {
        // TODO Auto-generated method stub
        String sql = "select t4.id,t4.communityDeptId,t4.commName,t4.period,t4.amount,t4.alSums1,t4.alSums2,t4.reservation_date from  (select distinct t1.id,t1.community_dept_id as communityDeptId,t2.name as commName,concat_ws(' ',t1.reservation_date, concat_ws('-',t1.start_time,t1.end_time) )as period,"
                + "t1.amount,t1.reservation_date,(select count(*) from hospital_colonoscopy_record t,hospital_colonoscopy_reservation_detail t3 where t.reserve_status = 2 and t.allocation_id = t1.id and t.sid = t3.sid)as alSums1,"
                + "(select count(*) from hospital_colonoscopy_record t where t.examination_status = 2 and t.allocation_id = t1.id) as alSums2 "
                + " from hospital_colonoscopy_reservation_allocation t1,itsys_department t2 where t1.community_dept_id = t2.id and t1.area_dept_id = " + areaId;
        List param = new ArrayList();
        if (vo.getCommunityDeptId() != null) {
            sql += " and t1.community_dept_id = ?";
            param.add(vo.getCommunityDeptId());
        }
        if (!StringUtil.isEmpty(vo.getStartDate())) {
            if (!StringUtil.isEmpty(vo.getEndDate())) {
                sql += " and date_format(t1.reservation_date,'%Y-%m-%d') >= ? and date_format(t1.reservation_date,'%Y-%m-%d') <= ?";
                param.add(vo.getStartDate());
                param.add(vo.getEndDate());
            } else {
                sql += " and date_format(t1.reservation_date,'%Y-%m-%d') >= ?";
                param.add(vo.getStartDate());
            }
        }
        sql += ") as t4 where 1=1";
        List<AreaListForExcelVo> list = jdbcTemplate.query(sql.toString(), param.toArray(), new BeanPropertyRowMapper<AreaListForExcelVo>(AreaListForExcelVo.class));
        List<AreaListForExcelVo> rs = new LinkedList<AreaListForExcelVo>();
        for (AreaListForExcelVo p : list) {
            AreaListForExcelVo area = new AreaListForExcelVo();
            List param1 = new ArrayList();
            //一览表查询详情
            String sql1 = "select t1.sid,t1.name,t1.phone,t1.sex,t1.age,u.nickName,"
                    + "case when t1.`group`= 'A' then 'A' when t1.`group` = 'B'  then 'B' when t1.`group` = 'C' and t1.risk_level is null then 'C' when t1.`group` = 'C' and t1.risk_level = 1 then 'Cd' when t1.`group` = 'C' and  t1.risk_level = 2 then 'Cg' ELSE NULL END 'group',"
                    + "t1.overall_status_cy as overallStatusCy,t2.reserve_status as reserveStatus,t2.examination_status as examinationStatus,t2.finished_status as finishedStatus"
                    + " from hospital_intestine_review t1, itsys_user u,hospital_colonoscopy_record t2,hospital_colonoscopy_reservation_allocation t3,hospital_colonoscopy_reservation_detail t4 "
                    + " where t1.sid = t2.sid and t1.create_user = u.loginName and t2.sid = t4.sid and t2.allocation_id = t3.id and t3.id = t4.allocation_id"
                    + " and t1.community_dept_id = " + p.getCommunityDeptId()
                    + " and t3.id = " + p.getId();
            List<AreaListForExcelVo> detaillist = jdbcTemplate.query(sql1.toString(), param1.toArray(), new BeanPropertyRowMapper<AreaListForExcelVo>(AreaListForExcelVo.class));
            area.setCommName(p.getCommName());
            area.setPeriod(p.getPeriod());
            area.setAmount(p.getAmount());
            area.setAlSums1(p.getAlSums1());
            area.setAlSums2(p.getAlSums2());
            rs.add(area);
            //遍历详情
            for (AreaListForExcelVo ars : detaillist) {
                AreaListForExcelVo detail = new AreaListForExcelVo();
                detail.setSid(ars.getSid());
                detail.setName(ars.getName());
                detail.setSex(ars.getSex());
                detail.setAge(ars.getAge());
                detail.setPhone(ars.getPhone());
                detail.setGroup(ars.getGroup());
                detail.setOverallStatusCy(ars.getOverallStatusCy());
                detail.setReserveStatus(ars.getReserveStatus());
                detail.setExaminationStatus(ars.getExaminationStatus());
                detail.setFinishedStatus(ars.getFinishedStatus());
                detail.setNickName(ars.getNickName());
                rs.add(detail);
            }
        }
        return rs;
    }

    @Override
    public ListPageUtil queryAllocationInArea(int communityDeptId, int pageNo, int pageSize) {
        // TODO Auto-generated method stub
        log.info("@Dao HcrAllocationDaoImpl query  Start ");
        String sql = "select t1.`id` as id, t1.amount as reserveable,concat_ws(' ',t1.reservation_date, concat_ws('-',t1.start_time,t1.end_time) )as period,t1.examination_place as name,t2.name as deptName,'结肠镜检查' as examinationName from hospital_colonoscopy_reservation_allocation t1,itsys_department t2 "
                + "where  t1.use_status=1 and t1.area_dept_id=t2.id and" +
                " (community_dept_id = ? or find_in_set(" + communityDeptId + ",community_dept_id_info)) " +
                "and str_to_date(concat_ws(' ',t1.reservation_date, t1.end_time ), '%Y-%m-%d %H:%i:%s')>=DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s') and str_to_date(concat_ws(' ',t1.reservation_date, t1.end_time ), '%Y-%m-%d %H:%i:%s')<=DATE_FORMAT(addtime(now(),'14 0:0:0'),'%Y-%m-%d %H:%i:%s') order by reservation_date ASC";
        List param = new ArrayList();
        param.add(communityDeptId);
        ListPageUtil listPage = new ListPageUtil(sql, param.toArray(), pageNo, pageSize, jdbcTemplate, null);
        return listPage;
    }

    @Override
    public void save(AllocationDto allocationDto1) {
        log.info("@Dao 结肠镜预约分配 save Start ");
        String sql = "insert into hospital_colonoscopy_reservation_allocation(area_dept_id,amount,community_dept_id,"
                + "reservation_date,start_time,end_time,examination_place,signature,date_created,update_time,rule_id,use_status,issue_type,community_dept_id_info) "
                + "values(?,?,?,?,?,?,?,?,now(),now(),?,?,?,?)";
        jdbcTemplate.update(sql, new Object[]{allocationDto1.getAreaDeptId(), allocationDto1.getAmount(),
                allocationDto1.getCommunityDeptId(), allocationDto1.getReservationDate(), allocationDto1.getStartTime(),
                allocationDto1.getEndTime(), allocationDto1.getExaminationPlace(), allocationDto1.getSignature(), allocationDto1.getRuleId(), allocationDto1.getUseStatus(), allocationDto1.getIssueType(), allocationDto1.getCommunityDeptIdInfo()});
        log.info("@Dao 结肠镜预约分配 save End");
    }

    @Override
    public ListPageUtil queryRule(HosAllocationRuleInfoDto hosAllocationRuleInfoDto) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer();
        //放号总数
        //sql.append("select sum(amount) from hospital_colonoscopy_reservation_allocation where `rule_id`=1 and reservation_date=curdate() group by reservation_date,rule_id");
        sql.append("SELECT rule.id AS ruleId, rule.rule_begin AS ruleBegin, rule.rule_end AS ruleEnd, rule.issue_type AS issueType, rule.begin_status AS beginStatus  " +
                " , rule.use_status AS useStatus  " +
                " , (  " +
                " SELECT SUM(amount)  " +
                " FROM hospital_colonoscopy_reservation_allocation  " +
                " WHERE `rule_id` = ruleId  " +
                "  AND reservation_date = curdate()  " +
                " GROUP BY reservation_date, rule_id  " +
                " ) AS fanghaoNum , " +
                "(" +
                "  SELECT COUNT(*)    " +
                "  FROM hospital_colonoscopy_reservation_detail    " +
                "  WHERE allocation_id IN (    " +
                "   SELECT id    " +
                "   FROM hospital_colonoscopy_reservation_allocation    " +
                "   WHERE `rule_id` = ruleId    " +
                "  )    " +
                " ) AS tingzhenyuyueNum" +
                " , (  " +
                " SELECT COUNT(*)  " +
                " FROM hospital_colonoscopy_reservation_detail  " +
                " WHERE allocation_id IN (  " +
                "  SELECT id  " +
                "  FROM hospital_colonoscopy_reservation_allocation  " +
                "  WHERE `rule_id` = ruleId  " +
                "   AND reservation_date = curdate()  " +
                "  )  " +
                " ) AS yuyueNum, (  " +
                " SELECT SUM(amount)  " +
                " FROM hospital_colonoscopy_reservation_allocation  " +
                " WHERE `rule_id` = ruleId  " +
                "  AND reservation_date = curdate()  " +
                " GROUP BY reservation_date, rule_id  " +
                " ) - (  " +
                " SELECT COUNT(*)  " +
                " FROM hospital_colonoscopy_reservation_detail  " +
                " WHERE allocation_id IN (  " +
                "  SELECT id  " +
                "  FROM hospital_colonoscopy_reservation_allocation  " +
                "  WHERE `rule_id` = ruleId  " +
                "   AND reservation_date = curdate()  " +
                "  )  " +
                " ) AS shengyu  " +
                "FROM hos_allocation_rule_info rule where area_dept_id=? ");

        List param = new ArrayList();
        param.add(hosAllocationRuleInfoDto.getAreaDeptId());
        if (!StringUtil.isEmpty(hosAllocationRuleInfoDto.getRuleBeginToString())) {
            if (!StringUtils.isEmpty(hosAllocationRuleInfoDto.getRuleBeginToString())) {
                sql.append(" and rule.rule_begin <= ? ");
                param.add(hosAllocationRuleInfoDto.getRuleBeginToString());
            }

            if (!StringUtils.isEmpty(hosAllocationRuleInfoDto.getRuleEndToString())) {
                sql.append(" and rule.rule_end >= ? ");
                param.add(hosAllocationRuleInfoDto.getRuleEndToString());
            }
        }
        sql.append(" order by begin_status,rule.rule_end desc");
        log.info("查询规则列表" + sql.toString());

        ListPageUtil listPage = new ListPageUtil(sql.toString(), param.toArray(), hosAllocationRuleInfoDto.getPageNo(), hosAllocationRuleInfoDto.getPageSize(), jdbcTemplate, null, Constans.SUBQUERY);
        return listPage;
    }

    @Override
    public List<DoctorInfo> queryCountryByRuleid(String ruleId) {
        String sql = "select  dep.name as loginName,dep.id,allocation.community_dept_id_info as communityDeptIdInfo from hospital_colonoscopy_reservation_allocation allocation,itsys_department dep  where dep.id=allocation.community_dept_id and  `rule_id`=? and reservation_date=curdate()  group by community_dept_id";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<DoctorInfo>(DoctorInfo.class), ruleId);
    }

    @Override
    public void updateRule(Integer id,String areaId) {
        String sql = "update hos_allocation_rule_info set use_status= 2  where id= ? and area_dept_id=?";
        jdbcTemplate.update(sql, new Object[]{id,areaId});
    }

    @Override
    public void updateAllocationS(Integer id,String  areaid) {
        String sql = "update hospital_colonoscopy_reservation_allocation set use_status= 2  where rule_id= ? AND reservation_date> now() and area_dept_id=?";
        jdbcTemplate.update(sql, new Object[]{id,areaid});
    }

    @Override
    public List<HospitalReview> queryPhoneByRuleId(Integer id) {
        String sql = "select phone from hospital_intestine_review where sid in (select  distinct(sid) from hospital_colonoscopy_reservation_detail where allocation_id in (select id from hospital_colonoscopy_reservation_allocation where rule_id=? and use_status=1))";
        log.info(sql);
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<HospitalReview>(HospitalReview.class), id);
    }

    @Override
    public List<HosAllocationRuleDeptInfoDto> queryPutCodeByRuleId(int areaId, TodoVo vo) {
        String sql = "select * from hos_allocation_rule_info rule ,hos_allocation_rule_dept_info d where rule.id=d.rule_id and rule.id=? and rule.area_dept_id=?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<HosAllocationRuleDeptInfoDto>(HosAllocationRuleDeptInfoDto.class), vo.getRuleId(), areaId);
    }

    @Override
    public ListPageUtil queryPutCodeByRuleId1(int areaId, TodoVo vo) {
        // TODO Auto-generated method stub
        String sql = "select t4.id,t4.communityDeptId,t4.commName,t4.period,t4.amount,t4.alSums1,t4.alSums2,t4.reservation_date from  (select distinct t1.id,t1.community_dept_id as communityDeptId,t2.name as commName,concat_ws(' ',t1.reservation_date, concat_ws('-',t1.start_time,t1.end_time) )as period,"
                + "t1.amount,t1.reservation_date,(select count(*) from hospital_colonoscopy_record t,hospital_colonoscopy_reservation_detail t3 where t.reserve_status = 2 and t.allocation_id = t1.id and t.sid = t3.sid)as alSums1,"
                + "(select count(*) from hospital_colonoscopy_record t where t.examination_status = 2 and t.allocation_id = t1.id) as alSums2 "
                + " from hospital_colonoscopy_reservation_allocation t1,itsys_department t2 where t1.community_dept_id = t2.id and t1.area_dept_id = " + areaId + " And t1.rule_id=" + vo.getRuleId();
        List param = new ArrayList();
        if (StringUtils.isEmpty(vo.getReservationDateToString())) {
            sql += " and date_format(t1.reservation_date,'%Y-%m-%d') = ? ";
            param.add(vo.getReservationDateToString());
        }
        sql += ") as t4 where 1=1 order by  t4.period desc";
        ListPageUtil listPage = new ListPageUtil(sql, param.toArray(), vo.getPageNo(), vo.getPageSize(), jdbcTemplate, null);
        return listPage;
    }

    @Override
    public List<ResultAllocation> queryByRuleIdAndDate(String date, String ruleId) {
        log.info("查询放号日期");
        String sql = "select t1.rule_id,t1.`reservation_date`,t2.name AS deptName,sum(amount) as sumAmount from hospital_colonoscopy_reservation_allocation t1,itsys_department t2 where t1.`area_dept_id`=t2.id and t1.rule_id=?   " +
                "        AND t1.reservation_date = ? and  use_status=1 group by t1.`reservation_date`,t2.id  ";
        log.info(sql);
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<ResultAllocation>(ResultAllocation.class), ruleId, date);
    }

    @Override
    public Integer queryDetailByRuleIdAndDate(String date, String ruleId) {
        log.info("查询放号日期 数量");
        String sql = "select count(*) from  hospital_colonoscopy_reservation_detail where allocation_id in (select id from hospital_colonoscopy_reservation_allocation where rule_id=? and reservation_date=?)";
        Integer result = 0;
        try {
            result = jdbcTemplate.queryForObject(sql.toString(), new Object[]{ruleId, date}, Integer.class);

        } catch (Exception e) {
            log.info("查询放号日期 数量 is error " + e.toString());
        }
        return result;
    }

    @Override
    public List<ResultAllocation> queryRuleById(String ruleId) {
        log.info("查询放号日期");
        String sql = " select * from  hos_allocation_rule_info where id=? ";
        log.info(sql);
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<ResultAllocation>(ResultAllocation.class), ruleId);
    }

    @Override
    public int queryNumByRuleIdAndDate(String ruleId, String reservationDateToString, String communityDeptId) {
        String sql = "select count(*) from hospital_colonoscopy_reservation_detail where allocation_id in (select id from hospital_colonoscopy_reservation_allocation a  where a.rule_id=? and reservation_date=? ) and community_dept_id=?";
        int result = jdbcTemplate.queryForObject(sql.toString(), new Object[]{ruleId, reservationDateToString, communityDeptId}, Integer.class);
        return result;
    }

    @Override
    public List<HosAllocationRuleDeptInfoDto> queryRuleDeptByRuleid(String ruleId) {
        String sql = " select * from  hos_allocation_rule_dept_info where rule_id=? ";
        log.info(sql);
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<HosAllocationRuleDeptInfoDto>(HosAllocationRuleDeptInfoDto.class), ruleId);
    }

    @Override
    public int querySumNumBy(String ruleId, String reservationDateToString,String communityDeptId) {
        String sql = "select amount from  hospital_colonoscopy_reservation_allocation where rule_id=? and reservation_date=? and community_dept_id=?";
        int result = jdbcTemplate.queryForObject(sql.toString(), new Object[]{ruleId, reservationDateToString,communityDeptId}, Integer.class);
        return result;
    }

    @Override
    public ListPageUtil queryEntityByRuleIdAndRule(String ruleId, String reservationDateToString, Integer communityDeptIdm, Integer pageNo, Integer pageSize) {
        String sql = "select d1.id as communityDeptId ,allocation.id as aId,allocation.reservation_date as reservationDate,allocation.start_time as startTime,allocation.end_time as endTime ,d.name as depName,d1.name as communityName,(select count(*) from  hospital_colonoscopy_reservation_detail where allocation_id in (select id from hospital_colonoscopy_reservation_allocation where `rule_id`=allocation.rule_id  AND CONCAT( reservation_date,start_time,end_time) = CONCAT(allocation.reservation_date,allocation.start_time,allocation.end_time)) and community_dept_id= " + communityDeptIdm + ") as yuyueNum from hospital_colonoscopy_reservation_allocation allocation,itsys_department d,itsys_department d1 where " +
                "d1.id=" + communityDeptIdm + " and d.id=allocation.area_dept_id  and  allocation.rule_id=? and allocation.reservation_date=? and  (allocation.community_dept_id = " + communityDeptIdm + " or find_in_set(" + communityDeptIdm + ",allocation.community_dept_id_info))";
        log.info("地区放号一览表 根据时间和规则id、规则 社区详情 查询对象列表" + sql.toString());
        List parm = new ArrayList();
        //判断添加where
        parm.add(ruleId);
        parm.add(reservationDateToString);
        ListPageUtil listPage = new ListPageUtil(sql.toString(), parm.toArray(), pageNo, pageSize, jdbcTemplate, null, Constans.SUBQUERY);
        return listPage;
    }

    @Override
    public ListPageUtil queryPerSonByRuleIdAndRule(TodoVo vo) {
        String sql = "select d.name as countryName,t2.reserve_status as reserveStatus,t3.end_time as endTime,t3.start_time as startTime,t3.reservation_date as reservationDate,t1.sid,t1.name,t1.phone,t1.sex,t1.age,case when t1.`group`= 'A' then 'A' when t1.`group` = 'B'  then 'B' when t1.`group` = 'C' and t1.risk_level is null then 'C' when t1.`group` = 'C' and t1.risk_level = 1 then 'Cd' when t1.`group` = 'C' and  t1.risk_level = 2 then 'Cg' ELSE NULL END 'group',t1.overall_status_cy as overallStatusCy,t2.reserve_status as reserveStatus,t2.examination_status as examinationStatus,t2.finished_status as finishedStatus from hospital_intestine_review t1,hospital_colonoscopy_record t2,hospital_colonoscopy_reservation_allocation t3,hospital_colonoscopy_reservation_detail t4,itsys_user u,itsys_department d where t1.sid = t2.sid and t2.sid = t4.sid and t2.allocation_id = t3.id and t3.id = t4.allocation_id AND t2.reserve_id=t4.id and t1.create_user = u.loginName AND t4.examine_status is null and t3.id in (" + vo.getAllocationId() + ") and d.id=t1.community_dept_id";
        List param = new ArrayList();
        if (null != vo.getCommunityDeptId()) {
            sql += " and t1.community_dept_id = ?";
            param.add(vo.getCommunityDeptId());
        }
        ListPageUtil listPage = new ListPageUtil(sql, param.toArray(), vo.getPageNo(), vo.getPageSize(), jdbcTemplate, null);
        return listPage;
    }

    @Override
    public ListPageUtil queryFanghao(TodoVo vo) {
        StringBuffer sql = new StringBuffer();
        List param = new ArrayList();
        sql.append("select * from (select reservation_date,area_dept_id from hospital_colonoscopy_reservation_allocation_info where  area_dept_id=? ");
        param.add(vo.getAreaDeptId());

        if (!StringUtils.isEmpty(vo.getStartDate())) {
            sql.append(" and reservation_date >= ? ");
            param.add(vo.getStartDate());
        }

        if (!StringUtils.isEmpty(vo.getEndDate())) {
            sql.append(" and reservation_date <=  ? ");
            param.add(vo.getEndDate());
        }

        if (null != vo.getCommunityDeptId()) {
            sql.append("and t1.community_dept_id = ?");
            param.add(vo.getCommunityDeptId());
        }
        sql.append(" group by reservation_date,area_dept_id ) as tt order by reservation_date desc ");
        ListPageUtil listPage = new ListPageUtil(sql.toString(), param.toArray(), vo.getPageNo(), vo.getPageSize(), jdbcTemplate, null);
        return listPage;
    }

    @Override
    public int queryNumByAreaIdAndDate(String area_dept_id, String reservation_date,Integer usestatus) {
        String sql = "select count(*) from hospital_colonoscopy_reservation_detail where allocation_id in (select id from hospital_colonoscopy_reservation_allocation a  where a.area_dept_id=? and reservation_date=? and use_status=?) ";
        int result = jdbcTemplate.queryForObject(sql.toString(), new Object[]{area_dept_id, reservation_date,usestatus}, Integer.class);
        return result;
    }

    @Override
    public List<AllocationDto> queryEntityByAreaIdAndDate(String area_dept_id, String reservation_date) {
        String sql = " select * from hospital_colonoscopy_reservation_allocation_info a  where a.area_dept_id=? and reservation_date=? ";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<AllocationDto>(AllocationDto.class), area_dept_id, reservation_date);
    }

    @Override
    public List<ColonoscopyDto> queryEntrtyByRuleId(ColonoscopyDto colonoscopyDto) {
        StringBuffer sql = new StringBuffer();

        sql.append("select t1.name,t1.phone,t1.create_user as createUser,rec.*,allocation.reservation_date as reservationDate,allocation.start_time as startTime,allocation.end_time as  endTime  from hospital_colonoscopy_reservation_detail detail,hospital_colonoscopy_record rec,hospital_colonoscopy_reservation_allocation allocation,hospital_intestine_review t1  where t1.sid=detail.sid and allocation.id=detail.allocation_id and rec.reserve_id=detail.id and detail.allocation_id in (select id from hospital_colonoscopy_reservation_allocation where  use_status=1 ");
        if (!StringUtils.isEmpty(colonoscopyDto.getRuleId())) {
            sql.append(" and  rule_id=? AND reservation_date> now() and area_dept_id=? ");
            sql.append(" ) ");
            return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<ColonoscopyDto>(ColonoscopyDto.class), colonoscopyDto.getRuleId(),colonoscopyDto.getArea_dept_id());
        } else {
            sql.append(" and  allocation.reservation_date=? and area_dept_id=?  ");
            sql.append(" ) ");
            return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<ColonoscopyDto>(ColonoscopyDto.class), colonoscopyDto.getReservationDate(),colonoscopyDto.getArea_dept_id());
        }

    }

    @Override
    public void updateAllocationSByDate(String reservationDate, Integer area_dept_id) {
        String sql = "update hospital_colonoscopy_reservation_allocation set use_status= 2  where reservation_date= ? and area_dept_id=?";
        jdbcTemplate.update(sql, new Object[]{reservationDate, area_dept_id});
    }

    @Override
    public ListPageUtil queryPerSonByRuleIdAndRuleByTingZhen(TodoVo vo) {
        String sql = "SELECT d.name AS countryName,  t3.end_time AS endTime, t3.start_time AS startTime, t3.reservation_date AS reservationDate    " +
                "   , t1.sid, t1.name, t1.phone, t1.sex, t1.age    " +
                "   , CASE     " +
                "      WHEN t1.`group` = 'A' THEN 'A'    " +
                "      WHEN t1.`group` = 'B' THEN 'B'    " +
                "      WHEN t1.`group` = 'C'    " +
                "      AND t1.risk_level IS NULL THEN 'C'    " +
                "      WHEN t1.`group` = 'C'    " +
                "      AND t1.risk_level = 1 THEN 'Cd'    " +
                "      WHEN t1.`group` = 'C'    " +
                "      AND t1.risk_level = 2 THEN 'Cg'    " +
                "      ELSE NULL    " +
                "      END AS 'group', t1.overall_status_cy AS overallStatusCy    " +
                "FROM hospital_intestine_review t1, hospital_colonoscopy_reservation_allocation t3, hospital_colonoscopy_reservation_detail t4, itsys_user u, itsys_department d    " +
                "WHERE t1.sid = t4.sid    " +
                "      AND t3.id = t4.allocation_id   AND t4.examine_status is not null " +
                "      AND t1.create_user = u.loginName    " +
                "      AND t3.id IN (" + vo.getAllocationId() + ")    " +
                "      AND d.id = t1.community_dept_id";
        List param = new ArrayList();
        if (null != vo.getCommunityDeptId()) {
            sql += " and t1.community_dept_id = ?";
            param.add(vo.getCommunityDeptId());
        }
        ListPageUtil listPage = new ListPageUtil(sql, param.toArray(), vo.getPageNo(), vo.getPageSize(), jdbcTemplate, null);
        return listPage;
    }

    @Override
    public List<AllocationDto> queryAllocationByRuleIdAndDate(TodoVo vo) {
        StringBuffer sql = new StringBuffer();

        sql.append("select * from hospital_colonoscopy_reservation_allocation where rule_id=? and reservation_date=? and area_dept_id=?");
        return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<AllocationDto>(AllocationDto.class), vo.getRuleId(), vo.getReservationDateToString(), vo.getAreaDeptId());
    }

    @Override
    public void saveByFuBiao(AllocationDto allocationDto1) {
        log.info("@Dao 结肠镜预约分配附表 save Start ");
        String sql = "insert into hospital_colonoscopy_reservation_allocation_info(area_dept_id,amount,community_dept_id,"
                + "reservation_date,start_time,end_time,examination_place,signature,date_created,update_time,rule_id,use_status,issue_type,community_dept_id_info) "
                + "values(?,?,?,?,?,?,?,?,now(),now(),?,?,?,?)";
        jdbcTemplate.update(sql, new Object[]{allocationDto1.getAreaDeptId(), allocationDto1.getAmount(),
                allocationDto1.getCommunityDeptId(), allocationDto1.getReservationDate(), allocationDto1.getStartTime(),
                allocationDto1.getEndTime(), allocationDto1.getExaminationPlace(), allocationDto1.getSignature(), allocationDto1.getRuleId(), allocationDto1.getUseStatus(), allocationDto1.getIssueType(), allocationDto1.getCommunityDeptIdInfo()});
        log.info("@Dao 结肠镜预约分配 save End");
    }

    @Override
    public void updateAllocationSALLDAy(Integer id,String area_dept_id) {
        String sql = "update hospital_colonoscopy_reservation_allocation_info set use_status= 2  where rule_id= ? AND reservation_date> now() and area_dept_id=?";
        jdbcTemplate.update(sql, new Object[]{id,area_dept_id});
    }

    @Override
    public void updateAllocationSByALLDay(String reservationDate, Integer area_dept_id) {
        String sql = "update hospital_colonoscopy_reservation_allocation_info set use_status= 2  where reservation_date= ? and area_dept_id=?";
        jdbcTemplate.update(sql, new Object[]{reservationDate, area_dept_id});
    }

    @Override
    public int queryByUseStatus(AllocationDto allocationDto1) {
        if(StringUtils.isEmpty(allocationDto1.getCommunityDeptId())){
            String sql = "select count(*) from hospital_colonoscopy_reservation_allocation_info where area_dept_id=? and amount=?  and " +
                    " reservation_date=? and start_time=? and end_time=? and examination_place=? and signature=? and rule_id=? and use_status=? and issue_type=? and community_dept_id_info=?";
            int result = jdbcTemplate.queryForObject(sql.toString(), new Object[]{allocationDto1.getAreaDeptId(), allocationDto1.getAmount(),
                     allocationDto1.getReservationDate(), allocationDto1.getStartTime(),
                    allocationDto1.getEndTime(), allocationDto1.getExaminationPlace(), allocationDto1.getSignature(), allocationDto1.getRuleId(), Constans.USESTATUS2, allocationDto1.getIssueType(), allocationDto1.getCommunityDeptIdInfo()}, Integer.class);
            return result;
        }
        if(StringUtils.isEmpty(allocationDto1.getCommunityDeptIdInfo())){
            String sql = "select count(*) from hospital_colonoscopy_reservation_allocation_info where area_dept_id=? and amount=? and community_dept_id=? and " +
                    " reservation_date=? and start_time=? and end_time=? and examination_place=? and signature=? and rule_id=? and use_status=? and issue_type=? ";
            int result = jdbcTemplate.queryForObject(sql.toString(), new Object[]{allocationDto1.getAreaDeptId(), allocationDto1.getAmount(),
                    allocationDto1.getCommunityDeptId(), allocationDto1.getReservationDate(), allocationDto1.getStartTime(),
                    allocationDto1.getEndTime(), allocationDto1.getExaminationPlace(), allocationDto1.getSignature(), allocationDto1.getRuleId(), Constans.USESTATUS2, allocationDto1.getIssueType()}, Integer.class);
            return result;
        }
        return 0;
    }

    @Override
    public int queryFanghaoNum(TodoVo vo) {

        String sql = "select IFNULL(sum(amount), 0) as fanghaoNum from hospital_colonoscopy_reservation_allocation_info where area_dept_id=? and reservation_date=? and use_status=?";
        int result = jdbcTemplate.queryForObject(sql.toString(), new Object[]{vo.getAreaDeptId(),vo.getReservationDateToString(),vo.getUseStatus() }, Integer.class);
        return result;
    }

    @Override
    public List<AllocationDto> queryEntityByAreaIdAndDateNo(String area_dept_id, String reservation_date,Integer usestatus) {
        String sql = " select * from hospital_colonoscopy_reservation_allocation a  where a.area_dept_id=? and reservation_date=? and use_status=?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<AllocationDto>(AllocationDto.class), area_dept_id, reservation_date,usestatus);

    }

    @Override
    public List<ResultAllocation> queryByRuleIdAndDateByUseStatus(String reservationDateToString, String ruleId) {
        String sql = "select t1.rule_id,t1.`reservation_date`,t2.name AS deptName,sum(amount) as sumAmount from hospital_colonoscopy_reservation_allocation t1,itsys_department t2 where t1.`area_dept_id`=t2.id and t1.rule_id=?   " +
                "        AND t1.reservation_date = ?  group by t1.`reservation_date`,t2.id  ";
        log.info(sql);
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<ResultAllocation>(ResultAllocation.class), ruleId, reservationDateToString);
    }
}
