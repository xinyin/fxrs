package com.yzj.jep.app.tag.crud;

import com.yzj.jep.app.tag.Option;
import com.yzj.jep.app.tag.OptionSupport;
import com.yzj.jep.app.tag.OptionSupportHandler;
import com.yzj.jep.app.tag.OptionUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;
import java.util.*;

/**
 * Datagrid 标记，客户端使用 $.fn.crudDatagrid 实现
 *
 * @author Michael
 * @version 3.0
 * @date 2012-3-16 上午10:10:38
 */
public class DatagridTag extends BodyTagSupport implements OptionSupport {

    private static final long serialVersionUID = -1670780497875545031L;

    /**
     * 父对象类型 - tree
     */
    public static final String PARENT_TYPE_TREE = "tree";
    /**
     * 父对象类型 - datagrid
     */
    public static final String PARENT_TYPE_DATAGRID = "datagrid";

    /**
     * 作为 datagrid 的 ID
     */
    private String id;
    /**
     * Datagrid 的标题，如果不赋值，则为 {alias}列表
     */
    private String title;
    /**
     * 领域对象的别名，出现在表格标题和各种提示信息中
     */
    private String alias;
    /**
     * 主键值字段，目前只用在编辑页面中传递 formHref 属性
     */
    private String keyField;
    /**
     * 模块，用于生成路径
     * 如果一个模块功能的路径为  /test/dept/list
     * 则 module = "test"
     */
    private String module;
    /**
     * 领域对象，用于生成路径
     * 如果一个模块功能的路径为  /test/dept/list
     * 则 domainObject = "dept"
     */
    private String domainObject;
    /**
     * 表格的样式，输出到 table
     */
    private String style;
    /**
     * 表格边框
     */
    private String border;
    /**
     * 引用的css样式表，输出到table
     */
    private String clazz;
    /**
     * 父对象
     */
    private String parentId;
    /**
     * 父对象的类型
     */
    private String parentType;
    /**
     * 父对象的关联字段
     */
    private String parentField;
    /**
     * 当前grid的对应字段
     */
    private String childField;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getKeyField() {
        return keyField;
    }

    public void setKeyField(String keyField) {
        this.keyField = keyField;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getDomainObject() {
        return domainObject;
    }

    public void setDomainObject(String domainObject) {
        this.domainObject = domainObject;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getBorder() {
        return border;
    }

    public void setBorder(String border) {
        this.border = border;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentType() {
        return parentType;
    }

    public void setParentType(String parentType) {
        this.parentType = parentType;
    }

    public String getParentField() {
        return parentField;
    }

    public void setParentField(String parentField) {
        this.parentField = parentField;
    }

    public String getChildField() {
        return childField;
    }

    public void setChildField(String childField) {
        this.childField = childField;
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
     * 数据列
     */
    List<Column> columns;

    public void addColumn(Column column) {
        if (columns != null) {
            columns.add(column);
        }
    }

    @Override
    public int doEndTag() throws JspException {

        /*未定义列，不做任何操作*/
        if (columns.size() > 0) {
            JspWriter writer = pageContext.getOut();
            /*
             * 生成 title, alias, keyField, actionPath, parent 等选项
             */
            if (title != null) {
                addOption("title", title);
            }
            if (alias != null) {
                addOption("objectAlias", alias);
            }
            if (keyField != null) {
                addOption("keyField", keyField);
            }

            /*actionPath 由 module 和 domainObject 生成*/
            String actionPath = module == null ? "" : module;
            actionPath = actionPath.endsWith("/") ? actionPath : actionPath + "/" + (domainObject == null ? "" : domainObject);
            if ("".equals(StringUtils.trim(actionPath))) {
                actionPath = null;
            }
            if (actionPath != null) {
                addOption("actionPath", actionPath);
            }

            /*生成关联的父对象*/
            if (!contains("parent")) {
                if (parentId != null) {
                    OptionSupportHandler dsh = new OptionSupportHandler();
                    dsh.addOption("id", parentId);
                    if (parentField != null) {
                        dsh.addOption("parentField", parentField);
                    }
                    if (childField != null) {
                        dsh.addOption("childField", childField);
                    }
                    if (parentType != null) {
                        dsh.addOption("type", parentType);
                    }
                    addOption("parent", dsh.getJSExpression());
                }
            }

            try {
                /*输出 datagrid 脚本*/
                writer.println("<script type=\"text/javascript\">");
                /*生成格式化 js 函数*/
                Map<String, String> formatters = new HashMap<String, String>();
                for (int i = 0; i < columns.size(); i++) {
                    Column c = columns.get(i);
                    if (StringUtils.isEmpty(c.getFormatter())) {
                        continue;
                    }
                    String formatter = c.getFormatter().trim();
                    /*处理 fixedDict */
                    if (formatter.startsWith("fixedDict:")) {
                        /*已经定义了函数*/
                        if (formatters.containsKey(formatter)) {
                            continue;
                        }
                        /*取回字典名称，生成函数*/
                        String dictName = formatter.replace("fixedDict:", "").trim();
                        if (StringUtils.isEmpty(dictName)) {
                            continue;
                        }
                        String functionName = "formatter_" + new Random().nextInt(65535);
                        writer.println("function " + functionName + "(value){return $.fixedDict.getLabel('" + dictName + "', value);}");
                        formatters.put(c.getFormatter(), functionName);
                    }
                    /*处理 dict*/
                    if (formatter.startsWith("dict:")) {
                        /*已经定义了函数*/
                        if (formatters.containsKey(formatter)) {
                            continue;
                        }
                        /*取回字典名称，生成函数*/
                        String dictName = formatter.replace("dict:", "").trim();
                        if (StringUtils.isEmpty(dictName)) {
                            continue;
                        }
                        String functionName = "formatter_" + new Random().nextInt(65535);
                        writer.println("function " + functionName + "(value){return $.dict.getLabel('" + dictName + "', value);}");
                        formatters.put(formatter, functionName);
                    }
                    /*处理 dict*/
                    if ("formatLongText".equals(formatter)) {
                        String functionName = "formatter_" + new Random().nextInt(65535);
                        writer.println("function " + functionName + "(value){return \"<table cellspacing=\\\"0\\\" cellpadding=\\\"0\\\" style=\\\"width:100%;border:none;word-wrap: break-word;table-layout: fixed;word-break:break-all\\\"><tr><td>\" + value + \"</td></tr></table>\";}");
                        formatters.put(formatter, functionName);
                    }
                }

                /*
                 * 加入一个该对象的锁。由于在 easyui-layout 中，layout会动态改变
                 * DOM 结构，导致该段脚本被执行两遍，可能会引发问题，
                 * 因此加入锁，防止重复执行。
                 */
                writer.println("var " + id + "_lock = false;");
                /*输出 crudDatagird*/
                writer.println("$(window).load(function(){");
                writer.println("if(!" + id + "_lock){");
                //writer.println("alert('" + id + "')");
                writer.println("$('#" + id + "').crudDatagrid(");
                writer.println(getJSExpression());
                writer.println(");" + id + "_lock=true;}");
                writer.println("});");
                writer.println("</script>");

                /*输出表格*/
                writer.print("<table id=\"" + id + "\" ");
                if (border != null) {
                    writer.print("border=\"" + border + "\"");
                }
                if (style != null) {
                    writer.print("style=\"" + style + "\"");
                }
                if (clazz != null) {
                    writer.print("class=\"" + clazz + "\" ");
                }
                writer.println(">");
                writer.println("\t<thead><tr>");
                for (int i = 0; i < columns.size(); i++) {
                    Column c = columns.get(i);
                    if (c.isCheckbox()) {// 该列是否是复选框列 gyzhang@20131127@nanhai
                        writer.print("\t\t<th field=\"" + c.getField() + "\" checkbox=\"true\"" + " width=\"" + c.getWidth() + "\" ");
                    } else {
                        writer.print("\t\t<th field=\"" + c.getField() + "\" width=\"" + c.getWidth() + "\" ");
                    }

                    if (c.getEditor() != null) {
                        writer.print("editor=\"" + c.getEditor() + "\" ");
                    }
                    if (c.getResizable() != null) {
                        writer.print("resizable=\"" + c.getResizable() + "\" ");
                    }
                    if (c.getSortable() != null) {
                        writer.print("sortable=\"" + c.getSortable() + "\" ");
                    }
                    if (c.getHidden() != null && "true".equals(c.getHidden())) {
                        writer.print("hidden=\"" + c.getHidden() + "\" ");
                    }
                    if (c.getStyler() != null) {
                        writer.print("styler=\"" + c.getStyler() + "\" ");
                    }
                    if (c.getSorter() != null) {
                        writer.print("sorter=\"" + c.getSorter() + "\" ");
                    }
                    /*判断 formatter*/
                    if (!StringUtils.isEmpty(c.getFormatter())) {
                        writer.print("formatter=\"");
                        String formatter = c.getFormatter().trim();
                        if (formatters.containsKey(formatter)) {
                            writer.print(formatters.get(formatter));
                        } else {
                            writer.print(c.getFormatter());
                        }
                        writer.print("\" ");
                    }
                    writer.println(">" + c.getTitle() + "</th>");
                }
                writer.println("\t</tr></thead>");
                writer.println("</table>");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return EVAL_PAGE;
    }

    @Override
    public int doStartTag() throws JspException {
        handler.clear();
        columns = new ArrayList<Column>();
        return EVAL_BODY_BUFFERED;
    }

}
