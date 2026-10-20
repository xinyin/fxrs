package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.model.TraCredit;
import com.yzj.jep.app.bus.service.TraCreditService;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.sys.service.UnitService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(value = TraCreditDealExportCallbackImpl.BEANID)
public class TraCreditDealExportCallbackImpl implements XlsExportCallback {
    public static final String BEANID = "TraCreditDealExportCallbackImpl";

    private static final String EXPORT_TEMPLATE_NAME = "BusTraCreditForm.xls";
    @Autowired
    DictItemService dictItemService;

    @Autowired
    UnitService unitService;


    @Override
    public String getExportTemplate() {
        return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_NAME;
    }

    @Override
    public String getExportFilename(Map parameters) throws ServiceException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String data = (String) parameters.get("id");
        String[] datas = data.split(",");

        String relatedName = datas[0];
        String identifyType = datas[1];
        String identNumber = datas[2];
        String status = datas[3];
        String regulatorOth1 = datas[4];
        String importantTrade = datas[5];
        String manageOrg = datas[6];
        String datareSource = datas[7];

        return "授信类模板" + relatedName + sf.format(new Date()) + ".xls";
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
        String relType = datas[1];
        String identNumber = datas[2];
        String status = datas[3];
        String regulatorOth1 = datas[4];
        String importantTrade = datas[5];
        String manageOrg = datas[6];
        String dataResource = datas[7];

        TraCreditService traCreditService = SpringContextHolder.getBean(TraCreditService.class);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-mm-YYYY HH:mm:ss");


        UserProfile userProfile = UserProfileUtils.getUserProfile();

        String code = userProfile.getUnitCode();
        // System.out.println("code = " + code);
        //  List<String> codes = unitService.selectUnitAllOrDeptAllCodes(code);
        List<Filter> filter = new Filter.Builder().eq("delflag", "1").eq("manageOrg", code).build();


        if (!"".equals(relatedName)) {
            filter.add(new Filter("relatedName", Operator.LIKE, relatedName));
        }
        if (!" ".equals(relType)) {
            filter.add(new Filter("relType", Operator.LIKE, relType));
        }
        if (!"".equals(identNumber)) {
            filter.add(new Filter("identNumber", Operator.LIKE, identNumber));
        }
        if (!" ".equals(status)) {
            filter.add(new Filter("status", Operator.LIKE, status));
        }

        if (!" ".equals(manageOrg)) {
            filter.add(new Filter("manageOrg", Operator.LIKE, manageOrg));
        }

        if (!" ".equals(regulatorOth1)) {
            filter.add(new Filter("regulatorOth1", Operator.LIKE, regulatorOth1));
        }


        if (!" ".equals(dataResource)) {
            filter.add(new Filter("dataResource", Operator.LIKE, dataResource));
        }


        if (!" ".equals(importantTrade)) {
            filter.add(new Filter("importantTrade", Operator.LIKE, importantTrade));
        }


        List<TraCredit> list = traCreditService.select(filter);


        //将证件类型编码更改为名称
        List<Filter> filterList = new Filter.Builder().eq("dictCode", "BUS-IdentifyType").build();
        List<DictItem> dictItem1 = dictItemService.select(filterList);
        Map map1 = new HashMap();
        for (DictItem dic : dictItem1) {
            map1.put(dic.getItemValue(), dic.getItemLabel());
        }

        //交易类型
        //交易类型编码更改为名称
        List<Filter> dictCode = new Filter.Builder().eq("dictCode", "BUS-BusinessType").build();
        List<DictItem> dictItem2 = dictItemService.select(dictCode);
        Map map2 = new HashMap();
        for (DictItem dic : dictItem2) {
            map2.put(dic.getItemValue(), dic.getItemLabel());
        }


        //业务类型  business_type
        //业务类型编码更改为名称
        List<Filter> dictCode1 = new Filter.Builder().eq("dictCode", "SUB-BusinessType").build();
        List<DictItem> dictItem3 = dictItemService.select(dictCode1);
        Map map3 = new HashMap();
        for (DictItem dic : dictItem3) {
            map3.put(dic.getItemValue(), dic.getItemLabel());
        }

        for (TraCredit traCredit : list) {

            traCredit.setTraPriceType((String) map2.get(traCredit.getTraPriceType()));
            traCredit.setBusinessType((String) map3.get(traCredit.getBusinessType()));
            traCredit.setIdentifyType((String) map1.get(traCredit.getIdentifyType()));
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


