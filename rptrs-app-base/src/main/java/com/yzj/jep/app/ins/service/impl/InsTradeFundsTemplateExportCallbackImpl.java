package com.yzj.jep.app.ins.service.impl;

import com.yzj.jep.app.ins.model.TraFunds;
import com.yzj.jep.app.ins.service.TraFundsService;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = InsTradeFundsTemplateExportCallbackImpl.BEANID)
public class InsTradeFundsTemplateExportCallbackImpl implements XlsExportCallback {
    public static final String BEANID = "InsTradeFundsTemplateExportCallbackImpl";

    private static final String EXPORT_TEMPLATE_NAME = "InsTradeFundsTemplate.xls";

    @Autowired
    private TraFundsService traFundsService;

    @Override
    public String getExportTemplate() {
        return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_NAME;
    }

    @Override
    public String getExportFilename(Map parameters) throws ServiceException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String fileName = "资金运用类交易信息_" + ".xls";
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
        if ("00".equals(data)) {
            resultMap.put("funds", new TraFunds());
            return resultMap;
        }
        //获取要导出自然人的id
        String[] param = data.split(",");
        String relName = param[0];
        String relType = param[1];
        String relNumber = param[2];
        String status = param[3];
        String investmentType = param[4];
        String caliber = param[5];
        String majorTrade = param[6];
        String tradeOrg = param[7];
        String dataSources = param[8];
        String startTranDate = param[9];
        String endTranDate = param[10];
        List<Filter> filters = new Filter.Builder().eq("delflag", "1").build();
        if (!"%".equals(relName)) {
            filters.add(new Filter("relName", Operator.LIKE, relName));
        }
        if (StringUtils.isNotBlank(relType)) {
            filters.add(new Filter("relType", relType));
        }
        if (!"%".equals(relNumber)) {
            filters.add(new Filter("relNumber", relNumber));
        }
        if (StringUtils.isNotBlank(status)) {
            filters.add(new Filter("status", status));
        }
        if (StringUtils.isNotBlank(investmentType)) {
            filters.add(new Filter("investmentType", investmentType));
        }
        if (StringUtils.isNotBlank(caliber)) {
            filters.add(new Filter("caliber", caliber));
        }
        if (StringUtils.isNotBlank(majorTrade)) {
            filters.add(new Filter("majorTrade", majorTrade));
        }
        if (StringUtils.isNotBlank(tradeOrg)) {
            filters.add(new Filter("tradeOrg", tradeOrg));
        }
        if (StringUtils.isNotBlank(dataSources)) {
            filters.add(new Filter("dataSources", dataSources));
        }
        if (!"%".equals(startTranDate)) {
            filters.add(new Filter("startTranDate", Operator.GE, startTranDate));
        }
        if (!"%".equals(endTranDate)) {
            filters.add(new Filter("endTranDate", Operator.LT, endTranDate));
        }

        List<TraFunds> traFunds = traFundsService.select(filters);
//        List<TraFundsVo> collect = traFunds.stream().map(traFunds1 -> {
//            TraFundsVo traFundsVo = new TraFundsVo();
//            BeanUtils.copyProperties(traFunds1, traFundsVo);
//            return traFundsVo;
//        }).collect(Collectors.toList());
        resultMap.put("funds", traFunds);
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
