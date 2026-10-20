/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月08日 18:29:09
 *
 */

package com.yzj.jep.app.bus.service;

import com.yzj.jep.app.bus.model.Product;
import com.yzj.jep.orm.service.CRUDService;

import java.util.List;
import java.util.Map;

public interface ProductService extends CRUDService<Product, Integer> {


    Double findNameAmt(String proname);

    Map<String, Object> getProductInfo(String proName);


}
