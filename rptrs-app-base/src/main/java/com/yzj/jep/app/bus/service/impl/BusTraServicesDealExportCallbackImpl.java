package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.model.TraService;
import com.yzj.jep.app.bus.model.TraUsecredit;
import com.yzj.jep.app.bus.service.TraServiceService;
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

@Service(value = BusTraServicesDealExportCallbackImpl.BEANID)
public class BusTraServicesDealExportCallbackImpl implements XlsExportCallback {
    public static final String BEANID = "BusTraServicesDealExportCallbackImpl";

    private static final String EXPORT_TEMPLATE_NAME = "BusTraServicesForm.xls";
    @Autowired
    DictItemService dictItemService;

    @Autowired
    UnitService unitService;

    @Autowired
    TraServiceService provideServicesService;


    @Override
    public String getExportTemplate() {
        return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_NAME;
    }

    @Override
    public String getExportFilename(Map parameters) throws ServiceException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        return "提供服务类" + sf.format(new Date()) + ".xls";
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
        provideServicesService = SpringContextHolder.getBean(TraServiceService.class);
        Map resultMap = new HashMap();
        //参数
        String data = (String) parameters.get("id");
        String[] datas = data.split(",");

        String relatedName = datas[0];
        String identlfyType = datas[1];
        String identNumber = datas[2];
        String status = datas[3];
        String businessType = datas[4];
        String regulatorOth1 = datas[5];
        String importantTrade = datas[6];
        String houseManageOrg = datas[7];
        String businessStartTime = datas[8];
        String businessEndTime = datas[9];
        //SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-mm-YYYY HH:mm:ss");
        UserProfile userProfile = UserProfileUtils.getUserProfile();
        List<String> codes = unitService.selectUnitAllOrDeptAllCodes(userProfile.getUnitCode());
        List<Filter> filter = new Filter.Builder()
                .eq("delflag", "1").build();

        if (!"%".equals(relatedName)) {
            filter.add(new Filter("relatedName", Operator.LIKE, relatedName));
        }
        if (!"".equals(identlfyType.trim())) {
            filter.add(new Filter("identlfyType", Operator.LIKE, identlfyType));
        }
        if (!"%".equals(identNumber)) {
            filter.add(new Filter("identNumber", Operator.LIKE, identNumber));
        }
        if (!"".equals(status.trim())) {
            filter.add(new Filter("status", Operator.LIKE, status));
        }
        if (!"".equals(businessType.trim())) {
            filter.add(new Filter("businessType", Operator.LIKE, businessType));
        }
        if (!"".equals(regulatorOth1.trim())) {
            filter.add(new Filter("regulatorOth1", Operator.LIKE, regulatorOth1));
        }
        if (!"".equals(importantTrade.trim())) {
            filter.add(new Filter("importantTrade", Operator.LIKE, importantTrade));
        }
        if (!"".equals(houseManageOrg.trim())) {
            filter.add(new Filter("houseManageOrg", Operator.LIKE, houseManageOrg));
        } else {
            filter.add(new Filter("houseManageOrg", Operator.IN, codes));
        }
        if (!"%".equals(businessStartTime.trim())) {
            filter.add(new Filter("businessStartTime", Operator.LIKE, businessStartTime));
        }
        if (!"%".equals(businessEndTime.trim())) {
            filter.add(new Filter("businessEndTime", Operator.LIKE, businessEndTime));
        }
        List<TraService> traServices = provideServicesService.select(filter);
        List<TraService> traServiceList = new ArrayList<>();

        //证件类型
        List<Filter> filter1 = new Filter.Builder().eq("dictCode", "BUS-IdentifyType").build();
        List<DictItem> dictItemList1 = dictItemService.select(filter1);
        Map busIdentifyType = new HashMap();
        for (DictItem dic : dictItemList1) {
            busIdentifyType.put(dic.getItemValue(), dic.getItemLabel());
        }
        //业务类型
        List<Filter> filter2 = new Filter.Builder().eq("dictCode", "BUS-ProvideServices").build();
        List<DictItem> dictItemList2 = dictItemService.select(filter2);
        Map busProvideServices = new HashMap();
        for (DictItem dic : dictItemList2) {
            busProvideServices.put(dic.getItemValue(), dic.getItemLabel());
        }
        //交易机构
        List<Filter> filter3 = new Filter.Builder().eq("dictCode", "BUS-GHOrg").build();
        List<DictItem> dictItemList3 = dictItemService.select(filter3);
        Map busGhOrg = new HashMap();
        for (DictItem dic : dictItemList3) {
            busGhOrg.put(dic.getItemValue(), dic.getItemLabel());
        }
        //关联方类型
        List<Filter> dictRelType = new Filter.Builder().eq("dictCode", "BUS-RelatedPartiesType").build();
        List<DictItem> dictItem_RelType = dictItemService.select(dictRelType);
        Map mapRelType = new HashMap();
        for (DictItem dic : dictItem_RelType) {
            mapRelType.put(dic.getItemValue(), dic.getItemLabel());
        }
        //交易类型
        List<Filter> filter4 = new Filter.Builder().eq("dictCode", "BUS-BusinessType").build();
        List<DictItem> dictItemList4 = dictItemService.select(filter4);
        Map busBusinessType = new HashMap();
        for (DictItem dic : dictItemList4) {
            busBusinessType.put(dic.getItemValue(), dic.getItemLabel());
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
        Map busApplystatus = new HashMap();
        for (DictItem dic : dictItemList6) {
            busApplystatus.put(dic.getItemValue(), dic.getItemLabel());
        }
        //是否重大关联
        List<Filter> filter7 = new Filter.Builder().eq("dictCode", "BUS-isNo").build();
        List<DictItem> dictItemList7 = dictItemService.select(filter7);
        Map busisNo = new HashMap();
        for (DictItem dic : dictItemList7) {
            busisNo.put(dic.getItemValue(), dic.getItemLabel());
        }
        //统计口径
        List<Filter> filter8 = new Filter.Builder().eq("dictCode", "BUS-Caliber").build();
        List<DictItem> dictItemList8 = dictItemService.select(filter8);
        Map busCaliber = new HashMap();
        for (DictItem dic : dictItemList8) {
            busCaliber.put(dic.getItemValue(), dic.getItemLabel());
        }

        for (TraService traSer : traServices) {
            //证件类型
            traSer.setIdentlfyType((String) busIdentifyType.get(traSer.getIdentlfyType()));
            //交易类型
            traSer.setTranType((String) busBusinessType.get(traSer.getTranType()));
            //业务类型
            traSer.setBusinessType((String) busProvideServices.get(traSer.getBusinessType()));
            //是否重大关联
            traSer.setImportantTrade((String) busisNo.get(traSer.getImportantTrade()));
            //统计口径
            traSer.setRegulatorOth1((String) busCaliber.get(traSer.getRegulatorOth1()));
            //状态
            traSer.setStatus((String) busApplystatus.get(traSer.getStatus()));
            //数据来源
            traSer.setDataResource((String) busDataS.get(traSer.getDataResource()));
            //管户机构
            traSer.setHouseManageOrg((String) busGhOrg.get(traSer.getHouseManageOrg()));
            //关联方类型
            traSer.setRelType((String) mapRelType.get(traSer.getRelType()));

            traServiceList.add(traSer);
        }
        resultMap.put("traSer", traServiceList);
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


