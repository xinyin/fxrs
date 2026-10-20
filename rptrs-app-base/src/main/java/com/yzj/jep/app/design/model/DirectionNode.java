package com.yzj.jep.app.design.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @author Administrator
 */
public class DirectionNode {

    /**
     * origin?
     */
    @JSONField(ordinal = 1)
    private String name;

    /**
     * 节点方向（left、right）
     * left：前一层
     * right：后一层
     */
    @JSONField(ordinal = 2)
    private String direction;

    @JSONField(ordinal = 3)
    private List<RelationTypeNode> children;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public List<RelationTypeNode> getChildren() {
        return children;
    }

    public void setChildren(List<RelationTypeNode> children) {
        this.children = children;
    }
}
