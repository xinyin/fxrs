/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月06日 20:12:29
 *
 */

package com.yzj.jep.app.bus.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.yzj.jep.orm.entity.BasePO;

@Entity
@Table(name = "bus_gd_file_data")
public class GdFileData extends BasePO {

    public static final String TABLE_ALIAS = "导出文件数据表";
    public static final String ALIAS_PID = "主键";
    public static final String ALIAS_SHAREHOLDERS_CODE = "股东代码";
    public static final String ALIAS_NAME = "股东名称";
    public static final String ALIAS_SHAREHOLDERS_TYPE = "股东类型";
    public static final String ALIAS_DOMESTIC_OR_FOREIGN = "境内境外";
    public static final String ALIAS_IDENTIFY_TYPE = "证件类型";
    public static final String ALIAS_IDENTIFY_NUMBER = "证件号码";
    public static final String ALIAS_DJ_STATUS = "账户冻结";
    public static final String ALIAS_HOLE_SHARE_AMOUNT = "持股数量";
    public static final String ALIAS_SHAREHOLDING_PROPORTION = "持股占比";
    public static final String ALIAS_STATUS = "审批状态";
    public static final String ALIAS_STATUS_COMMENT = "状态说明";
    public static final String ALIAS_BATCH_NUMBER = "数据批次";
    public static final String ALIAS_STATE_OWNED = "是否国有企业";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "shareholders_code", nullable = true, length = 50)
    private String shareholdersCode;

    @Column(name = "name_", nullable = true, length = 100)
    private String name_;

    @Column(name = "shareholders_type", nullable = true, length = 10)
    private String shareholdersType;

    @Column(name = "domestic_or_foreign", nullable = true, length = 50)
    private String domesticOrForeign;

    @Column(name = "identify_type", nullable = true, length = 10)
    private String identifyType;

    @Column(name = "identify_number", nullable = true, length = 50)
    private String identifyNumber;

    @Column(name = "dj_status", nullable = true, length = 20)
    private String djStatus;

    @Column(name = "hole_share_amount", nullable = true, length = 50)
    private String holeShareAmount;

    @Column(name = "shareholding_proportion", nullable = true, length = 10)
    private String shareholdingProportion;

    @Column(name = "status", nullable = true, length = 10)
    private String status;

    @Column(name = "status_comment", nullable = true, length = 500)
    private String statusComment;

    @Column(name = "batch_number", nullable = true, length = 20)
    private String batchNumber;

    @Column(name = "state_owned", nullable = true, length = 10)
    private String stateOwned;
    //columns END


    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public String getShareholdersCode() {
        return this.shareholdersCode;
    }

    public void setShareholdersCode(String value) {
        this.shareholdersCode = value;
    }


    public String getName() {
        return this.name_;
    }

    public void setName(String value) {
        this.name_ = value;
    }


    public String getShareholdersType() {
        return this.shareholdersType;
    }

    public void setShareholdersType(String value) {
        this.shareholdersType = value;
    }


    public String getDomesticOrForeign() {
        return this.domesticOrForeign;
    }

    public void setDomesticOrForeign(String value) {
        this.domesticOrForeign = value;
    }


    public String getIdentifyType() {
        return this.identifyType;
    }

    public void setIdentifyType(String value) {
        this.identifyType = value;
    }


    public String getIdentifyNumber() {
        return this.identifyNumber;
    }

    public void setIdentifyNumber(String value) {
        this.identifyNumber = value;
    }


    public String getDjStatus() {
        return this.djStatus;
    }

    public void setDjStatus(String value) {
        this.djStatus = value;
    }


    public String getHoleShareAmount() {
        return this.holeShareAmount;
    }

    public void setHoleShareAmount(String value) {
        this.holeShareAmount = value;
    }


    public String getShareholdingProportion() {
        return this.shareholdingProportion;
    }

    public void setShareholdingProportion(String value) {
        this.shareholdingProportion = value;
    }


    public String getStatus() {
        return this.status;
    }

    public void setStatus(String value) {
        this.status = value;
    }


    public String getStatusComment() {
        return this.statusComment;
    }

    public void setStatusComment(String value) {
        this.statusComment = value;
    }


    public String getBatchNumber() {
        return this.batchNumber;
    }

    public void setBatchNumber(String value) {
        this.batchNumber = value;
    }


    public String getStateOwned() {
        return this.stateOwned;
    }

    public void setStateOwned(String value) {
        this.stateOwned = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("gdFileData[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("shareholdersCode = ").append(shareholdersCode).append(", \n")
                .append("name = ").append(name_).append(", \n")
                .append("shareholdersType = ").append(shareholdersType).append(", \n")
                .append("domesticOrForeign = ").append(domesticOrForeign).append(", \n")
                .append("identifyType = ").append(identifyType).append(", \n")
                .append("identifyNumber = ").append(identifyNumber).append(", \n")
                .append("djStatus = ").append(djStatus).append(", \n")
                .append("holeShareAmount = ").append(holeShareAmount).append(", \n")
                .append("shareholdingProportion = ").append(shareholdingProportion).append(", \n")
                .append("status = ").append(status).append(", \n")
                .append("statusComment = ").append(statusComment).append(", \n")
                .append("batchNumber = ").append(batchNumber).append(", \n")
                .append("stateOwned = ").append(stateOwned).append(", \n")
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
        if (obj instanceof GdFileData == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        GdFileData other = (GdFileData) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

