package com.yuntongxun.itsys.base.dao;

import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.po.HospitalBiologicalSampleResultPO;
import com.yuntongxun.itsys.base.po.dto.ExeclData;
import com.yuntongxun.itsys.base.vo.HospitalBiologicalSampleResultVo;

import java.util.List;

/**
 * @author zongt
 * @date 2018/5/11
 */
public interface BiologSampleDao {
   Integer addBiologSample(HospitalBiologicalSampleResultPO hospitalBiologicalSampleResultPO);

   void addBiologSamples(List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultPOs);

   public ListPageUtil queryArea(HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultPO, Integer areaDeptId, Integer deptType, boolean b,String ids);

   public ListPageUtil queryCountry(HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultPO, boolean b,String ids);

   List<HospitalBiologicalSampleResultVo> quertById(Integer id, Integer areaDeptId);

   List<HospitalBiologicalSampleResultVo> queryByFrozenBoxCode(String frozenBoxCode,String table);

   void updateById(HospitalBiologicalSampleResultPO hospitalBiologicalSampleResultPO);

   void updateByIdNO(HospitalBiologicalSampleResultPO hospitalBiologicalSampleResultPO);

   List<HospitalBiologicalSampleResultVo> queryBySampleColumnAndLine(String frozenBoxCode,String sample_column,String sample_line);

   List<HospitalBiologicalSampleResultVo> queryByAssociatedSample(String associatedSample);

   int addBiologSampleInPerson(HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultVo,String tableName);

   void updateSampleEvent(HospitalBiologicalSampleResultPO hospitalBiologicalSampleResultVo);

    List<HospitalBiologicalSampleResultVo> queryBySampleID(String sampleId);

   List<HospitalBiologicalSampleResultVo> queryBySampleIDAndSampletype(String sampleId, String sampleType);

   void saveBiologBloodSample(List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultPOList);


   void updateALLById(List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultPOList);

   List<HospitalBiologicalSampleResultVo> quertByBloodId(Integer id);

    List<HospitalBiologicalSampleResultVo> queryByBloodSampleColumnAndLine(String frozenBoxCode, String sampleColumn, String sampleLine);

   List<HospitalBiologicalSampleResultVo> queryLikeFrozenBoxCode(String frozenBoxCode);

    List<HospitalBiologicalSampleResultVo> queryInBloodSampleIds(String s);

   List<HospitalBiologicalSampleResultVo>  querybloodSampleExcel(HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultVo);

    void updateForEdirStatus(String applyEditStatus1, String editStatus1, Integer id);

   List<HospitalBiologicalSampleResultVo> queryBloodById(Integer id);

   List<HospitalBiologicalSampleResultVo> stollbloodSampleQueryExcel(ExeclData execlData);

   List<HospitalBiologicalSampleResultVo> queryByBloodSampleColumnAndLineW(String frozenBoxCode, String sampleColumn, String sampleLine);
}
