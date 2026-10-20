package com.yzj.jep.app.sys.code;

import com.yzj.jep.core.code.FixedDict;

@FixedDict(label = "getLabel")
public enum ExportType {

    EXCEL("EXCEL"),
    PDF("PDF");
    private String label;

    private ExportType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
