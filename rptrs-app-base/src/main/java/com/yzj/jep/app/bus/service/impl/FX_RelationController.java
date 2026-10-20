package com.yzj.jep.app.bus.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.yzj.jep.app.base.constant.Log;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.RelHistoryService;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.bus.service.RelService;
import com.yzj.jep.app.ex.service.impl.ExHRApproveProcessAssigneeFinderHandler;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.AuthorityUtil;
import com.yzj.jep.app.wf.service.WorkflowOperation;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.util.RequestUtils;
import com.yzj.jep.workflow.api.WorkflowException;
import com.yzj.jep.workflow.impl.assign.ProcessAssigneeFinderHandler;
import com.yzj.jep.workflow.impl.model.StaffImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author ZL
 * 关联关系管理
 */
@Controller
@RequestMapping("/bus/relation")
public class FX_RelationController extends CrudController<Rel> {

    private static Logger logger = LoggerFactory.getLogger(FX_RelationController.class);

    @Autowired
    private RelService relService;
    @Autowired
    private DictItemService dictItemService;

    @Autowired
    private RelHistoryService relHistoryService;

    @Autowired
    WorkflowOperation workflowOperation;
    @Autowired
    private RelPsnService relPsnService;
    @Autowired
    private RelOrgService relOrgService;

    private static final Map<String, String> subjectName = new HashMap<>();

    static {

        /*
         * 根据不同的主体来添加不同的后缀
         * */
        subjectName.put("JT", "JT_");
        subjectName.put("SX", "SX_");
        subjectName.put("CX", "CX_");
        subjectName.put("XB", "XB_");
        subjectName.put("ZG", "ZG_");
    }

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    @Log(module = "关联关系维护", description = "查询关联关系列表数据")
    public String list(Model model) {
        //用户角色查询
        String staffCode = UserProfileUtils.getUserProfile().getStaffCode();
        String url = "bus/relation/list";
        AuthorityUtil authorityUtil = new AuthorityUtil();
        String code = authorityUtil.buttonPerm(staffCode, url);
        model.addAttribute("code", code);
        return "bus/relationList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<Rel> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        try {
            orders.add(Order.desc("modifiedTime"));
            relService.query(page, filters, orders);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("列表查询出错:" + e);
        }
    }

    @RequestMapping(value = "/add")
    @Log(module = "关联关系维护", description = "查询关联关系列表数据")
    public String add(Model model) {
        //用戶角色查詢
        String unitCode = UserProfileUtils.getUserProfile().getUnitCode();
        model.addAttribute("unitCode", unitCode);
        return "bus/relationAdd";
    }

    /**
     * 跳转“自然热”、“法人或组织机构”信息选择页面---查询本层关联方名称
     */
    @RequestMapping(value = "/forNameSelect")
    public String forNameSelect(Model model) {
        return "bus/forNameSelectAll";
    }

    /**
     * 查询所有“自然热”、“法人或组织机构”信息---查询本层关联方名称
     */
    @RequestMapping(value = "/forNameSelectAll", produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> forNameSelectAll(Page<RelPsnOrg> page, Model model, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        PageHelper.start(page);
        List<RelPsnOrg> relPsnOrg = new ArrayList<>();
        if (filters != null && filters.size() > 0) {
            String name = null;
            String code = null;
            for (Filter f : filters) {
                if ("fromMemo".equals(f.getProperty())) {
                    name = f.getValue().toString();
                }
                if ("identifyNumber".equals(f.getProperty())) {
                    code = f.getValue().toString();
                }
            }
            relPsnOrg = relService.getRelPsnOrgByNameOrNum(name, code);
        } else {
            relPsnOrg = relService.getRelPsnOrg();
        }
        PageInfo pageInfo = new PageInfo(relPsnOrg);
        result.put(RESULT_TOTAL, pageInfo.getTotal());
        result.put(RESULT_ROWS, pageInfo.getList());
        return result;
    }

    /**
     * 跳转“自然热”、“法人或组织机构”信息选择页面---查询前一层关联方名称
     */
    @RequestMapping(value = "/forNameSelectAll01")
    public String forNameSelectAll(Model model) {
        return "bus/relForNameSelectAll";
    }

    /**
     * 查询所有“自然热”、“法人或组织机构”信息---查询前一层关联方名称
     */
    @RequestMapping(value = "/relForNameSelectAll", produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> relForNameSelectAll(Page<RelPsnOrg> page, Model model, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        PageHelper.start(page);
        List<RelPsnOrg> relPsnOrg = new ArrayList<>();
        if (filters != null && filters.size() > 0) {
            String name = null;
            String code = null;
            for (Filter f : filters) {
                if ("fromMemo".equals(f.getProperty())) {
                    name = f.getValue().toString();
                }
                if ("identifyNumber".equals(f.getProperty())) {
                    code = f.getValue().toString();
                }
            }
            relPsnOrg = relService.getRelPsnOrgByNameOrNumFront(name, code);
        } else {
            relPsnOrg = relService.getRelPsnOrgFront();
        }
        PageInfo pageInfo = new PageInfo(relPsnOrg);
        result.put(RESULT_TOTAL, pageInfo.getTotal());
        result.put(RESULT_ROWS, pageInfo.getList());
        return result;
    }

    /**
     * 查询关联关系类型
     */
    @RequestMapping(value = "/queryClassify")
    @ResponseBody
    public List<DictItem> queryClassify(String fromType, String toType) {
        List<Filter> filter = new ArrayList<>();
        //前一层是toType
        if (StringUtils.isNotEmpty(fromType) && StringUtils.isNotEmpty(toType)) {
            if ("ORG".equals(toType)) {
                if ("PSN".equals(fromType)) {
                    filter.add(new Filter("dictCode", Operator.EQ, "BUS-RelOrgAndPSN"));
                } else if ("ORG".equals(fromType)) {
                    filter.add(new Filter("dictCode", Operator.EQ, "BUS-RelOrgOrOrg"));
                } else {
                    filter.add(new Filter("dictCode", Operator.EQ, "BUS-RelOrgOrPro"));
                }
            } else if ("PSN".equals(toType)) {
                if ("PSN".equals(fromType)) {
                    filter.add(new Filter("dictCode", Operator.EQ, "BUS-RelPsnAndPsn"));
                } else if ("ORG".equals(fromType)) {
                    filter.add(new Filter("dictCode", Operator.EQ, "BUS-RelPsnAndOrg"));
                } else {
                    filter.add(new Filter("dictCode", Operator.EQ, "BUS-RelPsnAndPro"));
                }
            } else {
                if ("PSN".equals(fromType)) {
                    filter.add(new Filter("dictCode", Operator.EQ, "BUS-RelProAndPsn"));
                } else if ("ORG".equals(fromType)) {
                    filter.add(new Filter("dictCode", Operator.EQ, "BUS-RelOrgOrPro"));
                }
            }
        }
        List<DictItem> list = dictItemService.select(filter);
        return list;
    }

    /**
     * 保存关联关系
     *
     * @param rel
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(Rel rel) {
        Map<String, Object> tips = new HashMap<String, Object>();
        UserProfile profile = UserProfileUtils.getUserProfile();
        List<Rel> psnOrg = relService.selectSavePsnOrg(rel.getFromIdentifyNumber(), rel.getToIdentifyNumber());
        if (!CollUtil.isEmpty(psnOrg)) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "该关联关系已存在！");
        } else {
            try {
                //有效关系为否时，失效日期存在，进解除关联关系库
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                String nowDate = dateFormat.format(date);
                Date InvalidTime = rel.getInvalidTime();
                String opDate = "";
                if (InvalidTime != null) {
                    opDate = dateFormat.format(InvalidTime);
                }
                //失效日期为当天才进入解除关联关系库
                if ("0".equals(rel.getIsValid()) && rel.getInvalidTime() != null && nowDate.equals(opDate)) {
                    rel.setApprovalStatus("6");
                    rel.setOperator(profile.getStaffName());
                    rel.setTransactionDep(profile.getDeptName());
                    relService.updatePsnOrgValid(rel);
                } else {
                    rel.setApprovalStatus("3");
                    rel.setOperator(profile.getStaffName());
                    rel.setTransactionDep(profile.getDeptName());
                }
                if (rel.getFromType().equals("PSN")) {
                    RelPsn psn = new RelPsn();
                    psn.setPid(Integer.valueOf(rel.getFromPid()));// 1是 0否
                    psn.setRelateNumber(String.valueOf(psn.getPid()+100000));
                    relPsnService.updateSelective(psn);
                    //当是法人时，将法人表中关系有效性改为是。
                } else if (rel.getFromType().equals("ORG")) {
                    RelOrg org = new RelOrg();
                    org.setPid(Integer.valueOf(rel.getFromPid()));
                    org.setRelateNumber(String.valueOf(org.getPid()+200000));
                    relOrgService.updateSelective(org);
                }
                relService.save(rel);
                tips.put(WebCode.SUCCESS, true);
                tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
            } catch (ServiceException e) {
                logger.info(e.getMessage());
                tips.put(WebCode.SUCCESS, false);
                tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
            }
        }
        return tips;
    }

    /**
     * 跳转关联关系修改页面
     */
    @RequestMapping(value = "/relationEdit/{pid}")
    @Log(module = "关联关系维护", description = "修改关联关系")
    public String edit(@PathVariable Integer pid, Model model) {
        Rel rel = relService.get(pid);
        String unitCode = UserProfileUtils.getUserProfile().getUnitCode();
        model.addAttribute("unitCode", unitCode);
        model.addAttribute("rel", rel);
        return "bus/relationEdit";
    }

    /**
     * 跳转查看页面
     */
    @RequestMapping(value = "/view/{pid}")
    @Log(module = "关联关系维护", description = "关联关系查看")
    public String view(@PathVariable Integer pid, Model model) {
        Rel rel = relService.get(pid);
        model.addAttribute("rel", rel);
        return "bus/relationView";
    }

    /**
     * 跳转查看页面
     */
    @RequestMapping(value = "/view1/{pid}")
    @Log(module = "关联关系维护", description = "关联关系查看")
    public String view1(@PathVariable Integer pid, Model model) {
        Rel rel = relService.get(pid);
        model.addAttribute("rel", rel);
        return "bus/relationView1";
    }

    /**
     * 修改关联关系
     */
    @RequestMapping(value = "/updateRelation")
    @ResponseBody
    public Map<String, Object> updateRelation(Rel rel) {
        Map<String, Object> updateTips = new HashMap<String, Object>();
        UserProfile profile = UserProfileUtils.getUserProfile();
        try {
            rel.setApprovalStatus("3");
            relService.updateSelective(rel);
            RelHistory relHistory = new RelHistory();
            BeanUtils.copyProperties(rel, relHistory);
            relHistory.setOperator(profile.getStaffName());
            relHistory.setRecordType(2);
            relHistory.setPid(null);
            relHistory.setTransactionDep(profile.getDeptName());
            relHistoryService.insertSelective(relHistory);
            updateTips.put(WebCode.SUCCESS, true);
            updateTips.put(WebCode.MSG, WebCode.SUCCESS_UPDATE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            updateTips.put(WebCode.SUCCESS, false);
            updateTips.put(WebCode.MSG, WebCode.FAULURE_UPDATE);
        }
        return updateTips;
    }

    /**
     * 删除关联关系
     */
    @RequestMapping(value = "/removeRelPsn/{pid}")
    @ResponseBody
    public Map<String, Object> removeRelPsn(@PathVariable Integer pid, Model model) {
        Map<String, Object> deleteTips = new HashMap<String, Object>();
        try {
            relService.deleteByPrimaryKey(pid);
            deleteTips.put(WebCode.SUCCESS, true);
            deleteTips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            deleteTips.put(WebCode.SUCCESS, false);
            deleteTips.put(WebCode.MSG, WebCode.FAILURE_DELETE);
        }
        return deleteTips;
    }

    /**
     * 提交審批
     */
    @RequestMapping(value = "/relationSubmit/{pid}")
    @ResponseBody
    @Log(module = "关联关系维护", description = "提交审批")
    public Map<String, Object> relationSubmit(@PathVariable Integer pid, Model model) {
        Map<String, Object> submitTips = new HashMap<String, Object>();
        try {
            Rel rel = relService.get(pid);
            rel.setApprovalStatus("4");
            relService.updateSelective(rel);
            submitTips.put("data", rel);
            submitTips.put(WebCode.SUCCESS, true);
            submitTips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            submitTips.put(WebCode.SUCCESS, false);
            submitTips.put(WebCode.MSG, "状态不符，提交失败！");
        }

        return submitTips;
    }

    /**
     * 批量提交审批
     *
     * @param pidArr
     * @return
     */
    @PostMapping("/batchRelationSubmit")
    @ResponseBody
    public Map<String, Object> batchRelationSubmit(@RequestBody List<String> pidArr) {
        Map<String, Object> submitTips = new HashMap<String, Object>();
        Map<String, Object> dataMap = new HashMap<>();
        List<Integer> queryList = new ArrayList<>();
        List<Map<String, Object>> mapList = new ArrayList<>();
        List<Rel> relList = new ArrayList<>();
        try {
            //校验是否均为未提交状态
            for (String pid : pidArr) {
                //修改数据状态
                Rel relToUpdate = new Rel();
                relToUpdate.setPid(Integer.valueOf(pid));
                relToUpdate.setApprovalStatus("4");
                relService.updateSelective(relToUpdate);
                Rel rel = relService.get(Integer.valueOf(pid));
                relList.add(rel);
            }
            /**此处不能传递数组*/
            String join = CollectionUtil.join(pidArr, ",");
            dataMap.put("pids", join);
            dataMap.put("data", relList);
            //保存数据
            submitTips.put("data", dataMap);
            submitTips.put(WebCode.SUCCESS, true);
            submitTips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } catch (Exception e) {
            logger.info(e.getMessage());
            submitTips.put(WebCode.SUCCESS, false);
            submitTips.put(WebCode.MSG, "状态不符，提交失败！");
        }
        return submitTips;
    }

    /**
     * 实体类转map
     *
     * @param obj
     * @return
     */
    public Map<String, Object> convertBeanToMap(Object obj) {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    if (null == value) {
                        map.put(key, "");
                    } else {
                        map.put(key, value);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("convertBean2Map Error {}", e);
        }
        return map;
    }

    @GetMapping("/getRelationsByPids/{pids}")
    @ResponseBody
    public Map<String, Object> getRelationsByPids(Integer pageSize, Integer pageNumber, @PathVariable("pids") String pids) {
        Map<String, Object> result = new HashMap<>();
        String[] split = pids.split(",");
        List<Filter> filters = new ArrayList<>();
        filters.add(new Filter("pid", Operator.IN, split));
        Page<Rel> page = new Page<>(pageNumber, pageSize);
        PageHelper.start(page);
        relService.query(page, filters);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;
    }


    /**
     * 批量审批
     */
    @RequestMapping(value = "/relationSubmit")
    @ResponseBody
    @Log(module = "关联关系维护", description = "批量审批")
    public Map<String, Object> relationSubmit() {
        Map<String, Object> result = new HashMap<String, Object>();
        UserProfile userProfile = UserProfileUtils.getUserProfile();
        if (userProfile == null || StringUtils.isEmpty(userProfile.getUnitCode())) {
            result.put(WebCode.SUCCESS, false);
            result.put(WebCode.MSG, "用户状态获取异常, 请检查登录状态!");
            return result;
        }
        ProcessAssigneeFinderHandler finderHandlerHrManager = new ExHRApproveProcessAssigneeFinderHandler();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("unitCode", userProfile.getUnitCode());
        String jsonStr = jsonObject.toString();
        Set<String> hrManagerSets = finderHandlerHrManager.find(null, null, jsonStr);
        // 为空,代表没有下一岗审批人
        if ((hrManagerSets == null || hrManagerSets.size() <= 0)) {
            result.put(WebCode.SUCCESS, false);
            result.put(WebCode.MSG, "该用户没有下一岗审批人, 请检查人员配置!");
            return result;
        }

        result.put(WebCode.SUCCESS, true);
        result.put(WebCode.MSG, WebCode.SUCCESS);
        return result;
    }

    /**
     * 提交审批
     *
     * @param pid
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/send/{pid}")
    @ResponseBody
    public Map<String, Object> send(@PathVariable Integer pid, Model model) throws Exception {
        Map<String, Object> tips = new HashMap<String, Object>();
        try {
            //获取当前登录用户
            UserProfile profile = UserProfileUtils.getUserProfile();
            String procInstId = null;

            StaffImpl staff = new StaffImpl();
            BeanUtils.copyProperties(profile, staff);

            Rel rel = relService.get(pid);
            if (StringUtils.isNotEmpty(rel.getProcInstId())) {
                procInstId = rel.getProcInstId();
            }
            JSONObject data = JSONObject.parseObject(JSONObject.toJSONString(rel));
            data.put("type", "REL");
            data.put("TransactionTtype","REL");
            data.put("startStaffCode", staff.getStaffCode());
            data.put("WorkflowType", "REL");
            String procDefCode = "FXBDX_GLGXBGBA";
            data.put("procDefCode", procDefCode);

            workflowOperation.sendSubmit(pid, procDefCode, staff, null, data.toJSONString(), "REL", procInstId);
        } catch (WorkflowException e) {
            e.printStackTrace();
            logger.error("流程启动失败，失败数据类型:关联关系---" + "pid" + pid, e);
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_START_PROCESS + e.getMessage());
            return tips;
        }

        tips.put(WebCode.SUCCESS, true);
        tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        return tips;
    }

    /**
     * 失效处理页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/relationFailure/{pid}")
    public String fallure(@PathVariable Integer pid, Model model) {
        Rel rel = relService.get(pid);
        String unitCode = UserProfileUtils.getUserProfile().getUnitCode();
        model.addAttribute("unitCode", unitCode);
        model.addAttribute("rel", rel);
        return "bus/relationFailure";
    }

    /**
     * 处理确认页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/relationConfirm/{pid}")
    public String confirm(@PathVariable Integer pid, Model model) {
        Rel rel = relService.get(pid);
        String unitCode = UserProfileUtils.getUserProfile().getUnitCode();
        model.addAttribute("unitCode", unitCode);
        model.addAttribute("rel", rel);
        return "bus/relationConfirm";
    }

    //处理数据

    /**
     * 失效处理保存
     *
     * @param rel
     * @return
     */
    @RequestMapping(value = "/saveFailure")
    @ResponseBody
    public Map<String, Object> saveFailure(Rel rel) {
        Map<String, Object> tips = new HashMap<String, Object>();
        UserProfile profile = UserProfileUtils.getUserProfile();
        //int psnOrg = relService.selectSavePsnOrg(rel.getFromIdentifyNumber(), rel.getToIdentifyNumber(), rel.getRelPsnOrg());
        try {
            //rel.setApprovalStatus("3");
            rel.setOperator(profile.getStaffName());
            rel.setTransactionDep(profile.getDeptName());
            rel.setFailureStatus("1");
            relService.updateSelective(rel);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_OPERATE);

        } catch (Exception e) {
            e.printStackTrace();
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
        }

        return tips;
    }


    /**
     * 失效确认处理
     *
     * @param rel
     * @return
     */
    @RequestMapping(value = "/saveConfirm")
    @ResponseBody
    public Map<String, Object> saveConfirm(Rel rel) {
        Map<String, Object> tips = new HashMap<String, Object>();
        UserProfile profile = UserProfileUtils.getUserProfile();
        //int psnOrg = relService.selectSavePsnOrg(rel.getFromIdentifyNumber(), rel.getToIdentifyNumber(), rel.getRelPsnOrg());
        try {
            rel.setOperator(profile.getStaffName());
            rel.setTransactionDep(profile.getDeptName());
            rel.setFailureStatus("2");
            relService.updateSelective(rel);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_OPERATE);
        } catch (Exception e) {
            e.printStackTrace();
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
        }
        return tips;
    }


}
