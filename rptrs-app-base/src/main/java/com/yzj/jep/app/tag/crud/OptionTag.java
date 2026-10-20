package com.yzj.jep.app.tag.crud;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.yzj.jep.app.tag.Option;
import com.yzj.jep.app.tag.OptionSupport;
import com.yzj.jep.app.tag.OptionSupportHandler;
import com.yzj.jep.app.tag.OptionTypeConstant;
import com.yzj.jep.app.tag.OptionUtils;

public class OptionTag extends BodyTagSupport implements OptionSupport {

    private static final long serialVersionUID = 6407526357717581522L;

    /*所有处理对象*/
    OptionSupportHandler handler = new OptionSupportHandler();

    /**
     * 增加选项，同名选项将被覆盖
     */
    @Override
    public void addOption(Option opt) {
        handler.addOption(opt);
    }

    /**
     * 获得所有选项
     */
    @Override
    public List<Option> getOptions() {
        return handler.getOptions();
    }

    /**
     * 判断是否包含某个选项
     */
    @Override
    public boolean contains(String key) {
        return handler.contains(key);
    }

    @Override
    public int doAfterBody() throws JspException {
        /*
         *  此处的处理逻辑为标记体优先于 value
         */
        if (getBodyContent() != null) {
            String body = getBodyContent().getString();
            setValue(body);
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        Option option = getOption();
        if (option != null) {
            /*如果没有指定 value 属性，将标记体的内容赋值给 value*/
            OptionSupport parent = (OptionSupport) getParent();
            try {
                parent.addOption(option);
            } catch (Exception ex) {
            }
        }
        return EVAL_PAGE;
    }

    @Override
    public int doStartTag() throws JspException {
        handler.clear();
        return EVAL_BODY_BUFFERED;
    }

    private String name;
    private String value;
    private String type = OptionTypeConstant.TYPE_AUTO;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private Option getOption() {
        /*
         *  此处的处理逻辑为 options 定义优先于 value 的定义
         */
        if (handler.size() > 0) {
            return OptionUtils.createOption(name, handler.getJSExpression(), OptionTypeConstant.TYPE_OBJECT);
        } else if (value != null) {
            return OptionUtils.createOption(name, value, type);
        }
        return null;
    }

    /**
     * 根据选项类型返回选项的表达式
     */
    @Override
    public String getJSExpression() {
        return null;
    }

}
