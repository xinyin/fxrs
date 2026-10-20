package com.yzj.jep.app.sub.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.yzj.jep.app.bus.service.RelProService;
import com.yzj.jep.app.sub.model.Sublog;
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
import com.yzj.jep.app.sub.service.InsServiceViewService;
import com.yzj.jep.app.sub.model.InsServiceView;
import static com.yzj.jep.app.util.ConfidentialInfoUtil.idEncrypt;

/**
 * @author llc
 */
@Service(value = TraSubServiceExportCallbackImpl.BEANID)
public class TraSubServiceExportCallbackImpl implements XlsExportCallback {
    public static final String BEANID = "TraSubServiceExportCallbackImpl";

    private static final String EXPORT_TEMPLATE_NAME = "traSubService.xls";

    RelProService relProService;

    SublogService sublogService;

    InsServiceViewService insServiceViewService;
    @Autowired
    DictItemService dictItemService;

    private static final String PREFIX = "单笔服务类报送_";

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
        insServiceViewService = SpringContextHolder.getBean(InsServiceViewService.class);

        Map resultMap = new HashMap(16);

        //参数
        String pid = (String) parameters.get("id");
        //拿到所选sublo数据
        Sublog sublog = sublogService.get(Integer.parseInt(pid));
        List<Filter> filters = new ArrayList<>();
        String serviceSeq = sublog.getBatchDecimal();
        filters.add(new Filter("serviceSeq",serviceSeq));
        List<InsServiceView> insServiceView =insServiceViewService.select(filters);
        insServiceView.stream().map(insService -> {
            //是否重大关联交易
            boolean flag = org.apache.commons.lang3.StringUtils.equals(insService.getMajorTrade(), "0");
            if (flag) {
                insService.setMajorTrade("否");
            } else {
                insService.setMajorTrade("是");
            }
            if("PSN".equals(insService.getRelType())){
                insService.setRelNumber(idEncrypt(insService.getRelNumber()));

            }
            if(!ObjectUtil.isEmpty(insService.getTranDate())){
                insService.setTranDateString(DateUtils.formatDateShort(insService.getTranDate()));
            }
            if(!ObjectUtil.isEmpty(insService.getBusStartTime())){
                insService.setBusStartTimeString(DateUtils.formatDateShort(insService.getBusStartTime()));
            }
            if(!ObjectUtil.isEmpty(insService.getBusEndTime())){
                insService.setBusEndTimeString(DateUtils.formatDateShort(insService.getBusEndTime()));
            }
            //交易类型
            insService.setTranType(selectByValue(insService.getTranType(), "INS-tranType"));
            //业务类型
            insService.setBusType(selectByValue(insService.getBusType(), "INS-ServiceType"));
            //交易类别
            insService.setRelType(selectByValue(insService.getRelType(), "INS-relType"));
            //单笔关联交易类型
            insService.setSingleRelatedTranType(selectByValue(insService.getSingleRelatedTranType(),"INS-SingleRelatedTranType"));
            //达到重大关联交易的情形
            insService.setMajorType(selectByValue(insService.getMajorType(), "INS-majorType"));
            //交易方向
            insService.setTradingDirection(selectByValue(insService.getTradingDirection(),"INS-Tra-Direction"));
            //
            //insService.setIsOverseasinvestment(selectByValue(insService.getIsOverseasinvestment(),"BUS-isNo"));
            //投资方式
            //insService.setInvestmentMethod(selectByValue(insService.getInvestmentMethod(),"INS-FundsinvestmentMethod"));

            return insService;
        }).collect(Collectors.toList());
        resultMap.put("service", insServiceView);
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
