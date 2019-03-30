package com.yuntongxun.itsys.base.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.common.util.StringUtil;
import com.yuntongxun.itsys.base.po.HospitalColonoscopyRecord;
import com.yuntongxun.itsys.base.vo.TodoVoPo;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.yuntongxun.itsys.base.dao.HcRecordDao;
import com.yuntongxun.itsys.base.vo.TodoVo;

/**
 * 立即预约页面查询待预约受试者
 *
 * @author liugb
 * Date 2018 4 16
 */
@Repository
public class HcRecordDaoImpl implements HcRecordDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final Logger log = LogManager.getLogger(HcRecordDaoImpl.class.getName());

    /**
     * 立即预约页面查询待预约受试者
     */
    @Override
    public ListPageUtil getHcRecordReview(int dept_id, TodoVo vo) {
        // TODO Auto-generated method stub
        log.info("HcRecordDaoImpl getHcRecordReview start");
        String sql = "select r.sid as sid,v.name as name,v.sex as sex,v.age as age,"
                + "v.phone as cellPhone, case when v.`group`= 'A' then 'A' when v.`group` = 'B'  then 'B' when v.`group` = 'C' and v.risk_level is null then 'C'   when v.`group` = 'C' and v.risk_level = 1 then 'Cd' when v.`group` = 'C' and  v.risk_level = 2 then 'Cg' ELSE NULL END 'group',v.in_group_date as inGroupDate,"
                + "v.overall_status_cy as overallStatusCy from hospital_colonoscopy_record r,hospital_intestine_review v,itsys_user u"
                + " where 1=1 and v.create_user = u.loginName and r.sid = v.sid and r.community_dept_id = ? and r.reserve_status = 1";
        List param = new ArrayList();
        log.info("HcRecordDaoImpl getHcRecordReview end");
        param.add(dept_id);
        if (!StringUtil.isEmpty(vo.getName())) {
            sql += " and v.`name` like ?";
            param.add("%" + vo.getName() + "%");
        }
        if (!StringUtil.isEmpty(vo.getSid())) {
            sql += " and v.sid like ?";
            param.add("%" + vo.getSid() + "%");
        }
        if (!StringUtil.isEmpty(vo.getPhone())) {
            sql += " and v.phone like ?";
            param.add("%" + vo.getPhone() + "%");
        }
        if (!StringUtil.isEmpty(vo.getGroup())) {
            if (vo.getGroup().equals("Cg")) {
                sql += " and v.`group`= 'C' and v.risk_level = ?";
                param.add(2);
            } else if (vo.getGroup().equals("Cd")) {
                sql += " and v.`group`= 'C' and v.risk_level = ?";
                param.add(1);
            } else {
                sql += " and v.`group`=?";
                param.add(vo.getGroup());
            }
        }
        if(!StringUtil.isEmpty(vo.getLoginName())){
        	sql += " and u.loginName = ?";
        	param.add(vo.getLoginName());
        }
        ListPageUtil listPage = new ListPageUtil(sql, param.toArray(), vo.getPageNo(), vo.getPageSize(), jdbcTemplate, null);
        return listPage;
    }

    /**
     * 查询 未按时完成结肠镜检查 受试者列表
     */
    @Override
    public ListPageUtil notFinishColonoscopy(Integer communityDeptId, int status, TodoVo vo) {
        log.info("@Dao notFinishColonoscopy  Start ");
        String sql = "select t1.id,t2.sid,t2.`name`,u.nickName,t2.age,t2.sex,t5.name as commdeptName, case when t2.`group`= 'A' then 'A' when t2.`group` = 'B'  then 'B' when t2.`group` = 'C' and t2.risk_level is null then 'C'   when t2.`group` = 'C' and t2.risk_level = 1 then 'Cd' when t2.`group` = 'C' and  t2.risk_level = 2 then 'Cg' ELSE NULL END 'group',"
        		+ "t2.phone as cellPhone,t2.in_group_date as inGroupDate,t3.reservation_date as reservationDate,t3.start_time as startTime,t3.end_time as endTime,t4.examination_status as examinationStatus,t4.finished_status as finishedStatus,t2.overall_status_cy as overallStatusCy,concat(t3.reservation_date,' ',t3.start_time,'-',t3.end_time) as peroid,t4.`id` as colonoscopyRecordId from hospital_todo_event t1,hospital_intestine_review t2,itsys_user u,itsys_department t5,hospital_colonoscopy_record t4 " +
                " LEFT JOIN hospital_colonoscopy_reservation_allocation t3 on t4.allocation_id = t3.id where "
                + "    t1.sid = t2.sid and t2.community_dept_id = t5.id"
                + " AND t2.create_user = u.loginName "
                + " and t1.data_id = t4.id "
                //+ " and t4.allocation_id = t3.id "
                + " and t1.community_dept_id = ? "
                + " and t1.type = ? "
                + " and t1.`status`=? ";
        List param = new ArrayList();
        param.add(communityDeptId);
        param.add(6);//6：未完成结肠镜检查
        param.add(status);

        if (!StringUtil.isEmpty(vo.getSid())) {
            sql += " and t2.sid like ?";
            param.add("%" + vo.getSid() + "%");
        }

        if (!StringUtil.isEmpty(vo.getName())) {
            sql += " and t2.`name` like ?";
            param.add("%" + vo.getName() + "%");
        }

        if (!StringUtil.isEmpty(vo.getPhone())) {
            sql += " and t2.phone like ?";
            param.add("%" + vo.getPhone() + "%");
        }

        if (!StringUtil.isEmpty(vo.getGroup())) {
            if (vo.getGroup().equals("Cg")) {
                sql += " and t2.`group`= 'C' and t2.risk_level = ?";
                param.add(2);
            } else if (vo.getGroup().equals("Cd")) {
                sql += " and t2.`group`= 'C' and t2.risk_level = ?";
                param.add(1);
            } else {
                sql += " and t2.`group`=?";
                param.add(vo.getGroup());
            }
        }
        if(!StringUtil.isEmpty(vo.getLoginName())){
        	sql += " and u.loginName = ?";
        	param.add(vo.getLoginName());
        }
        log.info("@Dao notReserveColonoscopy  End ");
        ListPageUtil listPage = new ListPageUtil(sql, param.toArray(), vo.getPageNo(), vo.getPageSize(), jdbcTemplate, null);
        return listPage;
    }

    /**
     * 按照地区查询未按时完成结肠镜检查
     * @param areaId
     * @param status
     * @param vo
     * @return
     */
    @Override
    public ListPageUtil notFinishColonoscopy1(Integer areaId, int status, TodoVo vo) {
        log.info("@Dao notFinishColonoscopy  Start ");
        String sql = "select t1.id,t2.sid,t2.`name`,u.nickName,t2.age,t2.sex,t5.name as commdeptName, case when t2.`group`= 'A' then 'A' when t2.`group` = 'B'  then 'B' when t2.`group` = 'C' and t2.risk_level is null then 'C'   when t2.`group` = 'C' and t2.risk_level = 1 then 'Cd' when t2.`group` = 'C' and  t2.risk_level = 2 then 'Cg' ELSE NULL END 'group',t5.name as commdeptName,"
                + "t2.phone as cellPhone,t2.in_group_date as inGroupDate,t3.reservation_date as reservationDate,t3.start_time as startTime,t3.end_time as endTime,t4.examination_status as examinationStatus,t4.finished_status as finishedStatus,t2.overall_status_cy as overallStatusCy,concat(t3.reservation_date,' ',t3.start_time,'-',t3.end_time) as peroid,t4.`id` as colonoscopyRecordId from hospital_todo_event t1,hospital_intestine_review t2,itsys_user u,itsys_department t5,hospital_colonoscopy_record t4 " +
                " LEFT JOIN hospital_colonoscopy_reservation_allocation t3 on t4.allocation_id = t3.id where "
                + "    t1.sid = t2.sid and t2.community_dept_id = t5.id"
                + " AND t2.create_user = u.loginName "
                + " and t1.data_id = t4.id "
                + " and t1.area_dept_id = ? "
                + " and t1.type = ? "
                + " and t1.`status`=? ";
        List param = new ArrayList();
        param.add(areaId);
        param.add(6);//6：未完成结肠镜检查
        param.add(status);

        if (!StringUtil.isEmpty(vo.getSid())) {
            sql += " and t2.sid like ?";
            param.add("%" + vo.getSid() + "%");
        }

        if (!StringUtil.isEmpty(vo.getName())) {
            sql += " and t2.`name` like ?";
            param.add("%" + vo.getName() + "%");
        }

        if (!StringUtil.isEmpty(vo.getPhone())) {
            sql += " and t2.phone like ?";
            param.add("%" + vo.getPhone() + "%");
        }

        if (!StringUtil.isEmpty(vo.getGroup())) {
            if (vo.getGroup().equals("Cg")) {
                sql += " and t2.`group`= 'C' and t2.risk_level = ?";
                param.add(2);
            } else if (vo.getGroup().equals("Cd")) {
                sql += " and t2.`group`= 'C' and t2.risk_level = ?";
                param.add(1);
            } else {
                sql += " and t2.`group`=?";
                param.add(vo.getGroup());
            }
        }
        if(vo.getCommunityDeptId() != null){
            sql += " and t1.community_dept_id = ?";
            param.add(vo.getCommunityDeptId());
        }
        if(!StringUtil.isEmpty(vo.getLoginName())){
            sql += " and u.loginName = ?";
            param.add(vo.getLoginName());
        }
        log.info("@Dao notReserveColonoscopy  End ");
        ListPageUtil listPage = new ListPageUtil(sql, param.toArray(), vo.getPageNo(), vo.getPageSize(), jdbcTemplate, null);
        return listPage;
    }

    /**
     * 查看该社区医院，未发放筛查结果告知书  受试者列表
     */
    @Override
    public ListPageUtil notIssueNotification(Integer communityDeptId, int status, TodoVo vo) {
        log.info("@Dao notReserveColonoscopy  Start ");
        String sql = "select  t2.data_id as id,  t1.sid,t1.`name`,u.nickName,t1.sex,t1.age,t1.phone as cellPhone,t3.name as commdeptName,"
        		+ " case when t1.`group`= 'A' then 'A' when t1.`group` = 'B'  then 'B' when t1.`group` = 'C' and t1.risk_level is null then 'C'   when t1.`group` = 'C' and t1.risk_level = 1 then 'Cd' when t1.`group` = 'C' and  t1.risk_level = 2 then 'Cg' ELSE NULL END 'group',"
        		+ "t1.in_group_date as inGroupDate,t1.overall_status_cy as overallStatusCy from hospital_intestine_review t1,hospital_todo_event t2 ,itsys_department t3, itsys_user u where"
                + "     t1.sid = t2.sid and t1.community_dept_id = t3.id"
                + " AND t1.create_user = u.loginName "
                + " and t2.community_dept_id=? "
                + " and t2.type=?"
                + " and t2.`status`=?";
        List param = new ArrayList();
        param.add(communityDeptId);
        param.add(7);//7：未发放筛查结果告知书
        param.add(status);

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

        if (!StringUtil.isEmpty(vo.getGroup())) {
            if (vo.getGroup().equals("Cg")) {
                sql += " and t1.`group` = 'C' and t1.risk_level = ?";
                param.add(2);
            } else if (vo.getGroup().equals("Cd")) {
                sql += " and t1.`group` = 'C' and t1.risk_level = ?";
                param.add(1);
            } else {
                sql += " and t1.`group` like ?";
                param.add("%" + vo.getGroup() + "%");
            }
        }
        if(!StringUtil.isEmpty(vo.getLoginName())){
        	sql += " and u.loginName = ?";
        	param.add(vo.getLoginName());
        }
        log.info("@Dao notReserveColonoscopy  End ");
        ListPageUtil listPage = new ListPageUtil(sql, param.toArray(), vo.getPageNo(), vo.getPageSize(), jdbcTemplate, null);
        return listPage;
    }

    /**
     * 按照地区查询未发放筛查结果告知书
     * @param areaId
     * @param status
     * @param vo
     * @return
     */
    @Override
    public ListPageUtil notIssueNotification1(Integer areaId, int status, TodoVo vo) {
        log.info("@Dao notReserveColonoscopy  Start ");
        String sql = "select  t2.data_id as id,  t1.sid,t1.`name`,u.nickName,t1.sex,t1.age,t1.phone as cellPhone,t3.name as commdeptName,"
                + " case when t1.`group`= 'A' then 'A' when t1.`group` = 'B'  then 'B' when t1.`group` = 'C' and t1.risk_level is null then 'C'   when t1.`group` = 'C' and t1.risk_level = 1 then 'Cd' when t1.`group` = 'C' and  t1.risk_level = 2 then 'Cg' ELSE NULL END 'group',"
                + "t1.in_group_date as inGroupDate,t1.overall_status_cy as overallStatusCy from hospital_intestine_review t1,hospital_todo_event t2 , itsys_department t3,itsys_user u where"
                + "     t1.sid = t2.sid and t1.community_dept_id = t3.id"
                + " AND t1.create_user = u.loginName "
                + " and t2.area_dept_id=? "
                + " and t2.type=?"
                + " and t2.`status`=?";
        List param = new ArrayList();
        param.add(areaId);
        param.add(7);//7：未发放筛查结果告知书
        param.add(status);

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

        if (!StringUtil.isEmpty(vo.getGroup())) {
            if (vo.getGroup().equals("Cg")) {
                sql += " and t1.`group` = 'C' and t1.risk_level = ?";
                param.add(2);
            } else if (vo.getGroup().equals("Cd")) {
                sql += " and t1.`group` = 'C' and t1.risk_level = ?";
                param.add(1);
            } else {
                sql += " and t1.`group` like ?";
                param.add("%" + vo.getGroup() + "%");
            }
        }
        if(vo.getCommunityDeptId() != null){
            sql += " and t2.community_dept_id = ?";
            param.add(vo.getCommunityDeptId());
        }
        if(!StringUtil.isEmpty(vo.getLoginName())){
            sql += " and u.loginName = ?";
            param.add(vo.getLoginName());
        }
        log.info("@Dao notReserveColonoscopy  End ");
        ListPageUtil listPage = new ListPageUtil(sql, param.toArray(), vo.getPageNo(), vo.getPageSize(), jdbcTemplate, null);
        return listPage;
    }

    @Override
    public ListPageUtil notReserveColonoscopy(Integer communityDeptId, int status, TodoVo vo) {
        log.info("@Dao notReserveColonoscopy  Start ");
        String sql = "select t4.dept_code ,t2.data_id as id,  t1.sid,t1.`name`,u.nickName,t1.sex,t1.age,t1.phone as cellPhone,t3.name as commdeptName,"
        		+ "case when t1.`group`= 'A' then 'A' when t1.`group` = 'B'  then 'B' when t1.`group` = 'C' and t1.risk_level is null then 'C'   when t1.`group` = 'C' and t1.risk_level = 1 then 'Cd' when t1.`group` = 'C' and  t1.risk_level = 2 then 'Cg' ELSE NULL END `group`,"
        		+ "t1.in_group_date as inGroupDate,t1.overall_status_cy as overallStatusCy,t2.data_id as colonoscopyRecordId from hospital_intestine_review t1,hospital_todo_event t2 ,itsys_department t3,itsys_department t4 , itsys_user u where"
                + "     t1.sid = t2.sid and t1.community_dept_id = t3.id"
                +" and t4.id=t3.pid "
                + " AND t1.create_user = u.loginName "
                + " and t2.community_dept_id=? "
                + " and t2.type=?"
                + " and t2.`status`=?";
        List param = new ArrayList();
        param.add(communityDeptId);
        param.add(5);//5：未预约结肠镜检查
        param.add(status);

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

        if (!StringUtil.isEmpty(vo.getGroup())) {
            if (vo.getGroup().equals("Cg")) {
                sql += " and t1.`group` = 'C' and t1.risk_level = ?";
                param.add(2);
            } else if (vo.getGroup().equals("Cd")) {
                sql += " and t1.`group` = 'C' and t1.risk_level = ?";
                param.add(1);
            } else {
                sql += " and t1.`group` like ?";
                param.add("%" + vo.getGroup() + "%");
            }
        }
        if(!StringUtil.isEmpty(vo.getLoginName())){
        	sql += " and u.loginName = ?";
        	param.add(vo.getLoginName());
        }
        log.info("@Dao notReserveColonoscopy  End ");
        ListPageUtil listPage = new ListPageUtil(sql, param.toArray(), vo.getPageNo(), vo.getPageSize(), jdbcTemplate, null);
        return listPage;
    }

    /**
     * 按照地区查询未预约结肠镜检查
     * @param areaId
     * @param status
     * @param vo
     * @return
     */
    @Override
    public ListPageUtil notReserveColonoscopy1(Integer areaId, int status, TodoVo vo) {
        log.info("@Dao notReserveColonoscopy  Start ");
        String sql = "select t2.data_id as id,  t1.sid,t1.`name`,u.nickName,t1.sex,t1.age,t1.phone as cellPhone,t3.name as commdeptName,"
                + "case when t1.`group`= 'A' then 'A' when t1.`group` = 'B'  then 'B' when t1.`group` = 'C' and t1.risk_level is null then 'C'   when t1.`group` = 'C' and t1.risk_level = 1 then 'Cd' when t1.`group` = 'C' and  t1.risk_level = 2 then 'Cg' ELSE NULL END `group`,"
                + "t1.in_group_date as inGroupDate,t1.overall_status_cy as overallStatusCy,t2.data_id as colonoscopyRecordId from hospital_intestine_review t1,hospital_todo_event t2 ,itsys_department t3, itsys_user u where"
                + "     t1.sid = t2.sid and t1.community_dept_id = t3.id"
                + " AND t1.create_user = u.loginName "
                + " and t2.area_dept_id=? "
                + " and t2.type=?"
                + " and t2.`status`=?";
        List param = new ArrayList();
        param.add(areaId);
        param.add(5);//5：未预约结肠镜检查
        param.add(status);

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

        if (!StringUtil.isEmpty(vo.getGroup())) {
            if (vo.getGroup().equals("Cg")) {
                sql += " and t1.`group` = 'C' and t1.risk_level = ?";
                param.add(2);
            } else if (vo.getGroup().equals("Cd")) {
                sql += " and t1.`group` = 'C' and t1.risk_level = ?";
                param.add(1);
            } else {
                sql += " and t1.`group` like ?";
                param.add("%" + vo.getGroup() + "%");
            }
        }
        if(vo.getCommunityDeptId() != null){
            sql += " and t2.community_dept_id = ?";
            param.add(vo.getCommunityDeptId());
        }
        if(!StringUtil.isEmpty(vo.getLoginName())){
            sql += " and u.loginName = ?";
            param.add(vo.getLoginName());
        }
        log.info("@Dao notReserveColonoscopy  End ");
        ListPageUtil listPage = new ListPageUtil(sql, param.toArray(), vo.getPageNo(), vo.getPageSize(), jdbcTemplate, null);
        return listPage;
    }

    @Override
    public ListPageUtil notEntryColonoscopyResultQuery(Integer areaDeptId, Integer communityDeptId, int status, TodoVo vo) {
        log.info("@Dao notEntryColonoscopyResultQuery  Start ");
        String sql = "select t4.`name` as commdeptName,t2.data_id as `id`, t1.sid,t1.`name`,u.nickName,t1.sex,t1.age,t1.phone as cellPhone,"
        		+ "case when t1.`group`= 'A' then 'A' when t1.`group` = 'B'  then 'B' when t1.`group` = 'C' and t1.risk_level is null then 'C'   when t1.`group` = 'C' and t1.risk_level = 1 then 'Cd' when t1.`group` = 'C' and  t1.risk_level = 2 then 'Cg' ELSE NULL END `group`,"
        		+ "t1.in_group_date as inGroupDate,t1.overall_status_cy as overallStatusCy,t2.data_id as colonoscopyRecordId,t3.`name` as deptName from hospital_intestine_review t1,hospital_todo_event t2,itsys_department t3,itsys_department t4 , itsys_user u  where"
                + "     t1.sid = t2.sid "
                + " and t2.area_dept_id = t3.id  "
                + " and t1.community_dept_id = t4.id  "
                + " AND t1.create_user = u.loginName  "
                + " and t2.area_dept_id=? "
                + " and t2.type=?"
                + " and t2.`status`=?";
        List param = new ArrayList();
        param.add(areaDeptId);
        param.add(vo.getType());//8：待录入肠镜结果
        param.add(status);

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

        if (!StringUtil.isEmpty(vo.getGroup())) {
            if (vo.getGroup().equals("Cg")) {
                sql += " and t1.`group` = 'C' and t1.risk_level = ?";
                param.add(2);
            } else if (vo.getGroup().equals("Cd")) {
                sql += " and t1.`group` = 'C' and t1.risk_level = ?";
                param.add(1);
            } else {
                sql += " and t1.`group` like ?";
                param.add("%" + vo.getGroup() + "%");
            }
        }

        if (communityDeptId != null) {
            sql += " and t2.`community_dept_id` = ?";
            param.add(communityDeptId);
        }
        if(!StringUtil.isEmpty(vo.getLoginName())){
        	sql += " and u.loginName = ?";
        	param.add(vo.getLoginName());
        }
        log.info("@Dao notEntryColonoscopyResultQuery  End ");
        ListPageUtil listPage = new ListPageUtil(sql, param.toArray(), vo.getPageNo(), vo.getPageSize(), jdbcTemplate, null);
        return listPage;
    }

    @Override
    public ListPageUtil notEntryPathologyResultQuery(Integer areaDeptId, Integer communityDeptId, int status,
                                                     TodoVo vo) {
        log.info("@Dao notEntryPathologyResultQuery  Start ");
        String sql = "select  t4.`name` as commdeptName,t2.data_id as `id`,  t1.sid,t1.`name`,u.nickName,t1.sex,t1.age,t1.phone as cellPhone,"
        		+ "case when t1.`group`= 'A' then 'A' when t1.`group` = 'B'  then 'B' when t1.`group` = 'C' and t1.risk_level is null then 'C'   when t1.`group` = 'C' and t1.risk_level = 1 then 'Cd' when t1.`group` = 'C' and  t1.risk_level = 2 then 'Cg' ELSE NULL END `group`,"
        		+ "t1.in_group_date as inGroupDate,t1.overall_status_cy as overallStatusCy,t2.data_id as colonoscopyRecordId,t3.`name` as deptName from hospital_intestine_review t1,hospital_todo_event t2,itsys_department t3,itsys_department t4 , itsys_user u where"
                + "     t1.sid = t2.sid "
                + " and t2.area_dept_id = t3.id  "
                + " and t1.community_dept_id = t4.id  "
                + " AND t1.create_user = u.loginName  "
                + " and t2.area_dept_id=? "
                + " and t2.type=?"
                + " and t2.`status`=?";
        List param = new ArrayList();
        param.add(areaDeptId);
        param.add(9);//9：待录入病理结果
        param.add(status);

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

        if (!StringUtil.isEmpty(vo.getGroup())) {
            if (vo.getGroup().equals("Cg")) {
                sql += " and t1.`group` = 'C' and t1.risk_level = ?";
                param.add(2);
            } else if (vo.getGroup().equals("Cd")) {
                sql += " and t1.`group` = 'C' and t1.risk_level = ?";
                param.add(1);
            } else {
                sql += " and t1.`group` like ?";
                param.add("%" + vo.getGroup() + "%");
            }
        }
        if (communityDeptId != null) {
            sql += " and t2.`community_dept_id` = ?";
            param.add(communityDeptId);
        }
        if(!StringUtil.isEmpty(vo.getLoginName())){
        	sql += " and u.loginName = ?";
        	param.add(vo.getLoginName());
        }
        log.info("@Dao notEntryPathologyResultQuery  End ");
        ListPageUtil listPage = new ListPageUtil(sql, param.toArray(), vo.getPageNo(), vo.getPageSize(), jdbcTemplate, null);
        return listPage;
    }

    @Override
    public ListPageUtil notEntryNotificationResultQuery(Integer areaDeptId, Integer communityDeptId, int status,
                                                        TodoVo vo) {
        log.info("@Dao notEntryNotificationResultQuery  Start ");
        String sql = "select t2.data_id as `id`, t1.sid,t1.`name`,u.nickName,t1.sex,t1.age,t1.phone as cellPhone,"
        		+ "case when t1.`group`= 'A' then 'A' when t1.`group` = 'B'  then 'B' when t1.`group` = 'C' and t1.risk_level is null then 'C'   when t1.`group` = 'C' and t1.risk_level = 1 then 'Cd' when t1.`group` = 'C' and  t1.risk_level = 2 then 'Cg' ELSE NULL END `group`,"
        		+ "t1.in_group_date as inGroupDate,t1.overall_status_cy as overallStatusCy,t2.data_id as colonoscopyRecordId,t3.`name` as commdeptName " +
                "from hospital_intestine_review t1,hospital_todo_event t2,itsys_department t3 , itsys_user u  where"
                + "     t1.sid = t2.sid "
                + " and t2.community_dept_id = t3.id  "
                + " AND t1.create_user = u.loginName  "
                + " and t2.area_dept_id=? "
                + " and t2.type=?"
                + " and t2.`status`=?";
        List param = new ArrayList();
        param.add(areaDeptId);
        param.add(10);//10：待录入筛查结果告知书
        param.add(status);

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

        if (!StringUtil.isEmpty(vo.getGroup())) {
            if (vo.getGroup().equals("Cg")) {
                sql += " and t1.`group` = 'C' and t1.risk_level = ?";
                param.add(2);
            } else if (vo.getGroup().equals("Cd")) {
                sql += " and t1.`group` = 'C' and t1.risk_level = ?";
                param.add(1);
            } else {
                sql += " and t1.`group` like ?";
                param.add("%" + vo.getGroup() + "%");
            }
        }

        if (communityDeptId != null) {
            sql += " and t2.`community_dept_id` = ?";
            param.add(communityDeptId);
        }
        if(!StringUtil.isEmpty(vo.getLoginName())){
        	sql += " and u.loginName = ?";
        	param.add(vo.getLoginName());
        }
        log.info("@Dao notEntryNotificationResultQuery  End ");
        ListPageUtil listPage = new ListPageUtil(sql, param.toArray(), vo.getPageNo(), vo.getPageSize(), jdbcTemplate, null);
        return listPage;
    }

    @Override
    public ListPageUtil notEntryNoSampleResultQuery(Integer areaDeptId, String communityDeptId, int status, TodoVoPo vo) {
        log.info("@Dao notEntryNotificationResultQuery  Start ");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("SELECT t5.associated_sample_id as associatedSampleId,t5.sample_type as sampleType, t4.`name` AS depName, t2.data_id AS `id`, t1.sid, t1.`name` , u.nickName " +
                ", t1.sex, t1.age, t1.phone AS cellPhone " +
                ", case when t1.`group`= 'A' then 'A' when t1.`group` = 'B'  then 'B' when t1.`group` = 'C' and t1.risk_level is null then 'C'   when t1.`group` = 'C' and t1.risk_level = 1 then 'Cd' when t1.`group` = 'C' and  t1.risk_level = 2 then 'Cg' ELSE NULL END `group` ,"
                + " t1.in_group_date AS inGroupDate, t1.overall_status_cy AS overallStatusCy, t2.data_id AS colonoscopyRecordId, t3.`name` AS deptName " +
                "FROM hospital_intestine_review t1 " +
                " LEFT JOIN hospital_todo_event t2 ON t1.sid = t2.sid " +
                " LEFT JOIN itsys_department t3 ON t2.area_dept_id = t3.id " +
                " LEFT JOIN itsys_department t4 ON t1.community_dept_id = t4.id " +
                " LEFT JOIN hospital_biological_sample_result t5 ON t5.id = t2.data_id " +
                " LEFT JOIN itsys_user u ON t1.create_user = u.loginName " +
                " where "
                + "  t2.area_dept_id=? "
                + " and t2.`status`=?");
        List param = new ArrayList();
        param.add(areaDeptId);
        param.add(status);
        if (Constans.SAMPLE_TYPE6.equals(vo.getSampleTypeAll3())) {
            stringBuffer.append(" and t2.type = ? ");
            param.add(Constans.PERSON_TODO_EVENT_TYPE18);//血清
        }
        if (Constans.SAMPLE_TYPE4.equals(vo.getSampleType())) {
            stringBuffer.append(" and t2.type = ? ");
            param.add(Constans.PERSON_TODO_EVENT_TYPE13);//唾液
        } else if (Constans.SAMPLE_TYPE5.equals(vo.getSampleType())) {
            stringBuffer.append(" and t2.type = ? ");
            param.add(Constans.PERSON_TODO_EVENT_TYPE12);//粪便
        } else if (Constans.SAMPLE_TYPE1.equals(vo.getSampleType())) {
            stringBuffer.append(" and t2.type = ? ");
            param.add(Constans.PERSON_TODO_EVENT_TYPE11);//血清
        } else if (Constans.SAMPLE_TYPE2.equals(vo.getSampleType())) {
            stringBuffer.append(" and t2.type = ? ");
            param.add(Constans.PERSON_TODO_EVENT_TYPE14);//血浆
        } else if (Constans.SAMPLE_TYPE3.equals(vo.getSampleType())) {
            stringBuffer.append(" and t2.type = ? ");
            param.add(Constans.PERSON_TODO_EVENT_TYPE15);//白细胞
        }


        if (!StringUtil.isEmpty(vo.getSid())) {
            stringBuffer.append(" and t1.sid like ?");
            param.add("%" + vo.getSid() + "%");
        }

        if (!StringUtil.isEmpty(vo.getName())) {
            stringBuffer.append(" and t1.`name` like ?");
            param.add("%" + vo.getName() + "%");
        }

        if (!StringUtil.isEmpty(vo.getPhone())) {
            stringBuffer.append(" and t1.phone like ?");
            param.add("%" + vo.getPhone() + "%");
        }

        if (!StringUtil.isEmpty(vo.getGroup())) {
            if (vo.getGroup().equals("Cg")) {
                stringBuffer.append(" and t1.`group` = 'C' and t1.risk_level = ?");
                param.add(2);
            } else if (vo.getGroup().equals("Cd")) {
                stringBuffer.append(" and t1.`group` = 'C' and t1.risk_level = ?");
                param.add(1);
            } else {
                stringBuffer.append(" and t1.`group` like ?");
                param.add("%" + vo.getGroup() + "%");
            }
        }

        if (!StringUtils.isEmpty(communityDeptId)) {
            stringBuffer.append(" and t2.`community_dept_id` = ?");
            param.add(communityDeptId);
        }
        if(!StringUtils.isEmpty(vo.getLoginName())){
        	stringBuffer.append(" and u.loginName = ?");
        	param.add(vo.getLoginName());
        }
        log.info("@Dao notEntryNotificationResultQuery  End ");
        ListPageUtil listPage = new ListPageUtil(stringBuffer.toString(), param.toArray(), vo.getPageNo(), vo.getPageSize(), jdbcTemplate, null);
        return listPage;
    }


    @Override
    public List<HospitalColonoscopyRecord> getStatusByAllocation_id(String status, String ids) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("select allocation_id,count(*) as " + status + " from hospital_colonoscopy_record where allocation_id In(" + ids + ")");
        if (!StringUtils.isEmpty(status)) {
            stringBuffer.append(" and " + status + "=2 ");
        }
        stringBuffer.append(" group by allocation_id");
        log.info(stringBuffer.toString());
        List<HospitalColonoscopyRecord> result;

        try {
            result = jdbcTemplate.query(stringBuffer.toString(), new BeanPropertyRowMapper<HospitalColonoscopyRecord>(HospitalColonoscopyRecord.class));
        } catch (Exception e) {
            log.info(e.getMessage());
            return null;
        }
        return result;
    }

    /**
     * 根据市区id查询所对应的社区未预约结肠镜检查
     */
    @Override
    public List notEntryAllocation(Integer areaId, int status) {
        // TODO Auto-generated method stub
        log.info("@Dao notEntryAllocation  Start ");
        String sql = "select count(distinct(t1.sid)) as notEntryallocations"
                + "  from hospital_intestine_review t1,hospital_todo_event t2,itsys_department t3 where"
                + " t1.sid = t2.sid and t2.area_dept_id = t3.pid"
                + " and t2.area_dept_id=? "
                + " and t2.type=?"
                + " and t2.`status`=?";
        log.info("@Dao notEntryAllocation  End ");
        List list = jdbcTemplate.queryForList(sql, areaId, 5, status);
        return list;
    }

    /**
     * 根据市区id未完成结肠镜检查
     */
    @Override
    public List notEntryFinish(Integer areaId, int status, TodoVo vo) {
        // TODO Auto-generated method stub
        log.info("@Dao notFinishColonoscopy  Start ");
        String sql = "select count(distinct(t2.sid)) as notFinishes from hospital_todo_event t1,hospital_intestine_review t2,hospital_colonoscopy_reservation_allocation t3,hospital_colonoscopy_record t4,itsys_department t5 where "
                + "    t1.sid = t2.sid and t1.area_dept_id = t5.pid"
                + " and t1.data_id = t4.id "
                + " and t4.allocation_id = t3.id "
                + " and t1.area_dept_id = ? "
                + " and t1.type = ? "
                + " and t1.`status`=? ";
        List list = jdbcTemplate.queryForList(sql, areaId, 6, status);
        return list;
    }

    //根据市区id查询未发放结果告知书
    @Override
    public List notPutoutNotice(Integer areaId, int status, TodoVo vo) {
        // TODO Auto-generated method stub
        log.info("@Dao notReserveColonoscopy  Start ");
        String sql = "select count(distinct(t1.sid)) as notNotices from hospital_intestine_review t1,hospital_todo_event t2,itsys_department t3 where"
                + "     t1.sid = t2.sid and t2.area_dept_id = t3.pid"
                + " and t2.area_dept_id=? "
                + " and t2.type=?"
                + " and t2.`status`=?";
        List list = jdbcTemplate.queryForList(sql, areaId, 7, status);
        return list;
    }

    @Override
    public ListPageUtil notEntryNoDNAResultQuery(Integer areaDeptId, String communityDeptId, int status, TodoVoPo vo) {
        log.info("@Dao notEntryNoDNAResultQuery  Start ");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("SELECT  t5.dna_check_filePath as dnaCheckFilePath,t5.dna_check_result as dnaCheckResult,t5.dna_check_quantum as dnaCheckQuantum,t5.dna_check_goal as dnaCheckGoal,t5.dna_code as dnaCode,t4.`name` AS depName, t2.data_id AS `id`, t1.sid, t1.`name` , u.nickName " +
                ", t1.sex, t1.age, t1.phone AS cellPhone, " +
                /*", concat_ws('', t1.group, CASE  WHEN t1.risk_level = '1' THEN 'd' WHEN t1.risk_level = '2' THEN 'g' ELSE NULL END) AS `group` " +*/
                "case when t1.`group`= 'A' then 'A' when t1.`group` = 'B'  then 'B' when t1.`group` = 'C' and t1.risk_level is null then 'C'   when t1.`group` = 'C' and t1.risk_level = 1 then 'Cd' when t1.`group` = 'C' and  t1.risk_level = 2 then 'Cg' ELSE NULL END `group`,"+
                " t1.in_group_date AS inGroupDate, t1.overall_status_cy AS overallStatusCy, t2.data_id AS colonoscopyRecordId, t3.`name` AS AreaName, " +
                " case when record.result_status is not null then 1 else 2 end as resultStatus "+
                "FROM hospital_intestine_review t1 " +
                " LEFT JOIN hospital_todo_event t2 ON t1.sid = t2.sid " +
                " LEFT JOIN itsys_department t3 ON t2.area_dept_id = t3.id " +
                " LEFT JOIN itsys_department t4 ON t1.community_dept_id = t4.id " +
                " LEFT JOIN hospital_stool_dna t5 ON t5.id = t2.data_id LEFT JOIN itsys_user u ON t1.create_user = u.loginName  " +
                " LEFT JOIN( select DISTINCT(sid) sid,r.result_status from hospital_colonoscopy_record r where r.result_status=2 ) as record ON record.sid = t5.sid " +
                " where  1=1 and t1.overall_status_cy <> 2  and t2.type="+Constans.PERSON_TODO_EVENT_TYPE16+" and t5.dna_check_result is not null "
                + " and t2.`status`=?");
        List param = new ArrayList();
        param.add(status);

        if(areaDeptId!=null){
            stringBuffer.append(" and t2.area_dept_id=? ");
            param.add(areaDeptId);
        }
        if (!StringUtil.isEmpty(vo.getSid())) {
            stringBuffer.append(" and t1.sid like ?");
            param.add("%" + vo.getSid() + "%");
        }

        if (!StringUtil.isEmpty(vo.getName())) {
            stringBuffer.append(" and t1.`name` like ?");
            param.add("%" + vo.getName() + "%");
        }

        if (!StringUtil.isEmpty(vo.getPhone())) {
            stringBuffer.append(" and t1.phone like ?");
            param.add("%" + vo.getPhone() + "%");
        }

        if (!StringUtil.isEmpty(vo.getGroup())) {
            if (vo.getGroup().equals("Cg")) {
                stringBuffer.append(" and t1.`group` = 'C' and t1.risk_level = ?");
                param.add(2);
            } else if (vo.getGroup().equals("Cd")) {
                stringBuffer.append(" and t1.`group` = 'C' and t1.risk_level = ?");
                param.add(1);
            } else {
                stringBuffer.append(" and t1.`group` like ?");
                param.add("%" + vo.getGroup() + "%");
            }
        }

        if (!StringUtils.isEmpty(communityDeptId)) {
            stringBuffer.append(" and t2.`community_dept_id` = ?");
            param.add(communityDeptId);
        }
        if(!StringUtils.isEmpty(vo.getLoginName())){
        	stringBuffer.append(" and u.loginName = ?");
        	param.add(vo.getLoginName());
        }
        if(vo.getResultStatus()!=null&&vo.getResultStatus()==1){
            stringBuffer.append(" and record.result_status = 2 ");
        }else if(vo.getResultStatus()!=null&&vo.getResultStatus()==2){
            stringBuffer.append(" and record.result_status is null ");
        }
        //设置排序时间
        stringBuffer.append(" order by t2.date_created desc");

        log.info("@Dao notEntryNotificationResultQuery  End ");
        ListPageUtil listPage = new ListPageUtil(stringBuffer.toString(), param.toArray(), vo.getPageNo(), vo.getPageSize(), jdbcTemplate, null);
        return listPage;
    }

    /**
     * 12:待接收生物样本 粪便系类
     * @param areaDeptId
     * @param communityDeptId
     * @param status
     * @param vo
     * @return
     */
    @Override
    public ListPageUtil notEntrySampleF(Integer areaDeptId,Integer communityDeptId, int status, TodoVo vo) {
        log.info("@Dao notEntryNotificationResultQuery  Start ");
        String sql = "select t2.data_id as `id`, t1.sid,t1.`name`,u.nickName,t1.sex,t1.age,t1.phone as cellPhone,"
                + "case when t1.`group`= 'A' then 'A' when t1.`group` = 'B'  then 'B' when t1.`group` = 'C' and t1.risk_level is null then 'C'   when t1.`group` = 'C' and t1.risk_level = 1 then 'Cd' when t1.`group` = 'C' and  t1.risk_level = 2 then 'Cg' ELSE NULL END `group`,"
                + "t1.in_group_date as inGroupDate,t1.overall_status_cy as overallStatusCy,t2.data_id as colonoscopyRecordId,t3.`name` as commdeptName " +
                " from hospital_intestine_review t1,hospital_todo_event t2,itsys_department t3 , itsys_user u  where"
                + "     t1.sid = t2.sid "
                + " and t2.community_dept_id = t3.id  "
                + " AND t1.create_user = u.loginName  "
                + " and t2.area_dept_id=? "
                + " and t2.type=?"
                + " and t2.`status`=?";
        List param = new ArrayList();
        param.add(areaDeptId);
        param.add(12);//12：待接收生物样本 粪便系类
        param.add(status);

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

        if (!StringUtil.isEmpty(vo.getGroup())) {
            if (vo.getGroup().equals("Cg")) {
                sql += " and t1.`group` = 'C' and t1.risk_level = ?";
                param.add(2);
            } else if (vo.getGroup().equals("Cd")) {
                sql += " and t1.`group` = 'C' and t1.risk_level = ?";
                param.add(1);
            } else {
                sql += " and t1.`group` like ?";
                param.add("%" + vo.getGroup() + "%");
            }
        }

        if (communityDeptId != null) {
            sql += " and t2.`community_dept_id` = ?";
            param.add(communityDeptId);
        }
        if(!StringUtil.isEmpty(vo.getLoginName())){
            sql += " and u.loginName = ?";
            param.add(vo.getLoginName());
        }
        log.info("@Dao notEntryNotificationResultQuery  End ");
        ListPageUtil listPage = new ListPageUtil(sql, param.toArray(), vo.getPageNo(), vo.getPageSize(), jdbcTemplate, null);
        return listPage;
    }

    /**
     * 13:待接收生物样本 唾液系列
     * @param areaDeptId
     * @param communityDeptId
     * @param status
     * @param vo
     * @return
     */
    @Override
    public ListPageUtil notEntrySampleM(Integer areaDeptId,Integer communityDeptId, int status, TodoVo vo) {
        log.info("@Dao notEntryNotificationResultQuery  Start ");
        String sql = "select t2.data_id as `id`, t1.sid,t1.`name`,u.nickName,t1.sex,t1.age,t1.phone as cellPhone,"
                + "case when t1.`group`= 'A' then 'A' when t1.`group` = 'B'  then 'B' when t1.`group` = 'C' and t1.risk_level is null then 'C'   when t1.`group` = 'C' and t1.risk_level = 1 then 'Cd' when t1.`group` = 'C' and  t1.risk_level = 2 then 'Cg' ELSE NULL END `group`,"
                + "t1.in_group_date as inGroupDate,t1.overall_status_cy as overallStatusCy,t2.data_id as colonoscopyRecordId,t3.`name` as commdeptName " +
                " from hospital_intestine_review t1,hospital_todo_event t2,itsys_department t3 , itsys_user u  where"
                + "     t1.sid = t2.sid "
                + " and t2.community_dept_id = t3.id  "
                + " AND t1.create_user = u.loginName  "
                + " and t2.area_dept_id=? "
                + " and t2.type=?"
                + " and t2.`status`=?";
        List param = new ArrayList();
        param.add(areaDeptId);
        param.add(13);//13：待接收生物样本 唾液系列
        param.add(status);

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

        if (!StringUtil.isEmpty(vo.getGroup())) {
            if (vo.getGroup().equals("Cg")) {
                sql += " and t1.`group` = 'C' and t1.risk_level = ?";
                param.add(2);
            } else if (vo.getGroup().equals("Cd")) {
                sql += " and t1.`group` = 'C' and t1.risk_level = ?";
                param.add(1);
            } else {
                sql += " and t1.`group` like ?";
                param.add("%" + vo.getGroup() + "%");
            }
        }

        if (communityDeptId != null) {
            sql += " and t2.`community_dept_id` = ?";
            param.add(communityDeptId);
        }
        if(!StringUtil.isEmpty(vo.getLoginName())){
            sql += " and u.loginName = ?";
            param.add(vo.getLoginName());
        }
        log.info("@Dao notEntryNotificationResultQuery  End ");
        ListPageUtil listPage = new ListPageUtil(sql, param.toArray(), vo.getPageNo(), vo.getPageSize(), jdbcTemplate, null);
        return listPage;
    }
    /**
     * 待接收生物样本 血液系列
     * @param areaDeptId
     * @param communityDeptId
     * @param status
     * @param vo
     * @return
     */
    @Override
    public ListPageUtil notEntrySampleS(Integer areaDeptId,Integer communityDeptId, int status, TodoVo vo) {
        log.info("@Dao notEntryNotificationResultQuery  Start ");
        String sql = "select t2.data_id as `id`, t1.sid,t1.`name`,u.nickName,t1.sex,t1.age,t1.phone as cellPhone,"
                + "case when t1.`group`= 'A' then 'A' when t1.`group` = 'B'  then 'B' when t1.`group` = 'C' and t1.risk_level is null then 'C'   when t1.`group` = 'C' and t1.risk_level = 1 then 'Cd' when t1.`group` = 'C' and  t1.risk_level = 2 then 'Cg' ELSE NULL END `group`,"
                + "t1.in_group_date as inGroupDate,t1.overall_status_cy as overallStatusCy,t2.data_id as colonoscopyRecordId,t3.`name` as commdeptName " +
                " from hospital_intestine_review t1,hospital_todo_event t2,itsys_department t3 , itsys_user u  where"
                + "     t1.sid = t2.sid "
                + " and t2.community_dept_id = t3.id  "
                + " AND t1.create_user = u.loginName  "
                + " and t2.area_dept_id=? "
                + " and t2.type=?"
                + " and t2.`status`=?";
        List param = new ArrayList();
        param.add(areaDeptId);
        param.add(18);//18：待接收生物样本 血液系列
        param.add(status);

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

        if (!StringUtil.isEmpty(vo.getGroup())) {
            if (vo.getGroup().equals("Cg")) {
                sql += " and t1.`group` = 'C' and t1.risk_level = ?";
                param.add(2);
            } else if (vo.getGroup().equals("Cd")) {
                sql += " and t1.`group` = 'C' and t1.risk_level = ?";
                param.add(1);
            } else {
                sql += " and t1.`group` like ?";
                param.add("%" + vo.getGroup() + "%");
            }
        }

        if (communityDeptId != null) {
            sql += " and t2.`community_dept_id` = ?";
            param.add(communityDeptId);
        }
        if(!StringUtil.isEmpty(vo.getLoginName())){
            sql += " and u.loginName = ?";
            param.add(vo.getLoginName());
        }
        log.info("@Dao notEntryNotificationResultQuery  End ");
        ListPageUtil listPage = new ListPageUtil(sql, param.toArray(), vo.getPageNo(), vo.getPageSize(), jdbcTemplate, null);
        return listPage;
    }

    /**
     * 地区账号下的代办
     * @param communityDeptId
     * @param status
     * @param vo
     * @return
     */
    @Override
    public ListPageUtil notEntryStollDNA1(Integer communityDeptId, int status, TodoVo vo) {
        log.info("@Dao notEntryNotificationResultQuery  Start ");
        String sql = "select t2.data_id as `id`, t1.sid,t1.`name`,u.nickName,t1.sex,t1.age,t1.phone as cellPhone,"
                + "case when t1.`group`= 'A' then 'A' when t1.`group` = 'B'  then 'B' when t1.`group` = 'C' and t1.risk_level is null then 'C'   when t1.`group` = 'C' and t1.risk_level = 1 then 'Cd' when t1.`group` = 'C' and  t1.risk_level = 2 then 'Cg' ELSE NULL END `group`,"
                + "t1.in_group_date as inGroupDate,t1.overall_status_cy as overallStatusCy,t2.data_id as colonoscopyRecordId,t3.`name` as commdeptName " +
                " from hospital_intestine_review t1,hospital_todo_event t2,itsys_department t3 , itsys_user u  where"
                + "     t1.sid = t2.sid "
                + " and t2.community_dept_id = t3.id  "
                + " AND t1.create_user = u.loginName  "
                + " and t2.community_dept_id=? "
                + " and t2.type=?"
                + " and t2.`status`=?";
        List param = new ArrayList();
        param.add(communityDeptId);
        param.add(17);//17：未发放粪便DNA结果
        param.add(status);

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

        if (!StringUtil.isEmpty(vo.getGroup())) {
            if (vo.getGroup().equals("Cg")) {
                sql += " and t1.`group` = 'C' and t1.risk_level = ?";
                param.add(2);
            } else if (vo.getGroup().equals("Cd")) {
                sql += " and t1.`group` = 'C' and t1.risk_level = ?";
                param.add(1);
            } else {
                sql += " and t1.`group` like ?";
                param.add("%" + vo.getGroup() + "%");
            }
        }
        if(vo.getCommunityDeptId() != null){
            sql += " and t2.community_dept_id = ?";
            param.add(vo.getCommunityDeptId());
        }
        if(!StringUtil.isEmpty(vo.getLoginName())){
            sql += " and u.loginName = ?";
            param.add(vo.getLoginName());
        }
        log.info("@Dao notEntryNotificationResultQuery  End ");
        ListPageUtil listPage = new ListPageUtil(sql, param.toArray(), vo.getPageNo(), vo.getPageSize(), jdbcTemplate, null);
        return listPage;
    }

    @Override
    public ListPageUtil notEntryCancerQuery(Integer areaDeptId, String communityDeptId, int status, TodoVoPo vo) {
        log.info("@Dao notEntryCancerQuery  Start ");
        String sql = "select t2.type as cancerFormType ,t2.data_id as `id`, t1.sid,t1.`name`,u.nickName,t3.name as depName,t1.sex,t1.age,t1.phone as cellPhone,"
                + "case when t1.`group`= 'A' then 'A' when t1.`group` = 'B'  then 'B' when t1.`group` = 'C' and t1.risk_level is null then 'C'   when t1.`group` = 'C' and t1.risk_level = 1 then 'Cd' when t1.`group` = 'C' and  t1.risk_level = 2 then 'Cg' ELSE NULL END `group`,"
                + "t1.in_group_date as inGroupDate,t1.overall_status_cy as overallStatusCy,t2.data_id as colonoscopyRecordId,t3.`name` as commdeptName " +
                " from hospital_intestine_review t1,hospital_todo_event t2,itsys_department t3 , itsys_user u  where"
                + "     t1.sid = t2.sid "
                + " and t2.community_dept_id = t3.id  "
                + " AND t1.create_user = u.loginName  "
                + " and t2.area_dept_id=? "
                + " and t2.`status`=?";
        List param = new ArrayList();
        param.add(areaDeptId);
        param.add(status);

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

        if (!StringUtil.isEmpty(vo.getGroup())) {
            if (vo.getGroup().equals("Cg")) {
                sql += " and t1.`group` = 'C' and t1.risk_level = ?";
                param.add(2);
            } else if (vo.getGroup().equals("Cd")) {
                sql += " and t1.`group` = 'C' and t1.risk_level = ?";
                param.add(1);
            } else {
                sql += " and t1.`group` like ?";
                param.add("%" + vo.getGroup() + "%");
            }
        }
        if(vo.getCommunityDeptId() != null){
            sql += " and t2.community_dept_id = ?";
            param.add(vo.getCommunityDeptId());
        }
        if(!StringUtil.isEmpty(vo.getLoginName())){
            sql += " and u.loginName = ?";
            param.add(vo.getLoginName());
        }

        if(vo.getCancerFormType()!=null){
            sql += " and t2.type= ?";
            param.add(vo.getCancerFormType());
        }else{
            sql+= " and (t2.type="+Constans.PERSON_TODO_EVENT_TYPE20+" or t2.type="+Constans.PERSON_TODO_EVENT_TYPE21+" or t2.type="+Constans.PERSON_TODO_EVENT_TYPE22+" or t2.type="+Constans.PERSON_TODO_EVENT_TYPE23+") ";
        }


        log.info("@Dao notEntryCancerQuery  End ");
        ListPageUtil listPage = new ListPageUtil(sql, param.toArray(), vo.getPageNo(), vo.getPageSize(), jdbcTemplate, null);
        return listPage;
    }

    /**
     * 未发放粪便DNA结果
     * @param areaDeptId
     * @param status
     * @param vo
     * @return
     */
    @Override
    public ListPageUtil notEntryStollDNA(Integer areaDeptId, int status, TodoVo vo) {
        log.info("@Dao notEntryNotificationResultQuery  Start ");
        String sql = "select t2.data_id as `id`, t1.sid,t1.`name`,u.nickName,t1.sex,t1.age,t1.phone as cellPhone,"
                + "case when t1.`group`= 'A' then 'A' when t1.`group` = 'B'  then 'B' when t1.`group` = 'C' and t1.risk_level is null then 'C'   when t1.`group` = 'C' and t1.risk_level = 1 then 'Cd' when t1.`group` = 'C' and  t1.risk_level = 2 then 'Cg' ELSE NULL END `group`,"
                + "t1.in_group_date as inGroupDate,t1.overall_status_cy as overallStatusCy,t2.data_id as colonoscopyRecordId,t3.`name` as commdeptName " +
                " from hospital_intestine_review t1,hospital_todo_event t2,itsys_department t3 , itsys_user u  where"
                + "     t1.sid = t2.sid "
                + " and t2.community_dept_id = t3.id  "
                + " AND t1.create_user = u.loginName  "
                + " and t2.area_dept_id=? "
                + " and t2.type=?"
                + " and t2.`status`=?";
        List param = new ArrayList();
        param.add(areaDeptId);
        param.add(17);//17：未发放粪便DNA结果
        param.add(status);

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

        if (!StringUtil.isEmpty(vo.getGroup())) {
            if (vo.getGroup().equals("Cg")) {
                sql += " and t1.`group` = 'C' and t1.risk_level = ?";
                param.add(2);
            } else if (vo.getGroup().equals("Cd")) {
                sql += " and t1.`group` = 'C' and t1.risk_level = ?";
                param.add(1);
            } else {
                sql += " and t1.`group` like ?";
                param.add("%" + vo.getGroup() + "%");
            }
        }
        if(vo.getCommunityDeptId() != null){
            sql += " and t2.community_dept_id = ?";
            param.add(vo.getCommunityDeptId());
        }
        if(!StringUtil.isEmpty(vo.getLoginName())){
            sql += " and u.loginName = ?";
            param.add(vo.getLoginName());
        }
        log.info("@Dao notEntryNotificationResultQuery  End ");
        ListPageUtil listPage = new ListPageUtil(sql, param.toArray(), vo.getPageNo(), vo.getPageSize(), jdbcTemplate, null);
        return listPage;
    }

    //  查询type 为 20,21,22,23：癌相关详情信息  zl
    @Override
    public ListPageUtil notEntryStoCancer(Integer communityDeptId, int status, TodoVo vo) {

        log.info("@Dao notEntryPathologyResultQuery  Start ");
        String sql = "select  t4.`name` as commdeptName,t2.data_id as `id`,  t1.sid,t1.`name`,u.nickName,t1.sex,t1.age,t1.phone as cellPhone,"
                + "case when t1.`group`= 'A' then 'A' when t1.`group` = 'B'  then 'B' when t1.`group` = 'C' and t1.risk_level is null then 'C'   when t1.`group` = 'C' and t1.risk_level = 1 then 'Cd' when t1.`group` = 'C' and  t1.risk_level = 2 then 'Cg' ELSE NULL END `group`,"
                + "t1.in_group_date as inGroupDate,t1.overall_status_cy as overallStatusCy,t2.data_id as colonoscopyRecordId,t3.`name` as deptName from hospital_intestine_review t1,hospital_todo_event t2,itsys_department t3,itsys_department t4 , itsys_user u where"
                + "     t1.sid = t2.sid "
                + " and t2.area_dept_id = t3.id  "
                + " and t1.community_dept_id = t4.id  "
                + " AND t1.create_user = u.loginName  "
                + " and t2.area_dept_id=? "
                + " and t2.type IN (20,21,22,23)"
                + " and t2.`status`=?";
        List param = new ArrayList();
        param.add(vo.getAreaDeptId());
        //param.add("(20,21,22,23)");
        param.add(status);

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

        if (!StringUtil.isEmpty(vo.getGroup())) {
            if (vo.getGroup().equals("Cg")) {
                sql += " and t1.`group` = 'C' and t1.risk_level = ?";
                param.add(2);
            } else if (vo.getGroup().equals("Cd")) {
                sql += " and t1.`group` = 'C' and t1.risk_level = ?";
                param.add(1);
            } else {
                sql += " and t1.`group` like ?";
                param.add("%" + vo.getGroup() + "%");
            }
        }
        if (communityDeptId != null) {
            sql += " and t2.`community_dept_id` = ?";
            param.add(communityDeptId);
        }
        if(!StringUtil.isEmpty(vo.getLoginName())){
            sql += " and u.loginName = ?";
            param.add(vo.getLoginName());
        }
        log.info("@Dao notEntryPathologyResultQuery  End ");
        ListPageUtil listPage = new ListPageUtil(sql, param.toArray(), vo.getPageNo(), vo.getPageSize(), jdbcTemplate, null);
        return listPage;
    }
}
