package com.yzj.jep.app.bus.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class DocOutsideVo implements Serializable {
    private Integer opt;
    private String sysName;
    private String articleNumber;
    private String issueOrg;
    private String isVal;
    private Date beginDate;
    private Date endDate;
    private List<Integer> docTableSelectItem;//doc文件选择

    public Integer getOpt() {
        return opt;
    }

    public void setOpt(Integer opt) {
        this.opt = opt;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public String getArticleNumber() {
        return articleNumber;
    }

    public void setArticleNumber(String articleNumber) {
        this.articleNumber = articleNumber;
    }

    public String getIssueOrg() {
        return issueOrg;
    }

    public void setIssueOrg(String issueOrg) {
        this.issueOrg = issueOrg;
    }

    public String getIsVal() {
        return isVal;
    }

    public void setIsVal(String isVal) {
        this.isVal = isVal;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<Integer> getDocTableSelectItem() {
        return docTableSelectItem;
    }

    public void setDocTableSelectItem(List<Integer> docTableSelectItem) {
        this.docTableSelectItem = docTableSelectItem;
    }

    @Override
    public String toString() {
        return "DocOutsideVo{" +
                "opt=" + opt +
                ", sysName='" + sysName + '\'' +
                ", articleNumber='" + articleNumber + '\'' +
                ", issueOrg='" + issueOrg + '\'' +
                ", isVal='" + isVal + '\'' +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", docTableSelectItem=" + docTableSelectItem +
                '}';
    }
}
