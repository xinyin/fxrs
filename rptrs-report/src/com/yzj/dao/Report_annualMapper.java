package com.yzj.dao;

import com.yzj.beans.Report_annual;
import com.yzj.beans.Report_statistical;
import org.springframework.stereotype.Service;

import java.util.List;

public interface Report_annualMapper {
    /*保险业务类*/
    public Report_annual selectBusiness(String starTime, String endTime, String code);
    /*资金运用类*/
    public Report_annual selectFunds(String starTime, String endTime, String code);
    /*利益转移类*/
    public Report_annual selectInterest(String starTime, String endTime, String code);
    /*提供服务类*/
    public Report_annual selectService(String starTime, String endTime, String code);
    /*投资入股类*/
    public Report_annual selectInvestment(String starTime, String endTime, String code);
    /*其他类*/
    public Report_annual selectOther(String starTime, String endTime, String code);
    /*查询各个表的记录数*/
    public Report_statistical selectTableCount (String starTime, String endTime, String code);
    /*查询总金额*/
    public Report_statistical selectTableAmount (String starTime, String endTime, String code);
}
