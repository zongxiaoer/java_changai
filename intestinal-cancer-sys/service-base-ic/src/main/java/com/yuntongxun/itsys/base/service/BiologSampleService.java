package com.yuntongxun.itsys.base.service;

import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.po.HospitalBiologicalSampleResultPO;
import com.yuntongxun.itsys.base.po.dto.courier.HospitalReferenceRecordDto;
import com.yuntongxun.itsys.base.po.dto.message.HospitalMessageCenterDto;
import com.yuntongxun.itsys.base.vo.HospitalBiologicalSampleResultVo;

import java.util.List;

/**
 * @author zongt
 * @date 2018/5/11
 */
public interface BiologSampleService {
    void   saveBiologSample(HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultPO);

    public ListPageUtil queryArea(HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultPO, String loginName);

    public ListPageUtil queryCountry(HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultPO, String loginName);
    void updateBiologSample(HospitalBiologicalSampleResultPO hospitalBiologicalSampleResultPO);

    public List<HospitalBiologicalSampleResultVo> queryById(Integer id, Integer areaDeptId);

    public List<HospitalBiologicalSampleResultVo> queryByFrozenBoxCode(String frozen_box_code,String tablename);

    public List<HospitalBiologicalSampleResultVo> queryBySampleColumnAndLine(String frozen_box_code,String sample_column,String sample_line);

    String queryByAssociatedSample(String associatedSample);

    boolean checkSample(String frozen_box_code,String sampleType);

    List<HospitalBiologicalSampleResultVo> queryBySampleID(String sampleId);

    List<HospitalBiologicalSampleResultVo> queryBySampleIDAndSampletype(String sampleId, String sampleType);

    void saveBiologBloodSample(HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultPO);

    void updateBiologBloodSample(HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultPO);

    List<HospitalBiologicalSampleResultVo> queryByBloodId(Integer id);

    List<HospitalBiologicalSampleResultVo> queryByBloodSampleColumnAndLine(String frozenBoxCode, String sampleColumn, String sampleLine);

    void updateEditBiologBloodSample(HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultPO,HospitalReferenceRecordDto hospitalReferenceRecordDto);

    void updateEditBiologSample(HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultPO, HospitalReferenceRecordDto hospitalReferenceRecordDto);


    List<HospitalBiologicalSampleResultVo> queryBloodById(Integer id);

    List<HospitalBiologicalSampleResultVo> queryByBloodSampleColumnAndLineW(String frozenBoxCode, String sampleColumn, String sampleLine);
}
