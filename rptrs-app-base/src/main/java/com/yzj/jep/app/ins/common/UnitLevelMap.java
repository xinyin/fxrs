package com.yzj.jep.app.ins.common;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: UnitLevelMap
 * @Author Whx
 * @Date: 2021/7/28 19:22
 * @Version 1.0
 * 总部机构与分部门的映射关系
 */
@Component
public class UnitLevelMap {

    public static Map<String, Object> unitMap = new HashMap<>();
    public static Map<String, Object> deptA = new HashMap<>();
    public static Map<String, Object> deptB = new HashMap<>();
    public static Map<String, Object> deptD = new HashMap<>();

    static {
        //初始化部门等级关系
        //A级分公司
        unitMap.put("8636", "A");//江西分公司
        unitMap.put("8633", "A");//浙江分公司
        unitMap.put("8651", "A");//四川分公司
        unitMap.put("8632", "A");//江苏分公司
        unitMap.put("8641", "A");//河南分公司
        unitMap.put("8644", "A");//广东分公司
        //B、C级分公司，B和C对应关系相同，此处设置为B
        unitMap.put("8661", "B");//陕西分公司
        unitMap.put("8610", "B");//北京分公司
        unitMap.put("8612", "B");//天津分公司
        unitMap.put("8621", "B");//辽宁分公司
        unitMap.put("8634", "B");//安徽分公司
        unitMap.put("8664", "B");//宁夏分公司
        unitMap.put("8623", "B");//黑龙江分公司
        unitMap.put("8643", "B");//湖南分公司
        unitMap.put("8637", "B");//山东分公司
        unitMap.put("8650", "B");//重庆分公司
        unitMap.put("8642", "B");//湖北分公司
        unitMap.put("8631", "B");//上海分公司
        unitMap.put("8613", "B");//河北分公司
        unitMap.put("8622", "B");//吉林分公司
        //D级 开业不满一年
        unitMap.put("8645", "D");//广西分公司

        //初始化A级分公司与总部对应关系，此处使用部门名称对应
        setListValue(deptA, "邮银业务部", "邮银业务部");
        setListValue(deptA, "个团险业务部", "个团险业务部");
        setListValue(deptA, "市场经营部", "创新业务部,市场经营部,产品精算部");
        setListValue(deptA, "机构管理部", "机构管理部");
        setListValue(deptA, "续期业务部", "续期业务部");
        setListValue(deptA, "运营管理部", "运营管理部");
        setListValue(deptA, "客户服务部", "客户服务部");
        setListValue(deptA, "信息技术部", "信息技术部");
        setListValue(deptA, "风控合规部", "风险管理部,法律合规部");
        setListValue(deptA, "人力资源部/党委组织部", "人力资源部/党委组织部");
        setListValue(deptA, "综合办公室/党委办公室", "审计部,综合办公室/党委办公室,规划建设部");
        setListValue(deptA, "财务部", "规划建设部,财务部");
        setListValue(deptA, "党委党建工作部", "党委党建工作部,工会");
        setListValue(deptA, "监察部", "纪检监察部（集团派驻）");
        setListValue(deptA, "营业部", "营业部");
        //初始化B级分公司与总部对应关系
        setListValue(deptB, "邮银业务部", "邮银业务部");
        setListValue(deptB, "市场经营部", "个团险业务部,创新业务部,市场经营部,产品精算部");
        setListValue(deptB, "机构管理部", "机构管理部");
        setListValue(deptB, "续期业务部", "续期业务部");
        setListValue(deptB, "运营管理部", "运营管理部");
        setListValue(deptB, "客户服务部", "客户服务部");
        setListValue(deptB, "信息技术部", "信息技术部");
        setListValue(deptB, "风控合规部", "风险管理部,法律合规部");
        setListValue(deptB, "人力资源部/党委组织部", "人力资源部/党委组织部");
        setListValue(deptB, "综合办公室/党委办公室", "审计部,综合办公室/党委办公室,规划建设部");
        setListValue(deptB, "财务部", "规划建设部,财务部");
        setListValue(deptB, "党委党建工作部", "党委党建工作部,工会");
        setListValue(deptB, "监察部", "纪检监察部（集团派驻）");
        setListValue(deptB, "营业部", "营业部");

        //初始化D级分公司与总部对应关系
        setListValue(deptD, "市场经营部", "邮银业务部,个团险业务部,创新业务部,市场经营部,产品精算部,机构管理部");
        setListValue(deptD, "运营管理部", "续期业务部,运营管理部,客户服务部");
        setListValue(deptD, "信息技术部", "信息技术部");
        setListValue(deptD, "风控合规部", "风险管理部,法律合规部");
        setListValue(deptD, "综合办公室/党委办公室", "人力资源部/党委组织部,审计部,综合办公室/党委办公室,规划建设部");
        setListValue(deptD, "财务部", "规划建设部,财务部");
        setListValue(deptD, "党委党建工作部", "党委党建工作部,工会");
        setListValue(deptD, "监察部", "纪检监察部（集团派驻）");
    }

    /**
     * @param map
     * @param key    低级部门名称
     * @param values 总部名称
     */
    public static void setListValue(Map<String, Object> map, String key, String values) {
        String[] split = values.split(",");
        List<String> list = new ArrayList<>();
        for (String s : split) {
            list.add(s);
        }
        map.put(key, list);
    }
}


