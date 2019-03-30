package com.yuntongxun.itsys.base.service;

import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.po.dto.courier.FrozenBoxCodeDto;
import com.yuntongxun.itsys.base.po.dto.courier.HospitalCourierResultDto;
import com.yuntongxun.itsys.base.po.dto.courier.HospitalReferenceRecordDto;
import com.yuntongxun.itsys.base.vo.HospitalBiologicalSampleResultVo;

import java.util.List;
import java.util.Map;

/**
 * @author zongt
 * @date 2018/6/21
 */
public interface HospitalCourierResultService {

    List<FrozenBoxCodeDto> queryFrozenBoxCodesInSample(String table, Integer areaDeptId, Integer communityDeptId);

    List<HospitalCourierResultDto> queryByCourierNumber(String courierNumber);

    void saveCourier(HospitalCourierResultDto hospitalCourierResultDto, List<FrozenBoxCodeDto> frozenBoxCodeDtosBoole, List<FrozenBoxCodeDto> frozenBoxCodeDtosSamply);

    ListPageUtil queryByEntity(HospitalCourierResultDto hospitalCourierResultDto);

    void updateCourierByAccept(HospitalCourierResultDto hospitalCourierResultDto);

    List<HospitalCourierResultDto> queryByIdANDCourierNumber(Integer id, String courierNumber);

    List<HospitalBiologicalSampleResultVo> querySamplyByCourierNumber(String courierNumber, String tableName);

    List<HospitalCourierResultDto> queryById(Integer id);

    List<FrozenBoxCodeDto> querySampleByCourierId(String id, String biologicalBloodSampleTable);

    void updateCourier(List<FrozenBoxCodeDto> frozenBoxCodeDtosBooleResult, List<FrozenBoxCodeDto> frozenBoxCodeDtosSamplyReslut, HospitalCourierResultDto hospitalCourierResultDto, List<FrozenBoxCodeDto> frozenBoxCodeDtosBoole, List<FrozenBoxCodeDto> frozenBoxCodeDtosSamply,HospitalReferenceRecordDto hospitalReferenceRecordDto);

    void updateStatusById(String applyStatus,String editStatus,String approvalStatus,String id,String table);

    int queryByFrozenBoxCodeDto(List<FrozenBoxCodeDto> frozenBoxCodeDtosBoole, String biologicalBloodSampleTable);

    List<FrozenBoxCodeDto> queryFrozenBoxCodesInBloodSample(String table, Integer areaDeptId, Integer communityDeptId);

    int queryByBloodFrozenBoxCodeDto(List<FrozenBoxCodeDto> frozenBoxCodeDtosSamply, String biologicalBloodSampleTable);
}
