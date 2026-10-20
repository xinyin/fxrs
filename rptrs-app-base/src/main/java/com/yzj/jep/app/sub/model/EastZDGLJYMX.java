package com.yzj.jep.app.sub.model;

import com.yzj.jep.orm.entity.BasePO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "east_zdgljymxb")
public class EastZDGLJYMX extends BasePO {
    @Id
    @Column(name = "ID", nullable = false, length = 11)
    private Integer id;
    @Column(name = "BUSSEDATE", nullable = false, length = 8)
    private String bussedate;
    @Column(name = "GLFBH", nullable = false, length = 100)
    private String glfbh;
    @Column(name = "GLFMC", nullable = false, length = 200)
    private String glfmc;
    @Column(name = "GLFSBM", nullable = false, length = 100)
    private String glfsbm;
    @Column(name = "GLFLB", nullable = false, length = 20)
    private String glflb;
    @Column(name = "JYBH", nullable = false, length = 100)
    private String jybh;
    @Column(name = "JYBD", nullable = true, length = 1000)
    private String jybd;
    @Column(name = "TYJYXYBZ", nullable = false, length = 10)
    private String tyjyxybz;
    @Column(name = "HTBH", nullable = false, length = 100)
    private String htbh;
    @Column(name = "HTQDRQ", nullable = true, length = 8)
    private String htqdrq;
    @Column(name = "YWQSRQ", nullable = false, length = 8)
    private String ywqsrq;
    @Column(name = "YWJSRQ", nullable = true, length = 8)
    private String ywjsrq;
    @Column(name = "GLJYYWLX", nullable = false, length = 50)
    private String gljyywlx;
    @Column(name = "GLJYJE", nullable = false, length = 22)
    private BigDecimal gljyje;
    @Column(name = "DDZDGLJYDQX", nullable = true, length = 20)
    private String ddzdgljydqx;
    @Column(name = "BNDYGGLFFSGLJYLJJE", nullable = true, length = 22)
    private String bndygglffsgljyljje;
    @Column(name = "GLJYJG", nullable = true, length = 22)
    private String gljyjg;
    @Column(name = "DJZC", nullable = false, length = 1000)
    private String djzc;
    @Column(name = "DCWZKJJYCGDYX", nullable = false, length = 1000)
    private String dcwzkjjycgdyx;
    @Column(name = "ZQLX", nullable = true, length = 20)
    private String zqlx;
    @Column(name = "SYL", nullable = true, length = 12)
    private String syl;
    @Column(name = "JYSL", nullable = true, length = 20)
    private String jysl;
    @Column(name = "BDGBHFEZB", nullable = true, length = 20)
    private String bdgbhfezb;
    @Column(name = "BDHGBHFEZB", nullable = true, length = 20)
    private String bdhgbhfezb;
    @Column(name = "SXRQ", nullable = true, length = 8)
    private String sxrq;
    @Column(name = "XZSPWH", nullable = true, length = 50)
    private String xzspwh;
    @Column(name = "BTZQYQK", nullable = true, length = 1000)
    private String btzqyqk;
    @Column(name = "QCJYJG", nullable = true, length = 22)
    private String qcjyjg;
    @Column(name = "QCJYRQ", nullable = true, length = 8)
    private String qcjyrq;
    @Column(name = "TZFS", nullable = true, length = 20)
    private String tzfs;
    @Column(name = "GQBL", nullable = true, length = 20)
    private String gqbl;
    @Column(name = "JRCPMC", nullable = true, length = 150)
    private String jrcpmc;
    @Column(name = "CPPZ_ZC_BAH", nullable = true, length = 100)
    private String cppz_zc_bah;
    @Column(name = "JRCPFXGM", nullable = true, length = 1000)
    private String jrcpfxgm;
    @Column(name = "TZJRCPFEZB", nullable = true, length = 22)
    private String tzjrcpfezb;
    @Column(name = "DCZCSJGLFBZ", nullable = true, length = 10)
    private String dczcsjglfbz;
    @Column(name = "DCJCZCSJKGGDHKGGDDGLFBZ", nullable = true, length = 10)
    private String dcjczcsjkggdhkggddglfbz;
    @Column(name = "DCZCJTMS", nullable = true, length = 1000)
    private String dczcjtms;
    @Column(name = "DCZCSJDGLFMC", nullable = true, length = 200)
    private String dczcsjdglfmc;
    @Column(name = "BTQYMC", nullable = true, length = 200)
    private String btqymc;
    @Column(name = "YWMS", nullable = true, length = 1000)
    private String ywms;
    @Column(name = "YLYQ", nullable = true, length = 1000)
    private String ylyq;
    @Column(name = "TZBL", nullable = true, length = 20)
    private String tzbl;
    @Column(name = "TZSYFPQK", nullable = true, length = 1000)
    private String tzsyfpqk;
    @Column(name = "SSFDQK", nullable = true, length = 1000)
    private String ssfdqk;
    @Column(name = "BDZCQK", nullable = true, length = 1000)
    private String bdzcqk;
    @Column(name = "BDQK", nullable = true, length = 1000)
    private String bdqk;
    @Column(name = "DBTJ", nullable = true, length = 1000)
    private String dbtj;
    @Column(name = "KGZGSJYBZ", nullable = false, length = 10)
    private String kgzgsjybz;
    @Column(name = "KGZGSQC", nullable = true, length = 200)
    private String kgzgsqc;
    @Column(name = "KGZGSZJHM", nullable = true, length = 100)
    private String kgzgszjhm;
    @Column(name = "QTSMSX", nullable = true, length = 1000)
    private String qtsmsx;
    @Column(name = "EAST_STATUS ", nullable = true, length = 4)
    private Integer eastStatus;
//    @Column(name = "sub_org", nullable = true, length = 65)
//    private String subOrg;
//
//    @Column(name = "sub_num", nullable = true, length = 10)
//    private String subNum;
//    @Column(name = "khbh", nullable = true, length = 65)
//    private String khbh;
//
//    @Column(name = "khmc", nullable = true, length = 65)
//    private String khmc;
//
//    @Column(name = "khsbm", nullable = true, length = 65)
//    private String khsbm;
//
//    @Column(name = "khlb", nullable = true, length = 65)
//    private String khlb;
//
//    @Column(name = "lxh", nullable = true, length = 20)
//    private String lxh;

//columns STOP


//    public String getLxh() {
//        return lxh;
//    }
//
//    public void setLxh(String lxh) {
//        this.lxh = lxh;
//    }
//
//    public String getKhbh() {
//        return khbh;
//    }
//
//    public void setKhbh(String khbh) {
//        this.khbh = khbh;
//    }
//
//    public String getKhmc() {
//        return khmc;
//    }
//
//    public void setKhmc(String khmc) {
//        this.khmc = khmc;
//    }
//
//    public String getKhsbm() {
//        return khsbm;
//    }
//
//    public void setKhsbm(String khsbm) {
//        this.khsbm = khsbm;
//    }
//
//    public String getKhlb() {
//        return khlb;
//    }
//
//    public void setKhlb(String khlb) {
//        this.khlb = khlb;
//    }
//
//    public String getSubOrg() {
//        return subOrg;
//    }
//
//    public void setSubOrg(String subOrg) {
//        this.subOrg = subOrg;
//    }
//
//    public String getSubNum() {
//        return subNum;
//    }
//
//    public void setSubNum(String subNum) {
//        this.subNum = subNum;
//    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBussedate() {
        return bussedate;
    }

    public void setBussedate(String bussedate) {
        this.bussedate = bussedate;
    }

    public String getGlfbh() {
        return glfbh;
    }

    public void setGlfbh(String glfbh) {
        this.glfbh = glfbh;
    }

    public String getGlfmc() {
        return glfmc;
    }

    public void setGlfmc(String glfmc) {
        this.glfmc = glfmc;
    }

    public String getGlfsbm() {
        return glfsbm;
    }

    public void setGlfsbm(String glfsbm) {
        this.glfsbm = glfsbm;
    }

    public String getGlflb() {
        return glflb;
    }

    public void setGlflb(String glflb) {
        this.glflb = glflb;
    }

    public String getJybh() {
        return jybh;
    }

    public void setJybh(String jybh) {
        this.jybh = jybh;
    }

    public String getJybd() {
        return jybd;
    }

    public void setJybd(String jybd) {
        this.jybd = jybd;
    }

    public String getTyjyxybz() {
        return tyjyxybz;
    }

    public void setTyjyxybz(String tyjyxybz) {
        this.tyjyxybz = tyjyxybz;
    }

    public String getHtbh() {
        return htbh;
    }

    public void setHtbh(String htbh) {
        this.htbh = htbh;
    }

    public String getHtqdrq() {
        return htqdrq;
    }

    public void setHtqdrq(String htqdrq) {
        this.htqdrq = htqdrq;
    }

    public String getYwqsrq() {
        return ywqsrq;
    }

    public void setYwqsrq(String ywqsrq) {
        this.ywqsrq = ywqsrq;
    }

    public String getYwjsrq() {
        return ywjsrq;
    }

    public void setYwjsrq(String ywjsrq) {
        this.ywjsrq = ywjsrq;
    }

    public String getGljyywlx() {
        return gljyywlx;
    }

    public void setGljyywlx(String gljyywlx) {
        this.gljyywlx = gljyywlx;
    }

    public BigDecimal getGljyje() {
        return gljyje;
    }

    public void setGljyje(BigDecimal gljyje) {
        this.gljyje = gljyje;
    }

    public String getDdzdgljydqx() {
        return ddzdgljydqx;
    }

    public void setDdzdgljydqx(String ddzdgljydqx) {
        this.ddzdgljydqx = ddzdgljydqx;
    }

    public String getBndygglffsgljyljje() {
        return bndygglffsgljyljje;
    }

    public void setBndygglffsgljyljje(String bndygglffsgljyljje) {
        this.bndygglffsgljyljje = bndygglffsgljyljje;
    }

    public String getGljyjg() {
        return gljyjg;
    }

    public void setGljyjg(String gljyjg) {
        this.gljyjg = gljyjg;
    }

    public String getDjzc() {
        return djzc;
    }

    public void setDjzc(String djzc) {
        this.djzc = djzc;
    }

    public String getDcwzkjjycgdyx() {
        return dcwzkjjycgdyx;
    }

    public void setDcwzkjjycgdyx(String dcwzkjjycgdyx) {
        this.dcwzkjjycgdyx = dcwzkjjycgdyx;
    }

    public String getZqlx() {
        return zqlx;
    }

    public void setZqlx(String zqlx) {
        this.zqlx = zqlx;
    }

    public String getSyl() {
        return syl;
    }

    public void setSyl(String syl) {
        this.syl = syl;
    }

    public String getJysl() {
        return jysl;
    }

    public void setJysl(String jysl) {
        this.jysl = jysl;
    }

    public String getBdgbhfezb() {
        return bdgbhfezb;
    }

    public void setBdgbhfezb(String bdgbhfezb) {
        this.bdgbhfezb = bdgbhfezb;
    }

    public String getBdhgbhfezb() {
        return bdhgbhfezb;
    }

    public void setBdhgbhfezb(String bdhgbhfezb) {
        this.bdhgbhfezb = bdhgbhfezb;
    }

    public String getSxrq() {
        return sxrq;
    }

    public void setSxrq(String sxrq) {
        this.sxrq = sxrq;
    }

    public String getXzspwh() {
        return xzspwh;
    }

    public void setXzspwh(String xzspwh) {
        this.xzspwh = xzspwh;
    }

    public String getBtzqyqk() {
        return btzqyqk;
    }

    public void setBtzqyqk(String btzqyqk) {
        this.btzqyqk = btzqyqk;
    }

    public String getQcjyjg() {
        return qcjyjg;
    }

    public void setQcjyjg(String qcjyjg) {
        this.qcjyjg = qcjyjg;
    }

    public String getQcjyrq() {
        return qcjyrq;
    }

    public void setQcjyrq(String qcjyrq) {
        this.qcjyrq = qcjyrq;
    }

    public String getTzfs() {
        return tzfs;
    }

    public void setTzfs(String tzfs) {
        this.tzfs = tzfs;
    }

    public String getGqbl() {
        return gqbl;
    }

    public void setGqbl(String gqbl) {
        this.gqbl = gqbl;
    }

    public String getJrcpmc() {
        return jrcpmc;
    }

    public void setJrcpmc(String jrcpmc) {
        this.jrcpmc = jrcpmc;
    }

    public String getCppz_zc_bah() {
        return cppz_zc_bah;
    }

    public void setCppz_zc_bah(String cppz_zc_bah) {
        this.cppz_zc_bah = cppz_zc_bah;
    }

    public String getJrcpfxgm() {
        return jrcpfxgm;
    }

    public void setJrcpfxgm(String jrcpfxgm) {
        this.jrcpfxgm = jrcpfxgm;
    }

    public String getTzjrcpfezb() {
        return tzjrcpfezb;
    }

    public void setTzjrcpfezb(String tzjrcpfezb) {
        this.tzjrcpfezb = tzjrcpfezb;
    }

    public String getDczcsjglfbz() {
        return dczcsjglfbz;
    }

    public void setDczcsjglfbz(String dczcsjglfbz) {
        this.dczcsjglfbz = dczcsjglfbz;
    }

    public String getDcjczcsjkggdhkggddglfbz() {
        return dcjczcsjkggdhkggddglfbz;
    }

    public void setDcjczcsjkggdhkggddglfbz(String dcjczcsjkggdhkggddglfbz) {
        this.dcjczcsjkggdhkggddglfbz = dcjczcsjkggdhkggddglfbz;
    }

    public String getDczcjtms() {
        return dczcjtms;
    }

    public void setDczcjtms(String dczcjtms) {
        this.dczcjtms = dczcjtms;
    }

    public String getDczcsjdglfmc() {
        return dczcsjdglfmc;
    }

    public void setDczcsjdglfmc(String dczcsjdglfmc) {
        this.dczcsjdglfmc = dczcsjdglfmc;
    }

    public String getBtqymc() {
        return btqymc;
    }

    public void setBtqymc(String btqymc) {
        this.btqymc = btqymc;
    }

    public String getYwms() {
        return ywms;
    }

    public void setYwms(String ywms) {
        this.ywms = ywms;
    }

    public String getYlyq() {
        return ylyq;
    }

    public void setYlyq(String ylyq) {
        this.ylyq = ylyq;
    }

    public String getTzbl() {
        return tzbl;
    }

    public void setTzbl(String tzbl) {
        this.tzbl = tzbl;
    }

    public String getTzsyfpqk() {
        return tzsyfpqk;
    }

    public void setTzsyfpqk(String tzsyfpqk) {
        this.tzsyfpqk = tzsyfpqk;
    }

    public String getSsfdqk() {
        return ssfdqk;
    }

    public void setSsfdqk(String ssfdqk) {
        this.ssfdqk = ssfdqk;
    }

    public String getBdzcqk() {
        return bdzcqk;
    }

    public void setBdzcqk(String bdzcqk) {
        this.bdzcqk = bdzcqk;
    }

    public String getBdqk() {
        return bdqk;
    }

    public void setBdqk(String bdqk) {
        this.bdqk = bdqk;
    }

    public String getDbtj() {
        return dbtj;
    }

    public void setDbtj(String dbtj) {
        this.dbtj = dbtj;
    }

    public String getKgzgsjybz() {
        return kgzgsjybz;
    }

    public void setKgzgsjybz(String kgzgsjybz) {
        this.kgzgsjybz = kgzgsjybz;
    }

    public String getKgzgsqc() {
        return kgzgsqc;
    }

    public void setKgzgsqc(String kgzgsqc) {
        this.kgzgsqc = kgzgsqc;
    }

    public String getKgzgszjhm() {
        return kgzgszjhm;
    }

    public void setKgzgszjhm(String kgzgszjhm) {
        this.kgzgszjhm = kgzgszjhm;
    }

    public String getQtsmsx() {
        return qtsmsx;
    }

    public void setQtsmsx(String qtsmsx) {
        this.qtsmsx = qtsmsx;
    }

    public Integer getEastStatus() {
        return eastStatus;
    }

    public void setEastStatus(Integer eastStatus) {
        this.eastStatus = eastStatus;
    }
}
