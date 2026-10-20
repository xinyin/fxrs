package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.sub.mapper.MidZjyygljytjbMapper;
import com.yzj.jep.app.sub.model.MidZjyygljytjb;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = MidEastZjyygljytjbExportCallbackImpl.BEANID)
public class MidEastZjyygljytjbExportCallbackImpl implements XlsExportCallback {

    public static final String BEANID = "MidEastZjyygljytjbExportCallbackImpl";

    private static final String EXPORT_TEMPLATE_NAME = "MidZjyygljytjb.xls";

    @Resource
    private MidZjyygljytjbMapper midZjyygljytjbMapper;


    @Override
    public String getExportTemplate() {
        return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_NAME;
    }

    @Override
    public String getExportFilename(Map parameters) throws ServiceException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        return "资金运用关联交易统计表_" + sf.format(new Date()) + ".xls";
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
        Map resultMap = new HashMap();
        //参数
        String data = (String) parameters.get("id");
        String[] param = data.split(",");
        String batchNo = param[0];
        MidZjyygljytjb mid = new MidZjyygljytjb();
        mid.setBatchno(batchNo);
        List<MidZjyygljytjb>  list =  midZjyygljytjbMapper.select(mid);
        resultMap.put("mid", list);
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
