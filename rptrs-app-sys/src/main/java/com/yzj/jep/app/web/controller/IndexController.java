package com.yzj.jep.app.web.controller;


import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.sys.model.Role;
import com.yzj.jep.sys.service.DutyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;


/**
 * 首页
 *
 * @time 2017年3月16日 上午10:16:28
 * @company 银之杰技术有限公司
 */
@Controller
public class IndexController {

    @Autowired
    private DutyService dutyService;

    /**
     * 跳转首页
     *
     * @param model
     * @throws Exception
     */
    @RequestMapping(value = {"", "/index"})
    public String index(Model model) throws Exception {
        UserProfile profile = UserProfileUtils.getUserProfile();
        String unitCode = profile.getUnitCode();
        model.addAttribute("merOrgHome", unitCode);
        return "index";
    }

    /**
     * 欢迎页面
     *
     * @param model
     * @throws Exception
     */
    @RequestMapping(value = {"", "/welcome"})
    public String welcome(Model model) throws Exception {
        String opt = "opt";
        //领导看不到待办
        String staffCode = UserProfileUtils.getUserProfile().getStaffCode();
        // 查找角色编码
        List<Role> rolelist = dutyService.getStaffRoleList(staffCode, null);
        List<String> roleStrlist = new ArrayList<>();

        for (Role role : rolelist) {
            roleStrlist.add(role.getRoleCode());
        }
        if (roleStrlist.contains("302010")) {
            opt = "noView";
        }

        //查询登陆人机构及下辖机构的信息
        UserProfile profile = UserProfileUtils.getUserProfile();
        String unitCode = profile.getUnitCode();
        if ("0001".equals(unitCode) || "0001-ZZHK".equals(unitCode)) {
            //资产
            model.addAttribute("opt1", "NoMH");
        } else {
            //管户机构
            model.addAttribute("opt1", "MH");
        }
        model.addAttribute("opt", opt);
        return "welcome";
    }

    //数据看板
    @RequestMapping(value = "/dataAnalysis")
    public String dataAnalysis(Model model) {
        return "dataAnalysis";
    }

    @RequestMapping(value = "/moreNotices")
    public String moreNotices(Model model) {
        return "";
    }

}