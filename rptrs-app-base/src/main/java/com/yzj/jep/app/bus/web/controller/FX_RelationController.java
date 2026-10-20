package com.yzj.jep.app.bus.web.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.yzj.jep.app.base.constant.Log;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.app.ex.service.impl.ExHRApproveProcessAssigneeFinderHandler;
import com.yzj.jep.app.ins.enums.RelatedPartyCategoryEnum;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.AuthorityUtil;
import com.yzj.jep.app.util.UpdateRelatedData;
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
import com.yzj.jep.sys.service.DutyService;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.yzj.jep.app.util.ConfidentialInfoUtil.idEncrypt;
import static com.yzj.jep.app.util.ConfidentialInfoUtil.otherIdEncrypt;

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
    private DutyService dutyService;

    @Autowired
    private RelPsnService relPsnService;

    @Autowired
    private RelOrgService relOrgService;

    @Autowired
    private RelProService relProService;

    @Resource
    private DelRelService delRelService;

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

    /**
     * 为列表页面做数据准备
     */
    @Override
    public Map<String, Object> query(Page<Rel> page, Model model, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        Rel rel = new Rel();
        rel.setDelflag("1");
        for (Filter f : filters) {
            if ("fromMemo".equals(f.getProperty())) {
                rel.setFromMemo(f.getValue().toString());
            } else if ("fromIdentifyNumber".equals(f.getProperty())) {
                rel.setFromIdentifyNumber(f.getValue().toString());
            } else if ("relPsnOrg".equals(f.getProperty())) {
                rel.setRelPsnOrg(f.getValue().toString());
            } else if ("toMemo".equals(f.getProperty())) {
                rel.setToMemo(f.getValue().toString());
            } else if ("toIdentifyNumber".equals(f.getProperty())){
                rel.setToIdentifyNumber(f.getValue().toString());
            } else if ("approvalStatus".equals(f.getProperty())){
                rel.setApprovalStatus(f.getValue().toString());
            } else if ("isValid".equals(f.getProperty())){
                rel.setIsValid(f.getValue().toString());
            } else if ("itemDescription".equals(f.getProperty())){
                rel.setItemDescription(f.getValue().toString());
            }
        }
        PageHelper.start(page);
        List<Rel> relList = relService.queryByParam(rel);
        relList.stream().forEach( e -> {
            //本层关联方为自然人，自然人证件号码脱敏
            if("PSN".equals(e.getFromType())){
                //查询关联方证件类型
                if(StringUtils.isNotEmpty(e.getFromIdentifyNumber())){
                    RelPsn relPsn = relPsnService.selectByIdentifyNumber(e.getFromIdentifyNumber());
                    if(ObjectUtil.isNotEmpty(relPsn)){
                        if("1".equals(relPsn.getIdentifyType())){
                            e.setFromIdentifyNumber(idEncrypt(e.getFromIdentifyNumber()));
                        }else{
                            e.setFromIdentifyNumber(otherIdEncrypt(e.getFromIdentifyNumber()));
                        }
                    }
                }
            }
            //前一层关联方为自然人，自然人证件号码脱敏
            if("PSN".equals(e.getToType())){
                //查询关联方证件类型
                if(StringUtils.isNotEmpty(e.getToIdentifyNumber())){
                    RelPsn relPsn = relPsnService.selectByIdentifyNumber(e.getToIdentifyNumber());
                    if(ObjectUtil.isNotEmpty(relPsn)){
                        if("1".equals(relPsn.getIdentifyType())){
                            e.setToIdentifyNumber(idEncrypt(e.getToIdentifyNumber()));
                        }else{
                            e.setToIdentifyNumber(otherIdEncrypt(e.getToIdentifyNumber()));
                        }
                    }
                }
            }
        });
        PageInfo pageInfo = new PageInfo(relList);
        result.put(RESULT_TOTAL, pageInfo.getTotal());
        result.put(RESULT_ROWS, pageInfo.getList());
        return result;
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
        filter.add(new Filter("dictCode", Operator.EQ, "BUS-RelZZD"));
        List<DictItem> list = dictItemService.select(filter);
        List<DictItem> resultList = new ArrayList<>();



        if (!"PSN".equals(fromType)  && !"PSN".equals(toType)) {
            resultList = list.stream().filter(dictItem -> !"109".equals(dictItem.getItemValue()) && !"111".equals(dictItem.getItemValue()) && !"112".equals(dictItem.getItemValue()) && !"113".equals(dictItem.getItemValue())  && !"119".equals(dictItem.getItemValue())).collect(Collectors.toList());
        }
        if ("PSN".equals(fromType)  && !"PSN".equals(toType)) {
            resultList = list.stream().filter(dictItem -> !"100".equals(dictItem.getItemValue()) && !"102".equals(dictItem.getItemValue()) && !"103".equals(dictItem.getItemValue()) && !"113".equals(dictItem.getItemValue())).collect(Collectors.toList());

        }
        if (!"PSN".equals(fromType)  && "PSN".equals(toType)) {
            resultList = list.stream().filter(dictItem -> !"106".equals(dictItem.getItemValue()) && !"107".equals(dictItem.getItemValue()) && !"104".equals(dictItem.getItemValue()) && !"113".equals(dictItem.getItemValue())).collect(Collectors.toList());

        }
        if ("PSN".equals(fromType)  && "PSN".equals(toType)) {
            resultList = list.stream().filter(dictItem -> "108".equals(dictItem.getItemValue()) || "113".equals(dictItem.getItemValue()) || "118".equals(dictItem.getItemValue())).collect(Collectors.toList());

        }

        return CollUtil.isEmpty(resultList) ? list : resultList;
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
        Map<String, Object> tips = new HashMap<>();
        UserProfile profile = UserProfileUtils.getUserProfile();
        List<Rel> psnOrg = relService.selectSavePsnOrg(rel.getFromIdentifyNumber(), rel.getToIdentifyNumber());
        if (!CollUtil.isEmpty(psnOrg)) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "该关联关系已存在！");
        } else {
            try {
                /*
                    新增的数据不在需要进行审批，所以保存的数据的状态直接为待归档
                 */

                assert profile != null;
                rel.setOperator(profile.getStaffName());
                rel.setTransactionDep(profile.getDeptName());
                rel.setApprovalStatus("7");


//                List<Role> rolelist = dutyService.getStaffRoleList(profile.getStaffCode(), null);
                //如果是总部合规部关联交易管理岗则直接归档，并保存至图数据库
               /* if(CollectionUtil.isNotEmpty(rolelist)){
                    for(Role role : rolelist){
                        if("1000012".equals(role.getRoleCode())){

                        }
                    }
                };*/

                String recordNumber = relService.getRecordNumber();
                rel.setRecordNumber(recordNumber);
                relService.save(rel);
                Rel relOld = new Rel();
                relOld.setDelflag("1");
                relOld.setFromIdentifyNumber(rel.getFromIdentifyNumber());
                relOld.setToIdentifyNumber(rel.getToIdentifyNumber());
                Rel relNew = relService.get(relOld);
                relHistoryService.saveHistory(relNew,"1");
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
     * 确认归档操作(点击确认归档后，需要更新关联方的状态，并加入图数据库)
     * @param pids      ID的集合
     * @return          返回结果
     */

    @RequestMapping(value = "/confirmArchiveHandler/{pids}")
    @ResponseBody
    @Transactional
    public Map<String, Object> confirmArchiveHandler(@PathVariable String pids) {
        Map<String, Object> tips = new HashMap<>();
        String[] pidStrArr = pids.split(",");
        UserProfile profile = UserProfileUtils.getUserProfile();
        try {
            for (String pidStr : pidStrArr) {
                int pid = Integer.parseInt(pidStr);
                // 更新关系表状态
                Rel relUpdate = new Rel();
                relUpdate.setApprovalStatus("6");
                relUpdate.setRelStatus("00");
                relUpdate.setPid(pid);
                relUpdate.setModifiedById(profile.getStaffCode());
                relUpdate.setModifiedByName(profile.getStaffName());
                relUpdate.setModifiedTime(new Date());
                relService.updateSelective(relUpdate);
                // 查询关系数据
                Rel rel = relService.get(pid);
                //失效日期处理
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                String nowDate = dateFormat.format(date);
                Date invalidTime = rel.getInvalidTime();
                String opDate = "";
                if (invalidTime != null) {
                    opDate = dateFormat.format(invalidTime);
                }
                // 判断当前时间与失效时间的大小
                boolean flag = nowDate.compareTo(opDate) < 0;
                // 是否有效关系
                String isValid = rel.getIsValid();
                //判断该记录是否是有效关系
                if ("1".equals(isValid) || ("0".equals(isValid) && flag)) {
                    //判断本层关联方类型为自然人时，将自然人表中关系有效性改为是。
                    // 是否有效 1是 0否
                    String effectRelation = "1";
                    // 是否关联方 0是 1否
                    String isAssoParty = "1";
                    changeRelatedValidStatus(rel.getFromMemo(), rel.getFromType(), rel.getFromIdentifyNumber(), effectRelation, isAssoParty);
//                    try {
//                        //新增关系图谱，此处只需要有新增图谱功能就可以，解除图谱关系显示，由批处理自动任务来处理
//                        List<Rel> list = new ArrayList<>();
//                        list.add(rel);
//                        // 检查解除关联方库中是否存在该关联方，存在及删除
//                        checkDelRel(rel);
//                        relService.insertNeo4j(list);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        throw new RuntimeException("关联关系审批完成后，加入图谱数据操作失败！");
//
//                    }
                }
            }
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } catch (RuntimeException e) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, e.getMessage());
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
        //本层关联方为自然人，自然人证件号码脱敏
        if("PSN".equals(rel.getFromType())){
            //查询关联方证件类型
            if(StringUtils.isNotEmpty(rel.getFromIdentifyNumber())){
                RelPsn relPsn = relPsnService.selectByIdentifyNumber(rel.getFromIdentifyNumber());
                if(ObjectUtil.isNotEmpty(relPsn)){
                    if("1".equals(relPsn.getIdentifyType())){
                        rel.setFromIdentifyNumber(idEncrypt(rel.getFromIdentifyNumber()));
                    }else{
                        rel.setFromIdentifyNumber(otherIdEncrypt(rel.getFromIdentifyNumber()));
                    }
                }
            }
        }
        //前一层关联方为自然人，自然人证件号码脱敏
        if("PSN".equals(rel.getToType())){
            //查询关联方证件类型
            if(StringUtils.isNotEmpty(rel.getToIdentifyNumber())){
                RelPsn relPsn = relPsnService.selectByIdentifyNumber(rel.getToIdentifyNumber());
                if(ObjectUtil.isNotEmpty(relPsn)){
                    if("1".equals(relPsn.getIdentifyType())){
                        rel.setToIdentifyNumber(idEncrypt(rel.getToIdentifyNumber()));
                    }else{
                        rel.setToIdentifyNumber(otherIdEncrypt(rel.getToIdentifyNumber()));
                    }
                }
            }
        }
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
        //本层关联方为自然人，自然人证件号码脱敏
        if("PSN".equals(rel.getFromType())){
            //查询关联方证件类型
            if(StringUtils.isNotEmpty(rel.getFromIdentifyNumber())){
                RelPsn relPsn = relPsnService.selectByIdentifyNumber(rel.getFromIdentifyNumber());
                if(ObjectUtil.isNotEmpty(relPsn)){
                    if("1".equals(relPsn.getIdentifyType())){
                        rel.setFromIdentifyNumber(idEncrypt(rel.getFromIdentifyNumber()));
                    }else{
                        rel.setFromIdentifyNumber(otherIdEncrypt(rel.getFromIdentifyNumber()));
                    }
                }
            }
        }
        //前一层关联方为自然人，自然人证件号码脱敏
        if("PSN".equals(rel.getToType())){
            //查询关联方证件类型
            if(StringUtils.isNotEmpty(rel.getToIdentifyNumber())){
                RelPsn relPsn = relPsnService.selectByIdentifyNumber(rel.getToIdentifyNumber());
                if(ObjectUtil.isNotEmpty(relPsn)){
                    if("1".equals(relPsn.getIdentifyType())){
                        rel.setToIdentifyNumber(idEncrypt(rel.getToIdentifyNumber()));
                    }else{
                        rel.setToIdentifyNumber(otherIdEncrypt(rel.getToIdentifyNumber()));
                    }
                }
            }
        }
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
            Rel rel1 = relService.get(rel.getPid());
            rel.setApprovalStatus("7");
            relService.updateSelective(rel);

            relHistoryService.saveHistory(rel1,"2");
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
            /*
             *  删除时
             */
            Rel rel = relService.get(pid);
            relHistoryService.saveHistory(rel,"3");
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
            com.alibaba.fastjson.JSONObject data = com.alibaba.fastjson.JSONObject.parseObject(JSONObject.toJSONString(rel));
            data.put("TransactionTtype", "REL");
            data.put("type", "REL");
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
    @Transactional
    public Map<String, Object> saveFailure(Rel rel) {
        Map<String, Object> tips = new HashMap<String, Object>();
        UserProfile profile = UserProfileUtils.getUserProfile();
        //int psnOrg = relService.selectSavePsnOrg(rel.getFromIdentifyNumber(), rel.getToIdentifyNumber(), rel.getRelPsnOrg());
        try {
            // 更新失效日期
            //rel.setApprovalStatus("3");
            rel.setOperator(profile.getStaffName());
            rel.setTransactionDep(profile.getDeptName());
            rel.setFailureStatus("2");
            relService.updateSelective(rel);

            UpdateRelatedData.updateDatabaseDataNew(rel);
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

    /**
     * 更新关联方状态
     * @param fromMemo              本层关联方名称
     * @param fromType              本层关联方类型
     * @param fromIdentifyNumber    本层关联方证件号码
     * @param effectRelation        该关联方是否有效关系
     * @param isAssoParty           是否关联方
     */
    private void changeRelatedValidStatus(String fromMemo, String fromType, String fromIdentifyNumber, String effectRelation, String isAssoParty) {
        if (RelatedPartyCategoryEnum.LEGALPERSON.getSysCode().equals(fromType)) {
            List<Filter> filter = new Filter.Builder()
                    .eq("name", fromMemo)
                    .eq("identifyNumber", fromIdentifyNumber)
                    .eq("delflag","1")
                    .build();
            RelPsn psn = relPsnService.get(filter);
            if (psn != null) {
                // 1是 0否
                psn.setEffectRelation(effectRelation);
                // 0是 1否
                psn.setIsAssoParty(isAssoParty);
                psn.setRelateNumber(String.valueOf(psn.getPid() + 100000));
                relPsnService.updateSelective(psn);
            }
            //当是法人时，将法人表中关系有效性改为是。
        } else if (RelatedPartyCategoryEnum.NATURALPERSON.getSysCode().equals(fromType)) {
            List<Filter> filter = new Filter.Builder()
                    .eq("orgName", fromMemo)
                    .eq("uniscCode", fromIdentifyNumber)
                    .build();
            RelOrg org = relOrgService.get(filter);
            if (org != null) {
                org.setEffectRelation(effectRelation);
                //是否关联方  1是 0 否
                org.setIsAssoParty(isAssoParty);
                org.setRelateNumber(String.valueOf(org.getPid() + 200000));
                relOrgService.updateSelective(org);
            }
            //更新金融产品数据
        } else if (RelatedPartyCategoryEnum.FINANCIALPRODUCTS.getSysCode().equals(fromType)) {
            List<Filter> filter = new Filter.Builder()
                    .eq("proName", fromMemo)
                    .eq("proProvider", fromIdentifyNumber)
                    .build();
            RelPro pro = relProService.get(filter);
            if (pro != null) {
                pro.setEffectRelation(effectRelation);
                relProService.updateSelective(pro);
            }
        }
    }


    /**
     * 确认归档时如果在接触关联方库中存在给关联方需要删除
     * @param rel                   关系数据
     */
    private void checkDelRel(Rel rel) {
        String fromType = rel.getFromType();
        if (!RelatedPartyCategoryEnum.FINANCIALPRODUCTS.getSysCode().equals(fromType)) {
            List<Filter> filters = new Filter.Builder().eq("relPid", rel.getFromPid()).eq("type", fromType).build();
            filters.add(new Filter("delflag", Operator.EQ, "1"));
            List<DelRel> delRelList = delRelService.select(filters);
            if (!CollUtil.isEmpty(delRelList)) {
                for (DelRel delRel : delRelList) {
                    int pid = delRel.getPid();
                    DelRel delRelUpdate = new DelRel();
                    delRelUpdate.setPid(pid);
                    delRelUpdate.setDelflag("0");
                    delRelService.updateSelective(delRelUpdate);
                }
            }
        }
    }


    @RequestMapping(value = "/recordNumber")
    @ResponseBody
    @Transactional
    public Map<String, Object> recordNumber() {
        Map<String, Object> tips = new HashMap<>();
        try {
            relService.isAddRecordNumber(tips);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } catch (RuntimeException e) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, e.getMessage());
        }
        return tips;
    }

}
