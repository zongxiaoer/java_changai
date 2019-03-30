package com.yuntongxun.itsys.base.service.impl;

import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.exception.ItSysException;
import com.yuntongxun.itsys.base.common.util.DateUtil;
import com.yuntongxun.itsys.base.common.util.GlobalErrorCode;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.dao.HospitalCourierResultDao;
import com.yuntongxun.itsys.base.dao.HospitalReferenceRecordDao;
import com.yuntongxun.itsys.base.po.dto.courier.FrozenBoxCodeDto;
import com.yuntongxun.itsys.base.po.dto.courier.HospitalCourierResultDto;
import com.yuntongxun.itsys.base.po.dto.courier.HospitalReferenceRecordDto;
import com.yuntongxun.itsys.base.service.HospitalCourierResultService;
import com.yuntongxun.itsys.base.vo.HospitalBiologicalSampleResultVo;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zongt
 * @date 2018/6/21
 */
@Service
public class HospitalCourierResultServiceImpl implements HospitalCourierResultService {

    private final Logger log = LogManager.getLogger(HospitalCourierResultServiceImpl.class);

    @Autowired
    private HospitalCourierResultDao hospitalCourierResultDao;

    @Autowired
    private HospitalReferenceRecordDao hospitalReferenceRecordDao;

    @Override
    public List<FrozenBoxCodeDto> queryFrozenBoxCodesInSample(String table, Integer areaDeptId, Integer communityDeptId) {
        List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultVoList = hospitalCourierResultDao.FrozenBoxCodes(table, areaDeptId, communityDeptId);
        List<FrozenBoxCodeDto> list = new ArrayList<>();
        for (HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultVo : hospitalBiologicalSampleResultVoList) {
            String frozenBoxCode = hospitalBiologicalSampleResultVo.getFrozenBoxCode();
            FrozenBoxCodeDto frozenBoxCodeDto = new FrozenBoxCodeDto();
            frozenBoxCodeDto.setFrozenBoxCode(frozenBoxCode);
            list.add(frozenBoxCodeDto);
        }
        return list;
    }

    @Override
    public List<HospitalCourierResultDto> queryByCourierNumber(String courierNumber) {
        return hospitalCourierResultDao.queryByCourierNumber(courierNumber);
    }

    @Override
    @Transactional
    public void saveCourier(HospitalCourierResultDto hospitalCourierResultDto, List<FrozenBoxCodeDto> frozenBoxCodeDtosBoole, List<FrozenBoxCodeDto> frozenBoxCodeDtosSamply) {
        String courierNumber = hospitalCourierResultDto.getCourierNumber();
        Integer courierStatus=Constans.COURIER_STATUS_CODE3;
        //入库
        try {
            String sendDateByString = hospitalCourierResultDto.getSendDateByString();
            hospitalCourierResultDto.setSendDate(DateUtil.getStringToDate(sendDateByString));
            hospitalCourierResultDao.saveCourier(hospitalCourierResultDto);
        } catch (Exception e) {
            log.info("hospitalCourierResultDao saveCourier  is error" + e.toString());
            throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG);
        }
        try {
            //根据冷冻盒编号批量修改状态
            if (frozenBoxCodeDtosBoole.size() > 0) {
                hospitalCourierResultDao.updateSamplyByFrozenBoxCodes(frozenBoxCodeDtosBoole, Constans.BIOLOGICAL_BLOOD_SAMPLE_TABLE,courierStatus,courierNumber);
            }
            if (frozenBoxCodeDtosSamply.size() > 0) {
                hospitalCourierResultDao.updateSamplyByFrozenBoxCodes(frozenBoxCodeDtosSamply, Constans.BIOLOGICAL_SAMPLE_TABLE,courierStatus,courierNumber);
            }
        } catch (Exception e) {
            log.info("hospitalCourierResultDao updateSamplyByFrozenBoxCodes  is error" + e.toString());
            throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG);
        }
    }

    @Override
    public ListPageUtil queryByEntity(HospitalCourierResultDto hospitalCourierResultDto) {
        return  hospitalCourierResultDao.queryByEntity(hospitalCourierResultDto,true);
    }

    @Override
    @Transactional
    public void updateCourierByAccept(HospitalCourierResultDto hospitalCourierResultDto) {
        Integer courierStatus=Constans.COURIER_STATUS_CODE1;
        try {
            String sendDateByString = hospitalCourierResultDto.getAcceptDateByString();
            hospitalCourierResultDto.setAcceptDate(DateUtil.getStringToDate(sendDateByString));
            hospitalCourierResultDto.setCourierStatus(courierStatus);
            hospitalCourierResultDao.updateCourier(hospitalCourierResultDto);
        } catch (Exception e) {
            log.info("hospitalCourierResultDao updateCourierByAccept  is error" + e.toString());
            throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG);
        }
        try {
            //根据冷冻盒编号批量修改状态
                hospitalCourierResultDao.updateSamplyByCourierId(courierStatus, Constans.BIOLOGICAL_BLOOD_SAMPLE_TABLE,hospitalCourierResultDto.getCourierNumber());
                hospitalCourierResultDao.updateSamplyByCourierId(courierStatus, Constans.BIOLOGICAL_SAMPLE_TABLE,hospitalCourierResultDto.getCourierNumber());
        } catch (Exception e) {
            log.info("hospitalCourierResultDao updateSamplyByCourierId  is error" + e.toString());
            throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG);
        }
    }

    @Override
    public List<HospitalCourierResultDto> queryByIdANDCourierNumber(Integer id, String courierNumber) {
        return hospitalCourierResultDao.queryByIdANDCourierNumber(id,courierNumber);
    }

    @Override
    public List<HospitalBiologicalSampleResultVo> querySamplyByCourierNumber(String courierNumber, String tableName) {
        return  hospitalCourierResultDao.querySamplyByCourierNumber(courierNumber,tableName);
    }

    @Override
    public List<HospitalCourierResultDto> queryById(Integer id) {
        return hospitalCourierResultDao.queryById(id);
    }

    @Override
    public List<FrozenBoxCodeDto> querySampleByCourierId(String id, String biologicalBloodSampleTable) {
        return   hospitalCourierResultDao.querySampleByCourierId(id,biologicalBloodSampleTable);
    }

    @Override
    @Transactional
    public void updateCourier(List<FrozenBoxCodeDto> frozenBoxCodeDtosBooleResult, List<FrozenBoxCodeDto> frozenBoxCodeDtosSamplyReslut, HospitalCourierResultDto hospitalCourierResultDto, List<FrozenBoxCodeDto> frozenBoxCodeDtosBoole, List<FrozenBoxCodeDto> frozenBoxCodeDtosSamply,HospitalReferenceRecordDto hospitalReferenceRecordDto) {
        String courierNumber = hospitalCourierResultDto.getCourierNumber();
        Integer courierStatus=Constans.COURIER_STATUS_CODE3;
        //入库
        try {
            hospitalReferenceRecordDao.updateForEdir(hospitalReferenceRecordDto);
            String sendDateByString = hospitalCourierResultDto.getSendDateByString();
            hospitalCourierResultDto.setSendDate(DateUtil.getStringToDate(sendDateByString));
            hospitalCourierResultDto.setCourierStatus(courierStatus);
            hospitalCourierResultDao.update(hospitalCourierResultDto);
        } catch (Exception e) {
            log.info("hospitalCourierResultDao saveCourier  is error" + e.toString());
            throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG);
        }
        try {
            //根据原来冷冻盒编号批量修改状态
            if (frozenBoxCodeDtosBooleResult.size() > 0) {
                hospitalCourierResultDao.updateSamplyByFrozenBoxCodes(frozenBoxCodeDtosBooleResult, Constans.BIOLOGICAL_BLOOD_SAMPLE_TABLE,Constans.COURIER_STATUS_CODE2,null);
            }
            if (frozenBoxCodeDtosSamplyReslut.size() > 0) {
                hospitalCourierResultDao.updateSamplyByFrozenBoxCodes(frozenBoxCodeDtosSamplyReslut, Constans.BIOLOGICAL_SAMPLE_TABLE,Constans.COURIER_STATUS_CODE2,null);
            }
            //根据接受的新的数据进行修改
            if (frozenBoxCodeDtosBoole.size() > 0) {
                hospitalCourierResultDao.updateSamplyByFrozenBoxCodes(frozenBoxCodeDtosBoole, Constans.BIOLOGICAL_BLOOD_SAMPLE_TABLE,Constans.COURIER_STATUS_CODE3,courierNumber);
            }
            if (frozenBoxCodeDtosSamply.size() > 0) {
                hospitalCourierResultDao.updateSamplyByFrozenBoxCodes(frozenBoxCodeDtosSamply, Constans.BIOLOGICAL_SAMPLE_TABLE,Constans.COURIER_STATUS_CODE3,courierNumber);
            }
        } catch (Exception e) {
            log.info("hospitalCourierResultDao updateSamplyByFrozenBoxCodes  is error" + e.toString());
            throw new ItSysException(GlobalErrorCode.PARAMETER_ERR_CODE, GlobalErrorCode.PARAMETER_ERR_MSG);
        }
    }

    @Override
    public void updateStatusById(String applyStatus,String editStatus,String approvalStatus,String id,String table) {
        hospitalCourierResultDao.updateStatusById(applyStatus,editStatus,approvalStatus,id,table);
    }

    @Override
    public int queryByFrozenBoxCodeDto(List<FrozenBoxCodeDto> frozenBoxCodeDtosBoole, String biologicalBloodSampleTable) {
        String substring="";
        StringBuffer stringBuffer=new StringBuffer();
        for (FrozenBoxCodeDto frozenBoxCodeDto:frozenBoxCodeDtosBoole) {
            String frozenBoxCode = frozenBoxCodeDto.getFrozenBoxCode();
            stringBuffer.append("'"+frozenBoxCode+"',");
        }
        if(!StringUtils.isEmpty(stringBuffer.toString())){
            substring= stringBuffer.substring(0, stringBuffer.length() - 1);
        }
        return hospitalCourierResultDao.queryByFrozenBoxCodeDto(substring,biologicalBloodSampleTable);
    }

    @Override
    public List<FrozenBoxCodeDto> queryFrozenBoxCodesInBloodSample(String table, Integer areaDeptId, Integer communityDeptId) {
        List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultVoList = hospitalCourierResultDao.queryFrozenBoxCodesInBloodSample(table, areaDeptId, communityDeptId);
        List<FrozenBoxCodeDto> list = new ArrayList<>();
        for (HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultVo : hospitalBiologicalSampleResultVoList) {
            String frozenBoxCode = hospitalBiologicalSampleResultVo.getFrozenBoxCode();
            FrozenBoxCodeDto frozenBoxCodeDto = new FrozenBoxCodeDto();
            frozenBoxCodeDto.setFrozenBoxCode(frozenBoxCode);
            list.add(frozenBoxCodeDto);
        }
        return list;
    }

    @Override
    public int queryByBloodFrozenBoxCodeDto(List<FrozenBoxCodeDto> frozenBoxCodeDtosSamply, String biologicalBloodSampleTable) {
        String substring="";
        StringBuffer stringBuffer=new StringBuffer();
        for (FrozenBoxCodeDto frozenBoxCodeDto:frozenBoxCodeDtosSamply) {
            String frozenBoxCode = frozenBoxCodeDto.getFrozenBoxCode();
            stringBuffer.append("'"+frozenBoxCode+"',");
        }
        if(!StringUtils.isEmpty(stringBuffer.toString())){
            substring= stringBuffer.substring(0, stringBuffer.length() - 1);
        }
        return hospitalCourierResultDao.queryByBloodFrozenBoxCodeDto(substring,biologicalBloodSampleTable);
    }

    public static void main(String[] args) {

        String substring="";
        List<FrozenBoxCodeDto> frozenBoxCodeDtosBoole =new ArrayList<>();
        FrozenBoxCodeDto frozenBoxCodeDto=new FrozenBoxCodeDto();
        frozenBoxCodeDto.setFrozenBoxCode("qq");
        FrozenBoxCodeDto frozenBoxCodeDto1=new FrozenBoxCodeDto();
        frozenBoxCodeDto1.setFrozenBoxCode("ww");
        FrozenBoxCodeDto frozenBoxCodeDto2=new FrozenBoxCodeDto();
        frozenBoxCodeDto2.setFrozenBoxCode("nn");
        frozenBoxCodeDtosBoole.add(frozenBoxCodeDto);
        frozenBoxCodeDtosBoole.add(frozenBoxCodeDto1);
        frozenBoxCodeDtosBoole.add(frozenBoxCodeDto2);
        StringBuffer stringBuffer=new StringBuffer();
        for (FrozenBoxCodeDto frozenBoxCodeDtos:frozenBoxCodeDtosBoole) {
            String frozenBoxCode = frozenBoxCodeDtos.getFrozenBoxCode();
            stringBuffer.append("'"+frozenBoxCode+"',");
        }
        if(!StringUtils.isEmpty(stringBuffer.toString())){
            substring= stringBuffer.substring(0, stringBuffer.length() - 1);
        }
        System.out.println(substring);
    }

}
