package com.yzj.jep.app.ex.service.impl;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
//import com.yzj.jep.app.ex.model.Expenseapply;
//import com.yzj.jep.app.ex.service.ExpenseapplyService;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.client.ProcessDataHandler;
import com.yzj.jep.workflow.client.annotation.DataHandler;

@DataHandler("YJZR_ManpowerApplyProcess001")
public class ManpowerApplyProcessDataHandler implements ProcessDataHandler {
//	ExpenseapplyService	expenseapplyService;

    @Override
    public void handleData(String data) throws WorkflowException {
        /**所有审批流程走完走此方法处理业务 */
		/*expenseapplyService = SpringContextHolder.getBean(ExpenseapplyService.class); 
		JSONObject json = JSONObject.parseObject(data);
		String ano = json.getString("ano");
		String eid = json.getString("eid");
		List<Filter> filters = new Filter.Builder().eq("eid", eid).eq("ano", ano).build();
		List<Expenseapply> expenseApply = expenseapplyService.select(filters);
		if(expenseApply!= null && expenseApply.size()>0) {
			for(Expenseapply expenseapply : expenseApply) {
				expenseapply.setApplyStatus("Aproved");
				expenseapplyService.update(expenseapply);
			}
		}*/
    }

}