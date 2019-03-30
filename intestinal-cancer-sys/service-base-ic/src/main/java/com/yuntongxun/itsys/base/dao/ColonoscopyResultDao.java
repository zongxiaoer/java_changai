package com.yuntongxun.itsys.base.dao;

import com.yuntongxun.itsys.base.po.ColonoscopyResult;
import com.yuntongxun.itsys.base.po.HospitalColonoscopyResult;
import com.yuntongxun.itsys.base.po.ViolationScheme;
import com.yuntongxun.itsys.base.po.dto.courier.HospitalCourierResultDto;

import java.util.List;

/**
 * 肠镜报告记录Dao
 */
public interface ColonoscopyResultDao {

    public Integer addColonoscopyResult(ColonoscopyResult colonoscopyResult);

    ColonoscopyResult queryHospitalColonoscopyResultById(Integer id,String sid);

    List<ColonoscopyResult> queryById(Integer id);

    Integer updateColonoscopyResult(ColonoscopyResult colonoscopyResult);

    Integer updateUrlByID(String pathUrl, Integer id);
}
