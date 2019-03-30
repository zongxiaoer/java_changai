package com.yuntongxun.itsys.base.dao.impl;

import com.alibaba.fastjson.JSON;
import com.yuntongxun.itsys.base.dao.FileUploadDao;
import com.yuntongxun.itsys.base.po.FileUploadLogPO;
import com.yuntongxun.itsys.base.po.HospitalBiologicalSampleResultPO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author wp_sp
 * @date 2018/5/10
 */
@Repository
public class FileUploadDaoImpl implements FileUploadDao {

    @Autowired
    private JdbcTemplate jdbc;
    private final Logger log = LogManager.getLogger(FileUploadDaoImpl.class.getName());


    @Override
    public int insertFileUploadLog(FileUploadLogPO fileUploadLogPO) {
        log.debug("上传文件数据库操作对象 ：=========" + JSON.toJSONString(fileUploadLogPO));
        String sql = "INSERT INTO `file_upload_log` (`id`,`file_name`, `file_nickname`, `file_path`, `file_upload_time`, `file_size`, `file_suffix`, `file_business_type`, `file_upload_user`) VALUES (?,?, ?, ?, now(), ?,?,?,?)";
        log.debug("上传文件数据库操作SQL ：=========" + sql);
        return jdbc.update(sql, fileUploadLogPO.getId(), fileUploadLogPO.getFileName(), fileUploadLogPO.getFileNickname(), fileUploadLogPO.getFilePath(), fileUploadLogPO.getFileSize(), fileUploadLogPO.getFileSuffix(), fileUploadLogPO.getFileBusinessType(), fileUploadLogPO.getFileUploadUser());
    }


}
