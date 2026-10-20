/*
 * Powered By xTOOLs
 * Dev by DuRisk@pactera.com
 * Copyright © 2009 - 2020 pactera.com. All rights reserved.
 *
 * Generated at 2020年05月25日 14:22:10
 *
 */

package com.yzj.jep.app.bus.web.controller;

import com.yzj.jep.app.base.constant.Log;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.RelOrgService;
import com.yzj.jep.app.bus.service.RelProService;
import com.yzj.jep.app.bus.service.RelPsnAndOrgOfProService;
import com.yzj.jep.app.bus.service.RelPsnService;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.sys.util.XssCheck;
import com.yzj.jep.app.util.AuthorityUtil;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.*;
import com.yzj.jep.sys.code.DutySource;
import com.yzj.jep.sys.model.Duty;
import com.yzj.jep.sys.service.DutyService;
import com.yzj.jep.sys.service.NotifyService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/bus/relPro")
public class RelProController extends CrudController<RelPro> {
    private static Logger logger = LoggerFactory.getLogger(RelProController.class);

    @Autowired
    private RelProService relProService;
    @Autowired
    private DutyService dutyService;

    @Autowired
    private RelOrgService relOrgService;
    @Autowired
    private NotifyService notifyService;


    @Autowired
    private RelPsnService relPsnService;
    @Autowired
    private RelPsnAndOrgOfProService relPsnAndOrgOfProService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        //用戶角色查詢
        String staffCode = UserProfileUtils.getUserProfile().getStaffCode();
        String url = "bus/relPro/list";
        AuthorityUtil authorityUtil = new AuthorityUtil();
        String code = authorityUtil.buttonPerm(staffCode, url);
        model.addAttribute("code", code);

        return "bus/relProList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<RelPro> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        filters.add(new Filter("delflag", 1));
        orders.add(new Order("modifiedTime", OrderType.DESC));
        relProService.query(page, filters, orders);
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

        RelPro relPro = new RelPro();
        relPro.setMgerOrg(unitCode);//设置管护机构
        model.addAttribute("relPro", relPro);
        return "bus/relProAdd";
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
        RelPro relPro = relProService.get(pid);
        UserProfile profile = UserProfileUtils.getUserProfile();
        String userid = profile.getUserid();
        if (userid.equals(relPro.getAmendId())) {
            map.put(WebCode.SUCCESS, true);
        } else {
            map.put(WebCode.SUCCESS, false);

        }
        return map;
    }


    /**
     * 编辑页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{pid}")
    public String edit(@PathVariable Integer pid, int op, Model model) {

        RelPro relPro = relProService.get(pid);
        model.addAttribute("relPro", relPro);
        model.addAttribute("opt", "edit");
        model.addAttribute("op", op);

        return "bus/relProAddOrEdit";
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

        RelPro relPro = relProService.get(pid);
        model.addAttribute("relPro", relPro);
        model.addAttribute("opt", "edit");
        model.addAttribute("op", "1");

        return "bus/relProAddOrEdit";
    }

    /**
     * 查看页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/view1/{pid}")
    public String view1(@PathVariable Integer pid, Model model) {

        RelPro relPro = relProService.get(pid);
        model.addAttribute("relPro", relPro);
        model.addAttribute("opt", "view");
        model.addAttribute("op", "1");

        return "bus/relProAddOrEdit1";
    }

    //反显，查询金融产品备案号
    @RequestMapping(value = "/selectProProvider")
    @ResponseBody
    @Log(module = "关联方管理", description = "所属自然人反显")
    public Map<String, Object> selectProProvider(String proProvider) {
        Map<String, Object> tips = new HashMap<String, Object>();
        UserProfile profile = UserProfileUtils.getUserProfile();
        String unitCode = profile.getUnitCode();
        List<Filter> filters = new Filter.Builder().eq("delflag", "1")
                .eq("proProvider", proProvider).build();//.eq("mgerOrg", unitCode)
        boolean exists = relProService.exists(filters);
        if (exists) {
            tips.put(WebCode.SUCCESS, true);
        } else {
            tips.put(WebCode.SUCCESS, false);
        }
        return tips;
    }

    /**
     * 保存
     *
     * @param relPro
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(RelPro relPro) {
        Map<String, Object> tips = new HashMap<String, Object>();
        boolean b = XssCheck.reflect(relPro);
        if (!b) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "数据格式不规范，请检查!");
            return tips;
        }
        int index = 0;
        String proPid = "";
        UserProfile profile = UserProfileUtils.getUserProfile();
        relPro.setAmendId(profile.getStaffCode());
        relPro.setAmendName(profile.getStaffName());
       Boolean isExist = relProService.exists(relPro.getPid());
        try {
            if (isExist) {
                index = relProService.updateSelective(relPro);
            } else {
                relPro.setDataSource("1");//数据来源为手工录入
                relPro.setEffectRelation("0");
                relPro.setApprovalStatus("6");//设置状态为归档
                index = relProService.insertSelective(relPro);
            }
            if (index != 0) {
                List<RelPro> relPros = relProService.select(relPro);
                tips.put("pid", relPros.get(0).getPid());
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
            relProService.deleteByPrimaryKey(pid);
            List<Filter> tfilters = new Filter.Builder().eq("toPid", pid.toString()).eq("toType", "PRO").eq("delflag", "1").build();
            boolean exists1 = relPsnAndOrgOfProService.exists(tfilters);
            if (exists1) {
                List<RelPsnAndOrgOfPro> select = relPsnAndOrgOfProService.select(tfilters);
                for (RelPsnAndOrgOfPro rel : select) {
                    rel.setRelStatus("02");
                    relPsnAndOrgOfProService.updateSelective(rel);
                }
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

        boolean exist = relProService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }

    @RequestMapping(value = "/psnOfProList/{pid}")
    public String affiliateView(@PathVariable String pid, int op, Model model) {
        model.addAttribute("fromPid", pid);
        model.addAttribute("op", op);
//		model.addAttribute("opt", opt);
        return "bus/psnOfProList";
    }

    @RequestMapping(value = "/orgOfProList/{pid}")
    public String rightsList(@PathVariable String pid, int op, Model model) {
        model.addAttribute("fromPid", pid);
        model.addAttribute("op", op);
//		model.addAttribute("opt", opt);
        return "bus/orgOfProList";
    }

    /**
     * 新增自然人关系信息页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/addPsnOfPro/{fromPid}")
    public String addAffiliate(@PathVariable Integer fromPid, Model model) {
        model.addAttribute("opt", "add");
        // 自然人pid
        model.addAttribute("fromPid", fromPid);
        return "bus/psnOfProAddOrEdit";
    }

    /**
     * 新增机构关系信息页面
     */
    @RequestMapping(value = "addOrgOfPro/{orgid}")
    public String addOrg(Model model, @PathVariable Integer orgid) {
        model.addAttribute("opt", "add");
        model.addAttribute("fromPid", orgid);
        return "bus/orgOfProAddOrEdit";
    }

    /*
     * 任务下发
     * */
    @RequestMapping(value = "/relProSend")
    public String relPsnSendCommand(Model model) {

        return "bus/relProSend";
    }

    /*
     * 确认下发
     * */
    @RequestMapping(value = "/taskSend")
    @ResponseBody
    public Map taskSend(String unitCode, String memo, String sendProId) {
        Map<String, Object> tips = new HashMap<String, Object>();
        //发放金融产品给指定机构补录
        List<Filter> filers = new Filter.Builder().eq("pid", sendProId).build();
        RelPro relPro = relProService.get(filers);
        //更换管库机构为下发机构
//		relPro.setMgerOrg(unitCode);
        //更新疑似自然人状态为待补录
        relPro.setApprovalStatus("2");
        relPro.setStatusComment(memo);
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
                    notifyService.createRemind(duty.getStaffCode(), "金融产品[" + relPro.getProName() + "]的补录任务已下发到您[" + duty.getStaffCode() + "]，请到金融产品列表完成补录。", "金融产品[" + relPro.getProName() + "]的补录任务已下发到您[" + duty.getStaffCode() + "]，请到金融产品列表完成补录。");
                }
                relProService.update(relPro);
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

    /*
     * 提交审批
     * */
    @RequestMapping(value = "/send/{pid}")
    @ResponseBody
    public Map<String, Object> send(@PathVariable Integer pid, Model model) {
        Map<String, Object> tips = new HashMap<String, Object>();
        /*List<RelPsnVo> relPsnVoList = relPsnAndOrgOfProService.selectRelPsnOfProInformation(pid.toString());
        List<RelOrgVo> tzOrgPsn = relPsnAndOrgOfProService.selectRelOrgOfProInformation(String.valueOf(pid));
        if (relPsnVoList.size() <= 0 && tzOrgPsn.size() <= 0) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "未设置关联关系");
            return tips;
        }*/
        RelPro relPro = relProService.get(pid);
        if ("3".equals(relPro.getApprovalStatus())) {
            //4 代表审批中
            relPro.setApprovalStatus("4");
            relProService.updateSelective(relPro);
            tips.put("data", relPro);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } else {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "状态不符，提交失败！");
        }
        return tips;
    }

    /**
     * 批量提交(多选)
     * @param pids
     * @return
     */
    @RequestMapping("/batchSend/{pids}")
    @ResponseBody
    public Map<String,Object> batchSend(@PathVariable String pids){
        Map<String,Object> result =new HashMap<>();
        String[] pidArr = pids.split(",");
        List<Integer> pidList=new ArrayList<>();
        //修改所有的数据为已审批状态
        for (String pid : pidArr) {
            Integer integerPid = Integer.valueOf(pid);
            RelPro relPro = new RelPro();
            relPro.setPid(integerPid);
            relPro.setApprovalStatus("4");
            relProService.updateSelective(relPro);
            pidList.add(integerPid);
        }
        //查询所有的数据
        List<Filter> filters = new ArrayList<>();
        filters.add(new Filter("pid",Operator.IN,pidList));
        List<RelPro> proList = relProService.select(filters);
        //将数据返回，已map格式返回
        Map<String,Object> data = new HashMap<>(16);
        data.put("pids",pids);
//        String jsonString = JSONObject.toJSONString(proList);
//        String decodeStr = Base64.encode(jsonString);
        data.put("data", proList);
        result.put("data", data);
        result.put(WebCode.SUCCESS, true);
        result.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        return result;
    }

    @RequestMapping(value = "/batchPids/{pids}")
    @ResponseBody
    public Map<String, Object> send(@PathVariable Integer[] pids, Model model) {
        Map<String, Object> tips = new HashMap<String, Object>();
        List<Filter> filters = new ArrayList<>();
        filters.add(new Filter("pid",Operator.IN,pids));
        List<RelPro> proList = relProService.select(filters);

        tips.put(WebCode.SUCCESS, true);
        tips.put("total", proList.size());
        tips.put("rows", proList);
        tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
        return tips;
    }

    /**
     * 查看机构关系信息页面
     */

    @RequestMapping(value = "viewPsnRelOrg/{relId}/{fromPid}/{type}")
    public String viewPsnRelOrg(@PathVariable Integer relId, @PathVariable Integer fromPid, @PathVariable String type, Model model) {
        model.addAttribute("opt", "edit");
        model.addAttribute("fromPid", fromPid);
        List<RelOrgVo> tzOrgPsn = new ArrayList<>();
        if ("UP".equals(type)) {
            //查找所属关联方
            tzOrgPsn = relOrgService.getProOrgPsn(String.valueOf(fromPid));
//			tzOrgPsn = relOrgService.getProOrgPsn(String.valueOf(fromPid));
        } else if ("DOWN".equals(type)) {
            //查找下级所属关联方
            tzOrgPsn = relOrgService.getDownProOrgPsn(String.valueOf(fromPid));
        } else {
            model.addAttribute("fromPid", fromPid);
            return "bus/psnRelOrgAddOrView";
        }
        List<RelOrgVo> orgs = tzOrgPsn.stream().filter(item -> item.getRelId().equals(relId)).collect(Collectors.toList());
        if (orgs.size() > 0) {
            RelOrgVo relOrg = orgs.get(0);
            model.addAttribute("relOrg", relOrg);
        }
        model.addAttribute("fromPid", fromPid);
        return "bus/psnRelOrgAddOrView";
    }

    /**
     * 查看自然人关系信息页面
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/viewAffiliate/{pid}/{relId}/{fromPid}")
    public String viewAffiliate(@PathVariable Integer pid, @PathVariable Integer relId, @PathVariable Integer fromPid, Model model) {

        RelPsn relPsn = relPsnService.get(pid);
        // RelService relService = SpringContextHolder.getBean(RelService.class);
        RelPsnAndOrgOfPro rel = relPsnAndOrgOfProService.get(relId);
        RelPsnVo relPsnVo = new RelPsnVo();
        BeanUtils.copyProperties(relPsn, relPsnVo);
        relPsnVo.setRelCategory(rel.getRelCategory());
        relPsnVo.setRelStatement(rel.getRelStatement());
        relPsnVo.setEffectiveDate(rel.getEffectiveDate());
        relPsnVo.setIneffectiveDate(rel.getIneffectiveDate());
        relPsnVo.setRelCategorySub(rel.getRelCategorySub());
        model.addAttribute("relPsnVo", relPsnVo);
        model.addAttribute("opt", "edit");
        model.addAttribute("op", "1");
        model.addAttribute("pid", pid);
        model.addAttribute("relId", relId);
        model.addAttribute("fromPid", fromPid);
        model.addAttribute("parentPid", pid);

        return "bus/proPsnaffiliateAddOrEdit";
    }

}
