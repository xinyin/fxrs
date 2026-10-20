package com.yzj.jep.app.design.modelNew;

import com.alibaba.fastjson.annotation.JSONField;
import com.yzj.jep.app.design.model.Children;

import java.util.List;

/**
 * @author Administrator
 */
public class RelationTypeFirstNode {

    @JSONField(ordinal = 1)
    private String name;

    @JSONField(ordinal = 2)
    private String ratio;

    /**
     * 箭头方向（左、右、双向）
     */
    @JSONField(ordinal = 3)
    private String mshMark;

//    @JSONField(ordinal = 4)
//    private List<RelationTypeSecondNode> children;
    @JSONField(ordinal = 4)
    private List<Children> children;


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


   /* public List<RelationTypeSecondNode> getChildren() {
		return children;
	}

	public void setChildren(List<RelationTypeSecondNode> children) {
		this.children = children;
	}

	public RelationTypeFirstNode(String name, String ratio, String mshMark, List<RelationTypeSecondNode> children) {
		super();
		this.name = name;
		this.ratio = ratio;
		this.mshMark = mshMark;
		this.children = children;
	}*/

    public List<Children> getChildren() {
        return children;
    }

    public void setChildren(List<Children> children) {
        this.children = children;
    }

    public RelationTypeFirstNode(String name, String ratio, String mshMark, List<Children> children) {
        this.name = name;
        this.ratio = ratio;
        this.mshMark = mshMark;
        this.children = children;
    }
}
