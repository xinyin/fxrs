package com.yzj.jep.app.bus.service.impl;

import com.alibaba.fastjson.JSON;
import com.yzj.jep.app.base.constant.IsStatusDict;
import com.yzj.jep.app.ins.model.TraOther;
import com.yzj.jep.app.ins.service.TraOtherService;
import com.yzj.jep.app.sys.util.Constants;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.sys.service.UnitService;
import com.yzj.jep.web.entity.NameValuePair;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = TraOtherTypeOutExportCallbackImpl.BEANID)
public class TraOtherTypeOutExportCallbackImpl implements XlsExportCallback {
    public static final String BEANID = "TraOtherTypeOutExportCallbackImpl";

    private static final String EXPORT_TEMPLATE_NAME = "TraOther.xls";
    @Autowired
    DictItemService dictItemService;

    @Autowired
    UnitService unitService;
    @Autowired
    TraOtherService traOtherService;


    @Override
    public String getExportTemplate() {
        return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_NAME;
    }

    @Override
    public String getExportFilename(Map parameters) throws ServiceException {
        return "其他类.xls";
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
        String data = (String) parameters.get("id");
        List<Filter> filters = null;
        String[] ids = data.split(",");
        String type = ids[0];
        if (!"check".equals(type)) {
            filters = new Filter.Builder().eq("delflag", "1").in("pid", ids).build();
        } else {
            filters = Constants.castList(Constants.conditions.get("filters"), Filter.class);
        }
        List<TraOther> traOthers = traOtherService.select(filters);
        List<TraOther> traOList = new ArrayList<>();
        for (TraOther traO : traOthers) {

            if (traO.getMajorType() != null) {
                boolean majorTradeb = StringUtils.equals(traO.getMajorType(), "0");
                if (majorTradeb) {
                    traO.setMajorTradeStr("是");
                } else {
                    traO.setMajorTradeStr("否");
                }
            }
            traO.setTraSubNameStr(traO.getTraSubName());
            traO.setStatusStr(selectByValue(traO.getStatus(), "BUS-Applystatus"));
            traO.setBusTypStr(selectByValue(traO.getBusType(), "INS-otherType"));
            traO.setRelTypeStr(selectByValue(traO.getRelType(), "INS-relType"));
            traO.setTranType(selectByValue(traO.getTranType(),"INS-tranType"));
            traO.setConsiderType(selectByValue(traO.getConsiderType(),"INS-considerType"));
            traO.setMajorType(selectByValue(traO.getMajorType(),"INS-majorType"));
            if (traO.getMajorType() == null || traO.getMajorType().equals("")){
                traO.setMajorType("未达到");
            }

            traO.setIsDealPrice(IsStatusDict.getLabel(traO.getIsDealPrice()));
            traO.setIsCashSubscribe(IsStatusDict.getLabel(traO.getIsCashSubscribe()));
            if (traO.getIsCashSubscribe()==null){
                    traO.setIsCashSubscribe("否");
           }
            traO.setContractDate(traO.getContractDate());
            traO.setTranDate(traO.getTranDate());
            traO.setTransNum(traO.getTransNum());
            traOList.add(traO);
        }

        resultMap.put("other", traOList);
        return resultMap;
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


