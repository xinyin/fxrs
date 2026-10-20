/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月02日 14:30:54
 *
 */

package com.yzj.jep.app.sub.mapper;

import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.app.sub.model.Loan;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@MyBatisMapper
public interface LoanMapper extends Mapper<Loan, Integer>{
    public BigDecimal getLoan(@Param("no") String no, @Param("start") Date start, @Param("end") Date end);
    public BigDecimal getOrgLoan(@Param("no") String no, @Param("start") Date start, @Param("end") Date end);
    public BigDecimal getBondInvestment(@Param("no") String no, @Param("start") Date start, @Param("end") Date end);
    public BigDecimal getOrgBondInvestment(@Param("no") String no, @Param("start") Date start, @Param("end") Date end);
    public BigDecimal getInvestment(@Param("no") String no, @Param("start") Date start, @Param("end") Date end);
    public BigDecimal getOrgInvestment(@Param("no") String no, @Param("start") Date start, @Param("end") Date end);
    public BigDecimal getOtherinnercredit(@Param("no") String no, @Param("start") Date start, @Param("end") Date end);
    public BigDecimal getOrgOtherinnercredit(@Param("no") String no, @Param("start") Date start, @Param("end") Date end);
    public BigDecimal getIrrevocablecommitments(@Param("no") String no, @Param("start") Date start, @Param("end") Date end);
    public BigDecimal getOrgIrrevocablecommitments(@Param("no") String no, @Param("start") Date start, @Param("end") Date end);
    public BigDecimal getNonbreakevenfinancial(@Param("no") String no, @Param("start") Date start, @Param("end") Date end);
    public BigDecimal getOrgNonbreakevenfinancial(@Param("no") String no, @Param("start") Date start, @Param("end") Date end);
    public BigDecimal getOtheroutcredit(@Param("no") String no, @Param("start") Date start, @Param("end") Date end);
    public BigDecimal getOrgOtheroutcredit(@Param("no") String no, @Param("start") Date start, @Param("end") Date end);
    public List<BigDecimal> getMaxCreditBalance(@Param("no") String no, @Param("start") Date start, @Param("end") Date end);
    public List<BigDecimal> getOrgMaxCreditBalance(@Param("no") String no, @Param("start") Date start, @Param("end") Date end);
}