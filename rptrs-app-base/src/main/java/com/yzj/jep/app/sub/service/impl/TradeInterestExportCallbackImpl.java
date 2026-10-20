package com.yzj.jep.app.sub.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.yzj.jep.app.bus.service.RelProService;
import com.yzj.jep.app.sub.model.InsFundsView;
import com.yzj.jep.app.sub.model.Sublog;
import com.yzj.jep.app.sub.service.InsFundsViewService;
import com.yzj.jep.app.sub.service.SublogService;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import com.yzj.jep.orm.entity.Filter;
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
import com.yzj.jep.app.sub.service.InsInterestViewService;
import com.yzj.jep.app.sub.model.InsInterestView;

/**
 * @author llc
 */
@Service(value = TradeInterestExportCallbackImpl.BEANID)
public class TradeInterestExportCallbackImpl implements XlsExportCallback {
    public static final String BEANID = "TradeInterestExportCallbackImpl";

    private static final String EXPORT_TEMPLATE_NAME = "traInterest.xls";

    RelProService relProService;

    SublogService sublogService;

    InsInterestViewService insInterestViewService;
    @Autowired
    DictItemService dictItemService;

    private static final String PREFIX = "单笔利益转移类报送_";

    private static final String SUFFIX = ".xls";


    @Override
    public String getExportTemplate() {
        return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_NAME;
    }

    @Override
    public String getExportFilename(Map parameters) throws ServiceException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String fileName = PREFIX + sf.format(new Date()) + SUFFIX;
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
        relProService = SpringContextHolder.getBean(RelProService.class);
        sublogService = SpringContextHolder.getBean(SublogService.class);
        insInterestViewService = SpringContextHolder.getBean(InsInterestViewService.class);

        Map resultMap = new HashMap(16);

        //参数
        String pid = (String) parameters.get("id");
        //拿到所选sublo数据
        Sublog sublog = sublogService.get(Integer.parseInt(pid));
        List<Filter> filters = new ArrayList<>();
        String interestSeq = sublog.getBatchDecimal();
        filters.add(new Filter("interestSeq",interestSeq));
        List<InsInterestView> insInterestView =insInterestViewService.select(filters);
        insInterestView.stream().map(insInterest -> {
            //是否重大关联交易
            boolean flag = org.apache.commons.lang3.StringUtils.equals(insInterest.getMajorTrade(), "0");
            if (flag) {
                insInterest.setMajorTrade("否");
            } else {
                insInterest.setMajorTrade("是");
            }
            if("PSN".equals(insInterest.getRelType())){
                insInterest.setRelNumber(idEncrypt(insInterest.getRelNumber()));

            }
            if(!ObjectUtil.isEmpty(insInterest.getTranDate())){
                insInterest.setTranDateString(DateUtils.formatDateShort(insInterest.getTranDate()));
            }
            if(!ObjectUtil.isEmpty(insInterest.getBusStartTime())){
                insInterest.setBusStartTimeString(DateUtils.formatDateShort(insInterest.getBusStartTime()));
            }
            if(!ObjectUtil.isEmpty(insInterest.getBusEndTime())){
                insInterest.setBusEndTimeString(DateUtils.formatDateShort(insInterest.getBusEndTime()));
            }
            //交易类型
            insInterest.setTranType(selectByValue(insInterest.getTranType(), "INS-tranType"));
            //业务类型
            insInterest.setBusType(selectByValue(insInterest.getBusType(), "INT-BusinessTypes"));
            //交易类别
            insInterest.setRelType(selectByValue(insInterest.getRelType(), "INS-relType"));
            //单笔关联交易类型
            insInterest.setSingleRelatedTranType(selectByValue(insInterest.getSingleRelatedTranType(),"INS-SingleRelatedTranType"));
            //达到重大关联交易的情形
            insInterest.setMajorType(selectByValue(insInterest.getMajorType(), "INS-majorType"));
            //交易方向
            insInterest.setTradingDirection(selectByValue(insInterest.getTradingDirection(), "INS-Tra-Directions"));
            //
            //insInterest.setIsOverseasinvestment(selectByValue(insInterest.getIsOverseasinvestment(),"BUS-isNo"));
            //投资方式
            //insInterest.setInvestmentMethod(selectByValue(insInterest.getInvestmentMethod(),"INS-FundsinvestmentMethod"));

            return insInterest;
        }).collect(Collectors.toList());
        resultMap.put("interest", insInterestView);
        return resultMap;
    }



    /**
     * @param property1
     * @param value1
     * @return
     * @desc 构建查询的filter
     */
    public List<Filter> builderFilter(String property1, String value1) {
        List<Filter> filters = new Filter.Builder().eq(property1, value1).build();
        return filters;
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
