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
import com.yzj.jep.app.bus.model.DeptEntitys;
import com.yzj.jep.app.bus.model.UnitEntitys;
import com.yzj.jep.app.bus.service.UpdateStaffService;
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
public class UpdateStaffServiceImpl extends MyBatisCRUDService<DeptEntitys, Integer> implements UpdateStaffService {

    Logger logger = LoggerFactory.getLogger(UpdateStaffServiceImpl.class);

    @Autowired
    private IFPaaS1Handler StaffGatewayClientHandler;

    @Override
    public Mapper<DeptEntitys, Integer> getMapper() {
        return null;
    }

    @Override
    public List<DeptEntitys> getStaff() {
        List<DeptEntitys> deptEntitysList = null;
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
                String string = data.toString();
                deptEntitysList = JSONArray.parseArray(string, DeptEntitys.class);
                return deptEntitysList;
            } else {
                logger.info("请求接口错误信息:" + code);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deptEntitysList;
    }



}