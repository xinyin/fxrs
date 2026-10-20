package com.yzj.jep.app.fil.model;

import java.io.Serializable;

public class RelTransactionBack implements Serializable {

    private String sourceSysCode;
    private String isBlock;


    public String getSourceSysCode() {
        return sourceSysCode;
    }

    public void setSourceSysCode(String sourceSysCode) {
        this.sourceSysCode = sourceSysCode;
    }

    public String getIsBlock() {
        return isBlock;
    }

    public void setIsBlock(String isBlock) {
        this.isBlock = isBlock;
    }
}
