package com.yzj.jep.app.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.yzj.jep.app.bus.model.RootOrgInfo;
import com.yzj.jep.app.bus.service.RootOrgInfoService;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.sys.service.AppConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * @author by Liqiang
 * @Date 2022/11/18
 * @Description 获取资金运用类限额
 */
public class ObtainFundsLimit {

    private static final Logger logger = LoggerFactory.getLogger(ObtainFundsLimit.class);

    private static final RootOrgInfoService rootOrgInfoService = SpringContextHolder.getBean(RootOrgInfoService.class);


    private static final AppConfigService appConfigService = SpringContextHolder.getBean(AppConfigService.class);

    private static final Integer YEARS = DateUtil.year(new Date()) - 10;

    public static Map<String, BigDecimal> getLimitRule(Integer year, Integer quarter) {

        Map<String, BigDecimal> result = new HashMap<>(7);

        String baseAmt = appConfigService.getConfigValue("major_base_amt");

        if (StringUtils.isEmpty(baseAmt)) {
            baseAmt = "3000";
        }
        BigDecimal limit01 = BigDecimal.ZERO;
        BigDecimal limit02 = BigDecimal.ZERO;
        BigDecimal limit03 = BigDecimal.ZERO;
        BigDecimal limit04 = BigDecimal.ZERO;
        BigDecimal limit05 = BigDecimal.ZERO;
        BigDecimal limit06 = BigDecimal.ZERO;
        BigDecimal limit07 = BigDecimal.ZERO;


        try {
            /*
             * 获取上年度基础数据
             */
            RootOrgInfo yearRoot = getYearRootInfo(year - 1, 5);
            // 上年末净资产
            BigDecimal netCapitalYear = BigDecimal.ZERO;
            // 上年末总资产
            BigDecimal totalCapitalYear = BigDecimal.ZERO;
            if (null != yearRoot) {
                netCapitalYear = yearRoot.getNetCaptial();
                totalCapitalYear = yearRoot.getTotalCapital();
                /*
                 * 投资单一关联方的限额	保险机构投资单一关联方的账面余额，合计不得超过保险机构上一年度末净资产的30%	此处应自动计算，即上年度末净资产*30%
                 */
                limit07 = yearRoot.getNetCaptial().multiply(new BigDecimal("0.3"));
            }
            /*
             * 获取上季度基础数据
             */
            List<Filter> filters = new ArrayList<>();
            if (quarter.equals(1)) {
                year = year - 1;
                quarter = 4;
            } else {
                quarter = quarter - 1;
            }
            filters.add(new Filter("delflag", Operator.EQ, "1"));
            filters.add(new Filter("years", Operator.EQ, year));
            filters.add(new Filter("quarter", Operator.EQ, quarter));
            List<RootOrgInfo> quarterRootInfo = rootOrgInfoService.select(filters);

            /* 权益类资产投资上限比例 */
            String msg = year + "年，第" + quarter + "季度机构基础数据不存在，请维护后在使用！";
            Assert.isTrue(!CollUtil.isEmpty(quarterRootInfo), msg);
            BigDecimal equityUpperLimitScale = BigDecimal.ONE;
            try {
                equityUpperLimitScale = new BigDecimal(quarterRootInfo.get(0).getEquityUpperLimitScale()).divide(new BigDecimal(100), 4, RoundingMode.HALF_UP);
            } catch (Exception e) {
                logger.error("进行权益类资产投资上限比例数值转换失败：" + e);
            }
            /*
             * 重大关联交易标准	保险机构重大关联交易是指保险机构与单个关联方之间单笔或年度累计交易金额达到3000万元以上，且占保险机构上一年度末经审计的净资产的1%以上的交易。	此处应自动计算，即Max[上年末净资产*1%,3000万]
             */
            // 上年末净资产的1%
            BigDecimal netCapitalYear1 = netCapitalYear.multiply(new BigDecimal("0.01"));
            limit01 = max(netCapitalYear1, new BigDecimal(baseAmt));
            /*
             * 对全部关联方的投资限额	保险机构投资全部关联方的账面余额，合计不得超过保险机构上一年度末总资产的25%与上一年度末净资产二者中的金额较低者；	此处应自动计算，即Min[上年总资产*25%,上年末净资产]
             */
            // 上年末总资产的25%
            BigDecimal totalCapitalYear25 = totalCapitalYear.multiply(new BigDecimal("0.25"));
            limit02 = min(totalCapitalYear25, netCapitalYear);
            /*
             * 投资关联方权益类资产限额	保险机构投资权益类资产的账面余额中，对关联方的投资金额不得超过大类资产投资限额的30%；	此处自动计算，∑协议（合同）金额<=上季度总资产根据人工填报的权益类投资上限比例*30%。
             */
            BigDecimal endLastQuarterReduce;
            if (CollUtil.isNotEmpty(quarterRootInfo)) {
                endLastQuarterReduce = ObjectUtil.isNotEmpty(quarterRootInfo.get(0).getTotalCapital()) ? quarterRootInfo.get(0).getTotalCapital() : BigDecimal.ZERO;
                limit03 = endLastQuarterReduce.multiply(new BigDecimal("0.3")).multiply(equityUpperLimitScale);
                /*
                 * 投资关联方不动产类资产限额（非标）	未在银行间市场、证券交易所市场等国务院同意设立的交易市场交易的资产，合计不高于本公司上季末总资产的25%	此处应自动计算，∑协议（合同）金额<=上季度总资产-回购-投连*30%*25%（上季末总资产的7.5%）
                 */
                limit04 = endLastQuarterReduce.multiply(new BigDecimal("0.3")).multiply(new BigDecimal("0.25"));
                /*
                 * 投资关联方其他金融资产限额（非标）	未在银行间市场、证券交易所市场等国务院同意设立的交易市场交易的资产，合计不高于本公司上季末总资产的20%。	此处应自动计算，∑协议（合同）金额<=上季度总资产-回购-投连*20%*30%（上季末总资产的6%）
                 */
                limit05 = endLastQuarterReduce.multiply(new BigDecimal("0.3")).multiply(new BigDecimal("0.20"));
                /*
                 * 投资关联方境外投资限额	保险机构境外投资的账面余额中，对关联方的投资金额不得超过大类资产投资限额的30%；	此处应自动计算，∑协议（合同）金额<=上季度总资产-回购-投连*15%*30%（上季末总资产的4.5%）
                 */
                limit06 = endLastQuarterReduce.multiply(new BigDecimal("0.3")).multiply(new BigDecimal("0.15"));
            }
        } catch (Exception e) {
            logger.error("查询限额数据失败：" + e);
        }
        result.put("limit01", changeFormat(limit01));
        result.put("limit02", changeFormat(limit02));
        result.put("limit03", changeFormat(limit03));
        result.put("limit04", changeFormat(limit04));
        result.put("limit05", changeFormat(limit05));
        result.put("limit06", changeFormat(limit06));
        result.put("limit07", changeFormat(limit07));

        return result;
    }


    /**
     * 获取年度基本数据
     */
    public static RootOrgInfo getYearRootInfo(Integer year, Integer quarter) {
        RootOrgInfo result;
        result = rootOrgInfoService.selectRootInfo(year, 5);
        if (null == result) {
            year = year - 1;
            if (year < (YEARS)) {
                return result;
            }
            result = getYearRootInfo(year, quarter);
        }
        return result;
    }

    /**
     * 取最大值
     */
    public static BigDecimal max(BigDecimal a, BigDecimal b) {
        if (a.compareTo(b) > 0) {
            return a;
        }
        return b;
    }


    /**
     * 取最小值
     */
    public static BigDecimal min(BigDecimal a, BigDecimal b) {
        if (a.compareTo(b) > 0) {
            return b;
        }
        return a;
    }

    /**
     * 转换小数点个数
     * @param amount            金额
     * @return                  返回结果
     */
    private static BigDecimal changeFormat(BigDecimal amount){
        if(amount.compareTo(BigDecimal.ZERO) > 0){
            return amount.setScale(4, RoundingMode.HALF_UP);
        }
        return amount;
    }
}
