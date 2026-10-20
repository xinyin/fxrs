package com.yzj.jep.app.mid.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelProService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.bus.service.RelService;
import com.yzj.jep.app.fil.model.*;
import com.yzj.jep.app.fil.service.*;
import com.yzj.jep.app.ins.model.TraInsenclosure;
import com.yzj.jep.app.ins.service.TraInsenclosureService;
import com.yzj.jep.app.mid.model.BusProc;
import com.yzj.jep.app.mid.service.BusProcService;
import com.yzj.jep.app.workflow.service.GetBusProcService;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.sys.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GetBusProcServiceImpl implements GetBusProcService {
    @Autowired
    private BusProcService busProcService;

    @Autowired
    private FilRelPsnService filRelPsnService;

    @Autowired
    private FilRelOrgService filRelOrgService;

    @Autowired
    private FilInvestmentService filInvestmentService;

    @Autowired
    private FilFundsService filFundsService;

    @Autowired
    private FilBusinessService filBusinessService;

    @Autowired
    private FilInterestService filInterestService;

    @Autowired
    private FilServiceService filServiceService;

    @Autowired
    private FilOtherService filOtherService;

    @Autowired
    private TraInsenclosureService traInsenclosureService;

    @Autowired
    StaffService staffService;

    @Autowired
    DictItemService dictItemService;

    @Autowired
    private RelService  relService;

    @Autowired
    private RelPsnService relPsnService;

    @Autowired
    private RelOrgService relOrgService;

    @Autowired
    private RelProService relProService;

    private static final HashMap<String,String> Type = new HashMap<>();
    private static final HashMap<String,String> DictType = new HashMap<>();

    static{
        Type.put("investment","01");//投资入股类
        Type.put("funds","02");//资金运用类
        Type.put("business","03");//保险业务类
        Type.put("interest","04");//利益转移类
        Type.put("service","05");//提供服务类
        Type.put("other","06");//其他类
        Type.put("relOrg","1");//法人或其他组织
        Type.put("relPsn","2");//自然人

        DictType.put("pricingPolicy","INS-Pricingpolicy");//定价政策
        DictType.put("considerType","INS-considerType");//审议类型
        DictType.put("businessType","INS-InvMethod01");//业务类型
        DictType.put("businessTypeF","INS-InvestmentType");//资金运用：业务类型
        DictType.put("tranType","INS-tranType");//交易类型
        DictType.put("securitiesType","INS-SecuritiesType");//定价策略
        DictType.put("unitfTradeAgrmentAbusiness","INT-InsTradeBusiness");//统一交易协议业务情况
        DictType.put("busType","INT-InsBusinessType");//业务类型
        DictType.put("busTypeI","INT-BusinessType");//利益转移:业务类型
        DictType.put("unitfTradeAgrmentInterest","INT-InsTradeBusiness");//利益转移：统一交易协议业务情况
        DictType.put("busTypeS","INS-ServiceType");//保险业务：业务类型
        DictType.put("investmentMethod","INS-Inv_method");//保险业务：业务类型
        DictType.put("identifyType","INS_PsnStatus");//关联方：证件类型
        DictType.put("nationality","BUS_nationality");//关联方：国籍
        DictType.put("predicate","BUS_Predicate");//关联方：关系基于类别
        DictType.put("relCategorySub","BUS-Rel");//关联方：关系
       // DictType.put("pricingPolicy","INS-Pricingpolicy");


    }


    @Override
    public Map<String, Object> selectByProcInstId(String procInstId) {
        Map<String,Object> tips = new HashMap<>();
        List<Filter> filters = new Filter.Builder().eq("procInstId",procInstId).build();
        List<BusProc> li=busProcService.select(filters);
        if(li.size() == 0){
            tips.put("count","0");
            return tips;
        }
        String FilPid=li.get(0).getFilPid();
        String tenantId = li.get(0).getTenantId();
        if("relPsn".equals(li.get(0).getBusType())){
            List<Filter> filters1 = new Filter.Builder().eq("pid",FilPid).eq("tenantId",tenantId).build();
            List<FilRelPsn> ll=filRelPsnService.select(filters1);
            tips.put("name",ll.get(0).getName());
        }else if("relOrg".equals(li.get(0).getBusType())){
            List<Filter> filters1 = new Filter.Builder().eq("pid",FilPid).eq("tenantId",tenantId).build();
            List<FilRelOrg> ll=filRelOrgService.select(filters1);
            tips.put("name",ll.get(0).getOrgName());
        }else if("investment".equals(li.get(0).getBusType())){
            List<Filter> filters1 = new Filter.Builder().eq("pid",FilPid).eq("tenantId",tenantId).build();
            List<FilInvestment> ll=filInvestmentService.select(filters1);
            tips.put("name",ll.get(0).getRelName());
            tips.put("agreementAmount",ll.get(0).getApproveTradeAmount());
        }else if("funds".equals(li.get(0).getBusType())){
            List<Filter> filters1 = new Filter.Builder().eq("pid",FilPid).eq("tenantId",tenantId).build();
            List<FilFunds> ll=filFundsService.select(filters1);
            tips.put("name",ll.get(0).getRelName());
            tips.put("agreementAmount",ll.get(0).getApproveTradeAmount());
        }else if("business".equals(li.get(0).getBusType())){
            List<Filter> filters1 = new Filter.Builder().eq("pid",FilPid).eq("tenantId",tenantId).build();
            List<FilBusiness> ll=filBusinessService.select(filters1);
            tips.put("name",ll.get(0).getRelName());
            tips.put("agreementAmount",ll.get(0).getApproveTradeAmount());
        }else if("interest".equals(li.get(0).getBusType())){
            List<Filter> filters1 = new Filter.Builder().eq("pid",FilPid).eq("tenantId",tenantId).build();
            List<FilInterest> ll=filInterestService.select(filters1);
            tips.put("name",ll.get(0).getRelName());
            tips.put("agreementAmount",ll.get(0).getApproveTradeAmount());
        }else if("service".equals(li.get(0).getBusType())){
            List<Filter> filters1 = new Filter.Builder().eq("pid",FilPid).eq("tenantId",tenantId).build();
            List<FilService> ll=filServiceService.select(filters1);
            tips.put("name",ll.get(0).getRelName());
            tips.put("agreementAmount",ll.get(0).getApproveTradeAmount());
        }else if("other".equals(li.get(0).getBusType())){
            List<Filter> filters1 = new Filter.Builder().eq("pid",FilPid).eq("tenantId",tenantId).build();
            try {
                List<FilOther> ll=filOtherService.select(filters1);
                tips.put("name",ll.get(0).getRelName());
                tips.put("agreementAmount",ll.get(0).getApproveTradeAmount());
            }catch (Exception ex){
                tips.put("name","-");
                tips.put("agreementAmount","-");
            }

        }
        if(li.size() == 0){
            tips.put("msg",false);
        }else{
            tips.put("count",li.size());
            tips.put("msg",true);
            tips.put("busProc",li.get(0));
            tips.put("busType",Type.get(li.get(0).getBusType()));
        }
        return tips;
    }

    @Override
    public List<Map<String,String>> selectPathBytransNum(String transNum,String url,String tenantId) {
        List<Map<String,String>> li = new ArrayList<>();
        List<Filter> filters = new Filter.Builder().eq("transNum", transNum).eq("delflag","1").eq("tenantId",tenantId).build();
        List<TraInsenclosure> insenclosures = traInsenclosureService.select(filters);
        if(0 == insenclosures.size()){return li;}
        for (TraInsenclosure t:insenclosures) {
            Map<String,String> tips = new HashMap<>();
            tips.put("path","/ins/traInsenclosure/previewyd?pid="+t.getPid()+"&tenantId="+tenantId);
            tips.put("staffName",staffService.get(t.getCreateById()).getStaffName());
            tips.put("deptName",staffService.get(t.getCreateById()).getDeptName());
            tips.put("tenantId",staffService.get(t.getCreateById()).getTenantId());
            tips.put("createTime",t.getUploadTime());
            tips.put("relatedName",t.getRelatedName());
            li.add(tips);
        }
        return li;
    }

    @Override
    public String selectItemByDictCode(String dictCode, String itemValue) {
        List<Filter> filters = new Filter.Builder().eq("dictCode", DictType.get(dictCode)).eq("itemValue",itemValue).build();
        List<DictItem> dictItem = dictItemService.select(filters);
        if(0 != dictItem.size()){
        return dictItem.get(0).getItemLabel();
        }else{
            return null;
        }
    }

    @Override
    public List<JSONObject> selectRelByPid(Integer pid,String tenantId) {
        List<JSONObject> li=new ArrayList<>();
        List<Filter> filters = new Filter.Builder().eq("toPid",pid).build();
        List<Rel> liRel = relService.select(filters);
        for (Rel r:liRel) {
            if("PSN".equals(r.getFromType())){
                RelPsnVo relPsnVo = new RelPsnVo();
                List<Filter> filters2 = new Filter.Builder().eq("pid",r.getFromPid()).build();
                RelPsn psn = relPsnService.get(filters2);
                if(null == psn) {continue;}
                relPsnVo.setName(psn.getName());
                relPsnVo.setIdentifyNumber(psn.getIdentifyNumber());
                relPsnVo.setRelCategorySub(selectItemByDictCode("relCategorySub",r.getRelCategorySub()));
                relPsnVo.setPredicate(selectItemByDictCode("predicate",r.getRelPredicate()));
                relPsnVo.setShareholdingRatio(r.getShareholdingRatio());
                relPsnVo.setActiveTime(r.getActiveTime());
                relPsnVo.setInvalidTime(r.getInvalidTime());
                relPsnVo.setRelStatement(r.getRelStatement());
                String json = JSONObject.toJSONString(relPsnVo);
                JSONObject jsonObject = JSONObject.parseObject(json);
                jsonObject.put("type","PSN");
                jsonObject.put("isShow",true);
                li.add(jsonObject);
            }else if("ORG".equals(r.getFromType())){
                RelOrgVo relOrgVo = new RelOrgVo();
                List<Filter> filters2 = new Filter.Builder().eq("pid",r.getFromPid()).build();
                RelOrg org = relOrgService.get(filters2);
                if(null == org) {continue;}
                relOrgVo.setOrgName(org.getOrgName());
                relOrgVo.setUniscCode(org.getUniscCode());
                relOrgVo.setRelCategorySub(selectItemByDictCode("relCategorySub",r.getRelCategorySub()));
                relOrgVo.setPredicate(selectItemByDictCode("predicate",r.getRelPredicate()));
                relOrgVo.setShareholdingRatio(r.getShareholdingRatio());
                relOrgVo.setActiveTime(r.getActiveTime());
                relOrgVo.setInvalidTime(r.getInvalidTime());
                relOrgVo.setRelStatement(r.getRelStatement());
                String json = JSONObject.toJSONString(relOrgVo);
                JSONObject jsonObject = JSONObject.parseObject(json);
                jsonObject.put("type","ORG");
                jsonObject.put("isShow",true);
                li.add(jsonObject);
            }else {
                RelProVo relProVo = new RelProVo();
                List<Filter> filters2 = new Filter.Builder().eq("pid",r.getFromPid()).build();
                RelPro pro = relProService.get(filters2);
                if(null == pro) {continue;}
                relProVo.setProName(pro.getProName());
                relProVo.setProProvider(pro.getProProvider());
                relProVo.setRelCategorySub(selectItemByDictCode("relCategorySub",r.getRelCategorySub()));
                relProVo.setRelPredicate(selectItemByDictCode("predicate",r.getRelPredicate()));
                relProVo.setShareholdingRatio(r.getShareholdingRatio());
                relProVo.setActiveTime(r.getActiveTime());
                relProVo.setInvalidTime(r.getInvalidTime());
                relProVo.setRelStatement(r.getRelStatement());
                String json = JSONObject.toJSONString(relProVo);
                JSONObject jsonObject = JSONObject.parseObject(json);
                jsonObject.put("type","PRO");
                jsonObject.put("isShow",true);
                li.add(jsonObject);
            }
        }
        return li;
    }


}
