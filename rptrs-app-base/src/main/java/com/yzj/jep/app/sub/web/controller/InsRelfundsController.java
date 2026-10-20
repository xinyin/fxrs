/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年12月19日 15:10:36
 *
 */

package com.yzj.jep.app.sub.web.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

import com.yzj.jep.app.base.constant.Log;
import com.yzj.jep.app.bus.model.RelFunds;
import com.yzj.jep.app.bus.service.RelFundsService;
import com.yzj.jep.app.sub.model.*;
import com.yzj.jep.app.sub.service.InsFundsViewService;
import com.yzj.jep.app.sub.service.InsRelfundsViewService;
import com.yzj.jep.app.sub.service.SublogService;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.AuthorityUtil;
import com.yzj.jep.orm.entity.*;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.orm.mybatis.util.StringUtil;
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

import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.app.sub.service.InsRelfundsService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;

@Controller
@RequestMapping("/sub/insRelfunds")
public class InsRelfundsController extends CrudController<InsRelfunds> {
    private static Logger logger = LoggerFactory.getLogger(InsRelfundsController.class);

    @Autowired
    private InsRelfundsService insRelfundsService;

    @Autowired
    private SublogService sublogService;

    @Autowired
    private InsRelfundsViewService insRelfundsViewService;
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

        String staffCode = UserProfileUtils.getUserProfile().getStaffCode();
        String url = "sub/insRelfunds/list";
        AuthorityUtil authorityUtil = new AuthorityUtil();
        String code = authorityUtil.buttonPerm(staffCode, url);
        model.addAttribute("code", code);
        return "sub/traRelfundsList";
    }


    /**
     * 新增报送文件
     */
    @RequestMapping(value = "/addSubFile")
    public String addSubFile(Model model) {
        UserProfile profile = UserProfileUtils.getUserProfile();
        String tenantId = subjectDate.get(profile.getTenantId());
        if (tenantId == null || tenantId == "") {
            model.addAttribute("subOrg", "");
        } else {
            model.addAttribute("subOrg", tenantId);
        }
        return "sub/relfundsSubFile";
    }


    /**
     * 查询所有满足条件的待报送文件
     */
    @RequestMapping(value = "/querySubFile", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> querySubFile(Page<InsRelfunds> page, HttpServletRequest request, String subYear, String subQuarter) {
        Map<String, Object> map = new HashMap<>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = new Order.Builder().desc("insFundsDate").build();
        filters.add(new Filter("status", "3"));
        PageHelper.start(page);
        List<InsRelfunds> insRelfunds = insRelfundsService.select(filters, orders);
        for (InsRelfunds insRelfund : insRelfunds) {
//            BigDecimal bigDecimal = new BigDecimal(insRelfund.getCorpTotalFund()).setScale(6, BigDecimal.ROUND_HALF_UP);
//            insRelfund.setCorpTotalFund(bigDecimal.toString());
            insRelfund.setCorpInvestFund(insRelfund.getCorpInvestFund().setScale(6,BigDecimal.ROUND_HALF_UP));
        }
        PageHelper.end(page, insRelfunds);
        map.put(RESULT_TOTAL, page.getTotal());
        map.put(RESULT_ROWS, page.getResult());
        return map;
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<InsRelfunds> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        insRelfundsService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "sub/insRelfundsEdit";
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

        InsRelfunds insRelfunds = insRelfundsService.get(pid);
        model.addAttribute("insRelfunds", insRelfunds);
        model.addAttribute("opt", "edit");

        return "sub/insRelfundsEdit";
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
        String strDate = sdf.format(sublog.getMaxPercentInNetAssets());
        model.addAttribute("date", strDate);
        model.addAttribute("sublog", sublog);
        model.addAttribute("subOrg", tenantId);
        return "sub/insRelfundsView";
    }

    @RequestMapping(value = "/viewHandler/{pid}")
    @ResponseBody
    public Map<String, Object> viewHandler(Page<InsRelfundsView> page, HttpServletRequest request, @PathVariable Integer pid) {
        Map<String, Object> result = new HashMap<>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        //通过pid去sublog表中查询所选择的对应数据 获取他的流水号
        Sublog sublog = sublogService.get(pid);
        String businessSeq = sublog.getBatchDecimal();
        filters.add(new Filter("relfundsSeq", businessSeq));
        List<Order> orders = RequestUtils.buildOrders(request);
        orders.add(new Order("modifiedTime", OrderType.DESC));
        PageHelper.start(page);
        List<InsRelfundsView> insRelfundsViews = insRelfundsViewService.select(filters);
        PageHelper.end(page, insRelfundsViews);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;
    }

    /**
     * 保存
     *
     * @param insRelfunds
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(InsRelfunds insRelfunds) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = insRelfundsService.exists(insRelfunds.getPid());
        try {
            if (isExist) {

                insRelfundsService.update(insRelfunds);
            } else {
                insRelfundsService.insert(insRelfunds);
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
     * 删除报送数据
     *
     * @param pid
     * @return
     */
    @RequestMapping(value = "/removeHandler/{pid}")
    @ResponseBody
    public Map<String, Object> removeHandler(@PathVariable Integer pid) {

        Map<String, Object> tips = new HashMap<String, Object>();
        try {
            insRelfundsService.deleteByPrimaryKey(pid);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
        } catch (Exception e) {
            e.printStackTrace();
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_DELETE);
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
            filters.add(Filter.eq("relfundsSeq", sublog.getBatchDecimal()));
            sublogService.deleteByPrimaryKey(pid);
            List<InsRelfundsView> insRelfundsViews = insRelfundsViewService.select(filters);
            for (InsRelfundsView insRelfundsView : insRelfundsViews) {
                InsRelfunds insRelfunds = insRelfundsService.get(insRelfundsView.getPortPid());
                insRelfunds.setStatus("3");
                insRelfundsService.updateSelective(insRelfunds);
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

        boolean exist = insRelfundsService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }


    /**
     * 与单一法人资金运用关联交易统计表-确认报送
     */
    @RequestMapping(value = "/traRelFundsSubmission/{pid}")
    public String traRelFundsSubmission(@PathVariable Integer pid, Model model) {

        Sublog sublog = sublogService.get(pid);
        model.addAttribute("sublog", sublog);
        model.addAttribute("pid", pid);

        return "sub/traRelFundsSubmission";
    }


    /**
     * 与单一法人资金运用关联交易统计表-报送日期-确定
     */
    @RequestMapping(value = "/saveTraRelFundsSubmission")
    @ResponseBody
    @Log(module = "报送管理", description = "保存数据页面")
    public Map<String, Object> saveTraRelFundsSubmission(Sublog sublog) {
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
