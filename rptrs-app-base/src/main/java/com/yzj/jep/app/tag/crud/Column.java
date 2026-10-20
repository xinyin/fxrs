package com.yzj.jep.app.tag.crud;

public class Column {

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
     * 列的排序器
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

}
