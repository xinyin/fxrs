/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年12月18日 16:19:09
 *
 */

package com.yzj.jep.app.sub.service.impl;

import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.ins.model.TraOther;
import com.yzj.jep.app.ins.service.TraOtherService;
import com.yzj.jep.app.sub.mapper.InsOtherMapper;
import com.yzj.jep.app.sub.model.InsBalance;
import com.yzj.jep.app.sub.model.InsOther;
import com.yzj.jep.app.sub.service.InsBalanceService;
import com.yzj.jep.app.sub.service.InsOtherService;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.web.entity.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class InsOtherServiceImpl extends MyBatisCRUDService<InsOther, Integer> implements InsOtherService {

    Logger logger = LoggerFactory.getLogger(InsOtherServiceImpl.class);

    @Autowired
    private InsOtherMapper insOtherMapper;

    @Override
    public Mapper<InsOther, Integer> getMapper() {
        return insOtherMapper;
    }

    @Autowired
    public TraOtherService traOtherService;
    @Autowired
    private RelPsnService relPsnService;
    @Autowired
    private RelOrgService relOrgService;
    @Autowired
    private DictItemService dictItemService;
    @Autowired
    private InsBalanceService insBalanceService;

    @Override
    public void extractInsOther(Map map, String nowDate) {
        String tag = (String) map.get("tag");
        List<TraOther> traOther = new ArrayList<>();
        Page<TraOther> page = new Page<>(1, 100);
        String id= "6,7";
        String[] status = id.split(",");
        List<Filter> filters = new Filter.Builder().in("status", status).build();
        List<Filter> filter = new Filter.Builder().in("status", status).like("modifiedTime", nowDate).ne("considerType", "3").build();
        filters.add(new Filter("considerType", "3"));//重大逐笔审批
        List<TraOther> select = traOtherService.select(filter);
        PageHelper.start(page);
        if ("all".equals(tag)) {
            traOther = traOtherService.select(filters);
        } else {
            filters.add(new Filter("modifiedTime", Operator.LIKE, nowDate));
            traOther = traOtherService.select(filters);
        }
        PageHelper.end(page, traOther);
        //总页数
        int pageSize = page.getPages();
        List<TraOther> exTraOther = page.getResult();
        //开始抽取
        startExtract(exTraOther);
        for (int i = 2; i <= pageSize; i++) {
            //从第二页开始循环
            page = new Page<>(i, 100);
            PageHelper.start(page);
            if ("all".equals(tag)) {
                traOther = traOtherService.select(filters);
            } else {
                filters.add(new Filter("modifiedTime", Operator.LIKE, nowDate));
                traOther = traOtherService.select(filters);
            }
            PageHelper.end(page, traOther);
            exTraOther = page.getResult();
            //开始抽取
            startExtract(exTraOther);
        }
        startExtractHz(select);//关联交易汇总表
    }

    private void startExtract(List<TraOther> traOthers) {
        for (TraOther traOther : traOthers) {
            InsOther insOther = new InsOther();
            BeanUtils.copyProperties(traOther, insOther);
            insOther.setTradeAmount(traOther.getRelTradeAmount());
            insOther.setTranDate(traOther.getContractDate());
            insOther.setStatus("3");
            insOther.setStatusComment("数据已抽取");
            insOther.setTotolTradeAmount(traOther.getAccuPracTradeAmount());
            insOther.setIsExtract("0");
            insOther.setIsDiscovery("0");
            List<Filter> filters = new Filter.Builder().eq("pid", insOther.getPid()).build();
            try {
                InsOther dbService = this.get(filters);
                if (dbService == null) {
                    this.insertSelective(insOther);
                }
            } catch (ServiceException e) {
                logger.error("其他类类" + insOther.getRelName() + "数据更新异常：", e);
            }
        }
    }

    private String selectByValue(String value, String simpleName) {
        List<NameValuePair> items = new ArrayList<>();
        try {
            List<Filter> filters = new Filter.Builder().eq("dictCode", simpleName).build();
            List<DictItem> list = dictItemService.select(filters);
            //过滤掉不要的字典项
            if (!CollectionUtils.isEmpty(list)) {
                for (DictItem dictItem : list) {
                    if (dictItem.getItemValue().equals(value)) {
                        return dictItem.getItemLabel();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     关联交易汇总表
      */
    private void startExtractHz(List<TraOther> traOthers) {
        for (TraOther traOther : traOthers) {
            try {

                //交易明细表
                InsBalance ib = new InsBalance();
                ib.setPid(traOther.getPid());
                ib.setRelId(traOther.getRelId());
                ib.setRelType(traOther.getRelType());
                ib.setRelName(traOther.getRelName());
                ib.setRelNumber(traOther.getRelNumber());
                List<Filter> filter = null;
                if (traOther.getRelType().equals("PSN")) {
                    filter = new Filter.Builder().eq("name", traOther.getRelName()).
                            like("identifyNumber", traOther.getRelNumber()).build();
                    RelPsn psn = relPsnService.get(filter);
                    ib.setRelCode(psn.getRelateNumber());
                } else if (traOther.getRelType().equals("ORG")) {
                    filter = new Filter.Builder().eq("orgName", traOther.getRelName()).
                            like("uniscCode", traOther.getRelNumber()).build();
                    RelOrg relOrg = relOrgService.get(filter);
                    ib.setRelCode(relOrg.getRelateNumber());
                }
                ib.setTradeOrg(traOther.getTradeOrg());
                ib.setTranType(traOther.getTranType());
                ib.setBusType(selectByValue(traOther.getBusType(), "INS-otherType"));
                ib.setContractSigningDate(traOther.getContractDate());
                ib.setTradeAmount(traOther.getRelTradeAmount());
                ib.setTenantId(traOther.getTenantId());
                ib.setIsExtract("0");
                ib.setCreateTime(new Date());
                ib.setDelflag("1");
                InsBalance insBalance = insBalanceService.get(ib);
                if (insBalance == null) {
                    insBalanceService.insert(ib);
                } else {
                    insBalanceService.updateSelective(ib);
                }
            } catch (ServiceException e) {
                logger.error("其他类类" + traOther.getRelName() + "数据更新异常：", e);
            }
        }
    }

}