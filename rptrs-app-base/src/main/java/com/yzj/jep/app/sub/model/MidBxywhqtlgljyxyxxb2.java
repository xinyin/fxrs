package com.yzj.jep.app.sub.model;

import cn.hutool.core.date.DateUtil;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.orm.entity.BasePO;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @TableName mid_bxywhqtlgljyxyxxb_2
 */
@Entity
@Table(name = "mid_bxywhqtlgljyxyxxb_2")
public class MidBxywhqtlgljyxyxxb2 extends BasePO {
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
     *
     */
    @Column(name = "BUSSNO", nullable = false, length = 600)
    private String bussno;

    /**
     * 申请日期
     */
    @Column(name = "MAKEDATE", nullable = true, length = 11)
    private Date makedate;

    /**
     * 标识日期
     */
    @Column(name = "BUSSDATE", nullable = true, length = 11)
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
     * 业务类型
     */
    @Column(name = "YWLX", nullable = true, length = 150)
    private String ywlx;

    /**
     * 合同编号
     */
    @Column(name = "HTBH", nullable = true, length = 600)
    private String htbh;

    /**
     * 合同状态
     */
    @Column(name = "HTZT", nullable = true, length = 30)
    private String htzt;

    /**
     * 保单号
     */
    @Column(name = "BDH", nullable = true, length = 600)
    private String bdh;

    /**
     * 团个性质
     */
    @Column(name = "TGXZ", nullable = true, length = 30)
    private String tgxz;

    /**
     * 合同签订日期
     */
    @Column(name = "HTQDRQ", nullable = true, length = 7)
    private Date htqdrq;

    /**
     * 业务起始日期
     */
    @Column(name = "YWQSRQ", nullable = true, length = 7)
    private Date ywqsrq;

    /**
     * 业务结束日期
     */
    @Column(name = "YWJSRQ", nullable = true, length = 7)
    private Date ywjsrq;

    /**
     * 货币代码
     */
    @Column(name = "HBDM", nullable = true, length = 9)
    private String hbdm;

    /**
     * 交易金额（原币）
     */
    @Column(name = "JYJEYB", nullable = true, length = 22)
    private BigDecimal jyjeyb;

    /**
     * 交易金额（本位币）
     */
    @Column(name = "JYJEBWB", nullable = true, length = 22)
    private BigDecimal jyjebwb;

    /**
     * 交易价格
     */
    @Column(name = "JYJG", nullable = true, length = 3000)
    private String jyjg;

    /**
     * 定价政策
     */
    @Column(name = "DJZC", nullable = true, length = 4000)
    private String djzc;

    /**
     * 交易概述
     */
    @Column(name = "JYGS", nullable = true, length = 4000)
    private String jygs;

    /**
     * 交易标的
     */
    @Column(name = "JYBD", nullable = true, length = 4000)
    private String jybd;

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
     * 单笔关联交易类型
     */
    @Column(name = "DBGLJYLX", nullable = true, length = 150)
    private String dbgljylx;

    /**
     * 达到重大关联交易的情形
     */
    @Column(name = "DDZDGLJYDQX", nullable = true, length = 30)
    private String ddzdgljydqx;

    /**
     * 其他说明事项
     */
    @Column(name = "QTSMSX", nullable = true, length = 4000)
    private String qtsmsx;

    /**
     * 统一交易协议标志
     */
    @Column(name = "TYJYXYBZ", nullable = true, length = 3)
    private Long tyjyxybz;

    /**
     * 补充协议标志
     */
    @Column(name = "BCXYBZ", nullable = true, length = 3)
    private Long bcxybz;

    /**
     * 主合同编号
     */
    @Column(name = "ZHTBH", nullable = true, length = 600)
    private String zhtbh;

    /**
     * 采集日期
     */
    @Column(name = "CJRQ", nullable = true, length = 7)
    private Date cjrq;

    /**
     * 监管辖区代码
     */
    @Column(name = "JGXQDM", nullable = true, length = 300)
    private String jgxqdm;

    private static final long serialVersionUID = 1L;


    @Transient
    private String makedateStr;

    @Transient
    private String bussdateStr;

    @Transient
    private String cjrqStr;
    @Transient
    private String htqdrqStr;

    @Transient
    private String ywqsrqStr;

    @Transient
    private String ywjsrqStr;

    public String getHtqdrqStr() {
        if (htqdrq != null) {
            return DateUtil.format(this.htqdrq, "yyyy-MM-dd");
        }
        return "";
    }

    public void setHtqdrqStr(String htqdrqStr) {
        if (htqdrqStr != null) {
            setHtqdrq(DateUtils.parseDate(htqdrqStr));
        }
    }

    public String getYwqsrqStr() {
        if (ywqsrq != null) {
            return DateUtil.format(this.ywqsrq, "yyyy-MM-dd");
        }
        return "";
    }

    public void setYwqsrqStr(String ywqsrqStr) {
        if (ywqsrqStr != null) {
            setYwqsrq(DateUtils.parseDate(ywqsrqStr));
        }
    }

    public String getYwjsrqStr() {
        if (ywjsrq != null) {
            return DateUtil.format(this.ywjsrq, "yyyy-MM-dd");
        }
        return "";
    }

    public void setYwjsrqStr(String ywjsrqStr) {
        if (ywjsrqStr != null) {
            setYwjsrq(DateUtils.parseDate(ywjsrqStr));
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

    /**
     * 流水号
     */
    public String getSerialno() {
        return serialno;
    }

    /**
     * 流水号
     */
    public void setSerialno(String serialno) {
        this.serialno = serialno;
    }

    /**
     * 原始数据批次号
     */
    public String getBatchno() {
        return batchno;
    }

    /**
     * 原始数据批次号
     */
    public void setBatchno(String batchno) {
        this.batchno = batchno;
    }

    /**
     *
     */
    public String getBussno() {
        return bussno;
    }

    /**
     *
     */
    public void setBussno(String bussno) {
        this.bussno = bussno;
    }

    /**
     * 申请日期
     */
    public Date getMakedate() {
        return makedate;
    }

    /**
     * 申请日期
     */
    public void setMakedate(Date makedate) {
        this.makedate = makedate;
    }

    /**
     * 标识日期
     */
    public Date getBussdate() {
        return bussdate;
    }

    /**
     * 标识日期
     */
    public void setBussdate(Date bussdate) {
        this.bussdate = bussdate;
    }

    /**
     * 数据来源
     */
    public String getDatasrc() {
        return datasrc;
    }

    /**
     * 数据来源
     */
    public void setDatasrc(String datasrc) {
        this.datasrc = datasrc;
    }

    /**
     * 状态位标签
     */
    public String getOthersign() {
        return othersign;
    }

    /**
     * 状态位标签
     */
    public void setOthersign(String othersign) {
        this.othersign = othersign;
    }

    /**
     * 备注说明
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注说明
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 保险机构代码
     */
    public String getBxjgdm() {
        return bxjgdm;
    }

    /**
     * 保险机构代码
     */
    public void setBxjgdm(String bxjgdm) {
        this.bxjgdm = bxjgdm;
    }

    /**
     * 保险机构名称
     */
    public String getBxjgmc() {
        return bxjgmc;
    }

    /**
     * 保险机构名称
     */
    public void setBxjgmc(String bxjgmc) {
        this.bxjgmc = bxjgmc;
    }

    /**
     * 业务类型
     */
    public String getYwlx() {
        return ywlx;
    }

    /**
     * 业务类型
     */
    public void setYwlx(String ywlx) {
        this.ywlx = ywlx;
    }

    /**
     * 合同编号
     */
    public String getHtbh() {
        return htbh;
    }

    /**
     * 合同编号
     */
    public void setHtbh(String htbh) {
        this.htbh = htbh;
    }

    /**
     * 合同状态
     */
    public String getHtzt() {
        return htzt;
    }

    /**
     * 合同状态
     */
    public void setHtzt(String htzt) {
        this.htzt = htzt;
    }

    /**
     * 保单号
     */
    public String getBdh() {
        return bdh;
    }

    /**
     * 保单号
     */
    public void setBdh(String bdh) {
        this.bdh = bdh;
    }

    /**
     * 团个性质
     */
    public String getTgxz() {
        return tgxz;
    }

    /**
     * 团个性质
     */
    public void setTgxz(String tgxz) {
        this.tgxz = tgxz;
    }

    /**
     * 合同签订日期
     */
    public Date getHtqdrq() {
        return htqdrq;
    }

    /**
     * 合同签订日期
     */
    public void setHtqdrq(Date htqdrq) {
        this.htqdrq = htqdrq;
    }

    /**
     * 业务起始日期
     */
    public Date getYwqsrq() {
        return ywqsrq;
    }

    /**
     * 业务起始日期
     */
    public void setYwqsrq(Date ywqsrq) {
        this.ywqsrq = ywqsrq;
    }

    /**
     * 业务结束日期
     */
    public Date getYwjsrq() {
        return ywjsrq;
    }

    /**
     * 业务结束日期
     */
    public void setYwjsrq(Date ywjsrq) {
        this.ywjsrq = ywjsrq;
    }

    /**
     * 货币代码
     */
    public String getHbdm() {
        return hbdm;
    }

    /**
     * 货币代码
     */
    public void setHbdm(String hbdm) {
        this.hbdm = hbdm;
    }

    /**
     * 交易金额（原币）
     */
    public BigDecimal getJyjeyb() {
        return jyjeyb;
    }

    /**
     * 交易金额（原币）
     */
    public void setJyjeyb(BigDecimal jyjeyb) {
        this.jyjeyb = jyjeyb;
    }

    /**
     * 交易金额（本位币）
     */
    public BigDecimal getJyjebwb() {
        return jyjebwb;
    }

    /**
     * 交易金额（本位币）
     */
    public void setJyjebwb(BigDecimal jyjebwb) {
        this.jyjebwb = jyjebwb;
    }

    /**
     * 交易价格
     */
    public String getJyjg() {
        return jyjg;
    }

    /**
     * 交易价格
     */
    public void setJyjg(String jyjg) {
        this.jyjg = jyjg;
    }

    /**
     * 定价政策
     */
    public String getDjzc() {
        return djzc;
    }

    /**
     * 定价政策
     */
    public void setDjzc(String djzc) {
        this.djzc = djzc;
    }

    /**
     * 交易概述
     */
    public String getJygs() {
        return jygs;
    }

    /**
     * 交易概述
     */
    public void setJygs(String jygs) {
        this.jygs = jygs;
    }

    /**
     * 交易标的
     */
    public String getJybd() {
        return jybd;
    }

    /**
     * 交易标的
     */
    public void setJybd(String jybd) {
        this.jybd = jybd;
    }

    /**
     * 关联方类型
     */
    public String getGlflx() {
        return glflx;
    }

    /**
     * 关联方类型
     */
    public void setGlflx(String glflx) {
        this.glflx = glflx;
    }

    /**
     * 关联方名称
     */
    public String getGlfmc() {
        return glfmc;
    }

    /**
     * 关联方名称
     */
    public void setGlfmc(String glfmc) {
        this.glfmc = glfmc;
    }

    /**
     * 关联方代码
     */
    public String getGlfdm() {
        return glfdm;
    }

    /**
     * 关联方代码
     */
    public void setGlfdm(String glfdm) {
        this.glfdm = glfdm;
    }

    /**
     * 单笔关联交易类型
     */
    public String getDbgljylx() {
        return dbgljylx;
    }

    /**
     * 单笔关联交易类型
     */
    public void setDbgljylx(String dbgljylx) {
        this.dbgljylx = dbgljylx;
    }

    /**
     * 达到重大关联交易的情形
     */
    public String getDdzdgljydqx() {
        return ddzdgljydqx;
    }

    /**
     * 达到重大关联交易的情形
     */
    public void setDdzdgljydqx(String ddzdgljydqx) {
        this.ddzdgljydqx = ddzdgljydqx;
    }

    /**
     * 其他说明事项
     */
    public String getQtsmsx() {
        return qtsmsx;
    }

    /**
     * 其他说明事项
     */
    public void setQtsmsx(String qtsmsx) {
        this.qtsmsx = qtsmsx;
    }

    /**
     * 统一交易协议标志
     */
    public Long getTyjyxybz() {
        return tyjyxybz;
    }

    /**
     * 统一交易协议标志
     */
    public void setTyjyxybz(Long tyjyxybz) {
        this.tyjyxybz = tyjyxybz;
    }

    /**
     * 补充协议标志
     */
    public Long getBcxybz() {
        return bcxybz;
    }

    /**
     * 补充协议标志
     */
    public void setBcxybz(Long bcxybz) {
        this.bcxybz = bcxybz;
    }

    /**
     * 主合同编号
     */
    public String getZhtbh() {
        return zhtbh;
    }

    /**
     * 主合同编号
     */
    public void setZhtbh(String zhtbh) {
        this.zhtbh = zhtbh;
    }

    /**
     * 采集日期
     */
    public Date getCjrq() {
        return cjrq;
    }

    /**
     * 采集日期
     */
    public void setCjrq(Date cjrq) {
        this.cjrq = cjrq;
    }

    /**
     * 监管辖区代码
     */
    public String getJgxqdm() {
        return jgxqdm;
    }

    /**
     * 监管辖区代码
     */
    public void setJgxqdm(String jgxqdm) {
        this.jgxqdm = jgxqdm;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        MidBxywhqtlgljyxyxxb2 other = (MidBxywhqtlgljyxyxxb2) that;
        return (this.getSerialno() == null ? other.getSerialno() == null : this.getSerialno().equals(other.getSerialno()))
            && (this.getBatchno() == null ? other.getBatchno() == null : this.getBatchno().equals(other.getBatchno()))
            && (this.getBussno() == null ? other.getBussno() == null : this.getBussno().equals(other.getBussno()))
            && (this.getMakedate() == null ? other.getMakedate() == null : this.getMakedate().equals(other.getMakedate()))
            && (this.getBussdate() == null ? other.getBussdate() == null : this.getBussdate().equals(other.getBussdate()))
            && (this.getDatasrc() == null ? other.getDatasrc() == null : this.getDatasrc().equals(other.getDatasrc()))
            && (this.getOthersign() == null ? other.getOthersign() == null : this.getOthersign().equals(other.getOthersign()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getBxjgdm() == null ? other.getBxjgdm() == null : this.getBxjgdm().equals(other.getBxjgdm()))
            && (this.getBxjgmc() == null ? other.getBxjgmc() == null : this.getBxjgmc().equals(other.getBxjgmc()))
            && (this.getYwlx() == null ? other.getYwlx() == null : this.getYwlx().equals(other.getYwlx()))
            && (this.getHtbh() == null ? other.getHtbh() == null : this.getHtbh().equals(other.getHtbh()))
            && (this.getHtzt() == null ? other.getHtzt() == null : this.getHtzt().equals(other.getHtzt()))
            && (this.getBdh() == null ? other.getBdh() == null : this.getBdh().equals(other.getBdh()))
            && (this.getTgxz() == null ? other.getTgxz() == null : this.getTgxz().equals(other.getTgxz()))
            && (this.getHtqdrq() == null ? other.getHtqdrq() == null : this.getHtqdrq().equals(other.getHtqdrq()))
            && (this.getYwqsrq() == null ? other.getYwqsrq() == null : this.getYwqsrq().equals(other.getYwqsrq()))
            && (this.getYwjsrq() == null ? other.getYwjsrq() == null : this.getYwjsrq().equals(other.getYwjsrq()))
            && (this.getHbdm() == null ? other.getHbdm() == null : this.getHbdm().equals(other.getHbdm()))
            && (this.getJyjeyb() == null ? other.getJyjeyb() == null : this.getJyjeyb().equals(other.getJyjeyb()))
            && (this.getJyjebwb() == null ? other.getJyjebwb() == null : this.getJyjebwb().equals(other.getJyjebwb()))
            && (this.getJyjg() == null ? other.getJyjg() == null : this.getJyjg().equals(other.getJyjg()))
            && (this.getDjzc() == null ? other.getDjzc() == null : this.getDjzc().equals(other.getDjzc()))
            && (this.getJygs() == null ? other.getJygs() == null : this.getJygs().equals(other.getJygs()))
            && (this.getJybd() == null ? other.getJybd() == null : this.getJybd().equals(other.getJybd()))
            && (this.getGlflx() == null ? other.getGlflx() == null : this.getGlflx().equals(other.getGlflx()))
            && (this.getGlfmc() == null ? other.getGlfmc() == null : this.getGlfmc().equals(other.getGlfmc()))
            && (this.getGlfdm() == null ? other.getGlfdm() == null : this.getGlfdm().equals(other.getGlfdm()))
            && (this.getDbgljylx() == null ? other.getDbgljylx() == null : this.getDbgljylx().equals(other.getDbgljylx()))
            && (this.getDdzdgljydqx() == null ? other.getDdzdgljydqx() == null : this.getDdzdgljydqx().equals(other.getDdzdgljydqx()))
            && (this.getQtsmsx() == null ? other.getQtsmsx() == null : this.getQtsmsx().equals(other.getQtsmsx()))
            && (this.getTyjyxybz() == null ? other.getTyjyxybz() == null : this.getTyjyxybz().equals(other.getTyjyxybz()))
            && (this.getBcxybz() == null ? other.getBcxybz() == null : this.getBcxybz().equals(other.getBcxybz()))
            && (this.getZhtbh() == null ? other.getZhtbh() == null : this.getZhtbh().equals(other.getZhtbh()))
            && (this.getCjrq() == null ? other.getCjrq() == null : this.getCjrq().equals(other.getCjrq()))
            && (this.getJgxqdm() == null ? other.getJgxqdm() == null : this.getJgxqdm().equals(other.getJgxqdm()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSerialno() == null) ? 0 : getSerialno().hashCode());
        result = prime * result + ((getBatchno() == null) ? 0 : getBatchno().hashCode());
        result = prime * result + ((getBussno() == null) ? 0 : getBussno().hashCode());
        result = prime * result + ((getMakedate() == null) ? 0 : getMakedate().hashCode());
        result = prime * result + ((getBussdate() == null) ? 0 : getBussdate().hashCode());
        result = prime * result + ((getDatasrc() == null) ? 0 : getDatasrc().hashCode());
        result = prime * result + ((getOthersign() == null) ? 0 : getOthersign().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getBxjgdm() == null) ? 0 : getBxjgdm().hashCode());
        result = prime * result + ((getBxjgmc() == null) ? 0 : getBxjgmc().hashCode());
        result = prime * result + ((getYwlx() == null) ? 0 : getYwlx().hashCode());
        result = prime * result + ((getHtbh() == null) ? 0 : getHtbh().hashCode());
        result = prime * result + ((getHtzt() == null) ? 0 : getHtzt().hashCode());
        result = prime * result + ((getBdh() == null) ? 0 : getBdh().hashCode());
        result = prime * result + ((getTgxz() == null) ? 0 : getTgxz().hashCode());
        result = prime * result + ((getHtqdrq() == null) ? 0 : getHtqdrq().hashCode());
        result = prime * result + ((getYwqsrq() == null) ? 0 : getYwqsrq().hashCode());
        result = prime * result + ((getYwjsrq() == null) ? 0 : getYwjsrq().hashCode());
        result = prime * result + ((getHbdm() == null) ? 0 : getHbdm().hashCode());
        result = prime * result + ((getJyjeyb() == null) ? 0 : getJyjeyb().hashCode());
        result = prime * result + ((getJyjebwb() == null) ? 0 : getJyjebwb().hashCode());
        result = prime * result + ((getJyjg() == null) ? 0 : getJyjg().hashCode());
        result = prime * result + ((getDjzc() == null) ? 0 : getDjzc().hashCode());
        result = prime * result + ((getJygs() == null) ? 0 : getJygs().hashCode());
        result = prime * result + ((getJybd() == null) ? 0 : getJybd().hashCode());
        result = prime * result + ((getGlflx() == null) ? 0 : getGlflx().hashCode());
        result = prime * result + ((getGlfmc() == null) ? 0 : getGlfmc().hashCode());
        result = prime * result + ((getGlfdm() == null) ? 0 : getGlfdm().hashCode());
        result = prime * result + ((getDbgljylx() == null) ? 0 : getDbgljylx().hashCode());
        result = prime * result + ((getDdzdgljydqx() == null) ? 0 : getDdzdgljydqx().hashCode());
        result = prime * result + ((getQtsmsx() == null) ? 0 : getQtsmsx().hashCode());
        result = prime * result + ((getTyjyxybz() == null) ? 0 : getTyjyxybz().hashCode());
        result = prime * result + ((getBcxybz() == null) ? 0 : getBcxybz().hashCode());
        result = prime * result + ((getZhtbh() == null) ? 0 : getZhtbh().hashCode());
        result = prime * result + ((getCjrq() == null) ? 0 : getCjrq().hashCode());
        result = prime * result + ((getJgxqdm() == null) ? 0 : getJgxqdm().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", serialno=").append(serialno);
        sb.append(", batchno=").append(batchno);
        sb.append(", bussno=").append(bussno);
        sb.append(", makedate=").append(makedate);
        sb.append(", bussdate=").append(bussdate);
        sb.append(", datasrc=").append(datasrc);
        sb.append(", othersign=").append(othersign);
        sb.append(", remark=").append(remark);
        sb.append(", bxjgdm=").append(bxjgdm);
        sb.append(", bxjgmc=").append(bxjgmc);
        sb.append(", ywlx=").append(ywlx);
        sb.append(", htbh=").append(htbh);
        sb.append(", htzt=").append(htzt);
        sb.append(", bdh=").append(bdh);
        sb.append(", tgxz=").append(tgxz);
        sb.append(", htqdrq=").append(htqdrq);
        sb.append(", ywqsrq=").append(ywqsrq);
        sb.append(", ywjsrq=").append(ywjsrq);
        sb.append(", hbdm=").append(hbdm);
        sb.append(", jyjeyb=").append(jyjeyb);
        sb.append(", jyjebwb=").append(jyjebwb);
        sb.append(", jyjg=").append(jyjg);
        sb.append(", djzc=").append(djzc);
        sb.append(", jygs=").append(jygs);
        sb.append(", jybd=").append(jybd);
        sb.append(", glflx=").append(glflx);
        sb.append(", glfmc=").append(glfmc);
        sb.append(", glfdm=").append(glfdm);
        sb.append(", dbgljylx=").append(dbgljylx);
        sb.append(", ddzdgljydqx=").append(ddzdgljydqx);
        sb.append(", qtsmsx=").append(qtsmsx);
        sb.append(", tyjyxybz=").append(tyjyxybz);
        sb.append(", bcxybz=").append(bcxybz);
        sb.append(", zhtbh=").append(zhtbh);
        sb.append(", cjrq=").append(cjrq);
        sb.append(", jgxqdm=").append(jgxqdm);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
