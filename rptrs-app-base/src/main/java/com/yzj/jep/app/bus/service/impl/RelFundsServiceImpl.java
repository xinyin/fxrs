package com.yzj.jep.app.bus.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.yzj.jep.app.bus.mapper.RelFundsMapper;
import com.yzj.jep.app.bus.model.RelFunds;
import com.yzj.jep.app.bus.service.RelFundsService;
import com.yzj.jep.app.bus.vo.RelFundsVo;
import com.yzj.jep.app.util.DateExtendUtil;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class RelFundsServiceImpl extends MyBatisCRUDService<RelFunds, Integer> implements RelFundsService {

    @Autowired
    private RelFundsMapper relFundsMapper;

    @Override
    public Mapper<RelFunds, Integer> getMapper() {
        return relFundsMapper;
    }

    @Override
    public RelFundsVo queryList(String investmentBalanceTime, String quarter) {
        //开始时间
        String startDate;
        //结束时间
        String endDate;

        if (StringUtils.isBlank(investmentBalanceTime)) {
            startDate = DateExtendUtil.getStartDateTimeOfYear();
            endDate = DateExtendUtil.getEndDateTimeOfYear();
        } else {
            if ("0".equals(quarter)) {
                startDate = investmentBalanceTime + "-01-01 00:00:00";
                endDate = investmentBalanceTime + "-12-31 23:59:59";
            } else {
                // 获取月份的起始日期和终止日期
                startDate = DateExtendUtil.getStartDateTimeOfMonth(investmentBalanceTime, quarter);
                endDate = DateExtendUtil.getEndDateTimeOfMonth(investmentBalanceTime, quarter);
            }
        }
        RelFundsVo relFundsVo = new RelFundsVo();
        relFundsVo.setStartInvestmentBalanceTime(startDate);
        relFundsVo.setEndInvestmentBalanceTime(endDate);
        return relFundsVo;

    }

    /**
     * 统计对所有关联交易的投资余额
     * @return
     */
    @Override
    public BigDecimal sumRelTraBalance() {
        return relFundsMapper.sumRelTraBalance();
    }

    @Override
    public BigDecimal sumAmountByRelId(String relId,String relType) {
        return relFundsMapper.sumAmountByRelId(relId,relType);
    }
    public List<RelFunds> queryYearFunds(String year){
        System.out.println(relFundsMapper.queryYearFunds(year));
        return relFundsMapper.queryYearFunds(year);
    }

    @Override
    public boolean checkExistRelated(String startTime, String endTime, String relNumber) {
        List<RelFunds>  relFundsList = relFundsMapper.checkExistRelated(startTime, endTime, relNumber);
        return CollUtil.isEmpty(relFundsList);
    }

    @Override
    public RelFunds selectRelFunds(String quarterBeginDate, String quarterEndDate) {
        return relFundsMapper.selectRelFunds(quarterBeginDate, quarterEndDate);
    }

}
