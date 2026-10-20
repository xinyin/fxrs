package com.yzj.jep.app.base.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author : zhangfei
 * @date : 2020/6/15 4:12 下午
 */
@Component
@PropertySource(value = {"classpath:jep.properties"})
public class Configs {
    @Value("${neo4jUrl}")
    public String neo4jUrl;

    @Value("${file.path}")
    public String filePath;

    @Value("${file.path.down}")
    public String filePathDown;

    @Value("${file.path.temp}")
    public String temp;

    @Value("${file.path.attachment}")
    public String attachment;

    @Value("${file.path.systematic}")
    public String systematic;

    @Value("${file.path.disclose}")
    public String disclose;

    @Value("${file.path.report}")
    public String report;
    @Value("${file.path.msgimg}")
    public String msgimg;

    @Value("${file.path.relResolution}")
    private String relResolutionPath;

    public String getRelResolutionPath() {
        return relResolutionPath;
    }

    public void setRelResolutionPath(String relResolutionPath) {
        this.relResolutionPath = relResolutionPath;
    }

    public String getMsgimg() {
        return msgimg;
    }

    public void setMsgimg(String msgimg) {
        this.msgimg = msgimg;
    }

    @Value("${file.path.knowledgebase.upload}")
    public String knowledgebase;

    @Value("${file.path.knowledgebase.download}")
    public String knowledgebaseDownload;

    @Value("${file.path.knowledgebase.download}")
    public String archManageDownload;

    @Value("/home/rptrs/upload/")
    public String reportEx;

    @Value("/home/rptrs/upload")
    public String reportZip;

    @Value("${file.path.east}")
    public String eastFile;

    @Value("${pm.appId}")
    public String appId;


    @Value("${pm.secretKey}")
    public String secretKey;


    @Value("${pm.empUrl}")
    public String empUrl;

    @Value("${file.path.template}")
    public String  template;
    @Value("${file.path.font}")
    public String  font;
    @Value("${file.path.save}")
    public String  save;

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public String getSave() {
        return save;
    }

    public void setSave(String save) {
        this.save = save;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getEmpUrl() {
        return empUrl;
    }

    public void setEmpUrl(String empUrl) {
        this.empUrl = empUrl;
    }

    public String getReportZip() {
        return reportZip;
    }

    public void setReportZip(String reportZip) {
        this.reportZip = reportZip;
    }

    public String getReportEx() {
        return reportEx;
    }

    public void setReportEx(String reportEx) {
        this.reportEx = reportEx;
    }

    public String getEastFile() {
        return eastFile;
    }

    public void setEastFile(String eastFile) {
        this.eastFile = eastFile;
    }

    public String getArchManageDownload() {
        return archManageDownload;
    }

    public void setArchManageDownload(String archManageDownload) {
        this.archManageDownload = archManageDownload;
    }

    public String getKnowledgebaseDownload() {
        return knowledgebaseDownload;
    }

    public void setKnowledgebaseDownload(String knowledgebaseDownload) {
        this.knowledgebaseDownload = knowledgebaseDownload;
    }

    public String getKnowledgebase() {
        return knowledgebase;
    }

    public void setKnowledgebase(String knowledgebase) {
        this.knowledgebase = knowledgebase;
    }

    public String getNeo4jUrl() {
        return neo4jUrl;
    }

    public void setNeo4jUrl(String neo4jUrl) {
        this.neo4jUrl = neo4jUrl;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePathDown() {
        return filePathDown;
    }

    public void setFilePathDown(String filePathDown) {
        this.filePathDown = filePathDown;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getSystematic() {
        return systematic;
    }

    public void setSystematic(String systematic) {
        this.systematic = systematic;
    }

    public String getDisclose() {
        return disclose;
    }

    public void setDisclose(String disclose) {
        this.disclose = disclose;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

}
