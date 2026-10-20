package com.yzj.jep.app.bus.custom;

import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.sys.dash.utils.WidgetUtils;
import com.yzj.jep.sys.dash.widget.AbstractWidget;
import com.yzj.jep.sys.dash.widget.annotation.CustomWidget;
import com.yzj.jep.sys.dash.widget.exception.WidgetException;
import com.yzj.jep.sys.dto.StaffNotifyDto;
import com.yzj.jep.sys.service.StaffNotifyService;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 待办事项生成组件
 *
 * @author Michael
 * durisk.yzj.com
 * 2015年12月21日
 */
//@CustomWidget(code = "NTODO", name = "待办事项", desc = "显示待办事项的组件", remark = "这是备注\r\n你设置了吗")

@Controller
@RequestMapping("/bus/TodoWidget")
public class TodoWidget extends CrudController<ProcInst> {



/*

	@Autowired
	private WorkflowEngine workflowEngine;
    */
    /*未审批*//*

	@RequestMapping(value = {"/todowidget"})
	@ResponseBody
	public List<Long> prepareQuery( HttpServletRequest request)
	{
		Page<Object> page= new Page<>();
		List<Long> result = new ArrayList<>();
		StaffImpl staff = new StaffImpl();
		List<Filter> filters = RequestUtils.buildFilters(request);
		List<Order> orders = RequestUtils.buildOrders(request);
		workflowEngine.getRuntimeService().queryTaskInst(page,staff,filters,orders);



		result.add(page.getTotal());
		return result;
	}

	*/
    /*已审批*//*

	@RequestMapping(value = {"/dowidget"})
	@ResponseBody
	public  List<Long>  queryHis(@SuppressWarnings("rawtypes") Page page,HttpServletRequest request)
	{
		List<Long> result = new ArrayList<>();
		List<Filter> filters = RequestUtils.buildFilters(request);
		List<Order> orders = RequestUtils.buildOrders(request);
		UserProfile user = UserProfileUtils.getUserProfile();
		workflowEngine.getHistoryService().queryDoneProcInst(page, user.getStaffCode(), filters, orders);
		result.add( page.getTotal());
		return result;
	}
*/

}
