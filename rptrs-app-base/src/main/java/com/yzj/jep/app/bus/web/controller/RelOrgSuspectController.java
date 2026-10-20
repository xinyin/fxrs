/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年03月23日 14:26:10
 *
 */

package com.yzj.jep.app.bus.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.model.suspect.UpSuspect;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.app.bus.vo.OrgToOrgVo;
import com.yzj.jep.app.bus.vo.OrgToPsnVo;
import com.yzj.jep.app.ins.model.TraFunds;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.AuthorityUtil;
import com.yzj.jep.app.util.BatchNumberUtil;
import com.yzj.jep.app.wf.service.WorkflowOperation;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.*;
import com.yzj.jep.orm.mybatis.util.StringUtil;
import com.yzj.jep.sys.code.DutySource;
import com.yzj.jep.sys.model.Duty;
import com.yzj.jep.sys.model.Role;
import com.yzj.jep.sys.service.DutyService;
import com.yzj.jep.sys.service.NotifyService;
import com.yzj.jep.sys.service.UnitService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.workflow.impl.model.StaffImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/bus/relOrgSuspect")
public class RelOrgSuspectController extends CrudController<RelOrgSuspect> {
    private static Logger logger = LoggerFactory.getLogger(RelOrgSuspectController.class);

    @Autowired
    private RelOrgSuspectService relOrgSuspectService;
    @Autowired
    WorkflowOperation workflowOperation;
    @Autowired
    private OrgSuspectService orgSuspectService;

    @Autowired
    private RelPsnSuspectService relPsnSuspectService;

    @Autowired
    private RelSuspectService relSuspectService;

    @Autowired
    private RelPsnService relPsnService;

    @Autowired
    private RelOrgService relOrgService;

    @Autowired
    private RelService relService;

    @Autowired
    private AligMapService aligMapService;

    @Autowired
    private UpSuspectService upSuspectService;

    @Autowired
    private RelFileService relFileService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private DutyService dutyService;

    @Autowired
    private NotifyService notifyService;


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
    public String list(Model model) {
        //用戶角色查詢
        String staffCode = UserProfileUtils.getUserProfile().getStaffCode();
        String url = "bus/relOrgSuspect/list";
        AuthorityUtil authorityUtil = new AuthorityUtil();
        String code = authorityUtil.buttonPerm(staffCode, url);
        model.addAttribute("code", code);
        return "bus/relOrgSuspectList";
    }

    /*
     * 任务下发
     * */
    @RequestMapping(value = "/relOrgSuspectSend")
    public String relOrgSend(Model model) {

        return "bus/relOrgSuspectSend";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<RelOrgSuspect> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        UserProfile profile = UserProfileUtils.getUserProfile();
        List codes = unitService.selectUnitAllOrDeptAllCodes(profile.getUnitCode());
//        filters.add(new Filter("mgerOrg", Operator.IN, codes));//暂时注掉,不区分部门
        filters.add(new Filter("delflag", 1));
//        orders.add(new Order("modifiedTime","desc"));
        orders.add(new Order("modifiedTime", OrderType.DESC));
        relOrgSuspectService.query(page, filters, orders);
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
        RelOrgSuspect relOrgSuspect = relOrgSuspectService.get(filers);
        //更换管库机构为下发机构
        relOrgSuspect.setMgerOrg(unitCode);
        //更新疑似机构状态为待补录
        relOrgSuspect.setApprovalStatus("2");
        relOrgSuspect.setStatusComment(memo);
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
                    notifyService.createRemind(duty.getStaffCode(), "疑似机构[" + relOrgSuspect.getOrgName() + "]的补录任务已下发到您[" + duty.getStaffCode() + "]，请到疑似机构列表完成补录。", "疑似机构[" + relOrgSuspect.getOrgName() + "]的补录任务已下发到您[" + duty.getStaffCode() + "]，请到疑似机构列表完成补录。");
                }
                relOrgSuspectService.update(relOrgSuspect);
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
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {
        UserProfile profile = UserProfileUtils.getUserProfile();
        String unitCode = profile.getUnitCode();
        model.addAttribute("unitCode", unitCode);
        return "bus/relOrgSuspectEdit";
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
        UserProfile profile = UserProfileUtils.getUserProfile();
        String unitCode = profile.getUnitCode();
        model.addAttribute("unitCode", unitCode);
        RelOrgSuspect relOrgSuspect = relOrgSuspectService.get(pid);
        model.addAttribute("relOrgSuspect", relOrgSuspect);
        model.addAttribute("opt", "edit");

        return "bus/relOrgSuspectEdit";
    }

    /*
     * 人工认定
     * */

    @RequestMapping(value = "/checkByOrg/{pid}")
    public String list(Model model, @PathVariable Integer pid) {

        RelOrgSuspect relOrgSuspect = relOrgSuspectService.get(pid);
        model.addAttribute("relOrgSuspect", relOrgSuspect);
        model.addAttribute("opt", "edit");
        return "bus/relOrgSuspectCheck";
    }

    /*
     * 获取疑似关联人的信息
     * 含上级与下级信息
     * */
    @RequestMapping(value = "/orgAudit/{pid}")
    @ResponseBody
    public JSONObject subAudit(@PathVariable Integer pid) {
        JSONObject result = new JSONObject();
        RelOrgSuspect relOrgSuspect = relOrgSuspectService.get(pid);
        //查询上级信息
        List<UpSuspect> upSuspectList = upSuspectService.getUpOrgInfo(pid);
        List<UpSuspect> upSuspectPantList = upSuspectService.getUpPsnInfo(pid);
        if (upSuspectList.size() > 0 || upSuspectPantList.size() > 0) {
            relOrgSuspect.setApprovalStatus("2");
            relOrgSuspectService.updateSelective(relOrgSuspect);
        } else {
            relOrgSuspect.setApprovalStatus("1");
            relOrgSuspectService.updateSelective(relOrgSuspect);
        }

        result.put("relOrgSuspect", relOrgSuspect);
        result.put("upSuspectList", upSuspectList);
        result.put("upSuspectPantList", upSuspectPantList);
        return result;
    }

    /*
     * 获取疑似关联人的上级与下级信息
     * */
    @RequestMapping(value = "/queryUpOrgInfo/{pid}")
    @ResponseBody
    public Map<String, Object> queryUpPsnInfo(@PathVariable Integer pid, Page<UpSuspect> page) {
        Map<String, Object> tips = new HashMap<String, Object>();
        //查询上级信息
        List<UpSuspect> upSuspectList = upSuspectService.getUpOrgInfo(pid);
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


    /**
     * 查看基本信息页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{pid}")
    public String view(@PathVariable Integer pid, Model model) {
        //疑似机构  --基本信息
        UserProfile profile = UserProfileUtils.getUserProfile();
        String unitCode = profile.getUnitCode();
        model.addAttribute("unitCode", unitCode);

        RelOrgSuspect relOrgSuspect = relOrgSuspectService.get(pid);
        model.addAttribute("relOrgSuspect", relOrgSuspect);
        model.addAttribute("opt", "view");
        return "bus/relOrgSuspectEditV";
    }

    /*
     * 机构关联方关系自然人信息
     * */
    @RequestMapping(value = "/viewSub/{pid}")
    public String viewSub(@PathVariable Integer pid, Model model) {
        model.addAttribute("pid", pid);
        return "bus/relOrgSubSustView";
    }

    /*
     * 跳转到编辑附属页面  舍弃！
     * */
//    @RequestMapping(value = "/editSub/{pid}")
//    public String editSub(@PathVariable Integer pid, Model model) {
//        model.addAttribute("pid", pid);
//        return "bus/relOrgEditSubSustView";
//    }


    /**
     * 机构关联方关系自然人信息数据准备
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/viewSubInfo/{pid}")
    @ResponseBody
    public Map<String, Object> viewSubInfo(@PathVariable Integer pid, Model model, Page<OrgSuspect> page) {
        //疑似机构  --附属-员工信息
        List<OrgSuspect> relPsnSuspectList = orgSuspectService.queryOrgSubInfo(pid);
        Map<String, Object> tips = new HashMap<String, Object>();
        tips.put("relOrgSubInfo", relPsnSuspectList);

        page.setTotal(relPsnSuspectList.size());
        int pages = (relPsnSuspectList.size() + page.getPageSize() - 1) / page.getPageSize();
        if (page.getPageNumber() > pages) {
            page.setPageNumber(1);
        }
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        List<OrgSuspect> relPsnVos = new ArrayList<OrgSuspect>();
        for (int i = index; i < index + page.getPageSize(); i++) {
            if (i < relPsnSuspectList.size()) {
                relPsnVos.add(relPsnSuspectList.get(i));
            }
        }
        page.setResult(relPsnVos);
        tips.put(RESULT_TOTAL, page.getTotal());
        tips.put(RESULT_ROWS, page.getResult());

        tips.put("pid", pid);
        return tips;
    }

    /*
     * 跳转到  机构关系信息页面
     * */
    @RequestMapping(value = "/viewHolder/{pid}")
    public String viewHolder(@PathVariable Integer pid, Model model) {
        model.addAttribute("pid", pid);
        return "bus/relOrgSusHoldList";
    }

    /*
     * 跳转到权益信息  编辑页面
     * */
    @RequestMapping(value = "/editHolder/{pid}")
    public String editHolder(@PathVariable Integer pid, Model model) {
        model.addAttribute("pid", pid);
        return "bus/relOrgEditSusHoldList";
    }


    /**
     * 查看机构 --权益信息  投资页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/viewShareInfo/{pid}")
    @ResponseBody
    public Map<String, Object> viewShareInfo(@PathVariable Integer pid, Model model, Page<OrgSuspect> page) {
        Map<String, Object> tips = new HashMap<String, Object>();
        //疑似机构  --权益信息
        //2 投资信息
        List<OrgSuspect> relPsnInvestList = orgSuspectService.getOrgInvest(pid);
        tips.put("relPsnInvest", relPsnInvestList);

        page.setTotal(relPsnInvestList.size());
        int pages = (relPsnInvestList.size() + page.getPageSize() - 1) / page.getPageSize();
        if (page.getPageNumber() > pages) {
            page.setPageNumber(1);
        }
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        List<OrgSuspect> relPsnVos = new ArrayList<OrgSuspect>();
        for (int i = index; i < index + page.getPageSize(); i++) {
            if (i < relPsnInvestList.size()) {
                relPsnVos.add(relPsnInvestList.get(i));
            }
        }
        page.setResult(relPsnVos);
        tips.put(RESULT_TOTAL, page.getTotal());
        tips.put(RESULT_ROWS, page.getResult());

        tips.put("pid", pid);
        return tips;
//		//疑似机构  --权益信息
//		Map<String, Object> tips = new HashMap<String, Object>();
//		//1 股东信息
//		List<OrgSuspect> relPsnShareList=orgSuspectService.getOrgShareOrg(pid);
//		tips.put("relPsnShare",relPsnShareList);
//
//		page.setTotal(relPsnShareList.size());
//		int pages = (relPsnShareList.size() + page.getPageSize() - 1) / page.getPageSize();
//		if (page.getPageNumber() > pages) {
//			page.setPageNumber(1);
//		}
//		page.setPages(pages);
//		int index = (page.getPageNumber() - 1) * page.getPageSize();
//		List<OrgSuspect> relPsnVos = new ArrayList<OrgSuspect>();
//		for (int i = index; i < index + page.getPageSize(); i++) {
//			if (i < relPsnShareList.size()) {
//				relPsnVos.add(relPsnShareList.get(i));
//			}
//		}
//		page.setResult(relPsnVos);
//		tips.put(RESULT_TOTAL, page.getTotal());
//		tips.put(RESULT_ROWS, page.getResult());
//
//		tips.put("pid",pid);
//		return tips;
    }


    /**
     * 查看机构 --权益信息  投资方页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/viewInvestInfo/{pid}")
    @ResponseBody
    public Map<String, Object> viewInvestInfo(@PathVariable Integer pid, Model model, Page<OrgSuspect> page) {
        Map<String, Object> tips = new HashMap<String, Object>();
        //疑似机构  --权益信息
        //2 投资信息
        List<OrgSuspect> relPsnInvestList = orgSuspectService.getOrgInvest(pid);
        tips.put("relPsnInvest", relPsnInvestList);

        page.setTotal(relPsnInvestList.size());
        int pages = (relPsnInvestList.size() + page.getPageSize() - 1) / page.getPageSize();
        if (page.getPageNumber() > pages) {
            page.setPageNumber(1);
        }
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        List<OrgSuspect> relPsnVos = new ArrayList<OrgSuspect>();
        for (int i = index; i < index + page.getPageSize(); i++) {
            if (i < relPsnInvestList.size()) {
                relPsnVos.add(relPsnInvestList.get(i));
            }
        }
        page.setResult(relPsnVos);
        tips.put(RESULT_TOTAL, page.getTotal());
        tips.put(RESULT_ROWS, page.getResult());

        tips.put("pid", pid);
        return tips;
    }

    /**
     * 保存
     *
     * @param relOrgSuspect
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(RelOrgSuspect relOrgSuspect, TraEnclosureVo traEnclosureVo) {
        Map<String, Object> tips = new HashMap<String, Object>();
        Boolean isExist = false;
        String relSerial = "";
        Integer index = 0;
        //根据部门存储数据权限
        UserProfile profile = UserProfileUtils.getUserProfile();
        relOrgSuspect.setMgerOrg(profile.getUnitCode());
        if (relOrgSuspect.getPid() != null) {
            isExist = relOrgSuspectService.exists(relOrgSuspect.getPid());
        }
        try {
            if (isExist) {
                index = relOrgSuspectService.updateSelective(relOrgSuspect);
                relSerial = relOrgSuspectService.get(relOrgSuspect.getPid()).getRelSerial();
            } else {
                List<RelOrgSuspect> relOrgSuspectList = relOrgSuspectService.select(relOrgSuspect);
                if (relOrgSuspectList.size() > 0) {
                    relOrgSuspect = relOrgSuspectList.get(0);
                } else {
                    //审批状态为待补录
                    relOrgSuspect.setIsBlack("0");//是否为黑名单 “0”否
                    relOrgSuspect.setDataSource("1");//数据来源为手工录入
                    relOrgSuspect.setApprovalStatus("3");
                    relOrgSuspect.setDelflag("1");
                    relOrgSuspect.setIsAssoParty("0");
                    relOrgSuspect.setRelSerial(BatchNumberUtil.makeBatchNumber());
                    index = relOrgSuspectService.insert(relOrgSuspect);
                    relOrgSuspect = relOrgSuspectService.select(relOrgSuspect).get(0);
                    Rel rel = new Rel();
                    rel.setToPid(String.valueOf(relOrgSuspect.getPid()));
                }
                relSerial = relOrgSuspect.getRelSerial();
            }
            List<Integer> docTableSelectItem = traEnclosureVo.getDocTableSelectItem();
            String merge = relOrgSuspect.getMgerOrg();
            relFileService.updateRelSerial(docTableSelectItem, relSerial, merge);
            if (index != 0) {
                List<RelOrgSuspect> relorg = relOrgSuspectService.select(relOrgSuspect);
                tips.put("pid", relorg.get(0).getPid());
            } else {
                tips.put("pid", relOrgSuspect.getPid());
            }
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
//            tips.put(WebCode.MSG, WebCode.Is_SET_REL);
//            if (relOrgSuspect != null) {
//                tips.put("orgid", relOrgSuspect.getPid());
//            }
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        }
        return tips;
//        UserProfile profile = UserProfileUtils.getUserProfile();
//        Map<String, Object> tips = new HashMap<String, Object>();
//        String relSerial = "";
//        Boolean isExist = relOrgSuspectService.exists(relOrgSuspect.getPid());
//        try {
//            if (isExist) {
//                relOrgSuspectService.updateSelective(relOrgSuspect);
//                relSerial = relOrgSuspectService.get(relOrgSuspect.getPid()).getRelSerial();
//            } else {
//                List<RelOrgSuspect> relOrgSuspectList = relOrgSuspectService.select(relOrgSuspect);
//                if(relOrgSuspectList.size()>0){
//                    relOrgSuspect = relOrgSuspectList.get(0);
//                }else {
//                    //默认设置成待审批
//                    relOrgSuspect.setApprovalStatus("1");
//                    // 获取表的最大id值
//                    Integer maxPid = relOrgSuspectService.getMaxId("bus_rel_org_suspect");
//                    relOrgSuspect.setPid(maxPid + 1);
//                    relOrgSuspect.setDelflag("1");
//                    relOrgSuspect.setStatus("3");
//                    relOrgSuspect.setIsBlack("0");
//                    relOrgSuspect.setIdentifyType("UNIFIEDSOCIALCREDITCODE");
//                    relOrgSuspect.setRelSerial(BatchNumberUtil.makeBatchNumber());
//                    relOrgSuspect.setMgerOrg(profile.getDeptCode());
//                    relOrgSuspectService.insertSelective(relOrgSuspect);
//                }
//                relSerial = relOrgSuspect.getRelSerial();
//            }
//            List<Integer> docTableSelectItem = traEnclosureVo.getDocTableSelectItem();
//            String merge=relOrgSuspect.getMgerOrg();
//            relFileService.updateRelSerial(docTableSelectItem,relSerial,merge);
//            tips.put(WebCode.SUCCESS, true);
////            tips.put(WebCode.MSG, WebCode.Is_SET_REL);
//            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
//            tips.put("fromPid", relOrgSuspect.getPid());
//        } catch (ServiceException e) {
//            logger.info(e.getMessage());
//            tips.put(WebCode.SUCCESS, false);
//            tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
//        }
//        return tips;
    }

    /* 把疑似法人机构的数据放到法人机构里，并且删除这条数据*/
    @RequestMapping(value = "/savedata/{pid}")
    public String savedata(@PathVariable Integer pid) {

        UserProfile profile = UserProfileUtils.getUserProfile();
        List codes = unitService.selectUnitAllOrDeptAllCodes(profile.getUnitCode());
        System.out.println("codes = " + codes);
        int keypid = pid;
        RelOrg relOrg = new RelOrg();

        RelOrgSuspect relOrgSuspect = relOrgSuspectService.get(pid);
        relOrgSuspect.setMgerOrg(profile.getUnitCode());    //管户机构
        relOrgSuspect.setValidStatus("1");

        relOrgSuspect.setPid(0);

        BeanUtils.copyProperties(relOrgSuspect, relOrg);
        relOrg.setStatus("1");//待审批
        relOrg.setEffectRelation("0");//否存在有效关系
        relOrg.setAmendId(profile.getUserid());
        relOrg.setAmendName(profile.getUsername());
        //查询是否存在相同的
        List<Filter> filters = new Filter.Builder().eq("orgName", relOrg.getOrgName()).eq("identifyType", relOrg.getIdentifyType()).like("uniscCode", relOrg.getUniscCode()).build();
        RelOrg relOrg1 = relOrgService.get(filters);
        if (relOrg1 == null) {
            int i = relOrgService.insert(relOrg);
            int i1 = relOrgSuspectService.deleteByPrimaryKey(keypid);
        }

        /*Integer pid1 = relOrg1.getPid();
        List<UpSuspect> upSuspectList = upSuspectService.getUpOrgInfo(pid1);

        Rel rel = new Rel();
        //关联法人的pid
        String ToPid = String.valueOf(pid1);
        rel.setToPid(ToPid);            //关联法人的pid 换成关联表的Topid
        for (UpSuspect upSuspect : upSuspectList) {
            Integer pid2 = upSuspect.getPid();     //疑似法人的关联关系的id
            String FormPid = String.valueOf(pid2);
            rel.setFromPid(FormPid);   //疑似法人的关联关系的id 换成关联关系的Frompid
            int j = relService.insert(rel);
        }*/

        return "bus/relOrgSuspectList";
    }

    /**
     * 删除关系自然人信息
     *
     * @param pid
     * @return
     */
    @RequestMapping(value = "/remove/{pid}")
    @ResponseBody
    public Map<String, Object> remove(@PathVariable Integer pid) {

        Map<String, Object> tips = new HashMap<String, Object>();
//        Rel rel = relService.get(pid);
//        rel.setDelflag("0");
//        int res = relService.delete(rel);
//        int res=relService.updateSelective(rel);
        int res = relOrgSuspectService.deleteByPrimaryKey(pid);
        if (res > 0) {
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, "删除成功");
        } else {
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, "删除失败");
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

        boolean exist = relOrgSuspectService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }


    /*
     * 新增关系自然人信息
     * */
    @RequestMapping(value = "/relPsnAdd/{pid}")
    public String relPsnAdd(@PathVariable Integer pid, Model model) {
        /*
         * Pid  源pid
         * */
        OrgToPsnVo orgToPsnVo = new OrgToPsnVo();
        orgToPsnVo.setFromPid(pid.toString());
        model.addAttribute("relPsnSuspect", orgToPsnVo);
        model.addAttribute("opt", "add");
        return "bus/orgToPsnEditOrAdd";
    }

    /*
     * 关系自然人信息编辑页面
     * */
    @RequestMapping(value = "/relPsnEdit/{relPid}")
    public String relPsnEdit(@PathVariable Integer relPid, Model model) {
        /*
         * relPid  关联关系表中的pid
         * 获取关联关系信息
         * */
        Rel rel = relService.get(relPid);
//        RelSuspect relSuspect = relSuspectService.get(relPid);
        /*
         * psnPid  自然人的pid  就是to_pid
         * 获取自然人信息
         * */
        String psnPid = rel.getFromPid();
//        String psnPid = relSuspect.getToPid();
        RelPsn relPsn = relPsnService.get(Integer.parseInt(psnPid));
//        RelPsnSuspect relPsnSuspect = relPsnSuspectService.get(Integer.parseInt(psnPid));


        /*
         * 二者数据结合到vo
         * */
        OrgToPsnVo orgToPsnVo = new OrgToPsnVo();
        BeanUtils.copyProperties(relPsn, orgToPsnVo);
        //此处机构id以to_pid为准
        BeanUtils.copyProperties(rel, orgToPsnVo);

        orgToPsnVo.setRelPid(rel.getPid());

        model.addAttribute("relPsnSuspect", orgToPsnVo);
        // 存储源pid
//        model.addAttribute("pid", Integer.parseInt(relSuspect.getFromPid()));
        model.addAttribute("opt", "edit");
        return "bus/orgToPsnEditOrAdd";
    }

    /*
     * 关系自然人信息详情页面
     * */
    @RequestMapping(value = "/relPsnEdits/{relPid}")
    public String relPsnEdits(@PathVariable Integer relPid, Model model) {
        /*
         * relPid  关联关系表中的pid
         * 获取关联关系信息
         * */
        Rel rel = relService.get(relPid);
//        RelSuspect relSuspect = relSuspectService.get(relPid);
        /*
         * psnPid  自然人的pid  就是to_pid
         * 获取自然人信息
         * */
        String psnPid = rel.getFromPid();
//        String psnPid = relSuspect.getToPid();
        RelPsn relPsn = relPsnService.get(Integer.parseInt(psnPid));
//        RelPsnSuspect relPsnSuspect = relPsnSuspectService.get(Integer.parseInt(psnPid));


        /*
         * 二者数据结合到vo
         * */
        OrgToPsnVo orgToPsnVo = new OrgToPsnVo();
        BeanUtils.copyProperties(relPsn, orgToPsnVo);
        //此处机构id以to_pid为准
        BeanUtils.copyProperties(rel, orgToPsnVo);

        orgToPsnVo.setRelPid(rel.getPid());

        model.addAttribute("relPsnSuspect", orgToPsnVo);
        // 存储源pid
//        model.addAttribute("pid", Integer.parseInt(relSuspect.getFromPid()));
        model.addAttribute("opt", "edit");
        return "bus/orgToPsnEditView";
    }

    /*
     * 保存或者编辑自然人关系
     * */
    @RequestMapping(value = "/relPsnSave/{parentPid}/{fromPid}")
    @ResponseBody
    public Map<String, Object> relPsnSave(@PathVariable String parentPid, @PathVariable String fromPid, OrgToPsnVo orgToPsnVo) {
        Map<String, Object> tips = new HashMap<String, Object>();
        //归属关联方
        RelPsn fromPsn = relPsnService.get(Integer.valueOf(parentPid));
        //新增关系to机构
        RelOrgSuspect relOrg = relOrgSuspectService.get(Integer.valueOf(fromPid));
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
            rel.setRelCategory(orgToPsnVo.getRelCategory());
            rel.setRelCategorySub(orgToPsnVo.getRelCategorySub());
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
                        .eq("relCategoryVal", orgToPsnVo.getRelCategory())
                        .eq("assoTypeVal", orgToPsnVo.getRelCategorySub())
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
                    //关系设置定为码
                    rel.setFromAligCode(alig);
                    rel.setToAligCode(toAligCode);
                    rel.setRegulator(aligMap.getCaliber());
                    rel.setDelflag("1");
                    rel.setFromType("PSN");
                    rel.setToType("SUSORG");
                    rel.setShareholdingRatio(orgToPsnVo.getShareholdingRatio());
                    //关联人设置口径
                    relOrg.setRegulatorOth1(aligMap.getCaliber());
                    //设置成待审批
                    relOrg.setApprovalStatus("3");
//                    relOrg.setStatus("3");
                } else {
                    //为null  代表to 不是关联方，仅仅维持关系即可
                    //关系设置定为码
                    rel.setFromAligCode(alig);
                    rel.setDelflag("1");
                    rel.setFromType("PSN");
                    rel.setToType("SUSORG");
                    rel.setShareholdingRatio(orgToPsnVo.getShareholdingRatio());
                    //设置成待审批
                    relOrg.setApprovalStatus("3");
//                    relOrg.setStatus("3");
                }
                relOrgSuspectService.update(relOrg);
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
//        Map<String, Object> tips = new HashMap<String, Object>();
//        /*
//         * 根据是否有自然人id校验
//         * */
//        RelPsnSuspect relPsnSuspect =new RelPsnSuspect();
//        RelSuspect relSuspect = new RelSuspect();
//        Boolean isExist = false;
//        if (StringUtil.isNotEmpty(orgToPsnVo.getToPid())) {
//            isExist = relPsnSuspectService.exists(Integer.parseInt(orgToPsnVo.getToPid()));
//        }
//        BeanUtils.copyProperties(orgToPsnVo, relPsnSuspect);
//        BeanUtils.copyProperties(orgToPsnVo, relSuspect);
//        try {
//            if (isExist) {
//                //将机构的id信息进行修改
//                relPsnSuspect.setPid(Integer.parseInt(orgToPsnVo.getToPid()));
//                relPsnSuspectService.updateSelective(relPsnSuspect);
//
//                relSuspect.setPid(orgToPsnVo.getRelPid());
//                relSuspectService.updateSelective(relSuspect);
//            } else {
////				// 获取  机构表的最大id值
//                Integer maxPid = relOrgSuspectService.getMaxId("bus_rel_psn_suspect");
//                relPsnSuspect.setPid(maxPid + 1);
//                relPsnSuspectService.insertSelective(relPsnSuspect);
//
//                //获取关联表的最大id值
//
//                Integer maxRelPid = relOrgSuspectService.getMaxId("bus_rel_suspect");
//                relSuspect.setPid(maxRelPid + 1);
//                relSuspect.setToPid(relPsnSuspect.getPid().toString());
//                relSuspect.setFromType("SUSORG");
//                relSuspect.setToType("SUSPSN");
//                relSuspectService.insertSelective(relSuspect);
//            }
//
//			tips.put(WebCode.SUCCESS, true);
//            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
//            tips.put("pid", orgToPsnVo.getFromPid());
//        } catch (ServiceException e) {
//            logger.info(e.getMessage());
//            tips.put(WebCode.SUCCESS, false);
//            tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
//        }
//        return tips;

    }


    /*
     * 保存或者编辑机构关系   ？？？
     * */
    @RequestMapping(value = "/relOrgSave/{paFromPid}/{fromPid}")
    @ResponseBody
    public Map<String, Object> relOrgSave(@PathVariable String paFromPid, @PathVariable String fromPid, OrgToOrgVo orgToOrgVo) {
        Map tips = new HashMap();
        //归属关联方
        RelOrg fromOrg = relOrgService.get(Integer.valueOf(paFromPid));
        //新增关系to机构
        RelOrgSuspect relOrg = relOrgSuspectService.get(Integer.valueOf(fromPid));
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
            rel.setRelCategory(orgToOrgVo.getRelCategory());
            rel.setRelCategorySub(orgToOrgVo.getRelCategorySub());
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
                        .eq("relCategoryVal", orgToOrgVo.getRelCategory())
                        .eq("assoTypeVal", orgToOrgVo.getRelCategorySub())
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
                    //关系设置定为码
                    rel.setFromAligCode(alig);
                    rel.setToAligCode(toAligCode);
                    rel.setRegulator(aligMap.getCaliber());
                    rel.setDelflag("1");
                    rel.setFromType("ORG");
                    rel.setToType("SUSORG");
                    rel.setShareholdingRatio(orgToOrgVo.getShareholdingRatio());
                    //关联人设置口径
                    relOrg.setRegulatorOth1(aligMap.getCaliber());
                } else {
                    //关系设置定为码
                    rel.setFromAligCode(alig);
                    rel.setDelflag("1");
                    rel.setFromType("ORG");
                    rel.setToType("SUSORG");
                    rel.setShareholdingRatio(orgToOrgVo.getShareholdingRatio());
                }
                relOrg.setStatus("3");
                relOrgSuspectService.update(relOrg);
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
//        Map<String, Object> tips = new HashMap<String, Object>();
//        /*
//         * 根据是否有  机构id校验    --to_pid是准确的
//         * */
//        RelOrgSuspect relOrgSuspect = new RelOrgSuspect();
//        RelSuspect relSuspect = new RelSuspect();
//        Boolean isExist = false;
//        if (StringUtil.isNotEmpty(orgToOrgVo.getToPid())) {
//            isExist = relOrgSuspectService.exists(Integer.parseInt(orgToOrgVo.getToPid()));
//        }
//        BeanUtils.copyProperties(orgToOrgVo, relOrgSuspect);
//        BeanUtils.copyProperties(orgToOrgVo, relSuspect);
//        try {
//            if (isExist) {
//                //将机构的id信息进行修改
//                relOrgSuspect.setPid(Integer.parseInt(orgToOrgVo.getToPid()));
//                relOrgSuspectService.updateSelective(relOrgSuspect);
//
//                relSuspect.setPid(orgToOrgVo.getRelPid());
//                relSuspectService.updateSelective(relSuspect);
//            } else {
//                //默认设置成待审批
//                relOrgSuspect.setApprovalStatus("1");
////				// 获取  机构表的最大id值
//                Integer maxPid = relOrgSuspectService.getMaxId("bus_rel_org_suspect");
//                relOrgSuspect.setPid(maxPid + 1);
//                relOrgSuspectService.insertSelective(relOrgSuspect);
//
//                //获取关联表的最大id值
//
//                Integer maxRelPid = relOrgSuspectService.getMaxId("bus_rel_suspect");
//                relSuspect.setPid(maxRelPid + 1);
//                relSuspect.setToPid(relOrgSuspect.getPid().toString());
//                relSuspect.setFromType("SUSORG");
//                relSuspect.setToType("SUSORG");
//                //做一个关系判断   投资方或者被投资方
//                if("0002".equals(relSuspect.getToMemo())){
//                    relSuspect.setFromMemo("0001");
//                }else {
//                    relSuspect.setFromMemo("0002");
//                }
//                relSuspectService.insertSelective(relSuspect);
//
//            }
//
//            tips.put(WebCode.SUCCESS, true);
//            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
//            tips.put("pid", orgToOrgVo.getFromPid());
//        } catch (ServiceException e) {
//            logger.info(e.getMessage());
//            tips.put(WebCode.SUCCESS, false);
//            tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
//        }
//        return tips;

    }

    /*
     * 跳转到  机构关系的新增页面
     * */
    @RequestMapping(value = "/relOrgAdd/{pid}")
    public String relOrgAdd(@PathVariable Integer pid, Model model) {
        /*
         * Pid  源pid
         * */
        OrgToOrgVo orgToOrgVo = new OrgToOrgVo();
        orgToOrgVo.setFromPid(pid.toString());
        model.addAttribute("relOrgSuspect", orgToOrgVo);
        model.addAttribute("opt", "add");
        return "bus/orgToOrgEditOrAdd";
    }

    /*
     * 跳转到  机构关系的编辑页面
     * */
    @RequestMapping(value = "/relOrgEdit/{pid}")
    public String relOrgEdit(@PathVariable Integer pid, Model model) {
        /*
         * relPid  关联关系表中的pid
         * 获取关联关系信息
         * */
        Rel rel = relService.get(pid);
//        RelSuspect relSuspect = relSuspectService.get(pid);
        /*
         * orgPid  机构的pid  就是to_pid
         * 获取机构信息
         * */
//        String orgPid = relSuspect.getToPid();
        String orgPid = rel.getFromPid();
        RelOrg relOrg = relOrgService.get(Integer.parseInt(orgPid));

        /*
         * 二者数据结合到vo
         * */
        OrgToOrgVo orgToOrgVo = new OrgToOrgVo();

        BeanUtils.copyProperties(rel, orgToOrgVo);
        //此处机构id以to_pid为准
        BeanUtils.copyProperties(relOrg, orgToOrgVo);

//		orgToOrgVo.setFromPid(relSuspect.getFromPid());
//		orgToOrgVo.setToPid(relSuspect.getToPid());
        orgToOrgVo.setRelPid(relOrg.getPid());
//		orgToOrgVo.setRegCapital();
//		orgToOrgVo.setShareholdingRatio();
//		model.addAttribute("relSuspect",relSuspect);
        model.addAttribute("relOrgSuspect", orgToOrgVo);
        // 存储源pid
//		model.addAttribute("pid",Integer.parseInt(relSuspect.getFromPid()));
        model.addAttribute("opt", "edit");
        return "bus/orgToOrgEditOrAdd";
    }

    /*
     * 跳转到  机构关系的详情页面
     * */
    @RequestMapping(value = "/relOrgView/{pid}")
    public String relOrgView(@PathVariable Integer pid, Model model) {
        /*
         * relPid  关联关系表中的pid
         * 获取关联关系信息
         * */
        Rel rel = relService.get(pid);
//        RelSuspect relSuspect = relSuspectService.get(pid);
        /*
         * orgPid  机构的pid  就是to_pid
         * 获取机构信息
         * */
//        String orgPid = relSuspect.getToPid();
        String orgPid = rel.getFromPid();
        RelOrg relOrg = relOrgService.get(Integer.parseInt(orgPid));

        /*
         * 二者数据结合到vo
         * */
        OrgToOrgVo orgToOrgVo = new OrgToOrgVo();

        BeanUtils.copyProperties(rel, orgToOrgVo);
        //此处机构id以to_pid为准
        BeanUtils.copyProperties(relOrg, orgToOrgVo);

//		orgToOrgVo.setFromPid(relSuspect.getFromPid());
//		orgToOrgVo.setToPid(relSuspect.getToPid());
        orgToOrgVo.setRelPid(relOrg.getPid());
//		orgToOrgVo.setRegCapital();
//		orgToOrgVo.setShareholdingRatio();
//		model.addAttribute("relSuspect",relSuspect);
        model.addAttribute("relOrgSuspect", orgToOrgVo);
        // 存储源pid
//		model.addAttribute("pid",Integer.parseInt(relSuspect.getFromPid()));
        model.addAttribute("opt", "edit");
        return "bus/orgToOrgView";
    }

    /*
     * 提交审批(集中审议)
     * */
    @RequestMapping(value = "/send/{pid}")
    @ResponseBody
    public Map<String, Object> sendjz(@PathVariable Integer pid, Model model) throws Exception {
        List<TraFunds> lli = new ArrayList<>();
        Map<String, Object> tips = new HashMap<String, Object>();
        //获取当前登录用户
        UserProfile profile = UserProfileUtils.getUserProfile();
        String procInstId = null;

        StaffImpl staff = new StaffImpl();
        BeanUtils.copyProperties(profile, staff);

        RelOrgSuspect relOrgSuspect = relOrgSuspectService.get(pid);
        if (StringUtils.isNotEmpty(relOrgSuspect.getProcInstId())) {
            procInstId = relOrgSuspect.getProcInstId();
        }
        //解析JSON
        com.alibaba.fastjson.JSONObject data = com.alibaba.fastjson.JSONObject.parseObject(JSONObject.toJSONString(relOrgSuspect));
        data.put("type", "ORG");
        data.put("startStaffCode", staff.getStaffCode());
        String procDefCode = null;
        procDefCode = subjectName.get(profile.getTenantId()) + "YSGLFSPLC";
        data.put("procDefCode", procDefCode);
        try {
            workflowOperation.sendSubmit(pid, procDefCode, staff, null, data.toJSONString(), "ORG", procInstId);
        } catch (Exception e) {
            logger.info("流程启动失败，失败数据类型:疑似法人---" + "pid" + pid, e);
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