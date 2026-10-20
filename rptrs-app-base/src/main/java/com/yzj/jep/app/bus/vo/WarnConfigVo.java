/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月06日 10:02:38
 *
 */

package com.yzj.jep.app.bus.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


public class WarnConfigVo {


    private Integer pid;

    private String warningIndex;

    private Integer warnThreshold1l;

    private Integer warnThreshold2l;

    private Integer warnThreshold3l;

    private Integer swarnThreshold1l;
    private Integer swarnThreshold2l;
    private Integer swarnThreshold3l;


    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public String getWarningIndex() {
        return this.warningIndex;
    }

    public void setWarningIndex(String value) {
        this.warningIndex = value;
    }

    public Integer getWarnThreshold1l() {
        return warnThreshold1l;
    }

    public void setWarnThreshold1l(Integer warnThreshold1l) {
        this.warnThreshold1l = warnThreshold1l;
    }

    public Integer getWarnThreshold2l() {
        return warnThreshold2l;
    }

    public void setWarnThreshold2l(Integer warnThreshold2l) {
        this.warnThreshold2l = warnThreshold2l;
    }

    public Integer getWarnThreshold3l() {
        return warnThreshold3l;
    }

    public void setWarnThreshold3l(Integer warnThreshold3l) {
        this.warnThreshold3l = warnThreshold3l;
    }

    public Integer getSwarnThreshold1l() {
        return swarnThreshold1l;
    }

    public void setSwarnThreshold1l(Integer swarnThreshold1l) {
        this.swarnThreshold1l = swarnThreshold1l;
    }

    public Integer getSwarnThreshold2l() {
        return swarnThreshold2l;
    }

    public void setSwarnThreshold2l(Integer swarnThreshold2l) {
        this.swarnThreshold2l = swarnThreshold2l;
    }

    public Integer getSwarnThreshold3l() {
        return swarnThreshold3l;
    }

    public void setSwarnThreshold3l(Integer swarnThreshold3l) {
        this.swarnThreshold3l = swarnThreshold3l;
    }

    @Override
    public String toString() {
        return new StringBuilder("warnConfig[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("warningIndex = ").append(warningIndex).append(", \n")
                .append("warnThreshold11 = ").append(warnThreshold1l).append(", \n")
                .append("2lwarnThreshold = ").append(warnThreshold2l).append(", \n")
                .append("3lwarnThreshold = ").append(warnThreshold3l).append(", \n")
                .append("]")
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getPid())
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof WarnConfigVo == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        WarnConfigVo other = (WarnConfigVo) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

