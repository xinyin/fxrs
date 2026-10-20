package com.yzj.jep.app.util;

import cn.hutool.core.util.ObjectUtil;
import com.yzj.jep.app.fil.model.RelatedTranBaseInfoVo;
import com.yzj.jep.app.fil.model.RelatedTranMapperVo;
import com.yzj.jep.app.ins.constants.TranTypeConstant;
import com.yzj.jep.app.ins.enums.RelatedPartyCategoryEnum;
import com.yzj.jep.app.ins.enums.YesOrNoEnum;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author by Liqiang
 * @Date 2022/10/19
 * @Description 判断关联交易是否为重大、一般关联交易
 */
public class JudgeTranType {


    public static RelatedTranMapperVo assemblyData(RelatedTranBaseInfoVo relatedTranBaseInfoVo, Integer relId) {
        RelatedTranMapperVo relatedTranMapperVo = new RelatedTranMapperVo();
        /* 判断是否重大关联交易 */
        BigDecimal relTrandeAmount = relatedTranBaseInfoVo.getRelTrandeAmount();
        String relNumber = relatedTranBaseInfoVo.getRelNumber();
        String relType = relatedTranBaseInfoVo.getRelType();
        String isConsiderSituation = relatedTranBaseInfoVo.getIsconsiderSituation();
        String isAgreement = null;
        if(TranTypeConstant.TRAN_TYPE_2.equals(relatedTranBaseInfoVo.getTranType()) || TranTypeConstant.TRAN_TYPE_4.equals(relatedTranBaseInfoVo.getTranType())){
            isAgreement = relatedTranBaseInfoVo.getIsAgreement();
        }
        /* 封装重大判断参数 */
        Map<String, Object> ruleMap = new HashMap<>(5);
        ruleMap.put("relType", relType);
        ruleMap.put("relNumber", relNumber);
        ruleMap.put("isConsiderSituation", isConsiderSituation);
        ruleMap.put("isAgreement", isAgreement);
        ruleMap.put("relTranAmount", relTrandeAmount);
        /* 判断重大 */
        Map<String, String> ruleResultMap = InsUtil.judgeMajor(ruleMap);

        if (Objects.equals(YesOrNoEnum.YES.getCode(), ruleResultMap.get("considerType"))) {
            /* 一般资金运用类需要逐笔披露 */
            if (TranTypeConstant.TRAN_TYPE_1.equals(relatedTranBaseInfoVo.getTranType())) {
                ruleResultMap.put("eachDisclosure", "1") ;
            }

            ruleResultMap.put("isconsiderSituation", YesOrNoEnum.YES.getCode());

            /* 是免于审议时如果是接口传入的，免于审议情形则按照接口传入的值来保存，否则按照关联方进行判断 */
            if(ObjectUtil.equals(YesOrNoEnum.YES.getCode(), isConsiderSituation)){
                ruleResultMap.put("considerSituation", relatedTranBaseInfoVo.getConsiderSituation());
            }else {
                if (Objects.equals(RelatedPartyCategoryEnum.NATURALPERSON.getSysCode(), relType)) {
                    ruleResultMap.put("considerSituation", "3");
                }
                if (Objects.equals(RelatedPartyCategoryEnum.LEGALPERSON.getSysCode(), relType)) {
                    ruleResultMap.put("considerSituation", "4");
                }
            }
        }
        relatedTranMapperVo.setMajorNumber(ruleResultMap.get("majorNumber"));
        relatedTranMapperVo.setMajorTradeCritical(ruleResultMap.get("majorTradeCritical"));
        relatedTranMapperVo.setMajorType(ruleResultMap.get("majorType"));
        relatedTranMapperVo.setMajorTrade(ruleResultMap.get("majorTrade"));
        relatedTranMapperVo.setSingleRelatedTranType(ruleResultMap.get("singleRelatedTrantype"));
        relatedTranMapperVo.setConsiderType(ruleResultMap.get("considerType"));
        relatedTranMapperVo.setEachReport(ruleResultMap.get("eachReport"));
        relatedTranMapperVo.setEachDisclosure(ruleResultMap.get("eachDisclosure"));
        relatedTranMapperVo.setIsCollectRelated(ruleResultMap.get("isCollectRelated"));
        relatedTranMapperVo.setIsconsiderSituation(ruleResultMap.get("isconsiderSituation"));
        relatedTranMapperVo.setConsiderSituation(ruleResultMap.get("considerSituation"));
        relatedTranMapperVo.setRelId(relId);
        relatedTranMapperVo.setIsAgreement(isAgreement);
        return relatedTranMapperVo;
    }
}
