package com.yzj.jep.app.sub.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.yzj.jep.app.bus.service.RelProService;
import com.yzj.jep.app.sub.model.InsAllfundsView;
import com.yzj.jep.app.sub.model.InsFundsView;
import com.yzj.jep.app.sub.model.Sublog;
import com.yzj.jep.app.sub.service.InsAllfundsViewService;
import com.yzj.jep.app.sub.service.InsFundsViewService;
import com.yzj.jep.app.sub.service.SublogService;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.commons.DateUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.OrderType;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.web.entity.NameValuePair;
import com.yzj.jep.web.util.RequestUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.yzj.jep.app.util.ConfidentialInfoUtil.idEncrypt;

/**
 * @author llc
 */
@Service(value = TraFundsExportCallbackImpl.BEANID)
public class TraFundsExportCallbackImpl implements XlsExportCallback {
    public static final String BEANID = "TraFundsExportCallbackImpl";

    private static final String EXPORT_TEMPLATE_NAME = "traFunds.xls";

    RelProService relProService;

    SublogService sublogService;


    InsFundsViewService insFundsViewService;
    @Autowired
    DictItemService dictItemService;

    private static final String PREFIX = "单笔资金运用类报送_";

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
        insFundsViewService = SpringContextHolder.getBean(InsFundsViewService.class);

        Map resultMap = new HashMap(16);

        //参数
        String pid = (String) parameters.get("id");
        //拿到所选sublo数据
        Sublog sublog = sublogService.get(Integer.parseInt(pid));
        List<Filter> filters = new ArrayList<>();
        String fundsSeq = sublog.getBatchDecimal();
        filters.add(new Filter("fundsSeq",fundsSeq));
        List<InsFundsView> insFundsView =insFundsViewService.select(filters);
        insFundsView.stream().map(traInterest -> {
            //是否重大关联交易
            boolean flag = org.apache.commons.lang3.StringUtils.equals(traInterest.getMajorTrade(), "0");
            if (flag) {
                traInterest.setMajorTrade("否");
            } else {
                traInterest.setMajorTrade("是");
            }
            if("PSN".equals(traInterest.getRelType())){
                traInterest.setRelNumber(idEncrypt(traInterest.getRelNumber()));

            }
            if(!ObjectUtil.isEmpty(traInterest.getTranDate())){
                traInterest.setTranDateString(DateUtils.formatDateShort(traInterest.getTranDate()));
            }
            if(!ObjectUtil.isEmpty(traInterest.getBusStartTime())){
                traInterest.setBusStartTimeString(DateUtils.formatDateShort(traInterest.getBusStartTime()));
            }
            if(!ObjectUtil.isEmpty(traInterest.getBusEndTime())){
                traInterest.setBusEndTimeString(DateUtils.formatDateShort(traInterest.getBusEndTime()));
            }
            //交易类型
            traInterest.setTranType(selectByValue(traInterest.getTranType(), "INS-tranType"));
            //业务类型
            traInterest.setBusType(selectByValue(traInterest.getBusType(), "INS-FundsType"));
            //交易类别
            traInterest.setRelType(selectByValue(traInterest.getRelType(), "INS-relType"));
            //单笔关联交易类型
            traInterest.setSingleRelatedTranType(selectByValue(traInterest.getSingleRelatedTranType(),"INS-SingleRelatedTranType"));
            //达到重大关联交易的情形
            traInterest.setMajorType(selectByValue(traInterest.getMajorType(), "INS-majorType"));
            //
            traInterest.setIsOverseasinvestment(selectByValue(traInterest.getIsOverseasinvestment(),"BUS-isNo"));
            //投资方式
            traInterest.setInvestmentMethod(selectByValue(traInterest.getInvestmentMethod(),"INS-FundsinvestmentMethod"));

            return traInterest;
        }).collect(Collectors.toList());
        resultMap.put("insFunds", insFundsView);
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
