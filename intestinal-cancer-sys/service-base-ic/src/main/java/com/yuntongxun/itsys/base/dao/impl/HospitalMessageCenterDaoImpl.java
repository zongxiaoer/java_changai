package com.yuntongxun.itsys.base.dao.impl;

import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.dao.HospitalMessageCenterDao;
import com.yuntongxun.itsys.base.po.dto.message.HospitalMessageCenterDto;
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

@Repository
public class HospitalMessageCenterDaoImpl implements HospitalMessageCenterDao {


    @Autowired
    private JdbcTemplate jdbctemp;

    private final Logger log = LogManager.getLogger(HospitalMessageCenterDaoImpl.class);

    @Override
    public void save(final List<HospitalMessageCenterDto> hospitalMessageCenterDto) {
        String sql = "insert into hospital_message_center(id,send_user,accept_user,message_type,message_text," +
                "sid,read_status,date_created,update_time,data_id,form_type,sample_type,courier_number,main_data_id) values(?,?,?,?,?,?,?,now(),now(),?,?,?,?,?)";
        jdbctemp.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i)
                    throws SQLException {
                HospitalMessageCenterDto arg0 = (HospitalMessageCenterDto) hospitalMessageCenterDto.get(i);
                ps.setObject(1, arg0.getId());
                ps.setObject(2, arg0.getSendUser());
                ps.setObject(3, arg0.getAcceptUser());
                ps.setObject(4, arg0.getMessageType());
                ps.setObject(5, arg0.getMessageText());
                ps.setObject(6, arg0.getSid());
                ps.setObject(7, arg0.getReadStatus());
                ps.setObject(8, arg0.getData_id());
                ps.setObject(9, arg0.getForm_type());
                ps.setObject(10, arg0.getSample_type());
                ps.setObject(11, arg0.getCourierNumber());
                ps.setObject(12,arg0.getMainDataId());
            }

            @Override
            public int getBatchSize() {
                // TODO Auto-generated method stub
                return hospitalMessageCenterDto.size();
            }
        });
    }

    @Override
    public ListPageUtil queryAllMessageCenter(HospitalMessageCenterDto hospitalMessageCenterDto) {
        List<Object> parm = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append("select id,send_user as sendUser,accept_user as acceptUser,message_type as messageType,message_text as messageText,read_status as readStatus,date_created as dateCreated,main_data_id as mainDataId from hospital_message_center where 1=1");

        if (!StringUtils.isEmpty(hospitalMessageCenterDto.getAcceptUser() )) {
            sql.append(" and accept_user=? ");
            parm.add(hospitalMessageCenterDto.getAcceptUser());
        }

        if (!StringUtils.isEmpty(hospitalMessageCenterDto.getMessageType())) {
            if(Constans.meaasge_typpe3.equals(hospitalMessageCenterDto.getMessageType())){
                sql.append(" and (message_type=2 or message_type=3) ");
            }else{
                sql.append(" and message_type=? ");
                parm.add(hospitalMessageCenterDto.getMessageType());
            }

        }
        sql.append(" order by date_created desc");
        ListPageUtil listPage = new ListPageUtil(sql.toString(), parm.toArray(), hospitalMessageCenterDto.getPageNo(), hospitalMessageCenterDto.getPageSize(), jdbctemp, null);
        return listPage;
    }

    @Override
    public void updateMessageCenterStatusById(HospitalMessageCenterDto hospitalMessageCenterDto) {
        String sql = "update hospital_message_center set `read_status`=?,"
                + "update_time=now() where `id`=?";
        jdbctemp.update(sql, new Object[]{hospitalMessageCenterDto.getReadStatus(), hospitalMessageCenterDto.getId()});
    }

    @Override
    public List<HospitalMessageCenterDto> queryEntityById(String id) {
        String sql = "select * from hospital_message_center where id=?";//+id+" and sid='"+sid+"'"
        //log.info(sql);
        return jdbctemp.query(sql, new BeanPropertyRowMapper<HospitalMessageCenterDto>(HospitalMessageCenterDto.class), id);
    }

    @Override
    public ListPageUtil queryByTableAndId(String form_type, Integer data_id) {
        List<Object> parm = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append("select apply_status as applyStatus,edit_status as editStatus,approval_status as approvalStatus from "+form_type+" where 1=1");

        if (data_id!=null) {
            sql.append(" and id=? ");
            parm.add(data_id);
        }
        ListPageUtil listPage = new ListPageUtil(sql.toString(), parm.toArray(), 1, 10, jdbctemp, null);
        return listPage;
    }

}
