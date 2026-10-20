/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月23日 13:59:30
 *
 */

package com.yzj.jep.app.bus.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.BatchNumberUtil;
import com.yzj.jep.app.util.ConfirmUtil;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.sys.code.DutySource;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.model.Duty;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.sys.service.DutyService;
import com.yzj.jep.sys.service.NotifyService;
import com.yzj.jep.sys.service.UnitService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.entity.BasicNameValuePair;
import com.yzj.jep.web.entity.NameValuePair;
import com.yzj.jep.web.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/bus/relOrgOfPro")
public class RelOrgOfProController extends CrudController<RelOrg> {
    private static Logger logger = LoggerFactory.getLogger(RelOrgOfProController.class);

    @Autowired
    private DutyService dutyService;
    @Autowired
    private NotifyService notifyService;

    @Autowired
    private RelOrgService relOrgService;

    @Autowired
    private RelProService relProService;

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
    private AligMapService aligMapService;
    @Autowired
    private UnitService unitService;

    @Autowired
    private RelPsnAndOrgOfProService relPsnAndOrgOfProService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "bus/relOrgList";
    }

    @Override
    public Map<String, Object> query(Page<RelOrg> page, Model model, HttpServletRequest request) {
        UserProfile profile = UserProfileUtils.getUserProfile();
        List<String> codes = unitService.selectUnitAllOrDeptAllCodes(profile.getUnitCode());
        String mgerOrg = "";
        for (int i = 0; i < codes.size(); i++) {
            String s = codes.get(i);
            if (i == codes.size() - 1) {
                mgerOrg += s;
                break;
            }
            mgerOrg += s + ",";
        }
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        // prepareQuery(page, filters, orders, request);
        RelOrg relOrg = new RelOrg();
        relOrg.setMgerOrg(mgerOrg);
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
            } else if ("dataSource".equals(f.getProperty())) {
                relOrg.setDataSource(f.getValue().toString());
            }
        }
        PageHelper.start(page);
        List<RelOrg> relOrgs = relOrgService.selectList(relOrg);
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
        relOrgService.query(page, filters, orders);
    }

    /*
     * 提交审批
     * */
    @RequestMapping(value = "/send/{pid}")
    @ResponseBody
    public Map<String, Object> send(@PathVariable Integer pid, Model model) {
        Map<String, Object> tips = new HashMap<String, Object>();
        RelOrg relOrg = relOrgService.get(pid);
        //查询机构的关系
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

        if (tzOrgPsn.size() <= 0 && relPsnVos.size() <= 0) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "未设置关联关系");
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
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {
        RelOrg relOrg = new RelOrg();
        relOrg.setIdentifyType("UNIFIEDSOCIALCREDITCODE");
        model.addAttribute("relOrg", relOrg);
        model.addAttribute("op", "0");
        return "bus/relOrgAdd";
    }


    /**
     * 归属自然人信息页面加载
     */
    @RequestMapping(value = {"/queryPsnList"}, produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> queryPsnList(Page<Rel> page, Model model, HttpServletRequest request, Integer orgid) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        PageHelper.start(page);
        List<RelPsnVo> relPsnVos = relOrgService.getOrgPsn(String.valueOf(orgid));
        PageInfo pageInfo = new PageInfo(relPsnVos);
        result.put(RESULT_TOTAL, pageInfo.getTotal());
        result.put(RESULT_ROWS, pageInfo.getList());
        return result;
    }

    /**
     * 下级自然人信息页面加载
     */
    @RequestMapping(value = {"/queryDownPsnList"}, produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> queryDownPsnList(Page<Rel> page, Model model, HttpServletRequest request, Integer orgid) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        PageHelper.start(page);
        List<RelPsnVo> relPsnVos = relOrgService.getDownOrgPsn(String.valueOf(orgid));
        PageInfo pageInfo = new PageInfo(relPsnVos);
        result.put(RESULT_TOTAL, pageInfo.getTotal());
        result.put(RESULT_ROWS, pageInfo.getList());
        return result;
    }


    /**
     * 编辑页面
     *
     * @param
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{pid}")
    public String edit(@PathVariable Integer pid, int op, Model model) {

        RelOrg org = relOrgService.get(pid);
        RelOrgVo relOrg = new RelOrgVo();
        BeanUtils.copyProperties(org, relOrg);
        if ("1".equals(org.getIsBlack())) {
            relOrg.setBlackReason("好多原因");
        }
        model.addAttribute("relOrg", relOrg);
        model.addAttribute("opt", "edit");
        model.addAttribute("op", op);
        return "bus/relOrgEdit";
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

        RelOrg relOrg = relOrgService.get(pid);
        model.addAttribute("relOrg", relOrg);
        model.addAttribute("opt", "edit");

        model.addAttribute("op", "1");
        return "bus/relOrgEdit";
    }

    /**
     * 保存
     *
     * @param relOrg
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(RelOrg relOrg, TraEnclosureVo traEnclosureVo) {

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
                    relOrg.setApprovalStatus("1");
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
        RelOrg relOrg = relOrgService.get(pid);
        relOrg.setDelflag("0");
        relOrgService.updateSelective(relOrg);
        tips.put(WebCode.SUCCESS, true);
        tips.put(WebCode.MSG, "删除成功");
//        try {
//            //relOrgService.deleteByPrimaryKey(pid);
//            RelOrg relOrg = new RelOrg();
//            relOrg.setDelflag("0");
//            relOrg.setPid(pid);
//            relOrgService.updateSelective(relOrg);
//            tips.put(WebCode.SUCCESS, true);
//            tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
//        } catch (ServiceException e) {
//            logger.info(e.getMessage());
//            tips.put(WebCode.SUCCESS, false);
//            tips.put(WebCode.MSG, WebCode.FAILURE_DELETE);
//        }

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

        boolean exist = relOrgService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }

    /**
     * 自然人信息跳转页面
     */
    @RequestMapping(value = "/listAffi/{orgid}")
    public String listAffi(Model model, int op, @PathVariable Integer orgid) {
        model.addAttribute("orgid", orgid);
        model.addAttribute("op", op);
        return "bus/relPsnListAffi";
    }

    /**
     * 附属信息编辑页面
     */
    @RequestMapping(value = "/editAffi/{orgid}")
    public String editAffi(Model model, @PathVariable Integer orgid) {
        model.addAttribute("orgid", orgid);
        return "EditGZ";
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
            return "EditTZ";
        } else {
            return "EditGZ";
        }

    }

//    /**
//     * 附属信息页面加载
//     */
//    @RequestMapping(value = {"/queryPsnList"}, produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
//    @ResponseBody
//    public Map<String, Object> queryPsnList(Page<Rel> page, Model model, HttpServletRequest request, Integer orgid) {
//        Map<String, Object> result = new HashMap<String, Object>();
//        List<Filter> filters = RequestUtils.buildFilters(request);
//        List<Order> orders = RequestUtils.buildOrders(request);
//        PageHelper.start(page);
//        List<RelPsnVo> relPsnVos = relOrgService.getOrgPsn(String.valueOf(orgid));
//        PageInfo pageInfo = new PageInfo(relPsnVos);
//        result.put(RESULT_TOTAL, pageInfo.getTotal());
//        result.put(RESULT_ROWS, pageInfo.getList());
//        return result;
//    }

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
     * 加入/移出黑名单
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/addOrRemoveBlack/{pid}")
    public String addBlack(@PathVariable Integer pid, Model model) {
        RelOrg relOrg = relOrgService.get(pid);
        model.addAttribute("relOrg", relOrg);
        if ("0".equals(relOrg.getIsBlack())) {
            model.addAttribute("addOrRemoveBlack", "加入黑名单原因");
        } else {
            model.addAttribute("addOrRemoveBlack", "移出黑名单原因");
        }
        return "bus/addOrRemoveBlackOrg";
    }

    /**
     * 加入/移出黑名单
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/addOrRemoveBlack")
    @ResponseBody
    public Map<String, Object> addBlack(RelOrg relOrg) {

        Map<String, Object> tips = new HashMap<String, Object>();
        RelOrg relOrg1 = relOrgService.get(relOrg.getPid());
        try {
            relOrg1.setApprovalStatus("4");
            relOrg1.setBlackReason(relOrg.getBlackReason());
            relOrgService.update(relOrg1);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, "提交成功");
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "提交失败");
        }

        return tips;
    }

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
        RelOrg relOrg = relOrgService.get(pid);
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
    @RequestMapping(value = "/disconnection")
    @ResponseBody
    public Map<String, Object> disconnection(RelPsnVo relPsnVo) {
        RelService relService = SpringContextHolder.getBean(RelService.class);
        Map<String, Object> tips = new HashMap<String, Object>();
        try {
            RelOrg relOrg = relOrgService.get(relPsnVo.getToPid());
            relOrg.setApprovalStatus("4");
            relOrgService.update(relOrg);
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

    @RequestMapping("/impor")
    @ResponseBody
    @Transactional
    public Map<String, Object> imporExcel(HttpServletRequest request, HttpServletResponse response, Model model) {
        //@RequestParam("file") MultipartFile file
//        MultipartHttpServletRequest multipartHttpServletRequest =(MultipartHttpServletRequest)request;
//        MultipartFile file = multipartHttpServletRequest.getFile("file");
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setServletContext(request.getSession().getServletContext());
        MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart(request);
        MultipartFile file = multipartRequest.getFile("file");
        Map<String, Object> map = new HashMap<String, Object>();
        InputStream is = null;
        try {
            is = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        relOrgService.insertExcelToDb(is, file);
        map.put(WebCode.SUCCESS, true);
        return map;
    }

    /**
     * 关系自然人信息页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/affiliateList/{pid}")
    public String affiliateView(@PathVariable String pid, Model model) {
        model.addAttribute("orgid", pid);
        model.addAttribute("op", "0");
        return "bus/relPsnListAffi";
    }

    /**
     * 关系自然人信息列表
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/affiliate/{pid}")
    @ResponseBody
    public Map<String, Object> affiliate(@PathVariable String pid, Model model, Page<RelPsnVo> page) {
        List<RelPsnVo> relPsnVoList = relPsnService.selectRelPsnInformation(pid);
        Map<String, Object> result = new HashMap<>();
        page.setTotal(relPsnVoList.size());
        int pages = (relPsnVoList.size() + page.getPageSize() - 1) / page.getPageSize();
        if (page.getPageNumber() > pages) {
            page.setPageNumber(1);
        }
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        List<RelPsnVo> relPsnVos = new ArrayList<RelPsnVo>();
        for (int i = index; i < index + page.getPageSize(); i++) {
            if (i < relPsnVoList.size()) {
                relPsnVos.add(relPsnVoList.get(i));
            }
        }
        page.setResult(relPsnVos);

        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());

        return result;
    }

    /**
     * 新增关系自然人信息页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/addAffiliate/{fromPid}")
    public String addAffiliate(@PathVariable Integer fromPid, Model model) {
        model.addAttribute("opt", "add");
        // 自然人pid
        model.addAttribute("fromPid", fromPid);
        return "bus/orgRelPsnAddOrEdit";
    }

    /**
     * 查看关系自然人信息页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/viewAffiliate/{relId}/{fromPid}")
    public String addAffiliate(@PathVariable Integer relId, @PathVariable Integer fromPid, Model model) {
        model.addAttribute("opt", "edit");
        List<RelPsnVo> relPsnVos = relOrgService.getOrgPsn(String.valueOf(fromPid));
        //取出符合条件的数据
        List<RelPsnVo> vos = relPsnVos.stream().filter(item -> item.getRelId().equals(relId)).collect(Collectors.toList());
        if (vos.size() > 0) {
            RelPsnVo relPsnVo = vos.get(0);
            model.addAttribute("relPsnVo", relPsnVo);
        }
        // 自然人pid
        model.addAttribute("fromPid", fromPid);
        return "bus/orgRelPsnView";
    }


    /**
     * 编辑关系自然人信息页面
     *
     * @param pid
     * @param model
     * @return pid  详情id
     * relId  关系对应id
     * fromPid 入口id
     */
    @RequestMapping(value = "/editAffiliate/{pid}/{relId}/{fromPid}")
    public String editAffiliate(@PathVariable Integer pid, @PathVariable Integer relId, @PathVariable Integer fromPid, Model model) {

        RelPsn relPsn = relPsnService.get(pid);
        RelService relService = SpringContextHolder.getBean(RelService.class);
        Rel rel = relService.get(relId);
        RelPsnVo relPsnVo = new RelPsnVo();
        BeanUtils.copyProperties(relPsn, relPsnVo);
        relPsnVo.setRelCategory(rel.getRelCategory());
        if (rel.getFromPid().equals(fromPid.toString())) {
            relPsnVo.setRelCategorySub(rel.getToName());
        } else {
            relPsnVo.setRelCategorySub(rel.getFromName());
        }
        RelDictService relDictService = SpringContextHolder.getBean(RelDictService.class);
        List<Filter> filters = new Filter.Builder().eq("relCategorySub", relPsnVo.getRelCategorySub()).build();
        List<RelDict> relDictList = relDictService.select(filters);
        relPsnVo.setRelCategorySub(relDictList.get(0).getRelCategorySubCode());
        model.addAttribute("relPsnVo", relPsnVo);
        model.addAttribute("opt", "edit");
        model.addAttribute("pid", pid);
        model.addAttribute("relId", relId);
        model.addAttribute("fromPid", fromPid);

        return "bus/orgRelPsnAddOrEdit";
    }

    /**
     * 保存关系自然人信息
     *
     * @param relPsnVo
     * @return
     */
    @RequestMapping(value = "/saveAffiliate/{parentPid}/{fromPid}")
    @ResponseBody
    public Map<String, Object> saveAffiliate(@PathVariable String parentPid, @PathVariable String fromPid, RelPsnVo relPsnVo) {
        Map<String, Object> tips = new HashMap<String, Object>();
        //归属关联方
        RelPsn fromPsn = relPsnService.get(Integer.valueOf(parentPid));
        //新增关系to机构
        RelOrg relOrg = relOrgService.get(Integer.valueOf(fromPid));
//        BeanUtils.copyProperties(relPsnVo, relPsn);
        String fromAlig = fromPsn.getAligCode();
        String chAligCode = relOrg.getAligCode();
        //新增关联方根据上级定为码以及关系判断是否关联方,更新to定为码
        if (StringUtils.isNotEmpty(fromAlig)) {
            //更新关系表
            Rel rel = new Rel();
            //归属关联方pid
            rel.setFromPid(parentPid);
            //添加基本信息自然人的pid
            rel.setToPid(fromPid);
            rel.setRelCategory(relPsnVo.getRelCategory());
            rel.setRelCategorySub(relPsnVo.getRelCategorySub());
            Rel dbRel = relService.get(rel);
            //如果查到两人间有相同关系，更新原先关系
            boolean isExist = false;
            if (dbRel != null) {
                isExist = true;
                rel.setPid(dbRel.getPid());
            }
            String[] aligs = fromAlig.split(",");
            for (String alig : aligs) {
                List<Filter> aligFilters = new Filter.Builder()
                        .eq("paAligCode", alig)
                        .eq("relCategoryVal", relPsnVo.getRelCategory())
                        .eq("assoTypeVal", relPsnVo.getRelCategorySub())
                        .build();
                AligMap aligMap = aligMapService.get(aligFilters);
                //to定为码不为空则为关联方，需要更新定为码
                if (aligMap != null) {
                    String toAligCode = aligMap.getChAligCode();
                    //判断to定为码是否已存在,确保不重复加
                    if (StringUtils.isNotEmpty(chAligCode)) {
                        String[] chAligCodes = chAligCode.split(",");
                        List<String> codeList = Arrays.asList(chAligCodes);
                        if (!codeList.contains(toAligCode)) {
                            relOrg.setAligCode(chAligCode + "," + toAligCode);
                        }
                    } else {
                        relOrg.setAligCode(toAligCode);
                    }
                    //存口径
                    relOrg.setRegulatorOth1(aligMap.getCaliber());
                    //关系设置定为码
                    rel.setFromAligCode(alig);
                    rel.setToAligCode(toAligCode);
                    rel.setRegulator(aligMap.getCaliber());
                    rel.setDelflag("1");
                    rel.setFromType("PSN");
                    rel.setToType("ORG");
                    if (relPsnVo.getShareholdingProportion() != null) {
                        rel.setShareholdingRatio(Float.parseFloat(relPsnVo.getShareholdingProportion()));
                    }
                    //关联人设置口径
                    relOrg.setRegulatorOth1(aligMap.getCaliber());
                } else {
                    //为null  代表to 不是关联方，仅仅维持关系即可
                    //关系设置定为码
                    rel.setFromAligCode(alig);
                    rel.setDelflag("1");
                    rel.setFromType("PSN");
                    rel.setToType("ORG");
                    if (relPsnVo.getShareholdingProportion() != null) {
                        rel.setShareholdingRatio(Float.parseFloat(relPsnVo.getShareholdingProportion()));
                    }
                }
                //设置成待审批
                relOrg.setApprovalStatus("3");
                relOrgService.update(relOrg);
                //关系状态为待新增
                rel.setRelStatus("01");
                if (isExist) {
                    relService.update(rel);
                } else {
                    relService.insert(rel);
                }
                tips.put(WebCode.SUCCESS, true);
                tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
                break;
            }
        } else {
            //非关联方，返回不可添加提示
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "超过关联方关系判定范围");
        }
        return tips;
    }
//    public Map<String, Object> saveAffiliate(RelPsnVo relPsnVo) {
//        RelPsn relPsn = new RelPsn();
//        BeanUtils.copyProperties(relPsnVo, relPsn);
//        RelService relService = SpringContextHolder.getBean(RelService.class);
//        Rel rel = new Rel();
//        rel.setRelCategory(relPsnVo.getRelCategory());
//        rel.setRelCategorySub(relPsnVo.getRelCategorySub());
//
//        RelDictService relDictService = SpringContextHolder.getBean(RelDictService.class);
//        RelDict relDict = relDictService.selectDict(relPsnVo.getRelCategory(), relPsnVo.getRelCategorySub());
//        rel.setFromName(relDict.getFromName());
//        rel.setToName(relDict.getToName());
//        rel.setPid(relPsnVo.getRelId());
//
//        Map<String, Object> tips = new HashMap<>();
//        Boolean isExist = false;
//        RelPsn psn = new RelPsn();
//        if (relPsnVo.getRelId() != null && !relPsnVo.getRelId().equals("")) {
//            rel.setPid(relPsnVo.getRelId());
////            isExist = relPsnService.exists(relPsn.getPid());
//            isExist = relService.exists(rel.getPid());
//        }
//        try {
//            if (isExist) {
//                relPsnService.update(relPsn);
//                rel = relService.get(relPsnVo.getRelId());
//                rel.setFromPid(String.valueOf(relPsnVo.getFromPid()));
//                rel.setToPid(String.valueOf(relPsnVo.getPid()));
//                relService.update(rel);
//            } else {
//                List<RelPsn> psns = new ArrayList<>();
//                List<Rel> rels = null;
//                if (relPsnVo.getIdentifyNumber() != null && !relPsnVo.getIdentifyNumber().equals("")) {
//
//                    psn.setIdentifyNumber(relPsnVo.getIdentifyNumber());
//                    psns = relPsnService.select(psn);
//                } else {
//                    tips.put(WebCode.MSG, "证件号码为空");
//                    tips.put(WebCode.SUCCESS, false);
//                    return tips;
//                }
//                Rel rel1 = new Rel();
//                if (psns.size() > 0) {
//                    Rel relCheck = new Rel();
//                    relCheck.setToPid(String.valueOf(relPsnVo.getFromPid()));
//                    relCheck.setFromPid(String.valueOf(relPsnService.select(psn).get(0).getPid()));
//                    relCheck.setToType("ORG");
//                    relCheck.setFromType("PSN");
//                    List<Rel> rels1 = relService.select(relCheck);
////                    if (rels1.size() > 0) {
////                        //开始反转
////                        rel1.setFromPid(String.valueOf(relPsnVo.getFromPid()));
////                        rel1.setFromType("PSN");
////                        rel1.setToPid(String.valueOf(relPsnService.select(psn).get(0).getPid()));
////                        rel1.setToType("ORG");
////                    }
//                } else {
//                    //1未提交/2已提交/3未审批
//                    relPsn.setDelflag("1");// 逻辑删除标记
//                    relPsn.setStatus("3");//审批状态
//                    relPsn.setIsBlack("0");//是否黑名单
//                    relPsn.setDataSource("1");//数据来源为手工录入“0”
//                    relPsnService.insertSelective(relPsn);
//                    relPsn = relPsnService.get(relPsn);
////                    rel1.setFromPid(String.valueOf(relPsnService.select(psn).get(0).getPid()));
////                    rel1.setToPid(String.valueOf(relPsnVo.getFromPid()));
//                    if (StringUtils.isEmpty(rel1.getRelStatus())) {
//                        rel1.setRelStatus("01");
//                    }
//                    rel1.setFromPid(String.valueOf(relPsnService.select(psn).get(0).getPid()));
//                    rel1.setFromType("PSN");
//                    rel1.setToPid(String.valueOf(relPsnVo.getFromPid()));
//                    rel1.setToType("ORG");
//                    rel1.setRelCategory(relPsnVo.getRelCategory());
//                    rel1.setRelCategorySub(relPsnVo.getRelCategorySub());
//                    rels = relService.select(rel1);
//                }
//
////                rel1.setFromPid(String.valueOf(relPsnVo.getFromPid()));
////                rel1.setFromType("ORG");
////                rel1.setToType("PSN");
////                rel1.setRelCategory(relPsnVo.getRelCategory());
////                rel1.setRelCategorySub(relPsnVo.getRelCategorySub());
////                rels = relService.select(rel1);
//                if (rels != null && rels.size() > 0) {
//                    tips.put(WebCode.SUCCESS, false);
//                    tips.put(WebCode.MSG, "关系重复");
//                    return tips;
//                } else {
//                    rel.setToPid(String.valueOf(relPsnVo.getFromPid()));
//                    rel.setFromPid(String.valueOf(relPsn.getPid()));
//                    rel.setFromType("PSN");
//                    rel.setToType("ORG");
//                    //0是股东  1是关联方  2两种都是
//                    rel.setRelStatus("01"); // 01代表是新增
//                    rel.setDelflag("1");
//                    relService.insert(rel);
//                }
//            }
//            tips.put(WebCode.SUCCESS, true);
//            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
//        } catch (ServiceException e) {
//            logger.info(e.getMessage());
//            tips.put(WebCode.SUCCESS, false);
//            tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
//        }
//        return tips;
//    }

    /**
     * 关系机构信息页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/relOrgView/{pid}")
    public String relOrgView(@PathVariable String pid, Model model) {
        model.addAttribute("fromPid", pid);
        return "bus/relOrgView";
    }

    /**
     * 关系机构信息列表
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/relOrgList/{pid}")
    @ResponseBody
    public Map<String, Object> relOrgList(@PathVariable String pid, Model model, Page<RelOrgVo> page) {
        List<RelOrgVo> relOrgVoList = relOrgService.queryRelOrgInformation(Integer.valueOf(pid));
        Map<String, Object> result = new HashMap<>();
        page.setTotal(relOrgVoList.size());
        int pages = (relOrgVoList.size() + page.getPageSize() - 1) / page.getPageSize();
        if (page.getPageNumber() > pages) {
            page.setPageNumber(1);
        }
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        List<RelOrgVo> relOrgVos = new ArrayList<RelOrgVo>();
        for (int i = index; i < index + page.getPageSize(); i++) {
            if (i < relOrgVoList.size()) {
                relOrgVos.add(relOrgVoList.get(i));
            }
        }
        page.setResult(relOrgVos);

        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());

        return result;
    }

    /**
     * 新增关系机构信息页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/addOrg/{fromPid}")
    public String addOrg(@PathVariable Integer fromPid, Model model) {
        model.addAttribute("opt", "add");
        // 自然人pid
        model.addAttribute("fromPid", fromPid);
        return "bus/affiliateAddOrEdit";
    }

    /**
     * 编辑关系机构信息页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/editOrg/{pid}/{relId}/{fromPid}")
    public String editOrg(@PathVariable Integer pid, @PathVariable Integer relId, @PathVariable Integer fromPid, Model model) {
        RelService relService = SpringContextHolder.getBean(RelService.class);
        RelDictService relDictService = SpringContextHolder.getBean(RelDictService.class);
        RelOrg relOrg = relOrgService.get(pid);
        Rel rel = relService.get(relId);
        RelOrgVo relOrgVo = new RelOrgVo();
        BeanUtils.copyProperties(relOrg, relOrgVo);
        relOrgVo.setRelCategory(rel.getRelCategory());
        if (rel.getFromPid().equals(fromPid.toString())) {
            relOrgVo.setRelCategorySub(rel.getToName());
        } else {
            relOrgVo.setRelCategorySub(rel.getFromName());
        }
        List<Filter> filters = new Filter.Builder().eq("relCategorySub", relOrgVo.getRelCategorySub()).build();
        List<RelDict> relDictList = relDictService.select(filters);
        relOrgVo.setRelCategorySub(relDictList.get(0).getRelCategorySubCode());
        relOrgVo.setEffectiveDate(rel.getEffectiveDate());
        relOrgVo.setIneffectiveDate(rel.getIneffectiveDate());
        model.addAttribute("relPsnVo", relOrgVo);
        model.addAttribute("opt", "edit");
        model.addAttribute("pid", pid);
        model.addAttribute("relId", relId);
        model.addAttribute("fromPid", fromPid);

        return "bus/affiliateAddOrEdit";
    }

    /**
     * 保存关系机构页面
     *
     * @param relOrgVo
     * @return
     */
    @RequestMapping(value = "/saveOrg/{paFromPid}/{fromPid}")
    @ResponseBody
    public Map<String, Object> saveOrg(@PathVariable String paFromPid, @PathVariable String fromPid, RelOrgVo relOrgVo) {
        Map tips = new HashMap();
        //归属关联方
        RelOrg fromOrg = relOrgService.get(Integer.valueOf(paFromPid));
        //新增关系to机构
        RelOrg relOrg = relOrgService.get(Integer.valueOf(fromPid));
        String fromAlig = fromOrg.getAligCode();
        String chAligCode = relOrg.getAligCode();
        //新增关联方根据上级定为码以及关系判断是否关联方,更新to定为码
        if (StringUtils.isNotEmpty(fromAlig)) {
            //更新关系表
            Rel rel = new Rel();
            //归属关联方pid
            rel.setFromPid(paFromPid);
            //添加基本信息机构的pid
            rel.setToPid(fromPid);
            rel.setRelCategory(relOrgVo.getRelCategory());
            rel.setRelCategorySub(relOrgVo.getRelCategorySub());
            Rel dbRel = relService.get(rel);
            //如果查到两机构间有相同关系，更新原先关系
            boolean isExist = false;
            if (dbRel != null) {
                isExist = true;
                rel.setPid(dbRel.getPid());
            }
            String[] aligs = fromAlig.split(",");
            for (String alig : aligs) {
                List<Filter> aligFilters = new Filter.Builder()
                        .eq("paAligCode", alig)
                        .eq("relCategoryVal", relOrgVo.getRelCategory())
                        .eq("assoTypeVal", relOrgVo.getRelCategorySub())
                        .build();
                AligMap aligMap = aligMapService.get(aligFilters);
                //to定为码不为空则为关联方，需要更新定为码
                if (aligMap != null) {
                    String toAligCode = aligMap.getChAligCode();
                    //判断to定为码是否已存在,确保不重复加
                    if (StringUtils.isNotEmpty(chAligCode)) {
                        String[] chAligCodes = chAligCode.split(",");
                        List<String> codeList = Arrays.asList(chAligCodes);
                        if (!codeList.contains(toAligCode)) {
                            relOrg.setAligCode(chAligCode + "," + toAligCode);
                        }
                    } else {
                        relOrg.setAligCode(toAligCode);
                    }
                    //存口径
                    relOrg.setRegulatorOth1(aligMap.getCaliber());
                    //关系设置定为码
                    rel.setFromAligCode(alig);
                    rel.setToAligCode(toAligCode);
                    rel.setRegulator(aligMap.getCaliber());
                    rel.setDelflag("1");
                    rel.setFromType("ORG");
                    rel.setToType("ORG");
                    if (relOrgVo.getShareholdingProportion() != null) {
                        rel.setShareholdingRatio(Float.parseFloat(relOrgVo.getShareholdingProportion()));
                    }
                    //关联人设置口径
                    relOrg.setRegulatorOth1(aligMap.getCaliber());
                } else {
                    //关系设置定为码
                    rel.setFromAligCode(alig);
                    rel.setDelflag("1");
                    rel.setFromType("ORG");
                    rel.setToType("ORG");
                    if (relOrgVo.getShareholdingProportion() != null) {
                        rel.setShareholdingRatio(Float.parseFloat(relOrgVo.getShareholdingProportion()));
                    }
                }
                relOrg.setApprovalStatus("3");
                relOrgService.update(relOrg);
                //关系状态为待新增
                rel.setRelStatus("01");
                if (isExist) {
                    relService.update(rel);
                } else {
                    relService.insert(rel);
                }
                tips.put(WebCode.SUCCESS, true);
                tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
                break;
            }
        } else {
            //非关联方，返回不可添加提示
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "超过关联方关系判定范围");
        }
        return tips;
    }

//    @RequestMapping("/saveOrg")/*/{fromPid}*/
//    @ResponseBody
//    @Transactional(readOnly = true)
//    public Map<String, Object> saveOrg(RelOrgVo relOrgVo) {
//        RelOrg relOrg = new RelOrg();
//        BeanUtils.copyProperties(relOrgVo, relOrg);
//        relOrg.setStatus("3");//审批状态
//        relOrg.setIsBlack("0");//是否黑名单
//        relOrg.setDelflag("1");// 逻辑删除标记
//        relOrg.setDataSource("1");//数据来源为手工录入“1”,系统披露为“2”
//
//        RelService relService = SpringContextHolder.getBean(RelService.class);
//        Rel rel = new Rel();
//        rel.setRelCategory(relOrgVo.getRelCategory());
//        rel.setRelCategorySub(relOrgVo.getRelCategorySub());
//
//        RelDictService relDictService = SpringContextHolder.getBean(RelDictService.class);
//        RelDict relDict = relDictService.selectDict(relOrgVo.getRelCategory(), relOrgVo.getRelCategorySub());
//        rel.setFromName(relDict.getFromName());
//        rel.setToName(relDict.getToName());
//        rel.setPid(relOrgVo.getRelId());
//        rel.setFromType("ORG");
//        rel.setToType("ORG");
//        rel.setDelflag("1");
//        //持股权
//        if (relOrgVo.getShareholdingRatio() != null && relOrgVo.getRelCategory().equals("C")) {
//            rel.setShareholdingRatio(relOrgVo.getShareholdingRatio());
//        } else {
//            rel.setShareholdingRatio(0f);
//        }
//        Map<String, Object> tips = new HashMap<String, Object>();
//        Boolean isExist = false;
//        RelOrg org = new RelOrg();
//        if (relOrgVo.getRelId() != null && !relOrgVo.getRelId().equals("")) {
//            rel.setPid(relOrgVo.getRelId());
//            isExist = relOrgService.exists(relOrg.getPid());
//        }
//        try {
//            if (isExist) {
//                relOrgService.update(relOrg);
//                rel.setFromPid(relOrgVo.getOrgId().toString());
//                rel.setToPid(String.valueOf(relOrgVo.getPid()));
//                relService.update(rel);
//            } else {
//                List<RelOrg> orgs = new ArrayList<>();
//                List<Rel> rels = null;
//                if (relOrgVo.getUniscCode() != null && !relOrgVo.getUniscCode().equals("")) {
//                    org.setUniscCode(relOrgVo.getUniscCode());
//                    orgs = relOrgService.select(org);
//                } else {
//                    tips.put(WebCode.SUCCESS, false);
//                    tips.put(WebCode.MSG, "证件号码为空");
//                    return tips;
//                }
//                Rel rel1 = new Rel();
//                if (orgs != null && orgs.size() > 0) {
//                    Rel relCheck = new Rel();
//                    relCheck.setToPid(relOrgVo.getFromPid());
//                    relCheck.setFromPid(String.valueOf(relOrgService.select(org).get(0).getPid()));
//                    relCheck.setToType("ORG");
//                    relCheck.setFromType("ORG");
//                    List<Rel> rels1 = relService.select(relCheck);
//                    if (rels1 != null && rels1.size() > 0) {
//                        //开始反转
//                        rel1.setFromPid(relOrgVo.getFromPid());
//                        rel1.setToPid(String.valueOf(relOrgService.select(org).get(0).getPid()));
//                    }
//                } else {
//                    relOrgService.insertSelective(relOrg);
//                    relOrg = relOrgService.get(relOrg);
//                    if (StringUtils.isEmpty(rel1.getRelStatus())) {
//                        rel1.setRelStatus("01");
//                    }
//                    rel1.setFromPid(relOrgService.select(org).get(0).getPid().toString());
//                    rel1.setFromType("ORG");
//                    rel1.setToPid(relOrgVo.getFromPid());
//                    rel1.setToType("ORG");
//                    rel1.setRelCategory(relOrgVo.getRelCategory());
//                    rel1.setRelCategorySub(relOrgVo.getRelCategorySub());
//                    rels = relService.select(rel1);
//                }
//
//                if (rels != null && rels.size() > 0) {
//                    tips.put(WebCode.SUCCESS, false);
//                    tips.put(WebCode.MSG, "关系重复");
//                    return tips;
//                } else {
//                    rel.setFromPid(relOrgVo.getOrgId().toString());
//                    rel.setToPid(relOrg.getPid().toString());
//                    rel.setRelStatus("01");
//                    relService.insert(rel);
//                }
//            }
//            tips.put(WebCode.SUCCESS, true);
//            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
//        } catch (ServiceException e) {
//            logger.info(e.getMessage());
//            tips.put(WebCode.SUCCESS, false);
//            tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
//        }
//        return tips;
//    }


    /*
     * 任务下发
     * */
    @RequestMapping(value = "/relOrgSend")
    public String relOrgSend(Model model) {

        return "bus/relOrgSend";

    }

    /*
     * 确认下发
     * */
    @RequestMapping(value = "/taskSend")
    @ResponseBody
    public Map taskSend(String unitCode, String memo, String sendOrgId) {
        Map<String, Object> tips = new HashMap<String, Object>();
        //发放疑似机构给指定机构补录
        List<Filter> filers = new Filter.Builder().eq("pid", sendOrgId).build();
        RelOrg relOrg = relOrgService.get(filers);
        //更换管库机构为下发机构
        relOrg.setMgerOrg(unitCode);
        //更新疑似机构状态为待补录
        relOrg.setApprovalStatus("2");
        relOrg.setStatusComment(memo);
        try {
            //查出指定机构角色为业务岗的人并通知补录
            List<Duty> dutys = dutyService.select(new Filter.Builder()
                    .like("positionCode", "120@" + unitCode)
                    .like("dutySource", DutySource.ROLE.toString())
                    .build());
            if (CollectionUtils.isNotEmpty(dutys)) {
                for (Duty duty : dutys) {
                    //通知补录人
                    /*创建提醒*/
                    notifyService.createRemind(duty.getStaffCode(), "机构[" + relOrg.getOrgName() + "]的补录任务已下发到您[" + duty.getStaffCode() + "]，请到机构列表完成补录。", "机构[" + relOrg.getOrgName() + "]的补录任务已下发到您[" + duty.getStaffCode() + "]，请到机构列表完成补录。");
                }
                relOrgService.update(relOrg);
                tips.put(WebCode.SUCCESS, true);
                tips.put(WebCode.MSG, WebCode.SUCCESS_TASKSEND);
            } else {
                tips.put(WebCode.SUCCESS, true);
                tips.put(WebCode.MSG, "该机构未设业务岗");
            }
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        }
        return tips;
    }

    /**
     * 反显，查询机构信息
     */
    @RequestMapping(value = "/selectRelOrg")
    @ResponseBody
    public List<RelOrg> selectRelOrg(String orgName, String identifyType, String uniscCode) {
        List<RelOrg> relOrgs = new ArrayList<>();
        List<Filter> filters = new Filter.Builder().eq("delflag", "1").nnl("aligCode").build();
        if (!StringUtils.isEmpty(orgName)) {
            filters.add(new Filter("orgName", orgName));
        }
        if (!StringUtils.isEmpty(identifyType)) {
            filters.add(new Filter("identifyType", identifyType));
        }
        if (!StringUtils.isEmpty(uniscCode)) {
            filters.add(new Filter("uniscCode", uniscCode));
        }
        relOrgs = relOrgService.select(filters);

        return relOrgs;
    }

    /**
     * 机构关系信息列表
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/orgOfProList/{pid}")
    @ResponseBody
    public Map<String, Object> orgOfProList(@PathVariable String pid, Model model, Page<RelOrgVo> page) {
        List<RelOrgVo> relOrgVoList = relPsnAndOrgOfProService.selectRelOrgOfProInformation(pid);
        Map<String, Object> result = new HashMap<String, Object>();
        page.setTotal(relOrgVoList.size());
        int pages = (relOrgVoList.size() + page.getPageSize() - 1) / page.getPageSize();
        if (page.getPageNumber() > pages) {
            page.setPageNumber(1);
        }
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        List<RelOrgVo> relOrgVos = new ArrayList<RelOrgVo>();
        for (int i = index; i < index + page.getPageSize(); i++) {
            if (i < relOrgVoList.size()) {
                relOrgVos.add(relOrgVoList.get(i));
            }
        }
        page.setResult(relOrgVos);

        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());

        return result;
    }

    /**
     * 金融产品关联归属机构
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/orgUpOfProList/{pid}")
    @ResponseBody
    public Map<String, Object> orgUpOfProList(@PathVariable String pid, Model model, Page<RelOrgVo> page) {
        List<RelOrgVo> relOrgVoList = relPsnAndOrgOfProService.selectRelOrgUpOfProInformation(pid);
        Map<String, Object> result = new HashMap<String, Object>();
        page.setTotal(relOrgVoList.size());
        int pages = (relOrgVoList.size() + page.getPageSize() - 1) / page.getPageSize();
        if (page.getPageNumber() > pages) {
            page.setPageNumber(1);
        }
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        List<RelOrgVo> relOrgVos = new ArrayList<RelOrgVo>();
        for (int i = index; i < index + page.getPageSize(); i++) {
            if (i < relOrgVoList.size()) {
                relOrgVos.add(relOrgVoList.get(i));
            }
        }
        page.setResult(relOrgVos);

        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());

        return result;
    }

    /**
     * 金融产品关联下级机构
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/orgDownOfProList/{pid}")
    @ResponseBody
    public Map<String, Object> orgDownOfProList(@PathVariable String pid, Model model, Page<RelOrgVo> page) {
        List<RelOrgVo> relOrgVoList = relPsnAndOrgOfProService.selectRelOrgDownOfProInformation(pid);
        Map<String, Object> result = new HashMap<String, Object>();
        page.setTotal(relOrgVoList.size());
        int pages = (relOrgVoList.size() + page.getPageSize() - 1) / page.getPageSize();
        if (page.getPageNumber() > pages) {
            page.setPageNumber(1);
        }
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        List<RelOrgVo> relOrgVos = new ArrayList<RelOrgVo>();
        for (int i = index; i < index + page.getPageSize(); i++) {
            if (i < relOrgVoList.size()) {
                relOrgVos.add(relOrgVoList.get(i));
            }
        }
        page.setResult(relOrgVos);

        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());

        return result;
    }


    /**
     * 编辑里的保存金融产品的机构关系信息
     *
     * @param relOrgVo
     * @return
     */
    @RequestMapping(value = "/saveOrgOfPros/{parentPid}/{relId}/{fromPid}")
    @ResponseBody
    public Map<String, Object> saveOrgOfPros(@PathVariable String parentPid, @PathVariable Integer relId, @PathVariable String fromPid, RelOrgVo relOrgVo) {
        Map<String, Object> tips = new HashMap<String, Object>();
        //归属关联方
        RelOrg fromOrg = relOrgService.get(Integer.valueOf(parentPid));
        //新增关系to金融产品
        RelPro relPro = relProService.get(Integer.valueOf(fromPid));
//        BeanUtils.copyProperties(relPsnVo, relPsn);
        String fromAlig = fromOrg.getAligCode();
        String chAligCode = relPro.getAligCode();
        //新增关联方根据上级定为码以及关系判断是否关联方,更新to定为码
        if (StringUtils.isNotEmpty(fromAlig)) {
            //更新关系表
            RelPsnAndOrgOfPro relPsnAndOrgOfPro = new RelPsnAndOrgOfPro();
            relPsnAndOrgOfPro.setPid(relOrgVo.getRelId());
            relPsnAndOrgOfPro.setDelflag("1");
            //归属关联方pid
            relPsnAndOrgOfPro.setFromPid(parentPid);
            //添加基本信息机构的pid
            relPsnAndOrgOfPro.setToPid(fromPid);
            relPsnAndOrgOfPro.setRelCategory(relOrgVo.getRelCategory());
            relPsnAndOrgOfPro.setRelCategorySub(relOrgVo.getRelCategorySub());
            RelPsnAndOrgOfPro dbRelPsnAndOrgOfPro = relPsnAndOrgOfProService.get(relPsnAndOrgOfPro);
            //如果查到两人间有相同关系，更新原先关系
            boolean isExist = false;
            if (dbRelPsnAndOrgOfPro != null) {
                isExist = true;
                relPsnAndOrgOfPro.setPid(dbRelPsnAndOrgOfPro.getPid());
            }
            String[] aligs = fromAlig.split(",");
            for (String alig : aligs) {
                List<Filter> aligFilters = new Filter.Builder()
                        .eq("paAligCode", alig)
                        .eq("relCategoryVal", relOrgVo.getRelCategory())
                        .eq("assoTypeVal", relOrgVo.getRelCategorySub())
                        .build();
                AligMap aligMap = aligMapService.get(aligFilters);
                //to定为码不为空则为关联方，需要更新定为码
                if (aligMap != null) {
                    String toAligCode = aligMap.getChAligCode();
                    //判断to定为码是否已存在,确保不重复加
                    if (StringUtils.isNotEmpty(chAligCode)) {
                        String[] chAligCodes = chAligCode.split(",");
                        List<String> codeList = Arrays.asList(chAligCodes);
                        if (!codeList.contains(toAligCode)) {
                            relPro.setAligCode(chAligCode + "," + toAligCode);
                        }
                    } else {
                        relPro.setAligCode(toAligCode);
                    }
                    //关系设置定为码
                    relPsnAndOrgOfPro.setFromAligCode(alig);
                    relPsnAndOrgOfPro.setToAligCode(toAligCode);
                    relPsnAndOrgOfPro.setRegulator(aligMap.getCaliber());
                    relPsnAndOrgOfPro.setDelflag("1");
                    relPsnAndOrgOfPro.setFromType("ORG");
                    relPsnAndOrgOfPro.setToType("PRO");
                    relPsnAndOrgOfPro.setShareholdingRatio(relOrgVo.getShareholdingRatio());
                    //关联人设置口径
                    relPsnAndOrgOfPro.setRegulator(aligMap.getCaliber());
                    relPsnAndOrgOfPro.setRelStatus("3");
                    relPsnAndOrgOfPro.setIsValid(relOrgVo.getIsValid());
                    relPsnAndOrgOfPro.setEffectiveDate(relOrgVo.getEffectiveDate());
                    relPsnAndOrgOfPro.setIneffectiveDate(relOrgVo.getIneffectiveDate());
                    relPsnAndOrgOfPro.setRelStatement(relOrgVo.getRelStatement());
                } else {
                    //为null  代表to 不是关联方，仅仅维持关系即可
                    //关系设置定为码
                    relPsnAndOrgOfPro.setFromAligCode(alig);
                    relPsnAndOrgOfPro.setDelflag("1");
                    relPsnAndOrgOfPro.setFromType("ORG");
                    relPsnAndOrgOfPro.setToType("PRO");
                    relPsnAndOrgOfPro.setShareholdingRatio(relOrgVo.getShareholdingRatio());
                    relPsnAndOrgOfPro.setRelStatus("3");
                    relPsnAndOrgOfPro.setIsValid(relOrgVo.getIsValid());
                    relPsnAndOrgOfPro.setEffectiveDate(relOrgVo.getEffectiveDate());
                    relPsnAndOrgOfPro.setIneffectiveDate(relOrgVo.getIneffectiveDate());
                    relPsnAndOrgOfPro.setRelStatement(relOrgVo.getRelStatement());
                    //设置成待审批
                    relPro.setApprovalStatus("3");
                }
                relProService.update(relPro);
                //关系状态为待新增
                relPsnAndOrgOfPro.setRelStatus("01");
                relPsnAndOrgOfPro.setPid(relId);

                relPsnAndOrgOfPro.setRelStatement(relOrgVo.getRelStatement());

                relPsnAndOrgOfProService.update(relPsnAndOrgOfPro);

                tips.put(WebCode.SUCCESS, true);
                tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
                break;
            }
        } else {
            //非关联方，返回不可添加提示
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "超过关联方关系判定范围");
        }
        return tips;
    }

    /**
     * 保存金融产品的机构关系信息
     *
     * @param relOrgVo
     * @return
     */
    @RequestMapping(value = "/saveOrgOfPro/{parentPid}/{fromPid}")
    @ResponseBody
    public Map<String, Object> saveOrgOfPro(@PathVariable String parentPid, @PathVariable String fromPid, RelOrgVo relOrgVo) {
        Map<String, Object> tips = new HashMap<String, Object>();
        //归属关联方
        RelOrg fromOrg = relOrgService.get(Integer.valueOf(parentPid));
        //新增关系to金融产品
        RelPro relPro = relProService.get(Integer.valueOf(fromPid));
        RelPsnAndOrgOfPro relPsnAndOrgOfPro = new RelPsnAndOrgOfPro();
        relPsnAndOrgOfPro.setPid(relOrgVo.getRelId());
        relPsnAndOrgOfPro.setDelflag("1");
        //归属关联方pid
        relPsnAndOrgOfPro.setFromPid(parentPid);
        //添加基本信息机构的pid
        relPsnAndOrgOfPro.setToPid(fromPid);
        RelPsnAndOrgOfPro dbRelPsnAndOrgOfPro = relPsnAndOrgOfProService.get(relPsnAndOrgOfPro);
        relPsnAndOrgOfPro.setRelCategory(relOrgVo.getRelCategory());
        relPsnAndOrgOfPro.setRelCategorySub(relOrgVo.getRelCategorySub());
        relPsnAndOrgOfPro.setEquityShare(relOrgVo.getEquityShare());
        //如果查到两人间有相同关系，更新原先关系
        boolean isExist = false;
        if (dbRelPsnAndOrgOfPro != null) {
            isExist = true;
            relPsnAndOrgOfPro.setPid(dbRelPsnAndOrgOfPro.getPid());
        }
        relPsnAndOrgOfPro.setDelflag("1");
        relPsnAndOrgOfPro.setFromType("ORG");
        relPsnAndOrgOfPro.setToType("PRO");
        relPsnAndOrgOfPro.setShareholdingRatio(relOrgVo.getShareholdingRatio());
        relPsnAndOrgOfPro.setRelStatus("3");
        relPsnAndOrgOfPro.setIsValid(relOrgVo.getIsValid());
        relPsnAndOrgOfPro.setActiveTimeString(relOrgVo.getActiveTimeString());
        relPsnAndOrgOfPro.setInvalidTimeString(relOrgVo.getInvalidTimeString());
        relPsnAndOrgOfPro.setRelStatement(relOrgVo.getRelStatement());

        relPro.setApprovalStatus("3");
//        new ConfirmUtil().relPro(relPro);
        relProService.update(relPro);
        //关系状态为待新增
        relPsnAndOrgOfPro.setRelStatus("01");
        if (isExist) {
            relPsnAndOrgOfProService.update(relPsnAndOrgOfPro);
        } else {
            relPsnAndOrgOfProService.insert(relPsnAndOrgOfPro);
        }
        tips.put(WebCode.SUCCESS, true);
        tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        return tips;
    }

    /**
     * 删除
     *
     * @param pid
     * @return
     */
    @RequestMapping(value = "/removeRelInformation/{pid}")
    @ResponseBody
    public Map<String, Object> removeRelInformation(@PathVariable Integer pid) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {

            RelPsnAndOrgOfPro relPsnAndOrgOfPro = relPsnAndOrgOfProService.get(pid);
            RelOrg relOrg = relOrgService.get(Integer.parseInt(relPsnAndOrgOfPro.getFromPid()));
            relOrg.setStatus("3");
            //  00 代表已归档
            if ("00".equals(relPsnAndOrgOfPro.getRelStatus())) {
                //改为 归档-删除
                relPsnAndOrgOfPro.setRelStatus("03");
                relPsnAndOrgOfProService.updateSelective(relPsnAndOrgOfPro);
                //将基本信息改为  待审批
                relOrgService.updateSelective(relOrg);
                tips.put(WebCode.SUCCESS, true);
                tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
            } else if (StringUtils.isEmpty(relPsnAndOrgOfPro.getRelStatus()) || "01".equals(relPsnAndOrgOfPro.getRelStatus())) {
                //改为 删除
                relPsnAndOrgOfProService.deleteByPrimaryKey(relPsnAndOrgOfPro.getPid());
//                relService.removeRel(rel.getFromPid(), rel.getToPid(), rel.getFromType(), rel.getToType());
                tips.put(WebCode.SUCCESS, true);
                tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
            }
//            relService.removeRel(rel.getFromPid(), rel.getToPid(), rel.getFromType(), rel.getToType());
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_DELETE);
        }

        return tips;
    }
}