package com.yzj.jep.app.sub.service.impl;

import com.yzj.jep.app.bus.model.RelPro;
import com.yzj.jep.app.bus.model.RelProOutVo;
import com.yzj.jep.app.bus.service.RelProService;
import com.yzj.jep.app.sub.model.ProductView;
import com.yzj.jep.app.sub.model.Sublog;
import com.yzj.jep.app.sub.service.ProductViewService;
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
@Service(value = ProductsExportCallbackImpl.BEANID)
public class ProductsExportCallbackImpl implements XlsExportCallback {
    public static final String BEANID = "ProductsExportCallbackImpl";

    private static final String EXPORT_TEMPLATE_NAME = "product.xls";

    RelProService relProService;

    SublogService sublogService;


    ProductViewService productViewService;
    @Autowired
    DictItemService dictItemService;

    private static final String PREFIX = "金融产品报送_";

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
        productViewService = SpringContextHolder.getBean(ProductViewService.class);

        Map resultMap = new HashMap(16);

        //参数
        String pid = (String) parameters.get("id");
        //拿到所选sublo数据
        Sublog sublog = sublogService.get(Integer.parseInt(pid));
        //根据所选sublog数据得流水号到productview查找此报送文件的所有得自然人档案数据
        List<Filter> filter = new Filter.Builder().eq("subSeq", sublog.getBatchDecimal()).build();
        List<ProductView> relProOutVos = productViewService.select(filter);
        Map<String, List<DictItem>> dictItemMap = DicItemUtils.getDicValue();

        relProOutVos.forEach(productView -> productView.setProductType(DicItemUtils.getItemLabel(dictItemMap, "BUS-pro", productView.getProductType())));

        resultMap.put("relProVoS", relProOutVos);
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
