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
import com.yzj.jep.app.contract.model.Anchor;
import com.yzj.jep.app.contract.model.AnchorRel;
import com.yzj.jep.app.contract.model.RelDistinguishVo;
import com.yzj.jep.app.bus.service.RelOrgBranchService;
import com.yzj.jep.app.holder.SpringHolder;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.web.entity.BasicNameValuePair;
import com.yzj.jep.web.entity.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 关联方识别接口
 * @author
 */
public class RelDistinguishHandler implements IFPaaS1Handler<Message> {
    private static final Logger logger = LoggerFactory.getLogger(com.fpaas.handl.GatewayServerHandler.class);
    private RelPsnService relPsnService;
    private RelOrgService relOrgService;
    @Autowired
    private RelOrgBranchService relOrgBranchService;

    @Override
    public Message handle(Message message) throws Exception {
        relOrgService = SpringHolder.getBean(RelOrgService.class);
        relPsnService = SpringHolder.getBean(RelPsnService.class);
        relOrgBranchService = SpringHolder.getBean(RelOrgBranchService.class);

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
                            RD.setRelType(psn1.getRelType());
                            RD.setRelName(psn1.getName());
                            RD.setRelUscType(psn1.getIdentifyType());
                            RD.setRelUscCode(psn1.getIdentifyNumber());
                            RD.setCustomerType(psn1.getRelType());
                            RD.setCustomerName(psn1.getName());
                            RD.setUscType(psn1.getIdentifyType());
                            RD.setUscCode(psn1.getIdentifyNumber());
                            RD.setIsRel("1");
                        }
                    } else {
                        RD.setCustomerType(RD.getCustomerType());
                        RD.setCustomerName(RD.getCustomerName());
                        RD.setUscType(RD.getUscType());
                        RD.setUscCode(RD.getUscCode());
                        RD.setIsRel("0");
                    }
                }
                if ("2".equals(RD.getCustomerType())) {
                    RelOrg org = new RelOrg();
                    org.setIdentifyType(RD.getUscType());
                    org.setUniscCode(RD.getUscCode());
                    List<RelOrg> relOrgList = relOrgService.select(org);
                    if (relOrgList != null && relOrgList.size() > 0) {
                        for (RelOrg org1 : relOrgList) {
                            RD.setRelType(org1.getRelType());
                            RD.setRelName(org1.getOrgName());
                            RD.setRelUscType(org1.getIdentifyType());
                            RD.setRelUscCode(org1.getUniscCode());
                            RD.setCustomerType(org1.getRelType());
                            RD.setCustomerName(org1.getOrgName());
                            RD.setUscType(org1.getIdentifyType());
                            RD.setUscCode(org1.getUniscCode());
                            RD.setIsRel("1");
                        }
                    }else {
                        RelOrgBranch relOrgBranch = new RelOrgBranch();
                        relOrgBranch.setIdentifyType(RD.getUscType());
                        relOrgBranch.setUniscCode(RD.getUscCode());
                        List<RelOrgBranch> relOrgBranchList = relOrgBranchService.select(relOrgBranch);
                        if (null != relOrgBranchList && relOrgBranchList.size() > 0){
                            for (RelOrgBranch branch : relOrgBranchList) {
                                RD.setRelType("2");
                                RD.setRelName(branch.getOrgName());
                                RD.setRelUscType(branch.getParentIdentifyType());
                                RD.setRelUscCode(branch.getParentUniscCode());
                                RD.setCustomerType("2");
                                RD.setCustomerName(branch.getOrgName());
                                RD.setUscType(branch.getIdentifyType());
                                RD.setUscCode(branch.getUniscCode());
                                RD.setIsRel("1");
                            }
                        } else {
                            RD.setCustomerType(RD.getCustomerType());
                            RD.setCustomerName(RD.getCustomerName());
                            RD.setUscType(RD.getUscType());
                            RD.setUscCode(RD.getUscCode());
                            RD.setIsRel("0");
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


