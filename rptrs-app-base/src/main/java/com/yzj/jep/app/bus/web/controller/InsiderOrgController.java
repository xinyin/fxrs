/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月23日 13:59:30
 *
 */

package com.yzj.jep.app.bus.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.BatchNumberUtil;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.entity.BasicNameValuePair;
import com.yzj.jep.web.entity.NameValuePair;
import com.yzj.jep.web.util.RequestUtils;
import com.yzj.jep.workflow.impl.model.StaffImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/bus/insiderOrg")
public class InsiderOrgController extends CrudController<RelOrg> {
    private static Logger logger = LoggerFactory.getLogger(InsiderOrgController.class);

    @Autowired
    private InsiderOrgService insiderOrgService;

    @Autowired
    private RelService relService;

    @Autowired
    private RelPsnService relPsnService;

    @Autowired
    private DictItemService dictItemService;

    @Autowired
    private RelDictService relDictService;
    @Autowired
    private RelFileService relFileService;

    @Autowired
    private RelOrgService relOrgService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {


        return "bus/insiderOrgList";
    }

    //跳转关系机构信息
    @RequestMapping(value = "/insiderList/{orgid}")
    public String list(Model model, @PathVariable Integer orgid) {
        model.addAttribute("orgid", orgid);

        return "bus/insiderList";
    }


    @Override
    public Map<String, Object> query(Page<RelOrg> page, Model model, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        // filters.add(new Filter("validStatus", "1"));

        // prepareQuery(page, filters, orders, request);
        RelOrg relOrg = new RelOrg();
        for (Filter f : filters) {
            if ("OrgName".equals(f.getProperty())) {
                relOrg.setOrgName(f.getValue().toString());
            } else if ("approvalStatus".equals(f.getProperty())) {
                relOrg.setApprovalStatus(f.getValue().toString());
            } else if ("identifyType".equals(f.getProperty())) {
                relOrg.setIdentifyType(f.getValue().toString());
            } else if ("isBlack".equals(f.getProperty())) {
                relOrg.setIsBlack(f.getValue().toString());
            } else if ("regulatorOth1".equals(f.getProperty())) {
                relOrg.setRegulatorOth1(f.getValue().toString());
            } else if ("uniscCode".equals(f.getProperty())) {
                relOrg.setUniscCode(f.getValue().toString());
            }
        }

        PageHelper.start(page);
        //  relOrg.setValidStatus("1");
        List<RelOrg> relOrgs = insiderOrgService.selectListNew(relOrg);

        for (RelOrg org : relOrgs) {
            String validStatus = org.getValidStatus();
            if (validStatus == null || "".equals(validStatus) || "0".equals(validStatus)) {
                org.setPresetLimit("0");
            }
        }

        /*List<RelOrg> relOrgs = insiderOrgService.selectList(relOrg);*/
        PageInfo pageInfo = new PageInfo(relOrgs);
        result.put(RESULT_TOTAL, pageInfo.getTotal());
        result.put(RESULT_ROWS, pageInfo.getList());
        return result;
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<RelOrg> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        filters.add(new Filter("delflag", "1"));


        RelOrg relOrg = new RelOrg();
        for (Filter f : filters) {
            if ("OrgName".equals(f.getProperty())) {
                relOrg.setOrgName(f.getValue().toString());
            } else if ("approvalStatus".equals(f.getProperty())) {
                relOrg.setApprovalStatus(f.getValue().toString());
            } else if ("identifyType".equals(f.getProperty())) {
                relOrg.setIdentifyType(f.getValue().toString());
            } else if ("isBlack".equals(f.getProperty())) {
                relOrg.setIsBlack(f.getValue().toString());
            } else if ("regulatorOth1".equals(f.getProperty())) {
                relOrg.setRegulatorOth1(f.getValue().toString());
            } else if ("uniscCode".equals(f.getProperty())) {
                relOrg.setUniscCode(f.getValue().toString());
            }
        }
        StaffImpl staff = new StaffImpl();

//        Order order=new Order("modifiedTime","desc");
//        orders.add(order);
        insiderOrgService.query(page, filters, orders);
    }



    /*
     * 提交审批
     * */
    /*@RequestMapping(value = "/send/{pid}")
    @ResponseBody
    public Map<String, Object> send(@PathVariable Integer pid, Model model) {
        Map<String, Object> tips = new HashMap<String, Object>();
        RelOrg relOrg = relOrgService.get(pid);
        //查询机构的关系
        List<RelOrgVo> tzOrgPsn = relOrgService.getOrgOrg(String.valueOf(pid));
        List<RelPsnVo> relPsnVos = relOrgService.getOrgPsn(String.valueOf(pid));
        //如果存在集团和集团号，可以直接提交
        if (StringUtils.isNotEmpty(relOrg.getMemGroup())  &&  StringUtils.isNotEmpty(relOrg.getGroupName())){
            relOrg.setApprovalStatus("3");
//            relOrg.setStatus("3");
            relOrgService.updateSelective(relOrg);
            tips.put("data", relOrg);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
            return tips;
        }

        if (tzOrgPsn.size()<=0 && relPsnVos.size()<=0){
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG,"未设置关联关系");
            return tips;
        }
        if ("3".equals(relOrg.getApprovalStatus())) {
            //4 代表审批中
            relOrg.setApprovalStatus("1");
            relOrgService.updateSelective(relOrg);
            tips.put("data", relOrg);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } else {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "状态不符，提交失败！");
        }
        return tips;
    }*/


    /**
     * 提交审批
     *
     * @param pid
     * @param model
     * @return
     */

    @RequestMapping(value = "/send/{pid}")
    @ResponseBody
    public Map<String, Object> send(@PathVariable Integer pid, Model model) {
        Map<String, Object> tips = new HashMap<String, Object>();
        RelOrg relOrg = relOrgService.get(pid);
        //
        List<RelOrgVo> tzOrgPsn = relOrgService.getOrgOrg(String.valueOf(pid));
        List<RelPsnVo> relPsnVos = relOrgService.getOrgPsn(String.valueOf(pid));
        //如果存在集团和集团号，可以直接提交
        if (StringUtils.isNotEmpty(relOrg.getMemGroup()) && StringUtils.isNotEmpty(relOrg.getGroupName())) {
            relOrg.setApprovalStatus("4");
            relOrgService.updateSelective(relOrg);
            tips.put("data", relOrg);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
            return tips;
        }

        if ("3".equals(relOrg.getApprovalStatus()) || "5".equals(relOrg.getApprovalStatus())) {
            //4 代表审批中
            relOrg.setApprovalStatus("4");
            relOrgService.updateSelective(relOrg);
            tips.put("data", relOrg);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } else {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "状态不符，提交失败！");
        }
        return tips;
    }


    /**
     * 新增页面
     *
     * @param model
     * @param model
     * @param model
     * @param model
     * @return
     */
  /*  @RequestMapping(value = "/add")
    public String add(Model model) {
//        RelOrg relOrg = new RelOrg();
//        relOrg.setIdentifyType("UNIFIEDSOCIALCREDITCODE");
//        model.addAttribute("relOrg", relOrg);
        return "bus/insiderOrgEdit";
    }*/


    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {
        RelOrg relOrg = new RelOrg();
        relOrg.setIdentifyType("UNIFIEDSOCIALCREDITCODE");
        model.addAttribute("relOrg", relOrg);
        model.addAttribute("op", "0");
        return "bus/insiderOrgAdd";
    }


    /**
     * 编辑页面
     *
     * @param pid
     * @param model
     * @return
     */
    /*@RequestMapping(value = "/edit/{pid}")
    public String edit(@PathVariable Integer pid, Model model) {

        RelOrg org = insiderOrgService.get(pid);
        //RelOrgVo relOrg = new RelOrgVo();
//        BeanUtils.copyProperties(org, relOrg);
//        if ("1".equals(org.getIsBlack())) {
//            relOrg.setBlackReason("好多原因");
//        }
        model.addAttribute("relOrg", org);
        model.addAttribute("opt", "edit");

        return "bus/insiderOrgEdit";
    }*/


    /**
     * 编辑页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{pid}")
    public String edit(@PathVariable Integer pid, Model model) {

        RelOrg org = insiderOrgService.get(pid);
        RelOrgVo relOrg = new RelOrgVo();
        BeanUtils.copyProperties(org, relOrg);
        if ("1".equals(org.getIsBlack())) {
            relOrg.setBlackReason("好多原因");
        }
        model.addAttribute("relOrg", org);
        model.addAttribute("opt", "edit");


        return "bus/insiderOrgAdd";
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

        RelOrg relOrg = insiderOrgService.get(pid);
        model.addAttribute("relOrg", relOrg);
        model.addAttribute("opt", "edit");
        model.addAttribute("op", "1");
//        return "bus/relOrgEdit";
//        return "bus/relOrgView";
        return "bus/insiderOrgViewCope";
    }

    /**
     * 保存
     *
     * @param relOrg
     * @return
     */
    /*@RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(RelOrg relOrg, TraEnclosureVo traEnclosureVo) {

        Map<String, Object> tips = new HashMap<String, Object>();
        Boolean isExist = false;
        String relSerial = "";
        if (relOrg.getPid() != null) {
            isExist = insiderOrgService.exists(relOrg.getPid());
        }
        try {
            if (isExist) {
                insiderOrgService.updateSelective(relOrg);
                relSerial = relPsnService.get(relOrg.getPid()).getRelSerial();
            } else {
                List<RelOrg> relOrgList = insiderOrgService.select(relOrg);
                if (relOrgList.size() > 0) {
                    relOrg = relOrgList.get(0);
                } else {
                    relOrg.setApprovalStatus("3");
                    relOrg.setDataSource("1");//数据来源为手工录入
//                    relOrg.setStatus("1");
                    relOrg.setDelflag("1");
                    relOrg.setRelSerial(BatchNumberUtil.makeBatchNumber());
                    relOrg.setIsInsider("1");
                    insiderOrgService.insert(relOrg);
                    relOrg = insiderOrgService.select(relOrg).get(0);
                    Rel rel = new Rel();
                    rel.setToPid(String.valueOf(relOrg.getPid()));
                }
                relSerial = relOrg.getRelSerial();
            }
            List<Integer> docTableSelectItem = traEnclosureVo.getDocTableSelectItem();
            String merge=relOrg.getMgerOrg();
            relFileService.updateRelSerial(docTableSelectItem, relSerial,merge);
            tips.put(WebCode.SUCCESS, true);
//            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
            tips.put("pid", relOrg.getPid());
//            if (relOrg != null) {
//                tips.put("orgid", relOrg.getPid());
//            }
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        }
        return tips;
    }*/
    @RequestMapping(value = "/save")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> save(RelOrg relOrg, TraEnclosureVo traEnclosureVo) {

        relOrg.setApprovalStatus("3");
        Map<String, Object> tips = new HashMap<String, Object>();
        Boolean isExist = false;
        String relSerial = "";
        Integer index = 0;
        //根据部门存储数据权限
        UserProfile profile = UserProfileUtils.getUserProfile();
        relOrg.setMgerOrg(profile.getUnitCode());
        if (relOrg.getPid() != null) {
            isExist = relOrgService.exists(relOrg.getPid());
        }
        try {
            if (isExist) {
                relOrgService.updateSelective(relOrg);
                relSerial = relOrgService.get(relOrg.getPid()).getRelSerial();
            } else {
                List<RelOrg> relOrgList = relOrgService.select(relOrg);
                if (relOrgList.size() > 0) {
                    relOrg = relOrgList.get(0);
                } else {
                    //审批状态为待补录
                    relOrg.setIsBlack("0");//是否为黑名单 “0”否
                    relOrg.setDataSource("1");//数据来源为手工录入
                    relOrg.setApprovalStatus("3");
                    relOrg.setDelflag("1");
                    relOrg.setRelSerial(BatchNumberUtil.makeBatchNumber());
                    index = relOrgService.insert(relOrg);
                    relOrg = relOrgService.select(relOrg).get(0);
                    Rel rel = new Rel();
                    rel.setToPid(String.valueOf(relOrg.getPid()));
                }
                relSerial = relOrg.getRelSerial();
            }
            List<Integer> docTableSelectItem = traEnclosureVo.getDocTableSelectItem();
            String merge = relOrg.getMgerOrg();
            relFileService.updateRelSerial(docTableSelectItem, relSerial, merge);
            if (index != 0) {
                List<RelOrg> relorg = relOrgService.select(relOrg);
                tips.put("orgid", relorg.get(0).getPid());
                tips.put("data", relOrg);
            } else {
                tips.put("orgid", relOrg.getPid());
            }
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);

//            tips.put(WebCode.MSG, WebCode.Is_SET_REL);
//            if (relOrg != null) {
//                tips.put("orgid", relOrg.getPid());
//            }
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        }
        return tips;
    }


    /**
     * 保存
     *
     * @param relOrg
     * @return
     */
    @RequestMapping(value = "/save/{pid},{shareholdingRatio}")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> save(RelOrg relOrg, @PathVariable String pid, @PathVariable Float shareholdingRatio) {

        Map<String, Object> tips = new HashMap<String, Object>();
        Boolean isExist = false;
        if (relOrg.getPid() != null) {
            isExist = relOrgService.exists(relOrg.getPid());
        }
        try {
            if (isExist) {

                relOrgService.update(relOrg);
            } else {
                relOrg.setApprovalStatus("3");
                relOrg.setDelflag("1");
//                relOrg.setStatus("1");
                relOrgService.insert(relOrg);
                if (pid != null) {
                    RelOrg org = new RelOrg();
                    List<RelOrg> orgs = relOrgService.select(org);
                    List<RelOrg> relOrgs = relOrgService.select(relOrg);
                    Rel rel = new Rel();
                    rel.setFromPid(pid);
                    rel.setToPid(String.valueOf(relOrgs.get(0).getPid()));
                    rel.setFromMemo(orgs.get(0).getOrgName());
                    rel.setToMemo(relOrgs.get(0).getOrgName());
                    rel.setFromType("ORG");
                    rel.setToType("ORG");
                    if (shareholdingRatio != null) {
                        rel.setToPidShareholders("1");
                        rel.setShareholdingRatio(shareholdingRatio);
                    }
                    relService.insert(rel);
                }

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
            insiderOrgService.deleteByPrimaryKey(pid);
            RelOrg relOrg = new RelOrg();
            relOrg.setDelflag("0");
            relOrg.setPid(pid);
            insiderOrgService.updateSelective(relOrg);
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

        boolean exist = insiderOrgService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }

    /**
     * 附属信息跳转页面
     */
    @RequestMapping(value = "/listAffi/{orgid}")
    public String listAffi(Model model, @PathVariable Integer orgid) {
        model.addAttribute("orgid", orgid);
        return "bus/insiderPsnListAffi";
    }

    /**
     * 附属信息编辑页面
     */
    @RequestMapping(value = "/editAffi/{orgid}")
    public String editAffi(Model model, @PathVariable Integer orgid) {
        model.addAttribute("orgid", orgid);
        return "bus/relPsnEditGZ";
    }

    /**
     * 编辑页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/editAffi/{orgid}/{relId}/{pid}")
    public String editAffi(@PathVariable Integer pid, Model model, @PathVariable Integer orgid, @PathVariable Integer relId) {

        RelPsn psn = relPsnService.get(pid);
        RelPsnVo relPsn = new RelPsnVo();
        BeanUtils.copyProperties(psn, relPsn);
        Rel rel = relService.get(relId);
        relPsn.setRelCategory(rel.getRelCategory());
        if (rel.getFromPid().equals(orgid)) {
            relPsn.setRelCategorySub(rel.getFromName());
        } else {
            relPsn.setRelCategorySub(rel.getToName());
        }
        RelDict relDict = new RelDict();
        if (relPsn.getRelCategorySub().contains(",")) {
            String code = "";
            String categorySub = relPsn.getRelCategorySub();
            String[] strings = categorySub.split(",");
            for (String str : strings) {
                RelDict relDict1 = new RelDict();
                relDict1.setRelCategorySub(str);
                RelDict dict = relDictService.select(relDict1).get(0);
                String subCode = dict.getRelCategorySubCode();
                code = code + "," + subCode;
            }
            code = code.substring(1, code.length());
            relPsn.setRelCategorySub(code);
        } else {
            relDict.setRelCategorySub(relPsn.getRelCategorySub());
            RelDict dict = relDictService.select(relDict).get(0);
            relPsn.setRelCategorySub(dict.getRelCategorySubCode());
        }
        model.addAttribute("relPsn", relPsn);
        model.addAttribute("opt", "edit");
        model.addAttribute("orgid", orgid);
        model.addAttribute("pid", pid);
        model.addAttribute("relId", relId);

        if (rel.getShareholdingRatio() != null) {
            relPsn.setShareholdingRatio(rel.getShareholdingRatio());
            return "bus/relPsnEditTZ";
        } else {
            return "bus/relPsnEditGZ";
        }
//        return "";

    }

    /**
     * 附属信息页面加载
     */
    @RequestMapping(value = {"/queryPsnList/{pid}"}, produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> queryPsnList(Page<RelOrg> page, Model model, HttpServletRequest request, @PathVariable Integer pid) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        PageHelper.start(page);
        List<RelOrg> relPsnVos = insiderOrgService.getOrgPsn(String.valueOf(pid));
        PageInfo pageInfo = new PageInfo(relPsnVos);
        result.put(RESULT_TOTAL, pageInfo.getTotal());
        result.put(RESULT_ROWS, pageInfo.getList());
        return result;
    }

    /**
     * 关系自然人信息页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/affiliateeList/{pid}")
    public String affiliateView(@PathVariable String pid, Model model) {

        model.addAttribute("pid", pid);

        return "bus/affiliateeList";
    }

    /**
     * 删除
     *
     * @param relId
     * @return
     */
    @RequestMapping(value = "/removeOrg/{relId}")
    @ResponseBody
    public Map<String, Object> removeOrg(@PathVariable Integer relId) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            Rel rel = new Rel();
            rel.setPid(relId);
            rel.setDelflag("0");
            relService.updateSelective(rel);
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
     * 查询关联关系查询联动列表
     */
    @RequestMapping(value = "queryCategorySub/{category}")
    @ResponseBody
    public List<NameValuePair> queryCategorySub(@PathVariable String category, @RequestParam(value = "excludes", required = false) String excludes) {
        String simpleName = "BUS-RelCategory";
        List<NameValuePair> items = new ArrayList<>();

        try {

            List<Filter> filters = new Filter.Builder().eq("dictCode", simpleName).build();
            List<DictItem> list = dictItemService.select(filters);

            //过滤掉不要的字典项
            if (!CollectionUtils.isEmpty(list)) {
                for (int i = 0; i < list.size(); i++) {
                    DictItem item = list.get(i);
                    if (org.springframework.util.StringUtils.hasText(excludes) && excludes.contains(item.getItemValue())) {
                        continue;
                    }
                    if (category.contains(item.getItemValue())) {
                        BasicNameValuePair nv = new BasicNameValuePair(item.getItemLabel(), item.getItemValue());
                        items.add(nv);
                    }
                }
            }
            return items;
        } catch (ServiceException se) {
            logger.error("系统出现异常：" + se.getMessage());
            throw se;
        }
    }

    /**
     * 新增关系自然人信息页面
     *
     * @param model
     * @return
     */
    /*@RequestMapping(value = "/addAffiliate/{fromPid}")
    public String addAffiliate(@PathVariable Integer fromPid, Model model) {
        model.addAttribute("opt", "add");
        // 自然人pid
        model.addAttribute("fromPid", fromPid);
        return "bus/naturalPersonAddOrEdit";
    }*/
    @RequestMapping(value = "/addAffiliate/{fromPid}")
    public String addAffiliate(@PathVariable Integer fromPid, Model model) {
        model.addAttribute("opt", "add");
        // 自然人pid
        model.addAttribute("fromPid", fromPid);
        return "bus/orgInsiderPsnAddOrEdit";
    }


    /**
     * 加入/移出黑名单
     *
     * @param pid
     * @param model-
     * @return
     */
    /*@RequestMapping(value = "/addOrRemoveBlack/{pid}")
    public String addBlack(@PathVariable Integer pid, Model model) {
        RelOrg relOrg = insiderOrgService.get(pid);
        model.addAttribute("relOrg", relOrg);
        if (relOrg.getIsBlack().equals("0")) {
            model.addAttribute("addOrRemoveBlack", "加入黑名单原因");
        } else {
            model.addAttribute("addOrRemoveBlack", "移出黑名单原因");
        }
        return "bus/addOrRemoveBlackOrg";
    }*/

    /**
     * 加入/移出黑名单
     *
     * @param
     * @return
     */
   /* @RequestMapping(value = "/addOrRemoveBlack")
    @ResponseBody
    public Map<String, Object> addBlack(RelOrg relOrg) {

        Map<String, Object> tips = new HashMap<String, Object>();
        RelOrg relOrg1 = insiderOrgService.get(relOrg.getPid());
        try {
            relOrg1.setApprovalStatus("4");
            relOrg1.setBlackReason(relOrg.getBlackReason());
            insiderOrgService.update(relOrg1);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, "提交成功");
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "提交失败");
        }

        return tips;
    }*/

    /**
     * 提交审核所需数据
     *
     * @param pid
     * @return
     */
    @RequestMapping(value = "/subAudit/{pid}")
    @ResponseBody
    public JSONObject subAudit(@PathVariable Integer pid) {
        JSONObject result = new JSONObject();
        RelOrg relOrg = insiderOrgService.get(pid);
        result.put("relOrg", relOrg);
        return result;
    }

    /**
     * 解除关联关系
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/disconnection/{pid}")
    public String disconnection(@PathVariable Integer pid, Model model) {
        model.addAttribute("toPid", pid);
        return "bus/disconnectionOrg";
    }

    /**
     * 解除关联关系
     *
     * @param
     * @return
     */
    /*@RequestMapping(value = "/disconnection")
    @ResponseBody
    public Map<String, Object> disconnection(RelPsnVo relPsnVo) {
        RelService relService = SpringContextHolder.getBean(RelService.class);
        Map<String, Object> tips = new HashMap<String, Object>();
        try {
            RelOrg relOrg = insiderOrgService.get(relPsnVo.getToPid());
            relOrg.setApprovalStatus("4");
            insiderOrgService.update(relOrg);
            Rel rel = new Rel();
            rel.setToPid(relPsnVo.getToPid().toString());
            List<Rel> relList = relService.select(rel);
            if (relList.size() > 0) {
                for (Rel rel1 : relList) {
                    rel1.setLiftReason(relPsnVo.getLiftReason());
                    relService.update(rel1);
                    relService.removeRel(rel1.getFromPid(), rel1.getToPid(), rel1.getFromType(), rel1.getToType());
                }
            } else {
                Rel rel1 = new Rel();
                rel1.setFromPid(relPsnVo.getToPid().toString());
                relList = relService.select(rel1);
                if (relList.size() > 0) {
                    for (Rel rel2 : relList) {
                        rel2.setLiftReason(relPsnVo.getLiftReason());
                        relService.update(rel2);
                        relService.removeRel(rel2.getFromPid(), rel2.getToPid(), rel2.getFromType(), rel2.getToType());
                    }
                }
            }
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, "提交成功");
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "提交失败");
        }
        return tips;
    }
*/

    /**
     * 保存关系机构信息
     *
     * @param relOrgVo
     * @return
     */
    @RequestMapping("/saveOrg")
    @ResponseBody
    public Map<String, Object> saveOrg(RelOrgVo relOrgVo) {
        //调用业务层方法
        RelDictService relDictService = SpringContextHolder.getBean(RelDictService.class);
        RelService relService = SpringContextHolder.getBean(RelService.class);
        RelOrgService relOrgService = SpringContextHolder.getBean(RelOrgService.class);

        //创建一个Map集合去存储捕捉的异常信息并将其在前台展示
        Map<String, Object> tips = new HashMap<String, Object>();
        Boolean isExist = false;
        Boolean isOrgExist = false;
        RelOrg org = new RelOrg();

        //创建关联表对象，并将fromType、toType、fromName、toName、fromID、关系类型、关系子类型存进去
        Rel rel = new Rel();
        rel.setFromType("ORG");
        rel.setToType("ORG");
        rel.setFromPid(String.valueOf(relOrgVo.getFromPid()));
        RelDict relDict = relDictService.selectDict(relOrgVo.getRelCategory(), relOrgVo.getRelCategorySub());
        rel.setFromName(relDict.getFromName());
        rel.setToName(relDict.getToName());
        rel.setRelCategory(relOrgVo.getRelCategory());
        rel.setRelCategorySub(relOrgVo.getRelCategorySub());

        //如果持股比例不为空并且关系类型为投资关系时，将持股比例set进关联表中;反之则将持股比例为0股set进去
        if (relOrgVo.getShareholdingRatio() != null && "C".equals(relOrgVo.getRelCategory())) {
            rel.setShareholdingRatio(relOrgVo.getShareholdingRatio());
        } else {
            rel.setShareholdingRatio(0f);
        }

        //创建一个新的RelOrg对象，并采用BeanUtils.copyProperties()方法进行relOrg和relOrgVo之间属性的赋值
        RelOrg relOrg = new RelOrg();
        BeanUtils.copyProperties(relOrgVo, relOrg);
        //若是展示表中的pid不为空，则对是否存在关联关系进行判断
        if (relOrgVo.getPid() != null) {
            isExist = relService.exists(relOrgVo.getRelId());
            isOrgExist = relOrgService.exists(relOrgVo.getPid());
        }

        //
        if (relOrgVo.getUniscCode() != null) {
            RelOrg orgCon = new RelOrg();
            orgCon.setUniscCode(relOrgVo.getUniscCode());
            //创建一个集合去存储关系机构，若是存在，则更改状态值
            List<RelOrg> relOrgs = relOrgService.select(orgCon);
            if (relOrgs.size() > 0) {
                org = relOrgs.get(0);
                isOrgExist = true;
            }
        }
        try {
            if (isExist) {
                rel.setPid(relOrgVo.getRelId());
                relService.updateSelective(rel);
                relOrgService.updateSelective(relOrg);
            } else {
                if (!isOrgExist) {
                    org = relOrgService.select(relOrg).get(0);
                    relOrgService.insert(relOrg);
                }
                rel.setToPid(String.valueOf(org.getPid()));
                rel.setDelflag("1");
                relService.insert(rel);
            }
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
            tips.put(WebCode.SUCCESS, true);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        }
        return tips;
    }


}