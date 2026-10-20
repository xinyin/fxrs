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
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.sys.model.Dict;
import com.yzj.jep.sys.service.DictCateService;
import com.yzj.jep.sys.service.DictService;
import com.yzj.jep.web.code.WebCode;
import com.yzj.jep.web.controller.CrudController;

/**
 * 字典
 *
 * @author ZhangYan
 * @time 2017年6月16日 上午10:44:36
 * @company 银之杰技术有限公司
 */
@Controller
@RequestMapping("/sys/dict")
public class DictController extends CrudController<Dict> {

    Logger logger = LoggerFactory.getLogger(DictController.class);

    @Autowired
    DictService dictService;

    @Autowired
    DictCateService dictCateService;

    /**
     * 列表页面
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/list")
    public String list(Model model) {
        return "sys/dictList";
    }

    /**
     * 为列表页面做数据准备
     */
    @Override
    protected void prepareQuery(Page<Dict> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        dictService.query(page, filters, orders);
    }

    /**
     * 新增页面
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/add")
    public String add(String dictCateCode, Model model) {

        Dict dict = new Dict();
        dict.setDictCateCode(dictCateCode);
        model.addAttribute("dict", dict);

        return "sys/dictEdit";
    }

    /**
     * 编辑页面
     *
     * @param code
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/edit/{dictCode}")
    public String edit(@PathVariable String dictCode, Model model) {

        Dict dict = dictService.get(dictCode);
        model.addAttribute("dict", dict);
        model.addAttribute("opt", "edit");

        return "sys/dictEdit";
    }

    /**
     * 查看页面
     *
     * @param code
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/view/{dictCode}")
    public String view(@PathVariable String dictCode, Model model) {

        Dict dict = dictService.get(dictCode);
        model.addAttribute("dict", dict);

        return "sys/dictView";
    }

    /**
     * 保存
     *
     * @param dict
     * @return
     */
    @RequestMapping(value = "/save")
    @ResponseBody
    public Map<String, Object> save(Dict dict) {

        Map<String, Object> tips = new HashMap<String, Object>();
        boolean b = XssCheck.reflect(dict);
        if (!b) {
            tips.put(WebCode.SUCCESS, false);
            tips.put(WebCode.MSG, "数据格式不规范，请检查!");
            return tips;
        }
        Boolean isExist = dictService.exists(dict.getDictCode());
        try {
            if (isExist) {
                dictService.update(dict);
            } else {
                dictService.insert(dict);
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
     * @param code
     * @return
     */
    @RequestMapping(value = "/remove/{code}")
    @ResponseBody
    public Map<String, Object> remove(@PathVariable String code) {

        Map<String, Object> tips = new HashMap<String, Object>();
        try {
            this.dictService.deleteByPrimaryKey(code);
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
     * 验证提交的dictCode是否已经存在数据库中
     *
     * @param dictCode
     * @return
     */
    @RequestMapping(value = "/check", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> check(String dictCode) {

        Map<String, Object> tips = new HashMap<String, Object>();

        Dict exist = dictService.get(dictCode);
        if (exist == null) {
            tips.put("valid", true);
        } else {
            tips.put("valid", false);
        }

        return tips;
    }

}
