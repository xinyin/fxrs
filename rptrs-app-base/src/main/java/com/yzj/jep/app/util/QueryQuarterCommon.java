package com.yzj.jep.app.util;

import com.yzj.jep.app.ins.constants.TranTypeConstant;
import com.yzj.jep.app.ins.model.QuarterlyVo;
import com.yzj.jep.app.ins.model.TraInvestment;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * @author by Liqiang
 * @Date 2022/11/15
 * @Description 查询数据报告通用方法
 */
public class QueryQuarterCommon {

    /**
     * 查询关联交易汇总后数据
     * @param list              原数据
     * @return                  汇总后数据
     */
    public static List<QuarterlyVo> queryQuarterTranData(List<TraInvestment> list){
        List<QuarterlyVo> quarterlyVos = new ArrayList<>();
        QuarterlyVo quarterlyVoFunds = new QuarterlyVo();
        QuarterlyVo quarterlyVoBusines = new QuarterlyVo();
        QuarterlyVo quarterlyVoInterest = new QuarterlyVo();
        QuarterlyVo quarterlyVoService = new QuarterlyVo();
        BigDecimal sunAmount = BigDecimal.ZERO;
        int sumNumber = 0;
        for (TraInvestment traInvestment : list) {
            BigDecimal pracTradeAmount = BigDecimal.ZERO;
            int number = traInvestment.getPid();
            if (StringUtils.isNotBlank(traInvestment.getPracTradeAmount())) {
                pracTradeAmount = new BigDecimal(traInvestment.getPracTradeAmount()).setScale(4, RoundingMode.HALF_UP);
            }
            if (TranTypeConstant.TRAN_TYPE_1.equals(traInvestment.getTranType())) {
                quarterlyVoFunds.setAmount(pracTradeAmount);
                quarterlyVoFunds.setTranType("资金运用类");
                quarterlyVoFunds.setNumber(traInvestment.getPid());
            }
            if (TranTypeConstant.TRAN_TYPE_2.equals(traInvestment.getTranType())) {
                quarterlyVoService.setAmount(pracTradeAmount);
                quarterlyVoService.setTranType("服务类");
                quarterlyVoService.setNumber(traInvestment.getPid());
            }
            if (TranTypeConstant.TRAN_TYPE_3.equals(traInvestment.getTranType())) {
                quarterlyVoInterest.setAmount(pracTradeAmount);
                quarterlyVoInterest.setTranType("利益转移类");
                quarterlyVoInterest.setNumber(traInvestment.getPid());
            }
            if (TranTypeConstant.TRAN_TYPE_4.equals(traInvestment.getTranType())) {
                quarterlyVoBusines.setAmount(pracTradeAmount);
                quarterlyVoBusines.setTranType("保险业务和其他类");
                quarterlyVoBusines.setNumber(traInvestment.getPid());
            }
            sunAmount = sunAmount.add(pracTradeAmount);
            sumNumber = sumNumber + number;
        }

        QuarterlyVo quarterlyVoSum = new QuarterlyVo();
        quarterlyVoSum.setTranType("总计");
        quarterlyVoSum.setNumber(sumNumber);
        quarterlyVoSum.setAmount(sunAmount);

        quarterlyVoSum.setProprotion("100.00%");
        quarterlyVoFunds.setProprotion(changeStr(quarterlyVoFunds.getAmount(), sunAmount));
        quarterlyVoService.setProprotion(changeStr(quarterlyVoService.getAmount(), sunAmount));
        quarterlyVoInterest.setProprotion(changeStr(quarterlyVoInterest.getAmount(), sunAmount));
        quarterlyVoBusines.setProprotion(changeStr(quarterlyVoBusines.getAmount(), sunAmount));

        quarterlyVos.add(quarterlyVoFunds);
        quarterlyVos.add(quarterlyVoService);
        quarterlyVos.add(quarterlyVoInterest);
        quarterlyVos.add(quarterlyVoBusines);
        quarterlyVos.add(quarterlyVoSum);

        return quarterlyVos;
    }

    /**
     *  字符转换
     * @param pracTradeAmount       关联交易实际金额
     * @param sumAmount             关联交易总金额
     * @return                      返回拼接的字符
     */
    private static String changeStr(BigDecimal pracTradeAmount, BigDecimal sumAmount){
        String proportion;
        if(pracTradeAmount.compareTo(BigDecimal.ZERO)==0){
            proportion = "0.00%";
        }else {
            BigDecimal proportionAmount = pracTradeAmount.divide(sumAmount, 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
            proportion = proportionAmount + "%";
        }
        return proportion;
    }
}
