package com.yuntongxun.itsys.base.service;

import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.po.dto.courier.HospitalReferenceRecordDto;
import com.yuntongxun.itsys.base.po.dto.message.HospitalMessageCenterDto;

import java.util.List;

public interface HospitalMessageCenterService {
    void save(List<HospitalMessageCenterDto> hospitalMessageCenterDto,HospitalReferenceRecordDto hospitalReferenceRecordDto,String applyStatus,String editStatus,String approvalStatus,String id,String table,boolean isok,String module);

    void update(List<HospitalMessageCenterDto> hospitalMessageCenterDtoList, HospitalReferenceRecordDto hospitalReferenceRecordDtos,String applyStatus,String editStatus,String approvalStatus,String id,String table);

    ListPageUtil queryAllMessageCenter(HospitalMessageCenterDto hospitalMessageCenterDto);

    void updateMessageCenterStatusById(HospitalMessageCenterDto hospitalMessageCenterDto);

    List<HospitalMessageCenterDto> queryEntityById(String id);

    ListPageUtil queryByTableAndId(String form_type, Integer data_id);
}
