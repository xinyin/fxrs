package com.yzj.jep.app.fPaas.server;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.fPaaS1.core.IFPaaS1Handler;
import com.fPaaS1.core.Message;
import com.yzj.jep.app.base.constant.AccumulatType;
import com.yzj.jep.app.base.constant.IsStatusDict;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.contract.model.Anchor;
import com.yzj.jep.app.contract.model.AnchorRel;
import com.yzj.jep.app.holder.SpringHolder;
import com.yzj.jep.app.ins.model.*;
import com.yzj.jep.app.ins.service.*;
import com.yzj.jep.app.util.BatchNumberUtil;
import com.yzj.jep.app.util.InsUtil;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.web.entity.BasicNameValuePair;
import com.yzj.jep.web.entity.NameValuePair;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.*;

/**
 *
 * 关联交易比例监测接口
 */
public class ProportionalMonitoringHandler implements IFPaaS1Handler<Message> {
    private static final Logger logger = LoggerFactory.getLogger(com.fpaas.handl.GatewayServerHandler.class);

    private TraInterestService traInterestService;
    private TraFundsService traFundsService;
    private TraBusinessService traBusinessService;
    private TraInvestmentService traInvestmentService;
    private TraServiceService traServiceService;
    private DictItemService dictItemService;
    private TraOtherService traOtherService;
    private RelPsnService relPsnService;
    private RelOrgService relOrgService;
    private BaseService baseService;
    RelDistinguishHandler relDistinguishHandler = new RelDistinguishHandler();

    @Override
    public Message handle(Message message) throws Exception {
        dictItemService = SpringHolder.getBean(DictItemService.class);
        traInvestmentService = SpringHolder.getBean(TraInvestmentService.class);
        traFundsService = SpringHolder.getBean(TraFundsService.class);
        traBusinessService = SpringHolder.getBean(TraBusinessService.class);
        traInterestService = SpringHolder.getBean(TraInterestService.class);
        traServiceService = SpringHolder.getBean(TraServiceService.class);
        traOtherService = SpringHolder.getBean(TraOtherService.class);
        baseService = SpringHolder.getBean(BaseService.class);
        Map tips = new HashMap<String,Object>();
        try{
        Map map = (Map) message.getTarget().getBodys();
        JSONObject data = new JSONObject(map);
        //返回锚点信息集
        List<Anchor> anchorList = new ArrayList<>();
        //关联交易范围(0：只有资产关联交易，1：只有委托机构关联交易，2：资产及委托机构关联交易都有)
        String relTranRange = null;
        Set<String> relTranRanges = new HashSet();
        //是否超过监管上限比例（0:否，1:是），当anchorType为M0时（属于资产），该项必填
        String isOverrun = "0";
        //超监管比例原因简述，当isOverrun为1时（超过监管比例），该项必填
        String overrunInf = null;
        //获取
        Map<String, String> overLimit = InsUtil.isOverLimit(data);
        String limitCode = overLimit.get("limitCode");
        String limitMsg = overLimit.get("limitMsg");
        if (limitCode.equals("0")) {
            isOverrun = "1";
            overrunInf = limitMsg;
        }
        //是否重大
        String relTranCategory = InsUtil.checkGreatCheck(data).get("isGreat");
        //重大情形
            String majorType = InsUtil.checkGreatCheck(data).get("majorType");
        //参数锚点信息集
        String strdataAnchorList = data.getString("anchorList");
        List<Anchor> dataAnchorList = JSONObject.parseArray(strdataAnchorList,Anchor.class);
        for (Anchor dataAnchor:dataAnchorList) {
            //关联信息集
            List<AnchorRel> dataAnchorRelList = dataAnchor.getRelList();
            //锚点类型
            String anchorType = dataAnchor.getAnchorType();
            relTranRanges.add(anchorType);
            for (AnchorRel dataAnchorRel:dataAnchorRelList) {
                //关联方类型
                dataAnchorRel.setRelType("1".equals(dataAnchorRel.getRelType()) ? "PSN":"ORG");
                //返回锚点关联方信息集
                List<AnchorRel> relList = new ArrayList<>();
                //关联交易类型
                String relTranType = dataAnchorRel.getRelTranType();
                //投资入股类
                if ("01".equals(relTranType)){
                    TraInvestment traInvestment = new TraInvestment();
                    //交易主题名称
                    traInvestment.setTraSubName(IdToMgerOrg(dataAnchor.getUscCode()));
                    //合同编号
                    traInvestment.setContractNo((String) map.get("contractNo"));
                    //traInvestment.setContractAmount(new BigDecimal(map.get("contAmt").toString()));
                    traInvestment.setRelType(dataAnchorRel.getRelType());
                    traInvestment.setRelName(dataAnchorRel.getRelName());
                    traInvestment.setRelNumber(dataAnchorRel.getUscCode());
                    //修改时间
                    traInvestment.setModifiedTime(new Date());
                    //获取交易对手pid
                    Integer pid = getPid(dataAnchorRel.getRelType(), dataAnchorRel.getUscType(), dataAnchorRel.getUscCode(),IdToMgerOrg(dataAnchor.getUscCode()));
                    if (null != pid){
                        traInvestment.setRelId(pid);
                        //累计交易金额
                        traInvestment.setTotolTradeAmount(baseService.getCurrYearTotalTradeAmount(pid, dataAnchorRel.getRelType(), IdToMgerOrg(dataAnchor.getUscCode()), AccumulatType.ZERO_NO.getHandler(), AccumulatType.COUNT_ALL.getHandler()));
                    }
                    traInvestment.setDataSources("3");
                    //交易价格
                    traInvestment.setContractAmount(new BigDecimal(dataAnchorRel.getRelTranAmt()));
                    //交易类型
                    traInvestment.setTranType("01");
                    //是否重大关联交易
                    traInvestment.setMajorTrade("1");
                    if ("M0".equals(anchorType)) {
                        String MajorTrade = relTranCategory == "0" ? "1": "0";
                        traInvestment.setMajorTrade(MajorTrade);
                        if ("0".equals(MajorTrade)){
                            traInvestment.setMajorType(majorType);
                        }
                        dataAnchorRel.setRelTranCategory(relTranCategory);
                    }
                    traInvestment.setStatus("1");
                    traInvestment.setInvestmentType("");
                    traInvestment.setAmendId("contract");
                    traInvestment.setDelflag(IsStatusDict.N.getValue());
                    //交易编号
                    traInvestment.setTransNum(BatchNumberUtil.makeBatchNumber());
                    traInvestment.setIsDealPrice("1");
                    traInvestment.setIsCashSubscript("1");
                    if("0".equals(isOverrun)) {
                        traInvestmentService.insert(traInvestment);
                    }
                }if ("02".equals(relTranType)){
                    //资金运用类
                    TraFunds traFunds = new TraFunds();
                    //交易主题名称
                    traFunds.setTraSubName(IdToMgerOrg(dataAnchor.getUscCode()));
                    //合同编号
                    traFunds.setContractNo((String) map.get("contractNo"));
                      //traFunds.setContractAmount(new BigDecimal((String) map.get("contAmt").toString()));
                    traFunds.setRelType(dataAnchorRel.getRelType());
                    traFunds.setRelName(dataAnchorRel.getRelName());
                    traFunds.setRelNumber(dataAnchorRel.getUscCode());
                    //修改时间
                    traFunds.setModifiedTime(new Date());
                    //获取交易对手pid
                    Integer pid = getPid(dataAnchorRel.getRelType(), dataAnchorRel.getUscType(), dataAnchorRel.getUscCode(),IdToMgerOrg(dataAnchor.getUscCode()));
                    if (null != pid){
                        traFunds.setRelId(pid);
                        //累计交易金额
                        traFunds.setTotolTradeAmount(baseService.getCurrYearTotalTradeAmount(pid, dataAnchorRel.getRelType(), IdToMgerOrg(dataAnchor.getUscCode()), AccumulatType.ZERO_NO.getHandler(), AccumulatType.COUNT_ALL.getHandler()));
                    }
                    traFunds.setDataSources("3");
                    //交易价格
                    traFunds.setContractAmount(new BigDecimal(dataAnchorRel.getRelTranAmt()));
                    //交易类型
                    traFunds.setTranType("02");
                    //是否重大关联交易
                    traFunds.setMajorTrade("1");
                    if ("M0".equals(anchorType)) {
                        String MajorTrade = relTranCategory == "0" ? "1": "0";
                        traFunds.setMajorTrade(MajorTrade);
                        if ("0".equals(MajorTrade)){
                            traFunds.setMajorType(majorType);
                        }
                        dataAnchorRel.setRelTranCategory(relTranCategory);
                    }
                    traFunds.setStatus("1");
                    traFunds.setInvestmentType("");
                    traFunds.setAmendId("contract");
                    traFunds.setDelflag(IsStatusDict.N.getValue());
                    //交易编号
                    traFunds.setTransNum(BatchNumberUtil.makeBatchNumber());
                    traFunds.setIsDealPrice("1");
                    traFunds.setIsCashSubscription("1");
                    if("0".equals(isOverrun)) {
                        traFundsService.insert(traFunds);
                    }
                }if ("03".equals(relTranType)){
                    //保险业务类
                    TraBusiness traBusiness = new TraBusiness();
                    //交易主题名称
                    traBusiness.setTraSubName(IdToMgerOrg(dataAnchor.getUscCode()));
                    //合同编号
                    traBusiness.setContractNo((String) map.get("contractNo"));
                   // traBusiness.setContractAmount(new BigDecimal((String) map.get("contAmt").toString()));
                    traBusiness.setRelType(dataAnchorRel.getRelType());
                    traBusiness.setRelName(dataAnchorRel.getRelName());
                    traBusiness.setRelNumber(dataAnchorRel.getUscCode());
                    //修改时间
                    traBusiness.setModifiedTime(new Date());
                    //获取交易对手pid
                    Integer pid = getPid(dataAnchorRel.getRelType(), dataAnchorRel.getUscType(), dataAnchorRel.getUscCode(),IdToMgerOrg(dataAnchor.getUscCode()));
                    if (null != pid){
                        traBusiness.setRelId(pid);
                        //累计交易金额
                        traBusiness.setTotolTradeAmount(baseService.getCurrYearTotalTradeAmount(pid, dataAnchorRel.getRelType(), IdToMgerOrg(dataAnchor.getUscCode()), AccumulatType.ZERO_NO.getHandler(), AccumulatType.COUNT_ALL.getHandler()));
                    }
                    traBusiness.setDataSources("3");
                    //交易价格
                    traBusiness.setContractAmount(new BigDecimal(dataAnchorRel.getRelTranAmt()));
                    //交易类型
                    traBusiness.setTranType("03");
                    //是否重大关联交易
                    traBusiness.setMajorTrade("1");
                    if ("M0".equals(anchorType)) {
                        String MajorTrade = relTranCategory == "0" ? "1": "0";
                        traBusiness.setMajorTrade(MajorTrade);
                        if ("0".equals(MajorTrade)){
                            traBusiness.setMajorType(majorType);
                        }
                        dataAnchorRel.setRelTranCategory(relTranCategory);
                    }
                    traBusiness.setStatus("1");
                    traBusiness.setBusType("");
                    traBusiness.setAmendId("contract");
                    traBusiness.setDelflag(IsStatusDict.N.getValue());
                    //交易编号
                    traBusiness.setTransNum(BatchNumberUtil.makeBatchNumber());
                    traBusiness.setIsDealPrice("1");
                    traBusiness.setIsCashSubscript("1");
                    if("0".equals(isOverrun)) {
                        traBusinessService.insert(traBusiness);
                    }
                }if ("04".equals(relTranType)){
                    //利益转移类
                    TraInterest trainterest = new TraInterest();
                    //交易主题名称
                    trainterest.setTraSubName(IdToMgerOrg(dataAnchor.getUscCode()));
                    //合同编号
                    trainterest.setContractNo((String) map.get("contractNo"));
                    //trainterest.setContractAmount(new BigDecimal((String) map.get("contAmt").toString()));
                    trainterest.setRelType(dataAnchorRel.getRelType());
                    trainterest.setRelName(dataAnchorRel.getRelName());
                    trainterest.setRelNumber(dataAnchorRel.getUscCode());
                    //修改时间
                    trainterest.setModifiedTime(new Date());
                    //获取交易对手pid
                    Integer pid = getPid(dataAnchorRel.getRelType(), dataAnchorRel.getUscType(), dataAnchorRel.getUscCode(),IdToMgerOrg(dataAnchor.getUscCode()));
                    if (null != pid){
                        trainterest.setRelId(pid);
                        //累计交易金额
                        trainterest.setTotolTradeAmount(baseService.getCurrYearTotalTradeAmount(pid, dataAnchorRel.getRelType(), IdToMgerOrg(dataAnchor.getUscCode()), AccumulatType.ZERO_NO.getHandler(), AccumulatType.COUNT_ALL.getHandler()));
                    }
                    trainterest.setDataSources("3");
                    //交易价格
                    trainterest.setContractAmount(new BigDecimal(dataAnchorRel.getRelTranAmt()));
                    //交易类型
                    trainterest.setTranType("04");
                    //是否重大关联交易
                    trainterest.setMajorTrade("1");
                    if ("M0".equals(anchorType)) {
                        String MajorTrade = relTranCategory == "0" ? "1": "0";
                        trainterest.setMajorTrade(MajorTrade);
                        if ("0".equals(MajorTrade)){
                            trainterest.setMajorType(majorType);
                        }
                        dataAnchorRel.setRelTranCategory(relTranCategory);
                    }
                    trainterest.setStatus("1");
                    trainterest.setBusType("");
                    trainterest.setAmendId("contract");
                    trainterest.setDelflag(IsStatusDict.N.getValue());
                    //交易编号
                    trainterest.setTransNum(BatchNumberUtil.makeBatchNumber());
                    trainterest.setIsDealPrice("1");
                    trainterest.setIsCashSubscription("1");
                    if("0".equals(isOverrun)) {
                        traInterestService.insert(trainterest);
                    }
                }if ("05".equals(relTranType)){
                    //提供服务类
                    TraService traService = new TraService();
                    //交易主题名称
                    traService.setTraSubName(IdToMgerOrg(dataAnchor.getUscCode()));
                    //合同编号
                    traService.setContractNo((String) map.get("contractNo"));
                    //traService.setContractAmount(new BigDecimal((String) map.get("contAmt").toString()));
                    traService.setRelType(dataAnchorRel.getRelType());
                    traService.setRelName(dataAnchorRel.getRelName());
                    traService.setRelNumber(dataAnchorRel.getUscCode());
                    //修改时间
                    traService.setModifiedTime(new Date());
                    //获取交易对手pid
                    Integer pid = getPid(dataAnchorRel.getRelType(), dataAnchorRel.getUscType(), dataAnchorRel.getUscCode(),IdToMgerOrg(dataAnchor.getUscCode()));
                    if (null != pid){
                        traService.setRelId(pid);
                        //累计交易金额
                        traService.setTotolTradeAmount(baseService.getCurrYearTotalTradeAmount(pid, dataAnchorRel.getRelType(), IdToMgerOrg(dataAnchor.getUscCode()), AccumulatType.ZERO_NO.getHandler(), AccumulatType.COUNT_ALL.getHandler()));
                    }
                    traService.setDataSources("3");
                    //交易价格
                    traService.setContractAmount(new BigDecimal(dataAnchorRel.getRelTranAmt()));
                    //交易类型
                    traService.setTranType("05");
                    //是否重大关联交易
                    traService.setMajorTrade("1");
                    if ("M0".equals(anchorType)) {
                        String MajorTrade = relTranCategory == "0" ? "1": "0";
                        traService.setMajorTrade(MajorTrade);
                        if ("0".equals(MajorTrade)){
                            traService.setMajorType(majorType);
                        }
                        dataAnchorRel.setRelTranCategory(relTranCategory);
                    }
                    traService.setStatus("1");
                    traService.setBusType("");
                    traService.setAmendId("contract");
                    traService.setDelflag(IsStatusDict.N.getValue());
                    //交易编号
                    traService.setTransNum(BatchNumberUtil.makeBatchNumber());
                    //默认国家定价现金交易为否
                    traService.setIsDealPrice("1");
                    traService.setIscashsubscribe("1");
                    if("0".equals(isOverrun)) {
                        traServiceService.insert(traService);
                    }
                }if("06".equals(relTranType)){
                    TraOther traOther = new TraOther();
                    //交易主题名称
                    traOther.setTraSubName(IdToMgerOrg(dataAnchor.getUscCode()));
                    //合同编号
                    traOther.setContractNo((String) map.get("contractNo"));
                    //traOther.setContractAmount(new BigDecimal((String) map.get("contAmt").toString()));
                    traOther.setRelType(dataAnchorRel.getRelType());
                    traOther.setRelName(dataAnchorRel.getRelName());
                    traOther.setRelNumber(dataAnchorRel.getUscCode());
                    //修改时间
                    traOther.setModifiedTime(new Date());
                    //获取交易对手pid
                    Integer pid = getPid(dataAnchorRel.getRelType(), dataAnchorRel.getUscType(), dataAnchorRel.getUscCode(),IdToMgerOrg(dataAnchor.getUscCode()));
                    if (null != pid){
                        traOther.setRelId(pid);
                        //累计交易金额
                        traOther.setTotolTradeAmount(baseService.getCurrYearTotalTradeAmount(pid, dataAnchorRel.getRelType(), IdToMgerOrg(dataAnchor.getUscCode()), AccumulatType.ZERO_NO.getHandler(), AccumulatType.COUNT_ALL.getHandler()));
                    }
                    traOther.setDataSources("3");
                    //交易价格
                    traOther.setContractAmount(new BigDecimal(dataAnchorRel.getRelTranAmt()));
                    //交易类型
                    traOther.setTranType("06");
                    //是否重大关联交易
                    traOther.setMajorTrade("1");
                    if ("M0".equals(anchorType)) {
                        String MajorTrade = relTranCategory == "0" ? "1": "0";
                        traOther.setMajorTrade(MajorTrade);
                        if ("0".equals(MajorTrade)){
                            traOther.setMajorType(majorType);
                        }
                        dataAnchorRel.setRelTranCategory(relTranCategory);
                    }
                    traOther.setStatus("1");
                    traOther.setBusType("");
                    traOther.setAmendId("contract");
                    traOther.setDelflag(IsStatusDict.N.getValue());
                    //交易编号
                    traOther.setTransNum(BatchNumberUtil.makeBatchNumber());
                    traOther.setIsDealPrice("1");
                    traOther.setIsCashSubscribe("1");
                    if("0".equals(isOverrun)) {
                        traOtherService.insert(traOther);
                    }

                }
                relList.add(dataAnchorRel);
            }
            //是否超过监管上限比例
            if ("M0".equals(anchorType)){
                //关联交易类别（是否重大）
                dataAnchor.setRelTranCategory(relTranCategory);
                dataAnchor.setIsOverrun(isOverrun);
            }if ("1".equals(isOverrun)){
                dataAnchor.setOverrunInf(overrunInf);
            }
            anchorList.add(dataAnchor);
        }
        //关联交易范围
       if (relTranRanges.size()>1){
           relTranRange = "2";
       }else {
           relTranRanges.add("M0");
           relTranRange = relTranRanges.size()== 1 ? "0" : "1";
       }
        tips.put("CHNL_ID",map.get("chnlId"));
        tips.put("TRANS_CODE","ProportionalMonitoring");
       tips.put("contractNo",map.get("contractNo"));
       tips.put("RelTranRange",relTranRange);
       tips.put("anchorList",anchorList);
        tips.put("resultCode","0000");
        tips.put("msg","成功");
        }catch (NullPointerException e){
            tips.put("resultCode","1001");
            tips.put("msg","失败");
        }catch (JSONException e){
            tips.put("resultCode","1001");
            tips.put("msg","失败");
        }catch (Exception e){
            tips.put("resultCode","9999");
            tips.put("msg","失败");
        }finally {
            message.getTarget().setBody(tips);
            return message;
        }
    }


    public String IdToMgerOrg(String UscCode){
        List<NameValuePair> items = new ArrayList<>();
        Map tips = new HashMap<String,String>();
        try
        {
            List<Filter> filters = new Filter.Builder().eq("dictCode", "BUS-IdToMgerOrg").build();
            List<DictItem> list = dictItemService.select(filters);

            //过滤掉不要的字典项
            if(!CollectionUtils.isEmpty(list))
            {
                for(int i = 0; i < list.size(); i++)
                {
                    DictItem item = list.get(i);
                    if(org.springframework.util.StringUtils.hasText("") && "".contains(item.getItemValue())) {
                        continue;
                    }
                    BasicNameValuePair nv = new BasicNameValuePair(item.getItemLabel(), item.getItemValue());
                    items.add(nv);
                }
            }
            for (NameValuePair nameValuePair:items) {
                tips.put(nameValuePair.getValue(),nameValuePair.getName());
            }
            return (String) tips.get(UscCode);
        } catch (ServiceException se) {
            logger.error("系统出现异常：" + se.getMessage());
            throw se;
        }
    }

    public Integer getPid(String relType ,String uscType, String relcode,String mgerOrg){
        relPsnService = SpringContextHolder.getBean(RelPsnService.class);
        relOrgService = SpringContextHolder.getBean(RelOrgService.class);
        Integer pid  = null ;
        if("PSN".equals(relType)){
            RelPsn psn = new RelPsn();
            psn.setIdentifyType(uscType);
            psn.setIdentifyNumber(relcode);
            psn.setMgerOrg(mgerOrg);
            RelPsn psn1 = relPsnService.get(psn);
            pid = psn1.getPid();
        }else {
            RelOrg relOrg = new RelOrg();
            relOrg.setIdentifyType(uscType);
            relOrg.setUniscCode(relcode);
            relOrg.setMgerOrg(mgerOrg);
            RelOrg relOrg1 = relOrgService.get(relOrg);
            pid = relOrg1.getPid();
        }
        return  pid ;
    }


}
