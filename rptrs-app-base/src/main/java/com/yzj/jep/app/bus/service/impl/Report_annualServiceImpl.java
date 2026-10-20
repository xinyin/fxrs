package com.yzj.jep.app.bus.service.impl;



import com.yzj.jep.app.bus.mapper.Report_annualMapper;
import com.yzj.jep.app.bus.model.ReportAnnual;
import com.yzj.jep.app.bus.model.ReportAnnualSum;
import com.yzj.jep.app.bus.model.ReportStatistical;
import com.yzj.jep.app.bus.service.Report_annualService;
import com.yzj.jep.commons.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class Report_annualServiceImpl implements Report_annualService {

    @Resource
    private Report_annualMapper report_annualMapper;

    private String firstQuarter = "第一季度";
    private String secondQuarter = "第二季度";
    private String thirdQuarter = "第三季度";
    private String fourthQuarter = "第四季度";
    /**
     * @desc 保险业务类
     * @param year
     * @return
     */
    public ReportAnnual selectBusiness(String year, String quarter, String code) {
        if (StringUtils.isEmpty(year)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
            Date date = new Date();
            year = sdf.format(date).substring(0,4);
        }
        String starTime = year + "-01-01";
        String endTime = year + "-12-31";
        if (quarter != null) {
            String time = quarterConversionMonth(quarter);
            if (time != null) {
                String[] times = time.split(",");
                starTime = year + times[0];
                endTime = year + times[1];
            }
        }
        //委托机构查询待审阅、已审阅、 中再资产只查询已归档 6：已归档 9：待审阅 8：已审阅
        String status = "'6','9','8'";
        if ("0001".equals(code) || "0001-ZZHK".equals(code)) {
            status = "6";
        }
        ReportAnnual annual = report_annualMapper.selectBusiness(starTime, endTime, code ,status);
        if (annual== null) {
            annual = new ReportAnnual("0", "0",
                    "0", "0" ,"0","0");
        }
        annual.setTranType("保险业务类");
        annual.setPid("1");
        annual.setYear(year);
        annual.setQuarter(quarter);
        return annual;
    }

    /**
     * @desc 资金运用类
     * @param year
     * @return
     */
    public ReportAnnual selectFunds(String year, String quarter, String code) {
        if (StringUtils.isEmpty(year)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
            Date date = new Date();
            year = sdf.format(date).substring(0,4);
        }
        String starTime = year + "-01-01";
        String endTime = year + "-12-31";
        if (quarter != null) {
            String time = quarterConversionMonth(quarter);
            if (time != null) {
                String[] times = time.split(",");
                starTime = year + times[0];
                endTime = year + times[1];
            }
        }
        //委托机构查询待审阅、已审阅、 中再资产只查询已归档 6：已归档 9：待审阅 8：已审阅
        String status = "'6','9','8'";
        if ("0001".equals(code) || "0001-ZZHK".equals(code)) {
            status = "6";
        }
        ReportAnnual annual = report_annualMapper.selectFunds(starTime, endTime, code, status);
        if (annual== null) {
            annual = new ReportAnnual("0", "0",
                    "0", "0" ,"0", "0");
        }
        annual.setTranType("资金运用类");
        annual.setPid("2");
        annual.setYear(year);
        annual.setQuarter(quarter);
        return annual;
    }

    /**
     * @desc 利益转移类
     * @param year
     * @return
     */
    public ReportAnnual selectInterest(String year, String quarter, String code) {
        if (StringUtils.isEmpty(year)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
            Date date = new Date();
            year = sdf.format(date).substring(0,4);
        }
        String starTime = year + "-01-01";
        String endTime = year + "-12-31";
        if (quarter != null) {
            String time = quarterConversionMonth(quarter);
            if (time != null) {
                String[] times = time.split(",");
                starTime = year + times[0];
                endTime = year + times[1];
            }
        }
        //委托机构查询待审阅、已审阅、 中再资产只查询已归档 6：已归档 9：待审阅 8：已审阅
        String status = "'6','9','8'";
        if ("0001".equals(code) || "0001-ZZHK".equals(code)) {
            status = "6";
        }
        ReportAnnual annual = report_annualMapper.selectInterest(starTime, endTime, code, status);
        if (annual== null) {
            annual = new ReportAnnual("0", "0",
                    "0", "0" ,"0", "0");
        }
        annual.setTranType("利益转移类");
        annual.setPid("3");
        annual.setYear(year);
        annual.setQuarter(quarter);
        return annual;
    }

    /**
     * @desc 提供服务类
     * @param year
     * @return
     */
    public ReportAnnual selectService(String year, String quarter, String code) {
        if (StringUtils.isEmpty(year)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
            Date date = new Date();
            year = sdf.format(date).substring(0,4);
        }
        String starTime = year + "-01-01";
        String endTime = year + "-12-31";
        if (quarter != null) {
            String time = quarterConversionMonth(quarter);
            if (time != null) {
                String[] times = time.split(",");
                starTime = year + times[0];
                endTime = year + times[1];
            }
        }
        //委托机构查询待审阅、已审阅、 中再资产只查询已归档 6：已归档 9：待审阅 8：已审阅
        String status = "'6','9','8'";
        if ("0001".equals(code) || "0001-ZZHK".equals(code)) {
            status = "6";
        }
        ReportAnnual annual = report_annualMapper.selectService(starTime, endTime, code, status);
        if (annual== null) {
            annual = new ReportAnnual("0", "0",
                    "0", "0" ,"0", "0");
        }
        annual.setTranType("提供货物或服务类");
        annual.setPid("4");
        annual.setYear(year);
        annual.setQuarter(quarter);
        return annual;
    }

    /**
     * @desc 投资入股类
     * @param year
     * @return
     */
    public ReportAnnual selectInvestment(String year, String quarter, String code) {
        if (StringUtils.isEmpty(year)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
            Date date = new Date();
            year = sdf.format(date).substring(0,4);
        }
        String starTime = year + "-01-01";
        String endTime = year + "-12-31";
        if (quarter != null) {
            String time = quarterConversionMonth(quarter);
            if (time != null) {
                String[] times = time.split(",");
                starTime = year + times[0];
                endTime = year + times[1];
            }
        }
        //委托机构查询待审阅、已审阅、 中再资产只查询已归档 6：已归档 9：待审阅 8：已审阅
        String status = "'6','9','8'";
        if ("0001".equals(code) || "0001-ZZHK".equals(code)) {
            status = "6";
        }
        ReportAnnual annual = report_annualMapper.selectInvestment(starTime, endTime, code, status);
        if (annual== null) {
            annual = new ReportAnnual("0", "0",
                    "0", "0" ,"0", "0");
        }
        annual.setTranType("投资入股类");
        annual.setPid("5");
        annual.setYear(year);
        annual.setQuarter(quarter);
        return annual;
    }

    /**
     * @desc 其他类
     * @param year
     * @return
     */
    public ReportAnnual selectOther(String year, String quarter, String code) {
        if (StringUtils.isEmpty(year)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
            Date date = new Date();
            year = sdf.format(date).substring(0,4);
        }
        String starTime = year + "-01-01";
        String endTime = year + "-12-31";
        if (quarter != null) {
            String time = quarterConversionMonth(quarter);
            if (time != null) {
                String[] times = time.split(",");
                starTime = year + times[0];
                endTime = year + times[1];
            }
        }

        //委托机构查询待审阅、已审阅、 中再资产只查询已归档 6：已归档 9：待审阅 8：已审阅
        String status = "'6','9','8'";
        if ("0001".equals(code) || "0001-ZZHK".equals(code)) {
            status = "6";
        }
        ReportAnnual annual = report_annualMapper.selectOther(starTime, endTime, code, status);
        if (annual== null) {
            annual = new ReportAnnual("0", "0",
                    "0", "0" ,"0", "0");
        }
        annual.setTranType("其他类");
        annual.setPid("6");
        annual.setYear(year);
        annual.setQuarter(quarter);
        return annual;
    }

    /**
     * @desc 查询各个表的记录数
     * @param year
     * @param quarter
     * @return
     */
    public ReportAnnualSum selectTableCount(String year, String quarter, String code) {
        if (year == null || year.trim() == "") {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
            Date date = new Date();
            year = sdf.format(date).substring(0,4);
        }
        String starTime = year + "-01-01";
        String endTime = year + "-12-31";
        if (quarter != null) {
            String time = quarterConversionMonth(quarter);
            if (time != null) {
                String[] times = time.split(",");
                starTime = year + times[0];
                endTime = year + times[1];
            }
        }
        //委托机构查询待审阅、已审阅、 中再资产只查询已归档 6：已归档 9：待审阅 8：已审阅
        String status = "'6','9','8'";
        if ("0001".equals(code) || "0001-ZZHK".equals(code)) {
            status = "6";
        }
        //查询记录数
        ReportStatistical result = report_annualMapper.selectTableCount(starTime, endTime, code, status);
        //查询表中的总金额
        ReportStatistical amount = report_annualMapper.selectTableAmount(starTime, endTime, code, status);
        ReportAnnualSum sum = new ReportAnnualSum();
        BigDecimal count = result.getBusinessCount().add(result.getFundsCount())
                .add(result.getInterestCount()).add(result.getInvestmentCount())
                .add(result.getOtherCount()).add(result.getServiceCount());
        BigDecimal amountSum = amount.getBusinessCount().add(amount.getFundsCount())
                .add(amount.getInterestCount()).add(amount.getInvestmentCount())
                .add(amount.getOtherCount()).add(amount.getServiceCount());
        sum.setTOTAL_TRADE_NUMBER(count.toString());
        sum.setTOTAL_TRADE_AMOUNT(amountSum.toString());
        return sum;
    }

    /**
     * @desc 将季度转换为对应月份
     */
    public String quarterConversionMonth (String quarter) {
        if (quarter == null) {
            return null;
        }

        if (firstQuarter.equals(quarter)) {
            return "-01-01,-03-31";
        }

        if (secondQuarter.equals(quarter)) {
            return "-04-01,-06-30";
        }

        if (thirdQuarter.equals(quarter)) {
            return "-07-01,-09-30";
        }

        if (fourthQuarter.equals(quarter)) {
            return "-10-01,-12-31";
        }
        return null;
    }
}
