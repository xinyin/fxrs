/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年08月31日 10:17:03
 *
 */

package com.yzj.jep.app.sub.service.impl;

import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.ins.model.TraBusiness;
import com.yzj.jep.app.ins.model.TraService;
import com.yzj.jep.app.ins.service.TraServiceService;
import com.yzj.jep.app.sub.mapper.InsServiceMapper;
import com.yzj.jep.app.sub.model.InsBalance;
import com.yzj.jep.app.sub.model.InsService;
import com.yzj.jep.app.sub.service.InsBalanceService;
import com.yzj.jep.app.sub.service.InsServiceService;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.commons.StringUtils;
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

@Service(value = "insServiceServiceImpl")
@Transactional
public class InsServiceServiceImpl extends MyBatisCRUDService<InsService, Integer> implements InsServiceService {

    Logger logger = LoggerFactory.getLogger(InsServiceServiceImpl.class);

    @Autowired
    private InsServiceMapper insServiceMapper;

    @Autowired
    private TraServiceService traServiceService;

    @Autowired
    private InsBalanceService insBalanceService;
    @Autowired
    private RelPsnService relPsnService;
    @Autowired
    private RelOrgService relOrgService;
    @Autowired
    private DictItemService dictItemService;

    @Override
    public Mapper<InsService, Integer> getMapper() {
        return insServiceMapper;
    }

    @Override
    public void extractInsService(Map map, String nowDate) {
        String tag = (String) map.get("tag");
        String id= "6,7";
        String[] status = id.split(",");
        List<Filter> filters = new Filter.Builder().eq("dataVersion","E2.0").in("status", status).eq("delflag", "1").build();
        List<Filter> filter = new Filter.Builder().eq("dataVersion","E2.0").in("status",status).like("modifiedTime", nowDate).eq("delflag", "1").eq("considerType", "3").build();
        //重大逐笔审批
        filters.add(new Filter("considerType", "3"));
        startPre(filters, tag, nowDate);
        List<TraService> select = traServiceService.select(filter);
        startExtractHz(select);
    }

    public void startPre(List<Filter> filters, String tag, String nowDate) {
        List<TraService> traServices = new ArrayList<>();
        Page<TraService> page = new Page<>(1, 100);
        PageHelper.start(page);
        if ("all".equals(tag)) {
            traServices = traServiceService.select(filters);
        } else {
            filters.add(new Filter("modifiedTime", Operator.LIKE, nowDate));
            traServices = traServiceService.select(filters);
        }
        PageHelper.end(page, traServices);
        //总页数
        int pageSize = page.getPages();
        List<TraService> exService = page.getResult();
        //开始抽取
        startExtract(exService);
        for (int i = 2; i <= pageSize; i++) {
            //从第二页开始循环
            page = new Page<>(i, 100);
            PageHelper.start(page);
            if ("all".equals(tag)) {
                traServices = traServiceService.select(filters);
            } else {
                filters.add(new Filter("modifiedTime", Operator.LIKE, nowDate));
                traServices = traServiceService.select(filters);
            }
            PageHelper.end(page, traServices);
            exService = page.getResult();
            //开始抽取
            startExtract(exService);
        }
    }

    private void startExtract(List<TraService> traServices) {
        for (TraService traService : traServices) {
            InsService insService = new InsService();
            BeanUtils.copyProperties(traService, insService);
            // 合同编号
            insService.setContractNo(StringUtils.isNotEmpty(traService.getUniqueCode()) ? traService.getUniqueCode() : traService.getTransNum());
            // 关联交易金额
            insService.setTradeAmount(traService.getRelTradeAmount());
            // 关联交易金额
            insService.setTranDate(traService.getContractDate());
            insService.setStatus("3");
            insService.setStatusComment("数据已抽取");
            insService.setIsExtract("0");
            insService.setIsDiscovery("0");
            List<Filter> filters = new Filter.Builder().eq("pid", insService.getPid()).build();
            try {
                InsService dbService = this.get(filters);
                if (dbService == null) {
                    this.insert(insService);
                }
            } catch (ServiceException e) {
                logger.error("服务类" + insService.getRelName() + "数据更新异常：", e);
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
    private void startExtractHz(List<TraService> traServices) {
        for (TraService traService : traServices) {

            try {
                //交易明细表
                InsBalance ib = new InsBalance();
                ib.setPid(traService.getPid());
                ib.setRelId(traService.getRelId());
                ib.setRelType(traService.getRelType());
                ib.setRelName(traService.getRelName());
                ib.setRelNumber(traService.getRelNumber());
                List<Filter> filter = null;
                if (traService.getRelType().equals("PSN")) {
                    filter = new Filter.Builder().eq("identifyNumber", traService.getRelNumber()).build();
                    RelPsn psn = relPsnService.get(filter);
                    ib.setRelCode(psn.getRelateNumber());
                } else if (traService.getRelType().equals("ORG")) {
                    filter = new Filter.Builder().eq("uniscCode", traService.getRelNumber()).build();
                    RelOrg relOrg = relOrgService.get(filter);
                    ib.setRelCode(relOrg.getRelateNumber());
                }
                ib.setTradeOrg(traService.getTradeOrg());
                ib.setTranType(traService.getTranType());
                ib.setBusType(selectByValue(traService.getBusType(), "INS-ServiceType"));
                ib.setContractSigningDate(traService.getContractDate());
                ib.setTradeAmount(traService.getRelTradeAmount());
                ib.setTenantId(traService.getTenantId());
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
                logger.error("提供服务类" + traService.getRelName() + "数据更新异常：", e);
            }
        }
    }
}
