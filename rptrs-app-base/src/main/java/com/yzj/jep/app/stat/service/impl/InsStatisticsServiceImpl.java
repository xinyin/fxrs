package com.yzj.jep.app.stat.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yzj.jep.app.bus.model.Rel;
import com.yzj.jep.app.bus.model.RelPro;
import com.yzj.jep.app.bus.service.RelProService;
import com.yzj.jep.app.bus.service.RelService;
import com.yzj.jep.app.ins.model.*;
import com.yzj.jep.app.ins.service.*;
import com.yzj.jep.app.stat.mapper.InsStatisticsMapper;
import com.yzj.jep.app.stat.service.InsStatisticsService;
import com.yzj.jep.app.sub.mapper.ProcessQueryMapper;
import com.yzj.jep.app.sub.model.MidBusProc;
import com.yzj.jep.app.sub.service.ProcessQueryService;
import com.yzj.jep.sys.model.Unit;
import com.yzj.jep.sys.service.UnitService;
import com.yzj.jep.workflow.wf.model.HisProcInst;
import com.yzj.jep.workflow.wf.model.HisProcLog;
import com.yzj.jep.workflow.wf.model.ProcInstData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @title: InsStatisticsServiceImpl
 * @Author Whx
 * @Date: 2021/7/12 16:59
 * @Version 1.0
 */
@Service
public class InsStatisticsServiceImpl implements InsStatisticsService {

    private static Logger logger = LoggerFactory.getLogger(InsStatisticsServiceImpl.class);

    private static final String TEMPLATE_FILE = "submitTemplate/insApproveStat.xlsx";

    @Autowired
    private InsStatisticsMapper insStatisticsMapper;

    @Autowired
    private ProcessQueryService processQueryService;

    @Autowired
    private UnitService unitService;
    @Autowired
    TraInvestmentService traInvestmentService;
    @Autowired
    TraServiceService traServiceService;
    @Autowired
    TraInterestService traInterestService;
    @Autowired
    TraFundsService traFundsService;
    @Autowired
    TraOtherService traOtherService;
    @Autowired
    TraBusinessService traBusinessService;
    @Autowired
    private ProcessQueryMapper processQueryMapper;
    @Autowired
    private RelProService relProService;
    @Autowired
    private RelService relService;

    /**
     * 分页查询所有流程数据
     *
     * @param pageSize
     * @param pageNumber
     * @param year
     * @param quarter
     * @param startTime
     * @param endTime
     * @return 统计数据为：法律合规管理员审批通过的关联交易数据。
     * 关联交易审批流程发起流程  分公司一般审议与重大审议    总公司一级部门一般审议与重大审议  总公司二级部门一般审议与重大审议
     * 其中分公司一般审议流程不经过总部法律合规部审批，故此处不统计分公司的一般关联交易审批流程
     */
    @Override
    public Map<String, Object> statIns(Integer pageSize, Integer pageNumber, String year, String quarter, String startTime, String endTime) {
        PageHelper.startPage(pageNumber, pageSize);
        //查询语句
        Map<String, Object> param = new HashMap<>();
        //处理查询条件
        processQueryConditions(param, year, quarter, startTime, endTime);
        //30运行中，40已结束，目前取运行中和已结束的数据
        List<Map> dataList = new ArrayList<>();
        List<HisProcInst> list = insStatisticsMapper.queryInsByConditions(param);
        PageInfo<HisProcInst> pageInfo = new PageInfo<>(list);
        Integer index = 1 + (pageNumber - 1) * pageSize;
        for (HisProcInst hisProcInst : list) {
            Map<String, Object> resultMap = new HashMap<>();
            //审批编号设置
            resultMap.put("appNo", param.get("year").toString().replace("%", "").concat("-").concat(index + ""));
            //经办单位
            String starterUnitCode = hisProcInst.getStarterUnitCode();
            Unit queryUnit = new Unit();
            queryUnit.setUnitCode(starterUnitCode);
            Unit unit = unitService.get(queryUnit);
            resultMap.put("starterUnitName", unit.getUnitName());
            //经办部门
            resultMap.put("assigneeOrg", hisProcInst.getStarterDeptName());
            if ("40".equals(hisProcInst.getStatus())) {
                //审批通过时间,流程结束通过审批的时间
                resultMap.put("endTime", hisProcInst.getArchieveTimeString());
            }
            //查询wf_his_proc_log表中部门为法律合规部的数据
            List<HisProcLog> logList = insStatisticsMapper.queryProcLogByInsId(hisProcInst.getProcessInstantId());
            if (CollectionUtil.isNotEmpty(logList)) {
                //查看最后一条数据是否为完成操作
                HisProcLog lastHisProcLog = logList.get(logList.size() - 1);
                if ("COMPLETE".equals(lastHisProcLog.getAction())) {
                    //任务已完成,设置信息
                    //审批时间
                    Date logTime = lastHisProcLog.getLogTime();
                    resultMap.put("approveTime", DateUtil.format(logTime, "yyyy-MM-dd"));
                    //经办人
                    resultMap.put("assignee", lastHisProcLog.getStaffName());
                    //审批意见
                    resultMap.put("memo", lastHisProcLog.getMemo());
                }
            }
            //此处封装前端展示数据
            if ("30".equals(hisProcInst.getStatus())) {
                //运行中的流程存在业务数据
                ProcInstData procInstData = insStatisticsMapper.queryInsDataByProcInsId(hisProcInst.getId());
                //解析数据
                if (procInstData != null) {
                    Map<String, Object> map = parseJsonData(procInstData.getFormData());
                    resultMap.putAll(map);
                }
            } else {
                //流程结束后业务数据会删除，此时在临时表中获取数据。
                //查询备份表
                List<MidBusProc> midBusProcs = processQueryService.queryMidData(hisProcInst.getId());
                if (CollectionUtil.isNotEmpty(midBusProcs)) {
                    MidBusProc midBusProc = midBusProcs.get(0);
                    String focusApprovePid = midBusProc.getFocusApprovePid();
                    Integer flagPid = Integer.valueOf(midBusProc.getFlagPid());
                    String taskId = hisProcInst.getTaskId();
                    String substring = taskId.substring(0, 1);
                    Map<String, Object> map = processData(substring, flagPid, focusApprovePid);
                    resultMap.putAll(map);
                }
            }
            dataList.add(resultMap);
            index++;
        }
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("total", pageInfo.getTotal());
        returnMap.put("rows", dataList);
        return returnMap;
    }

    /**
     * 统计所有的流程
     *
     * @param pageNumber
     * @param pageSize
     * @param procType
     * @param starterDeptName
     * @param starterName
     * @param status
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public PageInfo<Map<String, Object>> sumIns(Integer pageNumber, Integer pageSize, String procType, String starterDeptName, String starterName, String status, String startTime, String endTime) {
        PageHelper.startPage(pageNumber, pageSize);
        /**封装查询条件*/
        Map<String, Object> param = new HashMap<>();
        if (StrUtil.isNotBlank(procType)) {
            /**根据procType获取需要查看的流程*/
            param.put("procType", procType);
        }
        if (StrUtil.isNotBlank(starterDeptName)) {
            param.put("starterDeptName", starterDeptName);
        }
        if (StrUtil.isNotBlank(starterName)) {
            param.put("starterName", starterName);
        }
        if (StrUtil.isNotBlank(status)) {
            String[] statusArr = status.split(",");
            StringBuilder sb = new StringBuilder("(");
            for (int i = 0; i < statusArr.length; i++) {

                if (i == statusArr.length - 1) {
                    sb.append("'").append(statusArr[i]).append("'").append(")");
                } else {
                    sb.append("'").append(statusArr[i]).append("'").append(",");
                }
            }
            param.put("status", sb.toString());
        }
        if (StrUtil.isNotBlank(startTime)) {
            param.put("startTime", startTime);
        }
        if (StrUtil.isNotBlank(endTime)) {
            param.put("endTime", endTime);
        }
        List<Map<String, Object>> mapList = insStatisticsMapper.sumIns(param);
        /**处理审批概述 与部门名称*/
        if (CollectionUtil.isNotEmpty(mapList)) {
            for (Map<String, Object> map : mapList) {
                /**流程实例ID，关联备份表*/
                String id = (String) map.get("id");
                String procCode = (String) map.get("procCode");
                String insStatus = (String) map.get("status");
                String unitAndDeptName = "";
                /**处理发起部门信息*/
                String starterUnitCode = (String) map.get("starterUnitCode");
                String starterDeptName1 = (String) map.get("starterDeptName");
                Unit unit = new Unit();
                unit.setUnitCode(starterUnitCode);
                unit = unitService.get(unit);
                if (unit != null) {
                    unitAndDeptName = unit.getUnitName();
                }
                unitAndDeptName = unitAndDeptName.concat(starterDeptName1);
                map.put("starterDeptName", unitAndDeptName);
                String summarize = "";
                if ("40".equals(insStatus)) {
                    /**已完成的流程*/
                    /**taskId 非原始工作流的taskId,此处为发起流程时传入的值，审批完成后才会保存到流程中*/
                    String taskId = (String) map.get("taskId");
                    List<MidBusProc> midBusProcs = processQueryService.queryMidData(id);
                    if (CollectionUtil.isNotEmpty(midBusProcs)) {
                        summarize = queryFinishedData(midBusProcs.get(0), procCode, taskId);
                    }
                } else {
                    /**运行中或回退过的流程,查询流程数据表*/
                    ProcInstData procInstData = processQueryMapper.queryProcInstDataByprocInstId(id);
                    summarize = parseFormDate(procInstData, procCode);
                }
                map.put("summarize", summarize);
            }
        }
        return new PageInfo<>(mapList);
    }

    /**
     * 解析表单数据
     *
     * @param procInstData
     * @param procCode
     * @return
     */
    public String parseFormDate(ProcInstData procInstData, String procCode) {
        String summarize = "";
        StringBuilder sb = new StringBuilder();
        if (procInstData != null && StrUtil.isNotBlank(procInstData.getFormData())) {
            String formData = procInstData.getFormData();
            Map map = JSONObject.parseObject(formData, Map.class);
            //多条审批（集中审议）表单业务数据保存在data里
            Object data = map.get("data");
            if (data != null) {
                List<Map> maps = JSONObject.parseArray(data.toString(), Map.class);
                Map map1 = maps.get(0);
                if ("zyRel003".equals(procCode)) {
                    //关联关系
                    sb.append(map1.get("fromMemo"));
                } else if ("ZZ_Pro001".equals(procCode)) {
                    //金融产品，此处取产品名称
                    sb.append(map1.get("proName"));
                } else if ("ZZ_Org001".equals(procInstData)) {
                    //机构产品流程，此流程暂不取值
                } else if ("ZZ_taskDelegation".equals(procCode)) {
                    //不做处理
                } else if ("ZZ_AuthApproval".equals(procCode)) {
                    //不做处理
                } else {
                    //关联交易类型审批流程
                    sb.append(map1.get("tranName"));
                }
                //取交易类型
                if (maps.size() > 1) {
                    //一条数据，取业务数据的交易类型
                    sb.append("等").append(maps.size()).append("条");
                }
                summarize = sb.toString();
            } else {
                //直接取业务数据
                if ("zyRel003".equals(procCode)) {
                    summarize = (String) map.get("relName");
                } else if ("ZZ_Pro001".equals(procCode)) {
                    summarize = (String) map.get("proName");
                } else if ("ZZ_Org001".equals(procCode)) {
                } else if ("ZZ_taskDelegation".equals(procCode)) {
                    //不做处理
                } else if ("ZZ_AuthApproval".equals(procCode)) {
                    //不做处理
                } else {
                    summarize = (String) map.get("tranName");
                }
            }
        }
        return summarize;
    }

    /**
     * 已完成的流程查询数据
     *
     * @param busProc
     * @param procCode
     * @param taskId
     * @return
     */
    public String queryFinishedData(MidBusProc busProc, String procCode, String taskId) {
        String summarize = "";
        if (busProc == null) {
            return summarize;
        }
        StringBuilder sb = new StringBuilder(summarize);
        String focusApprovePid = busProc.getFocusApprovePid();
        String pidStr = null;
        int size = 0;
        if (StrUtil.isNotBlank(focusApprovePid)) {
            //多条数据
            String[] split = focusApprovePid.split(",");
            pidStr = split[0];
            size = split.length;
        } else {
            //单条数据,数据的pid
            pidStr = busProc.getFlagPid();
        }
        if ("zyRel003".equals(procCode)) {
            //关联关系
            Rel rel = relService.get(Integer.valueOf(pidStr));
            if (rel != null) {
                sb.append(rel.getFromMemo());
            }
        } else if ("ZZ_Pro001".equals(procCode)) {
            //金融产品，此处取产品名称
            RelPro relPro = relProService.get(Integer.valueOf(pidStr));
            if (relPro != null) {
                sb.append(relPro.getProName());
            }
        } else if ("ZZ_Org001".equals(procCode)) {
            //机构产品流程，此流程暂不取值
        } else if ("ZZ_taskDelegation".equals(procCode)) {
            //不做处理
        } else if ("ZZ_AuthApproval".equals(procCode)) {
            //不做处理
        } else {
            //关联交易类型审批流程
            //根据taskId的值判断是什么类型的数据
            String substring = taskId.substring(0, 1);
            if ("1".equals(substring)) {
                TraInvestment traInterest = traInvestmentService.get(Integer.valueOf(pidStr));
                if (traInterest != null) {
                    sb.append(traInterest.getTranName());
                }
            } else if ("2".equals(substring)) {
                TraFunds traFunds = traFundsService.get(Integer.valueOf(pidStr));
                if (traFunds != null) {
                    sb.append(traFunds.getTranName());
                }
            } else if ("3".equals(substring)) {
                TraBusiness traBusiness = traBusinessService.get(Integer.valueOf(pidStr));
                if (traBusiness != null) {
                    sb.append(traBusiness.getTranName());
                }
            } else if ("4".equals(substring)) {
                TraInterest traInterest = traInterestService.get(Integer.valueOf(pidStr));
                if (traInterest != null) {
                    sb.append(traInterest.getTranName());
                }
            } else if ("5".equals(substring)) {
                TraService traService = traServiceService.get(Integer.valueOf(pidStr));
                if (traService != null) {
                    sb.append(traService.getTranName());
                }
            } else if ("6".equals(substring)) {
                TraOther traOther = traOtherService.get(Integer.valueOf(pidStr));
                if (traOther != null) {
                    sb.append(traOther.getTranName());
                }
            } else {
                return sb.toString();
            }
        }
        if (StrUtil.isNotBlank(focusApprovePid)) {
            if (size > 1) {
                sb.append("等").append(size).append("条数据");
            }
        }
        return sb.toString();
    }

    /**
     * 处理查询条件
     *
     * @param param
     * @param year
     * @param quarter
     * @param startTime
     * @param endTime
     */
    private void processQueryConditions(Map<String, Object> param, String year, String quarter, String startTime, String endTime) {
        //年
        if (StrUtil.isNotBlank(year)) {
            param.put("year", year.trim().concat("%"));
        } else {
            int year1 = DateUtil.year(new Date());
            year = String.valueOf(year1);
            param.put("year", String.valueOf(year1).concat("%"));
        }
        //季度
        if (StrUtil.isNotBlank(quarter)) {
            int ofYear = DateUtil.year(new Date());
            String beginQuarter = null;
            String endQuarter = null;
            //季度endQuarter拼接最大时间
            if ("1".equals(quarter)) {
                beginQuarter = year.concat("-01-01 00:00:00");
                endQuarter = year.concat("-04-01 00:00:00");
            } else if ("2".equals(quarter)) {
                beginQuarter = year.concat("-04-01 00:00:00");
                endQuarter = year.concat("-07-01 00:00:00");
            } else if ("3".equals(quarter)) {
                beginQuarter = year.concat("-07-01 00:00:00");
                endQuarter = year.concat("-10-01 00:00:00");
            } else {
                beginQuarter = year.concat("-10-01 00:00:00");
                //年加一
                int addYear = ofYear + 1;
                endQuarter = String.valueOf(addYear).concat("-01-01 00:00:00");
            }
            param.put("beginQuarter", beginQuarter);
            param.put("endQuarter", endQuarter);
        }
        if (StrUtil.isNotBlank(startTime)) {
            param.put("startTime", startTime);
        }
        if (StrUtil.isNotBlank(endTime)) {
            //时间加一天
            param.put("endTime", endTime.concat(" 23:59:59"));
        }
    }


    private Map<String, Object> processData(String flag, Integer flagPid, String pids) {
        Map<String, Object> resultMap = new HashMap<>();
        String tranDate = "";
        //关联方名称
        String relName = "";
        //交易对手名称
        String tranOpponent = "";
        String tranName = "";
        String pracTradeAmount = "";
        //交易类型(重大、一般、集中审议、统一交易协议、统一交易协议执行情况。)
        String tranType = "";
        //交易金额
        String tradeAmount = "";
        BigDecimal relTradeAmount = new BigDecimal(0);

        if ("1".equals(flag)) {
            TraInvestment traInvestment = traInvestmentService.get(flagPid);
            if (traInvestment != null) {
                //交易时间
                tranDate = traInvestment.getTranDateString();
                relTradeAmount = traInvestment.getRelTradeAmount();
                //关联方名称(交易对手名称)
                String relInfoListStr = traInvestment.getRelInfoListStr();
                Map<String, Object> map = getRelNames(relInfoListStr);
                relName = (String) map.get("relName");
                tranOpponent = (String) map.get("tranOpponent");
                //交易名称
                tranName = traInvestment.getTranName();
                //交易金额
                pracTradeAmount = traInvestment.getPracTradeAmount();
                if (StrUtil.isNotBlank(pracTradeAmount)) {
                    tradeAmount = pracTradeAmount;
                } else {
                    tradeAmount = traInvestment.getRelTradeAmount().toString();
                }
                //是否重大
                String majorTrade = traInvestment.getMajorTrade();
                if ("0".equals(majorTrade)) {
                    //重大关联交易
                    tranType = "重大";
                } else {
                    tranType = "一般";
                }
            }
        } else if ("2".equals(flag)) {
            TraFunds traFunds = traFundsService.get(flagPid);
            if (traFunds != null) {
                //交易时间
                tranDate = traFunds.getTranDateString();
                //关联方名称
                String relInfoListStr = traFunds.getRelInfoListStr();
                Map<String, Object> map = getRelNames(relInfoListStr);
                relName = (String) map.get("relName");
                tranOpponent = (String) map.get("tranOpponent");
                //交易名称
                tranName = traFunds.getTranName();
                //交易金额
                pracTradeAmount = traFunds.getPracTradeAmount();
                relTradeAmount = traFunds.getRelTradeAmount();
                if (StrUtil.isNotBlank(pracTradeAmount)) {
                    tradeAmount = pracTradeAmount;
                } else {
                    tradeAmount = traFunds.getContractAmount().toString();
                }
                //是否重大
                String majorTrade = traFunds.getMajorTrade();
                if ("0".equals(majorTrade)) {
                    //重大关联交易
                    tranType = "重大";
                } else {
                    tranType = "一般";
                }
            }
        } else if ("3".equals(flag)) {
            TraBusiness traBusiness = traBusinessService.get(flagPid);
            if (traBusiness != null) {
                //交易时间
                tranDate = traBusiness.getTranDateString();
                relTradeAmount = traBusiness.getRelTradeAmount();
                //关联方名称
                String relInfoListStr = traBusiness.getRelInfoListStr();
                Map<String, Object> map = getRelNames(relInfoListStr);
                relName = (String) map.get("relName");
                tranOpponent = (String) map.get("tranOpponent");
                //交易名称
                tranName = traBusiness.getTranName();
                //交易金额
                pracTradeAmount = traBusiness.getPracTradeAmount();
                if (StrUtil.isNotBlank(pracTradeAmount)) {
                    tradeAmount = pracTradeAmount;
                } else {
                    tradeAmount = traBusiness.getContractAmount().toString();
                }
                //是否重大
                String majorTrade = traBusiness.getMajorTrade();
                if ("0".equals(majorTrade)) {
                    //重大关联交易
                    tranType = "重大";
                } else {
                    tranType = "一般";
                }
                //是否是统一交易协议
                String isAgreement = traBusiness.getIsAgreement();
                if ("0".equals(isAgreement)) {
                    //统一交易协议执行情况
                    String isAgreementImpl = traBusiness.getIsAgreementImpl();
                    if ("0".equals(isAgreementImpl)) {
                        tranType = "统一交易协议执行情况";
                    } else {
                        tranType = "统一交易协议";
                    }
                }
            }
        } else if ("4".equals(flag)) {
            TraInterest traInterest = traInterestService.get(flagPid);
            if (traInterest != null) {
                //交易时间
                tranDate = traInterest.getTranDateString();
                relTradeAmount = traInterest.getRelTradeAmount();
                //关联方名称
                String relInfoListStr = traInterest.getRelInfoListStr();
                Map<String, Object> map = getRelNames(relInfoListStr);
                relName = (String) map.get("relName");
                tranOpponent = (String) map.get("tranOpponent");
                //交易名称
                tranName = traInterest.getTranName();
                //交易金额
                pracTradeAmount = traInterest.getPracTradeAmount();
                if (StrUtil.isNotBlank(pracTradeAmount)) {
                    tradeAmount = pracTradeAmount;
                } else {
                    tradeAmount = traInterest.getContractAmount().toString();
                }
                //是否重大
                String majorTrade = traInterest.getMajorTrade();
                if ("0".equals(majorTrade)) {
                    //重大关联交易
                    tranType = "重大";
                } else {
                    tranType = "一般";
                }
                String isAgreement = traInterest.getIsAgreement();
                if ("0".equals(isAgreement)) {
                    //统一交易协议执行情况
                    String isAgreementImpl = traInterest.getIsAgreementImpl();
                    if ("0".equals(isAgreementImpl)) {
                        tranType = "统一交易协议执行情况";
                    } else {
                        tranType = "统一交易协议";
                    }
                }
            }
        } else if ("5".equals(flag)) {
            TraService traService = traServiceService.get(flagPid);
            if (traService != null) {
                //交易时间
                tranDate = traService.getTranDateString();
                relTradeAmount = traService.getRelTradeAmount();
                //关联方名称
                String relInfoListStr = traService.getRelInfoListStr();
                Map<String, Object> map = getRelNames(relInfoListStr);
                relName = (String) map.get("relName");
                tranOpponent = (String) map.get("tranOpponent");
                //交易名称
                tranName = traService.getTranName();
                //交易金额
                pracTradeAmount = traService.getPracTradeAmount();
                if (StrUtil.isNotBlank(pracTradeAmount)) {
                    tradeAmount = pracTradeAmount;
                } else {
                    tradeAmount = traService.getContractAmount().toString();
                }
                //是否重大
                String majorTrade = traService.getMajorTrade();
                if ("0".equals(majorTrade)) {
                    //重大关联交易
                    tranType = "重大";
                } else {
                    tranType = "一般";
                }
                String isAgreement = traService.getIsAgreement();
                if ("0".equals(isAgreement)) {
                    //统一交易协议执行情况
                    String isAgreementImpl = traService.getIsAgreementImpl();
                    if ("0".equals(isAgreementImpl)) {
                        tranType = "统一交易协议执行情况";
                    } else {
                        tranType = "统一交易协议";
                    }
                }
            }
        } else if ("6".equals(flag)) {
            TraOther traOther = traOtherService.get(flagPid);
            if (traOther != null) {
                //交易时间
                tranDate = traOther.getTranDateString();
                relTradeAmount = traOther.getRelTradeAmount();
                //关联方名称
                String relInfoListStr = traOther.getRelInfoListStr();
                Map<String, Object> map = getRelNames(relInfoListStr);
                relName = (String) map.get("relName");
                tranOpponent = (String) map.get("tranOpponent");
                //交易名称
                tranName = traOther.getTranName();
                //交易金额
                pracTradeAmount = traOther.getPracTradeAmount();
                if (StrUtil.isNotBlank(pracTradeAmount)) {
                    tradeAmount = pracTradeAmount;
                } else {
                    tradeAmount = traOther.getContractAmount().toString();
                }
                //是否重大
                String majorTrade = traOther.getMajorTrade();
                if ("0".equals(majorTrade)) {
                    //重大关联交易
                    tranType = "重大";
                } else {
                    tranType = "一般";
                }
            }
        }
        //设置交易类型
        if (StrUtil.isNotBlank(pids)) {
            //集中审议,拼接“等”
            String[] split = pids.split(",");
            if (split.length > 1) {
                if (StrUtil.isNotBlank(relName)) {
                    relName = new StringBuilder(relName).append("等").append(split.length).append("个关联方").toString();
                }
                if (StrUtil.isNotBlank(tranOpponent)) {
                    tranOpponent = new StringBuilder(tranOpponent).append("等").append(split.length).append("个交易对手").toString();
                }
                if (StrUtil.isNotBlank(tranName)) {
                    tranName = new StringBuilder(tranName).append("等").append(split.length).append("条数据").toString();
                }
            }
            tranType = "集中审议";
        }
        resultMap.put("tranDate", tranDate);
        resultMap.put("relName", relName);
        resultMap.put("tranOpponent", tranOpponent);
        resultMap.put("tranName", tranName);
        resultMap.put("tradeAmount", relTradeAmount);
        resultMap.put("tranType", tranType);
        return resultMap;
    }

    private Map<String, Object> getRelNames(List<TraRelInfo> traRelInfos) {
        Map<String, Object> map = new HashMap<>();
        //关联方名称
        String relName = "";
        //交易对手名称
        String tranOpponent = "";
        if (CollectionUtil.isNotEmpty(traRelInfos)) {
            StringBuilder sb1 = new StringBuilder(relName);
            StringBuilder sb2 = new StringBuilder(tranOpponent);
            for (int i = 0; i < traRelInfos.size(); i++) {
                if (i != traRelInfos.size() - 1) {
                    sb1.append(traRelInfos.get(i).getRelName()).append("、");
                    sb2.append(traRelInfos.get(i).getTranOpponent()).append("、");
                } else {
                    sb1.append(traRelInfos.get(i).getRelName());
                    sb2.append(traRelInfos.get(i).getTranOpponent());
                }
            }
            relName = sb1.toString();
            tranOpponent = sb2.toString();
        }
        map.put("relName", relName);
        map.put("tranOpponent", tranOpponent);
        return map;
    }

    /**
     * 获取关联交易人名称
     *
     * @param relInfoListStr
     * @return
     */
    private Map<String, Object> getRelNames(String relInfoListStr) {
        Map<String, Object> map = new HashMap<>();
        //关联方名称
        String relName = "";
        //交易对手名称
        String tranOpponent = "";
        if (StrUtil.isNotBlank(relInfoListStr)) {
            List<TraRelInfo> traRelInfos = JSONObject.parseArray(relInfoListStr, TraRelInfo.class);
            StringBuilder sb1 = new StringBuilder(relName);
            StringBuilder sb2 = new StringBuilder(tranOpponent);
            for (int i = 0; i < traRelInfos.size(); i++) {
                if (i != traRelInfos.size() - 1) {
                    sb1.append(traRelInfos.get(i).getRelName()).append("、");
                    sb2.append(traRelInfos.get(i).getTranOpponent()).append("、");
                } else {
                    sb1.append(traRelInfos.get(i).getRelName());
                    sb2.append(traRelInfos.get(i).getTranOpponent());
                }
            }
            relName = sb1.toString();
            tranOpponent = sb2.toString();
        }
        map.put("relName", relName);
        map.put("tranOpponent", tranOpponent);
        return map;
    }

    /**
     * 解析form表单数据
     *
     * @param formData
     * @return
     */
    private Map parseJsonData(String formData) {
        //返回数据结构
        Map<String, Object> dataMap = new HashMap<>();
        Map<String, Object> map = null;
        map = JSON.parseObject(formData, Map.class);
        //集中审议时将多数据以json字符串的方式封装到原对象中
        String pids = (String) map.get("pids");
        String tranType = (String) map.get("tranType");
        String data = (String) map.get("data");
        if (StrUtil.isBlank(tranType)) {
            return dataMap;
        }
        Integer size = 1;
        if (StrUtil.isNotBlank(data)) {
            //集中审议数据
            List<Map> mapList = JSONObject.parseArray(data, Map.class);
            if (CollectionUtil.isNotEmpty(mapList)) {
                size = mapList.size();
                Map<String, Object> mapObj = mapList.get(0);
                map = mapObj;
            }
        }
        if (map != null) {
            dataMap = processData(map, formData, size);
        }
        return dataMap;
    }

    /**
     * 处理运行时的json数据
     *
     * @param map
     * @param formData
     * @return
     */
    private Map<String, Object> processData(Map<String, Object> map, String formData, Integer size) {
        Map<String, Object> resultMap = new HashMap<>();
        String tranDate = "";
        //关联方名称
        String relName = "";
        //交易对手名称
        String tranOpponent = "";
        String tranName = "";
        String pracTradeAmount = "";
        //交易类型(重大、一般、集中审议、统一交易协议、统一交易协议执行情况。)
        String tranType = "";
        //交易金额
        String tradeAmount = "";
        BigDecimal relTradeAmount = new BigDecimal(0).setScale(6, BigDecimal.ROUND_UP);
        /**对应哪个表*/
        String flag = (String) map.get("tranType");
        if (StrUtil.isBlank(flag)) {
            return resultMap;
        }
        if ("01".equals(flag)) {
            TraInvestment traInvestment = JSONObject.parseObject(formData, TraInvestment.class);
            if (traInvestment != null) {
                //交易时间
                tranDate = traInvestment.getTranDateString();
                relTradeAmount = traInvestment.getRelTradeAmount();
                //关联方名称(交易对手名称)
                Map<String, Object> mapName = getRelNames(traInvestment.getRelInfoList());
                relName = (String) mapName.get("relName");
                tranOpponent = (String) mapName.get("tranOpponent");
                //交易名称
                tranName = traInvestment.getTranName();
                //交易金额
                pracTradeAmount = traInvestment.getPracTradeAmount();
                if (StrUtil.isNotBlank(pracTradeAmount)) {
                    tradeAmount = pracTradeAmount;
                } else {
                    tradeAmount = traInvestment.getContractAmount().toString();
                }
                //是否重大
                String majorTrade = traInvestment.getMajorTrade();
                if ("0".equals(majorTrade)) {
                    //重大关联交易
                    tranType = "重大";
                } else {
                    tranType = "一般";
                }
            }
        } else if ("02".equals(flag)) {
            TraFunds traFunds = JSONObject.parseObject(formData, TraFunds.class);
            if (traFunds != null) {
                //交易时间
                tranDate = traFunds.getTranDateString();
                relTradeAmount = traFunds.getRelTradeAmount();
                //关联方名称
                Map<String, Object> mapName = getRelNames(traFunds.getRelInfoList());
                relName = (String) mapName.get("relName");
                tranOpponent = (String) mapName.get("tranOpponent");
                //交易名称
                tranName = traFunds.getTranName();
                //交易金额
                pracTradeAmount = traFunds.getPracTradeAmount();
                if (StrUtil.isNotBlank(pracTradeAmount)) {
                    tradeAmount = pracTradeAmount;
                } else {
                    tradeAmount = traFunds.getContractAmount().toString();
                }
                //是否重大
                String majorTrade = traFunds.getMajorTrade();
                if ("0".equals(majorTrade)) {
                    //重大关联交易
                    tranType = "重大";
                } else {
                    tranType = "一般";
                }
            }
        } else if ("03".equals(flag)) {
            TraBusiness traBusiness = JSONObject.parseObject(formData, TraBusiness.class);
            if (traBusiness != null) {
                //交易时间
                tranDate = traBusiness.getTranDateString();
                //交易对手名称
                relName = traBusiness.getRelName();
                //关联方名称
                Map<String, Object> mapName = getRelNames(traBusiness.getRelInfoList());
                relName = (String) mapName.get("relName");
                tranOpponent = (String) mapName.get("tranOpponent");
                //交易名称
                tranName = traBusiness.getTranName();
                relTradeAmount = traBusiness.getRelTradeAmount();
                //交易金额
                pracTradeAmount = traBusiness.getPracTradeAmount();
                if (StrUtil.isNotBlank(pracTradeAmount)) {
                    tradeAmount = pracTradeAmount;
                } else {
                    tradeAmount = traBusiness.getContractAmount().toString();
                }
                //是否重大
                String majorTrade = traBusiness.getMajorTrade();
                if ("0".equals(majorTrade)) {
                    //重大关联交易
                    tranType = "重大";
                } else {
                    tranType = "一般";
                }
                //是否是统一交易协议
                String isAgreement = traBusiness.getIsAgreement();
                if ("0".equals(isAgreement)) {
                    //统一交易协议执行情况
                    String isAgreementImpl = traBusiness.getIsAgreementImpl();
                    if ("0".equals(isAgreementImpl)) {
                        tranType = "统一交易协议执行情况";
                    } else {
                        tranType = "统一交易协议";
                    }
                }
            }
        } else if ("04".equals(flag)) {
            TraInterest traInterest = JSONObject.parseObject(formData, TraInterest.class);
            if (traInterest != null) {
                //交易时间
                tranDate = traInterest.getTranDateString();
                relTradeAmount = traInterest.getRelTradeAmount();
                //关联方名称
                Map<String, Object> mapName = getRelNames(traInterest.getRelInfoList());
                relName = (String) mapName.get("relName");
                tranOpponent = (String) mapName.get("tranOpponent");
                //交易名称
                tranName = traInterest.getTranName();
                //交易金额
                pracTradeAmount = traInterest.getPracTradeAmount();
                if (StrUtil.isNotBlank(pracTradeAmount)) {
                    tradeAmount = pracTradeAmount;
                } else {
                    tradeAmount = traInterest.getContractAmount().toString();
                }
                //是否重大
                String majorTrade = traInterest.getMajorTrade();
                if ("0".equals(majorTrade)) {
                    //重大关联交易
                    tranType = "重大";
                } else {
                    tranType = "一般";
                }
                String isAgreement = traInterest.getIsAgreement();
                if ("0".equals(isAgreement)) {
                    //统一交易协议执行情况
                    String isAgreementImpl = traInterest.getIsAgreementImpl();
                    if ("0".equals(isAgreementImpl)) {
                        tranType = "统一交易协议执行情况";
                    } else {
                        tranType = "统一交易协议";
                    }
                }
            }
        } else if ("05".equals(flag)) {
            TraService traService = JSONObject.parseObject(formData, TraService.class);
            if (traService != null) {
                //交易时间
                tranDate = traService.getTranDateString();
                relTradeAmount = traService.getRelTradeAmount();
                //关联方名称
                Map<String, Object> mapName = getRelNames(traService.getRelInfoList());
                relName = (String) mapName.get("relName");
                tranOpponent = (String) mapName.get("tranOpponent");
                //交易名称
                tranName = traService.getTranName();
                //交易金额
                pracTradeAmount = traService.getPracTradeAmount();
                if (StrUtil.isNotBlank(pracTradeAmount)) {
                    tradeAmount = pracTradeAmount;
                } else {
                    tradeAmount = traService.getContractAmount().toString();
                }
                //是否重大
                String majorTrade = traService.getMajorTrade();
                if ("0".equals(majorTrade)) {
                    //重大关联交易
                    tranType = "重大";
                } else {
                    tranType = "一般";
                }
                String isAgreement = traService.getIsAgreement();
                if ("0".equals(isAgreement)) {
                    //统一交易协议执行情况
                    String isAgreementImpl = traService.getIsAgreementImpl();
                    if ("0".equals(isAgreementImpl)) {
                        tranType = "统一交易协议执行情况";
                    } else {
                        tranType = "统一交易协议";
                    }
                }
            }
        } else if ("06".equals(flag)) {
            TraOther traOther = JSONObject.parseObject(formData, TraOther.class);
            if (traOther != null) {
                //交易时间
                tranDate = traOther.getTranDateString();
                relTradeAmount = traOther.getRelTradeAmount();
                //关联方名称
                Map<String, Object> mapName = getRelNames(traOther.getRelInfoList());
                relName = (String) mapName.get("relName");
                tranOpponent = (String) mapName.get("tranOpponent");
                //交易名称
                tranName = traOther.getTranName();
                //交易金额
                pracTradeAmount = traOther.getPracTradeAmount();
                if (StrUtil.isNotBlank(pracTradeAmount)) {
                    tradeAmount = pracTradeAmount;
                } else {
                    tradeAmount = traOther.getContractAmount().toString();
                }
                //是否重大
                String majorTrade = traOther.getMajorTrade();
                if ("0".equals(majorTrade)) {
                    //重大关联交易
                    tranType = "重大";
                } else {
                    tranType = "一般";
                }
            }
        }
        //设置交易类型
        if (size > 1) {
            //集中审议,拼接“等”
            if (StrUtil.isNotBlank(relName)) {
                relName = new StringBuilder(relName).append("等").append(size).append("个关联方").toString();
            }
            if (StrUtil.isNotBlank(tranOpponent)) {
                tranOpponent = new StringBuilder(tranOpponent).append("等").append(size).append("个交易对手").toString();
            }
            if (StrUtil.isNotBlank(tranName)) {
                tranName = new StringBuilder(tranName).append("等").append(size).append("条数据").toString();
            }
            tranType = "集中审议";
        }
        resultMap.put("tranDate", tranDate);
        resultMap.put("relName", relName);
        resultMap.put("tranOpponent", tranOpponent);
        resultMap.put("tranName", tranName);
        resultMap.put("tradeAmount", relTradeAmount);
        resultMap.put("tranType", tranType);
        return resultMap;
    }
}
