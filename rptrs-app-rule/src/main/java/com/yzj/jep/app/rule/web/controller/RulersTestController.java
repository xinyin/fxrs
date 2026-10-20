package com.yzj.jep.app.rule.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.rules.drools5.model.ChkLog;
import com.yzj.jep.rules.drools5.service.ChkLogService;
import com.yzj.jep.rules.drools5.service.PackagesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName RulersTestController
 * @Description T0D0
 * @Author 米
 * @Date 22:16
 * @Version 1.0
 **/


/**
 * 规则引擎测试使用
 * 注：需要在
 * rptrs-jep\jep-app\jep-app-sys\src\main\resources\spring\jep-shiro.xml
 * 中添加地址
 */
@Controller
@RequestMapping("/rule")
public class RulersTestController {
    private static Logger logger = LoggerFactory.getLogger(PackagesController.class);

    @Autowired
    private PackagesService packagesService;

    @Autowired
    private ChkLogService chkLogService;

    @RequestMapping(value = "/creditRule")
    @ResponseBody
    public JSONObject creditRule(@RequestBody Map map) {
        //传入参数
        logger.info("-----------------------------"+map.toString());
        ChkLog chkLog = new ChkLog();
        String pkgId = "bfebf9ac-6f37-11ea-a153-8cec4b488362";//规则 id
        String busCode ="";//业务编码
        String traCode = "errParam";//交易编码
        String serNum ="";
        String channelCode ="";
        Double creaditAmountAdd ;
        JSONObject result = new JSONObject();
        JSONObject json = new JSONObject();
        Map param = new HashMap();

        if(map != null || !"".equals(map)){
            /*if(!map.get("serNum").equals("")){
                serNum =String.valueOf(map.get("serNum"));
            }
            if(!map.get("channelCode").equals("")){
                channelCode = String.valueOf(map.get("channelCode"));
            }*/
            serNum =String.valueOf(map.get("serNum"));
            channelCode = String.valueOf(map.get("channelCode"));
            if(map.get("creaditAmountAdd") != null || !"".equals(String.valueOf(map.get("creaditAmountAdd")))){
                if(!"".equals(serNum) && !"".equals(channelCode)){
                    try {
                        json.put("creaditAmountAdd",map.get("creaditAmountAdd"));
                        param = packagesService.execute(pkgId, json.toString());
                        //result.put("result",packagesService.execute(pkgId, json.toString()));
                        busCode = String.valueOf(param.get("returnMsg"));
                        traCode = "0000";
                        logger.info("------------------busCode:"+busCode +"-----------------traCode:"+traCode);
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                        traCode = "0001";
                        logger.info("------------------busCode:"+busCode +"-----------------traCode:"+traCode);
                    }
                }
            }
            param(chkLog,pkgId,busCode,serNum,channelCode,traCode);
            result.put("busCode",busCode);
            result.put("traCode",traCode);

        }else {
            param(chkLog,pkgId,busCode,serNum,channelCode,traCode);
            result.put("busCode",busCode);
            result.put("traCode",traCode);
        }
        return result;
    }

    /**
     * 信息入库
     * @param chkLog
     */
    public  void  insert(ChkLog chkLog ){
        try{
            chkLogService.insert(chkLog);
        }catch (Exception e){
            logger.info(e.getMessage());
        }
    }

    /**
     * 参数值设置
     * @param chkLog
     * @param pkgId
     * @param busCode
     */
    public  void param(ChkLog chkLog ,String pkgId, String busCode,String serNum,String channelCode,String traCode){
        chkLog.setSerNum(serNum);
        chkLog.setChannelCode(channelCode);
        chkLog.setBusCode(busCode);
        chkLog.setTraCode(traCode);
        chkLog.setTraType("授信类");
        chkLog.setRuleId(pkgId);
        chkLog.setCreateTime(new Date());
        insert(chkLog);
    }


}
