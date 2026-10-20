package com.yzj.jep.app.sys.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yzj.jep.app.sys.util.XssCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.sys.model.RoleStd;
import com.yzj.jep.sys.service.RoleStdService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.entity.NameValuePair;
import com.yzj.jep.web.util.NVUtils;

/**
 * 标准角色类型
 *
 * @author ZhangYan
 * @time 2017年5月22日 下午3:44:01
 * @company 银之杰技术有限公司
 */
@Controller
@RequestMapping("/sys/roleStd")
public class RoleStdController extends CrudController<RoleStd> {

    Logger logger = LoggerFactory.getLogger(RoleStdController.class);

    @Autowired
    RoleStdService roleStdService;

    /**
     * 列表页面
     *
     * @param model
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {
        return "sys/roleStdList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<RoleStd> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        roleStdService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return 新增页面
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {
        return "sys/roleStdEdit";
    }

    /**
     * 编辑页面
     *
     * @param roleStdCode 主键编码
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{roleStdCode}")
    public String edit(@PathVariable String roleStdCode, Model model) {

        RoleStd roleStd = roleStdService.get(roleStdCode);
        model.addAttribute("roleStd", roleStd);
        model.addAttribute("opt", "edit");

        return "sys/roleStdEdit";
    }

    /**
     * 查看页面
     *
     * @param roleStdCode 主键编码
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{roleStdCode}")
    public String view(@PathVariable String roleStdCode, Model model) {

        RoleStd roleStd = roleStdService.get(roleStdCode);
        model.addAttribute("roleStd", roleStd);

        return "sys/roleStdView";
    }

    /**
     * 保存
     *
     * @param roleStd
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(RoleStd roleStd) {

        Map<String, Object> tips = new HashMap<String, Object>();
        boolean b = XssCheck.reflect(roleStd);
        if (!b) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "数据格式不规范，请检查!");
            return tips;
        }
        Boolean isExist = roleStdService.exists(roleStd.getRoleStdCode());
        try {
            if (isExist) {
                roleStdService.update(roleStd);
            } else {
                roleStdService.insert(roleStd);
            }
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } catch (ServiceException e) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        }
        return tips;
    }

    /**
     * 删除
     *
     * @param roleStdCode
     * @return
     */
    @RequestMapping(value = "/remove/{roleStdCode}")
    @ResponseBody
    public Map<String, Object> remove(@PathVariable String roleStdCode) {

        Map<String, Object> tips = new HashMap<String, Object>();
        try {
            this.roleStdService.deleteByPrimaryKey(roleStdCode);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
        } catch (ServiceException e) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_DELETE);
        }
        return tips;
    }

    /**
     * 验证主键是否已经存在数据库中
     *
     * @param roleStdCode 角色类型主键
     * @return 返回验证结果true|false
     */
    @RequestMapping(value = "/check", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> check(String roleStdCode) {

        Map<String, Object> tips = new HashMap<String, Object>();
        Boolean isExist = roleStdService.exists(roleStdCode);
        tips.put("valid", !isExist);

        return tips;
    }

    /**
     * 以特殊格式获取所有角色类型
     *
     * @return json格式返回
     */
    @RequestMapping(value = "/queryAll")
    @ResponseBody
    public List<NameValuePair> queryAll() {
        List<RoleStd> result = roleStdService.selectAll();
        return NVUtils.convert(result, result.size(), "roleStdCode", "roleStdName");
    }

}
