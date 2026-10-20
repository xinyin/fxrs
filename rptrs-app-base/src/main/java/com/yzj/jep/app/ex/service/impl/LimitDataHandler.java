package com.yzj.jep.app.ex.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.model.Product;
import com.yzj.jep.app.bus.model.ProductExpected;
import com.yzj.jep.app.bus.service.ProductExpectedService;
import com.yzj.jep.app.bus.service.ProductService;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.client.ProcessDataHandler;
import com.yzj.jep.workflow.client.annotation.DataHandler;
import org.springframework.beans.BeanUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DataHandler("Estimated_Quota")
public class LimitDataHandler implements ProcessDataHandler {
    ProductService productService;
    ProductExpectedService productExpectedService;

    @Override
    public void handleData(String data) throws WorkflowException {
        productService = SpringContextHolder.getBean(ProductService.class);
        productExpectedService = SpringContextHolder.getBean(ProductExpectedService.class);
        JSONObject json = JSONObject.parseObject(data);
        Map<String, Object> tips = new HashMap<String, Object>();
        int pid = Integer.parseInt(json.getString("pid"));
        Product product = productService.get(pid);
        product.setApproveStatus("6");
        productService.updateSelective(product);
        ProductExpected productExpected = new ProductExpected();
        try {
            BeanUtils.copyProperties(product, productExpected);
            List<Filter> filters = new Filter.Builder().eq("pid", product.getPid()).build();
            boolean exists = productExpectedService.exists(filters);
            if (!exists) {
                productExpectedService.insert(productExpected);
            } else {
                ProductExpected productExpected1 = productExpectedService.get(filters);
                productExpected.setLid(productExpected1.getLid());
                productExpectedService.updateSelective(productExpected);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
