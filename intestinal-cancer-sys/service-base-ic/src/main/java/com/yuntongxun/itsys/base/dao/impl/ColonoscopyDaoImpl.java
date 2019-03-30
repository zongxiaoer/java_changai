package com.yuntongxun.itsys.base.dao.impl;

import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.GlobalErrorCode;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.common.util.StringUtil;
import com.yuntongxun.itsys.base.dao.ColonoscopyDao;
import com.yuntongxun.itsys.base.po.HospitalColonoscopyRecord;
import com.yuntongxun.itsys.base.po.ReservationDetail;
import com.yuntongxun.itsys.base.po.ReserveAllocation;
import com.yuntongxun.itsys.base.po.dto.allocation.AllocationDto;
import com.yuntongxun.itsys.base.po.dto.courier.HospitalReferenceRecordDto;
import com.yuntongxun.itsys.base.vo.*;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class ColonoscopyDaoImpl implements ColonoscopyDao {

    @Autowired
    private JdbcTemplate jdbctemp;

    private final Logger log = LogManager.getLogger(ColonoscopyDaoImpl.class);

    public final static int DEPARTMENT_TYPE_COMMUNITY = 1;
    public final static int DEPARTMENT_TYPE_AREA = 2;
    public final static int DEPARTMENT_TYPE_NATION = 3;

	@Override
	public ListPageUtil query(ColonoscopyVo queryCondition,Integer communityDeptId,Integer areaDeptId,Integer deptType,boolean isPage) {
		List<Object> parm = new ArrayList<Object>();
		String sql = "SELECT t1.`id` AS `id`, t1.sid AS sid, t2.`name` AS `name`, u.nickName, t2.`sex` AS `sex`,"
				+ " t2.`age` AS `age`, t2.`phone` AS `phone`, "
				+ " case when t2.`group`= 'A' then 'A' when t2.`group` = 'B'  then 'B' when t2.`group` = 'C' and t2.risk_level is null then 'C' when t2.`group` = 'C' and t2.risk_level = 1 then 'Cd' when t2.`group` = 'C' and  t2.risk_level = 2 then 'Cg' ELSE NULL END 'group',"
				+ " t2.`in_group_date` AS `inGroupDate`, "
				+ "t2.overall_status_cy AS overallStatusCy, t1.reserve_status AS reserveStatus, t1.reserve_date AS reserveDate, t1.allocation_id AS allocationId, "
				+ "t1.examination_status AS examinationStatus, t1.finished_status AS finishedStatus, t1.examination_date AS examinationDate,"
				+ "t1.notification_entry_status as notificationEntryStatus,t1.notification_issue_status AS notificationIssueStatus,"
				+ "t1.notification_issue_date as notificationIssueDate,t1.result_id AS resultId,t1.pathology_id AS pathologyId,"
				+ "t1.notification_id AS notificationId,t1.notification_issue_mode AS notificationIssueMode, t1.reserve_id as reserveId,"
				+ "t1.notification_issue_worker_code AS notificationIssueWorkerCode,t1.notification_issue_note AS notificationIssueNote,d.dept_code  "//增加d.dept_code查询用来区分
				+ " FROM hospital_colonoscopy_record t1, hospital_intestine_review t2 LEFT JOIN itsys_department d on t2.area_dept_id= d.id , itsys_user u  WHERE t2.sid = t1.sid  AND t2.create_user = u.loginName  and t2.overall_status_cy <> 2 ";
		sql += " and t1.community_dept_id = ?";
        parm.add(communityDeptId);

        if (!StringUtil.isEmpty(queryCondition.getSid())) {
            sql += " and t1.sid like ?";
            parm.add("%" + queryCondition.getSid() + "%");
        }
        if (!StringUtil.isEmpty(queryCondition.getName())) {
            sql += " and t2.`name` like ? ";
            parm.add("%" + queryCondition.getName() + "%");
        }
        if (!StringUtil.isEmpty(queryCondition.getPhone())) {
            sql += " and t2.`phone` like ? ";
            parm.add("%" + queryCondition.getPhone() + "%");
        }
        if (!StringUtil.isEmpty(queryCondition.getGroup())) {
            if (queryCondition.getGroup().equals("Cg")) {
                sql += " and t2.`group` = 'C' and t2.risk_level = ?";
                parm.add(2);
            } else if (queryCondition.getGroup().equals("Cd")) {
                sql += " and t2.`group` = 'C' and t2.risk_level = ?";
                parm.add(1);
            } else {
                sql += " and t2.`group` = ? ";
                parm.add(queryCondition.getGroup());
            }
        }
        if (!StringUtil.isEmpty(queryCondition.getReserveStatus())) {
            sql += " and t1.reserve_status = ? ";
            parm.add(queryCondition.getReserveStatus());
        }
        if (!StringUtil.isEmpty(queryCondition.getExaminationStatus())) {
            sql += " and t1.examination_status = ? ";
            parm.add(queryCondition.getExaminationStatus());
        }
        if (!StringUtil.isEmpty(queryCondition.getFinishedStatus())) {
            sql += " and t1.finished_status = ? ";
            parm.add(queryCondition.getFinishedStatus());
        }
        if (!StringUtil.isEmpty(queryCondition.getNotificationIssueStatus())) {
            sql += " and t1.notification_issue_status = ? ";
            parm.add(queryCondition.getNotificationIssueStatus());
        }
        if (!StringUtil.isEmpty(queryCondition.getLoginName())) {
            sql += " and u.loginName = ?";
            parm.add(queryCondition.getLoginName());
        }

        sql += " order by t2.date_created desc ";

        if (isPage) {
            ListPageUtil listPage = new ListPageUtil(sql, parm.toArray(), queryCondition.getPageNo(), queryCondition.getPageSize(), jdbctemp, null);
            return listPage;
        } else {
            ListPageUtil listPageNoPaging = new ListPageUtil(sql, parm.toArray(), 1, -1, jdbctemp, null);
            return listPageNoPaging;
        }
    }

    @Override
    public ListPageUtil queryForArea(ColonoscopyVo queryCondition, Integer communityDeptId, Integer areaDeptId, Integer deptType, boolean isPage) {
        List<Object> parm = new ArrayList<Object>();//宗曈修改已签到为签到
        String sql = "SELECT (select status from hospital_todo_event  as ht where  ht.`data_id`=t1.id and ht.type=6 and ht.sid=t1.sid and status=1) as chongxinyuyue," +//添加重新预约按钮参数
                "d.`name` as depName,t1.`id` AS `id`, t1.sid AS sid,"
                + " t2.`name` AS `name`, u.nickName , t2.`sex` AS `sex`, t2.`age` AS `age`, t2.`phone` AS `phone`,"
                + "  case when t2.`group`= 'A' then 'A' when t2.`group` = 'B'  then 'B' when t2.`group` = 'C' and t2.risk_level is null then 'C' when t2.`group` = 'C' and t2.risk_level = 1 then 'Cd' when t2.`group` = 'C' and  t2.risk_level = 2 then 'Cg' ELSE NULL END 'group', "
                + "t2.`in_group_date` AS `inGroupDate`, t2.overall_status_cy AS overallStatusCy, "
                + "t1.reserve_status AS reserveStatus, t1.reserve_date AS reserveDate, "
                + "t1.examination_status AS examinationStatus, t1.finished_status AS finishedStatus, "
                + "t1.examination_date AS examinationDate,t1.notification_entry_status as notificationEntryStatus,"
                + "t1.notification_issue_status AS notificationIssueStatus,t1.notification_issue_date as notificationIssueDate,"
                + "t1.result_id AS resultId,t1.pathology_id AS pathologyId,t1.notification_id AS notificationId,"
                + "t1.notification_issue_mode AS notificationIssueMode,t1.notification_issue_worker_code AS notificationIssueWorkerCode,"
                + "t1.notification_issue_note AS notificationIssueNote,result_status as resultState, pathology_status as pathologyStatus,d.dept_code,t1.allocation_id AS allocationId,t1.reserve_id as reserveId, "
                + "t1.source_type as source_type FROM hospital_colonoscopy_record t1, hospital_intestine_review t2,"
                + "itsys_department d , itsys_user u   WHERE t2.sid = t1.sid and t2.community_dept_id=d.id  AND t2.create_user = u.loginName  and t2.overall_status_cy <> 2 ";
        sql += " and t1.area_dept_id = ?";
        parm.add(areaDeptId);

        if (communityDeptId != null) {
            sql += " and t1.community_dept_id = ?";
            parm.add(communityDeptId);
        }

        if (!StringUtil.isEmpty(queryCondition.getSid())) {
            sql += " and t1.sid like ?";
            parm.add("%" + queryCondition.getSid() + "%");
        }
        if (!StringUtil.isEmpty(queryCondition.getName())) {
            sql += " and t2.`name` like ? ";
            parm.add("%" + queryCondition.getName() + "%");
        }
        if (!StringUtil.isEmpty(queryCondition.getPhone())) {
            sql += " and t2.`phone` like ? ";
            parm.add("%" + queryCondition.getPhone() + "%");
        }
        if (!StringUtil.isEmpty(queryCondition.getGroup())) {
            if (queryCondition.getGroup().equals("Cg")) {
                sql += " and t2.`group` = 'C' and t2.risk_level = ?";
                parm.add(2);
            } else if (queryCondition.getGroup().equals("Cd")) {
                sql += " and t2.`group` = 'C' and t2.risk_level = ?";
                parm.add(1);
            } else {
                sql += " and t2.`group` = ? ";
                parm.add(queryCondition.getGroup());
            }
        }
        if (!StringUtil.isEmpty(queryCondition.getReserveStatus())) {
            sql += " and t1.reserve_status = ? ";
            parm.add(queryCondition.getReserveStatus());
        }
        if (!StringUtil.isEmpty(queryCondition.getExaminationStatus())) {
            sql += " and t1.examination_status = ? ";
            parm.add(queryCondition.getExaminationStatus());
        }
        if (!StringUtil.isEmpty(queryCondition.getFinishedStatus())) {
            sql += " and t1.finished_status = ? ";
            parm.add(queryCondition.getFinishedStatus());
        }
        if (!StringUtil.isEmpty(queryCondition.getNotificationIssueStatus())) {
            sql += " and t1.notification_issue_status = ? ";
            parm.add(queryCondition.getNotificationIssueStatus());
        }
        if (!StringUtil.isEmpty(queryCondition.getLoginName())) {
            sql += " and u.loginName = ?";
            parm.add(queryCondition.getLoginName());
        }

        //签到
        if(!StringUtils.isEmpty(queryCondition.getSignState())){
            if(Constans.EXAMINATION_STATUS11.equals(queryCondition.getSignState())){
                sql += " and t1.examination_status is null ";
            }else if(Constans.EXAMINATION_STATUS22.equals(queryCondition.getSignState())){
                sql += " and t1.examination_status is not null ";
            }
        }

        //新增结肠镜结果状态，1：未录入，2：已录入
        if (!StringUtil.isEmpty(queryCondition.getResultStatus())) {
            sql += " and t1.result_status = ?";
            parm.add(queryCondition.getResultStatus());
        }

        //order by t1.date_created desc
        //sql +=" order by t1.update_time desc ";
        if (isPage) {
            ListPageUtil listPage = new ListPageUtil(sql, parm.toArray(), queryCondition.getPageNo(), queryCondition.getPageSize(), jdbctemp, null,Constans.SUBQUERY);
            return listPage;
        } else {
            ListPageUtil listPageNoPaging = new ListPageUtil(sql, parm.toArray(), 1, -1, jdbctemp, null,Constans.SUBQUERY);
            return listPageNoPaging;
        }
    }


    @Override
    public void updateReserveStatus(int colonoscopyRecordId, Date reserveDate, int reserveOperator, int reserveId, int reserveStatus, int allocationId) {
        String sql = "update hospital_colonoscopy_record set result_status=1,reserve_id=?,reserve_date=?,reserve_operator=?,reserve_status=?,allocation_id=?,update_time=now(),reserve_status_date=now() where `id`=?";
        jdbctemp.update(sql, new Object[]{reserveId, reserveDate, reserveOperator, reserveStatus, allocationId, colonoscopyRecordId});
    }

    @Override
    //不是通过本系统预约过来的用户
    public void updateReserveStatusByOtherSys(int colonoscopyRecordId, int reserveOperator, int reserveStatus, Date reserveDate) {
        //添加预约时间字段 by maxiang at 2018-06-25
        //String sql = "update hospital_colonoscopy_record set result_status=1,reserve_operator=?,reserve_status=?,update_time=now(),reserve_status_date=now() where `id`=?";
        String sql = "update hospital_colonoscopy_record set result_status=1,reserve_operator=?,reserve_date=?,reserve_status=?,update_time=now(),reserve_status_date=now() where `id`=?";
        jdbctemp.update(sql, new Object[]{reserveOperator, reserveDate, reserveStatus, colonoscopyRecordId});
    }

	@Override
	public ReserveDetail getReserveDetail(int id) {
		String sql = "select t2.sid as sid,t2.`name` as `name`,t2.phone as phone,t2.in_group_date as inGroupDate, t2.`group` as `group`,t4.`name` as `deptName`,t4.`desc` as `deptDesc`,t3.examination_place as examinationPlace,t3.reservation_date as reservationDate,t3.start_time as startTime, t3.end_time as endTime,t2.risk_level as riskLevel,t2.sex as sex,\n" +
                "  t2.age as age, t2.id_card as idCard from hospital_colonoscopy_record t1,hospital_intestine_review t2,hospital_colonoscopy_reservation_allocation t3,itsys_department t4 where t1.allocation_id = t3.id and t1.sid=t2.sid and t4.id = t2.area_dept_id and t1.id=?";
		ReserveDetail result=jdbctemp.queryForObject(sql,new BeanPropertyRowMapper<ReserveDetail>(ReserveDetail.class),id);
		return result;
	}

    @Override
    public void updateNotificationIssueStatus(ColonoscopyIssueVo vo, Integer operatorId, int colonoscopyNotificationIssued) {
        /**
         * 更新 结肠镜检查记录表 hospital_colonoscopy_record 表的相关字段，
         * notification_issue_status=2，
         * notification_issue_date=当前日期，
         * notification_issue_operator=当前操作用户id；
         * notification_issue_worker_code
         * notification_issue_note
         * notification_issue_mode
         */

        String sql = "update hospital_colonoscopy_record set notification_issue_status=?,notification_issue_date=?,notification_issue_operator=?,notification_issue_worker_code=?,notification_issue_note=?,notification_issue_mode=?,update_time=now() where `id`=?";
        jdbctemp.update(sql, new Object[]{colonoscopyNotificationIssued, vo.getIssueDateForSql(), operatorId, vo.getWorkerCode(), vo.getNote(), vo.getMode(), vo.getId()});
    }

    /**
     * 修改结肠镜检查记录表中的告知书录入状态
     * @param vo
     */
    @Override
    public void updateNotificationEntryStatus(ColonoscopyEntryVo vo) {
        String sql = "update hospital_colonoscopy_record set notification_entry_status=?,notification_entry_date=?,notification_entry_operator=?,notification_id=?,notification_issue_status=?,update_time=now() where `id`=?";
        jdbctemp.update(sql, new Object[]{vo.getNotificationEntryStatus(), vo.getNotificationEntryDate(), vo.getNotificationEntryOperator(), vo.getNotificationId(), vo.getNotificationIssueStatus(), vo.getColonoscopyRecordId()});
    }

    /**
     * 修改结肠镜检查记录表中的肠镜录入状态
     * @param vo
     */
    @Override
    public void updateNotificationResultStatus(ColonoscopyResultVo vo) {
        if (vo.getExaminationStatus() != null) {
            if (vo.getAllState() == 1) {
                //未完成肠镜
                String sql = "update hospital_colonoscopy_record set examination_status=?,examination_date=?,examination_check_date=?,examination_operator=?,finished_status=?,result_id=?,result_status=?,result_date=?,result_operator=?,update_time=now() where `id`=?";
                jdbctemp.update(sql, new Object[]{vo.getExaminationStatus(), vo.getExamination_date(), vo.getExamination_check_date(), vo.getExaminationOperator(), vo.getFinishedStatus(), vo.getResultId(), vo.getResultStatus(), vo.getResultDate(), vo.getResultOperator(), vo.getColonoscopyRecordId()});
            } else if (vo.getAllState() == 2) {
                //完成肠镜&&有病理
                String sql = "update hospital_colonoscopy_record set examination_status=?,examination_date=?,examination_check_date=?,examination_operator=?,finished_status=?,result_id=?,result_status=?,result_date=?,result_operator=?,pathology_status=?,update_time=now() where `id`=?";
                jdbctemp.update(sql, new Object[]{vo.getExaminationStatus(), vo.getExamination_date(), vo.getExamination_check_date(), vo.getExaminationOperator(), vo.getFinishedStatus(), vo.getResultId(), vo.getResultStatus(), vo.getResultDate(), vo.getResultOperator(), vo.getPathologyStatus(), vo.getColonoscopyRecordId()});
            } else if (vo.getAllState() == 3) {
                //完成肠镜但是没有病理
                String sql = "update hospital_colonoscopy_record set examination_status=?,examination_date=?,examination_check_date=?,examination_operator=?,finished_status=?,result_id=?,result_status=?,result_date=?,result_operator=?,notification_entry_status=?,update_time=now() where `id`=?";
                jdbctemp.update(sql, new Object[]{vo.getExaminationStatus(), vo.getExamination_date(), vo.getExamination_check_date(), vo.getExaminationOperator(), vo.getFinishedStatus(), vo.getResultId(), vo.getResultStatus(), vo.getResultDate(), vo.getResultOperator(), vo.getNotificationEntryStatus(), vo.getColonoscopyRecordId()});
            }
        } else {

        }
//        if(vo.getExaminationStatus()!=null) {
//            String sql = "update hospital_colonoscopy_record set examination_status=?,examination_date=now(),examination_check_date=now(),examination_operator=?,finished_status=?,result_id=?,result_status=?,result_date=?,result_operator=?,update_time=now() where `id`=?";
//            jdbctemp.update(sql, new Object[]{vo.getExaminationStatus(),vo.getExaminationOperator(),vo.getFinishedStatus(), vo.getResultId(), vo.getResultStatus(), vo.getResultDate(), vo.getResultOperator(), vo.getColonoscopyRecordId()});
//
//        }else{
//            String sql = "update hospital_colonoscopy_record set finished_status=?,result_id=?,result_status=?,result_date=?,result_operator=?,update_time=now() where `id`=?";
//            jdbctemp.update(sql, new Object[]{vo.getFinishedStatus(), vo.getResultId(), vo.getResultStatus(), vo.getResultDate(), vo.getResultOperator(), vo.getColonoscopyRecordId()});
//        }
    }

    /**
     * 修改结肠镜检查记录表中的病理状态
     * @param vo
     */
    @Override
    public void updateNotificationPathologyStatus(ColonoscopyPathologyStatusVo vo) {
        String sql = "update hospital_colonoscopy_record set pathology_id=?,pathology_status=?,pathology_date=?,pathology_operator=?,notification_entry_status=?,update_time=now() where `id`=?";
        //String sql = "update hospital_colonoscopy_record set pathology_id=?,pathology_status=?,pathology_date=?,pathology_operator=?,update_time=now() where `id`=?";
        jdbctemp.update(sql, new Object[]{vo.getPathologyId(), vo.getPathologyStatus(), vo.getPathologyDate(), vo.getPathologyOperator(), vo.getNotificationEntryStatus(), vo.getColonoscopyRecordId()});
    }


    @Override
    public int add(final HospitalColonoscopyRecord result) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final String sql = "INSERT INTO hospital_colonoscopy_record(sid,stage,reserve_status,community_dept_id,area_dept_id,date_created,update_time,source_type,editoperation_source,operation_source_id) values(?,?,?,?,?,now(),now(),?,?,?)";
        jdbctemp.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setObject(1, result.getSid());
                ps.setObject(2, result.getStage());
                ps.setObject(3, result.getReserve_status());
                ps.setObject(4, result.getCommunity_dept_id());
                ps.setObject(5, result.getArea_dept_id());
                ps.setObject(6, result.getSource_type());
                ps.setObject(7, result.getOperationSource());
                ps.setObject(8, result.getOperationSourceId());
                return ps;
            }

        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    /**
     * 获取到需要签到的人员列表
     * maxiang
     */
    public void getNeedSignedUsers() {
        String sql = "SELECT\n" +
                "\t*\n" +
                "FROM\n" +
                "\thospital_colonoscopy_record record\n" +
                "INNER JOIN hospital_intestine_review review ON record.sid = review.sid\n" +
                "INNER JOIN hospital_colonoscopy_reservation_allocation allocation ON record.allocation_id = allocation.id\n" +
                "INNER JOIN itsys_department dept ON dept.id = review.community_dept_id\n" +
                "\n" +
                "WHERE\n" +
                "\t1 = 1\n" +
                "AND record.reserve_status = 2\n" +
                "AND examination_status IS NULL\n" +
                "AND review.area_dept_id=1 ";
    }

    /**
     * 批量或单个修改结肠镜检查记录表中检查（就诊）状态
     * <p>
     * examination_status int 结肠镜检查就诊状态，1：未就诊，2：已就诊
     * examination_date datetime 就诊时间 当前时间
     * examination_operator int 确认就诊医生 当前登录医生
     *
     * @param vo
     */
    @Override
    public void updateExaminationStatus(ColonoscopyExaminationVo vo) {

        if (vo.getExaminationStatus() == Constans.EXAMINATION_STATUS1) {
            //未就诊
            String sql = "update hospital_colonoscopy_record set examination_status=?,examination_date=now(),examination_check_date=now(),examination_operator=?,update_time=now(),result_status=null,pathology_status=null,notification_entry_status=null,examination_check_date=now() where `id` in(" + vo.getRecordIds() + ")";
            jdbctemp.update(sql, new Object[]{vo.getExaminationStatus(), vo.getExaminationOperator()});
        } else {
            //已就诊
            String sql = "update hospital_colonoscopy_record set examination_status=?,examination_date=now(),examination_operator=?,examination_check_date=now(),result_status=1,update_time=now() where `id` in(" + vo.getRecordIds() + ")";
            jdbctemp.update(sql, new Object[]{vo.getExaminationStatus(), vo.getExaminationOperator()});
        }
    }

    /**
     * 国家肠镜管理筛查查询
     */
    @Override
    public ListPageUtil queryForNationList(ColonoscopyVo queryCondition, Integer nationId, Integer deptType, boolean isPage) {
        // TODO Auto-generated method stub
        List<Object> parm = new ArrayList<Object>();
        String sql = "SELECT t1.`id` AS `id`, t1.sid AS sid,d.`name` as depName,d1.name as areaName,"
                /*"SELECT d.`name` as depName,t1.`id` AS `id`, t1.sid AS sid,"*/
                + " t2.`name` AS `name`, u.nickName , t2.`sex` AS `sex`, t2.`age` AS `age`, t2.`phone` AS `phone`,"
                + "  case when t2.`group`= 'A' then 'A' when t2.`group` = 'B'  then 'B' when t2.`group` = 'C' and t2.risk_level is null then 'C' when t2.`group` = 'C' and t2.risk_level = 1 then 'Cd' when t2.`group` = 'C' and  t2.risk_level = 2 then 'Cg' ELSE NULL END 'group', "
                + "t2.`in_group_date` AS `inGroupDate`, t2.overall_status_cy AS overallStatusCy, "
                + "t1.reserve_status AS reserveStatus, t1.reserve_date AS reserveDate, "
                + "t1.examination_status AS examinationStatus, t1.finished_status AS finishedStatus, "
                + "t1.examination_date AS examinationDate,t1.notification_entry_status as notificationEntryStatus,"
                + "t1.notification_issue_status AS notificationIssueStatus,t1.notification_issue_date as notificationIssueDate,"
                + "t1.result_id AS resultId,t1.pathology_id AS pathologyId,t1.notification_id AS notificationId,"
                + "t1.notification_issue_mode AS notificationIssueMode,t1.notification_issue_worker_code AS notificationIssueWorkerCode,"
                + "t1.notification_issue_note AS notificationIssueNote,t1.result_status as resultState, t1.pathology_status as pathologyStatus,"
                + "t1.source_type as source_type FROM hospital_colonoscopy_record t1, hospital_intestine_review t2,"
                + "itsys_department d,itsys_department d1 , itsys_user u  WHERE t2.sid = t1.sid and t2.overall_status_cy <> 2  and t2.community_dept_id=d.id and t2.area_dept_id = d1.id AND t2.create_user = u.loginName ";
		/*sql += " and t1.area_dept_id = ?";
        parm.add(areaDeptId);*/
        //按地区条件查询
        if (queryCondition.getAreaDeptId() != null) {
            sql += " and t1.area_dept_id = ?";
            parm.add(queryCondition.getAreaDeptId());
        }
        //社区条件查询
        if (queryCondition.getCommunityDeptId() != null) {
            sql += " and t1.community_dept_id = ?";
            parm.add(queryCondition.getCommunityDeptId());
        }
        if (!StringUtil.isEmpty(queryCondition.getSid())) {
            sql += " and t1.sid like ?";
            parm.add("%" + queryCondition.getSid() + "%");
        }
        if (!StringUtil.isEmpty(queryCondition.getName())) {
            sql += " and t2.`name` like ? ";
            parm.add("%" + queryCondition.getName() + "%");
        }
        if (!StringUtil.isEmpty(queryCondition.getPhone())) {
            sql += " and t2.`phone` like ? ";
            parm.add("%" + queryCondition.getPhone() + "%");
        }
        if (!StringUtil.isEmpty(queryCondition.getGroup())) {
            if (queryCondition.getGroup().equals("Cg")) {
                sql += " and t2.`group` = 'C' and t2.risk_level = ?";
                parm.add(2);
            } else if (queryCondition.getGroup().equals("Cd")) {
                sql += " and t2.`group` = 'C' and t2.risk_level = ?";
                parm.add(1);
            } else {
                sql += " and t2.`group` = ? ";
                parm.add(queryCondition.getGroup());
            }
        }
        //新增结肠镜结果状态，1：未录入，2：已录入
        if (!StringUtil.isEmpty(queryCondition.getResultStatus())) {
            sql += " and t1.result_status = ?";
            parm.add(queryCondition.getResultStatus());
        }
        //新增结肠镜病理录入状态，1：未录入，2：已录入
        if (!StringUtil.isEmpty(queryCondition.getPathologyStatus())) {
            sql += " and t1.pathology_status = ?";
            parm.add(queryCondition.getPathologyStatus());
        }
        //结肠镜告知书录入状态，1：未录入，2：已录入
        if (!StringUtil.isEmpty(queryCondition.getNotificationEntryStatus())) {
            sql += " and t1.notification_entry_status = ?";
            parm.add(queryCondition.getNotificationEntryStatus());
        }
        //是否移除
        if (queryCondition.getDeleteFlag() != null) {
            sql += " and t2.delete_flag = ?";
            parm.add(queryCondition.getDeleteFlag());
        }
        if (!StringUtil.isEmpty(queryCondition.getReserveStatus())) {
            sql += " and t1.reserve_status = ? ";
            parm.add(queryCondition.getReserveStatus());
        }
        if (!StringUtil.isEmpty(queryCondition.getExaminationStatus())) {
            sql += " and t1.examination_status = ? ";
            parm.add(queryCondition.getExaminationStatus());
        }
        if (!StringUtil.isEmpty(queryCondition.getFinishedStatus())) {
            sql += " and t1.finished_status = ? ";
            parm.add(queryCondition.getFinishedStatus());
        }
        if (!StringUtil.isEmpty(queryCondition.getNotificationIssueStatus())) {
            sql += " and t1.notification_issue_status = ? ";
            parm.add(queryCondition.getNotificationIssueStatus());
        }
        if (!StringUtil.isEmpty(queryCondition.getLoginName())) {
            sql += " and u.loginName = ?";
            parm.add(queryCondition.getLoginName());
        }


        //签到
        if(!StringUtils.isEmpty(queryCondition.getSignState())){
            if(Constans.EXAMINATION_STATUS11.equals(queryCondition.getSignState())){
                sql += " and t1.examination_status is null ";
            }else if(Constans.EXAMINATION_STATUS22.equals(queryCondition.getSignState())){
                sql += " and t1.examination_status is not null ";
            }
        }


        if (isPage) {
            ListPageUtil listPage = new ListPageUtil(sql, parm.toArray(), queryCondition.getPageNo(), queryCondition.getPageSize(), jdbctemp, null);
            return listPage;
        } else {
            ListPageUtil listPageNoPaging = new ListPageUtil(sql, parm.toArray(), 1, -1, jdbctemp, null);
            return listPageNoPaging;
        }
    }

    //取消预约 修改预约状态
    @Override
    public void updateReserveStatusForCancelBooking(int colonoscopyRecordId) {
        String sql = "update hospital_colonoscopy_record set result_status=null,reserve_id=null,reserve_date=null,reserve_operator=null,reserve_status_date=null,reserve_status=1,allocation_id=null,update_time=now() where `id`=?";
        jdbctemp.update(sql, new Object[]{colonoscopyRecordId});
    }

    @Override
    public List<HospitalColonoscopyRecord> queryById(Integer colonoscopyRecordId) {
        String sql = "select * from hospital_colonoscopy_record where id=?";
        return jdbctemp.query(sql, new BeanPropertyRowMapper<HospitalColonoscopyRecord>(HospitalColonoscopyRecord.class),colonoscopyRecordId);

    }

    @Override
    public List<HospitalColonoscopyRecord> queryBySourceAndId(String editoperationSource, Integer operationSourceId) {
        String sql = "select * from hospital_colonoscopy_record where editoperation_source=? and operation_source_id=?";
        return jdbctemp.query(sql, new BeanPropertyRowMapper<HospitalColonoscopyRecord>(HospitalColonoscopyRecord.class),editoperationSource,operationSourceId);
    }

    //导出地区肠镜管理列表
    @Override
    public List<ColonoscopyVo> queryForAreaExcel(ColonoscopyVo queryCondition, Integer communityDeptId,
                                                 Integer areaDeptId, Integer deptType, boolean isPage) {
        // TODO Auto-generated method stub
        List<Object> parm = new ArrayList<Object>();
        String sql = "SELECT d.`name` as depName,d1.name as areaName,t1.`id` AS `id`, t1.sid AS sid,"
                + " t2.`name` AS `name`, t2.`sex` AS `sex`, t2.`age` AS `age`, t2.`phone` AS `phone`,"
                + "  case when t2.`group`= 'A' then 'A' when t2.`group` = 'B'  then 'B' when t2.`group` = 'C' and t2.risk_level is null then 'C' when t2.`group` = 'C' and t2.risk_level = 1 then 'Cd' when t2.`group` = 'C' and  t2.risk_level = 2 then 'Cg' ELSE NULL END 'group', "
                + "t2.`in_group_date` AS `inGroupDate`, t2.overall_status_cy AS overallStatusCy, "
                + "t1.reserve_status AS reserveStatus, t1.reserve_date AS reserveDate, "
                + "t1.examination_status AS examinationStatus, t1.finished_status AS finishedStatus, "
                + "t1.examination_date AS examinationDate,t1.notification_entry_status as notificationEntryStatus,"
                + "t1.result_status as resultStatus,t1.pathology_status as pathologyStatus,t1.notification_issue_status AS notificationIssueStatus,"
                + "u.nickName as createUser"
/*				+ "t1.notification_issue_status AS notificationIssueStatus,t1.notification_issue_date as notificationIssueDate,"
				+ "t1.result_id AS resultId,t1.pathology_id AS pathologyId,t1.notification_id AS notificationId,"
				+ "t1.notification_issue_mode AS notificationIssueMode,t1.notification_issue_worker_code AS notificationIssueWorkerCode,"
				+ "t1.notification_issue_note AS notificationIssueNote,result_status as resultState, pathology_status as pathologyStatus,"*/
                /*+ "t1.source_type as source_type FROM hospital_colonoscopy_record t1, hospital_intestine_review t2,"*/
                + " FROM hospital_colonoscopy_record t1, hospital_intestine_review t2,"
                + "itsys_department d,itsys_department d1,itsys_user u  WHERE t2.sid = t1.sid and t2.create_user = u.loginName "
                + " and t2.community_dept_id=d.id and t2.area_dept_id = d1.id and t2.overall_status_cy <> 2 ";


        sql += " and t1.area_dept_id = ?";
        parm.add(areaDeptId);

        if (communityDeptId != null) {
            sql += " and t1.community_dept_id = ?";
            parm.add(communityDeptId);
        }

        if (!StringUtil.isEmpty(queryCondition.getSid())) {
            sql += " and t1.sid like ?";
            parm.add("%" + queryCondition.getSid() + "%");
        }
        if (!StringUtil.isEmpty(queryCondition.getName())) {
            sql += " and t2.`name` like ? ";
            parm.add("%" + queryCondition.getName() + "%");
        }
        if (!StringUtil.isEmpty(queryCondition.getPhone())) {
            sql += " and t2.`phone` like ? ";
            parm.add("%" + queryCondition.getPhone() + "%");
        }
        if (!StringUtil.isEmpty(queryCondition.getGroup())) {
            if (queryCondition.getGroup().equals("Cg")) {
                sql += " and t2.`group` = 'C' and t2.risk_level = ?";
                parm.add(2);
            } else if (queryCondition.getGroup().equals("Cd")) {
                sql += " and t2.`group` = 'C' and t2.risk_level = ?";
                parm.add(1);
            } else {
                sql += " and t2.`group` = ? ";
                parm.add(queryCondition.getGroup());
            }
        }
        if (!StringUtil.isEmpty(queryCondition.getReserveStatus())) {
            sql += " and t1.reserve_status = ? ";
            parm.add(queryCondition.getReserveStatus());
        }
        if (!StringUtil.isEmpty(queryCondition.getExaminationStatus())) {
            sql += " and t1.examination_status = ? ";
            parm.add(queryCondition.getExaminationStatus());
        }
        if (!StringUtil.isEmpty(queryCondition.getFinishedStatus())) {
            sql += " and t1.finished_status = ? ";
            parm.add(queryCondition.getFinishedStatus());
        }
        if (!StringUtil.isEmpty(queryCondition.getNotificationIssueStatus())) {
            sql += " and t1.notification_issue_status = ? ";
            parm.add(queryCondition.getNotificationIssueStatus());
        }
        if (!StringUtil.isEmpty(queryCondition.getLoginName())) {
            sql += " and u.loginName = ?";
            parm.add(queryCondition.getLoginName());
        }
        List<ColonoscopyVo> list = jdbctemp.query(sql.toString(), parm.toArray(), new BeanPropertyRowMapper<ColonoscopyVo>(ColonoscopyVo.class));
        return list;
        /*if (isPage) {
        	ListPageUtil listPage = new ListPageUtil(sql, parm.toArray(), queryCondition.getPageNo(), queryCondition.getPageSize(), jdbctemp, null);
            return listPage;
        } else {
            ListPageUtil listPageNoPaging = new ListPageUtil(sql, parm.toArray(), 1, -1, jdbctemp, null);
            return listPageNoPaging;
        }*/
    }

    /**
     * 更改肠镜检查记录表主要包含 肠镜结果状态、病理状态、告知书状态、告知书发放状态
     * @param
     */
    @Override
    public void updateColonoscopyRecordForCheckStatus(HospitalColonoscopyRecord colonoscopyRecord) {
        String sql = "update hospital_colonoscopy_record set " +
                "result_id=?,result_status=?,result_date=?,result_operator=?,"+//肠镜结果状态
                "pathology_id=?,pathology_status=?,pathology_date=?,pathology_operator=?,"+//病理状态
                "notification_id=?,notification_entry_status=?,notification_entry_date=?,notification_entry_operator=?," +//告知书状态
                "notification_issue_status=?,notification_issue_date=?,notification_issue_operator=?,notification_issue_worker_code=?,notification_issue_note=?,notification_issue_mode=?," +//告知书发放状态
                "update_time=now() where `id`=?";
        jdbctemp.update(sql, new Object[]{colonoscopyRecord.getResult_id(),colonoscopyRecord.getResult_status(),colonoscopyRecord.getResult_date(),colonoscopyRecord.getResult_operator(),
                colonoscopyRecord.getPathology_id(),colonoscopyRecord.getPathology_status(),colonoscopyRecord.getPathology_date(),colonoscopyRecord.getPathology_operator(),
                colonoscopyRecord.getNotification_id(),colonoscopyRecord.getNotification_entry_status(),colonoscopyRecord.getNotification_entry_date(),colonoscopyRecord.getNotification_entry_operator(),
                colonoscopyRecord.getNotification_issue_status(),colonoscopyRecord.getNotification_issue_date(),colonoscopyRecord.getNotification_issue_operator(),colonoscopyRecord.getNotification_issue_worker_code(),colonoscopyRecord.getNotification_issue_note(),colonoscopyRecord.getNotification_issue_mode(),colonoscopyRecord.getId()});
    }

    /**
     * 肠镜申请表单编辑，修改其他表单的修改状态
     * @param
     */
    @Override
    public void updateCJFormUpdateStatus(HospitalReferenceRecordDto dto){
        Integer cjRecordId = dto.getRecordId();
        if(cjRecordId==null){
            log.info("HospitalMessageCenterServiceImpl_svae_cjRecordId is null");
            throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE,
                    GlobalErrorCode.PARAMETER_ERR_MSG);
        }

        String sql = "SELECT t.* from hospital_colonoscopy_record t WHERE t.id = ?";
        List<HospitalColonoscopyRecord> hospitalColonoscopyRecordList = jdbctemp.query(sql, new Object[]{cjRecordId},
                new BeanPropertyRowMapper<HospitalColonoscopyRecord>(HospitalColonoscopyRecord.class));

        HospitalColonoscopyRecord record = hospitalColonoscopyRecordList.get(0);

        if(Constans.HOSPITAL_COLONOSCOPY_RESULT.equals(dto.getFormType())){
          //肠镜结果
            if(record.getPathology_id()!=null){
                String sql1 = "update hospital_colonoscopy_pathology_result set apply_status= ?, edit_status= ?,approval_status is NULL ,update_time=now()  where  id= ?";
                jdbctemp.update(sql1, new Object[]{1,0,record.getPathology_id()});
            }
            if(record.getNotification_id()!=null){
                String sql1 = "update hospital_screening_notification set apply_status= ?, edit_status= ?,approval_status is NULL ,update_time=now()  where  id= ?";
                jdbctemp.update(sql1, new Object[]{1,0,record.getNotification_id()});
            }
        }

        if(Constans.HOSPITAL_COLONOSCOPY_PATHOLOGY_RESULT.equals(dto.getFormType())){
            //肠镜病理
            if(record.getResult_id()!=null){
                String sql1 = "update hospital_colonoscopy_result set apply_status= ?, edit_status= ?,approval_status is NULL ,update_time=now()  where  id= ?";
                jdbctemp.update(sql1, new Object[]{1,0,record.getResult_id()});
            }
            if(record.getNotification_id()!=null){
                String sql1 = "update hospital_screening_notification set apply_status= ?, edit_status= ?,approval_status is NULL ,update_time=now()  where  id= ?";
                jdbctemp.update(sql1, new Object[]{1,0,record.getNotification_id()});
            }
        }

        if(Constans.HOSPITAL_SCREENING_NOTIFICATION.equals(dto.getFormType())){
            //肠镜告知书
            if(record.getResult_id()!=null){
                String sql1 = "update hospital_colonoscopy_result set apply_status= ?, edit_status= ?,approval_status is NULL ,update_time=now()  where  id= ?";
                jdbctemp.update(sql1, new Object[]{1,0,record.getResult_id()});
            }
            if(record.getPathology_id()!=null){
                String sql1 = "update hospital_colonoscopy_pathology_result set apply_status= ?, edit_status= ?,approval_status is NULL ,update_time=now()  where  id= ?";
                jdbctemp.update(sql1, new Object[]{1,0,record.getPathology_id()});
            }
        }
    }

    @Override
    public List<ReserveAllocation> queryAllocationByrecordId(Integer id) {
        List<Object> parm = new ArrayList<Object>();
        //String sql="select str_to_date(concat_ws(' ',t1.reservation_date, t1.end_time ), '%Y-%m-%d %H:%i:%s') as atime from hospital_colonoscopy_reservation_allocation t1,hospital_colonoscopy_record t2 where t2.allocation_id=t1.id and t2.id= ?";
        String sql="select reserve_date as atime from  hospital_colonoscopy_record  where id= ?";
        parm.add(id);
        return jdbctemp.query(sql.toString(), parm.toArray(), new BeanPropertyRowMapper<ReserveAllocation>(ReserveAllocation.class));

    }

    @Override
    public List<AllocationDto> queryAllocationById(Integer allocationId) {
        List<Object> parm = new ArrayList<Object>();
        String sql="select *  from  hospital_colonoscopy_reservation_allocation  where id= ? ";
        parm.add(allocationId);
        return jdbctemp.query(sql.toString(), parm.toArray(), new BeanPropertyRowMapper<AllocationDto>(AllocationDto.class));

    }

    @Override
    public List<ReservationDetail> queryHospitalColonoscopyReservationDetail(Integer allocationId, String sid) {
        return null;
    }
}
