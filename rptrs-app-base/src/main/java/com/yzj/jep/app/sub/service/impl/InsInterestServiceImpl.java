/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年08月31日 10:16:59
 *
 */

package com.yzj.jep.app.sub.service.impl;

import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.ins.model.TraInterest;
import com.yzj.jep.app.ins.service.TraInterestService;
import com.yzj.jep.app.sub.mapper.InsInterestMapper;
import com.yzj.jep.app.sub.model.InsBalance;
import com.yzj.jep.app.sub.model.InsInterest;
import com.yzj.jep.app.sub.service.InsBalanceService;
import com.yzj.jep.app.sub.service.InsInterestService;
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


@Service(value = "insInterestServiceImpl")
@Transactional
public class InsInterestServiceImpl extends MyBatisCRUDService<InsInterest, Integer> implements InsInterestService {

    Logger logger = LoggerFactory.getLogger(InsInterestServiceImpl.class);

    @Autowired
    private InsInterestMapper insInterestMapper;

    @Autowired
    private TraInterestService traInterestService;
    @Autowired
    private RelPsnService relPsnService;
    @Autowired
    private RelOrgService relOrgService;
    @Autowired
    private DictItemService dictItemService;
    @Autowired
    private InsBalanceService insBalanceService;

    @Override
    public Mapper<InsInterest, Integer> getMapper() {
        return insInterestMapper;
    }


    @Override
    public void extractInsInterest(Map map, String nowDate) {
        String tag = (String) map.get("tag");
        String id= "6,7";
        String[] status = id.split(",");
        List<Filter> filters = new Filter.Builder().eq("dataVersion","E2.0").in("status", status).eq("delflag", "1").build();
        List<Filter> filter = new Filter.Builder().eq("dataVersion","E2.0").in("status", status).like("modifiedTime", nowDate).eq("delflag", "1").eq("considerType", "3").build();
        //重大逐笔审批
        filters.add(new Filter("considerType", "3"));
        startPre(filters, tag, nowDate);
        List<TraInterest> select = traInterestService.select(filter);
        startExtractHz(select);


    }

    public void startPre(List<Filter> filters, String tag, String nowDate) {
        List<TraInterest> traInterest = new ArrayList<>();
        Page<TraInterest> page = new Page<>(1, 100);
        PageHelper.start(page);
        if ("all".equals(tag)) {
            traInterest = traInterestService.select(filters);
        } else {
            filters.add(new Filter("modifiedTime", Operator.LIKE, nowDate));
            traInterest = traInterestService.select(filters);
        }
        PageHelper.end(page, traInterest);
        //总页数
        int pageSize = page.getPages();
        List<TraInterest> exInterest = page.getResult();
        //开始抽取
        startExtract(exInterest);
        for (int i = 2; i <= pageSize; i++) {
            //从第二页开始循环
            page = new Page<>(i, 100);
            PageHelper.start(page);
            if ("all".equals(tag)) {
                traInterest = traInterestService.select(filters);
            } else {
                filters.add(new Filter("modifiedTime", Operator.LIKE, nowDate));
                traInterest = traInterestService.select(filters);
            }
            PageHelper.end(page, traInterest);
            exInterest = page.getResult();
            //开始抽取
            startExtract(exInterest);
        }
    }

    private void startExtract(List<TraInterest> traInterests) {
        for (TraInterest traInterest : traInterests) {
            InsInterest insInterest = new InsInterest();
            BeanUtils.copyProperties(traInterest, insInterest);
            // 合同编号
            insInterest.setContractNo(StringUtils.isNotEmpty(traInterest.getUniqueCode()) ? traInterest.getUniqueCode() : traInterest.getTransNum());
            // 关联交易金额
            insInterest.setTradeAmount(traInterest.getRelTradeAmount());
            // 合同签署日期
            insInterest.setTranDate(traInterest.getContractDate());
            insInterest.setStatus("3");
            insInterest.setStatusComment("数据已抽取");
            //East抽数状态
            insInterest.setIsExtract("0");
            //披露状态
            insInterest.setIsDiscovery("0");
            List<Filter> filters = new Filter.Builder().eq("pid", insInterest.getPid()).build();
            try {
                InsInterest dbInsInterest = this.get(filters);
                if (dbInsInterest == null) {
                    this.insert(insInterest);
                }
            } catch (ServiceException e) {
                logger.error("利益转移类" + insInterest.getRelName() + "数据更新异常：", e);
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
    抽取到关联交易汇总表
    */
    private void startExtractHz(List<TraInterest> TraInterests) {
        for (TraInterest traInterest : TraInterests) {

            try {

                //交易明细表
                InsBalance ib = new InsBalance();
                ib.setPid(traInterest.getPid());
                ib.setRelId(traInterest.getRelId());
                ib.setRelType(traInterest.getRelType());
                ib.setRelName(traInterest.getRelName());
                ib.setRelNumber(traInterest.getRelNumber());
                List<Filter> filter = null;
                if (traInterest.getRelType().equals("PSN")) {
                    filter = new Filter.Builder().eq("identifyNumber", traInterest.getRelNumber()).build();
                    RelPsn psn = relPsnService.get(filter);
                    ib.setRelCode(psn.getRelateNumber());
                } else if (traInterest.getRelType().equals("ORG")) {
                    filter = new Filter.Builder().eq("uniscCode", traInterest.getRelNumber()).build();
                    RelOrg relOrg = relOrgService.get(filter);
                    ib.setRelCode(relOrg.getRelateNumber());
                }
                ib.setTradeOrg(traInterest.getTradeOrg());
                ib.setTranType(traInterest.getTranType());
                ib.setBusType(selectByValue(traInterest.getBusType(), "INT-BusinessType"));
                ib.setContractSigningDate(traInterest.getContractDate());
                ib.setTradeAmount(traInterest.getRelTradeAmount());
                ib.setTenantId(traInterest.getTenantId());
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
                logger.error("利益转移类" + traInterest.getRelName() + "数据更新异常：", e);
            }
        }
    }
}
