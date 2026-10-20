package com.yzj.jep.app.sub.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.yzj.jep.app.enums.RdsDataSrc;
import com.yzj.jep.app.ins.service.TraInterestService;
import com.yzj.jep.app.sub.mapper.MidGljymxbMapper;
import com.yzj.jep.app.sub.model.MidGljymxb;
import com.yzj.jep.app.sub.service.MidGljymxbService;
import com.yzj.jep.app.sub.service.RdsMiddatalogService;
import com.yzj.jep.app.util.DateExtendUtil;
import com.yzj.jep.app.util.DictUtil;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class MidGljymxbServiceImpl extends MyBatisCRUDService<MidGljymxb,Integer>
    implements MidGljymxbService {

    @Autowired
    private MidGljymxbMapper midGljymxbMapper;

    @Autowired
    private TraInterestService traInterestService;

    @Autowired
    private RdsMiddatalogService rdsMiddatalogService;
    @Override
    public Mapper<MidGljymxb, Integer> getMapper() {
        return midGljymxbMapper;
    }

    @Override
    public void createEastMidGljymxb(String batchNo) {
        // 获取上月日期开始
        String dataStartDate = DateExtendUtil.preMonthBeginDateStr(new Date());
        // 获取上月日期截止
        String dataEndDate = DateExtendUtil.preMonthEndDateStr(new Date());
        BigDecimal bigDecimal = new BigDecimal("10000");
        // 交易类型
        Map<String, String> typeMap = DictUtil.convertToMap("INS-tranType");
        Map<String, Map<String, String>> typeMaps = new HashMap<>();
        typeMaps.put("1", DictUtil.convertToMap("INS-FundsType"));
        typeMaps.put("2", DictUtil.convertToMap("INS-ServiceType"));
        typeMaps.put("3", DictUtil.convertToMap("INT-BusinessTypes"));
        typeMaps.put("4", DictUtil.convertToMap("INT-InsBusinessType"));

        List<MidGljymxb> select = midGljymxbMapper.selectTraDetails(dataStartDate,dataEndDate);
        for (MidGljymxb tra : select) {
            BigDecimal bwbjyje = tra.getBwbjyje();
            BigDecimal jybjyje = tra.getJybjyje();
            if (StringUtils.isNotEmpty(tra.getPayment()) && tra.getPayment().equals("1")) {
                if (ObjectUtil.isNotEmpty(bwbjyje)) {
                    bwbjyje = new BigDecimal("-" + tra.getBwbjyje());
                }
                if (ObjectUtil.isNotEmpty(jybjyje)) {
                    jybjyje = new BigDecimal("-" + tra.getJybjyje());
                }
            }
            if (ObjectUtil.isNotEmpty(bwbjyje)) {
                bwbjyje = bwbjyje.multiply(bigDecimal);
            }
            if (ObjectUtil.isNotEmpty(jybjyje)) {
                jybjyje = jybjyje.multiply(bigDecimal);
            }

            MidGljymxb east = new MidGljymxb();
            east.setSerialno(batchNo + tra.getPid());
            east.setBatchno(batchNo);
            east.setBussno(tra.getHtbh() + tra.getGlfdm() + tra.getJybh());
            east.setMakedate(new Date());
            east.setBussdate(new Date());
            east.setDatasrc(RdsDataSrc.GLJY.getValue());
            east.setOthersign("");
            east.setRemark("");
            east.setBxjgdm(""); // TODO 待查问
            east.setBxjgmc(""); // TODO 待查问

            east.setXyjybz(tra.getXyjybz());
            east.setHtbh(tra.getHtbh());//合同编号
            east.setJybh(tra.getJybh());
            String gljylx = tra.getGljylx();
            if (StringUtils.isNotEmpty(gljylx)) {
                east.setGljylx(typeMap.get(gljylx));
                east.setGljyywlx(typeMaps.get(gljylx).get(tra.getGljyywlx()));
            }

            east.setJybz(tra.getJybz());
            east.setJybjyje(jybjyje);
            east.setJybjyjejylsbh(tra.getJybjyjejylsbh());
            east.setJybjyjezjlsbh(tra.getJybjyjezjlsbh());
            east.setBwbjyje(bwbjyje);
            east.setJyrq(tra.getJyrq());
            east.setJrcpbh(tra.getJrcpbh());
            east.setJrcpmc(tra.getJrcpmc());
            east.setJyfx(tra.getJyfx());//交易方向
            east.setJygs(tra.getJygs());//交易概述
            east.setJybd(tra.getJybd());//交易标的
            east.setGlfmc(tra.getGlfdm());//
            east.setGlfdm(tra.getGlfmc());//
            east.setCjrq(DateExtendUtil.endOfLastMonth(new Date()));
            east.setJgxqdm(""); // TODO 待查问
            this.insert(east);

        }
    }
}




