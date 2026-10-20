package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.ins.model.TraInvestment;
import com.yzj.jep.app.ins.model.TraService;
import com.yzj.jep.app.ins.service.TraInvestmentService;
import com.yzj.jep.app.ins.service.TraServiceService;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = TraTempateCallbackImpl.BEANID)
public class TraTempateCallbackImpl implements XlsExportCallback {
    public static final String BEANID = "TraTempateCallbackImpl";

    private static final String EXPORT_TEMPLATE_NAME = "TraServices.xls";


    @Autowired
    private TraServiceService traServiceService;

    @Override
    public String getExportTemplate() {
        return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_NAME;
    }

    @Override
    public String getExportFilename(Map parameters) throws ServiceException {
        String fileName = "提供服务类交易模板.xls";
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
     * 编写业务代码
     */
    @Override
    public Map executeExportCallback(Map parameters) throws ServiceException {

        Map resultMap = new HashMap();
        List<TraService> traServices = null;
        resultMap.put("service", traServices);
        return resultMap;
    }

    @Override
    public Map executeExportCallback(HttpServletRequest request) throws ServiceException {
        return null;
    }

    @Override
    public void afterTransformXLS(Workbook workbook) {

    }
}
