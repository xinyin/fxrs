package com.yzj.jep.app.fil.web.controller;

import com.yzj.jep.app.fil.model.FilRelPro;
import com.yzj.jep.app.fil.service.FilRelProService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: FilRelProController
 * @Author Whx
 * @Date: 2021/8/3 23:16
 * @Version 1.0
 */
@Controller
@RequestMapping("/fil/filRelPro")
public class FilRelProController extends CrudController<FilRelPro> {

    private static Logger logger = LoggerFactory.getLogger(FilRelProController.class);

    @Autowired
    private FilRelProService filRelProService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "fil/filRelProList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<FilRelPro> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        filRelProService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "fil/filRelProEdit";
    }

    /**
     * 编辑页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{pid}")
    public String edit(@PathVariable Integer pid, Model model) {

        FilRelPro filRelPro = filRelProService.get(pid);
        model.addAttribute("filRelPro", filRelPro);
        model.addAttribute("opt", "edit");

        return "fil/filRelProEdit";
    }

    /**
     * 查看页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{pid}")
    public String view(@PathVariable Integer pid, Model model) {

        FilRelPro filRelPro = filRelProService.get(pid);
        model.addAttribute("relPro", filRelPro);
        model.addAttribute("opt", "edit");
        model.addAttribute("op", "1");

        return "bus/relProAddOrEdit";
    }



    @GetMapping("/queryPidsByConditions/{pids}")
    @ResponseBody
    public Map<String, Object> queryPidsByConditions(Page<FilRelPro> page, @PathVariable List<Integer> pids) {
        Map<String, Object> tips = new HashMap<String, Object>();
        List<Filter> filters = new ArrayList<>();
        filters.add(new Filter("pid", Operator.IN, pids));
        filRelProService.query(page, filters);
        tips.put(WebCode.SUCCESS, true);
        tips.put("total", page.getTotal());
        tips.put("rows", page.getResult());
        return tips;
    }
}
