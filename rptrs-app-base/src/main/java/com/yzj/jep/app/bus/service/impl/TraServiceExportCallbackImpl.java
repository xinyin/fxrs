package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.base.constant.IsStatusDict;
import com.yzj.jep.app.bus.service.ProductService;
import com.yzj.jep.app.ins.model.TraService;
import com.yzj.jep.app.ins.service.TraServiceService;
import com.yzj.jep.app.sys.util.Constants;
import com.yzj.jep.app.util.ConfidentialInfoUtil;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.web.entity.NameValuePair;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Service(value = TraServiceExportCallbackImpl.BEANID)
public class TraServiceExportCallbackImpl implements XlsExportCallback {
    public static final String BEANID = "TraServiceExportCallbackImpl";

    private static final String EXPORT_TEMPLATE_NAME = "TraService.xls";

    @Autowired
    private TraServiceService traServiceService;

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
        String fileName = "提供服务类交易_" + sf.format(new Date()) + ".xls";
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
        //获取要导出自然人的id
        /* relName  relType  relNumber  status  busType  caliber  majorTrade
         tradeOrg  dataSources  tranDate  tranDate    */
//        String[] param = data.split(",");
//        String relName = param[0];
//        String relType = param[1];
//        String relNumber = param[2];
//        String status = param[3];
//        String busType = param[4];
//        String caliber = param[5];
//        String majorTrade = param[6];
//        String tradeOrg = param[7];
//        String dataSources = param[8];
//        String startTranDate = param[9];
//        String endTranDate = param[10];
//        String isDealPrice = param[11];
        //List<Filter> filters = new Filter.Builder().eq("delflag", "1").build();
        /*if (!"%".equals(relName)) {
            filters.add(new Filter("relName", Operator.LIKE, relName));
        }
        if (!"%".equals(relType)) {
            filters.add(new Filter("relType", relType));
        }
        if (!"%".equals(relNumber)) {
            filters.add(new Filter("relNumber", relNumber));
        }
        if (!"%".equals(status)) {
            filters.add(new Filter("status", status));
        }
        if (!"%".equals(busType)) {
            filters.add(new Filter("busType", busType));
        }
        if (!"%".equals(caliber)) {
            filters.add(new Filter("caliber", caliber));
        }
        if (!"%".equals(majorTrade)) {
            filters.add(new Filter("majorTrade", majorTrade));
        }
        if (!"%".equals(tradeOrg)) {
            filters.add(new Filter("tradeOrg", tradeOrg));
        }
        if (!"%".equals(dataSources)) {
            filters.add(new Filter("dataSources", dataSources));
        }
        if (!"%".equals(startTranDate)) {
            filters.add(new Filter("startTranDate", Operator.GE, startTranDate));
        }
        if (!"%".equals(endTranDate)) {
            filters.add(new Filter("endTranDate", Operator.LT, endTranDate));
        }
        if (!"%".equals(isDealPrice)) {
            filters.add(new Filter("isDealPrice", isDealPrice));
        }*/

        /*//业务类型
        List<Filter> filters1=new Filter.Builder().eq("dictCode","INS-ServiceType").build();
        List<DictItem> dictItems =dictItemService.select(filters1);
        Map buyTypeMap= new HashMap();
        for (DictItem dic: dictItems){
            //relTypeMap.put(dic.getItemLabel() ,dic.getItemValue());
            buyTypeMap.put(dic.getItemValue(), dic.getItemLabel());
        }*/
        if (!"check".equals(type)) {
            filters = new Filter.Builder().eq("delflag", "1").in("pid", ids).build();
        } else {
            filters = Constants.castList(Constants.conditions.get("filters"), Filter.class);
        }
        List<TraService> traServices = traServiceService.select(filters);
        List<TraService> trasList = new ArrayList<>();
        for (TraService traS : traServices) {
            if (traS.getMajorTrade() != null) {
                boolean majorTradeb = StringUtils.equals(traS.getMajorTrade(), "0");
                if (majorTradeb) {
                    traS.setMajorTypeStr("是");
                } else {
                    traS.setMajorTypeStr("否");
                }
            }
            traS.setTraSubNameStr(traS.getTraSubName());
            traS.setStatusStr(selectByValue(traS.getStatus(), "BUS-Applystatus"));
            traS.setBusTypeStr(selectByValue(traS.getMajorTrade(), "INS-InvestmentType"));
            traS.setRelTypeStr(selectByValue(traS.getRelType(), "INS-relType"));
            traS.setTranDate(traS.getTranDate());
            traS.setConsiderType(selectByValue(traS.getConsiderType(),"INS-considerType"));
            traS.setBusTypeStr(selectByValue(traS.getBusType(),"INS-ServiceType"));
            traS.setTranType(selectByValue(traS.getTranType(),"INS-tranType"));
            traS.setIsAgreement(IsStatusDict.getLabel(traS.getIsAgreement()));
            traS.setIscashsubscribe(IsStatusDict.getLabel(traS.getIscashsubscribe()));
            traS.setMajorType(selectByValue(traS.getMajorType(),"INS-majorType"));
            traS.setBusType(invType(traS.getBusType()));
            traS.setIsDealPrice(isNO(traS.getIsDealPrice()));
            traS.setRelNumber(new ConfidentialInfoUtil().hideAllIdCardNum(traS.getRelNumber()));

            trasList.add(traS);
        }

        resultMap.put("ser", trasList);
        return resultMap;
    }

    private String invType(String investmentType) {
        List<Filter> filters = new Filter.Builder().eq("pid", investmentType).build();

        if (null == productService.get(filters)) {
            return "";
        }

        return productService.get(filters).getProName();
    }

    private String isNO(String strIsNO) {
        if (null != strIsNO) {
            if ("1".equals(strIsNO)) {
                strIsNO = "是";
            } else if ("0".equals(strIsNO)) {
                strIsNO = "否";
            }
        }
        return strIsNO;
    }

    @Override
    public Map executeExportCallback(HttpServletRequest request) throws ServiceException {
        return null;
    }

    @Override
    public void afterTransformXLS(Workbook workbook) {

    }

    private  String selectByValue(String value, String simpleName) {
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
            e.printStackTrace();
        }
        return null;
    }

}
