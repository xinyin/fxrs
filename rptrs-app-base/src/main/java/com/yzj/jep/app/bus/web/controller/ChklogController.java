package com.yzj.jep.app.bus.web.controller;


import com.yzj.jep.app.bus.model.Chklog;
import com.yzj.jep.app.bus.service.ChklogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/bus/chkLog")
public class ChklogController {

    @Autowired
    private ChklogService chklogService;


    @RequestMapping("/byisinsder")
    @ResponseBody
    public List<Integer> selectby0() {
        List<Integer> integers = chklogService.selectByisinsder0();
        return integers;
    }

}
