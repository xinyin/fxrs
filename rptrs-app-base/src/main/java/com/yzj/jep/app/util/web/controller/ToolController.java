package com.yzj.jep.app.util.web.controller;

import com.yzj.jep.orm.entity.Filter;
import com.yzj.jep.sys.model.Unit;
import com.yzj.jep.sys.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * 包含各种工具方法的controller
 */
@Controller
@RequestMapping("/utli/tool")
public class ToolController {

    @Autowired
    UnitService unitService;

    /**
     * 将各个部门或机构的pathCode写入的方法
     *
     */
    @RequestMapping(value = "/organizationTree")
    public void dept() {


        /**
         * 因数据库分库所以需要先登录各自主体的账号，访问url会自动将pathCode填写
         * ——selectAll()；虽然是全查数据但是分库插件往sql后拼入当前登陆人主体，
         * 需登录不同主体人员，才能将所有数据跑完。
         */

        List<Unit> unitList = unitService.selectAll();
        for (Unit u : unitList) {
            String pathCode = "";
            Unit forUnit = u;
            if ("".equals(u.getPathCode()) && null != u.getParentId()) {
                do {
                    pathCode = "." + forUnit.getParentId() + "." + pathCode;
                    forUnit = unitService.get(forUnit.getParentId());
                    if (null == forUnit) {
                        break;
                    }
                } while (!"".equals(forUnit.getParentId()));
                u.setPathCode(pathCode + "." + u.getUnitCode() + ".");
                unitService.updateSelective(u);
            }

        }

    }
}