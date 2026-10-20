package com.yzj.jep.app.ins.model;

import java.io.Serializable;

/**
 * 失败数据集合
 */
public class FailTransList implements Serializable {

    //关联交易唯一编码
    private String sourceSysCode;

    public String getSourceSysCode() {
        return sourceSysCode;
    }

    public void setSourceSysCode(String sourceSysCode) {
        this.sourceSysCode = sourceSysCode;
    }

    @Override
    public String toString() {
        return "FailTransList{" +
                "sourceSysCode='" + sourceSysCode + '\'' +
                '}';
    }
}
