package com.yzj.jep.app.bus.web.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.google.common.collect.Maps;
import com.yzj.jep.app.bus.model.PsnAndOrgVo;
import com.yzj.jep.app.bus.model.RelFunds;
import com.yzj.jep.app.bus.model.RelOrg;
import com.yzj.jep.app.bus.model.RelPsn;
import com.yzj.jep.app.bus.service.RelFundsService;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.bus.vo.RelFundsVo;
import com.yzj.jep.app.ins.service.BaseService;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.sys.util.XssCheck;
import com.yzj.jep.app.util.AuthorityUtil;
import com.yzj.jep.app.util.ConfidentialInfoUtil;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.*;
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
import java.util.*;

@Controller
@RequestMapping("/bus/relFunds")
public class RelFundsController extends CrudController<RelFunds> {
    private static Logger logger = LoggerFactory.getLogger(RelFundsController.class);

    @Autowired
    private RelFundsService relFundsService;
    @Autowired
    private RelPsnService relPsnService;
    @Autowired
    private RelOrgService relOrgService;
    @Autowired
    private BaseService baseService;


    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {
        String staffCode = UserProfileUtils.getUserProfile().getStaffCode();
        String url = "bus/relFunds/list";
        AuthorityUtil authorityUtil = new AuthorityUtil();
        String code = authorityUtil.buttonPerm(staffCode, url);
        model.addAttribute("code", code);
        return "bus/relFundsList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<RelFunds> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        relFundsService.query(page, filters, orders);
        List<RelFunds> list = page.getResult();
        list.stream().forEach( e -> {
            e.setRelIdentifyNumber(new ConfidentialInfoUtil().hideAllIdCardNum(e.getRelIdentifyNumber()));
        });

    }


    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "bus/relFundsEdit";
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

        RelFunds relFunds = relFundsService.get(pid);
        model.addAttribute("relFunds", relFunds);
        model.addAttribute("opt", "edit");

        return "bus/relFundsEdit";
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

        RelFunds relFunds = relFundsService.get(pid);
        if (null!=relFunds){
            relFunds.setRelIdentifyNumber(new ConfidentialInfoUtil().hideAllIdCardNum(relFunds.getRelIdentifyNumber()));
        }
        model.addAttribute("relFunds", relFunds);

        return "bus/relFundsView";
    }

    /**
     * 保存
     *
     * @param relFunds
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(RelFunds relFunds) {
        if (StringUtils.isEmpty(relFunds.getRelName())
                || relFunds.getFundsInvestmentBalance()==null
         ) {
            relFunds.setStatus("0");
        }else {
            relFunds.setStatus("1");
        }
        Map<String, Object> tips = new HashMap<String, Object>();
        boolean b = XssCheck.reflect(relFunds);
        if (!b) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "数据格式不规范，请检查!");
            return tips;
        }
        Boolean isExist = relFundsService.exists(relFunds.getPid());

        try {
            if (isExist) {

                relFundsService.updateSelective(relFunds);
            } else {
                relFundsService.insertSelective(relFunds);
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
            relFundsService.deleteByPrimaryKey(pid);
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

        boolean exist = relFundsService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }


    /**
     * 分页查询
     *
     * @param page
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"/query1"}, produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> query1(String quarter, String investmentBalanceTime, Page<RelFunds> page, Model model, HttpServletRequest request) {

        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        RelFundsVo relfundsVo = new RelFundsVo();
        relfundsVo = relFundsService.queryList(investmentBalanceTime, quarter);
        filters.add(new Filter("investmentBalanceTime", Operator.GE, relfundsVo.getStartInvestmentBalanceTime()));
        filters.add(new Filter("investmentBalanceTime", Operator.LE, relfundsVo.getEndInvestmentBalanceTime()));
        orders.add(new Order("investmentBalanceTime", OrderType.DESC));
        orders.add(new Order("pid", OrderType.DESC));
        prepareQuery(page, filters, orders, request);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;
    }


    @RequestMapping(value = "/relNameSelect")
    public String relNameSelect(Model model, String tranType) {
        model.addAttribute("tranType", tranType);
        return "bus/relFundsSelect";
    }

    /**
     * 关联关系查询
     */
    @RequestMapping(value = "/relSelectByName")
    @ResponseBody
    public Map<String, Object> queryVo(Page<PsnAndOrgVo> page, Model model, HttpServletRequest request, String traSubName, String tranType) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        List<Filter> filterss = new ArrayList<>();
        if (filters.size() > 0) {
            Filter filter = filters.get(0);
            filterss = new Filter.Builder().like("orgName", filter.getValue()).build();
        }
        filters.add(new Filter("delflag",Operator.EQ, "1"));
        filterss.add(new Filter("delflag",Operator.EQ, "1"));
        filters.add(new Filter("effectRelation",Operator.EQ, "1"));
        filterss.add(new Filter("effectRelation",Operator.EQ, "1"));


        List<RelPsn> relPsns = relPsnService.select(filters);
        List<RelOrg> relOrgs = relOrgService.select(filterss);
        List<PsnAndOrgVo> psnAndOrgVos = new ArrayList<>();
        for (RelPsn a : relPsns) {
            PsnAndOrgVo b = new PsnAndOrgVo();
            b.setPid(a.getPid().toString());
            b.setType("PSN");
            b.setName(a.getName());
            b.setIdentifyNumber(a.getIdentifyNumber());
            b.setIdentifyType(a.getIdentifyType());
            psnAndOrgVos.add(b);
        }
        for (RelOrg a : relOrgs) {
            PsnAndOrgVo b = new PsnAndOrgVo();
            b.setPid(a.getPid().toString());
            b.setType("ORG");
            b.setName(a.getOrgName());
            b.setIdentifyNumber(a.getUniscCode());
            b.setIdentifyType(a.getIdentifyType());
            psnAndOrgVos.add(b);
        }

        page.setTotal(psnAndOrgVos.size());
        int pages = (psnAndOrgVos.size() + page.getPageSize() - 1) / page.getPageSize();
        if (page.getPageNumber() > pages) {
            page.setPageNumber(1);
        }
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        List<PsnAndOrgVo> psnAndOrgVoss = new ArrayList<PsnAndOrgVo>();
        for (int i = index; i < index + page.getPageSize(); i++) {
            if (i < psnAndOrgVos.size()) {
                psnAndOrgVoss.add(psnAndOrgVos.get(i));
            }
        }
        page.setResult(psnAndOrgVoss);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;
    }


    @RequestMapping(value = "/checkExistRelated")
    @ResponseBody
    public Map<String, Object> checkExistRelated(Date investmentBalanceTime, String relNumber) {
        Map<String, Object> tips = Maps.newHashMap();
        try {
        if (ObjectUtil.isNotEmpty(investmentBalanceTime) && StringUtils.isNotEmpty(relNumber)) {


            Date startTime = DateUtil.beginOfQuarter(investmentBalanceTime);
            Date endTime = DateUtil.endOfQuarter(investmentBalanceTime);

            boolean flag = relFundsService.checkExistRelated(startTime.toString(), endTime.toString(), relNumber);
            if (flag) {
                tips.put(WebCode.SUCCESS, true);
                tips.put(WebCode.MSG, "单一关联方数据不存在");
            }else {
                tips.put(WebCode.SUCCESS, false);
                tips.put(WebCode.MSG, "单一关联方数据已经存在，同一季度不能重复添加！");
            }
        }else {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "传入参数不能为空！");
        }
    } catch (Exception e) {
        logger.info(e.getMessage());
        tips.put(WebCode.SUCCESS, false);
        tips.put(WebCode.MSG, WebCode.REQUEST_MISSING_UNKNOWN);
    }
        return tips;

    }
}
