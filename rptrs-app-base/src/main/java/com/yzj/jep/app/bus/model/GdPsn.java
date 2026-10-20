/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年04月01日 11:59:13
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
@Table(name = "bus_gd_psn")
public class GdPsn extends BasePO {

    public static final String TABLE_ALIAS = "GdPsn";
    public static final String ALIAS_PID = "主键";
    public static final String ALIAS_NAME = "姓名";
    public static final String ALIAS_IDENTIFY_TYPE = "证件类型";
    public static final String ALIAS_IDENTIFY_NUMBER = "证件号码";
    public static final String ALIAS_SEX = "性别";
    public static final String ALIAS_RACE = "民族";
    public static final String ALIAS_DOMESTIC_OR_FOREIGN = "境内境外";
    public static final String ALIAS_MARITAL_STATUS = "婚姻状况";
    public static final String ALIAS_MOBILE = "移动电话";
    public static final String ALIAS_EMAIL = "邮箱";
    public static final String ALIAS_BIRTHDATE = "出生日期";
    public static final String ALIAS_SHAREHOLDERS_CODE = "股东代码（账户号）";
    public static final String ALIAS_OPEN_BANK = "交易账户开户行";
    public static final String ALIAS_TRADE_ACCOUNT = "交易账户账号";
    public static final String ALIAS_IN_TIME = "入股时间";
    public static final String ALIAS_IN_PRICE = "入股价格";
    public static final String ALIAS_HOLE_SHARE_AMOUNT = "持股数量（股）";
    public static final String ALIAS_SHAREHOLDING_PROPORTION = "持股占比";
    public static final String ALIAS_FROZEN_SHARE_AMOUNT = "总冻结股数（股）";
    public static final String ALIAS_PLEDGED_SHARE_AMOUNT = "总质押股数（股）";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "name", nullable = true, length = 100)
    private String name;

    @Column(name = "identify_type", nullable = true, length = 10)
    private String identifyType;

    @Column(name = "identify_number", nullable = true, length = 20)
    private String identifyNumber;

    @Column(name = "sex", nullable = true, length = 100)
    private String sex;

    @Column(name = "race", nullable = true, length = 20)
    private String race;

    @Column(name = "domestic_or_foreign", nullable = true, length = 10)
    private String domesticOrForeign;

    @Column(name = "marital_status", nullable = true, length = 32)
    private String maritalStatus;

    @Column(name = "mobile", nullable = true, length = 11)
    private String mobile;

    @Column(name = "email", nullable = true, length = 32)
    private String email;

    @Column(name = "birthdate", nullable = true, length = 11)
    private String birthdate;

    @Column(name = "shareholders_code", nullable = true, length = 100)
    private String shareholdersCode;

    @Column(name = "open_bank", nullable = true, length = 100)
    private String openBank;

    @Column(name = "trade_account", nullable = true, length = 50)
    private String tradeAccount;

    @Column(name = "in_time", nullable = true, length = 11)
    private String inTime;

    @Column(name = "in_price", nullable = true, length = 20)
    private String inPrice;

    @Column(name = "hole_share_amount", nullable = true, length = 50)
    private String holeShareAmount;

    @Column(name = "shareholding_proportion", nullable = true, length = 10)
    private String shareholdingProportion;

    @Column(name = "frozen_share_amount", nullable = true, length = 20)
    private String frozenShareAmount;

    @Column(name = "pledged_share_amount", nullable = true, length = 20)
    private String pledgedShareAmount;
    //columns END


    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer value) {
        this.pid = value;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
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


    public String getSex() {
        return this.sex;
    }

    public void setSex(String value) {
        this.sex = value;
    }


    public String getRace() {
        return this.race;
    }

    public void setRace(String value) {
        this.race = value;
    }


    public String getDomesticOrForeign() {
        return this.domesticOrForeign;
    }

    public void setDomesticOrForeign(String value) {
        this.domesticOrForeign = value;
    }


    public String getMaritalStatus() {
        return this.maritalStatus;
    }

    public void setMaritalStatus(String value) {
        this.maritalStatus = value;
    }


    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String value) {
        this.mobile = value;
    }


    public String getEmail() {
        return this.email;
    }

    public void setEmail(String value) {
        this.email = value;
    }


    public String getBirthdate() {
        return this.birthdate;
    }

    public void setBirthdate(String value) {
        this.birthdate = value;
    }


    public String getShareholdersCode() {
        return this.shareholdersCode;
    }

    public void setShareholdersCode(String value) {
        this.shareholdersCode = value;
    }


    public String getOpenBank() {
        return this.openBank;
    }

    public void setOpenBank(String value) {
        this.openBank = value;
    }


    public String getTradeAccount() {
        return this.tradeAccount;
    }

    public void setTradeAccount(String value) {
        this.tradeAccount = value;
    }


    public String getInTime() {
        return this.inTime;
    }

    public void setInTime(String value) {
        this.inTime = value;
    }


    public String getInPrice() {
        return this.inPrice;
    }

    public void setInPrice(String value) {
        this.inPrice = value;
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


    public String getFrozenShareAmount() {
        return this.frozenShareAmount;
    }

    public void setFrozenShareAmount(String value) {
        this.frozenShareAmount = value;
    }


    public String getPledgedShareAmount() {
        return this.pledgedShareAmount;
    }

    public void setPledgedShareAmount(String value) {
        this.pledgedShareAmount = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("gdPsn[\n")
                .append("pid = ").append(pid).append(", \n")
                .append("name = ").append(name).append(", \n")
                .append("identifyType = ").append(identifyType).append(", \n")
                .append("identifyNumber = ").append(identifyNumber).append(", \n")
                .append("sex = ").append(sex).append(", \n")
                .append("race = ").append(race).append(", \n")
                .append("domesticOrForeign = ").append(domesticOrForeign).append(", \n")
                .append("maritalStatus = ").append(maritalStatus).append(", \n")
                .append("mobile = ").append(mobile).append(", \n")
                .append("email = ").append(email).append(", \n")
                .append("birthdate = ").append(birthdate).append(", \n")
                .append("shareholdersCode = ").append(shareholdersCode).append(", \n")
                .append("openBank = ").append(openBank).append(", \n")
                .append("tradeAccount = ").append(tradeAccount).append(", \n")
                .append("inTime = ").append(inTime).append(", \n")
                .append("inPrice = ").append(inPrice).append(", \n")
                .append("holeShareAmount = ").append(holeShareAmount).append(", \n")
                .append("shareholdingProportion = ").append(shareholdingProportion).append(", \n")
                .append("frozenShareAmount = ").append(frozenShareAmount).append(", \n")
                .append("pledgedShareAmount = ").append(pledgedShareAmount).append(", \n")
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
        if (obj instanceof GdPsn == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        GdPsn other = (GdPsn) obj;
        return new EqualsBuilder()
                .append(getPid(), other.getPid())
                .isEquals();
    }
}

