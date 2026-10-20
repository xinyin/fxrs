/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年06月16日 16:09:26
 *
 */

package com.yzj.jep.app.sys.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yzj.jep.app.sys.util.XssCheck;
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
import com.yzj.jep.orm.entity.Treeable;
import com.yzj.jep.sys.model.DictCate;
import com.yzj.jep.sys.service.DictCateService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.entity.TreeNode;
import com.yzj.jep.web.util.TreeUtils;

@Controller
@RequestMapping("/sys/dictCate")
public class DictCateController extends CrudController<DictCate> {

    Logger logger = LoggerFactory.getLogger(DictCateController.class);

    @Autowired
    DictCateService dictCateService;

    /**
     * 页面跳转到dictCateTree
     *
     * @param model
     * @return 树显示页面
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {

        return "sys/dictCateTree";
    }

    /**
     * 移除节点
     *
     * @param dictCateCode
     * @return
     */
    @RequestMapping(value = "/removeNode/{dictCateCode}")
    @ResponseBody
    public Map<String, Object> removeNode(@PathVariable String dictCateCode) {
        Map<String, Object> tips = new HashMap<String, Object>();

        /*查询当前节点是否有子节点*/
        long count = dictCateService.count(new Filter.Builder().eq("parentId", dictCateCode).build());

        /*总数大于0，提示先删除子节点，反之进行删除*/
        if (count > 0) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.DELETE_SUBNODE_FIRST);
        } else {
            try {
                dictCateService.deleteByPrimaryKey(dictCateCode);
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
     * @param dictCateCode 节点key
     * @param newName      节点新的名称
     * @return
     */
    @RequestMapping(value = "/renameNode/{dictCateCode}")
    @ResponseBody
    public Map<String, Object> renameNode(@PathVariable String dictCateCode, String newName) {
        Map<String, Object> tips = new HashMap<String, Object>();
        try {
            DictCate dictCate = new DictCate();
            dictCate.setDictCateCode(dictCateCode);
            dictCate.setDictCateName(newName);
            dictCateService.update(dictCate, new String[]{"DictCateName"});

            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_RENAME);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAULURE_RENAME);
        }
        return tips;
    }

    /**
     * 新增节点
     *
     * @param dictCate
     * @return
     */
    @RequestMapping(value = "/updateNode")
    @ResponseBody
    public Map<String, Object> updateNode(DictCate dictCate) {
        Map<String, Object> tips = new HashMap<String, Object>();
        boolean b = XssCheck.reflect(dictCate);
        if (!b) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "数据格式不规范，请检查!");
            return tips;
        }
        try {
            Boolean isExist = dictCateService.exists(dictCate.getDictCateCode());
            /*判断当前对象是否存在, 不存在就添加*/
            if (isExist) {
                dictCateService.update(dictCate);
            } else {
                dictCateService.insert(dictCate);
            }
            tips.put(Treeable.TREE_ID, dictCate.getDictCateCode());
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

        List<DictCate> list = dictCateService.select(filters);

        String[] columns = new String[]{
                "dictCateCode",
                "dictCateName",
                "dictCateDesc",
                "parentId",
        };

        List<TreeNode> nodes = TreeUtils.parse(list, columns);
        return nodes;
    }
}
