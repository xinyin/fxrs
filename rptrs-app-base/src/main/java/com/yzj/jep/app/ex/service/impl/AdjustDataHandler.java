package com.yzj.jep.app.ex.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.model.LimitTrans;
import com.yzj.jep.app.bus.model.Product;
import com.yzj.jep.app.bus.service.LimitTransService;
import com.yzj.jep.app.bus.service.ProductService;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.client.ProcessDataHandler;
import com.yzj.jep.workflow.client.annotation.DataHandler;

import java.util.HashMap;
import java.util.Map;

@DataHandler("ADJUST_LIMIT")
public class AdjustDataHandler implements ProcessDataHandler {
    LimitTransService limitTransService;
    ProductService productService;

    @Override
    public void handleData(String data) throws WorkflowException {
        limitTransService = SpringContextHolder.getBean(LimitTransService.class);
        productService = SpringContextHolder.getBean(ProductService.class);
        JSONObject json = JSONObject.parseObject(data);
        Map<String, Object> tips = new HashMap<String, Object>();
        LimitTrans limitTrans = limitTransService.get(Integer.parseInt(json.getString("pid")));
        limitTrans.setStatus("6");
        limitTransService.updateSelective(limitTrans);
        Integer befPid = limitTrans.getBefPid();
        Integer latPid = limitTrans.getLatPid();
        Product product1 = productService.get(befPid);
        Product product2 = productService.get(latPid);
        int i1 = Integer.parseInt(limitTrans.getPreLimitLater());
        int i2 = Integer.parseInt(limitTrans.getPasLimitLater());
        int i3 = i1 - i2;
        product1.setPreLimit(limitTrans.getPreLimitLater());
        product1.setPasLimit(i3 + "");
        product1.setRemLimit(limitTrans.getPasLimitLater());
        product1.setWarnLeve(limitTrans.getWarnLeveLater());
        product1.setLimitRate((i3 / Integer.parseInt(limitTrans.getPreLimitLater())) * 10);
        product1.setApproveStatus("6");
        productService.updateSelective(product1);
        int i4 = Integer.parseInt(limitTrans.getPreLimitLaterL());
        int i5 = Integer.parseInt(limitTrans.getPasLimitLaterL());
        int i6 = i4 - i5;
        product2.setPreLimit(limitTrans.getPreLimitLaterL());
        product2.setPasLimit(i6 + "");
        product2.setRemLimit(limitTrans.getPasLimitLaterL());
        product2.setWarnLeve(limitTrans.getWarnLeveLaterL());
        product2.setLimitRate((i6 / Integer.parseInt(limitTrans.getPreLimitLaterL())) * 10);
        product2.setApproveStatus("6");
        productService.updateSelective(product2);

    }
}
