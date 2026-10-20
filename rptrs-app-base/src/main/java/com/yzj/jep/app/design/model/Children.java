package com.yzj.jep.app.design.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @author Administrator
 */
public class Children {

    @JSONField(ordinal = 1)
    private String name;

    @JSONField(ordinal = 2)
    private String ratio;

    /**
     * 箭头方向（左、右、双向）
     */
    @JSONField(ordinal = 3)
    private String mshMark;

    @JSONField(ordinal = 4)
    private List<RelationTypeNode> children;

    @JSONField(ordinal = 5)
    private String relId;

    @JSONField(ordinal = 6)
    private String relType;

    /**
     * 层级，用于递归控制层数
     */
    @JSONField(ordinal = 7)
    private Integer hierarchy;

    /**
     * 单当前子集包含的关联关系用于递归控制
     */
    private List<String> relUnique;


    public Integer getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(Integer hierarchy) {
        this.hierarchy = hierarchy;
    }

    public List<String> getRelUnique() {
        return relUnique;
    }

    public void setRelUnique(List<String> relUnique) {
        this.relUnique = relUnique;
    }

    public Children(String name, String ratio) {
        this.name = name;
        this.ratio = ratio;
    }

    public Children() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public String getMshMark() {
        return mshMark;
    }

    public void setMshMark(String mshMark) {
        this.mshMark = mshMark;
    }

    public List<RelationTypeNode> getChildren() {
        return children;
    }

    public void setChildren(List<RelationTypeNode> children) {
        this.children = children;
    }

    public String getRelId() {
        return relId;
    }

    public void setRelId(String relId) {
        this.relId = relId;
    }

    public String getRelType() {
        return relType;
    }

    public void setRelType(String relType) {
        this.relType = relType;
    }
}
