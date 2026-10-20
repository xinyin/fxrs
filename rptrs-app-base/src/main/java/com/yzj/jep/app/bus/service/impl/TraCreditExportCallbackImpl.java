package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.bus.service.RelService;
import com.yzj.jep.app.bus.service.TraCreditService;
import com.yzj.jep.app.bus.vo.OrgInfoVo;
import com.yzj.jep.app.bus.vo.PsnInfoVo;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.model.Unit;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.sys.service.UnitService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service(value = TraCreditExportCallbackImpl.BEANID)
public class TraCreditExportCallbackImpl implements XlsExportCallback {
    public static final String BEANID = "TraCreditExportCallbackImpl";

    private static final String EXPORT_TEMPLATE_NAME = "BusTraCreditForm.xls";
    @Autowired
    DictItemService dictItemService;

    @Autowired
    UnitService unitService;


    @Override
    public String getExportTemplate() {
        return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_NAME;
    }
    //relatedName+","+identifyType+","+identNumber+","+status+","+businessType+","+tranType+","+dataResource+","+importantTrade+",";

    @Override
    public String getExportFilename(Map parameters) throws ServiceException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String data = (String) parameters.get("id");
        String[] datas = data.split(",");
        String relatedName = datas[0];
        String identifyType = datas[1];
        String identNumber = datas[2];
        String status = datas[3];
        String businessType = datas[4];
        String tranType = datas[5];
        String dataResource = datas[6];
        String importantTrade = datas[7];

        return "内部人授信类模板" + relatedName + sf.format(new Date()) + ".xls";
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

        String[] datas = data.split(",");
        String relatedName = datas[0];
        String identifyType = datas[1];
        String identNumber = datas[2];
        String status = datas[3];
        String businessType = datas[4];
        String tranType = datas[5];
        String dataResource = datas[6];
        String importantTrade = datas[7];


        TraCreditService traCreditService = SpringContextHolder.getBean(TraCreditService.class);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-mm-YYYY HH:mm:ss");

        List<Filter> filter = new Filter.Builder().eq("tranType", "1").eq("isInsider", "1").build();


        if (!"".equals(relatedName)) {
            filter.add(new Filter("relatedName", Operator.LIKE, relatedName));
        }
        if (!" ".equals(identifyType)) {
            filter.add(new Filter("identifyType", Operator.LIKE, identifyType));
        }
        if (!"".equals(identNumber)) {
            filter.add(new Filter("identNumber", Operator.LIKE, identNumber));
        }
        if (!" ".equals(status)) {
            filter.add(new Filter("status", Operator.LIKE, status));
        }

        if (!" ".equals(businessType)) {
            filter.add(new Filter("businessType", Operator.LIKE, businessType));
        }

        if (!" ".equals(tranType)) {
            filter.add(new Filter("tranType", Operator.LIKE, tranType));
        }


        if (!"undefined".equals(dataResource)) {
            filter.add(new Filter("dataResource", Operator.LIKE, dataResource));
        }


        if (!" ".equals(importantTrade)) {
            filter.add(new Filter("importantTrade", Operator.LIKE, importantTrade));
        }


        List<TraCredit> list = traCreditService.select(filter);


        //将证件类型名称更改为编码
        List<Filter> filterList = new Filter.Builder().eq("dictCode", "BUS-IdentifyType").build();
        List<DictItem> dictItem1 = dictItemService.select(filterList);
        Map map1 = new HashMap();
        for (DictItem dic : dictItem1) {
            map1.put(dic.getItemValue(), dic.getItemLabel());
        }


        //将交易类型名称转换为编码
        List<Filter> dictCode = new Filter.Builder().eq("dictCode", "BUS-TtradeType").build();
        List<DictItem> dictItem2 = dictItemService.select(dictCode);
        Map map2 = new HashMap();
        for (DictItem dic : dictItem2) {
            map2.put(dic.getItemValue(), dic.getItemLabel());
        }

        /*业务类型*/
        List<Filter> dictCode3 = new Filter.Builder().eq("dictCode", "BUS-BusinessType").build();
        List<DictItem> dictItem3 = dictItemService.select(dictCode3);
        Map map3 = new HashMap();
        for (DictItem dic : dictItem3) {
            map3.put(dic.getItemValue(), dic.getItemLabel());

        }
        //是否重大交易
        List<Filter> Status = new Filter.Builder().eq("dictCode", "BUS-isNo").build();
        List<DictItem> SStatus = dictItemService.select(Status);
        Map map4 = new HashMap();
        for (DictItem dic : SStatus) {
            map4.put(dic.getItemValue(), dic.getItemLabel());
        }

        //交易机构
        List<Filter> dictCode5 = new Filter.Builder().eq("dictCode", "BUS-GHOrg").build();
        List<DictItem> dictItem5 = dictItemService.select(dictCode5);
        Map map5 = new HashMap();
        for (DictItem dictItem : dictItem5) {
            map5.put(dictItem.getItemValue(), dictItem.getItemLabel());
        }

        //数据来源

        List<Filter> dictCode6 = new Filter.Builder().eq("dictCode", "BUS-GHOrg").build();
        List<DictItem> dictItem6 = dictItemService.select(dictCode6);
        Map map6 = new HashMap();
        for (DictItem dictItem : dictItem6) {
            map6.put(dictItem.getItemLabel(), dictItem.getItemValue());
        }

        for (TraCredit traCredit : list) {

            traCredit.setIdentifyType((String) map1.get(traCredit.getIdentifyType()));
            traCredit.setTranType((String) map2.get(traCredit.getTranType()));
            traCredit.setBusinessType((String) map3.get(traCredit.getBusinessType()));

            traCredit.setImportantTrade((String) map4.get(traCredit.getImportantTrade()));
            traCredit.setManageOrg((String) map5.get(traCredit.getManageOrg()));
            traCredit.setDataResource((String) map6.get(traCredit.getDataResource()));

            traCredit.setBzrIdentifyType((String) map1.get(traCredit.getBzrIdentifyType()));
        }

        resultMap.put("traCredit", list);
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

