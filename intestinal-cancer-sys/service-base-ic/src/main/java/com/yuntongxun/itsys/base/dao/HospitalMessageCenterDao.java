package com.yuntongxun.itsys.base.dao;

import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.po.dto.message.HospitalMessageCenterDto;
import java.util.List;

public interface HospitalMessageCenterDao {
    void save(List<HospitalMessageCenterDto> hospitalMessageCenterDtos);

    ListPageUtil queryAllMessageCenter(HospitalMessageCenterDto hospitalMessageCenterDto);

    void updateMessageCenterStatusById(HospitalMessageCenterDto hospitalMessageCenterDto);

    List<HospitalMessageCenterDto> queryEntityById(String id);

    ListPageUtil queryByTableAndId(String form_type, Integer data_id);
}
