package com.yzj.jep.app.ins.service;

import java.util.Map;

/**
 * @title: MajorTraCheckService
 * @Author Whx
 * @Date: 2021/8/18 15:42
 * @Version 1.0
 */
public interface MajorTraCheckService {
    Map<String, Object> sumAmount(Integer pid, String relType);

    Map<String, Object> sumRealTradeAmount(Integer pid, String relType);

    Integer hasUnassortedBus(String relNumber);

    /**
     * 新计算年度总交易金额，未清零交易金额汇总
     * @param pid
     * @param relType
     * @return
     */
    Map<String, Object> sumAmountNew(Integer pid, String relType,String tranType);
}
