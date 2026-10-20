package com.yzj.jep.app.sub.model;

import com.yzj.jep.orm.entity.BasePO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;


@Entity
@Table(name = "sub_person_view")
public class PersonView extends BasePO {
    public static final String TABLE_ALIAS = "Person";
    public static final String ALIAS_PID = "pid";
    public static final String ALIAS_NAME = "姓名";
    public static final String ALIAS_DOMESTIC_OR_FOREIGN = "境内(外)标志";
    public static final String ALIAS_IDENTITY_TYPE = "证件类型";
    public static final String ALIAS_IDENTITY_NO = "证件号码";
    public static final String ALIAS_SUB_SEQ = "流水号";

    @Id
    @Column(name = "pid", nullable = false, length = 10)
    private Integer pid;

    @Column(name = "name_", nullable = false, length = 10)
    private String name;

    @Column(name = "domestic_or_foreign", nullable = false, length = 2)
    private String domesticOrForeign;

    @Column(name = "identity_type", nullable = false, length = 2)
    private String identityType;

    @Column(name = "identity_no", nullable = false, length = 50)
    private String identityNo;

    @Column(name = "SUB_SEQ",nullable = false, length = 18)
    private String subSeq;
    @Column(name="prot_pid")
    private Integer protPid;

    public Integer getProtPid() {
        return protPid;
    }

    public void setProtPid(Integer protPid) {
        this.protPid = protPid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDomesticOrForeign() {
        return domesticOrForeign;
    }

    public void setDomesticOrForeign(String domesticOrForeign) {
        this.domesticOrForeign = domesticOrForeign;
    }

    public String getIdentityType() {
        return identityType;
    }

    public void setIdentityType(String identityType) {
        this.identityType = identityType;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public String getSubSeq() {
        return subSeq;
    }

    public void setSubSeq(String subSeq) {
        this.subSeq = subSeq;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PersonView that = (PersonView) o;
        return Objects.equals(pid, that.pid) &&
                Objects.equals(name, that.name) &&
                Objects.equals(domesticOrForeign, that.domesticOrForeign) &&
                Objects.equals(identityType, that.identityType) &&
                Objects.equals(identityNo, that.identityNo) &&
                Objects.equals(subSeq, that.subSeq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pid, name, domesticOrForeign, identityType, identityNo, subSeq);
    }

    @Override
    public String toString() {
        return "PersonView{" +
                "pid=" + pid +
                ", name='" + name + '\'' +
                ", domesticOrForeign='" + domesticOrForeign + '\'' +
                ", identityType='" + identityType + '\'' +
                ", identityNo='" + identityNo + '\'' +
                ", subSeq='" + subSeq + '\'' +
                '}';
    }
}
