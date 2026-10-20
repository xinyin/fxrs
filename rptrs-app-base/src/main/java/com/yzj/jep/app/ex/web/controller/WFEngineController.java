package com.yzj.jep.app.ex.web.controller;

import java.util.HashMap;
import java.util.Map;


import com.fPaaS1.core.IFPaaS1Handler;
import com.fPaaS1.core.Message;
import com.fPaaS1.core.factory.FaultFactory;
import com.fPaaS1.core.factory.MessageFactory;
import com.fPaaS1.core.utils.Constants;
import com.fpaas.startrun.StartUpRun;
import com.jzr.fpaas.util.MessageUtil;
import com.yzj.jep.app.sys.model.UserProfile;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Strings;
import com.yzj.jep.rules.drools5.model.Objects;
import com.yzj.jep.rules.drools5.service.PackagesService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/WF/test")
public class WFEngineController extends CrudController<Objects> {
    private static Logger logger = LoggerFactory.getLogger(WFEngineController.class);

	/*@Autowired
    private IFPaaS1Handler GatewayClientHandler;*/

    @Autowired
    private StartUpRun startUpRun;

    @RequestMapping(value = "/gateway")
    @ResponseBody
    public Object gateway() throws Exception {
        Message msg = null;
        Message resmsg = null;
        Map<String, Object> bodys = new HashMap<String, Object>();
        bodys.put("transId", "11111");
        bodys.put("descCode", "helloword");//你组包模板中需要的值
        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("serviceCode", "111");
        headers.put("chnlId", "02");
        headers.put("platForm", "22");
        headers.put("address", "192.168.1.103");
        msg = MessageFactory.create("1111", MessageFactory.createSimpleMessageInstance(),
                MessageFactory.createSimpleMessage(headers, bodys),
                FaultFactory.create(Constants.ResponseCode.SUCCESS, ""));
//        resmsg= (Message) GatewayClientHandler.handle(msg);
        System.out.println("获取返回：" + resmsg);
        return resmsg;
    }

    @RequestMapping(value = "/init")
    public String init() {
        StartUpRun.init();
        return "success";
    }

    @RequestMapping(value = "/billAdd")
    public String add(Model model) {
        return "ex/billAdd";
    }

    @RequestMapping(value = "/A1list")
    public String A1list(Model model) {
        return "rpt/atest";
    }

    //报表---授信银保监会
    @RequestMapping(value = "/CreditYBJ")
    public String creditYBJ(Model model) {
        return "rpt/creditYBJ";
    }

    //报表---单笔关联交易-其他
    @RequestMapping(value = "/singleOther")
    public String singleOther(Model model) {
        return "rpt/singleOther";
    }

    //报表---单笔关联交易-资产转移类
    @RequestMapping(value = "/singleAsset")
    public String singleAsset(Model model) {
        return "rpt/singleAsset";
    }

    //报表---单笔关联交易-提供服务类
    @RequestMapping(value = "/singleService")
    public String singleService(Model model) {
        return "rpt/singleService";
    }

    //报表---单笔关联交易-授信类
    @RequestMapping(value = "/singleCredit")
    public String singleCredit(Model model) {
        return "rpt/singleCredit";
    }


    @RequestMapping(value = "/attendance")
    public String attendance(Model model) {
        return "ex/attendance";
    }

    @RequestMapping(value = "/neo4j")
    public String neo4j(Model model) {
        Subject currentUser = SecurityUtils.getSubject();
        UserProfile currentUser1 = (UserProfile) currentUser.getSession().getAttribute("currentUser");
        model.addAttribute("tenantId",currentUser1.getTenantId());


        return "ex/neo4j";
    }

    /**
     * 请假流程预处理参数
     *
     * @return
     */
    @RequestMapping(value = "/saveAdj")
    @ResponseBody
    public Map<String, Object> saveAdj(Map<String, String> staffls, HttpServletRequest request) {

        Map<String, Object> tips = new HashMap<String, Object>();
        tips.put(WebCode.SUCCESS, true);
        tips.put("toHR", 0);
        tips.put("toDUM", 1);
        tips.put("toBUM", 0);
        tips.put("UnionId", (staffls.get("startDate") + staffls.get("endDate") + staffls.get("mobile") + staffls.get("hours")).replace(" ", ""));
        tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        return tips;
    }

}