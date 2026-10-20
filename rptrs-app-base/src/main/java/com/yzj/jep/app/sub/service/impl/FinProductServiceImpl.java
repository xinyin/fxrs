/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月02日 10:12:05
 *
 */

package com.yzj.jep.app.sub.service.impl;

import com.yzj.jep.app.bus.model.RelPro;
import com.yzj.jep.app.bus.service.RelProService;
import com.yzj.jep.app.sub.mapper.FinProductMapper;
import com.yzj.jep.app.sub.model.FinProduct;
import com.yzj.jep.app.sub.service.FinProductService;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service(value = "FinProductServiceImpl")
@Transactional
public class FinProductServiceImpl extends MyBatisCRUDService<FinProduct, Integer> implements FinProductService {

    Logger logger = LoggerFactory.getLogger(FinProductServiceImpl.class);

    @Autowired
    private FinProductMapper finProductMapper;

    @Autowired
    private RelProService relProService;

    @Override
    public Mapper<FinProduct, Integer> getMapper() {
        return finProductMapper;
    }

    @Override
    public void extractFinProduct(Map map, String nowDate) {
        String tag = (String) map.get("tag");
        List<RelPro> relPros = new ArrayList<>();
        List<Filter> filters = new Filter.Builder().like("modifiedTime", nowDate).eq("approvalStatus","6").eq("delflag", "1").build();
        //分批查询导入
        Page<RelPro> page = new Page<>(1, 100);
        PageHelper.start(page);
        if ("all".equals(tag)) {
            relPros = relProService.selectAll();
        } else {

            relPros = relProService.select(filters);
        }
        startExtract(relPros);
        PageHelper.end(page,relPros);
        int pageSize = page.getPageSize();
        List<RelPro> pros = page.getResult();
        startExtract(pros);
        for (int i = 2; i <= pageSize; i++) {
            //从第二页开始循环
            page=new Page<>(i,100);
            PageHelper.start(page);
            if ("all".equals(tag)){
                relPros=relProService.selectAll();
            }else {
                relPros=relProService.select(filters);
            }
            PageHelper.end(page,relPros);
            pros = page.getResult();
            //开始抽取
            startExtract(pros);
        }
    }

    private void startExtract(List<RelPro> relPros) {
        for (RelPro relPro : relPros) {
            FinProduct finProduct = new FinProduct();
            BeanUtils.copyProperties(relPro, finProduct);
            finProduct.setProductName(relPro.getProName());
            finProduct.setProductRegisterNo(relPro.getProCode());
            finProduct.setProductType(relPro.getProType());
            finProduct.setProductIssueNo(relPro.getProProvider());
            finProduct.setRegisterOrg(relPro.getProRegOrg());
            finProduct.setProductOwner(relPro.getAdName());
            finProduct.setProductOwnerUniscCode(relPro.getAdCreditCode());
            finProduct.setProductCustodian(relPro.getCareName());
            finProduct.setProductCustodianUniscCode(relPro.getCareCreditCode());
            finProduct.setStatus("3");
            finProduct.setStatusComment("数据已抽取");
            boolean exists = finProductMapper.existsByPrimaryKey(relPro.getPid());
            try {
                if (exists) {
                    finProductMapper.updateSelective(finProduct);
                } else {
                    finProductMapper.insertSelective(finProduct);
                }
            } catch (ServiceException e) {
                logger.error("金融产品[" + finProduct.getProductName() + "]数据更新异常：", e);
            }
        }
    }
}