package com.yzj.jep.app.design.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author Administrator
 */
public class RootNode {
    @JSONField(ordinal = 3)
    private String relId;

    @JSONField(ordinal = 4)
    private String relType;

    @JSONField(ordinal = 1)
    private String rootName;

    @JSONField(ordinal = 2)
    private DirectionData directionData;


    public String getRootName() {
        return rootName;
    }

    public void setRootName(String rootName) {
        this.rootName = rootName;
    }

    public DirectionData getDirectionData() {
        return directionData;
    }

    public void setDirectionData(DirectionData directionData) {
        this.directionData = directionData;
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
