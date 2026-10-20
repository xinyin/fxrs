package com.yzj.jep.app.sub.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.yzj.jep.app.bus.service.RelProService;
import com.yzj.jep.app.sub.model.InsAllbustraninfoView;
import com.yzj.jep.app.sub.model.Sublog;
import com.yzj.jep.app.sub.service.InsAllbustraninfoViewService;
import com.yzj.jep.app.sub.service.SublogService;
import com.yzj.jep.app.util.DicItemUtils;
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

/**
 * @author llc
 */
@Service(value = TradeAllBustranInfoExportCallbackImpl.BEANID)
public class TradeAllBustranInfoExportCallbackImpl implements XlsExportCallback {
    public static final String BEANID = "TradeAllBustranInfoExportCallbackImpl";

    private static final String EXPORT_TEMPLATE_NAME = "allBustranInfo.xls";

    RelProService relProService;

    SublogService sublogService;

    InsAllbustraninfoViewService insAllbustraninfoViewService;
    @Autowired
    DictItemService dictItemService;

    private static final String PREFIX = "关联交易明细表报送_";

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
        insAllbustraninfoViewService = SpringContextHolder.getBean(InsAllbustraninfoViewService.class);

        Map resultMap = new HashMap(16);

        //参数
        String pid = (String) parameters.get("id");
        //拿到所选sublo数据
        Sublog sublog = sublogService.get(Integer.parseInt(pid));
        List<Filter> filters = new ArrayList<>();
        Map<String, List<DictItem>> dictItemMap = DicItemUtils.getDicValue();
        String interestSeq = sublog.getBatchDecimal();
        filters.add(new Filter("allbustraninfoSeq", interestSeq));
        List<InsAllbustraninfoView> insAllbustraninfoViewList = insAllbustraninfoViewService.select(filters);
        insAllbustraninfoViewList.forEach(insAllbustraninfoView -> {
            String byValue = null;
            String tradingDirection = null;
            switch (insAllbustraninfoView.getTranType()) {
                case "1":
                    byValue = selectByValue(insAllbustraninfoView.getBusType(), "INS-FundsType");
                    break;
                case "2":
                    byValue = selectByValue(insAllbustraninfoView.getBusType(), "INS-ServiceType");
                    tradingDirection = selectByValue(insAllbustraninfoView.getTradingDirection(), "INS-Tra-Direction");
                    break;
                case "3":
                    byValue = selectByValue(insAllbustraninfoView.getBusType(), "INT-BusinessTypes");
                    tradingDirection = selectByValue(insAllbustraninfoView.getTradingDirection(), "INS-Tra-Directions");
                    break;
                case "4":
                    byValue = selectByValue(insAllbustraninfoView.getBusType(), "INT-InsBusinessType");
                    break;
                default:
                    byValue = null;
            }
            insAllbustraninfoView.setTradingDirection(tradingDirection);
            insAllbustraninfoView.setBusType(byValue);

            insAllbustraninfoView.setIsAgreement(DicItemUtils.getItemLabel(dictItemMap, "BUS-isNo", insAllbustraninfoView.getIsAgreement()));
            insAllbustraninfoView.setTranType(DicItemUtils.getItemLabel(dictItemMap, "INS-tranType", insAllbustraninfoView.getTranType()));

            if (!ObjectUtil.isEmpty(insAllbustraninfoView.getTranDate())) {
                insAllbustraninfoView.setTranDateStr(DateUtils.formatDateShort(insAllbustraninfoView.getTranDate()));
            }

            insAllbustraninfoView.setRelType(DicItemUtils.getItemLabel(dictItemMap, "INS-relType", insAllbustraninfoView.getRelType()));

        });
        resultMap.put("allbustraninfo", insAllbustraninfoViewList);
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
