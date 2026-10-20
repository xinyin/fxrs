/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月23日 13:59:31
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
import com.yzj.jep.orm.entity.Operator;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;


@Controller
@RequestMapping("/bus/relPsnOfPro")
public class RelPsnOfProController extends CrudController<RelPsn> {
    private static Logger logger = LoggerFactory.getLogger(RelPsnOfProController.class);

    @Autowired
    private RelPsnService relPsnService;
    @Autowired
    private RelFileService relFileService;
    @Autowired
    private RelOrgService relOrgService;
    @Autowired
    private DutyService dutyService;
    @Autowired
    private NotifyService notifyService;
    @Autowired
    private RelService relService;

    @Autowired
    private RelPsnAndOrgOfProService relPsnAndOrgOfProService;

    @Autowired
    private DictItemService dictItemService;

    @Autowired
    private AligMapService aligMapService;

    @Autowired
    private RelDictService relDictService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private RelProService relProService;

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "bus/relPsnList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<RelPsn> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {

        relPsnService.query(page, filters, orders);
    }

    /**
     * 为自然人列表页面做数据准备
     */
    @Override
    public Map<String, Object> query(Page<RelPsn> page, Model model, HttpServletRequest request) {
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
        RelPsn relPsn = new RelPsn();
        relPsn.setMgerOrg(mgerOrg);
        for (Filter f : filters) {
            if ("name".equals(f.getProperty())) {
                relPsn.setName(f.getValue().toString());
            } else if ("status".equals(f.getProperty())) {
                relPsn.setStatus(f.getValue().toString());
            } else if ("identifyType".equals(f.getProperty())) {
                relPsn.setIdentifyType(f.getValue().toString());
            } else if ("identifyNumber".equals(f.getProperty())) {
                relPsn.setIdentifyNumber(f.getValue().toString());
            } else if ("isBlack".equals(f.getProperty())) {
                relPsn.setIsBlack(f.getValue().toString());
            } else if ("dataSource".equals(f.getProperty())) {
                relPsn.setDataSource(f.getValue().toString());
            }
        }
        PageHelper.start(page);
        List<RelPsn> relPsns = relPsnService.selectPsnInformation(relPsn);
        PageInfo pageInfo = new PageInfo(relPsns);
        result.put(RESULT_TOTAL, pageInfo.getTotal());
        result.put(RESULT_ROWS, pageInfo.getList());
        return result;
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model) {
        model.addAttribute("opt", "add");
        RelPsn relPsn = new RelPsn();
        relPsn.setIdentifyType("IDCARD");
        relPsn.setRace("01");
        model.addAttribute("relPsn", relPsn);
        return "bus/relPsnAdd";
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

        RelPsn relPsn = relPsnService.get(pid);
        model.addAttribute("relPsn", relPsn);
        model.addAttribute("opt", "edit");
        model.addAttribute("op", op);
        return "bus/relPsnAddOrEdit";
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

        RelPsn relPsn = relPsnService.get(pid);
        model.addAttribute("relPsn", relPsn);
        model.addAttribute("opt", "edit");
//		return "bus/relPsnAddOrEdit";
//		return "bus/relPsnView";
        model.addAttribute("op", "1");
        return "bus/relPsnAddOrEdit";
    }

    /**
     * 保存
     *
     * @param relPsn
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(RelPsn relPsn, TraEnclosureVo traEnclosureVo) {

        Map<String, Object> tips = new HashMap<String, Object>();
//		RelPsnBlack relPsnBlack = new RelPsnBlack();
        String regulatorOth1 = relPsn.getRegulatorOth1();
        relPsn.setStatusComment("");
        //根据部门存储数据权限
        UserProfile profile = UserProfileUtils.getUserProfile();
        relPsn.setMgerOrg(profile.getUnitCode());

        Boolean isExist = false;
        int index = 0;
        String relSerial = "";
        if (relPsn.getPid() != null && !"".equals(relPsn.getPid())) {
            isExist = relPsnService.exists(relPsn.getPid());
        }
        try {
            if (isExist) {
//				index = relPsnService.update(relPsn);
                index = relPsnService.updateSelective(relPsn);
                relSerial = relPsnService.get(relPsn.getPid()).getRelSerial();
            } else {
                List<RelPsn> relPsnList = relPsnService.select(relPsn);
                if (relPsnList.size() > 0) {
                    relPsn = relPsnList.get(0);
                } else {
                    relPsn.setDelflag("1");// 逻辑删除标记
                    relPsn.setStatus("1");//待补录
                    relPsn.setIsBlack("0");//是否为黑名单 “0”否
                    relPsn.setDataSource("1");//数据来源为手工录入
                    relPsn.setRelSerial(BatchNumberUtil.makeBatchNumber());
                    index = relPsnService.insert(relPsn);
                }
                relSerial = relPsn.getRelSerial();
            }
            List<Integer> docTableSelectItem = traEnclosureVo.getDocTableSelectItem();
            String merge = relPsn.getMgerOrg();
            relFileService.updateRelSerial(docTableSelectItem, relSerial, merge);
            if (index != 0) {
                List<RelPsn> relPsn1 = relPsnService.select(relPsn);
                tips.put("pid", relPsn1.get(0).getPid());
            }
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
//            tips.put(WebCode.MSG, WebCode.Is_SET_REL);

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
        RelPsn relPsn = relPsnService.get(pid);
        relPsn.setDelflag("0");
        relPsnService.updateSelective(relPsn);
        tips.put(WebCode.SUCCESS, true);
        tips.put(WebCode.MSG, "删除成功");
//        try {
//			relPsnService.deleteByPrimaryKey(pid);
//            RelService relService = SpringContextHolder.getBean(RelService.class);
//            Rel rel = new Rel();
//            rel.setToPid(pid.toString());
//            List<Rel> relList = relService.select(rel);
//            if (relList.size() > 0) {
//                for (Rel rel1 : relList) {
//                    relService.removeRel(rel1.getFromPid(), rel1.getToPid(), rel1.getFromType(), rel1.getToType());
//                }
//            } else {
//                Rel rel1 = new Rel();
//                rel1.setFromPid(pid.toString());
//                relList = relService.select(rel1);
//                if (relList.size() > 0) {
//                    for (Rel rel2 : relList) {
//                        relService.removeRel(rel2.getFromPid(), rel2.getToPid(), rel2.getFromType(), rel2.getToType());
//                    }
//                }
//            }
//            tips.put(WebCode.SUCCESS, true);
//            tips.put(WebCode.MSG, WebCode.SUCCESS_ADD_BLT);
//        } catch (ServiceException e) {
//            logger.info(e.getMessage());
//            tips.put(WebCode.SUCCESS, false);
//            tips.put(WebCode.MSG, WebCode.FAILURE_DELETE);
//        }

        return tips;
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
        RelPsn relPsn = relPsnService.get(pid);
        model.addAttribute("relPsn", relPsn);
        if ("0".equals(relPsn.getIsBlack())) {
            model.addAttribute("addOrRemoveBlack", "加入黑名单原因");
        } else {
            model.addAttribute("addOrRemoveBlack", "移出黑名单原因");
        }
        return "bus/addOrRemoveBlack";
    }

    /**
     * 加入/移出黑名单
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/addOrRemoveBlack")
    @ResponseBody
    public Map<String, Object> addBlack(RelPsn relPsn) {

        Map<String, Object> tips = new HashMap<String, Object>();
        RelPsn relPsn1 = relPsnService.get(relPsn.getPid());
        try {
            relPsn1.setStatus("4");
            relPsn1.setBlackReason(relPsn.getBlackReason());
            relPsnService.update(relPsn1);
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
        RelPsn relPsn = relPsnService.get(pid);
        result.put("relPsn", relPsn);
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
        return "bus/disconnection";
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
            RelPsn relPsn = relPsnService.get(relPsnVo.getToPid());
            relPsn.setStatus("4");
            relPsnService.update(relPsn);
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

        boolean exist = relPsnService.exists(pid);
        tips.put("valid", !exist);

        return tips;
    }

    /**
     * 自然人关系信息页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/affiliateList/{pid}")
    public String affiliateView(@PathVariable String pid, int op, Model model) {
        model.addAttribute("fromPid", pid);
        model.addAttribute("op", op);
//		model.addAttribute("opt", opt);
        return "bus/affiliateList";
    }

    /**
     * 自然人关系信息列表
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/psnOfProList/{pid}")
    @ResponseBody
    public Map<String, Object> psnOfProList(@PathVariable String pid, Model model, Page<RelPsnVo> page) {
        List<RelPsnVo> relPsnVoList = relPsnAndOrgOfProService.selectRelPsnOfProInformation(pid);
        Map<String, Object> result = new HashMap<String, Object>();
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
     * 产品关联归属自然人列表
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/psnUpOfProList/{pid}")
    @ResponseBody
    public Map<String, Object> psnUpOfProList(@PathVariable String pid, Model model, Page<RelPsnVo> page) {
        List<RelPsnVo> relPsnVoList = relPsnAndOrgOfProService.selectRelPsnUpOfProInformation(pid);
        Map<String, Object> result = new HashMap<String, Object>();
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
     * 产品关联归属自然人列表
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/psnDownOfProList/{pid}")
    @ResponseBody
    public Map<String, Object> psnDownOfProList(@PathVariable String pid, Model model, Page<RelPsnVo> page) {
        List<RelPsnVo> relPsnVoList = relPsnAndOrgOfProService.selectRelPsnDownOfProInformation(pid);
        Map<String, Object> result = new HashMap<String, Object>();
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
     * 自然人关系信息列表   下级关联人
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/affiliateDown/{pid}")
    @ResponseBody
    public Map<String, Object> affiliateDown(@PathVariable String pid, Model model, Page<RelPsnVo> page) {
        List<RelPsnVo> relPsnVoList = relPsnService.selectDownRelPsnInformation(pid);
        Map<String, Object> result = new HashMap<String, Object>();
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
     * 新增自然人关系信息页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/addAffiliate/{fromPid}")
    public String addAffiliate(@PathVariable Integer fromPid, Model model) {
        model.addAttribute("opt", "add");
        // 自然人pid
        model.addAttribute("fromPid", fromPid);
        return "bus/psnUpAddOrEdit";
    }

    /**
     * 编辑自然人关系信息页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/editAffiliate/{pid}/{relId}/{fromPid}")
    public String editAffiliate(@PathVariable Integer pid, @PathVariable Integer relId, @PathVariable Integer fromPid, Model model) {

        RelPsn relPsn = relPsnService.get(pid);
        RelService relService = SpringContextHolder.getBean(RelService.class);
        Rel rel = relService.get(relId);
        RelPsnVo relPsnVo = new RelPsnVo();
        BeanUtils.copyProperties(relPsn, relPsnVo);
        relPsnVo.setRelCategory(rel.getRelCategory());
//        if (rel.getFromPid().equals(fromPid.toString())) {
//            relPsnVo.setRelCategorySub(rel.getToName());
//        } else {
//            relPsnVo.setRelCategorySub(rel.getFromName());
//        }
//        RelDictService relDictService = SpringContextHolder.getBean(RelDictService.class);
//        List<Filter> filters = new Filter.Builder().eq("relCategorySub", relPsnVo.getRelCategorySub()).build();
//        List<RelDict> relDictList = relDictService.select(filters);
//        relPsnVo.setRelCategorySub(relDictList.get(0).getRelCategorySubCode());
        relPsnVo.setRelCategorySub(rel.getRelCategorySub());
        relPsnVo.setRelStatement(rel.getRelStatement());
        model.addAttribute("relPsnVo", relPsnVo);
        model.addAttribute("opt", "edit");
        model.addAttribute("pid", pid);
        model.addAttribute("relId", relId);
        model.addAttribute("fromPid", fromPid);

        return "bus/affiliateAddOrEdit";
    }

    /**
     * 编辑保存金融产品的自然人关系信息
     *
     * @param relPsnVo
     * @return
     */

    @RequestMapping(value = "/savePsnOfPros/{parentPid}/{relId}/{fromPid}")
    @ResponseBody
    public Map<String, Object> savePsnOfPros(@PathVariable String parentPid, @PathVariable Integer relId, @PathVariable String fromPid, RelPsnVo relPsnVo) {
        Map<String, Object> tips = new HashMap<String, Object>();
        //归属关联方
        RelPsn fromPsn = relPsnService.get(Integer.valueOf(parentPid));
        //新增关系to金融产品
        RelPro relPro = relProService.get(Integer.valueOf(fromPid));
//        BeanUtils.copyProperties(relPsnVo, relPsn);
        String fromAlig = fromPsn.getAligCode();
        String chAligCode = relPro.getAligCode();
        //新增关联方根据上级定为码以及关系判断是否关联方,更新to定为码
        if (StringUtils.isNotEmpty(fromAlig)) {
            //更新关系表
            RelPsnAndOrgOfPro relPsnAndOrgOfPro = new RelPsnAndOrgOfPro();
            relPsnAndOrgOfPro.setPid(relPsnVo.getRelId());
            relPsnAndOrgOfPro.setDelflag("1");
            //归属关联方pid
            relPsnAndOrgOfPro.setFromPid(parentPid);
            //添加基本信息自然人的pid
            relPsnAndOrgOfPro.setToPid(fromPid);
            relPsnAndOrgOfPro.setRelCategory(relPsnVo.getRelCategory());
            relPsnAndOrgOfPro.setRelCategorySub(relPsnVo.getRelCategorySub());
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
                    relPsnAndOrgOfPro.setFromType("PSN");
                    relPsnAndOrgOfPro.setToType("PRO");
                    relPsnAndOrgOfPro.setShareholdingRatio(relPsnVo.getShareholdingRatio());
                    //关联人设置口径
                    relPsnAndOrgOfPro.setRegulator(aligMap.getCaliber());
                    relPsnAndOrgOfPro.setRelStatus("3");
                    relPsnAndOrgOfPro.setIsValid(relPsnVo.getIsValid());
                    relPsnAndOrgOfPro.setEffectiveDate(relPsnVo.getEffectiveDate());
                    relPsnAndOrgOfPro.setIneffectiveDate(relPsnVo.getIneffectiveDate());
                    relPsnAndOrgOfPro.setRelStatement(relPsnVo.getRelStatement());
                } else {
                    //为null  代表to 不是关联方，仅仅维持关系即可
                    //关系设置定为码
                    relPsnAndOrgOfPro.setFromAligCode(alig);
                    relPsnAndOrgOfPro.setDelflag("1");
                    relPsnAndOrgOfPro.setFromType("PSN");
                    relPsnAndOrgOfPro.setToType("PRO");
                    relPsnAndOrgOfPro.setShareholdingRatio(relPsnVo.getShareholdingRatio());
                    relPsnAndOrgOfPro.setRelStatus("3");
                    relPsnAndOrgOfPro.setIsValid(relPsnVo.getIsValid());
                    relPsnAndOrgOfPro.setEffectiveDate(relPsnVo.getEffectiveDate());
                    relPsnAndOrgOfPro.setIneffectiveDate(relPsnVo.getIneffectiveDate());
                    relPsnAndOrgOfPro.setRelStatement(relPsnVo.getRelStatement());

                    //设置成待审批
                    relPro.setApprovalStatus("3");
                }
                relProService.update(relPro);
                //关系状态为待新增
                relPsnAndOrgOfPro.setRelStatus("01");
                relPsnAndOrgOfPro.setPid(relId);
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
     * 保存金融产品的自然人关系信息
     *
     * @param relPsnVo
     * @return
     */
    @RequestMapping(value = "/savePsnOfPro/{parentPid}/{fromPid}")
    @ResponseBody
    public Map<String, Object> savePsnOfPro(@PathVariable String parentPid, @PathVariable String fromPid, RelPsnVo relPsnVo) {
        Map<String, Object> tips = new HashMap<String, Object>();
        //归属关联方
        RelPsn fromPsn = relPsnService.get(Integer.valueOf(parentPid));
        //新增关系to金融产品
        RelPro relPro = relProService.get(Integer.valueOf(fromPid));
//        BeanUtils.copyProperties(relPsnVo, relPsn);
        String fromAlig = fromPsn.getAligCode();
        String chAligCode = relPro.getAligCode();
        //新增关联方根据上级定为码以及关系判断是否关联方,更新to定为码
        if (StringUtils.isNotEmpty(fromAlig)) {
            //更新关系表
            RelPsnAndOrgOfPro relPsnAndOrgOfPro = new RelPsnAndOrgOfPro();
            relPsnAndOrgOfPro.setPid(relPsnVo.getRelId());
            relPsnAndOrgOfPro.setDelflag("1");
            //归属关联方pid
            relPsnAndOrgOfPro.setFromPid(parentPid);
            //添加基本信息自然人的pid
            relPsnAndOrgOfPro.setToPid(fromPid);
            RelPsnAndOrgOfPro dbRelPsnAndOrgOfPro = relPsnAndOrgOfProService.get(relPsnAndOrgOfPro);
            relPsnAndOrgOfPro.setRelCategory(relPsnVo.getRelCategory());
            relPsnAndOrgOfPro.setRelCategorySub(relPsnVo.getRelCategorySub());
            relPsnAndOrgOfPro.setEquityShare(relPsnVo.getEquityShare());
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
                    relPsnAndOrgOfPro.setFromType("PSN");
                    relPsnAndOrgOfPro.setToType("PRO");
                    relPsnAndOrgOfPro.setShareholdingRatio(relPsnVo.getShareholdingRatio());
                    //关联人设置口径
                    relPsnAndOrgOfPro.setRegulator(aligMap.getCaliber());
                    relPsnAndOrgOfPro.setRelStatus("3");
                    relPsnAndOrgOfPro.setIsValid(relPsnVo.getIsValid());
//                    relPsnAndOrgOfPro.setEffectiveDate(relPsnVo.getEffectiveDate());
//                    relPsnAndOrgOfPro.setIneffectiveDate(relPsnVo.getIneffectiveDate());
                    relPsnAndOrgOfPro.setActiveTimeString(relPsnVo.getActiveTimeString());
                    relPsnAndOrgOfPro.setInvalidTimeString(relPsnVo.getInvalidTimeString());
                    relPsnAndOrgOfPro.setRelStatement(relPsnVo.getRelStatement());
                } else {
                    //为null  代表to 不是关联方，仅仅维持关系即可
                    //关系设置定为码
                    relPsnAndOrgOfPro.setFromAligCode(alig);
                    relPsnAndOrgOfPro.setDelflag("1");
                    relPsnAndOrgOfPro.setFromType("PSN");
                    relPsnAndOrgOfPro.setToType("PRO");
                    relPsnAndOrgOfPro.setShareholdingRatio(relPsnVo.getShareholdingRatio());
                    relPsnAndOrgOfPro.setRelStatus("3");
                    relPsnAndOrgOfPro.setIsValid(relPsnVo.getIsValid());
                    /*relPsnAndOrgOfPro.setEffectiveDate(relPsnVo.getEffectiveDate());
                    relPsnAndOrgOfPro.setIneffectiveDate(relPsnVo.getIneffectiveDate());*/
                    relPsnAndOrgOfPro.setActiveTimeString(relPsnVo.getActiveTimeString());
                    relPsnAndOrgOfPro.setInvalidTimeString(relPsnVo.getInvalidTimeString());
                    relPsnAndOrgOfPro.setRelStatement(relPsnVo.getRelStatement());

                    //设置成待审批
                    relPro.setApprovalStatus("3");
                }
                relProService.update(relPro);
                //关系状态为待新增
                relPsnAndOrgOfPro.setRelStatus("01");
                if (isExist) {
                    relPsnAndOrgOfProService.update(relPsnAndOrgOfPro);
                } else {
                    relPsnAndOrgOfProService.insert(relPsnAndOrgOfPro);
//                    relPsnAndOrgOfProService.insertNeo4jPsnToPro(fromPsn, relPro, relPsnAndOrgOfPro);
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
     * 关系自然人删除
     *
     * @param pid
     * @return
     */
    @RequestMapping(value = "/removeRelInformation/{pid}")
    @ResponseBody
    public Map<String, Object> removeRelInformation(@PathVariable Integer pid) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {

            RelPsnAndOrgOfPro rel = relPsnAndOrgOfProService.get(pid);
            String toType = rel.getToType();
            String toPid = rel.getToPid();
            relPsnAndOrgOfProService.deleteByPrimaryKey(pid);
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

    /**
     * 金融产品关系机构删除
     *
     * @param pid
     * @return
     */
    @RequestMapping(value = "/removeRelOrgInformation/{pid}")
    @ResponseBody
    public Map<String, Object> removeRelOrgInformation(@PathVariable Integer pid) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {

            RelPsnAndOrgOfPro relPsnAndOrgOfPro = relPsnAndOrgOfProService.get(pid);
            String toPid = relPsnAndOrgOfPro.getToPid();
            String toType = relPsnAndOrgOfPro.getToType();
            relPsnAndOrgOfProService.deleteByPrimaryKey(pid);
            List<Filter> ffilters = new Filter.Builder().eq("toPid", toPid).eq("toType", toType).build();
            if (relPsnAndOrgOfProService.exists(ffilters)) {
                RelPro relPro = relProService.get(Integer.parseInt(toPid));
                relPro.setApprovalStatus("3");
                relProService.updateSelective(relPro);
                tips.put(WebCode.SUCCESS, true);
                tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
            } else {
                RelPro relPro = relProService.get(Integer.parseInt(toPid));
                relPro.setApprovalStatus("1");
                relProService.updateSelective(relPro);
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

    /**
     * 删除
     *
     * @param pid
     * @return
     */
    @RequestMapping(value = "/removeRelProOfPsn/{pid}")
    @ResponseBody
    public Map<String, Object> removeRelProOfPsn(@PathVariable Integer pid) {

        Map<String, Object> tips = new HashMap<String, Object>();

        try {

            RelPsnAndOrgOfPro relPsnAndOrgOfPro = relPsnAndOrgOfProService.get(pid);
            RelPsn relPsn = relPsnService.get(Integer.parseInt(relPsnAndOrgOfPro.getToPid()));
            relPsn.setStatus("3");
            //  00 代表已归档
            if ("00".equals(relPsnAndOrgOfPro.getRelStatus())) {
                //改为 归档-删除
                relPsnAndOrgOfPro.setRelStatus("03");
                relPsnAndOrgOfProService.updateSelective(relPsnAndOrgOfPro);
                //将基本信息改为  待审批
                relPsnService.updateSelective(relPsn);
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

    /**
     * 机构关系信息页面
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/psnRelOrgList/{pid}")
    public String rightsList(@PathVariable String pid, int op, Model model) {
        model.addAttribute("fromPid", pid);
        model.addAttribute("op", op);
//		model.addAttribute("opt", opt);
        return "bus/psnRelOrgList";
    }

//    /**
//     * 机构关系信息列表
//     *
//     * @param orgid
//     * @param model
//     * @return
//     */
//    @RequestMapping(value = "/psnRelOrg/{orgid}")
//    @ResponseBody
//    public Map<String, Object> psnRelOrg(@PathVariable String orgid, Model model, Page<RelPsnVo> page) {
//        Map<String, Object> result = new HashMap<String, Object>();
//        PageHelper.start(page);
//        RelOrgService relOrgService = SpringContextHolder.getBean(RelOrgService.class);
//        List<RelOrgVo> tzOrgPsn = relOrgService.getTZOrgPsn(String.valueOf(orgid));
//        PageInfo pageInfo = new PageInfo(tzOrgPsn);
//        result.put(RESULT_TOTAL, pageInfo.getTotal());
//        result.put(RESULT_ROWS, pageInfo.getList());
//
//        return result;
//    }

    /**
     * 新增机构关系信息页面
     */
    @RequestMapping(value = "addPsnRelOrg/{orgid}")
    public String addOrg(Model model, @PathVariable Integer orgid) {
        model.addAttribute("opt", "add");
        model.addAttribute("fromPid", orgid);
        return "bus/psnRelOrgAddOrEdit";
    }

    /**
     * 所属 机构关系信息列表
     *
     * @param orgid
     * @param model
     * @return
     */
    @RequestMapping(value = "/psnRelOrg/{orgid}")
    @ResponseBody
    public Map<String, Object> psnRelOrg(@PathVariable String orgid, Model model, Page<RelPsnVo> page) {
        Map<String, Object> result = new HashMap<String, Object>();
        PageHelper.start(page);
        RelOrgService relOrgService = SpringContextHolder.getBean(RelOrgService.class);
        List<RelOrgVo> tzOrgPsn = relOrgService.getTZOrgPsn(String.valueOf(orgid));
        PageInfo pageInfo = new PageInfo(tzOrgPsn);
        result.put(RESULT_TOTAL, pageInfo.getTotal());
        result.put(RESULT_ROWS, pageInfo.getList());

        return result;
    }

    /**
     * 下一级 机构关系信息列表
     *
     * @param orgid
     * @param model
     * @return
     */
    @RequestMapping(value = "/psnDownRelOrg/{orgid}")
    @ResponseBody
    public Map<String, Object> psnDownRelOrg(@PathVariable String orgid, Model model, Page<RelPsnVo> page) {
        Map<String, Object> result = new HashMap<String, Object>();
        PageHelper.start(page);
        RelOrgService relOrgService = SpringContextHolder.getBean(RelOrgService.class);
        List<RelOrgVo> tzOrgPsn = relOrgService.getDownTZOrgPsn(String.valueOf(orgid));
        PageInfo pageInfo = new PageInfo(tzOrgPsn);
        result.put(RESULT_TOTAL, pageInfo.getTotal());
        result.put(RESULT_ROWS, pageInfo.getList());

        return result;
    }

    /**
     * 编辑机构关系信息页面
     *
     * @param pid
     * @param model
     * @param fromPid
     * @param relId
     * @return
     */
    @RequestMapping(value = "/editPsnRelOrg/{pid}/{fromPid}/{relId}")
    public String editAll(@PathVariable Integer pid, Model model, @PathVariable Integer fromPid, @PathVariable Integer relId) {
        RelOrgService relOrgService = SpringContextHolder.getBean(RelOrgService.class);
        RelService relService = SpringContextHolder.getBean(RelService.class);
        RelOrg relOrg = relOrgService.get(pid);
        RelOrgVo relOrgVo = new RelOrgVo();
        BeanUtils.copyProperties(relOrg, relOrgVo);
        Rel rel = relService.get(relId);
        if (rel.getShareholdingRatio() != null) {
            relOrgVo.setShareholdingRatio(rel.getShareholdingRatio());
        }
        relOrgVo.setRelCategory(rel.getRelCategory());
        if (pid.equals(rel.getFromPid())) {
            relOrgVo.setRelCategorySub(rel.getToName());
        } else {
            relOrgVo.setRelCategorySub(rel.getFromName());
        }
        relOrgVo.setRelStatement(rel.getRelStatement());
        model.addAttribute("relOrg", relOrgVo);
        model.addAttribute("opt", "edit");
        model.addAttribute("fromPid", fromPid);
        model.addAttribute("relId", relId);
        return "bus/psnRelOrgAddOrEdit";
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
        RelPsn relPsn = relPsnService.get(Integer.valueOf(fromPid));
        String fromAlig = fromOrg.getAligCode();
        String chAligCode = relPsn.getAligCode();
        //新增关联方根据上级定为码以及关系判断是否关联方,更新to定为码
        if (StringUtils.isNotEmpty(fromAlig)) {
            //更新关系表
            Rel rel = new Rel();
            rel.setPid(relOrgVo.getRelId());
            rel.setDelflag("1");
            //归属关联方pid
            rel.setFromPid(paFromPid);
            //添加基本信息机构的pid
            rel.setToPid(fromPid);
            rel.setRelCategory(relOrgVo.getRelCategory());
            rel.setRelCategorySub(relOrgVo.getRelCategorySub());
            Rel dbRel = relService.get(rel);
            rel.setRelPredicate(relOrgVo.getPredicate());//关联关系判定
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
                    rel.setToType("PSN");
                    if (relOrgVo.getShareholdingProportion() != null) {
                        rel.setShareholdingRatio(Float.parseFloat(relOrgVo.getShareholdingProportion()));
                    }
                    //关联人设置口径
                    relPsn.setRegulatorOth1(aligMap.getCaliber());
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
                relPsn.setStatus("3");
                relPsnService.updateSelective(relPsn);
                //关系状态为待新增
                rel.setRelStatus("01");
                rel.setRelStatement(relOrgVo.getRelStatement());
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

    /**
     * @param flag
     * @return
     */
    @RequestMapping(value = "/exportHandler/{flag}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void exportHandler(HttpServletRequest request, HttpServletResponse response, @PathVariable String flag) {
        Map<String, Object> tips = new HashMap<String, Object>();
        String filename = "";
        String filePath = "";
        Date date = new Date();
        if ("1".equals(flag)) {
            filename = "自然人导入模板" + "relPsn.xls";
            filePath = "D:/exportHandler/relPsn.xls";
        } else if ("2".equals(flag)) {
            filename = "机构导入模板" + "relOrg.xls";
            filePath = "D:/exportHandler/relOrg.xls";
        } else if ("3".equals(flag)) {
            filename = "疑似自然人导入模板" + "relPsnSuspect.xls";
            filePath = "D:/exportHandler/relPsnSuspect.xls";
        } else if ("4".equals(flag)) {
            filename = "疑似机构导入模板" + "relOrgSuspect.xls";
            filePath = "D:/exportHandler/relOrgSuspect.xls";
        }
        File file = new File(filePath);

        if (file.exists()) {
            OutputStream out = null;
            FileInputStream in = null;
            try {
                // 1.读取要下载的内容
                in = new FileInputStream(file);

                // 2. 告诉浏览器下载的方式以及一些设置
                // 解决文件名乱码问题，获取浏览器类型，转换对应文件名编码格式，IE要求文件名必须是utf-8, firefo要求是iso-8859-1编码
                String agent = request.getHeader("user-agent");
                if (agent.contains("FireFox")) {
                    filename = new String(filename.getBytes("UTF-8"), "iso-8859-1");
                } else {
                    filename = URLEncoder.encode(filename, "UTF-8");
                }
                // 设置下载文件的mineType，告诉浏览器下载文件类型
                String mineType = request.getServletContext().getMimeType(filename);
                response.setContentType(mineType);
                // 设置一个响应头，无论是否被浏览器解析，都下载
                response.setHeader("Content-disposition", "attachment; filename=" + filename);
                // 将要下载的文件内容通过输出流写到浏览器
                out = response.getOutputStream();
                int len = 0;
                byte[] buffer = new byte[1024];
                while ((len = in.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                }
            } catch (Exception e) {
                e.printStackTrace();
//				tips.put("valid", "下载失败");
            } finally {

                try {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
//		tips.put("valid", "下载成功");
//        return tips;
    }

    //反显，查询自然人信息
    @RequestMapping(value = "/selectRelPsn")
    @ResponseBody
    public List<RelPsn> selectRelPsn(String name, String identifyType, String identifyNumber) {
        List<RelPsn> relPsns = new ArrayList<>();
        List<Filter> filters = new Filter.Builder().eq("delflag", "1").nnl("aligCode").build();
        RelPsn relPsn = new RelPsn();
        if (!StringUtils.isEmpty(name)) {
            filters.add(new Filter("name", name));
//            relPsn.setName(name);
        }
        if (!StringUtils.isEmpty(identifyType)) {
            filters.add(new Filter("identifyType", identifyType));
//            relPsn.setIdentifyType(identifyType);
        }
        if (!StringUtils.isEmpty(identifyNumber)) {
            filters.add(new Filter("identifyNumber", identifyNumber));
//            relPsn.setIdentifyNumber(identifyNumber);
        }
        relPsns = relPsnService.select(filters);

//        affiliateVos = treeForOrgService.selectAffiliateMessageForOrg(relOrg);
//        List<AffiliateVo> affiliateVos2 = treeForOrgService.selectAffiliateMessageForPsn(relPsn);
//        affiliateVos.addAll(affiliateVos2);

        return relPsns;
    }

    /*
     * 提交审批
     * */
    @RequestMapping(value = "/send/{pid}")
    @ResponseBody
    public Map<String, Object> send(@PathVariable Integer pid, Model model) {
        Map<String, Object> tips = new HashMap<String, Object>();
        List<RelPsnVo> relPsnVoList = relPsnService.selectRelPsnInformation(pid.toString());
        List<RelOrgVo> tzOrgPsn = relOrgService.getTZOrgPsn(String.valueOf(pid));
        if (relPsnVoList.size() <= 0 && tzOrgPsn.size() <= 0) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "未设置关联关系");
            return tips;
        }
        RelPsn relPsn = relPsnService.get(pid);
        if ("3".equals(relPsn.getStatus()) || "5".equals(relPsn.getStatus())) {
            //4 代表审批中
            relPsn.setStatus("4");
            relPsnService.updateSelective(relPsn);
            tips.put("data", relPsn);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } else {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "状态不符，提交失败！");
        }
        return tips;
    }

    /*
     * 任务下发
     * */
    @RequestMapping(value = "/relPsnSendCommand")
    public String relPsnSendCommand(Model model) {

        return "bus/relPsnSend";
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
        RelPsn relPsn = relPsnService.get(filers);
        //更换管库机构为下发机构
        relPsn.setMgerOrg(unitCode);
        //更新疑似自然人状态为待补录
        relPsn.setStatus("2");
        relPsn.setStatusComment(memo);
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
                    notifyService.createRemind(duty.getStaffCode(), "自然人[" + relPsn.getName() + "]的补录任务已下发到您[" + duty.getStaffCode() + "]，请到自然人列表完成补录。", "自然人[" + relPsn.getName() + "]的补录任务已下发到您[" + duty.getStaffCode() + "]，请到自然人列表完成补录。");
                }
                relPsnService.update(relPsn);
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
     * 单一查询页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/relSinQuery")
    public String relSinQuery(Model model) {
        return "bus/relSinQueList";
    }


    /**
     * 单一查询（自然人、机构）
     *
     * @param page
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/relSinQuery1")
    @ResponseBody
    public Map<String, Object> relSinQuery(Page<PsnAndOrgVo> page, Model model, HttpServletRequest request) {
        Map<String, Object> tips = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        PsnAndOrgVo psnAndOrgVo = new PsnAndOrgVo();
        for (Filter filter : filters) {
            if ("identifyNumber".equals(filter.getProperty())) {
                psnAndOrgVo.setIdentifyNumber(filter.getValue().toString());
            } else if ("name".equals(filter.getProperty())) {
                psnAndOrgVo.setName(filter.getValue().toString());
            } else if ("identifyType".equals(filter.getProperty())) {
                psnAndOrgVo.setIdentifyType(filter.getValue().toString());
            } else if ("relCategory".equals(filter.getProperty())) {
                psnAndOrgVo.setRelCategory(filter.getValue().toString());
            } else if ("relCategorySub".equals(filter.getProperty())) {
                psnAndOrgVo.setRelCategorySub(filter.getValue().toString());
            }
        }
        //新建一个list集合，用以存储列表所要展示的内容
        List<PsnAndOrgVo> psnAndOrgVoList = new ArrayList<>();
        //类表初始化的时候，不展示数据
        if (psnAndOrgVo.getName() == null && psnAndOrgVo.getIdentifyType() == null & psnAndOrgVo.getIdentifyNumber() == null) {
            PageHelper.start(page);
            PageInfo pageInfo = new PageInfo(psnAndOrgVoList);
            tips.put(RESULT_TOTAL, pageInfo.getTotal());
            tips.put(RESULT_ROWS, pageInfo.getList());
            return tips;
        }

        //设置一个ID值，用以查询关联关系
        String toPid = null;

        //relIsExist-->判断关系是否存在-->存在true，不存在为false
        Boolean relIsExist = false;

        //新建第一种可能性—————查询自然人
        RelPsn relPsn = new RelPsn();
        if (psnAndOrgVo.getName() != null) {
            relPsn.setName(psnAndOrgVo.getName());
        }
        if (psnAndOrgVo.getIdentifyType() != null) {
            relPsn.setIdentifyType(psnAndOrgVo.getIdentifyType());
        }
        if (psnAndOrgVo.getIdentifyNumber() != null) {
            relPsn.setIdentifyNumber(psnAndOrgVo.getIdentifyNumber());
        }

        //查询自然人是否存在
        List<RelPsn> relPsns = relPsnService.selectPsnInformation(relPsn);
        if (relPsns != null && relPsns.size() > 0) {
            psnAndOrgVo.setPid(String.valueOf(relPsns.get(0).getPid()));
            psnAndOrgVo.setName(relPsns.get(0).getName());
            psnAndOrgVo.setDomesticOrForeign(relPsns.get(0).getDomesticOrForeign());
            psnAndOrgVo.setMgerOrg(relPsns.get(0).getMgerOrg());
            psnAndOrgVo.setIdentifyType(relPsns.get(0).getIdentifyType());
            psnAndOrgVo.setIdentifyNumber(relPsns.get(0).getIdentifyNumber());
            psnAndOrgVo.setCaliber(relPsns.get(0).getRegulatorOth1());
            psnAndOrgVo.setIsBlack(relPsns.get(0).getIsBlack());
            psnAndOrgVoList.add(psnAndOrgVo);
            toPid = relPsns.get(0).getPid().toString();
        }

        //新建第二种可能性————查询机构
        RelOrg relOrg = new RelOrg();
        if (psnAndOrgVo.getName() != null) {
            relOrg.setOrgName(psnAndOrgVo.getName());
        }
        if (psnAndOrgVo.getIdentifyType() != null) {
            relOrg.setIdentifyType(psnAndOrgVo.getIdentifyType());
        }
        if (psnAndOrgVo.getIdentifyNumber() != null) {
            relOrg.setUniscCode(psnAndOrgVo.getIdentifyNumber());
        }
        //查询机构是否存在
        List<RelOrg> relOrgs = relOrgService.selectList(relOrg);
        if (relOrgs != null && relOrgs.size() > 0) {
            psnAndOrgVo.setPid(String.valueOf(relOrgs.get(0).getPid()));
            psnAndOrgVo.setName(relOrgs.get(0).getOrgName());
            psnAndOrgVo.setDomesticOrForeign(relOrgs.get(0).getDomesticOrForeign());
            psnAndOrgVo.setMgerOrg(relOrgs.get(0).getMgerOrg());
            psnAndOrgVo.setIdentifyType(relOrgs.get(0).getIdentifyType());
            psnAndOrgVo.setIdentifyNumber(relOrgs.get(0).getUniscCode());
            psnAndOrgVo.setCaliber(relOrgs.get(0).getRegulatorOth1());
            psnAndOrgVo.setIsBlack(relOrgs.get(0).getIsBlack());
            psnAndOrgVoList.add(psnAndOrgVo);
            toPid = relOrgs.get(0).getPid().toString();
        }

        //判断用户是否选择关联关系和关系-->选中为true，不选为false
        Boolean isExist = false;

        if (!StringUtils.isEmpty(psnAndOrgVo.getRelCategory()) || !StringUtils.isEmpty(psnAndOrgVo.getRelCategorySub())) {
            isExist = true;
        }

        //通过获得的ID值，作为toPid去关联表获取该自然人或机构与上级的关联关系
        List<PsnAndOrgVo> relPsnOrOrgs = null;
        if (toPid != null) {
            relPsnOrOrgs = relService.selectPsnOrOrg(toPid);
        }
        if (relPsnOrOrgs != null && relPsnOrOrgs.size() > 0) {
            for (PsnAndOrgVo relPsnOrOrg : relPsnOrOrgs) {
                if (isExist == true) {
                    if (psnAndOrgVo.getRelCategory() != null) {
                        if ((psnAndOrgVo.getRelCategory()).equals(relPsnOrOrg.getRelCategory())) {
                            if (psnAndOrgVo.getRelCategorySub() != null) {
                                if ((psnAndOrgVo.getRelCategorySub()).equals(relPsnOrOrg.getRelCategorySub())) {
                                    relIsExist = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        if (isExist == false || relIsExist == true) {
            //分页展示所查数据
            PageHelper.start(page);
            PageInfo pageInfo = new PageInfo(psnAndOrgVoList);
            tips.put(RESULT_TOTAL, pageInfo.getTotal());
            tips.put(RESULT_ROWS, pageInfo.getList());
            return tips;
        } else {
            PageHelper.start(page);
            PageInfo pageInfo = new PageInfo<>(new ArrayList<>());
            tips.put(RESULT_TOTAL, pageInfo.getTotal());
            tips.put(RESULT_ROWS, pageInfo.getList());
            return tips;
        }

    }

    /**
     * 批量查询页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/relBatQuery")
    public String relBatQuery(Model model) {
        System.out.println("11111");
        return "bus/relBatQueList";
    }


    /**
     * 批量查询（自然人，机构）
     *
     * @param page
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/relBatQuery1")
    @ResponseBody
    public Map<String, Object> relBatQuery1(Page<PsnAndOrgVo> page, Model model, HttpServletRequest request) {
        Map<String, Object> tips = new HashMap<>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        PsnAndOrgVo psnAndOrgVo = new PsnAndOrgVo();

        for (Filter filter : filters) {
            if ("caliber".equals(filter.getProperty())) {
                psnAndOrgVo.setCaliber(filter.getValue().toString());
            } else if ("relCategory".equals(filter.getProperty())) {
                psnAndOrgVo.setRelCategory(filter.getValue().toString());
            } else if ("fromRel".equals(filter.getProperty())) {
                psnAndOrgVo.setFromRel(filter.getValue().toString());
            } else if ("relCategorySub".equals(filter.getProperty())) {
                psnAndOrgVo.setRelCategorySub(filter.getValue().toString());
            } else if ("isBlack".equals(filter.getProperty())) {
                psnAndOrgVo.setIsBlack(filter.getValue().toString());
            } else if ("memGroup".equals(filter.getProperty())) {
                psnAndOrgVo.setMemGroup(filter.getValue().toString());
            } else if ("groupName".equals(filter.getProperty())) {
                psnAndOrgVo.setGroupName(filter.getValue().toString());
            } else if ("stateOwned".equals(filter.getProperty())) {
                psnAndOrgVo.setStateOwned(filter.getValue().toString());
            }
        }

        //新建一个list集合，用以存储列表所要展示的内容
        List<PsnAndOrgVo> psnAndOrgVoList = new ArrayList<>();

        //类表初始化的时候，不展示数据
        if (psnAndOrgVo.getName() == null && psnAndOrgVo.getCaliber() == null && psnAndOrgVo.getMemGroup() == null
                && psnAndOrgVo.getGroupName() == null && psnAndOrgVo.getStateOwned() == null
                && psnAndOrgVo.getFromRel() == null && psnAndOrgVo.getRelCategory() == null
                && psnAndOrgVo.getRelCategorySub() == null && psnAndOrgVo.getIsBlack() == null) {
            tips = limitPages(page, tips, psnAndOrgVoList);
            return tips;
        }


        List<Filter> orgFilters = new ArrayList<>();
        List<RelOrg> relOrgs = new ArrayList<>();

        List<Filter> relFilters = new ArrayList<>();
        List<Rel> rels = new ArrayList<>();

        List<Filter> psnFilters = new ArrayList<>();
        List<RelPsn> relPsns = new ArrayList<>();

        //判断是否选择关联关系
        if (psnAndOrgVo.getFromRel() != null || psnAndOrgVo.getRelCategory() != null || psnAndOrgVo.getRelCategorySub() != null) {
            if (psnAndOrgVo.getFromRel() != null) {
                relFilters.add(new Filter("fromMemo", Operator.LIKE, psnAndOrgVo.getFromRel()));
            }
            if (psnAndOrgVo.getRelCategory() != null) {
                relFilters.add(new Filter("relCategory", Operator.LIKE, psnAndOrgVo.getRelCategory()));
            }
            if (psnAndOrgVo.getRelCategorySub() != null) {
                relFilters.add(new Filter("relCategorySub", Operator.LIKE, psnAndOrgVo.getRelCategorySub()));
            }
            rels = relService.select(relFilters);
        } else {
            rels = null;
        }
        //relIsExist-->判断关系是否存在-->存在true，不存在为false
        Boolean relIsExist = false;
        if (rels != null && rels.size() > 0) {
            relIsExist = true;
        }

        //若有选择归属关联方主体、关联关系类别、关系
        if (relIsExist) {
            //若有选择集团号、集团名称、 是否国有企业，则判定所要查询为机构
            if (psnAndOrgVo.getMemGroup() != null || psnAndOrgVo.getGroupName() != null || psnAndOrgVo.getStateOwned() != null) {
                if (psnAndOrgVo.getMemGroup() != null) {
                    orgFilters.add(new Filter("memGroup", Operator.LIKE, psnAndOrgVo.getMemGroup()));
                }
                if (psnAndOrgVo.getGroupName() != null) {
                    orgFilters.add(new Filter("groupName", Operator.LIKE, psnAndOrgVo.getGroupName()));
                }
                if (psnAndOrgVo.getStateOwned() != null) {
                    orgFilters.add(new Filter("stateOwned", Operator.LIKE, psnAndOrgVo.getStateOwned()));
                }
                if (psnAndOrgVo.getCaliber() != null) {
                    orgFilters.add(new Filter("caliber", Operator.LIKE, psnAndOrgVo.getCaliber()));
                }
                if (psnAndOrgVo.getIsBlack() != null) {
                    orgFilters.add(new Filter("isBlack", Operator.LIKE, psnAndOrgVo.getIsBlack()));
                }
                //查询关联下的所有机构
                relOrgs = relOrgService.select(orgFilters);
                //relIsExist-->判断关联是否存在-->存在true，不存在为false
                Boolean orgIsExist = false;
                if (rels != null && rels.size() > 0) {
                    orgIsExist = true;
                }
                if (orgIsExist) {
                    for (Rel r : rels) {
                        for (RelOrg org : relOrgs) {
                            if ((r.getToPid()).equals(org.getPid().toString())) {
                                PsnAndOrgVo psnAndOrgVo1 = queryPsnOrOrg(org, null);
                                psnAndOrgVoList.add(psnAndOrgVo1);
                            }
                        }
                    }
                }
                tips = limitPages(page, tips, psnAndOrgVoList);
                return tips;
            }
            //若没有选择集团号、集团、是否国企，则进行枚举
            else {
                //查询自然人
                if (!StringUtils.isEmpty(psnAndOrgVo.getCaliber())) {
                    psnFilters.add(new Filter("caliber", Operator.LIKE, psnAndOrgVo.getCaliber()));
                }
                if (!StringUtils.isEmpty(psnAndOrgVo.getIsBlack())) {
                    psnFilters.add(new Filter("isBlack", Operator.LIKE, psnAndOrgVo.getIsBlack()));
                }
                relPsns = relPsnService.select(psnFilters);
                if (relPsns != null && relPsns.size() > 0) {
                    for (RelPsn psn : relPsns) {
                        for (Rel r : rels) {
                            if ((r.getToPid()).equals(psn.getPid().toString())) {
                                PsnAndOrgVo psnAndOrgVo1 = queryPsnOrOrg(null, psn);
                                psnAndOrgVoList.add(psnAndOrgVo1);
                            }
                        }
                    }
                }
                //若所查询的是机构
                if (!StringUtils.isEmpty(psnAndOrgVo.getCaliber())) {
                    orgFilters.add(new Filter("caliber", Operator.LIKE, psnAndOrgVo.getCaliber()));
                }
                if (!StringUtils.isEmpty(psnAndOrgVo.getIsBlack())) {
                    orgFilters.add(new Filter("isBlack", Operator.LIKE, psnAndOrgVo.getIsBlack()));
                }
                relOrgs = relOrgService.select(orgFilters);
                if (relOrgs != null && relOrgs.size() > 0) {
                    for (RelOrg org : relOrgs) {
                        if (relIsExist) {
                            for (Rel r : rels) {
                                if ((r.getToPid()).equals(org.getPid().toString())) {
                                    PsnAndOrgVo psnAndOrgVo1 = queryPsnOrOrg(org, null);
                                    psnAndOrgVoList.add(psnAndOrgVo1);
                                }
                            }
                        }
                    }
                }
                limitPages(page, tips, psnAndOrgVoList);
                return tips;
            }
        }
        //若没有选择关联关系
        else {
            //若有选择集团号、集团名称、 是否国有企业，则判定所要查询为机构
            if (psnAndOrgVo.getMemGroup() != null || psnAndOrgVo.getGroupName() != null || psnAndOrgVo.getStateOwned() != null) {
                if (psnAndOrgVo.getMemGroup() != null) {
                    orgFilters.add(new Filter("memGroup", Operator.LIKE, psnAndOrgVo.getMemGroup()));
                }
                if (psnAndOrgVo.getGroupName() != null) {
                    orgFilters.add(new Filter("groupName", Operator.LIKE, psnAndOrgVo.getGroupName()));
                }
                if (psnAndOrgVo.getStateOwned() != null) {
                    orgFilters.add(new Filter("stateOwned", Operator.LIKE, psnAndOrgVo.getStateOwned()));
                }
                if (psnAndOrgVo.getCaliber() != null) {
                    orgFilters.add(new Filter("caliber", Operator.LIKE, psnAndOrgVo.getCaliber()));
                }
                if (psnAndOrgVo.getIsBlack() != null) {
                    orgFilters.add(new Filter("isBlack", Operator.LIKE, psnAndOrgVo.getIsBlack()));
                }
                //查询关联下的所有机构
                relOrgs = relOrgService.select(orgFilters);
                //relIsExist-->判断关联是否存在-->存在true，不存在为false
                Boolean orgIsExist = false;
                if (relOrgs != null && relOrgs.size() > 0) {
                    orgIsExist = true;
                }
                if (orgIsExist) {
                    for (RelOrg org : relOrgs) {
                        PsnAndOrgVo psnAndOrgVo1 = queryPsnOrOrg(org, null);
                        psnAndOrgVoList.add(psnAndOrgVo1);
                    }
                    tips = limitPages(page, tips, psnAndOrgVoList);
                    return tips;
                }
            }
            //若没有选择集团号、集团、是否国企，则进行枚举
            else {
                //查询自然人
                if (!StringUtils.isEmpty(psnAndOrgVo.getCaliber())) {
                    psnFilters.add(new Filter("caliber", Operator.LIKE, psnAndOrgVo.getCaliber()));
                }
                if (!StringUtils.isEmpty(psnAndOrgVo.getIsBlack())) {
                    psnFilters.add(new Filter("isBlack", Operator.LIKE, psnAndOrgVo.getIsBlack()));
                }
                relPsns = relPsnService.select(psnFilters);
                if (relPsns != null && relPsns.size() > 0) {
                    for (RelPsn psn : relPsns) {
                        PsnAndOrgVo psnAndOrgVo1 = queryPsnOrOrg(null, psn);
                        psnAndOrgVoList.add(psnAndOrgVo1);
                    }
                }

                //若所查询的是机构
                if (!StringUtils.isEmpty(psnAndOrgVo.getCaliber())) {
                    orgFilters.add(new Filter("caliber", Operator.LIKE, psnAndOrgVo.getCaliber()));
                }
                if (!StringUtils.isEmpty(psnAndOrgVo.getIsBlack())) {
                    orgFilters.add(new Filter("isBlack", Operator.LIKE, psnAndOrgVo.getIsBlack()));
                }
                relOrgs = relOrgService.select(orgFilters);
                if (relOrgs != null && relOrgs.size() > 0) {
                    for (RelOrg org : relOrgs) {
                        if (relIsExist) {
                            PsnAndOrgVo psnAndOrgVo1 = queryPsnOrOrg(org, null);
                            psnAndOrgVoList.add(psnAndOrgVo1);
                        }
                    }
                }
            }
            tips = limitPages(page, tips, psnAndOrgVoList);
            return tips;
        }
    }

    //用于为批量查询提供数据支持
    public PsnAndOrgVo queryPsnOrOrg(RelOrg org, RelPsn psn) {
        PsnAndOrgVo psnAndOrgVo = new PsnAndOrgVo();
        if (org != null) {
            psnAndOrgVo.setPid(String.valueOf(org.getPid()));
            psnAndOrgVo.setName(org.getOrgName());
            psnAndOrgVo.setDomesticOrForeign(org.getDomesticOrForeign());
            psnAndOrgVo.setMgerOrg(org.getMgerOrg());
            psnAndOrgVo.setIdentifyType(org.getIdentifyType());
            psnAndOrgVo.setIdentifyNumber(org.getUniscCode());
            psnAndOrgVo.setCaliber(org.getRegulatorOth1());
            psnAndOrgVo.setIsBlack(org.getIsBlack());
        }
        if (psn != null) {
            psnAndOrgVo.setPid(String.valueOf(psn.getPid()));
            psnAndOrgVo.setName(psn.getName());
            psnAndOrgVo.setDomesticOrForeign(psn.getDomesticOrForeign());
            psnAndOrgVo.setMgerOrg(psn.getMgerOrg());
            psnAndOrgVo.setIdentifyType(psn.getIdentifyType());
            psnAndOrgVo.setIdentifyNumber(psn.getIdentifyNumber());
            psnAndOrgVo.setCaliber(psn.getRegulatorOth1());
            psnAndOrgVo.setIsBlack(psn.getIsBlack());
        }
        return psnAndOrgVo;
    }

    //分页
    public Map<String, Object> limitPages(Page<PsnAndOrgVo> page, Map<String, Object> tips, List<PsnAndOrgVo> psnAndOrgVoList) {
        page.setTotal(psnAndOrgVoList.size());
        int pages = (psnAndOrgVoList.size() + page.getPageSize() - 1) / page.getPageSize();
        if (page.getPageNumber() > pages) {
            page.setPageNumber(1);
        }
        page.setPages(pages);
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        List<PsnAndOrgVo> relPsnVos = new ArrayList<PsnAndOrgVo>();
        for (int i = index; i < index + page.getPageSize(); i++) {
            if (i < psnAndOrgVoList.size()) {
                relPsnVos.add(psnAndOrgVoList.get(i));
            }
        }
        page.setResult(relPsnVos);
        tips.put(RESULT_TOTAL, page.getTotal());
        tips.put(RESULT_ROWS, page.getResult());
        return tips;
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
    public List<NameValuePair> getDictItems(String relCategory, String type) throws Exception {
        List<NameValuePair> items = new ArrayList<>();
        try {

            List<Filter> filters = new Filter.Builder().eq("dictCode", "BUS-Rel").build();
            List<DictItem> list = dictItemService.select(filters);
            String includes = "";
            //添加关联方为人
            if ("p".equals(type)) {
                //亲属关系
                if ("A".equals(relCategory)) {
                    includes = "1,2,3,4,23";
                }//工作关系
                if ("B".equals(relCategory)) {
                    includes = "5,6,7,8";
                }//其他关系
                else if ("E".equals(relCategory)) {
                    includes = "21";
                }//投资关系
                else if ("C".equals(relCategory)) {
                    includes = "9,10";
                }//被投资关系
                else if ("D".equals(relCategory)) {
                    includes = "17,18,19,20";
                }
            }//添加关联方为机构
            else if ("o".equals(type)) {
                //工作关系
                if ("B".equals(relCategory)) {
                    includes = "5,6,7,8";
                }//投资关系
                else if ("C".equals(relCategory)) {
                    includes = "9,10";
                }//被投资关系
                else if ("D".equals(relCategory)) {
                    includes = "17,18,19,20";
                }//其他关系
                else if ("E".equals(relCategory)) {
                    includes = "22";
                }
            } else if ("r".equals(type)) {
                //工作关系
                if ("C".equals(relCategory)) {
                    includes = "24,25,26,27";
                }//投资关系
                else if ("D".equals(relCategory)) {
                    includes = "28,29,30,31";
                }//投资关系
                else if ("C,D".equals(relCategory)) {
                    includes = "24,25,26,27,28,29,30,31";
                }
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
     * 金融产品自然人关系信息列表   下级关联人
     *
     * @param pid
     * @param model
     * @return
     */
    @RequestMapping(value = "/psnOfProDown/{pid}")
    @ResponseBody
    public Map<String, Object> psnOfProDown(@PathVariable String pid, Model model, Page<RelPsnVo> page) {
        //通过金融产品ID查询归属自然人
//        selectRelPsnOfProInformation();
        List<RelPsnVo> relPsnVoList = relPsnService.selectDownRelPsnInformation(pid);
        Map<String, Object> result = new HashMap<String, Object>();
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
}