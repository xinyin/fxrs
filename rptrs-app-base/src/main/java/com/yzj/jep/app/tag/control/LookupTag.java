package com.yzj.jep.app.tag.control;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yzj.jep.app.tag.Option;
import com.yzj.jep.app.tag.OptionSupport;
import com.yzj.jep.app.tag.OptionSupportHandler;
import com.yzj.jep.app.tag.OptionUtils;

/**
 * 封装lookup
 *
 * @author Michael
 * @version 3.0
 * @date 2014年1月20日 下午7:25:38
 */
public class LookupTag extends BodyTagSupport implements OptionSupport {

    private static final long serialVersionUID = 633957035007648681L;

    private static final Logger logger = LoggerFactory.getLogger(LookupTag.class);

    /**
     * lookup的id
     */
    private String id;

    /**
     * 选择的数据来源
     */
    private String url = "";

    /**
     * lookup 默认创建两个隐藏域，textField 用来保存回显数据
     */
    private String textField;
    /**
     * lookup 默认创建两个隐藏域，valueField 用来保存值
     */
    private String valueField;

    /**
     * lookup 从选择页面接收的数据是一个对象，textRef引用到该对象的某个属性，用来获取显示数据
     */
    private String textRef = "name";
    /**
     * lookup 从选择页面接收的数据是一个对象，valueRef引用到该对象的某个属性，用来获取值
     */
    private String valueRef = "id";

    /**
     * 页面输出的style
     */
    private String style = "width: 198px;";


    /**
     * 是否不可用，true在显示页面中使用
     */
    private String disabled = "false";

    /**
     * 是否必须
     */
    private String required = null;

    /**
     * 设置为必输项时，错误提示信息
     */
    private String missingMessage = null;

    /**
     * 校验器
     */
    private String validType = null;

    /**
     * 校验失败的提示信息
     */
    private String invalidMessage = null;


    /**
     * 打开的窗口需要一个回调函数名，每个页面的实现可能不同，
     * 此属性定义的是打开窗口回调时的函数名，本身不用实现。
     * 默认为 result, 打开的窗口使用如下代码调用：
     * getWindowArgs('窗口ID').result();
     */
    private String callbackProp = "result";


    /**
     * 点击文本框时是否弹出窗口，默认弹出
     */
    private String fireOnClickInput = "true";


    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getTextField() {
        return textField;
    }

    public void setTextField(String textField) {
        this.textField = textField;
    }

    public String getValueField() {
        return valueField;
    }

    public void setValueField(String valueField) {
        this.valueField = valueField;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }


    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }


    public OptionSupportHandler getHandler() {
        return handler;
    }

    public void setHandler(OptionSupportHandler handler) {
        this.handler = handler;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public String getMissingMessage() {
        return missingMessage;
    }

    public void setMissingMessage(String missingMessage) {
        this.missingMessage = missingMessage;
    }

    public String getTextRef() {
        return textRef;
    }

    public void setTextRef(String textRef) {
        this.textRef = textRef;
    }

    public String getValueRef() {
        return valueRef;
    }

    public void setValueRef(String valueRef) {
        this.valueRef = valueRef;
    }

    public String getValidType() {
        return validType;
    }

    public void setValidType(String validType) {
        this.validType = validType;
    }

    public String getInvalidMessage() {
        return invalidMessage;
    }

    public void setInvalidMessage(String invalidMessage) {
        this.invalidMessage = invalidMessage;
    }

    public String getCallbackProp() {
        return callbackProp;
    }

    public void setCallbackProp(String callbackProp) {
        this.callbackProp = callbackProp;
    }

    public String getFireOnClickInput() {
        return fireOnClickInput;
    }

    public void setFireOnClickInput(String fireOnClickInput) {
        this.fireOnClickInput = fireOnClickInput;
    }

    /*所有选项处理对象*/
    OptionSupportHandler handler = new OptionSupportHandler();

    /**
     * 增加选项，同名选项将被覆盖
     */
    @Override
    public void addOption(Option opt) {
        handler.addOption(opt);
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

    /**
     * 获得选项的表达式
     */
    @Override
    public String getJSExpression() {
        return handler.getJSExpression();
    }

    @Override
    public int doEndTag() throws JspException {
        JspWriter writer = pageContext.getOut();

        addOption("textField", textField);
        addOption("valueField", valueField);
        addOption("textRef", textRef);
        addOption("valueRef", valueRef);
        addOption("disabled", disabled);
        addOption("url", url);
        addOption("callbackProp", callbackProp);
        addOption("fireOnClickInput", fireOnClickInput);

        if (required != null) {
            addOption("required", required);
        }
        if (missingMessage != null) {
            addOption("missingMessage", missingMessage);
        }

        if (validType != null) {
            addOption("validType", validType);
        }
        if (invalidMessage != null) {
            addOption("invalidMessage", invalidMessage);
        }

        try {
            /*输出 lookup 脚本*/
            writer.println("<script type=\"text/javascript\">");
            /*
             * 加入一个该对象的锁。由于在 easyui-layout 中，layout会动态改变
             * DOM 结构，导致该段脚本被执行两遍，可能会引发问题，
             * 因此加入锁，防止重复执行。
             */
            writer.println("var " + id + "_lock = false;");
            /*输出 lookup*/
            writer.println("jQuery(function($){");
            writer.println("if(!" + id + "_lock){");

            writer.println("$('#" + id + "').lookup(");
            writer.println(getJSExpression());
            writer.println(");");

            writer.println(id + "_lock=true;}");
            writer.println("});");
            writer.println("</script>");

            writer.print("<input id=\"" + id + "\" style=\"" + style + "\"/>");

        } catch (IOException e) {
            logger.error("写入lookup: " + id + " 发生IO异常，请稍后重试。");
        }

        return super.doEndTag();
    }

    @Override
    public int doStartTag() throws JspException {
        handler.clear();
        return super.doStartTag();
    }

    @Override
    public int doAfterBody() throws JspException {
        return super.doAfterBody();
    }

}
