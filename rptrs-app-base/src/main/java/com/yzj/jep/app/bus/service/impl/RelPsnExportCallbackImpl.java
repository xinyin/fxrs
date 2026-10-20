package com.yzj.jep.app.bus.service.impl;

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


/**
 * 自然人模板导出实现
 *
 * @author ke
 * @time 2020年3月13日 下午3:47:25
 * @company 银之杰技术有限公司
 */
@Service(value = RelPsnExportCallbackImpl.BEANID)
public class RelPsnExportCallbackImpl implements XlsExportCallback {

    public static final String BEANID = "RelPsnExportCallbackImpl";

    private static final String EXPORT_TEMPLATE_NAME = "RelPsnForm.xls";

    @Override
    public String getExportTemplate() {
        return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_NAME;
    }

    @Override
    public String getExportFilename(Map parameters) throws ServiceException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        return "自然人信息导入模板_" + sf.format(new Date()) + ".xls";
    }

    @Override
    public Map<Integer, List<int[]>> getMergeColumns() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<Integer, Integer> getMergeStartRowNum() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map executeExportCallback(Map parameters) throws ServiceException {
        Map resultMap = new HashMap();
        return resultMap;
    }

    @Override
    public void afterTransformXLS(Workbook workbook) {
        // TODO Auto-generated method stub

    }

    @Override
    public Map executeExportCallback(HttpServletRequest request) throws ServiceException {
        // TODO Auto-generated method stub
        return null;
    }

}
