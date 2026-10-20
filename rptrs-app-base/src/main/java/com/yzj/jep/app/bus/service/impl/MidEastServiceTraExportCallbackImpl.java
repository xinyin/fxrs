package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.sub.mapper.MidFwlgljyxyxxbMapper;
import com.yzj.jep.app.sub.model.MidFwlgljyxyxxb;
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

@Service(value = MidEastServiceTraExportCallbackImpl.BEANID)
public class MidEastServiceTraExportCallbackImpl implements XlsExportCallback {

    public static final String BEANID = "MidEastServiceTraExportCallbackImpl";

    private static final String EXPORT_TEMPLATE_NAME = "MidFwlgljyxyxxb.xls";

    @Resource
    private MidFwlgljyxyxxbMapper midFwlgljyxyxxbMapper;


    @Override
    public String getExportTemplate() {
        return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_NAME;
    }

    @Override
    public String getExportFilename(Map parameters) throws ServiceException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String fileName = "服务类关联交易协议信息表_" + sf.format(new Date()) + ".xls";
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
        Map resultMap = new HashMap();
        //参数
        String data = (String) parameters.get("id");
        String[] param = data.split(",");
        String batchNo = param[0];
        MidFwlgljyxyxxb fw = new MidFwlgljyxyxxb();
        fw.setBatchno(batchNo);
        List<MidFwlgljyxyxxb>  list =  midFwlgljyxyxxbMapper.select(fw);
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
