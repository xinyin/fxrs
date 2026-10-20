package com.yzj.service;

import com.yzj.beans.Report_annual;
import com.yzj.beans.Report_annualSum;
import com.yzj.beans.Report_statistical;
import com.yzj.dao.Report_annualMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class Report_annualService {
    @Autowired
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
    public Report_annual selectBusiness(String year, String quarter, String code) {
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
        Report_annual list = report_annualMapper.selectBusiness(starTime, endTime, code);
        return list;
    }

    /**
     * @desc 资金运用类
     * @param year
     * @return
     */
    public Report_annual selectFunds(String year, String quarter, String code) {
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
        Report_annual list = report_annualMapper.selectFunds(starTime, endTime, code);
        return list;
    }

    /**
     * @desc 利益转移类
     * @param year
     * @return
     */
    public Report_annual selectInterest(String year, String quarter, String code) {
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
        Report_annual list = report_annualMapper.selectInterest(starTime, endTime, code);
        return list;
    }

    /**
     * @desc 提供服务类
     * @param year
     * @return
     */
    public Report_annual selectService(String year, String quarter, String code) {
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
        Report_annual list = report_annualMapper.selectService(starTime, endTime, code);
        return list;
    }

    /**
     * @desc 投资入股类
     * @param year
     * @return
     */
    public Report_annual selectInvestment(String year, String quarter, String code) {
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
        Report_annual list = report_annualMapper.selectInvestment(starTime, endTime, code);
        return list;
    }

    /**
     * @desc 其他类
     * @param year
     * @return
     */
    public Report_annual selectOther(String year, String quarter, String code) {
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

        Report_annual list = report_annualMapper.selectOther(starTime, endTime, code);
        return list;
    }

    /**
     * @desc 查询各个表的记录数
     * @param year
     * @param quarter
     * @return
     */
    public Report_annualSum selectTableCount(String year, String quarter, String code) {
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
        //查询记录数
        Report_statistical result = report_annualMapper.selectTableCount(starTime, endTime, code);
        //查询表中的总金额
        Report_statistical amount = report_annualMapper.selectTableAmount(starTime, endTime, code);
        Report_annualSum sum = new Report_annualSum();
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
    private String quarterConversionMonth (String quarter) {
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
