package com.yzj.jep.app.bus.service.impl;


import com.yzj.jep.app.bus.model.Product;
import com.yzj.jep.app.bus.model.ProductVo;
import com.yzj.jep.app.bus.service.ProductService;
import com.yzj.jep.app.ins.model.TraBusiness;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.utils.xlsio.XlsExportCallback;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.sys.model.Tenant;
import com.yzj.jep.sys.service.TenantService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author LONG
 * @date 2020/8/7
 * @description
 */

@Service(value = BusLimitExportCallbackImpl.BEANID)
public class BusLimitExportCallbackImpl implements XlsExportCallback {

    public static final String BEANID = "BusLimitExportCallbackImpl";

    private static final String EXPORT_TEMPLATE_NAME = "BusLimitProduct.xls";

    @Autowired
    private ProductService productService;
    @Autowired
    private TenantService tenantService;

    @Override
    public String getExportTemplate() {
        return XlsExportCallback.XLSTEMPLATE_PATH + EXPORT_TEMPLATE_NAME;
    }

    @Override
    public String getExportFilename(Map parameters) throws ServiceException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String fileName = "预计额度列表信息_" + sf.format(new Date()) + ".xls";
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
        List<ProductVo> list = new ArrayList<>();
        //获取当前登录人的信息 获取租户id
        UserProfile profile = UserProfileUtils.getUserProfile();
        profile.setTenantId("11");
        //参数
        String data = (String) parameters.get("id");
        if ("00".equals(data)) {
            resultMap.put("product", new Product());
            return resultMap;
        }
        //获取要导出自然人的id
        String[] param = data.split(",");
        String tranOrg = param[0];
        String tradeType = param[1];
        String businessType = param[2];
        String proPath = param[3];
        String approveStatus = param[4];
        String warnLeve = param[5];

        List<Filter> filters = new Filter.Builder().eq("delflag", "1").build();
        if (StringUtils.isNotBlank(tranOrg)) {
            filters.add(new Filter("tranOrg", Operator.LIKE, tranOrg));
        }
        if (StringUtils.isNotBlank(tradeType)) {
            filters.add(new Filter("tradeType", Operator.LIKE, tradeType));
        }
        if (StringUtils.isNotBlank(businessType)) {
            filters.add(new Filter("businessType", Operator.LIKE, businessType));
        }
        if (!"%".equals(proPath)) {
            filters.add(new Filter("proPath", Operator.LIKE, proPath));
        }
        if (StringUtils.isNotBlank(approveStatus)) {
            filters.add(new Filter("approveStatus", Operator.LIKE, approveStatus));
        }
        if (StringUtils.isNotBlank(warnLeve)) {
            filters.add(new Filter("warnLeve", Operator.LIKE, warnLeve));
        }

        filters.add(new Filter("preLimit", Operator.NNL, ""));
        List<Product> products = productService.select(filters);
        if (products != null && products.size() > 0) {
            for (int i = 0; i < products.size(); i++) {
                ProductVo productVo = new ProductVo();
                productVo.setNo(String.valueOf(i + 1));
                productVo.setTradeType(products.get(i).getTradeType());
                productVo.setTranOrg(products.get(i).getTranOrg()); //交易主体
                productVo.setBusinessType(products.get(i).getBusinessType());
                productVo.setProName(products.get(i).getProName());
                productVo.setPasLimit(products.get(i).getPasLimit());
                productVo.setLimitRate(products.get(i).getLimitRate());
                productVo.setPreLimit(products.get(i).getPreLimit());
                productVo.setRemLimit(products.get(i).getRemLimit());
                productVo.setWarnLeve(products.get(i).getWarnLeve());
                productVo.setStatus(products.get(i).getApproveStatus());
                productVo.setStatusComment("");
                list.add(productVo);
            }
        }
        resultMap.put("products", list);
        return resultMap;
    }

    @Override
    public Map executeExportCallback(HttpServletRequest request) throws ServiceException {
        return null;
    }

    @Override
    public void afterTransformXLS(Workbook workbook) {

    }
}