/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年04月28日 16:00:38
 *
 */

package com.yzj.jep.app.bus.web.controller;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.pagehelper.PageInfo;
import com.yzj.jep.app.bus.model.MeetingFile;
import com.yzj.jep.app.bus.model.RelFile;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.service.MeetingFileService;
import com.yzj.jep.app.bus.service.RelFileService;
import com.yzj.jep.app.sys.util.XssCheck;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.web.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.app.bus.model.MeetingManage;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.app.bus.service.MeetingManageService;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Controller
@RequestMapping("/bus/meetingManage")
public class MeetingManageController extends CrudController<MeetingManage> {
    private static Logger logger = LoggerFactory.getLogger(MeetingManageController.class);

    @Autowired
    private MeetingManageService meetingManageService;

    @Autowired

    private MeetingFileService meetingFileService;
    @Autowired
    private RelFileService relFileService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "bus/meetingManageList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<MeetingManage> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        meetingManageService.query(page, filters, orders);
    }


    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "bus/meetingManageEdit";
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

        MeetingManage meetingManage = meetingManageService.get(pid);
        model.addAttribute("meetingManage", meetingManage);
        model.addAttribute("opt", "edit");

        return "bus/meetingManageEdit";
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

        MeetingManage meetingManage = meetingManageService.get(pid);
        model.addAttribute("meetingManage", meetingManage);

        return "bus/meetingManageView";
    }

    /**
     * 保存
     *
     * @param meetingManage
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(MeetingManage meetingManage) {

        Map<String, Object> tips = new HashMap<String, Object>();
        boolean b = XssCheck.reflect(meetingManage);
        if (!b) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "数据格式不规范，请检查!");
            return tips;
        }
        Boolean isExist = meetingManageService.exists(meetingManage.getPid());

        try {
            if (isExist) {
                meetingManage.setMeetInitiatorId("12");
                meetingManageService.update(meetingManage);

            } else {
                meetingManage.setMeetInitiatorId("12");
                meetingManageService.insert(meetingManage);
                /*根据meetingmanage其他内容查询出它的id*/
                String meetName = meetingManage.getMeetName();
                int pid = meetingManageService.selectByname(meetName);//会议的id
                Integer filepid = meetingManage.getFilepid();//会议管理里的文件id
                MeetingFile meetingFile = meetingFileService.selectByPid(filepid);//文件
                meetingFile.setMeetingPid(pid);
                meetingFileService.update(meetingFile);

            }

            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        }
        return tips;
    }

    /**
     * 删除
     *
     * @param pid
     * @return
     */
    @RequestMapping(value = "/remove/{pid}")
    @ResponseBody
    public Map<String, Object> remove(@PathVariable Integer pid) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            meetingManageService.deleteByPrimaryKey(pid);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_DELETE);
        }

        return tips;
    }

    /**
     * 验证提交的pid是否已经存在数据库中
     *
     * @param pid
     * @return
     */
    @RequestMapping(value = "/check", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> check(Integer pid) {

        Map<String, Object> tips = new HashMap<String, Object>();

        boolean exist = meetingManageService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }


}
