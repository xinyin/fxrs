package com.yzj.jep.app.bus.web.controller;

import com.yzj.jep.app.bus.model.AuthApply;
import com.yzj.jep.app.bus.model.EntrustApply;
import com.yzj.jep.app.bus.model.RelPro;
import com.yzj.jep.app.bus.service.AuthApplyService;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.workflow.web.controller.ProcessController;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Controller
@RequestMapping("/bus/auth/apply")
public class AuthApplyController extends CrudController<AuthApply> {


    @Autowired
    private AuthApplyService authApplyService;

    Logger logger = LoggerFactory.getLogger(ProcessController.class);

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {
        UserProfile profile = UserProfileUtils.getUserProfile();
        if (profile == null) {
            throw new ServiceException("user session may be expired.");
        }
        return "bus/authApplyList";
    }

    /**
     *  为列表页面做数据准备
     */

    @Override
    protected void prepareQuery(Page<AuthApply> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        try {
            orders.add(Order.desc("createTime"));
            filters.add(new Filter("delflag", Operator.EQ, 1));
            authApplyService.query(page, filters, orders);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/add")
    public String add(Model model) {
        UserProfile profile = UserProfileUtils.getUserProfile();
        AuthApply authApply = new AuthApply();
        authApply.setApplicant(profile.getStaffName());
        authApply.setDepartment(profile.getDeptName());
        model.addAttribute("authApply",authApply);
        return "bus/authApplyEdit";
    }
    /**
     *   保存关联关系
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(AuthApply authApply){
        Map<String, Object> tips = new HashMap<String, Object>();
        Boolean isExist = authApplyService.exists(authApply.getPid());
        if (isExist) {
            authApplyService.updateSelective(authApply);
        }else {
            //待提交
            authApply.setStatus("3");
            authApplyService.insertSelective(authApply);
        }
        tips.put(WebCode.SUCCESS, true);
        tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        return tips;
    }

    /**
     * 编辑页面
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        AuthApply authApply = authApplyService.get(Integer.valueOf(id));
        model.addAttribute("authApply", authApply);
        model.addAttribute("opt", "edit");
        return "bus/authApplyEdit";
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/remove/{id}")
    @ResponseBody
    public Map<String, Object> remove(@PathVariable String id) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            AuthApply authApply = authApplyService.get(Integer.valueOf(id));
            authApply.setDelflag("0");
            authApplyService.updateSelective(authApply);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_DELETE);
        }
        return tips;
    }

    /**
     * 用户信息查询
     * @return
     */
    @RequestMapping(value = "/applicantSelect")
    public String applicantSelect() {
        return "bus/applicantSelect";
    }

    /**
     * 角色信息查询
     * @return
     */
    @RequestMapping(value = "/roleSelect")
    public String roleSelect() {
        return "bus/roleSelect";
    }

    /*
     * 提交审批
     * */
    @RequestMapping(value = "/send/{pid}")
    @ResponseBody
    public Map<String, Object> send(@PathVariable String pid, Model model) {
        Map<String, Object> tips = new HashMap<String, Object>();
        AuthApply authApply = authApplyService.get(Integer.valueOf(pid));
        if ("3".equals(authApply.getStatus())) {
            //4 代表审批中
            authApply.setStatus("4");
            authApplyService.updateSelective(authApply);
            tips.put("data", authApply);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } else {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "状态不符，提交失败！");
        }
        return tips;
    }

    /**
     * 查看页面
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{pid}")
    public String view(@PathVariable Integer pid, Model model) {

        AuthApply authApply = authApplyService.get(pid);
        model.addAttribute("authApply", authApply);
        model.addAttribute("opt", "view");
        return "bus/authApplyEdit";
    }
}
