package com.yzj.jep.app.tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理 Option 的默认对象
 *
 * @author mma
 * @version 3.0
 * @date 2012-3-19 上午9:17:17
 */
public class OptionSupportHandler implements OptionSupport {

    /*所有选项*/
    Map<String, Option> options = new HashMap<String, Option>();

    @Override
    public String getJSExpression() {
        StringBuffer expr = new StringBuffer();
        expr.append("{");
        if (options.size() > 0) {
            for (Option opt : options.values()) {
                expr.append(opt.getExpr());
                expr.append(", ");
            }
            expr.deleteCharAt(expr.length() - 2);
        }
        expr.append("}");
        return expr.toString();
    }

    /**
     * 增加选项，同名选项将被覆盖
     */
    @Override
    public void addOption(Option opt) {
        if (options != null) {
            options.put(opt.getName(), opt);
        }
    }

    /**
     * 增加一个选项
     *
     * @param name
     * @param value
     */
    public void addOption(String name, String value) {
        Option option = OptionUtils.createOption(name, value);
        addOption(option);
    }

    /**
     * 判断是否包含某个选项
     */
    @Override
    public boolean contains(String key) {
        return options.containsKey(key);
    }

    /**
     * 获得所有选项
     */
    @Override
    public List<Option> getOptions() {
        if (options != null) {
            return new ArrayList<Option>(options.values());
        }
        return null;
    }

    /**
     * 清空选项
     */
    public void clear() {
        options.clear();
    }

    /**
     * 返回选项的个数
     *
     * @return
     */
    public int size() {
        return options.size();
    }
}
