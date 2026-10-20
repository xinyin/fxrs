package com.yzj.jep.app.ins.service;


import com.yzj.jep.app.ins.model.TraCounterparty;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface BaseService {
    Map<String, Object> getRelInfo(String relNumber, String relName);

    BigDecimal getCurrYearTotalTradeAmount(Integer RelId, String RelType);

    Long getMajorTradeCount(Integer relId, String relType);

    /**
     * 根据交易主体 关联方  交易类型
     *
     * @param RelId
     * @param RelType
     * @param traSubName
     * @param isAccumulated
     * @param countType
     * @return
     */
    BigDecimal getCurrYearTotalTradeAmount(Integer RelId, String RelType, String traSubName, String isAccumulated, String countType);

    /**
     * 根据交易主体 关联方 关联方类型 交易类型
     *
     * @param RelId
     * @param RelType
     * @param traSubName
     * @param tranType
     * @param isAccumulated
     * @param countType
     * @return
     */
    BigDecimal getCurrYearTotalTradeAmount(Integer RelId, String RelType, String traSubName, String tranType, String isAccumulated, String countType);

    /**
     * 获取在途的关联方的交易总额
     *
     * @param RelId 关联方ID
     * @param RelType 关联方类型  1.个人 2.机构
     * @param traSubName
     * @return
     */
    BigDecimal getInRoadTotalTradeAmount(Integer RelId, String RelType, String traSubName);

    /**
     * 根据累计交易规则，对累计数据进行归零处理
     *
     * @param RelId
     * @param RelType
     * @param traSubName
     * @param tranType
     */
    void grandTradeZero(Integer RelId, String RelType, String traSubName, String tranType);


    List<TraCounterparty> sumAfterCleanAmount(Map<String, Object> param);
}
