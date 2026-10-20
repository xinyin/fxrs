package com.yzj.jep.app.fPaas.server;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fPaaS1.core.IFPaaS1Handler;
import com.fPaaS1.core.Message;
import com.fPaaS1.utils.encrypt.Base64;
import com.google.common.io.Files;
import com.yzj.jep.app.base.utils.Configs;
import com.yzj.jep.app.holder.SpringHolder;
import com.yzj.jep.app.pub.model.Publog;
import com.yzj.jep.app.pub.service.PublogService;
import com.yzj.jep.app.sub.model.Sublog;
import com.yzj.jep.app.sub.service.SublogService;
import com.yzj.jep.app.util.ZipUtil;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.sys.model.Staff;
import com.yzj.jep.sys.service.StaffService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 3.2 报送及披露文件获取接口
 */
public class GetRelTranSubmitZipFileHandler implements IFPaaS1Handler<Message> {
    private static final Logger logger = LoggerFactory.getLogger(GetRelTranSubmitZipFileHandler.class);

    private static Configs configs;

    //交易报送服务类
    private SublogService sublogService;
    //用户服务
    private StaffService StaffService;
    //交易披露服务类
    private PublogService publogService;
    //显示2020-12-17 10:00:00格式
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public Message handle(Message message) throws Exception {
        JSONArray fileList = null;
        Staff staff = null;
        Map tips = new HashMap<String, Object>();
        try {
            configs = SpringHolder.getBean(Configs.class);
            sublogService = SpringHolder.getBean(SublogService.class);
            publogService = SpringHolder.getBean(PublogService.class);
            StaffService = SpringHolder.getBean(StaffService.class);
            // 获取请求数据
            Map<String, Object> map = (Map<String, Object>) message.getTarget().getBodys();
            //渠道号
            String chnlId = (String) map.get("chnlId");
            String tranCode = (String) map.get("tranCode");
            fileList = (JSONArray) map.get("fileList");
            //报送文件路径
            String reportPath = configs.getReport();
            //披露
            String disclosePath = configs.getDisclose();
            //合同文件打包文件临时存放路径
            String tempPath = configs.getTemp();
            //文件路径集合
            List<String> paths = new ArrayList<>();
            List<Filter> filters = null;
            for (int i = 0; i < fileList.size(); i++) {
                JSONObject obj = fileList.getJSONObject(i);
                String filePid = obj.getString("filePid");
                String fileType = obj.getString("fileType");
                String fileName = null;
                if (fileType.startsWith("P_")) {
                    filters = new Filter.Builder().eq("pid", filePid).eq("status", "0").build();
                    List<Publog> publogs = publogService.select(filters);
                    if (publogs != null && publogs.size() > 0) {
                        Publog publog = publogs.get(0);
                        String status = publog.getStatus();
                        fileName = publog.getPubFileName();
                        String createByName = publog.getCreateByName();
                        try {
                            if (staff != null) {
                                createByName = staff.getStaffName();
                            } else {
                                List<Filter> filters_1 = new Filter.Builder().eq("staffCode", createByName).build();
                                List<Staff> staffList = StaffService.select(filters_1);
                                if (staffList != null && staffList.size() > 0) {
                                    staff = staffList.get(0);
                                    createByName = staff.getStaffName();
                                }
                            }
                        } catch (Exception e) {
                        }
                        String createTime = simpleDateFormat.format(publog.getCreateTime());
                        //重新写入数据
                        obj.put("fileName", fileName);
                        obj.put("fileCreatTime", createTime);
                        obj.put("fileCreatUser", createByName);

                        //跟新status状态
                        Publog pub = new Publog();
                        pub.setPid(Integer.valueOf(filePid));
                        pub.setStatus("03");//已推送
                        publogService.updateSelective(pub);
                        paths.add(disclosePath + File.separator + fileName);
                    } else {
                        obj.put("describe", "The current document has been submitted");
                    }
                } else if (fileType.startsWith("B_")) {
                    filters = new Filter.Builder().eq("pid", filePid).eq("status", "01").build();
                    List<Sublog> sublogs = sublogService.select(filters);
                    if (sublogs != null && sublogs.size() > 0) {
                        Sublog sublog = sublogs.get(0);
                        String status = sublog.getStatus();
                        fileName = sublog.getFileName();
                        String createByName = sublog.getCreateByName();
                        try {
                            if (staff != null) {
                                createByName = staff.getStaffName();
                            } else {
                                List<Filter> filters_1 = new Filter.Builder().eq("staffCode", createByName).build();
                                List<Staff> staffList = StaffService.select(filters_1);
                                if (staffList != null && staffList.size() > 0) {
                                    staff = staffList.get(0);
                                    createByName = staff.getStaffName();
                                }
                            }
                        } catch (Exception e) {
                        }
                        String createTime = simpleDateFormat.format(sublog.getCreateTime());
                        //重新写入数据
                        obj.put("fileName", fileName);
                        obj.put("fileCreatTime", createTime);
                        obj.put("fileCreatUser", createByName);

                        //跟新status状态
                        Sublog sub = new Sublog();
                        sub.setPid(Integer.valueOf(filePid));
                        sub.setStatus("03");
                        sublogService.updateSelective(sub);
                        paths.add(reportPath + File.separator + fileName);
                    } else {
                        obj.put("describe", "The current document has been submitted");
                    }
                }
            }
            // 对披露文件进行打包处理并且附加描述信息
            //创建描述文档信息
            String desDoc = buildDescDoc(map);
            String zipPath = tempPath + File.separator + chnlId;
            String desPath = tempPath + File.separator + chnlId + File.separator + "fileInf.json";

            boolean state = writeFileToLocal(desDoc, desPath);
            if (state) {
                paths.add(desPath);
                File[] files = new File[paths.size()];
                for (int i = 0; i < paths.size(); i++) {
                    files[i] = new File(paths.get(i));
                }
                boolean zipState = ZipUtil.zip(zipPath, chnlId, files);
                //打包文件
                if (zipState) {
                    tips.put("zipFilePath", Base64Utils.encodeToString(zipPath.getBytes("UTF-8")));
                    tips.put("zipName", chnlId);
                    tips.put("resultCode", "0000");
                    tips.put("msg", "成功");
                    tips.put("CHNL_ID", chnlId);
                    tips.put("TRANS_CODE", tranCode);
                }
            } else {
                tips.put("resultCode", "9999");
                tips.put("msg", "失败");
            }
        } catch (Exception e) {
            tips.put("resultCode", "9999");
            tips.put("msg", "失败");
            logger.error("sublog|publog表在更新中发生异常:{}", e);
        }
        message.getTarget().setBody(tips);
        return message;
    }

    private String buildDescDoc(Map<String, Object> map) {
        JSONObject obj = new JSONObject();
        try {
            JSONObject BODY = new JSONObject();
            JSONArray fileList = (JSONArray) map.get("fileList");
            BODY.put("fileList", fileList);

            JSONObject SYSHEAD = new JSONObject();
            String TRANS_CODE = (String) map.get("tranCode");
            String CHNL_ID = (String) map.get("chnlId");
            SYSHEAD.put("TRANS_CODE", TRANS_CODE);
            SYSHEAD.put("CHNL_ID", CHNL_ID);

            obj.put("SYSHEAD", SYSHEAD);
            obj.put("BODY", BODY);
        } catch (Exception e) {
            logger.error("构建描述文件异常:{}", e);
        }
        return JSONObject.toJSONString(obj);
    }

    public boolean writeFileToLocal(String info, String FilePath) {
        BufferedWriter bufferedWriter = null;
        try {
            File file = new File(FilePath);
            File parentFile = file.getParentFile();
            boolean exists = parentFile.exists();
            if (!exists) {
                parentFile.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            } else {
                file.delete();
                file.createNewFile();
            }
            bufferedWriter = Files.newWriter(file, Charset.forName("UTF-8"));
            bufferedWriter.write(info);
            bufferedWriter.flush();
        } catch (IOException e) {
            logger.error("报送及披露文件获取接口生成描述文件异常:{}", e);
            return false;
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    logger.error("close bufferWritter error");
                }
            }
        }
        return true;
    }
}



