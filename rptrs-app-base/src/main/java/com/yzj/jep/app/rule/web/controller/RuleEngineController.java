package com.yzj.jep.app.rule.web.controller;

import java.util.HashMap;
import java.util.Map;


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

@Controller
@RequestMapping("/common/rule")
public class RuleEngineController extends CrudController<Objects> {
    private static Logger logger = LoggerFactory.getLogger(RuleEngineController.class);

    @Autowired
    private PackagesService packagesService;

    /**
     * 规则引擎
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/ruleById/{ruleid}")
    @ResponseBody
    public Map<String, Object> ruleById(@RequestBody String data, @PathVariable String ruleid) {

        Map<String, Object> tips = new HashMap<String, Object>();
        //执行指定模型，获取返回信息
        Map<String, Object> map1 = packagesService.execute(ruleid, data);
        if (map1.get("msg") != null && !Strings.isNullOrEmpty(map1.get("msg").toString())) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, map1.get("msg").toString());
            return tips;
        } else {
            tips.put(WebCode.MSG, "成功");
            tips.put(WebCode.SUCCESS, true);
        }
        return tips;
    }

    @RequestMapping(value = "/test")
    public String add(Model model) {
		/*HttpRequestUtil http=new HttpRequestUtil();
		String url="http://192.168.1.95:8080/serp/common/rule/create";
		JSONObject data= new JSONObject();
		data.put("budgetAmt", "1000");
		data.put("flag", "0");
		JSONObject retunjson=null;
		retunjson=http.httpPost(url, data);
		System.out.println("retunjson="+retunjson);*/
        return "ex/billAdd";
    }

}