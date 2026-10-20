package com.yzj.jep.app.ins.service.impl;

import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = InsTraFundsOutCallbackImpl.BEANID)
public class InsTraFundsOutCallbackImpl implements XlsExportCallback {
    public static final String BEANID = "InsTraFundsOutCallbackImpl";

    private static final String EXPORT_TEMPLATE_NAME = "InsTraFundsTemplate.xls";
    @Override
    public String getExportTemplate() {
        return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_NAME;
    }

    @Override
    public String getExportFilename(Map map) throws ServiceException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String fileName = "资金运用类关联交易模板下载_" + sf.format(new Date()) + ".xls";
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

    @Override
    public Map executeExportCallback(Map map) throws ServiceException {
        Map resultMap = new HashMap();
        return resultMap;
    }

    @Override
    public Map executeExportCallback(HttpServletRequest httpServletRequest) throws ServiceException {
        return null;
    }

    @Override
    public void afterTransformXLS(Workbook workbook) {

    }
}
