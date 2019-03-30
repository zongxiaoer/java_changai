package com.yuntongxun.itsys.base.service.impl;

import com.yuntongxun.itsys.base.common.constans.Constans;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.dao.BiologSampleDao;
import com.yuntongxun.itsys.base.dao.HospitalReferenceRecordDao;
import com.yuntongxun.itsys.base.po.HospitalBiologicalSampleResultPO;
import com.yuntongxun.itsys.base.po.dto.courier.HospitalReferenceRecordDto;
import com.yuntongxun.itsys.base.po.dto.message.HospitalMessageCenterDto;
import com.yuntongxun.itsys.base.service.BiologSampleService;
import com.yuntongxun.itsys.base.service.ColonoscopyService;
import com.yuntongxun.itsys.base.vo.DoctorInfo;
import com.yuntongxun.itsys.base.vo.HospitalBiologicalSampleResultVo;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author zongt
 * @date 2018/5/11
 */
@Service
public class BiologSampleServiceImpl implements BiologSampleService {

    private final Logger log = LogManager.getLogger(BiologSampleServiceImpl.class);


    @Autowired
    private BiologSampleDao biologSampleDao;

    @Autowired
    private ColonoscopyService colonoscopyService;

    @Autowired
    private HospitalReferenceRecordDao hospitalReferenceRecordDao;


    @Override
    @Transactional
    public void saveBiologSample(HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultPO) {
        List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultPOList = new ArrayList<>();
        hospitalBiologicalSampleResultPOList.add(hospitalBiologicalSampleResultPO);

        biologSampleDao.addBiologSamples(hospitalBiologicalSampleResultPOList);
    }

    @Override
    public ListPageUtil queryArea(HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultPO, String loginName) {
        //查询登录用户信息
        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);
        //筛查为6的地区，生物样本要将值更换为1 update by maxiang at 2018-07-30
        if(doctorInfo.getScreeningType()==6){
            doctorInfo.setScreeningType(1);
        }
        Integer screeningType = doctorInfo.getScreeningType();
        Integer areaDeptId = doctorInfo.getAreaDeptId();

        Set<Integer> list = new HashSet<>();
        //校验来自血液
        if (Constans.SAMPLE_TYPE6.equals(hospitalBiologicalSampleResultPO.getSampleTypeAll3()) && !StringUtils.isEmpty(hospitalBiologicalSampleResultPO.getFrozenBoxCode())) {
            List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultVoList = biologSampleDao.queryLikeFrozenBoxCode(hospitalBiologicalSampleResultPO.getFrozenBoxCode());
            for (HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultVo : hospitalBiologicalSampleResultVoList) {
                list.add(hospitalBiologicalSampleResultVo.getBloodSampleId());
            }
        }
        StringBuffer addId = new StringBuffer();
        for (Integer ids : list) {
            addId.append(ids + ",");
        }
        //分页查询结肠镜检查数据
        ListPageUtil listPage = biologSampleDao.queryArea(hospitalBiologicalSampleResultPO, doctorInfo.getAreaDeptId(), Constans.DEPARTMENT_TYPE_AREA, true, StringUtils.isEmpty(addId.toString()) ? "" : addId.substring(0, addId.toString().length() - 1).toString());
        //biologSampleDao.queryLikeFrozenBoxCode()
        List<Map<String, String>> resultList = listPage.getResultList();
        List<Map<String, String>> resultListMap = new ArrayList<>();

        for (Map<String, String> map : resultList) {
            String sampleType = map.get("sampleType");
            String sampleLine = map.get("sampleLine");
            String sampleColumn = map.get("sampleColumn");
            StringBuffer stringBuffer = new StringBuffer();
            if (sampleLine != null && !StringUtils.isEmpty(sampleLine) && !sampleLine.equals("null")) {
                if (sampleLine.contains(",")) {
                    String[] split = sampleLine.split(",");
                    String endNumber = split[split.length - 1];
                    String startNumber = split[0];
                    stringBuffer.append(sampleColumn + startNumber + "~" + sampleColumn + endNumber);
                } else {
                    stringBuffer.append(sampleColumn + sampleLine);
                }
            }

            map.put("frozenBoxCodeHeader", Constans.FROZEN_BOX_CODE_HEADER + screeningType + sampleType);
            map.put("sampleIdHeader", Constans.FROZEN_BOX_CODE_HEADER + screeningType);
            map.put("sampleColumnAndLine", stringBuffer.toString());
            resultListMap.add(map);

        }
        listPage.setResultList(resultListMap);
        return listPage;
    }

    @Override
    public ListPageUtil queryCountry(HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultPO, String loginName) {
        Set<Integer> list = new HashSet<>();
        //校验来自血液
        if (Constans.SAMPLE_TYPE6.equals(hospitalBiologicalSampleResultPO.getSampleTypeAll3()) && !StringUtils.isEmpty(hospitalBiologicalSampleResultPO.getFrozenBoxCode())) {
            List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultVoList = biologSampleDao.queryLikeFrozenBoxCode(hospitalBiologicalSampleResultPO.getFrozenBoxCode());
            for (HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultVo : hospitalBiologicalSampleResultVoList) {
                list.add(hospitalBiologicalSampleResultVo.getBloodSampleId());
            }
        }
        StringBuffer addId = new StringBuffer();
        for (Integer ids : list) {
            addId.append(ids + ",");
        }
        //分页查询结肠镜检查数据
        ListPageUtil listPage = biologSampleDao.queryCountry(hospitalBiologicalSampleResultPO, true, StringUtils.isEmpty(addId.toString()) ? "" : addId.substring(0, addId.toString().length() - 1).toString());
        List<Map<String, String>> resultList = listPage.getResultList();
        List<Map<String, String>> resultListMap = new ArrayList<>();
        for (Map<String, String> map : resultList) {
            String sampleLine = map.get("sampleLine");
            String sampleColumn = map.get("sampleColumn");
            StringBuffer stringBuffer = new StringBuffer();
            if (sampleLine != null && !StringUtils.isEmpty(sampleLine) && !sampleLine.equals("null")) {
                if (sampleLine.contains(",")) {
                    String[] split = sampleLine.split(",");
                    String endNumber = split[split.length - 1];
                    String startNumber = split[0];
                    stringBuffer.append(sampleColumn + startNumber + "~" + sampleColumn + endNumber);
                } else {
                    stringBuffer.append(sampleColumn + sampleLine);
                }
            }
            map.put("sampleColumnAndLine", stringBuffer.toString());
            resultListMap.add(map);

        }
        listPage.setResultList(resultListMap);
        return listPage;
    }

    @Override
    @Transactional
    public void updateBiologSample(HospitalBiologicalSampleResultPO hospitalBiologicalSampleResultPO) {
        //判断来自新增还是操作
        if (hospitalBiologicalSampleResultPO.getOperationSource().equals(Constans.OPERATION_SOURCE_TYPE)) {
        } else {
            if (hospitalBiologicalSampleResultPO.getSampleType().equals(Constans.SAMPLE_TYPE1)) {
                hospitalBiologicalSampleResultPO.setSampleType(Constans.PERSON_TODO_EVENT_TYPE11.toString());
            } else if (hospitalBiologicalSampleResultPO.getSampleType().equals(Constans.SAMPLE_TYPE2)) {
                hospitalBiologicalSampleResultPO.setSampleType(Constans.PERSON_TODO_EVENT_TYPE14.toString());
            } else if (hospitalBiologicalSampleResultPO.getSampleType().equals(Constans.SAMPLE_TYPE3)) {
                hospitalBiologicalSampleResultPO.setSampleType(Constans.PERSON_TODO_EVENT_TYPE15.toString());
            } else if (hospitalBiologicalSampleResultPO.getSampleType().equals(Constans.SAMPLE_TYPE4)) {
                hospitalBiologicalSampleResultPO.setSampleType(Constans.PERSON_TODO_EVENT_TYPE13.toString());
            } else if (hospitalBiologicalSampleResultPO.getSampleType().equals(Constans.SAMPLE_TYPE5)) {
                hospitalBiologicalSampleResultPO.setSampleType(Constans.PERSON_TODO_EVENT_TYPE12.toString());
            } else if (hospitalBiologicalSampleResultPO.getSampleType().equals(Constans.SAMPLE_TYPE6)) {
                hospitalBiologicalSampleResultPO.setSampleType(Constans.PERSON_TODO_EVENT_TYPE18.toString());

            }
            //修改代办
            biologSampleDao.updateSampleEvent(hospitalBiologicalSampleResultPO);
        }
        //判断是什么样本类型，如果是血清、血浆，位置自动加到5位

        if (hospitalBiologicalSampleResultPO.getSampleType().equals(Constans.PERSON_TODO_EVENT_TYPE11.toString()) || hospitalBiologicalSampleResultPO.getSampleType().equals(Constans.PERSON_TODO_EVENT_TYPE14.toString())) {
            String sampleLine = hospitalBiologicalSampleResultPO.getSampleLine();
            if (!StringUtils.isEmpty(sampleLine)) {
                if (sampleLine.equals(Constans.COLLECT_STATUS_SAMPLE_LINE1)) {
                    sampleLine = sampleLine + ",2,3,4,5";
                } else if (sampleLine.equals(Constans.COLLECT_STATUS_SAMPLE_LINE6)) {
                    sampleLine = sampleLine + ",7,8,9,10";
                }
                hospitalBiologicalSampleResultPO.setSampleLine(sampleLine);
            }
        }
        //修改生物样本信息
        try {
            if (hospitalBiologicalSampleResultPO.getCollectStatus().equals(Constans.COLLECT_STATUS_PROVIDE)) {
                //修改血液样本
                biologSampleDao.updateById(hospitalBiologicalSampleResultPO);
                //修改血液样本下样本
            } else {
                biologSampleDao.updateByIdNO(hospitalBiologicalSampleResultPO);
            }
        } catch (Exception e) {
            log.info(e.toString());
            throw new RuntimeException(e.toString());
        }
    }

    @Override
    public List<HospitalBiologicalSampleResultVo> queryById(Integer id, Integer areaDeptId) {

        return biologSampleDao.quertById(id, areaDeptId);
    }

    @Override
    public List<HospitalBiologicalSampleResultVo> queryByFrozenBoxCode(String frozen_box_code, String table) {
        return biologSampleDao.queryByFrozenBoxCode(frozen_box_code, table);
    }

    @Override
    public List<HospitalBiologicalSampleResultVo> queryBySampleColumnAndLine(String frozen_box_code, String sample_column, String sample_line) {
        return biologSampleDao.queryBySampleColumnAndLine(frozen_box_code, sample_column, sample_line);
    }

    @Override
    public String queryByAssociatedSample(String associatedSample) {
        List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultVos = biologSampleDao.queryByAssociatedSample(associatedSample);
        String sample_id = "";
        for (HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultVo : hospitalBiologicalSampleResultVos) {
            if (!StringUtils.isEmpty(hospitalBiologicalSampleResultVo.getSampleId())) {
                sample_id = hospitalBiologicalSampleResultVo.getSampleId();
                break;
            }
        }
        return sample_id;
    }

    @Override
    public boolean checkSample(String frozen_box_code, String sampleType) {
        List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultVos = biologSampleDao.queryByFrozenBoxCode(frozen_box_code, Constans.BIOLOGICAL_SAMPLE_TABLE);
        if (hospitalBiologicalSampleResultVos.size() > 0) {
            for (HospitalBiologicalSampleResultVo vo : hospitalBiologicalSampleResultVos) {
                if (!vo.getSampleType().equals(sampleType)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public List<HospitalBiologicalSampleResultVo> queryBySampleID(String sampleId) {
        return biologSampleDao.queryBySampleID(sampleId);
    }

    @Override
    public List<HospitalBiologicalSampleResultVo> queryBySampleIDAndSampletype(String sampleId, String sampleType) {
        return biologSampleDao.queryBySampleIDAndSampletype(sampleId, sampleType);
    }

    @Override
    @Transactional
    public void saveBiologBloodSample(HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultPO) {
        List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultPOList = new ArrayList<>();
        //int samplyBloodId = biologSampleDao.addBiologSampleInPerson(hospitalBiologicalSampleResultPO, Constans.BIOLOGICAL_SAMPLE_TABLE);
        Integer samplyBloodId = biologSampleDao.addBiologSample(hospitalBiologicalSampleResultPO);
        for (HospitalBiologicalSampleResultVo vo : hospitalBiologicalSampleResultPO.getHospitalBiologicalSampleResultPOList()) {
            if (vo.isChecklist()) {
                String sampleColumn = "";
                String sampleLine = "";
                for (String sampleColumnAndLine : vo.getSampleColumnAndLine()) {
                    String column = sampleColumnAndLine.substring(0, 1);//行
                    String line = sampleColumnAndLine.substring(1, sampleColumnAndLine.length());//列
                    if (!sampleColumn.equals(column)) {
                        sampleColumn = column;
                    }
                    if (!sampleLine.equals(line)) {
                        sampleLine += line + ",";
                    }
                }
                if (!StringUtils.isEmpty(sampleLine)) {
                    sampleLine = sampleLine.substring(0, sampleLine.length() - 1);
                }
                vo.setSampleColumn(sampleColumn);
                vo.setSampleLine(sampleLine);
                vo.setCollectStatus(hospitalBiologicalSampleResultPO.getCollectStatus());
                vo.setCourierStatus(hospitalBiologicalSampleResultPO.getCourierStatus());
                if (vo.getFrozenBoxCode().length() != 7) {
                    vo.setFrozenBoxCode(null);
                }
            }
            vo.setCommunityDeptId(hospitalBiologicalSampleResultPO.getCommunityDeptId());
            vo.setAreaDeptId(hospitalBiologicalSampleResultPO.getAreaDeptId());
            vo.setOperationSource(hospitalBiologicalSampleResultPO.getOperationSource());
            vo.setBloodSampleId(samplyBloodId);
            hospitalBiologicalSampleResultPOList.add(vo);
        }
        try {
            biologSampleDao.saveBiologBloodSample(hospitalBiologicalSampleResultPOList);
        } catch (Exception e) {
            log.info(e.toString());
            throw new RuntimeException("saveBiologBloodSample is error" + e.toString());
        }
    }

    @Override
    @Transactional
    public void updateBiologBloodSample(HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultPO) {
        //判断来自新增还是操作
        if (hospitalBiologicalSampleResultPO.getOperationSource().equals(Constans.OPERATION_SOURCE_TYPE)) {
        } else {
            //修改代办
            hospitalBiologicalSampleResultPO.setSampleType(Constans.PERSON_TODO_EVENT_TYPE18.toString());
            try {
                biologSampleDao.updateSampleEvent(hospitalBiologicalSampleResultPO);
            } catch (Exception e) {
                log.info(e.toString());
            }
        }

        //修改生物样本信息
        try {
            if (hospitalBiologicalSampleResultPO.getCollectStatus().equals(Constans.COLLECT_STATUS_PROVIDE)) {
                List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultPOList = new ArrayList<>();
                for (HospitalBiologicalSampleResultVo vo : hospitalBiologicalSampleResultPO.getHospitalBiologicalSampleResultPOList()) {
                    if (vo.isChecklist()) {
                        String sampleColumn = "";
                        String sampleLine = "";
                        for (String sampleColumnAndLine : vo.getSampleColumnAndLine()) {
                            String column = sampleColumnAndLine.substring(0, 1);//行
                            String line = sampleColumnAndLine.substring(1, sampleColumnAndLine.length());//列
                            if (!sampleColumn.equals(column)) {
                                sampleColumn = column;
                            }
                            if (!sampleLine.equals(line)) {
                                sampleLine += line + ",";
                            }
                        }
                        if (!StringUtils.isEmpty(sampleLine)) {
                            sampleLine = sampleLine.substring(0, sampleLine.length() - 1);
                        }
                        vo.setSampleColumn(sampleColumn);
                        vo.setSampleLine(sampleLine);
                        vo.setCourierStatus(Constans.COURIER_STATUS_CODE2);
                        hospitalBiologicalSampleResultPOList.add(vo);
                    }
                }
                try {
                    //修改血液样本
                    biologSampleDao.updateById(hospitalBiologicalSampleResultPO);
                    //修改血液样本下样本
                    biologSampleDao.updateALLById(hospitalBiologicalSampleResultPOList);
                } catch (Exception e) {
                    log.info(e.toString());
                }
            } else {
                biologSampleDao.updateByIdNO(hospitalBiologicalSampleResultPO);
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new RuntimeException("update BiologBloodSample is error" + e.toString());
        }
    }

    @Override
    public List<HospitalBiologicalSampleResultVo> queryByBloodId(Integer id) {
        return biologSampleDao.quertByBloodId(id);
    }

    @Override
    public List<HospitalBiologicalSampleResultVo> queryByBloodSampleColumnAndLine(String frozenBoxCode, String sampleColumn, String sampleLine) {
        return biologSampleDao.queryByBloodSampleColumnAndLine(frozenBoxCode, sampleColumn, sampleLine);
    }

    @Override
    @Transactional
    public void updateEditBiologBloodSample(HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultPO, HospitalReferenceRecordDto hospitalReferenceRecordDto) {
        //修改生物样本信息
        try {
            List<String> sampleTypes = new ArrayList<>();
            List<String> sampleGetTypes = new ArrayList<>();
/*            public static final String SAMPLE_TYPE1="S";//1--血清.
            public static final String SAMPLE_TYPE2="P";//2--血浆.
            public static final String SAMPLE_TYPE3="W";//3--白细胞*/
            sampleTypes.add(Constans.SAMPLE_TYPE1);
            sampleTypes.add(Constans.SAMPLE_TYPE2);
            sampleTypes.add(Constans.SAMPLE_TYPE3);
            hospitalReferenceRecordDao.updateForEdir(hospitalReferenceRecordDto);
            biologSampleDao.updateForEdirStatus(Constans.APPLY_EDIT_STATUS1,Constans.EDIT_STATUS1,hospitalBiologicalSampleResultPO.getId());
            if (hospitalBiologicalSampleResultPO.getCollectStatus().equals(Constans.COLLECT_STATUS_PROVIDE)) {
                List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultPOList = new ArrayList<>();
                for (HospitalBiologicalSampleResultVo vo : hospitalBiologicalSampleResultPO.getHospitalBiologicalSampleResultPOList()) {
                    if (vo.isChecklist()) {
                        String sampleColumn = "";
                        String sampleLine = "";
                        for (String sampleColumnAndLine : vo.getSampleColumnAndLine()) {
                            String column = sampleColumnAndLine.substring(0, 1);//行
                            String line = sampleColumnAndLine.substring(1, sampleColumnAndLine.length());//列
                            if (!sampleColumn.equals(column)) {
                                sampleColumn = column;
                            }
                            if (!sampleLine.equals(line)) {
                                sampleLine += line + ",";
                            }
                        }
                        if (!StringUtils.isEmpty(sampleLine)) {
                            sampleLine = sampleLine.substring(0, sampleLine.length() - 1);
                        }
                        vo.setSampleColumn(sampleColumn);
                        vo.setSampleLine(sampleLine);
                        vo.setBloodSampleId(hospitalBiologicalSampleResultPO.getId());
                        vo.setCourierStatus(Constans.COURIER_STATUS_CODE2);
                        hospitalBiologicalSampleResultPOList.add(vo);
                        sampleGetTypes.add(vo.getSampleType());
                    }
                }
                try {
                    //修改血液样本
                    biologSampleDao.updateById(hospitalBiologicalSampleResultPO);
                    sampleTypes.removeAll(sampleGetTypes);
                    for (String s:sampleTypes) {
                        HospitalBiologicalSampleResultVo vo=new HospitalBiologicalSampleResultVo();
                        vo.setBloodSampleId(hospitalBiologicalSampleResultPO.getId());
                        vo.setSampleType(s);
                        vo.setCourierStatus(null);
                        hospitalBiologicalSampleResultPOList.add(vo);
                    }

                    //修改血液样本下样本
                    biologSampleDao.updateALLById(hospitalBiologicalSampleResultPOList);
                } catch (Exception e) {
                    log.info(e.toString());
                }
            } /*else {
                biologSampleDao.updateByIdNO(hospitalBiologicalSampleResultPO);
                List<HospitalBiologicalSampleResultVo> hospitalBiologicalSampleResultPOList = new ArrayList<>();
                for (String sampleType:sampleTypes) {
                    HospitalBiologicalSampleResultVo vo=new HospitalBiologicalSampleResultVo();
                    vo.setBloodSampleId(hospitalBiologicalSampleResultPO.getId());
                    vo.setSampleType(sampleType);
                    vo.setCourierStatus(Constans.COURIER_STATUS_CODE2);

                    hospitalBiologicalSampleResultPOList.add(vo);
                }
                //修改血液样本下样本
                biologSampleDao.updateById(hospitalBiologicalSampleResultPO);
                biologSampleDao.updateALLById(hospitalBiologicalSampleResultPOList);
            }*/
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new RuntimeException("update BiologBloodSample is error" + e.toString());
        }
    }

    @Override
    @Transactional
    public void updateEditBiologSample(HospitalBiologicalSampleResultVo hospitalBiologicalSampleResultPO, HospitalReferenceRecordDto hospitalReferenceRecordDto) {

        //判断是什么样本类型，如果是血清、血浆，位置自动加到5位

        if (hospitalBiologicalSampleResultPO.getSampleType().equals(Constans.PERSON_TODO_EVENT_TYPE11.toString()) || hospitalBiologicalSampleResultPO.getSampleType().equals(Constans.PERSON_TODO_EVENT_TYPE14.toString())) {
            String sampleLine = hospitalBiologicalSampleResultPO.getSampleLine();
            if (!StringUtils.isEmpty(sampleLine)) {
                if (sampleLine.equals(Constans.COLLECT_STATUS_SAMPLE_LINE1)) {
                    sampleLine = sampleLine + ",2,3,4,5";
                } else if (sampleLine.equals(Constans.COLLECT_STATUS_SAMPLE_LINE6)) {
                    sampleLine = sampleLine + ",7,8,9,10";
                }
                hospitalBiologicalSampleResultPO.setSampleLine(sampleLine);
            }
        }
        //修改生物样本信息
        try {
            hospitalReferenceRecordDao.updateForEdir(hospitalReferenceRecordDto);
            biologSampleDao.updateForEdirStatus(Constans.APPLY_EDIT_STATUS1,Constans.EDIT_STATUS1,hospitalBiologicalSampleResultPO.getId());
            if (hospitalBiologicalSampleResultPO.getCollectStatus().equals(Constans.COLLECT_STATUS_PROVIDE)) {
                //修改血液样本
                biologSampleDao.updateById(hospitalBiologicalSampleResultPO);
                //修改血液样本下样本
            } /*else {
                //修改血液样本
                HospitalBiologicalSampleResultPO hospitalBiologicalSampleResultPO1=new HospitalBiologicalSampleResultPO();
                hospitalBiologicalSampleResultPO1.setId(hospitalBiologicalSampleResultPO.getId());
                hospitalBiologicalSampleResultPO1.setCollectStatus(Constans.);
                biologSampleDao.updateById(hospitalBiologicalSampleResultPO);
                biologSampleDao.updateByIdNO(hospitalBiologicalSampleResultPO);
            }*/
        } catch (Exception e) {
            log.info(e.toString());
            throw new RuntimeException(e.toString());
        }
    }

    @Override
    public List<HospitalBiologicalSampleResultVo> queryBloodById(Integer id) {
        return biologSampleDao.queryBloodById(id);
    }

    @Override
    public List<HospitalBiologicalSampleResultVo> queryByBloodSampleColumnAndLineW(String frozenBoxCode, String sampleColumn, String sampleLine) {
        return biologSampleDao.queryByBloodSampleColumnAndLineW(frozenBoxCode, sampleColumn, sampleLine);

    }


    public static void main(String[] args) {
        List<String> list1=new ArrayList<>();
        List<String> list2=new ArrayList<>();
        list1.add("1");
        list1.add("2");
        list1.add("3");
        list2.add("1");
        list2.add("2");
        list1.removeAll(list2);
        System.out.println(list1);
        System.out.println(list2);
    }
}
