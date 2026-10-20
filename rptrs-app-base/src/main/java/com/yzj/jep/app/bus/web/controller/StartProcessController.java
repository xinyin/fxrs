package com.yzj.jep.app.bus.web.controller;

import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.service.UserProfileService;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.sys.model.User;
import com.yzj.jep.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/bus/startProcess")
public class StartProcessController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserProfileService userProfileService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model, String passToken) {
        UserProfile profile = UserProfileUtils.getUserProfile();
        if (profile == null) {
            Map<String, Object> map = userService.processToken(passToken);
            User user = (User) map.get(UserService.CURRENT_USER);
            profile = userProfileService.getUserProfileByUser(user, null);
            model.addAttribute("passToken", map.get(UserService.PASS_TOKEN));
        }
        model.addAttribute("userCode", profile.getStaffCode());

        return "bus/startProcessList";
    }

}
