package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.model.TraUsecredit;
import com.yzj.jep.app.bus.service.TraUsecreditService;
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
import java.util.*;

@Service(value = TraUsecreditDealExportCallbackImpl.BEANID)
public class TraUsecreditDealExportCallbackImpl implements XlsExportCallback {
    public static final String BEANID = "TraUsecreditDealExportCallbackImpl";

    private static final String EXPORT_TEMPLATE_NAME = "BusTraUsecreditForm.xls";
    @Autowired
    DictItemService dictItemService;

    @Autowired
    UnitService unitService;
    @Autowired
    TraUsecreditService traUsecreditService;


    @Override
    public String getExportTemplate() {
        return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_NAME;
    }

    @Override
    public String getExportFilename(Map parameters) throws ServiceException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        return "用信类模板" + sf.format(new Date()) + ".xls";
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
        traUsecreditService = SpringContextHolder.getBean(TraUsecreditService.class);
        Map resultMap = new HashMap();
        //参数
        String data = (String) parameters.get("id");
        String[] datas = data.split(",");

        String relatedName = datas[0];
        String relType = datas[1];
        String identNumber = datas[2];
        String status = datas[3];
        String internalApplyNo = datas[4];
        String businessType = datas[5];
        String manageOrg = datas[6];
        String useCreditNum = datas[7];
        String dataResource = datas[8];
        //SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-mm-YYYY HH:mm:ss");
        UserProfile userProfile = UserProfileUtils.getUserProfile();
        List<String> codes = unitService.selectUnitAllOrDeptAllCodes(userProfile.getUnitCode());
        List<Filter> filter = new Filter.Builder()
                .eq("delflag", "1").build();

        if (!"%".equals(relatedName)) {
            filter.add(new Filter("relatedName", Operator.LIKE, relatedName));
        }
        if (!"".equals(relType.trim())) {
            filter.add(new Filter("relType", Operator.LIKE, relType));
        }
        if (!"%".equals(identNumber)) {
            filter.add(new Filter("identNumber", Operator.LIKE, identNumber));
        }
        if (!"".equals(status.trim())) {
            filter.add(new Filter("status", Operator.LIKE, status));
        }
        if (!"".equals(manageOrg.trim())) {
            filter.add(new Filter("manageOrg", Operator.LIKE, manageOrg));
        } else {
            filter.add(new Filter("manageOrg", Operator.IN, codes));
        }
        if (!"%".equals(internalApplyNo.trim())) {
            filter.add(new Filter("internalApplyNo", Operator.LIKE, internalApplyNo));
        }
        if (!"".equals(dataResource.trim())) {
            filter.add(new Filter("dataResource", Operator.LIKE, dataResource));
        }
        if (!"".equals(businessType.trim())) {
            filter.add(new Filter("businessType", Operator.LIKE, businessType));
        }
        if (!"%".equals(useCreditNum.trim())) {
            filter.add(new Filter("useCreditNum", Operator.LIKE, useCreditNum));
        }
        List<TraUsecredit> traUsecreditList = traUsecreditService.select(filter);
        List<TraUsecredit> traUseList = new ArrayList<>();

        //关联方类型
        List<Filter> filter1 = new Filter.Builder().eq("dictCode", "BUS-RelatedPartiesType").build();
        List<DictItem> dictItemList1 = dictItemService.select(filter1);
        Map busReltype = new HashMap();
        for (DictItem dic : dictItemList1) {
            busReltype.put(dic.getItemValue(), dic.getItemLabel());
        }
        //业务类型
        List<Filter> filter2 = new Filter.Builder().eq("dictCode", "BUS-UseCredit").build();
        List<DictItem> dictItemList2 = dictItemService.select(filter2);
        Map busUseCredit = new HashMap();
        for (DictItem dic : dictItemList2) {
            busUseCredit.put(dic.getItemValue(), dic.getItemLabel());
        }
        //交易机构
        List<Filter> filter3 = new Filter.Builder().eq("dictCode", "BUS-GHOrg").build();
        List<DictItem> dictItemList3 = dictItemService.select(filter3);
        Map busGhOrg = new HashMap();
        for (DictItem dic : dictItemList3) {
            busGhOrg.put(dic.getItemValue(), dic.getItemLabel());
        }
        //交易价格类型
        List<Filter> filter4 = new Filter.Builder().eq("dictCode", "BUS-traPrice").build();
        List<DictItem> dictItemList4 = dictItemService.select(filter4);
        Map busTraPrice = new HashMap();
        for (DictItem dic : dictItemList4) {
            busTraPrice.put(dic.getItemValue(), dic.getItemLabel());
        }
        //数据来源
        List<Filter> filter5 = new Filter.Builder().eq("dictCode", "BUS-DataSources").build();
        List<DictItem> dictItemList5 = dictItemService.select(filter5);
        Map busDataS = new HashMap();
        for (DictItem dic : dictItemList5) {
            busDataS.put(dic.getItemValue(), dic.getItemLabel());
        }
        //状态
        List<Filter> filter6 = new Filter.Builder().eq("dictCode", "BUS-Applystatus").build();
        List<DictItem> dictItemList6 = dictItemService.select(filter6);
        Map busStatus = new HashMap();
        for (DictItem dic : dictItemList6) {
            busStatus.put(dic.getItemValue(), dic.getItemLabel());
        }
        //利率类型
        List<Filter> filter7 = new Filter.Builder().eq("dictCode", "BUS-rateType").build();
        List<DictItem> dictItemList7 = dictItemService.select(filter7);
        Map rateTypeMap = new HashMap();
        for (DictItem dic : dictItemList7) {
            rateTypeMap.put(dic.getItemValue(), dic.getItemLabel());
        }
        //资金投向
        List<Filter> filter8 = new Filter.Builder().eq("dictCode", "BUS-Capitalinvestment").build();
        List<DictItem> dictItemList8 = dictItemService.select(filter8);
        Map capitalinvestmentMap = new HashMap();
        for (DictItem dic : dictItemList8) {
            capitalinvestmentMap.put(dic.getItemValue(), dic.getItemLabel());
        }
        for (TraUsecredit usecredit : traUsecreditList) {
            usecredit.setRelType((String) busReltype.get(usecredit.getRelType()));
            usecredit.setBusinessType((String) busUseCredit.get(usecredit.getBusinessType()));
            usecredit.setManageOrg((String) busGhOrg.get(usecredit.getManageOrg()));
            usecredit.setTraPriceType((String) busTraPrice.get(usecredit.getTraPriceType()));
            usecredit.setDataSources((String) busDataS.get(usecredit.getDataSources()));
            usecredit.setStatus((String) busStatus.get(usecredit.getStatus()));
            usecredit.setRateType((String) rateTypeMap.get(usecredit.getRateType()));
            usecredit.setCapitalInvestment((String) capitalinvestmentMap.get(usecredit.getCapitalInvestment()));
            traUseList.add(usecredit);
        }
        resultMap.put("traUsecredit", traUseList);
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


