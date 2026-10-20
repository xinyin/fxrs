package com.yzj.jep.app.sys.code;

import com.yzj.jep.core.code.FixedDict;

@FixedDict(label = "getLabel")
public enum ExportRange {

    CURRENT("当前页"),
    ALL("全部");
    private String label;

    private ExportRange(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
