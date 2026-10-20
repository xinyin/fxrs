package com.yzj.jep.app.sub.model;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.orm.entity.BasePO;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name = "mid_ydyglfzjyygljytjb_2")
public class MidYdyglfzjyygljytjb2 extends BasePO {
    /**
     * 流水号
     */
    @Id
    @Column(name = "SERIALNO", nullable = false, length = 60)
    private String serialno;

    /**
     * 原始数据批次号
     */
    @Column(name = "BATCHNO", nullable = false, length = 60)
    private String batchno;

    /**
     * 业务号
     */
    @Column(name = "BUSSNO", nullable = false, length = 600)
    private String bussno;

    /**
     * 申请日期
     */
    @Column(name = "MAKEDATE", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date makedate;

    /**
     * 标识日期
     */
    @Column(name = "BUSSDATE", nullable = true, length = 19)
    @Temporal(TemporalType.DATE)
    private Date bussdate;

    /**
     * 数据来源
     */
    @Column(name = "DATASRC", nullable = true, length = 2)
    private String datasrc;

    /**
     * 状态位标签
     */
    @Column(name = "OTHERSIGN", nullable = true, length = 6)
    private String othersign;

    /**
     * 备注说明
     */
    @Column(name = "REMARK", nullable = true, length = 150)
    private String remark;

    /**
     * 保险机构代码
     */
    @Column(name = "BXJGDM", nullable = true, length = 45)
    private String bxjgdm;

    /**
     * 保险机构名称
     */
    @Column(name = "BXJGMC", nullable = true, length = 600)
    private String bxjgmc;

    /**
     * 关联方类型
     */
    @Column(name = "GLFLX", nullable = true, length = 6)
    private String glflx;

    /**
     * 关联方名称
     */
    @Column(name = "GLFMC", nullable = true, length = 600)
    private String glfmc;

    /**
     * 关联方代码
     */
    @Column(name = "GLFDM", nullable = true, length = 600)
    private String glfdm;

    /**
     * 资金运用投资余额
     */
    @Column(name = "ZJYYTZYE", nullable = true, precision = 22, scale = 4)
    private BigDecimal zjyytzye;

    /**
     * 采集日期
     */
    @Column(name = "CJRQ", nullable = true, length = 10)
    @Temporal(TemporalType.DATE)
    private Date cjrq;

    /**
     * 监管辖区代码
     */
    @Column(name = "JGXQDM", nullable = true, length = 300)
    private String jgxqdm;

    @Transient
    private String makedateStr;

    @Transient
    private String bussdateStr;

    @Transient
    private String cjrqStr;

    public String getCjrqStr() {
        if (cjrq != null) {
            return DateUtil.format(this.cjrq, "yyyy-MM-dd");
        }
        return "";
    }

    public void setCjrqStr(String cjrqStr) {
        if (cjrqStr != null) {
            setCjrq(DateUtils.parseDate(cjrqStr));
        }
    }

    public String getMakedateStr() {
        if (makedate != null) {
            return DateUtil.format(this.makedate, "yyyy-MM-dd");
        }
        return "";
    }

    public void setMakedateStr(String makedateStr) {
        if (makedateStr != null) {
            setMakedate(DateUtils.parseDate(makedateStr));
        }
    }

    public String getBussdateStr() {
        if (bussdate != null) {
            return DateUtil.format(this.bussdate, "yyyy-MM-dd");
        }
        return "";
    }

    public void setBussdateStr(String bussdateStr) {
        if (bussdateStr != null) {
            setBussdate(DateUtils.parseDate(bussdateStr));
        }
    }

    public String getSerialno() {
        return serialno;
    }

    public void setSerialno(String serialno) {
        this.serialno = serialno;
    }

    public String getBatchno() {
        return batchno;
    }

    public void setBatchno(String batchno) {
        this.batchno = batchno;
    }

    public String getBussno() {
        return bussno;
    }

    public void setBussno(String bussno) {
        this.bussno = bussno;
    }

    public Date getMakedate() {
        return makedate;
    }

    public void setMakedate(Date makedate) {
        this.makedate = makedate;
    }

    public Date getBussdate() {
        return bussdate;
    }

    public void setBussdate(Date bussdate) {
        this.bussdate = bussdate;
    }

    public String getDatasrc() {
        return datasrc;
    }

    public void setDatasrc(String datasrc) {
        this.datasrc = datasrc;
    }

    public String getOthersign() {
        return othersign;
    }

    public void setOthersign(String othersign) {
        this.othersign = othersign;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBxjgdm() {
        return bxjgdm;
    }

    public void setBxjgdm(String bxjgdm) {
        this.bxjgdm = bxjgdm;
    }

    public String getBxjgmc() {
        return bxjgmc;
    }

    public void setBxjgmc(String bxjgmc) {
        this.bxjgmc = bxjgmc;
    }

    public String getGlflx() {
        return glflx;
    }

    public void setGlflx(String glflx) {
        this.glflx = glflx;
    }

    public String getGlfmc() {
        return glfmc;
    }

    public void setGlfmc(String glfmc) {
        this.glfmc = glfmc;
    }

    public String getGlfdm() {
        return glfdm;
    }

    public void setGlfdm(String glfdm) {
        this.glfdm = glfdm;
    }

    public BigDecimal getZjyytzye() {
        return zjyytzye;
    }

    public void setZjyytzye(BigDecimal zjyytzye) {
        this.zjyytzye = zjyytzye;
    }

    public Date getCjrq() {
        return cjrq;
    }

    public void setCjrq(Date cjrq) {
        this.cjrq = cjrq;
    }

    public String getJgxqdm() {
        return jgxqdm;
    }

    public void setJgxqdm(String jgxqdm) {
        this.jgxqdm = jgxqdm;
    }
}
