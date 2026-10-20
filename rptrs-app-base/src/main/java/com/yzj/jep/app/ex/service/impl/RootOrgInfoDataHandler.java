package com.yzj.jep.app.ex.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.model.RootOrgInfo;
import com.yzj.jep.app.bus.service.RootOrgInfoService;
import com.yzj.jep.app.fil.model.FilRootOrgInfo;
import com.yzj.jep.app.fil.service.FilRootOrgInfoService;
import com.yzj.jep.app.mid.model.BusProc;
import com.yzj.jep.app.mid.service.BusProcService;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.client.ProcessDataHandler;
import com.yzj.jep.workflow.client.annotation.DataHandler;

/**
 * @title: RootOrgInfoDataHandler
 * @Author Whx
 * @Date: 2021/7/20 9:41
 * @Version 1.0
 */
@DataHandler("ZZ_Org001")
public class RootOrgInfoDataHandler implements ProcessDataHandler {

    private RootOrgInfoService rootOrgInfoService;
    private FilRootOrgInfoService filRootOrgInfoService;
    private BusProcService busProcService;

    @Override
    public void handleData(String data) throws WorkflowException {
        busProcService = SpringContextHolder.getBean(BusProcService.class);
        rootOrgInfoService = SpringContextHolder.getBean(RootOrgInfoService.class);
        filRootOrgInfoService = SpringContextHolder.getBean(FilRootOrgInfoService.class);
        JSONObject json = JSONObject.parseObject(data);
        //备份数据
        String procInstId = json.getString("procInstId");
        String filNum = (String)json.get("filNum");
        //关联关系pid
        int pid = (int) json.get("pid");
        //修改数据为已归档
        RootOrgInfo rootOrgInfo = new RootOrgInfo();
        rootOrgInfo.setPid(pid);
        rootOrgInfo.setApprovalStatus("6");
        rootOrgInfoService.updateSelective(rootOrgInfo);
        //保存备份数据
        RootOrgInfo rootOrgInfo1 = rootOrgInfoService.get(pid);
        FilRootOrgInfo filRootOrgInfo2 = new FilRootOrgInfo();
        BeanUtil.copyProperties(rootOrgInfo1, filRootOrgInfo2);
        filRootOrgInfo2.setFilNum(filNum);
        filRootOrgInfoService.insertSelective(filRootOrgInfo2);
        filRootOrgInfo2 = filRootOrgInfoService.get(filRootOrgInfo2);
        //保存中间表
        saveMidBusProc(procInstId,String.valueOf(pid),String.valueOf(filRootOrgInfo2.getPid()),"rootOrgInfo",busProcService,null);
    }

    /**
     * 保存中间表
     * @param procInstId
     * @param pid
     * @param filPid
     * @param type
     */
    public void saveMidBusProc(String procInstId, String pid, String filPid, String type, BusProcService busProcService, String pids){
        BusProc busProc = new BusProc();
        busProc.setProcInstId(procInstId);
        if(StrUtil.isNotBlank(pids)){
            busProc.setFocusApprovePid(pids);
        }
        busProc.setFlagPid(pid);
        busProc.setFilPid(filPid);
        busProc.setBusType(type);
        busProcService.insertSelective(busProc);
    }
}
