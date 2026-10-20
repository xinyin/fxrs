package com.yzj.jep.app.sub.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.model.Rel;
import com.yzj.jep.app.bus.model.RelPro;
import com.yzj.jep.app.bus.service.RelProService;
import com.yzj.jep.app.bus.service.RelService;
import com.yzj.jep.app.ins.model.*;
import com.yzj.jep.app.ins.service.*;
import com.yzj.jep.app.mid.service.BusProcService;
import com.yzj.jep.app.sub.mapper.ProcessQueryMapper;
import com.yzj.jep.app.sub.model.MidBusProc;
import com.yzj.jep.app.sub.service.ProcessQueryService;
import com.yzj.jep.app.sub.vo.HisProcInstVo;
import com.yzj.jep.app.sub.vo.TaskInstVo;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.workflow.api.model.IProcInst;
import com.yzj.jep.workflow.impl.model.TaskInstImpl;
import com.yzj.jep.workflow.wf.model.*;
import com.yzj.jep.workflow.wf.service.ProcAutoTransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: ProcessServiceImpl
 * @Author Whx
 * @Date: 2021/7/15 10:35
 * @Version 1.0
 */
@Service
public class ProcessQueryServiceImpl implements ProcessQueryService {

    private static Logger logger = LoggerFactory.getLogger(ProcessQueryServiceImpl.class);


    private static final String TASK_TYPE_1 = "自有任务";
    private static final String TASK_TYPE_2 = "委托任务";

    @Autowired
    private ProcessQueryMapper processQueryMapper;

    @Autowired
    private BusProcService busProcService;

    @Autowired
    private RelService relService;

    @Autowired
    private RelProService relProService;

    @Autowired
    private TraInvestmentService traInvestmentService;

    @Autowired
    private TraFundsService traFundsService;

    @Autowired
    private TraServiceService traServiceService;

    @Autowired
    private TraInterestService traInterestService;

    @Autowired
    private TraOtherService traOtherService;

    @Autowired
    private TraBusinessService traBusinessService;
    @Autowired
    private ProcAutoTransferService procAutoTransferService;

    @Override
    public List<HisProcInstVo> queryTrustDetail(List<HisProcInst> result) {
        UserProfile user = UserProfileUtils.getUserProfile();
        String staffCode = user.getStaffCode();
        String staffName = user.getStaffName();
        List<HisProcInstVo> list = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(result)) {
            for (HisProcInst hisProcInst : result) {
                String procCode = hisProcInst.getProcCode();
                HisProcInstVo hisProcInstVo = new HisProcInstVo(hisProcInst);
                //查询审批日志表完成任务记录
                List<HisProcLog> hisProcLogList = processQueryMapper.queryNextStafIsNotUser(hisProcInst.getProcessInstantId(), staffCode);
                if (CollectionUtil.isEmpty(hisProcLogList)) {
                    //自有任务
                    hisProcInstVo.setTaskType(TASK_TYPE_1);
                } else {
                    //找到自己审批的节点，以最后一个完成审批的节点为准
                    Integer selfCompleted = null;
                    for (int i = 0; i < hisProcLogList.size(); i++) {
                        HisProcLog hisProcLog = hisProcLogList.get(i);
                        if (staffCode.equals(hisProcLog.getStaffCode())) {
                            selfCompleted = i;
                        }
                    }
                    if (selfCompleted == null || selfCompleted == 0) {
                        hisProcInstVo.setTaskType(TASK_TYPE_1);
                    } else {
                        //获取上一个节点的审批人
                        HisProcLog hisProcLog = hisProcLogList.get(selfCompleted - 1);
                        String nextStaff = hisProcLog.getNextStaff();
                        if (!nextStaff.contains(staffCode) || nextStaff.contains(staffName.concat("（代理"))) {
                            //委托任务
                            hisProcInstVo.setTaskType(TASK_TYPE_2);
                            //确定委托任务，查询审批时的时间点对应的审批人
                            //审批时间
                            String logTimeString = hisProcLogList.get(selfCompleted).getLogTimeString();
                            List<ProcAutoTransfer> tranList = processQueryMapper.queryTrans(staffCode, logTimeString);
                            if (CollectionUtil.isNotEmpty(tranList)) {
                                for (ProcAutoTransfer procAutoTransfer : tranList) {
                                    if (nextStaff.contains(procAutoTransfer.getStaffCode())) {
                                        hisProcInstVo.setTrustCode(procAutoTransfer.getStaffCode());
                                        hisProcInstVo.setTrustName(procAutoTransfer.getStaffName());
                                    }
                                }
                            }
                        } else {
                            //自有任务
                            hisProcInstVo.setTaskType(TASK_TYPE_1);
                        }
                    }
                }
               /* List<HisProcLog> hisProcLogList = processQueryMapper.queryHisProcLogByInsId(hisProcInstVo.getProcessInstantId(), "TRANSFER", userid);
                if (CollectionUtil.isNotEmpty(hisProcLogList)) {
                    hisProcInstVo.setTaskType(TASK_TYPE_2);
                    HisProcLog hisProcLog = hisProcLogList.get(0);
                    String nextStaff = hisProcLog.getNextStaff();
                    String[] split = nextStaff.split("[\\\\(\\\\)]");
                    *//*if (StrUtil.isNotBlank(nextStaff)) {
                        hisProcInstVo.setTrustCode(split[1]);
                        hisProcInstVo.setTrustName(split[0]);
                    }*//*
                    hisProcInstVo.setTrustCode(hisProcLog.getStaffCode());
                    hisProcInstVo.setTrustName(hisProcLog.getStaffName());
                } else {
                    hisProcInstVo.setTaskType(TASK_TYPE_1);
                }*/
                //处理表单数据
                if ("30".equals(hisProcInst.getStatus()) || "20".equals(hisProcInst.getStatus())) {
                    //未结束的流程查询表格数据
                    // TODO: 2021/7/16 结束的流程后续在备份表格中取
                    String procInstId = hisProcInst.getId();
                    //查询表单数据
                    ProcInstData procInstData = processQueryMapper.queryProcInstDataByprocInstId(procInstId);
                    //解析表但数据
                    String summarize = parseFormDate(procInstData, procCode);
                    hisProcInstVo.setSummarize(summarize);
                } else {
                    //审批流程已结束，查询流程与数据的中间表
                    List<MidBusProc> midBusProcs = this.queryMidData(hisProcInst.getId());
                    String summarize = queryFinishedData(CollectionUtil.isNotEmpty(midBusProcs) ? midBusProcs.get(0) : null, procCode, hisProcInst.getTaskId());
                    hisProcInstVo.setSummarize(summarize);
                }
                list.add(hisProcInstVo);
            }
        }
        return list;
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
            if(size>1){
                sb.append("等").append(size).append("条数据");
            }
        }
        return sb.toString();
    }

    /**
     * 运行中的任务处理
     *
     * @param result
     * @return
     */
    @Override
    public List<TaskInstVo> queryTaskType(List<ProcInst> result) {
        //获取当前登录用户
        UserProfile profile = UserProfileUtils.getUserProfile();
        //当前用户
        String staffCode = profile.getStaffCode();
        String staffName = profile.getStaffName();
        //查询我被委托
        List<Filter> filters1 = new ArrayList<>();
        filters1.add(new Filter("transferStaffCode", Operator.EQ, staffCode));
        List<ProcAutoTransfer> procAutoTransfers1 = procAutoTransferService.queryTransfer(filters1);
        //查询我委托
        List<Filter> filters2 = new ArrayList<>();
        filters2.add(new Filter("staffCode", Operator.EQ, staffCode));
        List<ProcAutoTransfer> procAutoTransfers2 = procAutoTransferService.queryTransfer(filters2);
        //我被别人委托
        Map<String, Object> toMap = getTransFer(procAutoTransfers1, "to");
        //我委托给别人
        Map<String, Object> fromMap = getTransFer(procAutoTransfers2, "from");
        //查询被委托人表
        List<TaskInstVo> list = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(result)) {
            for (IProcInst procInst : result) {
                TaskInstImpl taskInst = (TaskInstImpl) procInst;
                TaskInstVo taskInstVo = new TaskInstVo(taskInst);
                BeanUtils.copyProperties(taskInst, taskInstVo);
                //查询审批记录表，仅查询转发的数据
                String taskId = taskInst.getTaskId();


                List<HisProcLog> hisProcLogList = processQueryMapper.queryTaskImplByTaskId(null, null, taskId);
                if (CollectionUtil.isNotEmpty(hisProcLogList)) {
                    //设置节点审批类型
                    if (toMap == null) {
                        //自有任务，设置委托人
                        taskInstVo.setTaskType(TASK_TYPE_1);
                        if (fromMap != null) {
                            taskInstVo.setTrustCode(CollectionUtil.join((List<String>) fromMap.get("codes"), ","));
                            taskInstVo.setTrustName(CollectionUtil.join((List<String>) fromMap.get("names"), ","));
                        }
                    } else {
                        //判断是否是自己的任务
                        HisProcLog hisProcLog = hisProcLogList.get(0);
                        //设置委托人
                        String nextStaff = hisProcLog.getNextStaff();

                        if (nextStaff.contains(staffCode) && !nextStaff.contains(staffName.concat("（代理"))) {
                            //自有任务，设置委托人
                            taskInstVo.setTaskType(TASK_TYPE_1);
                            if (fromMap != null) {
                                taskInstVo.setTrustCode(CollectionUtil.join((List<String>) fromMap.get("codes"), ","));
                                taskInstVo.setTrustName(CollectionUtil.join((List<String>) fromMap.get("names"), ","));
                            }
                        } else {
                            taskInstVo.setTaskType(TASK_TYPE_2);
                            //委托任务，查找委托人
                            List<String> newCodes = new ArrayList<>();
                            List<String> newNames = new ArrayList<>();
                            List<String> codes = (List<String>) toMap.get("codes");
                            List<String> names = (List<String>) toMap.get("names");
                            for (String code : codes) {
                                if (nextStaff.contains(code)) {
                                    newCodes.add(code);
                                }
                            }
                            for (String name : names) {
                                if (nextStaff.contains(name)) {
                                    newNames.add(name);
                                }
                            }
                            taskInstVo.setTrustCode(CollectionUtil.join(newCodes, ","));
                            taskInstVo.setTrustName(CollectionUtil.join(newNames, ","));
                        }
                    }

                } else {
                    taskInstVo.setTaskType(TASK_TYPE_1);
                    if (fromMap != null) {
                        taskInstVo.setTrustCode(CollectionUtil.join((List<String>) fromMap.get("codes"), ","));
                        taskInstVo.setTrustName(CollectionUtil.join((List<String>) fromMap.get("names"), ","));
                    }
                }
                //设置审批概述，当前业务为单条审批取数据的交易类型，集中审议取数据第一条拼接数据的数量
                //查询wf_proc_inst_data 该表只有运行时的数据。审批中的数据存在
                //工作流表中对应的该流程实例的ID
                String procInstId = procInst.getId();
                //查询表单数据
                ProcInstData procInstData = processQueryMapper.queryProcInstDataByprocInstId(procInstId);
                //解析表但数据
                String summarize = parseFormDate(procInstData, procInst.getProcCode());
                taskInstVo.setSummarize(summarize);
                list.add(taskInstVo);
            }
        }
        return list;
    }

    private Map<String, Object> getTransFer(List<ProcAutoTransfer> procAutoTransfers, String flag) {
        if (CollectionUtil.isEmpty(procAutoTransfers)) {
            return null;
        }
        List<String> codes = new ArrayList<>();
        List<String> names = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        if ("to".equals(flag)) {
            //查询我被委托的原委托人
            for (ProcAutoTransfer procAutoTransfer : procAutoTransfers) {
                codes.add(procAutoTransfer.getStaffCode());
                names.add(procAutoTransfer.getStaffName());
            }
            map.put("codes", codes);
            map.put("names", names);
        } else {
            //查询我委托的人
            for (ProcAutoTransfer procAutoTransfer : procAutoTransfers) {
                codes.add(procAutoTransfer.getTransferStaffCode());
                names.add(procAutoTransfer.getTransferStaffName());
            }
            map.put("codes", codes);
            map.put("names", names);
        }
        return map;
    }

    @Override
    public List<MidBusProc> queryMidData(String procInsId) {
        return processQueryMapper.queryMidData(procInsId);
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
}
