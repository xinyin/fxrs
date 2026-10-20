package com.yzj.jep.app.contract.web.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.fPaaS1.core.IFPaaS1Handler;
import com.yzj.jep.app.base.utils.Configs;
import com.yzj.jep.app.bus.model.Rel;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelOrgBranch;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.contract.model.*;
import com.yzj.jep.app.contract.service.FileContractService;
import com.yzj.jep.app.contract.service.InformationAutoService;
import com.yzj.jep.app.bus.service.RelOrgBranchService;
import com.yzj.jep.app.contract.util.ContractUtil;
import com.yzj.jep.app.contract.util.HttpClientUtil;
import com.yzj.jep.app.ins.model.*;
import com.yzj.jep.app.ins.service.*;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.AppConfigService;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.sys.service.UnitService;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.entity.BasicNameValuePair;
import com.yzj.jep.web.entity.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Controller
@RequestMapping("/contract")
public class RelDistinguishController extends CrudController<Rel> {
    Logger logger = LoggerFactory.getLogger(RelDistinguishController.class);

    @Autowired
    private RelPsnService relPsnService;
    @Autowired
    private RelOrgService relOrgService;
    @Autowired
    private UnitService unitService;
    @Autowired
    private DictItemService dictItemService;
    @Autowired
    private TraFundsService traFundsService;
    @Autowired
    private TraServiceService traServiceService;
    @Autowired
    private TraInterestService traInterestService;
    @Autowired
    private TraBusinessService traBusinessService;
    @Autowired
    private TraInvestmentService traInvestmentService;
    @Autowired
    private IFPaaS1Handler ContractGatewayClientHandler;
    @Autowired
    private Configs config;
    @Autowired
    private FileContractService fileContractService;
    @Autowired
    private RelOrgBranchService relOrgBranchService;
    @Autowired
    InformationAutoService InformationAutoService;

    private static AppConfigService appConfigService = SpringContextHolder.getBean(AppConfigService.class);

    /**
     * 调用合同关联文件获取接口
     */
    @ResponseBody
    @RequestMapping(value = "/system/api", method = RequestMethod.POST)
    public void getContractFiles2Relate(@RequestBody Map<String, String> requestMap) {
        String directory = null;
        try {
            if (requestMap == null) {
                return;
            }
            String contractNo = requestMap.get("contractNo");
            String appId = appConfigService.getConfigValue("channel_app_id");
            String requestUrl = appConfigService.getConfigValue("channel_Contract_Files");
            String secretkey = appConfigService.getConfigValue("channel_contract_secretkey");

            String nonce = ContractUtil.getUUID();
            long timestamp = ContractUtil.getTimeStamp();
            //请求参数类
            ApiRequest request = new ApiRequest();
            //构建业务请求数据
            JSONObject busMsg = new JSONObject();
            busMsg.put("contractNo", contractNo);
            request.setAppId(appId);
            request.setNonce(nonce);
            request.setData(busMsg);
            request.setTimestamp(timestamp);
            //获取签名数据
            String sign = ContractUtil.getSign(secretkey, nonce, JSON.toJSONString(busMsg));
            request.setSig(sign);
            //获取返回信息
            Map<String, String> map = HttpClientUtil.httpPostByte(JSON.toJSONString(request), requestUrl, config.getTemp());
            if ("SUCCESS".equals(map.get("code"))) {
                String path = map.get("path");
                String name = map.get("name");
                ContractUtil.unZipFile(path, name);
                //根据抓取到的文件更新数据库
                fileContractService.save(contractNo, path, name);
                //zip文件路径
                directory = path + File.separator + name + ".zip";
            }
        } catch (Exception e) {
            logger.info("failed to call the signed contract information list interface " +
                    "to get the relevant information:{}", e);
        } finally {
            //删除zip文件
            if (directory != null) {
                File dirFile = new File(directory);
                if (dirFile.exists()) {
                    ContractUtil.delete(dirFile);
                }
            }
        }
    }


    @RequestMapping(value = "/relDistinguish1", method = RequestMethod.POST)
    public JSONObject relDistinguish1(@RequestBody String data1){
        Map tips = new HashMap<String, Object>(16);
        try {
            JSONObject data = JSONObject.parseObject(data1);
            String customerList = data.getString("customerList");
            //传入参数
            logger.info("=============================>" + data.toString());
//            Map result = new HashMap<String,Object>();
            //是否为关联方（0:否，1:是）
//            String isRel = "0";
//            List<RelDistinguishVo> relDistinguishVoList = new ArrayList<>();
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
                    }else{
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
            tips.put("customerList",dataRelDistinguishVoList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            String x = JSONObject.toJSONString(tips);
            JSONObject y = JSONObject.parseObject(x);
            return y;
        }
    }



    @RequestMapping(value = "/relDistinguish", method = RequestMethod.POST)
    public JSONObject relDistinguish(@RequestBody String data1) {
        Map tips = new HashMap<String, Object>();
        try {
            JSONObject data = JSONObject.parseObject(data1);
            Map result = new HashMap<String, Object>();
            //合同编号
            String contractNo = data.getString("contractNo");
            //是否关联交易（0:否，1:是）
            String isRelTran = "0";
            //锚点信息集
            List<Anchor> anchorList = new ArrayList<>();
            //从参数中取出锚点信息集
            String strdataAnchorList = data.getString("anchorList");
            List<Anchor> dataAnchorList = JSONObject.parseArray(strdataAnchorList, Anchor.class);
            //从参数中取出其他合同主体信息集
            String strdatacontractList = data.getString("contractList");
            List<Anchor> datacontractList = JSONObject.parseArray(strdatacontractList, Anchor.class);
            for (Anchor dataAnchor : dataAnchorList) {
                //锚点关联方信息集
                List<AnchorRel> relist = new ArrayList<>();
                //判断其他合同主体
                for (Anchor datacontract : datacontractList) {
                    if ("1".equals(datacontract.getContSubType())) {
                        RelPsn psn = new RelPsn();
                        psn.setIdentifyType(datacontract.getUscType());
                        psn.setIdentifyNumber(datacontract.getUscCode());
                        psn.setStatus("6");
                        List<RelPsn> relPsns = relPsnService.select(psn);
                        if (relPsns.size() > 0) {
                            for (RelPsn psn1 : relPsns) {
//                            Unit unit = new Unit();
//                            unit.setUnitName(dataAnchor.getContSubName());
//                            Unit unit1 = unitService.get(unit);
                                //系统中配置的字典 根据锚点证件号码拿出管户机构的编码去判断锚点和其他主体是否是关联方
                                boolean flot = psn1.getMgerOrg().equals(IdToMgerOrg(dataAnchor.getUscCode()));
                                if (flot) {
                                    isRelTran = "1";
                                    AnchorRel AR = new AnchorRel();
                                    AR.setRelName(datacontract.getContSubName());
                                    AR.setRelType("1");
                                    AR.setUscType(datacontract.getUscType());
                                    AR.setUscCode(datacontract.getUscCode());
                                    relist.add(AR);
                                }
                            }
                        }
                    }
                    if ("2".equals(datacontract.getContSubType())) {
                        RelOrg org = new RelOrg();
                        org.setIdentifyType(datacontract.getUscType());
                        org.setUniscCode(datacontract.getUscCode());
                        org.setApprovalStatus("6");
                        List<RelOrg> relOrgs = relOrgService.select(org);
                        if (relOrgs.size() > 0) {
                            for (RelOrg org1 : relOrgs) {
//                            Unit unit = new Unit();
//                            unit.setUnitName(dataAnchor.getContSubName());
//                            Unit unit1 = unitService.get(unit);
//                          系统中配置的字典 根据锚点证件号码拿出管户机构的编码去判断锚点和其他主体是否是关联方
                                boolean flot = org1.getMgerOrg().equals(IdToMgerOrg(dataAnchor.getUscCode()));
                                //如果是关联交易 将本条合同其他主体添加到本锚点的关联关系集中
                                if (flot) {
                                    isRelTran = "1";
                                    AnchorRel AR = new AnchorRel();
                                    AR.setRelName(datacontract.getContSubName());
                                    AR.setRelType("2");
                                    AR.setUscType(datacontract.getUscType());
                                    AR.setUscCode(datacontract.getUscCode());
                                    relist.add(AR);
                                }
                            }
                        }
                    }
                }
                //判断锚点之间
                for (Anchor dataAnchorto : dataAnchorList) {
                    if (!dataAnchorto.equals(dataAnchor)) {
                        if ("1".equals(dataAnchorto.getContSubType())) {
                            RelPsn psn = new RelPsn();
                            psn.setIdentifyType(dataAnchorto.getUscType());
                            psn.setIdentifyNumber(dataAnchorto.getUscCode());
                            psn.setStatus("6");
                            List<RelPsn> relPsns = relPsnService.select(psn);
                            if (relPsns.size() > 0) {
                                for (RelPsn psn1 : relPsns) {
//                            Unit unit = new Unit();
//                            unit.setUnitName(dataAnchor.getContSubName());
//                            Unit unit1 = unitService.get(unit);
                                    //系统中配置的字典 根据锚点证件号码拿出管户机构的编码去判断锚点和其他主体是否是关联方
                                    boolean flot = psn1.getMgerOrg().equals(IdToMgerOrg(dataAnchor.getUscCode()));
                                    if (flot) {
                                        isRelTran = "1";
                                        AnchorRel AR = new AnchorRel();
                                        AR.setRelName(dataAnchorto.getContSubName());
                                        AR.setRelType("1");
                                        AR.setUscType(dataAnchorto.getUscType());
                                        AR.setUscCode(dataAnchorto.getUscCode());
                                        relist.add(AR);
                                    }
                                }
                            }
                        }
                        if ("2".equals(dataAnchorto.getContSubType())) {
                            RelOrg org = new RelOrg();
                            org.setIdentifyType(dataAnchorto.getUscType());
                            org.setUniscCode(dataAnchorto.getUscCode());
                            org.setApprovalStatus("6");
                            List<RelOrg> relOrgs = relOrgService.select(org);
                            if (relOrgs.size() > 0) {
                                for (RelOrg org1 : relOrgs) {
//                            Unit unit = new Unit();
//                            unit.setUnitName(dataAnchor.getContSubName());
//                            Unit unit1 = unitService.get(unit);
//                          系统中配置的字典 根据锚点证件号码拿出管户机构的编码去判断锚点和其他主体是否是关联方
                                    boolean flot = org1.getMgerOrg().equals(IdToMgerOrg(dataAnchor.getUscCode()));
                                    //如果是关联交易 将本条合同其他主体添加到本锚点的关联关系集中
                                    if (flot) {
                                        isRelTran = "1";
                                        AnchorRel AR = new AnchorRel();
                                        AR.setRelName(dataAnchorto.getContSubName());
                                        AR.setRelType("2");
                                        AR.setUscType(dataAnchorto.getUscType());
                                        AR.setUscCode(dataAnchorto.getUscCode());
                                        relist.add(AR);
                                    }
                                }
                            }
                        }
                    }
                }
                if ("1".equals(isRelTran)) {
                    dataAnchor.setRelList(relist);
                    anchorList.add(dataAnchor);
                    isRelTran = "0";
                }
            }
            if (anchorList.size() > 0) {
                isRelTran = "1";
            }

            result.put("contractNo", contractNo);
            result.put("isRelTran", isRelTran);
            result.put("anchorList", anchorList);
            tips.put("resultCode", "0000");
            tips.put("msg", "成功");
            tips.put("data", result);
        } catch (NullPointerException e) {
            tips.put("resultCode", "1001");
            tips.put("msg", "失败");
        } catch (JSONException e) {
            tips.put("resultCode", "1001");
            tips.put("msg", "失败");
        } catch (Exception e) {
            tips.put("resultCode", "9999");
            tips.put("msg", "失败");
        } finally {
            String x = JSONObject.toJSONString(tips);
            JSONObject y = JSONObject.parseObject(x);
            return y;
        }
    }

    /**
     * 锚点证件号码 查管户机构编码
     */
    public String IdToMgerOrg(String UscCode) {
        List<NameValuePair> items = new ArrayList<>();
        Map tips = new HashMap<String, String>();
        try {
            List<Filter> filters = new Filter.Builder().eq("dictCode", "BUS-IdToMgerOrg").build();
            List<DictItem> list = dictItemService.select(filters);

            //过滤掉不要的字典项
            if (!CollectionUtils.isEmpty(list)) {
                for (int i = 0; i < list.size(); i++) {
                    DictItem item = list.get(i);
                    if (org.springframework.util.StringUtils.hasText("") && "".contains(item.getItemValue())) {
                        continue;
                    }
                    BasicNameValuePair nv = new BasicNameValuePair(item.getItemLabel(), item.getItemValue());
                    items.add(nv);
                }
            }
            for (NameValuePair nameValuePair : items) {
                tips.put(nameValuePair.getValue(), nameValuePair.getName());
            }
            return (String) tips.get(UscCode);
        } catch (ServiceException se) {
            logger.error("系统出现异常：" + se.getMessage());
            throw se;
        }
    }

    /**
     * 关联交易审批状态查询
     */
    @RequestMapping(value = "/selEtransaction", method = RequestMethod.POST)
    public JSONObject selEtransaction(@RequestBody String code) {
        Map map = new HashMap<String, Object>();
        try {
            JSONObject data = JSONObject.parseObject(code);
            String contractNo = (String) data.get("contractNo");
            String applyStatus = " 1";
            //运用资金审批状态
            TraFunds traFunds = new TraFunds();
            traFunds.setContractNo(contractNo);
            List<TraFunds> traFundsList = traFundsService.select(traFunds);
            for (TraFunds a : traFundsList) {
                if (!"6".equals(a.getStatus())) {
                    applyStatus = "0";
                }
            }
            //服务审批状态
            TraService traService = new TraService();
            traService.setContractNo(contractNo);
            List<TraService> traServiceList = traServiceService.select(traService);
            for (TraService a : traServiceList) {
                if (!"6".equals(a.getStatus())) {
                    applyStatus = "0";
                }
            }
            //利益审批状态
            TraInterest traInterest = new TraInterest();
            traInterest.setContractNo(contractNo);
            List<TraInterest> traInterestList = traInterestService.select(traInterest);
            for (TraInterest a : traInterestList) {
                if (!"6".equals(a.getStatus())) {
                    applyStatus = "0";
                }
            }
            //保险业务审批状态
            TraBusiness traBusiness = new TraBusiness();
            traBusiness.setContractNo(contractNo);
            List<TraBusiness> traBusinessList = traBusinessService.select(traBusiness);
            for (TraBusiness a : traBusinessList) {
                if (!"6".equals(a.getStatus())) {
                    applyStatus = "0";
                }
            }
            //投资入股审批状态
            TraInvestment traInvestment = new TraInvestment();
            traInvestment.setContractNo(contractNo);
            List<TraInvestment> traInvestmentList = traInvestmentService.select(traInvestment);
            for (TraInvestment a : traInvestmentList) {
                if (!"6".equals(a.getStatus())) {
                    applyStatus = "0";
                }
            }
            Map results = new HashMap<String, Object>();
            results.put("contractNo", contractNo);
            results.put("applyStatus", applyStatus);
            map.put("resultCode", "0000");
            map.put("msg", "成功");
            map.put("data", results);
        } catch (NullPointerException e) {
            map.put("resultCode", "1001");
            map.put("msg", "失败");
        } catch (JSONException e) {
            map.put("resultCode", "1001");
            map.put("msg", "失败");
        } catch (Exception e) {
            map.put("resultCode", "9999");
            map.put("msg", "失败");
        } finally {
            String g = JSONObject.toJSONString(map);
            JSONObject gg = JSONObject.parseObject(g);
            return gg;
        }


    }

    public static byte[] toByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while ((n = in.read(buffer)) != -1) {
            out.write(buffer, 0, n);
        }
        return out.toByteArray();
    }

    public static InputStream toInputStream(byte[] b) throws IOException {
        return new ByteArrayInputStream(b);
    }

    @RequestMapping(value = "/File", method = RequestMethod.POST)
    @ResponseBody
    public void getFile(@RequestBody String str, HttpServletResponse response) {
        BufferedInputStream buffInputStream = null;
        OutputStream outputStream = null;
        try {
            System.out.println("**str:**   " + str);
            buffInputStream = new BufferedInputStream(new FileInputStream(new File("E:\\testFile\\test.zip")));
            outputStream = response.getOutputStream();
            byte[] buff = new byte[1024 * 1024]; //如果是稍微大的文件，这里配置的大一些
            int len = 0;
            while ((len = buffInputStream.read(buff)) > 0) {
                //把文件流写入到response的输出流中，供请求端请求
                outputStream.write(buff, 0, len);
                outputStream.flush();
            }

            byte[] buffer = new byte[512];
            int length = 0;
            while ((length = buffInputStream.read(buffer)) != -1) {
                response.getOutputStream().write(buffer, 0, length);
            }
            response.getOutputStream().flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (buffInputStream != null) {
                    buffInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
