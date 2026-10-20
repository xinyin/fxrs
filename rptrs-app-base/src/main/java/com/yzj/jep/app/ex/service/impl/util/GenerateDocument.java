package com.yzj.jep.app.ex.service.impl.util;

import cn.hutool.core.date.DateTime;
import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.ins.model.*;
import com.yzj.jep.app.ins.service.*;
import com.yzj.jep.app.util.BatchNumberUtil;
import com.yzj.jep.app.util.Dict;
import com.yzj.jep.app.util.WorderToNewWordUtils;
import com.yzj.jep.commons.DateUtils;

import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.workflow.api.engine.WorkflowEngine;
import com.yzj.jep.workflow.api.model.IHisProcLog;


import com.yzj.jep.workflow.wf.model.HisProcInst;
import com.yzj.jep.workflow.wf.service.HisProcInstService;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.*;

/**
 * @author root
 * 重大审批候生成pdf审批记录
 */
public class GenerateDocument {

    private static Logger logger = LoggerFactory.getLogger(GenerateDocument.class);
    private static final WorkflowEngine workflowEngine = SpringContextHolder.getBean(WorkflowEngine.class);
    private static final HisProcInstService hisProcInstService = SpringContextHolder.getBean(HisProcInstService.class);
    private static final TraInsenclosureService traInsenclosureService = SpringContextHolder.getBean(TraInsenclosureService.class);
    private static final DictItemService dictItemService = SpringContextHolder.getBean(DictItemService.class);


    public static void processData(JSONObject json) {
        //获取生成审批单备案单数据
        Map<String, Object> map = approvaData(json);
        //根据模板生成生审批案单原始文件
        Map<String, Object> tips = PDFReportUtil.createPDF("file", map);
        //把路径写到附件表中

        TraInsenclosure enclosure = new TraInsenclosure();
        //存入数据
        //确保唯一
        enclosure.setEnclosurePid(BatchNumberUtil.makeBatchNumber());
        //存入数据
        Date currentDate = new Date();
        String currentDateString = currentDate.toString();
        enclosure.setFileType("pdf");
        enclosure.setPath(String.valueOf(tips.get("savePath")));
        enclosure.setRelatedName("关联交易重大审核表单");
        enclosure.setDelflag("1");
        //enclosure.setFileSize(String.valueOf(0.09));
        enclosure.setTransNum(json.getString("procInstId"));
        enclosure.setBusinessType("");
        enclosure.setCreateTime(currentDate);
        enclosure.setModifiedTime(currentDate);
        enclosure.setUploadTime(DateUtils.formatDate(new Date()));
        traInsenclosureService.insert(enclosure);
    }

    /**
     * 根据模板生成生审批案单原始文件
     *
     * @param map
     */
    public static Map<String, String> approvalFormGeneration(Map<String, Object> map) {
        Map<String, String> data = new HashMap<>();
        //模板存放路径home\rptrs
        String inputUrl = "C:/home/rptrs/关联交易办公室审计记录表.docx";
        //文件输出路径  C:\app\share\template
        String outputUrl = "C:/app/share/template";
        List<Object> logList = (List<Object>) map.get("logList");
        int[] placeList = {0, 0};
        List<Map<String, Object>> paragraphsTextList = new ArrayList<>();
        String suffix = inputUrl.substring(inputUrl.lastIndexOf("."));
        //新生产的模板文件
        String fileName = fileNameConversion((String) map.get("considerType")) + "_" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS");
        outputUrl = outputUrl + "/" + fileName + suffix;
        File dest = new File(outputUrl);
        if (!dest.getParentFile().exists()) {
            //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        if ("1".equals(map.get("considerType"))) {
            boolean changWord = WorderToNewWordUtils.changWord(inputUrl, outputUrl, map, new ArrayList<>());
        } else {
            boolean changWord = WorderToNewWordUtils.changWord(inputUrl, outputUrl, map, new ArrayList<>());
        }
        data.put("filePath", outputUrl + "/");
        data.put("fileName", fileName);
        return data;
    }

    /**
     * 根据不同数据类型处理数据并返回Map集合
     * @return
     */
    public static Map<String, Object> approvaData(JSONObject json) {
        Map<String, Object> data = new HashMap<>();
        //合同编号
        data.put("contractNo", json.getString("contractNo"));
        //关联方证件号码
        data.put("relNumber", json.getString("relNumber"));
        // 交易名称	${tranName}
        data.put("tranName", json.getString("tranName"));
        // 交易对手名称	${relName}
        data.put("relName", json.getString("relName"));
        //业务起始日期	${busStartTime}
        data.put("busStartTime", DateForm(json.getDate("busStartTime")));
        // 业务结束日期	${busEndTime}
        data.put("busEndTime", DateForm(json.getDate("busEndTime")));

        // 关联交易金额（万元）	${relTradeAmount}
        data.put("relTradeAmount", json.getString("relTradeAmount"));
        //交易价格	${tradePrice}
        data.put("tradePrice", json.getString("tradePrice"));
        //定价政策	${pricingPolicy}
        data.put("pricingPolicy", json.getString("pricingPolicy"));
        //交易描述	${transactionDescription}
        data.put("transactionDescription", json.getString("transactionDescription"));
        //交易标的	${tradeSubject}
        data.put("tradeSubject", json.getString("tradeSubject"));
        //对财务状况及经营成果的影响	${tradeOccur}
        data.put("tradeOccur", json.getString("tradeOccur"));
        //其他事项说明	${remark}
        data.put("remark", json.getString("remark"));
        String insType = json.getString("tranType");
        String busType = json.getString("busType");
        data.put("tranType",getDictValue("INS-tranType").get(insType));
        logger.info("查询判断:",insType,data.get("tranType"));
        logger.info("查询判断1：{}",insType);
        logger.info("查询判断2：{}",json.getString("busType"));
        logger.info("查询判断3：{}",busType);
        logger.info("查询判断4：{}",getDictValue("INS-tranType"));
        logger.info("查询判断5：{}",getDictValue("INS-FundsType"));
        logger.info("查询判断6：{}",getDictValue("BUS-NewBusinessType"));
        //合同签订日期	${tranDate} 资金运用类 tranDate 其他三类字段  contractDate
        if (Dict.TRAN_TYPE_02.equals(insType)) {
            data.put("tranDate", DateForm(json.getDate("tranDate")));
        }else {
            data.put("tranDate", DateForm(json.getDate("contractDate")));
        }
        //资金运用类
        if (Dict.TRAN_TYPE_02.equals(insType)) {
            data.put("busType", getDictValue("INS-FundsType").get(busType));
        } else if (Dict.TRAN_TYPE_05.equals(insType)) {
            //服务类
            data.put("busType", getDictValue("INS-ServiceType").get(busType));
        } else if (Dict.TRAN_TYPE_04.equals(insType)) {
            //利益转移
            data.put("busType", getDictValue("INT-BusinessTypes").get(busType));
        } else if (Dict.TRAN_TYPE_03.equals(insType)) {
            //保险业务或其他
            data.put("busType", getDictValue("INT-InsBusinessType").get(busType));
        }
        getHisLog(json, data);

        return data;
    }

    /**
     * 通过字典值获取字典项
     *
     * @param dictCode 输入字典值
     * @return
     */
    public static Map<String, String> getDictValue(String dictCode) {
        List<Filter> filters = new Filter.Builder().eq("dictCode", dictCode).build();
        List<DictItem> considerTypeDict = dictItemService.select(filters);
        Map considerTypeMap = new HashMap();
        for (DictItem dic : considerTypeDict) {
            considerTypeMap.put(dic.getItemValue(), dic.getItemLabel());
        }
        return considerTypeMap;
    }

    /**
     * 通过流程主键获取流程历史日志并返回list集合
     *
     * @param json
     * @return
     */
    private static void getHisLog(JSONObject json, Map<String, Object> data) {

        Map<String, String> dictValue = getDictValue("BUS-ApprovalToPdf");
        //资产管理中心组合管理部
        String asset ="1";
        //风险管理部
        String risk="1";
        //合规部
        String	compliance="1";
        //董事会秘书
        String	secretary="1";
        //财务部
        String	finance="1";
        asset = dictValue.get("asset");
        risk = dictValue.get("risk");
        compliance = dictValue.get("compliance");
        secretary = dictValue.get("secretary");
        finance = dictValue.get("finance");
        String procInstId = json.getString("procInstId");
        HisProcInst hisProcInst = new HisProcInst();
        hisProcInst.setId(procInstId);
       // hisProcInst = hisProcInstService.get(hisProcInst);
        //定义各部门人员的审批信息
        List<? extends IHisProcLog> logs = workflowEngine.getLogService().selectHistoryProcLog(procInstId);
        if (null != logs && logs.size() > 0) {

            //可能存在时间错乱导致数据导出，出现人员与部门对应不上，先过滤负责人节点的审批意见
            for (IHisProcLog procLog : logs) {
                if ("usertask1".equals(procLog.getTaskKey()) && "COMPLETE".equals(procLog.getAction())) {
                    data.put("chargePerson",NewName(procLog.getStaffName(),procLog.getStaffCode()));
                }else if ("usertask2".equals(procLog.getTaskKey()) && "COMPLETE".equals(procLog.getAction())) {
                    String memo = procLog.getMemo();
                    data.put("departmentHead", NewName(procLog.getStaffName(),procLog.getStaffCode()));
                    data.put("ApprovalComments", memo);
                }else if ("usertask5".equals(procLog.getTaskKey()) && "COMPLETE".equals(procLog.getAction())) {
                    data.put("reviewer", NewName(procLog.getStaffName(),procLog.getStaffCode()));
                    data.put("reviewerComments", procLog.getMemo());
                }else if ("usertask6".equals(procLog.getTaskKey()) && "COMPLETE".equals(procLog.getAction())) {
                    data.put("departmentHead1", NewName(procLog.getStaffName(),procLog.getStaffCode()));
                    data.put("headComments", procLog.getMemo());
                }else if ("usertask7".equals(procLog.getTaskKey()) && "COMPLETE".equals(procLog.getAction())) {
                    //资产管理中心
                    if (asset.equals(procLog.getDeptCode())) {
                        data.put("asset", NewName(procLog.getStaffName(), procLog.getStaffCode()));
                        data.put("assetComments", procLog.getMemo());
                    } else if (compliance.equals(procLog.getDeptCode())) {//合规部
                        data.put("compliance", NewName(procLog.getStaffName(), procLog.getStaffCode()));
                        data.put("complianceComments", procLog.getMemo());
                    }else if (secretary.equals(procLog.getDeptCode())) {//董事会秘书
                        data.put("secretary", NewName(procLog.getStaffName(), procLog.getStaffCode()));
                        data.put("secretaryComments", procLog.getMemo());
                    }else if (risk.equals(procLog.getDeptCode())) {//风险管理部负责人
                        data.put("risk", NewName(procLog.getStaffName(), procLog.getStaffCode()));
                        data.put("riskComments", procLog.getMemo());
                    }else if (finance.equals(procLog.getDeptCode())) {//财务部负责人
                        data.put("finance", NewName(procLog.getStaffName(), procLog.getStaffCode()));
                        data.put("financeComments", procLog.getMemo());
                    }
                }
            }

        }
    }

    /**
     * 转换文件名称
     *
     * @param considerType
     * @param
     * @return
     */
    public static String fileNameConversion(String considerType) {
        String newFileName = "";

        if ("4".equals(considerType)) {
            newFileName = "统一交易协议审查单";
        } else if ("1".equals(considerType)) {
            newFileName = "关联交易备案单（免于审议）";
        } else if ("2".equals(considerType)) {
            newFileName = "一般关联交易逐笔审批单";
        } else {
            newFileName = "重大关联交易审查单";

        }
        return newFileName;
    }
    public static String DateForm(Date date) {
        if(date ==null){
            return "";
        }
       return new DateTime(date).toString("YYYY年MM月dd日");
    }

    private static String NewName(String name,String code){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name);
        stringBuilder.append("(");
        stringBuilder.append(code);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

}
