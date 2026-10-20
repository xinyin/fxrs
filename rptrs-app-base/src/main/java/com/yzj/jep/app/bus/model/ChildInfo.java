package com.yzj.jep.app.bus.model;

import java.util.List;

public class ChildInfo {
    private String name;
    private String id;

    private String rootName;
    private String amount;
    private String ratio;
    private String direction;
    private String hasHumanholding;
    private String hasChildren;
    private String isExpand;
    private List<ChildInfo> children;

    public String getRootName() {
        return rootName;
    }

    public void setRootName(String rootName) {
        this.rootName = rootName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getHasHumanholding() {
        return hasHumanholding;
    }

    public void setHasHumanholding(String hasHumanholding) {
        this.hasHumanholding = hasHumanholding;
    }

    public String getHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(String hasChildren) {
        this.hasChildren = hasChildren;
    }

    public String getIsExpand() {
        return isExpand;
    }

    public void setIsExpand(String isExpand) {
        this.isExpand = isExpand;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ChildInfo> getChildren() {
        return children;
    }

    public void setChildren(List<ChildInfo> children) {
        this.children = children;
    }
}
