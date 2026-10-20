/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月02日 15:45:49
 *
 */

package com.yzj.jep.app.sub.service.impl;

import com.yzj.jep.app.bus.model.TraUsecredit;
import com.yzj.jep.app.bus.service.TraUsecreditService;
import com.yzj.jep.app.sub.mapper.LoanMapper;
import com.yzj.jep.app.sub.model.Loan;
import com.yzj.jep.app.sub.service.LoanService;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service(value = "LoanServiceImpl")
@Transactional
public class LoanServiceImpl extends MyBatisCRUDService<Loan, Integer> implements LoanService {

    Logger logger = LoggerFactory.getLogger(LoanServiceImpl.class);

    @Autowired
    private LoanMapper loanMapper;

    @Autowired
    private TraUsecreditService traUsecreditService;

    @Override
    public Mapper<Loan, Integer> getMapper() {
        return loanMapper;
    }

    @Override
    public void extractLoan(Map map, String nowDate) {
        String tag = (String) map.get("tag");
        List<TraUsecredit> traUsecredits = new ArrayList<>();
        Page<TraUsecredit> page = new Page<>(1, 100);
        PageHelper.start(page);
        if ("all".equals(tag)) {
            traUsecredits = traUsecreditService.selectAll();
        } else {
            List<Filter> filters = new Filter.Builder().like("modifiedTime", nowDate).build();
            traUsecredits = traUsecreditService.select(filters);
        }
        PageHelper.end(page, traUsecredits);
        //总页数
        int pageSize = page.getPages();
        List<TraUsecredit> exTraUsecredits = page.getResult();
        //开始抽取
        startExtract(exTraUsecredits);
        for (int i = 2; i <= pageSize; i++) {
            //从第二页开始循环
            page = new Page<>(i, 100);
            PageHelper.start(page);
            if ("all".equals(tag)) {
                traUsecredits = traUsecreditService.selectAll();
            } else {
                List<Filter> filters = new Filter.Builder().like("modifiedTime", nowDate).build();
                traUsecredits = traUsecreditService.select(filters);
            }
            PageHelper.end(page, traUsecredits);
            exTraUsecredits = page.getResult();
            //开始抽取
            startExtract(exTraUsecredits);
        }
    }

    private void startExtract(List<TraUsecredit> traUsecredits) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (TraUsecredit traUsecredit : traUsecredits) {
            Loan loan = new Loan();
            BeanUtils.copyProperties(traUsecredit, loan);
            loan.setRelatedPartyName(traUsecredit.getRelatedName());
            loan.setRelatedPartyCertNo(traUsecredit.getIdentNumber());
            loan.setRelatedPartyType(traUsecredit.getRelType());
            loan.setCreditNo(traUsecredit.getInternalApplyNo());
            loan.setTransContractNo(traUsecredit.getUseCreditNum());
            try {
                loan.setSignDate(traUsecredit.getContractSignDate() == null ? null : sdf.parse(traUsecredit.getContractSignDate()));
                loan.setTransStartDate(traUsecredit.getBusinessStartTime() == null ? null : sdf.parse(traUsecredit.getBusinessStartTime()));
                loan.setTransEndDate(traUsecredit.getBusinessEndTime() == null ? null : sdf.parse(traUsecredit.getBusinessEndTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            loan.setCreditType(traUsecredit.getBusinessType());
            loan.setTransPrice(traUsecredit.getTraPrice() == null ? null : new BigDecimal(traUsecredit.getTraPrice()));
            loan.setLpr(traUsecredit.getFloatRateBench());
            loan.setSpreads(traUsecredit.getFloatInterestRateSpread() == null ? null : new BigDecimal(traUsecredit.getFloatInterestRateSpread()));
            loan.setRateDiscount(traUsecredit.getFloatRateDiscountRate() == null ? null : new BigDecimal(traUsecredit.getFloatRateDiscountRate()));
            loan.setUsedFor(traUsecredit.getFundsUse());
            loan.setProductDesc(traUsecredit.getTargetsInvestmentDesc());
            loan.setProductName(traUsecredit.getFinancialProduct());
            loan.setProductIssueNo(traUsecredit.getProductRegNum());
            loan.setProductUsedFor(traUsecredit.getCapitalInvestment());
            //交易合同金额未找到，暂用本笔交易金额
            loan.setContractAmount(traUsecredit.getThisTraAmount());
            loan.setBalance(traUsecredit.getThisTraBalance());
            loan.setProductReleatedAmount(traUsecredit.getInvestmentAmount());
            //暂无对应字段
            loan.setPricingMark("无");
            loan.setStatus("0");
            loan.setStatusComment("数据已抽取");
            List<Filter> filters = new Filter.Builder().eq("pid", loan.getPid()).build();
            try {
                Loan dbLoan = this.get(filters);
                if (dbLoan != null) {
                    this.update(loan);
                } else {
                    this.insert(loan);
                }
            } catch (ServiceException e) {
                logger.error("用信明细[" + loan.getCreditNo() + "|" + loan.getTransContractNo() + "]数据更新异常：", e);
            }
        }
    }
}