package com.yzj.jep.app.bus.service;

import com.yzj.jep.app.bus.model.ReportAnnual;
import com.yzj.jep.app.bus.model.ReportAnnualSum;

public interface Report_annualService {
    /**
     * @desc 保险业务类
     * @param year
     * @return
     */
    public ReportAnnual selectBusiness(String year, String quarter, String code);

    /**
     * @desc 资金运用类
     * @param year
     * @return
     */
    public ReportAnnual selectFunds(String year, String quarter, String code);

    /**
     * @desc 利益转移类
     * @param year
     * @return
     */
    public ReportAnnual selectInterest(String year, String quarter, String code);

    /**
     * @desc 提供服务类
     * @param year
     * @return
     */
    public ReportAnnual selectService(String year, String quarter, String code);

    /**
     * @desc 投资入股类
     * @param year
     * @return
     */
    public ReportAnnual selectInvestment(String year, String quarter, String code);

    /**
     * @desc 其他类
     * @param year
     * @return
     */
    public ReportAnnual selectOther(String year, String quarter, String code);
    /**
     * @desc 查询各个表的记录数
     * @param year
     * @param quarter
     * @return
     */
    public ReportAnnualSum selectTableCount(String year, String quarter, String code);
    /**
     * @desc 将季度转换为对应月份
     */
    public String quarterConversionMonth (String quarter);
}
