/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年09月28日 10:02:44
 *
 */

package com.yzj.jep.app.bus.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.fPaaS1.core.IFPaaS1Handler;
import com.fPaaS1.core.Message;
import com.fPaaS1.core.factory.FaultFactory;
import com.fPaaS1.core.factory.MessageFactory;
import com.fPaaS1.core.utils.Constants;
import com.yzj.jep.app.bus.model.UnitEntitys;
import com.yzj.jep.app.bus.service.UpdateUnittService;
import com.yzj.jep.orm.mybatis.mapper.Mapper;
import com.yzj.jep.orm.mybatis.service.MyBatisCRUDService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UpdateUnitServiceImpl extends MyBatisCRUDService<UnitEntitys, Integer> implements UpdateUnittService {

    Logger logger = LoggerFactory.getLogger(UpdateUnitServiceImpl.class);

    @Autowired
    private IFPaaS1Handler DeptsGatewayClientHandler;

    @Override
    public List<UnitEntitys> deptsMethod() {
        List<UnitEntitys> unitEntitysList = new ArrayList<>();
        Message msg = null;
        Message resmsg = null;
        Map<String, Object> bodys = new HashMap<String, Object>();
        Map<String, Object> headers = new HashMap<String, Object>();

        //全局唯一流水号
        String transSeqNo =
                "99" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "0"
                        + String.format("%09d", new Random().nextInt(999999999));

        msg = MessageFactory.create(transSeqNo, MessageFactory.createSimpleMessageInstance(),
                MessageFactory.createSimpleMessage(headers, bodys),
                FaultFactory.create(Constants.ResponseCode.SUCCESS, ""));
        try {
            resmsg = (Message) DeptsGatewayClientHandler.handle(msg);

            Map<String, Object> map = (Map<String, Object>) resmsg.getTarget().getBodys();
            String code = (String) map.get("msgCode");
            //判断请求接口返回的状态码 0000--成功  1001--缺少参数  9999--数据异常
            if ("0000".equals(code)) {
                System.out.println(code);
                Object data = map.get("data");
                String string = data.toString();
                unitEntitysList = JSONArray.parseArray(string, UnitEntitys.class);
                return unitEntitysList;
            } else {
                logger.info("请求接口错误信息:" + code);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return unitEntitysList;


    }

    @Override
    public Mapper<UnitEntitys, Integer> getMapper() {
        return null;
    }
}