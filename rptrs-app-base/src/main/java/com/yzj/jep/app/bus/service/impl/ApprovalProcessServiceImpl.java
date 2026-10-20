package com.yzj.jep.app.bus.service.impl;

import com.yzj.jep.app.bus.service.ApprovalProcessService;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.sys.model.Duty;
import com.yzj.jep.workflow.api.engine.WorkflowEngine;
import com.yzj.jep.workflow.api.engine.WorkflowEngineAware;
import com.yzj.jep.workflow.api.external.service.SysUserService;
import com.yzj.jep.workflow.api.model.IStaff;
import com.yzj.jep.workflow.api.service.IRuntimeService;
import com.yzj.jep.workflow.impl.engine.AbstractActiviti5WorkflowEngineAware;
import com.yzj.jep.workflow.impl.engine.Activiti5WorkflowEngine;
import com.yzj.jep.workflow.impl.model.StaffImpl;
import com.yzj.jep.workflow.impl.model.TaskInstImpl;
import com.yzj.jep.workflow.impl.service.RuntimeServiceImpl;
import com.yzj.jep.workflow.wf.model.ProcAutoTransfer;
import com.yzj.jep.workflow.wf.model.ProcInst;
import org.activiti.engine.task.NativeTaskQuery;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(
        readOnly = true
)
public class ApprovalProcessServiceImpl extends AbstractActiviti5WorkflowEngineAware implements ApprovalProcessService {
    @Autowired
    private SysUserService sysUserService;


    public ApprovalProcessServiceImpl(){

    }
    public ApprovalProcessServiceImpl(Activiti5WorkflowEngine workflowEngine) {
        super(workflowEngine);
    }

    public List<ProcInst> getProInst(IStaff staff) {
        List<ProcInst> procInstList=new ArrayList<>();
        List<Filter> transfersFilters = (new Filter.Builder()).eq("transferStaffCode", staff.getStaffCode()).build();
        List<ProcAutoTransfer> transfers = this.workflowEngine.getProcAutoTransferService().queryTransfer(transfersFilters);
        Set<String> staffCodes = new HashSet();
        staffCodes.add(staff.getStaffCode());
        staff = setStaffPosition(staff);
        Set<String> positions = new HashSet();
        if (CollectionUtils.isNotEmpty(staff.getPositions())) {
            positions.addAll(staff.getPositions());
        }

        if (CollectionUtils.isNotEmpty(transfers)) {
            Iterator var9 = transfers.iterator();

            while (var9.hasNext()) {
                ProcAutoTransfer transfer = (ProcAutoTransfer) var9.next();
                staffCodes.add(transfer.getStaffCode());
                StaffImpl staffImpl = new StaffImpl();
                staffImpl.setStaffCode(transfer.getStaffCode());
                staffImpl.setStaffName(transfer.getStaffName());
                IStaff transferImpl = this.setStaffPosition(staffImpl);
                if (CollectionUtils.isNotEmpty(transferImpl.getPositions())) {
                    positions.addAll(transferImpl.getPositions());
                }
            }
        }

        StringBuilder staffCodesSB = new StringBuilder();
        Iterator var28 = staffCodes.iterator();

        while (var28.hasNext()) {
            String staffCode = (String) var28.next();
            if (StringUtils.isBlank(staffCodesSB.toString())) {
                staffCodesSB.append("'").append(staffCode).append("'");
            } else {
                staffCodesSB.append(",'").append(staffCode).append("'");
            }
        }

        StringBuilder positionsSB = new StringBuilder();
        Iterator var31 = positions.iterator();

        while (var31.hasNext()) {
            String position = (String) var31.next();
            if (StringUtils.isBlank(positionsSB.toString())) {
                positionsSB.append("'").append(position).append("'");
            } else {
                positionsSB.append(",'").append(position).append("'");
            }
        }

        StringBuilder sqlBuilder = new StringBuilder();
        String querySqlHead = "SELECT DISTINCT RES.* ";
        sqlBuilder.append(" FROM ").append(" ACT_RU_TASK RES ").append(" LEFT JOIN ACT_RU_IDENTITYLINK I_OR0 ON I_OR0.TASK_ID_ = RES.ID_  ").append(" LEFT JOIN wf_proc_inst wf on wf.PROCESS_INSTANT_ID_ = res.PROC_INST_ID_ ").append(" WHERE ").append(" RES.SUSPENSION_STATE_ = 1  ").append("AND wf.STATUS_ != 20").append(" AND(  ").append(" (  ").append(" RES.ASSIGNEE_ IN(").append(staffCodesSB.toString()).append(")").append(")").append(" OR(").append(" RES.ASSIGNEE_ IS NULL ").append(" AND I_OR0.TYPE_ = 'candidate' ").append(" AND( ").append(" I_OR0.USER_ID_ IN(").append(staffCodesSB.toString()).append(") ");
        if (StringUtils.isNotBlank(positionsSB.toString())) {
            sqlBuilder.append("OR I_OR0.GROUP_ID_ IN(").append(positionsSB.toString()).append(") ");
        }

        sqlBuilder.append(")").append(") ").append(") ").append("order by CREATE_TIME_ desc");
        NativeTaskQuery taskQusery = (NativeTaskQuery) this.activitiTaskService.createNativeTaskQuery().sql(querySqlHead + sqlBuilder.toString());
        List<Task> taskList=taskQusery.list();
        Iterator var22=taskList.iterator();
        while (var22.hasNext()){
            Task task=(Task)var22.next();
            ProcInst procInst=new ProcInst();
            procInst.setProcessInstantId(task.getProcessInstanceId());
            ProcInst procInsts = (ProcInst)this.workflowEngine.getProcInstService().get(procInst);
            procInstList.add(procInsts);

        }
        return procInstList ;
    }
    private IStaff setStaffPosition(IStaff staff) {
        StaffImpl staffImpl = new StaffImpl();
        BeanUtils.copyProperties(staff, staffImpl);
        Duty params = new Duty();
        params.setStaffCode(staff.getStaffCode());
        List<Duty> dutys = this.sysUserService.selectDuty(params);
        Iterator var5 = dutys.iterator();

        while(var5.hasNext()) {
            Duty duty = (Duty)var5.next();
            staffImpl.addPosition(duty.getPositionCode());
        }

        return staffImpl;
    }
}

