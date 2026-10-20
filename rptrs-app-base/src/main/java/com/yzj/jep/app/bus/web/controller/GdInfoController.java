package com.yzj.jep.app.bus.web.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.BatchNumberUtil;
import com.yzj.jep.app.util.GdTraUtil;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("bus/gdInfo")
public class GdInfoController extends CrudController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GdInfoController.class);

    @Autowired
    private GdInfoService gdInfoService;

    @Autowired
    private RelPsnService relPsnService;

    @Autowired
    private RelFileService relFileService;

    @Autowired
    private RelOrgService relOrgService;

    @Override
    public Map<String, Object> query(Page page, Model model, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        prepareQuery(page, filters, orders, request);
        GdInfoVo gdInfoVo = new GdInfoVo();
        for (Filter filter : filters) {
            if ("type".equals(filter.getProperty())) {
                gdInfoVo.setType(filter.getValue().toString());
            } else if ("domesticOrForeign".equals(filter.getProperty())) {
                gdInfoVo.setDomesticOrForeign(filter.getValue().toString());
            } else if ("name".equals(filter.getProperty())) {
                gdInfoVo.setName(filter.getValue().toString());
            } else if ("shareholdersCode".equals(filter.getProperty())) {
                gdInfoVo.setShareholdersCode(filter.getValue().toString());
            } else if ("stateOwned".equals(filter.getProperty())) {
                gdInfoVo.setStateOwned(filter.getValue().toString());
            } else if ("shareholdingProportion".equals(filter.getProperty())) {
                gdInfoVo.setShareholdingProportion(filter.getValue().toString());
            }
        }
        PageHelper.start(page);
        List<GdInfoVo> allGd = gdInfoService.getAllGd(gdInfoVo);
        PageInfo<GdInfoVo> pageInfo = new PageInfo<>(allGd);
        result.put(RESULT_TOTAL, pageInfo.getTotal());
        result.put(RESULT_ROWS, pageInfo.getList());
        return result;
    }

    /**
     * 股东信息页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {
        return "bus/GdInfoList";
    }

    /**
     * 自然人开户页面
     */
    @RequestMapping(value = "/psnOpen")
    public String psnOpen(Model model) {
        return "bus/psnOpen";
    }

    /**
     * 机构开户页面
     */
    @RequestMapping(value = "/orgOpen")
    public String orgOpen(Model model) {
        return "bus/orgOpen";
    }

    /**
     * 详情页面跳转
     */
    @RequestMapping(value = "/view/{pid}/{type}")
    public String view(Model model, @PathVariable String type, @PathVariable Integer pid) {
        model.addAttribute("opt", "view");
        if ("PSN".equals(type)) {
            RelPsn relPsn = relPsnService.get(pid);
            model.addAttribute("relPsn", relPsn);
            return "bus/psnView";
        }
        RelOrg relOrg = relOrgService.get(pid);
        model.addAttribute("relOrg", relOrg);
        return "bus/orgView";
    }

    /**
     * 保存自然人股东信息
     */
    @RequestMapping(value = "/savePsn")
    @ResponseBody
    @Transactional
    public Map<String, Object> savePsn(RelPsn relPsn, TraEnclosureVo traEnclosureVo) {
        Map<String, Object> tips = new HashMap<String, Object>();
        try {
            Integer pid = relPsn.getPid();
            String relSerial = null;
            if (pid != null) {
                relPsn.setStatus("3");
                relPsnService.updateSelective(relPsn);
                relSerial = relPsnService.get(relPsn.getPid()).getRelSerial();
            } else {
                relPsn.setDelflag("1");
                relPsn.setStatus("3");
                relPsn.setIsShareholder("1");
                relPsn.setIsBlack("0");
                UserProfile userProfile = UserProfileUtils.getUserProfile();
                String unitCode = userProfile.getUnitCode();
                relPsn.setMgerOrg(unitCode);
                relPsn.setRelSerial(BatchNumberUtil.makeBatchNumber());
                if ("1".equals(relPsn.getIsMainGd())) {
                    relPsn.setRegulatorOth1("YJH,ZJH");
                    relPsn.setAligCode("11");
                }
                relPsnService.insert(relPsn);
                relSerial = relPsn.getRelSerial();
                List<RelPsn> relPsn1 = relPsnService.select(relPsn);
                pid = relPsn1.get(0).getPid();
            }
            List<Integer> docTableSelectItem = traEnclosureVo.getDocTableSelectItem();
            String merge = relPsn.getMgerOrg();
            relFileService.updateRelSerial(docTableSelectItem, relSerial, merge);
            tips.put(WebCode.SUCCESS, true);
            tips.put("pid", pid);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } catch (ServiceException e) {
            LOGGER.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        }

        return tips;
    }

    /**
     * 保存机构股东信息
     */
    @RequestMapping(value = "/saveOrg")
    @ResponseBody
    @Transactional
    public Map<String, Object> saveOrg(RelOrg relOrg, TraEnclosureVo traEnclosureVo) {
        Map<String, Object> tips = new HashMap<String, Object>();
        try {
            Integer pid = relOrg.getPid();
            String relSerial = null;
            if (pid != null) {
                relOrg.setStatus("3");
                relOrgService.updateSelective(relOrg);
                relSerial = relOrgService.get(relOrg.getPid()).getRelSerial();
            } else {
                relOrg.setDelflag("1");
                relOrg.setStatus("3");
                relOrg.setIsShareholder("1");
                relOrg.setIsBlack("0");
                UserProfile userProfile = UserProfileUtils.getUserProfile();
                String unitCode = userProfile.getUnitCode();
                relOrg.setMgerOrg(unitCode);
                relOrg.setRelSerial(BatchNumberUtil.makeBatchNumber());
                if ("1".equals(relOrg.getIsMainGd())) {
                    relOrg.setRegulatorOth1("YJH,ZJH");
                    relOrg.setAligCode("14");
                }
                relOrgService.insert(relOrg);
                relSerial = relOrg.getRelSerial();
                List<RelOrg> relOrgs = relOrgService.select(relOrg);
                pid = relOrgs.get(0).getPid();
            }
            List<Integer> docTableSelectItem = traEnclosureVo.getDocTableSelectItem();
            String merge = relOrg.getMgerOrg();
            relFileService.updateRelSerial(docTableSelectItem, relSerial, merge);
            tips.put(WebCode.SUCCESS, true);
            tips.put("pid", pid);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } catch (ServiceException e) {
            LOGGER.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        }

        return tips;
    }

    /**
     * 验证提交的股东代码是否已经存在数据库中
     *
     * @param shareholdersCode
     * @return
     */
    @RequestMapping(value = {"/check/{type}/{pid}", "/check/{type}/"}, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> check(String shareholdersCode, @PathVariable(value = "pid", required = false) Integer pid, @PathVariable String type) {
        Map<String, Object> tips = new HashMap<String, Object>();
        if (pid != null) {
            if ("PSN".equals(type)) {
                if (shareholdersCode.equals(relPsnService.get(pid).getShareholdersCode())) {
                    tips.put("valid", true);
                    return tips;
                }
            }
            if ("ORG".equals(type)) {
                if (shareholdersCode.equals(relOrgService.get(pid).getShareholdersCode())) {
                    tips.put("valid", true);
                    return tips;
                }
            }
        }

        RelPsn relPsn = new RelPsn();
        relPsn.setShareholdersCode(shareholdersCode);
        RelPsn psn = relPsnService.get(relPsn);
        RelOrg relOrg = new RelOrg();
        relOrg.setShareholdersCode(shareholdersCode);
        RelOrg org = relOrgService.get(relOrg);
        tips.put("valid", psn == null && org == null);

        return tips;
    }

    /**
     * 计算股东持股占比
     */
    @RequestMapping(value = "/getPorportion/{holeShareAmount}")
    @ResponseBody
    public Map<String, Object> getPorportion(@PathVariable String holeShareAmount) {
        Map<String, Object> tips = Maps.newHashMap();
        Double proportion = GdTraUtil.getProportion(holeShareAmount);
        tips.put(WebCode.SUCCESS, true);
        tips.put("proportion", proportion);
        return tips;
    }

    /**
     * 编辑页面跳转
     */
    @RequestMapping(value = "/edit/{pid}/{type}")
    public String edit(@PathVariable Integer pid, @PathVariable String type, Model model) {
        model.addAttribute("opt", "edit");
        if ("PSN".equals(type)) {
            RelPsn relPsn = relPsnService.get(pid);
            model.addAttribute("relPsn", relPsn);
            return "bus/psnOpen";
        }
        RelOrg relOrg = relOrgService.get(pid);
        model.addAttribute("relOrg", relOrg);
        return "bus/orgOpen";
    }

    /**
     * 审批页面跳转
     */
    @RequestMapping(value = "/submit/{subType}/{type}/{pid}")
    public String submit(Model model, @PathVariable String subType, @PathVariable String type, @PathVariable Integer pid) {
        model.addAttribute("subType", subType);
        if ("PSN".equals(type)) {
            RelPsn relPsn = relPsnService.get(pid);
            model.addAttribute("relPsn", relPsn);
            return "bus/psnGdSubmit";
        }
        RelOrg relOrg = relOrgService.get(pid);
        model.addAttribute("relOrg", relOrg);
        return "bus/orgGdSubmit";
    }

    /**
     * 更改审批状态
     */
    @RequestMapping(value = "/changeApplyStatus/{type}/{pid}/{reason}")
    @ResponseBody
    public Map<String, Object> changeApplyStatus(@PathVariable String type, @PathVariable Integer pid, @PathVariable String reason) {
        Map<String, Object> tips = Maps.newHashMap();
        try {
            if ("ORG".equals(type)) {
                RelOrg relOrg = new RelOrg();
                relOrg.setStatus("4");
                relOrg.setPid(pid);
                relOrg.setStatusComment(reason);
                relOrgService.updateSelective(relOrg);
                tips.put(WebCode.SUCCESS, true);
                return tips;
            }
            RelPsn relPsn = new RelPsn();
            relPsn.setStatus("4");
            relPsn.setPid(pid);
            relPsn.setStatusComment(reason);
            relPsnService.updateSelective(relPsn);
            tips.put(WebCode.SUCCESS, true);
        } catch (ServiceException e) {
            LOGGER.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
        }

        return tips;
    }

    @RequestMapping(value = "/psnRelOrgList/{pid}")
    public String psnRelOrgList(@PathVariable Integer pid, Model model) {
        model.addAttribute("fromPid", pid);
        return "bus/psnGdRelOrgList";
    }

    @RequestMapping(value = "/psnRelPsnList/{pid}")
    public String psnRelPsnList(@PathVariable Integer pid, Model model) {
        model.addAttribute("fromPid", pid);
        return "bus/psnGdRelPsnList";
    }

    @RequestMapping(value = "/psnRelOrgEdit/{fromPid}")
    public String psnRelOrgEdit(@PathVariable Integer fromPid, Model model) {
        model.addAttribute("fromPid", fromPid);
        return "bus/psnGdRelOrgEdit";
    }

    @RequestMapping(value = "/psnRelPsnEdit/{fromPid}")
    public String psnRelPsnEdit(@PathVariable Integer fromPid, Model model) {
        model.addAttribute("opt", "add");
        model.addAttribute("fromPid", fromPid);

        return "bus/psnGdRelPsnEdit";
    }

    @RequestMapping(value = "/psnRelOrgView/{fromPid}")
    public String psnRelOrgView(@PathVariable Integer fromPid, Model model) {
        model.addAttribute("fromPid", fromPid);
        return "bus/psnGdRelOrgView";
    }

    @RequestMapping(value = "/psnRelPsnView/{fromPid}")
    public String psnRelPsnView(@PathVariable Integer fromPid, Model model) {
        model.addAttribute("opt", "add");
        model.addAttribute("fromPid", fromPid);

        return "bus/psnGdRelPsnView";
    }

    @RequestMapping(value = "/orgRelOrgList/{pid}")
    public String orgRelOrgList(@PathVariable Integer pid, Model model) {
        model.addAttribute("orgid", pid);
        return "bus/orgGdRelOrgList";
    }

    @RequestMapping(value = "/orgRelPsnList/{pid}")
    public String orgRelPsnList(@PathVariable Integer pid, Model model) {
        model.addAttribute("orgid", pid);
        return "bus/orgGdRelPsnList";
    }

    @RequestMapping(value = "/orgRelOrgEdit/{fromPid}")
    public String orgRelOrgEdit(@PathVariable Integer fromPid, Model model) {
        model.addAttribute("fromPid", fromPid);
        return "bus/orgGdRelOrgEdit";
    }

    @RequestMapping(value = "/orgRelPsnEdit/{fromPid}")
    public String orgRelPsnEdit(@PathVariable Integer fromPid, Model model) {
        model.addAttribute("opt", "add");
        model.addAttribute("fromPid", fromPid);

        return "bus/orgGdRelPsnEdit";
    }

    @RequestMapping(value = "/orgRelOrgView/{fromPid}")
    public String orgRelOrgView(@PathVariable Integer fromPid, Model model) {
        model.addAttribute("orgid", fromPid);
        return "bus/orgGdRelOrgView";
    }

    @RequestMapping(value = "/orgRelPsnView/{fromPid}")
    public String orgRelPsnView(@PathVariable Integer fromPid, Model model) {
        model.addAttribute("opt", "add");
        model.addAttribute("orgid", fromPid);

        return "bus/orgGdRelPsnView";
    }

    @RequestMapping(value = "/openSubmit/{pid}/{type}")
    public String openSubmit(Model model, @PathVariable Integer pid, @PathVariable String type) {
        if ("PSN".equals(type)) {
            RelPsn relPsn = relPsnService.get(pid);
            model.addAttribute("relPsn", relPsn);
            return "bus/psnOpenSubmit";
        }
        RelOrg relOrg = relOrgService.get(pid);
        model.addAttribute("relOrg", relOrg);
        return "bus/orgOpenSubmit";
    }

    @RequestMapping(value = "/psnRelOrgListSubmit/{pid}")
    public String psnRelOrgListSubmit(@PathVariable Integer pid, Model model) {
        model.addAttribute("fromPid", pid);
        return "bus/psnGdRelOrgListSubmit";
    }

    @RequestMapping(value = "/psnRelPsnListSubmit/{pid}")
    public String psnRelPsnListSubmit(@PathVariable Integer pid, Model model) {
        model.addAttribute("fromPid", pid);
        return "bus/psnGdRelPsnListSubmit";
    }

    @RequestMapping(value = "/orgRelOrgListSubmit/{pid}")
    public String orgRelOrgListSubmit(@PathVariable Integer pid, Model model) {
        model.addAttribute("orgid", pid);
        return "bus/orgGdRelOrgListSubmit";
    }

    @RequestMapping(value = "/orgRelPsnListSubmit/{pid}")
    public String orgRelPsnListSubmit(@PathVariable Integer pid, Model model) {
        model.addAttribute("orgid", pid);
        return "bus/orgGdRelPsnListSubmit";
    }

    @RequestMapping(value = "/changeApplyStatus")
    @ResponseBody
    public Map<String, Object> changeApplyStatus(Integer pid, String type, String statusComment) {
        Map<String, Object> tips = Maps.newHashMap();
        tips.put(WebCode.SUCCESS, true);
        try {
            if ("ORG".equals(type)) {
                RelOrg relOrg = new RelOrg();
                relOrg.setPid(pid);
                relOrg.setStatus("4");
                relOrg.setStatusComment(statusComment);
                relOrgService.updateSelective(relOrg);
                return tips;
            }
            RelPsn relPsn = new RelPsn();
            relPsn.setPid(pid);
            relPsn.setStatus("4");
            relPsn.setStatusComment(statusComment);
            relPsnService.updateSelective(relPsn);
        } catch (ServiceException e) {
            LOGGER.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAULURE_OPERATE);
        }
        return tips;
    }

    @RequestMapping(value = "/openExSubmit/{pid}/{type}")
    public String openExSubmit(Model model, @PathVariable Integer pid, @PathVariable String type) {
        if ("PSN".equals(type)) {
            RelPsn relPsn = relPsnService.get(pid);
            model.addAttribute("relPsn", relPsn);
            return "bus/psnOpenSubmit";
        }
        RelOrg relOrg = relOrgService.get(pid);
        model.addAttribute("relOrg", relOrg);
        return "ex/orgOpenSubmit";
    }

    @RequestMapping(value = "/psnRelOrgListExSubmit/{pid}")
    public String psnRelOrgListExSubmit(@PathVariable Integer pid, Model model) {
        model.addAttribute("fromPid", pid);
        return "ex/psnGdRelOrgListSubmit";
    }

    @RequestMapping(value = "/psnRelPsnListExSubmit/{pid}")
    public String psnRelPsnListExSubmit(@PathVariable Integer pid, Model model) {
        model.addAttribute("fromPid", pid);
        return "ex/psnGdRelPsnListSubmit";
    }

    @RequestMapping(value = "/orgRelOrgListExSubmit/{pid}")
    public String orgRelOrgListExSubmit(@PathVariable Integer pid, Model model) {
        model.addAttribute("orgid", pid);
        return "ex/orgGdRelOrgListSubmit";
    }

    @RequestMapping(value = "/orgRelPsnListExSubmit/{pid}")
    public String orgRelPsnListExSubmit(@PathVariable Integer pid, Model model) {
        model.addAttribute("orgid", pid);
        return "ex/orgGdRelPsnListSubmit";
    }
}
