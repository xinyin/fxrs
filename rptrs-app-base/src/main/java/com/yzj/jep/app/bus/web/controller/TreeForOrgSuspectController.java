package com.yzj.jep.app.bus.web.controller;


import com.alibaba.fastjson.JSONObject;
import com.yzj.jep.app.bus.model.*;
import com.yzj.jep.app.bus.service.*;
import com.yzj.jep.app.bus.vo.AffiliateVo;
import com.yzj.jep.app.bus.vo.SaveTreeMsgVo;
import com.yzj.jep.app.bus.vo.TreeNodeVo;
import com.yzj.jep.commons.StringUtils;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.entity.TreeNode;
import com.yzj.jep.web.util.TreeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/bus/treeSuspect")
public class TreeForOrgSuspectController {

    private static Logger logger = LoggerFactory.getLogger(TreeForOrgController.class);

    @Autowired
    private TreeForOrgService treeForOrgService;

    @Autowired
    private RelPsnService relPsnService;

    @Autowired
    private RelOrgService relOrgService;

    @Autowired
    private RelDicMapService relDicMapService;

    @Autowired
    private RelService relService;


    @RequestMapping(value = "/treeForPsnOrShare")
    public String list(Model model, HttpServletRequest request) {
        return "bus/treeForSuspectPsnOrShare";
    }


    @RequestMapping(value = "/allNode")
    @ResponseBody
    public List<Object> treeForOrg() {
        JSONObject jsonObject = new JSONObject();
        List<Object> listForObj = new ArrayList<>();
        Map<String, Object> node = new HashMap<>();
        Integer pid = 7777;
        RelOrg relOrg = new RelOrg();
        Map<String, Object> map = new HashMap<>();
        List<Filter> listForFilter = new Filter.Builder().eq("pid", pid).eq("delflag", 1).build();
        List<RelOrg> select = relOrgService.select(listForFilter);
        if (select != null && select.size() > 0) {
            relOrg = select.get(0);
        }
        map.put("pid", pid);
        map.put("name", relOrg.getOrgName());
        map.put("to_type", "ORG");
        List<Object> objects = this.selectForFrom(map);
        node.put("pid", pid);
        node.put("name", relOrg.getOrgName());
        node.put("type", "ORG");
        node.put("target", "main");
        node.put("children", objects);

        listForObj.add(node);
        return listForObj;
    }

    public List<Object> selectForFrom(Map<String, Object> map) {
        List<Object> treeForOrgVoList = new ArrayList<>();
        List<Map<String, Object>> pid1 = new ArrayList<>();
        if (!map.isEmpty()) {
            if (map.containsKey("pid") && map.containsKey("to_type")) {
                if ("PSN".equals(map.get("to_type"))) {
                    List<Map<String, Object>> pid = treeForOrgService.querySusOrgForPsnFrom(new Integer(String.valueOf(map.get("pid"))));
                    pid1 = treeForOrgService.querySusPsnForPsnFrom(new Integer(String.valueOf(map.get("pid"))));
                    pid1.addAll(pid);
                } else {
                    List<Map<String, Object>> pid = treeForOrgService.querySusOrgForOrgFrom(new Integer(String.valueOf(map.get("pid"))));
                    pid1 = treeForOrgService.querySusPsnForOrgFrom(new Integer(String.valueOf(map.get("pid"))));
                    pid1.addAll(pid);
                }
                for (int i = 0; i < pid1.size(); i++) {
                    List<Object> treeForOrgVoList1 = this.selectForFrom(pid1.get(i));
                    Map<String, Object> objectMap = new HashMap<>();
                    if (pid1.get(i).containsKey("name") && pid1.get(i).containsKey("rel_category")) {
                        String name = pid1.get(i).get("name").toString();
                        objectMap.put("name", name);
                        String relCategory = pid1.get(i).get("rel_category").toString();
                        objectMap.put("relCategory", relCategory);
                        List<Filter> filterForRelDicMap = new Filter.Builder().eq("subCode", relCategory).build();
                        List<RelDicMap> select = relDicMapService.select(filterForRelDicMap);
                        if (select != null && select.size() > 0) {
                            objectMap.put("nameAndType", name + "(" + select.get(0).getSubName() + ")");
                            objectMap.put("name", name + "(" + select.get(0).getSubName() + ")");
                            if (pid1.get(i).containsKey("rel_type")) {
                                if ("3".equals(pid1.get(i).get("rel_type"))) {
                                    if (pid1.get(i).containsKey("shareholding_ratio")) {
                                        objectMap.put("nameAndType", name + "(" + select.get(0).getSubName() + "," + pid1.get(i).get("shareholding_ratio") + "%)");
                                        objectMap.put("name", name + "(" + select.get(0).getSubName() + "," + pid1.get(i).get("shareholding_ratio") + "%)");
                                    }
                                }
                            }
                        }
                    }
                    if (pid1.get(i).containsKey("rel_category_sub")) {
                        objectMap.put("relCateorySub", pid1.get(i).get("rel_category_sub").toString());
                    }
                    if (pid1.get(i).containsKey("to_type")) {
                        objectMap.put("type", pid1.get(i).get("to_type").toString());
                    }
                    if (pid1.get(i).containsKey("pid")) {
                        objectMap.put("pid", pid1.get(i).get("pid"));
                    }
                    if (pid1.get(i).containsKey("shareholding_ratio")) {
                        objectMap.put("shareholdingRatio", pid1.get(i).get("shareholding_ratio"));
                    }
                    objectMap.put("pidAndType", pid1.get(i).get("pid").toString() + "," + pid1.get(i).get("to_type").toString());
                    if (pid1.get(i).containsKey("relPid")) {
                        objectMap.put("relPid", pid1.get(i).get("relPid"));
                    }
                    if (pid1.get(i).containsKey("shareholding_strand")) {
                        objectMap.put("shareholdingStrand", pid1.get(i).get("shareholding_strand"));
                    }
                    if (pid1.get(i).containsKey("shareholders_code")) {
                        objectMap.put("shareholdersCode", pid1.get(i).get("shareholders_code"));
                    }
                    if (pid1.get(i).containsKey("identify_type")) {
                        objectMap.put("identifyType", pid1.get(i).get("identify_type"));
                    }
                    if (pid1.get(i).containsKey("identify_number")) {
                        objectMap.put("identifyNumber", pid1.get(i).get("identify_number"));
                    }
                    objectMap.put("target", "child");
                    objectMap.put("children", treeForOrgVoList1);
                    treeForOrgVoList.add(objectMap);

                }
            }
        }
        return treeForOrgVoList;
    }


    /*
     * 树形展示
     * */
    @RequestMapping(value = "/treeNode")
    @ResponseBody
    public List<TreeNode> treeNode(String parentId, Model model, HttpServletRequest request) {
        List<TreeNodeVo> treeForOrgVoList = new ArrayList<>();
        if (StringUtils.isEmpty(parentId)) {
            List<Filter> filterForOrg = new Filter.Builder().eq("pid", 7777).eq("delflag", 1).build();
            List<RelOrg> select = relOrgService.select(filterForOrg);
            RelOrg relOrg = new RelOrg();
            if (select != null && select.size() > 0) {
                relOrg = select.get(0);
                TreeNodeVo treeNodeVo = new TreeNodeVo();
                treeNodeVo.setPid(relOrg.getPid());
                treeNodeVo.setType("ORG");
                treeNodeVo.setPidAndType(relOrg.getPid() + "," + "ORG");
                treeNodeVo.setName(relOrg.getOrgName());
                treeForOrgVoList.add(treeNodeVo);
            }
        } else {
            List<Map<String, Object>> pid1 = new ArrayList<>();
            List<String> pidAType = new ArrayList<>();
            String[] pidType = parentId.split(",");
            for (String s : pidType) {
                pidAType.add(s);
            }
            if ("PSN".equals(pidAType.get(1))) {
                List<Map<String, Object>> pid2 = treeForOrgService.queryOrgForPsnFrom(new Integer(pidAType.get(0)));
                pid1 = treeForOrgService.queryPsnForPsnFrom(new Integer(pidAType.get(0)));
                pid1.addAll(pid2);
            } else {
                List<Map<String, Object>> pid2 = treeForOrgService.queryOrgForOrgFrom(new Integer(pidAType.get(0)));
                pid1 = treeForOrgService.queryPsnForOrgFrom(new Integer(pidAType.get(0)));
                pid1.addAll(pid2);
            }
            for (int i = 0; i < pid1.size(); i++) {
                HashMap<String, Object> map = new HashMap<>();
                TreeNodeVo treeNodeVo = new TreeNodeVo();
                if (pid1.get(i).containsKey("name") && pid1.get(i).containsKey("rel_category")) {
                    String name = pid1.get(i).get("name").toString();
                    treeNodeVo.setName(name);
                    String relCategory = pid1.get(i).get("rel_category").toString();
                    treeNodeVo.setRelCategory(relCategory);
                    List<
                            Filter> filterForRelDicMap = new Filter.Builder().eq("subCode", relCategory).build();
                    List<RelDicMap> select = relDicMapService.select(filterForRelDicMap);
                    if (select != null && select.size() > 0) {
                        treeNodeVo.setNameAndType(name + "(" + select.get(0).getSubName() + ")");
                        treeNodeVo.setName(name + "(" + select.get(0).getSubName() + ")");
                        if (pid1.get(i).containsKey("rel_type")) {
                            if ("3".equals(pid1.get(i).get("rel_type"))) {
                                if (pid1.get(i).containsKey("shareholding_ratio")) {
                                    treeNodeVo.setNameAndType(name + "(" + select.get(0).getSubName() + "," + pid1.get(i).get("shareholding_ratio") + "%)");
                                    treeNodeVo.setName(name + "(" + select.get(0).getSubName() + "," + pid1.get(i).get("shareholding_ratio") + "%)");
                                }
                            }
                        }
                    }
                }
                if (pid1.get(i).containsKey("rel_category_sub")) {
                    treeNodeVo.setRelCategorySub(pid1.get(i).get("rel_category_sub").toString());
                }
                if (pid1.get(i).containsKey("to_type")) {
                    treeNodeVo.setType(pid1.get(i).get("to_type").toString());
                }
                if (pid1.get(i).containsKey("pid")) {
                    treeNodeVo.setPid(new Integer(String.valueOf(pid1.get(i).get("pid"))));
                }
                if (pid1.get(i).containsKey("shareholding_ratio")) {
                    if (!StringUtils.isEmpty(pid1.get(i).get("shareholding_ratio").toString())) {
                        Float fl = Float.parseFloat(pid1.get(i).get("shareholding_ratio").toString());
                        treeNodeVo.setShareholdingRatio(fl);
                    }
                }
                treeNodeVo.setPidAndType(pid1.get(i).get("pid").toString() + "," + pid1.get(i).get("to_type").toString());
                if (pid1.get(i).containsKey("relPid")) {
                    treeNodeVo.setRelPid(new Integer(String.valueOf(pid1.get(i).get("relPid"))));
                }
                if (pid1.get(i).containsKey("shareholding_strand")) {
                    treeNodeVo.setShareholdingStrand(pid1.get(i).get("shareholding_strand").toString());
                }
                if (pid1.get(i).containsKey("shareholders_code")) {
                    treeNodeVo.setShareholdersCode(pid1.get(i).get("shareholders_code").toString());
                }
                if (pid1.get(i).containsKey("identify_type")) {
                    treeNodeVo.setIdentifyType(pid1.get(i).get("identify_type").toString());
                }
                if (pid1.get(i).containsKey("identify_number")) {
                    treeNodeVo.setIdentifyNumber(pid1.get(i).get("identify_number").toString());
                }
                treeForOrgVoList.add(treeNodeVo);
            }
        }

        String[] columns = {"pid", "name", "type", "relCategory", "relCategorySub", "shareholdingRatio", "pidAndType", "nameAndType", "shareholdingStrand", "relPid", "shareholdersCode", "identifyType", "identifyNumber"};
        List<TreeNode> nodes = TreeUtils.parse(treeForOrgVoList, columns);
        return nodes;
    }


    /*
     * 查询关联方信息
     * */
    @RequestMapping(value = "/selectForAffiliateMessage")
    @ResponseBody
    public List<AffiliateVo> selectForAffiliateMessage(String name, String shareCode, String identifyType, String identifyNumber) {
        List<AffiliateVo> affiliateVos = new ArrayList<>();
        RelOrgSuspect relOrg = new RelOrgSuspect();
        RelPsnSuspect relPsn = new RelPsnSuspect();
        if (!StringUtils.isEmpty(name)) {
            relOrg.setOrgName(name);
            relPsn.setName(name);
        }
        if (!StringUtils.isEmpty(shareCode)) {
            relOrg.setShareholdersCode(shareCode);
            relPsn.setShareholdersCode(shareCode);
        }
        if (!StringUtils.isEmpty(identifyType)) {
            relOrg.setIdentifyType(identifyType);
            relPsn.setIdentifyType(identifyType);
        }
        if (!StringUtils.isEmpty(identifyNumber)) {
            relOrg.setUniscCode(identifyNumber);
            relPsn.setIdentifyNumber(identifyNumber);
        }
        affiliateVos = treeForOrgService.selectAffiliateMessageForSusOrg(relOrg);
        List<AffiliateVo> affiliateVos2 = treeForOrgService.selectAffiliateMessageForSusPsn(relPsn);
        affiliateVos.addAll(affiliateVos2);

        return affiliateVos;
    }


    /*
     * 查询疑似关联方信息
     * */
    @RequestMapping(value = "/selectSusForAffiliateMessage")
    @ResponseBody
    public List<AffiliateVo> selectSusForAffiliateMessage(String name, String shareCode, String identifyType, String identifyNumber) {
        List<AffiliateVo> affiliateVos = new ArrayList<>();
        RelOrgSuspect relOrg = new RelOrgSuspect();
        RelPsnSuspect relPsn = new RelPsnSuspect();
        if (!StringUtils.isEmpty(name)) {
            relOrg.setOrgName(name);
            relPsn.setName(name);
        }
        if (!StringUtils.isEmpty(shareCode)) {
            relOrg.setShareholdersCode(shareCode);
            relPsn.setShareholdersCode(shareCode);
        }
        if (!StringUtils.isEmpty(identifyType)) {
            relOrg.setIdentifyType(identifyType);
            relPsn.setIdentifyType(identifyType);
        }
        if (!StringUtils.isEmpty(identifyNumber)) {
            relOrg.setUniscCode(identifyNumber);
            relPsn.setIdentifyNumber(identifyNumber);
        }
        affiliateVos = treeForOrgService.selectAffiliateMessageForSusOrg(relOrg);
        List<AffiliateVo> affiliateVos2 = treeForOrgService.selectAffiliateMessageForSusPsn(relPsn);
        affiliateVos.addAll(affiliateVos2);

        return affiliateVos;
    }


    @RequestMapping(value = "/viewRelCategory")
    @ResponseBody
    public List<String> viewRelCategory(String relCategoryId, String type) {
        List<String> list = new ArrayList<>();
        if (!StringUtils.isEmpty(relCategoryId) && !StringUtils.isEmpty(type)) {
            List<Filter> filters = new Filter.Builder().eq("parentCode", relCategoryId).eq("subRelType", type).build();
            List<Order> orders = new Order.Builder().asc("subCode").build();
            List<RelDicMap> select = relDicMapService.select(filters, orders);
            if (select != null && select.size() > 0) {
                for (RelDicMap relDicMap : select) {
                    list.add(String.valueOf(relDicMap.getSubCode()));
                }
            }
        }
        return list;
    }

    /*
     * 保存
     * 1，保存关联关系  tpid等指向疑似  susPsn等
     * 2，工作流中，新增机构 并修改关系表  删除原来的
     *
     * * */
    @RequestMapping(value = "/saveRelMsg", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> saveRelMsg(SaveTreeMsgVo saveTreeMsgVo) {
        //将指向的机构或者自然人变成 SUSPSN  or SUSORG
        if ("ORG".equals(saveTreeMsgVo.getToType()) || "org".equals(saveTreeMsgVo.getToType())) {
            saveTreeMsgVo.setToType("SUSORG");
        }
        if ("PSN".equals(saveTreeMsgVo.getToType()) || "psn".equals(saveTreeMsgVo.getToType())) {
            saveTreeMsgVo.setToType("SUSPSN");
        }
        Map<String, Object> objectMap = treeForOrgService.saveRelSusMsg(saveTreeMsgVo);
        return objectMap;
    }

    /*
     * 删除关联关系
     * */
    @RequestMapping(value = "/delRelMsg", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> delRelMsg(SaveTreeMsgVo saveTreeMsgVo) {
        //将指向的机构或者自然人变成 SUSPSN  or SUSORG
        Map<String, Object> tips = new HashMap<String, Object>();
        Integer pid = saveTreeMsgVo.getRelPid();
        if (StringUtils.isEmpty(pid + "")) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        }
        Integer i = relService.deleteByPrimaryKey(pid);
        if (i == 1) {
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } else {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        }
        return tips;
    }

}
