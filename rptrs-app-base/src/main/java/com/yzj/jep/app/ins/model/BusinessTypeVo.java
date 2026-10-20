package com.yzj.jep.app.ins.model;

/**
 * @Author: lc
 * @Date: 2020/11/7 6:36
 * @Version 1.0
 */
public class BusinessTypeVo {
    //文件id
    private String pid;
    //业务类型编号
    private String bid;
    //附件业务类型
    private String businessType;
    //是否必须上传
    private String uploadMust;
    //上传状态
    private String uploadStatus;
    //原文件名称
    private String fileName;
    //文件大小
    private String fileSize;
    //上传时间
    private String uploadTime;
    //上传人
    private String uploadPerson;
    //所属部门
    private String belongOrg;

    private String type;
    private String isMust;
    private String isUpload;
    //附件业务类型字典编码
    private String typeCode;

    private String beiFeng;

    public String getBeiFeng() {
        return beiFeng;
    }

    public void setBeiFeng(String beiFeng) {
        this.beiFeng = beiFeng;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIsMust() {
        return isMust;
    }

    public void setIsMust(String isMust) {
        this.isMust = isMust;
    }

    public String getIsUpload() {
        return isUpload;
    }

    public void setIsUpload(String isUpload) {
        this.isUpload = isUpload;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getUploadMust() {
        return uploadMust;
    }

    public void setUploadMust(String uploadMust) {
        this.uploadMust = uploadMust;
    }

    public String getUploadStatus() {
        return uploadStatus;
    }

    public void setUploadStatus(String uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getUploadPerson() {
        return uploadPerson;
    }

    public void setUploadPerson(String uploadPerson) {
        this.uploadPerson = uploadPerson;
    }

    public String getBelongOrg() {
        return belongOrg;
    }

    public void setBelongOrg(String belongOrg) {
        this.belongOrg = belongOrg;
    }

    @Override
    public String toString() {
        return "BusinessTypeVo{" +
                "pid='" + pid + '\'' +
                ", bid='" + bid + '\'' +
                ", businessType='" + businessType + '\'' +
                ", uploadMust='" + uploadMust + '\'' +
                ", uploadStatus='" + uploadStatus + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileSize='" + fileSize + '\'' +
                ", uploadTime='" + uploadTime + '\'' +
                ", uploadPerson='" + uploadPerson + '\'' +
                ", belongOrg='" + belongOrg + '\'' +
                ", type='" + type + '\'' +
                ", isMust='" + isMust + '\'' +
                ", isUpload='" + isUpload + '\'' +
                ", typeCode='" + typeCode + '\'' +
                '}';
    }
}
