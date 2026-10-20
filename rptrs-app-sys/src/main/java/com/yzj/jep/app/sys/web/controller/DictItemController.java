/*
 * Powered By xTOOLs
 * Dev by DuRisk@yzj.com
 * Copyright © 2009 - 2017 yzj.com. All rights reserved.
 *
 * Generated at 2017年06月19日 14:44:02
 *
 */

package com.yzj.jep.app.sys.web.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.yzj.jep.app.sys.util.XssCheck;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzj.jep.core.exception.ServiceException;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.sys.model.DictItem;
import com.yzj.jep.sys.service.DictCateService;
import com.yzj.jep.sys.service.DictItemService;
import com.yzj.jep.sys.service.DictService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;
import com.yzj.jep.web.entity.BasicNameValuePair;
import com.yzj.jep.web.entity.NameValuePair;

@Controller
@RequestMapping("/sys/dictItem")
public class DictItemController extends CrudController<DictItem> {

    Logger logger = LoggerFactory.getLogger(DictItemController.class);

    @Autowired
    DictItemService dictItemService;

    @Autowired
    DictService dictService;

    @Autowired
    DictCateService dictCateService;

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<DictItem> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        dictItemService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(Model model, String dictCode, String dictCateCode) {

        DictItem dictItem = new DictItem();
        dictItem.setDictCateCode(dictCateCode);
        dictItem.setDictCode(dictCode);
        model.addAttribute("dictItem", dictItem);

        return "sys/dictItemEdit";
    }

    /**
     * 编辑页面
     *
     * @param itemId
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{itemId}")
    public String edit(@PathVariable String itemId, Model model) {

        DictItem dictItem = dictItemService.get(itemId);
        model.addAttribute("dictItem", dictItem);
        model.addAttribute("opt", "edit");

        return "sys/dictItemEdit";
    }

    /**
     * 查看页面
     *
     * @param itemId
     * @param model
     * @return
     */
    @RequestMapping(value = "/view/{itemId}")
    public String view(@PathVariable String itemId, Model model) {

        DictItem dictItem = dictItemService.get(itemId);
        model.addAttribute("dictItem", dictItem);

        return "sys/roleStdView";
    }

    /**
     * 保存
     *
     * @param dictItem
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(DictItem dictItem) {

        Map<String, Object> tips = new HashMap<String, Object>();
        boolean b = XssCheck.reflect(dictItem);
        if (!b) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "数据格式不规范，请检查!");
            return tips;
        }
        try {
            if (StringUtils.isEmpty(dictItem.getItemId())) {
                dictItem.setItemId(UUID.randomUUID().toString().replace("-",""));
                dictItemService.insert(dictItem);
            } else {
                dictItemService.update(dictItem);
            }
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_SAVE);
        } catch (ServiceException e) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_SAVE);
        }

        return tips;
    }

    /**
     * 删除
     *
     * @param itemId
     * @return
     */
    @RequestMapping(value = "/remove/{itemId}")
    @ResponseBody
    public Map<String, Object> remove(@PathVariable String itemId) {

        Map<String, Object> tips = new HashMap<String, Object>();
        try {
            dictItemService.deleteByPrimaryKey(itemId);
            tips.put(WebCode.SUCCESS, true);
            tips.put(WebCode.MSG, WebCode.SUCCESS_DELETE);
        } catch (ServiceException e) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, WebCode.FAILURE_DELETE);
        }

        return tips;
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "/check", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> check(String itemId) {

        Map<String, Object> tips = new HashMap<String, Object>();

        DictItem exist = dictItemService.get(itemId);
        if (exist == null) {
            tips.put("valid", true);
        } else {
            tips.put("valid", false);
        }
        return tips;
    }

    /**
     * 根据字典编码 查询字典
     *
     * @param simpleName
     * @param excludes
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getDictItems/{simpleName}")
    @ResponseBody
    public List<NameValuePair> getDictItems(@PathVariable String simpleName, @RequestParam(value = "excludes", required = false) String excludes) throws Exception {
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
                    BasicNameValuePair nv = new BasicNameValuePair(item.getItemLabel(), item.getItemValue());
                    items.add(nv);
                }
            }

            return items;
        } catch (ServiceException se) {
            logger.error("系统出现异常：" + se.getMessage());
            throw se;
        }
    }

}
