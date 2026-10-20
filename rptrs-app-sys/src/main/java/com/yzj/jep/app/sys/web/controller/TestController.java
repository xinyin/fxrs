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
@RequestMapping("/")
public class TestController {

    @RequestMapping(value = {"/test/{id}"})
    public String index(@PathVariable("id") String id, Model model, HttpServletRequest request) throws Exception {
        return "/test/" + id;
    }

}
