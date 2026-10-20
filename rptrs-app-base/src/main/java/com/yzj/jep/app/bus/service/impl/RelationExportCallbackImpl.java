package com.yzj.jep.app.bus.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.yzj.jep.app.bus.model.Rel;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.bus.service.RelService;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 关联关系维护导出
 */
@Service(value = RelationExportCallbackImpl.BEANID)
public class RelationExportCallbackImpl implements XlsExportCallback {

    public static final String BEANID = "RelationExportCallbackImpl";
    private static final String EXPORT_TEMPLATE_NAME = "Relation.xls";

    @Autowired
    RelService relService;

    @Autowired
    private RelPsnService relPsnService;

    @Autowired
    DictItemService dictItemService;

    @Override
    public String getExportTemplate() {
        return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_NAME;
    }

    @Override
    public String getExportFilename(Map parameters) throws ServiceException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String fileName = "关联关系维护_" + sf.format(new Date()) + ".xls";
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
     *
     * 编写业务代码
     *
     */
    @Override
    public Map executeExportCallback(Map parameters) throws ServiceException {
        Map resultMap = new HashMap();
        //参数
        String data = (String) parameters.get("id");
        List<Filter> filters = null;
        //获取导出的ID集合
        String[] ids = data.split(",");
        String type = ids[0];
        if(!"check".equals(type)){
            filters = new Filter.Builder().eq("delflag", "1").in("pid", ids).build();
        }else {
            //本层关联方名称
            String fromMemo = ids[1];
            //本层关联方代码
            String fromIdentifyNumber = ids[2];
            //关联关系
            String relPsnOrg = ids[3];
            //前层关联方名称
            String toMemo = ids[4];
            //前层关联方代码
            String toIdentifyNumber = ids[5];
            //数据状态
            String approvalStatus = ids[6];
            //与前一层是否有权益份额
            String equityShare = ids[7];
            //持有比例
            String shareholdingRatio = ids[8];
            //股权质押比例
            String pledgeRatio = ids[9];

            filters = new Filter.Builder().eq("delflag", "1").build();
            if (StringUtils.isNotBlank(fromMemo) && !"undefined".equals(fromMemo)) {
                filters.add(new Filter("fromMemo", Operator.LIKE, fromMemo));
            }
            if (StringUtils.isNotBlank(fromIdentifyNumber) && !"undefined".equals(fromIdentifyNumber)) {
                filters.add(new Filter("fromIdentifyNumber", fromIdentifyNumber));
            }
            if (StringUtils.isNotBlank(relPsnOrg) && !"undefined".equals(relPsnOrg)) {
                filters.add(new Filter("relPsnOrg", relPsnOrg));
            }
            if (StringUtils.isNotBlank(toMemo) && !"undefined".equals(toMemo)) {
                filters.add(new Filter("toMemo", Operator.LIKE, toMemo));
            }
            if (StringUtils.isNotBlank(toIdentifyNumber) && !"undefined".equals(toIdentifyNumber)) {
                filters.add(new Filter("toIdentifyNumber", toIdentifyNumber));
            }
            if (StringUtils.isNotBlank(approvalStatus) && !"undefined".equals(approvalStatus)) {
                filters.add(new Filter("approvalStatus", approvalStatus));
            }
            if (StringUtils.isNotBlank(equityShare) && !"undefined".equals(equityShare)) {
                filters.add(new Filter("equityShare", Operator.LIKE, equityShare));
            }
            if (StringUtils.isNotBlank(shareholdingRatio) && !"undefined".equals(shareholdingRatio)) {
                filters.add(new Filter("shareholdingRatio", shareholdingRatio));
            }
            if (StringUtils.isNotBlank(pledgeRatio) && !"undefined".equals(pledgeRatio)) {
                filters.add(new Filter("pledgeRatio", pledgeRatio));
            }
        }
        //获取符合条件的数据集合
        List<Rel> RelList = relService.select(filters);

        //关联方证件类型
        List<Filter> filters1 = new Filter.Builder().eq("dictCode", "BUS-OrgOrPsn").build();
        List<DictItem> dictItems1 = dictItemService.select(filters1);
        Map fromTypeMap = new HashMap();
        for (DictItem dic : dictItems1) {
            fromTypeMap.put(dic.getItemValue(), dic.getItemLabel());
        }
        //前一层关联方类型
        List<Filter> filters2 = new Filter.Builder().eq("dictCode", "BUS-OrgOrPsn").build();
        List<DictItem> dictItems2 = dictItemService.select(filters2);
        Map toTypeMap = new HashMap();
        for (DictItem dic : dictItems2) {
            toTypeMap.put(dic.getItemValue(), dic.getItemLabel());
        }
        //与前一层的关联关系
        List<Filter> filters3 = new Filter.Builder().eq("dictCode", "BUS-RelZZD").build();
        List<DictItem> dictItems3 = dictItemService.select(filters3);
        Map relPsnOrgMap = new HashMap();
        for (DictItem dic : dictItems3) {
            relPsnOrgMap.put(dic.getItemValue(), dic.getItemLabel());
        }
        //是否有权益份额
        List<Filter> filters4 = new Filter.Builder().eq("dictCode", "BUS-RelequityShare").build();
        List<DictItem> dictItems4 = dictItemService.select(filters4);
        Map equityShareMap = new HashMap();
        for (DictItem dic : dictItems4) {
            equityShareMap.put(dic.getItemValue(), dic.getItemLabel());
        }
        //数据状态
        List<Filter> filters5;
        filters5 = new Filter.Builder().eq("dictCode", "BUS-RelApplystatus").build();
        List<DictItem> dictItems5 = dictItemService.select(filters5);
        Map approvalStatusMap = new HashMap();
        for (DictItem dic : dictItems5) {
            approvalStatusMap.put(dic.getItemValue(), dic.getItemLabel());
        }
        //关系类型
        List<Filter> filters7 = new Filter.Builder().eq("dictCode", "BUS-RelationType").build();
        List<DictItem> dictItems7 = dictItemService.select(filters7);
        Map relationTypeMap = new HashMap();
        for (DictItem dic : dictItems7) {
            relationTypeMap.put(dic.getItemValue(), dic.getItemLabel());
        }

        for (Rel rel : RelList) {

            String fromType1 = rel.getFromType();
            if ("PSN".equals(fromType1)) {
                String fromIdentifyNumber = rel.getFromIdentifyNumber();

                if (StringUtils.isNotEmpty(fromIdentifyNumber)) {
                    RelPsn relPsn = relPsnService.selectByIdentifyNumber(rel.getFromIdentifyNumber());
                    if(ObjectUtil.isNotEmpty(relPsn)){
                        if("1".equals(relPsn.getIdentifyType())){
                            rel.setFromIdentifyNumber(idEncrypt(rel.getFromIdentifyNumber()));
                        }else{
                            rel.setFromIdentifyNumber(otherIdEncrypt(rel.getFromIdentifyNumber()));
                        }
                    }
                }
            }
            fromType1 = String.valueOf(fromTypeMap.get(fromType1));
            rel.setFromType(fromType1);

            String toType = rel.getToType();
            if("PSN".equals(rel.getToType())){
                //查询关联方证件类型
                if(StringUtils.isNotEmpty(rel.getToIdentifyNumber())){
                    RelPsn relPsn = relPsnService.selectByIdentifyNumber(rel.getToIdentifyNumber());
                    if(ObjectUtil.isNotEmpty(relPsn)){
                        if("1".equals(relPsn.getIdentifyType())){
                            rel.setToIdentifyNumber(idEncrypt(rel.getToIdentifyNumber()));
                        }else{
                            rel.setToIdentifyNumber(otherIdEncrypt(rel.getToIdentifyNumber()));
                        }
                    }
                }
            }
            toType = String.valueOf(toTypeMap.get(toType));
            rel.setToType(toType);

            String relPsnOrg1 = rel.getRelPsnOrg();
            relPsnOrg1 = String.valueOf(relPsnOrgMap.get(relPsnOrg1));
            rel.setShipString(relPsnOrg1);
            System.out.println(rel.getShipString());

            String relStatement = rel.getRelStatement();
            rel.setDescribeString(relStatement);

            String equityShare1 = rel.getEquityShare();
            equityShare1 = String.valueOf(equityShareMap.get(equityShare1));
            rel.setEquityShare(equityShare1);

            String approvalStatus1 = rel.getApprovalStatus();
            approvalStatus1 = String.valueOf(approvalStatusMap.get(approvalStatus1));
            rel.setApprovalStatus(approvalStatus1);

            String relationType1 = rel.getRelationType();
            relationType1 = String.valueOf(relationTypeMap.get(relationType1));
            rel.setRelationType(relationType1);

            Date modifiedTime1 = rel.getModifiedTime();
            Date activeTimeString = rel.getActiveTime();
            Date invalidTime = rel.getInvalidTime();
            SimpleDateFormat sfDate = new SimpleDateFormat("yyyy-MM-dd");
            if(invalidTime != null){
                rel.setActiveTimeString(sfDate.format(invalidTime));
            }
            rel.setActiveTimeString(sfDate.format(activeTimeString));
            rel.setModifiedTimeString(sfDate.format(modifiedTime1));

        }
        resultMap.put("rel", RelList);
        return resultMap;
    }

    @Override
    public Map executeExportCallback(HttpServletRequest request) throws ServiceException {
        return null;
    }

    @Override
    public void afterTransformXLS(Workbook workbook) {

    }

    //身份证前六后四脱敏
    public static String idEncrypt(String id) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(id) || (id.length() < 10)) {
            return id;
        }
        return id.replaceAll("(?<=\\w{6})\\w(?=\\w{4})", "*");
    }
    //自然人非身份证脱敏
    public static String otherIdEncrypt(String id) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(id) || (id.length() < 8)) {
            return id;
        }
        return id.replaceAll("(?<=\\w{3})\\w(?=\\w{2})", "*");
    }


}
