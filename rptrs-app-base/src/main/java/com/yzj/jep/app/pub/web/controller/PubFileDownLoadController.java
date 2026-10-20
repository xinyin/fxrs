package com.yzj.jep.app.pub.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.yzj.jep.app.pub.client.FileOnLineGetClient;
import com.yzj.jep.app.pub.model.PubFileTemplate;
import com.yzj.jep.app.pub.model.Publog;
import com.yzj.jep.app.pub.service.PubFileTemplateService;
import com.yzj.jep.app.pub.service.PublogService;
import com.yzj.jep.orm.entity.Filter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/pub/file")
public class PubFileDownLoadController {

    private static Logger logger = LoggerFactory.getLogger(PubFileDownLoadController.class);

    @Autowired
    private PublogService publogService;

    @Autowired
    private FileOnLineGetClient fileOnLineGetClient;

    @Autowired
    private PubFileTemplateService pubFileTemplateService;

    /**
     * 下载文件
     *
     * @param pid
     * @param request
     * @param response
     */
    @RequestMapping(value = "/downLoad/{pid}")
    public void downLoad(@PathVariable String pid, HttpServletRequest request,
                         HttpServletResponse response) {
        OutputStream outputStream = null;
        try {
            logger.info("下载在线编辑文件方法：" + pid);
            if (StringUtils.isNotBlank(pid)) {
                String fileUrl = null;
                String id = pid.substring(2);
                List<Filter> filters = new Filter.Builder().eq("pid", Integer.valueOf(id)).build();
                if (pid.startsWith("T_")) {
                    List<PubFileTemplate> pubFileTemplates = pubFileTemplateService.select(filters);
                    if (pubFileTemplates != null && pubFileTemplates.size() > 0) {
                        fileUrl = pubFileTemplates.get(0).getTempFileUrl();
                    }
                } else {
                    List<Publog> publogs = publogService.select(filters);
                    if (publogs != null && publogs.size() > 0) {
                        fileUrl = publogs.get(0).getFileUrl();
                    }
                }
                if (fileUrl != null) {
                    outputStream = response.getOutputStream();
                    byte[] bytes = FileUtils.readFileToByteArray(new File(fileUrl));
                    outputStream.write(bytes);
                    outputStream.flush();
                }
            }
        } catch (IOException e) {
            logger.error("gets a file interface exception:{}", e);
        } finally {
            IOUtils.closeQuietly(outputStream);
        }
    }

    /**
     * 判断链路是否连通
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/isConnected", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> isConnected(@RequestBody Map<String, Object> request) {
        Map<String, Object> message = Maps.newHashMap();
        try {
            logger.info("判断链路是否连通:" + request.toString());
            if (request == null) {
                message.put("error", 0);
                return message;
            }
            JSONObject json = new JSONObject(request);
            String resultCode = json.getString("resultCode");
            if (resultCode.equals("0000")) {
                JSONObject data = json.getJSONObject("data");
                String status = data.getString("status");
                if ("2".equals(status)) {
                    String id = data.getString("id");
                    fileOnLineGetClient.getOnLineFile(id);
                }
            }
        } catch (Exception e) {
            logger.error("an exception occurred in fetching information:{}", e);
        }
        message.put("error", 0);
        return message;
    }
}
