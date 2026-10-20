package com.yzj.jep.app.tag;

import java.util.List;

/**
 * 标识一个对象支持 options 选项
 * Options 是 option 的集合
 *
 * @author mma
 * @version 3.0
 * @date 2012-3-17 下午1:35:26
 */
public interface OptionSupport extends JSExpressionSupport {

    /**
     * 增加一个选项
     *
     * @param opt
     */
    void addOption(Option opt);

    /**
     * 返回所有 option 的集合
     *
     * @return
     */
    List<Option> getOptions();

    /**
     * 判断是否包含一个选项
     *
     * @param key
     * @return
     */
    boolean contains(String key);
}
