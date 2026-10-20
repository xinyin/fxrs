/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月10日 10:36:06
 *
 */

package com.yzj.jep.app.ins.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.mapper.ApprovalPushMapper;
import com.yzj.jep.app.bus.model.ApprovalPush;

import com.yzj.jep.app.contract.model.ApiResponse;
import com.yzj.jep.app.contract.util.HttpClientUtil;
import com.yzj.jep.app.ins.model.Email;
import com.yzj.jep.app.ins.service.ApprovalPushService;
import com.yzj.jep.app.record.model.InterfaceRecord;
import com.yzj.jep.app.record.service.TraInterfaceRecordService;
import com.yzj.jep.app.util.Dict;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import com.yzj.jep.sys.service.AppConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional(readOnly = false)
public class ApprovalPushServiceImpl extends MyBatisCRUDService<ApprovalPush, Integer> implements ApprovalPushService {

    Logger logger = LoggerFactory.getLogger(ApprovalPushServiceImpl.class);

    @Autowired
    private ApprovalPushMapper approvalPushMapper;

    @Autowired
    private AppConfigService appConfigService;
    @Autowired
    private TraInterfaceRecordService traInterfaceRecordService;

    private static final String ResultsInterface = "/fx/related/fx_related_main/fxRelatedMain.do?method=approveProcess";

    private static final String EmailApproverInterface = "/pflifenotice/emailBatch/sendBatchEmail.do";

    @Override
    public Mapper<ApprovalPush, Integer> getMapper() {
        return approvalPushMapper;
    }

    @Override
    public void pushResult(String contractNo, String status) {
        JSONObject param = new JSONObject();
        JSONObject paramSysHead = new JSONObject();
        JSONObject paramBody = new JSONObject();
        ApprovalPush approvalPush = new ApprovalPush();

            String transSeqNo ="99" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "0" + String.format("%09d", new Random().nextInt(999999999));
            paramSysHead.put("CHNL_ID",transSeqNo);
            paramSysHead.put("TRANS_CODE","getApprovalResult");
            paramBody.put("sourceSysCode",contractNo);
            paramBody.put("result",status);

            param.put("SYSHEAD", paramSysHead);
            param.put("BODY", paramBody);
            String url = appConfigService.getConfigValue("approvalResults")+ResultsInterface;
            logger.info("审批推送地址："+url);
            logger.info("审批推送的数据："+param);
            ApiResponse result = HttpClientUtil.httpPostJson(param.toJSONString(), url);
            logger.info("审批结果推送结果：" + JSON.toJSONString(result) + "============================================");
            if (!"200".equals(result.getResultCode())) {
                logger.info("审批结果推送失败:" + result.getMsg());
            }
            //审批后调用OA回推结果。
            interFaceRecord(transSeqNo,result);
            //推送结果入库
            List<Filter> filters = new Filter.Builder().eq("contractno", contractNo).build();
            List<ApprovalPush> apList = this.select(filters);
            // 返回信息码
            approvalPush.setCode(result.getResultCode());
            // 审批结果
            approvalPush.setStatus(status);
            // 唯一标识
            approvalPush.setContractno(contractNo);
            approvalPush.setCreateTime(new Date());
            if (apList.isEmpty()) {
                approvalPush.setPid(UUID.randomUUID().toString());
                this.insertSelective(approvalPush);
            }else {
                approvalPush.setPid(apList.get(0).getPid());
                this.updateSelective(approvalPush);
            }
    }

    @Override
    public void emailSends(String param) {
        try {
            logger.info("开始发送邮件 "+"============================================");
            logger.info("开始发送邮件1 "+"================="+param);
            Email result = HttpClientUtil.httpPostJsonEmail(param, "http://"+appConfigService.getConfigValue("Email_Approver")+EmailApproverInterface);
            logger.info("审批结果推送结果：" + JSONObject.toJSONString(result) + "============================================");
            String emailList = result.getEmailList();
            JSONArray jsonArray = JSONObject.parseArray(emailList);
            String resultCode = result.getResultCode();
            if (resultCode.equals("000")) {
                logger.info("邮件发送成功");
            }
        } catch (Exception e) {
//            e.printStackTrace();
            logger.error("邮件发送异常", e);
        }
    }

    /**
     * 关联交易接口记录
     * @param transSeqNo
     * @param result
     */
    public void interFaceRecord(String transSeqNo,ApiResponse result){
        try {
            InterfaceRecord interfaceRecord = new InterfaceRecord();
            interfaceRecord.setChnlId(transSeqNo);
            interfaceRecord.setTransCode("getApprovalResult");
            interfaceRecord.setBusName("关联交易审批结果接口");
            interfaceRecord.setType("2");
            interfaceRecord.setCode(result.getResultCode());
            if ("200".equals(result.getResultCode())) {
                interfaceRecord.setResult("1");
            }else {
                interfaceRecord.setResult("2");
            }
            traInterfaceRecordService.setInterfaceRecord(interfaceRecord);
        }catch (Exception e){
            logger.error("关联交易删除记录错误：{}",e);
        }
    }
}
