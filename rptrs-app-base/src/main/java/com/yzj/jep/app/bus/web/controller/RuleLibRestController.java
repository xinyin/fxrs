package com.yzj.jep.app.bus.web.controller;


import com.yzj.jep.app.bus.service.RuleLibRestCallback;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.PO;
import com.yzj.jep.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/rest/rule")
public class RuleLibRestController<T extends PO> extends BaseController<T> {

    @RequestMapping(value = "/{bean}/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public Map restRule(@PathVariable String bean, @PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取bean
        RuleLibRestCallback rCallback = (RuleLibRestCallback) SpringContextHolder.getBean(bean);
        Map parameters = new HashMap();
        Map result = new HashMap();
        parameters.put("id", id);
        result = rCallback.checkRule(parameters);
        return result;
    }
}
