package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.service.RuleLibRestCallback;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Create by zhangfei
 * DATE 2020/5/20 7:15 下午
 */
@Service(value = "TranRuleRestServiceImpl")
public class TranRuleRestServiceImpl implements RuleLibRestCallback {
    @Override
    public Map checkRule(Map parameters) {
        Map result = new HashMap();
        result.put("code", "00");
        return result;
    }
}
