package com.yzj.jep.app.ins.web.controller;

import com.yzj.jep.app.ins.service.MajorTraCheckService;
import com.yzj.jep.web.code.WebCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @title: 重大关联交易相关
 * @Author Whx
 * @Date: 2021/8/18 14:58
 * @Version 1.0  major
 */
@Controller
@RequestMapping("/ins/majorTraCheck")
public class MajorTraCheckController {

    @Autowired
    private MajorTraCheckService majorTraCheckService;

    /**
     * 选择交易对手时统计年度累计金额(万元) 与 累计实际交易金额（累计清零金额，排除单笔重大，统一交易协议下的）
     *
     * @return
     */
    @RequestMapping("/sumAmount/{pid}/{relType}/{tranType}")
    @ResponseBody
    public Map<String, Object> sumAmount(@PathVariable Integer pid, @PathVariable String relType,@PathVariable String tranType) {
        Map<String, Object> result = new HashMap<>(16);

        Map<String, Object> map = majorTraCheckService.sumAmountNew(pid, relType,tranType);
        result.put(WebCode.SUCCESS, true);
        result.put("msg", "666");
        result.putAll(map);
        return result;
    }

    @GetMapping("hasUnassortedBus/{relNumber}")
    @ResponseBody
    public Map<String, Object> hasUnassortedBus(@PathVariable String relNumber) {
        Map<String, Object> result = new HashMap<>();
        Integer count = majorTraCheckService.hasUnassortedBus(relNumber);
        if (count == 0) {
            /**不存在该关联方相关的未分类的业务数据*/
            result.put(WebCode.SUCCESS, true);
        } else {
            result.put(WebCode.SUCCESS, false);
        }
        result.put("count", count);
        return result;
    }
}
