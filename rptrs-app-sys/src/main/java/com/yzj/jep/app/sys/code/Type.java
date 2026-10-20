package com.yzj.jep.app.sys.code;

import com.yzj.jep.core.code.FixedDict;

@FixedDict(label = "getLabel")
public enum Type {

    ONE(1),
    ZERO(0);
    private int label;

    private Type(int label) {
        this.label = label;
    }

    public int getLabel() {
        return label;
    }

}
