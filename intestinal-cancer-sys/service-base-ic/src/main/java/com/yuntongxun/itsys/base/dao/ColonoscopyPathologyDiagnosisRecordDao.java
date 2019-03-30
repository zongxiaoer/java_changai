package com.yuntongxun.itsys.base.dao;

import com.yuntongxun.itsys.base.po.ColonoscopyPathologyDiagnosisRecord;

import java.util.List;

/**
 * 结肠镜病理诊断记录表Dao
 */
public interface ColonoscopyPathologyDiagnosisRecordDao {
    public Integer addColonoscopyPathologyDiagnosisRecord(ColonoscopyPathologyDiagnosisRecord colonoscopyPathologyDiagnosisRecord);

    List<ColonoscopyPathologyDiagnosisRecord> queryColonoscopyPathologyDiagnosisRecordsByPathologyResultId(Integer pathologyResultId);

    void deleteByResultId(Integer Id);
}
