package com.yzj.jep.app.ex.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.yzj.jep.app.ins.model.TraBusiness;
import com.yzj.jep.app.ins.model.TraFunds;
import com.yzj.jep.app.ins.model.TraInterest;
import com.yzj.jep.app.ins.model.TraService;
import com.yzj.jep.app.ins.service.*;
import com.yzj.jep.app.util.Dict;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @title: 清空累积重大的数据
 * @Author Whx
 * @Date: 2021/8/23 9:17
 * @Version 1.0
 */
public class ClearAccumulatedAmountImpl {

    public static final TraInvestmentService traInvestmentService = SpringContextHolder.getBean(TraInvestmentService.class);
    public static final TraFundsService traFundsService = SpringContextHolder.getBean(TraFundsService.class);
    public static final TraServiceService traServiceService = SpringContextHolder.getBean(TraServiceService.class);
    public static final TraInterestService traInterestService = SpringContextHolder.getBean(TraInterestService.class);
    public static final TraOtherService traOtherService = SpringContextHolder.getBean(TraOtherService.class);
    public static final TraBusinessService traBusinessService = SpringContextHolder.getBean(TraBusinessService.class);

    /**
     * 清空累积重大金额
     *
     * @param pid  主键
     * @param type 业务类型
     */
    public static void clearAccumulatedAmount(Integer pid, String type) {
        if (CharSequenceUtil.isBlank(type)) {
            return;
        }
        /* 达到重大关联交易的情形   1:单笔达到，2：累积达到，3：未达到 */
        String majorType = null;
        String relNumber = null;
        switch (type) {
            case Dict.TRAN_TYPE_NAME_FUNDS:
                TraFunds traFunds = traFundsService.get(pid);
                majorType = traFunds.getMajorType();
                relNumber = traFunds.getRelNumber();
                break;
            case Dict.TRAN_TYPE_NAME_BUSINESS:
                TraBusiness traBusiness = traBusinessService.get(pid);
                majorType = traBusiness.getMajorType();
                relNumber = traBusiness.getRelNumber();
                break;
            case Dict.TRAN_TYPE_NAME_INTEREST:
                TraInterest traInterest = traInterestService.get(pid);
                majorType = traInterest.getMajorType();
                relNumber = traInterest.getRelNumber();
                break;
            case Dict.TRAN_TYPE_NAME_SERVICE:
                TraService traService = traServiceService.get(pid);
                majorType = traService.getMajorType();
                relNumber = traService.getRelNumber();
                break;
            default:
        }
        /*只清空累积重大的情形*/
        if ("2".equals(majorType)) {
            doClear(relNumber);
        }
    }

    /**
     * 该关联方的所有交易都为已清零
     *
     * @param relNumber 身份证号或者社会信用统一代码
     */
    private static void doClear(String relNumber) {
        List<Filter> filters = new ArrayList<>();
        int year = DateUtil.year(new Date());
        filters.add(new Filter("delflag", Operator.EQ, "1"));
        filters.add(new Filter("relNumber", Operator.EQ, relNumber));
        //已归档，待确认
        filters.add(new Filter("status", Operator.IN, Arrays.asList("6", "7", "8")));
        filters.add(new Filter("modifiedTime", Operator.LIKE, String.valueOf(year)));
        /*资金运用*/
        List<TraFunds> traFundsList = traFundsService.select(filters);
        if (CollectionUtils.isNotEmpty(traFundsList)) {
            for (TraFunds traFunds : traFundsList) {
                traFunds.setIsAccumulated("1");
                traFundsService.updateSelective(traFunds);
            }
        }

        List<Filter> filter1 = new ArrayList<>();
        filter1.add(new Filter("delflag", Operator.EQ, "1"));
        filter1.add(new Filter("relNumber", Operator.EQ, relNumber));
        filter1.add(new Filter("status", Operator.IN, Arrays.asList("6", "7", "8")));
        filter1.add(new Filter("modifiedTime", Operator.LIKE, String.valueOf(year)));
        /*利益转移类*/
        List<TraInterest> traInterestList = traInterestService.select(filter1);
        if (CollectionUtils.isNotEmpty(traInterestList)) {
            for (TraInterest traInterest : traInterestList) {
                traInterest.setIsAccumulated("1");
                traInterestService.updateSelective(traInterest);
            }
        }

        /*服务类*/
        List<TraService> traServiceList = traServiceService.select(filter1);
        if (CollectionUtils.isNotEmpty(traServiceList)) {
            for (TraService traService : traServiceList) {
                traService.setIsAccumulated("1");
                traServiceService.updateSelective(traService);
            }
        }
        /*保险业务类*/
        List<TraBusiness> traBusinessList = traBusinessService.select(filter1);
        if (CollectionUtils.isNotEmpty(traBusinessList)) {
            for (TraBusiness traBusiness : traBusinessList) {
                traBusiness.setIsAccumulated("1");
                traBusinessService.updateSelective(traBusiness);
            }
        }
    }
}
