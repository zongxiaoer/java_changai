package com.yuntongxun.itsys.base.dao.impl;

import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.common.util.StringUtil;
import com.yuntongxun.itsys.base.dao.HospitalCourierResultDao;
import com.yuntongxun.itsys.base.po.dto.courier.FrozenBoxCodeDto;
import com.yuntongxun.itsys.base.po.dto.courier.HospitalCourierResultDto;
import com.yuntongxun.itsys.base.vo.HospitalBiologicalSampleResultVo;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zongt
 * @date 2018/6/21
 */
@Repository
public class HospitalCourierResultDaoImpl implements HospitalCourierResultDao {

    private final Logger log = LogManager.getLogger(HospitalCourierResultDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbctemp;

    @Override
    public List<HospitalBiologicalSampleResultVo> FrozenBoxCodes(String table, Integer areaDeptId, Integer communityDeptId) {
        String sql="select distinct frozen_box_code  from "+table+" where courier_status=2 and frozen_box_code is not null and LENGTH(frozen_box_code)=7 and area_dept_id="+areaDeptId+" and  frozen_box_code not in (select distinct frozen_box_code from hospital_biological_sample_result where courier_status=2 and frozen_box_code is not null and LENGTH(frozen_box_code)=7 and area_dept_id="+areaDeptId+" and (apply_status=1  or  edit_status=1) order by frozen_box_code asc)  order by frozen_box_code asc";
        //String sql = "select distinct frozen_box_code  from " + table + " where courier_status=2 and frozen_box_code is not null and LENGTH(frozen_box_code)=7 and area_dept_id=?  and apply_status<>1 and edit_status<>1 order by frozen_box_code asc";//and (apply_status=1 or edit_status=1)
        //log.info(sql);
        return jdbctemp.query(sql, new BeanPropertyRowMapper<HospitalBiologicalSampleResultVo>(HospitalBiologicalSampleResultVo.class));
    }

    @Override
    public List<HospitalCourierResultDto> queryByCourierNumber(String courierNumber) {
        String sql = "select * from hospital_courier_result where courier_number=?";//+id+" and sid='"+sid+"'"
        //log.info(sql);
        return jdbctemp.query(sql, new BeanPropertyRowMapper<HospitalCourierResultDto>(HospitalCourierResultDto.class), courierNumber);
    }

    @Override
    public void saveCourier(HospitalCourierResultDto hospitalCourierResultDto) {
        String sql = "insert into hospital_courier_result(courier_number,send_date,send_person,courier_company,screeningType,send_person_phone,area_dept_id,date_created,update_time) values(?,?,?,?,?,?,?,now(),now())";
        jdbctemp.update(sql, new Object[]{hospitalCourierResultDto.getCourierNumber(), hospitalCourierResultDto.getSendDate(), hospitalCourierResultDto.getSendPerson(), hospitalCourierResultDto.getCourierCompany(), hospitalCourierResultDto.getScreeningtype(), hospitalCourierResultDto.getSendPersonPhone(), hospitalCourierResultDto.getAreaDeptId()});
    }

    @Override
    public void updateSamplyByFrozenBoxCodes(final List<FrozenBoxCodeDto> frozenBoxCodeDtosBoole, String biologicalBloodSampleTable, final Integer courierStatus, final String courierNumber) {
        String sql = "update " + biologicalBloodSampleTable + " set courier_status=?, `courier_id`=? where frozen_box_code=?";
        jdbctemp.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public int getBatchSize() {
                return frozenBoxCodeDtosBoole.size();
                //这个方法设定更新记录数，通常List里面存放的都是我们要更新的，所以返回list.size();
            }

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                FrozenBoxCodeDto frozenBoxCodeDto = frozenBoxCodeDtosBoole.get(i);
                ps.setObject(1, courierStatus);
                ps.setObject(2, courierNumber);
                ps.setObject(3, frozenBoxCodeDto.getFrozenBoxCode());
            }
        });
    }

    @Override
    public ListPageUtil queryByEntity(HospitalCourierResultDto hospitalCourierResultDto, boolean isPage) {
        List<Object> parm = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append("select t1.edit_status as editStatus,t1.approval_status as approvalStatus,t1.apply_status as applyStatus,t1.id,t1.screeningType,t1.courier_number as courierNumber,t1.send_date as sendDate,t1.send_person as sendPerson,t1.accept_date as acceptDate,t1.accept_person as  acceptPerson,t1.courier_status as courierStatus from hospital_courier_result as t1 where 1=1"
        );

        if (hospitalCourierResultDto.getAreaDeptId() != null) {
            sql.append(" and t1.area_dept_id = ?");
            parm.add(hospitalCourierResultDto.getAreaDeptId());
        }
        if (!StringUtils.isEmpty(hospitalCourierResultDto.getScreeningtype())) {
            sql.append(" and t1.screeningType = ?");
            parm.add(hospitalCourierResultDto.getScreeningtype());
        }
        if (!StringUtil.isEmpty(hospitalCourierResultDto.getCourierNumber())) {
            sql.append(" and t1.courier_number like ?");
            parm.add("%" + hospitalCourierResultDto.getCourierNumber() + "%");
        }

        if (StringUtils.isNotBlank(hospitalCourierResultDto.getSendDateStart())) {
            sql.append(" and t1.send_date >= ? ");
            parm.add(hospitalCourierResultDto.getSendDateStart() + " 00:00:00");
        }


        if (StringUtils.isNotBlank(hospitalCourierResultDto.getSendDateEnd())) {
            sql.append(" and t1.send_date <= ? ");
            parm.add(hospitalCourierResultDto.getSendDateEnd() + " 23:59:59");
        }


        if (StringUtils.isNotBlank(hospitalCourierResultDto.getAcceptDateStart())) {
            sql.append(" and t1.accept_date >= ? ");
            parm.add(hospitalCourierResultDto.getAcceptDateStart() + " 00:00:00");
        }


        if (StringUtils.isNotBlank(hospitalCourierResultDto.getAcceptDateEnd())) {
            sql.append(" and t1.accept_date <= ? ");
            parm.add(hospitalCourierResultDto.getAcceptDateEnd() + " 23:59:59");
        }

        sql.append(" order by t1.date_created desc");
        if (isPage) {
            ListPageUtil listPage = new ListPageUtil(sql.toString(), parm.toArray(), hospitalCourierResultDto.getPageNo(), hospitalCourierResultDto.getPageSize(), jdbctemp, null);
            return listPage;
        } else {
            ListPageUtil listPageNoPaging = new ListPageUtil(sql.toString(), parm.toArray(), 1, -1, jdbctemp, null);
            return listPageNoPaging;
        }
    }

    @Override
    public void updateCourier(HospitalCourierResultDto hospitalCourierResultDto) {
        String sql = "update hospital_courier_result set accept_date= ?, accept_person= ?,courier_status=? where id= ? and courier_number= ?";
        jdbctemp.update(sql, new Object[]{hospitalCourierResultDto.getAcceptDate(), hospitalCourierResultDto.getAcceptPerson(),hospitalCourierResultDto.getCourierStatus() ,hospitalCourierResultDto.getId(), hospitalCourierResultDto.getCourierNumber()});
    }

    @Override
    public void updateSamplyByCourierId(Integer courierStatus, String biologicalBloodSampleTable, String courierNumber) {
            String sql = "update "+biologicalBloodSampleTable+" set courier_status=? where courier_id=? ";
            jdbctemp.update(sql, new Object[]{courierStatus, courierNumber});

        }

    @Override
    public List<HospitalCourierResultDto> queryByIdANDCourierNumber(Integer id, String courierNumber) {
        String sql = "select *  from hospital_courier_result  where id=? and courier_number=?";//+id+" and sid='"+sid+"'"
        //log.info(sql);
        return jdbctemp.query(sql, new BeanPropertyRowMapper<HospitalCourierResultDto>(HospitalCourierResultDto.class),id, courierNumber);
    }

    @Override
    public List<HospitalBiologicalSampleResultVo> querySamplyByCourierNumber(String courierNumber, String tableName) {
        String sql = "select distinct frozen_box_code  from " + tableName + " where courier_status !=2 and frozen_box_code is not null and LENGTH(frozen_box_code)=7 and courier_id=?  order by frozen_box_code asc";//+id+" and sid='"+sid+"'"
        //log.info(sql);
        return jdbctemp.query(sql, new BeanPropertyRowMapper<HospitalBiologicalSampleResultVo>(HospitalBiologicalSampleResultVo.class), courierNumber);
    }

    @Override
    public List<HospitalCourierResultDto> queryById(Integer id) {
        String sql = "select *  from hospital_courier_result  where id=?";//+id+" and sid='"+sid+"'"
        //log.info(sql);
        return jdbctemp.query(sql, new BeanPropertyRowMapper<HospitalCourierResultDto>(HospitalCourierResultDto.class),id);
    }

    @Override
    public List<FrozenBoxCodeDto> querySampleByCourierId(String id, String biologicalBloodSampleTable) {
        String sql = "select distinct(frozen_box_code)   from "+biologicalBloodSampleTable+"  where courier_id=?";//+id+" and sid='"+sid+"'"
        log.info(sql);
        return jdbctemp.query(sql, new BeanPropertyRowMapper<FrozenBoxCodeDto>(FrozenBoxCodeDto.class),id);
    }

    @Override
    public void update(HospitalCourierResultDto hospitalCourierResultDto) {
        String sql = "update hospital_courier_result set courier_number= ?, send_date= ?,send_person=?,courier_company= ?,send_person_phone=? ,update_time=now(),apply_status=?,edit_status=?,approval_status=?,courier_status=?  where  id= ?";
        jdbctemp.update(sql, new Object[]{hospitalCourierResultDto.getCourierNumber(), hospitalCourierResultDto.getSendDate(),hospitalCourierResultDto.getSendPerson() ,hospitalCourierResultDto.getCourierCompany(), hospitalCourierResultDto.getSendPersonPhone(),Constans.APPLY_EDIT_STATUS1,Constans.EDIT_STATUS1,null,hospitalCourierResultDto.getCourierStatus(),hospitalCourierResultDto.getId()});
    }

    @Override
    public void updateStatusById( String applyStatus,String editStatus,String approvalStatus,String id,String table) {
        String sql = "update "+table+" set apply_status= ?, edit_status= ?,approval_status=?,update_time=now()  where  id= ?";
        jdbctemp.update(sql, new Object[]{applyStatus, editStatus,approvalStatus ,id});
    }

    @Override
    public int queryByFrozenBoxCodeDto(String s, String biologicalBloodSampleTable) {
        String sql = "select count(*) from "+biologicalBloodSampleTable+" where id in("+s+") and (apply_status=1 or edit_status=1) ";
        int result = jdbctemp.queryForObject(sql.toString(), new Object[]{}, Integer.class);
        return result;
    }

    @Override
    public List<HospitalBiologicalSampleResultVo> queryFrozenBoxCodesInBloodSample(String table, Integer areaDeptId, Integer communityDeptId) {
        String sql ="select distinct t1.frozen_box_code   from "+table+" t1 left join hospital_biological_sample_result t2 on t2.id=t1.blood_sample_id  where t1.courier_status=2 and t1.frozen_box_code is not null and LENGTH(t1.frozen_box_code)=7 and t1.area_dept_id="+areaDeptId+"  and t1.frozen_box_code not in (select distinct t1.frozen_box_code   from hospital_biological_blood_sample_result t1 left join hospital_biological_sample_result t2 on t2.id=t1.blood_sample_id  where t1.courier_status=2 and t1.frozen_box_code is not null and LENGTH(t1.frozen_box_code)=7 and t1.area_dept_id="+areaDeptId+" and (t2.apply_status=1 or t2.edit_status =1) order by frozen_box_code asc)";
        //String sql = "select distinct frozen_box_code  from " + table + " where courier_status=2 and frozen_box_code is not null and LENGTH(frozen_box_code)=7 and area_dept_id=?  and (apply_status=1 or edit_status=1) order by frozen_box_code asc";//and (apply_status=1 or edit_status=1)
        //log.info(sql);
        return jdbctemp.query(sql, new BeanPropertyRowMapper<HospitalBiologicalSampleResultVo>(HospitalBiologicalSampleResultVo.class));
    }

    @Override
    public int queryByBloodFrozenBoxCodeDto(String substring, String biologicalBloodSampleTable) {
        //        //select distinct t1.frozen_box_code   from hospital_biological_blood_sample_result t1 left join hospital_biological_sample_result t2 on t2.id=t1.blood_sample_id  where t1.courier_status=2 and t1.frozen_box_code is not null and LENGTH(t1.frozen_box_code)=7 and t1.area_dept_id=? and t2.apply_status<>1 and t2.edit_status <>1 order by frozen_box_code asc
        String sql = "select count(*) from hospital_biological_blood_sample_result as t1 left join hospital_biological_sample_result t2 on t2.id=t1.blood_sample_id  where t1.id in("+substring+") and (t2.apply_status=1 or t2.edit_status=1) ";
        int result = jdbctemp.queryForObject(sql.toString(), new Object[]{}, Integer.class);
        return result;
    }
}
