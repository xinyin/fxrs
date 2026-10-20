package com.yzj.jep.app.util;


import com.yzj.jep.app.bus.model.Product;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 产品树形工具类--只适用于本项目下产品管理
 */

public class MenuTree {
    public static Map<String, Object> mapArray = new LinkedHashMap<String, Object>();
    public List<Product> menuCommon;
    public List<Object> list = new ArrayList<Object>();

    public List<Object> menuList(List<Product> products) {
        this.menuCommon = products;
        for (Product x : products) {
            Map<String, Object> mapArr = new LinkedHashMap<String, Object>();
            if (x.getPaProId() == 0) {
                mapArr.put("id", x.getPid());
                mapArr.put("pId", x.getPaProId());
                mapArr.put("name", x.getProName());
                mapArr.put("children", menuChild(x.getPid()));
                list.add(mapArr);
            }
        }
        return list;
    }

    public List<?> menuChild(int id) {
        List<Object> lists = new ArrayList<Object>();
        for (Product a : menuCommon) {
            Map<String, Object> childArray = new LinkedHashMap<String, Object>();
            if (a.getPaProId() == id) {
                childArray.put("id", a.getPid());
                childArray.put("pId", a.getPaProId());
                childArray.put("name", a.getProName());
                childArray.put("children", menuChild(a.getPid()));
                lists.add(childArray);
            }
        }
        return lists;
    }
}
