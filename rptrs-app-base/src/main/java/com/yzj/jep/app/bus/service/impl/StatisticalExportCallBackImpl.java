package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.model.ReportAnnual;
import com.yzj.jep.app.bus.service.Report_annualService;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Service(value = StatisticalExportCallBackImpl.BEANID)
public class StatisticalExportCallBackImpl implements XlsExportCallback {
    public static final String BEANID = "StatisticalExportCallBackImpl";
    private static final String EXPORT_TEMPLATE_NAME = "Statistical.xls";

    @Resource
    private Report_annualService report_annualService;

    /**
     * @desc 文件路径
     * @return
     */
    @Override
    public String getExportTemplate() {
        return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_NAME;
    }

    /**
     * @desc 文件名
     * @param parameters
     *            URL参数集合
     * @return
     * @throws ServiceException
     */
    @Override
    public String getExportFilename(Map parameters) throws ServiceException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String fileName = "统计分析_" + sf.format(new Date()) + ".xls";
        return fileName;
    }

    @Override
    public Map<Integer, List<int[]>> getMergeColumns() {
        return null;
    }

    @Override
    public Map<Integer, Integer> getMergeStartRowNum() {
        return null;
    }

    /**
     * @desc 业务代码
     * @param parameters
     * @return
     * @throws ServiceException
     */
    @Override
    public Map executeExportCallback(Map parameters) throws ServiceException {
        Map result = new HashMap();
        List<ReportAnnual> list = new ArrayList<>();
        String data = (String)parameters.get("id");
        String[] param = data.split(",");
        String year = param[0];
        String quarter = param[1];
        String code = UserProfileUtils.getUserProfile().getUnitCode();
        ReportAnnual businessAnnual = report_annualService.selectBusiness(year, quarter, code);
        ReportAnnual fundsAnnual = report_annualService.selectFunds(year, quarter, code);
        ReportAnnual interestAnnual = report_annualService.selectInterest(year, quarter, code);
        ReportAnnual investmentAnnual = report_annualService.selectInvestment(year, quarter,code);
        ReportAnnual otherAnnual = report_annualService.selectOther(year, quarter, code);
        ReportAnnual serviceAnnual = report_annualService.selectService(year, quarter, code);
        list.add(businessAnnual);
        list.add(fundsAnnual);
        list.add(interestAnnual);
        list.add(investmentAnnual);
        list.add(otherAnnual);
        list.add(serviceAnnual);
        result.put("statistical", list);
        return result;
    }

    @Override
    public Map executeExportCallback(HttpServletRequest request) throws ServiceException {
        return null;
    }

    @Override
    public void afterTransformXLS(Workbook workbook) {

    }
}
