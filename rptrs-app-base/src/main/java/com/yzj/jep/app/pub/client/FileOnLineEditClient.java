package com.yzj.jep.app.pub.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
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

import java.net.InetAddress;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 文件在线编辑客户端
 */
@Component
public class FileOnLineEditClient {

    private static Logger logger = LoggerFactory.getLogger(FileOnLineEditClient.class);

    @Autowired
    private AppConfigService appConfigService;

    @Autowired
    private PublogService publogService;

    @Autowired
    private PubFileTemplateService pubFileTemplateService;

    /**
     * 打开在线编辑页面
     *
     * @param mode
     * @param pid
     * @return
     */
    public String openEditPage(String mode, String pid, String type) {
        try {
            String appId = appConfigService.getConfigValue("ONLINE_OFFICE_APPID");
            String ipAddress = appConfigService.getConfigValue("PROGECT_ADDRESS");
            String secretkey = appConfigService.getConfigValue("ONLINE_OFFICE_SECRETKEY");
            String online_office_open_url = appConfigService.getConfigValue("ONLINE_OFFICE_OPEN");
            String callbackUrl = ipAddress + "/pub/file/isConnected";
            //查询fileName
            String fileName = null;
            List<Filter> filters = new Filter.Builder().eq("pid", Integer.valueOf(pid)).build();
            if (type.equals("template")) {
                List<PubFileTemplate> pubFileTemplates = pubFileTemplateService.select(filters);
                if (pubFileTemplates != null && pubFileTemplates.size() > 0) {
                    String fileType = pubFileTemplates.get(0).getFileType();
                    fileName = pubFileTemplates.get(0).getTempFileName() + "." + fileType;
                    pid = "T_" + pid;
                }
            } else {
                List<Publog> publogs = publogService.select(filters);
                if (publogs != null && publogs.size() > 0) {
                    String fileUrl = publogs.get(0).getFileUrl();
                    String fileType = fileUrl.substring(fileUrl.lastIndexOf("."));
                    fileName = publogs.get(0).getPubFileName() + fileType;
                    pid = "P_" + pid;
                }
            }
            String url = ipAddress + "/pub/file/downLoad/" + pid;
            if (fileName == null) {
                return null;
            }
            //请求参数类
            ApiRequest request = new ApiRequest();
            //构建业务请求数据
            String nonce = OfficeOnLineUtil.getUUID();
            long timestamp = OfficeOnLineUtil.getTimeStamp();
            request.setAppId(appId);
            request.setNonce(nonce);
            request.setTimestamp(timestamp);
            //获取签名数据
            JSONObject busMsg = new JSONObject(true);
            busMsg.put("url", url);
            busMsg.put("mode", mode);
            busMsg.put("fileName", fileName);
            if (mode.equals("edit")) {
                busMsg.put("callbackUrl", callbackUrl);
            }
            busMsg.put("id", pid);
            String message = busMsg.toString();
            String sign = OfficeOnLineUtil.getSign(secretkey, nonce, message);
            request.setSig(sign);
            request.setData(message);
            //获取返回信息
            logger.info("在线编辑请求信息:" + JSON.toJSONString(request));
            return HttpClientUtil.httpGetHtml(JSON.toJSONString(request), online_office_open_url);
        } catch (Exception e) {
            logger.error("exceptions in accessing information from the online edit page interface:{}", e);
        }
        return null;
    }
}


