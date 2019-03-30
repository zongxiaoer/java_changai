package com.yuntongxun.itsys.base.service.impl;

import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.GlobalErrorCode;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.dao.ColonoscopyDao;
import com.yuntongxun.itsys.base.dao.HospitalMessageCenterDao;
import com.yuntongxun.itsys.base.dao.HospitalReferenceRecordDao;
import com.yuntongxun.itsys.base.po.dto.courier.HospitalReferenceRecordDto;
import com.yuntongxun.itsys.base.po.dto.message.HospitalMessageCenterDto;
import com.yuntongxun.itsys.base.service.HospitalCourierResultService;
import com.yuntongxun.itsys.base.service.HospitalMessageCenterService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HospitalMessageCenterServiceImpl implements HospitalMessageCenterService {
    private final Logger log = LogManager.getLogger(HospitalMessageCenterServiceImpl.class);

    @Autowired
    private HospitalMessageCenterDao hospitalMessageCenterDao;

    @Autowired
    private HospitalReferenceRecordDao hospitalReferenceRecordDao;
    @Autowired
    private HospitalCourierResultService hospitalCourierResultService;

    @Autowired
    private ColonoscopyDao colonoscopyDao;

    @Override
    @Transactional
    public void save(List<HospitalMessageCenterDto > hospitalMessageCenterDto, HospitalReferenceRecordDto hospitalReferenceRecordDto,String applyStatus,String editStatus,String approvalStatus,String id,String table,boolean isok,String module) {
        try{
            if(hospitalReferenceRecordDto!=null){
                hospitalCourierResultService.updateStatusById(applyStatus,editStatus,approvalStatus,id,table);
                hospitalReferenceRecordDao.save(hospitalReferenceRecordDto);
            }
            if(isok){
                hospitalMessageCenterDao.save(hospitalMessageCenterDto);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new ItSysException(GlobalErrorCode.HOSPITAL_MESSAGE_CENTER_INSERT_ERROR_CODE,
                    GlobalErrorCode.HOSPITAL_MESSAGE_CENTER_INSERT_ERROR_MSG);
        }
    }

    @Override
    @Transactional
    public void update(List<HospitalMessageCenterDto> hospitalMessageCenterDtoList, HospitalReferenceRecordDto hospitalReferenceRecordDtos,String applyStatus,String editStatus,String approvalStatus,String id,String table) {
        try{
            hospitalCourierResultService.updateStatusById(applyStatus,editStatus,approvalStatus,id,table);

           // hospitalCourierResultService.updateStatusById((HospitalCourierResultDto)T);
            if(hospitalReferenceRecordDtos!=null){
                hospitalReferenceRecordDao.update(hospitalReferenceRecordDtos);
            }
            hospitalMessageCenterDao.save(hospitalMessageCenterDtoList);
        }catch (Exception e){
            System.out.println(e.toString());
            throw new ItSysException(GlobalErrorCode.HOSPITAL_MESSAGE_CENTER_INSERT_ERROR_CODE,
                    GlobalErrorCode.HOSPITAL_MESSAGE_CENTER_INSERT_ERROR_MSG);
        }
    }

    @Override
    public ListPageUtil queryAllMessageCenter(HospitalMessageCenterDto hospitalMessageCenterDto) {

        return hospitalMessageCenterDao.queryAllMessageCenter(hospitalMessageCenterDto);
    }

    @Override
    public void updateMessageCenterStatusById(HospitalMessageCenterDto hospitalMessageCenterDto) {
        hospitalMessageCenterDao.updateMessageCenterStatusById(hospitalMessageCenterDto);
    }

    @Override
    public List<HospitalMessageCenterDto> queryEntityById(String id) {
        return hospitalMessageCenterDao.queryEntityById(id);
    }

    @Override
    public ListPageUtil queryByTableAndId(String form_type, Integer data_id) {
        return hospitalMessageCenterDao.queryByTableAndId(form_type,data_id);
    }

}
