package com.yzj.jep.app.bus.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.yzj.jep.app.bus.model.Rel;
import com.yzj.jep.app.bus.model.RelHistory;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.service.RelHistoryService;
import com.yzj.jep.app.bus.service.RelOrgHistoryService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.bus.service.RelService;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.OrderType;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 关联关系维护导出
 */
@Service(value = RelHistoryExportCallbackImpl.BEANID)
public class RelHistoryExportCallbackImpl implements XlsExportCallback {

    public static final String BEANID = "RelHistoryExportCallbackImpl";
    private static final String EXPORT_TEMPLATE_NAME = "RelHistory.xls";

    @Autowired
    RelHistoryService relService;

    @Autowired
    DictItemService dictItemService;

    @Autowired
    private RelPsnService relPsnService;

    @Override
    public String getExportTemplate() {
        return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_NAME;
    }

    @Override
    public String getExportFilename(Map parameters) throws ServiceException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String fileName = "关联关系维护历史_" + sf.format(new Date()) + ".xls";
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
        RelHistoryService relHistoryService = SpringContextHolder.getBean(RelHistoryService.class);

        Map resultMap = new HashMap();
        String data = (String) parameters.get("id");
        String[] datas = data.split(",");
        String createTime = datas[1];
        String recordNumber = datas[2];
        String recordType = datas[3];
        List<Order> orders = new ArrayList<Order>();
        orders.add(new Order("createTime", OrderType.DESC));
        //获取符合条件的数据集合
        List<RelHistory> RelList = new ArrayList<>();
        // 时点查询列表数据
        if(StrUtil.isNotBlank(createTime)){
            RelList =relHistoryService.queryRecordNumberData(createTime);
        }else {
            RelList = relHistoryService.queryRecordNumber(recordType, recordNumber);
        }

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
        //关系是否仍然有效
        List<Filter> filters6 = new Filter.Builder().eq("dictCode", "BUS-isNo").build();
        List<DictItem> dictItems6 = dictItemService.select(filters6);
        Map isValidMap = new HashMap();
        for (DictItem dic : dictItems6) {
            isValidMap.put(dic.getItemValue(), dic.getItemLabel());
        }
        try {
            for (RelHistory rel : RelList) {
                String fromType1 = rel.getFromType();

                if("PSN".equals(fromType1)){
                    //查询关联方证件类型
                    if(StringUtils.isNotEmpty(rel.getFromIdentifyNumber())){
                        RelPsn relPsn = relPsnService.selectByIdentifyNumber(rel.getFromIdentifyNumber());
                        if(ObjectUtil.isNotEmpty(relPsn)){
                            if("1".equals(relPsn.getIdentifyType())){
                                rel.setFromIdentifyNumber(idEncrypt(rel.getFromIdentifyNumber()));
                            }else{
                                rel.setFromIdentifyNumber(otherIdEncrypt(rel.getFromIdentifyNumber()));
                            }
                        } else {
                            rel.setFromIdentifyNumber(idEncrypt(rel.getFromIdentifyNumber()));
                        }
                    }
                }

                fromType1 = String.valueOf(fromTypeMap.get(fromType1));
                rel.setFromType(fromType1);

                String toType = rel.getToType();

                if("PSN".equals(toType)){
                    //查询关联方证件类型
                    if(StringUtils.isNotEmpty(rel.getToIdentifyNumber())){
                        RelPsn relPsn = relPsnService.selectByIdentifyNumber(rel.getToIdentifyNumber());
                        if(ObjectUtil.isNotEmpty(relPsn)){
                            if("1".equals(relPsn.getIdentifyType())){
                                rel.setToIdentifyNumber(idEncrypt(rel.getToIdentifyNumber()));
                            }else{
                                rel.setToIdentifyNumber(otherIdEncrypt(rel.getToIdentifyNumber()));
                            }
                        } else {
                            rel.setToIdentifyNumber(idEncrypt(rel.getToIdentifyNumber()));
                        }
                    }
                }
                toType = String.valueOf(toTypeMap.get(toType));
                rel.setToType(toType);

                String relPsnOrg1 = rel.getRelPsnOrg();
                relPsnOrg1 = String.valueOf(relPsnOrgMap.get(relPsnOrg1));
                rel.setShipString(relPsnOrg1);

                String relStatement = rel.getRelStatement();
                rel.setDescribeString(relStatement);

                String equityShare1 = rel.getEquityShare();
                equityShare1 = String.valueOf(equityShareMap.get(equityShare1));
                rel.setEquityShare(equityShare1);

                String approvalStatus1 = rel.getApprovalStatus();
                approvalStatus1 = String.valueOf(approvalStatusMap.get(approvalStatus1));
                rel.setApprovalStatus(approvalStatus1);

                String isValid = rel.getIsValid();
                isValid = String.valueOf(isValidMap.get(isValid));
                rel.setIsValid(isValid);

                Date activeTimeString = rel.getActiveTime();
                Date invalidTime = rel.getInvalidTime();
                SimpleDateFormat sfDate = new SimpleDateFormat("yyyy-MM-dd");
                if(invalidTime != null){
                    rel.setActiveTimeString(sfDate.format(invalidTime));
                }
                rel.setActiveTimeString(sfDate.format(activeTimeString));

            }
        }catch (Exception e){
            e.printStackTrace();
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

    private List<Filter>  builderFilter(Map parameters){
        List<Filter> filters = null;

        //参数
        String data = (String) parameters.get("id");
        //获取导出的ID集合
        String[] ids = data.split(",");
        String type = ids[0];
        if(!"check".equals(type)){
            filters = new Filter.Builder().eq("delflag", "1").in("pid", ids).build();
        }else {
            //关联方名称
            String fromMemo = ids[1];
            //关联方代码
            String fromIdentifyNumber = ids[2];
            //关联方类型
            String fromType = ids[3];
            //前一层关联方名称
            String toMemo = ids[4];
            //前一层关联方代码
            String toIdentifyNumber = ids[5];
            //前一层关联方类型
            String toType = ids[6];
            //持有比例（%）
            String shareholdingRatio = ids[7];
            //股权质押比例(%)
            String pledgeRatio = ids[8];
            //生效日期
            String activeTime = ids[9];
            //失效日期
            String relInvalidTime = ids[10];

            //经办部门
            String transactionDep = ids[11];
            //经办人
            String operator = ids[12];
            //更新日期
            String createTime = ids[13];
            //是否有权益份额
            String equityShare = ids[14];
            //关联关系
            String relPsnOrg = ids[15];
            //数据状态
            String approvalStatus = ids[16];
            //该关系是否有效
            String isNo = ids[17];

            filters = new Filter.Builder().build();
            filters.add(new Filter("delflag", Operator.EQ, "1"));
            if (StringUtils.isNotBlank(fromMemo) && !"undefined".equals(fromMemo)) {
                filters.add(new Filter("fromMemo", Operator.LIKE, fromMemo));
            }
            if (StringUtils.isNotBlank(fromIdentifyNumber) && !"undefined".equals(fromIdentifyNumber)) {
                filters.add(new Filter("fromIdentifyNumber", fromIdentifyNumber));
            }
            if (StringUtils.isNotBlank(fromType) && !"undefined".equals(fromType)) {
                filters.add(new Filter("fromType",Operator.LIKE, fromType));
            }
            if (StringUtils.isNotBlank(equityShare) && !"undefined".equals(equityShare)) {
                filters.add(new Filter("equityShare",Operator.LIKE, equityShare));
            }
            if (StringUtils.isNotBlank(relPsnOrg) && !"undefined".equals(relPsnOrg)) {
                filters.add(new Filter("relPsnOrg",Operator.LIKE, relPsnOrg));
            }
            if (StringUtils.isNotBlank(approvalStatus) && !"undefined".equals(approvalStatus)) {
                filters.add(new Filter("approvalStatus",Operator.LIKE, approvalStatus));
            }

            if (StringUtils.isNotBlank(toMemo) && !"undefined".equals(toMemo)) {
                filters.add(new Filter("toMemo", toMemo));
            }
            if (StringUtils.isNotBlank(toIdentifyNumber) && !"undefined".equals(toIdentifyNumber)) {
                filters.add(new Filter("toIdentifyNumber", toIdentifyNumber));
            }
            if (StringUtils.isNotBlank(toType) && !"undefined".equals(toType)) {
                filters.add(new Filter("toType",Operator.LIKE, toType));
            }
            if (StringUtils.isNotBlank(shareholdingRatio) && !"undefined".equals(shareholdingRatio)) {
                filters.add(new Filter("shareholdingRatio", shareholdingRatio));
            }
            if (StringUtils.isNotBlank(pledgeRatio) && !"undefined".equals(pledgeRatio)) {
                filters.add(new Filter("pledgeRatio", pledgeRatio));
            }
            if (StringUtils.isNotBlank(activeTime) && !"undefined".equals(activeTime)) {
                filters.add(new Filter("activeTime", activeTime));
            }
            if (StringUtils.isNotBlank(relInvalidTime) && !"undefined".equals(relInvalidTime)) {
                filters.add(new Filter("relInvalidTime", relInvalidTime));
            }

            if (StringUtils.isNotBlank(transactionDep) && !"undefined".equals(transactionDep)) {
                filters.add(new Filter("transactionDep", transactionDep));
            }
            if (StringUtils.isNotBlank(operator) && !"undefined".equals(operator)) {
                filters.add(new Filter("operator", operator));
            }
            if (StringUtils.isNotBlank(createTime) && !"undefined".equals(createTime)) {
                filters.add(new Filter("createTime", createTime));
            }

            if (StringUtils.isNotBlank(isNo) && !"undefined".equals(isNo)) {
                filters.add(new Filter("isValid", isNo));
            }
        }
        return filters;
    }

    //身份证前六后四脱敏
    public static String idEncrypt(String id) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(id)) {
            return id;
        }

        if (id.length() <= 10) {
            return id.replaceAll("(?<=.).(?=.)", "*");
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
