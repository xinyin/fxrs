package com.yzj.jep.app.ins.service;

import java.util.Map;
import java.util.List;

/**
 * @author tw
 * @date 2020/6/30 13:48
 * @remark 保险类交易 合规校验及判断是否重大关联交易
 */
public interface RuleCheckService {


    List<Map<String, String>> ComPlianceCheck(String tradeType, Map ruleParams);

    Map GreatCheck(String ruleParam, String tradeType);

    String warnLevelCheck(Map params);

    Map<String, String> quotaCheck(Map ruleParams);
}
