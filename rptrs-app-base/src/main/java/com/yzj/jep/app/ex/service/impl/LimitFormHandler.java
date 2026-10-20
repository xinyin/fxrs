package com.yzj.jep.app.ex.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.model.Product;
import com.yzj.jep.app.bus.model.ProductExpected;
import com.yzj.jep.app.bus.service.ProductExpectedService;
import com.yzj.jep.app.ins.service.TraInvestmentService;
import com.yzj.jep.app.util.JsonUtil;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.client.ProcessFormHandler;
import com.yzj.jep.workflow.client.annotation.FormHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@FormHandler("Estimated_Quota")
public class LimitFormHandler implements ProcessFormHandler {

    ProductExpectedService productExpectedService;

    @Override
    public ModelAndView processForm(String nodeKey, String data, String opt, ModelAndView model) throws WorkflowException {
        productExpectedService = SpringContextHolder.getBean(ProductExpectedService.class);
        try {
            JSONObject json = JSONObject.parseObject(data);
            /*String cognizaReason = json.getString("cognizaReason");
            if (StringUtils.isEmpty(cognizaReason)) {
                model.addObject("cognizaReason", "暂无意见");
            } else {
                model.addObject("cognizaReason", json.getString("cognizaReason").trim());
            }*/
            json.remove("cognizaReason");
//       Product  product = JSON.toJavaObject(json,Product.class );
            //获取当前pid查询备案表ProductExpected里面是否有备案编号里包含pid
            List<Filter> filters = new Filter.Builder()
                    .eq("pid", Integer.valueOf(json.getString("pid")))
                    .build();
            List<ProductExpected> expected = productExpectedService.select(filters);
            if (expected != null && expected.size() > 0) {
                ProductExpected expected1 = expected.get(0);
                Product product1 = new Product();
                BeanUtils.copyProperties(expected1, product1);
                //实体类转json
//            JSONObject product2 = (JSONObject) JSON.toJSON(product);
                JSONObject product3 = (JSONObject) JSON.toJSON(product1);
                Map result = JsonUtil.compareJson(json, product3, null);
                model.addObject("product", json);
                model.addObject("product2", result);
                model.addObject("opt", "view");
                model.setViewName("ex/limitSubmit");
            } else {
//            JSONObject product1 = (JSONObject) JSON.toJSON(product);
//            JSONObject product2 = (JSONObject) JSON.toJSON(product);
                JSONObject json2 = json;
                Map result = JsonUtil.compareJson(json, json2, null);
                model.addObject("product", json);
                model.addObject("product2", result);
                model.addObject("opt", "view");
                model.setViewName("ex/limitSubmit");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         model.addObject("tranOrg", json.getString("tranOrg"));
         model.addObject("pid", json.getString("pid"));
         model.addObject("busDepart", json.getString("busDepart"));
         model.addObject("tranOrg", json.getString("tranOrg"));
         model.addObject("proName", json.getString("proName"));
         model.addObject("tradeType", json.getString("tradeType"));
         model.addObject("businessType", json.getString("businessType"));
         model.addObject("proPath", json.getString("proPath"));
         model.addObject("busDepart", json.getString("busDepart"));
         model.addObject("preLimitCurrency", json.getString("preLimitCurrency"));
         model.addObject("preLimit", json.getString("preLimit"));
         model.addObject("limitRate", json.getString("limitRate"));
         model.addObject("pasLimit", json.getString("pasLimit"));
         model.addObject("remLimit", json.getString("remLimit"));
         String cognizaReason = json.getString("cognizaReason");
         if (StringUtils.isEmpty(cognizaReason)) {
         model.addObject("cognizaReason", "暂无意见");
         } else {
         model.addObject("cognizaReason", json.getString("cognizaReason").trim());
         }
         model.setViewName("ex/limitSubmit");
         */

        return model;
    }
}
