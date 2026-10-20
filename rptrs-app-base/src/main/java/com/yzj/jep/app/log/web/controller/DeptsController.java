/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年07月16日 14:41:49
 *
 */

package com.yzj.jep.app.log.web.controller;

import com.fPaaS1.core.IFPaaS1Handler;
import com.fPaaS1.core.Message;
import com.fPaaS1.core.factory.FaultFactory;
import com.fPaaS1.core.factory.MessageFactory;
import com.fPaaS1.core.utils.Constants;
import com.yzj.jep.app.fPaas.client.HttpConstans;
import com.yzj.jep.app.log.model.LogRecord;
import com.yzj.jep.web.controller.CrudController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 这个类只是用gateway把接口弄通，具体请参见定时器  UpdateUnitTask   UpdateStaffTask
 * */

@Controller
@RequestMapping("/batch/data")
public class DeptsController extends CrudController<LogRecord> {
    private static Logger logger = LoggerFactory.getLogger(DeptsController.class);

    @Autowired
    private IFPaaS1Handler DeptsGatewayClientHandler;

    @Autowired
    private IFPaaS1Handler StaffGatewayClientHandler;

    @RequestMapping(value = "/depts")
    public void depts() {
        Message msg = null;
        Message resmsg = null;
        Map<String, Object> bodys = new HashMap<String, Object>();
        Map<String, Object> headers = new HashMap<String, Object>();

      /*  //请求方式及
        bodys.put("postType", HttpConstans.METHOD_POST);*/

        //全局唯一流水号
        String transSeqNo =
                "99" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "0"
                        + String.format("%09d", new Random().nextInt(999999999));
        int length = transSeqNo.length();


        msg = MessageFactory.create(transSeqNo, MessageFactory.createSimpleMessageInstance(),
                MessageFactory.createSimpleMessage(headers, bodys),
                FaultFactory.create(Constants.ResponseCode.SUCCESS, ""));
        try {
            resmsg = (Message) DeptsGatewayClientHandler.handle(msg);

            Map<String, Object> map = (Map<String, Object>) resmsg.getTarget().getBodys();
            String code = (String) map.get("msgCode");
            //判断请求接口返回的状态码 0000--成功  1001--缺少参数  9999--数据异常
            if ("0000".equals(code)) {
                Object data = map.get("data");
                System.out.println(data);
            } else {
                logger.info("请求接口错误信息:" + code);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/getStaff")
    public void staff() {
        Message msg = null;
        Message resmsg = null;
        Map<String, Object> bodys = new HashMap<String, Object>();
        Map<String, Object> headers = new HashMap<String, Object>();

        /*//请求方式及
        bodys.put("postType", HttpConstans.METHOD_POST);*/
        //全局唯一流水号
        String transSeqNo =
                "99" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "0"
                        + String.format("%09d", new Random().nextInt(999999999));

        msg = MessageFactory.create(transSeqNo, MessageFactory.createSimpleMessageInstance(),
                MessageFactory.createSimpleMessage(headers, bodys),
                FaultFactory.create(Constants.ResponseCode.SUCCESS, ""));
        try {
            resmsg = (Message) StaffGatewayClientHandler.handle(msg);

            Map<String, Object> map = (Map<String, Object>) resmsg.getTarget().getBodys();
            String code = (String) map.get("msgCode");
            //判断请求接口返回的状态码 0000--成功  1001--缺少参数  9999--数据异常
            if ("0000".equals(code)) {
                Object data = map.get("data");
                System.out.println(data);
            } else {
                logger.info("请求接口错误信息:" + code);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}