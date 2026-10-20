package com.yzj.jep.app.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.fPaaS1.core.IFPaaS1Handler;
import com.fPaaS1.core.Message;
import com.fPaaS1.core.factory.FaultFactory;
import com.fPaaS1.core.factory.MessageFactory;
import com.fPaaS1.core.utils.Constants;
import com.yzj.jep.app.bus.model.DeptEntitys;
import com.yzj.jep.app.bus.model.UnitEntitys;
import com.yzj.jep.app.fPaas.client.HttpConstans;
import com.yzj.jep.app.log.web.controller.DeptsController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 *
 * </p>
 *
 * @author yls
 * @since 2020/10/27
 */
public class DeptUtil {

    private static Logger logger = LoggerFactory.getLogger(DeptUtil.class);

    @Autowired
    private IFPaaS1Handler StaffGatewayClientHandler;

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

    /**
     * 这个方法只是把接口看通着没，实际不用的
     * @return
     */
    public List<DeptEntitys> listAboutDept() {

        List<DeptEntitys> deptEntitysList = null;
        String deptUrl = "http://172.20.100.60:6800/cramc-article-poll/manager/relatedParty/queryDeptAlls";
        String unitInf = new GetOtherInterfaces().getUnitInf(deptUrl);
        Map maps = (Map) JSON.parse(unitInf);
//此为接口所提供的状态码,0000/1001/9999
        String resultCode = (String) maps.get("resultCode");
        if ("0000".equals(resultCode)) {
            Object data = maps.get("data");
            String string = data.toString();
            deptEntitysList = JSONArray.parseArray(string, DeptEntitys.class);
            return deptEntitysList;
        } else {
            return deptEntitysList;
        }

    }
}
