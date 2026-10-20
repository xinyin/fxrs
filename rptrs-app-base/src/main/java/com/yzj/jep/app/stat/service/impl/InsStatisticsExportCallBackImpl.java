package com.yzj.jep.app.stat.service.impl;

import com.yzj.jep.app.bus.model.ReportAnnual;
import com.yzj.jep.app.bus.service.Report_annualService;
import com.yzj.jep.app.bus.service.impl.StatisticalExportCallBackImpl;
import com.yzj.jep.app.stat.service.InsStatisticsService;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @title: InsStatisticsExportCallBackImpl
 * @Author Whx
 * @Date: 2021/7/14 16:07
 * @Version 1.0
 */
@Service(value = InsStatisticsExportCallBackImpl.BEANID)
public class InsStatisticsExportCallBackImpl implements XlsExportCallback {

    public static final String BEANID = "InsStatisticsExportCallBackImpl";
    private static final String EXPORT_TEMPLATE_NAME = "insApproveStat.xls";

    @Resource
    private InsStatisticsService insStatisticsService;

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
        String fileName = "流程审批数据" + sf.format(new Date()) + ".xls";
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
        String params =(String) parameters.get("id");
        String[] split = params.split(",");
        Map map = insStatisticsService.statIns(Integer.MAX_VALUE, 1, split[0].replaceAll(" ",""), split[1].replaceAll(" ",""), split[2].replaceAll(" ",""), split[3].replaceAll(" ",""));
        List<Map> mapList = (List<Map>) map.get("rows");
        result.put("insStat", mapList);
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
