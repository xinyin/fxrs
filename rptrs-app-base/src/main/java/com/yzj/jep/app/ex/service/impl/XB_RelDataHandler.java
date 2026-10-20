package com.yzj.jep.app.ex.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.yzj.jep.app.bus.mapper.RelMapper;
import com.yzj.jep.app.bus.mapper.RelOrgMapper;
import com.yzj.jep.app.bus.mapper.RelProMapper;
import com.yzj.jep.app.bus.mapper.RelPsnMapper;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.client.ProcessDataHandler;
import com.yzj.jep.workflow.client.annotation.DataHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

//业务逻辑

@DataHandler("XB_GLGXSPLC")
public class XB_RelDataHandler implements ProcessDataHandler {
    private static Logger logger = LoggerFactory.getLogger(XB_RelDataHandler.class);
    TraCreditService traCreditService;
    RelService relService;
    RelPsnService relPsnService;
    RelOrgService relOrgService;
    TraBalanceDeductionService traBalanceDeductionService;
    TraUsecreditService traUsecreditService;

    RelProService relProService;

    private RelMapper relMapper;
    private RelPsnMapper relPsnMapper;
    private RelOrgMapper relOrgMapper;
    private RelProMapper relProMapper;

    @Override
    public void handleData(String data) throws WorkflowException {
        try {
            relService = SpringContextHolder.getBean(RelService.class);
            relPsnService = SpringContextHolder.getBean(RelPsnService.class);
            relOrgService = SpringContextHolder.getBean(RelOrgService.class);
            relProService = SpringContextHolder.getBean(RelProService.class);
            relMapper = SpringContextHolder.getBean(RelMapper.class);
            relPsnMapper = SpringContextHolder.getBean(RelPsnMapper.class);
            relOrgMapper = SpringContextHolder.getBean(RelOrgMapper.class);
            relProMapper = SpringContextHolder.getBean(RelProMapper.class);

            //解析JSON
            JSONObject json = JSONObject.parseObject(data);
            String pidString = json.getString("pid");
            Integer pid = Integer.parseInt(pidString);
            String type = (String) json.get("type");
            Rel rel = null;
            //当类型是关联关系
            if ("REL".equals(type)) {
                rel = new Rel();
                rel.setPid(pid);
                rel.setApprovalStatus("6");
                //更新关系数据
                relService.updateSelective(rel);
                //获取当前关系数据
                rel = relService.get(pid);
                //判断本层关联方类型为自然人时，将自然人表中关系有效性改为是。
                if (rel.getFromType().equals("PSN")) {
                    List<Filter> filter = new Filter.Builder()
                            .eq("name", rel.getFromMemo())
                            .eq("identifyNumber", rel.getFromIdentifyNumber())
                            .build();
                    RelPsn psn = relPsnService.get(filter);
                    if (psn != null) {
                        psn.setEffectRelation("1");
                        psn.setIsAssoParty("1");
                        relPsnService.updateSelective(psn);
                    }
                    //当是法人时，将法人表中关系有效性改为是。
                } else if (rel.getFromType().equals("ORG")) {
                    List<Filter> filter = new Filter.Builder()
                            .eq("orgName", rel.getFromMemo())
                            .eq("uniscCode", rel.getFromIdentifyNumber())
                            .build();
                    RelOrg org = relOrgService.get(filter);
                    if (org != null) {
                        org.setEffectRelation("1");
                        org.setIsAssoParty("1");//是否关联方  1是 0 否
                        relOrgService.updateSelective(org);
                    }
                } else if (rel.getFromType().equals("PRO")) {//更新金融产品数据
                    List<Filter> filter = new Filter.Builder()
                            .eq("proName", rel.getFromMemo())
                            .eq("proProvider", rel.getFromIdentifyNumber())
                            .build();
                    RelPro pro = relProService.get(filter);
                    if (pro != null) {
                        pro.setEffectRelation("1");
                        //pro.setIsAssoParty("1");
                        relProService.updateSelective(pro);
                    }
                }

                try {
                    //新增关系图谱，此处只需要有新增图谱功能就可以，解除图谱关系显示，由创建关系页面保存和批处理自动任务来处理
                    List<Rel> list = new ArrayList<>();
                    list.add(rel);
                    //relService.insertNeo4j(list);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("关联关系审批完成后，加入图谱数据操作失败！");
                }
            }
        } catch (ServiceException e) {
            e.printStackTrace();
            logger.error("关联关系审批完成后，数据处理操作失败！");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("关联关系审批完成后，数据处理操作失败！");
        }
    }




    private void creditProcess(String data, String memo) {
        TraCredit traCredit = JSON.parseObject(data, TraCredit.class);
        traCredit.setStatus("6");
        traCredit.setIsEffective("1");
        traCredit.setStatusComment(memo);
        traCreditService.updateSelective(traCredit);
    }

    private void useCreditProcess(String data, String memo) {
        TraUsecredit traUsecredit = JSON.parseObject(data, TraUsecredit.class);
        List<Filter> filters = Lists.newArrayList();
        String internalApplyNo = traUsecredit.getInternalApplyNo();
        filters.add(Filter.eq("internalApplyNo", internalApplyNo));
        filters.add(Filter.eq("delflag", "1"));
        TraCredit traCredit = traCreditService.get(filters);
        if ("1".equals(traUsecredit.getIsEffective())) {
            Long validityBalance = traCredit.getValidityBalance();
            BigDecimal balance = BigDecimal.valueOf(validityBalance);
            BigDecimal add = balance.add(traUsecredit.getThisTraDiff());
            traCredit.setValidityBalance(add.longValue());
        } else {
            Long validityBalance = traCredit.getValidityBalance();
            BigDecimal balance = validityBalance == null ? BigDecimal.ZERO : BigDecimal.valueOf(validityBalance);
            BigDecimal thisTraAmount = traUsecredit.getThisTraAmount();
            traCredit.setValidityBalance((balance.add(thisTraAmount)).longValue());
        }
        traCreditService.updateSelective(traCredit);
        traUsecredit.setIsEffective("1");
        traUsecredit.setStatus("6");
        traUsecredit.setStatusComment(memo);
        traUsecreditService.updateSelective(traUsecredit);
    }
}
