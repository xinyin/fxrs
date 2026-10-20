package com.yzj.jep.app.ex.service.impl;

import org.springframework.web.servlet.ModelAndView;

//import com.yzj.jep.app.ex.model.Budgetdetail;
//import com.yzj.jep.app.ex.model.Expenseapply;
//import com.yzj.jep.app.ex.model.Travelsegment;
//import com.yzj.jep.app.ex.service.BudgetdetailService;
//import com.yzj.jep.app.ex.service.ExpenseapplyService;
//import com.yzj.jep.app.ex.service.TravelsegmentService;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.api.code.ClientCodes;
import com.yzj.jep.workflow.client.ProcessFormHandler;
import com.yzj.jep.workflow.client.annotation.FormHandler;

@FormHandler("YJZR_ManpowerApplyProcess001")
public class ManpowerApplyProcessFormHandler implements ProcessFormHandler {

//	ExpenseapplyService	expenseapplyService;
//	BudgetdetailService	budgetdetailService;

    @Override
    public ModelAndView processForm(String nodekey, String data, String opt, ModelAndView model)
            throws WorkflowException {
        /**每次处理任务都走此方法显示处理明细页面 */
//		expenseapplyService = SpringContextHolder.getBean(ExpenseapplyService.class); 
//		budgetdetailService = SpringContextHolder.getBean(BudgetdetailService.class); 
		
		/*JSONObject json = JSONObject.parseObject(data);
		String ano = json.getString("ano");
		String eid = json.getString("eid");
		List<Filter> filters = new Filter.Builder().eq("eid", eid).eq("ano", ano).build();
		int maxSeqNo = budgetdetailService.selectMaxIndex(eid, ano);
		
		List<Budgetdetail> budgetDetails = budgetdetailService.select(filters);  
		Expenseapply expenseapply = expenseapplyService.select(filters).get(0);
		*/
        if (ClientCodes.OPT_EDIT.equals(opt)) {

            model.addObject("budgetdetails", null);
            model.addObject("expenseapply", null);
            model.addObject("maxBudIndex", null);
            model.setViewName("ex/expenseapplyProcessMpEdit");
        }
        if (ClientCodes.OPT_VIEW.equals(opt)) {
            model.addObject("budgetDetails", null);
            model.addObject("expenseapply", null);
            model.addObject("maxBudIndex", null);
            model.setViewName("ex/expenseapplyProcessMpView");
        }
        return model;
    }

}
