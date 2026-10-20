package com.yzj.jep.app.bus.model;

import java.io.Serializable;
import java.util.List;

public class GdTraZrVo implements Serializable {
    private Integer pid;
    private String fromShareholdersCode;
    private String fromShareholdersName;
    private String operationAmount;
    private String operationPrice;
    private String afterFromAmount;
    private String afterFromProportion;
    private String beforeFromAmount;
    private String beforeFromProportion;
    private String afterToAmount;
    private String afterToProportion;
    private String beforeToAmount;
    private String beforeToProportion;
    private String toShareholdersCode;
    private String toShareholdersName;
    private String opt;
    private List<Integer> docTableSelectItem;

    public List<Integer> getDocTableSelectItem() {
        return docTableSelectItem;
    }

    public void setDocTableSelectItem(List<Integer> docTableSelectItem) {
        this.docTableSelectItem = docTableSelectItem;
    }

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }

    public String getFromShareholdersName() {
        return fromShareholdersName;
    }

    public void setFromShareholdersName(String fromShareholdersName) {
        this.fromShareholdersName = fromShareholdersName;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getFromShareholdersCode() {
        return fromShareholdersCode;
    }

    public void setFromShareholdersCode(String fromShareholdersCode) {
        this.fromShareholdersCode = fromShareholdersCode;
    }

    public String getOperationAmount() {
        return operationAmount;
    }

    public void setOperationAmount(String operationAmount) {
        this.operationAmount = operationAmount;
    }

    public String getOperationPrice() {
        return operationPrice;
    }

    public void setOperationPrice(String operationPrice) {
        this.operationPrice = operationPrice;
    }

    public String getAfterFromAmount() {
        return afterFromAmount;
    }

    public void setAfterFromAmount(String afterFromAmount) {
        this.afterFromAmount = afterFromAmount;
    }

    public String getAfterFromProportion() {
        return afterFromProportion;
    }

    public void setAfterFromProportion(String afterFromProportion) {
        this.afterFromProportion = afterFromProportion;
    }

    public String getBeforeFromAmount() {
        return beforeFromAmount;
    }

    public void setBeforeFromAmount(String beforeFromAmount) {
        this.beforeFromAmount = beforeFromAmount;
    }

    public String getBeforeFromProportion() {
        return beforeFromProportion;
    }

    public void setBeforeFromProportion(String beforeFromProportion) {
        this.beforeFromProportion = beforeFromProportion;
    }

    public String getAfterToAmount() {
        return afterToAmount;
    }

    public void setAfterToAmount(String afterToAmount) {
        this.afterToAmount = afterToAmount;
    }

    public String getAfterToProportion() {
        return afterToProportion;
    }

    public void setAfterToProportion(String afterToProportion) {
        this.afterToProportion = afterToProportion;
    }

    public String getBeforeToAmount() {
        return beforeToAmount;
    }

    public void setBeforeToAmount(String beforeToAmount) {
        this.beforeToAmount = beforeToAmount;
    }

    public String getBeforeToProportion() {
        return beforeToProportion;
    }

    public void setBeforeToProportion(String beforeToProportion) {
        this.beforeToProportion = beforeToProportion;
    }

    public String getToShareholdersCode() {
        return toShareholdersCode;
    }

    public void setToShareholdersCode(String toShareholdersCode) {
        this.toShareholdersCode = toShareholdersCode;
    }

    public String getToShareholdersName() {
        return toShareholdersName;
    }

    public void setToShareholdersName(String toShareholdersName) {
        this.toShareholdersName = toShareholdersName;
    }
}
