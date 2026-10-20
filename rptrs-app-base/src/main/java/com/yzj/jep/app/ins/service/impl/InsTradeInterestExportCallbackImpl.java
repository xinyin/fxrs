package com.yzj.jep.app.ins.service.impl;

import com.yzj.jep.app.bus.service.ProductService;
import com.yzj.jep.app.ins.model.TraInterest;
import com.yzj.jep.app.ins.service.TraInterestService;
import com.yzj.jep.app.sys.util.Constants;
import com.yzj.jep.app.util.ConfidentialInfoUtil;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.web.entity.NameValuePair;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.yzj.jep.app.util.ConfidentialInfoUtil.idEncrypt;

@Service(value = InsTradeInterestExportCallbackImpl.BEANID)
public class InsTradeInterestExportCallbackImpl implements XlsExportCallback {
    public static final String BEANID = "InsTradeInterestExportCallbackImpl";

    private static final String EXPORT_TEMPLATE_NAME = "InsTraInterestExportTemplate.xls";

    @Autowired
    private TraInterestService traInterestService;

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
        String fileName = "利益转移类交易信息_" + sf.format(new Date()) + ".xls";
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

    /*
     * 编写业务代码
     * */
    @Override
    public Map executeExportCallback(Map parameters) throws ServiceException {
        Map resultMap = new HashMap();
        //参数
        String data = (String) parameters.get("id");
        if ("00".equals(data)) {
            resultMap.put("interest", new TraInterest());
            return resultMap;
        }
        List<Filter> filters = null;
        String[] ids = data.split(",");
        String type = ids[0];
        String relName = ids[1];
        String busType = ids[2];
        String relNumber = ids[3];
        String status = ids[4];
        String considerType = ids[5];
        String majorTrade = ids[6];
        String startTranDate = ids[7];
        String endTranDate = ids[8];
        String contractNo = ids[9];
        filters = new Filter.Builder().eq("delflag", "1").build();
        if (!"%".equals(relName.trim())) {
            filters.add(new Filter("relName", relName));
        }
        if (!"".equals(busType.trim())) {
            filters.add(new Filter("busType", busType));
        }
        if (!"%".equals(relNumber.trim())) {
            filters.add(new Filter("relNumber", relNumber));
        }

        if (!"".equals(status.trim())) {
            filters.add(new Filter("status", status));
        }
        if (!"".equals(considerType.trim())) {
            filters.add(new Filter("considerType", considerType));
        }
        if (!"".equals(majorTrade.trim())) {
            filters.add(new Filter("majorTrade", majorTrade));
        }
        if (!"%".equals(startTranDate.trim())) {
            Date startTranDate1 = DateUtils.parseDate(startTranDate);
            String format = DateUtils.format(startTranDate1, "yyyy-MM-dd");
            filters.add(new Filter("contractDate", Operator.GE, format));
        }
        if (!"%".equals(endTranDate.trim())) {
            Date endTranDate1 = DateUtils.parseDate(endTranDate);
            String format = DateUtils.format(endTranDate1, "yyyy-MM-dd");
            filters.add(new Filter("contractDate", Operator.LE, format));
        }

        if (!"%".equals(contractNo.trim())) {
            Date contractNo1 = DateUtils.parseDate(contractNo);
            String format = DateUtils.format(contractNo1, "yyyy-MM-dd");
            filters.add(new Filter("contractNo", Operator.LIKE, format));
        }

        List<TraInterest> traInterests = traInterestService.select(filters);
        traInterests.stream().forEach( e -> {
            e.setRelNumber(idEncrypt(e.getRelNumber()));
        });
        traInterests.stream().map(traInterest -> {
            //是否重大关联交易
            boolean flag = org.apache.commons.lang3.StringUtils.equals(traInterest.getMajorTrade(), "0");
            if (flag) {
                traInterest.setMajorTrade("否");
            } else {
                traInterest.setMajorTrade("是");
            }

            //是否为控股子公司
            flag = org.apache.commons.lang3.StringUtils.equals(traInterest.getIsHoldingSubsidiary(), "0");
            if (flag) {
                traInterest.setIsHoldingSubsidiary("否");
            } else {
                traInterest.setIsHoldingSubsidiary("是");
            }
            //是否为统一交易协议
            flag = org.apache.commons.lang3.StringUtils.equals(traInterest.getIsAgreement(), "0");
            if (flag) {
                traInterest.setIsAgreement("否");
            } else {
                traInterest.setIsAgreement("是");
            }
            //交易定价是否为国家规定
            flag = org.apache.commons.lang3.StringUtils.equals(traInterest.getIsDealPrice(), "0");
            if (flag) {
                traInterest.setIsDealPrice("否");
            } else {
                traInterest.setIsDealPrice("是");
            }

            //交易现金认购
            flag = org.apache.commons.lang3.StringUtils.equals(traInterest.getIsCashSubscription(), "0");
            if (flag) {
                traInterest.setIsCashSubscription("否");
            } else {
                traInterest.setIsCashSubscription("是");
            }
            //交易现金认购
            flag = org.apache.commons.lang3.StringUtils.equals(traInterest.getIsconsiderSituation(), "0");
            if (flag) {
                traInterest.setIsconsiderSituation("否");
            } else {
                traInterest.setIsconsiderSituation("是");
            }
            //交易主体名称
            traInterest.setTraSubName(traInterest.getTraSubName());
            //控股子公司证件类型
            traInterest.setHoldingSubsidiaryType(selectByValue(traInterest.getHoldingSubsidiaryType(), "BUS-ORGIdentifyType"));
            //交易类型
            traInterest.setTranType(selectByValue(traInterest.getTranType(), "INS-tranType"));
            //交易对手类型
            traInterest.setRelType(selectByValue(traInterest.getRelType(), "INS-relType"));
            //业务类型
            traInterest.setBusType(selectByValue(traInterest.getBusType(), "INT-BusinessType"));
            //审议类型
            traInterest.setConsiderType(selectByValue(traInterest.getConsiderType(), "INS-considerType"));
            //达到重大关联交易的情形
            traInterest.setMajorType(selectByValue(traInterest.getMajorType(), "INS-majorType"));
            //交易状态
            traInterest.setStatus(selectByValue(traInterest.getStatus(), "BUS-Applystatus"));
            //交易方向
            traInterest.setTradingDirection(selectByValue(traInterest.getTradingDirection(),"INS-Tra-Directions"));
            //免于审议情形
            traInterest.setConsiderSituation(selectByValue(traInterest.getConsiderSituation(),"INS-considerSituation"));
           /* traInterest.setRelNumber(new ConfidentialInfoUtil().hideAllIdCardNum(traInterest.getRelNumber()));*/
            traInterest.setRelNumber(traInterest.getRelNumber());
            //来源系统唯一编码
            traInterest.setUniqueCode(traInterest.getUniqueCode());
            //来源系统
            traInterest.setSourceSystem(traInterest.getSourceSystem());
            //来源模块
            traInterest.setSourceModule(traInterest.getSourceModule());
            //收付款标识
            traInterest.setPaymentId(selectByValue(traInterest.getPaymentId(), "INS-paymentId"));
            //场景
            traInterest.setScene(traInterest.getScene());
            //单笔关联交易类型
            traInterest.setSingleRelatedTranType(selectByValue(traInterest.getSingleRelatedTranType(),"INS-SingleRelatedTranType"));
            //是否需逐笔报告
            traInterest.setEachReport(selectByValue(traInterest.getEachReport(),"BUS-isNo"));
            //是否需逐笔披露
            traInterest.setEachDisclosure(selectByValue(traInterest.getEachDisclosure(),"BUS-isNo"));
            //是否需要补录关联方信息
            traInterest.setIsCollectRelated(selectByValue(traInterest.getIsCollectRelated(),"BUS-isNo"));
            //担保条件
            traInterest.setGuaranteeConditions(traInterest.getGuaranteeConditions());

            traInterest.setProtocolTransactionSymbol(selectByValue(traInterest.getProtocolTransactionSymbol(), "BUS-isNo"));
            traInterest.setFundsFlowNumber(traInterest.getFundsFlowNumber());
            traInterest.setContractStatus(selectByValue(traInterest.getContractStatus(), "BUS-contractStatus"));
            traInterest.setDataVersion(traInterest.getDataVersion());
            return traInterest;
        }).collect(Collectors.toList());
        resultMap.put("interest", traInterests);
        return resultMap;
    }

    @Override
    public Map executeExportCallback(HttpServletRequest request) throws ServiceException {
        return null;
    }

    @Override
    public void afterTransformXLS(Workbook workbook) {

    }

    private String selectByValue(String value, String simpleName) {
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
