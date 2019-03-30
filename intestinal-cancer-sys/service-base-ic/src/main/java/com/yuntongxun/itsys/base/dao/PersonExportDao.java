/**
 * Project Name:service-base-yl
 * File Name:PersonDao.java
 * Package Name:com.yuntongxun.itsys.base.dao
 * Date:2018年4月9日下午6:32:16
 * Copyright (c) 2018, ty All Rights Reserved.
 */

package com.yuntongxun.itsys.base.dao;

import com.yuntongxun.itsys.base.po.dto.ExeclData;
import com.yuntongxun.itsys.base.po.dto.HospitalReviewExport;
import com.yuntongxun.itsys.base.vo.ColonoscopyVo;
import com.yuntongxun.itsys.base.vo.HospitalBiologicalSampleResultVo;

import java.util.List;

/**
 * ClassName:PersonExportDao <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2018年4月9日 下午6:32:16 <br/>
 *
 * @author maxiang
 * @see
 * @since JDK 1.7
 */
public interface PersonExportDao {
    /**
     * 人员信息导出
     * @param person
     * @return
     */
    public List<HospitalReviewExport> queryExcel(HospitalReviewExport person,String level,Integer depId,String  userId);

    /**
     * 国家肠镜导出
     * @param queryCondition
     * @param communityDeptId
     * @param areaDeptId
     * @param deptType
     * @param isPage
     * @return
     */
    public List<ColonoscopyVo> queryForChangjingExcel(ColonoscopyVo queryCondition, Integer communityDeptId,
                                                 Integer areaDeptId, Integer deptType, boolean isPage);

    /**
     * 导出生物样本（唾液/粪便）
     * @param areaDeptId
     * @param sampleType
     * @return
     */
    public List<HospitalBiologicalSampleResultVo> queryForBiologicalSampleExcel(Integer areaDeptId,String sampleType);

    List<HospitalReviewExport> stollnationUsersQueryExcel(ExeclData execlData);

    List<ColonoscopyVo> queryFoStollCountryCJExcel(ExeclData execlData);
}

