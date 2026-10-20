/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年04月28日 14:27:32
 *
 */

package com.yzj.jep.app.bus.mapper;

import com.yzj.jep.app.bus.model.Product;
import com.yzj.jep.orm.mybatis.annotation.MyBatisMapper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@MyBatisMapper
@Repository
public interface ProductMapper extends Mapper<Product, Integer> {

    Double findNameAmt(String proname);
}