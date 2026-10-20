/*
 * Powered By xTOOLs
 * Development by xtools@xprogrammer.net
 * Copyright © 2009 - 2017 xprogrammer.net. All rights reserved.
 *
 * Generated at 2017年12月26日 09:07:29
 *
 */


package com.yzj.jep.app.sys.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Treeable;
import com.yzj.jep.sys.model.MetaRegion;
import com.yzj.jep.sys.service.MetaRegionService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.entity.TreeNode;
import com.yzj.jep.web.util.TreeUtils;

@Controller
@RequestMapping("/sys/metaRegion")
public class MetaRegionController extends CrudController<MetaRegion> {
    private static Logger logger = LoggerFactory.getLogger(MetaRegionController.class);

    @Autowired
    private MetaRegionService metaRegionService;


    /**
     * 页面跳转到metaRegionTree
     *
     * @param model
     * @return 树显示页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {
        return "sys/metaRegionTree";
    }


    @RequestMapping(value = "/dialog")
    public String dialog(Model model) {
        return "sys/metaRegionDlg";
    }

    /**
     * 移除元数据区域，元数据的分类。节点
     *
     * @param regionId
     * @return
     */
    @RequestMapping(value = "/removeNode/{regionId}")
    @ResponseBody
    public Map<String, Object> removeNode(@PathVariable String regionId) {
        Map<String, Object> tips = new HashMap<String, Object>();

        /*查询当前节点是否有子节点*/
        long count = metaRegionService.count(new Filter.Builder().eq("parentId", regionId).build());

        /*总数大于0，提示先删除子节点，反之进行删除*/
        if (count > 0) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.DELETE_SUBNODE_FIRST);
        } else {
            try {
                metaRegionService.deleteByPrimaryKey(regionId);
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
     * 重命名节点
     *
     * @param regionId 节点key
     * @param newName  节点新的名称
     * @return
     */
    @RequestMapping(value = "/renameNode/{regionId}")
    @ResponseBody
    public Map<String, Object> renameNode(@PathVariable String regionId, String newName) {
        Map<String, Object> tips = new HashMap<String, Object>();
        try {
            MetaRegion metaRegion = new MetaRegion();
            metaRegion.setRegionId(regionId);
            metaRegion.setRegionName(newName);
            metaRegionService.update(metaRegion, new String[]{"MetaRegionName"});

            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_RENAME);
        } catch (Exception ex) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAULURE_RENAME);
        }
        return tips;
    }

    /**
     * 新增节点
     *
     * @param metaRegion
     * @return
     */
    @RequestMapping(value = "/updateNode")
    @ResponseBody
    public Map<String, Object> updateNode(MetaRegion metaRegion) {
        Map<String, Object> tips = new HashMap<String, Object>();
        try {
            /*判断当前对象是否存在, 不存在就添加*/
            Boolean exist = metaRegionService.exists(metaRegion.getRegionId());
            if (exist) {
                metaRegionService.update(metaRegion);
            } else {
                metaRegionService.insertMetaRegion(metaRegion);
            }

            tips.put(Treeable.TREE_ID, metaRegion.getRegionId());
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } catch (Exception ex) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        }
        return tips;
    }

    /**
     * 初始化加载数据
     *
     * @param parentId
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/treeNode")
    @ResponseBody
    public List<TreeNode> treeNode(String parentId, Model model, HttpServletRequest request) {
        List<Filter> filters = null;

        if (StringUtils.isBlank(parentId)) {
            filters = new Filter.Builder().nl("parentId").build();
        } else {
            filters = new Filter.Builder().eq("parentId", parentId).build();
        }

        List<Order> orders = new Order.Builder().asc("disorder").build();
        List<MetaRegion> list = metaRegionService.select(filters, orders);

        String[] columns = new String[]{
                "regionId",
                "regionCode",
                "regionName",
                "regionDesc",
                "tablespaceId",
                "tablespaceCode",
                "parentId",
                "pathCode",
                "pathName",
                "status",
                "hierarchy",
                "disorder",
                "tableCount",
        };

        List<TreeNode> nodes = TreeUtils.parse(list, columns);
        return nodes;
    }

    /**
     * 验证提交的regionCode是否已经存在数据库中
     *
     * @param regionCode
     * @return
     */
    @RequestMapping(value = "/check", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> check(String regionCode) {

        Map<String, Object> tips = new HashMap<String, Object>();

        List<Filter> filters = new Filter.Builder().eq("regionCode", regionCode).build();
        Boolean isExist = metaRegionService.exists(filters);
        tips.put("valid", !isExist);
        return tips;
    }

    /**
     * 验证提交的regionCode是否已经存在数据库中
     *
     * @param regionCode
     * @return
     */
    @RequestMapping(value = "/getMetaRegion", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> getMetaRegion(String regionId) {
        Map<String, Object> tips = new HashMap<String, Object>();
        MetaRegion metaRegion = metaRegionService.get(regionId);
        tips.put("metaRegion", metaRegion);
        return tips;
    }

    /**
     * 根据角色获得其对应的表，把这些表附加到数据区域树上
     *
     * @return 权限控制主页面
     */
    @RequestMapping(value = "/getRegionTreeByRoleCode")
    @ResponseBody
    public List<TreeNode> getRegionTreeByRoleCode(String parentId, Model model, HttpServletRequest request, String roleCode) {
        List<TreeNode> nodes = new ArrayList<TreeNode>();
        /*只有当权限ID不为空的时候才进行查询*/
        if (StringUtils.isNotBlank(roleCode)) {
            List<Treeable> regionTreeList = metaRegionService.getMetaRegionTreeByRoleCodeAndRegionId(roleCode, parentId);
            String[] columns = new String[]{
                    "type"
            };
            nodes = TreeUtils.parse(regionTreeList, columns);
        }

        return nodes;
    }
}