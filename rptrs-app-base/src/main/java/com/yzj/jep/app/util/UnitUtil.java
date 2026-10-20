package com.yzj.jep.app.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.fPaaS1.core.IFPaaS1Handler;
import com.fPaaS1.core.Message;
import com.fPaaS1.core.factory.FaultFactory;
import com.fPaaS1.core.factory.MessageFactory;
import com.fPaaS1.core.utils.Constants;
import com.yzj.jep.app.bus.model.UnitEntitys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 机构树信息同步
 * </p>
 *
 * @author yls
 * @since 2020/10/21
 */
public class UnitUtil {
    private static Logger logger = LoggerFactory.getLogger(UnitUtil.class);

    @Autowired
    private IFPaaS1Handler DeptsGatewayClientHandler;

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


    //自己写的测试接口的类,在实际代码总不用le,实际在用gateway
    /*0000	成功
      1001	请求参数缺失，缺少参数{请求参数名称}。
       9999	服务端未知错误。
     */
    public List<UnitEntitys> listAboutEntity() {
        List<UnitEntitys> unitEntitysList = null;
        String unitUrl = "http://172.20.100.60:6700/cramc-article-poll/manager/relatedParty/queryDeptAlls";
        String result = new GetOtherInterfaces().getUnitInf(unitUrl);
        Map maps = (Map) JSON.parse(result);
        //此为接口所提供的状态码,0000/1001/9999
        String resultCode = (String) maps.get("resultCode");
        if ("0000".equals(resultCode)) {
            Object data = maps.get("data");
            String string = data.toString();
            string = string.replace("-", null);
            unitEntitysList = JSONArray.parseArray(string, UnitEntitys.class);
            return unitEntitysList;
        } else {
            return unitEntitysList;
        }

    }

}


