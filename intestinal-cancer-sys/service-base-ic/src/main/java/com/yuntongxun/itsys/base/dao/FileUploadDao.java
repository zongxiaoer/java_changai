package com.yuntongxun.itsys.base.dao;

import com.yuntongxun.itsys.base.po.FileUploadLogPO;

import java.util.List;

/**
 * @author wp_sp
 * @date 2018/5/10
 */
public interface FileUploadDao {
    /**
     * 入库单个文件上传记录`
     * @param fileUploadLogPO
     * @return
     */
    public int insertFileUploadLog(FileUploadLogPO fileUploadLogPO);

}
