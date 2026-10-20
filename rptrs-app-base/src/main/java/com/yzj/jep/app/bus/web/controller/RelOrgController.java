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
import com.yzj.jep.app.base.constant.Log;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.app.fil.model.FilRelOrg;
import com.yzj.jep.app.fil.service.FilRelOrgService;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.sys.util.XssCheck;
import com.yzj.jep.app.util.*;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.core.exception.MessageException;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.core.utils.XlsMessage;
import com.yzj.jep.core.utils.xlsio.XlsImportCallback;
import com.yzj.jep.core.utils.xlsio.exception.XlsIeException;
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
import net.sf.jxls.reader.*;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

import static com.yzj.jep.app.sys.web.controller.XlsieController.READ_STATUS;

@Controller
@RequestMapping("/bus/relOrg")
public class RelOrgController extends CrudController<RelOrg> {
    private static Logger logger = LoggerFactory.getLogger(RelOrgController.class);
    @Autowired
    private DelRelService delRelService;
    @Autowired
    FilRelOrgService filRelOrgService;
    @Autowired
    private DutyService dutyService;
    @Autowired
    private NotifyService notifyService;

    @Autowired
    private RelOrgService relOrgService;

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

    @Autowired
    private RelProService relProService;

    @Autowired
    private RelOrgHistoryService relOrgHistoryService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    @Log(module = "关联方管理", description = "机构关联方列表数据")
    public String list(Model model) {

        //用戶角色查詢
        String staffCode = UserProfileUtils.getUserProfile().getStaffCode();
        String url = "bus/relOrg/list";
        AuthorityUtil authorityUtil = new AuthorityUtil();
        String code = authorityUtil.buttonPerm(staffCode, url);
        model.addAttribute("code", code);

        return "bus/relOrgList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    @Log(module = "关联方管理", description = "机构关联方列表数据")
    protected void prepareQuery(Page<RelOrg> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        filters.add(new Filter("delflag", "1"));
        orders.add(Order.desc("modifiedTime"));
        relOrgService.query(page, filters, orders);
        List<RelOrg> list = page.getResult();
        page.setResult(list);
    }

    /*
     * 提交审批
     * */
    @RequestMapping(value = "/send/{pid}")
    @ResponseBody
    @Log(module = "关联方管理", description = "提交审批")
    public Map<String, Object> send(@PathVariable Integer pid, Model model) {
        Map<String, Object> tips = new HashMap<String, Object>();
        RelOrg relOrg = relOrgService.get(pid);
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
    @Log(module = "关联方管理", description = "新增机构关联方数据")
    public String add(Model model) {
        RelOrg relOrg = new RelOrg();
        UserProfile profile = UserProfileUtils.getUserProfile();
        String unitCode = profile.getUnitCode();
        relOrg.setMgerOrg(unitCode);
        model.addAttribute("relOrg", relOrg);
        model.addAttribute("op", "add");
        return "bus/relOrgAdd";
    }

    /**
     * 在输入证件号码时查询号码是否在同一租户下存在
     *
     * @return
     */
    @RequestMapping(value = "/selectMgerOrg")
    @ResponseBody
    @Log(module = "法人或机构管理", description = "新增页面对证件号码验证添加")
    public Boolean selectMgerOrg(String uniscCode, String identifyType) {
        List<Filter> filters = new Filter.Builder().eq("delflag", "1").build();
        if (uniscCode != null && uniscCode != "") {
            filters.add(new Filter("uniscCode", uniscCode));
        }
        if (identifyType != null && identifyType != "") {
            filters.add(new Filter("identifyType", identifyType));
        }
        boolean exists = relOrgService.exists(filters);
        if (exists) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 归属自然人信息页面加载
     */
    @RequestMapping(value = {"/queryPsnList"}, produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
    @ResponseBody
    @Log(module = "关联方管理", description = "归属关系自然人数据")
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
    @Log(module = "关联方管理", description = "下级关系自然人列表数据")
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

    @RequestMapping(value = "/affirmOrg/{pid}")
    public String ccc(@PathVariable String pid, Model model) {
        model.addAttribute("pid", pid);
        return "bus/orgConfirm";
    }

    /**
     * 我的工作台查看页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/confirmView/{pid}")
    public String confirmView(@PathVariable Integer pid, Model model) {
        RelOrg relOrg = relOrgService.get(pid);
        Map result = new HashMap<>();
        model.addAttribute("relOrg", relOrg);
        model.addAttribute("relOrg2", result);
        return "ex/orgForm";
    }

    /**
     * 确认按钮
     *
     * @param pid
     * @return
     */
    @RequestMapping(value = "/affirm")
    @ResponseBody
    @Log(module = "关联方管理", description = "按钮查看权限后端查询")
    public Map<String, Object> affirm(String pid) {
        Map<String, Object> map = new HashMap<>();
        try {
            RelOrg relOrg = relOrgService.get(Integer.parseInt(pid));
            relOrg.setApprovalStatus("6");
            relOrgService.updateSelective(relOrg);

            List<Filter> tfilter = new Filter.Builder().eq("toPid", pid).eq("toType", "ORG").build();
            if (relService.exists(tfilter)) {
                for (Rel rl : relService.select(tfilter)) {
                    rl.setRelStatus("00");
                    relService.updateSelective(rl);
                }
            }
            if (relPsnAndOrgOfProService.exists(tfilter)) {
                for (RelPsnAndOrgOfPro rl : relPsnAndOrgOfProService.select(tfilter)) {
                    rl.setRelStatus("00");
                    relPsnAndOrgOfProService.updateSelective(rl);
                }
            }

            map.put(WebCode.SUCCESS, true);
            map.put(WebCode.MSG, "操作成功！");

        } catch (ServiceException e) {
            logger.info(e.getMessage());
            map.put(WebCode.SUCCESS, false);
            map.put(WebCode.MSG, "操作失败！");
        }

        Integer id = Integer.parseInt(pid);
        RelOrg relOrg = new RelOrg();
        relOrg.setPid(id);
        relOrg.setApprovalStatus("6");


        return map;
    }


    /**
     * 编辑页面
     *
     * @param
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{pid}")
    @Log(module = "关联方管理", description = "编辑机构关联方数据")
    public String edit(@PathVariable Integer pid, Model model) {

        RelOrg org = relOrgService.get(pid);
        RelOrgVo relOrg = new RelOrgVo();
        BeanUtils.copyProperties(org, relOrg);
        //类型转换，将小数类型转为字符串类型，为页面数据做准备
        Float regCapital = org.getRegCapital();
        if (null != regCapital) {
            relOrg.setRegisteredCapital(new BigDecimal(regCapital).stripTrailingZeros().toPlainString());
        }
        model.addAttribute("relOrg", relOrg);
        model.addAttribute("op", "edit");
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
    @Log(module = "关联方管理", description = "查看机构关联方列表数据")
    public String view(@PathVariable Integer pid, Model model) {

        RelOrg org = relOrgService.get(pid);
        RelOrgVo relOrg = new RelOrgVo();
        BeanUtils.copyProperties(org, relOrg);
        //类型转换，将小数类型转为字符串类型，为页面数据做准备
        Float regCapital = org.getRegCapital();
        if (null != regCapital) {
            relOrg.setRegisteredCapital(new BigDecimal(regCapital).stripTrailingZeros().toPlainString());
        }
//        relOrg.setUniscCode(new ConfidentialInfoUtil().hideAllIdCardNum(relOrg.getUniscCode()));
//        relOrg.setLegalIdentifyNumber(new ConfidentialInfoUtil().hideAllIdCardNum(relOrg.getLegalIdentifyNumber()));   //法人编号
        model.addAttribute("relOrg", relOrg);
        model.addAttribute("opt", "view");
        model.addAttribute("op", "view");
        return "bus/relOrgEdit";
    }

    /**
     * 编辑按钮查看权限后端查询
     *
     * @param pid
     * @return
     */
    @RequestMapping(value = "/createPerisExist")
    @ResponseBody
    @Log(module = "关联方管理", description = "按钮查看权限后端查询")
    public Map<String, Boolean> createPerisExist(Integer pid) {
        Map<String, Boolean> map = new HashMap<>();
        RelOrg relOrg = relOrgService.get(pid);
        UserProfile profile = UserProfileUtils.getUserProfile();
        String userid = profile.getUserid();
        if (userid.equals(relOrg.getAmendId())) {
            map.put(WebCode.SUCCESS, true);
        } else {
            map.put(WebCode.SUCCESS, false);
        }
        return map;
    }


    /**
     * 我的工作台查看页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/orgForm/{pid}")
    @Log(module = "关联方管理", description = "查看机构关联方列表数据")
    public String formView(@PathVariable Integer pid, Model model) {
        RelOrg relOrg = relOrgService.get(pid);
        UserProfile profile = UserProfileUtils.getUserProfile();
        String userid = profile.getUserid();
//        if (!userid.equals(relOrg.getModifiedById())) {
//            relOrg.setUniscCode(new ConfidentialInfoUtil().hideAllIdCardNum(relOrg.getUniscCode()));
//            relOrg.setLegalIdentifyNumber(new ConfidentialInfoUtil().hideAllIdCardNum(relOrg.getLegalIdentifyNumber()));
//        }

        List<Filter> filters = new Filter.Builder().eq("flagPid", pid).build();
        List<FilRelOrg> list = filRelOrgService.select(filters);
//        if (list.size() >= 1) {
//            FilRelOrg filRelOrg = list.get(0);
//            RelOrg relOrgL = new RelOrg();
//            BeanUtils.copyProperties(filRelOrg, relOrgL);
//            relOrgL.setUniscCode(new ConfidentialInfoUtil().hideAllIdCardNum(relOrg.getUniscCode()));
//            relOrgL.setLegalIdentifyNumber(new ConfidentialInfoUtil().hideAllIdCardNum(relOrg.getLegalIdentifyNumber()));
//
//            //实体类转json
//            JSONObject relPsn1 = (JSONObject) JSON.toJSON(relOrg);
//            JSONObject relPsn2 = (JSONObject) JSON.toJSON(relOrgL);
//            Map result = JsonUtil.compareJson(relPsn1, relPsn2, null);
//            model.addAttribute("relOrg", relOrg);
//            model.addAttribute("relOrg2", result);
//            model.addAttribute("opt", "view");
//        } else {
        Map result = new HashMap<>();
        model.addAttribute("relOrg", relOrg);
        model.addAttribute("relOrg2", result);
//        }
        return "ex/orgForm";
    }

    /**
     * 保存
     *
     * @param relOrg
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    @Log(module = "关联方管理", description = "保存机构关联方数据")
    public Map<String, Object> save(RelOrg relOrg, TraEnclosureVo traEnclosureVo) {
        Map<String, Object> tips = new HashMap<String, Object>();
        // todo 20250214 按客户要求去除法人证件号校验
        /*boolean b = XssCheck.reflect(relOrg);
        if (!b) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "数据格式不规范，请检查!");
            return tips;
        }*/

        String maxBh = relOrgService.getMaxBh();
        relOrg.setGlfbh(maxBh);
        relOrg.setRelateNumber(maxBh);
        Integer index = 0;
        try {
            RelOrgHistory relOrgHistory = null;
            //获取当前登陆人员信息
            UserProfile profile = UserProfileUtils.getUserProfile();
            //1、判断是新增保存还是编辑保存
            if (relOrg.getPid() != null) {
                //编辑保存
                //构建查询条件，查询关联方是否存在

                RelOrg relOrg1 = relOrgService.get(relOrg.getPid());
                //对数据进行历史记录保存
                relOrgHistory = new RelOrgHistory();
                BeanUtils.copyProperties(relOrg1, relOrgHistory);
                relOrgHistory.setPid(null);
                relOrgHistory.setCreateTime(new Date());
                relOrgHistory.setDelflag(Dict.SF_1);

                Integer fPid = relOrg1.getPid();
                String  effectRelation = relOrg1.getEffectRelation();
                BeanUtils.copyProperties(relOrg, relOrg1);
                relOrg1.setAmendId(profile.getStaffCode());
                relOrg1.setAmendName(profile.getStaffName());
                relOrg1.setMgerOrg(profile.getUnitCode());
                relOrg1.setTransactionDep(profile.getDeptName());
                relOrg1.setOperator(profile.getStaffName());
                relOrg1.setEffectRelation(effectRelation);
                //更新
                index = relOrgService.updateSelective(relOrg1);

                if (index > 0) {
                    //1、根据当前数据的pid，查询关系表里对应的frompid或者topid的数据
                    //2、遍历结果集进行关系数据更新
                    List<Filter> fromFilters = new Filter.Builder().eq("fromPid", fPid).eq("delflag",Dict.SF_1).build();
                    List<Rel> fromlist = relService.select(fromFilters);
                    for (Rel rel:fromlist ) {
                        rel.setFromIdentifyNumber(relOrg1.getUniscCode());
                        rel.setFromMemo(relOrg1.getOrgName());
                        relService.updateSelective(rel);
                    }
                    List<Filter> toFilters = new Filter.Builder().eq("toPid", fPid).eq("delflag",Dict.SF_1).build();
                    List<Rel> tolist = relService.select(toFilters);
                    for (Rel rel:tolist) {
                        rel.setToIdentifyNumber(relOrg1.getUniscCode());
                        rel.setToMemo(relOrg1.getOrgName());
                        relService.updateSelective(rel);
                    }

                    relOrgHistory.setRecordType("2");
                    relOrgHistory.setSourceId(relOrg1.getPid());
                    // 插入备份表
                    relOrgHistory.setApprovalStatus("6");
                    relOrgHistoryService.insertSelective(relOrgHistory);
                }
                tips.put(WebCode.SUCCESS, true);
                tips.put(WebCode.MSG, WebCode.SUCCESS_UPDATE);
            } else {
                //构建查询条件，查询关联方是否存在
                List<Filter> filters = new Filter.Builder().build();
                filters.add(new Filter("uniscCode",relOrg.getUniscCode()));
                filters.add(new Filter("identifyType",relOrg.getIdentifyType()));
                filters.add(new Filter("delflag",Dict.SF_1));
                boolean res = relOrgService.exists(filters);
                if (res){
                    tips.put(WebCode.SUCCESS, false);
                    tips.put(WebCode.MSG,"关联方已存在" );
                    return tips;
                }
                // 新增时需要查询当前是否存在相应版本号的数据
                List<Filter> isExistNum = new Filter.Builder().nnl("recordNumber").eq("delflag", "1").build();
                List<RelOrg> orgList = relOrgService.select(isExistNum);

                String recordNumberNew;
                if(CollectionUtils.isNotEmpty(orgList)){
                    recordNumberNew = orgList.get(0).getRecordNumber();
                }else {
                    // 查询当前最新版本号
                    List<Filter> filterNow = new Filter.Builder().nnl("recordNumber").build();
                    List<Order> orders = new Order.Builder().desc("createTime").build();
                    List<RelPsn> orgDayList = relPsnService.select(filterNow, orders);
                    boolean flag = CollectionUtils.isNotEmpty(orgDayList);
                    if(flag){
                        recordNumberNew =  orgDayList.get(0).getRecordNumber();
                    }else {
                        recordNumberNew = IntegerUtils.recordNumber(flag, null);
                    }
                }
                relOrg.setRecordNumber(recordNumberNew);
                //新增保存
                relOrg.setAmendId(profile.getStaffCode());
                relOrg.setAmendName(profile.getStaffName());
                relOrg.setMgerOrg(profile.getUnitCode());
                relOrg.setTransactionDep(profile.getDeptName());
                relOrg.setOperator(profile.getStaffName());
                //默认数据状态，已归档
                relOrg.setApprovalStatus("6");//已归档
                //添加其他默认值
                relOrg.setIsBlack("0");//是否为黑名单 “0”否
                relOrg.setEffectRelation("0");//是否存在有效关系 1是 0否
                relOrg.setDataSource("1");//数据来源为手工录入
                relOrg.setDelflag("1");
                relOrg.setIsAssoParty("0"); //是否为关联方 1是，0否

                relOrgService.insertSelective(relOrg);
                //对刚保存的数据报一遍查询
                relOrg = relOrgService.select(relOrg).get(0);
                //对数据进行历史记录保存
                relOrgHistory = new RelOrgHistory();
                BeanUtils.copyProperties(relOrg, relOrgHistory);
                relOrgHistory.setPid(null);
                relOrgHistory.setApprovalStatus("6");
                relOrgHistory.setRecordType("1");
                relOrgHistory.setCreateTime(new Date());
                relOrgHistory.setDelflag(Dict.SF_1);
                relOrgHistory.setSourceId(relOrg.getPid());
                relOrgHistoryService.insertSelective(relOrgHistory);

                tips.put(WebCode.SUCCESS, true);
                tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
            tips.put(WebCode.FAILED, true);
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
    @Log(module = "关联方管理", description = "保存")
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
    @RequestMapping(value = "/remove/{pids}")
    @ResponseBody
    @Log(module = "关联方管理", description = "删除机构关联方")
    public Map<String, Object> remove(@PathVariable String pids) {

        Map<String, Object> tips = new HashMap<>();

        String[] pidStrArr = pids.split(",");
        for (String pidStr : pidStrArr) {
            int pid = Integer.parseInt(pidStr);
            try {
                RelOrg relOrg = relOrgService.get(pid);
                UserProfile profile = UserProfileUtils.getUserProfile();

                //修改删除标识符
                relOrg.setDelflag("0");
                relOrgService.updateSelective(relOrg);
                List<Filter> tfilters = new Filter.Builder().eq("toPid", pidStr).eq("toType", "ORG").eq("delflag", "1").build();
                boolean exists = relService.exists(tfilters);
                boolean exists1 = relPsnAndOrgOfProService.exists(tfilters);
                if (exists) {
                    List<Rel> select = relService.select(tfilters);
                    for (Rel rel : select) {
                        rel.setRelStatus("02");
                        relService.updateSelective(rel);
                    }
                }
                if (exists1) {
                    List<RelPsnAndOrgOfPro> select = relPsnAndOrgOfProService.select(tfilters);
                    for (RelPsnAndOrgOfPro rel : select) {
                        rel.setRelStatus("02");
                        relPsnAndOrgOfProService.updateSelective(rel);
                    }
                }

                // 记录删除历史
                RelOrgHistory relOrgHistory = new RelOrgHistory();
                BeanUtils.copyProperties(relOrg, relOrgHistory);
                relOrgHistory.setPid(null);
                relOrgHistory.setSourceId(relOrg.getPid());
                relOrgHistory.setRecordType("3");
                relOrgHistory.setCreateTime(new Date());
                relOrgHistory.setDelflag(Dict.SF_1);
                relOrgHistory.setApprovalStatus("6");
                relOrgHistoryService.insertSelective(relOrgHistory);
                tips.put(WebCode.SUCCESS, true);
                tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
            } catch (ServiceException e) {
                logger.info(e.getMessage());
                tips.put(WebCode.SUCCESS, false);
                tips.put(WebCode.MSG, WebCode.FAILURE_DELETE);
            }
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
    @Log(module = "关联方管理", description = "查重")
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
    @Log(module = "关联方管理", description = "关系自然人列表数据")
    public String listAffi(Model model, int op, @PathVariable Integer orgid) {
        model.addAttribute("orgid", orgid);
        model.addAttribute("op", op);
        return "bus/relPsnListAffi";
    }

    /**
     * 附属信息编辑页面
     */
    @RequestMapping(value = "/editAffi/{orgid}")
    @Log(module = "关联方管理", description = "附属关系自然人编辑数据")
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
    @Log(module = "关联方管理", description = "编辑关系机构数据")
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

    /**
     * 删除
     *
     * @param relId
     * @return
     */
    @RequestMapping(value = "/removeOrg/{relId}")
    @ResponseBody
    @Log(module = "关联方管理", description = "删除机构关联方列表数据")
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
    @Log(module = "关联方管理", description = "查询关联关系查询联动列表数据")
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
    @Log(module = "关联方管理", description = "加入/移出黑名单")
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
    @Log(module = "关联方管理", description = "加入/移出黑名单")
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
    @Log(module = "关联方管理", description = "提交审核")
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
    @Log(module = "关联方管理", description = "解除关联关系")
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
    @Log(module = "关联方管理", description = "解除关联关系")
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

    @RequestMapping(value = "/import", method = RequestMethod.POST, headers = {"Accept=application/json"})
//    @ResponseBody
//    @Transactional
    @Log(module = "关联方管理", description = "查询报送列表数据")
    public void imporExcel(HttpServletRequest request, HttpServletResponse response) {

        List<XlsMessage> lstMsg = new ArrayList<XlsMessage>();
        Map<String, Object> tips = new HashMap<String, Object>();
        tips.put("success", false);

        MultipartFile fileXls = null;
        MultipartResolver resolver = new CommonsMultipartResolver();
        //判断 request 是否有文件上传,即多部分请求
        if (resolver.isMultipart(request)) {
            MultipartRequest multipartRequest = resolver.resolveMultipart(request);
            Map<String, MultipartFile> fileMaps = multipartRequest.getFileMap();
            /*只处理一个文件*/
            for (MultipartFile file : fileMaps.values()) {
                fileXls = file;
                break;
            }
        }

        //判断当前是否有文件需要处理
        if (null != fileXls) {
            try {
                String beanName = request.getParameter("bean");
                if (StringUtils.isNotBlank(beanName)) {
                    Map parameters = new HashMap();
                    Enumeration e = (Enumeration) request.getParameterNames();
                    while (e.hasMoreElements()) {
                        String parameterName = (String) e.nextElement();
                        if (StringUtils.isNotBlank(parameterName)) {
                            parameters.put(parameterName, request.getParameter(parameterName));
                        }
                    }
                    XlsImportCallback iCallback = (XlsImportCallback) SpringContextHolder.getBean(beanName);

                    //读取并解析Excel数据,返回需要的结果集合
                    Map mapReaded = parseXls(fileXls.getInputStream(), beanName);
                    //读取后的结果集
                    XLSReadStatus readStatus = (XLSReadStatus) mapReaded.get(READ_STATUS);
                    //文件内容合法性检验
                    String msgCheck = iCallback.checkBeforeImport(parameters, mapReaded);
                    if (StringUtils.isNotBlank(msgCheck)) {
                        XLSReadMessage msgInvalide = new XLSReadMessage(msgCheck);
                        readStatus.getReadMessages().add(msgInvalide);
                        readStatus.setStatusOK(false);
                    }

                    if (readStatus.isStatusOK()) {
                        //删除readStatus
                        mapReaded.remove(READ_STATUS);
                        //回到业务方法保存数据
                        try {
                            XlsMessage returnMsg = iCallback.executeSaveCallback(parameters, mapReaded);
                            if (returnMsg.getException() != null)//返回异常，代表导入失败
                            {
                                lstMsg.add(returnMsg);
                                tips.put("success", false);
                            } else//没有异常，代表导入成功
                            {
                                lstMsg.add(returnMsg);
                                tips.put("success", true);
                            }
                        } catch (Exception be) {
                            throw new XlsIeException("保存业务数据错误！");
                        }

                        //保存成功信息返回前台  如果在具体处理类中没有添加导入成功与否的处理，这里添加通用的提示信息
                        if (lstMsg.size() == 0) {
                            XlsMessage msg = new XlsMessage();
                            msg.setMessage("导入成功。");
                            lstMsg.add(msg);
                            tips.put("success", true);
                        }
                    } else {
                        //异常信息返回前台
                        lstMsg.addAll(readStatus.getReadMessages());
                    }
                }
            } catch (MessageException e) {

            } catch (Exception e) {

                logger.error("系统错误:" + e.getMessage());
                //异常信息返回前台
                XlsMessage msg = new XlsMessage();
                msg.setMessage("系统错误");
                msg.setException(e.getMessage());
                lstMsg.add(msg);
            }
        } else {
            XlsMessage msg = new XlsMessage();
            msg.setMessage("无法解析当前文件。");
            lstMsg.add(msg);
        }

        tips.put("msg", lstMsg);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");

        try {
            //将导入日志信息写到前端页面
            JSONObject returnData = JSONObject.parseObject(JSON.toJSONString(tips));
            response.getWriter().write(returnData.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Log(module = "关联方管理", description = "查询报送列表数据")
    private Map parseXls(InputStream inputXLS, String bean) throws ServiceException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream input;
        InputStream input2;
        XlsImportCallback iCallback = (XlsImportCallback) SpringContextHolder.getBean(bean);
        try {

            byte[] buffer = new byte[1024 * 1024 * 3];
            int len;
            while ((len = inputXLS.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();

            input = new ByteArrayInputStream(baos.toByteArray());
            input2 = new ByteArrayInputStream(baos.toByteArray());

            List lstRtn = new ArrayList();

            String xmlTemplate = iCallback.getImportXmlTemplate();
            if (StringUtils.isEmpty(xmlTemplate)) {
                throw new XlsIeException("导入Excel的规则配置文件为空!");
            }
            //设置读取时忽略错误
            ReaderConfig.getInstance().setSkipErrors(true);
            InputStream inputXML = new BufferedInputStream(getClass().getResourceAsStream(xmlTemplate));
            XLSReader mainReader = ReaderBuilder.buildFromXML(inputXML);

            //读取excel回调,组装
            Map beans = new HashMap();
            iCallback.setXlsReaderBeans(beans);
            XLSReadStatus readStatus = mainReader.read(input2, beans);
//            beans = iCallback.setReturnBeanFromXls(beans, input);
            if (inputXLS != null) {
                inputXLS.close();
                inputXLS = null;
            }
            if (baos != null) {
                baos.close();
                baos = null;
            }
            if (input != null) {
                input.close();
                input = null;
            }
            if (input2 != null) {
                input2.close();
                input2 = null;
            }

            //lstRtn.add(readStatus);
            beans.put(READ_STATUS, readStatus);
            if (!readStatus.isStatusOK()) {
                beans.put(READ_STATUS, readStatus);
                return beans;
            } else {
                return beans;
            }


        } catch (IOException e) {
            throw new ServiceException(e);
        } catch (SAXException e) {
            throw new ServiceException(e);
        } catch (InvalidFormatException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * 关系自然人信息页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/affiliateList/{pid}")
    @Log(module = "关联方管理", description = "关系自然人列表数据")
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
    @Log(module = "关联方管理", description = "关系自然人列表数据")
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
    @Log(module = "关联方管理", description = "新增关系自然人数据")
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
    @RequestMapping(value = "/viewAffiliate/{pid}/{relId}/{fromPid}")
    @Log(module = "关联方管理", description = "查看关系自然人列表数据")
    public String addAffiliate(@PathVariable Integer pid, @PathVariable Integer relId, @PathVariable Integer fromPid, Model model) {

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
        BeanUtils.copyProperties(rel, relPsnVo);
        relPsnVo.setRelStatement(rel.getRelStatement());
        relPsnVo.setActiveTimeString(rel.getActiveTimeString());
        relPsnVo.setInvalidTimeString(rel.getInvalidTimeString());
        relPsnVo.setIsValid(rel.getIsValid());
        relPsnVo.setEquityShare(rel.getEquityShare());
        relPsnVo.setShareholdingRatio(rel.getShareholdingRatio());
        relPsnVo.setPledgeRatio(rel.getPledgeRatio());
        relPsnVo.setIdentifyNumber(new ConfidentialInfoUtil().hideAllIdCardNum(relPsn.getIdentifyNumber()));
        relPsnVo.setMobile(new ConfidentialInfoUtil().hideAllPhoneNum(relPsn.getMobile()));
        model.addAttribute("relPsnVo", relPsnVo);
        model.addAttribute("opt", "edit");

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
    @Log(module = "关联方管理", description = "编辑关系自然人数据")
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
        BeanUtils.copyProperties(rel, relPsnVo);
        relPsnVo.setRelStatement(rel.getRelStatement());
        relPsnVo.setActiveTimeString(rel.getActiveTimeString());
        relPsnVo.setInvalidTimeString(rel.getInvalidTimeString());
        relPsnVo.setEquityShare(rel.getEquityShare());
        relPsnVo.setShareholdingRatio(rel.getShareholdingRatio());
        relPsnVo.setPledgeRatio(rel.getPledgeRatio());
        model.addAttribute("relPsnVo", relPsnVo);
        model.addAttribute("opt", "edit");
        model.addAttribute("pid", pid);
        model.addAttribute("relId", relId);
        model.addAttribute("fromPid", fromPid);
        model.addAttribute("savefromPid", pid);

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
    @Log(module = "关联方管理", description = "保存关系自然人数据")
    public Map<String, Object> saveAffiliate(@PathVariable String parentPid, @PathVariable String fromPid, RelPsnVo relPsnVo) throws ParseException {
        Map<String, Object> tips = new HashMap<String, Object>();
        //归属关联方
        RelPsn fromPsn = relPsnService.get(Integer.valueOf(parentPid));
        //新增关系to机构
        RelOrg relOrg = relOrgService.get(Integer.valueOf(fromPid));
        Rel rel = new Rel();
        //归属关联方pid
        rel.setFromPid(parentPid);
        //添加基本信息自然人的pid
        rel.setToPid(fromPid);
        Rel dbRel = relService.get(rel);
        rel.setRelCategory(relPsnVo.getRelCategory());
        rel.setRelCategorySub(relPsnVo.getRelCategorySub());
        rel.setRelPredicate(relPsnVo.getPredicate());//关联关系判定
        rel.setEquityShare(relPsnVo.getEquityShare());
        rel.setPledgeRatio(relPsnVo.getPledgeRatio());
        rel.setShareholdingRatio(relPsnVo.getShareholdingRatio());
        rel.setEffectiveDate(relPsnVo.getEffectiveDate());
        rel.setIneffectiveDate(relPsnVo.getIneffectiveDate());
        rel.setIsValid(relPsnVo.getIsValid());
        //如果查到两人间有相同关系，更新原先关系
        boolean isExist = false;
        if (dbRel != null) {
            isExist = true;
            rel.setPid(dbRel.getPid());
        }
        rel.setDelflag("1");
        rel.setFromType("PSN");
        rel.setToType("ORG");
        if (relPsnVo.getShareholdingProportion() != null) {
            rel.setShareholdingRatio(Float.parseFloat(relPsnVo.getShareholdingProportion()));
        }
        new ConfirmUtil().relOrg(relOrg); //设置状态
        relOrgService.updateSelective(relOrg);
        //关系状态为待新增
        rel.setRelStatus("01");
        rel.setRelStatement(relPsnVo.getRelStatement());
        rel.setActiveTimeString(relPsnVo.getActiveTimeString());//生效时间
        rel.setInvalidTimeString(relPsnVo.getInvalidTimeString());//失效时间
        if (isExist) {
            relService.updateSelective(rel);
        } else {
            relService.insert(rel);

        }
        tips.put(WebCode.SUCCESS, true);
        tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        return tips;
    }

    /**
     * 关系机构信息页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/relOrgView/{pid}")
    @Log(module = "关联方管理", description = "关系机构列表数据")
    public String relOrgView(@PathVariable String pid, Model model) {
        model.addAttribute("fromPid", pid);
        return "bus/relOrgView";
    }


    /**
     * 金融的关系编辑机构关系信息页面
     *
     * @param pid
     * @param model
     * @param fromPid
     * @param relId
     * @return
     */
    @RequestMapping(value = "/editProRelOrg/{pid}/{fromPid}/{relId}")
    @Log(module = "关联方管理", description = "金融的关系编辑机构关系信息数据")
    public String editProRelOrg(@PathVariable Integer pid, Model model, @PathVariable Integer fromPid, @PathVariable Integer relId) {
        RelOrgService relOrgService = SpringContextHolder.getBean(RelOrgService.class);
        RelOrg relOrg = relOrgService.get(pid);
        RelOrgVo relOrgVo = new RelOrgVo();
        BeanUtils.copyProperties(relOrg, relOrgVo);
        RelPsnAndOrgOfPro rel = relPsnAndOrgOfProService.get(relId);
        if (rel.getShareholdingRatio() != null) {
            relOrgVo.setShareholdingRatio(rel.getShareholdingRatio());
        }
        relOrgVo.setRelCategory(rel.getRelCategory());
        relOrgVo.setRelCategorySub(rel.getRelCategorySub());
        relOrgVo.setActiveTimeString(rel.getActiveTimeString());
        relOrgVo.setInvalidTimeString(rel.getInvalidTimeString());
        relOrgVo.setEquityShare(rel.getEquityShare());
        relOrgVo.setRelStatement(rel.getRelStatement());
        model.addAttribute("relOrg", relOrgVo);
        model.addAttribute("opt", "edit");
        model.addAttribute("fromPid", fromPid);
        model.addAttribute("relId", relId);
        model.addAttribute("paFromPid", pid);

        return "bus/proRelOrgAddOrEdit";
    }

    /**
     * 金融的关系查看机构关系信息页面
     *
     * @param pid
     * @param model
     * @param fromPid
     * @param relId
     * @return
     */
    @RequestMapping(value = "/viewPsnRelOrgs/{pid}/{fromPid}/{relId}")
    @Log(module = "关联方管理", description = "金融的关系查看机构关系信息数据")
    public String viewPsnRelOrg(@PathVariable Integer pid, Model model, @PathVariable Integer fromPid, @PathVariable Integer relId) {
        RelOrgService relOrgService = SpringContextHolder.getBean(RelOrgService.class);
        RelOrg relOrg = relOrgService.get(pid);
        RelOrgVo relOrgVo = new RelOrgVo();
        BeanUtils.copyProperties(relOrg, relOrgVo);
        RelPsnAndOrgOfPro rel = relPsnAndOrgOfProService.get(relId);
        if (rel.getShareholdingRatio() != null) {
            relOrgVo.setShareholdingRatio(rel.getShareholdingRatio());
        }
        relOrgVo.setRelCategory(rel.getRelCategory());
        relOrgVo.setRelCategorySub(rel.getRelCategorySub());
        relOrgVo.setActiveTimeString(rel.getActiveTimeString());
        relOrgVo.setInvalidTimeString(rel.getInvalidTimeString());
        relOrgVo.setEquityShare(rel.getEquityShare());
        relOrgVo.setRelStatement(rel.getRelStatement());
        model.addAttribute("relOrg", relOrgVo);
        model.addAttribute("opt", "view");
        model.addAttribute("fromPid", fromPid);
        model.addAttribute("relId", relId);
        model.addAttribute("paFromPid", pid);

        return "bus/proRelOrgAddOrEdit";
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
    @Log(module = "关联方管理", description = "关系机构信息列表数据")
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
    @Log(module = "关联方管理", description = "新增关系机构数据")
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
    @Log(module = "关联方管理", description = "编辑关系机构数据")
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
        relOrgVo.setRelStatement(rel.getRelStatement());
       /* List<Filter> filters = new Filter.Builder().eq("relCategorySub", relOrgVo.getRelCategorySub()).build();
        List<RelDict> relDictList = relDictService.select(filters);
        relOrgVo.setRelCategorySub(relDictList.get(0).getRelCategorySubCode());*/
        model.addAttribute("relPsnVo", relOrgVo);
        model.addAttribute("opt", "edit");
        model.addAttribute("pid", pid);
        model.addAttribute("relId", relId);
        model.addAttribute("fromPid", fromPid);

        return "bus/affiliateAddOrEdit";
    }


    /*保存金融的自然人*/

    @RequestMapping(value = "/ProsavePsn/{paFromPid}/{fromPid}")
    @ResponseBody
    @Log(module = "关联方管理", description = "保存金融的自然人数据")
    public Map<String, Object> ProsavePsn(@PathVariable String paFromPid, @PathVariable String fromPid, RelOrgVo relOrgVo) {
        Map tips = new HashMap();

        //frompid  jingrong
        //归属关联方
        RelPsn fromOrg = relPsnService.get(Integer.valueOf(paFromPid));
        //新增关系to机构
        RelPro relPro = relProService.get(Integer.valueOf(fromPid));
        String fromAlig = fromOrg.getAligCode();
        String chAligCode = relPro.getAligCode();
        //新增关联方根据上级定为码以及关系判断是否关联方,更新to定为码
        if (com.yzj.jep.commons.StringUtils.isNotEmpty(fromAlig)) {
            //更新关系表
            Rel rel = new Rel();
            //归属关联方pid
            rel.setFromPid(paFromPid);
            //添加基本信息机构的pid
            rel.setToPid(fromPid);
            rel.setRelCategory(relOrgVo.getRelCategory());
            rel.setRelCategorySub(relOrgVo.getRelCategorySub());
            Rel dbRel = relService.get(rel);
            rel.setIsValid(relOrgVo.getIsValid());
            rel.setEffectiveDate(relOrgVo.getEffectiveDate());
            rel.setIneffectiveDate(relOrgVo.getIneffectiveDate());
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
                    if (com.yzj.jep.commons.StringUtils.isNotEmpty(chAligCode)) {
                        String[] chAligCodes = chAligCode.split(",");
                        List<String> codeList = Arrays.asList(chAligCodes);
                        if (!codeList.contains(toAligCode)) {
                            relPro.setAligCode(chAligCode + "," + toAligCode);
                        }
                    } else {
                        relPro.setAligCode(toAligCode);
                    }
                    //关系设置定为码
                    rel.setFromAligCode(alig);
                    rel.setToAligCode(toAligCode);
                    rel.setRegulator(aligMap.getCaliber());
                    rel.setDelflag("1");
                    rel.setFromType("ORG");
                    rel.setToType("PSN");
                    if (relOrgVo.getShareholdingProportion() != null) {
                        rel.setShareholdingRatio(Float.parseFloat(relOrgVo.getShareholdingProportion()));
                    }
                    //关联人设置口径
                    // relPro.setRegulatorOth1(aligMap.getCaliber());
                } else {
                    //关系设置定为码
                    rel.setFromAligCode(alig);
                    rel.setDelflag("1");
                    rel.setFromType("ORG");
                    rel.setToType("PSN");
                    if (relOrgVo.getShareholdingProportion() != null) {
                        rel.setShareholdingRatio(Float.parseFloat(relOrgVo.getShareholdingProportion()));
                    }
                }
                // relPro.setStatus("3");
                relProService.update(relPro);
                //关系状态为待新增
                rel.setRelStatus("01");
                if (isExist) {
                    relService.update(rel);
                } else {
                    relService.insert(rel);
//                    relPsnService.insertNeo4jOrgToPsn(fromOrg, relPro, rel);
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

    /**
     * 保存关系机构页面
     *
     * @param relOrgVo
     * @return
     */
    @RequestMapping(value = "/saveOrg/{paFromPid}/{fromPid}")
    @ResponseBody
    @Transactional
    @Log(module = "关联方管理", description = "保存关系机构数据")
    public Map<String, Object> saveOrg(@PathVariable String paFromPid, @PathVariable String fromPid, RelOrgVo relOrgVo) throws Exception {
        Map tips = new HashMap();
        //归属关联方
        RelOrg fromOrg = relOrgService.get(Integer.valueOf(paFromPid));
        //新增关系to机构
        RelOrg relOrg = relOrgService.get(Integer.valueOf(fromPid));
        //更新关系表
        Rel rel = new Rel();
        //归属关联方pid
        rel.setFromPid(paFromPid);
        //添加基本信息机构的pid
        rel.setToPid(fromPid);
        Rel dbRel = relService.get(rel);
        rel.setRelCategory(relOrgVo.getRelCategory());
        rel.setRelCategorySub(relOrgVo.getRelCategorySub());
        rel.setRelPredicate(relOrgVo.getPredicate());//关联关系判定
        rel.setEquityShare(relOrgVo.getEquityShare());
        rel.setIsValid(relOrgVo.getIsValid());
        rel.setPledgeRatio(relOrgVo.getPledgeRatio());
        rel.setShareholdingRatio(relOrgVo.getShareholdingRatio());
        rel.setEffectiveDate(relOrgVo.getEffectiveDate());
        rel.setIneffectiveDate(relOrgVo.getIneffectiveDate());
        //如果查到两机构间有相同关系，更新原先关系
        boolean isExist = false;
        if (dbRel != null) {
            isExist = true;
            rel.setPid(dbRel.getPid());
        }
        rel.setDelflag("1");
        rel.setFromType("ORG");
        rel.setToType("ORG");
        if (relOrgVo.getShareholdingProportion() != null) {
            rel.setShareholdingRatio(Float.parseFloat(relOrgVo.getShareholdingProportion()));
        }
        if (relOrgVo.getShareholdingRatio() != null) {
            rel.setShareholdingRatio(relOrgVo.getShareholdingRatio());
        }
        new ConfirmUtil().relOrg(relOrg); //设置状态
        relOrgService.updateSelective(relOrg);
        //关系状态为待新增
        rel.setRelStatus("01");
        rel.setRelStatement(relOrgVo.getRelStatement());
        rel.setActiveTimeString(relOrgVo.getActiveTimeString());
        rel.setInvalidTimeString(relOrgVo.getInvalidTimeString());
        if (isExist) {
            relService.updateSelective(rel);
        } else {
            relService.insert(rel);
        }

        tips.put(WebCode.SUCCESS, true);
        tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);

        return tips;
    }

    /*
     * 任务下发
     * */
    @RequestMapping(value = "/relOrgSend")
    @Log(module = "关联方管理", description = "任务下发")
    public String relOrgSend(Model model) {

        return "bus/relOrgSend";

    }

    /*
     * 确认下发
     * */
    @RequestMapping(value = "/taskSend")
    @ResponseBody
    @Log(module = "关联方管理", description = "确认下发")
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
    @Log(module = "关联方管理", description = "反显机构信息")
    public List<RelOrg> selectRelPsn(String orgName, String identifyType, String uniscCode) {
        List<RelOrg> relOrgs = new ArrayList<>();
        UserProfile profile = UserProfileUtils.getUserProfile();
        List<Filter> filters = new Filter.Builder().eq("delflag", "1").eq("approvalStatus", "6").build();
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
     * 根据父级关系查询子关系下拉选项
     *
     * @param relCategory
     * @param type
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryRelCategorySub")
    @ResponseBody
    @Log(module = "关联方管理", description = "父级关系联动下级关系")
    public List<NameValuePair> getDictItems(String relCategory, String type) throws Exception {
        List<NameValuePair> items = new ArrayList<>();
        try {

            List<Filter> filters = new Filter.Builder().eq("dictCode", "BUS-Rel").build();
            List<DictItem> list = dictItemService.select(filters);
            String includes = "";
            //添加关联方为机构
            if ("p".equals(type)) {
                //工作关系
                if ("2".equals(relCategory)) {
                    includes = "6,7,8,9,10,11,12";
                }//被投资关系
                if ("4".equals(relCategory)) {
                    includes = "25,26,27,28";
                }//	一致行动人/协议或依赖关系
                if ("5".equals(relCategory)) {
                    includes = "33";
                }
                if ("6".equals(relCategory)) {
                    includes = "34,35";
                }
            }//添加关联方为机构
            else if ("o".equals(type)) {
                //投资关系
                if ("3".equals(relCategory)) {
                    includes = "17,18,19,20";
                }//被投资关系
                if ("4".equals(relCategory)) {
                    includes = "25,26,27,28";
                }//	一致行动人/协议或依赖关系
                if ("5".equals(relCategory)) {
                    includes = "33";
                }
                if ("6".equals(relCategory)) {
                    includes = "34,35";
                }
            }//添加关联方为金融产品
            else if ("r".equals(type)) {
                if ("3".equals(relCategory)) {
                    includes = "21,22,23,24";
                }//被投资关系
                if ("4".equals(relCategory)) {
                    includes = "25,26,27,28";
                }//	一致行动人/协议或依赖关系
            } else if ("t".equals(type)) {
                if ("3".equals(relCategory)) {
                    includes = "21,22,23,24";
                }//被投资关系
                if ("4".equals(relCategory)) {
                    includes = "29,30,31,32";
                }//	一致行动人/协议或依赖关系
            }
            String[] includesArr = includes.split(",");
            List<String> includesList = Arrays.asList(includesArr);
            //过滤掉不要的字典项
            if (!org.springframework.util.CollectionUtils.isEmpty(list)) {
                for (int i = 0; i < list.size(); i++) {
                    DictItem item = list.get(i);
                    if (org.springframework.util.StringUtils.hasText(includes) && includesList.contains(item.getItemValue())) {
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
     * 跳转到金融产品信息
     */
    @RequestMapping(value = "/orgToProList/{orgid}")
    @Log(module = "关联方管理", description = "金融产品列表数据")
    public String proRelList(Model model, int op, @PathVariable Integer orgid) throws Exception {
        model.addAttribute("orgid", orgid);
        model.addAttribute("op", op);
        return "bus/orgToProList";
    }

    /*
     * 查找归属金融产品的信息
     * */
    @RequestMapping(value = {"/queryProVo"}, produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
    @ResponseBody
    @Log(module = "关联方管理", description = "归属金融产品列表数据")
    public Map<String, Object> queryProVo(Page<RelProVo> page, Model model, HttpServletRequest request, Integer orgid) {
        Map<String, Object> result = new HashMap<String, Object>();
        PageHelper.start(page);
        List<RelProVo> tzOrgPsn = relOrgService.getOrgUpPro(String.valueOf(orgid));
        PageInfo pageInfo = new PageInfo(tzOrgPsn);
        result.put(RESULT_TOTAL, pageInfo.getTotal());
        result.put(RESULT_ROWS, pageInfo.getList());
        return result;
    }

    /*
     * 查找下级金融产品的信息
     * */
    @RequestMapping(value = {"/queryDownProVo"}, produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
    @ResponseBody
    @Log(module = "关联方管理", description = "下级金融产品列表数据")
    public Map<String, Object> queryDownProVo(Page<RelProVo> page, Model model, HttpServletRequest request, Integer orgid) {
        Map<String, Object> result = new HashMap<String, Object>();
        PageHelper.start(page);
        List<RelProVo> tzOrgPsn = relOrgService.getOrgDownPro(String.valueOf(orgid));
        PageInfo pageInfo = new PageInfo(tzOrgPsn);
        result.put(RESULT_TOTAL, pageInfo.getTotal());
        result.put(RESULT_ROWS, pageInfo.getList());
        return result;
    }

    /**
     * 机构关联金融产品
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/proOfOrgList/{pid}")
    @Log(module = "关联方管理", description = "关系金融产品数据")
    public String proOfOrgList(@PathVariable String pid, int op, Model model) {
        model.addAttribute("fromPid", pid);
        model.addAttribute("op", op);
//		model.addAttribute("opt", opt);
        return "bus/proOfOrgList";
    }

    /**
     * 自然人归属的金融产品
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/proUpOfOrgList/{pid}")
    @ResponseBody
    @Log(module = "关联方管理", description = "自然人归属的金融产品")
    public Map<String, Object> proUpOfOrgList(@PathVariable String pid, Model model, Page<RelProVo> page) {
        List<RelProVo> relProVoList = relPsnAndOrgOfProService.selectRelProUpOfOrgInformation(pid);
        Map<String, Object> result = new HashMap<String, Object>();
        page.setTotal(relProVoList.size());
        int pages = (relProVoList.size() + page.getPageSize() - 1) / page.getPageSize();
        if (page.getPageNumber() > pages) {
            page.setPageNumber(1);
        }
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        List<RelProVo> relProVos = new ArrayList<RelProVo>();
        for (int i = index; i < index + page.getPageSize(); i++) {
            if (i < relProVoList.size()) {
                relProVos.add(relProVoList.get(i));
            }
        }
        page.setResult(relProVos);

        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());

        return result;
    }

    /**
     * 自然人归属的金融产品
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/proDownOfOrgListss/{pid}")
    @ResponseBody
    @Log(module = "关联方管理", description = "自然人归属的金融产品")
    public Map<String, Object> proDownOfOrgLists(@PathVariable String pid, Model model, Page<RelProVo> page) {
        List<RelProVo> relProVoList = relPsnAndOrgOfProService.selectRelProDownOfOrgInformation(pid);
        Map<String, Object> result = new HashMap<String, Object>();
        page.setTotal(relProVoList.size());
        int pages = (relProVoList.size() + page.getPageSize() - 1) / page.getPageSize();
        if (page.getPageNumber() > pages) {
            page.setPageNumber(1);
        }
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        List<RelProVo> relProVos = new ArrayList<RelProVo>();
        for (int i = index; i < index + page.getPageSize(); i++) {
            if (i < relProVoList.size()) {
                relProVos.add(relProVoList.get(i));
            }
        }
        page.setResult(relProVos);

        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());

        return result;
    }

    /**
     * 机构关联下级金融产品
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/addProUpOfOrg/{pid}")
    @Log(module = "关联方管理", description = "机构关联下级金融产品")
    public String addProUpOfOrg(@PathVariable Integer pid, Model model) {
        model.addAttribute("opt", "add");
        // 机构pid
        model.addAttribute("fromPid", pid);
        return "bus/proOfOrgAddOrEdit";
    }

    /**
     * 编辑机构关联方所属金融产品
     *
     * @param
     * @param model
     * @return
     */
    @RequestMapping(value = "/editProUpOfOrg/{pid}/{relId}/{fromPid}")
    @Log(module = "关联方管理", description = "金融产品编辑所属自然人")
    public String proeditPsn(@PathVariable Integer pid, @PathVariable Integer relId, @PathVariable Integer fromPid, Model model) {
        RelPro relPro = relProService.get(pid);
        RelPsnAndOrgOfPro rel = relPsnAndOrgOfProService.get(relId);
        RelProVo relProVo = new RelProVo();
        BeanUtils.copyProperties(relPro, relProVo);
        relProVo.setRelStatement(rel.getRelStatement());
        relProVo.setActiveTimeString(rel.getActiveTimeString());
        relProVo.setInvalidTimeString(rel.getInvalidTimeString());
        relProVo.setEquityShare(rel.getEquityShare());
        relProVo.setRelCategory(rel.getRelCategory());
        relProVo.setRelCategorySub(rel.getRelCategorySub());
        relProVo.setShareholdingRatio(rel.getShareholdingRatio());
        model.addAttribute("relProVo", relProVo);
        model.addAttribute("opt", "edit");
        model.addAttribute("op", "0");
        model.addAttribute("pid", pid);
        model.addAttribute("relId", relId);
        model.addAttribute("paFromPid", pid);
        model.addAttribute("fromPid", rel.getToPid());
        return "bus/proOfOrgEdit";
    }

    /**
     * 编辑机构关联方所属金融产品
     *
     * @param
     * @param model
     * @return
     */
    @RequestMapping(value = "/viewProUpOfOrg/{pid}/{relId}/{fromPid}")
    @Log(module = "关联方管理", description = "金融产品编辑所属自然人")
    public String viewProUpOfOrg(@PathVariable Integer pid, @PathVariable Integer relId, @PathVariable Integer fromPid, Model model) {
        RelPro relPro = relProService.get(pid);
        // RelService relService = SpringContextHolder.getBean(RelService.class);
        RelPsnAndOrgOfPro rel = relPsnAndOrgOfProService.get(relId);
        RelProVo relProVo = new RelProVo();
        BeanUtils.copyProperties(relPro, relProVo);
        relProVo.setRelStatement(rel.getRelStatement());
//        relPsnVo.setEffectiveDate(rel.getEffectiveDate());
//        relPsnVo.setIneffectiveDate(rel.getIneffectiveDate());
        relProVo.setActiveTimeString(rel.getActiveTimeString());
        relProVo.setInvalidTimeString(rel.getInvalidTimeString());
        relProVo.setEquityShare(rel.getEquityShare());
        relProVo.setRelCategory(rel.getRelCategory());
        relProVo.setRelCategorySub(rel.getRelCategorySub());
        relProVo.setShareholdingRatio(rel.getShareholdingRatio());
        model.addAttribute("relProVo", relProVo);
        model.addAttribute("opt", "edit");
        model.addAttribute("op", "1");
        model.addAttribute("pid", pid);
        model.addAttribute("relId", relId);
        model.addAttribute("paFromPid", pid);
        model.addAttribute("fromPid", rel.getToPid());
        return "bus/proOfOrgEdit";
    }


    /**
     * 自然人关联下级金融产品
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/proDownOfOrgList/{pid}")
    @ResponseBody
    @Log(module = "关联方管理", description = "自然人关联下级金融产品")
    public Map<String, Object> proDownOfOrgList(@PathVariable String pid, Model model, Page<RelProVo> page) {
        List<RelProVo> relProVoList = relPsnAndOrgOfProService.selectRelProDownOfOrgInformation(pid);
        Map<String, Object> result = new HashMap<String, Object>();
        page.setTotal(relProVoList.size());
        int pages = (relProVoList.size() + page.getPageSize() - 1) / page.getPageSize();
        if (page.getPageNumber() > pages) {
            page.setPageNumber(1);
        }
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        List<RelProVo> relProVos = new ArrayList<RelProVo>();
        for (int i = index; i < index + page.getPageSize(); i++) {
            if (i < relProVoList.size()) {
                relProVos.add(relProVoList.get(i));
            }
        }
        page.setResult(relProVos);

        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());

        return result;
    }

    /**
     * 保存自然人的金融产品关系信息
     *
     * @param relProVo
     * @return
     */
    @RequestMapping(value = "/saveProOfOrg/{parentPid}/{fromPid}")
    @ResponseBody
    @Log(module = "关联方管理", description = "保存自然人的金融产品关系信息")
    public Map<String, Object> saveProOfOrg(@PathVariable String parentPid, @PathVariable String fromPid, RelProVo relProVo) {
        Map<String, Object> tips = new HashMap<String, Object>();
        //归属关联方金融产品
        RelPro fromPro = relProService.get(Integer.valueOf(parentPid));
        //新增关系to机构
        RelOrg relOrg = relOrgService.get(Integer.valueOf(fromPid));
        //更新关系表
        RelPsnAndOrgOfPro relPsnAndOrgOfPro = new RelPsnAndOrgOfPro();
        relPsnAndOrgOfPro.setPid(relProVo.getRelId());
        relPsnAndOrgOfPro.setDelflag("1");
        //归属关联方pid
        relPsnAndOrgOfPro.setFromPid(parentPid);
        //添加基本信息机构的pid
        relPsnAndOrgOfPro.setToPid(fromPid);
        RelPsnAndOrgOfPro dbRelPsnAndOrgOfPro = relPsnAndOrgOfProService.get(relPsnAndOrgOfPro);
        relPsnAndOrgOfPro.setRelCategory(relProVo.getRelCategory());
        relPsnAndOrgOfPro.setEquityShare(relProVo.getEquityShare());
        relPsnAndOrgOfPro.setRelCategorySub(relProVo.getRelCategorySub());
        //如果查到两人间有相同关系，更新原先关系
        boolean isExist = false;
        if (dbRelPsnAndOrgOfPro != null) {
            isExist = true;
            relPsnAndOrgOfPro.setPid(dbRelPsnAndOrgOfPro.getPid());
        }
        relPsnAndOrgOfPro.setDelflag("1");
        relPsnAndOrgOfPro.setFromType("PRO");
        relPsnAndOrgOfPro.setToType("ORG");
        relPsnAndOrgOfPro.setShareholdingRatio(relProVo.getShareholdingRatio());
        relPsnAndOrgOfPro.setRelStatus("01");
        relPsnAndOrgOfPro.setIsValid(relProVo.getIsValid());
        relPsnAndOrgOfPro.setEffectiveDate(relProVo.getEffectiveDate());
        relPsnAndOrgOfPro.setIneffectiveDate(relProVo.getIneffectiveDate());
        relPsnAndOrgOfPro.setRelStatement(relProVo.getRelStatement());

        new ConfirmUtil().relOrg(relOrg); //设置状态
        relOrgService.updateSelective(relOrg);
        //
        relPsnAndOrgOfPro.setRelStatus("01");
        relPsnAndOrgOfPro.setActiveTimeString(relProVo.getActiveTimeString());
        relPsnAndOrgOfPro.setInvalidTimeString(relProVo.getInvalidTimeString());
        if (isExist) {
            relPsnAndOrgOfProService.updateSelective(relPsnAndOrgOfPro);
        } else {
            relPsnAndOrgOfProService.insert(relPsnAndOrgOfPro);
        }
        tips.put(WebCode.SUCCESS, true);
        tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        return tips;
    }

    @RequestMapping(value = "/recordNumber")
    @ResponseBody
    @Transactional
    public Map<String, Object> recordNumber() {
        Map<String, Object> tips = new HashMap<>();
        try {
            relOrgService.isAddRecordNumber(tips);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } catch (RuntimeException e) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, e.getMessage());
        }
        return tips;
    }
}
