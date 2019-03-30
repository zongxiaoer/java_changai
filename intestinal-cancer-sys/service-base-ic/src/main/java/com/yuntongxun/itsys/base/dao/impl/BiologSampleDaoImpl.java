package com.yuntongxun.itsys.base.dao.impl;

import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.common.util.StringUtil;
import com.yuntongxun.itsys.base.dao.BiologSampleDao;
import com.yuntongxun.itsys.base.po.HospitalBiologicalSampleResultPO;
import com.yuntongxun.itsys.base.po.dto.ExeclData;
import com.yuntongxun.itsys.base.service.impl.BiologSampleServiceImpl;
import com.yuntongxun.itsys.base.vo.HospitalBiologicalSampleResultVo;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
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
import java.util.List;

/**
 * @author zongt
 * @date 2018/5/11
 */
@Repository
public class BiologSampleDaoImpl implements BiologSampleDao {


    @Autowired
    private JdbcTemplate jdbctemp;

    private final Logger log = LogManager.getLogger(BiologSampleServiceImpl.class);

    @Override
    public Integer addBiologSample(final HospitalBiologicalSampleResultPO hospitalBiologicalSampleResultPO) {
        log.info("@dao addBiologSampleInPerson 新增生物样本记录 dna={}.", hospitalBiologicalSampleResultPO.toString());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("insert into hospital_biological_sample_result(sid,stage,sample_type,community_dept_id,area_dept_id,operation_source,associated_sample_id,collect_status,collect_status_date,frozen_box_code,sample_column,sample_line,sample_note,sample_id,courier_status,date_created,update_time) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),now())");
        jdbctemp.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(stringBuffer.toString(), Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, hospitalBiologicalSampleResultPO.getSid());
                ps.setInt(2, hospitalBiologicalSampleResultPO.getStage());
                ps.setString(3, hospitalBiologicalSampleResultPO.getSampleType());
                ps.setInt(4, hospitalBiologicalSampleResultPO.getCommunityDeptId());
                ps.setInt(5, hospitalBiologicalSampleResultPO.getAreaDeptId());
                ps.setString(6, hospitalBiologicalSampleResultPO.getOperationSource());
                ps.setString(7, hospitalBiologicalSampleResultPO.getAssociatedSampleId());
                ps.setInt(8, hospitalBiologicalSampleResultPO.getCollectStatus());
                ps.setObject(9, hospitalBiologicalSampleResultPO.getCollectStatusDate());
                ps.setObject(10, hospitalBiologicalSampleResultPO.getFrozenBoxCode());
                ps.setObject(11, hospitalBiologicalSampleResultPO.getSampleColumn());
                ps.setObject(12, hospitalBiologicalSampleResultPO.getSampleLine());
                ps.setObject(13, hospitalBiologicalSampleResultPO.getSampleNote());
                ps.setObject(14, hospitalBiologicalSampleResultPO.getSampleId());
                ps.setObject(15, hospitalBiologicalSampleResultPO.getCourierStatus());
                return ps;
            }

        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void addBiologSamples(final List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultPOs) {
        String sql = "insert into hospital_biological_sample_result(sid,stage,sample_type,community_dept_id,area_dept_id,operation_source,associated_sample_id,collect_status,collect_status_date,frozen_box_code,sample_column,sample_line,sample_note,courier_status,sample_id,date_created,update_time) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),now())";
        jdbctemp.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i)
                    throws SQLException {
                HospitalBiologicalSampleResultPO arg0 = (HospitalBiologicalSampleResultPO) hospitalBiologicalSampleResultPOs.get(i);
                ps.setString(1, arg0.getSid());
                ps.setInt(2, arg0.getStage());
                ps.setString(3, arg0.getSampleType());
                ps.setInt(4, arg0.getCommunityDeptId());
                ps.setInt(5, arg0.getAreaDeptId());
                ps.setString(6, arg0.getOperationSource());
                ps.setString(7, arg0.getAssociatedSampleId());
                ps.setInt(8, arg0.getCollectStatus());
                ps.setObject(9, arg0.getCollectStatusDate());
                ps.setObject(10, arg0.getFrozenBoxCode());
                ps.setObject(11, arg0.getSampleColumn());
                ps.setObject(12, arg0.getSampleLine());
                ps.setObject(13, arg0.getSampleNote());
                ps.setObject(14, arg0.getCourierStatus());
                ps.setObject(15, arg0.getSampleId());
            }

            @Override
            public int getBatchSize() {
                // TODO Auto-generated method stub
                return hospitalBiologicalSampleResultPOs.size();
            }
        });
    }

    @Override
    public ListPageUtil queryArea(HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultPO, Integer areaDeptId, Integer deptType, boolean isPage,String ids) {
        List<Object> parm = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();

        if(!Constans.SAMPLE_TYPE6.equals(hospitalBiologicalSampleResultPO.getSampleType())&&StringUtils.isEmpty(hospitalBiologicalSampleResultPO.getSampleTypeAll3())){
            sql.append("select t2.apply_status as applyStatus,t2.edit_status as editStatus,t2.approval_status as approvalStatus,d.`name` as depName,t2.`id` as `id`, t1.sid,t1.`name`,u.nickName,t1.phone, case when t1.`group`= 'A' then 'A' when t1.`group` = 'B'  then 'B' when t1.`group` = 'C' and t1.risk_level is null then 'C' when t1.`group` = 'C' and t1.risk_level = 1 then 'Cd' when t1.`group` = 'C' and  t1.risk_level = 2 then 'Cg' ELSE NULL END 'group', t1.overall_status_cy as overallStatusCy,t2.sample_id as sampleId,t2.sample_type as sampleType,t2.collect_status as collectStatus,t2.collect_status_date as collectStatusDate,t2.frozen_box_code as frozenBoxCode,t2.sample_column as sampleColumn,t2.sample_line as sampleLine,t2.sample_note as sampleNote,t2.courier_status as courierStatus,t2.associated_sample_id as associatedSampleId  from hospital_intestine_review t1  left join hospital_biological_sample_result t2  on t1.sid = t2.sid left join itsys_department d on t1.community_dept_id=d.id  LEFT JOIN itsys_user u ON t1.create_user = u.loginName    where   t1.overall_status_cy <> 2 ");
        }else{
            sql.append("select t2.apply_status as applyStatus,t2.edit_status as editStatus,t2.approval_status as approvalStatus,pboold.`courier_status` as pCourierStatus,wboold.`courier_status` as wCourierStatus,sboold.`courier_status` as sCourierStatus,d.`name` as depName,t2.`id` as `id`, t1.sid,t1.`name`,u.nickName,t1.phone,"
                    + " case when t1.`group`= 'A' then 'A' when t1.`group` = 'B'  then 'B' when t1.`group` = 'C' and t1.risk_level is null then 'C' when t1.`group` = 'C' and t1.risk_level = 1 then 'Cd' when t1.`group` = 'C' and  t1.risk_level = 2 then 'Cg' ELSE NULL END 'group', "
                    + "t1.overall_status_cy as overallStatusCy,"
                    + "t2.sample_id as sampleId,t2.sample_type as sampleType,t2.collect_status as collectStatus,"
                    + "t2.collect_status_date as collectStatusDate,t2.frozen_box_code as frozenBoxCode,t2.sample_column as sampleColumn,t2.sample_line as sampleLine,t2.sample_note as sampleNote,t2.courier_status as courierStatus,t2.associated_sample_id as associatedSampleId "
                    + " from hospital_intestine_review t1  left join hospital_biological_sample_result t2  on t1.sid = t2.sid left join itsys_department d on t1.community_dept_id=d.id  LEFT JOIN itsys_user u ON t1.create_user = u.loginName  " +
                    " LEFT JOIN (select * from hospital_biological_blood_sample_result where sample_type='S') as sboold on sboold.blood_sample_id=t2.id "+
                    " LEFT JOIN (select * from hospital_biological_blood_sample_result where sample_type='W') as wboold on wboold.blood_sample_id=t2.id "+
                    " LEFT JOIN (select * from hospital_biological_blood_sample_result where sample_type='P') as pboold on pboold.blood_sample_id=t2.id "
                    +"where   t1.overall_status_cy <> 2  ");
        }

        if (Constans.DEPARTMENT_TYPE_AREA == deptType) {
            sql.append(" and t2.area_dept_id = ?");
            parm.add(areaDeptId);
        }

        if (hospitalBiologicalSampleResultPO.getCommunityDeptId() != null) {
            sql.append(" and t2.community_dept_id = ?");
            parm.add(hospitalBiologicalSampleResultPO.getCommunityDeptId());
        }
        if (!StringUtil.isEmpty(hospitalBiologicalSampleResultPO.getSid())) {
            sql.append(" and t1.sid like ?");
            parm.add("%" + hospitalBiologicalSampleResultPO.getSid() + "%");
        }
        if (!StringUtil.isEmpty(hospitalBiologicalSampleResultPO.getName())) {
            sql.append(" and t1.`name` like ? ");
            parm.add("%" + hospitalBiologicalSampleResultPO.getName() + "%");
        }
        if (!StringUtil.isEmpty(hospitalBiologicalSampleResultPO.getPhone())) {
            sql.append(" and t1.`phone` like ? ");
            parm.add("%" + hospitalBiologicalSampleResultPO.getPhone() + "%");
        }
        if (!StringUtil.isEmpty(hospitalBiologicalSampleResultPO.getGroup())) {
            if (hospitalBiologicalSampleResultPO.getGroup().equals("Cg")) {
                sql.append(" and t1.`group` = 'C' and t1.risk_level = ?");
                parm.add(2);
            } else if (hospitalBiologicalSampleResultPO.getGroup().equals("Cd")) {
                sql.append(" and t1.`group` = 'C' and t1.risk_level = ?");
                parm.add(1);
            } else {
                sql.append(" and t1.`group` = ? ");
                parm.add(hospitalBiologicalSampleResultPO.getGroup());
            }
        }
        if (!StringUtils.isEmpty(hospitalBiologicalSampleResultPO.getSampleType())) {
            sql.append(" and t2.`sample_type` = ? ");
            parm.add(hospitalBiologicalSampleResultPO.getSampleType());
        }
        //非血液样本查询
        if (!StringUtils.isEmpty(hospitalBiologicalSampleResultPO.getFrozenBoxCode())&&!StringUtils.isEmpty(hospitalBiologicalSampleResultPO.getSampleType())&&!hospitalBiologicalSampleResultPO.getSampleType().equals(Constans.SAMPLE_TYPE6)) {
            sql.append(" and t2.`frozen_box_code` like ? ");
            parm.add("%" + hospitalBiologicalSampleResultPO.getFrozenBoxCode() + "%");
        }

        if (!StringUtils.isEmpty(hospitalBiologicalSampleResultPO.getSampleId())) {
            sql.append(" and t2.`sample_id` like ? ");
            parm.add("%" + hospitalBiologicalSampleResultPO.getSampleId() + "%");
        }

        if (hospitalBiologicalSampleResultPO.getCollectStatus() != null) {
            sql.append(" and t2.`collect_status` = ? ");
            parm.add(hospitalBiologicalSampleResultPO.getCollectStatus());
        }
        if (hospitalBiologicalSampleResultPO.getCourierStatus() != null) {
            sql.append(" and t2.`courier_status` = ? ");
            parm.add(hospitalBiologicalSampleResultPO.getCourierStatus());
        }
/*        if (!StringUtils.isEmpty(hospitalBiologicalSampleResultPO.getSampleColumn())) {
            sql.append(" and t2.`sample_column` = ? ");
            parm.add(hospitalBiologicalSampleResultPO.getSampleColumn());
        }
        if (!StringUtils.isEmpty(hospitalBiologicalSampleResultPO.getSampleLine())) {
            sql.append("  and FIND_IN_SET(?,sample_line) ");
            parm.add(hospitalBiologicalSampleResultPO.getSampleLine());
        }*/
        if (!StringUtils.isEmpty(hospitalBiologicalSampleResultPO.getSampleTypeAll3())) {
            sql.append(" and t2.`sample_type` =? ");
            parm.add(hospitalBiologicalSampleResultPO.getSampleTypeAll3());
        }

        if(hospitalBiologicalSampleResultPO.getpCourierStatus()!=null){
            sql.append(" and pboold.courier_status = ? ");
            parm.add(hospitalBiologicalSampleResultPO.getpCourierStatus());
        }

        if(hospitalBiologicalSampleResultPO.getsCourierStatus()!=null){
            sql.append(" and sboold.courier_status = ? ");
            parm.add(hospitalBiologicalSampleResultPO.getsCourierStatus());
        }

        if(hospitalBiologicalSampleResultPO.getwCourierStatus()!=null){
            sql.append(" and wboold.courier_status = ? ");
            parm.add(hospitalBiologicalSampleResultPO.getwCourierStatus());
        }

        if (StringUtils.isNotBlank(hospitalBiologicalSampleResultPO.getCollectStatusStartDate())) {
            sql.append(" and t2.collect_status_date >= ? ");
            parm.add(hospitalBiologicalSampleResultPO.getCollectStatusStartDate() + " 00:00:00");
        }

        if (StringUtils.isNotBlank(hospitalBiologicalSampleResultPO.getCollectStatusEndDate())) {
            sql.append(" and t2.collect_status_date <= ? ");
            parm.add(hospitalBiologicalSampleResultPO.getCollectStatusEndDate() + " 23:59:59");
        }



        if(!StringUtils.isEmpty(hospitalBiologicalSampleResultPO.getFrozenBoxCode())&&!StringUtils.isEmpty(hospitalBiologicalSampleResultPO.getSampleTypeAll3())&&StringUtils.isEmpty(hospitalBiologicalSampleResultPO.getSampleType())){
            if(!StringUtils.isEmpty(ids)){
                sql.append(" and t2.id in ( "+ids+" ) ");
            }else if(StringUtils.isEmpty(ids)){
                sql.append(" and t2.id in ( '' ) ");
            }
        }
        if(StringUtils.isNotBlank(hospitalBiologicalSampleResultPO.getLoginName())){
            sql.append(" and u.loginName = ?");
            parm.add(hospitalBiologicalSampleResultPO.getLoginName());
        }

        sql.append(" order by t2.date_created desc");

        if (isPage) {
            ListPageUtil listPage = new ListPageUtil(sql.toString(), parm.toArray(), hospitalBiologicalSampleResultPO.getPageNo(), hospitalBiologicalSampleResultPO.getPageSize(), jdbctemp, null);
            return listPage;
        } else {
            ListPageUtil listPageNoPaging = new ListPageUtil(sql.toString(), parm.toArray(), 1, -1, jdbctemp, null);
            return listPageNoPaging;
        }
    }

    @Override
    public ListPageUtil queryCountry(HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultPO, boolean isPage,String ids) {
        List<Object> parm = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        if(!Constans.SAMPLE_TYPE6.equals(hospitalBiologicalSampleResultPO.getSampleType())&&StringUtils.isEmpty(hospitalBiologicalSampleResultPO.getSampleTypeAll3())){
            sql.append("select t2.approval_status as approvalStatus,t2.apply_status as applyStatus,t2.edit_status as editStatus,d2.`name` as AreaName,d.`name` as depName,t2.`id` as `id`, t1.sid,t1.`name`, u.nickName ,t1.phone,"
                    + " case when t1.`group`= 'A' then 'A' when t1.`group` = 'B'  then 'B' when t1.`group` = 'C' and t1.risk_level is null then 'C' when t1.`group` = 'C' and t1.risk_level = 1 then 'Cd' when t1.`group` = 'C' and  t1.risk_level = 2 then 'Cg' ELSE NULL END 'group', "
                    + "t1.overall_status_cy as overallStatusCy,"
                    + "t2.sample_id as sampleId,t2.sample_type as sampleType,t2.collect_status as collectStatus,"
                    + "t2.collect_status_date as collectStatusDate,t2.frozen_box_code as frozenBoxCode,t2.sample_column as sampleColumn,t2.sample_line as sampleLine,t2.sample_note as sampleNote,t2.courier_status as courierStatus,t2.associated_sample_id as associatedSampleId "
                    + " from hospital_intestine_review t1 left join hospital_biological_sample_result t2 on t1.sid = t2.sid left join  itsys_department d on t1.community_dept_id=d.id left join itsys_department d2 on t2.area_dept_id=d2.id left join  itsys_user u on t1.create_user = u.loginName   "
                    +"where  t1.overall_status_cy <> 2 "
            );
        }else{
            sql.append("select t2.approval_status as approvalStatus,t2.apply_status as applyStatus,t2.edit_status as editStatus,pboold.`courier_status` as pCourierStatus,wboold.`courier_status` as wCourierStatus,sboold.`courier_status` as sCourierStatus,d2.`name` as AreaName,d.`name` as depName,t2.`id` as `id`, t1.sid,t1.`name`, u.nickName ,t1.phone,"
                    + " case when t1.`group`= 'A' then 'A' when t1.`group` = 'B'  then 'B' when t1.`group` = 'C' and t1.risk_level is null then 'C' when t1.`group` = 'C' and t1.risk_level = 1 then 'Cd' when t1.`group` = 'C' and  t1.risk_level = 2 then 'Cg' ELSE NULL END 'group', "
                    + "t1.overall_status_cy as overallStatusCy,"
                    + "t2.sample_id as sampleId,t2.sample_type as sampleType,t2.collect_status as collectStatus,"
                    + "t2.collect_status_date as collectStatusDate,t2.frozen_box_code as frozenBoxCode,t2.sample_column as sampleColumn,t2.sample_line as sampleLine,t2.sample_note as sampleNote,t2.courier_status as courierStatus,t2.associated_sample_id as associatedSampleId "
                    + " from hospital_intestine_review t1 left join hospital_biological_sample_result t2 on t1.sid = t2.sid left join  itsys_department d on t1.community_dept_id=d.id left join itsys_department d2 on t2.area_dept_id=d2.id left join  itsys_user u on t1.create_user = u.loginName   "+
                    " LEFT JOIN (select * from hospital_biological_blood_sample_result where sample_type='S') as sboold on sboold.blood_sample_id=t2.id "+
                    " LEFT JOIN (select * from hospital_biological_blood_sample_result where sample_type='W') as wboold on wboold.blood_sample_id=t2.id "+
                    " LEFT JOIN (select * from hospital_biological_blood_sample_result where sample_type='P') as pboold on pboold.blood_sample_id=t2.id "
                    +"where  t1.overall_status_cy <> 2 "
            );
        }
/*        sql.append("select t2.approval_status as approvalStatus,t2.apply_status as applyStatus,t2.edit_status as editStatus,pboold.`courier_status` as pCourierStatus,wboold.`courier_status` as wCourierStatus,sboold.`courier_status` as sCourierStatus,d2.`name` as AreaName,d.`name` as depName,t2.`id` as `id`, t1.sid,t1.`name`, u.nickName ,t1.phone,"
                + " case when t1.`group`= 'A' then 'A' when t1.`group` = 'B'  then 'B' when t1.`group` = 'C' and t1.risk_level is null then 'C' when t1.`group` = 'C' and t1.risk_level = 1 then 'Cd' when t1.`group` = 'C' and  t1.risk_level = 2 then 'Cg' ELSE NULL END 'group', "
                + "t1.overall_status_cy as overallStatusCy,"
                + "t2.sample_id as sampleId,t2.sample_type as sampleType,t2.collect_status as collectStatus,"
                + "t2.collect_status_date as collectStatusDate,t2.frozen_box_code as frozenBoxCode,t2.sample_column as sampleColumn,t2.sample_line as sampleLine,t2.sample_note as sampleNote,t2.courier_status as courierStatus,t2.associated_sample_id as associatedSampleId "
                + " from hospital_intestine_review t1 left join hospital_biological_sample_result t2 on t1.sid = t2.sid left join  itsys_department d on t1.community_dept_id=d.id left join itsys_department d2 on t2.area_dept_id=d2.id left join  itsys_user u on t1.create_user = u.loginName   "+
                " LEFT JOIN (select * from hospital_biological_blood_sample_result where sample_type='S') as sboold on sboold.blood_sample_id=t2.id "+
                " LEFT JOIN (select * from hospital_biological_blood_sample_result where sample_type='W') as wboold on wboold.blood_sample_id=t2.id "+
                " LEFT JOIN (select * from hospital_biological_blood_sample_result where sample_type='P') as pboold on pboold.blood_sample_id=t2.id "
                +"where  t1.overall_status_cy <> 2 "
        );*/

        if (hospitalBiologicalSampleResultPO.getAreaDeptId() != null) {
            sql.append(" and t2.area_dept_id = ?");
            parm.add(hospitalBiologicalSampleResultPO.getAreaDeptId());
        }
        if (hospitalBiologicalSampleResultPO.getCommunityDeptId() != null) {
            sql.append(" and t2.community_dept_id = ?");
            parm.add(hospitalBiologicalSampleResultPO.getCommunityDeptId());
        }
        if (!StringUtil.isEmpty(hospitalBiologicalSampleResultPO.getSid())) {
            sql.append(" and t1.sid like ?");
            parm.add("%" + hospitalBiologicalSampleResultPO.getSid() + "%");
        }
        if (!StringUtil.isEmpty(hospitalBiologicalSampleResultPO.getName())) {
            sql.append(" and t1.`name` like ? ");
            parm.add("%" + hospitalBiologicalSampleResultPO.getName() + "%");
        }
        if (!StringUtil.isEmpty(hospitalBiologicalSampleResultPO.getPhone())) {
            sql.append(" and t1.`phone` like ? ");
            parm.add("%" + hospitalBiologicalSampleResultPO.getPhone() + "%");
        }
        if (!StringUtil.isEmpty(hospitalBiologicalSampleResultPO.getGroup())) {
            if (hospitalBiologicalSampleResultPO.getGroup().equals("Cg")) {
                sql.append(" and t1.`group` = 'C' and t1.risk_level = ?");
                parm.add(2);
            } else if (hospitalBiologicalSampleResultPO.getGroup().equals("Cd")) {
                sql.append(" and t1.`group` = 'C' and t1.risk_level = ?");
                parm.add(1);
            } else {
                sql.append(" and t1.`group` = ? ");
                parm.add(hospitalBiologicalSampleResultPO.getGroup());
            }
        }
        if (hospitalBiologicalSampleResultPO.getSampleType() != null) {
            sql.append(" and t2.`sample_type` = ? ");
            parm.add(hospitalBiologicalSampleResultPO.getSampleType());
        }

        if (!StringUtils.isEmpty(hospitalBiologicalSampleResultPO.getFrozenBoxCode())&&!StringUtils.isEmpty(hospitalBiologicalSampleResultPO.getSampleType())&&!hospitalBiologicalSampleResultPO.getSampleType().equals(Constans.SAMPLE_TYPE6)) {
            sql.append(" and t2.`frozen_box_code` like ? ");
            parm.add("%" + hospitalBiologicalSampleResultPO.getFrozenBoxCode() + "%");
        }

        if (!StringUtils.isEmpty(hospitalBiologicalSampleResultPO.getSampleId())) {
            sql.append(" and t2.`sample_id` like ? ");
            parm.add("%" + hospitalBiologicalSampleResultPO.getSampleId() + "%");
        }

        if (hospitalBiologicalSampleResultPO.getCollectStatus() != null) {
            sql.append(" and t2.`collect_status` = ? ");
            parm.add(hospitalBiologicalSampleResultPO.getCollectStatus());
        }
        if (hospitalBiologicalSampleResultPO.getCourierStatus() != null) {
            sql.append(" and t2.`courier_status` = ? ");
            parm.add(hospitalBiologicalSampleResultPO.getCourierStatus());
        }
        if (!StringUtils.isEmpty(hospitalBiologicalSampleResultPO.getSampleColumn())) {
            sql.append(" and t2.`sample_column` = ? ");
            parm.add(hospitalBiologicalSampleResultPO.getSampleColumn());
        }
        if (!StringUtils.isEmpty(hospitalBiologicalSampleResultPO.getSampleLine())) {
            sql.append("  and FIND_IN_SET(?,sample_line) ");
            parm.add(hospitalBiologicalSampleResultPO.getSampleLine());
        }
        if (!StringUtils.isEmpty(hospitalBiologicalSampleResultPO.getSampleTypeAll3())) {
            sql.append(" and t2.`sample_type` =? ");
            parm.add(hospitalBiologicalSampleResultPO.getSampleTypeAll3());
        }
        if (StringUtils.isNotBlank(hospitalBiologicalSampleResultPO.getCollectStatusStartDate())) {
            sql.append(" and t2.collect_status_date >= ? ");
            parm.add(hospitalBiologicalSampleResultPO.getCollectStatusStartDate() + " 00:00:00");
        }
        if(hospitalBiologicalSampleResultPO.getpCourierStatus()!=null){
            sql.append(" and pboold.courier_status = ? ");
            parm.add(hospitalBiologicalSampleResultPO.getpCourierStatus());
        }

        if(hospitalBiologicalSampleResultPO.getsCourierStatus()!=null){
            sql.append(" and sboold.courier_status = ? ");
            parm.add(hospitalBiologicalSampleResultPO.getsCourierStatus());
        }

        if(hospitalBiologicalSampleResultPO.getwCourierStatus()!=null){
            sql.append(" and wboold.courier_status = ? ");
            parm.add(hospitalBiologicalSampleResultPO.getwCourierStatus());
        }

        if (StringUtils.isNotBlank(hospitalBiologicalSampleResultPO.getCollectStatusEndDate())) {
            sql.append(" and t2.collect_status_date <= ? ");
            parm.add(hospitalBiologicalSampleResultPO.getCollectStatusEndDate() + " 23:59:59");
        }
/*        if(!StringUtils.isEmpty(ids)){
            sql.append(" and t2.id in ( "+ids+" ) ");
        }*/

/*        if(!StringUtils.isEmpty(hospitalBiologicalSampleResultPO.getFrozenBoxCode())&&!StringUtils.isEmpty(hospitalBiologicalSampleResultPO.getSampleType())&&!hospitalBiologicalSampleResultPO.getSampleType().equals(Constans.SAMPLE_TYPE6)){
            if(!StringUtils.isEmpty(ids)){
                sql.append(" and t2.id in ( "+ids+" ) ");
            }else if(StringUtils.isEmpty(ids)){
                sql.append(" and t2.id in ( '' ) ");
            }
        }*/


        if(!StringUtils.isEmpty(hospitalBiologicalSampleResultPO.getFrozenBoxCode())&&!StringUtils.isEmpty(hospitalBiologicalSampleResultPO.getSampleTypeAll3())&&StringUtils.isEmpty(hospitalBiologicalSampleResultPO.getSampleType())){
            if(!StringUtils.isEmpty(ids)){
                sql.append(" and t2.id in ( "+ids+" ) ");
            }else if(StringUtils.isEmpty(ids)){
                sql.append(" and t2.id in ( '' ) ");
            }
        }

        if(StringUtils.isNotBlank(hospitalBiologicalSampleResultPO.getLoginName())){
            sql.append(" and u.loginName = ?");
            parm.add(hospitalBiologicalSampleResultPO.getLoginName());
        }
        sql.append(" order by t2.date_created desc");


        if (isPage) {
            ListPageUtil listPage = new ListPageUtil(sql.toString(), parm.toArray(), hospitalBiologicalSampleResultPO.getPageNo(), hospitalBiologicalSampleResultPO.getPageSize(), jdbctemp, null);
            return listPage;
        } else {
            ListPageUtil listPageNoPaging = new ListPageUtil(sql.toString(), parm.toArray(), 1, -1, jdbctemp, null);
            return listPageNoPaging;
        }
    }

    @Override
    public List<HospitalBiologicalSampleResultVo> quertById(Integer id, Integer areaDeptId) {
        String sql = "select * from hospital_biological_sample_result  where id=? ";//+id+" and sid='"+sid+"'"
        if(areaDeptId!=null){
            sql+="and area_dept_id=?";
            return jdbctemp.query(sql, new BeanPropertyRowMapper<HospitalBiologicalSampleResultVo>(HospitalBiologicalSampleResultVo.class), id, areaDeptId);
        }
        log.info(sql);
        return jdbctemp.query(sql, new BeanPropertyRowMapper<HospitalBiologicalSampleResultVo>(HospitalBiologicalSampleResultVo.class), id);
    }

    @Override
    public List<HospitalBiologicalSampleResultVo> queryByFrozenBoxCode(String frozenBoxCode,String table) {
        String sql = "select * from "+table+"  where frozen_box_code=?";//+id+" and sid='"+sid+"'"
        //log.info(sql);
        return jdbctemp.query(sql, new BeanPropertyRowMapper<HospitalBiologicalSampleResultVo>(HospitalBiologicalSampleResultVo.class), frozenBoxCode);

    }

    @Override
    public void updateById(HospitalBiologicalSampleResultPO hospitalBiologicalSampleResultPO) {
        String sql = "update hospital_biological_sample_result set sample_id=?,`collect_status`=?,collect_status_date=?"
                + ",frozen_box_code=?, `sample_column`=?, sample_line=?,sample_note=?, courier_status=?,update_time=now() where `id`=?";
        jdbctemp.update(sql, new Object[]{hospitalBiologicalSampleResultPO.getSampleId(), hospitalBiologicalSampleResultPO.getCollectStatus(), hospitalBiologicalSampleResultPO.getCollectStatusDate(), hospitalBiologicalSampleResultPO.getFrozenBoxCode(), hospitalBiologicalSampleResultPO.getSampleColumn(), hospitalBiologicalSampleResultPO.getSampleLine(),
                hospitalBiologicalSampleResultPO.getSampleNote(), hospitalBiologicalSampleResultPO.getCourierStatus(), hospitalBiologicalSampleResultPO.getId()});
    }

    @Override
    public void updateByIdNO(HospitalBiologicalSampleResultPO hospitalBiologicalSampleResultPO) {
        String sql = "update hospital_biological_sample_result set `collect_status`=?,"
                + "update_time=now() where `id`=?";
        jdbctemp.update(sql, new Object[]{hospitalBiologicalSampleResultPO.getCollectStatus(), hospitalBiologicalSampleResultPO.getId()});
    }

    @Override
    public List<HospitalBiologicalSampleResultVo> queryBySampleColumnAndLine(String frozenBoxCode, String sample_column, String sample_line) {
        //String sql = "select * from hospital_biological_sample_result where frozen_box_code=? AND sample_column=? and FIND_IN_SET(?,sample_line)";//+id+" and sid='"+sid+"'"
        String sql = "select * from hospital_biological_sample_result where frozen_box_code=? AND sample_column=? and sample_line like ?";
        //log.info(sql);
        return jdbctemp.query(sql, new BeanPropertyRowMapper<HospitalBiologicalSampleResultVo>(HospitalBiologicalSampleResultVo.class), frozenBoxCode, sample_column, "%" + sample_line + "%");

    }

    @Override
    public List<HospitalBiologicalSampleResultVo> queryByAssociatedSample(String associatedSample) {
        String sql = "select * from hospital_biological_sample_result where associated_sample_id=?";//+id+" and sid='"+sid+"'"
        return jdbctemp.query(sql, new BeanPropertyRowMapper<HospitalBiologicalSampleResultVo>(HospitalBiologicalSampleResultVo.class), associatedSample);
    }

    @Override
    public int addBiologSampleInPerson(final HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultVo, final String tableName) {
        log.info("@dao addBiologSampleInPerson 新增生物样本记录 dna={}.", hospitalBiologicalSampleResultVo.toString());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("INSERT INTO ");
        stringBuffer.append(tableName);
        if (tableName.equals(Constans.BIOLOGICAL_SAMPLE_TABLE)) {
            stringBuffer.append("(sid,stage,sample_type,community_dept_id,area_dept_id,operation_source,associated_sample_id,date_created,update_time,editoperation_source,operation_source_id) values(?,?,?,?,?,?,?,now(),now(),?,?)");
        } else {
            stringBuffer.append("(sample_type,community_dept_id,area_dept_id,operation_source,blood_sample_id,date_created,update_time,editoperation_source,operation_source_id) values(?,?,?,?,?,now(),now(),?,?)");
        }
        jdbctemp.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(stringBuffer.toString(), Statement.RETURN_GENERATED_KEYS);
                if (tableName.equals(Constans.BIOLOGICAL_SAMPLE_TABLE)) {
                    ps.setObject(1, hospitalBiologicalSampleResultVo.getSid());
                    ps.setObject(2, hospitalBiologicalSampleResultVo.getStage());
                    ps.setObject(3, hospitalBiologicalSampleResultVo.getSampleType());
                    ps.setObject(4, hospitalBiologicalSampleResultVo.getCommunityDeptId());
                    ps.setObject(5, hospitalBiologicalSampleResultVo.getAreaDeptId());
                    ps.setObject(6, hospitalBiologicalSampleResultVo.getOperationSource());
                    ps.setObject(7, hospitalBiologicalSampleResultVo.getAssociatedSampleId());
                    ps.setObject(8, hospitalBiologicalSampleResultVo.getEditoperationSource());
                    ps.setObject(9, hospitalBiologicalSampleResultVo.getOperationSourceId());
                } else {
                    ps.setObject(1, hospitalBiologicalSampleResultVo.getSampleType());
                    ps.setObject(2, hospitalBiologicalSampleResultVo.getCommunityDeptId());
                    ps.setObject(3, hospitalBiologicalSampleResultVo.getAreaDeptId());
                    ps.setObject(4, hospitalBiologicalSampleResultVo.getOperationSource());
                    ps.setObject(5, hospitalBiologicalSampleResultVo.getBloodSampleId());
                    ps.setObject(6, hospitalBiologicalSampleResultVo.getEditoperationSource());
                    ps.setObject(7, hospitalBiologicalSampleResultVo.getOperationSourceId());
                }

                return ps;
            }

        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void updateSampleEvent(HospitalBiologicalSampleResultPO hospitalBiologicalSampleResultVo) {
        String sql = "UPDATE hospital_todo_event set status =?,update_time=now() where data_id=? and type=?";
        jdbctemp.update(sql, new Object[]{Constans.PERSON_TODO_EVENT_STATUS2, hospitalBiologicalSampleResultVo.getId(), hospitalBiologicalSampleResultVo.getSampleType()});
    }

    @Override
    public List<HospitalBiologicalSampleResultVo> queryBySampleID(String sampleId) {
        String sql = "select * from hospital_biological_sample_result where sample_id=?";//+id+" and sid='"+sid+"'"
        //log.info(sql);
        return jdbctemp.query(sql, new BeanPropertyRowMapper<HospitalBiologicalSampleResultVo>(HospitalBiologicalSampleResultVo.class), sampleId);
    }

    @Override
    public List<HospitalBiologicalSampleResultVo> queryBySampleIDAndSampletype(String sampleId, String sampleType) {
        String sql = "select * from hospital_biological_sample_result where sample_id=? AND sample_type=? ";//+id+" and sid='"+sid+"'"
        return jdbctemp.query(sql, new BeanPropertyRowMapper<HospitalBiologicalSampleResultVo>(HospitalBiologicalSampleResultVo.class), sampleId, sampleType);
    }

    @Override
    public void saveBiologBloodSample(final List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultPOList) {
        String sql = "insert into hospital_biological_blood_sample_result(sample_type,community_dept_id,area_dept_id,operation_source,reslut_status,frozen_box_code,sample_column,sample_line,courier_status,blood_sample_id,date_created,update_time) values(?,?,?,?,?,?,?,?,?,?,now(),now())";
        jdbctemp.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i)
                    throws SQLException {
                HospitalBiologicalSampleResultVo arg0 = (HospitalBiologicalSampleResultVo) hospitalBiologicalSampleResultPOList.get(i);
                ps.setObject(1, arg0.getSampleType()==null?"":arg0.getSampleType());
                ps.setObject(2, arg0.getCommunityDeptId());
                ps.setObject(3, arg0.getAreaDeptId());
                ps.setObject(4, arg0.getOperationSource());
                ps.setObject(5, arg0.getCollectStatus());
                ps.setObject(6, arg0.getFrozenBoxCode()==null?"":arg0.getFrozenBoxCode());
                ps.setObject(7, arg0.getSampleColumn()==null?"":arg0.getSampleColumn());
                ps.setObject(8, arg0.getSampleLine()==null?"":arg0.getSampleLine());
                ps.setObject(9, arg0.getCourierStatus());
                ps.setObject(10, arg0.getBloodSampleId());
            }

            @Override
            public int getBatchSize() {
                // TODO Auto-generated method stub
                return hospitalBiologicalSampleResultPOList.size();
            }
        });
    }

    @Override
    public void updateALLById(final List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultPOList) {
        String sql = "update hospital_biological_blood_sample_result set frozen_box_code=?, `sample_column`=?, sample_line=?,courier_status=?,reslut_status=?,update_time=now() where blood_sample_id=? and sample_type=?";
        jdbctemp.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public int getBatchSize() {
                return hospitalBiologicalSampleResultPOList.size();
                //这个方法设定更新记录数，通常List里面存放的都是我们要更新的，所以返回list.size();
            }

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                HospitalBiologicalSampleResultVo vo = hospitalBiologicalSampleResultPOList.get(i);
                ps.setObject(1, vo.getFrozenBoxCode());
                ps.setObject(2, vo.getSampleColumn());
                ps.setObject(3, vo.getSampleLine());
                ps.setObject(4, vo.getCourierStatus());
                if(vo.getFrozenBoxCode()==null){
                    ps.setObject(5, null);
                }else{
                    ps.setObject(5, Constans.COLLECT_STATUS_PROVIDE);
                }
                ps.setObject(6, vo.getBloodSampleId());
                ps.setObject(7, vo.getSampleType());
            }
        });
    }

    @Override
    public List<HospitalBiologicalSampleResultVo> quertByBloodId(Integer id) {
        String sql = "select * from hospital_biological_blood_sample_result where blood_sample_id=? order by sample_type asc";//+id+" and sid='"+sid+"'"
        //log.info(sql);
        return jdbctemp.query(sql, new BeanPropertyRowMapper<HospitalBiologicalSampleResultVo>(HospitalBiologicalSampleResultVo.class), id);
    }

    @Override
    public List<HospitalBiologicalSampleResultVo>  queryByBloodSampleColumnAndLine(String frozenBoxCode, String sampleColumn, String sampleLine) {
        //String sql = "select * from hospital_biological_sample_result where frozen_box_code=? AND sample_column=? and FIND_IN_SET(?,sample_line)";//+id+" and sid='"+sid+"'"
        String sql = "select * from hospital_biological_blood_sample_result where frozen_box_code=? AND sample_column=? and sample_line like ?";
        //log.info(sql);
        return jdbctemp.query(sql, new BeanPropertyRowMapper<HospitalBiologicalSampleResultVo>(HospitalBiologicalSampleResultVo.class), frozenBoxCode, sampleColumn, "%" + sampleLine + "%");

    }

    @Override
    public List<HospitalBiologicalSampleResultVo> queryLikeFrozenBoxCode(String frozenBoxCode) {
        //String sql = "select * from hospital_biological_sample_result where frozen_box_code=? AND sample_column=? and FIND_IN_SET(?,sample_line)";//+id+" and sid='"+sid+"'"
        String sql = "select blood_sample_id from hospital_biological_blood_sample_result where frozen_box_code like ? ";
        //log.info(sql);
        return  jdbctemp.query(sql, new BeanPropertyRowMapper<HospitalBiologicalSampleResultVo>(HospitalBiologicalSampleResultVo.class), "%" + frozenBoxCode + "%");

    }

    @Override
    public List<HospitalBiologicalSampleResultVo> queryInBloodSampleIds(String s) {
        //String sql = "select * from hospital_biological_sample_result where frozen_box_code=? AND sample_column=? and FIND_IN_SET(?,sample_line)";//+id+" and sid='"+sid+"'"
        String sql = "select * from hospital_biological_blood_sample_result where blood_sample_id in ( "+s+")";
        log.info(sql);
        try{
            jdbctemp.query(sql, new BeanPropertyRowMapper<HospitalBiologicalSampleResultVo>(HospitalBiologicalSampleResultVo.class));
        }catch (Exception e){
            log.info(e.toString());
        }
        return  jdbctemp.query(sql, new BeanPropertyRowMapper<HospitalBiologicalSampleResultVo>(HospitalBiologicalSampleResultVo.class));
    }

    @Override
    public List<HospitalBiologicalSampleResultVo>  querybloodSampleExcel(HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultVo) {
        List<Object> parm = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT \n" +
                " d2.name as areaName,\n" +
                " u.nickName as nickName,\n" +
                " sboold.sample_column  AS  sSampleColumn,\n" +
                " pboold.sample_column  AS  pSampleColumn,\n" +
                " wboold.sample_column  AS  wSampleColumn,\n" +
                " sboold.sample_line  AS  sSampleLine ,\n" +
                " pboold.sample_line  AS  pSampleLine,\n" +
                " wboold.sample_line  AS  wSampleLine,\n" +
                " wboold.frozen_box_code AS wFrozenBoxCode,\n" +
                " sboold.frozen_box_code AS sFrozenBoxCode,\n" +
                " pboold.frozen_box_code AS pFrozenBoxCode, \n" +
                " pboold.`courier_status` AS pCourierStatus, \n" +
                " wboold.`courier_status` AS wCourierStatus, \n" +
                " sboold.`courier_status` AS sCourierStatus, d.`name` AS commName, t2.`id` AS `id`, t1.sid, t1.`name`\n" +
                "\t, u.nickName, t1.phone\n" +
                "\t, CASE \n" +
                "\t\tWHEN t1.`group` = 'A' THEN 'A'\n" +
                "\t\tWHEN t1.`group` = 'B' THEN 'B'\n" +
                "\t\tWHEN t1.`group` = 'C'\n" +
                "\t\tAND t1.risk_level IS NULL THEN 'C'\n" +
                "\t\tWHEN t1.`group` = 'C'\n" +
                "\t\tAND t1.risk_level = 1 THEN 'Cd'\n" +
                "\t\tWHEN t1.`group` = 'C'\n" +
                "\t\tAND t1.risk_level = 2 THEN 'Cg'\n" +
                "\t\tELSE NULL\n" +
                "\tEND AS 'group', t1.overall_status_cy AS overallStatusCy, t2.sample_id AS sampleId, t2.sample_type AS sampleType, t2.collect_status AS collectStatus\n" +
                "\t, t2.collect_status_date AS collectStatusDate, t2.frozen_box_code AS frozenBoxCode, t2.sample_column AS sampleColumn, t2.sample_line AS sampleLine, t2.sample_note AS sampleNote\n" +
                "\t, t2.courier_status AS courierStatus, t2.associated_sample_id AS associatedSampleId\n" +
                "FROM hospital_intestine_review t1\n" +
                "\tINNER JOIN hospital_biological_sample_result t2 ON t1.sid = t2.sid\n" +
                "\tLEFT JOIN itsys_department d ON t1.community_dept_id = d.id\n" +
                "\tLEFT JOIN itsys_department d2 ON  t1.area_dept_id =d2.id\n" +
                "\tLEFT JOIN itsys_user u ON t1.create_user = u.loginName \n" +
                "\tLEFT JOIN (\n" +
                "\t\tSELECT *\n" +
                "\t\tFROM hospital_biological_blood_sample_result\n" +
                "\t\tWHERE sample_type = 'S'\n" +
                "\t) sboold\n" +
                "\tON sboold.blood_sample_id = t2.id\n" +
                "\tLEFT JOIN (\n" +
                "\t\tSELECT *\n" +
                "\t\tFROM hospital_biological_blood_sample_result\n" +
                "\t\tWHERE sample_type = 'W'\n" +
                "\t) wboold\n" +
                "\tON wboold.blood_sample_id = t2.id\n" +
                "\tLEFT JOIN (\n" +
                "\t\tSELECT *\n" +
                "\t\tFROM hospital_biological_blood_sample_result\n" +
                "\t\tWHERE sample_type = 'P'\n" +
                "\t) pboold\n" +
                "\tON pboold.blood_sample_id = t2.id\n" +
                "WHERE t1.overall_status_cy <> 2  and t2.sample_type='A' ");
                if(hospitalBiologicalSampleResultVo.getAreaDeptId()!=null){
                    sql.append(" and t1.area_dept_id ="+hospitalBiologicalSampleResultVo.getAreaDeptId());
                }
                sql.append(" order by t2.date_created desc");

        log.info("sql"+sql.toString());
        return  jdbctemp.query(sql.toString(), new BeanPropertyRowMapper<HospitalBiologicalSampleResultVo>(HospitalBiologicalSampleResultVo.class));

    }

    @Override
    public void updateForEdirStatus(String applyEditStatus1, String editStatus1, Integer id) {
        String sql = "update hospital_biological_sample_result set apply_status= ?, edit_status= ?,approval_status=?  where id=? ";
        jdbctemp.update(sql, new Object[]{applyEditStatus1, editStatus1,null,id});
    }

    @Override
    public List<HospitalBiologicalSampleResultVo> queryBloodById(Integer id) {
        String sql = "select * from hospital_biological_blood_sample_result  where id=? ";//+id+" and sid='"+sid+"'"
        log.info(sql);
        return jdbctemp.query(sql, new BeanPropertyRowMapper<HospitalBiologicalSampleResultVo>(HospitalBiologicalSampleResultVo.class), id);
    }

    @Override
    public List<HospitalBiologicalSampleResultVo> stollbloodSampleQueryExcel(ExeclData execlData) {
        List<Object> parm = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT \n" +
                " d2.name as areaName,\n" +
                " u.nickName as nickName,\n" +
                " sboold.sample_column  AS  sSampleColumn,\n" +
                " pboold.sample_column  AS  pSampleColumn,\n" +
                " wboold.sample_column  AS  wSampleColumn,\n" +
                " sboold.sample_line  AS  sSampleLine ,\n" +
                " pboold.sample_line  AS  pSampleLine,\n" +
                " wboold.sample_line  AS  wSampleLine,\n" +
                " wboold.frozen_box_code AS wFrozenBoxCode,\n" +
                " sboold.frozen_box_code AS sFrozenBoxCode,\n" +
                " pboold.frozen_box_code AS pFrozenBoxCode, \n" +
                " pboold.`courier_status` AS pCourierStatus, \n" +
                " wboold.`courier_status` AS wCourierStatus, \n" +
                " sboold.`courier_status` AS sCourierStatus, d.`name` AS commName, t2.`id` AS `id`, t1.sid, t1.`name`\n" +
                "\t, u.nickName, t1.phone\n" +
                "\t, CASE \n" +
                "\t\tWHEN t1.`group` = 'A' THEN 'A'\n" +
                "\t\tWHEN t1.`group` = 'B' THEN 'B'\n" +
                "\t\tWHEN t1.`group` = 'C'\n" +
                "\t\tAND t1.risk_level IS NULL THEN 'C'\n" +
                "\t\tWHEN t1.`group` = 'C'\n" +
                "\t\tAND t1.risk_level = 1 THEN 'Cd'\n" +
                "\t\tWHEN t1.`group` = 'C'\n" +
                "\t\tAND t1.risk_level = 2 THEN 'Cg'\n" +
                "\t\tELSE NULL\n" +
                "\tEND AS 'group', t1.overall_status_cy AS overallStatusCy, t2.sample_id AS sampleId, t2.sample_type AS sampleType, t2.collect_status AS collectStatus\n" +
                "\t, t2.collect_status_date AS collectStatusDate, t2.frozen_box_code AS frozenBoxCode, t2.sample_column AS sampleColumn, t2.sample_line AS sampleLine, t2.sample_note AS sampleNote\n" +
                "\t, t2.courier_status AS courierStatus, t2.associated_sample_id AS associatedSampleId\n" +
                "FROM hospital_intestine_review t1\n" +
                "\tINNER JOIN hospital_biological_sample_result t2 ON t1.sid = t2.sid\n" +
                "\tLEFT JOIN itsys_department d ON t1.community_dept_id = d.id\n" +
                "\tLEFT JOIN itsys_department d2 ON  t1.area_dept_id =d2.id\n" +
                "\tLEFT JOIN itsys_user u ON t1.create_user = u.loginName \n" +
                "\tLEFT JOIN (\n" +
                "\t\tSELECT *\n" +
                "\t\tFROM hospital_biological_blood_sample_result\n" +
                "\t\tWHERE sample_type = 'S'\n" +
                "\t) sboold\n" +
                "\tON sboold.blood_sample_id = t2.id\n" +
                "\tLEFT JOIN (\n" +
                "\t\tSELECT *\n" +
                "\t\tFROM hospital_biological_blood_sample_result\n" +
                "\t\tWHERE sample_type = 'W'\n" +
                "\t) wboold\n" +
                "\tON wboold.blood_sample_id = t2.id\n" +
                "\tLEFT JOIN (\n" +
                "\t\tSELECT *\n" +
                "\t\tFROM hospital_biological_blood_sample_result\n" +
                "\t\tWHERE sample_type = 'P'\n" +
                "\t) pboold\n" +
                "\tON pboold.blood_sample_id = t2.id\n" +
                "WHERE t1.overall_status_cy <> 2  and t2.sample_type='A' ");

        if (!StringUtil.isEmpty(execlData.getGroup())) {
            if (execlData.getGroup().equals("Cg")) {
                sql.append(" and t1.`group` = 'C' and t1.risk_level = ? ");
                parm.add(2);
            } else if (execlData.getGroup().equals("Cd")) {
                sql.append(" and t1.`group` = 'C' and t1.risk_level = ? ");
                parm.add(1);
            } else {
                sql.append(" and t1.`group` = ? ");
                parm.add(execlData.getGroup());
            }
        }

        if (execlData.getInGroupDateStart() != null) {
            sql.append(" and t1.in_group_date >= ? ");
            parm.add(execlData.getInGroupDateStart());
        }

        if (execlData.getInGroupDateEnd() != null) {
            sql.append(" and t1.in_group_date <= ? ");
            parm.add(execlData.getInGroupDateEnd());
        }

        if (execlData.getStage_cy() != null) {
            sql.append(" and t1.stage_cy = ? ");
            parm.add(execlData.getStage_cy());
        }
        if (execlData.getSiteId() != null) {
            sql.append("  AND t1.site_id =  ? ");
            parm.add(execlData.getSiteId());
        }

        sql.append(" order by t2.date_created desc");

        log.info("sql"+sql.toString());
        if (parm.size() > 0) {
            return jdbctemp.query(sql.toString(), parm.toArray(), new BeanPropertyRowMapper<HospitalBiologicalSampleResultVo>(HospitalBiologicalSampleResultVo.class));
        }
        return  jdbctemp.query(sql.toString(), parm.toArray(), new BeanPropertyRowMapper<HospitalBiologicalSampleResultVo>(HospitalBiologicalSampleResultVo.class));
    }

    @Override
    public List<HospitalBiologicalSampleResultVo> queryByBloodSampleColumnAndLineW(String frozenBoxCode, String sampleColumn, String sampleLine) {
        //String sql = "select * from hospital_biological_sample_result where frozen_box_code=? AND sample_column=? and FIND_IN_SET(?,sample_line)";//+id+" and sid='"+sid+"'"
        String sql = "select * from hospital_biological_blood_sample_result where frozen_box_code=? AND sample_column=? and sample_line = ?";
        //log.info(sql);
        return jdbctemp.query(sql, new BeanPropertyRowMapper<HospitalBiologicalSampleResultVo>(HospitalBiologicalSampleResultVo.class), frozenBoxCode, sampleColumn, sampleLine);
    }


    public static void main(String[] args) {
        String tableName = "table";
        final StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("INSERT INTO ");
        stringBuffer.append(tableName);
        stringBuffer.append("(sid,stage,sample_type,community_dept_id,area_dept_id,operation_source,associated_sample_id,date_created,update_time");
        stringBuffer.append(") values(?,?,?,?,?,?,?");
        stringBuffer.append(",now(),now())");
        System.out.println(stringBuffer.toString());
    }
}
