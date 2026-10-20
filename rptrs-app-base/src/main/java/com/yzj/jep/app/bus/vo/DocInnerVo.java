package com.yzj.jep.app.bus.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class DocInnerVo implements Serializable {
    private Integer opt;
    private String sysName;
    private String articleNumber;
    private String isVal;
    private String dispatchDep;
    private Date beginDate;
    private Date endDate;
    private List<Integer> docTableSelectItem;
    private List<Integer> docTable1SelectItem;

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

    public String getIsVal() {
        return isVal;
    }

    public void setIsVal(String isVal) {
        this.isVal = isVal;
    }

    public String getDispatchDep() {
        return dispatchDep;
    }

    public void setDispatchDep(String dispatchDep) {
        this.dispatchDep = dispatchDep;
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

    public List<Integer> getDocTable1SelectItem() {
        return docTable1SelectItem;
    }

    public void setDocTable1SelectItem(List<Integer> docTable1SelectItem) {
        this.docTable1SelectItem = docTable1SelectItem;
    }

    @Override
    public String toString() {
        return "DocInnerVo{" +
                "opt=" + opt +
                ", sysName='" + sysName + '\'' +
                ", articleNumber='" + articleNumber + '\'' +
                ", isVal='" + isVal + '\'' +
                ", dispatchDep='" + dispatchDep + '\'' +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", docTableSelectItem=" + docTableSelectItem +
                ", docTable1SelectItem=" + docTable1SelectItem +
                '}';
    }
}
