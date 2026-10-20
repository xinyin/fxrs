package com.yzj.jep.app.sys.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Michael
 * @time 2017年3月16日 上午10:16:28
 * @company 银之杰技术有限公司
 */
@Controller
@RequestMapping("/component")
public class ComponentController {

    @RequestMapping(value = {"/{page}"})
    public String index(@PathVariable("page") String page, Model model, HttpServletRequest request) throws Exception {
        return "/component/" + page;
    }

}
