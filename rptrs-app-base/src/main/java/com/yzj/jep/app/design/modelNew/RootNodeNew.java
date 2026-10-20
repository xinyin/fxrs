package com.yzj.jep.app.design.modelNew;

import com.alibaba.fastjson.annotation.JSONField;
/**
 * @author Administrator
 */
public class RootNodeNew {

    @JSONField(ordinal = 1)
    private String rootName;

    @JSONField(ordinal = 2)
    private DirectionDataNew directionData;


    public String getRootName() {
        return rootName;
    }

    public void setRootName(String rootName) {
        this.rootName = rootName;
    }

    public DirectionDataNew getDirectionData() {
        return directionData;
    }

    public void setDirectionData(DirectionDataNew directionData) {
        this.directionData = directionData;
    }
}
