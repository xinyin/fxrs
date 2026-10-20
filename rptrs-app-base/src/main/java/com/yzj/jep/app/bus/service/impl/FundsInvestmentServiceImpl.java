package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.mapper.FundsInvestmentMapper;
import com.yzj.jep.app.bus.model.FundsInvestment;
import com.yzj.jep.app.bus.service.FundsInvestmentService;
import com.yzj.jep.app.bus.vo.FundsInvestmentStaVo;
import com.yzj.jep.app.bus.vo.FundsInvestmentVo;
import com.yzj.jep.app.util.DateExtendUtil;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class FundsInvestmentServiceImpl extends MyBatisCRUDService<FundsInvestment, Integer> implements FundsInvestmentService {

    @Autowired
    private FundsInvestmentMapper fundsInvestmentMapper;

    @Override
    public Mapper<FundsInvestment, Integer> getMapper() {
        return fundsInvestmentMapper;
    }

    @Override
    public FundsInvestmentVo queryList(String quarter, String investmentBalanceTime) {
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
        FundsInvestmentVo fundsInvestmentVo=new FundsInvestmentVo();
        fundsInvestmentVo.setStartInvestmentBalanceTime(startDate);
        fundsInvestmentVo.setEndInvestmentBalanceTime(endDate);
        return fundsInvestmentVo;
    }

    @Override
    public FundsInvestmentStaVo queryStaList(String quarter, String investmentBalanceTime) {
        //开始时间
        String startInvestmentBalanceTime;
        //结束时间
        String endInvestmentBalanceTime;
        if (StringUtils.isBlank(investmentBalanceTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            Date date = new Date();
            investmentBalanceTime = sdf.format(date);
        }
        switch (quarter) {
            case "1":
                startInvestmentBalanceTime = investmentBalanceTime + "-01-01 00:00:00";
                endInvestmentBalanceTime = investmentBalanceTime + "-03-31 23:59:59";
                break;
            case "2":
                startInvestmentBalanceTime = investmentBalanceTime + "-04-01 00:00:00";
                endInvestmentBalanceTime = investmentBalanceTime + "-06-30 23:59:59";
                break;
            case "3":
                startInvestmentBalanceTime = investmentBalanceTime + "-07-01 00:00:00";
                endInvestmentBalanceTime = investmentBalanceTime + "-09-30 23:59:59";
                break;
            default:
                startInvestmentBalanceTime = investmentBalanceTime + "-10-01 00:00:00";
                endInvestmentBalanceTime = investmentBalanceTime + "-12-31 23:59:59";
                break;
        }
        FundsInvestmentStaVo fundsInvestmentStaVo = new FundsInvestmentStaVo();
        fundsInvestmentStaVo.setStartInvestmentBalanceTime(startInvestmentBalanceTime);
        fundsInvestmentStaVo.setEndInvestmentBalanceTime(endInvestmentBalanceTime);
        return fundsInvestmentStaVo;

    }

    @Override
    public FundsInvestment queryFundsInvestment() {
        return fundsInvestmentMapper.queryFundsInvestment();
    }

    @Override
    public FundsInvestmentVo checkBalance() {
        return fundsInvestmentMapper.checkBalance();
    }

    @Override
    public FundsInvestment selectFundsInvestment(String quarterBeginDate, String quarterEndDate) {
        return fundsInvestmentMapper.selectFundsInvestment(quarterBeginDate, quarterEndDate);
    }

    @Override
    public List<FundsInvestment> queryYearFunds(String year){
        return fundsInvestmentMapper.queryYearFunds(year);
    }

}
