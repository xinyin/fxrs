package com.yzj.jep.app.sub.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.yzj.jep.app.bus.service.RelProService;
import com.yzj.jep.app.ins.service.TraBusinessService;
import com.yzj.jep.app.sub.model.InsFundsView;
import com.yzj.jep.app.sub.model.Sublog;
import com.yzj.jep.app.sub.service.InsFundsViewService;
import com.yzj.jep.app.sub.service.InsBusinessViewService;
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
import com.yzj.jep.app.sub.model.InsBusinessView;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.yzj.jep.app.util.ConfidentialInfoUtil.idEncrypt;

/**
 * @author llc
 */
@Service(value = TraBusinessExportCallbackImpl.BEANID)
public class TraBusinessExportCallbackImpl implements XlsExportCallback {
    public static final String BEANID = "TraBusinessExportCallbackImpl";

    private static final String EXPORT_TEMPLATE_NAME = "traBusiness.xls";

    RelProService relProService;

    SublogService sublogService;

    InsBusinessViewService insBusinessViewService;
    @Autowired
    DictItemService dictItemService;

    private static final String PREFIX = "单笔保险业务及其他类报送_";

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
        insBusinessViewService = SpringContextHolder.getBean(InsBusinessViewService.class);


        Map resultMap = new HashMap(16);

        //参数
        String pid = (String) parameters.get("id");
        //拿到所选sublo数据
        Sublog sublog = sublogService.get(Integer.parseInt(pid));
        List<Filter> filters = new ArrayList<>();
        String businessSeq = sublog.getBatchDecimal();
        filters.add(new Filter("businessSeq",businessSeq));
        List<InsBusinessView> insBusinessView =insBusinessViewService.select(filters);
        insBusinessView.stream().map(traBusiness -> {
            //是否重大关联交易
            boolean flag = org.apache.commons.lang3.StringUtils.equals(traBusiness.getMajorTrade(), "0");
            if (flag) {
                traBusiness.setMajorTrade("否");
            } else {
                traBusiness.setMajorTrade("是");
            }
            if("PSN".equals(traBusiness.getRelType())){
                traBusiness.setRelNumber(idEncrypt(traBusiness.getRelNumber()));

            }
            if(!ObjectUtil.isEmpty(traBusiness.getTranDate())){
                traBusiness.setTranDateString(DateUtils.formatDateShort(traBusiness.getTranDate()));
            }
            if(!ObjectUtil.isEmpty(traBusiness.getBusStartTime())){
                traBusiness.setBusStartTimeString(DateUtils.formatDateShort(traBusiness.getBusStartTime()));
            }
            if(!ObjectUtil.isEmpty(traBusiness.getBusEndTime())){
                traBusiness.setBusEndTimeString(DateUtils.formatDateShort(traBusiness.getBusEndTime()));
            }
            //交易类型
            traBusiness.setTranType(selectByValue(traBusiness.getTranType(), "INS-tranType"));
            //业务类型
            traBusiness.setBusType(selectByValue(traBusiness.getBusType(), "INT-InsBusinessType"));
            //交易类别
            traBusiness.setRelType(selectByValue(traBusiness.getRelType(), "INS-relType"));
            //单笔关联交易类型
            traBusiness.setSingleRelatedTranType(selectByValue(traBusiness.getSingleRelatedTranType(),"INS-SingleRelatedTranType"));
            //达到重大关联交易的情形
            traBusiness.setMajorType(selectByValue(traBusiness.getMajorType(), "INS-majorType"));
            //是否境外投资
            //traInterest.setIsOverseasinvestment(selectByValue(traInterest.getIsOverseasinvestment(),"BUS-isNo"));
            //投资方式
            //traInterest.setInvestmentMethod(selectByValue(traInterest.getInvestmentMethod(),"INS-FundsinvestmentMethod"));

            return traBusiness;
        }).collect(Collectors.toList());
        resultMap.put("business", insBusinessView);
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
