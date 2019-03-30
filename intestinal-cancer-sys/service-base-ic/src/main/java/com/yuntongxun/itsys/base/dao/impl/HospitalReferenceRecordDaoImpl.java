package com.yuntongxun.itsys.base.dao.impl;/*
 * Copyright (C), 2015-2018, 壹永科技有限公司
 * FileName: HospitalReferenceRecordDaoImpl
 * Author:   zongtong
 * Date:     2018/7/17 上午12:02
 * History:
 * <author>          <time>                <version>
 * zongtong           2018/7/17 上午12:02           v1.0.0
 */

import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.dao.HospitalReferenceRecordDao;
import com.yuntongxun.itsys.base.po.dto.courier.HospitalReferenceRecordDto;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class HospitalReferenceRecordDaoImpl implements HospitalReferenceRecordDao {

    @Autowired
    private JdbcTemplate jdbctemp;

    private final Logger log = LogManager.getLogger(HospitalReferenceRecordDaoImpl.class);

    @Override
    public Integer save(final  HospitalReferenceRecordDto hospitalReferenceRecordDto) {
        log.info("@dao HospitalReferenceRecordDaoImpl 插入编辑记录数据", hospitalReferenceRecordDto.toString());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("insert into hospital_reference_record(data_id,form_type,apply_status,apply_date,send_person,old_data,date_created,update_time,remark,main_data_id) values(?,?,?,now(),?,?,now(),now(),?,?)");
        jdbctemp.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(stringBuffer.toString(), Statement.RETURN_GENERATED_KEYS);
                ps.setObject(1, hospitalReferenceRecordDto.getDataId());
                ps.setObject(2, hospitalReferenceRecordDto.getFormType());
                ps.setObject(3, Constans.APPLY_EDIT_STATUS1);//申请状态   0-申请
                ps.setObject(4, hospitalReferenceRecordDto.getSendPerson());
                ps.setObject(5, hospitalReferenceRecordDto.getOldData());
                ps.setObject(6, hospitalReferenceRecordDto.getRemark());
                ps.setObject(7,hospitalReferenceRecordDto.getMainDataId());
                return ps;
            }

        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public List<HospitalReferenceRecordDto> queryEntityByIdAndType(HospitalReferenceRecordDto hospitalReferenceRecordDtos) {
        String sql = "select * from hospital_reference_record where data_id=? AND form_type=? and status=?";
        //String sql = "select * from hospital_biological_sample_result where frozen_box_code=? AND sample_column=? and sample_line like ?";
        //log.info(sql);
        return jdbctemp.query(sql, new BeanPropertyRowMapper<HospitalReferenceRecordDto>(HospitalReferenceRecordDto.class), hospitalReferenceRecordDtos.getDataId(), hospitalReferenceRecordDtos.getFormType(),hospitalReferenceRecordDtos.getStatus());

    }

    @Override
    public void update(HospitalReferenceRecordDto hospitalReferenceRecordDtos) {
        String sql = "update hospital_reference_record set accept_person= ?, no_apply_status= ?,no_apply_date=now(),status=?  where  id= ?";
        jdbctemp.update(sql, new Object[]{hospitalReferenceRecordDtos.getAcceptPerson(), hospitalReferenceRecordDtos.getNoApplyStatus(),hospitalReferenceRecordDtos.getStatus() ,hospitalReferenceRecordDtos.getId()});
    }

    @Override
    public ListPageUtil queryById(Integer id, String hospitalCourierResult) {
        List<Object> parm = new ArrayList<Object>();
        parm.add(id);
        String sql = "select  id,apply_status as applyStatus,edit_status as editStatus,approval_status as approvalStatus from "+hospitalCourierResult+" where id=?";//+id+" and sid='"+sid+"'"
        //log.info(sql);
        //return jdbctemp.query(sql, new BeanPropertyRowMapper<HospitalCourierResultDto>(HospitalCourierResultDto.class),id);


        Map<String,String> translateParm=new HashMap();
        ListPageUtil listPage=new ListPageUtil(sql.toString(),parm.toArray(),1, 1,jdbctemp,translateParm);
        return listPage;
       // ListPageUtil listPageNoPaging = new ListPageUtil(sql, parm.toArray(), 1, -1, jdbctemp, null);
       // return listPageNoPaging;
    }

    @Override
    public void updateForEdir(HospitalReferenceRecordDto hospitalReferenceRecordDto) {
        String sql = "update hospital_reference_record set edit_person= ?, old_data= ?,edit_date=now(),status=?  where data_id=? AND form_type=? and status=?";
        jdbctemp.update(sql, new Object[]{hospitalReferenceRecordDto.getEditPerson(), hospitalReferenceRecordDto.getOldData(),Constans.HOSPITAL_REFERENCE_RECORD_EDIT_FINISH ,hospitalReferenceRecordDto.getDataId(),hospitalReferenceRecordDto.getFormType(),hospitalReferenceRecordDto.getStatus()});
    }

    @Override
    public void updateForEdirStatus(String applyEditStatus1, String editStatus1, Integer id) {
        String sql = "update hospital_violation_scheme set apply_status= ?, edit_status= ?,approval_status=?  where id=? ";
        jdbctemp.update(sql, new Object[]{applyEditStatus1, editStatus1,null,id});
    }

    @Override
    public void saveArea(final HospitalReferenceRecordDto hospitalReferenceRecordDto) {
        //String sql = "update hospital_reference_record set edit_person= ?, old_data= ?,edit_date=now(),status=?  where data_id=? AND form_type=? and status=?";
       // jdbctemp.update(sql, new Object[]{hospitalReferenceRecordDto.getEditPerson(), hospitalReferenceRecordDto.getOldData(),Constans.HOSPITAL_REFERENCE_RECORD_EDIT_FINISH ,hospitalReferenceRecordDto.getDataId(),hospitalReferenceRecordDto.getFormType(),hospitalReferenceRecordDto.getStatus()});


        log.info("@dao saveArea 插入编辑记录数据", hospitalReferenceRecordDto.toString());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("insert into hospital_reference_record(data_id,form_type,edit_person,old_data,edit_date,status,update_time,date_created) values(?,?,?,?,now(),?,now(),now())");
        jdbctemp.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(stringBuffer.toString(), Statement.RETURN_GENERATED_KEYS);
                ps.setObject(1, hospitalReferenceRecordDto.getDataId());
                ps.setObject(2, hospitalReferenceRecordDto.getFormType());
                ps.setObject(3, hospitalReferenceRecordDto.getEditPerson());//申请状态   0-申请
                ps.setObject(4, hospitalReferenceRecordDto.getOldData());
                ps.setObject(5, Constans.HOSPITAL_REFERENCE_RECORD_EDIT_FINISH);
                return ps;
            }

        }, keyHolder);
    }


}
