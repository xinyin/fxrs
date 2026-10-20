package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.model.TraAsset;
import com.yzj.jep.app.bus.model.TraUsecredit;
import com.yzj.jep.app.bus.service.TraAssetService;
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
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Service(value = TraAssetDealExportCallbackImpl.BEANID)
public class TraAssetDealExportCallbackImpl implements XlsExportCallback {
    public static final String BEANID = "TraAssetDealExportCallbackImpl";

    private static final String EXPORT_TEMPLATE_NAME = "BusTraAssetForm.xls";
    @Autowired
    DictItemService dictItemService;

    @Autowired
    UnitService unitService;

    TraAssetService traAssetService;


    @Override
    public String getExportTemplate() {
        return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_NAME;
    }

    @Override
    public String getExportFilename(Map parameters) throws ServiceException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        return "资产转移类" + sf.format(new Date()) + ".xls";
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
        traAssetService = SpringContextHolder.getBean(TraAssetService.class);
        Map resultMap = new HashMap();
        //参数
        String data = (String) parameters.get("id");
        String[] datas = data.split(",");

        String relatedName = datas[0];
        String identNumber = datas[1];
        String status = datas[2];
        String businessType = datas[3];
        String regulatorOth1 = datas[4];
        String importantTrade = datas[5];
        String manageOrg = datas[6];
        String businessStartTime = datas[7];
        String businessEndTime = datas[8];
        //SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-mm-YYYY HH:mm:ss");
        UserProfile userProfile = UserProfileUtils.getUserProfile();
        List<String> codes = unitService.selectUnitAllOrDeptAllCodes(userProfile.getUnitCode());
        List<Filter> filter = new Filter.Builder()
                .eq("delflag", "1").build();

        if (!"%".equals(relatedName)) {
            filter.add(new Filter("relatedName", Operator.LIKE, relatedName));
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
        if (!"".equals(manageOrg.trim())) {
            filter.add(new Filter("manageOrg", Operator.LIKE, manageOrg));
        } else {
            filter.add(new Filter("manageOrg", Operator.IN, codes));
        }
        if (!"".equals(importantTrade.trim())) {
            filter.add(new Filter("importantTrade", Operator.LIKE, importantTrade));
        }
        if (!"%".equals(businessStartTime.trim())) {
            filter.add(new Filter("businessStartTime", Operator.LIKE, businessStartTime));
        }
        if (!"%".equals(businessEndTime.trim())) {
            filter.add(new Filter("businessEndTime", Operator.LIKE, businessEndTime));
        }
        List<TraAsset> traAssets = traAssetService.select(filter);
        List<TraAsset> traAssetList = new ArrayList<>();


        //关联方类型
        List<Filter> dictRelType = new Filter.Builder().eq("dictCode", "BUS-RelatedPartiesType").build();
        List<DictItem> dictItem_RelType = dictItemService.select(dictRelType);
        Map mapRelType = new HashMap();
        for (DictItem dic : dictItem_RelType) {
            mapRelType.put(dic.getItemValue(), dic.getItemLabel());
        }
        //交易机构
        List<Filter> filter3 = new Filter.Builder().eq("dictCode", "BUS-GHOrg").build();
        List<DictItem> dictItemList3 = dictItemService.select(filter3);
        Map busGhOrg = new HashMap();
        for (DictItem dic : dictItemList3) {
            busGhOrg.put(dic.getItemValue(), dic.getItemLabel());
        }
        //证件类型
        List<Filter> filter1 = new Filter.Builder().eq("dictCode", "BUS-IdentifyType").build();
        List<DictItem> dictItemList1 = dictItemService.select(filter1);
        Map busIdType = new HashMap();
        for (DictItem dic : dictItemList1) {
            busIdType.put(dic.getItemValue(), dic.getItemLabel());
        }
        //交易类型
        List<Filter> filter4 = new Filter.Builder().eq("dictCode", "BUS-BusinessType").build();
        List<DictItem> dictItemList4 = dictItemService.select(filter4);
        Map busBusinessType = new HashMap();
        for (DictItem dic : dictItemList4) {
            busBusinessType.put(dic.getItemValue(), dic.getItemLabel());
        }
        //业务类型
        List<Filter> filter2 = new Filter.Builder().eq("dictCode", "BUS-AssetTransfer").build();
        List<DictItem> dictItemList2 = dictItemService.select(filter2);
        Map busAssetTransfer = new HashMap();
        for (DictItem dic : dictItemList2) {
            busAssetTransfer.put(dic.getItemValue(), dic.getItemLabel());
        }
        //是否重大关联
        List<Filter> filter5 = new Filter.Builder().eq("dictCode", "BUS-isNo").build();
        List<DictItem> dictItemList5 = dictItemService.select(filter5);
        Map busisNo = new HashMap();
        for (DictItem dic : dictItemList5) {
            busisNo.put(dic.getItemValue(), dic.getItemLabel());
        }
        //状态
        List<Filter> filter6 = new Filter.Builder().eq("dictCode", "BUS-Applystatus").build();
        List<DictItem> dictItemList6 = dictItemService.select(filter6);
        Map busStatus = new HashMap();
        for (DictItem dic : dictItemList6) {
            busStatus.put(dic.getItemValue(), dic.getItemLabel());
        }
        //统计口径
        List<Filter> filter7 = new Filter.Builder().eq("dictCode", "BUS-Caliber").build();
        List<DictItem> dictItemList7 = dictItemService.select(filter7);
        Map busCaliber = new HashMap();
        for (DictItem dic : dictItemList7) {
            busCaliber.put(dic.getItemValue(), dic.getItemLabel());
        }
        //是否重大关联
        List<Filter> filter8 = new Filter.Builder().eq("dictCode", "BUS-DataSources").build();
        List<DictItem> dictItemList8 = dictItemService.select(filter8);
        Map busDataS = new HashMap();
        for (DictItem dic : dictItemList8) {
            busDataS.put(dic.getItemValue(), dic.getItemLabel());
        }
        for (TraAsset traA : traAssets) {
            //管户机构
            traA.setManageOrg((String) busGhOrg.get(traA.getManageOrg()));
            //证件类型
            traA.setIdentifyType((String) busIdType.get(traA.getIdentifyType()));
            //交易类型
            traA.setTranType((String) busBusinessType.get(traA.getTranType()));
            //业务类型
            traA.setBusinessType((String) busAssetTransfer.get(traA.getBusinessType()));
            //是否重大关联交易
            traA.setImportantTrade((String) busisNo.get(traA.getImportantTrade()));
            //统计口径
            traA.setRegulatorOth1((String) busCaliber.get(traA.getRegulatorOth1()));
            //状态
            traA.setStatus((String) busStatus.get(traA.getStatus()));
            //数据来源
            traA.setDataResource((String) busDataS.get(traA.getDataResource()));
            //关联方类型
            traA.setRelType((String) mapRelType.get(traA.getRelType()));

            //表格中 交易批准日期 由 交易日期 代替
            traAssetList.add(traA);
        }
        resultMap.put("traA", traAssetList);
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


