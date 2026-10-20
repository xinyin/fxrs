package com.yzj.jep.app.pub.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.base.utils.Configs;
import com.yzj.jep.app.contract.model.ApiRequest;
import com.yzj.jep.app.contract.model.ApiResponse;
import com.yzj.jep.app.contract.util.ContractUtil;
import com.yzj.jep.app.contract.util.HttpClientUtil;
import com.yzj.jep.app.pub.model.PubFileTemplate;
import com.yzj.jep.app.pub.model.Publog;
import com.yzj.jep.app.pub.service.PubFileTemplateService;
import com.yzj.jep.app.pub.service.PublogService;
import com.yzj.jep.app.pub.util.OfficeOnLineUtil;
import com.yzj.jep.app.pub.util.StatusCode;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.sys.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

/**
 * 获取在线编辑文件
 */
@Component
public class FileOnLineGetClient {

    private static Logger logger = LoggerFactory.getLogger(FileOnLineGetClient.class);

    @Autowired
    private Configs config;

    @Autowired
    private PublogService publogService;

    @Autowired
    private AppConfigService appConfigService;

    @Autowired
    private PubFileTemplateService pubFileTemplateService;

    /**
     * 获取在线编辑文件
     *
     * @param pid
     * @return
     */
    public String getOnLineFile(String pid) {
        try {
            String appId = appConfigService.getConfigValue("ONLINE_OFFICE_APPID");
            String secretkey = appConfigService.getConfigValue("ONLINE_OFFICE_SECRETKEY");
            String online_office_getFile_url = appConfigService.getConfigValue("ONLINE_OFFICE_GETFILE");
            //请求参数类
            ApiRequest request = new ApiRequest();
            //构建业务请求数据
            String nonce = OfficeOnLineUtil.getUUID();
            long timestamp = OfficeOnLineUtil.getTimeStamp();
            request.setAppId(appId);
            request.setNonce(nonce);
            request.setTimestamp(timestamp);
            //获取签名数据
            JSONObject busMsg = new JSONObject();
            busMsg.put("id", pid);
            String message = busMsg.toString();
            String sign = ContractUtil.getSign(secretkey, nonce, message);
            request.setSig(sign);
            request.setData(message);
            //获取文件名
            String fileName = null;
            String id = pid.substring(2);
            List<Filter> filters = new Filter.Builder().eq("pid", Integer.valueOf(id)).build();
            if (pid.startsWith("T_")) {
                List<PubFileTemplate> pubFileTemplates = pubFileTemplateService.select(filters);
                if (pubFileTemplates != null && pubFileTemplates.size() > 0) {
                    String fileType = pubFileTemplates.get(0).getFileType();
                    fileName = pubFileTemplates.get(0).getTempFileName() + "." + fileType;
                }
            } else {
                List<Publog> publogs = publogService.select(filters);
                if (publogs != null && publogs.size() > 0) {
                    String fileUrl = publogs.get(0).getFileUrl();
                    String fileType = fileUrl.substring(fileUrl.lastIndexOf("."));
                    fileName = publogs.get(0).getPubFileName() + fileType;
                }
            }
            //获取文件
            logger.info("获取编辑后的文件:" + JSON.toJSONString(request));
            HttpClientUtil.httpGetFile(request, fileName, online_office_getFile_url, config.getDisclose());
        } catch (Exception e) {
            logger.error("exceptions in accessing information from the online edit page interface:{}", e);
        }
        return null;
    }
}


