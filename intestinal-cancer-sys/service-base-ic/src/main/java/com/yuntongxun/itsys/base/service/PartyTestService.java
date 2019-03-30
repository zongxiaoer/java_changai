package com.yuntongxun.itsys.base.service;

import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.po.DnaSynLogPO;
import com.yuntongxun.itsys.base.po.HospitalStoolDnaPO;
import com.yuntongxun.itsys.base.po.dto.parytest.PartyTestInformDto;
import com.yuntongxun.itsys.base.po.dto.parytest.PartyTestQueryDto;
import com.yuntongxun.itsys.base.vo.Result;

import java.util.List;

/**
 * @author wp_sp
 * @date 2018/5/10
 */
public interface PartyTestService {
    /**
     * 根据分页信息查询DNA编码列表
     *
     * @param partyTestQueryDto
     * @return
     */
    ListPageUtil queryDNAcodeListForPage(PartyTestQueryDto partyTestQueryDto);

    /**
     * 根据sid 和 dna编码录入 dna检测结果数据
     *
     * @param partyTestQueryDto
     */
    void informByDnaCodeAndSid(PartyTestInformDto partyTestQueryDto);

    /**
     * 根据id 查询dna第三方检测结果详情
     *
     * @param partyTestQueryDto
     * @return
     */
    HospitalStoolDnaPO querybyid(PartyTestInformDto partyTestQueryDto);


    List<String> queryAll();


    Integer synResult(PartyTestInformDto partyTestQueryDto);

    void addDnaSynLog(DnaSynLogPO synLogPO);
}
