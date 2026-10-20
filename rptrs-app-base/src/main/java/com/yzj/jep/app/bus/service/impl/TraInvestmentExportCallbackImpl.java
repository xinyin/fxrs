package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.service.ProductService;
import com.yzj.jep.app.ins.model.TraInvestment;
import com.yzj.jep.app.ins.service.TraInvestmentService;
import com.yzj.jep.app.sys.util.Constants;
import com.yzj.jep.app.util.ConfidentialInfoUtil;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.web.entity.NameValuePair;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service(value = TraInvestmentExportCallbackImpl.BEANID)
public class TraInvestmentExportCallbackImpl implements XlsExportCallback {
    public static final String BEANID = "TraInvestmentExportCallbackImpl";

    private static final String EXPORT_TEMPLATE_NAME = "TraInvestment.xls";

    @Autowired
    private TraInvestmentService traInvestmentService;

    @Autowired
    DictItemService dictItemService;
    @Autowired
    ProductService productService;

    @Override

    public String getExportTemplate() {
        return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_NAME;
    }

    @Override
    public String getExportFilename(Map parameters) throws ServiceException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String fileName = "投资入股类交易_" + sf.format(new Date()) + ".xls";
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
        //参数
        String data = (String) parameters.get("id");
        List<Filter> filters = null;
        String[] ids = data.split(",");
        String type = ids[0];
        if ("00".equals(data)) {
            resultMap.put("inv", new TraInvestment());
            return resultMap;
        }
        if (!"check".equals(type)) {
            filters = new Filter.Builder().eq("delflag", "1").in("pid", ids).build();
        } else {
            filters = Constants.castList(Constants.conditions.get("filters"), Filter.class);
        }
        List<TraInvestment> traInvestList = traInvestmentService.select(filters);
        traInvestList.stream().map(traInvestment -> {
            //是否重大关联交易
            boolean flag = StringUtils.equals(traInvestment.getMajorTrade(), "0");
            if (flag) {
                traInvestment.setMajorTrade("是");
            } else {
                traInvestment.setMajorTrade("否");
            }
            //是否为控股子公司
            flag = StringUtils.equals(traInvestment.getIsHoldingSubsidiary(), "0");
            if (flag) {
                traInvestment.setIsHoldingSubsidiary("是");
            } else {
                traInvestment.setIsHoldingSubsidiary("否");
            }
            //交易定价是否为国家规定
            flag = StringUtils.equals(traInvestment.getIsDealPrice(), "0");
            if (flag) {
                traInvestment.setIsDealPrice("是");
            } else {
                traInvestment.setIsDealPrice("否");
            }
            //交易现金认购
            flag = StringUtils.equals(traInvestment.getIsCashSubscript(), "0");
            if (flag) {
                traInvestment.setIsCashSubscript("是");
            } else {
                traInvestment.setIsCashSubscript("否");
            }
            //证券类型
            List<Filter> filters1 = new Filter.Builder().eq("dictCode", "INS-SecuritiesType").build();
            List<DictItem> dictItems = dictItemService.select(filters1);
            Map securitiesTypeMap = new HashMap();
            for (DictItem dic : dictItems) {
                securitiesTypeMap.put(dic.getItemValue(), dic.getItemLabel());
            }
            //交易主体名称
            traInvestment.setTraSubName(traInvestment.getTraSubName());
            //控股子公司证件类型
            traInvestment.setHoldingSubsidiaryType(selectByValue(traInvestment.getHoldingSubsidiaryType(), "BUS-ORGIdentifyType"));
            //交易类型
            traInvestment.setTranType(selectByValue(traInvestment.getTranType(), "INS-tranType"));
            //业务类型,原来的封不上
            traInvestment.setBusTypeStr(selectByValue(traInvestment.getInvestmentType(), "INS-InvestmentType"));
            //证券类型
            traInvestment.setSecuritiesType(selectByValue(traInvestment.getSecuritiesType(), "INS-SecuritiesType"));
            //审议类型
            traInvestment.setConsiderType(selectByValue(traInvestment.getConsiderType(), "INS-considerType"));
            //达到重大关联交易的情形
            traInvestment.setMajorType(selectByValue(traInvestment.getMajorType(), "INS-majorType"));
            //交易状态
            traInvestment.setStatus(selectByValue(traInvestment.getStatus(), "BUS-Applystatus"));
            traInvestment.setRelNumber(new ConfidentialInfoUtil().hideAllIdCardNum(traInvestment.getRelNumber()));
            return traInvestment;
        }).collect(Collectors.toList());


        resultMap.put("inv", traInvestList);
        return resultMap;
    }


    private String invType(String investmentType) {
        List<Filter> filters = new Filter.Builder().eq("pid", investmentType).build();

        if (null == productService.get(filters)) {
            return "";
        }

        return productService.get(filters).getProName();
    }


    @Override
    public Map executeExportCallback(HttpServletRequest request) throws ServiceException {
        return null;
    }

    @Override
    public void afterTransformXLS(Workbook workbook) {

    }

    //----------------------------------------
    public String selectByValue(String value, String simpleName) {
        List<NameValuePair> items = new ArrayList<>();
        try {
            List<Filter> filters = new Filter.Builder().eq("dictCode", simpleName).build();
            List<DictItem> list = dictItemService.select(filters);
            //过滤掉不要的字典项
            if (!CollectionUtils.isEmpty(list)) {
                for (DictItem dictItem : list) {
                    if (dictItem.getItemValue().equals(value)) {
                        return dictItem.getItemLabel();
                    }
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

}
