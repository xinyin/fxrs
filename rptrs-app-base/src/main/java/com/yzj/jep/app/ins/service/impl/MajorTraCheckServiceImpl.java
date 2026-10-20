package com.yzj.jep.app.ins.service.impl;

import cn.hutool.core.date.DateUtil;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.ins.mapper.MajorTraCheckMapper;
import com.yzj.jep.app.ins.service.MajorTraCheckService;
import com.yzj.jep.app.util.Dict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @title: MajorTraCheckServiceImpl
 * @Author Whx
 * @Date: 2021/8/18 15:42
 * @Version 1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MajorTraCheckServiceImpl implements MajorTraCheckService {

    @Autowired
    private RelOrgService relOrgService;

    @Autowired
    private RelPsnService relPsnService;

    @Autowired
    private MajorTraCheckMapper majorTraCheckMapper;

    /**
     * 统计关联交易年度交易总额与 累计实际交易金额
     *
     * @param pid
     * @param relType
     * @return
     */
    @Override
    public Map<String, Object> sumAmount(Integer pid, String relType) {
        Map<String, Object> result = new HashMap<>();
        /**本年度与该关联方累计交易金额（万元） 此处取实际交易金额  取已归档和待确认的数据，履行中的数据实际交易金额无值*/
        int year = DateUtil.year(new Date());
        Map<String, Object> param = new HashMap<>();
        /**年度*/
        param.put("year", String.valueOf(year).concat("%"));
        /**关联方证件relNumber*/
        String relNumber = null;
        if ("ORG".equals(relType)) {
            RelOrg relOrg = relOrgService.get(pid);
            relNumber = relOrg.getUniscCode();
        } else {
            RelPsn relPsn = relPsnService.get(pid);
            relNumber = relPsn.getIdentifyNumber();
        }
        /**模糊查询，在rel_info_list_str 中查询*/
        String queryStr = "\"relNumber\":" + "\"" + relNumber + "\"";
        param.put("relNumber", "%".concat(queryStr).concat("%"));
        param.put("tableName", "ins_tra_investment");
        /**投资入股类*/
        BigDecimal traInvestmentAmount = majorTraCheckMapper.sumAmount(param);
        /**资金运用类*/
        param.put("tableName", "ins_tra_funds");
        BigDecimal traFundsAmount = majorTraCheckMapper.sumAmount(param);
        /**保险业务类*/
        param.put("tableName", "ins_tra_business");
        BigDecimal traBusinessAmount = majorTraCheckMapper.sumAmount(param);
        /**利益转移类*/
        param.put("tableName", "ins_tra_interest");
        BigDecimal traInterestAmount = majorTraCheckMapper.sumAmount(param);
        /**提供货物或服务类*/
        param.put("tableName", "ins_tra_service");
        BigDecimal traServiceAmount = majorTraCheckMapper.sumAmount(param);
        /**其他类*/
        param.put("tableName", "ins_tra_other");
        BigDecimal traOtherAmount = majorTraCheckMapper.sumAmount(param);
        /**合并所有的金额*/
        BigDecimal totalTradeAmount = traInvestmentAmount.add(traFundsAmount).add(traBusinessAmount).add(traInterestAmount).add(traServiceAmount).add(traOtherAmount).setScale(6, BigDecimal.ROUND_HALF_UP);
        result.put("totalTradeAmount", totalTradeAmount);

        /**统计年度累计实际交易金额*/
        Map<String, Object> realTradeMap = sumRealTradeAmount(pid, relType);
        result.putAll(realTradeMap);
        return result;
    }

    /**
     * 统计年度累计实际交易金额
     *
     * @param pid
     * @param relType
     * @return
     */
    @Override
    public Map<String, Object> sumRealTradeAmount(Integer pid, String relType) {
        Map<String, Object> result = new HashMap<>();
        int year = DateUtil.year(new Date());
        Map<String, Object> param = new HashMap<>();
        /**年度*/
        param.put("year", String.valueOf(year).concat("%"));
        /**关联方证件relNumber*/
        String relNumber = null;
        if ("ORG".equals(relType)) {
            RelOrg relOrg = relOrgService.get(pid);
            relNumber = relOrg.getUniscCode();
        } else {
            RelPsn relPsn = relPsnService.get(pid);
            relNumber = relPsn.getIdentifyNumber();
        }
        /**免审/集中审议类型的实际交易金额+一般交易审议类型的已审批通过的审批金额,此处不计算本次交易的金额  此处取关联交易金额 rel_trade_amount*/
        /*
         * not_major_rel_number   未清零的关联方relNumber,多个使用,隔开，重大关联交易审批完成时需要维护此值
         */
        /**关联交易包含此关联方并且未经过清零的交易*/
        String queryStr = "\"relNumber\":" + "\"" + relNumber + "\"";
        param.put("relNumber", "%".concat(queryStr).concat("%"));
        param.put("notClear", relNumber);
        param.put("tableName", "ins_tra_investment");
        /**投资入股类,分成两个部分 */
        /**免审/集中审议类型的实际交易金额*/
        BigDecimal traInvestmentAmount = majorTraCheckMapper.sumNotClearAmount(param);
        /**一般交易审议类型的已审批通过的审批金额*/
        BigDecimal completeTraInvestmentAmount = majorTraCheckMapper.sumCompleteApprove(param);
        /**资金运用类*/
        param.put("tableName", "ins_tra_funds");
        BigDecimal traFundsAmount = majorTraCheckMapper.sumNotClearAmount(param);
        BigDecimal completeTraFundsAmount = majorTraCheckMapper.sumCompleteApprove(param);
        /**保险业务类*/
        param.put("tableName", "ins_tra_business");
        BigDecimal traBusinessAmount = majorTraCheckMapper.sumNotClearAmount(param);
        BigDecimal completeTraBusinessAmount = majorTraCheckMapper.sumCompleteApprove(param);
        /**利益转移类*/
        param.put("tableName", "ins_tra_interest");
        BigDecimal traInterestAmount = majorTraCheckMapper.sumNotClearAmount(param);
        BigDecimal completeTraInterestAmount = majorTraCheckMapper.sumCompleteApprove(param);
        /**提供货物或服务类*/
        param.put("tableName", "ins_tra_service");
        BigDecimal traServiceAmount = majorTraCheckMapper.sumNotClearAmount(param);
        BigDecimal completeTraServiceAmount = majorTraCheckMapper.sumCompleteApprove(param);
        /**其他类*/
        param.put("tableName", "ins_tra_other");
        BigDecimal traOtherAmount = majorTraCheckMapper.sumNotClearAmount(param);
        BigDecimal completeTraOtherAmount = majorTraCheckMapper.sumCompleteApprove(param);
        /**合并所有金额*/
        BigDecimal tradeZeroAmount = traInvestmentAmount.add(traFundsAmount).add(traBusinessAmount).add(traInterestAmount).add(traServiceAmount).add(traOtherAmount)
                .add(completeTraInvestmentAmount).add(completeTraFundsAmount).add(completeTraBusinessAmount).add(completeTraInterestAmount).add(completeTraServiceAmount).add(completeTraOtherAmount).setScale(6, BigDecimal.ROUND_HALF_UP);
        result.put("tradeZeroAmount", tradeZeroAmount);
        return result;
    }

    /**
     * 查询该关联方未归类的业务数据
     *
     * @param relNumber
     * @return
     */
    @Override
    public Integer hasUnassortedBus(String relNumber) {
        Map<String, Object> param = new HashMap<>();
        String queryStr = "\"relNumber\":" + "\"" + relNumber + "\"";
        param.put("relNumber", "%".concat(queryStr).concat("%"));
        return majorTraCheckMapper.hasUnassortedBus(param);
    }

    /**
     * 统计汇总年度总交易金额，未清零累计金额
     * @param pid
     * @param relType
     * @return
     */
    @Override
    public Map<String, Object> sumAmountNew(Integer pid, String relType,String tranType) {
        Map<String, Object> result = new HashMap<>();
        /**本年度与该关联方累计交易金额（万元） 此处取实际交易金额已归档和待确认的数据*/
        int year = DateUtil.year(new Date());
        Map<String, Object> param = new HashMap<>();
        /**年度*/
        param.put("year", year);
        /**关联方证件relNumber*/
        String relNumber = null;
        if ("ORG".equals(relType)) {
            RelOrg relOrg = relOrgService.get(pid);
            relNumber = relOrg.getUniscCode();
        } else {
            RelPsn relPsn = relPsnService.get(pid);
            relNumber = relPsn.getIdentifyNumber();
        }
        param.put("relType",relType);
        param.put("tranType",tranType);
        param.put("relNumber", relNumber);
        BigDecimal yearRelTotalAmount = majorTraCheckMapper.sumRelTradeAmount(param);
        //年度总资产，包括重大，累计重大，统一交易协议
        result.put("totalTradeAmount", yearRelTotalAmount);

        //统计年度未清零状态的关联交易金额,（排除重大和统一交易协议的）
        param.put("isAccumulated", Dict.SF_0);
        param.put("majorTrade", Dict.SF_0);
        param.put("isTradeAgreement", Dict.SF_0);
        BigDecimal tradeZeroAmount = majorTraCheckMapper.sumRelTradeAmount(param);
        result.put("tradeZeroAmount", tradeZeroAmount);
        return result;
    }
}
