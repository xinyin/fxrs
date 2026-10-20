package com.yzj.jep.app.bus.web.controller;

import com.yzj.jep.app.bus.service.impl.ApprovalProcessServiceImpl;
import com.yzj.jep.app.bus.vo.ProcInstVo;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.util.RequestUtils;
import com.yzj.jep.workflow.api.engine.WorkflowEngine;
import com.yzj.jep.workflow.impl.model.StaffImpl;
import com.yzj.jep.workflow.wf.model.ProcInst;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/bus/approvalProcess")
public class ApprovalProcessController extends CrudController<ProcInst> {
    @Autowired
    private WorkflowEngine workflowEngine;



    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "bus/approvalProcessList";
    }

    @RequestMapping(value = {"/queryAppProc"}, produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> queryAppProc(Page<ProcInstVo> page, Model model, HttpServletRequest request, String passToken) {
        Page p=new Page();
        p.setPageSize(99999);
        p.setPageNumber(1);
        UserProfile profile = UserProfileUtils.getUserProfile();
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        StaffImpl staff = new StaffImpl();
        Page page1=new Page();
        BeanUtils.copyProperties(profile, staff);
        Page p1=workflowEngine.getRuntimeService().queryTaskInst(p, staff, filters, orders);

        for(int i=0;i<p1.getResult().size();i++){
            ProcInstVo procInstVo=new ProcInstVo();
            BeanUtils.copyProperties(p1.getResult().get(i), procInstVo);
            procInstVo.setHandleStatus("未处理");
            page1.getResult().add(procInstVo);
        }
        long pageT=p1.getTotal();
        UserProfile user = UserProfileUtils.getUserProfile();
        Page p2=workflowEngine.getHistoryService().queryDoneProcInst(p, user.getStaffCode(), filters, orders);
        for(int i=0;i<(p2.getResult().size());i++){
            ProcInstVo procInstVo=new ProcInstVo();
            BeanUtils.copyProperties(p2.getResult().get(i), procInstVo);
            procInstVo.setHandleStatus("已处理");
            page1.getResult().add(procInstVo);
        }
        pageT+=p2.getTotal();
        Page page2=new Page();
        for(int i=0;i<page.getPageSize();i++){
            if((i+((page.getPageNumber()-1)*page.getPageSize()))>=page1.getResult().size()){
                break ;
            }
            page2.getResult().add(page1.getResult().get(i+((page.getPageNumber()-1)*page.getPageSize())));
        }
        result.put(RESULT_TOTAL, pageT);
        result.put(RESULT_ROWS, page2.getResult());
        return result;
    }

}
