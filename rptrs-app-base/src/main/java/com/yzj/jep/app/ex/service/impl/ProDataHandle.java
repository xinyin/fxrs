package com.yzj.jep.app.ex.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.mapper.DelRelMapper;
import com.yzj.jep.app.bus.model.RelPro;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.app.fil.model.FilRelPro;
import com.yzj.jep.app.fil.service.FilRelProService;
import com.yzj.jep.app.mid.model.BusProc;
import com.yzj.jep.app.mid.service.BusProcService;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.client.ProcessDataHandler;
import com.yzj.jep.workflow.client.annotation.DataHandler;

@DataHandler("ZZ_Pro001")
public class ProDataHandle implements ProcessDataHandler {
    RelPsnService relPsnService;
    RelOrgService relOrgService;
    RelService relService;
    RelProService relProService;
    RelPsnAndOrgOfProService relPsnAndOrgOfProService;
    private DelRelMapper delRelMapper;
    DelRelService delRelService;
    private DictItemService dictItemService;
    private BusProcService busProcService;
    private FilRelProService filRelProService;

    @Override
    public void handleData(String data) throws WorkflowException {
        relPsnAndOrgOfProService = SpringContextHolder.getBean(RelPsnAndOrgOfProService.class);
        relPsnService = SpringContextHolder.getBean(RelPsnService.class);
        relOrgService = SpringContextHolder.getBean(RelOrgService.class);
        relService = SpringContextHolder.getBean(RelService.class);
        relProService = SpringContextHolder.getBean(RelProService.class);
        delRelMapper = SpringContextHolder.getBean(DelRelMapper.class);
        delRelService = SpringContextHolder.getBean(DelRelService.class);
        dictItemService = SpringContextHolder.getBean(DictItemService.class);
        busProcService = SpringContextHolder.getBean(BusProcService.class);
        filRelProService = SpringContextHolder.getBean(FilRelProService.class);
        JSONObject json = JSONObject.parseObject(data);
        String procInstId = (String) json.get("procInstId");
        //判断单条还是多条数据
        Object formData = json.get("data");
        if(formData!=null){
            //处理多条数据
            String pids =(String) json.get("pids");
            String[] pidArr = pids.split(",");
            //修改
            for (String pidStr : pidArr) {
                RelPro relPro = new RelPro();
                relPro.setPid(Integer.valueOf(pidStr));
                relPro.setApprovalStatus("6");
                //是否存在有效关系  0 无  1 有
                relPro.setEffectRelation("1");
                relPro.setStatusComment("审批通过");
                relProService.updateSelective(relPro);
                RelPro relPro1 = relProService.get(Integer.valueOf(pidStr));
                //保存备份
                FilRelPro filRelPro = new FilRelPro();
                BeanUtil.copyProperties(relPro1,filRelPro);
                filRelProService.insertSelective(filRelPro);
                FilRelPro filRelPro1 = filRelProService.get(filRelPro);
                saveMidBusProc(procInstId,pidStr,String.valueOf(filRelPro1.getPid()),"pro",busProcService,pids);
            }
        }else {
            //处理单条数据
            int pid = (int) json.get("pid");
            //1 查询出所有的关系自然人  新增改为归档  归档-删除 改为删除
            //查询自然人的关系
            //3 状态由审批中改为 已归档 6
            RelPro relPro = relProService.get(pid);
            relPro.setApprovalStatus("6");
            //是否建立有效关系:是否存在有效关系  0 无  1 有
            relPro.setEffectRelation("1");
            relPro.setStatusComment("审批通过");
            relProService.updateSelective(relPro);
            FilRelPro filRelPro = new FilRelPro();
            BeanUtil.copyProperties(relPro,filRelPro);
            filRelProService.insertSelective(filRelPro);
            FilRelPro filRelPro1 = filRelProService.get(filRelPro);
            saveMidBusProc(procInstId,String.valueOf(pid),String.valueOf(filRelPro1.getPid()),"pro",busProcService,null);
            /*List<Filter> relIsVaild = new Filter.Builder().eq("toPid", pid).eq("toType", "PRO").eq("delflag", "1").build();
            List<RelPsnAndOrgOfPro> select1 = relPsnAndOrgOfProService.select(relIsVaild);
            for (RelPsnAndOrgOfPro rel : select1) {
                rel.setRelStatus("00");
                relPsnAndOrgOfProService.updateSelective(rel);
            }*/
        }
    }
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
