package com.yzj.jep.app.design.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author Administrator
 */
public class DirectionData {

    @JSONField(ordinal = 1)
    private DirectionNode left;

    @JSONField(ordinal = 2)
    private DirectionNode right;

    public DirectionNode getLeft() {
        return left;
    }

    public void setLeft(DirectionNode left) {
        this.left = left;
    }

    public DirectionNode getRight() {
        return right;
    }

    public void setRight(DirectionNode right) {
        this.right = right;
    }
}
