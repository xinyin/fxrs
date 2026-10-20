package com.yzj.jep.app.tag.control;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yzj.jep.app.tag.Option;
import com.yzj.jep.app.tag.OptionSupport;
import com.yzj.jep.app.tag.OptionSupportHandler;
import com.yzj.jep.app.tag.OptionUtils;

/**
 * 封装easyUI的combobox
 *
 * @author gyzhang
 * @version 3.1.1
 * @date 2012-6-16 上午7:29:21
 */
public class DateBoxTag extends BodyTagSupport implements OptionSupport {
    private static final long serialVersionUID = 605509631836375378L;
    private static final Logger logger = LoggerFactory.getLogger(DateBoxTag.class);

    static final String CACHE_TYPE_CUSTOM = "custom";
    static final String CACHE_TYPE_FIXEDDICT = "fixedDict";
    static final String CACHE_TYPE_DICT = "dict";

    /**
     * combobox的id
     */
    private String id;
    /**
     * 页面输出的style
     */
    private String style = "width: 198px;";


    /**
     * 是否支持编辑
     */
    private String editable = "true";

    /**
     * 是否不可用，true在显示页面中使用
     */
    private String disabled = "false";

    /**
     * 默认值
     */
    private String defaultValue = null;


    /**
     * 是否必须
     */
    private String required = null;

    /**
     * 设置为必输项时，错误提示信息
     */
    private String missingMessage = null;

    /**
     * 点击输入框，是否直接弹出选项，默认为是
     */
    private boolean showPanelOnClick = true;

    /**
     * 是否显示格式提示
     */
    private boolean showTooltip = false;


    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }


    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }


    public String getEditable() {
        return editable;
    }

    public void setEditable(String editable) {
        this.editable = editable;
    }

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
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

    public boolean isShowPanelOnClick() {
        return showPanelOnClick;
    }

    public void setShowPanelOnClick(boolean showPanelOnClick) {
        this.showPanelOnClick = showPanelOnClick;
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

        addOption("editable", editable);
        addOption("disabled", disabled);

        if (required != null) {
            addOption("required", required);
        }
        if (missingMessage != null) {
            addOption("missingMessage", missingMessage);
        }
        if (!StringUtils.isEmpty(defaultValue)) {
            addOption("value", defaultValue);
        }

        try {
            /*输出 datebox 脚本*/
            writer.println("<script type=\"text/javascript\">");
            /*
             * 加入一个该对象的锁。由于在 easyui-layout 中，layout会动态改变
             * DOM 结构，导致该段脚本被执行两遍，可能会引发问题，
             * 因此加入锁，防止重复执行。
             */
            writer.println("var " + id + "_lock = false;");
            /*输出 datebox*/
            writer.println("jQuery(function($){");
            writer.println("if(!" + id + "_lock){");

            writer.println("$('#" + id + "').datebox(");
            writer.println(getJSExpression());
            writer.println(");");

            writer.println("var _textbox = $('#" + id + "').datebox('textbox');");
            writer.println("_textbox.blur(function(e){");
            writer.println("var _text = $('#" + id + "').datebox('getText');");
            writer.println("if(_text && /^(\\d{4})(\\-)(\\d{2})(\\-)(\\d{2})$/g.test(_text)){return;}");
            writer.println("if(_text && /^(\\d{4})(\\d{2})(\\d{2})$/g.test(_text)){");
            writer.println("var _d = RegExp.$1 + '-' + RegExp.$2 + '-' + RegExp.$3;");
            writer.println("$('#" + id + "').datebox('setValue', _d);return;");
            writer.println("}");
            writer.println("if(_text && /^(\\d{4})\\/(\\d{2})\\/(\\d{2})$/g.test(_text)){");
            writer.println("var _d = RegExp.$1 + '-' + RegExp.$2 + '-' + RegExp.$3;");
            writer.println("$('#" + id + "').datebox('setValue', _d);return;");
            writer.println("}");
            writer.println("if(_text && /^(\\d{2})\\/(\\d{2})\\/(\\d{4})$/g.test(_text)){");
            writer.println("var _d = RegExp.$3 + '-' + RegExp.$1 + '-' + RegExp.$2;");
            writer.println("$('#" + id + "').datebox('setValue', _d);return;");
            writer.println("}");
            //writer.println("$('#" + id + "').datebox('setValue', '');");
            writer.println("});");

            writer.println(id + "_lock=true;}");

            if (showPanelOnClick) {
                writer.println("var " + id + "_textbox = $('#" + id + "').datebox('textbox');");
                writer.println("" + id + "_textbox.click('showPanel', function(e){$('#" + id + "').datebox('showPanel');});");
            }
            if (showTooltip) {
                writer.println("var " + id + "_textbox = $('#" + id + "').datebox('textbox');");
                writer.println("var " + id + "_tooltip = $('#" + id + "_tooltip');");
                writer.println("" + id + "_tooltip.css({width: (" + id + "_textbox.width() > 120 ? " + id + "_textbox.width() : 120), height: 24, position: 'absolute', left: " + id + "_textbox.position().left, top: " + id + "_textbox.position().top - 27});");
                writer.println("" + id + "_textbox.focus('tooltip', function(e){" + id + "_tooltip.fadeIn('normal');});");
                writer.println("" + id + "_textbox.blur('tooltip', function(e){" + id + "_tooltip.fadeOut('fast');});");
            }

            writer.println("});");

            writer.println("</script>");
            if (showTooltip) {
                writer.print("<span id=\"" + id + "_tooltip\" style=\"width:1px;height:1px;padding:2px;padding-left:4px;background-color:#FFFFCC;display:none;border:solid 1px #CC9933;\">格式：2014-01-01</span>");
            }
            /*输出datebox*/
            writer.print("<input id=\"" + id + "\" name=\"" + id + "\" ");
            writer.print("style=\"" + style + "\" ");
            writer.print("/>");


        } catch (IOException e) {
            logger.error("写入DateBox: " + id + " 发生IO异常，请稍后重试。", e);
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
