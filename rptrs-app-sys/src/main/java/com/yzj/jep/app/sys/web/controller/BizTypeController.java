/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年07月19日 10:51:56
 *
 */


package com.yzj.jep.app.sys.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Treeable;
import com.yzj.jep.sys.model.BizType;
import com.yzj.jep.sys.service.BizTypeService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.entity.TreeNode;
import com.yzj.jep.web.util.TreeUtils;

@Controller
@RequestMapping("/sys/bizType")
public class BizTypeController extends CrudController<BizType> {
    Logger logger = LoggerFactory.getLogger(BizTypeController.class);

    @Autowired
    private BizTypeService bizTypeService;


    /**
     * 页面跳转到bizTypeTree
     *
     * @param model
     * @return 树显示页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "sys/bizTypeTree";
    }

    @RequestMapping(value = "/dialog")
    public String dialog(Model model) {
        return "sys/bizTypeDlg";
    }

    /**
     * 移除业务种类表节点
     *
     * @param bizTypeCode
     * @return
     */
    @RequestMapping(value = "/removeNode/{bizTypeCode}")
    @ResponseBody
    public Map<String, Object> removeNode(@PathVariable String bizTypeCode) {
        Map<String, Object> tips = new HashMap<String, Object>();

        /*查询当前节点是否有子节点*/
        long count = bizTypeService.count(new Filter.Builder().eq("parentId", bizTypeCode).build());

        /*总数大于0，提示先删除子节点，反之进行删除*/
        if (count > 0) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.DELETE_SUBNODE_FIRST);
        } else {
            bizTypeService.deleteByPrimaryKey(bizTypeCode);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
        }
        return tips;
    }

    /**
     * 重命名节点
     *
     * @param bizTypeCode 节点key
     * @param newName     节点新的名称
     * @return
     */
    @RequestMapping(value = "/renameNode/{bizTypeCode}")
    @ResponseBody
    public Map<String, Object> renameNode(@PathVariable String bizTypeCode, String newName) {
        Map<String, Object> tips = new HashMap<String, Object>();
        try {
            BizType bizType = new BizType();
            bizType.setBizTypeCode(bizTypeCode);
            bizType.setBizTypeName(newName);
            bizTypeService.update(bizType, new String[]{"BizTypeName"});

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
     * @param bizType
     * @return
     */
    @RequestMapping(value = "/updateNode")
    @ResponseBody
    public Map<String, Object> updateNode(BizType bizType) {
        Map<String, Object> tips = new HashMap<String, Object>();
        try {
            BizType exist = bizTypeService.get(bizType.getBizTypeCode());
            /*判断当前对象是否存在, 不存在就添加*/
            if (exist == null) {
                bizTypeService.insert(bizType);
            } else {
                bizTypeService.update(bizType);
            }

            tips.put(Treeable.TREE_ID, bizType.getBizTypeCode());
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

        if (parentId == null) {
            filters = new Filter.Builder().nl("parentId").build();
        } else {
            filters = new Filter.Builder().eq("parentId", parentId).build();
        }

        List<BizType> list = bizTypeService.select(filters);

        String[] columns = new String[]{
                "bizTypeCode",
                "bizTypeName",
                "parentId",
                "status",
                "remark",
        };

        List<TreeNode> nodes = TreeUtils.parse(list, columns);
        return nodes;
    }
}