package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.model.Product;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.service.ProductService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.core.utils.XlsMessage;
import com.yzj.jep.core.utils.xlsio.XlsImportCallback;
import com.yzj.jep.orm.entity.Filter;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 预计额度导入实现
 */
@Service(value = LimitImportCallbackImpl.BEANID)
public class LimitImportCallbackImpl implements XlsImportCallback {

    public static final String BEANID = "LimitImportCallbackImpl";

    //配置导入的模板（XML）文件
    private static final String XML_TEMP_FILE = "LimitImportMapper.xml";
    private static final String IMPORT_DATAS = "limits";

    @Override
    public String getImportXmlTemplate() {
        return XLSTEMPLATE_PATH + XML_TEMP_FILE;
    }

    @Override
    public void setXlsReaderBeans(Map beans) {
        //一般而言，有几个sheet页，就有几个元素
        beans.put(IMPORT_DATAS, new ArrayList());
    }

    @Override
    public XlsMessage executeSaveCallback(Map parameters, Map beans) throws ServiceException {
        XlsMessage res = null;
        int index = 0;
        @SuppressWarnings("unchecked")
        List<Product> list = (List<Product>) beans.get(IMPORT_DATAS);
        if (list != null && list.size() > 0) {
            //需要调用合适的事务方法保存导入的数据
            ProductService productService = SpringContextHolder.getBean(ProductService.class);
//            RelPsnService relPsnService = SpringContextHolder.getBean(RelPsnService.class);
            for (Product entity : list) {
                List<Filter> filters = new Filter.Builder().eq("proid", entity.getProId()).build();
                List<Product> productList = productService.select(filters);
                if (productList.size() == 0) {
                    productService.insert(entity);
                    System.out.println("导入成功：" + entity.getProId());
                }
              /*  List<Filter> filters = new Filter.Builder().eq("identifyNumber",entity.getIdentifyNumber()).build();
                List<RelPsn> relPsnList = relPsnService.select(filters);
                if(relPsnList.size()== 0){
                    relPsnService.insert(entity);
                    System.out.println("导入成功：" + entity.getIdentifyNumber());
                }*/
                else {
                    index++;
                }

            }
            res = new XlsMessage();
            int size = list.size() - index;

            res.setMessage("成功导入" + size + "自然人信息,导入信息已存在" + index + "条");
        } else {
            //导入失败时，添加提示信息
            res = new XlsMessage();
            res.setMessage("导入失败：");
            res.setException("导入失败，请检查导入数据。");
        }
        return res;
    }

    @Override
    public String checkBeforeImport(Map parameters, Map mapReaded) throws ServiceException {
        //如果通过检查，就保持result为null，否则给出返回错误描述
        String result = null;
        @SuppressWarnings("unchecked")
        List<Product> list = (List<Product>) mapReaded.get(IMPORT_DATAS);
        if (list != null && list.size() > 0) {
            //TODO 执行导入前的检查，如xxx编码是否存在，xxx引用的编码不存在等

        } else {
            result = "导入数据为空。";
        }
        return result;
    }

    @Override
    public Map setReturnBeanFromXls(Map beans, InputStream inputXLS) throws ServiceException {
        //对导入的数据做特殊处理，如不处理则直接返回beans
        @SuppressWarnings("unchecked")
        List<Product> list = (List<Product>) beans.get(IMPORT_DATAS);

        if (CollectionUtils.isNotEmpty(list)) {
            for (Product entity : list) {
                if (StringUtils.isEmpty(entity.getProId())) {
                    //TODO 演示对从excel读取后的数据在做导入处理前做业务处理
                    System.out.println("演示，在这里对：" + entity.getProId() + "做进一步处理。");
                }
            }
        }

        return beans;
    }
}
