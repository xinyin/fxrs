/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年04月07日 20:40:20
 *
 */

package com.yzj.jep.app.sou.model;

import com.yzj.jep.orm.entity.BasePO;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sou_insider")
public class Insider extends BasePO {

    public static final String TABLE_ALIAS = "Insider";
    public static final String ALIAS_IDNO = "idno";
    public static final String ALIAS_NAME = "员工姓名";
    public static final String ALIAS_EN_NAME = "员工英文姓名";
    public static final String ALIAS_ID_TYPE = "证件类别";
    public static final String ALIAS_ID_CARD = "证件号码";
    public static final String ALIAS_NATIONALITY = "国籍";
    public static final String ALIAS_ETHNIC = "名族";
    public static final String ALIAS_GENDER = "性别";
    public static final String ALIAS_EDUCATION = "学历";
    public static final String ALIAS_BIRTHDATE = "出生日期";
    public static final String ALIAS_DEPART = "所在部门";
    public static final String ALIAS_DUTY = "职务";
    public static final String ALIAS_ADDRESS = "家庭住址";
    public static final String ALIAS_MOBILE = "移动电话";
    public static final String ALIAS_MARITAL_STATUS = "婚姻状况";
    public static final String ALIAS_SP_NAME = "配偶姓名";
    public static final String ALIAS_SP_PHONE = "配偶联系电话";
    public static final String ALIAS_SP_MOBILE = "配偶移动电话";
    public static final String ALIAS_SP_COMPANY = "配偶工作单位";
    public static final String ALIAS_SP_DUTY = "配偶职务";
    public static final String ALIAS_SP_INV_ENT = "配偶投资企业";
    public static final String ALIAS_SP_INV_PROP = "配偶投资占比";
    public static final String ALIAS_OTHER_FA_REL = "家属关系";
    public static final String ALIAS_OTHER_FA_NAME = "其他家属姓名";
    public static final String ALIAS_OTHER_FA_PHONE = "其他家属联系电话";
    public static final String ALIAS_OTHER_FA_MOBILE = "其他家属移动电话";
    public static final String ALIAS_OTHER_FA_CO = "其他家属工作单位";
    public static final String ALIAS_OTHER_FA_DUTY = "其他家属职务";
    public static final String ALIAS_OTHER_FA_INV = "其他家属投资企业";
    public static final String ALIAS_OTHER_FA_INVP = "otherFaInvp";


    //可以直接使用: @Length(max=50,message="xxx长度不能大于50。")显示错误消息
    //columns START

    @Id
    @Column(name = "idno", nullable = false, length = 10)
    private Integer idno;

    @Column(name = "name", nullable = true, length = 20)
    private String name;

    @Column(name = "en_name", nullable = true, length = 25)
    private String enName;

    @Column(name = "id_type", nullable = true, length = 25)
    private String idType;

    @Column(name = "id_card", nullable = true, length = 25)
    private String idCard;

    @Column(name = "nationality", nullable = true, length = 25)
    private String nationality;

    @Column(name = "ethnic", nullable = true, length = 25)
    private String ethnic;

    @Column(name = "gender", nullable = true, length = 10)
    private Integer gender;

    @Column(name = "education", nullable = true, length = 25)
    private String education;

    @Column(name = "birthdate", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date birthdate;

    @Column(name = "depart", nullable = true, length = 25)
    private String depart;

    @Column(name = "duty", nullable = true, length = 25)
    private String duty;

    @Column(name = "address", nullable = true, length = 25)
    private String address;

    @Column(name = "mobile", nullable = true, length = 25)
    private String mobile;

    @Column(name = "marital_status", nullable = true, length = 25)
    private String maritalStatus;

    @Column(name = "sp_name", nullable = true, length = 25)
    private String spName;

    @Column(name = "sp_phone", nullable = true, length = 25)
    private String spPhone;

    @Column(name = "sp_mobile", nullable = true, length = 25)
    private String spMobile;

    @Column(name = "sp_company", nullable = true, length = 25)
    private String spCompany;

    @Column(name = "sp_duty", nullable = true, length = 25)
    private String spDuty;

    @Column(name = "sp_inv_ent", nullable = true, length = 25)
    private String spInvEnt;

    @Column(name = "sp_inv_prop", nullable = true, length = 25)
    private String spInvProp;

    @Column(name = "other_fa_rel", nullable = true, length = 25)
    private String otherFaRel;

    @Column(name = "other_fa_name", nullable = true, length = 25)
    private String otherFaName;

    @Column(name = "other_fa_phone", nullable = true, length = 25)
    private String otherFaPhone;

    @Column(name = "other_fa_mobile", nullable = true, length = 25)
    private String otherFaMobile;

    @Column(name = "other_fa_co", nullable = true, length = 25)
    private String otherFaCo;

    @Column(name = "other_fa_duty", nullable = true, length = 25)
    private String otherFaDuty;

    @Column(name = "other_fa_inv", nullable = true, length = 25)
    private String otherFaInv;

    @Column(name = "other_fa_invp", nullable = true, length = 25)
    private String otherFaInvp;
    //columns END


    public Integer getIdno() {
        return this.idno;
    }

    public void setIdno(Integer value) {
        this.idno = value;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }


    public String getEnName() {
        return this.enName;
    }

    public void setEnName(String value) {
        this.enName = value;
    }


    public String getIdType() {
        return this.idType;
    }

    public void setIdType(String value) {
        this.idType = value;
    }


    public String getIdCard() {
        return this.idCard;
    }

    public void setIdCard(String value) {
        this.idCard = value;
    }


    public String getNationality() {
        return this.nationality;
    }

    public void setNationality(String value) {
        this.nationality = value;
    }


    public String getEthnic() {
        return this.ethnic;
    }

    public void setEthnic(String value) {
        this.ethnic = value;
    }


    public Integer getGender() {
        return this.gender;
    }

    public void setGender(Integer value) {
        this.gender = value;
    }


    public String getEducation() {
        return this.education;
    }

    public void setEducation(String value) {
        this.education = value;
    }


    public Date getBirthdate() {
        return this.birthdate;
    }

    public void setBirthdate(Date value) {
        this.birthdate = value;
    }


    public String getDepart() {
        return this.depart;
    }

    public void setDepart(String value) {
        this.depart = value;
    }


    public String getDuty() {
        return this.duty;
    }

    public void setDuty(String value) {
        this.duty = value;
    }


    public String getAddress() {
        return this.address;
    }

    public void setAddress(String value) {
        this.address = value;
    }


    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String value) {
        this.mobile = value;
    }


    public String getMaritalStatus() {
        return this.maritalStatus;
    }

    public void setMaritalStatus(String value) {
        this.maritalStatus = value;
    }


    public String getSpName() {
        return this.spName;
    }

    public void setSpName(String value) {
        this.spName = value;
    }


    public String getSpPhone() {
        return this.spPhone;
    }

    public void setSpPhone(String value) {
        this.spPhone = value;
    }


    public String getSpMobile() {
        return this.spMobile;
    }

    public void setSpMobile(String value) {
        this.spMobile = value;
    }


    public String getSpCompany() {
        return this.spCompany;
    }

    public void setSpCompany(String value) {
        this.spCompany = value;
    }


    public String getSpDuty() {
        return this.spDuty;
    }

    public void setSpDuty(String value) {
        this.spDuty = value;
    }


    public String getSpInvEnt() {
        return this.spInvEnt;
    }

    public void setSpInvEnt(String value) {
        this.spInvEnt = value;
    }


    public String getSpInvProp() {
        return this.spInvProp;
    }

    public void setSpInvProp(String value) {
        this.spInvProp = value;
    }


    public String getOtherFaRel() {
        return this.otherFaRel;
    }

    public void setOtherFaRel(String value) {
        this.otherFaRel = value;
    }


    public String getOtherFaName() {
        return this.otherFaName;
    }

    public void setOtherFaName(String value) {
        this.otherFaName = value;
    }


    public String getOtherFaPhone() {
        return this.otherFaPhone;
    }

    public void setOtherFaPhone(String value) {
        this.otherFaPhone = value;
    }


    public String getOtherFaMobile() {
        return this.otherFaMobile;
    }

    public void setOtherFaMobile(String value) {
        this.otherFaMobile = value;
    }


    public String getOtherFaCo() {
        return this.otherFaCo;
    }

    public void setOtherFaCo(String value) {
        this.otherFaCo = value;
    }


    public String getOtherFaDuty() {
        return this.otherFaDuty;
    }

    public void setOtherFaDuty(String value) {
        this.otherFaDuty = value;
    }


    public String getOtherFaInv() {
        return this.otherFaInv;
    }

    public void setOtherFaInv(String value) {
        this.otherFaInv = value;
    }


    public String getOtherFaInvp() {
        return this.otherFaInvp;
    }

    public void setOtherFaInvp(String value) {
        this.otherFaInvp = value;
    }


    @Override
    public String toString() {
        return new StringBuilder("insider[\n")
                .append("idno = ").append(idno).append(", \n")
                .append("name = ").append(name).append(", \n")
                .append("enName = ").append(enName).append(", \n")
                .append("idType = ").append(idType).append(", \n")
                .append("idCard = ").append(idCard).append(", \n")
                .append("nationality = ").append(nationality).append(", \n")
                .append("ethnic = ").append(ethnic).append(", \n")
                .append("gender = ").append(gender).append(", \n")
                .append("education = ").append(education).append(", \n")
                .append("birthdate = ").append(birthdate).append(", \n")
                .append("depart = ").append(depart).append(", \n")
                .append("duty = ").append(duty).append(", \n")
                .append("address = ").append(address).append(", \n")
                .append("mobile = ").append(mobile).append(", \n")
                .append("maritalStatus = ").append(maritalStatus).append(", \n")
                .append("spName = ").append(spName).append(", \n")
                .append("spPhone = ").append(spPhone).append(", \n")
                .append("spMobile = ").append(spMobile).append(", \n")
                .append("spCompany = ").append(spCompany).append(", \n")
                .append("spDuty = ").append(spDuty).append(", \n")
                .append("spInvEnt = ").append(spInvEnt).append(", \n")
                .append("spInvProp = ").append(spInvProp).append(", \n")
                .append("otherFaRel = ").append(otherFaRel).append(", \n")
                .append("otherFaName = ").append(otherFaName).append(", \n")
                .append("otherFaPhone = ").append(otherFaPhone).append(", \n")
                .append("otherFaMobile = ").append(otherFaMobile).append(", \n")
                .append("otherFaCo = ").append(otherFaCo).append(", \n")
                .append("otherFaDuty = ").append(otherFaDuty).append(", \n")
                .append("otherFaInv = ").append(otherFaInv).append(", \n")
                .append("otherFaInvp = ").append(otherFaInvp).append(", \n")
                .append("]")
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getIdno())
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Insider == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Insider other = (Insider) obj;
        return new EqualsBuilder()
                .append(getIdno(), other.getIdno())
                .isEquals();
    }
}

