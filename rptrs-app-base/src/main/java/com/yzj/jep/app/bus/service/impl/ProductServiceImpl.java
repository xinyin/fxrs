/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月08日 18:29:09
 *
 */

package com.yzj.jep.app.bus.service.impl;

import com.google.common.collect.Maps;
import com.yzj.jep.app.bus.mapper.ProductMapper;
import com.yzj.jep.app.bus.model.Product;
import com.yzj.jep.app.bus.service.ProductService;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import com.yzj.jep.web.code.WebCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(readOnly = true)
public class ProductServiceImpl extends MyBatisCRUDService<Product, Integer> implements ProductService {

    Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Mapper<Product, Integer> getMapper() {
        return productMapper;
    }


    @Override
    public Double findNameAmt(String proname) {
        return productMapper.findNameAmt(proname);
    }

    @Override
    public Map<String, Object> getProductInfo(String proName) {
        Map<String, Object> tips = Maps.newHashMap();
        Product product = this.get(new Filter.Builder().eq("proName", proName).build());
        if (product == null) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "产品不存在");
            return tips;
        }
        Integer busLevel = Integer.valueOf(product.getBusLevel());
        Integer pid = product.getPaProId();
        tips.put("productId", product.getPid());
        for (int i = 1; i < busLevel; i++) {
            Product product1 = this.get(new Filter.Builder().eq("pid", pid).build());
            tips.put(product1.getBusLevel(), product1.getProName());
            pid = product1.getPaProId();
        }
        tips.put("tranOrg", product.getTranOrg());
        return tips;
    }
}