/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月24日 16:35:24
 *
 */

package com.yzj.jep.app.bus.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.cims.sync.util.PullUtil;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.yzj.jep.app.base.utils.Configs;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.app.ins.model.*;
import com.yzj.jep.app.ins.model.TraService;
import com.yzj.jep.app.sys.model.UserProfile;
import com.yzj.jep.app.sys.util.UserProfileUtils;
import com.yzj.jep.app.util.ConfidentialInfoUtil;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Operator;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.sys.model.User;
import com.yzj.jep.sys.service.UserService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.util.RequestUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/bus/rel")
public class RelController extends CrudController<Rel> {
    private static Logger logger = LoggerFactory.getLogger(RelController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RelService relService;

    @Autowired
    private RelProService relProService;

    @Autowired
    private RelPsnService relPsnService;

    @Autowired
    private RelPsnAndOrgOfProService relPsnAndOrgOfProService;

    @Autowired
    private RelOrgService relOrgService;

    @Autowired
    private RelDictService relDictService;

    private static final Integer NUMBER = 50;

    @Autowired
    private Configs configs;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list/{fromPid}")
    public String list(Model model, int op, @PathVariable Integer fromPid) {
        model.addAttribute("orgid", fromPid);
        model.addAttribute("op", op);

        return "bus/relList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<Rel> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        relService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {

        return "bus/relEdit";
    }

    /*
     * 查看是否有关联关系
     * */

    @RequestMapping(value = "/queryRel/{pids}/{type}")
    @ResponseBody
    public Map<String, Object> queryRel(@PathVariable String pids, @PathVariable String type) {
        Map<String, Object> tips = new HashMap<String, Object>();
        if (StringUtils.isEmpty(type) || StringUtils.isEmpty(pids)) {
            //参数为空，查询失败，不可审批
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "查询关系失败！");
            return tips;
        }


        String[] pidStrArr = pids.split(",");
        for (String pidStr : pidStrArr) {
            int pid = Integer.parseInt(pidStr);
            if ("PSN".equals(type)) {

                List<Filter> tfilters = new Filter.Builder().eq("toPid", pidStr).eq("toType", "PSN").eq("relStatus", "00").eq("isValid", "1").eq("delflag", "1").build();
                //查询自然人的关系
                boolean exists = relService.exists(tfilters);
                if (exists) {
                    tips.put(WebCode.SUCCESS, false);
                    tips.put(WebCode.MSG, "该条数据下存在关系！请先解除关系");
                    return tips;
                } else {
                    RelPsn relPsn = relPsnService.get(pid);
                    tips.put("data", relPsn);
                }
            } else if ("ORG".equals(type)) {
                //查询机构的关系
                List<Filter> tfilters = new Filter.Builder().eq("toPid", pidStr).eq("toType", "ORG").eq("relStatus", "00").eq("isValid", "1").eq("delflag", "1").build();
                //查询机构的关系
                boolean exists = relService.exists(tfilters);
                boolean exists1 = relPsnAndOrgOfProService.exists(tfilters);
                if (exists || exists1) {
                    tips.put(WebCode.SUCCESS, false);
                    tips.put(WebCode.MSG, "该条数据下存在关系！请先解除关系");
                    return tips;
                } else {
                    RelOrg relOrg = relOrgService.get(pid);
                    tips.put("data", relOrg);
                }
            } else if ("PRO".equals(type)) {
                //查询金融产品与关联自然人、关联机构的关系
                List<Filter> tfilters = new Filter.Builder().eq("toPid", pidStr).eq("toType", "PRO").eq("relStatus", "00").eq("isValid", "1").eq("delflag", "1").build();
                //查询金融产品的关系
                boolean exists1 = relPsnAndOrgOfProService.exists(tfilters);
                if (exists1) {
                    tips.put(WebCode.SUCCESS, false);
                    tips.put(WebCode.MSG, "该条数据下存在关系！请先解除关系");
                    return tips;
                } else {
                    RelPro relPro = relProService.get(pid);
                    tips.put("data", relPro);
                }
            }
        }
        tips.put(WebCode.SUCCESS, true);
        tips.put(WebCode.MSG, "无关联关系,可删除");
        return tips;
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

        Rel rel = relService.get(pid);
        model.addAttribute("rel", rel);
        model.addAttribute("opt", "edit");

        return "bus/relEdit";
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

        Rel rel = relService.get(pid);
        model.addAttribute("rel", rel);

        return "bus/relView";
    }

    /**
     * 保存
     *
     * @param rel
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(Rel rel) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = relService.exists(rel.getPid());
        try {
            if (isExist) {

                relService.update(rel);
            } else {
                relService.insert(rel);
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
            RelOrg relOrg = new RelOrg();
            relOrg.setPid(pid);
            relOrg.setDelflag("1");
            relOrgService.updateSelective(relOrg);
            //relService.deleteByPrimaryKey(pid);
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

        boolean exist = relService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }

    /**
     * 所属关联自然人 机构
     * , produces = {"application/json;charset=UTF-8"}
     *
     * @param page
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"/queryVo"}, produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> queryVo(Page<RelOrgVo> page, Model model, HttpServletRequest request, Integer orgid) {
        Map<String, Object> result = new HashMap<String, Object>();
//        PageHelper.start(page);
        List<RelOrgVo> tzOrgPsn = relOrgService.getOrgOrg(String.valueOf(orgid));
//        PageInfo pageInfo = new PageInfo(tzOrgPsn);
        page.setTotal(tzOrgPsn.size());
        int pages = (tzOrgPsn.size() + page.getPageSize() - 1) / page.getPageSize();
        if (page.getPageNumber() > pages) {
            page.setPageNumber(1);
        }
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        List<RelOrgVo> relOrgVos = new ArrayList<RelOrgVo>();
        for (int i = index; i < index + page.getPageSize(); i++) {
            if (i < tzOrgPsn.size()) {
                relOrgVos.add(tzOrgPsn.get(i));
            }
        }
        page.setResult(relOrgVos);

        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;
    }


    /**
     * 下级关联自然人  机构
     *
     * @param page
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"/queryDownVo"}, produces = {"application/json;charset=UTF-8"}, method = {RequestMethod.GET})
    @ResponseBody
    public Map<String, Object> queryDownVo(Page<Rel> page, Model model, HttpServletRequest request, Integer orgid) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        PageHelper.start(page);
        List<RelOrgVo> tzOrgPsn = relOrgService.getDownOrgOrg(String.valueOf(orgid));
        PageInfo pageInfo = new PageInfo(tzOrgPsn);
        result.put(RESULT_TOTAL, pageInfo.getTotal());
        result.put(RESULT_ROWS, pageInfo.getList());
        return result;
    }


    /**
     * 权益信息投资情况列表分页查询
     *
     * @param page
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"/queryTZVo"}, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> queryTZVo(Page<Rel> page, Model model, HttpServletRequest request, Integer orgid) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        PageHelper.start(page);
        Rel relconn = new Rel();
        relconn.setRelSort("quanyi");
        relconn.setRelCategorySub("chigu");
        relconn.setFromType("ORG");
        relconn.setFromPid(String.valueOf(orgid));
        relconn.setRelCategory("touzi");
        relconn.setFromType("ORG");
        relconn.setToType("ORG");
        relconn.setFromPid(String.valueOf(orgid));
        relService.query(page, relconn);
        List<Rel> result1 = page.getResult();
        List<RelVo> relVos = Lists.newArrayList();
        for (Rel rel : result1) {
            String toType = rel.getToType();
            String toPid = rel.getToPid();
            Integer pid = rel.getPid();
            RelVo relVo = new RelVo();
            if ("ORG".equals(toType)) {
                RelOrg org = relOrgService.get(Integer.valueOf(toPid));
                relVo.setToMemo(org.getOrgName());
                relVo.setIdentifyNumber(org.getUniscCode());
                relVo.setIdentifyType(org.getIdentifyType());
                relVo.setShareholdingRatio(String.valueOf(rel.getShareholdingRatio()));
                relVo.setPid(pid);
                relVo.setTopid(rel.getToPid());
                relVo.setToType("ORG");
            } else {
                RelPsn psn = relPsnService.get(Integer.valueOf(toPid));
                relVo.setPid(pid);
                relVo.setShareholdingRatio(String.valueOf(rel.getShareholdingRatio()));
                relVo.setIdentifyType(psn.getIdentifyType());
                relVo.setIdentifyNumber(psn.getIdentifyNumber());
                relVo.setToMemo(psn.getName());
                relVo.setToType("PSN");
            }
            relVos.add(relVo);
        }
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, relVos);
        return result;
    }

    /**
     * 跳转新增自然人股东页面
     */
    @RequestMapping(value = "/addPsn/{orgid}")
    public String addPsn(Model model, @PathVariable Integer orgid) {
        model.addAttribute("orgid", orgid);
        return "bus/relPsnEditEqu";
    }

    /**
     * 跳转新增机构股东页面
     */
    @RequestMapping(value = "addOrg/{orgid}")
    public String addOrg(Model model, @PathVariable Integer orgid) {
        model.addAttribute("opt", "add");
        model.addAttribute("fromPid", orgid);
        return "bus/relOrgEditEqu";
    }

    /**
     * 编辑自然人页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/editPsn/{pid}")
    public String editPsn(@PathVariable Integer pid, Model model) {

        Rel rel = relService.get(pid);
        RelPsn relPsn = relPsnService.get(rel.getPid());
        RelPsnVo relPsnVo = new RelPsnVo();
        BeanUtils.copyProperties(relPsn, relPsnVo);
        relPsnVo.setShareholdingRatio(rel.getShareholdingRatio());
        model.addAttribute("relPsn", relPsnVo);
        model.addAttribute("opt", "edit");

        return "bus/relOrgEditEqu";
    }

    /**
     * 编辑机构股东页面
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/editOrg/{pid}")
    public String edifOrg(@PathVariable Integer pid, Model model) {
        Rel rel = relService.get(pid);
        RelOrg relOrg = relOrgService.get(rel.getPid());
        RelOrgVo relOrgVo = new RelOrgVo();
        BeanUtils.copyProperties(relOrg, relOrgVo);
        relOrgVo.setShareholdingRatio(rel.getShareholdingRatio());
        model.addAttribute("relOrg", relOrgVo);
        model.addAttribute("opt", "edit");

        return "bus/relOrgEditEqu";
    }

    /**
     * 保存自然人股东
     */
    @RequestMapping(value = "/savePsn")
    @ResponseBody
    @Transactional(readOnly = true)
    public Map<String, Object> savePsn(RelPsnVo relPsnVo) {
        Map<String, Object> tips = new HashMap<String, Object>();
        Boolean isExist = false;
        Boolean isPsnExist = false;
        RelPsn psn = null;
        Rel rel = new Rel();
        rel.setToMemo(relPsnVo.getName());
        rel.setShareholdingRatio(relPsnVo.getShareholdingRatio());
        rel.setPid(relPsnVo.getRelId());
        RelPsn relPsn = new RelPsn();
        BeanUtils.copyProperties(relPsnVo, relPsn);
        if (relPsnVo.getRelId() != null) {
            isExist = relService.exists(relPsnVo.getRelId());
        }
        if (relPsnVo.getPid() != null) {
            isPsnExist = relPsnService.exists(relPsnVo.getPid());
        }
        try {
            if (isExist) {
                relPsnService.updateSelective(relPsn);
                relService.updateSelective(rel);
            } else {
                if (!isPsnExist) {
                    relPsnService.insert(relPsn);
                    psn = relPsnService.select(relPsn).get(0);
                }
                RelOrg relOrg = relOrgService.get(relPsnVo.getOrgId());
                rel.setFromPid(String.valueOf(relOrg.getPid()));
                rel.setFromType("ORG");
                rel.setFromMemo(relOrg.getOrgName());
                rel.setToPid(String.valueOf(psn.getPid()));
                rel.setToMemo(relPsnVo.getName());
                rel.setToType("PSN");
                rel.setShareholdingRatio(relPsnVo.getShareholdingRatio());
                rel.setRelSort("quanyi");
                rel.setRelCategory("gudong");
                rel.setRelCategorySub("chigu");
                relService.insert(rel);
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
     * 保存机构股东
     */
    @RequestMapping(value = "/saveOrg")
    @ResponseBody
    @Transactional(readOnly = true)
    public Map<String, Object> saveOrg(RelOrgVo relOrgVo) {
        Map<String, Object> tips = new HashMap<String, Object>();
        Boolean isExist = false;
        Boolean isOrgExist = false;
        RelOrg org = new RelOrg();
        Rel rel = new Rel();
        rel.setFromType("ORG");
        rel.setToType("ORG");
        rel.setFromPid(String.valueOf(relOrgVo.getOrgId()));
        RelDict relDict = relDictService.selectDict(relOrgVo.getRelCategory(), relOrgVo.getRelCategorySub());
        rel.setFromName(relDict.getFromName());
        rel.setToName(relDict.getToName());
        rel.setRelCategory(relOrgVo.getRelCategory());
        rel.setRelCategorySub(relOrgVo.getRelCategorySub());
        if (relOrgVo.getShareholdingRatio() != null) {
            rel.setShareholdingRatio(relOrgVo.getShareholdingRatio());
        }
        RelOrg relOrg = new RelOrg();
        BeanUtils.copyProperties(relOrgVo, relOrg);
        if (relOrgVo.getPid() != null) {
            isExist = relService.exists(relOrgVo.getRelId());
        }
        if (relOrgVo.getPid() != null) {
            isOrgExist = relOrgService.exists(relOrgVo.getPid());
        }
        if (relOrgVo.getUniscCode() != null) {
            RelOrg orgCon = new RelOrg();
            orgCon.setUniscCode(relOrgVo.getUniscCode());
            List<RelOrg> relOrgs = relOrgService.select(orgCon);
            if (relOrgs.size() > 0) {
                isOrgExist = true;
                org = relOrgs.get(0);
            }
        }
        try {
            if (isExist) {
                rel.setPid(relOrgVo.getRelId());
                relService.updateSelective(rel);
                relOrgService.updateSelective(relOrg);
            } else {
                if (!isOrgExist) {
                    relOrg.setDelflag("1");
                    relOrg.setApprovalStatus("3");
                    relOrg.setIsBlack("0");
                    relOrgService.insert(relOrg);
                    org = relOrgService.select(relOrg).get(0);
                    rel.setToPid(String.valueOf(org.getPid()));
                } else {
                    rel.setToPid(String.valueOf(org.getPid()));
                }
                rel.setDelflag("1");
                relService.insert(rel);
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
     * 保存投资机构
     */
    @RequestMapping(value = "/saveTZOrg")
    @ResponseBody
    @Transactional(readOnly = true)
    public Map<String, Object> saveTZOrg(RelOrgVo relOrgVo) {
        Map<String, Object> tips = new HashMap<String, Object>();
        Boolean isExist = false;
        Boolean isOrgExist = false;
        RelOrg org = new RelOrg();
        Rel rel = new Rel();
        rel.setToMemo(relOrgVo.getOrgName());
        rel.setShareholdingRatio(relOrgVo.getShareholdingRatio());
        rel.setPid(relOrgVo.getRelId());
        RelOrg relOrg = new RelOrg();
        BeanUtils.copyProperties(relOrgVo, relOrg);
        if (relOrgVo.getPid() != null) {
            isExist = relService.exists(relOrgVo.getRelId());
        }
        if (relOrgVo.getPid() != null) {
            isOrgExist = relOrgService.exists(relOrgVo.getPid());
        }
        try {
            if (isExist) {
                relOrgService.updateSelective(relOrg);
                relService.updateSelective(rel);
            } else {
                if (!isOrgExist) {
                    relOrgService.insert(relOrg);
                    org = relOrgService.select(relOrg).get(0);
                }
                RelOrg org1 = relOrgService.get(relOrgVo.getOrgId());
                rel.setRelSort("quanyi");
                rel.setRelCategory("touzi");
                rel.setRelCategorySub("chigu");
                rel.setShareholdingRatio(relOrgVo.getShareholdingRatio());
                rel.setToPid(String.valueOf(org.getPid()));
                rel.setToMemo(org.getOrgName());
                rel.setToType("ORG");
                rel.setFromMemo(org1.getOrgName());
                rel.setFromPid(String.valueOf(org1.getPid()));
                rel.setToType("ORG");
                relService.insert(rel);
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
     * 保存
     *
     * @param relPsn
     * @return
     */
    @RequestMapping(value = "/ffi")
    @ResponseBody
    public Map<String, Object> save(RelPsn relPsn) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Boolean isExist = relPsnService.exists(relPsn.getPid());
        try {
            if (isExist) {

                relPsnService.update(relPsn);
            } else {
                relPsnService.insert(relPsn);
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

    @RequestMapping(value = "/editAll/{pid}/{orgid}/{relId}")
    public String editAll(@PathVariable Integer pid, Model model, @PathVariable Integer orgid, @PathVariable Integer relId) {
        RelOrg relOrg = relOrgService.get(pid);
        RelOrgVo relOrgVo = new RelOrgVo();
        BeanUtils.copyProperties(relOrg, relOrgVo);
        Rel rel = relService.get(relId);
        if (rel.getShareholdingRatio() != null) {
            relOrgVo.setShareholdingRatio(rel.getShareholdingRatio());
        }
        BeanUtils.copyProperties(rel, relOrgVo);
        model.addAttribute("relOrg", relOrgVo);
        model.addAttribute("opt", "edit");
        model.addAttribute("orgid", orgid);
        model.addAttribute("relId", relId);
        model.addAttribute("paFromPid", pid);
        model.addAttribute("fromPid", orgid);
        return "bus/relOrgEditEqu";
    }

    /**
     * 保存机构附属信息
     */
    @RequestMapping(value = "/saveAffi")
    @ResponseBody
    @Transactional(readOnly = true)
    public Map<String, Object> saveAffi(RelPsnVo relPsnVo) {
        Map<String, Object> tips = new HashMap<String, Object>();
        //关联关系是否存在
        Boolean isExist = false;
        //人是否存在
        Boolean isPsnExist = false;
        Rel rel = new Rel();
        rel.setFromPid(String.valueOf(relPsnVo.getOrgId()));
        rel.setFromType("ORG");
        rel.setToType("PSN");

        if (relPsnVo.getRelCategorySub().contains(",")) {
            String toName = "";
            String fromName = "";
            String relcategorySub = relPsnVo.getRelCategorySub();
            String[] strings = relcategorySub.split(",");
            for (String str : strings) {
                RelDict relDict = relDictService.selectDict(relPsnVo.getRelCategory(), str);
                toName = toName + "," + relDict.getToName();
                fromName = fromName + "," + relDict.getFromName() + ",";
            }
            toName = toName.substring(1, toName.length());
            fromName = fromName.substring(1, fromName.length());
            rel.setFromName(fromName);
            rel.setToName(toName);
        } else {
            RelDict relDict = relDictService.selectDict(relPsnVo.getRelCategory(), relPsnVo.getRelCategorySub());
            rel.setFromName(relDict.getFromName());
            rel.setToName(relDict.getToName());
        }

        rel.setRelCategory(relPsnVo.getRelCategory());
        rel.setRelCategorySub(relPsnVo.getRelCategorySub());
        if ("C".equals(relPsnVo.getRelCategory()) && relPsnVo.getShareholdingRatio() != null) {
            rel.setShareholdingRatio(relPsnVo.getShareholdingRatio());
        }
        if ("B".equals(relPsnVo.getRelCategory())) {
            rel.setShareholdingRatio(0.0f);
        }
        RelPsn psn = new RelPsn();
        RelPsn relPsn = new RelPsn();
        BeanUtils.copyProperties(relPsnVo, relPsn);
        if (relPsnVo.getRelId() != null) {
            isExist = relService.exists(relPsnVo.getRelId());
        }
        if (relPsnVo.getPid() != null) {
            isPsnExist = relPsnService.exists(relPsnVo.getPid());
        }
        if (relPsnVo.getIdentifyNumber() != null) {
            RelPsn psnCon = new RelPsn();
            psnCon.setIdentifyNumber(relPsnVo.getIdentifyNumber());
            List<RelPsn> relPsns = relPsnService.select(psnCon);
            if (relPsns.size() > 0) {
                isPsnExist = true;
            }
        }
        try {
            if (isExist) {
                relPsnService.updateSelective(relPsn);
                rel.setPid(relPsnVo.getRelId());
                rel.setToPid(String.valueOf(relPsnVo.getPid()));
                relService.updateSelective(rel);
            } else {
                if (!isPsnExist) {
                    relPsn.setDelflag("1");
                    relPsnService.insert(relPsn);
                    psn = relPsnService.select(relPsn).get(0);
                }
                rel.setToPid(String.valueOf(psn.getPid()));
                rel.setDelflag("1");
                relService.insert(rel);
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

    /*
     * 机构关系删除
     * */
    @RequestMapping(value = "/removeRel/{relId}")
    @ResponseBody
    public Map<String, Object> removeRel(@PathVariable Integer relId) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            Rel rel1 = relService.get(relId);
            String toType = rel1.getToType();
            String toPid = rel1.getToPid();
            relService.deleteByPrimaryKey(relId);
            List<Filter> ffilters = new Filter.Builder().eq("toPid", toPid).eq("toType", toType).build();
            if (relService.exists(ffilters) || relPsnAndOrgOfProService.exists(ffilters)) {
                RelOrg relOrg = relOrgService.get(Integer.parseInt(toPid));
                relOrg.setApprovalStatus("3");
                relOrgService.updateSelective(relOrg);
                tips.put(WebCode.SUCCESS, true);
                tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
            } else {
                RelOrg relOrg = relOrgService.get(Integer.parseInt(toPid));
                relOrg.setApprovalStatus("1");
                relOrgService.updateSelective(relOrg);
                tips.put(WebCode.SUCCESS, true);
                tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
            }
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_DELETE);
        }

        return tips;
    }


    /*
     * 自然人机构关系删除
     * */
    @RequestMapping(value = "/removePsnRel/{relId}")
    @ResponseBody
    public Map<String, Object> removePsnRel(@PathVariable Integer relId) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {
            Rel rel1 = relService.get(relId);
            String toType = rel1.getToType();
            String toPid = rel1.getToPid();
            relService.deleteByPrimaryKey(relId);
            List<Filter> ffilters = new Filter.Builder().eq("toPid", toPid).eq("toType", toType).build();
            if (relService.exists(ffilters) || relPsnAndOrgOfProService.exists(ffilters)) {
                RelPsn relPsn = relPsnService.get(Integer.parseInt(toPid));
                relPsn.setStatus("3");
                relPsnService.updateSelective(relPsn);
                tips.put(WebCode.SUCCESS, true);
                tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
            } else {
                RelPsn relPsn = relPsnService.get(Integer.parseInt(toPid));
                relPsn.setStatus("1");
                relPsnService.updateSelective(relPsn);
                tips.put(WebCode.SUCCESS, true);
                tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
            }
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_DELETE);
        }

        return tips;
    }

    @RequestMapping(value = "/removeAll/{orgId}")
    @ResponseBody
    public Map<String, Object> removeAll(@PathVariable String orgId) {
        Map<String, Object> tips = new HashMap<String, Object>();
        try {
            Rel rel = new Rel();
            rel.setToPid(orgId);
            List<Rel> relList = relService.select(rel);
            if (relList.size() > 0) {
                for (Rel rel1 : relList) {
                    relService.removeRel(rel1.getFromPid(), rel1.getToPid(), rel1.getFromType(), rel1.getToType());
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
     * 查看关系机构信息页面
     */
    @RequestMapping(value = "viewOrg/{pid}/{orgid}/{relId}")
    public String viewOrg(@PathVariable Integer pid, Model model, @PathVariable Integer orgid, @PathVariable Integer relId) {
        RelOrg relOrg = relOrgService.get(pid);
        RelOrgVo relOrgVo = new RelOrgVo();
        BeanUtils.copyProperties(relOrg, relOrgVo);
        Rel rel = relService.get(relId);
        if (rel.getShareholdingRatio() != null) {
            relOrgVo.setShareholdingRatio(rel.getShareholdingRatio());
        }
        BeanUtils.copyProperties(rel, relOrgVo);
        model.addAttribute("relOrg", relOrgVo);
        model.addAttribute("opt", "edit");
        return "bus/relOrgViewEqu";
    }

    /**
     * 根据名称查询反显信息
     *
     * @param model
     * @param type
     * @return
     */
    @RequestMapping(value = "/forNameSelect/{type}")
    public String relNameSelect(Model model, @PathVariable String type) {
        model.addAttribute("type", type);
        return "bus/forNameSelect";
    }

    /**
     * 交易对手查询
     */
    @RequestMapping(value = "/selectName")
    @ResponseBody
    public Map<String, Object> queryVo(Page<ForPidEntity> page, HttpServletRequest request, String type) {
        Map<String, Object> result = new HashMap<String, Object>();
        UserProfile profile = UserProfileUtils.getUserProfile();
        String unitCode = profile.getUnitCode();
        List<Filter> filters = RequestUtils.buildFilters(request);
        filters.add(new Filter("delflag", "1"));
        filters.add(new Filter("mgerOrg", unitCode));
//        List<Filter> filter  = new Filter.Builder().like("delflag", "1").eq("mgerOrg",unitCode).build();
        ForPidEntity forPidEntity;
        List<ForPidEntity> forPidEntityList = new ArrayList<>();
        if ("psn".equals(type)) {
            filters.add(new Filter("status", "6"));
            if (relPsnService.exists(filters)) {
                List<RelPsn> select = relPsnService.select(filters);
                for (RelPsn relPsn : select) {
                    forPidEntity = new ForPidEntity();
                    forPidEntity.setPid(relPsn.getPid());   //pid
                    forPidEntity.setName(relPsn.getName());  //自然人名称
                    forPidEntity.setNumPSNType(relPsn.getIdentifyType());   //证件类型
                    forPidEntity.setNumber(new ConfidentialInfoUtil().hideAllIdCardNum(relPsn.getIdentifyNumber()));   //证件号码
                    forPidEntity.setDomesticOrForeign(relPsn.getDomesticOrForeign());   //境内外
                    forPidEntityList.add(forPidEntity);
                }
            }
        }
        if ("org".equals(type)) {
            filters.add(new Filter("approvalStatus", "6"));
            if (relOrgService.exists(filters)) {
                List<RelOrg> select = relOrgService.select(filters);
                for (RelOrg org : select) {
                    forPidEntity = new ForPidEntity();
                    forPidEntity.setPid(org.getPid());   //pid
                    forPidEntity.setName(org.getOrgName());  //机构名称
                    forPidEntity.setNumORGType(org.getIdentifyType());   //证件类型
                    forPidEntity.setNumber(org.getUniscCode());  //证件号码
                    forPidEntity.setDomesticOrForeign(org.getDomesticOrForeign());  //境内外
                    forPidEntity.setLegalRepresentative(org.getLegalPerson());  //法定代表人
                    forPidEntity.setOrgStatus(org.getStatus());  //机构状态
                    forPidEntityList.add(forPidEntity);
                }
            }
        }
        if ("pro".equals(type)) {
            filters.add(new Filter("approvalStatus", "6"));
            if (relProService.exists(filters)) {
                List<RelPro> select = relProService.select(filters);
                for (RelPro pro : select) {
                    forPidEntity = new ForPidEntity();
                    forPidEntity.setPid(pro.getPid());
                    forPidEntity.setName(pro.getProName());
                    forPidEntity.setProType(pro.getProType()); //产品分类
                    forPidEntity.setNumber(pro.getProProvider());
                    forPidEntityList.add(forPidEntity);
                }
            }
        }
        page.setTotal(forPidEntityList.size());
        int pages = (forPidEntityList.size() + page.getPageSize() - 1) / page.getPageSize();
        if (page.getPageNumber() > pages) {
            page.setPageNumber(1);
        }
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        List<ForPidEntity> forPidEntities = new ArrayList<ForPidEntity>();
        for (int i = index; i < index + page.getPageSize(); i++) {
            if (i < forPidEntityList.size()) {
                forPidEntities.add(forPidEntityList.get(i));
            }
        }
        page.setResult(forPidEntities);
        result.put(RESULT_TOTAL, page.getTotal());
        result.put(RESULT_ROWS, page.getResult());
        return result;
    }

    @RequestMapping("/pull")
    @ResponseBody
    public Map<String,Object> synUser(HttpServletRequest request) throws Exception {
        Map<String,Object> map = new HashMap<>(16);
        String appId =configs.getAppId();
        String secretKey =configs.getSecretKey();
        String url =configs.getEmpUrl();
        //这个参数的含义是拉取这个时间戳之后的数据，需要保存上一次拉取的时间，全量拉取可以传null
        String syncTime=String.valueOf(System.currentTimeMillis());
        try {
            java.util.List<com.cims.sync.dto.UserBean> list = PullUtil.pullUserFromCims(appId,secretKey,url,null);
            //计算分批次数
            int limit = (list.size() + NUMBER - 1) / NUMBER;
            //分成limit次发请求到数据库，in（）操作时   可以把多条数据分割成多组请求
            Stream.iterate(0, n -> n + 1).limit(limit).forEach(a -> {
                //拿到这个参数的流的 （a * NUMBER）后面的数据  .limit（NUMBER）->后面数据的50条  .collect(Collectors.toList()->组成一个toList
                List<com.cims.sync.dto.UserBean> userList = list.stream().skip(a * NUMBER).limit(NUMBER).collect(Collectors.toList());
                for (Object user:userList){
                    JSONObject jsonObject = (JSONObject) JSONObject.toJSON(user);
                    User sysUser = new User();
                    sysUser.setUserId(jsonObject.getString("principalId"));
                    ByteSource salt = new SimpleByteSource(sysUser.getUserId());
                    //Object md5Password = new Md5Hash("123456", salt, 1);
                    Object md5Password = new Md5Hash("Abcd1234@", salt, 1);
                    sysUser.setPassword(md5Password.toString());
                    JSONObject attr = JSONObject.parseObject(jsonObject.getString("attributes"));
                    sysUser.setUserName(attr.getString("realname"));
                    sysUser.setAccountNonExpired(1);
                    sysUser.setAccountNonLocked(0);
                    sysUser.setCredentialNonExpired(1);
                    sysUser.setEnabled(1);
                    sysUser.setCreateTime(new Date());
                    sysUser.setModifiedTime(new Date());
                    userService.insert(sysUser);
                }
            });
        }catch (Exception e){
            map.put("message","失败");
            map.put("code","9999");
            e.printStackTrace();
            return map;
        }
        map.put("message","成功");
        map.put("code","200");
        return map;
    }

}