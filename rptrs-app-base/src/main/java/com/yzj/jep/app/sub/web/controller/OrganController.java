/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月02日 09:48:44
 *
 */

package com.yzj.jep.app.sub.web.controller;

import com.yzj.jep.app.base.constant.Log;
import com.yzj.jep.app.bus.model.Rel;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.sub.mapper.OrganMapper;
import com.yzj.jep.app.sub.model.*;
import com.yzj.jep.app.sub.service.OrganService;
import com.yzj.jep.app.sub.service.OrganViewService;
import com.yzj.jep.app.sub.service.SublogService;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.AuthorityUtil;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.OrderType;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.sys.service.TenantService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sub/organ")
public class OrganController extends CrudController<Organ> {
    private static Logger logger = LoggerFactory.getLogger(OrganController.class);

    @Autowired
    private OrganService organService;

    @Autowired
    private SublogService sublogService;

    @Autowired
    private TenantService tenantService;

    @Autowired
    private OrganViewService organViewService;
    private static final Map<String, String> subjectDate = new HashMap<>();

    static {

        /*
         * 根据不同的主体来添加不同的后缀
         * */
        subjectDate.put("FX", "复星保德信人寿保险有限公司");
    }
    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {
        UserProfile profile = UserProfileUtils.getUserProfile();
        String staffCode = UserProfileUtils.getUserProfile().getStaffCode();
        String url = "sub/organ/list";
        AuthorityUtil authorityUtil = new AuthorityUtil();
        String code = authorityUtil.buttonPerm(staffCode, url);
        model.addAttribute("code", code);
        String tenantId = profile.getTenantId();
        if (tenantId == null || tenantId == "") {
            model.addAttribute("subOrg", "");
        } else {
            model.addAttribute("subOrg", tenantId);
        }
        return "sub/organList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<Organ> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        organService.query(page, filters, orders);
    }

    /**
     * 为新增报送文件页面准备数据
     */
    @RequestMapping(value = "/queryAdd")
    @ResponseBody
    public Map<String, Object> queryAdd(Page<Organ> page, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        //设置查询条件 报送文件状态为待报送
        List<Filter> filters = new Filter.Builder().eq("status","3").build();
        PageHelper.start(page);
        List<Organ> organList = organService.select(filters);
        PageHelper.end(page,organList);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;
    }

    /**
     * 新增报送文件
     */
    @RequestMapping(value = "/addSubFile")
    @Log(module = "法人和其他组织关联方组织报送", description = "新增报送文件列表")
    public String addSubFile(Model model) {
        UserProfile profile = UserProfileUtils.getUserProfile();
        String tenantId = subjectDate.get(profile.getTenantId());
        if (tenantId == null || tenantId ==""){
            model.addAttribute("subOrg", "");
        }else {
            model.addAttribute("subOrg", tenantId);
        }
        return "sub/addSubOrgan";
    }


    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "sub/organEdit";
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

        Organ organ = organService.get(pid);
        model.addAttribute("organ", organ);
        model.addAttribute("opt", "edit");

        return "sub/organEdit";
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
        UserProfile profile = UserProfileUtils.getUserProfile();
        Sublog sublog = sublogService.get(pid);
        String tenantId = subjectDate.get(profile.getTenantId());
        //格式化规则
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //格式化成yyyy-MM-dd格式的时间字符串
        String strDate= sdf.format(sublog.getMaxPercentInNetAssets() );
        model.addAttribute("date",strDate);
        model.addAttribute("sublog", sublog);
        model.addAttribute("subOrg", tenantId);
        return "sub/organView";
    }

    /**
     * 查看页面展示数据 viewHandler
     */
    @RequestMapping(value = "/viewHandler/{pid}")
    @ResponseBody
    @Log(module = "报送管理", description = "查看生成报送文件详细信息")
    public Map<String, Object> viewHandler(Page<OrganView> page, HttpServletRequest request, @PathVariable Integer pid) {
        Map<String, Object> result = new HashMap<>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        //通过pid去sublog表中查询所选择的对应数据 获取他的流水号
        Sublog sublog = sublogService.get(pid);
        String organSeq = sublog.getBatchDecimal();
        filters.add(new Filter("organSeq", organSeq));
        List<Order> orders = RequestUtils.buildOrders(request);
        orders.add(new Order("modifiedTime", OrderType.DESC));
        PageHelper.start(page);
        List<OrganView> organViews = organViewService.select(filters);
        PageHelper.end(page, organViews);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;
    }

    /**
     * 保存
     *
     * @param organ
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(Organ organ) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = organService.exists(organ.getPid());
        try {
            if (isExist) {

                organService.update(organ);
            } else {
                organService.insert(organ);
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
            Sublog sublog = sublogService.get(pid);
            List<Filter> filters = new ArrayList<>();
            filters.add(Filter.eq("organSeq", sublog.getBatchDecimal()));
            sublogService.deleteByPrimaryKey(pid);
            List<OrganView> organViews = organViewService.select(filters);
            for (OrganView organView : organViews) {
                Organ organ = organService.get(organView.getProtPid());
                organ.setStatus("3");
                organService.updateSelective(organ);
            }
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

        boolean exist = organService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }


    /**
     * 法人和组织报送-确认报送
     */
    @RequestMapping(value = "/submission/{pid}")
    public String submission(@PathVariable Integer pid,Model model) {

        Sublog sublog = sublogService.get(pid);
        model.addAttribute("sublog", sublog);
        model.addAttribute("pid", pid);

        return "sub/orgSubmission";
    }


    /**
     * 报送日期-确定
     */
    @RequestMapping(value = "/saveOrgSubmission")
    @ResponseBody
    @Log(module = "报送管理",description = "保存数据页面")
    public Map<String, Object> saveOrgSubmission(Sublog sublog) {
        Map<String, Object> tips = new HashMap<String, Object>();
        Boolean isExist = sublogService.exists(sublog.getPid());
        try {
            if (isExist) {
                //修改报送状态为-已报送
                sublog.setStatus("02");
                sublogService.update(sublog);
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
}