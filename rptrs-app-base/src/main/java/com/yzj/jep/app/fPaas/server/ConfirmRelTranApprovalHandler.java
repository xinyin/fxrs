package com.yzj.jep.app.fPaas.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fPaaS1.core.IFPaaS1Handler;
import com.fPaaS1.core.Message;
import com.google.common.io.Files;
import com.yzj.jep.app.base.utils.Configs;
import com.yzj.jep.app.holder.SpringHolder;
import com.yzj.jep.app.pub.model.Publog;
import com.yzj.jep.app.pub.service.PublogService;
import com.yzj.jep.app.sub.model.Sublog;
import com.yzj.jep.app.sub.service.SublogService;
import com.yzj.jep.app.util.ZipUtil;
import com.yzj.jep.orm.entity.Filter;
import org.apache.avalon.framework.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * 3.3 报送及披露文件审批确认接口
 */
public class ConfirmRelTranApprovalHandler implements IFPaaS1Handler<Message> {
    private static final Logger logger = LoggerFactory.getLogger(ConfirmRelTranApprovalHandler.class);

    private static Configs configs;

    //交易报送服务类
    private SublogService sublogService;
    //交易披露服务类
    private PublogService publogService;

    @Override
    public Message handle(Message message) throws Exception {
        JSONArray fileList = null;
        String chnlId = null;
        String tranCode = null;
        Map tips = new HashMap<String, Object>();
        try {
            sublogService = SpringHolder.getBean(SublogService.class);
            publogService = SpringHolder.getBean(PublogService.class);
            // 获取请求数据
            Map<String, Object> map = (Map<String, Object>) message.getTarget().getBodys();
            List<Map<String, Object>> lists = new ArrayList<>();
            //渠道号
            chnlId = String.valueOf(map.get("chnlId"));
            tranCode = String.valueOf(map.get("tranCode"));
            //审批ID
            String approvalID = String.valueOf(map.get("approvalID"));

            fileList = (JSONArray) map.get("fileList");
            //序列号
            int serialNo = 1;
            for (int i = 0; i < fileList.size(); i++) {
                Map<String, Object> list = new HashMap();

                JSONObject obj = fileList.getJSONObject(i);
                String filePid = obj.getString("filePid");
                String fileType = obj.getString("fileType");

                list.put("serialNo", String.valueOf(serialNo++));
                list.put("fileName", obj.getString("fileName"));
                list.put("fileType", obj.getString("fileType"));
                list.put("filePid", obj.getString("filePid"));

                List<Filter> filters = new Filter.Builder().eq("pid", filePid).build();
                if (fileType.startsWith("P_")) {
                    List<Publog> publogs = publogService.select(filters);
                    Publog publog = publogs.get(0);
                    String fileName = publog.getPubFileName();
                    Date createTime = publog.getCreateTime();
                    String createByName = publog.getCreateByName();
                    //更新审批信息
                    String approvalState = updateLogInfo(approvalID, filePid, fileType);
                    //重新写入数据
                    list.put("fileName", fileName);
                    //跟新返回信息
                    list.put("approvalState", approvalState);
                    lists.add(list);
                } else if (fileType.startsWith("B_")) {
                    List<Sublog> sublogs = sublogService.select(filters);
                    Sublog sublog = sublogs.get(0);
                    String fileName = sublog.getFileName();
                    Date createTime = sublog.getCreateTime();
                    String createByName = sublog.getCreateByName();
                    //更新审批信息
                    String approvalState = updateLogInfo(approvalID, filePid, fileType);
                    //重新写入数据
                    list.put("fileName", fileName);
                    //跟新返回信息
                    list.put("approvalState", approvalState);
                    lists.add(list);
                }
            }
            tips.put("fileList", lists);
            tips.put("resultCode", "0000");
            tips.put("msg", "成功");
            tips.put("CHNL_ID", chnlId);
            tips.put("TRANS_CODE", tranCode);

        } catch (Exception e) {
            tips.put("resultCode", "9999");
            tips.put("msg", "失败");
            tips.put("CHNL_ID", chnlId);
            tips.put("TRANS_CODE", tranCode);
            logger.error("sublog|publog表在更新approvalID值过程中发生异常:{}", e);
        }
        message.getTarget().setBody(tips);
        return message;
    }

    private String updateLogInfo(String approvalID, String pid, String fileType) {
        try {
            if (fileType.startsWith("B_")) {
                // 更新sublog
                Sublog sublog = new Sublog();
                sublog.setPid(Integer.valueOf(pid));
                List<Sublog> sublogList = sublogService.select(sublog);
                for (Sublog sublogg : sublogList) {
                    sublogg.setApprovalId(approvalID);
                    sublogService.update(sublogg);
                }
            }
            if (fileType.startsWith("P_")) {
                // 更新publog
                Publog publog = new Publog();
                publog.setPid(Integer.valueOf(pid));
                List<Publog> publogList = publogService.select(publog);
                for (Publog publogg : publogList) {
                    publogg.setApprovalId(approvalID);
                    publogService.update(publogg);
                }
            }
        } catch (Exception e) {
            logger.error("sublog|publog表在更新approvalID值过程中发生异常:{}", e);
            return "0";
        }
        return "1";
    }

}



