package com.yuntongxun.itsys.base.po;

import java.io.Serializable;
import java.util.Date;

public class FileUploadLogPO implements Serializable {
    private String id;

    private String fileName;

    private String fileNickname;

    private String filePath;

    private Date fileUploadTime;

    private Double fileSize;

    private String fileSuffix;

    private Integer fileBusinessType;

    private String fileUploadUser;

    private Integer status;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getFileNickname() {
        return fileNickname;
    }

    public void setFileNickname(String fileNickname) {
        this.fileNickname = fileNickname == null ? null : fileNickname.trim();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public Date getFileUploadTime() {
        return fileUploadTime;
    }

    public void setFileUploadTime(Date fileUploadTime) {
        this.fileUploadTime = fileUploadTime;
    }

    public Double getFileSize() {
        return fileSize;
    }

    public void setFileSize(Double fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix == null ? null : fileSuffix.trim();
    }

    public Integer getFileBusinessType() {
        return fileBusinessType;
    }

    public void setFileBusinessType(Integer fileBusinessType) {
        this.fileBusinessType = fileBusinessType;
    }

    public String getFileUploadUser() {
        return fileUploadUser;
    }

    public void setFileUploadUser(String fileUploadUser) {
        this.fileUploadUser = fileUploadUser == null ? null : fileUploadUser.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}