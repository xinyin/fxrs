package com.yzj.jep.app.tag.crud;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Datagrid 列标记
 *
 * @author mma
 * @version 3.0
 * @date 2012-3-17 下午1:30:36
 */
public class ColumnTag extends TagSupport {

    private static final long serialVersionUID = -6603256673775909358L;
    /**
     * 复选框
     */
    private boolean checkbox;
    /**
     * 标题
     */
    private String title;
    /**
     * 字段名
     */
    private String field;
    /**
     * 宽度
     */
    private String width = "60";
    /**
     * 内容格式化函数名
     */
    private String formatter;
    /**
     * 列的样式器
     */
    private String styler;
    /**
     * 排序器
     */
    private String sorter;
    /**
     * 编辑器
     */
    private String editor;
    /**
     * 是否排序
     */
    private String sortable;
    /**
     * 是否可改变大小
     */
    private String resizable;
    /**
     * 是否隐藏
     */
    private String hidden;

    public boolean isCheckbox() {
        return checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getFormatter() {
        return formatter;
    }

    public void setFormatter(String formatter) {
        this.formatter = formatter;
    }

    public String getStyler() {
        return styler;
    }

    public void setStyler(String styler) {
        this.styler = styler;
    }

    public String getSorter() {
        return sorter;
    }

    public void setSorter(String sorter) {
        this.sorter = sorter;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getSortable() {
        return sortable;
    }

    public void setSortable(String sortable) {
        this.sortable = sortable;
    }

    public String getResizable() {
        return resizable;
    }

    public void setResizable(String resizable) {
        this.resizable = resizable;
    }

    public String getHidden() {
        return hidden;
    }

    public void setHidden(String hidden) {
        this.hidden = hidden;
    }

    @Override
    public int doStartTag() throws JspException {
        return EVAL_PAGE;
    }

    @Override
    public int doEndTag() throws JspException {
        Column c = new Column();
        c.setCheckbox(checkbox);
        c.setField(field);
        c.setTitle(title);
        c.setHidden(hidden);
        c.setFormatter(formatter);
        c.setStyler(styler);
        c.setSorter(sorter);
        c.setSortable(sortable);
        c.setResizable(resizable);
        c.setWidth(width);
        c.setEditor(editor);

        DatagridTag parent = (DatagridTag) getParent();
        parent.addColumn(c);
        return SKIP_BODY;
    }

}
