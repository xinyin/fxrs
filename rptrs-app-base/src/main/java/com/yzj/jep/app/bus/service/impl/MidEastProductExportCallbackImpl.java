package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.sub.mapper.MidGlfxxbMapper;
import com.yzj.jep.app.sub.mapper.MidJrcpxxbMapper;
import com.yzj.jep.app.sub.model.MidGlfxxb;
import com.yzj.jep.app.sub.model.MidJrcpxxb;
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

@Service(value = MidEastProductExportCallbackImpl.BEANID)
public class MidEastProductExportCallbackImpl implements XlsExportCallback {

    public static final String BEANID = "MidEastProductExportCallbackImpl";

    private static final String EXPORT_TEMPLATE_NAME = "MidJrcpxxb.xls";

    @Resource
    private MidJrcpxxbMapper midJrcpxxbMapper;


    @Override
    public String getExportTemplate() {
        return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_NAME;
    }

    @Override
    public String getExportFilename(Map parameters) throws ServiceException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String fileName = "金融产品信息表_" + sf.format(new Date()) + ".xls";
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
        MidJrcpxxb glf = new MidJrcpxxb();
        glf.setBatchno(batchNo);
        List<MidJrcpxxb>  list =  midJrcpxxbMapper.select(glf);
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
