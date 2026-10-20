package com.yzj.jep.app.contract.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fPaaS1.core.IFPaaS1Handler;
import com.fPaaS1.core.Message;
import com.fPaaS1.core.factory.FaultFactory;
import com.fPaaS1.core.factory.MessageFactory;
import com.fPaaS1.core.utils.Constants;
import com.yzj.jep.app.base.utils.Configs;
import com.yzj.jep.app.contract.model.ApiRequest;
import com.yzj.jep.app.contract.model.ApiResponse;
import com.yzj.jep.app.contract.service.FileContractService;
import com.yzj.jep.app.contract.service.InformationAutoService;
import com.yzj.jep.app.contract.util.ContractUtil;
import com.yzj.jep.app.contract.util.HttpClientUtil;
import com.yzj.jep.app.contract.util.MD5FileUtil;
import com.yzj.jep.app.contract.util.MD5Util;
import com.yzj.jep.app.fPaas.client.HttpConstans;
import com.yzj.jep.app.fPaas.client.InformationAutoHandler;
import com.yzj.jep.app.ins.model.*;
import com.yzj.jep.app.ins.service.*;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.sys.service.AppConfigService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 合同签订信息自动获取接口
 */
@Service
public class InformationAutoServiceImpl implements InformationAutoService {

    private static final Logger logger = LoggerFactory.getLogger(FileContractServiceImpl.class);

    @Autowired
    private Configs config;
    @Autowired
    private FileContractService fileContractService;

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static AppConfigService appConfigService = SpringContextHolder.getBean(AppConfigService.class);

    @Override
    public void getSignedContractList() {
        try {
            String appId = appConfigService.getConfigValue("channel_app_id");
            String cfUrl = appConfigService.getConfigValue("channel_Contract_Files");//合同关联文件获取接口url
            String clUrl = appConfigService.getConfigValue("channel_Contract_List");//已签订合同信息列表接口url
            String secretkey = appConfigService.getConfigValue("channel_contract_secretkey");

            Calendar calendar = Calendar.getInstance();
            String nonce = ContractUtil.getUUID();
            long timestamp = ContractUtil.getTimeStamp();
            Date today = new Date();//获取当前日期前一周得日期 格式yyyy-MM-dd
            calendar.setTime(today);
            calendar.add(Calendar.DAY_OF_MONTH, -7);
            Date yesterday = calendar.getTime();//一周前日期
            String signApplyTimeEnd = simpleDateFormat.format(today);
            String signApplyTimeStart = simpleDateFormat.format(yesterday);
            //请求参数类
            ApiRequest request = new ApiRequest();
            //构建业务请求数据
            JSONObject busMsg = new JSONObject();
            busMsg.put("signApplyTimeStart", signApplyTimeEnd);
            busMsg.put("signApplyTimeEnd", signApplyTimeStart);
            request.setAppId(appId);
            request.setNonce(nonce);
            request.setTimestamp(timestamp);
            //获取签名数据
            String sign = ContractUtil.getSign(secretkey, nonce, JSON.toJSONString(busMsg));
            request.setSig(sign);
            request.setData(busMsg);
            //获取返回信息
            String response = HttpClientUtil.httpGetHtml(JSON.toJSONString(request), clUrl);
            if (response == null) {
                return;
            }
            JSONObject object = JSONObject.parseObject(response);
            String resultCode = object.getString("resultCode");
            if (resultCode.equals("0000")) {
                JSONArray contractList = object.getJSONObject("data").getJSONArray("contractList");
                for (int i = 0; i < contractList.size(); i++) {
                    JSONObject contract = contractList.getJSONObject(i);
                    String contractNo = contract.getString("contractNo");
                    String nonce_1 = ContractUtil.getUUID();
                    long timestamp_1 = ContractUtil.getTimeStamp();
                    //请求参数类
                    ApiRequest request_1 = new ApiRequest();
                    //构建业务请求数据
                    JSONObject busMsg_1 = new JSONObject();
                    busMsg_1.put("contractNo", contractNo);
                    request_1.setAppId(appId);
                    request_1.setNonce(nonce_1);
                    request_1.setData(busMsg_1);
                    request_1.setTimestamp(timestamp_1);
                    //获取签名数据
                    String sign_1 = ContractUtil.getSign(secretkey, nonce_1, JSON.toJSONString(busMsg_1));
                    request_1.setSig(sign_1);
                    //获取返回信息
                    logger.info("调用接口请求信息:{}", JSON.toJSONString(request_1));
                    //获取返回信息
                    Map<String, String> map = HttpClientUtil.httpPostByte(JSON.toJSONString(request_1), cfUrl, config.getTemp());
                    if ("SUCCESS".equals(map.get("code"))) {
                        String path = map.get("path");
                        String name = map.get("name");
                        ContractUtil.unZipFile(path, name);
                        //判断文件和附件是否关联
                        boolean isNeedDel = fileContractService.scanFileAttach(path, name, contractNo);
                        String dirPath = path + File.separator + name;
                        String zipPath = dirPath.concat(".zip");

                        //1.删除压缩包
                        File zipFile = new File(zipPath);
                        if (zipFile.exists()) {
                            ContractUtil.delete(zipFile);
                        }
                        //2.删除
                        if (isNeedDel) {
                            //2.删除文件夹以及文件夹内的文件
                            File file = new File(dirPath);
                            if (file.exists()) {
                                ContractUtil.delete(file);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("automatic execution of program exceptions:{}", e);
        }
    }
}