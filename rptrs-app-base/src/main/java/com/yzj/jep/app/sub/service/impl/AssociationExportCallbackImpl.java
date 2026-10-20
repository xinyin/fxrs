package com.yzj.jep.app.sub.service.impl;

import com.yzj.jep.app.bus.model.RelPro;
import com.yzj.jep.app.bus.model.RelProOutVo;
import com.yzj.jep.app.bus.service.RelProService;
import com.yzj.jep.app.sub.model.OrganView;
import com.yzj.jep.app.sub.model.RelatedRelationView;
import com.yzj.jep.app.sub.model.Sublog;
import com.yzj.jep.app.sub.service.OrganViewService;
import com.yzj.jep.app.sub.service.RelatedRelationViewService;
import com.yzj.jep.app.sub.service.SublogService;
import com.yzj.jep.app.util.DicItemUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
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
@Service(value = AssociationExportCallbackImpl.BEANID)
public class AssociationExportCallbackImpl implements XlsExportCallback {
    public static final String BEANID = "AssociationExportCallbackImpl";

    private static final String EXPORT_TEMPLATE_NAME = "association.xls";

    RelProService relProService;

    SublogService sublogService;


    RelatedRelationViewService relatedRelationViewService;
    @Autowired
    DictItemService dictItemService;

    private static final String PREFIX = "关联关系报送_";

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
        relatedRelationViewService = SpringContextHolder.getBean(RelatedRelationViewService.class);

        Map resultMap = new HashMap(16);

        //参数
        String pid = (String) parameters.get("id");
        //拿到所选sublog数据
        Sublog sublog = sublogService.get(Integer.parseInt(pid));
        List<Filter> filter = new Filter.Builder().eq("relationSeq", sublog.getBatchDecimal()).build();
        List<RelatedRelationView> association = relatedRelationViewService.select(filter);
        Map<String, List<DictItem>> dictItemMap = DicItemUtils.getDicValue();

        association.forEach(productView -> {
            productView.setEntityType(DicItemUtils.getItemLabel(dictItemMap, "Rel_POP", productView.getEntityType()));
            productView.setSuperEntityType(DicItemUtils.getItemLabel(dictItemMap, "Rel_POP", productView.getSuperEntityType()));
            productView.setRelationType(DicItemUtils.getItemLabel(dictItemMap, "BUS-RelZZD", productView.getRelationType()));
            productView.setEquityShare(DicItemUtils.getItemLabel(dictItemMap, "BUS-RelequityShare", productView.getEquityShare()));
            productView.setValidStatus(DicItemUtils.getItemLabel(dictItemMap, "BUS-isNo", productView.getValidStatus()));

        });
        resultMap.put("association", association);
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
