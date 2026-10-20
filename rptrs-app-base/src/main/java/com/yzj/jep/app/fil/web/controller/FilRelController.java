package com.yzj.jep.app.fil.web.controller;

import com.yzj.jep.app.fil.model.FilRel;
import com.yzj.jep.app.fil.service.FilRelService;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: FilRelController
 * @Author Whx
 * @Date: 2021/8/4 10:19
 * @Version 1.0
 */
@Controller
@RequestMapping("/fil/filRel")
public class FilRelController extends CrudController<FilRel> {

    @Autowired
    private FilRelService filRelService;

    @GetMapping("/queryPidsByConditions/{pids}")
    @ResponseBody
    public Map<String, Object> queryPidsByConditions(Page<FilRel> page, @PathVariable List<Integer> pids) {
        Map<String, Object> tips = new HashMap<String, Object>();
        List<Filter> filters = new ArrayList<>();
        filters.add(new Filter("pid", Operator.IN, pids));
        filRelService.query(page, filters);
        tips.put(WebCode.SUCCESS, true);
        tips.put("total", page.getTotal());
        tips.put("rows", page.getResult());
        return tips;
    }
}
