/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2020 yzj.com. All rights reserved.
 *
 * Generated at 2020年03月23日 13:59:30
 *
 */

package com.yzj.jep.app.bus.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.app.util.BatchNumberUtil;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.core.holder.SpringContextHolder;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictItemService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/bus/quota")
public class QuotaController extends CrudController<RelOrg> {
    private static Logger logger = LoggerFactory.getLogger(QuotaController.class);

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

    /**
     * 获取列表页面
     *
     * @return 列表页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "bus/quotaList";
    }

    @Override
    public Map<String, Object> query(Page<RelOrg> page, Model model, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Filter> filters = RequestUtils.buildFilters(request);
        List<Order> orders = RequestUtils.buildOrders(request);
        // prepareQuery(page, filters, orders, request);
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
        PageHelper.start(page);
//        List<RelOrg> relOrgs = relOrgService.selectList(relOrg);
        List<RelOrg> relOrgs = relOrgService.selectListQuota(relOrg);
        PageInfo pageInfo = new PageInfo(relOrgs);
        result.put(RESULT_TOTAL, pageInfo.getTotal());
        result.put(RESULT_ROWS, pageInfo.getList());
        return result;
    }

    /**
     * 为列表页面做数据准备
     */
   /* @Override
    protected void prepareQuery(Page<RelOrg> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        filters.add(new Filter("delflag", "1"));
//        filters.add(new Filter("presetLimit",""));
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
    }*/
    @Override
    protected void prepareQuery(Page<RelOrg> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        filters.add(new Filter("delflag", "1"));
//        filters.add(new Filter("presetLimit",""));
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
        return "bus/quotaEdit";
    }

    /**
     * 编辑页面
     *
     * @param
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{pid}")
    public String edit(@PathVariable Integer pid, Model model) {

        RelOrg org = relOrgService.get(pid);
        RelOrgVo relOrg = new RelOrgVo();
        BeanUtils.copyProperties(org, relOrg);
        if ("1".equals(org.getIsBlack())) {
            relOrg.setBlackReason("好多原因");
        }
        model.addAttribute("relOrg", relOrg);
        model.addAttribute("opt", "edit");

        return "bus/quotaEdit";
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

//        return "bus/relOrgEdit";
//        return "bus/relOrgView";
        return "bus/quotaViewCope";
    }

    /**
     * 保存
     * 作废 无效
     * @param relOrg
     * @return
     */
    /*@RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(RelOrg relOrg, TraEnclosureVo traEnclosureVo) {

        Map<String, Object> tips = new HashMap<String, Object>();
        Boolean isExist = false;
        String relSerial = "";
        Integer index = 0;
        if (relOrg.getPid() != null) {
            isExist = relOrgService.exists(relOrg.getPid());
        }
        try {
            if (isExist) {
                relOrgService.updateSelective(relOrg);
                relSerial = relPsnService.get(relOrg.getPid()).getRelSerial();
            } else {
                List<RelOrg> relOrgList = relOrgService.select(relOrg);
                if (relOrgList.size() > 0) {
                    relOrg = relOrgList.get(0);
                } else {
                    relOrg.setApprovalStatus("3");
                    relOrg.setDelflag("1");
                    relOrg.setRelSerial(BatchNumberUtil.makeBatchNumber());
                    relOrg.setIsInsider("1");
                    index = relOrgService.insert(relOrg);
                    relOrg = relOrgService.select(relOrg).get(0);
                    Rel rel = new Rel();
                    rel.setToPid(String.valueOf(relOrg.getPid()));
                }
                relSerial = relOrg.getRelSerial();
            }
            List<Integer> docTableSelectItem = traEnclosureVo.getDocTableSelectItem();
            String merge=relOrg.getMgerOrg();
            relFileService.updateRelSerial(docTableSelectItem, relSerial,merge);
            if (index != 0) {
                List<RelOrg> relorg = relOrgService.select(relOrg);
                tips.put("pid", relorg.get(0).getPid());
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
    }*/


    /**
     * 更新
     *
     * @param relOrg
     * @param traEnclosureVo
     * @return
     */

    @RequestMapping(value = "/update")
    @ResponseBody
    public Map<String, Object> update(RelOrg relOrg, TraEnclosureVo traEnclosureVo) {

        Map<String, Object> tips = new HashMap<String, Object>();
        String uniscCode = relOrg.getUniscCode();
        //   String validStatus = relOrg.getValidStatus();
        ArrayList<Filter> list = new ArrayList<>();
        list.add(Filter.eq("uniscCode", uniscCode));
        //  list.add(Filter.eq("validStatus",validStatus));
        list.add(Filter.eq("isInsider", "1"));

        RelOrg rel = relOrgService.get(list);

        try {
            if (rel != null) {
                //update
                rel.setPresetLimit(relOrg.getPresetLimit());
                rel.setValidStatus(relOrg.getValidStatus());

                relOrgService.update(rel);
                tips.put(WebCode.SUCCESS, true);
                tips.put(WebCode.MSG, WebCode.SUCCESS_UPDATE);
            } else {
                //tips
                logger.info("uniscCode: " + uniscCode + "不是内部人");
                tips.put(WebCode.SUCCESS, false);
                tips.put(WebCode.MSG, uniscCode + "不是内部人");
            }
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
            //relOrgService.deleteByPrimaryKey(pid);
            //  RelOrg relOrg = new RelOrg();
            /*relOrgService.selectByPid(pid);*/

            List<Filter> filters = new Filter.Builder().eq("pid", pid).build();
            List<RelOrg> relOrgs = relOrgService.select(filters);
            RelOrg relOrg = relOrgs.get(0);
            relOrg.setDelflag("0");
            relOrg.setPresetLimit(null);
            relOrgService.update(relOrg);
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
     * 验证提交内部交易新增法人信息
     *
     * @param uniscCode
     * @return
     */
    @RequestMapping(value = "/uniscCode", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> uniscCode(String uniscCode) {

        Map<String, Object> tips = new HashMap<String, Object>();

        tips.put("InputUniscCode", uniscCode);


        List<RelOrg> relOrgs = relOrgService.select(new Filter.Builder().eq("uniscCode", uniscCode).build());

        if (relOrgs.size() >= 1) {
            RelOrg relOrg = relOrgs.get(0);
            tips.put("name", relOrg.getLegalPerson());
            tips.put("type", relOrg.getIdentifyType());
            tips.put("number", relOrg.getUniscCode());
        } else {
            //
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

        boolean exist = relOrgService.exists(pid);
        tips.put("valid", !exist);

        return tips;
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
            return "bus/relPsnEditTZ";
        } else {
            return "bus/relPsnEditGZ";
        }

    }

    /**
     * 附属信息页面加载
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
     * 删除
     *
     * @param relId
     * @return
     */
    /*@RequestMapping(value = "/removeOrg/{relId}")
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
    }*/

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


}