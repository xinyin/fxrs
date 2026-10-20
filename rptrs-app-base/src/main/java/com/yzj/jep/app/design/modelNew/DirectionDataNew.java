package com.yzj.jep.app.design.modelNew;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author Administrator
 */
public class DirectionDataNew {

    @JSONField(ordinal = 1)
    private DirectionNodeNew left;

    @JSONField(ordinal = 2)
    private DirectionNodeNew right;

    public DirectionNodeNew getLeft() {
        return left;
    }

    public void setLeft(DirectionNodeNew left) {
        this.left = left;
    }

    public DirectionNodeNew getRight() {
        return right;
    }

    public void setRight(DirectionNodeNew right) {
        this.right = right;
    }
}
