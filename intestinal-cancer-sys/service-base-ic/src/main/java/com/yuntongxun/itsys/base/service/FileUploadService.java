package com.yuntongxun.itsys.base.service;

import com.yuntongxun.itsys.base.po.FileUploadLogPO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author wp_sp
 * @date 2018/5/10
 */
public interface FileUploadService {
    /**
     * 获取上传文件后缀属性名
     *
     * @param file
     * @return
     */
    String getFileNameSuffix(MultipartFile file);


    /**
     * 上传单个文件并响应文件上传对象
     *
     * @param file
     * @param loginName
     * @param fileSourceType4
     * @return
     */
    FileUploadLogPO upLoadSingleFile(MultipartFile file, String loginName, Integer fileSourceType4);

    FileUploadLogPO upLoadImgeFile(MultipartFile file, String loginName, Integer fileSourceType5,Integer id);

    List<FileUploadLogPO> upLoadColonoscopyImgeFiles(MultipartFile[] files, String loginName, Integer fileSourceType6, Integer id);

    /**
     * c3上传多癌文件
     * @param files
     * @param loginName
     * @param fileSourceType7
     * @param id
     * @return
     */
    List<FileUploadLogPO> upLoadCancerFiles(MultipartFile[] files, String loginName, Integer fileSourceType7, Integer id);
}
