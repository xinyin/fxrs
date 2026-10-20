package com.yzj.jep.app.ins.service.impl;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.yzj.jep.app.ins.mapper.TraInvestmentMapper;
import com.yzj.jep.app.ins.model.QuarterlyVo;
import com.yzj.jep.app.ins.model.TraInvestment;
import com.yzj.jep.app.util.QueryQuarterCommon;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

@Service(value = reportDataExcelImpl.BEANID)
public class reportDataExcelImpl implements XlsExportCallback {

    public static final String BEANID = "reportDataExcelImpl";

    private static final String EXPORT_TEMPLATE_NAME = "reportDataExport.xls";

    private static final String DICTCODE = "dictCode";

    @Autowired
    private TraInvestmentMapper traInvestmentMapper;


    @Override
    public String getExportTemplate() {
        return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_NAME;
    }

    @Override
    public String getExportFilename(Map parameters) throws ServiceException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String fileName = "关联交易数据汇总_" + sf.format(new Date()) + ".xls";
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
    public Map executeExportCallback(Map parameters) throws ServiceException {

        Map resultMap = new HashMap();
        String data = (String) parameters.get("id");
        String[] ids = data.split(",");
        String year = ids[1];
        String quarter = ids[2];
        Map<String, Object> param = new HashMap<>();
        //处理查询条件
        StringBuilder stringBuilder = new StringBuilder();
        if (!year.equals("null")) {
            stringBuilder.append(year);
            param.put("year", stringBuilder.toString().concat("%"));
        }
        //季度
        if (!quarter.equals("null")) {
            int ofYear = DateUtil.year(new Date());
            if (StrUtil.isBlank(year)) {
                stringBuilder.append(ofYear);
            }
            String beginQuarter = null;
            String endQuarter = null;
            //季度endQuarter拼接最大时间
            if ("1".equals(quarter)) {
                beginQuarter = stringBuilder.toString().concat("-01-01");
                endQuarter = stringBuilder.toString().concat("-04-01");
            } else if ("2".equals(quarter)) {
                beginQuarter = stringBuilder.toString().concat("-04-01");
                endQuarter = stringBuilder.toString().concat("-07-01");
            } else if ("3".equals(quarter)) {
                beginQuarter = stringBuilder.toString().concat("-07-01");
                endQuarter = stringBuilder.toString().concat("-10-01");
            } else {
                beginQuarter = stringBuilder.toString().concat("-10-01");
                //年加一
                int addYear = ofYear + 1;
                endQuarter = new StringBuilder(addYear).append("-01-01").toString();
            }
            param.put("beginQuarter", beginQuarter);
            param.put("endQuarter", endQuarter);
        }

        List<TraInvestment> list = traInvestmentMapper.getByInt(param);
        List<QuarterlyVo> quarterlyVos = new ArrayList<>();
        if(!CollUtil.isEmpty(list)){
            quarterlyVos = QueryQuarterCommon.queryQuarterTranData(list);
        }

        resultMap.put("report",quarterlyVos);
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
