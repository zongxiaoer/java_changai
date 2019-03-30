package com.yuntongxun.itsys.base.dao;

import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.po.dto.courier.FrozenBoxCodeDto;
import com.yuntongxun.itsys.base.po.dto.courier.HospitalCourierResultDto;
import com.yuntongxun.itsys.base.vo.HospitalBiologicalSampleResultVo;

import java.util.List;

/**
 * @author zongt
 * @date 2018/6/21
 */
public interface HospitalCourierResultDao {
    List<HospitalBiologicalSampleResultVo> FrozenBoxCodes(String table, Integer areaDeptId, Integer communityDeptId);

    List<HospitalCourierResultDto> queryByCourierNumber(String courierNumber);

    void saveCourier(HospitalCourierResultDto hospitalCourierResultDto);

    void updateSamplyByFrozenBoxCodes(List<FrozenBoxCodeDto> frozenBoxCodeDtosBoole, String biologicalBloodSampleTable,final Integer courierStatus,final String courierNumber);

    ListPageUtil queryByEntity(HospitalCourierResultDto hospitalCourierResultDto, boolean isPage);

    void updateCourier(HospitalCourierResultDto hospitalCourierResultDto);

    void updateSamplyByCourierId(Integer courierStatus, String biologicalBloodSampleTable,String courierNumber);

    List<HospitalCourierResultDto> queryByIdANDCourierNumber(Integer id, String courierNumber);

    List<HospitalBiologicalSampleResultVo> querySamplyByCourierNumber(String courierNumber, String tableName);

    List<HospitalCourierResultDto> queryById(Integer id);

    List<FrozenBoxCodeDto> querySampleByCourierId(String id, String biologicalBloodSampleTable);

    void update(HospitalCourierResultDto hospitalCourierResultDto);

    void updateStatusById(String applyStatus,String editStatus,String approvalStatus,String id,String table);

    int queryByFrozenBoxCodeDto(String s, String biologicalBloodSampleTable);

    List<HospitalBiologicalSampleResultVo> queryFrozenBoxCodesInBloodSample(String table, Integer areaDeptId, Integer communityDeptId);

    int queryByBloodFrozenBoxCodeDto(String substring, String biologicalBloodSampleTable);

}
