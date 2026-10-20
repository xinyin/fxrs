package com.yzj.jep.app.sub.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.yzj.jep.app.enums.RdsDataSrc;
import com.yzj.jep.app.ins.model.TraInterest;
import com.yzj.jep.app.ins.service.TraInterestService;
import com.yzj.jep.app.sub.mapper.MidLyzylgljyxyxxbMapper;
import com.yzj.jep.app.sub.model.MidLyzylgljyxyxxb;
import com.yzj.jep.app.sub.service.MidLyzylgljyxyxxbService;
import com.yzj.jep.app.util.DateExtendUtil;
import com.yzj.jep.app.util.DateUtils;
import com.yzj.jep.app.util.DictUtil;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class MidLyzylgljyxyxxbServiceImpl extends MyBatisCRUDService<MidLyzylgljyxyxxb,Integer>
    implements MidLyzylgljyxyxxbService {

    @Autowired
    private MidLyzylgljyxyxxbMapper midLyzylgljyxyxxbMapper;

    @Autowired
    private TraInterestService traInterestService;
    @Override
    public Mapper<MidLyzylgljyxyxxb, Integer> getMapper() {
        return midLyzylgljyxyxxbMapper;
    }

    @Override
    public void createEastMidLyzylgljyxyxxb(String batchNo) {
        // 获取上月日期开始
        String dataStartDate = DateUtils.preMonthBeginDateStr(new Date());
        // 获取上月日期截止
        String dataEndDate = DateUtils.preMonthEndDateStr(new Date());
        List<Filter> filter = new Filter.Builder()
                .eq("dataVersion","E2.0")
                .eq("delflag", 1)
                .eq("status","6")
                .eq("protocolTransactionSymbol", "1")
                .ge("contractDate", dataStartDate)
                .le("contractDate", dataEndDate)
                .build();
        List<TraInterest> select = traInterestService.select(filter);
        Map<String, String> typeMap = DictUtil.convertToMap("INT-BusinessTypes");
        Map<String, String> convertToMap = DictUtil.convertToMap("BUS-contractStatus");
        Map<String, String> stringStringMap = DictUtil.convertToMap("INS-SingleRelatedTranType");
        Map<String, String> majorTypeMap = DictUtil.convertToMap("INS-majorType");
        Map<String, String> directionsMap = DictUtil.convertToMap("INS-Tra-Directions");
        for (TraInterest tra : select) {
            MidLyzylgljyxyxxb east = new MidLyzylgljyxyxxb();
            east.setSerialno(batchNo + tra.getPid());
            east.setBatchno(batchNo);
            east.setBussno(tra.getContractNo() + tra.getRelNumber());
            east.setMakedate(new Date());
            east.setBussdate(new Date());
            east.setDatasrc(RdsDataSrc.GLJY.getValue());
            east.setOthersign("");
            east.setRemark("");
            east.setBxjgdm(""); // TODO 待查问
            east.setBxjgmc(""); // TODO 待查问

            east.setYwlx(typeMap.get(tra.getBusType()));//业务类型
            east.setHtbh(tra.getContractNo());//合同编号
            east.setHtzt(convertToMap.get(tra.getContractStatus()));//合同状态
            east.setHtqdrq(tra.getContractDate());//合同签订日期
            east.setYwqsrq(tra.getBusEndTime());//业务起始日期
            east.setYwjsrq(tra.getBusEndTime());//业务结束日期
            east.setJyfx(directionsMap.get(tra.getTradingDirection()));//交易方向
            east.setHbdm(tra.getCurrencyCode());//货币代码
            east.setJyjeyb(tra.getOriginalCurrencyAmount());//交易金额（原币）
            east.setJyjebwb(tra.getRelTradeAmount());//交易金额（本位币）
            east.setJyjg(tra.getTradePrice());//交易价格
            east.setDjzc(tra.getPricingPolicy());//定价政策
            east.setJygs(tra.getTranName());//交易概述
            east.setJybd(tra.getTradeSubject());//交易标的
            east.setGlflx(DictUtil.convertRelType(tra.getRelType()));//关联方类型
            east.setGlfmc(tra.getRelName());//
            east.setGlfdm(tra.getRelNumber());//
            east.setDbgljylx(stringStringMap.get(tra.getSingleRelatedTranType()));//单笔关联交易类型
            east.setDdzdgljydqx(majorTypeMap.get(tra.getMajorType()));//达到重大关联交易的情形
            east.setQtsmsx(tra.getRemark());//其他说明事项
            east.setBcxybz(Long.valueOf(tra.getProtocolTransactionSymbol()));//补充协议标志
            east.setZhtbh(tra.getMasterAgreementNumber());//主合同编号
            east.setDbtj(tra.getGuaranteeConditions());


            east.setCjrq(DateExtendUtil.endOfLastMonth(new Date()));
            east.setJgxqdm(""); // TODO 待查问
            this.insert(east);

        }
    }
}




