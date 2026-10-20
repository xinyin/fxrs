package com.yzj.jep.app.bus.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yzj.jep.orm.entity.BasePO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bus_chk_log")
public class Chklog extends BasePO {
    public static final String TABLE_ID = "id";
    public static final String ALIAS_SER_NUM = "流水号";
    public static final String ALIAS_RULE_ID = "规则id";
    public static final String ALIAS_BUS_CODE = "业务结果";
    public static final String ALIAS_TRA_CODE = "交易结果";
    public static final String ALIAS_CHANNEL_CODE = "渠道编码";
    public static final String ALIAS_TRA_TYPE = "交易类型";
    public static final String ALIAS_TYPE = "预警分类";

    public static final String ALIAS_LEVEL = "预警级别";
    public static final String ALIAS_IS_INSIDER = "是否内部人";

    @Id
    @Column(name = "id", nullable = false, length = 11)
    private Integer id;

    @Column(name = "ser_num", nullable = true, length = 64)
    private String serNum;

    @Column(name = "rule_id", nullable = true, length = 64)
    private String ruleId;

    @Column(name = "bus_code", nullable = true, length = 64)
    private String busCode;

    @Column(name = "tra_code", nullable = true, length = 64)
    private String traCode;

    @Column(name = "channel_code", nullable = true, length = 64)
    private String channelCode;

    @Column(name = "tra_type", nullable = true, length = 64)
    private String traType;

    @Column(name = "type", nullable = true, length = 64)
    /* @JsonSerialize(using = .class)*/
    private String type;

    @Column(name = "log_level", nullable = true, length = 64)
    /* @JsonSerialize(using = .class)*/
    private String level;
    @Column(name = "is_insider", nullable = true, length = 64)
    /*@JsonSerialize(using = .class)*/
    private String is_insider;

    public static String getTableId() {
        return TABLE_ID;
    }

    public static String getAliasSerNum() {
        return ALIAS_SER_NUM;
    }

    public static String getAliasRuleId() {
        return ALIAS_RULE_ID;
    }

    public static String getAliasBusCode() {
        return ALIAS_BUS_CODE;
    }

    public static String getAliasTraCode() {
        return ALIAS_TRA_CODE;
    }

    public static String getAliasChannelCode() {
        return ALIAS_CHANNEL_CODE;
    }

    public static String getAliasTraType() {
        return ALIAS_TRA_TYPE;
    }

    public static String getAliasType() {
        return ALIAS_TYPE;
    }

    public static String getAliasLevel() {
        return ALIAS_LEVEL;
    }

    public static String getAliasIsInsider() {
        return ALIAS_IS_INSIDER;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSerNum() {
        return serNum;
    }

    public void setSerNum(String serNum) {
        this.serNum = serNum;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getBusCode() {
        return busCode;
    }

    public void setBusCode(String busCode) {
        this.busCode = busCode;
    }

    public String getTraCode() {
        return traCode;
    }

    public void setTraCode(String traCode) {
        this.traCode = traCode;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getTraType() {
        return traType;
    }

    public void setTraType(String traType) {
        this.traType = traType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getIs_insider() {
        return is_insider;
    }

    public void setIs_insider(String is_insider) {
        this.is_insider = is_insider;
    }

    @Override
    public String toString() {
        return "Chklog{" +
                "id=" + id +
                ", serNum='" + serNum + '\'' +
                ", ruleId='" + ruleId + '\'' +
                ", busCode='" + busCode + '\'' +
                ", traCode='" + traCode + '\'' +
                ", channelCode='" + channelCode + '\'' +
                ", traType='" + traType + '\'' +
                ", type='" + type + '\'' +
                ", level='" + level + '\'' +
                ", is_insider='" + is_insider + '\'' +
                '}';
    }
}
