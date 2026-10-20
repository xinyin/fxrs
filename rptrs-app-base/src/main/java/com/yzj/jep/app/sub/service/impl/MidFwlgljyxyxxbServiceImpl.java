package com.yzj.jep.app.sub.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.yzj.jep.app.enums.RdsDataSrc;
import com.yzj.jep.app.ins.model.TraService;
import com.yzj.jep.app.ins.service.TraServiceService;
import com.yzj.jep.app.sub.mapper.MidFwlgljyxyxxbMapper;
import com.yzj.jep.app.sub.model.MidFwlgljyxyxxb;
import com.yzj.jep.app.sub.service.MidFwlgljyxyxxbService;
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

/**
* @author Administrator
* @description 针对表【mid_fwlgljyxyxxb_2】的数据库操作Service实现
* @createDate 2025-01-09 17:14:41
*/
@Service
@Transactional
public class MidFwlgljyxyxxbServiceImpl extends MyBatisCRUDService<MidFwlgljyxyxxb,Integer>
    implements MidFwlgljyxyxxbService {

    @Autowired
    private MidFwlgljyxyxxbMapper midFwlgljyxyxxbMapper;

    @Autowired
    private TraServiceService traServiceService;
    @Override
    public Mapper<MidFwlgljyxyxxb, Integer> getMapper() {
        return midFwlgljyxyxxbMapper;
    }

    @Override
    public void createEastMidFwlgljyxyxxb(String batchNo) {
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
        List<TraService> select = traServiceService.select(filter);
        Map<String, String> typeMap = DictUtil.convertToMap("INS-ServiceType");
        Map<String, String> convertToMap = DictUtil.convertToMap("BUS-contractStatus");
        Map<String, String> stringStringMap = DictUtil.convertToMap("INS-SingleRelatedTranType");
        Map<String, String> majorTypeMap = DictUtil.convertToMap("INS-majorType");
        Map<String, String> directionMap = DictUtil.convertToMap("INS-Tra-Direction");
        for (TraService tra : select) {
            MidFwlgljyxyxxb east = new MidFwlgljyxyxxb();
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
            east.setJyfx(directionMap.get(tra.getTradingDirection()));//交易方向
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
            east.setTyjyxybz(Long.valueOf(tra.getIsAgreement()));//统一交易协议标志
            east.setBcxybz(Long.valueOf(tra.getProtocolTransactionSymbol()));//补充协议标志
            east.setZhtbh(tra.getMasterAgreementNumber());//主合同编号



            east.setCjrq(DateExtendUtil.endOfLastMonth(new Date()));
            east.setJgxqdm(""); // TODO 待查问
            this.insert(east);

        }
    }
}




