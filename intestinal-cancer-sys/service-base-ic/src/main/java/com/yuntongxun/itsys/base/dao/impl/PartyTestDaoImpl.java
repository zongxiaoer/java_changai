package com.yuntongxun.itsys.base.dao.impl;

import com.yuntongxun.itsys.base.dao.PartyTestDao;
import com.yuntongxun.itsys.base.po.HospitalStoolDnaPO;
import com.yuntongxun.itsys.base.po.dto.parytest.PartyTestInformDto;
import com.yuntongxun.itsys.base.vo.HospitalBiologicalSampleResultVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wp_sp
 * @date 2018/5/11
 */
@Repository
public class PartyTestDaoImpl implements PartyTestDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final Logger log = LogManager.getLogger(PartyTestDaoImpl.class.getName());

    @Override
    public void informByDnaCodeAndSid(PartyTestInformDto partyTestQueryDto) {
        String sql = "UPDATE `hospital_stool_dna` SET dna_check_inform_status=?,dna_check_enter_status=?,`dna_check_result`=?, `dna_check_quantum`=?, `dna_check_goal`=?, `dna_check_filePath`=?, `dna_check_enter_date`=now() WHERE (`id`=? AND sid = ? AND dna_code = ?)";
        jdbcTemplate.update(sql,partyTestQueryDto.getDnaCheckInformStatus(),partyTestQueryDto.getDnaCheckEnterStatus(),partyTestQueryDto.getDnaCheckResult(), partyTestQueryDto.getDnaCheckQuantum(), partyTestQueryDto.getDnaCheckGoal(), partyTestQueryDto.getDnaCheckFilepath(), partyTestQueryDto.getId(), partyTestQueryDto.getSid(), partyTestQueryDto.getDnaCode());
    }

    @Override
    public HospitalStoolDnaPO querybyid(PartyTestInformDto partyTestQueryDto) {
        String sql = "SELECT id,sid,dna_check_enter_date,dna_check_enter_status,dna_check_filePath,dna_check_goal,dna_check_inform_community_date,dna_check_inform_nation_date,dna_check_inform_status,dna_check_quantum,dna_check_result FROM hospital_stool_dna WHERE id= ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<HospitalStoolDnaPO>(HospitalStoolDnaPO.class), partyTestQueryDto.getId());
    }

    @Override
    public List<HospitalStoolDnaPO> queryAll() {
        String sql = "SELECT r.dna_code FROM hospital_stool_dna  r LEFT JOIN hospital_intestine_review a ON r.sid = a.sid WHERE 1=1 and r.dna_code is not null and a.overall_status_cy <> 2 and r.dna_check_result is null and r.apply_status=0 and r.edit_status=0 and r.approval_status is null";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<HospitalStoolDnaPO>(HospitalStoolDnaPO.class));
    }
}
