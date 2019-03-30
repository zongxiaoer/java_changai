package com.yuntongxun.itsys.base.dao;

import com.yuntongxun.itsys.base.po.HospitalStoolDnaPO;
import com.yuntongxun.itsys.base.po.dto.parytest.PartyTestInformDto;

import java.util.List;

/**
 * @author wp_sp
 * @date 2018/5/11
 */
public interface PartyTestDao {
    /**
     * 根据dna编码和sid 录入dna检测结果
     *
     * @param partyTestQueryDto
     */
    void informByDnaCodeAndSid(PartyTestInformDto partyTestQueryDto);

    /**
     * 根据id 查询dna检测结果
     *
     * @param partyTestQueryDto
     * @return
     */
    HospitalStoolDnaPO querybyid(PartyTestInformDto partyTestQueryDto);

    List<HospitalStoolDnaPO> queryAll();

}
