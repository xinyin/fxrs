package com.yzj.jep.app.design.modelNew;

import com.alibaba.fastjson.annotation.JSONField;
import com.yzj.jep.app.design.model.Children;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class RelationTypeSecondNode {

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
    private List<Children> children;



    public RelationTypeSecondNode() {}

    public RelationTypeSecondNode(String name, String ratio, String mshMark, List<Children> children) {
        super();
        this.name = name;
        this.ratio = ratio;
        this.mshMark = mshMark;
        this.children = children;
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

    public List<Children> getChildren() {
        return children;
    }

    public void setChildren(List<Children> children) {
        this.children = children;
    }

    public void addChildren(Children children){
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        this.children .add(children);
    }

}
