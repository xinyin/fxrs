package com.yzj.jep.app.bus.mapper;

import com.yzj.jep.app.bus.model.ReportAnnual;
import com.yzj.jep.app.bus.model.ReportStatistical;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;

@MyBatisMapper
public interface Report_annualMapper {
    /*保险业务类*/
    public ReportAnnual selectBusiness(String starTime, String endTime, String code, String status);
    /*资金运用类*/
    public ReportAnnual selectFunds(String starTime, String endTime, String code, String status);
    /*利益转移类*/
    public ReportAnnual selectInterest(String starTime, String endTime, String code, String status);
    /*提供服务类*/
    public ReportAnnual selectService(String starTime, String endTime, String code, String status);
    /*投资入股类*/
    public ReportAnnual selectInvestment(String starTime, String endTime, String code, String status);
    /*其他类*/
    public ReportAnnual selectOther(String starTime, String endTime, String code, String status);
    /*查询各个表的记录数*/
    public ReportStatistical selectTableCount (String starTime, String endTime, String code, String status);
    /*查询总金额*/
    public ReportStatistical selectTableAmount (String starTime, String endTime, String code, String status);
}
