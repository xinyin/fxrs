package com.yzj.jep.app.sub.model;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.orm.entity.BasePO;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name = "mid_zjyygljytjb_2")
public class MidZjyygljytjb2 extends BasePO {
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
     * 流动性资产投资余额
     */
    @Column(name = "LDXZCTZYE", nullable = true, precision = 22, scale = 4)
    private BigDecimal ldxzctzye;

    /**
     * 固定收益类资产投资余额
     */
    @Column(name = "GDSYLZCTZYE", nullable = true, precision = 22, scale = 4)
    private BigDecimal gdsylzctzye;

    /**
     * 权益类资产投资余额
     */
    @Column(name = "QYLZCTZYE", nullable = true, precision = 22, scale = 4)
    private BigDecimal qylzctzye;

    /**
     * 不动产类资产投资余额
     */
    @Column(name = "BDCLZCTZYE", nullable = true, precision = 22, scale = 4)
    private BigDecimal bdclzctzye;

    /**
     * 保险资产管理产品投资余额
     */
    @Column(name = "BXZCGLCPTZYE", nullable = true, precision = 22, scale = 4)
    private BigDecimal bxzcglcptzye;

    /**
     * 商业银行理财产品投资余额
     */
    @Column(name = "SYYHLCCPTZYE", nullable = true, precision = 22, scale = 4)
    private BigDecimal syyhlccptzye;

    /**
     * 银行业金融机构信贷资产支持证券投资余额
     */
    @Column(name = "YHYJRJGXDZCZCZQTZYE", nullable = true, precision = 22, scale = 4)
    private BigDecimal yhyjrjgxdzczczqtzye;

    /**
     * 信托公司集合资金信托计划投资余额
     */
    @Column(name = "XTGSJHJJXTJHTZYE", nullable = true, precision = 22, scale = 4)
    private BigDecimal xtgsjhjjxtjhtzye;

    /**
     * 证券公司专项资产管理计划投资余额
     */
    @Column(name = "ZQGSZXZCGLJHTZYE", nullable = true, precision = 22, scale = 4)
    private BigDecimal zqgszxzcgljhtzye;

    /**
     * 保险资管公司发行资产支持计划投资余额
     */
    @Column(name = "BXZGGSFXZCZCJHTZYE", nullable = true, precision = 22, scale = 4)
    private BigDecimal bxzggsfxzczcjhtzye;

    /**
     * 其他金融资产投资余额
     */
    @Column(name = "QTJRZCTZYE", nullable = true, precision = 22, scale = 4)
    private BigDecimal qtjrzctzye;

    /**
     * 全部投资余额
     */
    @Column(name = "QBTZYE", nullable = true, precision = 22, scale = 4)
    private BigDecimal qbtzye;

    /**
     * 境外投资余额
     */
    @Column(name = "JWTZYE", nullable = true, precision = 22, scale = 4)
    private BigDecimal jwtzye;

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

    public BigDecimal getLdxzctzye() {
        return ldxzctzye;
    }

    public void setLdxzctzye(BigDecimal ldxzctzye) {
        this.ldxzctzye = ldxzctzye;
    }

    public BigDecimal getGdsylzctzye() {
        return gdsylzctzye;
    }

    public void setGdsylzctzye(BigDecimal gdsylzctzye) {
        this.gdsylzctzye = gdsylzctzye;
    }

    public BigDecimal getQylzctzye() {
        return qylzctzye;
    }

    public void setQylzctzye(BigDecimal qylzctzye) {
        this.qylzctzye = qylzctzye;
    }

    public BigDecimal getBdclzctzye() {
        return bdclzctzye;
    }

    public void setBdclzctzye(BigDecimal bdclzctzye) {
        this.bdclzctzye = bdclzctzye;
    }

    public BigDecimal getBxzcglcptzye() {
        return bxzcglcptzye;
    }

    public void setBxzcglcptzye(BigDecimal bxzcglcptzye) {
        this.bxzcglcptzye = bxzcglcptzye;
    }

    public BigDecimal getSyyhlccptzye() {
        return syyhlccptzye;
    }

    public void setSyyhlccptzye(BigDecimal syyhlccptzye) {
        this.syyhlccptzye = syyhlccptzye;
    }

    public BigDecimal getYhyjrjgxdzczczqtzye() {
        return yhyjrjgxdzczczqtzye;
    }

    public void setYhyjrjgxdzczczqtzye(BigDecimal yhyjrjgxdzczczqtzye) {
        this.yhyjrjgxdzczczqtzye = yhyjrjgxdzczczqtzye;
    }

    public BigDecimal getXtgsjhjjxtjhtzye() {
        return xtgsjhjjxtjhtzye;
    }

    public void setXtgsjhjjxtjhtzye(BigDecimal xtgsjhjjxtjhtzye) {
        this.xtgsjhjjxtjhtzye = xtgsjhjjxtjhtzye;
    }

    public BigDecimal getZqgszxzcgljhtzye() {
        return zqgszxzcgljhtzye;
    }

    public void setZqgszxzcgljhtzye(BigDecimal zqgszxzcgljhtzye) {
        this.zqgszxzcgljhtzye = zqgszxzcgljhtzye;
    }

    public BigDecimal getBxzggsfxzczcjhtzye() {
        return bxzggsfxzczcjhtzye;
    }

    public void setBxzggsfxzczcjhtzye(BigDecimal bxzggsfxzczcjhtzye) {
        this.bxzggsfxzczcjhtzye = bxzggsfxzczcjhtzye;
    }

    public BigDecimal getQtjrzctzye() {
        return qtjrzctzye;
    }

    public void setQtjrzctzye(BigDecimal qtjrzctzye) {
        this.qtjrzctzye = qtjrzctzye;
    }

    public BigDecimal getQbtzye() {
        return qbtzye;
    }

    public void setQbtzye(BigDecimal qbtzye) {
        this.qbtzye = qbtzye;
    }

    public BigDecimal getJwtzye() {
        return jwtzye;
    }

    public void setJwtzye(BigDecimal jwtzye) {
        this.jwtzye = jwtzye;
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
