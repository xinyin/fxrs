package com.yzj.jep.app.fPaas.server;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.fPaaS1.core.IFPaaS1Handler;
import com.fPaaS1.core.Message;
import com.yzj.jep.app.bus.model.GdTraZr;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.contract.model.RelDistinguishVo;
import com.yzj.jep.app.holder.SpringHolder;
import com.yzj.jep.app.ins.model.*;
import com.yzj.jep.app.ins.service.*;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.orm.entity.Order;
import org.drools.rule.From;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * 关联交易未识别类型接受接口
 */
public class TraUnSortOutHandler implements IFPaaS1Handler<Message> {

    private static final Logger logger = LoggerFactory.getLogger(TraUnSortOutHandler.class);

    private UnSortOutService unSortOutService;

    private TraInterestService traInterestService;

    private TraFundsService traFundsService;

    private TraInvestmentService traInvestmentService;

    private TraOtherService traOtherService;

    private TraServiceService traServiceService;

    private TraBusinessService traBusinessService;

    private static final Integer NUMBER = 50;


    @Override
    public Message handle(Message message) throws Exception {
        unSortOutService = SpringHolder.getBean(UnSortOutService.class);
        traServiceService = SpringHolder.getBean(TraServiceService.class);
        traOtherService = SpringHolder.getBean(TraOtherService.class);
        traInterestService = SpringHolder.getBean(TraInterestService.class);
        traInvestmentService = SpringHolder.getBean(TraInvestmentService.class);
        traFundsService = SpringHolder.getBean(TraFundsService.class);
        traBusinessService = SpringHolder.getBean(TraBusinessService.class);
        Map tips = new HashMap<String,Object>(16);
        Map map = (Map) message.getTarget().getBodys();
        try {

            /**
             * 为列表页面做数据准备
             */
            JSONObject data = new JSONObject(map);
            //传入参数
            logger.info("未识别关联交易接口入参：" + data.toString());
            String str =  data.getString("tradList");
            List<UnSortOut> tradList = JSONObject.parseArray(str,UnSortOut.class);
            if (tradList.size()>0) {
                //计算分批次数
                int limit = (tradList.size() + NUMBER - 1) / NUMBER;
                //分成limit次发请求到数据库，in（）操作时   可以把多条数据分割成多组请求
                Stream.iterate(0, n -> n + 1).limit(limit).forEach(a -> {
                    //拿到这个参数的流的 （a * NUMBER）后面的数据  .limit（NUMBER）->后面数据的50条  .collect(Collectors.toList()->组成一个toList
                    List<UnSortOut> paperEntityList = tradList.stream().skip(a * NUMBER).limit(NUMBER).collect(Collectors.toList());
                    for (UnSortOut unSortOut:paperEntityList){
                        List< Filter > filters = new ArrayList<>();
                        filters.add(new Filter("contractNo", Operator.EQ, unSortOut.getContractNo()));
                        filters.add(new Filter("pracTradeAmount", Operator.NL, null));
                        //有合同
                        if(StringUtils.isNotEmpty(unSortOut.getContractNo())){
                            if(StringUtils.isEmpty(unSortOut.getTradeAmount())){
                                //法律合规系统推送（无实际交易金额）直接入库待分类
                                this.instrade(unSortOut);
                            }else {
                                //erp推送（有实际交易金额） 第一次付款（修改原数据，补全实际交易金额）
                                //先查询未分类表，如果不存在再查具体交易表
                                UnSortOut un = unSortOutService.get(filters);
                                int num=0;
                                if(null == un){
                                    num = this.updatePracTradeAmount(filters,unSortOut);
                                }else {
                                    //仍在未分类的数据修改实际交易金额
                                    un.setTradeAmount(unSortOut.getTradeAmount());
                                    num = unSortOutService.updateSelective(un);
                                }
                                //非第一次付款（生成新的数据）
                                if(num <= 0){
                                    this.instrade(unSortOut);
                                }
                            }
                        }
                        //无合同 erp推送（有实际交易金额）
                        if(StringUtils.isEmpty(unSortOut.getContractNo()) && StringUtils.isNotEmpty(unSortOut.getTradeAmount())){
                            this.instrade(unSortOut);
                        }
                    }
                });
            }
            tips.put("CHNL_ID",map.get("chnlId"));
            tips.put("TRANS_CODE","traUnSortOut");
            tips.put("resultCode","0000");
            tips.put("msg","成功");
        } catch (NullPointerException e){
            e.printStackTrace();
            tips.put("resultCode","1001");
            tips.put("msg","失败");
        } catch (JSONException e){
            tips.put("resultCode","1001");
            tips.put("msg","失败");
        } catch (Exception e) {
            tips.put("resultCode", "9999");
            tips.put("msg", "失败");
        } finally {
            message.getTarget().setBody(tips);
            return message;
        }
    }

    public int updatePracTradeAmount(List< Filter > filters,UnSortOut unSortOut ){
        int num =0;
        //投资入股类交易
        TraInvestment Investment =  traInvestmentService.get(filters);
        if(null != Investment){
            Investment.setPracTradeAmount(unSortOut.getTradeAmount());
            num = traInvestmentService.updateSelective(Investment);
        }
        //资产运用类
        TraFunds traFunds = traFundsService.get(filters);
        if(null != traFunds){
            traFunds.setPracTradeAmount(unSortOut.getTradeAmount());
            num = traFundsService.updateSelective(traFunds);
        }
        //利益转移类
        TraInterest traInterest = traInterestService.get(filters);
        if(null != traInterest){
            traInterest.setPracTradeAmount(unSortOut.getTradeAmount());
            num = traInterestService.updateSelective(traInterest);
        }
        //其他类
        TraOther traOther = traOtherService.get(filters);
        if(null != traOther){
            traOther.setPracTradeAmount(unSortOut.getTradeAmount());
            num = traOtherService.updateSelective(traOther);
        }
        //提供服务类
        TraService traService = traServiceService.get(filters);
        if(null != traService){
            traService.setPracTradeAmount(unSortOut.getTradeAmount());
            num = traServiceService.updateSelective(traService);
        }
        //保险业务类
        TraBusiness traBusiness = traBusinessService.get(filters);
        if(null != traBusiness){
            traBusiness.setPracTradeAmount(unSortOut.getTradeAmount());
            num =traBusinessService.updateSelective(traBusiness);
        }
        return num;
    }

    public  void instrade(UnSortOut unSortOut){
        if(StringUtils.isEmpty(unSortOut.getTranType())){
            unSortOutService.insertSelective(unSortOut);
        }else {
            unSortOut.setTranType(unSortOut.getTranType().replace("0",""));
          ///  unSortOutService.addTrad(unSortOut);
        }
    }
}
