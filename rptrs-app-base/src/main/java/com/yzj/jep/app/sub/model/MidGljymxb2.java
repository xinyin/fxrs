package com.yzj.jep.app.sub.model;

import cn.hutool.core.date.DateUtil;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.orm.entity.BasePO;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "mid_gljymxb_2")
public class MidGljymxb2 extends BasePO {
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
     * 协议交易标志
     */
    @Column(name = "XYJYBZ", nullable = true, length = 3)
    private Long xyjybz;

    /**
     * 合同编号
     */
    @Column(name = "HTBH", nullable = true, length = 600)
    private String htbh;

    /**
     * 交易编号
     */
    @Column(name = "JYBH", nullable = true, length = 600)
    private String jybh;
    /**
     * 关联交易类型
     */
    @Column(name = "GLJYLX", nullable = true, length = 150)
    private String gljylx;

    /**
     * 关联交易业务类型
     */
    @Column(name = "GLJYYWLX", nullable = true, length = 150)
    private String gljyywlx;

    /**
     * 交易币种
     */
    @Column(name = "JYBZ", nullable = true, length = 9)
    private String jybz;

    /**
     * 交易金额（原币）
     */
    @Column(name = "JYBJYJE", nullable = true, length = 22)
    private BigDecimal jybjyje;

    /**
     * 交易金额（本位币）
     */
    @Column(name = "BWBJYJE", nullable = true, length = 22)
    private BigDecimal bwbjyje;


    /**
     * 交易方向
     */
    @Column(name = "JYFX", nullable = true, length = 30)
    private String jyfx;

    /**
     * 交易币交易金额资金流水编号
     */
    @Column(name = "JYBJYJEZJLSBH", nullable = true, length = 1500)
    private String jybjyjezjlsbh;

    /**
     * 交易币交易金额交易流水编号
     */
    @Column(name = "JYBJYJEJYLSBH", nullable = true, length = 1500)
    private String jybjyjejylsbh;

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
     * 交易日期
     */
    @Column(name = "JYRQ", nullable = true, length = 7)
    private Date jyrq;


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
     * 金融产品编号
     */
    @Column(name = "JRCPBH", nullable = true, length = 600)
    private String jrcpbh;

    /**
     * 金融产品名称
     */
    @Column(name = "JRCPMC", nullable = true, length = 600)
    private String jrcpmc;

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

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    @Transient
    private String pid;



    private static final long serialVersionUID = 1L;
    @Transient
    private String makedateStr;

    @Transient
    private String bussdateStr;

    @Transient
    private String cjrqStr;
    @Transient
    private String jyrqStr;

    public String getJyrqStr() {
        if (jyrq != null) {
            return DateUtil.format(this.jyrq, "yyyy-MM-dd");
        }
        return "";
    }

    public void setJyrqStr(String jyrqStr) {
        if (jyrqStr != null) {
            setJyrq(DateUtils.parseDate(jyrqStr));
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

    public Long getXyjybz() {
        return xyjybz;
    }

    public void setXyjybz(Long xyjybz) {
        this.xyjybz = xyjybz;
    }

    public String getJybh() {
        return jybh;
    }

    public void setJybh(String jybh) {
        this.jybh = jybh;
    }

    public String getGljylx() {
        return gljylx;
    }

    public void setGljylx(String gljylx) {
        this.gljylx = gljylx;
    }

    public String getGljyywlx() {
        return gljyywlx;
    }

    public void setGljyywlx(String gljyywlx) {
        this.gljyywlx = gljyywlx;
    }

    public String getJybz() {
        return jybz;
    }

    public void setJybz(String jybz) {
        this.jybz = jybz;
    }

    public BigDecimal getJybjyje() {
        return jybjyje;
    }

    public void setJybjyje(BigDecimal jybjyje) {
        this.jybjyje = jybjyje;
    }

    public BigDecimal getBwbjyje() {
        return bwbjyje;
    }

    public void setBwbjyje(BigDecimal bwbjyje) {
        this.bwbjyje = bwbjyje;
    }

    public String getJyfx() {
        return jyfx;
    }

    public void setJyfx(String jyfx) {
        this.jyfx = jyfx;
    }

    public String getJybjyjezjlsbh() {
        return jybjyjezjlsbh;
    }

    public void setJybjyjezjlsbh(String jybjyjezjlsbh) {
        this.jybjyjezjlsbh = jybjyjezjlsbh;
    }

    public String getJybjyjejylsbh() {
        return jybjyjejylsbh;
    }

    public void setJybjyjejylsbh(String jybjyjejylsbh) {
        this.jybjyjejylsbh = jybjyjejylsbh;
    }

    public Date getJyrq() {
        return jyrq;
    }

    public void setJyrq(Date jyrq) {
        this.jyrq = jyrq;
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


    public String getJrcpbh() {
        return jrcpbh;
    }

    public void setJrcpbh(String jrcpbh) {
        this.jrcpbh = jrcpbh;
    }

    public String getJrcpmc() {
        return jrcpmc;
    }

    public void setJrcpmc(String jrcpmc) {
        this.jrcpmc = jrcpmc;
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
        MidGljymxb2 other = (MidGljymxb2) that;
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
            && (this.getHtbh() == null ? other.getHtbh() == null : this.getHtbh().equals(other.getHtbh()))
            && (this.getXyjybz() == null ? other.getXyjybz() == null : this.getXyjybz().equals(other.getXyjybz()))
            && (this.getJybh() == null ? other.getJybh() == null : this.getJybh().equals(other.getJybh()))
            && (this.getGljylx() == null ? other.getGljylx() == null : this.getGljylx().equals(other.getGljylx()))
            && (this.getGljyywlx() == null ? other.getGljyywlx() == null : this.getGljyywlx().equals(other.getGljyywlx()))
            && (this.getJybz() == null ? other.getJybz() == null : this.getJybz().equals(other.getJybz()))
            && (this.getJybjyje() == null ? other.getJybjyje() == null : this.getJybjyje().equals(other.getJybjyje()))
            && (this.getBwbjyje() == null ? other.getBwbjyje() == null : this.getBwbjyje().equals(other.getBwbjyje()))
            && (this.getJyfx() == null ? other.getJyfx() == null : this.getJyfx().equals(other.getJyfx()))
            && (this.getJyrq() == null ? other.getJyrq() == null : this.getJyrq().equals(other.getJyrq()))
            && (this.getJygs() == null ? other.getJygs() == null : this.getJygs().equals(other.getJygs()))
            && (this.getJybd() == null ? other.getJybd() == null : this.getJybd().equals(other.getJybd()))
            && (this.getGlfmc() == null ? other.getGlfmc() == null : this.getGlfmc().equals(other.getGlfmc()))
            && (this.getGlfdm() == null ? other.getGlfdm() == null : this.getGlfdm().equals(other.getGlfdm()))
            && (this.getCjrq() == null ? other.getCjrq() == null : this.getCjrq().equals(other.getCjrq()))
            && (this.getJrcpbh() == null ? other.getJrcpbh() == null : this.getJrcpbh().equals(other.getJrcpbh()))
            && (this.getJrcpmc() == null ? other.getJrcpmc() == null : this.getJrcpmc().equals(other.getJrcpmc()))
            && (this.getJybjyjezjlsbh() == null ? other.getJybjyjezjlsbh() == null : this.getJybjyjezjlsbh().equals(other.getJybjyjezjlsbh()))
            && (this.getJybjyjejylsbh() == null ? other.getJybjyjejylsbh() == null : this.getJybjyjejylsbh().equals(other.getJybjyjejylsbh()))
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
        result = prime * result + ((getHtbh() == null) ? 0 : getHtbh().hashCode());
        result = prime * result + ((getJybh() == null) ? 0 : getJybh().hashCode());
        result = prime * result + ((getGljylx() == null) ? 0 : getGljylx().hashCode());
        result = prime * result + ((getGljyywlx() == null) ? 0 : getGljyywlx().hashCode());
        result = prime * result + ((getJybz() == null) ? 0 : getJybz().hashCode());
        result = prime * result + ((getJybjyje() == null) ? 0 : getJybjyje().hashCode());
        result = prime * result + ((getBwbjyje() == null) ? 0 : getBwbjyje().hashCode());
        result = prime * result + ((getJyfx() == null) ? 0 : getJyfx().hashCode());
        result = prime * result + ((getJybjyjezjlsbh() == null) ? 0 : getJybjyjezjlsbh().hashCode());
        result = prime * result + ((getJybjyjejylsbh() == null) ? 0 : getJybjyjejylsbh().hashCode());
        result = prime * result + ((getJygs() == null) ? 0 : getJygs().hashCode());
        result = prime * result + ((getJybd() == null) ? 0 : getJybd().hashCode());
        result = prime * result + ((getGlfmc() == null) ? 0 : getGlfmc().hashCode());
        result = prime * result + ((getGlfdm() == null) ? 0 : getGlfdm().hashCode());
        result = prime * result + ((getJyrq() == null) ? 0 : getJyrq().hashCode());
        result = prime * result + ((getCjrq() == null) ? 0 : getCjrq().hashCode());
        result = prime * result + ((getJgxqdm() == null) ? 0 : getJgxqdm().hashCode());
        result = prime * result + ((getJrcpbh() == null) ? 0 : getJrcpbh().hashCode());
        result = prime * result + ((getJrcpmc() == null) ? 0 : getJrcpmc().hashCode());
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
        sb.append(", xyjybz=").append(xyjybz);
        sb.append(", htbh=").append(htbh);
        sb.append(", jybh=").append(jybh);
        sb.append(", gljylx=").append(gljylx);
        sb.append(", gljyywlx=").append(gljyywlx);
        sb.append(", jybz=").append(jybz);
        sb.append(", jybjyje=").append(jybjyje);
        sb.append(", bwbjyje=").append(bwbjyje);
        sb.append(", jyfx=").append(jyfx);
        sb.append(", jybjyjejylsbh=").append(jybjyjejylsbh);
        sb.append(", jybjyjezjlsbh=").append(jybjyjezjlsbh);
        sb.append(", jygs=").append(jygs);
        sb.append(", jybd=").append(jybd);
        sb.append(", glfmc=").append(glfmc);
        sb.append(", glfdm=").append(glfdm);
        sb.append(", jyrq=").append(jyrq);
        sb.append(", cjrq=").append(cjrq);
        sb.append(", jgxqdm=").append(jgxqdm);
        sb.append(", jrcpbh=").append(jrcpbh);
        sb.append(", jrcpmc=").append(jrcpmc);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
