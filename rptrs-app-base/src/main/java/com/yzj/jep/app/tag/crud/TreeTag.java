package com.yzj.jep.app.tag.crud;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;

import com.yzj.jep.app.tag.Option;
import com.yzj.jep.app.tag.OptionSupport;
import com.yzj.jep.app.tag.OptionSupportHandler;
import com.yzj.jep.app.tag.OptionUtils;

/**
 * 树标记库
 *
 * @author mma
 * @version 3.0
 * @date 2012-3-22 下午4:28:09
 */
public class TreeTag extends BodyTagSupport implements OptionSupport {

    private static final long serialVersionUID = -2226465095580450703L;

    /**
     * 作为 tree 的 ID
     */
    private String id;
    /**
     * 领域对象的别名，出现在各种提示信息中
     */
    private String alias;
    /**
     * 用于编辑的表单名称，可选，默认 "editForm"
     */
    private String editFormId;
    /**
     * 表单中的主键值字段，用于和树节点交互信息
     */
    private String keyField;
    /**
     * 表单中的名称字段，用于和树节点交互信息，可选
     */
    private String textField;
    /**
     * 表单中父节点的主键字段，用于和树节点交互信息，可选
     */
    private String parentKeyField;
    /**
     * 表单中父节点的名称字段，用于和树节点交互信息，可选
     */
    private String parentTextField;

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
     * 引用的css样式表，输出到table
     */
    private String clazz;


    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getEditFormId() {
        return editFormId;
    }

    public void setEditFormId(String editFormId) {
        this.editFormId = editFormId;
    }

    public String getKeyField() {
        return keyField;
    }

    public void setKeyField(String keyField) {
        this.keyField = keyField;
    }

    public String getTextField() {
        return textField;
    }

    public void setTextField(String textField) {
        this.textField = textField;
    }

    public String getParentKeyField() {
        return parentKeyField;
    }

    public void setParentKeyField(String parentKeyField) {
        this.parentKeyField = parentKeyField;
    }

    public String getParentTextField() {
        return parentTextField;
    }

    public void setParentTextField(String parentTextField) {
        this.parentTextField = parentTextField;
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

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
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
    public int doAfterBody() throws JspException {
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {

        JspWriter writer = pageContext.getOut();
        /*
         * 生成  alias, actionPath, parent 等选项
         */
        if (alias != null) {
            addOption("objectAlias", alias);
        }

        /*actionPath 由 module 和 domainObject 生成*/
        String actionPath = module == null ? "" : module;
        actionPath = actionPath.endsWith("/") ? actionPath : actionPath + "/" + (domainObject == null ? "" : domainObject);
        if ("".equals(StringUtils.trim(actionPath)) || "/".equals(StringUtils.trim(actionPath))) {
            actionPath = null;
        }
        if (actionPath != null) {
            addOption("actionPath", actionPath);
        }

        /*生成关联的表单对象*/
        if (!contains("editForm")) {
            OptionSupportHandler dsh = new OptionSupportHandler();
            if (editFormId != null) {
                dsh.addOption("id", editFormId);
            }

            if (keyField != null) {
                dsh.addOption("keyField", keyField);
            }
            if (textField != null) {
                dsh.addOption("textField", textField);
            }
            if (parentKeyField != null) {
                dsh.addOption("parentKeyField", parentKeyField);
            }
            if (parentTextField != null) {
                dsh.addOption("parentTextField", parentTextField);
            }
            addOption("editForm", dsh.getJSExpression());
        }

        try {
            /*输出 tree 脚本*/
            writer.println("<script type=\"text/javascript\">");
            /*
             * 加入一个该对象的锁。由于在 easyui-layout 中，layout会动态改变
             * DOM 结构，导致该段脚本被执行两遍，可能会引发问题，
             * 因此加入锁，防止重复执行。
             */
            writer.println("var " + id + "_lock = false;");
            /*输出 tree*/
            writer.println("$(window).load(function(){");
            writer.println("if(!" + id + "_lock){");
            //writer.println("alert('" + id + "')");
            writer.println("$('#" + id + "').crudTree(");
            writer.println(getJSExpression());
            writer.println(");" + id + "_lock=true;}");
            writer.println("});");
            writer.println("</script>");

            /*输出UL*/
            writer.print("<ul id=\"" + id + "\" ");
            if (style != null) {
                writer.print("style=\"" + style + "\"");
            }
            if (clazz != null) {
                writer.print("class=\"" + clazz + "\" ");
            }
            writer.println("></ul>");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return EVAL_PAGE;
    }

    @Override
    public int doStartTag() throws JspException {
        handler.clear();
        return EVAL_BODY_BUFFERED;
    }

}
