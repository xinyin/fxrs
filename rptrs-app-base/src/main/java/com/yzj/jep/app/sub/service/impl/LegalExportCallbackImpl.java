package com.yzj.jep.app.sub.service.impl;

import com.yzj.jep.app.bus.service.RelProService;
import com.yzj.jep.app.sub.model.OrganView;
import com.yzj.jep.app.sub.model.Sublog;
import com.yzj.jep.app.sub.service.OrganViewService;
import com.yzj.jep.app.sub.service.SublogService;
import com.yzj.jep.app.util.DicItemUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author llc
 */
@Service(value = LegalExportCallbackImpl.BEANID)
public class LegalExportCallbackImpl implements XlsExportCallback {
    public static final String BEANID = "LegalExportCallbackImpl";

    private static final String EXPORT_TEMPLATE_NAME = "legal.xls";

    RelProService relProService;

    SublogService sublogService;

    OrganViewService organViewService;
    @Autowired
    DictItemService dictItemService;

    private static final String PREFIX = "关联法人报送_";

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
        organViewService = SpringContextHolder.getBean(OrganViewService.class);

        Map resultMap = new HashMap(16);

        //参数
        String pid = (String) parameters.get("id");
        //拿到所选sublog数据
        Sublog sublog = sublogService.get(Integer.parseInt(pid));
        String organSeq = sublog.getBatchDecimal();
        List<Filter> filters = new ArrayList<>();
        filters.add(new Filter("organSeq", organSeq));
        List<OrganView> legal = organViewService.select(filters);
        Map<String, List<DictItem>> dictItemMap = DicItemUtils.getDicValue();

        legal.forEach(productView -> {
            productView.setDomesticOrForeign(DicItemUtils.getItemLabel(dictItemMap, "BUS-DomesticOrForeign", productView.getDomesticOrForeign()));
            productView.setIdentifyType(DicItemUtils.getItemLabel(dictItemMap, "BUS-ORGIdentifyType", productView.getIdentifyType()));
        });
        resultMap.put("orgs", legal);
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
}
