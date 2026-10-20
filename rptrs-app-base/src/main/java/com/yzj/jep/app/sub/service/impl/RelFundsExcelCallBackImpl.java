package com.yzj.jep.app.sub.service.impl;

import com.yzj.jep.app.sub.model.InsRelfunds;
import com.yzj.jep.app.sub.service.InsRelfundsService;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import com.yzj.jep.orm.entity.Filter;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = RelFundsExcelCallBackImpl.BEANID)
public class RelFundsExcelCallBackImpl  implements XlsExportCallback{
    private static Logger logger = LoggerFactory.getLogger(RelFundsExcelCallBackImpl.class);
    public static final String BEANID = "RelFundsExcelCallBackImpl";
    private static final String EXPORT_TEMPLATE_NAME = "relFunds.xls";
    private static final String PREFIX = "与单一法人资金运用关联交易统计表模板下载_";

    private static final String SUFFIX = ".xls";
    @Autowired
    InsRelfundsService insRelfundsService;

    @Override
    public String getExportTemplate() {
        return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_NAME;
    }

    @Override
    public String getExportFilename(Map parameters) throws ServiceException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String fileName = PREFIX + sf.format(new Date()) + SUFFIX;
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

    /*
     * 编写业务代码
     * */
    @Override
    public Map executeExportCallback(Map parameters) throws ServiceException {
        logger.info("业务逻辑开始。。。。。");
        Map resultMap = new HashMap();
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
