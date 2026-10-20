package com.yzj.jep.app.util;

import com.yzj.jep.app.base.utils.Configs;
import com.yzj.jep.core.holder.SpringContextHolder;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class Neo4jUtil {
    private static Logger logger = LoggerFactory.getLogger(Neo4jUtil.class);

    @Autowired
    private static Configs configs;

    public static JSONObject addNeo4jRel(JSONObject param) {
        configs = SpringContextHolder.getBean(Configs.class);
        JSONObject result = new JSONObject();
        String url = configs.getNeo4jUrl() + "/relOrg/create";
//        String url = "http://localhost:8091/relOrg/create";
        String resultStr = HttpRequestUtil.doPost(url, param.toString());
        return JSONObject.fromObject(resultStr);
    }

    public static JSONObject removeNeo4jRel(JSONObject param) {
        configs = SpringContextHolder.getBean(Configs.class);
        JSONObject result = new JSONObject();
        String url = configs.getNeo4jUrl() + "/relOrg/delete";
        String resultStr = HttpRequestUtil.doPost(url, param.toString());
        return JSONObject.fromObject(resultStr);
    }

    public static void main(String[] arg) {
        net.sf.json.JSONObject param = new net.sf.json.JSONObject();
        param.put("fromPid", 1);
        param.put("toPid", 2);
        param.put("fromName", "测试");
        param.put("toName", "测试2");
        param.put("fromType", "p");
        param.put("toType", "p");
        param.put("fromIdentifyType", "1");
        param.put("toIdentifyType", "1");
        param.put("fromIdentifyNumber", "111111111");
        param.put("toIdentifyNumber", "2222222222");
        param.put("relCategory", "测试");
        param.put("relCategorySub", "测试");
        //是否关联方
        param.put("fromIsRelParty", "0");
        //是否内部人
        param.put("fromIsInsider", "0");
        //是否持股
        param.put("fromIsShareholder", "0");
        //是否关联方
        param.put("toIsRelParty", "0");
        //是否内部人
        param.put("toIsInsider", "0");
        //是否持股
        param.put("toIsShareholder", "0");
        net.sf.json.JSONObject result = Neo4jUtil.addNeo4jRel(param);
    }
}
