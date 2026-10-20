package com.yzj.jep.app.bus.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yzj.jep.app.bus.mapper.UnifiedProtocolMapper;
import com.yzj.jep.app.bus.model.RelDict;
import com.yzj.jep.app.bus.service.RelDictService;
import com.yzj.jep.app.bus.service.UnifiedProtocolService;
import com.yzj.jep.app.bus.vo.ProtocolVo;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.sys.service.DictService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: UnifiedProtocolServiceImpl
 * @Author Whx
 * @Date: 2021/7/16 17:43
 * @Version 1.0
 */
@Service
public class UnifiedProtocolServiceImpl implements UnifiedProtocolService {
    Logger logger = LoggerFactory.getLogger(UnifiedProtocolServiceImpl.class);

    @Autowired
    private UnifiedProtocolMapper unifiedProtocolMapper;

    @Autowired
    private DictItemService dictItemService;

    /**
     * 多条件查询具有统一框架协议的业务表
     *
     * @param pageNumber
     * @param pageSize
     * @param year
     * @param quarter
     * @param contractNo
     * @return
     */
    @Override
    public PageInfo<ProtocolVo> queryByConditions(Integer pageNumber, Integer pageSize, String year, String quarter, String contractNo) {
        PageHelper.startPage(pageNumber, pageSize);
        //构造查询条件
        Map<String, Object> param = new HashMap<>();
        //处理查询条件
        StringBuilder stringBuilder = new StringBuilder();
        if (StrUtil.isNotBlank(year)) {
            stringBuilder.append(year);
            param.put("year", stringBuilder.toString().concat("%"));
        }
        //季度
        if (StrUtil.isNotBlank(quarter)) {
            int ofYear = DateUtil.year(new Date());
            if (StrUtil.isBlank(year)) {
                stringBuilder.append(ofYear);
            }
            String beginQuarter = null;
            String endQuarter = null;
            //季度endQuarter拼接最大时间
            if ("1".equals(quarter)) {
                beginQuarter = stringBuilder.toString().concat("-01-01");
                endQuarter = stringBuilder.toString().concat("-04-01");
            } else if ("2".equals(quarter)) {
                beginQuarter = stringBuilder.toString().concat("-04-01");
                endQuarter = stringBuilder.toString().concat("-07-01");
            } else if ("3".equals(quarter)) {
                beginQuarter = stringBuilder.toString().concat("-07-01");
                endQuarter = stringBuilder.toString().concat("-10-01");
            } else {
                beginQuarter = stringBuilder.toString().concat("-10-01");
                //年加一
                int addYear = ofYear + 1;
                //endQuarter = new StringBuilder(addYear).append("-01-01").toString();
                  endQuarter = addYear+"-01-01";
            }
            param.put("beginQuarter", beginQuarter);
            param.put("endQuarter", endQuarter);
        }
        if (StrUtil.isNotBlank(contractNo)) {
            param.put("contractNo", contractNo);
        }
        List<ProtocolVo> list = unifiedProtocolMapper.queryByConditions(param);
        list.forEach(traAgreement -> getAccounted(traAgreement));
        PageInfo<ProtocolVo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public void getAccounted(ProtocolVo traAgreement) {
        BigDecimal accounted = BigDecimal.ZERO;
        if (BigDecimal.ZERO.compareTo(empty(traAgreement.getContractAmount())) < 0) {
            accounted = empty(traAgreement.getPracTradeAmount()).divide(empty(traAgreement.getContractAmount()), 4, RoundingMode.HALF_UP);
            traAgreement.setAccounted(accounted);
            return;
        }
        traAgreement.setAccounted(accounted);
    }

    public BigDecimal empty(String b){
        if(StringUtils.isEmpty(b)){
            return BigDecimal.ZERO;
        }
        return new BigDecimal(b);
    }


    /**
     * 查询统一交易协议执行情况
     *
     * @param relNumber
     * @param relName
     * @return
     */
    @Override
    public List<ProtocolVo> getIsAgreementImpl(String relNumber, String relName) {
        List<ProtocolVo> resultList = null;
        try {
            //获取全部非统一交易协议执行情况的数据
            resultList = unifiedProtocolMapper.getIsAgreement(relNumber, relName);

            //循环遍历数据，获取所有的执行情况
            if (null != resultList && resultList.size() > 0) {
                for (ProtocolVo protocaol : resultList) {
                    //根据交易类型获取业务类型
                    if (null != protocaol.getTranType()) {
                        String busType = getBusType(protocaol.getTranType(),protocaol.getBusType());
                        protocaol.setBusType(busType);
                    }

                    //通过协议编号查询实际执行情况
                    if (null != protocaol.getAgreementNum() && protocaol.getAgreementNum() != "") {
                        ProtocolVo protocolVo = unifiedProtocolMapper.getAgreementTotalDetail(protocaol.getAgreementNum());
                        if (null != protocolVo && protocolVo.getRelTradeAmount() != null) {
                            String pracTradeAmount = new BigDecimal(protocolVo.getRelTradeAmount()).toString();
                            protocaol.setPracTradeAmount(pracTradeAmount);//实际交易金额
                            //实际交易金额占比
                            if (protocaol.getPracTradeAmount() != null && protocaol.getContractAmount() != null) {
                                String rat = new BigDecimal(protocaol.getPracTradeAmount()).divide(new BigDecimal(protocaol.getContractAmount()), 5, RoundingMode.HALF_UP).multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString();
                                protocaol.setRat(rat);
                            } else {
                                continue;//不符合继续执行 循环
                            }

                        } else {
                            continue;//不符合继续执行 循环
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取统一交易协议执行情况出错:" + e.getMessage());
        }
        return resultList;

    }

    /**
     * 通过业务类型编码，和交易类型编码 ，获取实际业务类型的值
     * @param tranType
     * @param busType
     * @return
     */
    private String getBusType(String tranType,String busType) {
        String result = null;
        try {
            List<DictItem> dictItemList = null;
            List<Filter> filters = null;
            if ("01".equals(tranType)) {//INS-InvestmentType
                filters = new Filter.Builder().eq("dictCode", "INS-InvestmentType").build();
            }
            if ("02".equals(tranType)) {
                //业务类型 INS-FundsType
                filters = new Filter.Builder().eq("dictCode", "INS-FundsType").build();
            }
            if ("03".equals(tranType)) {//INS-InsBusinessType
                //业务类型 INT-InsBusinessType
                filters = new Filter.Builder().eq("dictCode", "INT-InsBusinessType").build();
            }
            if ("04".equals(tranType)) {
                //业务类型 INT-BusinessType
                filters = new Filter.Builder().eq("dictCode", "INT-BusinessType").build();
            }
            if ("05".equals(tranType)) {
                //业务类型 INS-ServiceType
                filters = new Filter.Builder().eq("dictCode", "INS-ServiceType").build();
            }
            if ("06".equals(tranType)) {
                //业务类型 INS-otherType
                filters = new Filter.Builder().eq("dictCode", "INS-otherType").build();
            }

            dictItemList = dictItemService.select(filters);
            Map busTypeMap = new HashMap();
            for (DictItem dic : dictItemList) {
                busTypeMap.put(dic.getItemValue(),dic.getItemLabel());
            }
            result = (String) busTypeMap.get(busType);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查找业务类型失败："+e.getMessage());
        }
        return result;
    }

}
