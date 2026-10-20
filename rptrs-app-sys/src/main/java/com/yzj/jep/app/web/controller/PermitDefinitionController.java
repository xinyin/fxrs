package com.yzj.jep.app.web.controller;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.yzj.jep.app.sys.dto.PermitTypeDto;
import com.yzj.jep.app.sys.model.Function;
import com.yzj.jep.app.sys.service.FunctionService;
import com.yzj.jep.commons.CollectionUtils;
import com.yzj.jep.core.factory.ServiceFactory;
import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.orm.entity.Order;
import com.yzj.jep.orm.entity.Page;
import com.yzj.jep.orm.mybatis.helper.PageHelper;
import com.yzj.jep.orm.service.EntityService;
import com.yzj.jep.sys.code.ResourceKind;
import com.yzj.jep.sys.model.Authorization;
import com.yzj.jep.sys.model.Role;
import com.yzj.jep.sys.service.AuthorizationService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取数据权限定义的 controller
 *
 * @author Michael
 * @time 2017年9月5日 下午3:40:31
 * @company 银之杰技术有限公司
 */
@Controller
@RequestMapping("/permit")
public class PermitDefinitionController {

    Logger logger = LoggerFactory.getLogger(PermitDefinitionController.class);

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private FunctionService functionService;


    private static EntityService entityService;

    static {
        entityService = ServiceFactory.createService(EntityService.class);
    }

    /**
     * 为列表页面做数据准备
     */

    protected void prepareQuery(Page<Function> page, List<Filter> filters, List<Order> orders, HttpServletRequest request) {
        functionService.query(page, filters, orders);
    }

    /**
     * 登录页面
     *
     * @param model
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/permitTypes")
    @ResponseBody
    public Map<String, Object> permitTypes(Page<Function> page, String menuCode, String roleCode, String type, Model model, HttpServletRequest request) throws Exception {
        Map<String, Object> data = new HashMap<>();
        List<PermitTypeDto> items = new ArrayList<>();
        /*只有当menuCode不等于空的时候，才是正确的请求*/
        List<Authorization> authorizations = null;
        List<Function> functionList = Lists.newArrayList();
        PageHelper.start(page);
        if (StringUtils.isNotBlank(menuCode)) {
            functionList = functionService.selectAll();
            List<Filter> filters = new ArrayList<>();
            filters.add(new Filter("auzTarget", roleCode));
            filters.add(new Filter("kind", ResourceKind.FP.name()));
            filters.add(new Filter("menuCode", menuCode));
            filters.add(new Filter("auzType", type));
            authorizations = authorizationService.select(filters);
            List<String> funCodes = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(authorizations)) {
                //把该角色菜单下所有授权的按钮code放入集合
                for (Authorization au : authorizations) {
                    funCodes.add(au.getResourceCode());
                }
            }
            //遍历所有按钮，重新封装页面选中反显
            for (Function fun : functionList) {
                //有权限选中
                if (funCodes.contains(fun.getButtonCode())) {
                    items.add(new PermitTypeDto(fun.getButtonName(), fun.getButtonCode(), true));
                } else {
                    items.add(new PermitTypeDto(fun.getButtonName(), fun.getButtonCode(), false));
                }
            }
        }
        PageInfo pageInfo = new PageInfo(items);
//		PageInfo pageInfo = new PageInfo(items);
        data.put("total", functionService.count());
        data.put("rows", pageInfo.getList());

		/*data.put("total",items.size());
		data.put("rows", items);*/
        return data;
    }
}
