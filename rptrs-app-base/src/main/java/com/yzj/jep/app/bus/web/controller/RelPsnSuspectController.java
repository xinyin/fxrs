/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年03月23日 14:26:15
 *
 */

package com.yzj.jep.app.bus.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.model.suspect.DownSuspect;
import com.yzj.jep.app.bus.model.suspect.UpSuspect;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.app.bus.vo.PersonOrgVo;
import com.yzj.jep.app.bus.vo.PersonRelationVo;
import com.yzj.jep.app.ins.model.TraFunds;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.AuthorityUtil;
import com.yzj.jep.app.util.BatchNumberUtil;
import com.yzj.jep.app.util.IdCheckUtils;
import com.yzj.jep.app.wf.service.WorkflowOperation;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.*;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.sys.code.DutySource;
import com.yzj.jep.sys.model.Duty;
import com.yzj.jep.sys.model.Role;
import com.yzj.jep.sys.service.DutyService;
import com.yzj.jep.sys.service.NotifyService;
import com.yzj.jep.sys.service.UnitService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.util.RequestUtils;
import com.yzj.jep.workflow.impl.model.StaffImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/bus/relPsnSuspect")
public class RelPsnSuspectController extends CrudController<RelPsnSuspect> {
    private static Logger logger = LoggerFactory.getLogger(RelPsnSuspectController.class);


    @Autowired
    private RelPsnSuspectService relPsnSuspectService;
    @Autowired
    private RelPsnService relPsnService;

    @Autowired
    private RelOrgService relOrgService;
    @Autowired
    private DownSuspectService downSuspectService;

    @Autowired
    private UpSuspectService upSuspectService;
    @Autowired
    private RelService relService;

    @Autowired
    private RelFileService relFileService;

    @Autowired
    private AligMapService aligMapService;

    @Autowired
    private DutyService dutyService;

    @Autowired
    private NotifyService notifyService;

    @Autowired
    private UnitService unitService;

    @Autowired
    WorkflowOperation workflowOperation;


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


    @ResponseBody
    @RequestMapping(value = "/checkByPsn1/{pid}")
    public int list(@PathVariable Integer pid) {
        int data;
        String s = String.valueOf(pid);
        Rel rel = new Rel();
        rel.setFromPid(s);
        String fromPid = rel.getFromPid();
        List<String> rels = relService.selectByFormPid(fromPid);

        rel.setToPid(s);
        String toPid = rel.getToPid();
        List<String> rels1 = relService.selectByToPid(toPid);


        RelPsnSuspect relPsnSuspect = relPsnSuspectService.get(pid);

        /*关系是否有效*/

        if (rels.size() != 0 || rels1.size() != 0) {

            data = 1;
        } else {
            data = 2;
        }
        return data;
    }

    /*
     * 跳转到人工认定界面
     * */
    /*添加判断通过pid当作fromid和toid查询，并且判断Vail是否为1或0，1为关系有效*/
    @RequestMapping(value = "/checkByPsn/{pid}")
    public String list(Model model, @PathVariable Integer pid) {


        RelPsnSuspect relPsnSuspect = relPsnSuspectService.get(pid);

        model.addAttribute("relPsnSuspect", relPsnSuspect);
        model.addAttribute("opt", "edit");


        return "bus/relPsnSuspectCheck";
    }


    @RequestMapping(value = "/relPsnSend")
    public String relPsnSend(Model model) {

        return "bus/relPsnSuspectListSend";
    }


    /*
     * 确认下发
     * */
    @RequestMapping(value = "/taskSend")
    @ResponseBody
    public Map taskSend(String unitCode, String memo, String sendPsnId) {
        Map<String, Object> tips = new HashMap<String, Object>();
        //发放疑似自然人给指定机构补录
        List<Filter> filers = new Filter.Builder().eq("pid", sendPsnId).build();


        RelPsnSuspect relPsnSuspect = relPsnSuspectService.get(filers);
        //更换管库机构为下发机构
        relPsnSuspect.setMgerOrg(unitCode);
        //更新疑似自然人状态为待审批
        relPsnSuspect.setStatus("4");
        relPsnSuspect.setStatusComment(memo);
        try {
            //查出指定机构角色为业务岗的人并通知补录
            List<Duty> dutys = dutyService.select(new Filter.Builder()
                    .like("positionCode", "1110@" + unitCode)
                    .like("dutySource", DutySource.ROLE.toString())
                    .build());
            if (CollectionUtils.isNotEmpty(dutys)) {
                for (Duty duty : dutys) {
                    //通知补录人
                    /*创建提醒*/
                    notifyService.createRemind(duty.getStaffCode(), "自然人[" + relPsnSuspect.getName() + "]的补录任务已下发到您[" + duty.getStaffCode() + "]，请到自然人列表完成补录。", "自然人[" + relPsnSuspect.getName() + "]的补录任务已下发到您[" + duty.getStaffCode() + "]，请到自然人列表完成补录。");
                }
                relPsnSuspectService.update(relPsnSuspect);
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
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {
        //用戶角色查詢
        String staffCode = UserProfileUtils.getUserProfile().getStaffCode();
        String url = "bus/relPsnSuspect/list";
        AuthorityUtil authorityUtil = new AuthorityUtil();
        String code = authorityUtil.buttonPerm(staffCode, url);
        model.addAttribute("code", code);

        return "bus/relPsnSuspectList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<RelPsnSuspect> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        filters.add(new Filter("delflag", Operator.EQ, 1));
        orders.add(new Order("modifiedTime", OrderType.DESC));
        relPsnSuspectService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {
        UserProfile profile = UserProfileUtils.getUserProfile();
        String unitCode = profile.getUnitCode();
        RelPsnSuspect relPsnSuspect = new RelPsnSuspect();
        relPsnSuspect.setDataSource("1");
        model.addAttribute("relPsnSuspect",relPsnSuspect);
        model.addAttribute("opt", "add");
        model.addAttribute("unitCode", unitCode);
        return "bus/suspectRelPsnAddOrEdit";
    }

    /**
     * 编辑页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{pid}")
    public String edit(@PathVariable Integer pid, Model model, HttpServletRequest request) {
        //获取当前登录用户
        UserProfile profile = UserProfileUtils.getUserProfile();
        //获取人员的编号
        String unitCode = profile.getUnitCode();
        model.addAttribute("opt", "edit");
        model.addAttribute("unitCode", unitCode);
        List<Filter> filters = RequestUtils.buildFilters(request);
        filters.add(new Filter("pid", pid));
        filters.add(new Filter("deflag", 1));
        List<RelPsnSuspect> relPsnSuspects = relPsnSuspectService.select(filters);
        if (relPsnSuspects != null && relPsnSuspects.size() > 0) {
            model.addAttribute("relPsnSuspect", relPsnSuspects.get(0));
        }
        model.addAttribute("pid", pid);
        return "bus/suspectRelPsnAddOrEdit";
    }

    /*
     * 获取疑似关联人的信息
     * 含上级与下级信息
     * */
    @RequestMapping(value = "/psnAudit/{pid}")
    @ResponseBody
    public JSONObject subAudit(@PathVariable Integer pid) {
        JSONObject result = new JSONObject();
        RelPsnSuspect relPsnSuspect = relPsnSuspectService.get(pid);
        // 查询下级信息
        List<DownSuspect> downSuspectList = downSuspectService.getDownPsnInfo(pid);
        //查询上级信息
        List<UpSuspect> upSuspectList = upSuspectService.getUpPsnInfo(pid);
        if (downSuspectList.size() >= 0 || upSuspectList.size() >= 0) {
            relPsnSuspect.setStatus("2");
            relPsnSuspectService.updateSelective(relPsnSuspect);
        } else {
            relPsnSuspect.setStatus("1");
            relPsnSuspectService.updateSelective(relPsnSuspect);
        }
//        relPsnSuspect.setDownSuspectList(downSuspectList);
//        relPsnSuspect.setUpSuspectList(upSuspectList);
        result.put("relPsnSuspect", relPsnSuspect);
        result.put("downSuspectList", downSuspectList);
        result.put("upSuspectList", upSuspectList);
        return result;
    }


    /*
     * 获取疑似关联人的上级与下级信息
     * */
    @RequestMapping(value = "/queryUpPsnInfo/{pid}")
    @ResponseBody
    public Map<String, Object> queryUpPsnInfo(@PathVariable Integer pid, Page<UpSuspect> page) {
        Map<String, Object> tips = new HashMap<String, Object>();
        //查询上级信息
        List<UpSuspect> upSuspectList = upSuspectService.getUpPsnInfo(pid);
        page.setTotal(upSuspectList.size());
        int pages = (upSuspectList.size() + page.getPageSize() - 1) / page.getPageSize();
        if (page.getPageNumber() > pages) {
            page.setPageNumber(1);
        }
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        List<UpSuspect> upSuspects = new ArrayList<UpSuspect>();
        for (int i = index; i < index + page.getPageSize(); i++) {
            if (i < upSuspectList.size()) {
                upSuspects.add(upSuspectList.get(i));
            }
        }
        page.setResult(upSuspects);

        tips.put(RESULT_TOTAL, page.getTotal());
        tips.put(RESULT_ROWS, page.getResult());
        tips.put("upSuspectList", upSuspectList);
        return tips;
    }


    /*
     * 获取  关联方上级的信息
     * */
    @RequestMapping(value = "/queryUpInfo/{pid}/{toType}")
    @ResponseBody
    public Map<String, Object> queryUpInfo(@PathVariable Integer pid, @PathVariable String toType, Page<UpSuspect> page) {
        Map<String, Object> tips = new HashMap<String, Object>();
        //查询上级信息
        List<UpSuspect> upSuspectList = new ArrayList<>();
        if ("ORG".equals(toType)) {
            upSuspectList = upSuspectService.getUpInfoOnOrg(pid);
        } else if ("PSN".equals(toType)) {
            upSuspectList = upSuspectService.getUpInfoOnPsn(pid);
        }
        page.setTotal(upSuspectList.size());
        int pages = (upSuspectList.size() + page.getPageSize() - 1) / page.getPageSize();
        if (page.getPageNumber() > pages) {
            page.setPageNumber(1);
        }
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        List<UpSuspect> upSuspects = new ArrayList<UpSuspect>();
        for (int i = index; i < index + page.getPageSize(); i++) {
            if (i < upSuspectList.size()) {
                upSuspects.add(upSuspectList.get(i));
            }
        }
        page.setResult(upSuspects);

        tips.put(RESULT_TOTAL, page.getTotal());
        tips.put(RESULT_ROWS, page.getResult());
        tips.put("upSuspectList", upSuspectList);
        return tips;
    }


    /*
     * 获取疑似关联人的上级与下级信息
     * */
    @RequestMapping(value = "/queryDownPsnInfo/{pid}")
    @ResponseBody
    public Map<String, Object> queryDownPsnInfo(@PathVariable Integer pid, Page<DownSuspect> page) {
        Map<String, Object> tips = new HashMap<String, Object>();
        List<DownSuspect> downSuspectList = downSuspectService.getDownPsnInfo(pid);

        page.setTotal(downSuspectList.size());
        int pages = (downSuspectList.size() + page.getPageSize() - 1) / page.getPageSize();
        if (page.getPageNumber() > pages) {
            page.setPageNumber(1);
        }
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        List<DownSuspect> downSuspects = new ArrayList<DownSuspect>();
        for (int i = index; i < index + page.getPageSize(); i++) {
            if (i < downSuspectList.size()) {
                downSuspects.add(downSuspectList.get(i));
            }
        }
        page.setResult(downSuspects);

        tips.put(RESULT_TOTAL, page.getTotal());
        tips.put(RESULT_ROWS, page.getResult());
        tips.put("downSuspectList", downSuspectList);
        return tips;
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

        RelPsnSuspect relPsnSuspect = relPsnSuspectService.get(pid);
        model.addAttribute("relPsnSuspect", relPsnSuspect);
        model.addAttribute("opt", "view");
        return "bus/suspectRelPsnAddOrEditV";
//        return "bus/relPsnSuspectView";
    }


    /**
     * 保存
     *
     * @return
     * @Param relPsnSuspect
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(RelPsnSuspect relPsnSuspect, TraEnclosureVo traEnclosureVo) {
        Map<String, Object> tips = new HashMap<String, Object>();
        if (relPsnSuspect.getIdentifyType().equals("1")) {
            String identifyNumber = relPsnSuspect.getIdentifyNumber();
            boolean idNumCHK = IdCheckUtils.idNumCHK(identifyNumber);
            if (!idNumCHK) {
                tips.put(WebCode.SUCCESS, false);
                tips.put(WebCode.MSG, "身份证号码有误!请检查,重新填写");
                return tips;
            }
        }
        String relSerial = "";
        Boolean isExist = false;
        int index = 0;
        if (relPsnSuspect.getPid() != null && !"".equals(relPsnSuspect.getPid())) {
            isExist = relPsnSuspectService.exists(relPsnSuspect.getPid());
        }
        try {
            if (isExist) {

                index = relPsnSuspectService.updateSelective(relPsnSuspect);
                relSerial = relPsnSuspectService.get(relPsnSuspect.getPid()).getRelSerial();
            } else {
                List<RelPsnSuspect> relPsnList = relPsnSuspectService.select(relPsnSuspect);
                if (relPsnList.size() > 0) {
                    relPsnSuspect = relPsnList.get(0);
                } else {
                    List<RelPsnSuspect> relPsnSuspectList = relPsnSuspectService.select(relPsnSuspect);
                    if (relPsnSuspectList.size() > 0) {
                        relPsnSuspect = relPsnSuspectList.get(0);
                    } else {
                        relPsnSuspect.setDelflag("1");
                        relPsnSuspect.setIsAssoParty("0");
                        relPsnSuspect.setStatus("3");
                        relPsnSuspect.setIsBlack("0");
                        //relPsnSuspect.setDataSource("1");//数据来源为手工录入
                        relPsnSuspect.setRelSerial(BatchNumberUtil.makeBatchNumber());
                        index = relPsnSuspectService.insert(relPsnSuspect);
                    }
                    relSerial = relPsnSuspect.getRelSerial();
                }
            }
            List<Integer> docTableSelectItem = traEnclosureVo.getDocTableSelectItem();
            String merge = relPsnSuspect.getMgerOrg();
            relFileService.updateRelSerial(docTableSelectItem, relSerial, merge);
            if (index != 0) {
                List<RelPsnSuspect> relPsn1 = relPsnSuspectService.select(relPsnSuspect);
                tips.put("pid", relPsn1.get(0).getPid());
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
            relPsnSuspectService.deleteByPrimaryKey(pid);
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

        boolean exist = relPsnSuspectService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }

    /*
     * 自然人基本信息新增
     * */
    @RequestMapping(value = "/insertPsnMsg")
    @ResponseBody
    public Map<String, Object> insertPsnMsg(@RequestBody RelPsnSuspect relPsnSuspect) {
        Map<String, Object> result = relPsnSuspectService.insertPsnMsg(relPsnSuspect);
        return result;
    }

    /*
     * 自然人基本信息修改
     * */
    @RequestMapping(value = "/updatePsnMsg")
    @ResponseBody
    public Map<String, Object> updatePsnMsg(RelPsnSuspect relPsnSuspect) {
        Map<String, Object> result = null;
        if (relPsnSuspect.getPid() != null) {
            result = relPsnSuspectService.updatePsnMsg(relPsnSuspect);
        } else {
            result = relPsnSuspectService.insertPsnMsg(relPsnSuspect);
        }
        return result;
    }

    /*
     * 自然人附属信息保存
     * */
    @RequestMapping(value = "/saveSuspectAffiliate/{parentPid}/{fromPid}")
    @ResponseBody
    public Map<String, Object> insertPsgRelMsg(@PathVariable String parentPid, @PathVariable String fromPid, PersonRelationVo personRelationVo) {
        Map<String, Object> tips = new HashMap<String, Object>();
        //归属关联方
        RelPsn fromPsn = relPsnService.get(Integer.valueOf(parentPid));
        //新增关系to自然人
        RelPsnSuspect relPsn = relPsnSuspectService.get(Integer.valueOf(fromPid));
//        BeanUtils.copyProperties(relPsnVo, relPsn);
        String fromAlig = fromPsn.getAligCode();
        String chAligCode = relPsn.getAligCode();
        //新增关联方根据上级定为码以及关系判断是否关联方,更新to定为码
        if (StringUtils.isNotEmpty(fromAlig)) {
            //更新关系表
            Rel rel = new Rel();
            //归属关联方pid
            rel.setFromPid(parentPid);
            //添加基本信息自然人的pid
            rel.setToPid(fromPid);
            rel.setRelCategory(personRelationVo.getRelCategory());
            rel.setRelCategorySub(personRelationVo.getRelCategorySub());
            Rel dbRel = relService.get(rel);
            rel.setRelPredicate(personRelationVo.getPredicate());
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
                        .eq("relCategoryVal", personRelationVo.getRelCategory())
                        .eq("assoTypeVal", personRelationVo.getRelCategorySub())
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
                            relPsn.setAligCode(chAligCode + "," + toAligCode);
                        }
                    } else {
                        relPsn.setAligCode(toAligCode);
                    }
                    //关系设置定为码
                    rel.setFromAligCode(alig);
                    rel.setToAligCode(toAligCode);
                    rel.setRegulator(aligMap.getCaliber());
                    rel.setDelflag("1");
                    rel.setFromType("PSN");
                    rel.setToType("SUSPSN");
                    rel.setShareholdingRatio(personRelationVo.getShareholdingRatio());
                    //关联人设置口径
                    relPsn.setRegulatorOth1(aligMap.getCaliber());
                    relPsn.setStatus("1");
                } else {
                    //为null  代表to 不是关联方，仅仅维持关系即可
                    //关系设置定为码
                    rel.setFromAligCode(alig);
                    rel.setDelflag("1");
                    rel.setFromType("PSN");
                    rel.setToType("SUSPSN");
                    rel.setShareholdingRatio(personRelationVo.getShareholdingRatio());
                    //设置成待提交
                    relPsn.setStatus("3");
                }
                relPsnSuspectService.updateSelective(relPsn);
                //关系状态为待新增
                rel.setRelStatus("01");
                if (isExist) {
                    relService.updateSelective(rel);
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

//    /*
//    * 自然人附属信息修改
//    * */
//    @RequestMapping(value = "/updatePsgRelMsg")
//    @ResponseBody
//    public Map<String,Object> updatePsgRelMsg(@RequestBody PersonRelationVo personRelationVo){
//        Map<String, Object> result = relPsnSuspectService.updatePsgRelMsg(personRelationVo);
//        return result;
//    }

    /*
     * 自然人附属信息删除
     * */
    @RequestMapping(value = "/delAffiliate/{pidForRel}")
    @ResponseBody
    public Map<String, Object> delPsgRelMsg(@PathVariable Integer pidForRel) {
        // 删除疑似关联人
//        Map<String, Object> result = relPsnSuspectService.delPsgRelMsg(pidForRel);
        Map<String, Object> result = new HashMap<>();
        Rel rel = relService.get(pidForRel);
        rel.setDelflag("0");
        int res = relService.updateSelective(rel);
        if (res > 0) {
            result.put(WebCode.SUCCESS, true);
            result.put(WebCode.MSG, "删除成功");
        } else {
            result.put(WebCode.SUCCESS, false);
            result.put(WebCode.MSG, "删除失败");
        }
        return result;
    }

    /*
     * 自然人机构信息保存
     * */
    @RequestMapping(value = "/saveSuspectOrg/{paFromPid}/{fromPid}")
    @ResponseBody
    public Map<String, Object> insertPsgOrg(@PathVariable String paFromPid, @PathVariable String fromPid, PersonOrgVo personOrgVo) {
        Map tips = new HashMap();
        //归属关联方
        RelOrg fromOrg = relOrgService.get(Integer.valueOf(paFromPid));
        //新增关系to机构e
        RelPsnSuspect relPsn = relPsnSuspectService.get(Integer.valueOf(fromPid));
        String fromAlig = fromOrg.getAligCode();
        String chAligCode = relPsn.getAligCode();
        //新增关联方根据上级定为码以及关系判断是否关联方,更新to定为码
        if (StringUtils.isNotEmpty(fromAlig)) {
            //更新关系表
            Rel rel = new Rel();
            //归属关联方pid
            rel.setFromPid(paFromPid);
            //添加基本信息机构的pid
            rel.setToPid(fromPid);
            rel.setRelCategory(personOrgVo.getRelCategory());
            rel.setRelCategorySub(personOrgVo.getRelCategorySub());
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
                        .eq("relCategoryVal", personOrgVo.getRelCategory())
                        .eq("assoTypeVal", personOrgVo.getRelCategorySub())
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
                            relPsn.setAligCode(chAligCode + "," + toAligCode);
                        }
                    } else {
                        relPsn.setAligCode(toAligCode);
                    }
                    //关系设置定为码
                    rel.setFromAligCode(alig);
                    rel.setToAligCode(toAligCode);
                    rel.setRegulator(aligMap.getCaliber());
                    rel.setDelflag("1");
                    rel.setFromType("ORG");
                    rel.setToType("SUSPSN");
                    rel.setShareholdingRatio(personOrgVo.getShareholdingRatio());
                    //关联人设置口径
                    relPsn.setRegulatorOth1(aligMap.getCaliber());
                } else {
                    //关系设置定为码
                    rel.setFromAligCode(alig);
                    rel.setDelflag("1");
                    rel.setFromType("ORG");
                    rel.setToType("SUSPSN");
                    rel.setShareholdingRatio(personOrgVo.getShareholdingRatio());
                }
                relPsn.setStatus("3");
                relPsnSuspectService.update(relPsn);
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

//    /*
//    * 自然人机构信息修改
//    * */
//    @RequestMapping(value = "/updatePsgOrg")
//    @ResponseBody
//    public Map<String,Object> updatePsgOrg(@RequestBody PersonOrgVo personOrgVo){
//        Map<String, Object> result = relPsnSuspectService.updatePsgOrg(personOrgVo);
//        return result;
//    }

    /*
     * 自然人机构信息删除
     * */
    @RequestMapping(value = "/delPsnRelOrg/{pidForRel}")
    @ResponseBody
    public Map<String, Object> delPsgOrg(@PathVariable Integer pidForRel) {
        Map<String, Object> result = new HashMap<>();
        Rel rel = relService.get(pidForRel);
        rel.setDelflag("0");
        int res = relService.updateSelective(rel);
        if (res > 0) {
            result.put(WebCode.SUCCESS, true);
            result.put(WebCode.MSG, "删除成功");
        } else {
            result.put(WebCode.SUCCESS, false);
            result.put(WebCode.MSG, "删除失败");
        }
        return result;
    }


    /*
     * 自然人附属信息查询
     * */
    @RequestMapping(value = "/selectForMsg")
    @ResponseBody
    public List<PersonRelationVo> selectForMsg(@RequestParam Integer pid) {
        List<PersonRelationVo> personRelationVoList = relPsnSuspectService.queryPsnRelation(pid);
        return personRelationVoList;
    }

    /*
     * 自然人机构关系查询
     * */
    @RequestMapping(value = "/selectForOrg")
    @ResponseBody
    public List<PersonOrgVo> selectForOrg(@RequestParam Integer pid) {
        List<PersonOrgVo> personOrgVoList = relPsnSuspectService.queryPsnOrg(pid);
        return personOrgVoList;
    }

    /*
     * 自然人信息删除
     * */
    @RequestMapping(value = "/delPsnForFrom/{pidForForm}")
    @ResponseBody
    public Map<String, Object> delForPsn(@PathVariable Integer pidForForm) {
//        Map<String, Object> map = relPsnSuspectService.delPsn(pidForForm);
        Map<String, Object> map = new HashMap<>();
        relPsnSuspectService.deleteByPrimaryKey(pidForForm);
        map.put(WebCode.SUCCESS, true);
        map.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
        return map;
    }


    /*
     * 自然人基本信息查询
     * */
    @RequestMapping(value = "/selectForFormPsn")
    @ResponseBody
    public List<RelPsnSuspect> selectForFormPsn(@RequestParam Integer pid) {
        List<Filter> filterForPsnPid = new Filter.Builder().eq("pid", pid).eq("delflag", 1).build();
        List<RelPsnSuspect> relPsnSuspects = relPsnSuspectService.select(filterForPsnPid);
        return relPsnSuspects;
    }

    /**
     * 附属信息页面
     */
    @RequestMapping(value = "/suspectAffiliateList/{opt}/{pid}")
    public String affiliateView(@PathVariable String opt, @PathVariable String pid, Model model) {
        model.addAttribute("fromPid", pid);
        model.addAttribute("opt", opt);
        return "bus/suspectAffiliateList";
    }


    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/addForRelPsnAdd")
    public String addForRelPsnAdd(Model model) {
        model.addAttribute("opt", "add");
        return "bus/suspectRelPsnAddOrEdit";
    }


    /* 把疑似自然人的数据放到自然人里，并且删除这条数据*/
    @RequestMapping(value = "/savedata/{pid}")
    public String savedata(@PathVariable Integer pid) {

        UserProfile profile = UserProfileUtils.getUserProfile();
        List codes = unitService.selectUnitAllOrDeptAllCodes(profile.getUnitCode());

        System.out.println("codes = " + codes);

        int keypid = pid;
        RelPsn relPsn = new RelPsn();

        RelPsnSuspect relPsnSuspect = relPsnSuspectService.get(pid);
        relPsnSuspect.setMgerOrg("Z001");    //管户机构
        relPsnSuspect.setValidStatus("1");


        relPsnSuspect.setPid(0);

        BeanUtils.copyProperties(relPsnSuspect, relPsn);
        relPsn.setStatus("4");//待审批
        relPsn.setEffectRelation("0");//否存在有效关系
        relPsn.setAmendId(profile.getUserid());
        relPsn.setAmendName(profile.getUsername());

        //  List<RelPsn> relPsns = relPsnService.selectByname(name);
        List<Filter> filters = new Filter.Builder().eq("name", relPsn.getName()).eq("identifyType", relPsn.getIdentifyType()).like("identifyNumber", relPsn.getIdentifyNumber()).build();
        RelPsn relPsn1 = relPsnService.get(filters);
        if (relPsn1 == null) {
            int i = relPsnService.insert(relPsn);
            int i1 = relPsnSuspectService.deleteByPrimaryKey(keypid);
        }
        /*Integer pid1 = relPsn1.getPid();
        List<UpSuspect> upSuspectList = upSuspectService.getUpPsnInfo(pid1);

        Rel rel = new Rel();
        //关联自然人的pid
        String ToPid = String.valueOf(pid1);
        rel.setToPid(ToPid);            //关联自然人的pid 换成关联表的Topid
        for (UpSuspect upSuspect : upSuspectList) {
            Integer pid2 = upSuspect.getPid();     //疑似关联人的关联关系的id
            String FormPid = String.valueOf(pid2);
            rel.setFromPid(FormPid);   //疑似关联人的关联关系的id 换成关联关系的Frompid
            int j = relService.insert(rel);
        }*/

        return "bus/relPsnSuspectList";
    }


    /**
     * 疑似自然人  ---自然人
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/suspectAffiliateSelect/{pid}")
    @ResponseBody
    public Map<String, Object> affiliate(@PathVariable String pid, Model model, Page<PersonRelationVo> page) {

        List<PersonRelationVo> personRelationVoList = relPsnSuspectService.queryPsnRelation(new Integer(pid));
        Map<String, Object> result = new HashMap<String, Object>();
        page.setTotal(personRelationVoList.size());
        int pages = (personRelationVoList.size() + page.getPageSize() - 1) / page.getPageSize();
        if (page.getPageNumber() > pages) {
            page.setPageNumber(1);
        }
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        List<PersonRelationVo> personRelationVos = new ArrayList<PersonRelationVo>();
        for (int i = index; i < index + page.getPageSize(); i++) {
            if (i < personRelationVoList.size()) {
                personRelationVos.add(personRelationVoList.get(i));
            }
        }
        page.setResult(personRelationVos);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;
    }

    /**
     * 附属信息列表
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/suspectAffiliateSelectDown/{pid}")
    @ResponseBody
    public Map<String, Object> affiliateDown(@PathVariable String pid, Model model, Page<PersonRelationVo> page) {

        List<PersonRelationVo> personRelationVoList = relPsnSuspectService.queryPsnRelation(new Integer(pid));
        Map<String, Object> result = new HashMap<String, Object>();
        page.setTotal(personRelationVoList.size());
        int pages = (personRelationVoList.size() + page.getPageSize() - 1) / page.getPageSize();
        if (page.getPageNumber() > pages) {
            page.setPageNumber(1);
        }
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        List<PersonRelationVo> personRelationVos = new ArrayList<PersonRelationVo>();
        for (int i = index; i < index + page.getPageSize(); i++) {
            if (i < personRelationVoList.size()) {
                personRelationVos.add(personRelationVoList.get(i));
            }
        }
        page.setResult(personRelationVos);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;
    }


    /**
     * 新增自然人附属信息页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/suspectAffiliateAdd/{fromPid}")
    public String addAffiliate(@PathVariable Integer fromPid, Model model) {
        model.addAttribute("opt", "add");
        model.addAttribute("fromPid", fromPid);
        return "bus/suspectAffiliateAddOrEdit";
    }

    /**
     * 编辑自然人附属信息页面
     *
     * @param
     * @param model
     * @return
     */
    @RequestMapping(value = "/editAffiliate/{pidForPsnTo}/{pidForRel}/{pidForPsnFrom}")
    public String editAffiliate(@PathVariable Integer pidForPsnTo, @PathVariable Integer pidForRel, @PathVariable Integer pidForPsnFrom, Model model) {

        List<PersonRelationVo> personRelationVoList = relPsnSuspectService.queryPsnRelation(pidForPsnFrom);
        PersonRelationVo personRelationVoOne = new PersonRelationVo();
        for (PersonRelationVo personRelationVo : personRelationVoList) {
            if (pidForPsnTo.equals(personRelationVo.getPidForPsnTo()) && pidForPsnFrom.equals(personRelationVo.getPidForPsnFrom()) && pidForRel.equals(personRelationVo.getPidForRel())) {
                personRelationVoOne = personRelationVo;
            }
        }
        /*RelDictService relDictService = SpringContextHolder.getBean(RelDictService.class);
        List<Filter> filters = new Filter.Builder().eq("relCategorySub", personRelationVoOne.getRelCategorySub()).build();
        List<RelDict> relDictList = relDictService.select(filters);
        personRelationVoOne.setRelCategorySub(relDictList.get(0).getRelCategorySubCode());*/
        model.addAttribute("personRelationVo", personRelationVoOne);
        model.addAttribute("opt", "edit");
        model.addAttribute("pid", pidForPsnTo);
        model.addAttribute("relId", pidForRel);
        model.addAttribute("fromPid", pidForPsnFrom);

        return "bus/suspectAffiliateAddOrEdit";
    }

    /**
     * 机构关系信息页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/psnRelOrgList/{opt}/{pid}")
    public String rightsList(@PathVariable String opt, @PathVariable String pid, Model model) {
        model.addAttribute("fromPid", pid);
        model.addAttribute("opt", opt);
        return "bus/suspectPsnRelOrgList";
    }

    /**
     * 机构关系信息列表
     *
     * @param fromPid
     * @param model
     * @return
     */
    @RequestMapping(value = "/suspectPsnRelOrgSelect/{fromPid}")
    @ResponseBody
    public Map<String, Object> suspectPsnRelOrgSelect(@PathVariable String fromPid, Model model, Page<RelPsnVo> page) {
        Map<String, Object> result = new HashMap<String, Object>();
        PageHelper.start(page);
        List<PersonOrgVo> personOrgVoList = relPsnSuspectService.queryPsnOrg(new Integer(fromPid));

        PageInfo pageInfo = new PageInfo(personOrgVoList);
        result.put(RESULT_TOTAL, pageInfo.getTotal());
        result.put(RESULT_ROWS, pageInfo.getList());

        return result;
    }

    /**
     * 新增机构关系信息页面
     */
    @RequestMapping(value = "suspectAddPsnRelOrg/{fromPid}")
    public String suspectAddPsnRelOrg(Model model, @PathVariable Integer fromPid) {
        model.addAttribute("opt", "add");
        model.addAttribute("fromPid", fromPid);
        return "bus/suspectPsnRelOrgAddOrEdit";
    }

    /*
     * 编辑机构关系信息页面
     * */
    @RequestMapping(value = "/suspectEditPsnRelOrg/{pid}/{fromPid}/{relId}")
    public String suspectEditPsnRelOrg(Model model, @PathVariable Integer pid, @PathVariable Integer fromPid, @PathVariable Integer relId) {
        model.addAttribute("opt", "edit");
        model.addAttribute("fromPid", fromPid);
        List<PersonOrgVo> personOrgVoList = relPsnSuspectService.queryPsnOrg(fromPid);
        PersonOrgVo personOrgVoOne = new PersonOrgVo();
        if (personOrgVoList != null && personOrgVoList.size() > 0) {
            for (PersonOrgVo personOrgVo : personOrgVoList) {
                if (pid.equals(personOrgVo.getPidForOrgTo()) && fromPid.equals(personOrgVo.getPidForOrgFrom()) && relId.equals(personOrgVo.getPidForRel())) {
                    personOrgVoOne = personOrgVo;
                }
            }
        }
        RelDictService relDictService = SpringContextHolder.getBean(RelDictService.class);
        List<Filter> filters = new Filter.Builder().eq("relCategorySub", personOrgVoOne.getRelCategorySub()).build();
        List<RelDict> relDictList = relDictService.select(filters);
        personOrgVoOne.setRelCategorySub(relDictList.get(0).getRelCategorySubCode());
        model.addAttribute("personOrgVo", personOrgVoOne);
        return "bus/suspectPsnRelOrgAddOrEdit";
    }

    /**
     * 根据父级关系查询子关系下拉选项
     *
     * @return
     * @throws Exception
     */
//    @RequestMapping(value = "/queryRelCategorySub")
//    @ResponseBody
//    public List<NameValuePair> getDictItems(String relCategory, String type) throws Exception {
//        List<NameValuePair> items = new ArrayList<>();
//        try {
//
//            List<Filter> filters = new Filter.Builder().eq("dictCode", "BUS-Rel").build();
//            List<DictItem> list = dictItemService.select(filters);
//            String includes = "";
//            //添加关联方为人
//            if ("p".equals(type)) {
//                //亲属关系
//                if ("A".equals(relCategory)) {
//                    includes = "1,2,3,4";
//                }//工作关系
//                if ("B".equals(relCategory)) {
//                    includes = "5,6,7,8";
//                }//其他关系
//                else if ("E".equals(relCategory)) {
//                    includes = "21";
//                }
//            }//添加关联方为机构
//            else if ("o".equals(type)) {
//                //工作关系
//                if ("B".equals(relCategory)) {
//                    includes = "5,6,7,8";
//                }//投资关系
//                else if ("C".equals(relCategory)) {
//                    includes = "9,10";
//                }//被投资关系
//                else if ("E".equals(relCategory)) {
//                    includes = "17,18,19,20";
//                }//其他关系
//                else if ("E".equals(relCategory)) {
//                    includes = "22";
//                }
//            }
//            String[] includesArr = includes.split(",");
//            List<String> includesList = Arrays.asList(includesArr);
//            //过滤掉不要的字典项
//            if (!org.springframework.util.CollectionUtils.isEmpty(list)) {
//                for (int i = 0; i < list.size(); i++) {
//                    DictItem item = list.get(i);
//                    if (org.springframework.util.StringUtils.hasText(includes) && includesList.contains(item.getItemValue())) {
//                        BasicNameValuePair nv = new BasicNameValuePair(item.getItemLabel(), item.getItemValue());
//                        items.add(nv);
//                    }
//                }
//            }
//
//            return items;
//        } catch (ServiceException se) {
//            logger.error("系统出现异常：" + se.getMessage());
//            throw se;
//        }
//    }



    /*
     * 提交审批(集中审议)
     * */
    @RequestMapping(value = "/send/{pid}")
    @ResponseBody
    public Map<String, Object> sendjz(@PathVariable Integer pid, Model model) throws Exception {
        List<TraFunds> lli = new ArrayList<>();
        Map<String, Object> tips = new HashMap<String, Object>();
        /*List<String> li= Arrays.asList(pid.split(","));*/
        //获取当前登录用户
        UserProfile profile = UserProfileUtils.getUserProfile();
        //String staffCode = profile.getStaffCode();
        String procInstId = "";

        StaffImpl staff = new StaffImpl();
        BeanUtils.copyProperties(profile, staff);

        RelPsnSuspect relPsnSuspect = relPsnSuspectService.get(pid);
        if (StringUtils.isNotEmpty(relPsnSuspect.getProcInstId())) {
            procInstId = relPsnSuspect.getProcInstId();
        }

        com.alibaba.fastjson.JSONObject data = com.alibaba.fastjson.JSONObject.parseObject(JSONObject.toJSONString(relPsnSuspect));
          /*  if(org.apache.commons.lang.StringUtils.isNotBlank(funds.getProcinstId())){
                procInstId = funds.getProcinstId();
            }*/
        data.put("type", "PSN");
        data.put("startStaffCode", staff.getStaffCode());
        String procDefCode = null;
        procDefCode = subjectName.get(profile.getTenantId()) + "YSGLFSPLC";
        data.put("procDefCode", procDefCode);
        try {
            workflowOperation.sendSubmit(pid, procDefCode, staff, null, data.toJSONString(), "PSN", procInstId);
        } catch (Exception e) {
            logger.info("流程启动失败，失败数据类型:疑似自然人---" + "pid" + pid, e);
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
            return tips;
        }


        tips.put("data", lli);
        tips.put(WebCode.SUCCESS, true);
        tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        return tips;
    }
}