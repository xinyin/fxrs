package com.yzj.jep.app.bus.web.controller;

import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.sys.dto.StaffNotifyDto;
import com.yzj.jep.sys.model.StaffNotify;
import com.yzj.jep.sys.service.NotifyService;
import com.yzj.jep.sys.service.StaffNotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/bus/AnnWidget")
public class AnnounmentWidgetController {


    @Autowired
    private StaffNotifyService staffNotifyService;

    @Autowired
    private NotifyService notifyService;

    @ResponseBody
    @RequestMapping(value = "/sysnotice")
    public List annwidget(Model model) {


        Page<StaffNotifyDto> page = new Page<>();

        List<Order> orders = new Order.Builder().desc("createTime").build();

        UserProfile staff = UserProfileUtils.getUserProfile();
        List<Filter> filters = new Filter.Builder().eq("staffCode", staff.getStaffCode()).build();

        // List<Filter> filters = new Filter.Builder().eq("staffCode",staff.getStaffCode()).eq("senderName","系统自动发送").eq("subject","发生重大关联交易,请处理").build();
        StaffNotifyService staffNotifyService = SpringContextHolder.getBean(StaffNotifyService.class);
        staffNotifyService.queryNotifyByStaffCodeAndType(page, filters, orders);


        List<StaffNotifyDto> result = page.getResult();
        List list = new ArrayList();
        for (int i = 0; i < 4; i++) {
            Map map = new HashMap();
            map.put("subject", result.get(i).getSubject());
            map.put("createtime", new SimpleDateFormat("yyyy-MM-dd").format(result.get(i).getCreateTime()));
            StaffNotify staffNotify1 = new StaffNotify();
            staffNotify1.setNotifyId(result.get(i).getId());
            StaffNotify staffNotify = staffNotifyService.get(staffNotify1);
            map.put("id", staffNotify.getId());
            list.add(map);
        }
       /* Map map = new HashMap();
        map.put("subject", result.get(0).getSubject());
        map.put("createtime", new SimpleDateFormat("yyyy-MM-dd").format(result.get(0).getCreateTime()));
        StaffNotify staffNotify1=new StaffNotify();
        staffNotify1.setNotifyId(result.get(0).getId());
        StaffNotify staffNotify = staffNotifyService.get(staffNotify1);
        map.put("id",staffNotify.getId());
        list.add(map);
        Map map1 = new HashMap();
        map1.put("subject", result.get(1).getSubject());
        map1.put("createtime", new SimpleDateFormat("yyyy-MM-dd").format(result.get(1).getCreateTime()));

        staffNotify1.setNotifyId(result.get(1).getId());
        StaffNotify staffNotifya = staffNotifyService.get(staffNotify1);
        map1.put("id",staffNotifya.getId());
        list.add(map1);
        Map map2 = new HashMap();
        map2.put("subject", result.get(2).getSubject());
        map2.put("createtime", new SimpleDateFormat("yyyy-MM-dd").format(result.get(2).getCreateTime()));
        staffNotify1.setNotifyId(result.get(2).getId());
        StaffNotify staffNotifyb = staffNotifyService.get(staffNotify1);
        map2.put("id",staffNotifyb.getId());
        list.add(map2);
        Map map3 = new HashMap();
        map3.put("subject", result.get(3).getSubject());
        map3.put("createtime", new SimpleDateFormat("yyyy-MM-dd").format(result.get(3).getCreateTime()));
        staffNotify1.setNotifyId(result.get(3).getId());
        StaffNotify staffNotifyc = staffNotifyService.get(staffNotify1);
        map3.put("id",staffNotifyc.getId());
        list.add(map3);*/
        /*for (StaffNotifyDto staffNotifyDto : result) {

            map.put("subject",staffNotifyDto.getSubject());
            map.put("createtime",new SimpleDateFormat("yyyy-MM-dd").format(staffNotifyDto.getCreateTime()));
            map.put("staffNotifyId",staffNotifyDto.getStaffNotifyId());
            list.add(map);
        }*/
        return list;
    }
}
