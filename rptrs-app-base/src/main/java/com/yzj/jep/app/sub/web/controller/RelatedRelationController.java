/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年06月02日 09:48:52
 *
 */

package com.yzj.jep.app.sub.web.controller;

import com.yzj.jep.app.base.constant.Log;
import com.yzj.jep.app.bus.model.Rel;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelPro;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelProService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.bus.service.RelService;
import com.yzj.jep.app.ins.web.controller.Commons;
import com.yzj.jep.app.sub.model.*;
import com.yzj.jep.app.sub.service.RelatedRelationService;
import com.yzj.jep.app.sub.service.RelatedRelationViewService;
import com.yzj.jep.app.sub.service.SublogService;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.AuthorityUtil;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.sys.service.TenantService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/sub/relatedRelation")
public class RelatedRelationController extends CrudController<RelatedRelation> {
    private static Logger logger = LoggerFactory.getLogger(RelatedRelationController.class);

    @Autowired
    private RelatedRelationService relatedRelationService;

    @Autowired
    private SublogService sublogService;

    @Autowired
    private TenantService tenantService;

    @Autowired
    private RelatedRelationViewService relatedRelationViewService;
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
        String url = "sub/relatedRelation/list";
        AuthorityUtil authorityUtil = new AuthorityUtil();
        String code = authorityUtil.buttonPerm(staffCode, url);
        model.addAttribute("code", code);
        String tenantId = profile.getTenantId();
        if (tenantId == null || tenantId == "") {
            model.addAttribute("subOrg", "");
        } else {
            model.addAttribute("subOrg", tenantId);
        }
        return "sub/relatedRelationList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<RelatedRelation> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        relatedRelationService.query(page, filters, orders);
    }

    /**
     * 查询报送列表数据
     *
     * @param type
     * @param page
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"/query/{type}"}, method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> queryVo(@PathVariable String type, Page<Rel> page, Model model, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        switch (type) {
            case "organ":
                filters.add(new Filter("subType", "0"));
                break;
            case "person":
                filters.add(new Filter("subType", "1"));
                break;
            case "rel":
                filters.add(new Filter("subType", "2"));
                break;
            case "pro":
                filters.add(new Filter("subType", "3"));
                break;
            case "credit":
                filters.add(new Filter("subType", "4"));
                break;
            case "loan":
                filters.add(new Filter("subType", "5"));
                break;
            case "noLoan":
                filters.add(new Filter("subType", "6"));
                break;
            case "balance":
                filters.add(new Filter("subType", "7"));
                break;
            default:
        }
        List<Order> orders = RequestUtils.buildOrders(request);
        orders.add(new Order("modifiedTime"));
        PageHelper.start(page);
        List<Sublog> subLogs = sublogService.select(filters);
        PageHelper.end(page, subLogs);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;
    }

    @Autowired
   private  RelService relService;
    @Autowired
    private RelPsnService relPsnService;
    @Autowired
    private RelOrgService relOrgService;

    public String relationMapping(String subStr) {
        if (subStr == null) {
            return "";
        }
        Integer s = Integer.parseInt(subStr);
        if (s == 1 || s == 2 || s == 3 || s == 4 || s == 5) {
            return "113";
        }
        if (s == 6 || s == 7 || s == 8) {
            return "109";
        }
        if (s == 9 || s == 10) {
            return "112";
        }
        if (s == 11) {
            return "111";
        }
        if (s == 12) {
            return "115";
        }
        if (s == 13) {
            return "106";
        }
        if (s == 14) {
            return "104";
        }
        if (s == 15) {
            return "107";
        }
        if (s == 16) {
            return "105";
        }

        if (s == 17) {
            return "106";
        }
        if (s == 18) {
            return "104";
        }
        if (s == 19) {
            return "107";
        }
        if (s == 20) {
            return "105";
        }

        if (s == 21) {
            return "106";
        }
        if (s == 22) {
            return "104";
        }
        if (s == 23) {
            return "107";
        }
        if (s == 24) {
            return "105";
        }
        if (s == 25) {
            return "102";
        }
        if (s == 26) {
            return "100";
        }
        if (s == 27) {
            return "103";
        }
        if (s == 28) {
            return "101";
        }
        if (s == 34) {
            return "108";
        }
        if (s == 35) {
            return "108";
        }
        return "";
    }
    @Autowired
    private RelProService relProService;
    /**
     * 为新增报送文件页面准备数据
     */
    @RequestMapping(value = "/queryAdd")
    @ResponseBody
    public Map<String, Object> queryAdd(Page<RelatedRelation> page, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        //设置查询条件 报送文件状态为待报送
        List<Filter> filters2 = new Filter.Builder().eq("status","3").build();
        PageHelper.start(page);
        List<RelatedRelation> relatedRelations = relatedRelationService.select(filters2);
        PageHelper.end(page, relatedRelations);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;
    }

    /**
     * 新增报送文件
     */
    @RequestMapping(value = "/addSubFile")
    @Log(module = "关联关系图谱报送", description = "新增报送文件列表")
    public String addSubFile(Model model) {
        UserProfile profile = UserProfileUtils.getUserProfile();
        String abbreviationName = Commons.getTenantAbbreviationNameByTenantId(profile.getTenantId());
        if (abbreviationName == null || abbreviationName ==""){
            model.addAttribute("subOrg", "");
        }else {
            model.addAttribute("subOrg", abbreviationName);
        }
        return "sub/addSubrelatedRelation";
    }


    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "sub/relatedRelationEdit";
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

        RelatedRelation relatedRelation = relatedRelationService.get(pid);
        model.addAttribute("relatedRelation", relatedRelation);
        model.addAttribute("opt", "edit");

        return "sub/relatedRelationEdit";
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
        String abbreviationName = Commons.getTenantAbbreviationNameByTenantId(profile.getTenantId());
        //格式化规则
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //格式化成yyyy-MM-dd格式的时间字符串
        String strDate= sdf.format(sublog.getMaxPercentInNetAssets() );
        model.addAttribute("date",strDate);
        model.addAttribute("sublog", sublog);
        model.addAttribute("subOrg", abbreviationName);
        return "sub/relatedRelationView";
    }

    /**
     * 查看页面展示数据 viewHandler
     */
    @RequestMapping(value = "/viewHandler/{pid}")
    @ResponseBody
    public Map<String, Object> viewHandler(Page<RelatedRelationView> page, @PathVariable Integer pid) {
        Map<String, Object> result = new HashMap<>();
        Sublog sublog = sublogService.get(pid);
        List<Filter> filter = new Filter.Builder().eq("relationSeq", sublog.getBatchDecimal()).build();
        PageHelper.start(page);
        List<RelatedRelationView> relatedRelationViews = relatedRelationViewService.select(filter);
        PageHelper.end(page,relatedRelationViews);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;
    }

    /**
     * 保存
     *
     * @param relatedRelation
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(RelatedRelation relatedRelation) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = relatedRelationService.exists(relatedRelation.getPid());
        try {
            if (isExist) {

                relatedRelationService.update(relatedRelation);
            } else {
                relatedRelationService.insert(relatedRelation);
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
            filters.add(Filter.eq("relationSeq", sublog.getBatchDecimal()));
            sublogService.deleteByPrimaryKey(pid);
            List<RelatedRelationView> relatedRelationViews = relatedRelationViewService.select(filters);
            for (RelatedRelationView relatedRelationView : relatedRelationViews) {
                RelatedRelation relatedRelation = relatedRelationService.get(relatedRelationView.getProdPid());
                relatedRelation.setStatus("3");
                relatedRelationService.updateSelective(relatedRelation);
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

        boolean exist = relatedRelationService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }

    /**
     * 关联关系报送-确认报送
     */
    @RequestMapping(value = "/submission/{pid}")
    public String submission(@PathVariable Integer pid,Model model) {

        Sublog sublog = sublogService.get(pid);
        model.addAttribute("sublog", sublog);
        model.addAttribute("pid", pid);

        return "sub/relatedRelationSubmission";
    }


    /**
     * 关联关系-报送日期-确定
     */
    @RequestMapping(value = "/saveRelatedRelationSubmission")
    @ResponseBody
    @Log(module = "报送管理",description = "保存数据页面")
    public Map<String, Object> saveRelatedRelationSubmission(Sublog sublog) {
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