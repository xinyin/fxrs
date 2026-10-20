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
import com.yzj.jep.app.tag.OptionTypeConstant;
import com.yzj.jep.app.tag.OptionUtils;

/**
 * 封装easyUI的combobox
 *
 * @author gyzhang
 * @version 3.1.1
 * @date 2012-6-16 上午7:29:21
 */
public class ComboBoxTag extends BodyTagSupport implements OptionSupport {
    private static final long serialVersionUID = 605509631836375378L;
    private static final Logger logger = LoggerFactory.getLogger(ComboBoxTag.class);

    static final String CACHE_TYPE_CUSTOM = "custom";
    static final String CACHE_TYPE_FIXEDDICT = "fixedDict";
    static final String CACHE_TYPE_DICT = "dict";

    /**
     * combobox的id
     */
    private String id;
    /**
     * 数据来源，有fixedDict、dict和custom三种
     */
    private String dataSource = "custom";
    /**
     * A URL to load list data from remote.<br/>
     * 1、当dataSource为fixedDict时，url为fixedDict的类名，如PrivilegeType权限类型emum的类名；<br/>
     * 2、当dataSource为dict时，url为dict的code，如BUSINESS_TYPE业务类型字典的code；<br/>
     * 3、当dataSource为custom时，url为服务器Controller的一个方法，该方法返回序列化后的jason对象，controller方法标注为@ResponseBody，并RequestMethod.
     * POST和method属性保持一致。<br/>
     */
    private String url = "";

    /**
     * 不再开放 method 属性，默认全部为 POST
     */
    private String method = "post";
    /**
     * combobox显示的值
     */
    private String textField = "name";
    /**
     * combobox保存的值
     */
    private String valueField = "value";
    /**
     * 页面输出的style
     */
    private String style = "width: 198px;";

    /**
     * 控件的css类名称
     */
    private String clazz = "easyui-combobox";
    /**
     * 是否支持多选
     */
    private String multiple = "false";

    /**
     * 是否支持编辑
     */
    private String editable = "false";

    /**
     * 是否不可用，true在显示页面中使用
     */
    private String disabled = "false";

    /**
     * 默认值
     */
    private String defaultValue = null;

    /**
     * 是否在数据项中增加一项空白
     */
    private Boolean headEmpty = false;

    /**
     * 明确指定需要包含的值
     */
    private String includes = null;

    /**
     * 明确指定需要排除的值
     */
    private String excludes = null;

    /**
     * 下拉面板的高度
     */
    private String panelHeight = "auto";

    /**
     * 缓存的名字，如果不给，随机生成一个
     */
    private String cacheName = null;

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


    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
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

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getMultiple() {
        return multiple;
    }

    public void setMultiple(String multiple) {
        this.multiple = multiple;
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

    public Boolean getHeadEmpty() {
        return headEmpty;
    }

    public void setHeadEmpty(Boolean headEmpty) {
        this.headEmpty = headEmpty;
    }

    public String getIncludes() {
        return includes;
    }

    public void setIncludes(String includes) {
        this.includes = includes;
    }

    public String getExcludes() {
        return excludes;
    }

    public void setExcludes(String excludes) {
        this.excludes = excludes;
    }

    public String getPanelHeight() {
        return panelHeight;
    }

    public void setPanelHeight(String panelHeight) {
        this.panelHeight = panelHeight;
    }


    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
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

    /**
     * 判断是否需要cache的名称， fixedDict 和 dict 返回 url
     * custom 如果没有cacheName， 则生成一个随机的名称
     *
     * @return
     */
    private String getCacheNameIfRequired() {
//		if(CACHE_TYPE_CUSTOM.equals(dataSource))
//		{
//			if(cacheName != null)
//			{
//				return cacheName;
//			}
//			else
//			{
//				return getTimedCacheName();
//			}
//		}
        return url;
    }

    private String getTimedCacheName() {
        return "cache" + System.currentTimeMillis();
    }

    @Override
    public int doEndTag() throws JspException {
        JspWriter writer = pageContext.getOut();
        /*将给定的属性设置到option中*/
//		if ("fixedDict".equals(dataSource))
//			addOption("data", "$.fixedDict.get('" + url + "')");
//		if ("dict".equals(dataSource))
//			addOption("data", "$.dict.get('" + url + "')");
//		if ("custom".equals(dataSource))
//			addOption("url", url);

        StringBuffer onLoadSuccess = new StringBuffer("function(data){");

        if (defaultValue != null) {
            onLoadSuccess.append("$('#" + id + "').combobox('setValue', '" + defaultValue + "');");
        }

        if (showPanelOnClick) {
            onLoadSuccess.append("var textbox = $('#" + id + "').combobox('textbox');textbox.click('showPanel', function(e){$('#" + id + "').combobox('showPanel');});");
        }

        onLoadSuccess.append("}");
        addOption(OptionUtils.createOption("onLoadSuccess", onLoadSuccess.toString(), OptionTypeConstant.TYPE_OBJECT));

        try {
            /*输出 combobox 脚本*/
            writer.println("<script type=\"text/javascript\">");
            /*
             * 加入一个该对象的锁。由于在 easyui-layout 中，layout会动态改变
             * DOM 结构，导致该段脚本被执行两遍，可能会引发问题，
             * 因此加入锁，防止重复执行。
             */
            writer.println("var " + id + "_lock = false;");
            /*输出 combobox*/
            writer.println("jQuery(function($){");
            writer.println("if(!" + id + "_lock){");

            /*此处改用  $.cache 实现*/
            String cacheName = getCacheNameIfRequired();
            writer.println("$.cache.cache('" + dataSource + "', '" + url + "', '" + cacheName + "');");
            writer.println("var data = $.cache.getFilteredCache('" + cacheName + "', '"
                    + valueField + "', '" + (includes == null ? "" : includes) + "', '"
                    + (excludes == null ? "" : excludes) + "');");

            /*需要为首行增加一个空值*/
            if (headEmpty) {
                writer.println("data.unshift({'" + textField + "': '[请选择...]', '" + valueField + "': ''});");
            }
            addOption(OptionUtils.createOption("data", "data", OptionTypeConstant.TYPE_OBJECT));


            writer.println("$('#" + id + "').combobox(");
            writer.println(getJSExpression());
            writer.println(");");

            writer.println(id + "_lock=true;}");
            writer.println("});");
            writer.println("</script>");

            /*输出combox*/
            writer.print("<select id=\"" + id + "\" name=\"" + id + "\" ");
            writer.print("style=\"" + style + "\" ");
            writer.print("class=\"" + clazz + "\" ");
            writer.print("></select>");

        } catch (IOException e) {
            logger.error("写入ComboBox: " + id + " 发生IO异常，请稍后重试。");
        }

        return super.doEndTag();
    }

    @Override
    public int doStartTag() throws JspException {
        handler.clear();

        /*
         * 将属性对应到option，应该放在 doStartTag 中，否则会将x:option中的设置覆盖掉
         * modified by mamin 20140807
         */
        addOption("method", method);
        addOption("textField", textField);
        addOption("valueField", valueField);
        addOption("multiple", multiple);
        addOption("editable", editable);
        addOption("disabled", disabled);
        addOption("panelHeight", panelHeight);

        if (required != null) {
            addOption("required", required);
        }
        if (missingMessage != null) {
            addOption("missingMessage", missingMessage);
        }
        return super.doStartTag();
    }

    @Override
    public int doAfterBody() throws JspException {
        return super.doAfterBody();
    }

}
