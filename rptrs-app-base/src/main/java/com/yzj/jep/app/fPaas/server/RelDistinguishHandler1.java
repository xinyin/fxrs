package com.yzj.jep.app.fPaas.server;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.fPaaS1.core.IFPaaS1Handler;
import com.fPaaS1.core.Message;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelOrgBranch;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.contract.model.RelDistinguishVo;
import com.yzj.jep.app.bus.service.RelOrgBranchService;
import com.yzj.jep.app.holder.SpringHolder;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  关联方识别接口
 * @author wll
 */
public class RelDistinguishHandler1 implements IFPaaS1Handler<Message> {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RelPsnService relPsnService;
    @Autowired
    private RelOrgService relOrgService;
    @Autowired
    private RelOrgBranchService relOrgBranchService;


    @Override
    public Message handle(Message message) throws Exception {
        relOrgService = SpringHolder.getBean(RelOrgService.class);
        relPsnService = SpringHolder.getBean(RelPsnService.class);

        Map tips = new HashMap<String,Object>(16);
        Map map = (Map) message.getTarget().getBodys();

        try {
            JSONObject data = new JSONObject(map);
            //传入参数
            logger.info("=============================>" + data.toString());

            //是否为关联方（0:否，1:是）
//            String isRel = "0";

            String customerList = data.getString("customerList");
            List<RelDistinguishVo> dataRelDistinguishVoList = JSONObject.parseArray(customerList,RelDistinguishVo.class);
            for (RelDistinguishVo RD : dataRelDistinguishVoList) {
                if ("1".equals(RD.getCustomerType())) {
                    RelPsn psn = new RelPsn();
                    psn.setIdentifyType(RD.getUscType());
                    psn.setIdentifyNumber(RD.getUscCode());
                    List<RelPsn> relPsnList = relPsnService.select(psn);
                    if (relPsnList != null && relPsnList.size() > 0) {
                        for (RelPsn psn1 : relPsnList) {
                            RD.setIsRel("1");
                            RD.setRelType(psn1.getRelType());
                            RD.setRelName(psn1.getName());
                            RD.setUscType(psn1.getIdentifyType());
                            RD.setUscCode(psn1.getIdentifyNumber());
                            RD.setCustomerType(psn1.getRelType());
                            RD.setCustomerName(psn1.getName());
                            RD.setUscType(psn1.getIdentifyType());
                            RD.setUscCode(psn1.getIdentifyNumber());
                        }
                    } else {
                        RD.setIsRel("0");
                        RD.setRelType(RD.getCustomerType());
                        RD.setRelName(RD.getCustomerName());
                        RD.setUscType(RD.getUscType());
                        RD.setUscCode(RD.getUscCode());
                    }
                }
                if ("2".equals(RD.getCustomerType())) {
                    RelOrg org = new RelOrg();
                    org.setIdentifyType(RD.getUscType());
                    org.setUniscCode(RD.getUscCode());
                    List<RelOrg> relOrgList = relOrgService.select(org);
                    if (relOrgList != null && relOrgList.size() > 0) {
                        for (RelOrg org1 : relOrgList) {
                            RD.setIsRel("1");
                            RD.setRelType(org1.getRelType());
                            RD.setRelName(org1.getOrgName());
                            RD.setUscType(org1.getIdentifyType());
                            RD.setUscCode(org1.getUniscCode());
                            RD.setCustomerType(org1.getRelType());
                            RD.setCustomerName(org1.getOrgName());
                            RD.setUscType(org1.getIdentifyType());
                            RD.setUscCode(org1.getUniscCode());
                        }
                    }else {
                        RelOrgBranch relOrgBranch = new RelOrgBranch();
                        relOrgBranch.setIdentifyType(RD.getUscType());
                        relOrgBranch.setUniscCode(RD.getUscCode());
                        List<RelOrgBranch> relOrgBranchList = relOrgBranchService.select(relOrgBranch);
                        if (relOrgBranchList.size() > 0){
                            for (RelOrgBranch branch : relOrgBranchList) {
                                RD.setIsRel("0");
                                RD.setRelType("2");
                                RD.setRelName(branch.getOrgName());
                                RD.setUscType(branch.getParentIdentifyType());
                                RD.setUscCode(branch.getParentUniscCode());
                                RD.setCustomerType("2");
                                RD.setCustomerName(branch.getOrgName());
                                RD.setUscType(branch.getIdentifyType());
                                RD.setUscCode(branch.getUniscCode());
                            }
                        }else {
                            RD.setIsRel("0");
                            RD.setRelType(RD.getCustomerType());
                            RD.setRelName(RD.getCustomerName());
                            RD.setUscType(RD.getUscType());
                            RD.setUscCode(RD.getUscCode());
                        }
                    }
                }
            }
            tips.put("CHNL_ID",map.get("chnlId"));
            tips.put("TRANS_CODE","relDistinguish");
            tips.put("resultCode","0000");
            tips.put("msg","成功");
            tips.put("customerList",dataRelDistinguishVoList);
        } catch (NullPointerException e){
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
}
