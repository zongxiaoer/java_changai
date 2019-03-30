package com.yuntongxun.itsys.base.service.impl;

import com.yuntongxun.itsys.base.common.util.DateUtil;
import com.yuntongxun.itsys.base.common.util.JSONUtils;
import com.yuntongxun.itsys.base.dao.HcRecordDao;
import com.yuntongxun.itsys.base.dao.HcrAllocationDao;
import com.yuntongxun.itsys.base.po.HospitalColonoscopyRecord;
import com.yuntongxun.itsys.base.po.ReserveAllocation;
import com.yuntongxun.itsys.base.service.ColonoscopyService;
import com.yuntongxun.itsys.base.vo.DoctorInfo;
import com.yuntongxun.itsys.base.vo.ReserveAllocationVo;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yuntongxun.itsys.base.common.util.ListPageUtil;
import com.yuntongxun.itsys.base.common.util.ResultUtil;
import com.yuntongxun.itsys.base.service.ColonoscopyReservationAllocationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zongt
 * @date 2018/4/20
 */

@Service
public class ColonoscopyReservationAllocationServiceImpl implements ColonoscopyReservationAllocationService {
    private final Logger log = LogManager.getLogger(DepartMentServiceImpl.class);

    @Autowired
    private HcrAllocationDao colonoscopyReservationAllocationDao;

    @Autowired
    private ColonoscopyService colonoscopyService;

    @Autowired
    private HcRecordDao hcRecordDao;


    @Override
    public String queryColonoscopyReservationAllocation(String body, String loginName) {
        JsonObject obj = new JsonParser().parse(body).getAsJsonObject();

        DoctorInfo doctorInfo = colonoscopyService.getDoctorInfo(loginName);

        //获取地区ID
        Integer areaDeptId = doctorInfo.getAreaDeptId();
        //开始时间
        String startTime = obj.get("startTime") == null || obj.get("startTime").toString().equals("null") ? null : obj.get("startTime").getAsString();
        //结束时间
        String endTime = obj.get("endTime") == null || obj.get("endTime").toString().equals("null") ? null : obj.get("endTime").getAsString();
        //社区医院唯一标识
        String communityDeptId = obj.get("communityDeptId") == null || obj.get("communityDeptId").toString().equals("null") ? null : obj.get("communityDeptId").getAsString();
        int pageNo = obj.get("pageNo") == null || obj.get("pageNo").toString().equals("null") ? -1 : obj.get("pageNo").getAsInt();
        int pageSize = obj.get("pageSize") == null || obj.get("pageSize").toString().equals("null") ? -1 : obj.get("pageSize").getAsInt();
        log.info("@Service queryDictionary parm:  startTime={},endTime={},communityDeptId={},pageNo={},pageSize={}", startTime, endTime, communityDeptId, pageNo, pageSize);
        //获取结肠镜预约分配表集合
        ListPageUtil listPage = colonoscopyReservationAllocationDao.queryColonoscopyReservationAllocation(startTime, endTime, communityDeptId, areaDeptId, pageNo, pageSize);

        List<Map<String, Object>> listPageResultList = listPage.getResultList();
        StringBuffer stringBuffer = new StringBuffer();
        List<ReserveAllocation> reserveAllocationList = new ArrayList<>();
        for (Map<String, Object> map : listPageResultList) {
            ReserveAllocation reserveAllocation = new ReserveAllocation();
            //把map转为对象一样的名字
            try {
                org.apache.commons.beanutils.BeanUtils.populate(reserveAllocation, map);
                reserveAllocationList.add(reserveAllocation);
                //拼接IDs
                stringBuffer.append(reserveAllocation.getId() + ",");
            } catch (Exception e) {
                log.info("map 转对象存在问题");
            }

        }
        List<ReserveAllocationVo> reserveAllocationVoList = new ArrayList<>();
        //获取结肠镜预约分配Id
        List<HospitalColonoscopyRecord> examination_status = new ArrayList<>();
        List<HospitalColonoscopyRecord> reserve_status = new ArrayList<>();
        if (reserveAllocationList.size() > 0) {
            String ids = stringBuffer.toString().substring(0, stringBuffer.toString().length() - 1);
            if (!StringUtils.isEmpty(ids)) {
                //获取已就诊数量
                examination_status = hcRecordDao.getStatusByAllocation_id("examination_status", ids);
                //获取已预约数量
                reserve_status = hcRecordDao.getStatusByAllocation_id("reserve_status", ids);
            }
            //数据整理
            for (ReserveAllocation reserveAllocation : reserveAllocationList) {
                ReserveAllocationVo reserveAllocationVo = new ReserveAllocationVo();
                BeanUtils.copyProperties(reserveAllocation, reserveAllocationVo);
                reserveAllocationVo.setReservationDate(reserveAllocation.getReservationDate() == null ? "" : DateUtil.dateToStr(reserveAllocation.getReservationDate(), DateUtil.YMR_SLASH));
                reserveAllocationVo.setReservationDate(new StringBuffer().append(reserveAllocationVo.getReservationDate()+"  时间"+reserveAllocation.getStartTime()+"--"+reserveAllocation.getEndTime()).toString());
                for (HospitalColonoscopyRecord hospitalColonoscopyRecord : examination_status) {
                    if (reserveAllocationVo.getId().toString().equals(hospitalColonoscopyRecord.getAllocation_id())) {
                        reserveAllocationVo.setExaminationStatus(hospitalColonoscopyRecord.getExamination_status());
                        break;
                    } else {
                        reserveAllocationVo.setExaminationStatus(0);
                    }
                }
                for (HospitalColonoscopyRecord hospitalColonoscopyRecord : reserve_status) {
                    if (reserveAllocationVo.getId().toString().equals(hospitalColonoscopyRecord.getAllocation_id())) {
                        reserveAllocationVo.setReserveStatus(hospitalColonoscopyRecord.getReserve_status());
                        break;
                    } else {
                        reserveAllocationVo.setReserveStatus(0);
                    }
                }
                reserveAllocationVoList.add(reserveAllocationVo);
            }
        }
        return JSONUtils.toJson(ResultUtil.success(reserveAllocationVoList, listPage.getPageInfo()));
    }

    public static void main(String[] args) {
        JsonObject obj = null;
        System.out.println(obj.equals("null"));
    }
}
